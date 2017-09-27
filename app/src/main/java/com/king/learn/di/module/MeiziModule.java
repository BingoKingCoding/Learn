package com.king.learn.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import com.king.learn.mvp.contract.MeiziContract;
import com.king.learn.mvp.model.MeiziModel;


@Module
public class MeiziModule
{
    private MeiziContract.View view;

    /**
     * 构建MeiziModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public MeiziModule(MeiziContract.View view)
    {
        this.view = view;
    }

    @ActivityScope
    @Provides
    MeiziContract.View provideMeiziView()
    {
        return this.view;
    }

    @ActivityScope
    @Provides
    MeiziContract.Model provideMeiziModel(MeiziModel model)
    {
        return model;
    }
}