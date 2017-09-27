package com.king.learn.mvp.ui.widget.dialog;

import android.view.View;

/**
 */

public interface IDialogConfirm
{
    /**
     * 设置自定义View，替换掉中间内容部分的布局
     *
     * @param layoutId
     * @return
     */
    IDialogConfirm setCustomView(int layoutId);

    /**
     * 设置自定义View，替换掉中间内容部分的布局
     *
     * @param view
     * @return
     */
    IDialogConfirm setCustomView(View view);

    /**
     * 设置点击回调
     *
     * @param callback
     * @return
     */
    IDialogConfirm setCallback(Callback callback);

    /**
     * 设置标题文字
     *
     * @param text
     * @return
     */
    IDialogConfirm setTextTitle(String text);

    /**
     * 设置内容文字
     *
     * @param text
     * @return
     */
    IDialogConfirm setTextContent(String text);

    /**
     * 设置取消按钮文字
     *
     * @param text
     * @return
     */
    IDialogConfirm setTextCancel(String text);

    /**
     * 设置确定按钮文字
     *
     * @param text
     * @return
     */
    IDialogConfirm setTextConfirm(String text);

    /**
     * 设置标题文字颜色
     *
     * @param color
     * @return
     */
    IDialogConfirm setTextColorTitle(int color);

    /**
     * 设置内容文字颜色
     *
     * @param color
     * @return
     */
    IDialogConfirm setTextColorContent(int color);

    /**
     * 设置取消文字颜色
     *
     * @param color
     * @return
     */
    IDialogConfirm setTextColorCancel(int color);

    /**
     * 设置确认文字颜色
     *
     * @param color
     * @return
     */
    IDialogConfirm setTextColorConfirm(int color);

    interface Callback
    {
        void onClickCancel(View v, DialogBase dialog);

        void onClickConfirm(View v, DialogBase dialog);
    }
}
