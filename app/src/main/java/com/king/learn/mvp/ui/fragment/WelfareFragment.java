package com.king.learn.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jess.arms.di.component.AppComponent;
import com.king.learn.app.base.BaseFragment;
import com.king.learn.mvp.contract.WelfareContract;
import com.king.learn.mvp.model.entity.GankEntity;
import com.king.learn.mvp.presenter.WelfarePresenter;

import java.util.List;

/**
 * <请描述这个类是干什么的>
 * Created by wwb on 2017/9/20 16:13.
 */

public class WelfareFragment extends BaseFragment<WelfarePresenter> implements WelfareContract.View,SwipeRefreshLayout.OnRefreshListener
{
    public static WelfareFragment newInstance() {
        WelfareFragment fragment = new WelfareFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(AppComponent appComponent)
    {

    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return null;
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
    public void startLoadMore()
    {

    }

    @Override
    public void endLoadMore()
    {

    }

    @Override
    public void setNewData(List<GankEntity.ResultsBean> mData)
    {

    }

    @Override
    public void setAddData(List<GankEntity.ResultsBean> results)
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

    @Override
    public void onRefresh()
    {
        
    }
}
