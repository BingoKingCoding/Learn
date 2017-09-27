package com.king.learn.mvp.ui.widget.dialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;

import com.king.learn.app.utils.DDViewUtil;

/**
 * 确认框
 */

public class AppDialogConfirm extends DialogConfirm
{
    public AppDialogConfirm(@NonNull Context context)
    {
        super(context);
    }

    @Override
    protected void changeBottomButtonIfNeed()
    {
        if (tv_cancel.getVisibility() == View.VISIBLE && tv_confirm.getVisibility() == View.VISIBLE)
        {
            DDViewUtil.setMarginLeft(tv_confirm, DDViewUtil.dp2px(20));
        } else if (tv_cancel.getVisibility() == View.VISIBLE)
        {

        } else if (tv_confirm.getVisibility() == View.VISIBLE)
        {
            DDViewUtil.setMarginLeft(tv_confirm, 0);
        }
    }
}
