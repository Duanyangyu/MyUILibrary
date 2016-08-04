package com.test.duan.myuilibrarytest;

import android.content.Intent;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ButtonBarLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.test.duan.myuilibrarytest.activity.AnimationTestAct;
import com.test.duan.myuilibrarytest.activity.AnimationTestActivity;
import com.test.duan.myuilibrarytest.activity.CalendarActivity;
import com.test.duan.myuilibrarytest.activity.CircleAnimActivity;
import com.test.duan.myuilibrarytest.activity.CircleMenuActivity;
import com.test.duan.myuilibrarytest.activity.CoordinatorActivity;
import com.test.duan.myuilibrarytest.activity.FaceActivity;
import com.test.duan.myuilibrarytest.activity.FlexBoxActivity;
import com.test.duan.myuilibrarytest.activity.FoldingCellActivity;
import com.test.duan.myuilibrarytest.activity.GradListActivity;
import com.test.duan.myuilibrarytest.activity.GridViewActivity;
import com.test.duan.myuilibrarytest.activity.GuaKaActivity;
import com.test.duan.myuilibrarytest.activity.GuaKaListActivity;
import com.test.duan.myuilibrarytest.activity.MiaoJieActivity;
import com.test.duan.myuilibrarytest.activity.MotionActivity;
import com.test.duan.myuilibrarytest.activity.MultiHeightActivity;
import com.test.duan.myuilibrarytest.activity.MyViewActivity;
import com.test.duan.myuilibrarytest.activity.ScrollDemoAcitivity;
import com.test.duan.myuilibrarytest.activity.TestActivity;
import com.test.duan.myuilibrarytest.activity.VoiceActivity;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView(){
//        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.snackbar_container);
        findViewById(R.id.btn_calendarView).setOnClickListener(this);
        findViewById(R.id.btn_showsnackbar).setOnClickListener(this);
        findViewById(R.id.btn_gradList).setOnClickListener(this);
        findViewById(R.id.btn_gradView).setOnClickListener(this);
        findViewById(R.id.btn_circle_anim).setOnClickListener(this);
        findViewById(R.id.btn_circle_menu).setOnClickListener(this);
        findViewById(R.id.btn_folding_Cell).setOnClickListener(this);
        findViewById(R.id.btn_test).setOnClickListener(this);
        findViewById(R.id.btn_flexbox).setOnClickListener(this);
        findViewById(R.id.btn_coordinator).setOnClickListener(this);
        findViewById(R.id.btn_scrll_demo).setOnClickListener(this);
        findViewById(R.id.btn_guaguaka).setOnClickListener(this);
        findViewById(R.id.btn_guaguaka_list).setOnClickListener(this);
        findViewById(R.id.btn_anim_test).setOnClickListener(this);
        findViewById(R.id.btn_motion_event).setOnClickListener(this);
        findViewById(R.id.btn_voice).setOnClickListener(this);
        findViewById(R.id.btn_face).setOnClickListener(this);
        findViewById(R.id.btn_view).setOnClickListener(this);
        findViewById(R.id.btn_miaojie).setOnClickListener(this);
        findViewById(R.id.btn_multi_list_act).setOnClickListener(this);
        findViewById(R.id.btn_animation_test).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v != null) {
            int id = v.getId();
            Intent intent = new Intent();
            switch (id){
                case R.id.btn_calendarView:
                    intent.setClass(this,CalendarActivity.class);
                    break;
                case R.id.btn_showsnackbar:

                    break;
                case R.id.btn_gradList:
                    intent.setClass(this, GradListActivity.class);
                    break;
                case R.id.btn_gradView:
                    intent.setClass(this, GridViewActivity.class);
                    break;
                case R.id.btn_circle_anim:
                    intent.setClass(this, CircleAnimActivity.class);
                    break;
                case R.id.btn_circle_menu:
                    intent.setClass(this, CircleMenuActivity.class);
                    break;
                case R.id.btn_folding_Cell:
                    intent.setClass(this, FoldingCellActivity.class);
                    break;
                case R.id.btn_scrll_demo:
                    intent.setClass(this, ScrollDemoAcitivity.class);
                    break;
                case R.id.btn_flexbox:
                    intent.setClass(this, FlexBoxActivity.class);
                    break;
                case R.id.btn_coordinator:
                    intent.setClass(this, CoordinatorActivity.class);
                    break;
                case R.id.btn_guaguaka:
                    Log.d("----","进入刮刮卡页面");
                    intent.setClass(this, GuaKaActivity.class);
                    break;
                case R.id.btn_guaguaka_list:
                    Log.d("----","进入刮刮卡列表页面");
                    intent.setClass(this, GuaKaListActivity.class);
                    break;
                case R.id.btn_anim_test:
                    intent.setClass(this, AnimationTestAct.class);
                    break;
                case R.id.btn_test:
                    intent.setClass(this,TestActivity.class);
                    break;
                case R.id.btn_motion_event:
                    intent.setClass(this, MotionActivity.class);
                    break;
                case R.id.btn_voice:
                    intent.setClass(this, VoiceActivity.class);
                    break;
                case R.id.btn_face:
                    intent.setClass(this, FaceActivity.class);
                    break;
                case R.id.btn_view:
                    intent.setClass(this, MyViewActivity.class);
                    break;
                case R.id.btn_miaojie:
                    intent.setClass(this, MiaoJieActivity.class);
                    break;
                case R.id.btn_multi_list_act:
                    intent.setClass(this, MultiHeightActivity.class);
                    break;
                case R.id.btn_animation_test:
                    intent.setClass(this, AnimationTestActivity.class);
                    break;
            }
            startActivity(intent);
        }
    }

}
