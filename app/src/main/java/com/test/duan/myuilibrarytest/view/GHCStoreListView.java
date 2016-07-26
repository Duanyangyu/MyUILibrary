package com.test.duan.myuilibrarytest.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.TextView;

import com.test.duan.myuilibrarytest.R;


/*
 * Created by Duanyy on 2015/11/20.
 */
public class GHCStoreListView extends ListView {
    private int ITEM_HEIGHT ;//标准item高,
    private int mITEM_MAX_HEIGHT ;
    private int mLastFirstVisiblePosition = 0;
    private int distanceOneItem;//记录滚动距离，向上滚动时-ITEM_HEIGHT到0，向下滚动是0到ITEM_HEIGHT,当listview FirstVisiblePosition 设置为0
    private int mLastDistanceOneItem = 1;
    private int MIN_FONT_SIZE_STORE_NAME = 25;
    private int MAX_FONT_SIZE_STORE_NAME = 15;
    private int selfHeight ;

    public GHCStoreListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();

        selfHeight = (int) getResources().getDimension(R.dimen.store_item_height);
        ITEM_HEIGHT = selfHeight / 4;
        mITEM_MAX_HEIGHT = selfHeight / 2;

        Log.d("height",ITEM_HEIGHT+","+mITEM_MAX_HEIGHT);
    }

    private void init(){
        this.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return gestureDetector.onTouchEvent(event);
            }
        });
    }

    public boolean canScrollVertically(int direction) {
        final int offset = computeVerticalScrollOffset();
        final int range = computeVerticalScrollRange() - computeVerticalScrollExtent();
        if (range == 0) return false;
        if (direction < 0) {
            return offset > 0;
        } else {
            return offset < range - 1;
        }
    }

    private GestureDetector gestureDetector = new GestureDetector(
            new GestureDetector.SimpleOnGestureListener(){
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return super.onSingleTapUp(e);
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    super.onLongPress(e);
                }

                @Override
                public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                    Log.d("distanceY",distanceY+"");
                    if (canScrollVertically(Math.round(distanceY))) {
                        distanceOneItem += Math.round(distanceY);
                    } else {
                        distanceOneItem = 0;
                        if (distanceY > 0) {
                            mLastDistanceOneItem = 0;
                        } else {
                            mLastDistanceOneItem = 1;
                        }
                    }

                    if (getFirstVisiblePosition() == mLastFirstVisiblePosition) {
                        if ((distanceY < 0 && (mLastDistanceOneItem >= 0 && distanceOneItem < 0))
                                || (distanceY > 0 && (mLastDistanceOneItem < 0 && distanceOneItem >= 0))) {//从正变负或从负变正，但是firstposition没变
                            return false;
                        } else {
                            mLastDistanceOneItem = distanceOneItem;
                        }
                        mLastFirstVisiblePosition = getFirstVisiblePosition();
                    } else {
                        mLastFirstVisiblePosition = getFirstVisiblePosition();
                        distanceOneItem = 0;
                        if (distanceY > 0) {
                            mLastDistanceOneItem = 1;
                        } else {
                            mLastDistanceOneItem = -1;
                        }
                    }
                    changeItemHeightOnScroll();
                    return false;
                }

                @Override
                public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                    return super.onFling(e1, e2, velocityX, velocityY);
