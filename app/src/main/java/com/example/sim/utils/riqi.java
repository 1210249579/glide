package com.example.sim.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.DatePicker;

import androidx.annotation.ColorRes;

public class riqi extends DatePicker {

    private Paint m = new Paint();

    @SuppressLint("NewApi")
    public riqi(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
    public riqi(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    public riqi(Context context, AttributeSet attrs ) {
        super(context, attrs);
    }
    public riqi(Context context) {
        super(context);
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //得到控件的size和模式
        int widthSpaceSize =MeasureSpec.getSize(widthMeasureSpec);
        int heightSpaceSize =MeasureSpec.getSize(heightMeasureSpec);
        int HeightSpaceMode =MeasureSpec.getMode(heightMeasureSpec);
        int widthSpaceMode =MeasureSpec.getMode(widthMeasureSpec);
        /**
         * 对AT_MOST模式进行纠正，使得warp_content有效
         * AT_MOST模式，就是经常用到的wrap_content
         * EXACTLY ，就是经常用到的match_parent,和精确到dp
         * 源码最后调用getDefaultSize中的MeasuredDimension方法进行确定控件的宽高
         * 由于里面源码，只要是自定义控件，无论padding,还是warp_content,都是默认为全填充满
         * 所以要自定义去考虑
         */
        if (widthSpaceMode == MeasureSpec.AT_MOST && HeightSpaceMode == MeasureSpec.AT_MOST){
            setMeasuredDimension(2000,2000);
        }else if (widthSpaceMode == MeasureSpec.AT_MOST){
            setMeasuredDimension(2000,heightSpaceSize);
        }else if (HeightSpaceMode == MeasureSpec.AT_MOST){
            setMeasuredDimension(widthSpaceSize,2000);
        }
    }

    /**
     * 解决设置不了内边距，padding的问题，也可以自定义控件背景
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();
        int width = getWidth() - paddingLeft - paddingRight;
        int height = getHeight() - paddingTop - paddingBottom;
        //控件画圆
        int radius = Math.min(width,height) /2;
        m.setColor(Color.BLUE);
        canvas.drawCircle(paddingLeft +width/2 ,paddingTop + height/2,radius,m);
    }
}
