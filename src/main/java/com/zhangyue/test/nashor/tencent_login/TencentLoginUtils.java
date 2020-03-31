package com.zhangyue.test.nashor.tencent_login;

import lombok.extern.slf4j.Slf4j;
import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import java.util.Random;
import java.util.stream.Collectors;

/**
 * @desc:
 * @author: YanMeng
 * @date: 2019-10-30
 * @url https://opencv.org/
 */
@Slf4j
public class TencentLoginUtils {

    private static final Random RAND = new Random(System.currentTimeMillis());
    private static final String YOULIANGHUI_URL = "https://e.qq.com/dev/index.html";

    // 本地图片缓存地址,                todo 上线改为线上路径
    private static final String IMG_LOCAL_BASEPATH = "/Users/coderam/soft/";
    static {
        // 设置webdriver本地路径,      todo 上线改为线上路径
        System.setProperty("webdriver.chrome.driver", "/Users/coderam/soft/chromedriver");
        // 加载本地库文件,             todo 上线改为线上路径
        System.load("/usr/local/lib/libopencv_java412.dylib");
    }


    public static void main(String[] args) {
        String cookie = loginYouLiangHui("1325245900", "zxcasdqwe123");
        log.info("cookie: {}", cookie);
    }

    /**
     * 登陆优量汇, 获取cookie
     * @param username
     * @param password
     * @return
     */
    public static String loginYouLiangHui(String username, String password){
        ChromeDriver driver = new ChromeDriver();
        try {
            driver.get(YOULIANGHUI_URL);
            Thread.sleep(3000);         // 等待优量汇首页加载
            return login(driver, username, password);
        }catch (Exception e){
            log.error("访问优量汇首页异常: ", e);
            return null;
        }finally {
            try {
                Thread.sleep(60000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            driver.quit();
        }
    }


    /**
     * 微信统一授权登陆模块登陆
     * @param driver
     * @param username
     * @param password
     * @return
     */
    private static String login(ChromeDriver driver, String username, String password){
        try {
            driver.findElementById("loginBtn").click();             // 点击登陆按钮
            Thread.sleep(3000);                                     // 等待授权登陆页加载
        }catch (Exception e){
            log.error("跳转登陆页异常: ", e);
            return null;
        }

        try {
            driver.switchTo().frame("ptlogin_iframe");                 // 选中登陆iframe框
            Thread.sleep(RAND.nextInt(3000)+1000);
            driver.findElementById("switcher_plogin").click();      // 切换账号密码登陆
            driver.findElementById("u").sendKeys(username);
            driver.findElementById("p").sendKeys(password);
            Thread.sleep(RAND.nextInt(3000)+1000);
            driver.findElementById("login_button").click();         // 点击登陆按钮
            Thread.sleep(3000);                                     // 等待跳转页加载

        }catch (Exception e){
            log.error("登陆异常: ", e);
            return null;
        }

        // 出现滑块页面, 模拟滑块滑动
        if(ChromeDriverUtils.containElementById(driver, "tcaptcha_iframe")){
            slideImg(driver);
            driver.switchTo().defaultContent();
            driver.switchTo().frame("ptlogin_iframe");
        }

        if(!driver.getCurrentUrl().contains("graph.qq.com")){
            log.info("登陆成功: {}", driver.getCurrentUrl());
            String cookies = driver.manage().getCookies().stream()
                    .map(cookie -> cookie.getName()+"="+cookie.getValue()+"; ")
                    .collect(Collectors.joining());
            return cookies.substring(0, cookies.length()-1);
        }else {
            doSomethingQuietly(() ->
                    log.info("错误信息: {}", driver.findElementById("err_m").getText()));
            return null;
        }
    }

    private static void slideImg(ChromeDriver driver){

        for(int i = 0; i < 10; i++){
            driver.switchTo().defaultContent();
            driver.switchTo().frame("ptlogin_iframe");

            if(!driver.getCurrentUrl().contains("graph.qq.com")){
                log.info("登陆成功: {}", driver.getCurrentUrl());
                break;
            }else {
                // 还在当前页面时, 获取提示或者错误信息
                log.info("登陆失败: {}", driver.getCurrentUrl());
                if(ChromeDriverUtils.containElementById(driver, "tcaptcha_iframe")){
                    doSomethingQuietly(() ->
                            log.info("滑块滑动错误, 提示信息: {}", driver.findElementById("guideText").getText()));
                }else{
                    // 登陆后已不在滑块页面, 退出
                    break;
                }
            }

            String slideBlockLoaclPath = IMG_LOCAL_BASEPATH + "slideBlock_" + System.currentTimeMillis() + ".png";
            String slideBgLoaclPath = IMG_LOCAL_BASEPATH + "slideBg_" + System.currentTimeMillis() + ".png";

            try {
                driver.switchTo().frame("tcaptcha_iframe");     // 选中滑块iframe框
                driver.findElementById("e_reload").click();  // 刷新滑块图
                Thread.sleep(2000);

                String slideBlockUrl = driver.findElementById("slideBlock").getAttribute("src");
                String slideBgUrl = driver.findElementById("slideBg").getAttribute("src");
                ImageDownloadUtils.download(slideBlockUrl, slideBlockLoaclPath);
                ImageDownloadUtils.download(slideBgUrl, slideBgLoaclPath);
            }catch (Exception e){
                log.error("加载滑块图片异常, 当前页面已不在活动: ", e);
                continue;
            }

            int[] indexs = matchImg(slideBlockLoaclPath, slideBgLoaclPath, Imgproc.TM_CCOEFF_NORMED);
            // 计算滑动像素, indexs[0]匹配到的x轴偏移量, 280图片在页面像素, 680图片实际像素, 23滑块初始距左侧边距
            int offset = (int)(indexs[0] * (280 / 680.0) - 23);
            // 混淆偏移量
            offset = offset + (RAND.nextInt(3) - 6);
            int remainingOffset = offset;
            log.info("偏移量: {}", offset);
            // 点击并滑动
            try {
                WebElement slideBtn = driver.findElementById("tcaptcha_drag_thumb");    // 滑块按钮
                Actions actions = new Actions(driver).clickAndHold(slideBtn);
                Thread.sleep(RAND.nextInt(200)+100);

                while (remainingOffset > 0){
                    int span = 0;
                    double ratio = remainingOffset/(double)offset;
                    if (ratio < 0.2 || ratio > 0.8){
                        // 开始和结束阶段移动较慢
                        span = 5 + RAND.nextInt(3);
                    } else{
                        // 中间部分移动快
                        span = 10 + RAND.nextInt(6);
                    }
                    remainingOffset -= span;
                    actions.moveByOffset(span, RAND.nextInt(2)-4);
                    actions.pause(RAND.nextInt(100)+50);
                }
                actions.release(slideBtn).perform();

                Thread.sleep(2000);
            }catch (Exception e){
                log.error("滑动滑块异常", e);
                continue;
            }
        }
    }

    private static void doSomethingQuietly(Runnable runnable){
        try {
            runnable.run();
        }catch (Exception e){
            // do nothing
        }
    }

    private static int[] matchImg(String slideBlockLoaclPath, String slideBgLoaclPath, int method){

        // ------------------------ 1.加载图片并二值化
        // 灰度模式加载滑块图和背景图
        Mat slideBlockMat = Imgcodecs.imread(slideBlockLoaclPath, Imgcodecs.IMREAD_GRAYSCALE);
        Mat slideBgMat = Imgcodecs.imread(slideBgLoaclPath, Imgcodecs.IMREAD_GRAYSCALE);

        Mat slideBlockMatBW = new Mat();
        Mat slideBgMatBW = new Mat();
        // 二值化
        Imgproc.threshold(slideBlockMat, slideBlockMatBW, 0, 255, Imgproc.THRESH_BINARY);
        Imgproc.threshold(slideBgMat, slideBgMatBW, 0, 255, Imgproc.THRESH_BINARY| Imgproc.THRESH_OTSU);

        // ------------------------ 2.滑块图转换
        // 截去白边
        Mat tmpMat1 = removeEdge(slideBlockMatBW, 0, 200);
        // 截去黑边
        Mat tmpMat2 = removeEdge(tmpMat1, 1, 50);
        // 裁边2像素, 用于解决背景图中滑块边缘有边框无法正常匹配问题
        Mat tmpMat3 = cut(tmpMat2, 2, 2, tmpMat2.width() - 4, tmpMat2.height() - 4);
        Mat slideBlockMatBW_ = new Mat();
        // 反色
        Core.bitwise_not(tmpMat3, slideBlockMatBW_);

        // ------------------------ 3. 匹配
        Mat result = new Mat();
        Imgproc.matchTemplate(slideBgMatBW, slideBlockMatBW_, result, Imgproc.TM_CCOEFF_NORMED);

        // ------------------------ 4. 获取模板匹配结果
        Core.normalize(result, result,0, 1, Core.NORM_MINMAX, -1, new Mat());
        Core.MinMaxLocResult mmr = Core.minMaxLoc(result);
        double x,y;
        if (method == Imgproc.TM_SQDIFF_NORMED || method == Imgproc.TM_SQDIFF) {
            x = mmr.minLoc.x;
            y = mmr.minLoc.y;
        } else {
            x = mmr.maxLoc.x;
            y = mmr.maxLoc.y;
        }

        // 返回左上右下两点像素坐标
        return new int[]{(int)x, (int)y, (int)x+slideBlockMatBW_.cols(), (int)y+slideBlockMatBW_.rows()};
        // 图片显示, 测试用
//        Imgproc.rectangle(
//                slideBgMat,
//                new Point(x, y),
//                new Point(x+slideBlockMatBW_.cols(),y+slideBlockMatBW_.rows()),
//                new Scalar(0, 255, 0),
//                2,
//                Imgproc.LINE_AA);
//        Imgproc.putText(slideBgMat,"Match Success",new Point(x,y),Imgproc.FONT_HERSHEY_SCRIPT_COMPLEX, 1.0, new Scalar(0, 255, 0),1,Imgproc.LINE_AA);

//        HighGui.imshow("模板匹配", slideBgMat);
//        HighGui.imshow("滑块", slideBlockMatBW_);
//        HighGui.waitKey(0);

    }

    /**
     *
     * @param grayMat
     * @param mode          0-白边; 1-黑边;
     * @param blackValue    阈值, 控制截掉黑边/白边中目标颜色的比例 0~255
     * @return
     */
    public static Mat removeEdge(Mat grayMat, int mode, int blackValue) {
        // 灰度
        int topRow = 0;
        int leftCol = 0;
        int rightCol = grayMat.width() - 1;
        int bottomRow = grayMat.height() - 1;

        // 上方黑边判断
        for (int row = 0; row < grayMat.height(); row++) {
            // 判断当前行是否基本“全黑”，阈值自定义；

            if (mode == 0 && sum(grayMat.row(row)) / grayMat.width() > blackValue) {
                //System.out.println(sum(grayMat.row(row))+" / "+grayMat.width()+" > " + blackValue);
                // 更新截取条件
                topRow = row;
            } else if (mode == 1 && sum(grayMat.row(row)) / grayMat.width() < blackValue) {
                //System.out.println(sum(grayMat.row(row))+" / "+grayMat.width()+" < " + blackValue);
                topRow = row;
            } else {
                break;
            }
        }
        // 左边黑边判断
        for (int col = 0; col < grayMat.width(); col++) {
            // 判断当前列是否基本“全黑”，阈值自定义；
            if (mode == 0 && sum(grayMat.col(col)) / grayMat.height() > blackValue) {
                // 更新截取条件
                leftCol = col;
            } else if (mode == 1 && sum(grayMat.col(col)) / grayMat.height() < blackValue) {
                leftCol = col;
            } else {
                break;
            }
        }
        // 右边黑边判断
        for (int col = grayMat.width() - 1; col > 0; col--) {
            // 判断当前列是否基本“全黑”，阈值自定义；
            if (mode == 0 && sum(grayMat.col(col)) / grayMat.height() > blackValue) {
                // 更新截取条件
                rightCol = col;
            } else if (mode == 1 && sum(grayMat.col(col)) / grayMat.height() < blackValue) {
                rightCol = col;
            } else {
                break;
            }
        }
        // 下方黑边判断
        for (int row = grayMat.height() - 1; row > 0; row--) {
            // 判断当前行是否基本“全黑”，阈值自定义；
            if (mode == 0 && sum(grayMat.row(row)) / grayMat.width() > blackValue) {
                // 更新截取条件
                bottomRow = row;
            } else if (mode == 1 && sum(grayMat.row(row)) / grayMat.width() < blackValue) {
                bottomRow = row;
            } else {
                break;
            }
        }

        int x = leftCol+1;
        int y = topRow+1;
        int width = rightCol - leftCol - 1;
        int height = bottomRow - topRow - 1;

        if (leftCol == 0 && rightCol == grayMat.width() - 1 && topRow == 0 && bottomRow == grayMat.height() - 1) {
            System.out.println("原图");
            return grayMat;
        }
        return cut(grayMat, x, y, width, height);
    }

    /**
     * 求和
     *
     * @param mat mat
     * @return sum
     */
    private static int sum(Mat mat) {
        int sum = 0;
        for (int row = 0; row < mat.height(); row++) {
            for (int col = 0; col < mat.width(); col++) {
                sum += mat.get(row, col)[0];
            }
        }
        return sum;
    }

    /**
     * 截取
     * @param mat 原始mat
     * @param x 截取左上角x坐标
     * @param y 截取左上角y坐标
     * @param width 截取宽度
     * @param height 截取高度
     * @return
     */
    private static Mat cut(Mat mat, int x, int y, int width, int height) {
        Rect rect = new Rect(x, y, width, height);
        Mat tmp = new Mat(mat,rect);
        Mat result = new Mat();
        tmp.copyTo(result);
        return result;
    }
}
