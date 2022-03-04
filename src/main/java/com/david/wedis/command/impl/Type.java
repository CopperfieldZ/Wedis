package com.david.wedis.command.impl;


import com.david.wedis.command.Command;
import com.david.wedis.command.CommandType;
import com.david.wedis.WedisCore;
import com.david.wedis.datatype.*;
import com.david.wedis.resp.BulkString;
import com.david.wedis.resp.Resp;
import com.david.wedis.resp.SimpleString;
import io.netty.channel.ChannelHandlerContext;

public class Type implements Command
{
    private BytesWrapper key;

    @Override
    public CommandType type()
    {
        return CommandType.type;
    }

    @Override
    public void setContent(Resp[] array)
    {
        key = ((BulkString) array[1]).getContent();
    }

    @Override
    public void handle(ChannelHandlerContext ctx, WedisCore redisCore)
    {
        RedisData redisData = redisCore.get(key);
        if (redisData == null)
        {
            ctx.writeAndFlush(new SimpleString("none"));
        }
        else if (redisData instanceof RedisString)
        {
            ctx.writeAndFlush(new SimpleString("string"));
        }
        else if (redisData instanceof RedisList)
        {
            ctx.writeAndFlush(new SimpleString("list"));
        }
        else if (redisData instanceof RedisSet)
        {
            ctx.writeAndFlush(new SimpleString("set"));
        }
        else if (redisData instanceof RedisHash)
        {
            ctx.writeAndFlush(new SimpleString("hash"));
        }
        else if (redisData instanceof RedisZset)
        {
            ctx.writeAndFlush(new SimpleString("zset"));
        }
        else
        {
            throw new UnsupportedOperationException();
        }
    }
}
