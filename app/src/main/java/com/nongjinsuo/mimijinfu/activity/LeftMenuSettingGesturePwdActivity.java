package com.nongjinsuo.mimijinfu.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.nongjinsuo.mimijinfu.R;
import com.nongjinsuo.mimijinfu.beans.BaseVo;
import com.nongjinsuo.mimijinfu.config.AiMiCrowdFundingApplication;
import com.nongjinsuo.mimijinfu.config.Constants;
import com.nongjinsuo.mimijinfu.config.SharedPreferenceUtil;
import com.nongjinsuo.mimijinfu.dao.http.JacksonRequest;
import com.nongjinsuo.mimijinfu.dao.http.RequestMap;
import com.nongjinsuo.mimijinfu.dialog.MyProgressDialog;
import com.nongjinsuo.mimijinfu.util.LogUtil;
import com.nongjinsuo.mimijinfu.util.TimeUtil;
import com.nongjinsuo.mimijinfu.util.Util;
import com.nongjinsuo.mimijinfu.widget.EventBarEntity;
import com.nongjinsuo.mimijinfu.widget.GesturePwdView;


import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

public class LeftMenuSettingGesturePwdActivity extends AbstractActivity implements OnClickListener {
    /**
     * 滑动锁对象
     **/
    private GesturePwdView mGesturePwdView;

    /**
     * 忘记密码
     **/
    private TextView mForgetTv;

    /**
     * 密码提示图片id集合
     **/
    private int[] tipsIvIds = new int[]{R.id.gesture_pwd_iv0, R.id.gesture_pwd_iv1, R.id.gesture_pwd_iv2,
            R.id.gesture_pwd_iv3, R.id.gesture_pwd_iv4, R.id.gesture_pwd_iv5, R.id.gesture_pwd_iv6, R.id.gesture_pwd_iv7,
            R.id.gesture_pwd_iv8};

    /**
     * 提示文本
     **/
    private TextView mtipsTv;

    private LinearLayout tipsIvLayout;

    /**
     * 密码提示图片集合
     **/
    private List<ImageView> tipsIvList = new ArrayList<ImageView>();

    private Animation shakeAnimation;


    /**
     * 手势密码 设置/未设置 textview标记
     **/
    private String gesturePwdStr;

