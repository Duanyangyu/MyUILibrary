package com.test.duan.myuilibrarytest.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;
import android.widget.Scroller;

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
                Log.d(TAG,"action move");

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

    private int totalY = 0;

    GestureDetector mGestureDetector = new GestureDetector(
            new GestureDetector.SimpleOnGestureListener(){
                @Override
                public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

                    int firstVisiblePosition = getFirstVisiblePosition();
                    int lastVisiblePosition = getLastVisiblePosition();
                    Log.d(TAG,"firstVisiblePosition: "+firstVisiblePosition+",  lastVisiblePosition:"+lastVisiblePosition);

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



    private void playAnimation(final int start, final int end){
        final ValueAnimator animator = ValueAnimator.ofInt(start,end).setDuration(5000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float fraction = animator.getAnimatedFraction();
                MultiHeightListView.this.scrollTo(0, (int) (start+(end*fraction)));
            }
        });
        animator.start();
    }

}
