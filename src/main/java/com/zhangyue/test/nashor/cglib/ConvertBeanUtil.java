package com.zhangyue.test.nashor.cglib;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import net.sf.cglib.beans.BeanCopier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 用于java bean类型转换的工具类, 通过cglib自动注入匹配的属性(根据get/set方法判断)
 * 没有扩展支持BeanCopier的Convert部分
 *
 * @author YanMeng
 * @date 16-9-28
 */
public class ConvertBeanUtil {

    private static Logger LOGGER = LoggerFactory.getLogger(ConvertBeanUtil.class);

    /**
     * 因为BeanCopier创建比较耗费资源, 所以作为每个转换copier为单例
     */
    private static ConcurrentHashMap<String, BeanCopier> cache = new ConcurrentHashMap<String, BeanCopier>();


    //==================================================================================================== private method start
    private static BeanCopier getCopier(Class source, Class target){

        String key = source.getName() + target.getName();
        BeanCopier copier = cache.get(key);

        if(copier == null){
            copier = createBeanCopier(source, target, key);
        }
        return copier;
    }

    @SuppressWarnings({"rawtypes"})
    private static BeanCopier createBeanCopier(Class sourceClass, Class targetClass, String cacheKey){
        BeanCopier copier = BeanCopier.create(sourceClass, targetClass, Boolean.FALSE);
        cache.putIfAbsent(cacheKey, copier);
        return cache.get(cacheKey);
    }
    //==================================================================================================== private method end


    //==================================================================================================== public method begin
    public static <T> T copy(Object source, Class<T> targetClass) {

        if(source == null || targetClass == null){
            return null;
        }

        T target;
        try {
            target = targetClass.newInstance();
        } catch (Exception e) {
            LOGGER.error("", e);
            return null;
        }

        copy(source, target);

        return target;
    }

    public static void copy(Object source, Object target){

        if(source == null || target == null){
            return ;
        }

        getCopier(source.getClass(), target.getClass()).copy(source, target, null);
    }

    public static <T> List<T> copyList(List<?> sourceList, Class<T> targetType){

        if(sourceList == null || targetType == null){
            return null;
        }

        List<T> targetList = null;
        try {
            targetList = sourceList.getClass().newInstance();
        } catch (Exception e) {
            LOGGER.error("", e);
            return null;
        }

        for(Object source : sourceList){
            targetList.add(copy(source, targetType));
        }

        return targetList;
    }
    //==================================================================================================== public method end
}
