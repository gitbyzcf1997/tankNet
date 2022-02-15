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
public class TankJoinMsgEncoder extends MessageToByteEncoder<TankJoinMsg> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, TankJoinMsg tankJoinMsg, ByteBuf byteBuf) throws Exception {
     byteBuf.writeBytes(tankJoinMsg.toBytes());
    }
}
