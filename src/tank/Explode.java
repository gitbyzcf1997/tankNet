package tank;

import java.awt.*;

/**
 * @Auther:ZhenCF
 * @Date: 2022-01-26-3:45
 * @Description: tank
 * @version: 1.0
 */

/**
 * 爆炸效果类
 */
public class Explode {
    //图片宽高
    public static int WIDTH=ResourceMgr.explodes[0].getWidth();
    public static int HEIGHT=ResourceMgr.explodes[0].getHeight();
    //坐标
    private int x,y;
    private boolean living=true;
    TankFrame tf=null;
    private int step=0;
    public Thread audio=null;
    public Explode(int x, int y, TankFrame tf) {
        this.x = x;
        this.y = y;
        this.tf = tf;
       if(audio==null) {
            new Thread(() -> {
                new Audio("audio/explode.wav").play();
            }).start();
        }
    }
    public void paint(Graphics g){
        g.drawImage(ResourceMgr.explodes[step++],x,y,null);
        if(step>=ResourceMgr.explodes.length)tf.explodes.remove(this);
    }
}
