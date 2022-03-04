package com.david.wedis.channel;

import com.david.wedis.channel.epoll.EpollChannelOption;
import com.david.wedis.channel.kqueue.KqueueChannelOption;
import com.david.wedis.channel.select.NioSelectChannelOption;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.kqueue.KQueue;

public class DefaultChannelSelectStrategy implements ChannelSelectStrategy {
    @Override
    public LocalChannelOption select(){

        if(KQueue.isAvailable()){
            return new KqueueChannelOption();
        }
        if(Epoll.isAvailable()){
            return new EpollChannelOption();
        }
        return new NioSelectChannelOption();
    }
}
