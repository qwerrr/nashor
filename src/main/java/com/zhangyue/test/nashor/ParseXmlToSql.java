package com.zhangyue.test.nashor;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.FileConfiguration;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * @author YanMeng
 * @date 17-7-20.
 */
public class ParseXmlToSql {

    public static void main(String[] args) {
        Integer i = 20170713;
        while (i++ < 20170721){
            for (int j = 0; j < 24; j++){
                System.out.println(i + "-" + (j < 10 ? "0"+j : j));
            }
        }
    }

//    public static void main(String[] args) {
//        ParseXmlToSql foo = new ParseXmlToSql();
//        foo.print(foo.load("/home/coderam/task_xz.xml"));
//    }

    private Configuration load(String filePath){
        FileConfiguration configuration = null;
        try {
            configuration = new XMLConfiguration(filePath);
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
        if(configuration != null){
            configuration.setReloadingStrategy(new FileChangedReloadingStrategy());
        }

        return configuration;
    }

    Logger logger = LoggerFactory.getLogger(this.getClass());

    public void print(Configuration conf) {


        //{"output":"/user/importer/prod_sensors/xal_basic/project=xal_filemanager/dt={dt}/hour={hour}/pn={pn}","bin":"/home/apps/etl_xal_sensors/bin/event_data_converter.sh"}
        String a = "insert into action_node(`type`,input_pattern,pool_name,executor_name,args,is_active) values('%s','%s','spark','bigdata-gw00%s.eq-sg-2.apus.com','%s',1);";

        int counter = 0;

        for (int i = 0; ; i++) {
            String group = conf.getString(String.format("triger(%d)[@group]", i));
            String name = conf.getString(String.format("triger(%d)[@name]", i));
            String ok = conf.getString(String.format("triger(%d)[@ok]", i));
            String fail = conf.getString(String.format("triger(%d)[@fail]", i));
            String failover = conf.getString(String.format("triger(%d)[@failover]", i));
            String pool = conf.getString(String.format("triger(%d)[@pool]", i));
            String log = conf.getString(String.format("triger(%d)[@log]", i));

            if (StringUtils.isEmpty(name)) break;

            for (int j = 0; ; j++) {
                String type = conf.getString(String.format("triger(%d).task(%d)[@type]", i, j));
                String ok2 = conf.getString(String.format("triger(%d).task(%d)[@ok]", i, j));
                String fail2 = conf.getString(String.format("triger(%d).task(%d)[@fail]", i, j));
                String failover2 = conf.getString(String.format("triger(%d).task(%d)[@failover]", i, j));
                String pool2 = conf.getString(String.format("triger(%d).task(%d)[@pool]", i, j));
                String log2 = conf.getString(String.format("triger(%d).task(%d)[@log]", i, j));
                String input = conf.getString(String.format("triger(%d).task(%d).input", i, j));
                String output = conf.getString(String.format("triger(%d).task(%d).output", i, j));
                if (StringUtils.isEmpty(type)) break;

                String bin = conf.getString(String.format("triger(%d).task(%d).args.bin", i, j));

                if(StringUtils.isNotEmpty(bin)){
                    bin = bin.replace("/data/importer/xal_etl/basic-etl","/home/apps/etl_xal_basic");
                    bin = bin.replace("/data/importer/xal_etl/sensors-etl","/home/apps/etl_xal_sensors");
                }


                JSONObject args = new JSONObject();

                args.put("output", output);
                if(StringUtils.isNotEmpty(bin)) {
                    args.put("bin", bin);
                }
                args.put("log", "/data/azkaben/executions/logs/{runtime}_"+(counter)+".log");

                System.out.println(String.format(a, type,input, (counter++)%3+1, args.toString()));
            }

        }
    }
}
