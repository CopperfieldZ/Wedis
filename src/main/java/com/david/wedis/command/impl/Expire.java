package com.david.wedis.command.impl;


import com.david.wedis.WedisCore;
import com.david.wedis.command.CommandType;
import com.david.wedis.command.WriteCommand;
import com.david.wedis.datatype.BytesWrapper;
import com.david.wedis.datatype.RedisData;
import com.david.wedis.resp.BulkString;
import com.david.wedis.resp.Resp;
import com.david.wedis.resp.RespInt;
import io.netty.channel.ChannelHandlerContext;

public class Expire implements WriteCommand
{
    private BytesWrapper key;
    private int          second;

    @Override
    public CommandType type()
    {
        return CommandType.expire;
    }


    @Override
    public void setContent(Resp[] array)
    {
        key = ((BulkString) array[1]).getContent();
        second = Integer.parseInt(((BulkString) array[2]).getContent().toUtf8String());
    }

    @Override
    public void handle(ChannelHandlerContext ctx, WedisCore redisCore)
    {
        RedisData redisData = redisCore.get(key);
        if (redisData == null)
        {
            ctx.writeAndFlush(new RespInt(0));
        }
        else
        {
            redisData.setTimeout(System.currentTimeMillis() + (second * 1000));
            ctx.writeAndFlush(new RespInt(1));
        }
    }

    @Override
    public void handle(WedisCore redisCore) {
        RedisData redisData = redisCore.get(key);
        if (redisData == null)
        {
        }
        else
        {
            redisData.setTimeout(System.currentTimeMillis() + (second * 1000));
        }
    }
}
