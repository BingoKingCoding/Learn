package com.king.learn.app;

import android.content.Context;

/**
 * <获取全局的context>
 * Created by wwb on 2017/9/26 15:20.
 */

public class DDLibrary
{
    private static DDLibrary sInstance;

    private Context mContext;
    private DDLibrary()
    {
    }

    public Context getContext()
    {
        return mContext;
    }
    public void init(Context context)
    {
        mContext = context.getApplicationContext();

    }
    public static DDLibrary getInstance()
    {
        if (sInstance == null)
        {
            sInstance = new DDLibrary();
        }
        return sInstance;
    }
}
