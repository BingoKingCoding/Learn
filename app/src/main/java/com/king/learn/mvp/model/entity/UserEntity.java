package com.king.learn.mvp.model.entity;

import java.io.Serializable;

/**
 * <请描述这个类是干什么的>
 * Created by wwb on 2017/10/13 17:23.
 */

public class UserEntity implements Serializable
{
    static final long serialVersionUID = 0;
    private String user_id = ""; // 用户id
    private String luck_num = ""; // 靓号
    private String nick_name; // 昵称
    private String signature; // 签名
    private int sex; // 0-未知，1-男，2-女
    private int login_type; //0：微信；1：QQ；2：手机；3：微博 ;4 : 游客登录
    private String city; // 所在城市
    private String province;//所在省份
    private String emotional_state;//情感状态
    private String birthday;//生日
    private int is_authentication;// "0",//是否认证 0指未认证  1指待审核 2指认证 3指审核不通
    private String job;//职业
    private String head_image; // 头像
    private int user_level; // 用户等级
    private String v_type;// 认证类型 0 未认证 1 普通 2企业
    private String v_icon;// 认证图标
    private String v_explain;// 认证说明
    private String home_url;// 主页

}
