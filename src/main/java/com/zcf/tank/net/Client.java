package com.zcf.tank.net;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.ReferenceCountUtil;

/**
 * @Auther:ZhenCF
 * @Date: 2022-02-14-20:19
 * @Description: com.zcf.tank.net
 * @version: 1.0
 */
public class Client {
    private Channel channel=null;
    public void connect(){
        EventLoopGroup group=new NioEventLoopGroup(1);
        try {
            Bootstrap b = new Bootstrap();
            ChannelFuture f = b.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ClientChannelInitializer())
                    .connect("127.0.0.1", 8888)
                    .addListener(new ChannelFutureListener() {
                        @Override
                        public void operationComplete(ChannelFuture channelFuture) throws Exception {
                            if(!channelFuture.isSuccess()){
                                System.out.println("not connected!");
                            }else{
                                System.out.println("connect!");
                                channel=channelFuture.channel();
                            }
                        }
                    })
                    .sync();
            f.channel().closeFuture();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            group.shutdownGracefully();
        }
    }
}
class ClientChannelInitializer extends ChannelInitializer<SocketChannel>{

    @Override
    protected void initChannel(SocketChannel channel) throws Exception {
        channel.pipeline().addLast(new ClientChannelHandler());
    }
}
class ClientChannelHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf=null;
        try{
            buf=(ByteBuf)msg;
            byte[] bytes = new byte[buf.readableBytes()];
            buf.getBytes(buf.readerIndex(),bytes);
            String str = new String(bytes);
            System.out.println(str);
        }finally {
            if(buf!=null){
                ReferenceCountUtil.release(msg);
            }
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
    }
}
