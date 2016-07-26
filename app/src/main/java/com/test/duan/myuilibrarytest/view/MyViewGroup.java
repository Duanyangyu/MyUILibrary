package com.test.duan.myuilibrarytest.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * Created by Duanyy on 2016/7/14.
 */
public class MyViewGroup extends LinearLayout {
    public MyViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    private static final String TAG = "MyViewGroup";

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action){
            case MotionEvent.ACTION_DOWN:

                Log.d(TAG,"getLeft: "+getLeft());
                Log.d(TAG,"getRight-getLeft: "+(getRight()-getLeft()));
                Log.d(TAG,"getWidth: "+getWidth());
                Log.d(TAG,"getTranslationX: "+getTranslationX());
                Log.d(TAG,"getX: "+getX());

                break;
        }
        return super.onTouchEvent(event);
    }
}
