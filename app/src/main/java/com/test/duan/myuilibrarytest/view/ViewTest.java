package com.test.duan.myuilibrarytest.view;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.test.TouchUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.Scroller;

/**
 * Created by Duanyy on 2016/6/17.
 * 研究事件处理，的自定义view
 */

public class ViewTest extends ImageView {

    private Context mContext;
    private Scroller mScroller;

    public ViewTest(Context context) {
        this(context,null);
    }

    public ViewTest(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ViewTest(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = getContext();
        mScroller = new Scroller(mContext);
    }

    private static final String TAG = "----view----";

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {

        boolean consume = super.dispatchTouchEvent(event);

        Log.d(TAG,"dispatchTouchEvent return:  "+consume);
        return consume;
    }


    private int mLastX;
    private int mLastY;


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        boolean consume = super.onTouchEvent(event);

        int x = (int) event.getRawX();
        int y = (int) event.getY();

        int action = event.getAction();
        switch (action){
            case MotionEvent.ACTION_DOWN:

                mLastX = x;
                mLastY = y;
                consume = true;
                break;
            case MotionEvent.ACTION_MOVE:

                if (Math.abs(x-mLastX) > 8){

                }
                //利用Scroller实现平滑移动
//                smoothScrollTo(x,y);

                ObjectAnimator.ofFloat(this,"translationX",mLastX,x).setDuration(1000).start();
                mLastX = x;

                consume = true;
                break;
            case MotionEvent.ACTION_UP:

                break;
        }

        Log.d(TAG,"onTouchEvent return:  "+consume);
        return consume;
    }

    @Override
    public void computeScroll() {
        Log.d("Scroll","---computeScroll--");
        if (mScroller.computeScrollOffset()){
            scrollTo(mScroller.getCurrX(),mScroller.getCurrY());
            postInvalidate();//在子线程中强制重绘。换成Invalidate()试试？
        }

    }
    private void smoothScrollTo(int toX,int toY){
        Log.d("Scroll","---smoothScrollTo--");
        int scrollX = getScrollX();
        int scrollY = getScrollY();
        int delta = toX - scrollX;
        mScroller.startScroll(scrollX,scrollY,delta,0,2000);
        invalidate();


    }
}
