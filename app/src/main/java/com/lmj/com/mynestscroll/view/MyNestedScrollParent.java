package com.lmj.com.mynestscroll.view;

import android.content.Context;
import android.support.v4.view.NestedScrollingParent;
import android.support.v4.view.NestedScrollingParentHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.RelativeLayout;
import android.widget.Scroller;
import android.widget.TextView;

public class MyNestedScrollParent extends RelativeLayout implements NestedScrollingParent {
    private String Tag = "MyNestedScrollParent";
//    private TextView tv;
    private int titleRowNum;
    private MyNestedScrollChildL nsc0;
    private MyNestedScrollChildL nsc1;
    private NestedScrollingParentHelper mParentHelper;
//    private int tvHeight;

    private int length = (int) TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, 100, getContext().getResources().getDisplayMetrics()
    ); // 半径

    private Scroller scroller;

    public MyNestedScrollParent(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyNestedScrollParent(Context context) {
        super(context);
        init();
    }

    @Override
    public void computeScroll() {
        if (scroller.computeScrollOffset()) {
            int newY = scroller.getCurrY();
            scrollTo(0, newY);
            invalidate();
        }

    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
//        tv = (TextView) getChildAt(1);
        nsc0 = (MyNestedScrollChildL) getChildAt(1);
        nsc1 = (MyNestedScrollChildL) getChildAt(2);
       /* tv.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if(tvHeight<=0){
                    tvHeight =  tv.getMeasuredHeight();
                    titleRowNum = tv.getLineCount();
                    Log.e("Bill", "tvHeight:" + tvHeight + "|titleRowNum:" + titleRowNum);
                }
            }
        });*/



    }

    @Override
    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
        Log.e("Bill", "NestedScrollingParent onStartNestedScroll");
        return true;
    }
    private void init() {
        mParentHelper = new NestedScrollingParentHelper(this);
        scroller = new Scroller(getContext());

    }

    @Override
    public void onNestedScrollAccepted(View child, View target, int nestedScrollAxes) {
        Log.e("Bill", "NestedScrollingParent onNestedScrollAccepted"+"child:"+child+",target:"+target+",nestedScrollAxes:"+nestedScrollAxes);
        mParentHelper.onNestedScrollAccepted(child, target, nestedScrollAxes);
    }

    @Override
    public void onStopNestedScroll(View target) {
        Log.e("Bill", "NestedScrollingParent onStopNestedScroll--target:"+target);
        mParentHelper.onStopNestedScroll(target);
    }

    @Override
    public void onNestedScroll(View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        Log.e("Bill", "NestedScrollingParent onNestedScroll--"+"target:"+target+",dxConsumed"+dxConsumed+",dyConsumed:"+dyConsumed
        +",dxUnconsumed:"+dxUnconsumed+",dyUnconsumed:"+dyUnconsumed);
    }

    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {
       if(dy > 0 && getScrollY() < length){ // 上拉
           consumed[1]=length;
            scroller.startScroll(0, getScrollY(), 0, length);
            invalidate();
        }else if(dy < 0 && getScrollY()>0){ // 下滑
           if(target == nsc0 || (target == nsc1 && nsc1.getScrollY() == 0)){
               consumed[1]=length;
               scroller.startScroll(0, getScrollY(), 0, -length);
               invalidate();
           }
        }
        int num = -1;
        if(target == nsc0){
            num = 1;
        }else if(target == nsc1){
            num = 2;
        }
        Log.e("Bill3", "nsc0:::" + nsc0.getScrollY() + "nsc1:" + nsc1.getScrollY() + "|view:" + num);


        /*if(showImg(dy)||hideImg(dy)){//如果父亲自己要滑动，则拦截
            consumed[1]=dy;
            scrollBy(0,dy);
//            int distanceY = dy;
//            scroller.startScroll(0, getScrollY(), 0, distanceY);
//            invalidate();
            Log.i("onNestedPreScroll","Parent滑动："+dy);
        }*/
        Log.e("Bill2", "NestedScrollingParent onNestedPreScroll--getScrollY():"+getScrollY()+",dx:"+dx+",dy:"+dy+",consumed:"+nsc1.getScrollY());
    }

    @Override
    public boolean onNestedFling(View target, float velocityX, float velocityY, boolean consumed) {
        Log.e("Bill", "NestedScrollingParent onNestedFling--target:"+target);
        return false;
    }

    @Override
    public boolean onNestedPreFling(View target, float velocityX, float velocityY) {
        Log.e("Bill", "NestedScrollingParent onNestedPreFling--target:"+target);
        return false;
    }

    @Override
    public int getNestedScrollAxes() {
        Log.e("Bill", "NestedScrollingParent getNestedScrollAxes");
        return 0;
    }

    @Override
    public void scrollTo(int x, int y) {
        if(y<0){
            y=0;
        }
        if(y>length){
            y=length;
        }

        super.scrollTo(x, y);
    }

    /**
    下拉的时候是否要向下滑动显示图片
     */
    public boolean showImg(int dy){
        if(dy<0){
            if(getScrollY()>0&&nsc1.getScrollY()==0){//如果parent外框，还可以往上滑动
                return true;
            }
        }


        return false;
    }

    /**
     * 上拉的时候，是否要向上滑动，隐藏图片
     * @return
     */
    public boolean hideImg(int dy){
        if(dy>0){
            if(getScrollY()<length){//如果parent外框，还可以往下滑动
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        return super.onTouchEvent(event);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        Log.i("aaa","getY():getRawY:"+event.getRawY());
        return super.dispatchTouchEvent(event);
    }
}
