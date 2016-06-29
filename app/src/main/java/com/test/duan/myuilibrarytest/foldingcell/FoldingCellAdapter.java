package com.test.duan.myuilibrarytest.foldingcell;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.kogitune.activity_transition.ActivityTransitionLauncher;
import com.ramotion.foldingcell.FoldingCell;
import com.test.duan.myuilibrarytest.R;
import com.test.duan.myuilibrarytest.activity.FoldDetailActivity;
import com.test.duan.myuilibrarytest.view.StarView;

import java.util.List;
import java.util.Random;

/**
 * Created by Duanyy on 2016/5/11.
 */
public class FoldingCellAdapter extends BaseAdapter {

    private Context mContext;
    private List<String> mList;

    public FoldingCellAdapter(Context mContext, List<String> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View ret = null;
        if (convertView == null){
            ret = LayoutInflater.from(mContext).inflate(R.layout.item_folding_cell,parent,false);
        }else {
            ret = convertView;
        }

        Random random = new Random();

        StarView starView_1 = (StarView) ret.findViewById(R.id.starview_1);
        StarView starView_2 = (StarView) ret.findViewById(R.id.starview_2);
//        starView.setStarLevel(random.nextInt(11));
        int level = random.nextInt(11);
        Log.d("====","level:  "+ level);
        starView_1.setStarLevel(level);
        starView_2.setStarLevel(level);

        final FoldingCell fc = (FoldingCell) ret.findViewById(R.id.folding_cell);
        fc.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        fc.toggle(false);
                    }
                }
        );

        final View shareView = ret.findViewById(R.id.view_share_1);

        ret.findViewById(R.id.btn_jump).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mContext, FoldDetailActivity.class);
                        ActivityTransitionLauncher.with((Activity) mContext).from(shareView).launch(intent);
                    }
                }
        );

        return ret;
    }
}
