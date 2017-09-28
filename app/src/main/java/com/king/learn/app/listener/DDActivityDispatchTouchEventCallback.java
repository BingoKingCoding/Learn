package com.king.learn.app.listener;

import android.app.Activity;
import android.view.MotionEvent;

/**
 */

public interface DDActivityDispatchTouchEventCallback
{
    boolean dispatchTouchEvent(Activity activity, MotionEvent ev);
}
