package com.king.learn.di.module;

import com.jess.arms.di.scope.ActivityScope;
import com.king.learn.mvp.contract.HomeContract;
import com.king.learn.mvp.model.HomeModel;

import dagger.Module;
import dagger.Provides;

/**
 * <请描述这个类是干什么的>
 * Created by wwb on 2017/9/20 17:46.
 */
@Module
public class HomeModule
{
    private HomeContract.View view;

    /**
     * 构建HomeModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public HomeModule(HomeContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    HomeContract.View provideHomeView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    HomeContract.Model provideHomeModel(HomeModel model) {
        return model;
    }
}
