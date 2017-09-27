package com.king.learn.mvp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.Preconditions;
import com.king.learn.R;
import com.king.learn.app.utils.FragmentUtils;
import com.king.learn.di.component.DaggerMainComponent;
import com.king.learn.di.module.MainModule;
import com.king.learn.mvp.contract.MainContract;
import com.king.learn.mvp.presenter.MainPresenter;
import com.king.learn.mvp.ui.fragment.CollectFragment;
import com.king.learn.mvp.ui.fragment.HomeFragment;
import com.king.learn.mvp.ui.fragment.WelfareFragment;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.king.learn.app.EventBusTags.ACTIVITY_FRAGMENT_REPLACE;

public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.View
{
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.bottomBar)
    BottomBar mBottomBar;
    @BindView(R.id.toolbar_back)
    RelativeLayout toolbarBack;
    private RxPermissions mRxPermissions;
    private List<Integer> mTitles;
    private List<Fragment> mFragments;
    private List<Integer> mNavIds;
    private int mReplace = 0;

    private OnTabSelectListener mOnTabSelectListener = tabId ->
    {
        switch (tabId)
        {
            case R.id.tab_home:
                mReplace = 0;
                break;
            case R.id.tab_dashboard:
                mReplace = 1;
                break;
            case R.id.tab_mycenter:
                mReplace = 2;
                break;
        }
        mToolbarTitle.setText(mTitles.get(mReplace));
        FragmentUtils.hideAllShowFragment(mFragments.get(mReplace));

    };


    @Override
    public void setupActivityComponent(AppComponent appComponent)
    {
        this.mRxPermissions = new RxPermissions(this);
        //如找不到该类,请编译一下项目
        DaggerMainComponent
                .builder()
                .appComponent(appComponent)
                .mainModule(new MainModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(Bundle savedInstanceState)
    {
        return R.layout.activity_main;
    }

    @Override
    public void initData(Bundle savedInstanceState)
    {
        toolbarBack.setVisibility(View.GONE);
        mPresenter.requestPermissions();
        if (mTitles == null)
        {
            mTitles = new ArrayList<>();
            mTitles.add(R.string.title_home);
            mTitles.add(R.string.title_dashboard);
            mTitles.add(R.string.title_mecenter);
        }
        if (mNavIds == null)
        {
            mNavIds = new ArrayList<>();
            mNavIds.add(R.id.tab_home);
            mNavIds.add(R.id.tab_dashboard);
            mNavIds.add(R.id.tab_mycenter);
        }
        HomeFragment homeFragment;
        WelfareFragment welfareFragment;
        CollectFragment collectFragment;

        if (savedInstanceState == null)
        {
            homeFragment = HomeFragment.newInstance();
            welfareFragment = WelfareFragment.newInstance();
            collectFragment = CollectFragment.newInstance();
        }else {
            mReplace = savedInstanceState.getInt(ACTIVITY_FRAGMENT_REPLACE);
            FragmentManager fm = getSupportFragmentManager();
            homeFragment = (HomeFragment) FragmentUtils.findFragment(fm,HomeFragment.class);
            welfareFragment = (WelfareFragment) FragmentUtils.findFragment(fm,WelfareFragment.class);
            collectFragment = (CollectFragment) FragmentUtils.findFragment(fm,CollectFragment.class);
        }

        if (mFragments == null)
        {
            mFragments = new ArrayList<>();
            mFragments.add(homeFragment);
            mFragments.add(welfareFragment);
            mFragments.add(collectFragment);
        }
        FragmentUtils.addFragments(getSupportFragmentManager(),mFragments,R.id.main_frame,0);
        mBottomBar.setOnTabSelectListener(mOnTabSelectListener);
    }

    @Override
    public RxPermissions getRxPermissions()
    {
        return mRxPermissions;
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
        Preconditions.checkNotNull(message);
        ArmsUtils.snackbarText(message);
    }

    @Override
    public void launchActivity(Intent intent)
    {
        Preconditions.checkNotNull(intent);
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void killMyself()
    {
        finish();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        //保存当前Activity显示的Fragment索引
        outState.putInt(ACTIVITY_FRAGMENT_REPLACE, mReplace);
    }

    @Override
    protected void onDestroy()
    {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        try {
            InputMethodManager.class.getDeclaredMethod("windowDismissed", IBinder.class).invoke(imm,
                    getWindow().getDecorView().getWindowToken());
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onDestroy();
        this.mRxPermissions = null;
        this.mTitles = null;
        this.mFragments = null;
        this.mNavIds = null;
    }
}
