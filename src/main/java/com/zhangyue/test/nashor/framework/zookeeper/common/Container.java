package com.zhangyue.test.nashor.framework.zookeeper.common;

/**
 * 每个Container为一个线程, 模拟一个简单的虚拟机, 可以进行输出/输出, 在发生异常或者关闭前可以保持所有数据的状态
 *
 * @author YanMeng
 * @date 16-9-5
 */
public class Container {

    private Container self = null;
    private Runner runner = null;
    private Mirror mirror = null;

    private boolean isStarted = Boolean.FALSE;

    private Container(){}

    public static Container getContainer(){
        Container container = new Container();
        container.self = container;
        return container.self;
    }

    public static Container getStartedContainer(Mirror mirror){
        Container container = getContainer();
        container.start(mirror);
        return container;
    }

    public boolean start(Mirror mirror){

        if(!isStarted){

            if(!loadMirror(mirror)){
                return Boolean.FALSE;
            }
            if(!init()){
                return Boolean.FALSE;
            }

            isStarted = Boolean.TRUE;
        }
        return isStarted;
    }

    private boolean init(){
        if(mirror != null){
            Runner runner = new Runner(mirror);
            this.runner = runner;
            return Boolean.TRUE;
        }

        return Boolean.FALSE;
    }

    private boolean loadMirror(Mirror mirror){
        if(mirror == null){
            this.mirror = mirror;
            return mirror != null;
        }
        return false;
    }

    private static class Runner extends Thread{

        private Mirror mirror = null;
        Runner(Mirror mirror){
            this.mirror = mirror;
        }

        @Override
        public void run() {
            super.run();
        }
    }
}
