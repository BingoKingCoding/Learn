package com.king.learn.di.module;

import com.jess.arms.di.scope.ActivityScope;
import com.king.learn.mvp.contract.WelfareContract;
import com.king.learn.mvp.model.WelfareModel;

import dagger.Module;
import dagger.Provides;

/**
 * <请描述这个类是干什么的>
 * Created by wwb on 2017/9/25 15:54.
 */
@Module
public class WelfareModule
{
    private WelfareContract.View mView;

    public WelfareModule(WelfareContract.View mView){
        this.mView = mView;
    }

    @Provides
    @ActivityScope
    WelfareContract.View provideWelfareView(){
        return this.mView;
    }

    @ActivityScope
    @Provides
    WelfareContract.Model provideWelfareModel(WelfareModel model) {
        return model;
    }

}
