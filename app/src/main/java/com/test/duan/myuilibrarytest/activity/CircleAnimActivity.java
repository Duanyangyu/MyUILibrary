package com.test.duan.myuilibrarytest.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.test.duan.myuilibrarytest.R;
import com.test.duan.myuilibrarytest.view.RingView;

import java.util.ArrayList;
import java.util.List;

public class CircleAnimActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle_anim);
        RingView ringView = (RingView) findViewById(R.id.ringview);
        List<View> list = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setBackground(getResources().getDrawable(R.drawable.shape_circle_red));
            list.add(imageView);
        }
        ringView.addButton(list);
    }
}
