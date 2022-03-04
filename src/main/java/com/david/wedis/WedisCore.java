package com.david.wedis;


import com.david.wedis.datatype.BytesWrapper;
import com.david.wedis.datatype.RedisData;
import io.netty.channel.Channel;

import java.util.List;
import java.util.Set;

public interface WedisCore
{
    Set<BytesWrapper> keys();

    void putClient(BytesWrapper connectionName, Channel channelContext);

    boolean exist(BytesWrapper key);

    void put(BytesWrapper key, RedisData redisData);

    RedisData get(BytesWrapper key);

    long remove(List<BytesWrapper> keys);

    void cleanAll();
}
