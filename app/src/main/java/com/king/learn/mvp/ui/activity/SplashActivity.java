package com.king.learn.mvp.ui.activity;

import android.Manifest;
import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.telephony.TelephonyManager;

import com.blankj.utilcode.util.SPUtils;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.king.learn.R;
import com.king.learn.di.component.DaggerSplashComponent;
import com.king.learn.di.module.SplashModule;
import com.king.learn.mvp.contract.SplashContract;
import com.king.learn.mvp.presenter.SplashPresenter;
import com.king.learn.mvp.ui.widget.FixedImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;
import timber.log.Timber;

import static com.jess.arms.utils.Preconditions.checkNotNull;
import static com.king.learn.app.utils.DDImageUtil.InputStream2Drawable;


public class SplashActivity extends BaseActivity<SplashPresenter> implements SplashContract.View,EasyPermissions.PermissionCallbacks
{

    @BindView(R.id.splash_img)
    FixedImageView splashImg;
    private static final int PERMISSON_REQUESTCODE = 1;
    /**
     * 需要进行检测的权限数组
     */
    protected String[] needPermissions = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
    };
    @Override
    public void setupActivityComponent(AppComponent appComponent)
    {
        DaggerSplashComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .splashModule(new SplashModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(Bundle savedInstanceState)
    {
        return R.layout.activity_splash; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(Bundle savedInstanceState)
    {
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
    protected void onStart()
    {
        super.onStart();
        requestPermissions();
    }

    @AfterPermissionGranted(PERMISSON_REQUESTCODE)
    public void requestPermissions()
    {
        if (!EasyPermissions.hasPermissions(this, needPermissions))
        {
            EasyPermissions.requestPermissions(this, "应用需要这些权限", PERMISSON_REQUESTCODE, needPermissions);
        } else
        {
            mPresenter.getADImages();
            TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            String deviceId = tm.getDeviceId();
            mPresenter.requestSplash(deviceId);
        }
    }

    public void showMissingPermissionDialog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示");
        builder.setMessage("当前应用缺少必要权限。请点击\"设置\"-\"权限\"-打开所需权限。");

        // 拒绝, 退出应用
        builder.setNegativeButton("取消",
                (dialog, which) -> finish());
        builder.setPositiveButton("设置",
                (dialog, which) -> startAppSettings());
        builder.setCancelable(false);
        builder.show();

    }

    @Override
    public void delaySplash(List<String> picList)
    {
        if (picList.size() > 0) {
            Random random = new Random();
            int index = random.nextInt(picList.size());
            int imgIndex = SPUtils.getInstance().getInt("splash_img_index", 0);
            Timber.i("当前的imgIndex=" + imgIndex);
            if (index == imgIndex) {
                if (index >= picList.size()) {
                    index--;
                } else if (imgIndex == 0) {
                    if (index + 1 < picList.size()) {
                        index++;
                    }
                }
            }
            SPUtils.getInstance().put("splash_img_index", index);
            Timber.i("当前的picList.size=" + picList.size() + ",index = " + index);
            File file = new File(picList.get(index));
            try {
                InputStream fis = new FileInputStream(file);
                splashImg.setImageDrawable(InputStream2Drawable(fis));
                animWelcomeImage();
                fis.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {

            }
        } else {
            try {
                AssetManager assetManager = this.getAssets();
                InputStream in = assetManager.open("welcome_default.jpg");
                splashImg.setImageDrawable(InputStream2Drawable(in));
                animWelcomeImage();
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 启动应用的设置
     */
    private void startAppSettings()
    {
        Intent intent = new Intent(
                Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + getPackageName()));
        startActivity(intent);
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
    }

    private void animWelcomeImage()
    {
        ObjectAnimator animator = ObjectAnimator.ofFloat(splashImg, "translationX", -100F);
        animator.setDuration(2000L).start();
        animator.addListener(new Animator.AnimatorListener()
        {
            @Override
            public void onAnimationStart(Animator animation)
            {

            }

            @Override
            public void onAnimationEnd(Animator animation)
            {
                startMainOrLogin();
            }

            @Override
            public void onAnimationCancel(Animator animation)
            {

            }

            @Override
            public void onAnimationRepeat(Animator animation)
            {

            }
        });
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms)
    {
        recreate();
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms)
    {
        showMissingPermissionDialog();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    private void startMainOrLogin(){
//        UserEntity user = UserModelDao.query();
//        if (user != null)
//        {
//            startMainActivity();
//        } else
//        {
//            startLoginActivity();
//        }
    }

    private void startMainActivity(){
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void startLoginActivity(){
        Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
