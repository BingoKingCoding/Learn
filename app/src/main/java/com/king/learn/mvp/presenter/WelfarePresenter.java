package com.king.learn.mvp.presenter;

import android.app.Application;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.king.learn.mvp.contract.WelfareContract;
import com.king.learn.mvp.model.entity.DaoGankEntity;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;

/**
 * <请描述这个类是干什么的>
 * Created by wwb on 2017/9/22 17:08.
 */
@ActivityScope
public class WelfarePresenter extends BasePresenter<WelfareContract.Model,WelfareContract.View>
{
    private RxErrorHandler mErrorHandler;
    private Application mApplication;
    private ImageLoader mImageLoader;
    private AppManager mAppManager;
    private DaoGankEntity daoGankEntity;
    @Inject
    public WelfarePresenter(WelfareContract.Model model, WelfareContract.View rootView
            , RxErrorHandler handler, Application application
            , ImageLoader imageLoader, AppManager appManager){
        super(model, rootView);
        this.mErrorHandler = handler;
        this.mApplication = application;
        this.mImageLoader = imageLoader;
        this.mAppManager = appManager;
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
    }









}
