package com.zhangyue.test.nashor.kafka;

import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import kafka.serializer.StringEncoder;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author YanMeng
 * @date 16-11-10
 */
public class kafkaProducer {

    Logger logger = LoggerFactory.getLogger(kafkaProducer.class);

    private Producer getProducer(){
        Properties properties = new Properties();

        /** 声明zk */
        properties.put("zookeeper.connect", "127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2183,127.0.0.1:2184,127.0.0.1:2185");
        /** 声明kafka broker, 格式:192.168.1.111:9093,192.168.1.112:9094 */
        properties.put("metadata.broker.list", "127.0.0.1:9092");
        /** 默认为serializer.class */
        properties.put("serializer.class", StringEncoder.class.getName());
        /** 可选配置，如果不配置，则使用默认的partitioner */
        //properties.put("partitioner.class", "com.catt.kafka.demo.PartitionerDemo");
        /**
         * 触发acknowledgement机制，否则是fire and forget，可能会引起数据丢失
         * 值为0,1,-1,可以参考
         * http://kafka.apache.org/08/configuration.html
         */
        //properties.put("request.required.acks", "1");

        return new KafkaProducer(properties);
    }

    /**
     * 推送消息
     * topic 不可为null
     *
     * @param topic The topic the record will be appended to
     * @param partition The partition to which the record should be sent
     * @param timestamp The timestamp of the record
     * @param key The key that will be included in the record
     * @param value The record contents
     * @return
     */
    public <K,V> boolean push(String topic, Integer partition, Long timestamp, K key, V value) throws ExecutionException, InterruptedException {
        ProducerRecord producerRecord = new ProducerRecord(topic, partition, timestamp, key, value);
        Future<RecordMetadata> future =  getProducer().send(producerRecord);
        while (!future.isDone());
        RecordMetadata recordMetadata = future.get();
        logger.info("push result: {}", recordMetadata);
        return Boolean.TRUE;
    }
}
