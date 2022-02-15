package com.zcf.tank;

import com.zcf.tank.net.Client;

import com.zcf.util.PropertMgr;

/**
 * @Auther:ZhenCF
 * @Date: 2022-01-24-21:40
 * @Description: com.zcf.tank
 * @version: 1.0
 */
public class Main {
    public static void main(String[] args) throws InterruptedException {
        TankFrame tankFrame = TankFrame.INSTANCE;
        PropertMgr pg = PropertMgr.getPropertMgr();
        int initTankCount= pg.getInt("initTankCount");
//        //初始化敌方坦克
//        for(int i=0;i<initTankCount;i++){
//            tankFrame.tanks.add(new Tank((i*ResourceMgr.badtankU.getWidth())*2,150,Dir.DOWN,tankFrame,Group.BAD));
//        }
       // new Thread(()->{new Audio("audio/war1.wav").loop();}).start();
        new Thread(()-> {
            while (true) {
                try {
                    Thread.sleep(25);
                    tankFrame.repaint();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

        Client.INSTANCE.connect();
    }
}
