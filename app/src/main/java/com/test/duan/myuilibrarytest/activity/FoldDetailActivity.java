package com.test.duan.myuilibrarytest.activity;

import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.kogitune.activity_transition.ActivityTransition;
import com.kogitune.activity_transition.ExitActivityTransition;
import com.test.duan.myuilibrarytest.R;

public class FoldDetailActivity extends FragmentActivity {

    private ExitActivityTransition exitTransition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fold_detail);
        View shareView = findViewById(R.id.view_share_2);
        shareView.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(FoldDetailActivity.this,"kdlfk",Toast.LENGTH_SHORT).show();
                    }
                }
        );
        ActivityTransition.with(getIntent()).to(findViewById(R.id.view_share_2)).start(savedInstanceState);
//        setExitSharedElementCallback();
//        exitTransition = ActivityTransition.with(getIntent()).to(findViewById(R.id.view_share_2)).start(savedInstanceState);

    }

}
