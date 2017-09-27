package com.king.learn.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.king.learn.R;
import com.king.learn.app.base.BaseFragment;
import com.king.learn.di.component.DaggerCollectComponent;
import com.king.learn.di.module.CollectModule;
import com.king.learn.mvp.contract.CollectContract;
import com.king.learn.mvp.presenter.CollectPresenter;
import com.king.learn.mvp.ui.adapter.CollectViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;

/**
 * <请描述这个类是干什么的>
 * Created by wwb on 2017/9/20 16:13.
 */

public class CollectFragment extends BaseFragment<CollectPresenter> implements CollectContract.View
{
    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.mainPager)
    ViewPager mainPager;
    private List<Fragment> mFragments;

    public static CollectFragment newInstance() {
        CollectFragment fragment = new CollectFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(AppComponent appComponent)
    {
        DaggerCollectComponent.builder()
                .appComponent(appComponent)
                .collectModule(new CollectModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_collect,container,false);
    }

    @Override
    public void initData(Bundle savedInstanceState)
    {
        if (mFragments == null) {
            mFragments = new ArrayList<>();
            mFragments.add(MeiziFragment.newInstance());
            mFragments.add(ArticleFragment.newInstance());
        }
        mainPager.setOffscreenPageLimit(mFragments.size());
        mainPager.setAdapter(new CollectViewPagerAdapter(getChildFragmentManager(),mFragments));
        tabs.setupWithViewPager(mainPager);
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
        checkNotNull(message);
        ArmsUtils.snackbarText(message);
    }

    @Override
    public void launchActivity(Intent intent)
    {
        checkNotNull(intent);
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void killMyself()
    {

    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        mainPager = null;
        tabs = null;
    }
}
