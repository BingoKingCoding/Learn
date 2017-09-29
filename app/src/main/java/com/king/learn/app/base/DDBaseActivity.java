package com.king.learn.app.base;

import android.graphics.Color;
import android.support.v7.widget.Toolbar;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.mvp.IPresenter;

/**
 */

public abstract class DDBaseActivity<P extends IPresenter> extends BaseActivity<P>
{

    /**
     * 子类可以直接用
     *
     * @param title
     */
    protected void setToolBar(Toolbar toolbar, String title) {
        setToolBar(toolbar,title,true);
    }
    /**
     * 子类可以直接用
     *
     * @param title
     */
    protected void setToolBar(Toolbar toolbar, String title , boolean enable) {
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(enable);//1.显示toolbar的返回按钮左上角图标
        getSupportActionBar().setDisplayShowHomeEnabled(enable);//2.显示toolbar的返回按钮12要一起用
        getSupportActionBar().setDisplayShowTitleEnabled(enable);//显示toolbar的标题
        toolbar.setNavigationOnClickListener(view -> onBackPressed());
    }

}
