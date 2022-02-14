package com.zcf.tank.net;

import com.zcf.tank.Dir;
import com.zcf.tank.Group;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.Base64;
import java.util.List;
import java.util.UUID;

/**
 * @Auther:ZhenCF
 * @Date: 2022-02-14-22:03
 * @Description: com.zcf.tank.net
 * @version: 1.0
 */
public class TankJoinDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        if(byteBuf.readableBytes()<33)return;//解决 TCP拆包  粘包问题

        TankJoinMsg msg = new TankJoinMsg();

        msg.x=byteBuf.readInt();
        msg.y=byteBuf.readInt();
        msg.dir= Dir.values()[byteBuf.readInt()];
        msg.moving=byteBuf.readBoolean();
        msg.group= Group.values()[byteBuf.readInt()];
        msg.id=new UUID(byteBuf.readLong(), byteBuf.readLong());
        list.add(msg);
    }
}
