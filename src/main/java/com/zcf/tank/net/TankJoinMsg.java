package com.zcf.tank.net;

import com.zcf.tank.Dir;
import com.zcf.tank.Group;
import com.zcf.tank.Tank;
import com.zcf.tank.TankFrame;

import java.io.*;
import java.util.UUID;

/**
 * @Auther:ZhenCF
 * @Date: 2022-02-14-21:35
 * @Description: com.zcf.tank.net
 * @version: 1.0
 */
public class TankJoinMsg extends Msg{
    public int x,y;
    public Dir dir;
    public boolean moving;
    public Group group;
    public UUID id;

    public TankJoinMsg(Tank t){
        this.x=t.getX();
        this.y=t.getY();
        this.dir=t.getDir();
        this.group=t.getGroup();
        this.id=t.getId();
        this.moving=t.isMoving();
    }

    public TankJoinMsg(int x, int y, Dir dir, boolean moving, Group group, UUID id) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.moving = moving;
        this.group = group;
        this.id = id;
    }

    public TankJoinMsg() {
    }

    /**
     * 将类的属性转为二进制数据
     * @return
     */
    @Override
    public byte[] toBytes(){
        ByteArrayOutputStream baos=null;
        DataOutputStream dos=null;
        byte[] bytes=null;
        try{
            baos=new ByteArrayOutputStream();
            dos=new DataOutputStream(baos);
            dos.writeInt(x);
            dos.writeInt(y);
            dos.writeInt(dir.ordinal());
            dos.writeBoolean(moving);
            dos.writeInt(group.ordinal());
            //因为DataOutputStream只能写基础数据类型 128为的UUID没办法写出 所以分高位和低位两次写出
            dos.writeLong(id.getMostSignificantBits());
            dos.writeLong(id.getLeastSignificantBits());
            dos.flush();
            bytes=baos.toByteArray();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try{
                if(baos!=null){
                    baos.close();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            try{
                if(dos!=null){
                    dos.close();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return bytes;
    }

    @Override
    public void parse(byte[] bytes) {
        DataInputStream dis = new DataInputStream(new ByteArrayInputStream(bytes));
        try{
            this.x=dis.readInt();
            this.y=dis.readInt();
            this.dir=Dir.values()[dis.readInt()];
            this.moving=dis.readBoolean();
            this.group=Group.values()[dis.readInt()];
            this.id=new UUID(dis.readLong(),dis.readLong());
            //this.name=dis.readUTF();
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            try {
                dis.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public MsgType getMsgType() {
        return MsgType.TankJoin;
    }

    @Override
    public String toString() {
        StringBuilder builder=new StringBuilder();
        builder.append(this.getClass().getName())
                .append("[")
                .append("uuid="+id+"|")
                //.append("name="+name+"|")
                .append("x="+x+"|")
                .append("y="+y+"|")
                .append("moving="+moving+"|")
                .append("dir="+dir+"|")
                .append("group="+group+"|")
                .append("]");
        return builder.toString();
    }
    @Override
    public void handle() {
        if(this.id.equals(TankFrame.INSTANCE.getMyTank().getId())||TankFrame.INSTANCE.findTankByUUID(this.id)!=null)return;
        Tank t=new Tank(this);
        TankFrame.INSTANCE.addTank(t);
        Client.INSTANCE.send(new TankJoinMsg(TankFrame.INSTANCE.getMyTank()));
    }

}
