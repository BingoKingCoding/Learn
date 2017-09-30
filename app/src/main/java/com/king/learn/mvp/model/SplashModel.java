package com.king.learn.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.king.learn.app.utils.DDFileUtil;
import com.king.learn.mvp.contract.SplashContract;
import com.king.learn.mvp.model.api.Api;
import com.king.learn.mvp.model.api.service.CommonService;
import com.king.learn.mvp.model.entity.SplashEntity;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import me.jessyan.retrofiturlmanager.RetrofitUrlManager;

/**
 * <请描述这个类是干什么的>
 * Created by adou on 2017/9/21:22:19.
 *
 * @Email:634051075@qq.com
 */
@ActivityScope
public class SplashModel extends BaseModel implements SplashContract.Model
{
    private Gson mGson;
    private Application mApplication;

    @Inject
    public SplashModel(IRepositoryManager repositoryManager, Gson gson, Application application)
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
    public Observable<SplashEntity> requestSplash(String deviceId)
    {
        String client = "android";
        String version = "1.3.0";
        Long time = System.currentTimeMillis() / 1000;
        RetrofitUrlManager.getInstance().putDomain("adimages", Api.ADURL);
        return mRepositoryManager.obtainRetrofitService(CommonService.class)
                .requestSplash(client,version,time,deviceId);
    }

    @Override
    public List<String> getAllAD()
    {
        return DDFileUtil.getAllAD();
    }
}
