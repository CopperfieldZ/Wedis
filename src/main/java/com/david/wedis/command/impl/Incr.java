package com.david.wedis.command.impl;

import com.david.wedis.command.CommandType;
import com.david.wedis.command.WriteCommand;
import com.david.wedis.WedisCore;
import com.david.wedis.datatype.BytesWrapper;
import com.david.wedis.datatype.RedisData;
import com.david.wedis.datatype.RedisString;
import com.david.wedis.resp.BulkString;
import com.david.wedis.resp.Resp;
import com.david.wedis.resp.SimpleString;
import com.david.wedis.util.Format;
import io.netty.channel.ChannelHandlerContext;

import static java.nio.charset.StandardCharsets.UTF_8;

public class Incr implements WriteCommand
{
    private BytesWrapper key;

    @Override
    public CommandType type()
    {
        return CommandType.incr;
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
            RedisString stringData = new RedisString();
            BytesWrapper bytesWrapper=new BytesWrapper("0".getBytes(UTF_8));
            stringData.setValue(bytesWrapper);
            redisCore.put(key, stringData);
            ctx.writeAndFlush(new BulkString(bytesWrapper));
        }
        else if (redisData instanceof RedisString)
        {
            try {
                BytesWrapper value = ((RedisString) redisData).getValue();
                long v= Format.parseLong(value.getByteArray(),10);
                ++v;
                BytesWrapper bytesWrapper= new BytesWrapper(Format.toByteArray(v));
                ((RedisString) redisData).setValue(bytesWrapper);
                ctx.writeAndFlush(new BulkString(bytesWrapper));
            }catch (NumberFormatException exception){
                ctx.writeAndFlush(new SimpleString("value is not an integer or out of range"));
            }

        }
        else
        {
            throw new UnsupportedOperationException();
        }
    }

    @Override
    public void handle(WedisCore redisCore) {
        RedisData redisData = redisCore.get(key);
        if (redisData == null)
        {
            RedisString stringData = new RedisString(new BytesWrapper("0".getBytes(UTF_8)));
            redisCore.put(key, stringData);
        }
        else if (redisData instanceof RedisString)
        {
            try {
                BytesWrapper value = ((RedisString) redisData).getValue();
                long v= Format.parseLong(value.getByteArray(),10);
                ++v;
                ((RedisString) redisData).setValue(new BytesWrapper(Format.toByteArray(v)));
            }catch (NumberFormatException exception){
            }
        }
        else
        {
            throw new UnsupportedOperationException();
        }
    }
}
