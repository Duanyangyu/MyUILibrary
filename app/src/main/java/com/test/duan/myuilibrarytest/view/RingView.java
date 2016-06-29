package com.test.duan.myuilibrarytest.view;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.test.duan.myuilibrarytest.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

/**
 * Created by Duanyy on 2016/5/6.
 */
public class RingView extends FrameLayout implements View.OnClickListener {

    private Context mContext;

    private View mRing;
    private View mCircle;

    private int mCircleX;
    private int mCircleY;
    private double mRadius;
    private double mRingWidth;

    //内圆圆心距离环形中间线的距离
    private int mDistance;

    //圆形区域的左边缘
    private int mCircleLeft;
    private int mCircleRight;
    //环形的左边缘
    private int mRingLeft;

    private List<View> mBtnList;
//    private View mButton1;
    private FrameLayout mFramLayout;
    private Map<View, Params> mParamsMap;
    private LayoutParams mParams;

    public RingView(Context context) {
        this(context,null,0);
    }

    public RingView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public RingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
    }

    private void initView(){
        mFramLayout = (FrameLayout) LayoutInflater.from(mContext).inflate(R.layout.view_ring, this,true);
        mRing = mFramLayout.findViewById(R.id.v_ring);
        mCircle = mFramLayout.findViewById(R.id.v_circle);
        mCircle.setOnClickListener(this);
        getCircleInfo();
    }

    public void addButton(List<View> list){
        this.mBtnList = list;

    }

    @Override
    public void onClick(View v) {
        calcuAnimParams();
        //播放动画
        //1.圆环变大

        //2.按钮出现
//        AnimatorSet animatorSet = createTranslationAnimations(mButton1, mCircleX-(mRingLeft + mCircleLeft) / 2, mCircleY);
//        int deltaX = (int) (mRingWidth/2 + mRadius);
//        AnimatorSet animatorSet = createTranslationAnimations(mButton1,deltaX , mCircleY);
//        animatorSet.start();
//        mButton1.bringToFront();

        for (int i = 0; i < mBtnList.size(); i++) {
            View view = mBtnList.get(i);
            AnimatorSet set = createTranslationAnimations(view, (int) mParamsMap.get(view).deltaX, (int) mParamsMap.get(view).deltaY);
            set.start();
            view.bringToFront();
        }

    }

    private void calcuAnimParams(){
        if (mBtnList == null) {
            return;
        }
        int count = mBtnList.size();
        double angle = 360.0d / (count-1);
        mParamsMap = new HashMap<>();
        for (int i = 0; i < count; i++) {
            double deltaY = (mDistance-mRingWidth/4) * Math.sin(angle * (i));
            double deltaX = (mDistance-mRingWidth/4) * Math.cos(angle * (i));
            Params params = new Params();
            params.deltaX = deltaX;
            params.deltaY = deltaY;
            View view = mBtnList.get(i);
            mParamsMap.put(view,params);
            Log.d("params","angle: "+angle * (i)+" || params "+i+":   ("+params.deltaX+" , "+params.deltaY+")");
        }
    }

    //给Button制作动画
    private AnimatorSet createTranslationAnimations(View view,int endX,int  endY){
        ObjectAnimator animX = ObjectAnimator.ofFloat(view,"translationX",endX);
        ObjectAnimator animY = ObjectAnimator.ofFloat(view,"translationY",endY);
        ObjectAnimator animRotation = ObjectAnimator.ofFloat(view,"rotation",0,180);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animX,animY,animRotation);
        return animatorSet;
    }

    //子View是否被添加
    private boolean idViewAdded = true;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        switch (action){
            case MotionEvent.ACTION_DOWN:
                initView();
                if (idViewAdded) {
                    idViewAdded = false;
                    mParams = new LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT);
                    for (int i = 0; i < mBtnList.size(); i++) {
                        View view = mBtnList.get(i);
                        view.setLayoutParams(mParams);
                        mParams.gravity = Gravity.CENTER;
                        mFramLayout.addView(view);
                    }

                }
                break;
        }
        return super.dispatchTouchEvent(ev);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        boolean ret = super.onTouchEvent(event);
        switch (action){
            case MotionEvent.ACTION_DOWN:

                ret = true;
                break;
            case MotionEvent.ACTION_MOVE:

                break;
            case MotionEvent.ACTION_UP:

                break;

        }
        return ret;
    }

    private void updateView(int moveX,int moveY){

        for (View view : mBtnList){


        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

    }

    //是否触摸在圆形内部
    private boolean touchInCircle(int moveX,int moveY){
        double distance = getDistance(moveX, moveY);
        if (distance > mRadius){
            return false;
        }else {
            return true;
        }
    }

    private double getDistance(int moveX,int moveY){
        int _moveX = Math.abs(moveX - mCircleX);
        int _moveY = Math.abs(moveY - mCircleY);
        return Math.sqrt(_moveX * _moveX + _moveY * _moveY);
    }

    //计算圆心坐标
    private void getCircleInfo(){
        final ViewTreeObserver observer = getViewTreeObserver();
        observer.addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        observer.removeOnGlobalLayoutListener(this);
                        mCircleLeft = mCircle.getLeft();
                        mCircleRight = mCircle.getRight();
                        int top = mCircle.getTop();
                        int bottom = mCircle.getBottom();
                        mCircleX = (mCircleLeft + mCircleRight) / 2;
                        mCircleY = (top + bottom) / 2;
                        mRadius = (mCircleRight - mCircleLeft) / 2;

                        mRingLeft = mRing.getLeft();
                        mRingWidth = mCircleLeft - mRingLeft;

                        mDistance = (int) (mRadius + mRingWidth/2);

                    }
                }
        );
    }
    class Params{
        public double deltaX;
        public double deltaY;
    }
}
