package com.king.learn.mvp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.blankj.utilcode.util.ToastUtils;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.king.learn.R;
import com.king.learn.app.base.DDBaseActivity;
import com.king.learn.app.utils.CommonUtil;
import com.king.learn.di.component.DaggerWebViewComponent;
import com.king.learn.di.module.WebViewModule;
import com.king.learn.mvp.contract.WebViewContract;
import com.king.learn.mvp.presenter.WebViewPresenter;
import com.king.learn.mvp.ui.widget.webview.AppJsHandler;
import com.king.learn.mvp.ui.widget.webview.FullscreenHolder;
import com.king.learn.mvp.ui.widget.webview.IWebPageView;
import com.king.learn.mvp.ui.widget.webview.MyWebChromeClient;
import com.king.learn.mvp.ui.widget.webview.MyWebViewClient;
import com.king.learn.mvp.ui.widget.webview.ShareUtils;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class WebViewActivity extends DDBaseActivity<WebViewPresenter> implements WebViewContract.View,IWebPageView
{
    //加载错误时候网页
    public static final String no_network_url = "file:///android_asset/error_network.html";

    // 进度条
    ProgressBar mProgressBar;
    public WebView webView;
    // 全屏时视频加载view
    FrameLayout videoFullView;
    Toolbar mTitleToolBar;
    // 进度条是否加载到90%
    public boolean mProgress90;
    // 网页是否加载完成
    public boolean mPageFinish;
    // 加载视频相关
    private MyWebChromeClient mWebChromeClient;
    // title
    private String mTitle;
    // 网页链接
    private String mUrl;

    @Override
    public void setupActivityComponent(AppComponent appComponent)
    {
        DaggerWebViewComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .webViewModule(new WebViewModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(Bundle savedInstanceState)
    {
        return R.layout.activity_webview;
    }

    @Override
    public void initData(Bundle savedInstanceState)
    {
        getIntentData();
        initTitle();
        initWebView();
        loadUrl();
    }
    public void loadUrl(){
        if (!TextUtils.isEmpty(mUrl))
        {
            webView.loadUrl(mUrl);
        }
    }
    private void initTitle()
    {
        mProgressBar = (ProgressBar) findViewById(R.id.pb_progress);
        webView = (WebView) findViewById(R.id.webview);
        videoFullView = (FrameLayout) findViewById(R.id.video_fullView);
        mTitleToolBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mTitleToolBar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
        {
            //去除默认Title显示
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.mipmap.ic_arrow_back_white_24dp);
        }
        setTitle(mTitle);
        mTitleToolBar.setOverflowIcon(ContextCompat.getDrawable(this, R.mipmap.actionbar_more));
        mTitleToolBar.setNavigationOnClickListener(v ->
        {
            if (no_network_url.equals(webView.getUrl())) {
                //如果当前url是网络错误的url，返回就关闭页面
                finish();
                return;
            }
            if (goBack())
            {
            } else
            {
                onBackPressed();
            }
        });
        mTitleToolBar.setOnMenuItemClickListener(item ->
        {
            switch (item.getItemId())
            {
                case R.id.actionbar_share:// 分享到
                    String shareText = mWebChromeClient.getTitle() + mUrl + "（分享自" + R.string.app_name + "）";
                    ShareUtils.share(WebViewActivity.this, shareText);
                    break;
                case R.id.actionbar_cope:// 复制链接
                    CommonUtil.copy(mUrl);
                    ToastUtils.showShort("复制成功");
                    break;
                case R.id.actionbar_open:// 打开链接
                    CommonUtil.openLink(WebViewActivity.this, mUrl);
                    break;
            }
            return false;
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.webview_menu, menu);
        return true;
    }

    private void getIntentData()
    {
        if (getIntent() != null)
        {
            mTitle = getIntent().getStringExtra("mTitle");
            mUrl = getIntent().getStringExtra("mUrl");
        }
    }


    public void setTitle(String mTitle)
    {
        mTitleToolBar.setTitle(mTitle);
    }

    private void initWebView()
    {
        mProgressBar.setVisibility(View.VISIBLE);
        WebSettings ws = webView.getSettings();
        // 网页内容的宽度是否可大于WebView控件的宽度
        ws.setLoadWithOverviewMode(false);
        // 保存表单数据
        ws.setSaveFormData(true);
        // 是否应该支持使用其屏幕缩放控件和手势缩放
        ws.setSupportZoom(true);
        ws.setBuiltInZoomControls(true);
        ws.setDisplayZoomControls(false);
        // 启动应用缓存
        ws.setAppCacheEnabled(true);
        // 设置缓存模式
        ws.setCacheMode(WebSettings.LOAD_DEFAULT);
        // setDefaultZoom  api19被弃用
        // 设置此属性，可任意比例缩放。
        ws.setUseWideViewPort(true);
        // 缩放比例 1
        webView.setInitialScale(1);
        // 告诉WebView启用JavaScript执行。默认的是false。
        ws.setJavaScriptEnabled(true);
        //  页面加载好以后，再放开图片
        ws.setBlockNetworkImage(false);
        // 使用localStorage则必须打开
        ws.setDomStorageEnabled(true);
        // 排版适应屏幕
        ws.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        // WebView是否支持多个窗口。
        ws.setSupportMultipleWindows(true);

        // webview从5.0开始默认不允许混合模式,https中不能加载http资源,需要设置开启。
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            ws.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        /** 设置字体默认缩放大小(改变网页字体大小,setTextSize  api14被弃用)*/
        ws.setTextZoom(100);

        mWebChromeClient = new MyWebChromeClient(this);
        webView.setWebChromeClient(mWebChromeClient);
        // 与js交互(这里的"App"可根据公司前端协商定义)
        webView.addJavascriptInterface(new AppJsHandler(this), "App");
        webView.setWebViewClient(new MyWebViewClient(this));
    }

    @Override
    public void showLoading()
    {

    }

    @Override
    public void hideLoading()
    {

    }

    @Override
    public void showMessage(@NonNull String message)
    {
        checkNotNull(message);
        ArmsUtils.snackbarText(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent)
    {
        checkNotNull(intent);
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void killMyself()
    {
        finish();
    }


    @Override
    public void hindProgressBar()
    {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showWebView()
    {
        webView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hindWebView()
    {
        webView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void startProgress()
    {
        startProgress90();
    }

    @Override
    public void progressChanged(int newProgress)
    {
        if (mProgress90)
        {
            int progress = newProgress * 100;
            if (progress > 900)
            {
                mProgressBar.setProgress(progress);
                if (progress == 1000)
                {
                    mProgressBar.setVisibility(View.GONE);
                }
            }
        }
    }

    @Override
    public void fullViewAddView(View view)
    {
        FrameLayout decor = (FrameLayout) getWindow().getDecorView();
        videoFullView = new FullscreenHolder(WebViewActivity.this);
        videoFullView.addView(view);
        decor.addView(videoFullView);
    }

    @Override
    public void showVideoFullView()
    {
        videoFullView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hindVideoFullView()
    {
        videoFullView.setVisibility(View.GONE);
    }

    public boolean goBack()
    {
        boolean goback = false;
        if (webView != null && webView.canGoBack())
        {
            webView.goBack();
            goback = true;
        }
        return goback;
    }

    /**
     * 进度条 假装加载到90%
     */
    public void startProgress90()
    {
        mProgressBar.setVisibility(View.VISIBLE);
        for (int i = 0; i < 900; i++)
        {
            final int progress = i + 1;
            mProgressBar.postDelayed(() ->
            {
                mProgressBar.setProgress(progress);
                if (progress == 900)
                {
                    mProgress90 = true;
                    if (mPageFinish)
                    {
                        startProgress90to100();
                    }
                }
            }, (i + 1) * 2);
        }
    }

    /**
     * 进度条 加载到100%
     */
    public void startProgress90to100()
    {
        for (int i = 900; i <= 1000; i++)
        {
            final int progress = i + 1;
            mProgressBar.postDelayed(() ->
            {
                mProgressBar.setProgress(progress);
                if (progress == 1000)
                {
                    mProgressBar.setVisibility(View.GONE);
                }
            }, (i + 1) * 2);
        }
    }


    public FrameLayout getVideoFullView()
    {
        return videoFullView;
    }


    /**
     * 全屏时按返加键执行退出全屏方法
     */
    public void hideCustomView()
    {
        mWebChromeClient.onHideCustomView();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    /**
     * 上传图片之后的回调
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent)
    {
        if (requestCode == MyWebChromeClient.FILECHOOSER_RESULTCODE)
        {
            mWebChromeClient.mUploadMessage(intent, resultCode);
        } else if (requestCode == MyWebChromeClient.FILECHOOSER_RESULTCODE_FOR_ANDROID_5)
        {
            mWebChromeClient.mUploadMessageForAndroid5(intent, resultCode);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK)
        {
            //全屏播放退出全屏
            if (mWebChromeClient.inCustomView())
            {
                hideCustomView();
                return true;

                //返回网页上一页
            } else if (webView.canGoBack())
            {
                webView.goBack();
                return true;

                //退出网页
            } else
            {
                webView.loadUrl("about:blank");
                finish();
            }
        }
        return false;
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        webView.onPause();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        webView.onResume();
        // 支付宝网页版在打开文章详情之后,无法点击按钮下一步
        webView.resumeTimers();
        // 设置为横屏
        if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)
        {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        videoFullView.removeAllViews();
        if (webView != null)
        {
            ViewGroup parent = (ViewGroup) webView.getParent();
            if (parent != null)
            {
                parent.removeView(webView);
            }
            webView.removeAllViews();
            webView.loadUrl("about:blank");
            webView.stopLoading();
            webView.setWebChromeClient(null);
            webView.setWebViewClient(null);
            webView.destroy();
            webView = null;
        }
    }

    /**
     * 打开网页:
     *
     * @param mContext 上下文
     * @param mUrl     要加载的网页url
     * @param mTitle   title
     */
    public static void loadUrl(Context mContext, String mUrl, String mTitle)
    {
        Intent intent = new Intent(mContext, WebViewActivity.class);
        intent.putExtra("mUrl", mUrl);
        intent.putExtra("mTitle", mTitle);
        mContext.startActivity(intent);
    }

}
