package com.test.duan.myuilibrarytest.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.test.duan.myuilibrarytest.R;
import com.test.duan.myuilibrarytest.view.GHCLoadAnim;
import com.test.duan.myuilibrarytest.view.ServiceView;

public class TestActivity extends AppCompatActivity {


    private GHCLoadAnim ghcLoadAnim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ghcLoadAnim = (GHCLoadAnim) findViewById(R.id.ghcloadanim);
        edittest();

    }

    int i = 0;

    private void edittest() {
        EditText editText = (EditText) findViewById(R.id.edittext_test);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                System.out.println("beforeTextChanged,  "+i++);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                System.out.println("onTextChanged,  "+i++);
            }

            @Override
            public void afterTextChanged(Editable s) {
                System.out.println("afterTextChanged,  "+i++);
            }
        });
    }


    public void btnStart(View view) {
        ghcLoadAnim.start();
    }

    public void btnStop(View view) {
        ghcLoadAnim.stop();
    }

}
