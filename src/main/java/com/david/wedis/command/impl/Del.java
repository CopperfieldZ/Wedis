package com.david.wedis.command.impl;


import com.david.wedis.command.CommandType;
import com.david.wedis.command.WriteCommand;
import com.david.wedis.WedisCore;
import com.david.wedis.datatype.BytesWrapper;
import com.david.wedis.resp.BulkString;
import com.david.wedis.resp.Resp;
import com.david.wedis.resp.RespInt;
import io.netty.channel.ChannelHandlerContext;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Del implements WriteCommand
{
    private List<BytesWrapper> keys;

    @Override
    public CommandType type()
    {
        return CommandType.del;
    }

    @Override
    public void setContent(Resp[] array)
    {
        keys = Stream.of(array).skip(1).map(resp -> ((BulkString) resp).getContent()).collect(Collectors.toList());
    }

    @Override
    public void handle(ChannelHandlerContext ctx, WedisCore redisCore)
    {
        long remove = redisCore.remove(keys);
        ctx.writeAndFlush(new RespInt((int) remove));
    }

    @Override
    public void handle(WedisCore redisCore) {
        redisCore.remove(keys);
    }
}
