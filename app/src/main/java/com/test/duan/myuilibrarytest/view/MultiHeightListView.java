package com.test.duan.myuilibrarytest.view;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AnimationSet;
import android.widget.ListView;
import android.widget.Scroller;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Duanyy on 2016/8/1.
 */
public class MultiHeightListView extends ListView {

    private Context mContext;

    //标准的行高
    private int standardItemHeight;

    //最大的行高值
    private int maxItemHeight;

    //最小的行高值
    private int minItemHeight;

    private int mLastY;

    private static final String TAG = "MultiHeightListView";
    private Scroller mScroller;


    private static int mScrollState;

    //上滑标志位
    private static final int STATE_SHANGHUA = 0;

    //下拉标志位
    private static final int STATE_XIALA = 1;
    private View firstView;

    public MultiHeightListView(Context context) {
        this(context,null);
    }

    public MultiHeightListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
        setOnTouchListener(mOnTouchListener);
    }

    private void init(){
        mContext = getContext();
        mScroller = new Scroller(mContext);

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }


    private int mStartY;
    private int mDistanceY;

    public int getmStartY() {
        return mStartY;
    }

    public int getmDistanceY() {
        return mDistanceY;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        int action = ev.getAction();

        int cuttentY = (int) ev.getY();

        firstView = getChildAt(0);

        switch (action){

            case MotionEvent.ACTION_DOWN:
                Log.d(TAG,"action down");

                break;
            case MotionEvent.ACTION_MOVE:
//                Log.d(TAG,"action move");

                int delat = cuttentY - mLastY;
                if (delat > 0){
                    //下拉
                    mScrollState = STATE_XIALA;
                }else {
                    //上滑
                    mScrollState = STATE_SHANGHUA;
                }
                mLastY = cuttentY;

                break;
            case MotionEvent.ACTION_UP:
                Log.d(TAG,"action up");


                break;

        }

        return super.onTouchEvent(ev);
    }

    OnTouchListener mOnTouchListener = new OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            return mGestureDetector.onTouchEvent(event);
        }
    };

    public int mTop;

    GestureDetector mGestureDetector = new GestureDetector(
            new GestureDetector.SimpleOnGestureListener(){



                @Override
                public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {



                    int firstVisiblePosition = getFirstVisiblePosition();
                    int lastVisiblePosition = getLastVisiblePosition();

                    View firstView = getChildAt(0);
                    if (firstView != null){
                        mTop = firstView.getTop();
                    }
                    Log.d(TAG,"onFling(), firstVisiblePosition:  "+firstVisiblePosition+",   mTop: "+mTop);

                    playAnimation(firstVisiblePosition,lastVisiblePosition);


                    return true;
                }
            }
    );

    @Override
    public void computeScroll() {

        if (mScroller.computeScrollOffset()){
            scrollTo(0,mScroller.getCurrY());
            postInvalidate();
        }

    }



    private void createAnimation(final int start, final int end){
        final ValueAnimator animator = ValueAnimator.ofFloat(start,end).setDuration(3000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float fraction = animator.getAnimatedFraction();
                MultiHeightListView.this.scrollTo(0, (int) (start+(end*fraction)));
            }
        });
        animator.start();
    }

    private Animator createAnimation(View targetView, int startY, int endY){
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(targetView,"translationY",startY,endY);
        return objectAnimator;
    }

    private void playAnimation(int firstPosition,int lastPosition){
        List<Animator> list = new ArrayList<>();
        int count = getCount();
        for (int i = 0; i < count; i++) {
            View view = getChildAt(i);
            Animator animation = createAnimation(view, 0, mTop);
            list.add(animation);
        }
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(list);
        animatorSet.setDuration(500);
        animatorSet.setInterpolator(new AccelerateDecelerateInterpolator());
        animatorSet.start();
        Log.d(TAG,"animator started");
    }

}
