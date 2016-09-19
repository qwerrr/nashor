package com.zhangyue.test.nashor.framework.zookeeper.curator.config_service;

import java.util.List;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;

import com.zhangyue.test.nashor.framework.zookeeper.curator.ConfigService;
import com.zhangyue.test.nashor.framework.zookeeper.curator.common.ZkContext;
import com.zhangyue.test.nashor.framework.zookeeper.curator.common.ZkService;

/**
 * ZK实现配置管理
 *
 * @author YanMeng
 * @date 16-9-2
 */
public class ZkConfigService extends ConfigService {

    //TODO 项目中注解注入
    private ZkService zkService;
    private String root = ZkContext.DEFAULT_CONFIG_SERVICE_ROOT;

    public ZkConfigService(String root){

        super();

        if(root != null && !root.trim().equals("")){
            this.root = root;
        }
        init();
    }

    public void init(){
        logger.info("[初始化配置中心]开始");
        try {
            if(!zkService.exists(root)){
                //创建根目录
                zkService.create(root, "".getBytes(),CreateMode.PERSISTENT);
            }else{
                //加载配置数据到本地, 并绑定配置内容修改监听
                List<String> childrens = zkService.getChildren(root);
                for (String key : childrens){
                    super.saveConfig(key, new String(zkService.getData(keyToPath(key))));
                    zkService.ndcCallBack(keyToPath(key), new ConfUpdateWatcher(zkService, configs, root));
                }
            }
            //绑定配置增加/删除监听
            zkService.nccCallBack(root, new ConfAddOrDelWatcher(zkService, configs, root));
        } catch (Exception e) {
            logger.error("[初始化配置中心]异常:{}", e);
        }
        logger.info("[初始化配置中心]结束");
    }

    public String keyToPath(String key){
        return root.concat(ZkContext.PATH_SEPARATOR).concat(key);
    }


    @Override
    public boolean saveConfig(String key, String value) {

        try {
            if(super.saveConfig(key, value)){   //本地保存成功
                String path = keyToPath(key);
                if(!zkService.exists(path)){    //远程也不存在
                    //创建并添加配置修改监听
                    zkService.create(path, value.getBytes(), CreateMode.PERSISTENT);
                    zkService.ndcCallBack(path, new ConfUpdateWatcher(zkService, configs, root));
                    return Boolean.TRUE;
                }
            }
        } catch (Exception e) {
            logger.error("[保存配置]异常:{}", e);
        }

        return Boolean.FALSE;
    }

    /**
     * 更新配置信息, 如果不存在, 不进行创建
     * @param key
     * @param value
     * @return
     */
    public boolean updateConfig(String key, String value) {

        try {
            if(super.updateConfig(key, value)){     //本地修改成功
                String path = keyToPath(key);
                if(zkService.exists(path)){         //远程也存在
                    //修改远程配置
                    zkService.setData(path, value.getBytes());
                    return Boolean.TRUE;
                }
            }
        } catch (Exception e) {
            logger.error("[修改配置]异常:{}", e);
        }
        return Boolean.FALSE;
    }


    /**
     * 设置或更新配置信息
     * @param key
     * @param value
     * @return
     * @throws KeeperException
     * @throws InterruptedException
     */
    public boolean saveOrUpdateConfig(String key, String value) {

        super.saveOrUpdateConfig(key, value);
        String path = keyToPath(key);

        try {
            if(zkService.exists(path)){
                zkService.setData(path, value.getBytes());
            }else{
                zkService.create(path, value.getBytes(), CreateMode.PERSISTENT);
                zkService.ndcCallBack(path, new ConfUpdateWatcher(zkService, configs, root));
            }
        }catch (Exception e) {
            logger.error("[新增/修改配置]异常:{}", e);
            return Boolean.FALSE;
        }

        return Boolean.TRUE;
    }

    public boolean removeConfig(String key) {

        try {
            if(super.removeConfig(key)){        //本地删除成功
                String path = keyToPath(key);
                if(zkService.exists(path)){     //远程也存在
                    //删除远程配置
                    zkService.delete(path);
                    return Boolean.TRUE;
                }
            }

        }catch (Exception e) {
            logger.error("[新增/修改配置]异常:{}", e);
        }
        return Boolean.FALSE;
    }

}

