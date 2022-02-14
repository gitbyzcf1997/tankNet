package com.zcf.tank.net;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * @Auther:ZhenCF
 * @Date: 2022-02-14-20:26
 * @Description: com.zcf.tank.net
 * @version: 1.0
 */
public class Server {
    static ChannelGroup clients=new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    public void serverStart(){
        //线程池
        EventLoopGroup bootGroup=new NioEventLoopGroup(1);
        EventLoopGroup workGroup=new NioEventLoopGroup(2);
        //ServerNetty引导类
        try {

            ServerBootstrap bootstrap=new ServerBootstrap();
            ChannelFuture f = bootstrap.group(bootGroup, workGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ServerChildChannelInitializer())
                    .bind(8888)
                    .addListener(new ChannelFutureListener() {
                        @Override
                        public void operationComplete(ChannelFuture channelFuture) throws Exception {
                            if (!channelFuture.isSuccess()){
                                ServerFrame.INSTANCE.updateServerMsg("服务启动失败！");
                            }else{
                                ServerFrame.INSTANCE.updateServerMsg("服务启动成功！");
                            }
                        }
                    })
                    .sync();
            f.channel().closeFuture().sync();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            workGroup.shutdownGracefully();
            bootGroup.shutdownGracefully();
        }
    }
}
class ServerChildChannelInitializer extends ChannelInitializer<SocketChannel>{

    @Override
    protected void initChannel(SocketChannel channel) throws Exception {
        channel.pipeline().addLast(new ChildChannelHandler());
    }
}
class ChildChannelHandler extends ChannelInboundHandlerAdapter{
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf=null;
        try {
            buf = (ByteBuf) msg;
            byte[] bytes = new byte[buf.readableBytes()];
            buf.getBytes(buf.readerIndex(), bytes);
            String str = new String(bytes);
            ServerFrame.INSTANCE.updateClientrMsg(ctx.channel().remoteAddress()+str);
            if(str.equals("_bye_")){
                Server.clients.remove(ctx.channel());
                ctx.close();
                ServerFrame.INSTANCE.updateServerMsg(ctx.channel().remoteAddress()+"要求退出！");
            }else{
                Server.clients.writeAndFlush(msg);
            }
        }finally {
            //if(buf!=null)ReferenceCountUtil.release(msg);
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Server.clients.add(ctx.channel());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        Server.clients.remove(ctx.channel());
        ctx.close();
    }
}