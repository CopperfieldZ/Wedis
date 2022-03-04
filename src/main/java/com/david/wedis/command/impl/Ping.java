package com.david.wedis.command.impl;

import com.david.wedis.command.Command;
import com.david.wedis.command.CommandType;
import com.david.wedis.WedisCore;
import com.david.wedis.resp.Resp;
import com.david.wedis.resp.SimpleString;
import io.netty.channel.ChannelHandlerContext;

public class Ping implements Command
{

    @Override
    public CommandType type()
    {
        return CommandType.lrem;
    }

    @Override
    public void setContent(Resp[] array)
    {
    }

    @Override
    public void handle(ChannelHandlerContext ctx, WedisCore redisCore)
    {
        ctx.write(new SimpleString("PONG"));
        ctx.flush();
    }
}
