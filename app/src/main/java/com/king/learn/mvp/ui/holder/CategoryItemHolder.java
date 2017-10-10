package com.king.learn.mvp.ui.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jess.arms.base.BaseHolder;
import com.king.learn.R;
import com.king.learn.app.utils.CategoryType;
import com.king.learn.mvp.model.entity.GankEntity;

import butterknife.BindView;

/**
 * <请描述这个类是干什么的>
 * Created by wwb on 2017/9/21 12:06.
 */

public class CategoryItemHolder extends BaseHolder<GankEntity.ResultsBean>
{
    @BindView(R.id.ivImage)
    ImageView ivImage;
    @BindView(R.id.tvDesc)
    TextView tvDesc;
    @BindView(R.id.tvAuthor)
    TextView tvAuthor;
    @BindView(R.id.tvDate)
    TextView tvDate;

    public CategoryItemHolder(View itemView)
    {
        super(itemView);
    }

    @Override
    public void setData(GankEntity.ResultsBean data, int position)
    {
        tvDate.setText(data.publishedAt);
        tvAuthor.setText(data.who);
        tvDesc.setText(data.desc);
        if (data.type.equals(CategoryType.ANDROID_STR)){
            ivImage.setImageResource(R.mipmap.ic_android);
        }else  if (data.type.equals(CategoryType.IOS_STR)){
            ivImage.setImageResource(R.mipmap.ic_apple);
        }else  if (data.type.equals(CategoryType.QIAN_STR)){
            ivImage.setImageResource(R.mipmap.html);
        }
    }
}
