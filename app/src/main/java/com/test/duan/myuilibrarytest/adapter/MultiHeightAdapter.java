package com.test.duan.myuilibrarytest.adapter;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.test.duan.myuilibrarytest.R;
import com.test.duan.myuilibrarytest.view.MultiHeightListView;

import org.xutils.x;

/**
 * Created by Duanyy on 2016/8/1.
 */
public class MultiHeightAdapter extends BaseAdapter {

    private Context mContext;
    private MultiHeightListView mListView;

    private static final String TAG = "MultiHeightAdapter";

    public MultiHeightAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setmListView(MultiHeightListView mListView) {
        this.mListView = mListView;
    }

    @Override
    public int getCount() {
        return 8;
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

        Log.d(TAG,"getView 填充布局:  "+getItemId(position));

        View ret = LayoutInflater.from(mContext).inflate(R.layout.item_store,parent,false);

        ImageView imageView = (ImageView) ret.findViewById(R.id.iv_cover);
        x.image().bind(imageView,"http://www.pptbz.com/pptpic/UploadFiles_6909/201110/20111014111307895.jpg");
        ((TextView)ret.findViewById(R.id.tv_name)).setText(getItemId(position)+"");

        return ret;
    }

    private void playAnimation(final int start, final int end, final View targetView){
        final ValueAnimator animator = ValueAnimator.ofInt(start,end).setDuration(5000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float fraction = animator.getAnimatedFraction();
                targetView.scrollTo(0, (int) (start+(end*fraction)));
            }
        });
        animator.start();
    }

}