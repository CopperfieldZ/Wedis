package com.david.wedis.command.impl.list;

import com.david.wedis.command.CommandType;
import com.david.wedis.command.impl.Push;
import com.david.wedis.datatype.RedisList;

public class Lpush extends Push
{

    public Lpush()
    {
        super(RedisList::lpush);
    }

    @Override
    public CommandType type()
    {
        return CommandType.lpush;
    }
}
