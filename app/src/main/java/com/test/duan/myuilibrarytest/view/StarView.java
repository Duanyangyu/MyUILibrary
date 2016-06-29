package com.test.duan.myuilibrarytest.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.test.duan.myuilibrarytest.R;

/**
 * Created by Duanyy on 2016/5/12.
 */
public class StarView extends LinearLayout {

    private Context mContext;
    private int mStarLevel = 10;
    private LayoutParams mParams;

    public StarView(Context context) {
        this(context,null,0);
    }

    public StarView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public StarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        initView();
    }

    private void initView(){
        this.setOrientation(HORIZONTAL);
        mParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mParams.gravity = Gravity.CENTER;
        mParams.weight = 1;
        mParams.leftMargin = 10;
    }

    private void initStar(){
        this.removeAllViews();
        int grayCount = 0;
        if (mStarLevel <= 0){
            //添加五只灰色星星
            grayCount = 5;
        }else if (mStarLevel % 2 == 0){
            int count = mStarLevel / 2;
            //添加黄色星星
            for (int i = 0; i < count; i++) {
                addViewByType(1);
            }
            grayCount = 5 - count;

        }else if (mStarLevel % 2 != 0){
            int count = mStarLevel / 2;
            int _count = mStarLevel % 2;
            for (int i = 0; i < count; i++) {
                addViewByType(1);
            }
            for (int i = 0; i < _count; i++) {
                addViewByType(0);
            }
            grayCount = 5 - count - 1;
        }
        for (int i = 0; i < grayCount; i++) {
            addViewByType(-1);
        }
    }

    /**
     * 1:黄色星星
     * -1:灰色星星
     * 0:半个星星
     * @param type
     */
    private void addViewByType(int type){
        int res = R.mipmap.icon_star_yellow;
        if (-1 == type){
            res = R.mipmap.icon_star_gray;
        }
        if (0 == type){
            res = R.mipmap.icon_star_half;
        }
        ImageView imageView = new ImageView(mContext);
        imageView.setImageResource(res);
        addView(imageView,mParams);
    }

    /**
     * 设置星星的数量
     * level <= 0: 星星全部显示灰色；
     * level取值： 1——10
     */
    public void setStarLevel(int level){
        if (level > 10){
            this.mStarLevel = 10;
        }
        this.mStarLevel = level;
        initStar();
    }

}
