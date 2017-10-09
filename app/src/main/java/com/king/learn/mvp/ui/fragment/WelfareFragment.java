package com.king.learn.mvp.ui.fragment;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.Preconditions;
import com.king.learn.R;
import com.king.learn.app.base.BaseFragment;
import com.king.learn.di.component.DaggerWelfareComponent;
import com.king.learn.di.module.WelfareModule;
import com.king.learn.mvp.contract.WelfareContract;
import com.king.learn.mvp.model.entity.GankEntity;
import com.king.learn.mvp.presenter.WelfarePresenter;
import com.king.learn.mvp.ui.adapter.WelfareAdapter;
import com.king.learn.mvp.ui.widget.varunest.sparkbutton.SparkButton;

import java.util.List;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import me.yuqirong.cardswipelayout.CardConfig;
import me.yuqirong.cardswipelayout.CardItemTouchHelperCallback;
import me.yuqirong.cardswipelayout.CardLayoutManager;
import me.yuqirong.cardswipelayout.OnSwipeListener;

import static com.king.learn.R.id.recyclerView;


/**
 * <请描述这个类是干什么的>
 * Created by wwb on 2017/9/20 16:13.
 */

public class WelfareFragment extends BaseFragment<WelfarePresenter> implements WelfareContract.View,SwipeRefreshLayout.OnRefreshListener
{
    @BindView(recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.refreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.spark_button)
    SparkButton mSparkButton;

    private WelfareAdapter mWelfareAdapter;

    public static WelfareFragment newInstance() {
        WelfareFragment fragment = new WelfareFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(AppComponent appComponent)
    {
        DaggerWelfareComponent
                .builder()
                .appComponent(appComponent)
                .welfareModule(new WelfareModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_welfare,container,false);
    }

    @Override
    public void initData(Bundle savedInstanceState)
    {
        mSwipeRefreshLayout.setOnRefreshListener(this);
        PagerSnapHelper pagerSnapHelper = new PagerSnapHelper();
        pagerSnapHelper.attachToRecyclerView(mRecyclerView);
        mWelfareAdapter= new WelfareAdapter(null);
        TextView textView = new TextView(getContext());
        textView.setText("没有更多内容了");
        textView.setGravity(Gravity.CENTER);
//        mAdapter.setEmptyView(textView);
        mRecyclerView.setAdapter(mWelfareAdapter);

    }
    private static final DecelerateInterpolator DECCELERATE_INTERPOLATOR = new DecelerateInterpolator();
    private static final AccelerateInterpolator ACCELERATE_INTERPOLATOR = new AccelerateInterpolator();
    private void animatePhotoLike() {

        mSparkButton.setScaleY(0.1f);
        mSparkButton.setScaleX(0.1f);

        AnimatorSet animatorSet = new AnimatorSet();

        ObjectAnimator imgScaleUpYAnim = ObjectAnimator.ofFloat(mSparkButton, "scaleY", 0.1f, 1f);
        imgScaleUpYAnim.setDuration(300);
        imgScaleUpYAnim.setInterpolator(DECCELERATE_INTERPOLATOR);
        ObjectAnimator imgScaleUpXAnim = ObjectAnimator.ofFloat(mSparkButton, "scaleX", 0.1f, 1f);
        imgScaleUpXAnim.setDuration(300);
        imgScaleUpXAnim.setInterpolator(DECCELERATE_INTERPOLATOR);

        ObjectAnimator imgScaleDownYAnim = ObjectAnimator.ofFloat(mSparkButton, "scaleY", 1f, 0f);
        imgScaleDownYAnim.setDuration(300);
        imgScaleDownYAnim.setInterpolator(ACCELERATE_INTERPOLATOR);
        ObjectAnimator imgScaleDownXAnim = ObjectAnimator.ofFloat(mSparkButton, "scaleX", 1f, 0f);
        imgScaleDownXAnim.setDuration(300);
        imgScaleDownXAnim.setInterpolator(ACCELERATE_INTERPOLATOR);

        animatorSet.playTogether(imgScaleUpYAnim, imgScaleUpXAnim);
        animatorSet.play(imgScaleDownYAnim).with(imgScaleDownXAnim).after(imgScaleUpYAnim);

        animatorSet.start();
    }

    private void collectWelfare(BaseQuickAdapter adapter, View view, int position) {
        GankEntity.ResultsBean entity = (GankEntity.ResultsBean) adapter.getItem(position);
        mPresenter.addToFavorites(entity);
    }

    private void collectWelfare(Object o) {
        animatePhotoLike();
        GankEntity.ResultsBean entity = (GankEntity.ResultsBean)o;
        mPresenter.addToFavorites(entity);
    }


    @Override
    protected void onFragmentFirstVisible()
    {
        //去服务器下载数据
        mPresenter.requestData(true);
    }

    @Override
    public void setData(Object data)
    {

    }

    @Override
    public void startLoadMore()
    {
        mWelfareAdapter.setEnableLoadMore(true);
    }

    @Override
    public void endLoadMore()
    {
        mWelfareAdapter.loadMoreComplete();
    }

    @Override
    public void setNewData(List<GankEntity.ResultsBean> mData)
    {
        CardItemTouchHelperCallback cardCallback = new CardItemTouchHelperCallback(mRecyclerView.getAdapter(),mData);
        cardCallback.setOnSwipedListener(new OnSwipeListener()
        {
            @Override
            public void onSwiping(RecyclerView.ViewHolder viewHolder, float ratio, int direction)
            {

            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, Object o, int direction)
            {
                mPresenter.requestData(false);
                if(direction == CardConfig.SWIPED_RIGHT){
                    collectWelfare(o);

                }
            }

            @Override
            public void onSwipedClear()
            {

            }
        });
        final ItemTouchHelper touchHelper = new ItemTouchHelper(cardCallback);
        final CardLayoutManager cardLayoutManager = new CardLayoutManager(mRecyclerView, touchHelper);
        mRecyclerView.setLayoutManager(cardLayoutManager);
        touchHelper.attachToRecyclerView(mRecyclerView);

        mWelfareAdapter.setNewData(mData);
        if (mWelfareAdapter.getData().size() < 3){
            mPresenter.requestData(false);
        }

    }

    @Override
    public void setAddData(List<GankEntity.ResultsBean> results)
    {
        mWelfareAdapter.addData(results);
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
        mPresenter.requestData(true);
    }
}
