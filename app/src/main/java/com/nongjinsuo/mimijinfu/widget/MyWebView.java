package com.nongjinsuo.mimijinfu.widget;

import android.content.Context;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.webkit.WebView;

public class MyWebView extends WebView{
	public MyWebView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}
	public MyWebView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	public MyWebView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	PointF downP = new PointF();
    /** 触摸时当前的点 **/
    PointF curP = new PointF(); 
    OnSingleTouchListener onSingleTouchListener;

    @Override
    public boolean onTouchEvent(MotionEvent arg0) {
    	// TODO Auto-generated method stub
    	curP.x = arg0.getX();
        curP.y = arg0.getY();
        if(arg0.getAction() == MotionEvent.ACTION_DOWN){
            //记录按下时候的坐标
            //切记不可用 downP = curP ，这样在改变curP的时候，downP也会改变
            downP.x = arg0.getX();
            downP.y = arg0.getY();
            //此句代码是为了通知他的父ViewPager现在进行的是本控件的操作，不要对我的操作进行干扰
            //getParent().requestDisallowInterceptTouchEvent(true);
        }
        if(arg0.getAction() == MotionEvent.ACTION_UP){
            //在up时判断是否按下和松手的坐标为一个点
            //如果是一个点，将执行点击事件，这是我自己写的点击事件，而不是onclick
            if(Math.abs(downP.x-curP.x)<10 && Math.abs(downP.y-curP.y)<10){
                onSingleTouch();
                //return true;
            }
            else{
            	//getParent().requestDisallowInterceptTouchEvent(false);
            }
        }
    	return super.onTouchEvent(arg0);
    }
    public void onSingleTouch() {
        if (onSingleTouchListener!= null) {

            onSingleTouchListener.onSingleTouch();
        }
    }
    public interface OnSingleTouchListener {
        public void onSingleTouch();
    }

    public void setOnSingleTouchListener(OnSingleTouchListener onSingleTouchListener) {
        this.onSingleTouchListener = onSingleTouchListener;
    }
}
