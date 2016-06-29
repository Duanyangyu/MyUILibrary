package com.test.duan.myuilibrarytest.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.FrameLayout;

/**
 * Created by Duanyy on 2016/6/17.
 * 研究事件处理，的自定义viewGroup
 */

public class ViewGroupTest extends FrameLayout {

    public ViewGroupTest(Context context) {
        super(context);
    }

    public ViewGroupTest(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ViewGroupTest(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    private static final String TAG = "----viewGroup----";
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        boolean consume = super.dispatchTouchEvent(ev);

        Log.d(TAG,"dispatchTouchEvent return:  "+consume);
        return consume;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        boolean consume = super.onInterceptTouchEvent(ev);
        Log.d(TAG,"onInterceptTouchEvent return:  "+consume);
        return consume;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean consume = super.onTouchEvent(event);
        Log.d(TAG,"onTouchEvent retun:  "+consume);
        return consume;
    }
}
