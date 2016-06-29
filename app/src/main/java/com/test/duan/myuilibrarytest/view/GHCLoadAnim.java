package com.test.duan.myuilibrarytest.view;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.test.duan.myuilibrarytest.R;


/**
 * Created by Duanyy on 2016/6/16.
 * 第二版本中的loading动画
 */

public class GHCLoadAnim extends LinearLayout {

    private Context mContext;
    private View mRootView;
    private ImageView mImageView;
    private TextView mTextView;
    private AnimationDrawable mAnimDrawable;

    public GHCLoadAnim(Context context) {
        this(context,null);
    }

    public GHCLoadAnim(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public GHCLoadAnim(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = getContext();
        init();
    }

    private void init(){
        mRootView = LayoutInflater.from(mContext).inflate(R.layout.anim_loading_header, this, true);
        mImageView = (ImageView) mRootView.findViewById(R.id.iv_header_anim);
        mTextView = (TextView) mRootView.findViewById(R.id.tv_header_text);
        mAnimDrawable = (AnimationDrawable) mImageView.getBackground();
    }

    public void start(){
        if (mAnimDrawable != null){
            mAnimDrawable.start();
        }
    }

    public void stop(){
        if (mAnimDrawable != null){
            mAnimDrawable.stop();
        }
    }

    public void setLoadingText(String text){
        mTextView.setText(text);
    }

}
