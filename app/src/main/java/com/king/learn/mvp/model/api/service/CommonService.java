/*
  * Copyright 2017 JessYan
  *
  * Licensed under the Apache License, Version 2.0 (the "License");
  * you may not use this file except in compliance with the License.
  * You may obtain a copy of the License at
  *
  *      http://www.apache.org/licenses/LICENSE-2.0
  *
  * Unless required by applicable law or agreed to in writing, software
  * distributed under the License is distributed on an "AS IS" BASIS,
  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  * See the License for the specific language governing permissions and
  * limitations under the License.
  */
package com.king.learn.mvp.model.api.service;

import com.king.learn.mvp.model.entity.GankEntity;
import com.king.learn.mvp.model.entity.SplashEntity;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * 存放通用的一些API
 */
public interface CommonService
{
    @GET("api/data/{type}/{pageSize}/{page}")
    Observable<GankEntity> gank(@Path("type") String type, @Path("pageSize") int pageSize, @Path("page") String page);

    // 随机获取一个妹子
    @GET("api/random/data/福利/1")
    Observable<GankEntity> getRandomGirl();
    // 加上 Domain-Name header
    @Headers({"Domain-Name: adimages"})
    @GET("static/picture_list.txt")
    Observable<SplashEntity> requestSplash(@Query("client") String client, @Query("version") String version, @Query("time") Long time, @Query("device_id") String deviceId);

}
