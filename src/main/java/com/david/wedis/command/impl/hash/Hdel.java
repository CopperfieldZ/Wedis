package com.david.wedis.command.impl.hash;


import com.david.wedis.command.CommandType;
import com.david.wedis.command.WriteCommand;
import com.david.wedis.WedisCore;
import com.david.wedis.datatype.BytesWrapper;
import com.david.wedis.datatype.RedisHash;
import com.david.wedis.resp.BulkString;
import com.david.wedis.resp.Resp;
import com.david.wedis.resp.RespInt;
import io.netty.channel.ChannelHandlerContext;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Hdel implements WriteCommand
{
    private BytesWrapper       key;
    private List<BytesWrapper> fields;

    @Override
    public CommandType type()
    {
        return CommandType.hdel;
    }

    @Override
    public void setContent(Resp[] array)
    {
        key = ((BulkString) array[1]).getContent();
        fields = Stream.of(array).skip(2).map(resp -> ((BulkString) resp).getContent()).collect(Collectors.toList());
    }

    @Override
    public void handle(ChannelHandlerContext ctx, WedisCore redisCore)
    {
        RedisHash redisHash = (RedisHash) redisCore.get(key);
        int       del       = redisHash.del(fields);
        ctx.writeAndFlush(new RespInt(del));
    }

    @Override
    public void handle(WedisCore redisCore) {
        RedisHash redisHash = (RedisHash) redisCore.get(key);
        redisHash.del(fields);
    }
}
