package com.david.wedis.command;


import com.david.wedis.resp.BulkString;
import com.david.wedis.resp.Resp;
import com.david.wedis.resp.RespArray;
import com.david.wedis.resp.SimpleString;
import com.david.wedis.util.TRACEID;
import org.apache.log4j.Logger;


import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class CommandFactory
{
    private static final Logger LOGGER = Logger.getLogger(CommandFactory.class);
    static               Map<String, Supplier<Command>> map    = new HashMap<>();

    static
    {
        for (CommandType each : CommandType.values())
        {
            map.put(each.name(), each.getSupplier());
        }
    }

    public static Command from(RespArray arrays)
    {
        Resp[]            array       = arrays.getArray();
        String            commandName = ((BulkString) array[0]).getContent().toUtf8String().toLowerCase();
        Supplier<Command> supplier    = map.get(commandName);
        if (supplier == null)
        {
            LOGGER.debug("traceId:"+TRACEID.currentTraceId()+" 不支持的命令："+ commandName);
            System.out.println("不支持的命令：" + commandName);
            return null;
        }
        else
        {
            try
            {
                Command command = supplier.get();
                command.setContent(array);
                return command;
            }
            catch (Throwable e)
            {
                LOGGER.debug("traceId:"+TRACEID.currentTraceId()+" 不支持的命令：{},数据读取异常"+commandName);
                e.printStackTrace();
                return null;
            }
        }
    }
    public static Command from(SimpleString string)
    {
        String            commandName = string.getContent().toLowerCase();
        Supplier<Command> supplier    = map.get(commandName);
        if (supplier == null)
        {
            LOGGER.debug("traceId:"+TRACEID.currentTraceId()+" 不支持的命令："+ commandName);
            System.out.println("不支持的命令：" + commandName);
            return null;
        }
        else
        {
            try
            {
                return supplier.get();
            }
            catch (Throwable e)
            {
                LOGGER.debug("traceId:"+TRACEID.currentTraceId()+" 不支持的命令：{},数据读取异常"+commandName);
                e.printStackTrace();
                return null;
            }
        }
    }
}
