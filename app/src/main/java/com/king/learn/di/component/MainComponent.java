package com.king.learn.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.king.learn.di.module.MainModule;
import com.king.learn.mvp.ui.activity.MainActivity;

import dagger.Component;

/**
 * <请描述这个类是干什么的>
 * Created by wwb on 2017/9/20 16:54.
 */
@ActivityScope
@Component(modules = MainModule.class, dependencies = AppComponent.class)
public interface MainComponent
{
    void inject(MainActivity activity);
}
