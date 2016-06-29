package com.test.duan.myuilibrarytest.activity;

import android.animation.ObjectAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.test.duan.myuilibrarytest.R;

public class AnimationTestAct extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation_test);
        findViewById(R.id.button).setOnClickListener(this);
        findViewById(R.id.button2).setOnClickListener(this);
        findViewById(R.id.button3).setOnClickListener(this);
    }

    private void animCreater() {
        View view = findViewById(R.id.button);
//        ObjectAnimator animator = ObjectAnimator.ofInt(view, "translationY", -10);
//        animator.start();
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.translate_test);
        animation.setDuration(2000);
        view.startAnimation(animation);

    }

    private void animRotateCreater() {
        View view = findViewById(R.id.button4);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.rotate_test);
        view.startAnimation(animation);

    }


    @Override
    public void onClick(View v) {
        if (v != null) {
            int id = v.getId();
            switch (id) {
                case R.id.button2:
                    animCreater();
                    break;

                case R.id.button3:
                    animRotateCreater();
                    break;

                case R.id.button:
                    Toast.makeText(this, "动画View被点击了", Toast.LENGTH_SHORT).show();
                    break;
            }
        }

    }
}
