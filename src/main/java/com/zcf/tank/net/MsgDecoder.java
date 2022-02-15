package com.zcf.tank.net;

import com.zcf.tank.Dir;
import com.zcf.tank.Group;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;
import java.util.UUID;

/**
 * @Auther:ZhenCF
 * @Date: 2022-02-15-14:03
 * @Description: com.zcf.tank.net
 * @version: 1.0
 */
public class MsgDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf buf, List<Object> out) throws Exception {
        if(buf.readableBytes()<8)return;//解决 TCP拆包  粘包问题
        //标记从哪个位置开始读的
        buf.markReaderIndex();
        MsgType msgType = MsgType.values()[buf.readInt()];
        int length=buf.readInt();
        if(buf.readableBytes()<length){
            //如果buf的数据小于length说明还是发包
            //回到标记的位置  重新读
            buf.resetReaderIndex();
            return;
        }
        byte[] bytes = new byte[length];
        buf.readBytes(bytes);
        Msg msg=null;
        //使用反射
        Class.forName("com.zcf.tank.net."+msgType.toString()+"Msg").getDeclaredConstructor().newInstance();
        switch (msgType){
            case TankJoin:
                 msg = new TankJoinMsg();
                msg.parse(bytes);
                out.add(msg);
                break;
            case TankStartMoving:
                 msg = new TankStartMovingMsg();
                break;
            case TankStop:
                msg=new TankStopMsg();
                break;
            default:break;
        }
        msg.parse(bytes);
        out.add(msg);
    }
}
