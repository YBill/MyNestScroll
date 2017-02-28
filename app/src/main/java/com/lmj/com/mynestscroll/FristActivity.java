package com.lmj.com.mynestscroll;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by Bill on 2017/2/28.
 */

public class FristActivity extends AppCompatActivity implements View.OnClickListener {

    private Button oneTitleBtn;
    private Button twoTitleBtn;
    private Button threeTitleBtn;
    private Button fourTitleBtn;
    private Button fiveTitleBtn;
    private Button sixTitleBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frist);
        oneTitleBtn = (Button) findViewById(R.id.btn_one);
        twoTitleBtn = (Button) findViewById(R.id.btn_two);
        threeTitleBtn = (Button) findViewById(R.id.btn_three);
        fourTitleBtn = (Button) findViewById(R.id.btn_four);
        fiveTitleBtn = (Button) findViewById(R.id.btn_five);
        sixTitleBtn = (Button) findViewById(R.id.btn_six);
        oneTitleBtn.setOnClickListener(this);
        twoTitleBtn.setOnClickListener(this);
        threeTitleBtn.setOnClickListener(this);
        fourTitleBtn.setOnClickListener(this);
        fiveTitleBtn.setOnClickListener(this);
        sixTitleBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v == oneTitleBtn) {
            String title = "我是一个标题";
            gotoActivity(title);
        } else if (v == twoTitleBtn) {
            String title = "我是一个标题我是一个标题我是一个标题我是一个标题我是一个标题我是一个标题";
            gotoActivity(title);
        } else if (v == threeTitleBtn) {
            String title = "我是一个标题我是一个标题我是一个标题我是一个标题我是一个标题我是一个标题我是一个标题我是一个标题我是一个标题";
            gotoActivity(title);
        } else if (v == fourTitleBtn) {
            String title = "我是一个标题我是一个标题我是一个标题我是一个标题我是一个标题我是一个标题我是一个标题我是一个标题我是一个标题我是一个标题我是一个标题我是一个标题";
            gotoActivity(title);
        } else if (v == fiveTitleBtn) {
            String title = "我是一个标题我是一个标题我是一个标题我是一个标题我是一个标题我是一个标题我是一个标题我是一个标题我是一个标题我是一个标题我是一个标题我是一个标题我是一个标题我是一个标题我是一个标题我是一个标题我是一个标题";
            gotoActivity(title);
        } else if (v == sixTitleBtn) {
            String title = "我是一个标题我是一个标题我是一个标题我是一个标题我是一个标题我是一个标题我是一个标题我是一个标题我是一个标题我是一个标题我是一个标题我是一个标题我是一个标题我是一个标题我是一个标题我是一个标题我是一个标题我是一个标题";
            gotoActivity(title);
        }

    }

    private void gotoActivity(String title){
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("title", title);
        startActivity(intent);
    }


}
