package com.king.learn.mvp.model;

import android.app.Application;

import com.blankj.utilcode.util.EmptyUtils;
import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.king.learn.app.GreenDaoHelper;
import com.king.learn.app.greendao.DaoGankEntityDao;
import com.king.learn.app.utils.CategoryType;
import com.king.learn.mvp.contract.MeiziContract;
import com.king.learn.mvp.model.entity.DaoGankEntity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;


@ActivityScope
public class MeiziModel extends BaseModel implements MeiziContract.Model
{
    private Gson mGson;
    private Application mApplication;
    private List<DaoGankEntity> mDaoGankEntityList;
    private ArrayList<String> mImages;
    @Inject
    public MeiziModel(IRepositoryManager repositoryManager, Gson gson, Application application)
    {
        super(repositoryManager);
        this.mGson = gson;
        this.mApplication = application;
        mImages = new ArrayList<>();
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public List<DaoGankEntity> getEntity()
    {
        mDaoGankEntityList = GreenDaoHelper.getDaoSession().getDaoGankEntityDao()
                .queryBuilder()
                .where(DaoGankEntityDao.Properties.Type.eq(CategoryType.GIRLS_STR))
                .orderDesc(DaoGankEntityDao.Properties.Addtime)
                .list();
        return mDaoGankEntityList;
    }

    @Override
    public ArrayList<String> getImages()
    {
        if (EmptyUtils.isNotEmpty(mDaoGankEntityList))
        {
            mImages.clear();
            for (DaoGankEntity daoGankEntity : mDaoGankEntityList)
            {
                mImages.add(daoGankEntity.getUrl());
            }
            return mImages;
        }
        return new ArrayList<>();
    }
}