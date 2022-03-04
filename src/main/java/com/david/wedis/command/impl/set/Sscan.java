package com.david.wedis.command.impl.set;

import com.david.wedis.command.CommandType;
import com.david.wedis.command.impl.AbstraceScan;
import com.david.wedis.WedisCore;
import com.david.wedis.datatype.BytesWrapper;
import com.david.wedis.datatype.RedisSet;
import com.david.wedis.resp.BulkString;
import com.david.wedis.resp.Resp;
import com.david.wedis.resp.RespArray;

import java.util.List;
import java.util.stream.Collectors;

public class Sscan extends AbstraceScan
{
    private BytesWrapper key;

    @Override
    public CommandType type()
    {
        return CommandType.sscan;
    }

    @Override
    public void setContent(Resp[] array)
    {
        key = ((BulkString) array[1]).getContent();
    }

    @Override
    protected RespArray get(WedisCore redisCore)
    {
        RedisSet         redisSet = (RedisSet) redisCore.get(key);
        List<BulkString> collect  = redisSet.keys().stream().map(keyName -> new BulkString(keyName)).collect(Collectors.toList());
        return new RespArray(collect.toArray(new Resp[collect.size()]));
    }
}
