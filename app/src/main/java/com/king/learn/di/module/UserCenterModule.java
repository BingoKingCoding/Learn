package com.king.learn.di.module;

import com.jess.arms.di.scope.ActivityScope;
import com.king.learn.mvp.contract.UserCenterContract;
import com.king.learn.mvp.model.UserCenterModel;

import dagger.Module;
import dagger.Provides;


@Module
public class UserCenterModule
{
    private UserCenterContract.View view;

    /**
     * 构建UserCenterModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public UserCenterModule(UserCenterContract.View view)
    {
        this.view = view;
    }

    @ActivityScope
    @Provides
    UserCenterContract.View provideUserCenterView()
    {
        return this.view;
    }

    @ActivityScope
    @Provides
    UserCenterContract.Model provideUserCenterModel(UserCenterModel model)
    {
        return model;
    }
}