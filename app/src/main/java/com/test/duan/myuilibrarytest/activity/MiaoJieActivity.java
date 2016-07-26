package com.test.duan.myuilibrarytest.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.test.duan.myuilibrarytest.R;
import com.test.duan.myuilibrarytest.adapter.StoreItemAdapter;
import com.test.duan.myuilibrarytest.view.GHCStoreListView;

public class MiaoJieActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_miao_jie);

        GHCStoreListView listView = (GHCStoreListView) findViewById(R.id.store_listview);
        StoreItemAdapter adapter = new StoreItemAdapter(this);
        listView.setAdapter(adapter);


    }


}
