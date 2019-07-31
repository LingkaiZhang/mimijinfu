package com.nongjinsuo.mimijinfu.widget;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.EditText;

import com.nongjinsuo.mimijinfu.R;

public class MyEditText extends EditText {

    private Context mContext;
    private Drawable imgDel_Gray;
    private Drawable imgDel_Bule;

    public MyEditText(Context context) {
        super(context);
        mContext = context;
        init();
    }

    public MyEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
        init();
    }

    public MyEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    /***
     * 初始化
     */
    public void init() {
        // TODO Auto-generated method stub
        imgDel_Bule = mContext.getResources().getDrawable(R.drawable.img_qk);
        setDrawble();
        // 对EditText文本状态监听
        this.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
                setDrawble();
            }
        });
    }

    /***
     * 设置图片
     */
    public void setDrawble() {
        if (this.length() < 1) {
            /****
             * 此方法意思是在EditText添加图片 参数： left - 左边图片id top - 顶部图片id right - 右边图片id
             * bottom - 底部图片id
             */
            this.setCompoundDrawablesWithIntrinsicBounds(null, null,
                    null, null);
        } else {
            this.setCompoundDrawablesWithIntrinsicBounds(null, null,
                    imgDel_Bule, null);
        }
    }

    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
        if (focused){// 此处为得到焦点时的处理内容
            if (getText().toString().length()>0){
                this.setCompoundDrawablesWithIntrinsicBounds(null, null,
                        imgDel_Bule, null);
            }
        }else{// 此处为失去焦点时的处理内容
            this.setCompoundDrawablesWithIntrinsicBounds(null, null,
                    null, null);
        }
    }

    /***
     * 设置删除事件监听
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // TODO Auto-generated method stub
        if (imgDel_Bule != null && event.getAction() == MotionEvent.ACTION_UP) {
            int eventX = (int) event.getRawX();
            int eventY = (int) event.getRawY();
            Rect rect = new Rect();
            getGlobalVisibleRect(rect);
            rect.left = rect.right - 50;
            if (rect.contains(eventX, eventY))
                setText("");
        }
        return super.onTouchEvent(event);
    }
}
