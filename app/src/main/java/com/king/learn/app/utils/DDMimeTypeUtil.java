package com.king.learn.app.utils;

/**
 * <请描述这个类是干什么的>
 */

public class DDMimeTypeUtil
{
    public static String getMimeType(String extString)
    {
        String mimeType = null;
        if ("jpg".equalsIgnoreCase(extString))
        {
            mimeType = "image/jpeg";
        } else if ("jpe".equalsIgnoreCase(extString))
        {
            mimeType = "image/jpeg";
        } else if ("jpeg".equalsIgnoreCase(extString))
        {
            mimeType = "image/jpeg";
        } else if ("png".equalsIgnoreCase(extString))
        {
            mimeType = "image/png";
        } else if ("gif".equalsIgnoreCase(extString))
        {
            mimeType = "image/gif";
        } else
        {

        }
        return mimeType;
    }
}
