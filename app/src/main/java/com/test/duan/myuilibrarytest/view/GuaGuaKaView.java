package com.test.duan.myuilibrarytest.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.test.duan.myuilibrarytest.R;

/**
 * Created by Duanyy on 2016/6/13.
 */

public class GuaGuaKaView extends View {

    private Canvas mCanvas;
    private Path mPath;
    private Paint mPaint;
    private Bitmap mBitmap;

    private int mLastX;
    private int mLastY;

    private String mText;

    private volatile boolean mComplete = false;

    private Bitmap bitmapContent;

    public OnGuaGuaKaCompleteListener mListener;

    public interface OnGuaGuaKaCompleteListener{
        void complete();
    }

    public GuaGuaKaView(Context context) {
        this(context,null);
    }

    public GuaGuaKaView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public GuaGuaKaView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed,l,t,r,b);
    }

    public void setOnGuaGuaKaCompleteListener(OnGuaGuaKaCompleteListener mListener) {
        this.mListener = mListener;
    }

    public void setText(String text) {
        this.mText = text;
    }

    private void init(){
        mPath = new Path();
        mPaint = new Paint();
        bitmapContent = BitmapFactory.decodeResource(getResources(), R.mipmap.icon_service_video_bg);
    }

    private void setUpPaint(){
        mPaint.setDither(true);
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.RED);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(40);
        mPaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.outWidth = width;
        options.outHeight = height;
        mBitmap = BitmapFactory.decodeResource(getResources(),R.mipmap.icon_guaka_mengban, options).copy(Bitmap.Config.ARGB_8888, true);
        mCanvas = new Canvas(mBitmap);

        if (!TextUtils.isEmpty(mText)){
            drawText(mText,mCanvas);
        }

        setUpPaint();
    }

    private void drawText(String text,Canvas canvas){
        Paint mPaint = new Paint();
        mPaint.setStrokeWidth(3);
        mPaint.setTextSize(40);
        mPaint.setColor(Color.WHITE);
        mPaint.setTextAlign(Paint.Align.LEFT);
        Rect bounds = new Rect();
        mPaint.getTextBounds(text, 0, text.length(), bounds);
        Paint.FontMetricsInt fontMetrics = mPaint.getFontMetricsInt();
        int baseline = (getMeasuredHeight() - fontMetrics.bottom + fontMetrics.top) / 2 - fontMetrics.top;
        canvas.drawText(text,getMeasuredWidth() / 2 - bounds.width() / 2, baseline, mPaint);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(bitmapContent,0,0,null);

        if (mComplete && mListener != null){
            mListener.complete();
        }

        if (!mComplete){
            drawPath();
            canvas.drawBitmap(mBitmap,0,0,null);
        }

    }

    private void drawPath(){
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
        mCanvas.drawPath(mPath,mPaint);
    }





    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int action = event.getAction();
        //问题：此处调用getRawX会产生什么Bug？稍候尝试一下。
        int x = (int) event.getX();
        int y = (int) event.getY();

        switch (action){
            case MotionEvent.ACTION_DOWN:
                mLastX = x;
                mLastY = y;

                mPath.moveTo(mLastX,mLastY);
                break;
            case MotionEvent.ACTION_MOVE:

                if (Math.abs(x-mLastX ) < Math.abs(y-mLastY)){
                    return false;
                }

                getParent().requestDisallowInterceptTouchEvent(true);

                mPath.lineTo(x,y);
                mLastX = x;
                mLastY = y;
                break;
            case MotionEvent.ACTION_UP:

                new Thread(mRunnable ).start();

                break;
            default:
                break;
        }
        invalidate();
//        return super.onTouchEvent(event);
        return true;
    }

    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            int w = mCanvas.getWidth();
            int h = mCanvas.getHeight();
            float wipeArea = 0;
            float tatalArea = w * h;

            int[] pixels = new int[w*h];
            mBitmap.getPixels(pixels,0,w,0,0,w,h);

            for (int i = 0; i < w; i++) {
                for (int j = 0; j < h; j++) {
                    int index = i+j*w;
                    if (pixels[index] == 0){
                        wipeArea ++;
                    }
                }
            }

            if (wipeArea > 0 && tatalArea > 0){

                int percent = (int)(wipeArea *100/ tatalArea);
                Log.d("percent",":   "+percent);
                if (percent > 40){
                    //达到阈值，清除图层区域
                    mComplete = true;
                    postInvalidate();
                }
            }

        }
    };


}
