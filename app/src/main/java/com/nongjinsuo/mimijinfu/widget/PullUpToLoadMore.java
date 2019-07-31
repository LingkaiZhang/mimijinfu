package com.nongjinsuo.mimijinfu.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Scroller;

import com.nongjinsuo.mimijinfu.util.LogUtil;

/**
 * Created by baoyunlong on 16/6/8.
 */
public class PullUpToLoadMore extends ViewGroup {
    public static String TAG = PullUpToLoadMore.class.getName();

    MyScrollView topScrollView, bottomScrollView;
    VelocityTracker velocityTracker = VelocityTracker.obtain();
    Scroller scroller = new Scroller(getContext());

    int currPosition = 0;
    int position1Y;
    int lastY;
    public int scaledTouchSlop;//最小滑动距离
    int speed = 200;
    boolean isIntercept;
    private int webViewHeight;

    public boolean bottomScrollVIewIsInTop = true;
    public boolean topScrollViewIsBottom = false;
    private OnScrollToCurrPosition onScrollToCurrPosition;
    private OnCanRefresh onCanRefresh;

    public PullUpToLoadMore(Context context) {
        super(context);
        init();
    }

    public PullUpToLoadMore(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PullUpToLoadMore(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public int getCurrPosition() {
        return currPosition;
    }

    public void setCurrPosition(int currPosition) {
        this.currPosition = currPosition;
    }

    public void setWebViewHeight(int webViewHeight) {
        this.webViewHeight = webViewHeight;
    }

    private void init() {

        post(new Runnable() {
            @Override
            public void run() {
                topScrollView = (MyScrollView) getChildAt(0);
                bottomScrollView = (MyScrollView) getChildAt(1);
                topScrollView.setScrollListener(new MyScrollView.ScrollListener() {
                    @Override
                    public void onScrollToBottom() {
                        topScrollViewIsBottom = true;
                        if (onCanRefresh!=null)
                        onCanRefresh.isRefresh(false);
                        LogUtil.d("scroll","onScrollToBottom");
                    }

                    @Override
                    public void onScrollToTop() {
                        if (onCanRefresh!=null)
                        onCanRefresh.isRefresh(true);
                        LogUtil.d("scroll","onScrollToTop");
                    }

                    @Override
                    public void onScroll(int scrollY) {
                        LogUtil.d("scroll","onScroll--"+scrollY);
                    }

                    @Override
                    public void notBottom() {
                        topScrollViewIsBottom = false;
//                        onCanRefresh.isRefresh(false);
                        LogUtil.d("scroll","notBottom");
                    }

                });

                bottomScrollView.setScrollListener(new MyScrollView.ScrollListener() {
                    @Override
                    public void onScrollToBottom() {
                        if (onCanRefresh!=null)
                        onCanRefresh.isRefresh(false);
                    }

                    @Override
                    public void onScrollToTop() {
                        if (onCanRefresh!=null)
                        onCanRefresh.isRefresh(false);
                    }

                    @Override
                    public void onScroll(int scrollY) {
                        if (onCanRefresh!=null)
                        onCanRefresh.isRefresh(false);
                        if (scrollY == 0) {
                            bottomScrollVIewIsInTop = true;
                        } else {
                            bottomScrollVIewIsInTop = false;
                        }
                    }
                    @Override
                    public void notBottom() {
                        if (onCanRefresh!=null)
                        onCanRefresh.isRefresh(false);
                    }
                });
                position1Y = topScrollView.getBottom();
                scaledTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop() + 100;
            }
        });
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        //防止子View禁止父view拦截事件
        this.requestDisallowInterceptTouchEvent(false);
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int y = (int) ev.getY();

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                //判断是否已经滚动到了底部
                if (topScrollViewIsBottom) {
                    int dy = lastY - y;

                    //判断是否是向上滑动和是否在第一屏
                    if (dy > 0 && currPosition == 0) {
                        if (dy >= scaledTouchSlop) {
                            isIntercept = true;//拦截事件
                            lastY = y;
                        }
                    }
                }

                if (bottomScrollVIewIsInTop) {
                    int dy = lastY - y;

                    //判断是否是向下滑动和是否在第二屏
                    if (dy < 0 && currPosition == 1) {
                        if (Math.abs(dy) >= scaledTouchSlop) {
                            isIntercept = true;
                        }
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                if (bottomScrollVIewIsInTop) {
                    int dy = Math.abs(lastY - y);

                    //判断是否是向下滑动和是否在第二屏
                    if (dy >= scaledTouchSlop && currPosition == 1) {
                        isIntercept = true;
                    } else {
                        isIntercept = false;
                    }
                }
                break;
        }
        return isIntercept;
    }

    public OnScrollToCurrPosition getOnScrollToCurrPosition() {
        return onScrollToCurrPosition;
    }

    public void setOnScrollToCurrPosition(OnScrollToCurrPosition onScrollToCurrPosition) {
        this.onScrollToCurrPosition = onScrollToCurrPosition;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int y = (int) event.getY();
        velocityTracker.addMovement(event);

        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                int dy = lastY - y;
                if (getScrollY() + dy < 0) {
                    dy = getScrollY() + dy + Math.abs(getScrollY() + dy);
                }

                if (getScrollY() + dy + getHeight() > bottomScrollView.getBottom()) {
                    dy = dy - (getScrollY() + dy - (bottomScrollView.getBottom() - getHeight()));
                }
                scrollBy(0, dy);
                break;
            case MotionEvent.ACTION_UP:
                isIntercept = false;
                velocityTracker.computeCurrentVelocity(1000);
                float yVelocity = velocityTracker.getYVelocity();

                if (currPosition == 0) {
                    if (yVelocity < 0 && yVelocity < -speed) {
                        smoothScroll(position1Y);
                        currPosition = 1;
                        if (onScrollToCurrPosition != null) {
                            onScrollToCurrPosition.currPosition(currPosition);
                        }
                    } else {
                        smoothScroll(0);
                    }
                } else {
                    if (yVelocity > 0 && yVelocity > speed) {
                        smoothScroll(0);
                        currPosition = 0;
                        onScrollToCurrPosition.currPosition(currPosition);

                    } else {
                        smoothScroll(position1Y);

                    }
                }
                break;
        }
        lastY = y;
        return true;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measureChildren(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();
        int childTop = 0;
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            child.layout(l, childTop, r, childTop + child.getMeasuredHeight());
            childTop += child.getMeasuredHeight();
        }
    }


    //通过Scroller实现弹性滑动
    private void smoothScroll(int tartY) {
        int dy = tartY - getScrollY();
        scroller.startScroll(getScrollX(), getScrollY(), 0, dy);
        invalidate();
    }


    //滚动到顶部
    public void scrollToTop() {
        smoothScroll(0);
        currPosition = 0;
        topScrollView.smoothScrollTo(0, 0);
    }

    @Override
    public void computeScroll() {
        if (scroller.computeScrollOffset()) {
            scrollTo(scroller.getCurrX(), scroller.getCurrY());
            postInvalidate();
        }
    }

    public interface OnScrollToCurrPosition {
        void currPosition(int position);
    }
    public interface OnCanRefresh{
        void isRefresh(boolean boolRefresh);
    }

    public OnCanRefresh getOnCanRefresh() {
        return onCanRefresh;
    }

    public void setOnCanRefresh(OnCanRefresh onCanRefresh) {
        this.onCanRefresh = onCanRefresh;
    }
}
