package com.david.wedis.command.impl.set;


import com.david.wedis.command.CommandType;
import com.david.wedis.command.WriteCommand;
import com.david.wedis.WedisCore;
import com.david.wedis.datatype.BytesWrapper;
import com.david.wedis.datatype.RedisSet;
import com.david.wedis.resp.BulkString;
import com.david.wedis.resp.Resp;
import com.david.wedis.resp.RespInt;
import io.netty.channel.ChannelHandlerContext;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Srem implements WriteCommand
{
    private BytesWrapper       key;
    private List<BytesWrapper> members;

    @Override
    public CommandType type()
    {
        return CommandType.srem;
    }

    @Override
    public void setContent(Resp[] array)
    {
        key = ((BulkString) array[1]).getContent();
        members = Stream.of(array).skip(2).map(resp -> ((BulkString) resp).getContent()).collect(Collectors.toList());
    }

    @Override
    public void handle(ChannelHandlerContext ctx, WedisCore redisCore)
    {
        RedisSet redisSet = (RedisSet) redisCore.get(key);
        int      srem     = redisSet.srem(members);
        ctx.writeAndFlush(new RespInt(srem));
    }

    @Override
    public void handle(WedisCore redisCore) {
        RedisSet redisSet = (RedisSet) redisCore.get(key);
        redisSet.srem(members);
    }
}
