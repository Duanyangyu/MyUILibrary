package com.test.duan.myuilibrarytest.activity;

import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ListViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;

import com.ramotion.foldingcell.FoldingCell;
import com.test.duan.myuilibrarytest.R;
import com.test.duan.myuilibrarytest.foldingcell.FoldingCellAdapter;
import com.test.duan.myuilibrarytest.view.AutoScrollListView;

import java.util.ArrayList;
import java.util.List;

public class FoldingCellActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_folding_cell);
        initView();
    }

    private void initView(){
        AutoScrollListView listView = (AutoScrollListView) findViewById(R.id.list_foldcell);
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add(""+i);
        }
        FoldingCellAdapter adapter = new FoldingCellAdapter(this,list);
        listView.setAdapter(adapter);
    }

    private View viewCreater(){
        View view = LayoutInflater.from(this).inflate(R.layout.item_folding_cell, null);
        final FoldingCell fc = (FoldingCell) view.findViewById(R.id.folding_cell);
        fc.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        fc.toggle(false);
                    }
                }
        );
        return view;
    }
}
