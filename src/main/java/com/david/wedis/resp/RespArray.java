package com.david.wedis.resp;

public class RespArray implements Resp
{

    Resp[] array;

    public RespArray(Resp[] array)
    {
        this.array = array;
    }

    public Resp[] getArray()
    {
        return array;
    }
}
