package com.zhangyue.test.nashor.framework.zookeeper.example;

import java.io.IOException;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;

import com.zhangyue.test.nashor.framework.zookeeper.ZkConnFactory;

/**
 * ZK实现配置管理
 *
 * @author YanMeng
 * @date 16-9-2
 */
public class ConfigService {

    private static final String DEFAULT_CONFIG_SERVICE_ROOT = "/ConfigService";

    private static String root = DEFAULT_CONFIG_SERVICE_ROOT;
    private static boolean inited = false;

    private static ZooKeeper zkClient;

    public static boolean init(String ip, int port) throws IOException, KeeperException, InterruptedException {
        if(ConfigService.inited){
            return Boolean.FALSE;
        }
        zkClient = ZkConnFactory.getZk(ip, port);

        if(zkClient.exists(DEFAULT_CONFIG_SERVICE_ROOT, false) == null){
            zkClient.create(DEFAULT_CONFIG_SERVICE_ROOT, "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        }

        ConfigService.inited = Boolean.TRUE;
        return ConfigService.inited;
    }


    public static boolean init(String ip, int port, String root) throws IOException, KeeperException, InterruptedException {
        if(init(ip, port)){
            ConfigService.root = root;
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    /**
     * 设置配置信息, 如果已经存在, 不进行更新
     * @param key
     * @param value
     * @return
     */
    public boolean setConfig(String key, String value){
        String path = root.concat(key.startsWith("/") ? key : "/").concat(key);
        return false;
    }

    public boolean updateConfig(String key, String value){
        return false;
    }


    public static void main(String[] args) {



        boolean z = false;
        char c = '张';
        byte b = 123;
        short s = 1234;
        int i = 1234;
        long l = 1234l;

        float f = 123.45f;
        double d = 1234.567;
        String str = "asdf987987asdfwqwer321as6fd546qw6e5r413as2df465as4sdf65qw1er32165a4sdf68a7sdf654asd6f54qwe98r76a5s4ef65a4sdf987asd6f541qw63er9qwe87654as1df";

        long now = 0;
        long now_ = 0;
        StringBuilder stringBuilder = new StringBuilder();
        StringBuffer stringBuffer = new StringBuffer();
        String result = "";

        //840955+1121878+821794+906758+555707+818203+904464+846339+764162+921868+548657+839920+870798+647095
        //774826+543828+781705+792158+796456+748570+817003+808725+788084+812693+798535+848933+785519+797709+795444
//        now = System.nanoTime();
//        stringBuilder.append(z).append(c).append(b).append(s).append(i).append(l).append(f).append(d).append(str);
//        now_ = System.nanoTime();
//        System.out.println(now_ - now);
//        System.out.println(stringBuilder.toString());

        //726920+646354+841949+808910+690499+796241+837426+943814+778104+847847+724374+806683+755741+795251
        //817397+818147+796519+841634+564433+844597+904607+810555+551052+797372+772799+824489+951124+547631+760206
//        now = System.nanoTime();
//        stringBuffer.append(z).append(c).append(b).append(s).append(i).append(l).append(f).append(d).append(str);
//        now_ = System.nanoTime();
//        System.out.println(now_ - now);
//        System.out.println(stringBuffer.toString());

        //1197107+870803+924021+813591+812390+779721+777119+748173+851193+781041+791724+545764+783085+843561+986899+888972
        now = System.nanoTime();
        result = result+z+c+b+s+i+l+f+d+str;
        now_ = System.nanoTime();
        System.out.println(now_ - now);
        System.out.println(result);



        /*
        761792
        95608
        897082
        98167
         */
    }
}

