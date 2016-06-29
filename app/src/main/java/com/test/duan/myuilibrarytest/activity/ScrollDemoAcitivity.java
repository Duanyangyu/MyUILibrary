package com.test.duan.myuilibrarytest.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioGroup;

import com.test.duan.myuilibrarytest.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Duanyy on 2016/6/1.
 */
public class ScrollDemoAcitivity extends Activity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {

    private ImageView mImageView;
    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroll_demo);
        findViewById(R.id.btn_down).setOnClickListener(this);
        findViewById(R.id.btn_up).setOnClickListener(this);
        findViewById(R.id._btn_down).setOnClickListener(this);
        findViewById(R.id._btn_up).setOnClickListener(this);
        mImageView = (ImageView) findViewById(R.id.iv_center);
        mListView = (ListView) findViewById(R.id.listview);
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radio_group);
        radioGroup.setOnCheckedChangeListener(this);

        List<String> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add("android "+i);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list);
        mListView.setAdapter(adapter);

    }

    @Override
    public void onClick(View v) {
        if (v != null) {
            int id = v.getId();
            switch (id){
                case R.id.btn_down:
                    if (mScrlloFlag == R.id.btn_scroll_by){
                        mImageView.scrollBy(0,20);
                    }else if (mScrlloFlag == R.id.btn_scroll_to){
                        mImageView.scrollTo(0,20);
                    }
                    break;
                case R.id.btn_up:
                    if (mScrlloFlag == R.id.btn_scroll_by){
                        mImageView.scrollBy(0,-20);
                    }else if (mScrlloFlag == R.id.btn_scroll_to){
                        mImageView.scrollTo(0,-20);
                    }
                    break;
                case R.id._btn_down:
                    if (mScrlloFlag == R.id.btn_scroll_by){
                        mListView.scrollBy(0,20);
                    }else if (mScrlloFlag == R.id.btn_scroll_to){
                        mListView.scrollTo(0,20);
                    }
                    break;
                case R.id._btn_up:
                    if (mScrlloFlag == R.id.btn_scroll_by){
                        mListView.scrollBy(0,-60);
                    }else if (mScrlloFlag == R.id.btn_scroll_to){
                        mListView.scrollTo(0,-60);
                    }
                    break;
            }
        }
    }

    int mScrlloFlag = R.id.btn_scroll_to;

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (checkedId == R.id.btn_scroll_by){
            mScrlloFlag = R.id.btn_scroll_by;
        }else if (checkedId == R.id.btn_scroll_to){
            mScrlloFlag = R.id.btn_scroll_to;
        }
    }
}
