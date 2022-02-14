package tank.test;
import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Auther:ZhenCF
 * @Date: 2022-01-26-0:23
 * @Description: tank.test
 * @version: 1.0
 */
public class ImageTest {
    @Test
    public void test(){
        try {
            //图片流：
            BufferedImage image=ImageIO.read(new File("F:/Java学习/tank/src/images/tankD.gif"));
            assertNotNull(image);
            //ClassLoader()方式 获取相对路径内的文件
            BufferedImage image2 = ImageIO.read(ImageTest.class.getClassLoader().getResourceAsStream("images/tankD.gif"));
            assertNotNull(image2);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
