package com.king.learn.mvp.model.api.service;

import com.king.learn.mvp.model.entity.UserBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

/**
 * <存放关于用户的一些api>
 *
 * Created by wwb on 2017/9/15 14:20.
 */

public interface UserService
{
    String HEADER_API_VERSION = "Accept: application/vnd.github.v3+json";

    @Headers({HEADER_API_VERSION})
    @GET("/users")
    Observable<List<UserBean>> getUsers(@Query("since") int lastIdQueried, @Query("per_page") int perPage);
}
