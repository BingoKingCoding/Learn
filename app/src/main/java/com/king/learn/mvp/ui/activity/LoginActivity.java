package com.king.learn.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.king.learn.R;
import com.king.learn.app.base.DDBaseActivity;
import com.king.learn.di.component.DaggerLoginComponent;
import com.king.learn.di.module.LoginModule;
import com.king.learn.mvp.contract.LoginContract;
import com.king.learn.mvp.presenter.LoginPresenter;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class LoginActivity extends DDBaseActivity<LoginPresenter> implements LoginContract.View
{


    @Override
    public void setupActivityComponent(AppComponent appComponent)
    {
        DaggerLoginComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .loginModule(new LoginModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(Bundle savedInstanceState)
    {
        return R.layout.activity_login; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(Bundle savedInstanceState)
    {

    }


    @Override
    public void showLoading()
    {

    }

    @Override
    public void hideLoading()
    {

    }

    @Override
    public void showMessage(@NonNull String message)
    {
        checkNotNull(message);
        ArmsUtils.snackbarText(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent)
    {
        checkNotNull(intent);
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void killMyself()
    {
        finish();
    }


}
