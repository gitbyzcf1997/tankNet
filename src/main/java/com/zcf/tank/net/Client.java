package com.zcf.tank.net;


import com.zcf.tank.TankFrame;
import io.netty.bootstrap.Bootstrap;


import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @Auther:ZhenCF
 * @Date: 2022-02-14-20:19
 * @Description: com.zcf.tank.net
 * @version: 1.0
 */
public class Client {
    public static final Client INSTANCE = new Client();
    private Channel channel=null;
    private Client(){}

    /**
     * Client端的连接方法 初始化Netty并连接
     */
    public void connect(){
        EventLoopGroup group=new NioEventLoopGroup(1);
        Bootstrap b = new Bootstrap();
        try {
            ChannelFuture f = b.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ClientChannelInitializer())
                    .connect("127.0.0.1", 8888);
            f.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture channelFuture) throws Exception {
                    if(!channelFuture.isSuccess()){
                        System.out.println("not connected!");
                    }else{
                        System.out.println("connect!");
                        channel=channelFuture.channel();
                    }
                }
            });
            f.sync();
            //wait until close
            f.channel().closeFuture().sync();
            System.out.println("client断开");
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("出错");
        }finally {
            group.shutdownGracefully();
            System.out.println("关闭group");
        }
    }

    /**
     * 发送数据的方法
     * @param msg TankJoinMsg.class
     */
    public void send(Msg msg){
        channel.writeAndFlush(msg);
    }
}
class ClientChannelInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel channel) throws Exception {
        channel.pipeline()
                .addLast(new MsgEncoder())
                .addLast(new MsgDecoder())
                .addLast(new ClientChannelHandler());
    }
}
class ClientChannelHandler extends SimpleChannelInboundHandler<Msg> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Msg msg) throws Exception {
        msg.handle();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(new TankJoinMsg(TankFrame.INSTANCE.getMyTank()));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
    }
}
