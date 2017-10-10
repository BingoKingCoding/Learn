package com.king.learn.mvp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.Preconditions;
import com.king.learn.R;
import com.king.learn.app.base.DDBaseActivity;
import com.king.learn.app.utils.FragmentUtils;
import com.king.learn.di.component.DaggerMainComponent;
import com.king.learn.di.module.MainModule;
import com.king.learn.mvp.contract.MainContract;
import com.king.learn.mvp.presenter.MainPresenter;
import com.king.learn.mvp.ui.fragment.CollectFragment;
import com.king.learn.mvp.ui.fragment.HomeFragment;
import com.king.learn.mvp.ui.fragment.UserCenterFragment;
import com.king.learn.mvp.ui.fragment.WelfareFragment;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.king.learn.app.EventBusTags.ACTIVITY_FRAGMENT_REPLACE;

public class MainActivity extends DDBaseActivity<MainPresenter> implements MainContract.View
{
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.bottomBar)
    BottomBar mBottomBar;
    @BindView(R.id.dl_layout)
    DrawerLayout dlLayout;
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
            case R.id.tab_myfavourite:
                mReplace = 2;
                break;
            case R.id.tab_mycenter:
                mReplace = 3;
                break;
        }
        mToolbarTitle.setText(mTitles.get(mReplace));
        FragmentUtils.hideAllShowFragment(mFragments.get(mReplace));

    };

    @OnClick(R.id.fl_title_menu)
    public void flTitleMenu() {
        dlLayout.openDrawer(GravityCompat.START);
    }
    @OnClick(R.id.fl_exit_app)
    public void exit() {
        exitApp();
    }

    @OnClick(R.id.fl_feedback)
    public void feedback() {
//        startActivity(new Intent(this, FeedbackActivity.class));
    }

    @OnClick(R.id.fl_about_us)
    public void aboutUs() {
//        startActivity(new Intent(this, AboutUsActivity.class));
    }

    @OnClick(R.id.fl_setting)
    public void setting() {
        showMessage("设置暂时还没有开发哦");
    }

    @OnClick(R.id.fl_theme_color)
    public void themeColor() {
        showMessage("个性换肤暂时还没有开发");
    }

    @Override
    public void setupActivityComponent(AppComponent appComponent)
    {
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
        mIsExitApp = true;
//        StatusBarUtil.setColor(this, DDResourcesUtil.getColor(R.color.colorPrimary));
        if (mTitles == null)
        {
            mTitles = new ArrayList<>();
            mTitles.add(R.string.title_home);
            mTitles.add(R.string.title_dashboard);
            mTitles.add(R.string.title_favourite);
            mTitles.add(R.string.title_mecenter);
        }
        if (mNavIds == null)
        {
            mNavIds = new ArrayList<>();
            mNavIds.add(R.id.tab_home);
            mNavIds.add(R.id.tab_dashboard);
            mNavIds.add(R.id.tab_myfavourite);
            mNavIds.add(R.id.tab_mycenter);
        }
        HomeFragment homeFragment;
        WelfareFragment welfareFragment;
        CollectFragment collectFragment;
        UserCenterFragment userCenterFragment;
        if (savedInstanceState == null)
        {
            homeFragment = HomeFragment.newInstance();
            welfareFragment = WelfareFragment.newInstance();
            collectFragment = CollectFragment.newInstance();
            userCenterFragment = UserCenterFragment.newInstance();
        }else {
            mReplace = savedInstanceState.getInt(ACTIVITY_FRAGMENT_REPLACE);
            FragmentManager fm = getSupportFragmentManager();
            homeFragment = (HomeFragment) FragmentUtils.findFragment(fm,HomeFragment.class);
            welfareFragment = (WelfareFragment) FragmentUtils.findFragment(fm,WelfareFragment.class);
            collectFragment = (CollectFragment) FragmentUtils.findFragment(fm,CollectFragment.class);
            userCenterFragment = (UserCenterFragment) FragmentUtils.findFragment(fm,UserCenterFragment.class);
        }

        if (mFragments == null)
        {
            mFragments = new ArrayList<>();
            mFragments.add(homeFragment);
            mFragments.add(welfareFragment);
            mFragments.add(collectFragment);
            mFragments.add(userCenterFragment);
        }
        FragmentUtils.addFragments(getSupportFragmentManager(),mFragments,R.id.main_frame,0);
        mBottomBar.setOnTabSelectListener(mOnTabSelectListener);
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
        this.mTitles = null;
        this.mFragments = null;
        this.mNavIds = null;
    }

    @Override
    public void onBackPressed() {
        if (dlLayout.isDrawerOpen(GravityCompat.START)) {
            dlLayout.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }
    }

}
