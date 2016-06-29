package com.test.duan.myuilibrarytest.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Vibrator;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.GridView;
import android.widget.ImageView;

import com.test.duan.myuilibrarytest.adapter.DragAdapter;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Duanyy on 2016/4/19.
 */
public class DragGridView extends GridView {

    //分别记录按下时的X，Y坐标
    private int mDownX;
    private int mDownY;
    private int mDragPosition;

    //滑动时的坐标
    private int moveY;
    private int moveX;

    //利用以下四个距离来确定镜像的坐标
    private int mPoint2ItemTop;
    private int mPoint2ItemLeft;
    private int mOffset2Top;
    private int mOffset2Left;

    //超越以下边界值，gridview将自动滑动
    private int mUpScrollBorder ;
    private int mDownScrollBorder;

    private final int speed = 20;

    //设置识别长按的时间
    private long dragResponseMS = 300;

    //拖动标志位
    private boolean isDrag = false;

    //动画标志位
    private boolean mAnimationEnd = true;

    private Vibrator mVibrator;

    private Handler handler = new Handler();

    //被拖动的view
    private View mStartDragView = null;
    private WindowManager mWindowManager;
    private ImageView mDragImageView;
    private Bitmap mDragBitmap;
    private WindowManager.LayoutParams mParams;

    private int mNumcolunms;

    public DragGridView(Context context) {
        this(context,null);
    }

