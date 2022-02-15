package com.zcf.tank.net;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @Auther:ZhenCF
 * @Date: 2022-02-14-22:01
 * @Description: com.zcf.tank.net
 * @version: 1.0
 */
public class MsgEncoder extends MessageToByteEncoder<Msg> {


    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Msg msg, ByteBuf buf) throws Exception {
        buf.writeInt(msg.getMsgType().ordinal());
        byte[] bytes = msg.toBytes();
        buf.writeInt(bytes.length);
        buf.writeBytes(bytes);
    }
}
