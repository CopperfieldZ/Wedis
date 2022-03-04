package com.david.wedis.command.impl;


import com.david.wedis.command.WriteCommand;
import com.david.wedis.WedisCore;
import com.david.wedis.datatype.BytesWrapper;
import com.david.wedis.datatype.RedisData;
import com.david.wedis.datatype.RedisList;
import com.david.wedis.resp.BulkString;
import com.david.wedis.resp.Errors;
import com.david.wedis.resp.Resp;
import com.david.wedis.resp.RespInt;
import io.netty.channel.ChannelHandlerContext;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

public abstract class Push implements WriteCommand
{
    BiConsumer<RedisList, List<BytesWrapper>> biConsumer;
    private BytesWrapper       key;
    private List<BytesWrapper> value;

    public Push(BiConsumer<RedisList, List<BytesWrapper>> biConsumer)
    {
        this.biConsumer = biConsumer;
    }

    @Override
    public void setContent(Resp[] array)
    {
        key = ((BulkString) array[1]).getContent();
        value = new ArrayList<>();
        for (int i = 2; i < array.length; i++)
        {
            value.add(((BulkString) array[i]).getContent());
        }
    }

    @Override
    public void handle(ChannelHandlerContext ctx, WedisCore redisCore)
    {
        RedisData redisData = redisCore.get(key);
        if (redisData == null)
        {
            RedisList redisList = new RedisList();
            biConsumer.accept(redisList, value);
            redisCore.put(key, redisList);
            ctx.writeAndFlush(new RespInt(redisList.size()));
        }
        else if (redisData != null && !(redisData instanceof RedisList))
        {
            ctx.writeAndFlush(new Errors("wrong type"));
        }
        else
        {
            biConsumer.accept((RedisList) redisData, value);
            redisCore.put(key, redisData);
            ctx.writeAndFlush(new RespInt(((RedisList) redisData).size()));
        }
    }
    @Override
    public void handle( WedisCore redisCore)
    {
        RedisData redisData = redisCore.get(key);
        if (redisData == null)
        {
            RedisList redisList = new RedisList();
            biConsumer.accept(redisList, value);
            redisCore.put(key, redisList);

        }
        else if (redisData != null && !(redisData instanceof RedisList))
        {
        }
        else
        {
            biConsumer.accept((RedisList) redisData, value);
            redisCore.put(key, redisData);
        }
    }
}
