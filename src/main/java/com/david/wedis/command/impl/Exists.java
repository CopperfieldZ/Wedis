package com.david.wedis.command.impl;


import com.david.wedis.command.Command;
import com.david.wedis.command.CommandType;
import com.david.wedis.WedisCore;
import com.david.wedis.datatype.BytesWrapper;
import com.david.wedis.resp.BulkString;
import com.david.wedis.resp.Resp;
import com.david.wedis.resp.RespInt;
import io.netty.channel.ChannelHandlerContext;

public class Exists implements Command
{
    private BytesWrapper key;

    @Override
    public CommandType type()
    {
        return CommandType.exists;
    }

    @Override
    public void setContent(Resp[] array)
    {
        key = ((BulkString) array[1]).getContent();
    }

    @Override
    public void handle(ChannelHandlerContext ctx, WedisCore redisCore)
    {
        boolean exist = redisCore.exist(key);
        if (exist)
        {
            ctx.writeAndFlush(new RespInt(1));
        }
        else
        {
            ctx.writeAndFlush(new RespInt(0));
        }
    }
}
