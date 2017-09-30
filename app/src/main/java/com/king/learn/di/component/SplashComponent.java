package com.king.learn.di.component;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.king.learn.di.module.SplashModule;

import com.king.learn.mvp.ui.activity.SplashActivity;

@ActivityScope
@Component(modules = SplashModule.class, dependencies = AppComponent.class)
public interface SplashComponent
{
    void inject(SplashActivity activity);
}