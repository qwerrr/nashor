package com.zhangyue.test.nashor.statsd;

/**
 * StatsdClient.java
 *
 * (C) 2011 Meetup, Inc.
 * Author: Andrew Gwozdziewycz <andrew@meetup.com>, @apgwoz
 *
 * statsD官方java客户端
 *
 * Counter:
 *  描述:
 *      计数器, 适用于频繁增加/减少的数据; 每10s进行归档记录, 数据项清零
 *  相关方法:
 *      increment
 *      decrement
 * Gauge:
 *      标准数据; 每10s进行归档记录, 数据项数据保留
 *  相关方法:
 *      gauge
 * Timing:
 *      时间戳数据, 适用于需要计算平均值,中位数,和值,最大值,最小值; 每10s进行归档记录, 数据项清零
 *  相关方法:
 *      timing
 * Set:
 *      集合数据, 适用于统计数据出现次数; 每10s进行归档记录, 数据项清零
 *  相关方法:
 *      set
 *
 *
 *
 *
 * Example usage:
 *
 *    StatsdClient client = new StatsdClient("statsd.example.com", 8125);
 *    // increment by 1
 *    client.increment("foo.bar.baz");
 *    // increment by 10
 *    client.increment("foo.bar.baz", 10);
 *    // sample rate
 *    client.increment("foo.bar.baz", 10, .1);
 *    // increment multiple keys by 1
 *    client.increment("foo.bar.baz", "foo.bar.boo", "foo.baz.bar");
 *    // increment multiple keys by 10 -- yeah, it's "backwards"
 *    client.increment(10, "foo.bar.baz", "foo.bar.boo", "foo.baz.bar");
 *    // multiple keys with a sample rate
 *    client.increment(10, .1, "foo.bar.baz", "foo.bar.boo", "foo.baz.bar");
 *
 *    // To enable multi metrics (aka more than 1 metric in a UDP packet) (disabled by default)
 *    client.enableMultiMetrics(true);  //disable by passing in false
 *    // To fine-tune udp packet buffer size (default=1500)
 *    client.setBufferSize((short) 1500);
 *    // To force flush the buffer out (good idea to add to your shutdown path)
 *    client.flush();
 *
 *
 * Note: For best results, and greater availability, you'll probably want to
 * create a wrapper class which creates a static client and proxies to it.
 *
 * You know... the "Java way."
 */

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.StandardSocketOptions;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.Locale;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.log4j.Logger;

public class StatsdClient extends TimerTask {

    private ByteBuffer sendBuffer;
    private Timer flushTimer;
    private boolean multi_metrics = false;

    private static final Random RNG = new Random();
    private static final Logger log = Logger.getLogger(StatsdClient.class.getName());

    private final InetSocketAddress _address;
    private final DatagramChannel _channel;

    public StatsdClient(String host, int port, boolean unBlocking, short bufferSize) throws UnknownHostException, IOException {
        this(InetAddress.getByName(host), port, unBlocking, bufferSize);
    }

    public StatsdClient(InetAddress host, int port, boolean unBlocking, short bufferSize) throws IOException {
        _address = new InetSocketAddress(host, port);
        _channel = DatagramChannel.open();
        /* Put this in non-blocking mode so send does not block forever. */
        _channel.configureBlocking(unBlocking);
        /* Increase the size of the output buffer so that the size is larger than our buffer size. */
        _channel.setOption(StandardSocketOptions.SO_SNDBUF, doublePow2(bufferSize, 1));
        setBufferSize(bufferSize);
    }

    /**
     * 递归获取比bufferSize大的2的n次方, 最后结果乘2
     * 保证output buffer比buffersize大
     *
     * @param bufferSize
     * @param current
     * @return
     */
    private static Integer doublePow2(short bufferSize, int current){
        return current > bufferSize ? current*2 : doublePow2(bufferSize, current*2);
    }

    protected void finalize() {
        flush();
    }

    public synchronized void setBufferSize(short packetBufferSize) {
        if(sendBuffer != null) {
            flush();
        }
        sendBuffer = ByteBuffer.allocate(packetBufferSize);
    }

    public synchronized void enableMultiMetrics(boolean enable) {
        multi_metrics = enable;
    }

    public synchronized boolean startFlushTimer(long period) {
        if(flushTimer == null) {
            // period is in msecs
            if(period <= 0) { period = 2000; }
            flushTimer = new Timer();

            // We pass this object in as the TimerTask (which calls run())
            flushTimer.schedule((TimerTask)this, period, period);
            return true;
        }
        return false;
    }

    public synchronized void stopFlushTimer() {
        if(flushTimer != null) {
            flushTimer.cancel();
            flushTimer = null;
        }
    }

    public void run() {     // used by Timer, we're a Runnable TimerTask
        flush();
    }


    public boolean timing(String key, int value) {
        return timing(key, value, 1.0);
    }

    public boolean timing(String key, int value, double sampleRate) {
        return send(sampleRate, String.format(Locale.ENGLISH, "%s:%d|ms", key, value));
    }

    public boolean decrement(String key) {
        return increment(key, -1, 1.0);
    }

    public boolean decrement(String key, int magnitude) {
        return decrement(key, magnitude, 1.0);
    }

