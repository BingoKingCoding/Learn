package com.king.learn.mvp.ui.widget.base;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.jess.arms.base.BaseActivity;
import com.king.learn.app.listener.ISDViewContainer;
import com.king.learn.app.utils.DDViewUtil;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 */

public class BaseAppView extends FrameLayout implements
        View.OnClickListener,
        ISDViewContainer
{

    /**
     * 是否需要注册EventBus事件
     */
    private boolean mNeedRegisterEventBus = true;
    /**
     * 是否已经注册EventBus事件
     */
    private boolean mHasRegisterEventBus = false;
    /**
     * 设置是否消费掉触摸事件，true-事件不会透过view继续往下传递
     */
    private boolean mConsumeTouchEvent = false;

    private boolean mHasOnLayout = false;
    private List<Runnable> mListLayoutRunnable;

    public BaseAppView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        baseInit();
    }

    public BaseAppView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        baseInit();
    }

    public BaseAppView(Context context)
    {
        super(context);
        baseInit();
    }

    private void baseInit()
    {
        int layoutId = onCreateContentView();
        if (layoutId != 0)
        {
            setContentView(layoutId);
        }

        onBaseInit();
    }

    /**
     * 可重写此方法返回布局id
     *
     */
    protected int onCreateContentView()
    {
        return 0;
    }

    /**
     * 基类构造方法调用的初始化方法<br>
     * 如果子类在此方法内访问子类定义属性时候直接new的属性，如：private String value = "value"，则value的值将为null
     */
    protected void onBaseInit()
    {

    }

    /**
     * 设置是否需要注册EventBus事件
     *
     * @param needRegisterEventBus
     */
    public void setNeedRegisterEventBus(boolean needRegisterEventBus)
    {
        mNeedRegisterEventBus = needRegisterEventBus;
    }

    /**
     * 设置宽度
     *
     * @param width
     */
    public BaseAppView setWidth(int width)
    {
        ViewGroup.LayoutParams params = getLayoutParams();
        if (params == null)
        {
            params = generateDefaultLayoutParams();
        }
        if (params.width != width)
        {
            params.width = width;
            setLayoutParams(params);
        }
        return this;
    }

    /**
     * 设置高度
     *
     * @param height
     */
    public BaseAppView setHeight(int height)
    {
        ViewGroup.LayoutParams params = getLayoutParams();
        if (params == null)
        {
            params = generateDefaultLayoutParams();
        }
        if (params.height != height)
        {
            params.height = height;
            setLayoutParams(params);
        }
        return this;
    }

    /**
     * 设置是否消费掉触摸事件
     *
     * @param consumeTouchEvent true-消费掉事件，事件不会透过view继续往下传递
     */
    public BaseAppView setConsumeTouchEvent(boolean consumeTouchEvent)
    {
        this.mConsumeTouchEvent = consumeTouchEvent;
        return this;
    }


    public Activity getActivity()
    {
        Context context = getContext();
        if (context instanceof Activity)
        {
            return (Activity) context;
        } else
        {
            return null;
        }
    }

    public BaseActivity getBaseActivity()
    {
        Activity activity = getActivity();
        if (activity instanceof BaseActivity)
        {
            return (BaseActivity) activity;
        } else
        {
            return null;
        }
    }

    /**
     * 设置布局
     *
     * @param layoutId 布局id
     */
    public void setContentView(int layoutId)
    {
        removeAllViews();
        LayoutInflater.from(getContext()).inflate(layoutId, this, true);
    }

    public void setContentView(View contentView)
    {
        removeAllViews();
        addView(contentView);
    }

    public void setContentView(View contentView, ViewGroup.LayoutParams params)
    {
        removeAllViews();
        addView(contentView, params);
    }


    /**
     * 把自己从父布局移除
     */
    public void removeSelf()
    {
        DDViewUtil.removeView(this);
    }


    @Subscriber
    public void onEventMainThread(Object o)
    {
    }

    @Override
    public void onClick(View v)
    {
    }

    public boolean isVisible()
    {
        return View.VISIBLE == getVisibility();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom)
    {
        super.onLayout(changed, left, top, right, bottom);
        mHasOnLayout = true;
        runLayoutRunnableIfNeed();
    }

    private void runLayoutRunnableIfNeed()
    {
        if (mListLayoutRunnable == null || mListLayoutRunnable.isEmpty())
        {
            return;
        }

        Iterator<Runnable> it = mListLayoutRunnable.iterator();
        while (it.hasNext())
        {
            Runnable r = it.next();
            r.run();
            it.remove();
        }
        mListLayoutRunnable = null;
    }

    /**
     * 如果View已经触发过onLayout方法，则Runnable对象在调用此方法的时候直接触发<br>
     * 如果View还没触发过onLayout方法，则会在第一次onLayout方法触发的时候触发Runnable对象
     *
     * @param r
     * @return true-直接执行
     */
    public boolean postLayoutRunnable(Runnable r)
    {
        if (mHasOnLayout)
        {
            r.run();
            return true;
        } else
        {
            if (mListLayoutRunnable == null)
            {
                mListLayoutRunnable = new ArrayList<>();
            }
            mListLayoutRunnable.add(r);
            return false;
        }
    }

    /**
     * 移除Runnable
     *
     * @param r
     * @return
     */
    public boolean removeLayoutRunnable(Runnable r)
    {
        if (mListLayoutRunnable == null || mListLayoutRunnable.isEmpty())
        {
            return false;
        }
        boolean result = mListLayoutRunnable.remove(r);
        if (mListLayoutRunnable.isEmpty())
        {
            mListLayoutRunnable = null;
        }
        return result;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        if (mConsumeTouchEvent)
        {
            super.onTouchEvent(event);
            return true;
        }
        return super.onTouchEvent(event);
    }


    @Override
    protected void onAttachedToWindow()
    {
        registerEventBus();
        super.onAttachedToWindow();
    }

    @Override
    protected void onDetachedFromWindow()
    {
        mHasOnLayout = false;
        if (mListLayoutRunnable != null)
        {
            mListLayoutRunnable.clear();
            mListLayoutRunnable = null;
        }

        unregisterEventBus();
        super.onDetachedFromWindow();
    }
    /**
     * 注册EventBus
     */
    public final void registerEventBus()
    {
        if (mNeedRegisterEventBus)
        {
            if (!mHasRegisterEventBus)
            {
                EventBus.getDefault().register(this);
                mHasRegisterEventBus = true;
            }
        }
    }

    /**
     * 取消注册EventBus
     */
    public final void unregisterEventBus()
    {
        if (mHasRegisterEventBus)
        {
            EventBus.getDefault().unregister(this);
            mHasRegisterEventBus = false;
        }
    }

//    public void showProgressDialog(String msg)
//    {
//        if (getBaseActivity() != null)
//        {
//            getBaseActivity().showProgressDialog(msg);
//        }
//    }

//    public void dismissProgressDialog()
//    {
//        if (getBaseActivity() != null)
//        {
//            getBaseActivity().dismissProgressDialog();
//        }
//    }

    @Override
    public void addView(int parentId, View view)
    {
        DDViewUtil.addView((ViewGroup) findViewById(parentId), view);
    }

    @Override
    public View removeView(int viewId)
    {
        View view = findViewById(viewId);
        removeView(view);
        return view;
    }

    @Override
    public void replaceView(int parentId, View child)
    {
        DDViewUtil.replaceView((ViewGroup) findViewById(parentId), child);
    }

    @Override
    public void replaceView(ViewGroup parent, View child)
    {
        DDViewUtil.replaceView(parent, child);
    }

    @Override
    public void toggleView(int parentId, View child)
    {
        DDViewUtil.toggleView((ViewGroup) findViewById(parentId), child);
    }

    @Override
    public void toggleView(ViewGroup parent, View child)
    {
        DDViewUtil.toggleView(parent, child);
    }

}
