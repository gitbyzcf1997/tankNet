package com.zcf.tank.net;

import com.zcf.tank.Dir;
import com.zcf.tank.Tank;
import com.zcf.tank.TankFrame;

import java.io.*;
import java.util.UUID;

/**
 * @Auther:ZhenCF
 * @Date: 2022-02-15-19:21
 * @Description: com.zcf.tank.net
 * @version: 1.0
 */
public class TankStopMsg extends Msg {
    UUID id;
    int x,y;
    boolean moving;
    Dir dir;

    public TankStopMsg() {
    }
    public TankStopMsg(Tank tank){
        this.x=tank.getX();
        this.y=tank.getY();
        this.moving=tank.isMoving();
        this.dir=tank.getDir();
        this.id=tank.getId();
    }
    public TankStopMsg(UUID id, int x, int y, boolean moving, Dir dir) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.moving = moving;
        this.dir = dir;
    }

    @Override
    public void handle() {
        if(this.id.equals(TankFrame.INSTANCE.getMyTank().getId()))return;
        Tank t = TankFrame.INSTANCE.findTankByUUID(this.id);
        t.setDir(this.dir);
        t.setX(this.x);
        t.setY(this.y);
        t.setMoving(this.moving);
    }

    @Override
    public byte[] toBytes() {
        ByteArrayOutputStream baos=null;
        DataOutputStream dos=null;
        byte[] bytes=null;
        try{
         baos = new ByteArrayOutputStream();
         dos=new DataOutputStream(baos);
         dos.writeLong(id.getMostSignificantBits());
         dos.writeLong(id.getLeastSignificantBits());
         dos.writeInt(x);
         dos.writeInt(y);
         dos.writeBoolean(moving);
         dos.writeInt(dir.ordinal());
         dos.flush();
         bytes=baos.toByteArray();
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            if(baos!=null){
                try {
                    baos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if(dos!=null){
                    try {
                        dos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return bytes;
    }

    @Override
    public void parse(byte[] bytes) {
        ByteArrayInputStream bis=null;
        DataInputStream dis=null;
        try{
            bis=new ByteArrayInputStream(bytes);
            dis=new DataInputStream(bis);
            this.id=new UUID(dis.readLong(),dis.readLong());
            this.x=dis.readInt();
            this.y=dis.readInt();
            this.moving=dis.readBoolean();
            this.dir=Dir.values()[dis.readInt()];
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            if(bis!=null){
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if(dis!=null){
                    try {
                        dis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        }
    }

    @Override
    public MsgType getMsgType() {
        return MsgType.TankStop;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Dir getDir() {
        return dir;
    }

    public void setDir(Dir dir) {
        this.dir = dir;
    }
}
