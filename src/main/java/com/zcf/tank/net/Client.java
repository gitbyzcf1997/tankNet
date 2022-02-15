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
            f.channel().closeFuture().sync();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            group.shutdownGracefully();
        }
    }

    /**
     * 发送数据的方法
     * @param msg TankJoinMsg.class
     */
    public void send(Msg msg){
        channel.writeAndFlush(msg);
    }
    public void closeConnect(){
//        this.send("_bye_");
//        channel.close();
    }
}
class ClientChannelInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel channel) throws Exception {
        channel.pipeline()
                .addLast(new TankJoinMsgDecoder())
                .addLast(new TankJoinMsgEncoder())
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
        System.out.println("已发送");
    }
}
