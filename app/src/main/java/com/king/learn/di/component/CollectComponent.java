package com.king.learn.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.king.learn.di.module.CollectModule;
import com.king.learn.mvp.ui.fragment.CollectFragment;

import dagger.Component;

@ActivityScope
@Component(modules = CollectModule.class, dependencies = AppComponent.class)
public interface CollectComponent
{
    void inject(CollectFragment fragment);
}