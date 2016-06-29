package com.test.duan.myuilibrarytest.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.ListView;

/**
 * Created by Duanyy on 2016/5/13.
 */
public class AutoScrollListView extends ListView {


    private GestureDetector.SimpleOnGestureListener mSimpleGestureDetector;

    private GestureDetector mGestureDector;

    public AutoScrollListView(Context context) {
        this(context,null,0);
    }

    public AutoScrollListView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public AutoScrollListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mSimpleGestureDetector = new GestureDetector.SimpleOnGestureListener(){
           @Override
           public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

//               Log.d("======","velocityX:  "+velocityX);
//               Log.d("======","velocityY:  "+velocityY);
               return super.onFling(e1,e2,velocityX,velocityY);
           }
       };
        mGestureDector = new GestureDetector(getContext(),mSimpleGestureDetector);
    }



    @Override
    public boolean onTouchEvent(MotionEvent ev) {
//        return mGestureDector.onTouchEvent(ev);
        int action = ev.getAction();
        switch (action){
            case MotionEvent.ACTION_SCROLL:
                Log.d("===+++",ev.getAxisValue(MotionEvent.AXIS_HAT_Y)+"");
                break;
        }
        return super.onTouchEvent(ev);
    }
}
