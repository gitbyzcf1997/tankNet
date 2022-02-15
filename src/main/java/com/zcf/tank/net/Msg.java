package com.zcf.tank.net;

/**
 * @Auther:ZhenCF
 * @Date: 2022-02-15-15:10
 * @Description: com.zcf.tank.net
 * @version: 1.0
 */
public abstract class Msg {
    public abstract void handle();
    public abstract byte[] toBytes();
    public abstract void parse(byte[] bytes);
    public abstract MsgType getMsgType();
}
