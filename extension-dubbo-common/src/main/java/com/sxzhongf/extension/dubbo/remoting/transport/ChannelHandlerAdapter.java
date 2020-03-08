package com.sxzhongf.extension.dubbo.remoting.transport;

import com.sxzhongf.extension.dubbo.remoting.Channel;
import com.sxzhongf.extension.dubbo.remoting.ChannelHandler;
import com.sxzhongf.extension.dubbo.remoting.RemotingException;

/**
 * ChannelHandlerAdapter
 *
 * @author <a href="mailto:magicianisaac@gmail.com">Isaac.Zhang | 若初</a>
 * @since 2020/3/8
 **/
public class ChannelHandlerAdapter implements ChannelHandler {

    @Override
    public void connected(Channel channel) throws RemotingException {
    }

    @Override
    public void disconnected(Channel channel) throws RemotingException {
    }

    @Override
    public void sent(Channel channel, Object message) throws RemotingException {
    }

    @Override
    public void received(Channel channel, Object message) throws RemotingException {
    }

    @Override
    public void caught(Channel channel, Throwable exception) throws RemotingException {
    }

}
