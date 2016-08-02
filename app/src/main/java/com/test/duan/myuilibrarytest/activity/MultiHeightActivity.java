package com.test.duan.myuilibrarytest.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.test.duan.myuilibrarytest.R;
import com.test.duan.myuilibrarytest.adapter.MultiHeightAdapter;
import com.test.duan.myuilibrarytest.view.MultiHeightListView;

public class MultiHeightActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_height);

        MultiHeightListView multiHeightListView =
                (MultiHeightListView) findViewById(R.id.multi_height_listview);

        MultiHeightAdapter adapter = new MultiHeightAdapter(this);
        adapter.setmListView(multiHeightListView);

        multiHeightListView.setAdapter(adapter);

    }



}
