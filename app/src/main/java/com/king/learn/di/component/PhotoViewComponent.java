package com.king.learn.di.component;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.king.learn.di.module.PhotoViewModule;

import com.king.learn.mvp.ui.activity.PhotoViewActivity;

@ActivityScope
@Component(modules = PhotoViewModule.class, dependencies = AppComponent.class)
public interface PhotoViewComponent
{
    void inject(PhotoViewActivity activity);
}