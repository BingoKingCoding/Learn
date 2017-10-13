package com.king.learn.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import com.king.learn.mvp.contract.WebViewContract;
import com.king.learn.mvp.model.WebViewModel;


@Module
public class WebViewModule
{
    private WebViewContract.View view;

    /**
     * 构建WebViewModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public WebViewModule(WebViewContract.View view)
    {
        this.view = view;
    }

    @ActivityScope
    @Provides
    WebViewContract.View provideWebViewView()
    {
        return this.view;
    }

    @ActivityScope
    @Provides
    WebViewContract.Model provideWebViewModel(WebViewModel model)
    {
        return model;
    }
}