package com.david.wedis.command.impl;


import com.david.wedis.command.Command;
import com.david.wedis.command.CommandType;
import com.david.wedis.WedisCore;
import com.david.wedis.resp.Resp;
import com.david.wedis.resp.SimpleString;
import io.netty.channel.ChannelHandlerContext;

public class Quit implements Command
{
    @Override
    public CommandType type()
    {
        return CommandType.quit;
    }

    @Override
    public void setContent(Resp[] array)
    {
    }

    @Override
    public void handle(ChannelHandlerContext ctx, WedisCore redisCore)
    {
        ctx.writeAndFlush(SimpleString.OK);
        ctx.close();
    }
}
