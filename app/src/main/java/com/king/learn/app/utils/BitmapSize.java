package com.king.learn.app.utils;

/**
 * <请描述这个类是干什么的>
 */

public class BitmapSize
{
    public static final BitmapSize ZERO = new BitmapSize(0, 0);

    private final int width;
    private final int height;

    public BitmapSize(int width, int height)
    {
        this.width = width;
        this.height = height;
    }

    /**
     * Scales down dimensions in <b>sampleSize</b> times. Returns new object.
     */
    public BitmapSize scaleDown(int sampleSize)
    {
        return new BitmapSize(width / sampleSize, height / sampleSize);
    }

    /**
     * Scales dimensions according to incoming scale. Returns new object.
     */
    public BitmapSize scale(float scale)
    {
        return new BitmapSize((int) (width * scale), (int) (height * scale));
    }

    public int getWidth()
    {
        return width;
    }

    public int getHeight()
    {
        return height;
    }

    @Override
    public String toString()
    {
        return "_" + width + "_" + height;
    }
}
