package com.king.learn.app.holder;

import com.king.learn.app.listener.DDIterateCallback;
import com.king.learn.app.utils.DDCollectionUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * <内部用ArrayList实现的多对象持有管理类>
 * Created by wwb on 2017/9/18 17:45.
 */

public class DDObjectsHolder<T> implements ISDObjectsHolder<T>
{
    private List<T> mListObject = new ArrayList<T>();

    @Override
    public synchronized void add(T object)
    {
        if (object == null)
        {
            return;
        }
        if (!mListObject.contains(object))
        {
            mListObject.add(object);
        }
    }

    @Override
    public synchronized boolean remove(T object)
    {
        if (object == null)
        {
            return false;
        }
        return mListObject.remove(object);
    }

    @Override
    public synchronized boolean contains(T object)
    {
        if (object == null)
        {
            return false;
        }
        return mListObject.contains(object);
    }

    @Override
    public int size()
    {
        return mListObject.size();
    }

    @Override
    public synchronized void clear()
    {
        mListObject.clear();
    }

    @Override
    public synchronized boolean foreach(DDIterateCallback<T> callback)
    {
        return DDCollectionUtil.foreach(mListObject, callback);
    }

    @Override
    public synchronized boolean foreachReverse(DDIterateCallback<T> callback)
    {
        return DDCollectionUtil.foreachReverse(mListObject, callback);
    }
}
