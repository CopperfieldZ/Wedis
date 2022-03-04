package com.david.wedis.resp;

import com.david.wedis.datatype.BytesWrapper;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class BulkString implements Resp
{
    public static final BulkString NullBulkString = new BulkString(null);
    static final        Charset    CHARSET        = StandardCharsets.UTF_8;
    BytesWrapper content;

    public BulkString(BytesWrapper content)
    {
        this.content = content;
    }

    public BytesWrapper getContent()
    {
        return content;
    }
}
