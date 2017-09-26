package com.king.learn.mvp.ui.widget.dialog;

import android.view.View;
import android.view.ViewGroup;

/**
 */

public interface IDialogBase
{
    /**
     * 返回内容View
     *
     * @return
     */
    View getContentView();

    /**
     * 设置内容布局id
     *
     * @param layoutId
     */
    void setContentView(int layoutId);

    /**
     * 设置内容
     *
     * @param view
     */
    void setContentView(View view);

    /**
     * 设置内容
     *
     * @param view
     * @param params
     */
    void setContentView(View view, ViewGroup.LayoutParams params);

    /**
     * 设置宽度
     *
     * @param width
     * @return
     */
    IDialogBase setWidth(int width);

    /**
     * 设置高度
     *
     * @param height
     * @return
     */
    IDialogBase setHeight(int height);

    /**
     * 设置全屏
     *
     * @return
     */
    IDialogBase setFullScreen();

    /**
     * 返回默认的padding
     *
     * @return
     */
    int getDefaultPadding();

    /**
     * 设置左边间距
     *
     * @param padding
     * @return
     */
    IDialogBase paddingLeft(int padding);

    /**
     * 设置顶部间距
     *
     * @param padding
     * @return
     */
    IDialogBase paddingTop(int padding);

    /**
     * 设置右边间距
     *
     * @param padding
     * @return
     */
    IDialogBase paddingRight(int padding);

    /**
     * 设置底部间距
     *
     * @param padding
     * @return
     */
    IDialogBase paddingBottom(int padding);

    /**
     * 设置上下左右间距
     *
     * @param paddings
     * @return
     */
    IDialogBase paddings(int paddings);

    /**
     * 是否点击按钮后自动关闭窗口
     *
     * @return
     */
    boolean isDismissAfterClick();

    /**
     * 设置是否点击按钮后自动关闭窗口,默认true(是)
     *
     * @param dismissAfterClick
     * @return
     */
    IDialogBase setDismissAfterClick(boolean dismissAfterClick);

    /**
     * 设置窗口显示的位置
     *
     * @param gravity
     * @return
     */
    IDialogBase setGrativity(int gravity);

    /**
     * 设置窗口动画style
     *
     * @param resId
     * @return
     */
    IDialogBase setAnimations(int resId);

    /**
     * 显示顶部
     */
    void showTop();

    /**
     * 显示中央
     */
    void showCenter();

    /**
     * 显示底部
     */
    void showBottom();

    /**
     * 延迟多久后关闭窗口
     *
     * @param delay （毫秒）
     * @return
     */
    IDialogBase startDismissRunnable(long delay);

    /**
     * 停止延迟关闭任务
     *
     * @return
     */
    IDialogBase stopDismissRunnable();
}
