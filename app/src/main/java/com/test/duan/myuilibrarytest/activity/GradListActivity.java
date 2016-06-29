package com.test.duan.myuilibrarytest.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import com.test.duan.myuilibrarytest.R;
import com.test.duan.myuilibrarytest.adapter.DragListAdapter;
import com.test.duan.myuilibrarytest.view.DragListView;

import java.util.ArrayList;
import java.util.List;

public class GradListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grad_list);
        DragListView gradListView = (DragListView) findViewById(R.id.list_gradList);
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            list.add("Android "+i);
        }
        DragListAdapter adapter = new DragListAdapter(this,list);
        gradListView.setAdapter(adapter);
    }

}
