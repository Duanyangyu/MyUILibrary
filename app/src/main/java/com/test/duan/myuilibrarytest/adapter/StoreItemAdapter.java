package com.test.duan.myuilibrarytest.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.test.duan.myuilibrarytest.R;


/**
 * Created by Duanyy on 2015/11/19.
 * 门店页列表用到的适配器，支持首行高度最高。
 */
public class StoreItemAdapter extends BaseAdapter {

    private Context context;

    private boolean isFirst = true;
    private View.OnClickListener listener;
    private int ITEM_HEIGHT;
    private int ITEM_MAX_HEIGHT;

    public StoreItemAdapter(Context context) {
        this.context = context;

    }

    public void setListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    public void setITEM_HEIGHT(int selfHeight) {
        this.ITEM_HEIGHT = selfHeight / 4;
        this.ITEM_MAX_HEIGHT = selfHeight / 2;
    }

    @Override
    public int getCount() {

        return 10;
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
        Log.d("listview",position+"");
        View ret;
        ViewHolder viewHolder = null;
        if (convertView != null) {
            ret = convertView;
            viewHolder = (ViewHolder) ret.getTag();
            viewHolder.iv_StorePhone.setTag(position);
            viewHolder.iv_StoreMap.setTag(position);
        }else {
            ret = LayoutInflater.from(context).inflate(R.layout.item_store,parent,false);
            viewHolder = new ViewHolder();
            ret.setTag(viewHolder);
            viewHolder.iv_StoreImg = (ImageView) ret.findViewById(R.id.iv_cover);
            viewHolder.iv_StorePhone = (ImageView) ret.findViewById(R.id.iv_phone);
            viewHolder.iv_StoreMap = (ImageView) ret.findViewById(R.id.iv_map);
            viewHolder.tv_StoreName = (TextView) ret.findViewById(R.id.tv_name);
            viewHolder.tv_StorePhone = (TextView) ret.findViewById(R.id.tv_phone);
            viewHolder.vg_phone_map = (ViewGroup) ret.findViewById(R.id.vg_phone_map);
            viewHolder.iv_black = (ImageView) ret.findViewById(R.id.iv_black);
            viewHolder.iv_StorePhone.setTag(position);
            viewHolder.iv_StoreMap.setTag(position);
            //绑定监听器
            viewHolder.iv_StorePhone.setOnClickListener(listener);
            viewHolder.iv_StoreMap.setOnClickListener(listener);
        }
        if (position == 0 && isFirst){
            viewHolder.vg_phone_map.setAlpha(1f);
            viewHolder.tv_StorePhone.setAlpha(1f);
//            viewHolder.iv_black.setAlpha(0f);
            viewHolder.tv_StoreName.setTextSize(25);
//            ret.setLayoutParams(new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
//                    (int)context.getResources().getDimension(R.dimen.store_item_max_heigth)));
            ret.setLayoutParams(new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ITEM_MAX_HEIGHT));
        }else if (position != getCount()-1){
            viewHolder.vg_phone_map.setAlpha(0f);
            viewHolder.tv_StorePhone.setAlpha(0f);
//            viewHolder.iv_black.setAlpha(0.5f);
            viewHolder.tv_StoreName.setTextSize(15);
//            ret.setLayoutParams(new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
//                    (int) context.getResources().getDimension(R.dimen.store_item_height)));
            ret.setLayoutParams(new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ITEM_HEIGHT));
        }else if (position == getCount()-1){
            Log.d("listview","到达最后一个item");
            ret.setLayoutParams(new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ITEM_MAX_HEIGHT));
        }

        //设置资源
        viewHolder.iv_StoreImg.setImageResource(R.mipmap.icon_bg);
        viewHolder.iv_StoreImg.setScaleType(ImageView.ScaleType.CENTER_CROP);
        viewHolder.tv_StorePhone.setText("18764895321");
        viewHolder.tv_StoreName.setText("名字");
        return ret;
    }
    class ViewHolder{
        public ImageView iv_StoreImg;
        public ImageView iv_StoreMap;
        public ImageView iv_StorePhone;
        public TextView tv_StorePhone;
        public TextView tv_StoreName;
        public ViewGroup vg_phone_map;
        public ImageView iv_black;
    }
}
