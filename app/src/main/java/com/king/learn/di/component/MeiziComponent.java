package com.king.learn.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.king.learn.di.module.MeiziModule;
import com.king.learn.mvp.ui.fragment.MeiziFragment;

import dagger.Component;

@ActivityScope
@Component(modules = MeiziModule.class, dependencies = AppComponent.class)
public interface MeiziComponent
{
    void inject(MeiziFragment fragment);
}