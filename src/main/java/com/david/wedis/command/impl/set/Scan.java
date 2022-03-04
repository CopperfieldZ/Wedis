package com.david.wedis.command.impl.set;


import com.david.wedis.command.Command;
import com.david.wedis.command.CommandType;
import com.david.wedis.WedisCore;
import com.david.wedis.datatype.BytesWrapper;
import com.david.wedis.resp.BulkString;
import com.david.wedis.resp.Resp;
import com.david.wedis.resp.RespArray;
import io.netty.channel.ChannelHandlerContext;

import java.util.List;
import java.util.stream.Collectors;

public class Scan implements Command
{
    @Override
    public CommandType type()
    {
        return CommandType.scan;
    }

    @Override
    public void setContent(Resp[] array)
    {
    }

    @Override
    public void handle(ChannelHandlerContext ctx, WedisCore redisCore)
    {
        Resp[]     array       = new Resp[2];
        BulkString blukStrings = new BulkString(new BytesWrapper("0".getBytes(CHARSET)));
        array[0] = blukStrings;
        List<BulkString> collect = redisCore.keys().stream().map(keyName -> new BulkString(keyName)).collect(Collectors.toList());
        array[1] = new RespArray(collect.toArray(new Resp[collect.size()]));
        ctx.writeAndFlush(new RespArray(array));
    }
}
