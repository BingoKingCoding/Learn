package com.king.learn.mvp.model.api;

/**
 * <请描述这个类是干什么的>
 * Created by wwb on 2017/9/15 14:14.
 */

public interface Api
{
    public static final int HTTP_CACHE_SIZE = 20 * 1024 * 1024;
    public static final int HTTP_CONNECT_TIMEOUT = 15 * 1000;
    public static final int HTTP_READ_TIMEOUT = 20 * 1000;
    String APP_DOMAIN = "http://gank.io/";
    String RequestSuccess = "0";
    public static final String BASEURL = "https://api.douban.com/";
    public static final String ADURL = "http://static.owspace.com/";
    public static final String APIKEY = "0b2bdeda43b5688921839c8ecb20399b";
}
