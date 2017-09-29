package com.king.learn.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.king.learn.app.GreenDaoHelper;
import com.king.learn.app.greendao.DaoGankEntityDao;
import com.king.learn.mvp.contract.DetailContract;
import com.king.learn.mvp.model.api.service.CommonService;
import com.king.learn.mvp.model.entity.DaoGankEntity;
import com.king.learn.mvp.model.entity.GankEntity;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;


@ActivityScope
public class DetailModel extends BaseModel implements DetailContract.Model
{
    private Gson mGson;
    private Application mApplication;

    @Inject
    public DetailModel(IRepositoryManager repositoryManager, Gson gson, Application application)
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
    public Observable<GankEntity> getRandomGirl()
    {
        return mRepositoryManager.obtainRetrofitService(CommonService.class).getRandomGirl();
    }

    @Override
    public List<DaoGankEntity> queryById(String id)
    {
        return GreenDaoHelper
                .getDaoSession()
                .getDaoGankEntityDao()
                .queryBuilder()
                .where(DaoGankEntityDao.Properties._id.eq(id))
                .list();
    }

    @Override
    public void removeByid(String id)
    {
            GreenDaoHelper.getDaoSession().getDaoGankEntityDao()
                    .queryBuilder().where(DaoGankEntityDao.Properties._id.eq(id))
                    .buildDelete().executeDeleteWithoutDetachingEntities();
    }

    @Override
    public void addGankEntity(DaoGankEntity entity)
    {
            GreenDaoHelper.getDaoSession().getDaoGankEntityDao().insert(entity);
    }
}