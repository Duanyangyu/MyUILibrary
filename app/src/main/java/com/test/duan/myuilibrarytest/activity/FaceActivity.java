package com.test.duan.myuilibrarytest.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.widget.FrameLayout;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Duanyy on 2016/7/5.
 */
public class FaceActivity extends FragmentActivity {

    private FrameLayout mFramlayout;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();

    }

    private void initView(){
        mFramlayout = new FrameLayout(this);
        mViewPager = new ViewPager(this);
        mFramlayout.addView(mViewPager);
        setContentView(mFramlayout);
    }

    private void getString(){

        List<String> mStringList = new ArrayList<>();
        try {
            InputStream is = getResources().getAssets().open("emojikf.txt");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is,"utf-8"));
            String str = null;

            while ((str = bufferedReader.readLine()) != null){
                mStringList.add(str);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initFaceMap(List<String> list){

        if (list == null || list.size() == 0){
            return;
        }

        for (String s : list){
            String[] text = s.split(",");
        }

    }




}
