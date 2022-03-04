package com.david.wedis.command.impl;

import com.david.wedis.command.Command;
import com.david.wedis.command.CommandType;
import com.david.wedis.WedisCore;
import com.david.wedis.resp.BulkString;
import com.david.wedis.resp.Resp;
import com.david.wedis.resp.SimpleString;
import io.netty.channel.ChannelHandlerContext;

public class Select implements Command
{
    private Integer index;
    @Override
    public CommandType type()
    {
        return CommandType.select;
    }

    @Override
    public void setContent(Resp[] array)
    {
         index = Integer.parseInt(((BulkString) array[1]).getContent().toUtf8String());
    }

    @Override
    public void handle(ChannelHandlerContext ctx, WedisCore redisCore)
    {
        if(index>0){
            SimpleString ok = new SimpleString("-ERR invalid DB index");
            ctx.writeAndFlush(ok);
        }else {
            SimpleString ok = new SimpleString("OK");
            ctx.writeAndFlush(ok);
        }

    }
}