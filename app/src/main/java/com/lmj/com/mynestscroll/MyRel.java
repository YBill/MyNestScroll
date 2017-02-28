package com.lmj.com.mynestscroll;

import android.content.Context;
import android.support.v4.view.NestedScrollingChild;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * Created by Bill on 2017/2/28.
 */

public class MyRel extends RelativeLayout implements NestedScrollingChild {
    public MyRel(Context context) {
        super(context);
    }

    public MyRel(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyRel(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
