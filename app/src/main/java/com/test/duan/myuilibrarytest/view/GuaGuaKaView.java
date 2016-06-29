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
import android.util.AttributeSet;
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

    private Bitmap bitmapContent;

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

        mBitmap = Bitmap.createBitmap(width,height, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);
        mCanvas.drawColor(Color.parseColor("#354a6c"));
        setUpPaint();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(bitmapContent,0,0,null);
        drawPath();
        canvas.drawBitmap(mBitmap,0,0,null);
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
                mPath.lineTo(x,y);
                mLastX = x;
                mLastY = y;
                break;
            case MotionEvent.ACTION_UP:

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
            int w = getWidth();
            int h = getHeight();
            float wipeArea = 0;
            float tatalArea = w * h;

            int[] pixels = new int[w*h];
            mBitmap.getPixels(pixels,0,w,0,0,w,h);

            for (int i = 0; i < w; i++) {
                for (int j = 0; j < h; j++) {
                    int index = i*w+j;
                    if (pixels[index] == 0){
                        wipeArea ++;
                    }
                }
            }




        }
    };


}
