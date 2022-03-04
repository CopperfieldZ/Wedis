package com.david.wedis.command.impl.string;


import com.david.wedis.command.CommandType;
import com.david.wedis.command.WriteCommand;
import com.david.wedis.WedisCore;
import com.david.wedis.datatype.BytesWrapper;
import com.david.wedis.datatype.RedisString;
import com.david.wedis.resp.BulkString;
import com.david.wedis.resp.Resp;
import com.david.wedis.resp.RespInt;
import io.netty.channel.ChannelHandlerContext;

public class SetNx implements WriteCommand
{
    private BytesWrapper key;
    private BytesWrapper value;

    @Override
    public CommandType type()
    {
        return CommandType.setnx;
    }

    @Override
    public void setContent(Resp[] array)
    {
        key = ((BulkString) array[1]).getContent();
        value = ((BulkString) array[2]).getContent();
    }

    @Override
    public void handle(ChannelHandlerContext ctx, WedisCore redisCore)
    {
        boolean exist = redisCore.exist(key);
        if (exist)
        {
            ctx.writeAndFlush(new RespInt(0));
        }
        else
        {
            RedisString redisString = new RedisString();
            redisString.setValue(value);
            redisCore.put(key, redisString);
            ctx.writeAndFlush(new RespInt(1));
        }
    }

    @Override
    public void handle(WedisCore redisCore) {
        boolean exist = redisCore.exist(key);
        if (exist)
        {
        }
        else
        {
            RedisString redisString = new RedisString();
            redisString.setValue(value);
            redisCore.put(key, redisString);

        }
    }
}
