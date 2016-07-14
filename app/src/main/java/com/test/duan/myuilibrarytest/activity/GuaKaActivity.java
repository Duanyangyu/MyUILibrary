package com.test.duan.myuilibrarytest.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.test.duan.myuilibrarytest.R;
import com.test.duan.myuilibrarytest.view.GuaGuaKaView;

public class GuaKaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gua_ka);

        GuaGuaKaView guaGuaKaView = (GuaGuaKaView) findViewById(R.id.guaguaka);
        guaGuaKaView.setOnGuaGuaKaCompleteListener(
                new GuaGuaKaView.OnGuaGuaKaCompleteListener() {
                    @Override
                    public void complete() {
                        Toast.makeText(GuaKaActivity.this,"完了",Toast.LENGTH_SHORT).show();
                    }
                }
        );

    }
}
