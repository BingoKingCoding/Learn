package com.king.learn.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.king.learn.di.module.UserModule;

import dagger.Component;

/**
 * <请描述这个类是干什么的>
 * Created by wwb on 2017/9/15 11:17.
 */
@ActivityScope
@Component(modules = UserModule.class,dependencies = AppComponent.class)
public interface UserComponent
{
}
