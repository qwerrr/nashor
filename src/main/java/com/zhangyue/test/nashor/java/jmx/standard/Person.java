package com.zhangyue.test.nashor.java.jmx.standard;

public class Person implements PersonMBean {

    String name;
    Integer age;

    public String getName() {
        System.out.println(String.format("getName() returned %s", name));
        return name;
    }

    public void setName(String name) {
        System.out.println(String.format("setName() set %s", age));
        this.name = name;
    }

    public Integer getAge() {
        System.out.println(String.format("getAge() returned %s", age));
        return age;
    }

    public void setAge(Integer age) {
        System.out.println(String.format("setAge() set %s", age));
        this.age = age;
    }

    public void hello() {
        System.out.println("Hello JMX!");
    }
}
