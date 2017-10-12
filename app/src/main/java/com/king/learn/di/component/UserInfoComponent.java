package com.king.learn.di.component;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.king.learn.di.module.UserInfoModule;

import com.king.learn.mvp.ui.activity.UserInfoActivity;

@ActivityScope
@Component(modules = UserInfoModule.class, dependencies = AppComponent.class)
public interface UserInfoComponent
{
    void inject(UserInfoActivity activity);
}