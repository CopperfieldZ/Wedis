package com.david.wedis.command.impl;

import com.david.wedis.command.CommandType;
import com.david.wedis.datatype.RedisList;

public class Rpush extends Push
{

    public Rpush()
    {
        super(RedisList::rpush);
    }

    @Override
    public CommandType type()
    {
        return CommandType.rpush;
    }
}
