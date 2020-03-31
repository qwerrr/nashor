package com.zhangyue.test.nashor.tencent_login;

import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;

/**
 * @desc:
 * @author: YanMeng
 * @date: 2019-10-30
 */
@Slf4j
public class ImageDownloadUtils {

    public static void main(String[] args) {
        String path = "https://hy.captcha.qq.com/hycdn_1_1585801539876046080_0?aid=716027609&captype=&curenv=inner&protocol=https&clientype=2&disturblevel=&apptype=2&noheader=&color=&showtype=embed&fb=1&theme=&lang=2052&ua=TW96aWxsYS81LjAgKE1hY2ludG9zaDsgSW50ZWwgTWFjIE9TIFggMTBfMTRfNCkgQXBwbGVXZWJLaXQvNTM3LjM2IChLSFRNTCwgbGlrZSBHZWNrbykgQ2hyb21lLzc2LjAuMzgwOS4xMDAgU2FmYXJpLzUzNy4zNg==&grayscale=1&subsid=6&sess=CTbtwJLQtH-NrJOA1nDDKkYoQR-aE87EUNxJ6iTEjQZ61ZYWwHNa-Nqvp2utvf7mPoQ8TKxhpNcfITCitMHcPqNF4hpiZtmt9zyS0W23K7hyjtLJ4e-l2Z-0oFWMqhvArDiPpdG3tkvXoHMxiUqisRjL_ycrSaNcX3cNqd7jEDAtVfTpNPeFpjYm-xtzTkBPzAI_Oxy-CCs*&fwidth=0&sid=6753510377175455215&forcestyle=undefined&wxLang=&tcScale=1&noBorder=noborder&uid=98765431&cap_cd=eMFGZGrJeqWmNotapb537QXa2_9lbn0NznWAo5oyf3YOwD1MXI74mw**&rnd=319844&TCapIframeLoadTime=34&prehandleLoadTime=14&createIframeStart=1572424168287&rand=87200762&websig=76162b8a1597b24130927868ed7f6799bedd3e18f9135ae90967553520f92a57ac37a0c3ddeca6aa9bdc153085bc4220e2cd6c2745addbc03fba7a1568372231&vsig=c01wYipi2I10okHwECwhLdiAkzctEK2-jSciXObo3bWp_ax8m6PWzZfdhjSUFNAG8ffGbSUcQybhmnwxfer0_ai-uz2HmyVsgg9so_qiHzrWzwGaBapDxqgRCsQ6sydnfHtC7bv4r7nXIyhnpWikCH-Iqg-8b9oUpPNBSLE2qXbh41Z20Ne47yJbA**&img_index=1";
        download(path, "/Users/coderam/soft/slideBg_" + System.currentTimeMillis() + ".png");
    }

    public static void download(String imgUrl, String localPath) {

        Preconditions.checkArgument(!StringUtils.isAnyEmpty(imgUrl, localPath), "参数不能为空");
        Preconditions.checkArgument(localPath.lastIndexOf(".") > 0, "参数格式错误, 无格式后缀");

        try {
            String format = localPath.substring(localPath.lastIndexOf(".") + 1);
            BufferedImage img = ImageIO.read(new URL(imgUrl));
            ImageIO.write(img, format, new File(localPath));
        } catch (Exception e) {
            log.error("抓取图片异常: ", e);
            throw new RuntimeException("抓取图片异常: " + imgUrl);
        }
    }
}
