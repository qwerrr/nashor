package com.zhangyue.test.nashor.influxdb.impl;

import com.zhangyue.test.nashor.influxdb.InfluxConnector;
import org.influxdb.dto.Point;

import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 * 适用于记录次数频繁的情况
 *
 * 按 缓存条数/时间 自动刷新缓存
 */
public class CacheInfluxConnector extends InfluxConnector {

    // 缓冲条数
    private Integer flushNum;
    // 缓冲刷新时间间隔(单位:ms)
    private Integer flushIntervalMs;

    public CacheInfluxConnector(String url, Integer flushNum, Integer flushIntervalMs) {
        super(url);
        this.flushNum = flushNum;
        this.flushIntervalMs = flushIntervalMs;
        influxDB.enableBatch(flushNum,flushIntervalMs,TimeUnit.MILLISECONDS);
    }

    @Override
    public void write(String dbName, Point... points) {
        runable();
        if(points != null && points.length != 0) {
            // autogen指当前库默认的保留策略
            Arrays.asList(points).forEach(point -> influxDB.write(dbName, "autogen", point));
        }
    }

    @Override
    public void close() throws IOException {
        if(influxDB != null){
            influxDB.flush();
            influxDB.close();
        }
    }
}
