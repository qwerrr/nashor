package com.zhangyue.test.nashor.influxdb;

import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Point;
import org.influxdb.dto.Pong;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;
import java.io.IOException;

public abstract class InfluxConnector implements Closeable{

    protected static Logger logger = LoggerFactory.getLogger(InfluxConnector.class);
    protected InfluxDB influxDB;

    /**
     * @param url 例: http://127.0.0.1:8086
     */
    public InfluxConnector(String url){
        influxDB = InfluxDBFactory.connect(url);
        Pong pong = influxDB.ping();
        logger.info("influxDB连接成功: {}", pong.toString());
    }

    protected void runable(){
        if(influxDB == null){
            throw new IllegalStateException("连接influxdb失败!");
        }
    }

    @Override
    protected void finalize() throws Throwable {
        close();
    }

    @Override
    public void close() throws IOException {
        if(influxDB != null) influxDB.close();
    }

    protected abstract void write(String dbName, Point... points);
}
