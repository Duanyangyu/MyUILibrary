package com.test.duan.myuilibrarytest.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.test.duan.myuilibrarytest.R;
import com.test.duan.myuilibrarytest.adapter.DragAdapter;
import com.test.duan.myuilibrarytest.view.DragGridView;

import java.util.ArrayList;
import java.util.List;

public class GridViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_view);
        DragGridView dragGridView = (DragGridView) findViewById(R.id.grid_gradview);
        dragGridView.setNumColumns(4);
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 40; i++) {
            list.add("android"+i);
        }
        DragAdapter adapter = new DragAdapter(this,list);
        dragGridView.setAdapter(adapter);
    }
}
