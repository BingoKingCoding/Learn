package com.king.learn.app.base;

import android.graphics.Color;
import android.support.v7.widget.Toolbar;

import com.afollestad.materialdialogs.MaterialDialog;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.mvp.IPresenter;
import com.jess.arms.utils.ArmsUtils;
import com.king.learn.R;

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
    protected MaterialDialog mProgressDialog;
    public void showProgress(boolean flag, String message){
        if (mProgressDialog == null) {
            mProgressDialog = new MaterialDialog.Builder(this)
                    .title(R.string.progress_dialog)
                    .content(message)
                    .progress(true, 0).build();
            mProgressDialog.setCancelable(flag);
            mProgressDialog.setCanceledOnTouchOutside(false);
        }
        mProgressDialog.show();
    }

    public void showProgress(String message) {
        showProgress(true, message);
    }

    public void showProgress() {
        showProgress(true);
    }

    public void showProgress(boolean flag) {
        showProgress(flag, "");
    }

    public void hideProgress(){
        if (mProgressDialog == null)
            return;

        if (mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }


//    public Observable<Object> clicks(int viewId) {
//        return clicks(findViewById(viewId));
//    }
//
//    public Observable<Object> clicks(View view) {
//        return RxView.clicks(view).throttleFirst(500, TimeUnit.MILLISECONDS).compose(bindToLifecycle());
//    }


}
