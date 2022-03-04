package com.david.wedis.command.impl.string;


import com.david.wedis.command.Command;
import com.david.wedis.command.CommandType;
import com.david.wedis.WedisCore;
import com.david.wedis.datatype.BytesWrapper;
import com.david.wedis.datatype.RedisData;
import com.david.wedis.datatype.RedisString;
import com.david.wedis.resp.BulkString;
import com.david.wedis.resp.Resp;
import io.netty.channel.ChannelHandlerContext;

public class Get implements Command
{
    private BytesWrapper key;

    @Override
    public CommandType type()
    {
        return CommandType.get;
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
            ctx.writeAndFlush(BulkString.NullBulkString);
        }
        else if (redisData instanceof RedisString)
        {
            BytesWrapper value = ((RedisString) redisData).getValue();
            ctx.writeAndFlush(new BulkString(value));
        }
        else
        {
            throw new UnsupportedOperationException();
        }
    }
}
