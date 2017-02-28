package com.lmj.com.mynestscroll;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lmj.com.mynestscroll.view.MyNestedScrollParent;

public class MainActivity extends AppCompatActivity {

    private MyNestedScrollParent parent;
    private TextView titleText;
    private int lineNum;

    private int defaultHeight;
    private int diffHeight;

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

        parent = (MyNestedScrollParent) findViewById(R.id.parent);
        titleText = (TextView) findViewById(R.id.tv_title);
        titleText.setText(title);

        titleText.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if(lineNum <= 0){
                    lineNum = titleText.getLineCount();
                    Log.e("Bill", "lineNum:::" + lineNum + "|titleHeight:" + titleText.getHeight() + "|:" + parent.getHeight());

                    int height = defaultHeight + diffHeight*(lineNum-1);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);
                    parent.setLayoutParams(params);

                }
            }
        });

    }
}
