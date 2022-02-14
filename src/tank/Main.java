package tank;

import util.PropertMgr;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * @Auther:ZhenCF
 * @Date: 2022-01-24-21:40
 * @Description: tank
 * @version: 1.0
 */
public class Main {
    public static void main(String[] args) throws InterruptedException {
        TankFrame tankFrame = new TankFrame();
        PropertMgr pg = PropertMgr.getPropertMgr();
        int initTankCount= pg.getInt("initTankCount");
//        //初始化敌方坦克
//        for(int i=0;i<initTankCount;i++){
//            tankFrame.tanks.add(new Tank((i*ResourceMgr.badtankU.getWidth())*2,150,Dir.DOWN,tankFrame,Group.BAD));
//        }
        new Thread(()->{new Audio("audio/war1.wav").loop();}).start();
        while (true){
            Thread.sleep(50);
            tankFrame.repaint();
        }
    }
}
