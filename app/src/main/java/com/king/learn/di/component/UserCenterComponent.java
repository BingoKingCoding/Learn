package com.king.learn.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.king.learn.di.module.UserCenterModule;
import com.king.learn.mvp.ui.fragment.UserCenterFragment;

import dagger.Component;

@ActivityScope
@Component(modules = UserCenterModule.class, dependencies = AppComponent.class)
public interface UserCenterComponent
{
    void inject(UserCenterFragment fragment);
}