package com.david.wedis.command;

import com.david.wedis.WedisCore;


public interface WriteCommand extends Command {
    /**
     * for aof
     * @param redisCore
     */
    void handle(WedisCore redisCore);

}
