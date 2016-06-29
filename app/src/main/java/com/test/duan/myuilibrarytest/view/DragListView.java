package com.test.duan.myuilibrarytest.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Vibrator;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.test.duan.myuilibrarytest.R;
import com.test.duan.myuilibrarytest.adapter.DragBaseAdapter;

import java.util.List;

/**
 * Created by Duanyy on 2016/4/22.
 */
public class DragListView extends ListView {

    private View mDragView;
    private Bitmap mDragBitmap;
    private ImageView mDragImageView;

    private Handler mHandler = new Handler();

    private WindowManager mWindowManager;

    private Vibrator mVibrator;
    private long mLongClickBorder = 500;

    private int mStatusHeight;

    private boolean isDrag = false;
    private int mDragX;
    private int mDragY;
    private WindowManager.LayoutParams mParams;
    private int mDragPosition;
    private DragBaseAdapter mAdapter;

    public DragListView(Context context, AttributeSet attrs) {
        this(context,attrs,0);
    }

    public DragListView(Context context) {
        this(context,null,0);
    }

    public DragListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mVibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        mStatusHeight = getStatusHeight(context);
        int mDis2Top = (int) getY();
        Log.d("---","mDis2Top"+mDis2Top);
    }

    /*思路:
    * 1.启动长按线程，识别长按操作
    * 2.创建item镜像
    * 3.实时刷新镜像位置
    * 4.实现屏幕自动滑动
    * */

    private Runnable mOnLongClickRunnable = new Runnable() {
        @Override
        public void run() {
            //开启拖拽模式
            isDrag = true;

            mVibrator.vibrate(50);
            createDragImage();
        }
    };

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        switch (action){
            case MotionEvent.ACTION_DOWN:
                //mLongClickBorder毫秒时间间隔以后启动长按线程
                mHandler.postDelayed(mOnLongClickRunnable,mLongClickBorder);

                int downX = (int) ev.getX();
                int downY = (int) ev.getY();
                mDragPosition = pointToPosition(downX, downY);

                if (mDragPosition == AbsListView.INVALID_POSITION){
                    return super.dispatchTouchEvent(ev);
                }

                mDragView = getChildAt(mDragPosition - getFirstVisiblePosition());

                mDragView.setDrawingCacheEnabled(true);
                mDragBitmap = Bitmap.createBitmap(mDragView.getDrawingCache());
                mDragView.destroyDrawingCache();

                mDragY = (int) (mDragView.getTop()+ev.getRawY()-ev.getY()-mStatusHeight);
                mDragX = mDragView.getLeft();

                break;
            case MotionEvent.ACTION_MOVE:

                break;
            case MotionEvent.ACTION_UP:
                mHandler.removeCallbacks(mOnLongClickRunnable);

                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (isDrag && mDragImageView != null){
            int action = ev.getAction();
            switch (action){
                case MotionEvent.ACTION_DOWN:


                    break;
                case MotionEvent.ACTION_MOVE:
                    int mMoveX = (int) ev.getX();
                    int mMoveY = (int) ev.getY();
                    onDragItem(mMoveX,mMoveY);
                    onSwapItem(mMoveX,mMoveY);
                    break;
                case MotionEvent.ACTION_UP:
                    int moveX = (int) ev.getX();
                    int moveY = (int) ev.getY();

                    removeDragView();

                    mAdapter.hideItemAt(-1);
                    isDrag= false;
                    break;
            }
            return true;
        }
        return super.onTouchEvent(ev);
    }

    private void updateDragView(int moveX,int moveY){
        mParams.x = moveX;
        Log.d("====","moveY"+moveX+"");
        Log.d("====","mParams"+mParams.x);
        mParams.y = moveY  + mStatusHeight;
        mWindowManager.updateViewLayout(mDragImageView,mParams);
    }

    //拖拽过程中调用；功能：
    // 1.控制拖拽过程中的Item显示与隐藏
    // 2.更新镜像的位置
    private void onDragItem(int moveX,int moveY){
        updateDragView(moveX,moveY);
        int tempPosition = pointToPosition(moveX, moveY);
        Log.d("tempposition",tempPosition+"");

        if (mAdapter == null){
            mAdapter = (DragBaseAdapter) getAdapter();
        }

        if (tempPosition != mDragPosition){
//            mAdapter.hideItemAt(tempPosition);
        }
    }

    private void onSwapItem(int moveX,int moveY){
        int tempPos = pointToPosition(moveX, moveY);
        if (tempPos == AbsListView.INVALID_POSITION){
            return;
        }
        mAdapter.reorderItem(mDragPosition,tempPos);

    }

    private void removeDragView(){
        if (mDragImageView != null) {
            Log.d("=====","mDragImageView:"+mDragImageView+", 被移除");
            mWindowManager.removeView(mDragImageView);
            mDragImageView = null;
            mDragBitmap.recycle();
        }
    }

    private int getStatusHeight(Context context){
        int statusHeight;
        Rect localRect = new Rect();
        ((Activity)context).getWindow().getDecorView().getWindowVisibleDisplayFrame(localRect);
        statusHeight = localRect.top;

        if (statusHeight == 0){
            try {
                Class<?> localClass = Class.forName("com.android.internal.R$dimen");
                Object localObject = localClass.newInstance();
                int i5 = Integer.parseInt(localClass.getField("status_bar_height").get(localObject).toString());
                statusHeight = context.getResources().getDimensionPixelSize(i5);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        }
        return statusHeight;
    }

    private void createDragImage(){

        mParams = new WindowManager.LayoutParams();
        mParams.x = mDragX;
        mParams.y = mDragY;
        mParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        mParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        mParams.gravity = Gravity.TOP|Gravity.LEFT;
        mParams.format = PixelFormat.TRANSLUCENT;
        mParams.alpha = 0.55f;
        mParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE ;
        mDragImageView = new ImageView(getContext());
        mDragImageView.setImageBitmap(mDragBitmap);
        Log.d("---", mDragImageView.toString());
        mWindowManager.addView(mDragImageView, mParams);
    }
}
