package com.king.learn.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.king.learn.di.module.WelfareModule;
import com.king.learn.mvp.ui.fragment.WelfareFragment;

import dagger.Component;

/**
 * <请描述这个类是干什么的>
 * Created by wwb on 2017/9/25 16:02.
 */
@ActivityScope
@Component(modules = WelfareModule.class,dependencies = AppComponent.class)
public interface WelfareComponent
{
    void inject(WelfareFragment fragment);
}
