package com.king.learn.mvp.model.entity;

/**
 * <请描述这个类是干什么的>
 * Created by wwb on 2017/9/15 14:15.
 */

public class UserBean
{
    private final int id;
    private final String login;
    private final String avatar_url;

    public UserBean(int id, String login, String avatar_url) {
        this.id = id;
        this.login = login;
        this.avatar_url = avatar_url;
    }

    public String getAvatarUrl() {
        if (avatar_url.isEmpty()) return avatar_url;
        return avatar_url.split("\\?")[0];
    }


    public int getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    @Override public String toString() {
        return "id -> " + id + " login -> " + login;
    }
}
