package com.king.learn.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.king.learn.app.utils.DDFileUtil;
import com.king.learn.app.utils.OkHttpDownloader;
import com.king.learn.mvp.contract.PhotoViewContract;

import javax.inject.Inject;


@ActivityScope
public class PhotoViewModel extends BaseModel implements PhotoViewContract.Model
{
    private Gson mGson;
    private Application mApplication;

    @Inject
    public PhotoViewModel(IRepositoryManager repositoryManager, Gson gson, Application application)
    {
        super(repositoryManager);
        this.mGson = gson;
        this.mApplication = application;
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public void savePic(String url,OkHttpDownloader.DownloadListener listener)
    {
        OkHttpDownloader.downloadImage(url,DDFileUtil.IMAGE_CACHE_DIR,listener);
    }
}