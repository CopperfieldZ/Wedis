package com.david.wedis.command.impl;


import com.david.wedis.command.CommandType;
import com.david.wedis.command.WriteCommand;
import com.david.wedis.WedisCore;
import com.david.wedis.datatype.BytesWrapper;
import com.david.wedis.datatype.RedisData;
import com.david.wedis.resp.BulkString;
import com.david.wedis.resp.Resp;
import com.david.wedis.resp.RespInt;
import io.netty.channel.ChannelHandlerContext;

public class Ttl implements WriteCommand
{
    private BytesWrapper key;

    @Override
    public CommandType type()
    {
        return CommandType.ttl;
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
            ctx.writeAndFlush(new RespInt(-2));
        }
        else if (redisData.timeout() == -1)
        {
            ctx.writeAndFlush(new RespInt(-1));
        }
        else
        {
            long second = (redisData.timeout() - System.currentTimeMillis()) / 1000;
            ctx.writeAndFlush(new RespInt((int) second));
        }
    }

    @Override
    public void handle(WedisCore redisCore) {
        RedisData redisData = redisCore.get(key);
        if (redisData == null)
        {
        }
        else if (redisData.timeout() == -1)
        {
        }
        else
        {
            long second = (redisData.timeout() - System.currentTimeMillis()) / 1000;
        }
    }
}