    /**
     * 已设置过手势 并且修改手势验证成功,重启activity准备再次记录手势标示
     **/
    private boolean isChangeValidateSuccess = false;
    public static final String INTENT_TYPE = "intentType";
    private int errorInputNum = 5;
    private int intentType = 0;
    public static final int INTENT_MAIN = 1;
    public static final int INTENT_UPDATE = 0;
    public static final int INTENT_CANCLE = 2;
    private TextView tvDay;
    private TextView tvMonth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        isTemplate = false;
        isStatusBarTransparent = true;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_left_menu_setting_gesture_pwd);
        intentType = getIntent().getIntExtra(INTENT_TYPE, 0);
        isChangeValidateSuccess = getIntent().getBooleanExtra("isChangeValidateSuccess", false);
        initViews();
        setListener();
        initData();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.setting_gesture_pwd_forget: {
//                SharedPreferenceUtil.saveString(this, SharedPreferenceUtil.GESTURE_PWD_TAG, "");
//                // 刷新设置 界面textview
//                PasswordManagementActivity.getInstance().refreshGesturePwdTv();
//                finish();
                if (mGesturePwdView.validationStatus == GesturePwdView.GesturePwdStatus.VALIDATE_AGAIN) {
                    mGesturePwdView.validationStatus = GesturePwdView.GesturePwdStatus.INITIAL;
                    mGesturePwdView.tempResString = new StringBuffer();
                    mtipsTv.setText("绘制解锁密码");
                    refreshTipsImg(new ArrayList<Integer>());
                } else {
                    new AlertDialog.Builder(LeftMenuSettingGesturePwdActivity.this).setMessage("忘记密码,需要重新登录").setCancelable(true).setNegativeButton("取消", null).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            logOut();
                        }
                    }).show();
                }
                break;
            }
        }
    }

    public void initViews() {
        mGesturePwdView = (GesturePwdView) findViewById(R.id.setting_gesture_pwd_gesture_view);
        mtipsTv = (TextView) findViewById(R.id.setting_gesture_pwd_tips_tv);
        tipsIvLayout = (LinearLayout) findViewById(R.id.gesture_pwd_iv_layout);
        mForgetTv = (TextView) findViewById(R.id.setting_gesture_pwd_forget);
        tvDay = (TextView) findViewById(R.id.tvDay);
        tvMonth = (TextView) findViewById(R.id.tvMonth);
        for (int i = 0; i < tipsIvIds.length; i++) {
            ImageView iv = (ImageView) findViewById(tipsIvIds[i]);
            tipsIvList.add(iv);
        }
        tvDay.setText(TimeUtil.getDayNum());
        tvMonth.setText(TimeUtil.getMonthEnglish());
    }

    public void setListener() {
        // TODO Auto-generated method stub
        mForgetTv.setOnClickListener(this);
        // 左右移动动画
        shakeAnimation = AnimationUtils.loadAnimation(LeftMenuSettingGesturePwdActivity.this, R.anim.shake);
        // 手势完成后回调
        mGesturePwdView.setOnGestureFinishListener(new GesturePwdView.OnGestureFinishListener() {

            @Override
            public void OnGestureFinish(int status, String key, List<Integer> linedCycles) {
                // TODO Auto-generated method stub
                switch (status) {
                    case GesturePwdView.GesturePwdStatus.NUMBER_ERROR:// 连接点数少于4个
                        mtipsTv.setText(getResources().getString(R.string.setting_gesture_pwd_number));
                        mtipsTv.startAnimation(shakeAnimation);
                        break;
                    case GesturePwdView.GesturePwdStatus.VALIDATE_AGAIN:// 第一次手势绘制成功 二次验证状态
                        mtipsTv.setText(getResources().getString(R.string.setting_gesture_pwd_drawing_again));
                        refreshTipsImg(linedCycles);
                        mForgetTv.setVisibility(View.VISIBLE);
                        mForgetTv.setText("重新绘制");

                        break;
                    case GesturePwdView.GesturePwdStatus.VALIDATE_AGAIN_ERROR:// 手势设置失败 二次验证状态
                        mtipsTv.setText(getResources().getString(R.string.setting_gesture_pwd_drawing_inconsistent));
                        mtipsTv.startAnimation(shakeAnimation);
                        break;
                    case GesturePwdView.GesturePwdStatus.VALIDATE_AGAIN_SUCCESS:// 手势设置成功 二次验证状态
                        SharedPreferenceUtil.saveString(LeftMenuSettingGesturePwdActivity.this, SharedPreferenceUtil.GESTURE_PWD_TAG, key);
                        // 刷新设置 界面textview
                        PasswordManagementActivity.getInstance().refreshGesturePwdTv();
                        if (isChangeValidateSuccess) {
                            Toast.makeText(LeftMenuSettingGesturePwdActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(LeftMenuSettingGesturePwdActivity.this, "设置成功", Toast.LENGTH_SHORT).show();
                        }
                        LeftMenuSettingGesturePwdActivity.this.finish();
                        break;
                    case GesturePwdView.GesturePwdStatus.VALIDATE_SUCCESS:// 对比一致 与成功记录手势对比
                        if (intentType == INTENT_MAIN) {
                            LeftMenuSettingGesturePwdActivity.this.finish();
                        } else if (intentType == INTENT_CANCLE) {
                            SharedPreferenceUtil.removeString(LeftMenuSettingGesturePwdActivity.this, SharedPreferenceUtil.GESTURE_PWD_TAG);
                            LeftMenuSettingGesturePwdActivity.this.finish();
                        } else {
//                            LeftMenuSettingGesturePwdActivity.this.finish();
//                            Intent intent =
//                                    new Intent(LeftMenuSettingGesturePwdActivity.this, LeftMenuSettingGesturePwdActivity.class);
//                            intent.putExtra("isChangeValidateSuccess", true);
//                            startActivity(intent);
                            mGesturePwdView.validationStatus = GesturePwdView.GesturePwdStatus.INITIAL;
                            isChangeValidateSuccess = true;
                            intentType = 0;
                            initData();
                            mGesturePwdView.tempResString = new StringBuffer();
                        }
                        break;
                    case GesturePwdView.GesturePwdStatus.VALIDATE_ERROR:// 对比不一致 与成功记录手势对比

                        if (errorInputNum == 5) {
                            mtipsTv.setText(getResources().getString(R.string.setting_gesture_pwd_validate_error));
                        } else {
                            mtipsTv.setText("密码输入错误，您还可以输入" + errorInputNum + "次");
                        }
                        errorInputNum--;
                        if (errorInputNum == 0) {
                            new AlertDialog.Builder(LeftMenuSettingGesturePwdActivity.this).setMessage("您已连续5次输错手势，手势密码已关闭，请重新登录").setCancelable(false).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    logOut();
                                }
                            }).show();
                            return;
                        }
                        mtipsTv.startAnimation(shakeAnimation);
                        break;
                    default:
                        break;
                }
            }
        });
    }

    protected void onDestroy() {
        super.onDestroy();
        AiMiCrowdFundingApplication.context().cancelPendingRequests(getClass().getName());
    }

    /**
     * 退出登录
     */
    void logOut() {
        final MyProgressDialog myProgressDialog = new MyProgressDialog(LeftMenuSettingGesturePwdActivity.this, "正在退出登录");
        myProgressDialog.show();
        JacksonRequest<BaseVo> jacksonRequest = new JacksonRequest<>(RequestMap.getlogout(), BaseVo.class, new Response.Listener<BaseVo>() {
            @Override
            public void onResponse(BaseVo baseVo) {
                myProgressDialog.dismiss();
                if (baseVo.code.equals(Constants.SUCCESS)) {
                    if (intentType == INTENT_UPDATE || intentType == INTENT_CANCLE) {
                        if (PasswordManagementActivity.getInstance() != null) {
                            PasswordManagementActivity.getInstance().finish();
                        }
                        if (SettingActivity.getInstance() != null) {
                            SettingActivity.getInstance().finish();
                        }
                        EventBus.getDefault().post(new EventBarEntity(EventBarEntity.UPDATE_LOGOUT_VIEW));
                    }
                    finish();
                    Util.clearUserInfo(LeftMenuSettingGesturePwdActivity.this);
                    Intent intent = new Intent(LeftMenuSettingGesturePwdActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                if (volleyError != null && volleyError.getCause() != null) {
                    myProgressDialog.dismiss();
                    LogUtil.d("volleyError", volleyError.getCause().getMessage());
                }
            }
        });
        jacksonRequest.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 1, 1.0f));
       AiMiCrowdFundingApplication.context().addToRequestQueue(jacksonRequest,getClass().getName());
    }

    public void initData() {

        gesturePwdStr = SharedPreferenceUtil.getString(this, SharedPreferenceUtil.GESTURE_PWD_TAG);
        if (gesturePwdStr.length() > 0 && isChangeValidateSuccess == false)// 已设置密码但还没有与原密码验证的状态 相当于设置成功后第一次进入
        // 更改文字,title,隐藏tip img 更改滑动锁view初始状态,对比值
        {
            tipsIvLayout.setVisibility(View.GONE);
            mGesturePwdView.validationStatus = GesturePwdView.GesturePwdStatus.VALIDATE;
            mGesturePwdView.tempResString = new StringBuffer(gesturePwdStr);
            if (intentType == INTENT_MAIN) {
                mtipsTv.setText(getResources().getString(R.string.setting_gesture_pwd));
            } else if (intentType == INTENT_CANCLE) {
                mtipsTv.setText(getResources().getString(R.string.setting_gesture_pwd));
            } else {
                mtipsTv.setText(getResources().getString(R.string.setting_gesture_pwd_validate));
                mForgetTv.setVisibility(View.INVISIBLE);
            }
        } else {// 准备存储密码
            tipsIvLayout.setVisibility(View.VISIBLE);
            mtipsTv.setText("绘制解锁密码");
        }

    }

    /**
     * 刷新提示图片
     **/
    public void refreshTipsImg(List<Integer> linedCycles) {
        for (int i = 0; i < tipsIvList.size(); i++) {
            tipsIvList.get(i).setImageResource(R.drawable.gesture_pwd_round_white);
            for (Integer integer : linedCycles) {
                if (i == integer) {
                    tipsIvList.get(i).setImageResource(R.drawable.gesture_pwd_round_blue);
                }
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (MainActivity.getActivity() != null) {
                MainActivity.getActivity().finish();
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void init() {

    }

    @Override
    public void addListener() {

    }
}
