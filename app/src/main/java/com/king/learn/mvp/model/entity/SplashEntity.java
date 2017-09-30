package com.king.learn.mvp.model.entity;

import java.util.List;

/**
 * <请描述这个类是干什么的>
 * Created by wwb on 2017/9/30 14:43.
 */

public class SplashEntity
{
    /**
     * <p>http://static.owspace.com/static/picture_list.txt?client=android&version=1.3.0&time=1467864021&device_id=866963027059338</p>
     * status : ok
     * time : 1501498961
     * images : ["https://img.owspace.com/Public/uploads/Picture/2017-07-31/597f0e4ec4bc4.jpg","https://img.owspace.com/Public/uploads/Picture/2017-07-31/597f0e4a932fd.jpg","https://img.owspace.com/Public/uploads/Picture/2017-06-18/594656c50d16a.jpg","https://img.owspace.com/Public/uploads/Picture/2017-06-18/5946568145a40.jpg","https://img.owspace.com/Public/uploads/Picture/2017-06-18/594656689bcf1.jpg","https://img.owspace.com/Public/uploads/Picture/2017-06-18/5946566115a44.jpg","https://img.owspace.com/Public/uploads/Picture/2017-03-21/58d0ad39741e4.jpg","https://img.owspace.com/Public/uploads/Picture/2017-03-09/58c05a7b47737.jpg"]
     * count : 8
     */

    private String status;
    private int time;
    private int count;
    private List<String> images;

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public int getTime()
    {
        return time;
    }

    public void setTime(int time)
    {
        this.time = time;
    }

    public int getCount()
    {
        return count;
    }

    public void setCount(int count)
    {
        this.count = count;
    }

    public List<String> getImages()
    {
        return images;
    }

    public void setImages(List<String> images)
    {
        this.images = images;
    }
}
