package com.king.learn.app.listener;

import android.app.Activity;
import android.view.KeyEvent;

/**
 */

public interface DDActivityDispatchKeyEventCallback
{
    boolean dispatchKeyEvent(Activity activity, KeyEvent event);
}
