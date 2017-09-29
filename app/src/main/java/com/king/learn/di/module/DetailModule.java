package com.king.learn.di.module;

import com.jess.arms.di.scope.ActivityScope;
import com.king.learn.mvp.contract.DetailContract;
import com.king.learn.mvp.model.DetailModel;

import dagger.Module;
import dagger.Provides;


@Module
public class DetailModule
{
    private DetailContract.View view;

    /**
     * 构建DetailModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public DetailModule(DetailContract.View view)
    {
        this.view = view;
    }

    @ActivityScope
    @Provides
    DetailContract.View provideDetailView()
    {
        return this.view;
    }

    @ActivityScope
    @Provides
    DetailContract.Model provideDetailModel(DetailModel model)
    {
        return model;
    }
}