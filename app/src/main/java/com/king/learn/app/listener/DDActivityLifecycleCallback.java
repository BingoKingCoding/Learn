package com.king.learn.app.listener;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;

/**
 */

public interface DDActivityLifecycleCallback extends Application.ActivityLifecycleCallbacks
{
    void onActivityRestoreInstanceState(Activity activity, Bundle savedInstanceState);

    void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data);
}
