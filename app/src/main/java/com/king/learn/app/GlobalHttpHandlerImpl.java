package com.king.learn.app;

import android.content.Context;
import android.text.TextUtils;

import com.jess.arms.http.GlobalHttpHandler;
import com.jess.arms.http.RequestInterceptor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import timber.log.Timber;

/**
 * <请描述这个类是干什么的>
 * Created by wwb on 2017/9/15 17:13.
 */

public class GlobalHttpHandlerImpl implements GlobalHttpHandler
{
    private Context mContext;
    public GlobalHttpHandlerImpl(Context context){
        this.mContext = context ;
    }

    @Override
    public Response onHttpResultResponse(String httpResult, Interceptor.Chain chain, Response response)
    {
         /* 这里可以先客户端一步拿到每一次http请求的结果,可以解析成json,做一些操作,如检测到token过期后
                       重新请求token,并重新执行请求 */
        try {
            if (!TextUtils.isEmpty(httpResult) && RequestInterceptor.isJson(response.body().contentType())) {
                JSONArray array = new JSONArray(httpResult);
                JSONObject object = (JSONObject) array.get(0);
                String login = object.getString("login");
                String avatar_url = object.getString("avatar_url");
                Timber.w("Result ------> " + login + "    ||   Avatar_url------> " + avatar_url);
            }

        } catch (JSONException e) {
            e.printStackTrace();
            return response;
        }


                 /* 这里如果发现token过期,可以先请求最新的token,然后在拿新的token放入request里去重新请求
                    注意在这个回调之前已经调用过proceed,所以这里必须自己去建立网络请求,如使用okhttp使用新的request去请求
                    create a new request and modify it accordingly using the new token
                    Request newRequest = chain.request().newBuilder().header("token", newToken)
                                         .build();

                    retry the request

                    response.body().close();
                    如果使用okhttp将新的请求,请求成功后,将返回的response  return出去即可
                    如果不需要返回新的结果,则直接把response参数返回出去 */
        return response;
    }

    @Override
    public Request onHttpRequestBefore(Interceptor.Chain chain, Request request)
    {
        /* 如果需要再请求服务器之前做一些操作,则重新返回一个做过操作的的request如增加header,不做操作则直接返回request参数
                       return chain.request().newBuilder().header("token", tokenId)
                              .build(); */
        return request;
    }
}
