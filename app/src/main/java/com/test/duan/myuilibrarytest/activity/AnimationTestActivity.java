package com.test.duan.myuilibrarytest.activity;

import android.animation.ObjectAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.test.duan.myuilibrarytest.R;

public class AnimationTestActivity extends AppCompatActivity {

    private View mTargetView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation_test2);
        mTargetView = findViewById(R.id.target_view);


    }

    public void btnUp(View view) {

        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(mTargetView,"translationY",mTargetView.getTop(),-100);
        objectAnimator.setDuration(600);
        objectAnimator.start();
    }

    public void btnDown(View view) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(mTargetView,"translationY",0,100);
        objectAnimator.setDuration(600);
        objectAnimator.start();
    }
}
