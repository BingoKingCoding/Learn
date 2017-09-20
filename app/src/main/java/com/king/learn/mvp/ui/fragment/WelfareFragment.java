package com.king.learn.mvp.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jess.arms.di.component.AppComponent;
import com.king.learn.app.base.BaseFragment;

/**
 * <请描述这个类是干什么的>
 * Created by wwb on 2017/9/20 16:13.
 */

public class WelfareFragment extends BaseFragment
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
}
