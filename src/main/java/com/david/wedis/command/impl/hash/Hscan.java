package com.david.wedis.command.impl.hash;

import com.david.wedis.command.CommandType;
import com.david.wedis.command.impl.AbstraceScan;
import com.david.wedis.WedisCore;
import com.david.wedis.datatype.BytesWrapper;
import com.david.wedis.datatype.RedisHash;
import com.david.wedis.resp.BulkString;
import com.david.wedis.resp.Resp;
import com.david.wedis.resp.RespArray;

import java.util.Map;
import java.util.stream.Stream;

public class Hscan extends AbstraceScan
{
    private BytesWrapper key;

    @Override
    public CommandType type()
    {
        return CommandType.hscan;
    }

    @Override
    public void setContent(Resp[] array)
    {
        key = ((BulkString) array[1]).getContent();
    }

    @Override
    protected RespArray get(WedisCore redisCore)
    {
        RedisHash                       redisHash = (RedisHash) redisCore.get(key);
        Map<BytesWrapper, BytesWrapper> map       = redisHash.getMap();
        return new RespArray(map.entrySet().stream().flatMap(entry -> {
            Resp[] resps = new Resp[2];
            resps[0] = new BulkString(entry.getKey());
            resps[1] = new BulkString(entry.getValue());
            return Stream.of(resps);
        }).toArray(Resp[]::new));
    }
}
