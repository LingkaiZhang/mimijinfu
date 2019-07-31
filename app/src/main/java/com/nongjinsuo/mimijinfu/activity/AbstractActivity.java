package com.nongjinsuo.mimijinfu.activity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nongjinsuo.mimijinfu.R;
import com.nongjinsuo.mimijinfu.config.Constants;
import com.nongjinsuo.mimijinfu.util.StatusBarUtil;
import com.nongjinsuo.mimijinfu.util.Util;
import com.pnikosis.materialishprogress.ProgressWheel;
import com.umeng.analytics.MobclickAgent;
/** 
 * description: 基础类
 * autour: czz
 * date: 2017/4/21 0021 下午 5:28 
 * update: 2017/4/21 0021
 * version: 
*/
public abstract class AbstractActivity extends FragmentActivity implements IBase {
    protected ViewGroup mainBody; // 主体显示

    protected boolean isTemplate = true; // 是否使用模板
    protected boolean isStatusBarTransparent = false;//不是用模版titlebar时候 状态栏设置为什么颜色  true 透明，false 白色
    protected View titleBar;// 标题栏
    protected TextView titleView; // 标题
    protected ImageButton templateButtonLeft; // 模板中左则Button,不使用模板时，不会显示;
    protected TextView templateTextViewLeft;// 模板左侧TextView
    protected ImageButton templateButtonRight; // 模板中右则Button,不使用模板时，不会显示;
    protected TextView templateTextViewRight;//模板右侧TextView
    private View reLoadView;// 重新加载的 布局
    private Toast mToast;
    private ProgressWheel progressWheel;
    private ImageView ivStatusBar;// 4.4 -5.0版本需要添加一个view给状态栏占位

    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.template);
        mainBody = (ViewGroup) findViewById(R.id.view_mainBody);
        reLoadView = findViewById(R.id.reLoadView);
        reLoadView.setVisibility(View.GONE);
        titleBar = findViewById(R.id.titleBar);
        ivStatusBar = (ImageView) findViewById(R.id.ivStatusBar);
        progressWheel = (ProgressWheel) findViewById(R.id.progress_wheel);
        if (!isTemplate) {// 不使用模板就把标题栏去掉
            titleBar.setVisibility(View.GONE);
            //不使用模版的标题栏的时候：1.压根没有标题栏 2.有标题栏但是现有的无法满足
            if (isStatusBarTransparent) {
                StatusBarUtil.setTransparent(this, Color.TRANSPARENT);
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    if (Util.isMIUI()){
                        setStatusBar4_5(android.R.color.transparent);
                        StatusBarUtil.setStatusBarDarkMode(true, this);
                    }
                    else if (Util.isFlyme())
                        StatusBarUtil.setStatusBarDarkIcon(getWindow(), true);
                    else {
                        StatusBarUtil.setTransparent(this, Color.BLACK);
                        setStatusBar4_5(android.R.color.transparent);
                    }
                } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                    StatusBarUtil.setStatusBar(true, this);
                    setStatusBar4_5(R.color.titlebar_color);

                }
            }
        } else {
            templateButtonLeft = (ImageButton) findViewById(R.id.title_but_left);
            templateTextViewLeft = (TextView) findViewById(R.id.title_tv_left);
            templateButtonRight = (ImageButton) findViewById(R.id.title_but_right);
            templateTextViewRight = (TextView) findViewById(R.id.title_tv_right);
            templateButtonLeft.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
            titleView = (TextView) findViewById(R.id.title_text);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//判断系统Android版本>=5.0
                if (Util.isMIUI())
                {
                    setStatusBar4_5(android.R.color.transparent);
                    StatusBarUtil.setStatusBarDarkMode(true, this);
                }
                else if (Util.isFlyme())
                    StatusBarUtil.setStatusBarDarkIcon(getWindow(), true);
                else {
                    StatusBarUtil.setTransparent(this, Color.BLACK);
                    setStatusBar4_5(android.R.color.transparent);
                }
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {//4.4=<判断系统Android版本<5.0
                StatusBarUtil.setStatusBar(true, this);
                setStatusBar4_5(R.color.titlebar_color);
            }
        }
        // 获取屏幕的宽高
        Display defaultDisplay = getWindowManager().getDefaultDisplay();
        Constants.WINDOW_WIDTH = defaultDisplay.getWidth();
        Constants.WINDOW_HEIGHT = defaultDisplay.getHeight();

        Util.getUserInfo(this);
    }

    private void setStatusBar4_5(int color) {
        ivStatusBar.setVisibility(View.VISIBLE);
        ivStatusBar.setBackgroundColor(getResources().getColor(color));
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, Util.getStatusBarHeight(this));
        ivStatusBar.setLayoutParams(layoutParams);
    }


    protected void showLoading() {
        if (progressWheel != null)
            progressWheel.setVisibility(View.VISIBLE);
    }

    protected void cancleLoading() {
        if (progressWheel != null)
            progressWheel.setVisibility(View.GONE);
    }

    /**
     * 简化findView操作，无需每次都去强制转换
     *
     * @param id
     * @return
     */
    protected <T extends View> T getViewById(int id) {
        return (T) findViewById(id);
    }

    protected <T extends View> T getViewById(View v, int id) {
        return (T) v.findViewById(id);
    }

    @Override
    public void setContentView(int layoutResID) {
        if (layoutResID == R.layout.template) {
            super.setContentView(layoutResID);
        } else {
            mainBody.removeAllViews();
            mainBody.addView(this.getLayoutInflater().inflate(layoutResID, null));
        }
    }

    public void showShortToastMessage(String message) {
        if (mToast == null) {
            mToast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(message);
            mToast.setDuration(Toast.LENGTH_SHORT);
        }
        mToast.show();
    }

    public void cancelToast() {
        if (mToast != null) {
            mToast.cancel();
        }
    }

    public void onBackPressed() {
        cancelToast();
        super.onBackPressed();
    }

    @Override
    public void setContentView(View view) {
        mainBody.removeAllViews();
        mainBody.addView(view);
    }

    @Override
    public void setContentView(View view, LayoutParams params) {
        mainBody.removeAllViews();
        mainBody.addView(view, params);
    }


    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
        Util.getUserInfo(this);
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Util.getUserInfo(this);
    }
}
//