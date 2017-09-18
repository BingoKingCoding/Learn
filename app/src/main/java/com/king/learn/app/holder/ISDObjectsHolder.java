package com.king.learn.app.holder;

import com.king.learn.app.listener.DDIterateCallback;

/**
 * <多对象持有管理类适用于保存监听对象>
 * Created by wwb on 2017/9/18 17:42.
 */

public interface ISDObjectsHolder<T>
{
    /**
     * 添加对象
     *
     * @param object
     */
    void add(T object);

    /**
     * 移除对象
     *
     * @param object
     * @return
     */
    boolean remove(T object);

    /**
     * 是否已经包含了该对象
     *
     * @param object
     * @return
     */
    boolean contains(T object);

    /**
     * 对象个数
     *
     * @return
     */
    int size();

    /**
     * 清空对象
     */
    void clear();

    /**
     * 遍历对象
     *
     * @param callback
     * @return
     */
    boolean foreach(DDIterateCallback<T> callback);

    /**
     * 倒序遍历对象
     *
     * @param callback
     * @return
     */
    boolean foreachReverse(DDIterateCallback<T> callback);
}