    public boolean decrement(String key, int magnitude, double sampleRate) {
        magnitude = magnitude < 0 ? magnitude : -magnitude;
        return increment(key, magnitude, sampleRate);
    }

    public boolean decrement(String... keys) {
        return increment(-1, 1.0, keys);
    }

    public boolean decrement(int magnitude, String... keys) {
        magnitude = magnitude < 0 ? magnitude : -magnitude;
        return increment(magnitude, 1.0, keys);
    }

    /**
     * 自减操作
     * 每10s中进行自动归档, 数据项被记录到influxdb并且数据项被清空为0
     *
     * @param magnitude 自减数值
     * @param sampleRate 抽样比例 - 小于1时, 按照${sampleRate*100}%比例抽样; 例: 传值0.1,按10%抽样
     *                   在数据量较大时, 请评估合适的比例进行抽样
     * @param keys 数据项名
     * @return
     */
    public boolean decrement(int magnitude, double sampleRate, String... keys) {
        magnitude = magnitude < 0 ? magnitude : -magnitude;
        return increment(magnitude, sampleRate, keys);
    }

    public boolean increment(String key) {
        return increment(key, 1, 1.0);
    }

    public boolean increment(String key, int magnitude) {
        return increment(key, magnitude, 1.0);
    }

    public boolean increment(String key, int magnitude, double sampleRate) {
        String stat = String.format(Locale.ENGLISH, "%s:%s|c", key, magnitude);
        return send(sampleRate, stat);
    }

    /**
     * 自增操作
     * 每10s中进行自动归档, 数据项被记录到influxdb并且数据项被清空为0
     * @param magnitude 自增数值
     * @param sampleRate 抽样比例 - 小于1时, 按照${sampleRate*100}%比例抽样; 例: 传值0.1,按10%抽样
     *                   在数据量较大时, 请评估合适的比例进行抽样
     * @param keys 数据项名
     * @return
     */
    public boolean increment(int magnitude, double sampleRate, String... keys) {
        String[] stats = new String[keys.length];
        for (int i = 0; i < keys.length; i++) {
            stats[i] = String.format(Locale.ENGLISH, "%s:%s|c", keys[i], magnitude);
        }
        return send(sampleRate, stats);
    }

    /**
     * 设置数值
     * @param key 数据项名
     * @param magnitude
     * @return
     */
    public boolean gauge(String key, double magnitude){
        return gauge(key, magnitude, 1.0);
    }

    public boolean gauge(String key, double magnitude, double sampleRate){
        final String stat = String.format(Locale.ENGLISH, "%s:%s|g", key, magnitude);
        return send(sampleRate, stat);
    }

    public boolean set(String key, String magnitude){
        return set(key, magnitude, 1.0);
    }

    public boolean set(String key, String magnitude, double sampleRate){
        final String stat = String.format(Locale.ENGLISH, "%s:%s|s", key, magnitude);
        return send(sampleRate, stat);
    }

    private boolean send(double sampleRate, String... stats) {

        boolean retval = false; // didn't send anything
        if (sampleRate < 1.0) {
            for (String stat : stats) {
                if (RNG.nextDouble() <= sampleRate) {
                    stat = String.format(Locale.ENGLISH, "%s|@%f", stat, sampleRate);
                    if (doSend(stat)) {
                        retval = true;
                    }
                }
            }
        } else {
            for (String stat : stats) {
                if (doSend(stat)) {
                    retval = true;
                }
            }
        }

        return retval;
    }

    private synchronized boolean doSend(String stat) {
        try {
            final byte[] data = stat.getBytes("utf-8");

            // If we're going to go past the threshold of the buffer then flush.
            // the +1 is for the potential '\n' in multi_metrics below
            if(sendBuffer.remaining() < (data.length + 1)) {
                flush();
            }

            if(sendBuffer.position() > 0) {         // multiple metrics are separated by '\n'
                sendBuffer.put( (byte) '\n');
            }

            sendBuffer.put(data);   // append the data

            if(! multi_metrics) {
                flush();
            }

            return true;

        } catch (IOException e) {
            log.error(
                    String.format("Could not send stat %s to host %s:%d", sendBuffer.toString(), _address.getHostName(),
                            _address.getPort()), e);
            return false;
        }
    }

    public synchronized boolean flush() {
        try {
            final int sizeOfBuffer = sendBuffer.position();

            if(sizeOfBuffer <= 0) { return false; } // empty buffer

            // send and reset the buffer
            sendBuffer.flip();
            final int nbSentBytes = _channel.send(sendBuffer, _address);
            sendBuffer.limit(sendBuffer.capacity());
            sendBuffer.rewind();

            if (sizeOfBuffer == nbSentBytes) {
                return true;
            } else {
                log.error(String.format(
                        "Could not send entirely stat %s to host %s:%d. Only sent %d bytes out of %d bytes", sendBuffer.toString(),
                        _address.getHostName(), _address.getPort(), nbSentBytes, sizeOfBuffer));
                return false;
            }

        } catch (IOException e) {
            /* This would be a good place to close the channel down and recreate it. */
            log.error(
                    String.format("Could not send stat %s to host %s:%d", sendBuffer.toString(), _address.getHostName(),
                            _address.getPort()), e);
            return false;
        }
    }
}