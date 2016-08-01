package com.test.duan.myuilibrarytest.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.test.duan.myuilibrarytest.R;

/**
 * Created by Duanyy on 2016/8/1.
 */
public class MultiHeightAdapter extends BaseAdapter {

    private Context mContext;

    public MultiHeightAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return 30;
    }

    @Override
    public Object getItem(int position) {
        return 0;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View ret;
        ret = LayoutInflater.from(mContext).inflate(R.layout.item_store,parent,false);
        ((TextView)ret.findViewById(R.id.tv_name)).setText(getItemId(position)+"");

        return ret;
    }
}