package com.king.learn.mvp.ui.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.utils.ArmsUtils;
import com.king.learn.app.glide.GlideImageConfig;

import java.util.List;

import uk.co.senab.photoview.PhotoView;

/**
 */

public class PhotoViewPageAdapter extends DDPagerAdapter<String>
{
    public AppComponent mAppComponent;
    public ImageLoader mImageLoader;
    public PhotoViewPageAdapter(List<String> listModel, Activity activity)
    {
        super(listModel, activity);
        mAppComponent = ArmsUtils.obtainAppComponentFromContext(activity);
        mImageLoader = mAppComponent.imageLoader();
    }

    @Override
    public View getView(ViewGroup container, int position)
    {
        String url = getData(position);
        PhotoView photoView = new PhotoView(getActivity());
        mImageLoader.loadImage(getActivity(),
                GlideImageConfig
                .builder()
                .url(url)
                .imageView(photoView)
                .build());
        return photoView;
    }
}
