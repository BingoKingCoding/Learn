package com.king.learn.mvp.contract;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.king.learn.mvp.model.entity.SplashEntity;

import java.util.List;

import io.reactivex.Observable;

/**
 * <请描述这个类是干什么的>
 * Created by adou on 2017/9/21:22:14.
 *
 * @Email:634051075@qq.com
 */

public interface SplashContract
{
    interface View extends IView{
        //申请权限
        void delaySplash(List<String> picList);
    }
    interface Model extends IModel{
        Observable<SplashEntity> requestSplash(String deviceId);
        List<String> getAllAD();
    }
}
