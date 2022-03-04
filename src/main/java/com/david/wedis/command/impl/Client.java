package com.david.wedis.command.impl;


import com.david.wedis.command.Command;
import com.david.wedis.command.CommandType;
import com.david.wedis.WedisCore;
import com.david.wedis.datatype.BytesWrapper;
import com.david.wedis.resp.BulkString;
import com.david.wedis.resp.Resp;
import com.david.wedis.resp.SimpleString;
import com.david.wedis.util.TRACEID;
import io.netty.channel.ChannelHandlerContext;

public class Client implements Command
{
    private String subCommand;
    private Resp[] array;

    @Override
    public CommandType type()
    {
        return CommandType.client;
    }

    @Override
    public void setContent(Resp[] array)
    {
        this.array = array;
        subCommand = ((BulkString) array[1]).getContent().toUtf8String();
    }

    @Override
    public void handle(ChannelHandlerContext ctx, WedisCore redisCore)
    {
        String traceId = TRACEID.currentTraceId();
        LOGGER.debug("traceId:{} 当前的子命令是：{}"+traceId+subCommand);
        switch (subCommand)
        {
            case "setname":
                BytesWrapper connectionName = ((BulkString) array[2]).getContent();
                redisCore.putClient(connectionName, ctx.channel());
                break;
            default:
                throw new IllegalArgumentException();
        }
        ctx.writeAndFlush(new SimpleString("OK"));
    }
}
