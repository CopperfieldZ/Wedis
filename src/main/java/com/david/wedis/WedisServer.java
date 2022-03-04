package com.david.wedis;

public interface WedisServer
{
    void start();

    void close();

    WedisCore getRedisCore();
}
