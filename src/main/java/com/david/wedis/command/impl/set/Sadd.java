package com.david.wedis.command.impl.set;


import com.david.wedis.command.CommandType;
import com.david.wedis.command.WriteCommand;
import com.david.wedis.WedisCore;
import com.david.wedis.datatype.BytesWrapper;
import com.david.wedis.datatype.RedisData;
import com.david.wedis.datatype.RedisSet;
import com.david.wedis.resp.BulkString;
import com.david.wedis.resp.Resp;
import com.david.wedis.resp.RespInt;
import io.netty.channel.ChannelHandlerContext;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Sadd implements WriteCommand
{
    List<BytesWrapper> member;
    private BytesWrapper key;

    @Override
    public CommandType type()
    {
        return CommandType.sadd;
    }

    @Override
    public void setContent(Resp[] array)
    {
        key = ((BulkString) array[1]).getContent();
        member = Stream.of(array).skip(2).map(resp -> ((BulkString) resp).getContent()).collect(Collectors.toList());
    }

    @Override
    public void handle(ChannelHandlerContext ctx, WedisCore redisCore)
    {
        RedisData redisData = redisCore.get(key);
        if (redisData == null)
        {
            RedisSet redisSet = new RedisSet();
            int      sadd     = redisSet.sadd(member);
            redisCore.put(key, redisSet);
            ctx.writeAndFlush(new RespInt(sadd));
        }
        else if (redisData instanceof RedisSet)
        {
            RedisSet redisSet = (RedisSet) redisData;
            int      sadd     = redisSet.sadd(member);
            ctx.writeAndFlush(new RespInt(sadd));
        }
        else
        {
            throw new IllegalArgumentException("类型不匹配");
        }
    }

    @Override
    public void handle(WedisCore redisCore) {
        RedisData redisData = redisCore.get(key);
        if (redisData == null)
        {
            RedisSet redisSet = new RedisSet();
            redisSet.sadd(member);
            redisCore.put(key, redisSet);
        }
        else if (redisData instanceof RedisSet)
        {
            RedisSet redisSet = (RedisSet) redisData;
            redisSet.sadd(member);
        }
        else
        {
            throw new IllegalArgumentException("类型不匹配");
        }
    }
}
