package com.david.wedis.command.impl;


import com.david.wedis.command.Command;
import com.david.wedis.command.CommandType;
import com.david.wedis.WedisCore;
import com.david.wedis.resp.BulkString;
import com.david.wedis.resp.Resp;
import com.david.wedis.resp.SimpleString;
import io.netty.channel.ChannelHandlerContext;

public class Auth implements Command
{
    private String password;

    @Override
    public CommandType type()
    {
        return CommandType.auth;
    }

    @Override
    public void setContent(Resp[] array)
    {
        BulkString blukStrings = (BulkString) array[1];
        byte[]     content     = blukStrings.getContent().getByteArray();
        if (content.length == 0)
        {
            password = "";
        }
        else
        {
            password = new String(content);
        }
    }

    @Override
    public void handle(ChannelHandlerContext ctx, WedisCore redisCore)
    {
        SimpleString ok = new SimpleString("OK");
        ctx.writeAndFlush(ok);
    }
}
