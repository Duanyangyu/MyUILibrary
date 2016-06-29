package com.test.duan.myuilibrarytest.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.test.duan.myuilibrarytest.R;


/**
 * Created by Duanyy on 2016/5/25.
 */
public class ServiceView extends FrameLayout {

    private Context mContext;

    private ImageView mBottomImageView;
    private ImageView mMiddleView;
    private TextView mTopTextView;
    private TextView mBelowTextView;
    private LinearLayout mLineLayout;

    private int mMoveX;
    private int mDownX;


    public ServiceView(Context context) {
        this(context,null);
    }

    public ServiceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;

        init();
    }

    private void init(){
        LayoutParams params_1 = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        //最底层的图片背景
        mBottomImageView = new ImageView(mContext);

        //中间层的灰色蒙版
        mMiddleView = new ImageView(mContext);
        mMiddleView.setAlpha(0.3f);
        mMiddleView.setBackground(getResources().getDrawable(R.color.color_blue_mengban));

        //最顶层的文本层，放在一个垂直的线性布局中
        mTopTextView = new TextView(mContext);
        mTopTextView.setTextColor(Color.WHITE);
        mTopTextView.setTextSize(getResources().getDimension(R.dimen.font_size16sp));
        mBelowTextView = new TextView(mContext);
        mBelowTextView.setTextColor(Color.WHITE);
        mBelowTextView.setTextSize(getResources().getDimension(R.dimen.font_size10sp));

        mLineLayout = new LinearLayout(mContext);
        mLineLayout.setOrientation(LinearLayout.VERTICAL);
        mLineLayout.setGravity(Gravity.CENTER_HORIZONTAL);

        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mLineLayout.addView(mTopTextView);
        p.topMargin = 13;
        mLineLayout.addView(mBelowTextView,p);


        this.addView(mBottomImageView,params_1);
        this.addView(mMiddleView,params_1);
        LayoutParams params_2 = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params_2.gravity = Gravity.CENTER;
        this.addView(mLineLayout,params_2);
    }

    public void setBitmap(int resource){
        mBottomImageView.setImageResource(resource);
    }

    public void setTopText(String text){
        mTopTextView.setText(text);
    }

    public void setmBelowText(String text){
        mBelowTextView.setText(text);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        //左滑，右滑
        int action = event.getAction();
        switch (action){
            case MotionEvent.ACTION_DOWN:
                mDownX = (int) event.getX();
                Log.d("====","action_down: "+ mDownX);
                break;
            case MotionEvent.ACTION_MOVE:
                mMoveX = (int) event.getX();
                Log.d("====","action_move: "+ mMoveX);
//                if (Math.abs(mMoveX - mDownX) >= 100){
//                    changeViewShowing(mMiddleView);
//                    changeViewShowing(mLineLayout);
//                }
                break;
            case MotionEvent.ACTION_UP:
                int mUpX = (int) event.getX();
                Log.d("====","action_up: "+ mUpX);
                if (Math.abs(mUpX - mDownX) >= 100){
                    changeViewShowing(mMiddleView);
                    changeViewShowing(mLineLayout);
                }
                break;
        }
//        return super.onTouchEvent(event);
        return true;
    }

    //切换View的显示状态
    private void changeViewShowing(View view){

        if (view == null){
            return;
        }

        int visibility = view.getVisibility();
        if (visibility == VISIBLE){
            view.setVisibility(GONE);
        }
        if (visibility == GONE || visibility == INVISIBLE){
            view.setVisibility(VISIBLE);
        }
    }

}
