package com.king.learn.di.component;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.king.learn.di.module.WebViewModule;

import com.king.learn.mvp.ui.activity.WebViewActivity;

@ActivityScope
@Component(modules = WebViewModule.class, dependencies = AppComponent.class)
public interface WebViewComponent
{
    void inject(WebViewActivity activity);
}