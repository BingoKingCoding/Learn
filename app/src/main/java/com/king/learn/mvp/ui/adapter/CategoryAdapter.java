package com.king.learn.mvp.ui.adapter;

import android.view.View;

import com.jess.arms.base.BaseHolder;
import com.jess.arms.base.DefaultAdapter;
import com.king.learn.R;
import com.king.learn.mvp.model.entity.GankEntity;
import com.king.learn.mvp.ui.holder.CategoryItemHolder;

import java.util.List;

/**
 * <请描述这个类是干什么的>
 * Created by wwb on 2017/9/21 12:04.
 */

public class CategoryAdapter extends DefaultAdapter<GankEntity.ResultsBean>
{
    public CategoryAdapter(List<GankEntity.ResultsBean> infos)
    {
        super(infos);
    }

    @Override
    public BaseHolder<GankEntity.ResultsBean> getHolder(View v, int viewType)
    {
        return new CategoryItemHolder(v);
    }

    @Override
    public int getLayoutId(int viewType)
    {
        return R.layout.item_android;
    }
}
