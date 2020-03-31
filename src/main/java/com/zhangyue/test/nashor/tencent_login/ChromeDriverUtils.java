package com.zhangyue.test.nashor.tencent_login;

import org.openqa.selenium.chrome.ChromeDriver;

/**
 * @desc:
 * @author: YanMeng
 * @date: 2019-11-04
 */
public class ChromeDriverUtils {

    public static boolean containElementById(ChromeDriver driver, String target){
        try {
            return driver.findElementsById(target).get(0) != null;
        }catch (Exception e){
            return false;
        }
    }
    public static boolean containElementByXPath(ChromeDriver driver, String target){
        try {
            return driver.findElementsByXPath(target).get(0) != null;
        }catch (Exception e){
            return false;
        }
    }

    public static boolean containElementByClassName(ChromeDriver driver, String target){
        try {
            return driver.findElementsByClassName(target).get(0) != null;
        }catch (Exception e){
            return false;
        }
    }

    public static boolean containElementByCssSelector(ChromeDriver driver, String target){
        try {
            return driver.findElementsByCssSelector(target).get(0) != null;
        }catch (Exception e){
            return false;
        }
    }

    public static boolean containElementByLinkText(ChromeDriver driver, String target){
        try {
            return driver.findElementsByLinkText(target).get(0) != null;
        }catch (Exception e){
            return false;
        }
    }

    public static boolean containElementByName(ChromeDriver driver, String target){
        try {
            return driver.findElementsByName(target).get(0) != null;
        }catch (Exception e){
            return false;
        }
    }

    public static boolean containElementByPartialLinkText(ChromeDriver driver, String target){
        try {
            return driver.findElementsByPartialLinkText(target).get(0) != null;
        }catch (Exception e){
            return false;
        }
    }
}
