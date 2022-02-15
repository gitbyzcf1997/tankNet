package com.zcf.tank;

import com.zcf.tank.net.MsgType;
import com.zcf.tank.net.MsgDecoder;
import com.zcf.tank.net.MsgEncoder;
import com.zcf.tank.net.TankJoinMsg;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.Assert.assertEquals;

/**
 * @Auther:ZhenCF
 * @Date: 2022-02-14-22:18
 * @Description: com.zcf.tank
 * @version: 1.0
 */
public class TankJoinMsgTest {
    @Test
    void testEncoder(){
        EmbeddedChannel ch=new EmbeddedChannel();
        UUID id = UUID.randomUUID();
        TankJoinMsg msg = new TankJoinMsg(5, 10, Dir.DOWN, true, Group.GOOD, id);
        ch.pipeline().addLast(new MsgEncoder()).addLast(new MsgDecoder());
        ch.writeOutbound(msg);
        ByteBuf buf = (ByteBuf) ch.readOutbound();
        MsgType msgType = MsgType.values()[buf.readInt()];
        assertEquals(MsgType.TankJoin,msgType);
        int length = buf.readInt();
        assertEquals(33,length);
        int x = buf.readInt();
        int y = buf.readInt();
        Dir dir=Dir.values()[buf.readInt()];
        boolean moving = buf.readBoolean();
        Group g=Group.values()[buf.readInt()];
        UUID uuid = new UUID(buf.readLong(), buf.readLong());
        assertEquals(5,x);
        assertEquals(10,y);
        assertEquals(Dir.DOWN,dir);
        assertEquals(true,moving);
        assertEquals(Group.GOOD,g);
        assertEquals(id,uuid);
    }
    @Test
    void testDecoder(){
        EmbeddedChannel ch=new EmbeddedChannel();
        UUID id = UUID.randomUUID();
        TankJoinMsg msg = new TankJoinMsg(5, 10, Dir.DOWN, true, Group.GOOD, id);
        ch.pipeline().addLast(new MsgDecoder());
        ByteBuf buf=Unpooled.buffer();
        buf.writeInt(MsgType.TankJoin.ordinal());
        byte[] bytes = msg.toBytes();
        buf.writeInt(bytes.length);
        buf.writeBytes(bytes);
        ch.writeInbound(buf.duplicate());
        TankJoinMsg msgR = (TankJoinMsg) ch.readInbound();
        assertEquals(5,msgR.x);
        assertEquals(10,msgR.y);
        assertEquals(Dir.DOWN,msgR.dir);
        assertEquals(true,msgR.moving);
        assertEquals(Group.GOOD,msgR.group);
        assertEquals(id,msgR.id);
    }
}
