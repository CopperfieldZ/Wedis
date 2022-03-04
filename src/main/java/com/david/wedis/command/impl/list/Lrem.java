package com.david.wedis.command.impl.list;


import com.david.wedis.command.CommandType;
import com.david.wedis.command.WriteCommand;
import com.david.wedis.WedisCore;
import com.david.wedis.datatype.BytesWrapper;
import com.david.wedis.datatype.RedisList;
import com.david.wedis.resp.BulkString;
import com.david.wedis.resp.Resp;
import com.david.wedis.resp.RespInt;
import io.netty.channel.ChannelHandlerContext;

public class Lrem implements WriteCommand
{
    private BytesWrapper key;
    private BytesWrapper value;

    @Override
    public CommandType type()
    {
        return CommandType.lrem;
    }

    @Override
    public void setContent(Resp[] array)
    {
        key = ((BulkString) array[1]).getContent();
        value = ((BulkString) array[3]).getContent();
    }

    @Override
    public void handle(ChannelHandlerContext ctx, WedisCore redisCore)
    {
        RedisList redisList = (RedisList) redisCore.get(key);
        int       remove    = redisList.remove(value);
        ctx.writeAndFlush(new RespInt(remove));
    }

    @Override
    public void handle(WedisCore redisCore) {
        RedisList redisList = (RedisList) redisCore.get(key);
        redisList.remove(value);
    }
}
