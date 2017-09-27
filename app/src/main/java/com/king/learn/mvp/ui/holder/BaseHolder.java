package com.king.learn.mvp.ui.holder;

import android.content.Context;
import android.view.View;

import com.chad.library.adapter.base.BaseViewHolder;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.utils.ArmsUtils;

/**
 * <item中有使用到图片加载的就实现本类>
 * Created by wwb on 2017/9/27 14:41.
 */

public class BaseHolder extends BaseViewHolder
{
    public AppComponent mAppComponent;
    public ImageLoader mImageLoader;//用于加载图片的管理类,默认使用glide,使用策略模式,可替换框架
    public BaseHolder(View view)
    {
        super(view);
        mAppComponent = ArmsUtils.obtainAppComponentFromContext(itemView.getContext());
        mImageLoader = mAppComponent.imageLoader();
    }

    public Context getContext(){
        return mAppComponent.appManager().getCurrentActivity() == null
                ? mAppComponent.application() : mAppComponent.appManager().getCurrentActivity();
    }
}
