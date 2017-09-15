package com.king.learn.mvp.model.api.cache;

import com.king.learn.mvp.model.entity.UserBean;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.rx_cache2.DynamicKey;
import io.rx_cache2.EvictProvider;
import io.rx_cache2.LifeCache;
import io.rx_cache2.Reply;

/**
 * <请描述这个类是干什么的>
 * Created by wwb on 2017/9/15 14:18.
 */

public interface CommonCache
{
    @LifeCache(duration = 2, timeUnit = TimeUnit.MINUTES)
    Observable<Reply<List<UserBean>>> getUsers(Observable<List<UserBean>> users, DynamicKey idLastUserQueried, EvictProvider evictProvider);
}
