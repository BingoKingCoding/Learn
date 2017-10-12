package com.king.learn.mvp.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
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
import com.king.learn.mvp.ui.activity.UserInfoActivity;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * <个人中心>
 * Created by wwb on 2017/10/10 16:38.
 */

public class UserCenterFragment extends BaseFragment<UserCenterPresenter> implements UserCenterContract.View
{
    @BindView(R.id.iv_userhead_me)
    CircleImageView iv_userhead_me;


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
    protected void onFragmentFirstVisible()
    {
        super.onFragmentFirstVisible();
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

    @OnClick({R.id.rili_me, R.id.tianqi_me, R.id.led_me, R.id.sdt_me, R.id.erweima_me, R.id.setting_me, R.id.fab})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rili_me:
//                startActivity(new Intent(getContext(),CalendarActivity.class));
                break;
            case R.id.tianqi_me:
//                startActivity(new Intent(getContext(), WeatherActivity.class));
                break;
            case R.id.led_me:
//                startActivity(new Intent(getContext(),LEDActivity.class));
                break;
            case R.id.sdt_me:
//                startActivity(new Intent(getContext(),FlashActivity.class));
                break;
            case R.id.erweima_me:
//                startActivity(new Intent(getContext(),ZxingActivity.class));
                break;
            case R.id.setting_me:
//                startActivity(new Intent(getContext(), SettingActivity.class));
                break;
            case R.id.fab:
                Intent intent = new Intent(getContext(), UserInfoActivity.class);
                ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
                        getActivity(),
                        iv_userhead_me,
                        getString(R.string.transition_userhead)
                );

                ActivityCompat.startActivity((Activity) getContext(),intent,optionsCompat.toBundle());
                break;
        }
    }

}
