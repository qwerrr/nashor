package com.zhangyue.test.nashor.java.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author YanMeng
 * @date 16-10-31
 */
public class TestAnnotaionParser {

    public Class<TargetClass> target;

    Logger logger = LoggerFactory.getLogger(TestAnnotaionParser.class);

    @Before
    public void preDo(){
        target = TargetClass.class;
    }

    private void printAnnotation(TestAnnotation testAnnotation){
        logger.info("==============================================");
        logger.info("{}", testAnnotation.name());
        logger.info("{}", testAnnotation.id());
        logger.info("{}", testAnnotation.gid());
        logger.info("==============================================");
    }

    @Test
    public void parseClass(){
        Annotation [] annotations = target.getAnnotations();
        for (Annotation annotation : annotations){
            if(annotation.annotationType() == TestAnnotation.class){
                TestAnnotation testAnnotation = (TestAnnotation)annotation;
                printAnnotation(testAnnotation);
            }
        }
    }

    @Test
    public void parseMethod(){
        Method [] methods = target.getMethods();
        for(Method method : methods){
            Annotation [] annotations = method.getAnnotations();
            for (Annotation annotation : annotations){
                if(annotation.annotationType() == TestAnnotation.class){
                    TestAnnotation testAnnotation = (TestAnnotation)annotation;
                    printAnnotation(testAnnotation);
                }
            }
        }

    }

}
