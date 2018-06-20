package com.lmj.com.mynestscroll;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.lmj.com.mynestscroll.view2.ImageBottomLayout;

/**
 * Created by Bill on 2017/2/28.
 */

public class SecondActivity extends AppCompatActivity {

    private ImageBottomLayout mBottomLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        mBottomLayout = (ImageBottomLayout) findViewById(R.id.bottom_layout);
        mBottomLayout.setTitle("这是一个Title");
        mBottomLayout.setContent(getString(R.string.large_text));
        mBottomLayout.invalidate();
        /*mBottomLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                mBottomLayout.setContent(getString(R.string.large_text));
            }
        }, 20);*/

    }

}
