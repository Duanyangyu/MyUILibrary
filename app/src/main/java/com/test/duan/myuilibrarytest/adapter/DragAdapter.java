package com.test.duan.myuilibrarytest.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.test.duan.myuilibrarytest.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Duanyy on 2016/4/22.
 */
public class DragAdapter extends BaseAdapter implements DragBaseAdapter{

    private Context context;
    private List<String> list;

    private int mHidePosition = -1;

    public DragAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.item_gridview, null);
        ImageView mImageView = (ImageView) convertView.findViewById(R.id.img_gridView);
        TextView mTextView = (TextView) convertView.findViewById(R.id.tv_gridview);

        mTextView.setText(list.get(position));
        mImageView.setImageResource(R.mipmap.ic_launcher);

        if(position == mHidePosition){
            convertView.setVisibility(View.INVISIBLE);
        }

        return convertView;
    }


    @Override
    public void hideItemAt(int positon) {
        this.mHidePosition = positon;
    }

    //对两个position位置的元素进行位置对调
    @Override
    public void reorderItem(int oldPosition, int newPosition) {
//        List<String> newList = new ArrayList<>();
//        int size = this.list.size();
//        for (int i = 0; i < size; i++) {
//            if (i == oldPosition){
//                newList.add(oldPosition,this.list.get(oldPosition));
//                continue;
//            }
//            if (i == newPosition){
//                newList.add(newPosition,this.list.get(newPosition));
//                continue;
//            }
//            newList.add(i,this.list.get(i));
//        }
//        this.list = null;
//        this.list = newList;
//        notifyDataSetChanged();

        String temp = list.get(oldPosition);
        if (oldPosition < newPosition){
            for (int i = oldPosition; i < newPosition ; i++) {
                Collections.swap(list,i,i+1);
            }
        }
        if (oldPosition > newPosition){
            for (int i = oldPosition; i > newPosition ; i--) {
                Collections.swap(list,i,i-1);
            }
        }
        list.set(newPosition,temp);
        notifyDataSetChanged();
    }
}
