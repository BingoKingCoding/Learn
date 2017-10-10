package com.king.learn.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.Preconditions;
import com.king.learn.R;
import com.king.learn.app.base.BaseFragment;
import com.king.learn.di.component.DaggerArticleComponent;
import com.king.learn.di.module.ArticleModule;
import com.king.learn.mvp.contract.ArticleContract;
import com.king.learn.mvp.model.entity.DaoGankEntity;
import com.king.learn.mvp.model.entity.GankEntity;
import com.king.learn.mvp.presenter.ArticlePresenter;
import com.king.learn.mvp.ui.adapter.ArticleAdapter;

import java.util.List;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

import static com.king.learn.app.ARouterPaths.MAIN_DETAIL;
import static com.king.learn.app.EventBusTags.EXTRA_DETAIL;

/**
 * <文章收藏页面>
 * Created by wwb on 2017/9/27 17:08.
 */

public class ArticleFragment extends BaseFragment<ArticlePresenter> implements ArticleContract.View, SwipeRefreshLayout.OnRefreshListener
{
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.refreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private ArticleAdapter mAdapter;
    private GankEntity.ResultsBean intentArticle;

    public static ArticleFragment newInstance()
    {
        ArticleFragment fragment = new ArticleFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(AppComponent appComponent)
    {
        DaggerArticleComponent.builder()
                .appComponent(appComponent)
                .articleModule(new ArticleModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.layout_refresh_list, container, false);
    }

    @Override
    public void initData(Bundle savedInstanceState)
    {
        mSwipeRefreshLayout.setOnRefreshListener(this);
        ArmsUtils.configRecycleView(mRecyclerView, new LinearLayoutManager(getActivity()));

        mAdapter = new ArticleAdapter(null);
        mAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);
        mAdapter.setOnItemClickListener((adapter, view, position) ->
        {
            DaoGankEntity bean = (DaoGankEntity) adapter.getItem(position);
            if (intentArticle == null)
                intentArticle = new GankEntity.ResultsBean();
            intentArticle._id = bean._id;
            intentArticle.createdAt = bean.createdAt;
            intentArticle.desc = bean.desc;
            intentArticle.images = bean.images;
            intentArticle.publishedAt = bean.publishedAt;
            intentArticle.source = bean.source;
            intentArticle.type = bean.type;
            intentArticle.url = bean.url;
            intentArticle.used = bean.used;
            intentArticle.who = bean.who;
            ARouter.getInstance().build(MAIN_DETAIL)
                    .withSerializable(EXTRA_DETAIL, intentArticle)
                    .navigation();
        });
        TextView textView = new TextView(getContext());
        textView.setText("没有更多内容了");
        textView.setGravity(Gravity.CENTER);
        mAdapter.setEmptyView(textView);
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
        mAdapter.notifyDataSetChanged();
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
                .subscribe(integer -> mSwipeRefreshLayout.setRefreshing(true));
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
