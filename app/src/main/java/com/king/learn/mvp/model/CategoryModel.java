package com.king.learn.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.king.learn.mvp.contract.CategoryContract;
import com.king.learn.mvp.model.api.service.CommonService;
import com.king.learn.mvp.model.entity.GankEntity;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * <请描述这个类是干什么的>
 * Created by wwb on 2017/9/20 18:02.
 */
@ActivityScope
public class CategoryModel extends BaseModel implements CategoryContract.Model
{
    private Gson mGson;
    private Application mApplication;
    public static final int USERS_PER_PAGESIZE = 10;
    @Inject
    public CategoryModel(IRepositoryManager repositoryManager, Gson gson, Application application)
    {
        super(repositoryManager);
        this.mGson = gson;
        this.mApplication = application;
    }

    @Override
    public Observable<GankEntity> gank(String type, String page)
    {
        return mRepositoryManager.obtainRetrofitService(CommonService.class)
                .gank(type, USERS_PER_PAGESIZE, page);
    }
}
