package tank;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * @Auther:ZhenCF
 * @Date: 2022-01-24-21:56
 * @Description: tank
 * @version: 1.0
 */

/**
 * 窗口
 */
public class TankFrame extends Frame {
    //主坦克
    Tank myTank=new Tank(200,400,Dir.DOWN,this,Group.GOOD);
    //Explode e=new Explode(100,100,this);
    //爆炸合集
    List<Explode> explodes=new ArrayList<>();
    //子弹合集
    List<Bullet> bulletList=new ArrayList<>();
    //敌方坦克
    List<Tank> tanks=new ArrayList<>();
    //窗口宽高
    public static final int GAMEWIDTH=1500;
    public static final int GAMEHEIGHT=800;
    public TankFrame(){
        //窗口宽高
        setSize(GAMEWIDTH,GAMEHEIGHT);
        //窗口是否可改变大小
        setResizable(false);
        //窗口标题
        setTitle("坦克大战");
        //窗口可见
        setVisible(true);
        myTank.setMoving(false);
        //窗口X号事件监听
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                //程序退出
                System.exit(0);
            }
        });
        //添加键盘事件监听
        addKeyListener(new MyKeyListener());
    }
    //窗口 重新绘制的时候调用
    @Override
    public void paint(Graphics g) {
        Color c = g.getColor();
        g.setColor(Color.WHITE);
        g.drawString("子弹的数量:"+bulletList.size(),10,60);
        g.drawString("敌人的数量:"+tanks.size(),10,80);
        g.setColor(c);
        //Graphics 画笔类
        //填充一个矩形
        if(myTank!=null){
            myTank.paint(g);
        }
        for(int i=0;i<bulletList.size();i++){
            bulletList.get(i).paint(g);
        }
        for(int i=0;i<tanks.size();i++){
            tanks.get(i).paint(g);
        }
        for(int i=0;i<bulletList.size();i++){
            for(int j=0;j<tanks.size();j++) {
                bulletList.get(i).collideWith(tanks.get(j));
            }
        }
        for (int i=0;i<explodes.size();i++){
           explodes.get(i).paint(g);
        }

    }
    Image offScreenImage=null;

    @Override
    public void update(Graphics g) {
        if(offScreenImage==null){
            offScreenImage=this.createImage(GAMEWIDTH,GAMEHEIGHT);
        }
        Graphics gOffScreen = offScreenImage.getGraphics();
        Color c = gOffScreen.getColor();
        gOffScreen.setColor(Color.BLACK);
        gOffScreen.fillRect(0,0,GAMEWIDTH,GAMEHEIGHT);
        gOffScreen.setColor(c);
        paint(gOffScreen);
        g.drawImage(offScreenImage,0,0,null);
    }

    class MyKeyListener extends KeyAdapter{
        //添加是否按下
        boolean bL=false;
        boolean bU=false;
        boolean bR=false;
        boolean bD=false;
        @Override
        public void keyPressed(KeyEvent e) {
            //当按键按下时调用
            //获取所按下按键的值
            int key = e.getKeyCode();
            switch (key){
                case KeyEvent.VK_LEFT:
                    bL=true;
                    break;
                case KeyEvent.VK_UP:
                    bU=true;

                    break;
                case KeyEvent.VK_RIGHT:
                    bR=true;

                    break;
                case KeyEvent.VK_DOWN:
                    bD=true;
                    break;
                    default:break;
            }
            setMainTankDir();
            new Thread(()->new Audio("audio/tank_move.wav").play()).start();
        }

        private void setMainTankDir() {
            if(!bL&&!bU&&!bR&&!bD){
                myTank.setMoving(false);
            }else {
                myTank.setMoving(true);
                if (bL) myTank.setDir(Dir.LEFT);
                if (bU) myTank.setDir(Dir.UP);
                if (bR) myTank.setDir(Dir.RIGHT);
                if (bD) myTank.setDir(Dir.DOWN);
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            //当按键释放时调用
            int key = e.getKeyCode();
            switch (key){
                case KeyEvent.VK_LEFT:
                    bL=false;
                    break;
                case KeyEvent.VK_UP:
                    bU=false;
                    break;
                case KeyEvent.VK_RIGHT:
                    bR=false;
                    break;
                case KeyEvent.VK_DOWN:
                    bD=false;
                    break;
                case KeyEvent.VK_X:
                    myTank.fire();
                    break;
            }
            setMainTankDir();
        }
    }
}
