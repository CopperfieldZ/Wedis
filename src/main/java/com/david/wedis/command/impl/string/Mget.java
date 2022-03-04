package com.david.wedis.command.impl.string;

import com.david.wedis.command.Command;
import com.david.wedis.command.CommandType;
import com.david.wedis.WedisCore;
import com.david.wedis.datatype.BytesWrapper;
import com.david.wedis.datatype.RedisData;
import com.david.wedis.datatype.RedisString;
import com.david.wedis.resp.BulkString;
import com.david.wedis.resp.Resp;
import com.david.wedis.resp.RespArray;
import io.netty.channel.ChannelHandlerContext;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Mget implements Command
{
    private List<BytesWrapper> keys;

    @Override
    public CommandType type()
    {
        return CommandType.mget;
    }

    @Override
    public void setContent(Resp[] array)
    {
        keys = Stream.of(array).skip(1).map(resp -> ((BulkString) resp).getContent()).collect(Collectors.toList());
    }

    @Override
    public void handle(ChannelHandlerContext ctx, WedisCore redisCore)
    {
        LinkedList<BytesWrapper> linkedList= new LinkedList();
        keys.forEach(key -> {
            RedisData redisData = redisCore.get(key);
            if (redisData == null)
            {
            }
            else if (redisData instanceof RedisString)
            {
                linkedList.add(((RedisString) redisData).getValue()) ;
            }
            else
            {
                throw new UnsupportedOperationException();
            }
        });
        RespArray respArray = new RespArray(linkedList.stream().map(BulkString::new).toArray(Resp[]::new));
        ctx.writeAndFlush(respArray);
    }

}
