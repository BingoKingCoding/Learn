package com.king.learn.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.king.learn.di.module.DetailModule;
import com.king.learn.mvp.ui.activity.DetailActivity;

import dagger.Component;

@ActivityScope
@Component(modules = DetailModule.class, dependencies = AppComponent.class)
public interface DetailComponent
{
    void inject(DetailActivity activity);
}