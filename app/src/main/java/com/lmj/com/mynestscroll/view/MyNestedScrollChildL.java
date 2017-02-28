package com.lmj.com.mynestscroll.view;

import android.content.Context;
import android.support.v4.view.NestedScrollingChild;
import android.support.v4.view.NestedScrollingChildHelper;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.LinearLayout;

public class MyNestedScrollChildL extends LinearLayout implements NestedScrollingChild {
    private String Tag = "MyNestedScrollChild";
    private NestedScrollingChildHelper mScrollingChildHelper;
    private final int[] mScrollOffset = new int[2];
    private final int[] mScrollConsumed = new int[2];
    private final int[] mNestedOffsets = new int[2];
    private int mLastTouchX;
    private int mScrollPointerId;
    private int mLastTouchY;
    private int mTouchSlop;
    private boolean mIsBeingDragged;
    private int showHeight;

    public MyNestedScrollChildL(Context context) {
        super(context);
        init(context);
    }

    public MyNestedScrollChildL(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }


    public void init(Context context) {
        final ViewConfiguration configuration = ViewConfiguration.get(context);
        mTouchSlop = configuration.getScaledTouchSlop();
    }

    @Override
    public void setNestedScrollingEnabled(boolean enabled) {
        Log.e("Bill", "NestedScrollingChild setNestedScrollingEnabled:" + enabled);
        getScrollingChildHelper().setNestedScrollingEnabled(enabled);
    }

    @Override
    public boolean isNestedScrollingEnabled() {
        Log.e("Bill", "NestedScrollingChild isNestedScrollingEnabled");
        return getScrollingChildHelper().isNestedScrollingEnabled();
    }

    @Override
    public boolean startNestedScroll(int axes) {
        Log.e("Bill", "NestedScrollingChild startNestedScroll :" + axes);
        return getScrollingChildHelper().startNestedScroll(axes);
    }

    @Override
    public void stopNestedScroll() {
        Log.e("Bill", "NestedScrollingChild stopNestedScroll");
        getScrollingChildHelper().stopNestedScroll();
    }

    @Override
    public boolean hasNestedScrollingParent() {
        Log.e("Bill", "NestedScrollingChild hasNestedScrollingParent");
        return getScrollingChildHelper().hasNestedScrollingParent();
    }

    @Override
    public boolean dispatchNestedScroll(int dxConsumed, int dyConsumed, int dxUnconsumed,
                                        int dyUnconsumed, int[] offsetInWindow) {
        Log.e("Bill", "NestedScrollingChild dispatchNestedScroll:dxConsumed:" + dxConsumed + "," +
                "dyConsumed:" + dyConsumed + ",dxUnconsumed:" + dxUnconsumed + ",dyUnconsumed:" +
                dyUnconsumed + ",offsetInWindow:" + offsetInWindow);
        return getScrollingChildHelper().dispatchNestedScroll(dxConsumed, dyConsumed,
                dxUnconsumed, dyUnconsumed, offsetInWindow);
    }

    @Override
    public boolean dispatchNestedPreScroll(int dx, int dy, int[] consumed, int[] offsetInWindow) {
        Log.e("Bill", "NestedScrollingChild dispatchNestedPreScroll:dx" + dx + ",dy:" + dy + ",consumed:" + consumed + ",offsetInWindow:" + offsetInWindow);
        return getScrollingChildHelper().dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow);
    }

    @Override
    public boolean dispatchNestedFling(float velocityX, float velocityY, boolean consumed) {
        Log.e("Bill", "NestedScrollingChild dispatchNestedFling:velocityX:" + velocityX + ",velocityY:" + velocityY + ",consumed:" + consumed);
        return getScrollingChildHelper().dispatchNestedFling(velocityX, velocityY, consumed);
    }

    @Override
    public boolean dispatchNestedPreFling(float velocityX, float velocityY) {
        Log.e("Bill", "NestedScrollingChild dispatchNestedPreFling:velocityX:" + velocityX + ",velocityY:" + velocityY);
        return getScrollingChildHelper().dispatchNestedPreFling(velocityX, velocityY);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:

            {

                mLastTouchY = (int) (e.getRawY() + 0.5f);
                int nestedScrollAxis = ViewCompat.SCROLL_AXIS_NONE;

                nestedScrollAxis |= ViewCompat.SCROLL_AXIS_HORIZONTAL;
                startNestedScroll(nestedScrollAxis);
            }
            break;
            case MotionEvent.ACTION_MOVE: {
                Log.i("aaa", "Child--getRawY:" + e.getRawY());

                int x = (int) (e.getX() + 0.5f);
                int y = (int) (e.getRawY() + 0.5f);
                int dx = mLastTouchX - x;
                int dy = mLastTouchY - y;
                Log.i(Tag, "child:dy:" + dy + ",mLastTouchY:" + mLastTouchY + ",y;" + y);
                Log.i(Tag, "xxx");
                mLastTouchY = y;
                mLastTouchX = x;
                if (dispatchNestedPreScroll(dx, dy, mScrollConsumed, mScrollOffset)) {

                    dy -= mScrollConsumed[1];
                    if (dy == 0) {
                        return true;
                    }

                } else {
                    scrollBy(0, dy);

                }
            }


        }

        return true;
    }

    private NestedScrollingChildHelper getScrollingChildHelper() {
        if (mScrollingChildHelper == null) {
            mScrollingChildHelper = new NestedScrollingChildHelper(this);
            mScrollingChildHelper.setNestedScrollingEnabled(true);
        }
        return mScrollingChildHelper;
    }

    @Override
    public void scrollTo(int x, int y) {

        int sy = getScrollY();
        int mh = getMeasuredHeight();
        int MaxY = getMeasuredHeight() - showHeight;
        if (y > MaxY) {
            y = MaxY;
        }
        if (y < 0) {
            y = 0;
        }
        super.scrollTo(x, y);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //第一次测量，因为布局文件中高度是wrap_content，因此测量模式为ATMOST，即高度不能超过父控件的剩余空间
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        showHeight = getMeasuredHeight();

        //第二次测量，对高度没有任何限制，那么测量出来的就是完全展示内容所需要的高度
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}