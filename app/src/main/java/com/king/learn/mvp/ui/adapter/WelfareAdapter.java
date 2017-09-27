package com.king.learn.mvp.ui.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.king.learn.R;
import com.king.learn.app.glide.GlideImageConfig;
import com.king.learn.mvp.model.entity.GankEntity;
import com.king.learn.mvp.ui.holder.BaseHolder;

import java.util.List;

/**
 */

public class WelfareAdapter extends BaseQuickAdapter<GankEntity.ResultsBean,BaseHolder>
{
    public WelfareAdapter(@Nullable List<GankEntity.ResultsBean> data)
    {
        super(R.layout.item_girls, data);
    }

    @Override
    protected void convert(BaseHolder helper, GankEntity.ResultsBean item)
    {
        helper.mImageLoader.loadImage(helper.getContext(),
                GlideImageConfig
                .builder()
                .url(item.url)
                .imageView(helper.getView(R.id.ivImage))
                .build());
    }
}
