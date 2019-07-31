package com.nongjinsuo.mimijinfu.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.kyleduo.switchbutton.SwitchButton;
import com.nongjinsuo.mimijinfu.R;
import com.nongjinsuo.mimijinfu.config.AiMiCrowdFundingApplication;
import com.nongjinsuo.mimijinfu.config.Constants;
import com.nongjinsuo.mimijinfu.config.SharedPreferenceUtil;
import com.nongjinsuo.mimijinfu.dao.http.JacksonRequest;
import com.nongjinsuo.mimijinfu.dao.http.RequestMap;
import com.nongjinsuo.mimijinfu.httpmodel.UserverificationModel;
import com.nongjinsuo.mimijinfu.util.LogUtil;
import com.nongjinsuo.mimijinfu.widget.EventBarEntity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * @author czz
 * @Description: (密码管理)
 */
public class PasswordManagementActivity extends AbstractActivity {
    @BindView(R.id.llLoginPwd)
    LinearLayout llLoginPwd;
    @BindView(R.id.llJyPwd)
    LinearLayout llJyPwd;
    @BindView(R.id.tvSetLoginPwd)
    TextView tvSetLoginPwd;
    @BindView(R.id.tvSetJyPwd)
    TextView tvSetJyPwd;
    @BindView(R.id.Shoushi)
    LinearLayout Shoushi;
    public UserverificationModel.Verification verification;
    @BindView(R.id.switch1)
    SwitchButton switch1;
    @BindView(R.id.llUpdateSsMm)
    LinearLayout llUpdateSsMm;
    /**
     * 手势密码 设置/未设置 textview标记
     **/
    private String gesturePwdStr = "";
    private static PasswordManagementActivity activity;
    public static PasswordManagementActivity getInstance() {
        return activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        titleView.setText("密码管理");
        setContentView(R.layout.activity_password_management);
        ButterKnife.bind(this);
        init();
        addListener();
        EventBus.getDefault().register(this);
        activity = this;
        refreshGesturePwdTv();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        AiMiCrowdFundingApplication.context().cancelPendingRequests(getClass().getName());
    }

    public void onEventMainThread(EventBarEntity entity) {
        if (entity.getType() == EventBarEntity.UPDATE_SET_PWD) {
            loadData();
        } else if (entity.getType() == EventBarEntity.FINISH_ACTIVITY) {
            finish();
        }
    }

    @Override
    public void init() {
        loadData();
    }

    @Override
    public void addListener() {
        switch1.setClickable(false);
    }
    private void loadData() {
        showLoading();
        JacksonRequest<UserverificationModel> jacksonRequest = new JacksonRequest<>(RequestMap.getUserverification(1), UserverificationModel.class, new Response.Listener<UserverificationModel>() {
            @Override
            public void onResponse(UserverificationModel baseVo) {
                cancleLoading();
                if (baseVo.code.equals(Constants.SUCCESS)) {
                    verification = baseVo.result;
                    if (verification.getVerificationstatus() == Constants.TYPE_KHCG) {
                        tvSetJyPwd.setText("修改交易密码");
                    } else {
                        tvSetJyPwd.setText("设置交易密码");
                    }
                } else {
                    showShortToastMessage(baseVo.getText());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                cancleLoading();
                if (volleyError != null && volleyError.getCause() != null) {
                    LogUtil.d("volleyError", volleyError.getCause().getMessage());
                }
            }
        });
        jacksonRequest.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 1, 1.0f));
       AiMiCrowdFundingApplication.context().addToRequestQueue(jacksonRequest,getClass().getName());
    }

    @OnClick({R.id.llLoginPwd, R.id.llJyPwd, R.id.Shoushi,R.id.llUpdateSsMm})
    public void onClick(View view) {
        final Intent intent;
        switch (view.getId()) {
            case R.id.llLoginPwd:
                intent = new Intent(PasswordManagementActivity.this, UpdateLoginPasswordActivity.class);
                startActivity(intent);
                break;
            case R.id.llJyPwd:
                if (verification.getVerificationstatus() == Constants.TYPE_KHCG) {
                    intent = new Intent(PasswordManagementActivity.this, SetSinaPwdWebViewActivity.class);
                    intent.putExtra(SetSinaPwdWebViewActivity.URL, verification.getEditpaypwd_redirect_url());
                    intent.putExtra(SetSinaPwdWebViewActivity.IS_UPDATE_PWD, SetSinaPwdWebViewActivity.UPDATE_PWD);
                    startActivity(intent);
                } else {
                    intent = new Intent(PasswordManagementActivity.this, AccountSettingActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.Shoushi:
                if (gesturePwdStr.length() > 0)//已设置手势
                {
                    switch1.setChecked(true);
                    intent = new Intent(PasswordManagementActivity.this, LeftMenuSettingGesturePwdActivity.class);
                    intent.putExtra(LeftMenuSettingGesturePwdActivity.INTENT_TYPE, LeftMenuSettingGesturePwdActivity.INTENT_CANCLE);
                    startActivity(intent);
                } else {
                    switch1.setChecked(true);
                    intent = new Intent(PasswordManagementActivity.this, LeftMenuSettingGesturePwdActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.llUpdateSsMm:
                startActivity(new Intent(PasswordManagementActivity.this, LeftMenuSettingGesturePwdActivity.class));
                break;
        }
    }

    /**
     * 刷新 手势密码设置/未设置 textview
     **/
    public void refreshGesturePwdTv() {
        gesturePwdStr = SharedPreferenceUtil.getString(this, SharedPreferenceUtil.GESTURE_PWD_TAG);
        if (gesturePwdStr.length() > 0)//已设置手势
        {
            switch1.setChecked(true);
            llUpdateSsMm.setVisibility(View.VISIBLE);
        } else {
            switch1.setChecked(false);
            llUpdateSsMm.setVisibility(View.GONE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshGesturePwdTv();
    }

}
