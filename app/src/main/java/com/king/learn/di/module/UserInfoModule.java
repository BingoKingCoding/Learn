package com.king.learn.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import com.king.learn.mvp.contract.UserInfoContract;
import com.king.learn.mvp.model.UserInfoModel;


@Module
public class UserInfoModule
{
    private UserInfoContract.View view;

    /**
     * 构建UserInfoModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public UserInfoModule(UserInfoContract.View view)
    {
        this.view = view;
    }

    @ActivityScope
    @Provides
    UserInfoContract.View provideUserInfoView()
    {
        return this.view;
    }

    @ActivityScope
    @Provides
    UserInfoContract.Model provideUserInfoModel(UserInfoModel model)
    {
        return model;
    }
}