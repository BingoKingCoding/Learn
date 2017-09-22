package com.king.learn.mvp.model;

import android.os.Message;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.king.learn.mvp.contract.WelfareContract;
import com.king.learn.mvp.model.api.service.CommonService;
import com.king.learn.mvp.model.entity.DaoGankEntity;
import com.king.learn.mvp.model.entity.GankEntity;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * <请描述这个类是干什么的>
 * Created by wwb on 2017/9/22 17:04.
 */
@ActivityScope
public class WelfareModel extends BaseModel implements WelfareContract.Model
{
    @Inject
    public WelfareModel(IRepositoryManager repositoryManager)
    {
        super(repositoryManager);
    }

    @Override
    public Observable<GankEntity> getRandomGirl()
    {
        Observable<GankEntity> randomGirl = mRepositoryManager.obtainRetrofitService(CommonService.class)
                .getRandomGirl();
        return randomGirl;
    }

    @Override
    public Message addGankEntity(DaoGankEntity daoGankEntity)
    {
        return null;
    }
}
