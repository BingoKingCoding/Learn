package com.king.learn.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jess.arms.base.DefaultAdapter;
import com.jess.arms.di.component.AppComponent;
import com.king.learn.app.base.BaseFragment;
import com.king.learn.mvp.contract.CategoryContract;
import com.king.learn.mvp.presenter.CategoryPresenter;

/**
 * <请描述这个类是干什么的>
 * Created by wwb on 2017/9/20 17:19.
 */

public class CategoryFragment extends BaseFragment<CategoryPresenter> implements CategoryContract.View
{

    public static CategoryFragment newInstance(String type) {
        CategoryFragment fragment = new CategoryFragment();
        Bundle bundle = new Bundle();
        bundle.putString("type", type);
        fragment.setArguments(bundle);
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
    public void setAdapter(DefaultAdapter mAdapter)
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
