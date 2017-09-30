package com.king.learn.mvp.presenter;

import android.app.Application;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.king.learn.app.utils.CollectionUtil;
import com.king.learn.app.utils.OkHttpDownloader;
import com.king.learn.mvp.contract.PhotoViewContract;

import java.io.IOException;
import java.util.ArrayList;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;


@ActivityScope
public class PhotoViewPresenter extends BasePresenter<PhotoViewContract.Model, PhotoViewContract.View>
{
    private RxErrorHandler mErrorHandler;
    private Application mApplication;
    private ImageLoader mImageLoader;
    private AppManager mAppManager;

    @Inject
    public PhotoViewPresenter(PhotoViewContract.Model model, PhotoViewContract.View rootView
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

    public void savePic(ArrayList<String> listImageUrl, int selectPosition)
    {
        String url = CollectionUtil.get(listImageUrl, selectPosition);
        mModel.savePic(url, new OkHttpDownloader.DownloadListener()
        {
            @Override
            public void onSuccess()
            {
                mRootView.showMessage("图片保存成功");
            }

            @Override
            public void onFailure(IOException e)
            {
                mRootView.showMessage("图片保存失败");
            }
        });
    }

}
