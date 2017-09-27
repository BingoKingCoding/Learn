package com.king.learn.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.Preconditions;
import com.king.learn.R;
import com.king.learn.app.base.BaseFragment;
import com.king.learn.di.component.DaggerMeiziComponent;
import com.king.learn.di.module.MeiziModule;
import com.king.learn.mvp.contract.MeiziContract;
import com.king.learn.mvp.model.entity.DaoGankEntity;
import com.king.learn.mvp.presenter.MeiziPresenter;
import com.king.learn.mvp.ui.adapter.MeiziAdapter;
import com.king.learn.mvp.ui.widget.SpacesItemDecoration;

import org.simple.eventbus.Subscriber;

import java.util.List;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

import static com.king.learn.R.id.recyclerView;

/**
 * <请描述这个类是干什么的>
 * Created by wwb on 2017/9/27 16:33.
 */

public class MeiziFragment extends BaseFragment<MeiziPresenter> implements MeiziContract.View,SwipeRefreshLayout.OnRefreshListener
{

    @BindView(recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.refreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private MeiziAdapter mAdapter;
    public static MeiziFragment newInstance(){
        MeiziFragment fragment = new MeiziFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(AppComponent appComponent)
    {
        DaggerMeiziComponent.builder()
                .appComponent(appComponent)
                .meiziModule(new MeiziModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.layout_refresh_list,container,false);
    }

    @Override
    public void initData(Bundle savedInstanceState)
    {
        mSwipeRefreshLayout.setOnRefreshListener(this);
        ArmsUtils.configRecycleView(mRecyclerView,new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        mAdapter = new MeiziAdapter(null);
        mAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);
        TextView textView = new TextView(getContext());
        textView.setText("没有更多内容了");
        textView.setGravity(Gravity.CENTER);
        mAdapter.setEmptyView(textView);

        SpacesItemDecoration decoration=new SpacesItemDecoration(16);
        mRecyclerView.addItemDecoration(decoration);

        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void onFragmentFirstVisible()
    {
        mPresenter.requestData(true);
    }

    @Override
    public void setData(Object data)
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
    public void showMessage(String message)
    {
        Preconditions.checkNotNull(message);
        ArmsUtils.snackbarText(message);
    }

    @Override
    public void launchActivity(Intent intent)
    {
        Preconditions.checkNotNull(intent);
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void killMyself()
    {

    }

    @Override
    public void onRefresh()
    {
        mPresenter.requestData(true);
    }

    @Override
    public void startLoadMore()
    {
        Observable.just(1)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(integer ->
                        mSwipeRefreshLayout.setRefreshing(true));
    }

    @Subscriber(tag = "meizi")
    private void updateAdapter(Object o){
        mPresenter.requestData(true);
    }

    @Override
    public void endLoadMore()
    {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void setAdapter(List<DaoGankEntity> entity)
    {
        mAdapter.setNewData(entity);
    }
}
