package com.king.learn.mvp.ui.widget.webview;

import android.view.View;

/**
 * <定义实现的方法>
 * Created by wwb on 2017/7/4 17:23.
 */

public interface IWebPageView
{
    // 隐藏进度条
    void hindProgressBar();

    // 显示webview
    void showWebView();

    // 隐藏webview
    void hindWebView();

    //  进度条先加载到90%,然后再加载到100%
    void startProgress();
    /**
     * 进度条变化时调用
     */
    void progressChanged(int newProgress);

    /**
     * 播放网络视频全屏调用
     */
    void fullViewAddView(View view);

    void showVideoFullView();

    void hindVideoFullView();
}
