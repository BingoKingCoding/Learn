package com.king.learn.mvp.presenter;

import android.app.Application;

import com.blankj.utilcode.util.NetworkUtils;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.king.learn.app.utils.OkHttpDownloader;
import com.king.learn.mvp.contract.SplashContract;
import com.king.learn.mvp.model.entity.SplashEntity;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;
import timber.log.Timber;


@ActivityScope
public class SplashPresenter extends BasePresenter<SplashContract.Model, SplashContract.View>
{
    private RxErrorHandler mErrorHandler;
    private Application mApplication;
    private ImageLoader mImageLoader;
    private AppManager mAppManager;
    @Inject
    public SplashPresenter(SplashContract.Model model, SplashContract.View rootView
            , RxErrorHandler handler, Application application
            , ImageLoader imageLoader, AppManager appManager)
    {
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

    public void getADImages(){
        mRootView.delaySplash(mModel.getAllAD());
    }

    public void requestSplash(String deviceId){
        mModel.requestSplash(deviceId)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3,2))
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ErrorHandleSubscriber<SplashEntity>(mErrorHandler)
                {
                    @Override
                    public void onNext(@NonNull SplashEntity splashEntity)
                    {
                        if (NetworkUtils.isWifiConnected())
                        {
                            if (splashEntity != null)
                            {
                                List<String> imgs = splashEntity.getImages();
                                for (String url : imgs)
                                {
                                    OkHttpDownloader.downloadADImage(url);
                                }
                            }
                        } else
                        {
                            Timber.i("不是WIFI环境,就不去下载图片了");
                        }
                    }
                });
    }

}
