package com.king.learn.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.king.learn.di.module.CategoryModule;
import com.king.learn.mvp.ui.fragment.CategoryFragment;

import dagger.Component;

/**
 * <请描述这个类是干什么的>
 * Created by wwb on 2017/9/21 15:53.
 */
@ActivityScope
@Component(modules = CategoryModule.class,dependencies = AppComponent.class)
public interface CategoryComponent
{
    void inject(CategoryFragment fragment);
}
