package com.lmj.com.mynestscroll;

import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lmj.com.mynestscroll.view.MyNestedScrollParent;

public class MainActivity extends AppCompatActivity {

    private MyNestedScrollParent parent;
    private TextView contentText;
    private TextView titleText;
    private int lineNum;

    private int defaultHeight;
    private int diffHeight;

    private View bottonView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        defaultHeight = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 207, this.getResources().getDisplayMetrics()
        );
        diffHeight = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 22, this.getResources().getDisplayMetrics()
        );


        String title = getIntent().getStringExtra("title");
        String content = getIntent().getStringExtra("content");

        bottonView = findViewById(R.id.view_bottom);
        parent = (MyNestedScrollParent) findViewById(R.id.parent);
        parent.setOnSlideListener(new MyNestedScrollParent.onSlideListener() {
            @TargetApi(Build.VERSION_CODES.HONEYCOMB)
            @Override
            public void slideFinish(int flag) {
                if(flag == 1){

                    if(bottonView.getAlpha() != 1)
                        bottonView.setAlpha(1);
                    /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                        bottonView.setAlpha(1L);
                    }*/
//                    if(bottonView.getVisibility() != View.VISIBLE)
//                        bottonView.setVisibility(View.VISIBLE);
                }else{
                    /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                        bottonView.setAlpha(0L);
                    }*/
                    if(bottonView.getAlpha() != 0){
                        ObjectAnimator anim2 = ObjectAnimator.ofFloat(bottonView, "alpha",  1, 0);
                        anim2.setDuration(400);
                        anim2.start();

                    }

//                    ValueAnimator colorAnim = ObjectAnimator.ofInt(bottonView,"backgroundColor", 0xa0000000, 0x00000000);
//                    colorAnim.setDuration(500);
//                    colorAnim.setEvaluator(new ArgbEvaluator());
//                    colorAnim.start();


//                    ObjectAnimator animator = ObjectAnimator.ofFloat(bottonView, "alpha", 1f, 0f);
//                    animator.setDuration(200);
//                    animator.start();
                    /*new Handler(){
                        @Override
                        public void handleMessage(Message msg) {
                            ObjectAnimator animator = ObjectAnimator.ofFloat(bottonView, "alpha", 1f, 0f);
                            animator.setDuration(200);
                            animator.start();


//                            if(bottonView.getVisibility() != View.INVISIBLE)
//                                bottonView.setVisibility(View.INVISIBLE);
                        }
                    }.sendEmptyMessageDelayed(0, 200);*/

                }
            }
        });

        contentText = (TextView) findViewById(R.id.tv_content);
        titleText = (TextView) findViewById(R.id.tv_title);
        titleText.setText(title);
        contentText.setText(content);

        titleText.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if(lineNum <= 0){
                    lineNum = titleText.getLineCount();
                    Log.e("Bill", "lineNum:::" + lineNum + "|titleHeight:" + titleText.getHeight() + "|:" + parent.getHeight());

                    int height = defaultHeight + diffHeight*(lineNum-1);
                    FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);
                    parent.setLayoutParams(params);

                }
            }
        });

    }
}
