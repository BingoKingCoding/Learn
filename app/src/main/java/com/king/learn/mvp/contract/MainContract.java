package com.king.learn.mvp.contract;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;

/**
 * <请描述这个类是干什么的>
 * Created by wwb on 2017/9/20 15:49.
 */

public interface MainContract
{
    //对于经常使用的关于UI的方法可以定义到BaseView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView
    {
    }

    //Model层定义接口,外部只需关心model返回的数据,无需关心内部细节,及是否使用缓存
    interface Model extends IModel
    {

    }
}
