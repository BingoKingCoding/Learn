package com.king.learn.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.launcher.ARouter;
import com.jess.arms.base.DefaultAdapter;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.Preconditions;
import com.king.learn.R;
import com.king.learn.app.base.BaseFragment;
import com.king.learn.di.component.DaggerCategoryComponent;
import com.king.learn.di.module.CategoryModule;
import com.king.learn.mvp.contract.CategoryContract;
import com.king.learn.mvp.model.entity.GankEntity;
import com.king.learn.mvp.presenter.CategoryPresenter;
import com.paginate.Paginate;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

import static com.king.learn.app.ARouterPaths.MAIN_DETAIL;
import static com.king.learn.app.EventBusTags.EXTRA_DETAIL;

/**
 * <请描述这个类是干什么的>
 * Created by wwb on 2017/9/20 17:19.
 */

public class CategoryFragment extends BaseFragment<CategoryPresenter> implements CategoryContract.View,SwipeRefreshLayout.OnRefreshListener
{
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.refreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private boolean isLoadingMore;
    private Paginate mPaginate;
    private String type;

    public static CategoryFragment newInstance(String type) {
        CategoryFragment fragment = new CategoryFragment();
        Bundle bundle = new Bundle();
        bundle.putString("type", type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void setupFragmentComponent(AppComponent appComponent)
    {
        DaggerCategoryComponent.builder()
                .appComponent(appComponent)
                .categoryModule(new CategoryModule(this))
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
        type = getArguments().getString("type");
        mSwipeRefreshLayout.setOnRefreshListener(this);
        ArmsUtils.configRecycleView(mRecyclerView, new LinearLayoutManager(getActivity()));
    }

    @Override
    protected void onFragmentFirstVisible()
    {
        //去服务器下载数据
        mPresenter.requestData(type,true);
    }

    @Override
    public void setData(Object data)
    {

    }

    @Override
    public void startLoadMore()
    {
        isLoadingMore = true;
    }

    @Override
    public void endLoadMore()
    {
        isLoadingMore = false;
    }

    @Override
    public void setAdapter(DefaultAdapter mAdapter)
    {
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener((view, viewType, data, position) ->
        {
            GankEntity.ResultsBean bean = (GankEntity.ResultsBean) data;
            ARouter.getInstance().build(MAIN_DETAIL)
                    .withSerializable(EXTRA_DETAIL,bean)
                    .navigation();
        });
        initPaginate();
    }

    private void initPaginate()
    {
        if (mPaginate == null)
        {
            Paginate.Callbacks callback = new Paginate.Callbacks()
            {
                @Override
                public void onLoadMore()
                {
                    mPresenter.requestData(type,false);
                }

                @Override
                public boolean isLoading()
                {
                    return isLoadingMore;
                }

                @Override
                public boolean hasLoadedAllItems()
                {
                    return false;
                }
            };
            mPaginate = Paginate.with(mRecyclerView,callback)
                    .setLoadingTriggerThreshold(0)
                    .build();
            mPaginate.setHasMoreDataToLoad(false);
        }
    }

    @Override
    public void showLoading()
    {
        Observable.just(1)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(integer ->
                        mSwipeRefreshLayout.setRefreshing(true));
    }

    @Override
    public void hideLoading()
    {
        mSwipeRefreshLayout.setRefreshing(false);
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
        mPresenter.requestData(type,true);
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        DefaultAdapter.releaseAllHolder(mRecyclerView);//super.onDestroy()之后会unbind,所有view被置为null,所以必须在之前调用
        this.mPaginate = null;
    }
}
