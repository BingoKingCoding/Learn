package com.king.learn.mvp.contract;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;

/**
 * <请描述这个类是干什么的>
 * Created by adou on 2017/9/21:22:14.
 *
 * @Email:634051075@qq.com
 */

public interface SplashContract
{
    interface View extends IView{

    }
    interface Model extends IModel{
        void getSplash(String deviceId);
    }
}
