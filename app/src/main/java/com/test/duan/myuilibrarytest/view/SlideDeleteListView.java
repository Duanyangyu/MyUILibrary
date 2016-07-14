package com.test.duan.myuilibrarytest.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListView;

/**
 * Created by Duanyy on 2016/7/1.
 */
public class SlideDeleteListView extends ListView {

    private Context mContext;

    public SlideDeleteListView(Context context) {
        this(context,null,0);
    }

    public SlideDeleteListView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SlideDeleteListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        return super.onTouchEvent(ev);
    }
}
