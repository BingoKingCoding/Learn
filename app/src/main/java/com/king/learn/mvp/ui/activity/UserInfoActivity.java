package com.king.learn.mvp.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoImpl;
import com.jph.takephoto.model.InvokeParam;
import com.jph.takephoto.model.TContextWrap;
import com.jph.takephoto.model.TResult;
import com.jph.takephoto.permission.InvokeListener;
import com.jph.takephoto.permission.PermissionManager;
import com.jph.takephoto.permission.TakePhotoInvocationHandler;
import com.king.learn.R;
import com.king.learn.app.base.DDBaseActivity;
import com.king.learn.app.utils.DDFileUtil;
import com.king.learn.di.component.DaggerUserInfoComponent;
import com.king.learn.di.module.UserInfoModule;
import com.king.learn.mvp.contract.UserInfoContract;
import com.king.learn.mvp.presenter.UserInfoPresenter;
import com.king.learn.mvp.ui.widget.ClearEditText;

import java.io.File;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class UserInfoActivity extends DDBaseActivity<UserInfoPresenter> implements UserInfoContract.View, TakePhoto.TakeResultListener, InvokeListener
{
    @BindView(R.id.et_user_geyan)
    ClearEditText et_user_geyan;
    @BindView(R.id.toolbar_userinfo)
    Toolbar mToolbar;
    private TakePhoto takePhoto;
    private InvokeParam invokeParam;

    @Override
    public void setupActivityComponent(AppComponent appComponent)
    {
        DaggerUserInfoComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .userInfoModule(new UserInfoModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(Bundle savedInstanceState)
    {
        return R.layout.activity_user_info; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(Bundle savedInstanceState)
    {
        initToolbar();
        getTakePhoto().onCreate(savedInstanceState);
    }

    private void initToolbar()
    {
        setToolBar(mToolbar,"编辑个人信息");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        getTakePhoto().onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
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

    private String avatar = "user_avatar.jpg";
    public void takeOrPickPhoto()
    {
        File file = DDFileUtil.creatImageCache(avatar);
        Uri imageUri = Uri.fromFile(file);
        TakePhoto takePhoto = getTakePhoto();

    }


    @Override
    public void takeSuccess(TResult result)
    {

    }

    @Override
    public void takeFail(TResult result, String msg)
    {

    }

    @Override
    public void takeCancel()
    {

    }

    @Override
    public PermissionManager.TPermissionType invoke(InvokeParam invokeParam)
    {
        PermissionManager.TPermissionType type = PermissionManager.checkPermission(TContextWrap.of(this), invokeParam.getMethod());
        if (PermissionManager.TPermissionType.WAIT.equals(type))
        {
            this.invokeParam = invokeParam;
        }
        return type;
    }

    /**
     * 获取TakePhoto实例
     *
     * @return
     */
    public TakePhoto getTakePhoto()
    {
        if (takePhoto == null)
        {
            takePhoto = (TakePhoto) TakePhotoInvocationHandler.of(this).bind(new TakePhotoImpl(this, this));
        }
        return takePhoto;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        //以下代码为处理Android6.0、7.0动态权限所需
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.TPermissionType type = PermissionManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.handlePermissionsResult(this, type, invokeParam, this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        getTakePhoto().onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }
}
