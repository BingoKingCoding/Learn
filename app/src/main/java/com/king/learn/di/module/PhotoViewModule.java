package com.king.learn.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import com.king.learn.mvp.contract.PhotoViewContract;
import com.king.learn.mvp.model.PhotoViewModel;


@Module
public class PhotoViewModule
{
    private PhotoViewContract.View view;

    /**
     * 构建PhotoViewModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public PhotoViewModule(PhotoViewContract.View view)
    {
        this.view = view;
    }

    @ActivityScope
    @Provides
    PhotoViewContract.View providePhotoViewView()
    {
        return this.view;
    }

    @ActivityScope
    @Provides
    PhotoViewContract.Model providePhotoViewModel(PhotoViewModel model)
    {
        return model;
    }
}