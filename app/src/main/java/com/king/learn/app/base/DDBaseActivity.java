package com.king.learn.app.base;

import android.graphics.Color;
import android.support.v7.widget.Toolbar;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.mvp.IPresenter;
import com.jess.arms.utils.ArmsUtils;

/**
 */

public abstract class DDBaseActivity<P extends IPresenter> extends BaseActivity<P>
{

    /**
     * 触摸返回键是否退出App
     */
    protected boolean mIsExitApp = false;
    protected long mExitTime = 0;

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

    @Override
    public void onBackPressed()
    {
        if (mIsExitApp)
        {
            exitApp();
        } else
        {
            super.onBackPressed();
        }
    }

    public void exitApp()
    {
        if (System.currentTimeMillis() - mExitTime > 2000)
        {
            ArmsUtils.snackbarText("再按一次退出!");
        } else
        {
            ArmsUtils.exitApp();
        }
        mExitTime = System.currentTimeMillis();
    }

    protected void showLoadingDialog(){
    }

    protected void hideLoadingDialog(){
    }

}
