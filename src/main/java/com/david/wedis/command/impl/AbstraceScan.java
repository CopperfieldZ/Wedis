package com.david.wedis.command.impl;


import com.david.wedis.command.Command;
import com.david.wedis.WedisCore;
import com.david.wedis.datatype.BytesWrapper;
import com.david.wedis.resp.BulkString;
import com.david.wedis.resp.Resp;
import com.david.wedis.resp.RespArray;
import io.netty.channel.ChannelHandlerContext;

public abstract class AbstraceScan implements Command
{

    @Override
    public void handle(ChannelHandlerContext ctx, WedisCore redisCore)
    {
        Resp[]     array       = new Resp[2];
        BulkString blukStrings = new BulkString(new BytesWrapper("0".getBytes(CHARSET)));
        array[0] = blukStrings;
        array[1] = get(redisCore);
        ctx.writeAndFlush(new RespArray(array));
    }

    protected abstract RespArray get(WedisCore redisCore);
}
