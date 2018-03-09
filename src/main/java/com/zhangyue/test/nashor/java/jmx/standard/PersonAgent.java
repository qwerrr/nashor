package com.zhangyue.test.nashor.java.jmx.standard;

import javax.management.*;
import java.lang.management.ManagementFactory;


/**
 * StandardMBean 测试
 */
public class PersonAgent {

    public static void main(String[] args) throws MalformedObjectNameException, NotCompliantMBeanException, InstanceAlreadyExistsException, MBeanRegistrationException, InterruptedException {
        MBeanServer server = ManagementFactory.getPlatformMBeanServer();

        //ObjectName规范 - 域名:name=MBean名称(域名和MBean的名称任意)
        ObjectName personName = new ObjectName("nashor.jmx.test:name=person");
        server.registerMBean(new Person(), personName);


        jConsoleTest();


    }

    public static void jConsoleTest() throws InterruptedException {
        Thread.sleep(60*60*1000);
    }

    public static void jdmkTest() throws MalformedObjectNameException {
        ObjectName adapterName = new ObjectName("PersionAgent:name=htmladapter,port=8080");

    }
}
