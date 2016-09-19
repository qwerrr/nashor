package com.zhangyue.test.nashor.framework.zookeeper.curator;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 封装配置中心和具体实现不相关的内容
 *
 * @author YanMeng
 * @date 16-9-19
 */
public abstract class ConfigService {

    protected static Logger logger = LoggerFactory.getLogger(ConfigService.class);

    protected volatile Map<String, String> configs = new HashMap<String, String>();

    /**
     * 查询单条配置
     * @param key
     * @return
     */
    public String getConfig(String key){
        return configs.get(key);
    }

    /**
     * 查询所有配置
     * @return
     */
    public Map<String, String> getConfigs(){
        Map<String, String> map = new HashMap<String, String>();
        map.putAll(configs);
        return map;
    }

    /**
     * 保存配置, 如果配置已经存在, 不修改
     * @param key
     * @param value
     * @return
     */
    public boolean saveConfig(String key, String value) {
        if(configs.containsKey(key)){
            return Boolean.FALSE;
        }else{
            configs.put(key, value);
        }
        return Boolean.TRUE;
    }

    /**
     * 修改配置, 如果配置不存在, 不新增
     * @param key
     * @param value
     * @return
     */
    public boolean updateConfig(String key, String value) {
        if(configs.containsKey(key)){
            configs.put(key, value);
        }else{
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    /**
     * 配置不存在, 新增; 存在, 修改
     * @param key
     * @param value
     * @return
     */
    public boolean saveOrUpdateConfig(String key, String value) {
        configs.put(key, value);
        return Boolean.TRUE;
    }

    /**
     * 删除配置
     * @param key
     * @return
     */
    public boolean removeConfig(String key){
        if(configs.containsKey(key)){
            configs.remove(key);
        }else{
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }
}
