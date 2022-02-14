package util;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @Auther:ZhenCF
 * @Date: 2022-01-26-6:52
 * @Description: util
 * @version: 1.0
 */
public class ImageUtil {
    public static BufferedImage rotateImage(final BufferedImage bufferedImage,final int degree){
        int w=bufferedImage.getWidth();
        int h=bufferedImage.getHeight();
        int type=bufferedImage.getColorModel().getTransparency();
        BufferedImage image;
        Graphics2D graphics2d;
        (graphics2d=(image=new BufferedImage(w,h,type))
                .createGraphics()).setRenderingHint(
                        RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BILINEAR
                );
        graphics2d.rotate(Math.toRadians(degree),w/2,h/2);
        graphics2d.drawImage(bufferedImage,0,0,null);
        graphics2d.dispose();
        return image;
    }
}
