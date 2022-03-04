package com.david.wedis.command.impl;


import com.david.wedis.command.Command;
import com.david.wedis.command.CommandType;
import com.david.wedis.WedisCore;
import com.david.wedis.datatype.BytesWrapper;
import com.david.wedis.resp.BulkString;
import com.david.wedis.resp.Resp;
import io.netty.channel.ChannelHandlerContext;

import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Info implements Command
{
    @Override
    public CommandType type()
    {
        return CommandType.info;
    }

    @Override
    public void setContent(Resp[] array)
    {
    }

    @Override
    public void handle(ChannelHandlerContext ctx, WedisCore redisCore)
    {
        List<String> list = new ArrayList<>();
        list.add("redis_version:jfire_redis_mock");
        list.add("os:" + System.getProperty("os.name"));
        list.add("process_id:" + getPid());
        Optional<String> reduce = list.stream().map(name -> name + "\r\n").reduce((first, second) -> first + second);
        String           s      = reduce.get();
        ctx.writeAndFlush(new BulkString(new BytesWrapper(s.getBytes(CHARSET))));
    }

    private String getPid()
    {
        String name = ManagementFactory.getRuntimeMXBean().getName();
        String pid  = name.split("@")[0];
        return pid;
    }
}