//                    return true;
                }

                @Override
                public void onShowPress(MotionEvent e) {
                    super.onShowPress(e);
                }

                @Override
                public boolean onDown(MotionEvent e) {
                    return super.onDown(e);
                }
            }
    );
    private void changeItemHeightOnScroll() {
        View item0 = getChildAt(0);
        View item1 = getChildAt(1);
        View item2 = null;
        View view = getChildAt(2);
        if (view != null){
            Object tag = view.getTag();
            if (tag instanceof String){
                if ("building".equals(tag)){
                    item2 = view;
                }
            }
        }
        //第二个item中的服务中心名字文本框
        TextView tv_name1 = (TextView) item1.findViewById(R.id.tv_name);
        //第二个item中的电话号码文本框
        TextView tv_phone1 = (TextView) item1.findViewById(R.id.tv_phone);
        //第二个item中的电话和地图图片
        View vg_phone_map1 = item1.findViewById(R.id.vg_phone_map);
        //第一个item中的服务中心名字文本框
        TextView tv_name0 = (TextView) item0.findViewById(R.id.tv_name);
        //第一个item中的电话号码文本框，设置初始透明度
        TextView tv_phone0 = (TextView) item0.findViewById(R.id.tv_phone);
        //第一个item中的电话和地图图片，设置初始透明度
        View vg_phone_map0 = item0.findViewById(R.id.vg_phone_map);
        //第一个item中的遮罩
        View v_black0 = item0.findViewById(R.id.iv_black);
        //第二个item中的遮罩
        View v_black1 = item1.findViewById(R.id.iv_black);
        //设置第一个item中的控件透明度
        tv_phone0.setAlpha(1);
        vg_phone_map0.setAlpha(1);
        v_black0.setAlpha(0f);
        //服务中心名字设置初始大小
        tv_name0.setTextSize(TypedValue.COMPLEX_UNIT_SP,MAX_FONT_SIZE_STORE_NAME);
        int changeHeight1;
        int changeHeight2;
        int change;
        int changeHeight;
        if (distanceOneItem == 0) return;
        if (distanceOneItem > 0) {
            changeHeight1 = distanceOneItem * mITEM_MAX_HEIGHT / ITEM_HEIGHT;//放大

            if (changeHeight1 > mITEM_MAX_HEIGHT) {
                changeHeight1 = mITEM_MAX_HEIGHT;
            }
            if (changeHeight1 <= ITEM_HEIGHT) {
                changeHeight1 = ITEM_HEIGHT;
            }
            changeHeight2 = changeHeight1;
            change = changeHeight1 - item1.getHeight();
            changeHeight = item0.getHeight() - change;
            if (changeHeight > mITEM_MAX_HEIGHT) {
                changeHeight = mITEM_MAX_HEIGHT;
            }
            if (changeHeight <= ITEM_HEIGHT) {
                changeHeight = ITEM_HEIGHT;
            }
            //向上滑动的时候
            //改变服务中心名字的字体大小
            float fontSize1 = 10f * (float)distanceOneItem/(float)ITEM_HEIGHT+MIN_FONT_SIZE_STORE_NAME;
            if (fontSize1 > 25){
                fontSize1 = 25;
            }
            if (fontSize1 < 15){
                fontSize1 = 15;
            }
            if (tv_name1 != null) {
                tv_name1.setTextSize(TypedValue.COMPLEX_UNIT_SP,fontSize1);
            }
            //改变图片和文字的透明度
            float alpha = (float)distanceOneItem/(float)(ITEM_HEIGHT);
            Log.d("alpha===","向上滑动"+alpha);
            if (vg_phone_map1 != null){
                vg_phone_map1.setAlpha(alpha);
            }
            if (tv_phone1 != null) {
                tv_phone1.setAlpha(alpha);
            }
//            Log.d("------distanceOneItem",distanceOneItem+"");
//            Log.d("------alpha",vg_phone_map1.getId()+"-------"+alpha+"");
            //改变遮罩的透明度
//            float black_alpha = (float) (0.5-(0.5*(float)distanceOneItem/(float)(ITEM_HEIGHT)));
//            v_black1.setAlpha(black_alpha);
            //改变图片的大小

            //改变item的高度
            Log.d("上滑height",changeHeight+","+changeHeight1);
            item0.setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, changeHeight));
            if (item2 == null){
                item1.setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, changeHeight1));
            }
            if (item2 != null){
                item2.setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, changeHeight2));
                Log.d("item2",changeHeight2+"");
            }
        } else {
            changeHeight1 = (ITEM_HEIGHT + distanceOneItem) * mITEM_MAX_HEIGHT / ITEM_HEIGHT;//缩小
            if (changeHeight1 > mITEM_MAX_HEIGHT) {
                changeHeight1 = mITEM_MAX_HEIGHT;
            }
            changeHeight2 = changeHeight1;
            if (changeHeight1 <= ITEM_HEIGHT) {
                changeHeight1 = ITEM_HEIGHT;
            }
            change = item1.getHeight() - changeHeight1;
            changeHeight = item0.getHeight() + change;//放大
            if (changeHeight > mITEM_MAX_HEIGHT) {
                changeHeight = mITEM_MAX_HEIGHT;
            }
            if (changeHeight <= ITEM_HEIGHT) {
                changeHeight = ITEM_HEIGHT;
            }
            //向下滑动的时候
            //改变服务中心名字的大小
            float fontSize1 = MAX_FONT_SIZE_STORE_NAME + 10f * (float)distanceOneItem/(float)ITEM_HEIGHT;
            if (fontSize1 < MIN_FONT_SIZE_STORE_NAME){
                fontSize1 = MIN_FONT_SIZE_STORE_NAME;
            }
            Log.d("aaaaa",fontSize1+"");
            if (tv_name1 != null) {
                tv_name1.setTextSize(TypedValue.COMPLEX_UNIT_SP,fontSize1);
            }
            //改变遮罩的透明度
//            float black_alpha = (float) (0f-(0.5*(float)distanceOneItem/(float)(ITEM_HEIGHT)));
//            Log.d("black alpha",black_alpha+"");
//            v_black1.setAlpha(black_alpha);
            //改变图片和文字的透明度
            float alpha = 1f+(float)distanceOneItem/(float)(ITEM_HEIGHT);
            Log.d("alpha===","向下滑动"+alpha);
            if (vg_phone_map1 != null) {
                vg_phone_map1.setAlpha(alpha);
            }
            if (tv_phone1 != null) {
                tv_phone1.setAlpha(alpha);
            }
//            Log.d("++++++distanceOneItem",distanceOneItem+"");
//            Log.d("++++++alpha",vg_phone_map1.getId()+"--->"+distanceOneItem+"///"+alpha+"");
            //改变item的高度
            Log.d("下滑height", changeHeight + "," + changeHeight1);
//            item0.setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, changeHeight));
//            item1.setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, changeHeight1));
            if (item2 != null){
//                item2.setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, changeHeight1));
            }
        }
    }
}
