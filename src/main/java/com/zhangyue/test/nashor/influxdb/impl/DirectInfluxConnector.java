package com.zhangyue.test.nashor.influxdb.impl;

import com.zhangyue.test.nashor.influxdb.InfluxConnector;
import org.influxdb.InfluxDB;
import org.influxdb.dto.BatchPoints;
import org.influxdb.dto.Point;

import java.util.Arrays;

/**
 * 无缓存, 直接写入influxdb
 */
public class DirectInfluxConnector extends InfluxConnector {


    public DirectInfluxConnector(String url) {
        super(url);
    }

    @Override
    public void write(String dbName, Point... points) {

        runable();

        if(points != null && points.length != 0) {
            BatchPoints batchPoints = BatchPoints
                    .database(dbName)
                    .retentionPolicy("autogen")
                    .consistency(InfluxDB.ConsistencyLevel.ALL)
                    .build();

            Arrays.asList(points).forEach(batchPoints::point);
            influxDB.write(batchPoints);
        }
    }
}
