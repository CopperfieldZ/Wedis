package com.david.wedis.command.impl.zset;


import com.david.wedis.command.CommandType;
import com.david.wedis.command.WriteCommand;
import com.david.wedis.WedisCore;
import com.david.wedis.datatype.BytesWrapper;
import com.david.wedis.datatype.RedisData;
import com.david.wedis.datatype.RedisZset;
import com.david.wedis.resp.BulkString;
import com.david.wedis.resp.Resp;
import com.david.wedis.resp.RespInt;
import io.netty.channel.ChannelHandlerContext;

import java.util.ArrayList;
import java.util.List;

public class Zadd implements WriteCommand
{
    private BytesWrapper            key;
    private List<RedisZset.ZsetKey> keys;

    @Override
    public CommandType type()
    {
        return CommandType.zadd;
    }

    @Override
    public void setContent(Resp[] array)
    {
        key = ((BulkString) array[1]).getContent();
        keys = new ArrayList<>();
        for (int i = 2; i + 1 < array.length; i += 2)
        {
            long         score  = Long.parseLong(((BulkString) array[i]).getContent().toUtf8String());
            BytesWrapper member = ((BulkString) array[i + 1]).getContent();
            keys.add(new RedisZset.ZsetKey(member, score));
        }
    }

    @Override
    public void handle(ChannelHandlerContext ctx, WedisCore redisCore)
    {
        RedisData redisData = redisCore.get(key);
        if (redisData == null)
        {
            RedisZset redisZset = new RedisZset();
            int       add       = redisZset.add(keys);
            redisCore.put(key, redisZset);
            ctx.writeAndFlush(new RespInt(add));
        }
        else if (redisData instanceof RedisZset)
        {
            RedisZset redisZset = (RedisZset) redisData;
            int       add       = redisZset.add(keys);
            ctx.writeAndFlush(new RespInt(add));
        }
        else
        {
            throw new UnsupportedOperationException("类型不匹配");
        }
    }

    @Override
    public void handle(WedisCore redisCore) {
        RedisData redisData = redisCore.get(key);
        if (redisData == null)
        {
            RedisZset redisZset = new RedisZset();
            int       add       = redisZset.add(keys);
            redisCore.put(key, redisZset);
        }
        else if (redisData instanceof RedisZset)
        {
            RedisZset redisZset = (RedisZset) redisData;
            int       add       = redisZset.add(keys);
        }
        else
        {
            throw new UnsupportedOperationException("类型不匹配");
        }
    }
}
