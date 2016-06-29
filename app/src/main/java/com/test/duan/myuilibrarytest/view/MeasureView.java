package com.test.duan.myuilibrarytest.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by Duanyy on 2016/5/18.
 */
public class MeasureView extends View {

    public MeasureView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MeasureView(Context context) {
        this(context,null);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);

        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        switch (widthMode){
            case MeasureSpec.EXACTLY:
                Log.d("====","width :   MeasureSpec.EXACTLY,  "+widthSize);
                break;
            case MeasureSpec.AT_MOST:
                Log.d("====","width :   MeasureSpec.AT_MOST,  "+widthSize);
                break;
            case MeasureSpec.UNSPECIFIED:
                Log.d("====","width :   MeasureSpec.UNSPECIFIED,  "+widthSize);
                break;
        }
        //实际控制view的大小
        setMeasuredDimension(widthMeasureSpec,heightMeasureSpec);

    }
}
