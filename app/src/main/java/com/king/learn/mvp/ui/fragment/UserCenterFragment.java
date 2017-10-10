package com.king.learn.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jess.arms.di.component.AppComponent;
import com.king.learn.R;
import com.king.learn.app.base.BaseFragment;
import com.king.learn.di.component.DaggerUserCenterComponent;
import com.king.learn.di.module.UserCenterModule;
import com.king.learn.mvp.contract.UserCenterContract;
import com.king.learn.mvp.presenter.UserCenterPresenter;

/**
 * <个人中心>
 * Created by wwb on 2017/10/10 16:38.
 */

public class UserCenterFragment extends BaseFragment<UserCenterPresenter> implements UserCenterContract.View
{

    public static UserCenterFragment newInstance() {
        UserCenterFragment fragment = new UserCenterFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(AppComponent appComponent)
    {
        DaggerUserCenterComponent.builder()
                .appComponent(appComponent)
                .userCenterModule(new UserCenterModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_user_center,container,false);
    }

    @Override
    public void initData(Bundle savedInstanceState)
    {

    }

    @Override
    public void setData(Object data)
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
    public void showMessage(String message)
    {

    }

    @Override
    public void launchActivity(Intent intent)
    {

    }

    @Override
    public void killMyself()
    {

    }
}
