package com.lmj.com.mynestscroll.view2;

import android.content.Context;
import android.graphics.RectF;
import android.text.method.ScrollingMovementMethod;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lmj.com.mynestscroll.R;

/**
 * Created by Bill on 2017/4/12.
 * 仿网易新闻图片新闻详情底部Layout
 */

public class ImageBottomLayout extends LinearLayout {

    private float lastY = 0;
    private int top;
    private int bottom;
    private TextView mContentView;
    private TextView mTitleView;
    private TextView pageIndex;
    private TextView pageSize;
    private int botTop = 0;
    private int botBottom = 0;
    private int maxBottom;
    private Context context;

    public ImageBottomLayout(Context context) {
        super(context);
        initView(context, null);
    }

    public ImageBottomLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    public ImageBottomLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    /**
     * 初始化View
     *
     * @param context
     * @param attrs
     */
    private void initView(Context context, AttributeSet attrs) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View mView = inflater.inflate(R.layout.bottom_layout, this, true);

        mTitleView = (TextView) mView.findViewById(R.id.act_image_title);
        mContentView = (TextView) mView.findViewById(R.id.bot_content);
        pageIndex = (TextView) mView.findViewById(R.id.act_img_index);
        pageSize = (TextView) mView.findViewById(R.id.act_img_size);

        mContentView.setMovementMethod(new ScrollingMovementMethod());
        this.context = context;
    }

    /**
     * 设置标题
     *
     * @param title
     */
    public void setTtitle(String title) {
        mTitleView.setText(title);
    }

    /**
     * 设置页数
     *
     * @param index
     * @param size
     */
    public void setPage(String index, String size) {
        pageIndex.setText(index);
        pageSize.setText(size);
    }

    /**
     * 设置内容
     *
     * @param content
     */
    private boolean isChanged;

    public void setContent(final String content) {
        setPage("1", "/5");
        mContentView.setText(content);

        mContentView.scrollTo(0, 0);

        isChanged = false;
        post(new Runnable() {
            @Override
            public void run() {
                if (!isChanged) {
                    layout(getLeft(), botTop, getRight(), botBottom);
                }
            }
        });

        //添加全局布局侦听器
        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                isChanged = true;
                maxBottom = getBottom();
                botTop = getTop() + (mContentView.getHeight() - mContentView.getMinHeight());
                botBottom = getBottom() + (mContentView.getHeight() - mContentView.getMinHeight());
                layout(getLeft(), botTop, getRight(), botBottom);

                getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });

    }

    /**
     * 计算指定的 View 在屏幕中的坐标。
     */
    public static RectF calcViewScreenLocation(View view) {
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        return new RectF(location[0], location[1], location[0] + view.getWidth(),
                location[1] + view.getHeight());
    }

    /**
     * 分发事件
     *
     * @param event
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastY = event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                //移动的距离
                float distanceY = event.getRawY() - lastY;
                boolean isMove = true;
                if (distanceY > 0) {
                    RectF rect = calcViewScreenLocation(mTitleView);
                    boolean isInViewRect = rect.contains(event.getRawX(), event.getRawY());
                    if (isInViewRect) {
                        isMove = true;
                    } else if (mContentView.getScrollY() > 0) {
                        isMove = false;
                    }
                }

                if (isMove) {
                    top = (int) (getTop() + distanceY);
                    bottom = (int) (getBottom() + distanceY);
                    if (top < 0) {
                        top = 0;
                        bottom = getHeight();
                    }
                    if (bottom > botBottom) {
                        bottom = botBottom;
                        top = botBottom - getHeight();
                    }

                    if (bottom < maxBottom) {
                        bottom = maxBottom;
                        top = maxBottom - getHeight();
                    }
                }

                layout(getLeft(), top, getRight(), bottom);

                lastY = event.getRawY();


                break;
            case MotionEvent.ACTION_POINTER_UP:
            case MotionEvent.ACTION_CANCEL:
                break;
        }
        return super.dispatchTouchEvent(event);
    }

}
