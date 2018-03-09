package com.zhangyue.test.nashor.influxdb;

import com.zhangyue.test.nashor.influxdb.impl.CacheInfluxConnector;
import com.zhangyue.test.nashor.influxdb.impl.DirectInfluxConnector;
import org.influxdb.dto.Point;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * influx连接测试类
 *
 *
 * 关于表名
 *      使用 measurement 方法指定
 *      表名规范:{模块}_{业务}_{更新间隔}
 *      更新间隔单位: 周-w; 天-d; 小时-h; 分钟-m; 秒-s
 *      例: hdfs_warehouseinfo_3m
 *
 * 关于time
 *      influxdb主键, 根据情况指定
 *
 * 关于field
 *      存放各类数值
 *
 * 关于tag
 *      存放各类属性
 */
public class InfluxDBTest {

    InfluxConnector conn = null;

    /**
     * 测试直接发送, 无缓存
     * @throws InterruptedException
     */
    @Test
    public void writeDirect() throws InterruptedException {

        conn = new DirectInfluxConnector("http://127.0.0.1:8086");

        Point point = Point.measurement("hdfs_warehouseinfo_3m")
                .time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
                .addField("size", 82698321L)
                .addField("is_success", true)
                .addField("data_count", 3062912L)
                .tag("pn", "launcher")
                .tag("et", "records")
                .tag("ct", "dw")
                .build();

        Thread.sleep(100);

        Point point_ = Point.measurement("hdfs_warehouseinfo_3m")
                .time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
                .addField("size", 623981L)
                .addField("is_success", false)
                .addField("data_count", 23137L)
                .tag("pn", "acecamera")
                .tag("et", "records")
                .tag("ct", "dw")
                .build();

        conn.write("test", point, point_);
    }


    /**
     * 测试缓存发送
     * @throws InterruptedException
     */
    @Test
    public void writeCache() throws InterruptedException {

        conn = new CacheInfluxConnector("http://127.0.0.1:8086", 1000, 100);

        Point point = Point.measurement("hdfs_warehouseinfo_3m")
                .time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
                .addField("size", 82698321L)
                .addField("is_success", true)
                .addField("data_count", 3062912L)
                .tag("pn", "launcher")
                .tag("et", "records")
                .tag("ct", "dw")
                .build();

        Thread.sleep(100);

        Point point_ = Point.measurement("hdfs_warehouseinfo_3m")
                .time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
                .addField("size", 623981L)
                .addField("is_success", false)
                .addField("data_count", 23137L)
                .tag("pn", "acecamera")
                .tag("et", "records")
                .tag("ct", "dw")
                .build();

        conn.write("test", point, point_);

        Thread.sleep(100);
    }

    /**
     * 测试使用POJO进行写入
     */
}
