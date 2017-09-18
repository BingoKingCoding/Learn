package com.king.learn.app.listener;

import java.util.Iterator;

/**
 * <遍历回调>
 * Created by wwb on 2017/9/18 17:40.
 */

public interface DDIterateCallback<T>
{
    /**
     * 返回true，结束遍历
     *
     * @param i
     * @param item
     * @param it
     * @return
     */
    boolean next(int i, T item, Iterator<T> it);
}
