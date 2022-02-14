package com.zcf.tank.test;
import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Auther:ZhenCF
 * @Date: 2022-01-26-0:23
 * @Description: com.zcf.tank.test
 * @version: 1.0
 */
public class ImageTest {
    @Test
    public void test(){
        try {
            //图片流：
            BufferedImage image=ImageIO.read(new File("F:/Java学习/com.zcf.tank/src/com.zcf.tank.images/tankD.gif"));
            assertNotNull(image);
            //ClassLoader()方式 获取相对路径内的文件
            BufferedImage image2 = ImageIO.read(ImageTest.class.getClassLoader().getResourceAsStream("com/zcf/tank/images/tankD.gif"));
            assertNotNull(image2);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
