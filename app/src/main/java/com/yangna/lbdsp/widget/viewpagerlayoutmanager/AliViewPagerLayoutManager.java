package com.yangna.lbdsp.widget.viewpagerlayoutmanager;

import android.content.Context;

import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;


/**
 * @Author: YuanPeikai
 * @CreateDate: 2019/8/30 17:05
 * @Description: （1）借助 PagerSnapHelper 来实现一屏只显示一个item
 * （2）通过 onScrollStateChanged 的状态监听，来实现是否滚动到底部的监听
 * （3）通过 scrollVerticallyBy 回调中的 int dy ，来判断是否为上滑和下滑
 * （4）通过 RecyclerView.OnChildAttachStateChangeListener 的回调监听，实现滑动的位置 Position
 */
public class AliViewPagerLayoutManager extends LinearLayoutManager {
    private static final String TAG = "ViewPagerLayoutManager";
    private PagerSnapHelper mPagerSnapHelper;
    private OnViewPagerListener mOnViewPagerListener;
    private RecyclerView mRecyclerView;
    private int mDrift;//位移，用来判断移动方向

    public AliViewPagerLayoutManager(Context context, int orientation) {
        super(context, orientation, false);
        init();
    }

    public AliViewPagerLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
        init();
    }

    private void init() {
        mPagerSnapHelper = new PagerSnapHelper();
    }

    @Override
    public void onAttachedToWindow(RecyclerView view) {
        super.onAttachedToWindow(view);
        mPagerSnapHelper.attachToRecyclerView(view);
        this.mRecyclerView = view;
        mRecyclerView.addOnChildAttachStateChangeListener(mChildAttachStateChangeListener);
    }


    /**
     * 滑动状态的改变
     * 缓慢拖拽-> SCROLL_STATE_DRAGGING
     * 快速滚动-> SCROLL_STATE_SETTLING
     * 空闲状态-> SCROLL_STATE_IDLE
     *
     * @param state
     */
    @Override
    public void onScrollStateChanged(int state) {
        switch (state) {
            case RecyclerView.SCROLL_STATE_IDLE://空闲状态
                View viewIdle = mPagerSnapHelper.findSnapView(this);
                int positionIdle = getPosition(viewIdle);
                if (mOnViewPagerListener != null && getChildCount() == 1) {
                    mOnViewPagerListener.onPageSelected(positionIdle, positionIdle == getItemCount() - 1);
                }
                break;
            case RecyclerView.SCROLL_STATE_DRAGGING: //缓慢拖拽
                View viewDrag = mPagerSnapHelper.findSnapView(this);
                int positionDrag = getPosition(viewDrag);
                break;
            case RecyclerView.SCROLL_STATE_SETTLING: //快速滚动
                View viewSettling = mPagerSnapHelper.findSnapView(this);
                int positionSettling = getPosition(viewSettling);
                break;

        }
    }

    /**
     * 布局完成后调用
     *
     * @param state
     */
    /*@Override
    public void onLayoutCompleted(RecyclerView.State state) {
        super.onLayoutCompleted(state);
        if (mOnViewPagerListener != null) mOnViewPagerListener.onInitComplete();
    }*/


    /**
     * 监听竖直方向的相对偏移量
     *
     * @param dy>0     ：上滑  dy<0 ：下滑
     * @param recycler
     * @param state
     * @return
     */
    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
        this.mDrift = dy;
        return super.scrollVerticallyBy(dy, recycler, state);
    }


    /**
     * 监听水平方向的相对偏移量
     *
     * @param dx
     * @param recycler
     * @param state
     * @return
     */
    @Override
    public int scrollHorizontallyBy(int dx, RecyclerView.Recycler recycler, RecyclerView.State state) {
        this.mDrift = dx;
        return super.scrollHorizontallyBy(dx, recycler, state);
    }

    /**
     * 设置监听
     *
     * @param listener
     */
    public void setOnViewPagerListener(OnViewPagerListener listener) {
        this.mOnViewPagerListener = listener;
    }

    private RecyclerView.OnChildAttachStateChangeListener mChildAttachStateChangeListener = new RecyclerView.OnChildAttachStateChangeListener() {
        @Override
        public void onChildViewAttachedToWindow(View view) {
            if (mOnViewPagerListener != null && getChildCount() == 1) {
                mOnViewPagerListener.onInitComplete();
            }
        }

        @Override
        public void onChildViewDetachedFromWindow(View view) {
            System.out.println("ViewPagerLayoutManager.onChildViewDetachedFromWindow==>> " + mDrift);
            if (mDrift >= 0) {
                if (mOnViewPagerListener != null)
                    mOnViewPagerListener.onPageRelease(true, getPosition(view));
            } else {
                if (mOnViewPagerListener != null)
                    mOnViewPagerListener.onPageRelease(false, getPosition(view));
            }
        }
    };
}