    public DragGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mVibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
    }

    @Override
    public void setNumColumns(int numColumns) {
        super.setNumColumns(numColumns);
        this.mNumcolunms = numColumns;
    }

    private Runnable mLongClickRunnable = new Runnable() {
        @Override
        public void run() {
            // 长按识别线程一旦启动，则意味着此为长按交互
            isDrag = true;
            //震动50ms
            mVibrator.vibrate(50);

            mStartDragView.setVisibility(INVISIBLE);
            Log.d("mStartDragViewLongClick",mStartDragView.toString());
            //生成镜像
            createDragImage(mDragBitmap);
            //自动滑动
            handler.postDelayed(mScrollRunnable,10);
        }
    };

    //处理GridView自动滑动
    private Runnable mScrollRunnable = new Runnable() {
        @Override
        public void run() {
            int scrollY = 0;
            Log.d("scrollrunnable","moveY:"+moveY);
            Log.d("scrollrunnable","thread name:"+Thread.currentThread().getName()
                    +"--isAlive:"+Thread.currentThread().isAlive()
                            +"--"+moveY+"");
            if (moveY <= mUpScrollBorder){
                scrollY = -speed;
                handler.postDelayed(this,25);
            }else if (moveY > mDownScrollBorder){
                scrollY = speed;
                handler.postDelayed(this,25);
            }else {
                scrollY = 0;
                handler.removeCallbacks(this);
            }

            //利用计算出的滑动方向，进行滑动
            smoothScrollBy(scrollY,10);

            //交换item
            onSwapItem(moveX,moveY);
        }
    };

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        if (ev != null) {
            int action = ev.getAction();
            switch (action){
                case MotionEvent.ACTION_DOWN:

                    mDownX = (int) ev.getX();
                    mDownY = (int) ev.getY();

                    mDragPosition = pointToPosition(mDownX,mDownY);

                    if (mDragPosition == AbsListView.INVALID_POSITION){
                        return super.dispatchTouchEvent(ev);
                    }

                    //获取被拖动的View
                    mStartDragView = getChildAt(mDragPosition - getFirstVisiblePosition());
                    Log.d("mStartDragView dispatch",mStartDragView.toString());
                    Log.d("position","mDragPositon"+mDragPosition+"  getFirstVisiblePosition()"+getFirstVisiblePosition());

                    //事件被触发，启动长按识别线程
                    handler.postDelayed(mLongClickRunnable, dragResponseMS);

                    //获取DragGridView自动向上滚动的偏移量，小于这个值，DragGridView向下滚动
                    mDownScrollBorder = getHeight()* 3/4;
                    Log.d("scrollborder",getHeight()+" height");
                    Log.d("scrollborder",mDownScrollBorder+" down");

                    //获取DragGridView自动向下滚动的偏移量，大于这个值，DragGridView向上滚动
                    mUpScrollBorder = getHeight() / 4;
                    Log.d("scrollborder",mUpScrollBorder+" up");

                    //计算WindowView的一系列坐标
                    mPoint2ItemLeft = mDownX - mStartDragView.getLeft();
                    mPoint2ItemTop = mDownY - mStartDragView.getTop();

                    mOffset2Top = (int) (ev.getRawY() - mDownY);
                    mOffset2Left = (int) (ev.getRawX() - mDownX);

                    mStartDragView.setDrawingCacheEnabled(true);
                    mDragBitmap = Bitmap.createBitmap(mStartDragView.getDrawingCache());
                    mStartDragView.destroyDrawingCache();
                    break;
                case MotionEvent.ACTION_MOVE:

                    int moveX = (int) ev.getX();
                    int moveY = (int) ev.getY();

                    if (!isTouchInItem(mStartDragView,moveX,moveY)){
                        handler.removeCallbacks(mLongClickRunnable);
                    }

                    break;

                case MotionEvent.ACTION_UP:
                    //将线程移除
                    handler.removeCallbacks(mScrollRunnable);
                    handler.removeCallbacks(mLongClickRunnable);

                    break;
            }
        }

        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (isDrag && mDragImageView != null){
            int action = ev.getAction();
            switch (action){
                case MotionEvent.ACTION_MOVE:
                    Log.d("actionmove","moveY:  "+moveY+"");
                    moveX = (int) ev.getX();
                    moveY = (int) ev.getY();
                    //拖动item
                    onDragItem(moveX, moveY);
                    break;
                case MotionEvent.ACTION_UP:
                    onStopDrag();
                    isDrag = false;
                    mStartDragView = null;
                    break;
            }
            return true;
        }
        return super.onTouchEvent(ev);
    }

    private void onSwapItem(int downX, int downY){
        final int tempPosition = pointToPosition(downX, downY);

        if (tempPosition != mDragPosition && tempPosition != AbsListView.INVALID_POSITION){

            //为何这么调用？
            DragAdapter mDragAdapter = (DragAdapter) getAdapter();
//            mDragAdapter.hideItemAt(tempPosition-getFirstVisiblePosition());
//            mDragAdapter.reorderItem(mDragPosition,tempPosition);
//            getChildAt(tempPosition - getFirstVisiblePosition()).setVisibility(INVISIBLE);
//            getChildAt(mDragPosition - getFirstVisiblePosition()).setVisibility(VISIBLE);
//            mDragPosition = tempPosition;
            mDragAdapter.reorderItem(mDragPosition, tempPosition);
            mDragAdapter.hideItemAt(tempPosition);

            final ViewTreeObserver observer = getViewTreeObserver();
            observer.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {

                @Override
                public boolean onPreDraw() {
                    observer.removeOnPreDrawListener(this);
                    animRecorder(mDragPosition,tempPosition);
                    mDragPosition = tempPosition;
                    return true;
                }
            } );
        }
    }

    private void onDragItem(int moveX,int moveY){
        //更新镜像的位置
        mParams.x = moveX - mPoint2ItemLeft + mOffset2Left;
        mParams.y = moveY - mPoint2ItemTop + mOffset2Top - getStatusHeight(getContext());
        mWindowManager.updateViewLayout(mDragImageView, mParams);
        //显示隐藏Item
        onSwapItem(moveX,moveY);
        handler.post(mScrollRunnable);
    }

    private void onStopDrag(){
        View view = getChildAt(mDragPosition - getFirstVisiblePosition());
        if (view != null){
            view.setVisibility(VISIBLE);
        }
        //停止拖动，显示所有的item
        ((DragAdapter)getAdapter()).hideItemAt(-1);
        removeDragImage();
    }

    private void createDragImage(Bitmap bitmap){
        mParams = new WindowManager.LayoutParams();
        mParams.gravity = Gravity.TOP|Gravity.LEFT;
        mParams.format = PixelFormat.TRANSLUCENT;
        mParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        mParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        mParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE ;
        mParams.x = mDownX - mPoint2ItemLeft + mOffset2Left;
        mParams.y = mDownY - mPoint2ItemTop + mOffset2Top - getStatusHeight(getContext());
//        mParams.x = 200;
//        mParams.y = 600;
        mParams.alpha = 0.5f;

        mDragImageView = new ImageView(getContext());
        mWindowManager.addView(mDragImageView,mParams);
        mDragImageView.setImageBitmap(bitmap);
    }

    private boolean isTouchInItem(View view,int x,int y){
        if (view == null){
            return false;
        }
        if (x < view.getLeft())
            return false;
        if (x > view.getLeft()+view.getWidth())
            return false;
        if (y < view.getTop())
            return false;
        if (y > view.getTop()+view.getHeight())
            return false;
        return true;
    }

    private void removeDragImage(){
        if (mDragImageView != null){
            mWindowManager.removeView(mDragImageView);
            mDragImageView = null;
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

    private AnimatorSet animationCreator(View view, float startX, float endX, float startY, float endY){
        ObjectAnimator animX = ObjectAnimator.ofFloat(view,"translationX",startX,endX);
        ObjectAnimator animY = ObjectAnimator.ofFloat(view,"translationY",startY,endY);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animX,animY);
        return animatorSet;
    }

    private void animRecorder(int oldPosition,int newPosition){
        boolean isForward = oldPosition < newPosition;
        List<Animator> animList = new LinkedList<>();
        if (isForward){
            for (int i = oldPosition; i < newPosition; i++) {
                View view = getChildAt(i - getFirstVisiblePosition());
                if ((i + 1) % mNumcolunms == 0){
                    AnimatorSet animatorSet = animationCreator(view, view.getWidth() * (mNumcolunms - 1), 0, view.getHeight(), 0);
                    animList.add(animatorSet);
                }else {
                    AnimatorSet animatorSet = animationCreator(view,view.getWidth(),0,0,0);
                    animList.add(animatorSet);
                }
            }
        }else {
            for (int i = newPosition; i < oldPosition; i++) {
                View view = getChildAt(i - getFirstVisiblePosition());
                if ((i + mNumcolunms) % mNumcolunms == 0) {
                    animList.add(animationCreator(view,
                            view.getWidth() * (mNumcolunms - 1), 0,
                            -view.getHeight(), 0));
                } else {
                    animList.add(animationCreator(view,
                            -view.getWidth(), 0, 0, 0));
                }
            }
        }

        AnimatorSet resultSet = new AnimatorSet();
        resultSet.playTogether(animList);
        resultSet.setInterpolator(new AccelerateDecelerateInterpolator());
        resultSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                mAnimationEnd = true;
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                mAnimationEnd = false;
            }
        });
        resultSet.start();
    }
}
