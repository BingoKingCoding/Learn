package com.king.learn.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.king.learn.di.module.ArticleModule;
import com.king.learn.mvp.ui.fragment.ArticleFragment;

import dagger.Component;

@ActivityScope
@Component(modules = ArticleModule.class, dependencies = AppComponent.class)
public interface ArticleComponent
{
    void inject(ArticleFragment fragment);
}