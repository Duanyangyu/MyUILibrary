package com.test.duan.myuilibrarytest.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.test.duan.myuilibrarytest.R;

import java.util.Collections;
import java.util.List;

/**
 * Created by Duanyy on 2016/5/5.
 */
public class DragListAdapter extends BaseAdapter implements DragBaseAdapter {

    private Context mContext;
    private List<String> mList;

    private int mHidePosition = -1;

    public DragListAdapter(Context mContext, List<String> mList) {
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
        TextView textView ;
        if (convertView == null){
            ret = LayoutInflater.from(mContext).inflate(R.layout.item_drag_listview,parent,false);
            textView = (TextView) ret.findViewById(R.id.tv_content);
            ret.setTag(textView);
        }else {
            ret = convertView;
            textView = (TextView) ret.getTag();
        }
        textView.setText(mList.get(position));

        if (position == mHidePosition){
            ret.setVisibility(View.INVISIBLE);
        }else {
            ret.setVisibility(View.VISIBLE);
        }

        return ret;
    }

    @Override
    public void hideItemAt(int positon) {
        this.mHidePosition = positon;
        notifyDataSetChanged();
    }

    //操作数据源集合，交换两个位置的元素
    @Override
    public void reorderItem(int oldPosition, int newPosition) {
        String temp = mList.get(oldPosition);
        if (oldPosition < newPosition){
            for (int i = oldPosition; i < newPosition ; i++) {
                Collections.swap(mList,i,i+1);
            }
        }
        if (oldPosition > newPosition){
            for (int i = oldPosition; i > newPosition ; i--) {
                Collections.swap(mList,i,i-1);
            }
        }
        mList.set(newPosition,temp);
        notifyDataSetChanged();
    }
}
