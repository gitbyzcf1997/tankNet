package com.zcf.tank;

import com.zcf.tank.net.MsgDecoder;
import com.zcf.tank.net.MsgEncoder;
import com.zcf.tank.net.MsgType;
import com.zcf.tank.net.TankStartMovingMsg;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.assertEquals;

/**
 * @Auther:ZhenCF
 * @Date: 2022-02-15-16:31
 * @Description: com.zcf.tank
 * @version: 1.0
 */
public class TankStartMovingMsgTest {
    @Test
    public void testEncoder(){
        EmbeddedChannel ch=new EmbeddedChannel();
        ch.pipeline().addLast(new MsgEncoder());
        TankStartMovingMsg tsmm = new TankStartMovingMsg();
        tsmm.setId(UUID.randomUUID());
        tsmm.setX(10);
        tsmm.setY(10);
        tsmm.setDir(Dir.LEFT);
        ch.writeOutbound(tsmm);
        ByteBuf buf = (ByteBuf) ch.readOutbound();
        MsgType msgType = MsgType.values()[buf.readInt()];
        //assertEquals(MsgType.TankStartMoving,msgType);
        int length = buf.readInt();
        //assertEquals(28,length);
        UUID id=new UUID(buf.readLong(),buf.readLong());
        int x=buf.readInt();
        int y = buf.readInt();
        Dir dir=Dir.values()[buf.readInt()];
        assertEquals(id,tsmm.getId());
        assertEquals(x,tsmm.getX());
        assertEquals(y,tsmm.getY());
        assertEquals(dir,tsmm.getDir());
    }
    @Test
    public void testDecoder(){
        EmbeddedChannel ch=new EmbeddedChannel();
        ch.pipeline().addLast(new MsgDecoder());
        TankStartMovingMsg tsmm = new TankStartMovingMsg(UUID.randomUUID(), 5, 10, Dir.RIGHT);
        ByteBuf buf = Unpooled.buffer();
        buf.writeInt(MsgType.TankStartMoving.ordinal());
        byte[] bytes = tsmm.toBytes();
        buf.writeInt(bytes.length);
        buf.writeBytes(bytes);
        ch.writeInbound(buf.duplicate());
        TankStartMovingMsg msgR = (TankStartMovingMsg) ch.readInbound();
        assertEquals(5,msgR.getX());
        assertEquals(10,msgR.getY());
        assertEquals(Dir.RIGHT,msgR.getDir());
        assertEquals(tsmm.getId(),msgR.getId());
    }
}
