package com.david.wedis.command.impl.string;


import com.david.wedis.command.CommandType;
import com.david.wedis.command.WriteCommand;
import com.david.wedis.WedisCore;
import com.david.wedis.datatype.BytesWrapper;
import com.david.wedis.datatype.RedisString;
import com.david.wedis.resp.BulkString;
import com.david.wedis.resp.Resp;
import com.david.wedis.resp.SimpleString;
import io.netty.channel.ChannelHandlerContext;

public class SetEx implements WriteCommand
{
    private BytesWrapper key;
    private int          seconds;
    private BytesWrapper value;

    @Override
    public CommandType type()
    {
        return CommandType.setex;
    }

    @Override
    public void setContent(Resp[] array)
    {
        key = ((BulkString) array[1]).getContent();
        seconds = Integer.parseInt(((BulkString) array[2]).getContent().toUtf8String());
        value = ((BulkString) array[3]).getContent();
    }

    @Override
    public void handle(ChannelHandlerContext ctx, WedisCore redisCore)
    {
        RedisString redisString = new RedisString();
        redisString.setValue(value);
        redisString.setTimeout(System.currentTimeMillis() + (seconds * 1000L));
        redisCore.put(key, redisString);
        ctx.writeAndFlush(SimpleString.OK);
    }

    @Override
    public void handle(WedisCore redisCore) {
        RedisString redisString = new RedisString();
        redisString.setValue(value);
        redisString.setTimeout(System.currentTimeMillis() + (seconds * 1000L));
        redisCore.put(key, redisString);
    }
}
