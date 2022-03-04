package com.david.wedis.command.impl.list;


import com.david.wedis.command.Command;
import com.david.wedis.command.CommandType;
import com.david.wedis.WedisCore;
import com.david.wedis.datatype.BytesWrapper;
import com.david.wedis.datatype.RedisList;
import com.david.wedis.resp.BulkString;
import com.david.wedis.resp.Resp;
import com.david.wedis.resp.RespArray;
import io.netty.channel.ChannelHandlerContext;

import java.util.List;

public class Lrange implements Command
{
    BytesWrapper key;
    int          start;
    int          end;

    @Override
    public CommandType type()
    {
        return CommandType.lrange;
    }

    @Override
    public void setContent(Resp[] array)
    {
        key = ((BulkString) array[1]).getContent();
        start = Integer.parseInt(((BulkString) array[2]).getContent().toUtf8String());
        end = Integer.parseInt(((BulkString) array[3]).getContent().toUtf8String());
    }

    @Override
    public void handle(ChannelHandlerContext ctx, WedisCore redisCore)
    {
        RedisList          redisList = (RedisList) redisCore.get(key);
        List<BytesWrapper> lrang     = redisList.lrang(start, end);
        RespArray          respArray = new RespArray(lrang.stream().map(BulkString::new).toArray(Resp[]::new));
        ctx.writeAndFlush(respArray);
    }
}
