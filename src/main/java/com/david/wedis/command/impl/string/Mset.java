package com.david.wedis.command.impl.string;

import com.david.wedis.command.CommandType;
import com.david.wedis.command.WriteCommand;
import com.david.wedis.WedisCore;
import com.david.wedis.datatype.BytesWrapper;
import com.david.wedis.datatype.RedisString;
import com.david.wedis.resp.BulkString;
import com.david.wedis.resp.Resp;
import com.david.wedis.resp.SimpleString;
import io.netty.channel.ChannelHandlerContext;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Mset implements WriteCommand
{
    private List<BytesWrapper> kvList;

    @Override
    public CommandType type()
    {
        return CommandType.mset;
    }

    @Override
    public void setContent(Resp[] array)
    {
        kvList = Stream.of(array).skip(1).map(resp -> ((BulkString) resp).getContent()).collect(Collectors.toList());
    }

    @Override
    public void handle(ChannelHandlerContext ctx, WedisCore redisCore)
    {
        for (int i = 0; i<kvList.size();i+=2) {

            redisCore.put(kvList.get(i), new RedisString(kvList.get(i+1)));
        }
        ctx.writeAndFlush(SimpleString.OK);
    }

    @Override
    public void handle(WedisCore redisCore) {
        for (int i = 0; i<kvList.size();i+=2) {
            redisCore.put(kvList.get(i), new RedisString(kvList.get(i+1)));
        }
    }
}
