package com.test.duan.myuilibrarytest.view;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.TranslateAnimation;
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

    private boolean outBound = false;
    private int firstOut;
    private int distance;

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

    private Rect mItemViewRect = new Rect();

    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        int action = ev.getAction();

        int cuttentY = (int) ev.getY();

        firstView = getChildAt(0);

        switch (action){

            case MotionEvent.ACTION_DOWN:


                break;
            case MotionEvent.ACTION_MOVE:

                int delat = cuttentY - mLastY;
                if (delat > 0){
                    //下拉
                    mScrollState = STATE_XIALA;
                }else {
                    //上滑
                    mScrollState = STATE_SHANGHUA;
                }
                mLastY = cuttentY;
                Log.d(TAG,"action move");
                break;
            case MotionEvent.ACTION_UP:

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

    GestureDetector mGestureDetector = new GestureDetector(
            new GestureDetector.SimpleOnGestureListener(){
                @Override
                public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                    Log.d(TAG,"onFling()");

                    int distance = 0;
                    if (STATE_SHANGHUA == mScrollState){

                        distance = firstView.getHeight() + firstView.getTop();
                    }else if (STATE_XIALA == mScrollState){
                        distance = firstView.getTop();
                    }
                    Log.d(TAG,"action up");
                    if (firstView.getTop() != 0){
                        mScroller.startScroll(0,0,0,distance,10000);
                    }

                    return true;
                }
            }
    );

    @Override
    public void computeScroll() {

        if (mScroller.computeScrollOffset()){
            scrollTo(mScroller.getCurrX(),mScroller.getCurrY());
            postInvalidate();
        }

    }
}
