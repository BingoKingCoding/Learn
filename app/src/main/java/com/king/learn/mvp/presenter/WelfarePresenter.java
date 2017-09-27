package com.king.learn.mvp.presenter;

import android.app.Application;
import android.os.Message;

import com.blankj.utilcode.util.TimeUtils;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.king.learn.app.utils.RxUtils;
import com.king.learn.mvp.contract.WelfareContract;
import com.king.learn.mvp.model.entity.DaoGankEntity;
import com.king.learn.mvp.model.entity.GankEntity;

import org.simple.eventbus.EventBus;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;

/**
 * <请描述这个类是干什么的>
 * Created by wwb on 2017/9/22 17:08.
 */
@ActivityScope
public class WelfarePresenter extends BasePresenter<WelfareContract.Model,WelfareContract.View>
{
    private RxErrorHandler mErrorHandler;
    private Application mApplication;
    private ImageLoader mImageLoader;
    private AppManager mAppManager;
    private DaoGankEntity daoGankEntity;
    @Inject
    public WelfarePresenter(WelfareContract.Model model, WelfareContract.View rootView
            , RxErrorHandler handler, Application application
            , ImageLoader imageLoader, AppManager appManager){
        super(model, rootView);
        this.mErrorHandler = handler;
        this.mApplication = application;
        this.mImageLoader = imageLoader;
        this.mAppManager = appManager;
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
    }


    public void requestData(boolean pullToRefresh){
         mModel.getRandomGirl()
                 .subscribeOn(Schedulers.io())
                 .retryWhen(new RetryWithDelay(3,2))
                 .doOnSubscribe(disposable ->
                 {
                     if (pullToRefresh)
                         mRootView.showLoading();
                     else
                         mRootView.startLoadMore();
                 })
                 .subscribeOn(AndroidSchedulers.mainThread())
                 .observeOn(AndroidSchedulers.mainThread())
                 .doAfterTerminate(() ->
                 {
                     if (pullToRefresh)
                         mRootView.hideLoading();
                     else
                         mRootView.endLoadMore();
                 }).compose(RxUtils.bindToLifecycle(mRootView))
                 .subscribe(new ErrorHandleSubscriber<GankEntity>(mErrorHandler)
                 {
                     @Override
                     public void onNext(@NonNull GankEntity gankEntity)
                     {
                         if (pullToRefresh)
                         {
                             mRootView.setNewData(gankEntity.results);
                         }else {
                             mRootView.setAddData(gankEntity.results);
                         }
                     }
                 });
    }

    public void addToFavorites(GankEntity.ResultsBean entity){
        DaoGankEntity daoGankEntity = entityToDao(entity);
        Message message = mModel.addGankEntity(daoGankEntity);
        String a = null;
        switch (message.what)
        {
            case 101:
                a = "该图片已经收藏过了";
                break;
            case 102:
                a = "收藏成功";
                EventBus.getDefault().post(new Object(),"meizi");
                break;
            case 103:
                a = "收藏失败";
                break;
        }
        mRootView.showMessage(a);
    }

    private DaoGankEntity entityToDao(GankEntity.ResultsBean entity){
        String str = TimeUtils.getNowString();//获取当前时间
        if (daoGankEntity == null){
            daoGankEntity = new DaoGankEntity();
        }
        daoGankEntity._id = entity._id;
        daoGankEntity.createdAt = entity.createdAt;
        daoGankEntity.desc = entity.desc;
        daoGankEntity.publishedAt = entity.publishedAt;
        daoGankEntity.source = entity.source;
        daoGankEntity.type = entity.type;
        daoGankEntity.url = entity.url;
        daoGankEntity.used = entity.used;
        daoGankEntity.who = entity.who;
        daoGankEntity.addtime =str;
        return daoGankEntity;
    }


}
