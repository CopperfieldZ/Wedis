package com.david.wedis.command;

import com.david.wedis.command.impl.*;
import com.david.wedis.command.impl.string.*;

import com.david.wedis.command.impl.hash.Hdel;
import com.david.wedis.command.impl.hash.Hscan;
import com.david.wedis.command.impl.hash.Hset;
import com.david.wedis.command.impl.list.Lpush;
import com.david.wedis.command.impl.list.Lrange;
import com.david.wedis.command.impl.list.Lrem;
import com.david.wedis.command.impl.set.Sadd;
import com.david.wedis.command.impl.set.Scan;
import com.david.wedis.command.impl.set.Srem;
import com.david.wedis.command.impl.set.Sscan;
import com.david.wedis.command.impl.zset.Zadd;
import com.david.wedis.command.impl.zset.Zrem;
import com.david.wedis.command.impl.zset.Zrevrange;

import java.util.function.Supplier;

public enum CommandType
{
    auth(Auth::new), config(Config::new), scan(Scan::new),//
    info(Info::new), client(Client::new), set(Set::new), type(Type::new),//
    ttl(Ttl::new), get(Get::new), quit(Quit::new),//
    setnx(SetNx::new), lpush(Lpush::new), lrange(Lrange::new), lrem(Lrem::new), rpush(Rpush::new), del(Del::new), sadd(Sadd::new),//
    sscan(Sscan::new), srem(Srem::new), hset(Hset::new), hscan(Hscan::new), hdel(Hdel::new),//
    zadd(Zadd::new), zrevrange(Zrevrange::new), zrem(Zrem::new), setex(SetEx::new), exists(Exists::new), expire(Expire::new),
    ping(Ping::new),select(Select::new),keys(Keys::new),incr(Incr::new),decr(Decr::new),mset(Mset::new),mget(Mget::new),
    //
    ;

    private final Supplier<Command> supplier;

    CommandType(Supplier supplier)
    {
        this.supplier = supplier;
    }

    public Supplier<Command> getSupplier()
    {
        return supplier;
    }
}
