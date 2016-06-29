package com.test.duan.myuilibrarytest.view;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.test.duan.myuilibrarytest.R;

/**
 * Created by Duanyy on 2016/5/20.
 */
public class AutoTopView extends FrameLayout{

    private Context mContext;
    private View mView;
    private View mTopView;
    private ListView mListView;

    public AutoTopView(Context context) {
        this(context,null,0);
    }

    public AutoTopView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public AutoTopView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = getContext();
        init();
    }

    private void init(){
        mView = LayoutInflater.from(mContext).inflate(R.layout.auto_top_view, this, true);
        mTopView = mView.findViewById(R.id.view_top);
        mListView = (ListView) mView.findViewById(R.id.view_listview);
    }

    private int mDownY;
    private int mMoveY;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d("++++","onTouch");
        int action = event.getAction();
        switch (action){
            case MotionEvent.ACTION_DOWN:
                this.mDownY = (int) event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                this.mMoveY = (int) event.getY();
                Log.d("++++",mMoveY+"");
                int tempPadding = mMoveY - mDownY;
                if (tempPadding < 0){
                    //TopView消失
                    if (tempPadding <= -120)
                        tempPadding = -120;
                }else {
                    if (tempPadding >= 120){
                        tempPadding = 120;
                    }
                }
                setPadding4Top(tempPadding);
                break;
        }

        return super.onTouchEvent(event);
    }

    private void setPadding4Top(int padding){
        if (mTopView != null){
            mTopView.setPadding(0,padding,0,0);
        }
    }
}
