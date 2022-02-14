package tank;

/**
 * @Auther:ZhenCF
 * @Date: 2022-01-26-1:43
 * @Description: tank
 * @version: 1.0
 */



import util.ImageUtil;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * 资源管理类
 * 在程序加载时把图片资源初始化
 */
public class ResourceMgr {
    //初始化四张图片
    public  static BufferedImage goodtankL,goodtankR,goodtankU,goodtankD=null;
    public  static BufferedImage badtankL,badtankR,badtankU,badtankD=null;
    public  static BufferedImage bulletL,bulletR,bulletU,bulletD=null;
    public  static BufferedImage[] explodes=new BufferedImage[16];
    static {
        try {
            //将坦克的图片加载到内存
            goodtankU=ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("images/GoodTank1.png"));
            goodtankL= ImageUtil.rotateImage(goodtankU,-90);
            goodtankR=ImageUtil.rotateImage(goodtankU,90);
            goodtankD=ImageUtil.rotateImage(goodtankU,180);
            //将坦克的图片加载到内存
            badtankU=ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("images/BadTank1.png"));
            badtankL= ImageUtil.rotateImage(badtankU,-90);
            badtankR=ImageUtil.rotateImage(badtankU,90);
            badtankD=ImageUtil.rotateImage(badtankU,180);
            //将子弹的图片加载到内存
            bulletU=ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("images/bulletU.png"));
            bulletL=ImageUtil.rotateImage(bulletU,-90);
            bulletR=ImageUtil.rotateImage(bulletU,90);
            bulletD=ImageUtil.rotateImage(bulletU,180);
            //将爆炸的图片加载到内存
            for(int i=0;i<16;i++){
                explodes[i]=ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("images/e"+(i+1)+".gif"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
