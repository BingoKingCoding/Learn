package com.king.learn.di.module;

import com.jess.arms.di.scope.ActivityScope;
import com.king.learn.mvp.contract.SplashContract;
import com.king.learn.mvp.model.SplashModel;

import dagger.Module;
import dagger.Provides;


@Module
public class SplashModule
{
    private SplashContract.View view;

    /**
     * 构建SplashModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public SplashModule(SplashContract.View view)
    {
        this.view = view;
    }

    @ActivityScope
    @Provides
    SplashContract.View provideSplashView()
    {
        return this.view;
    }

    @ActivityScope
    @Provides
    SplashContract.Model provideSplashModel(SplashModel model)
    {
        return model;
    }
}