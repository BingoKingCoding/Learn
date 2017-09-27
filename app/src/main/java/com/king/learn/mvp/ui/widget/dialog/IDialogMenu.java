package com.king.learn.mvp.ui.widget.dialog;

import android.view.View;
import android.widget.BaseAdapter;

import java.util.List;

/**
 */

public interface IDialogMenu
{
    /**
     * 设置标题文字
     *
     * @param text
     * @return
     */
    IDialogMenu setTextTitle(String text);

    /**
     * 设置取消文字
     *
     * @param text
     * @return
     */
    IDialogMenu setTextCancel(String text);

    /**
     * 设置回调
     *
     * @param callback
     * @return
     */
    IDialogMenu setCallback(Callback callback);

    /**
     * 设置列表数据
     *
     * @param objects
     * @return
     */
    IDialogMenu setItems(Object... objects);

    /**
     * 设置列表数据
     *
     * @param listObject
     * @return
     */
    IDialogMenu setItems(List<Object> listObject);

    /**
     * 设置适配器
     *
     * @param adapter
     * @return
     */
    IDialogMenu setAdapter(BaseAdapter adapter);


    interface Callback
    {
        void onClickItem(View v, int index, DialogBase dialog);

        void onClickCancel(View v, DialogBase dialog);
    }
}
