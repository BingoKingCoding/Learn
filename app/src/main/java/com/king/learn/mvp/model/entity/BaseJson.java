package com.king.learn.mvp.model.entity;

import com.king.learn.mvp.model.api.Api;

import java.io.Serializable;

/**
 * 如果你服务器返回的数据固定为这种方式(字段名可根据服务器更改)
 * 替换范型即可重用BaseJson
 * Created by wwb on 2017/9/15 14:10.
 */

public class BaseJson<T> implements Serializable
{
    private T data;
    private String code;
    private String msg;

    public T getData()
    {
        return data;
    }

    public void setData(T data)
    {
        this.data = data;
    }

    public String getCode()
    {
        return code;
    }

    public void setCode(String code)
    {
        this.code = code;
    }

    public String getMsg()
    {
        return msg;
    }

    public void setMsg(String msg)
    {
        this.msg = msg;
    }

    public boolean isSuccess()
    {
        if (code.equals(Api.RequestSuccess))
        {
            return true;
        }
        return false;
    }

}
