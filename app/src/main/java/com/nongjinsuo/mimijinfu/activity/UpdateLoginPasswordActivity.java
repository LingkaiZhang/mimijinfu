package com.nongjinsuo.mimijinfu.activity;

import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.pnikosis.materialishprogress.ProgressWheel;
import com.nongjinsuo.mimijinfu.R;
import com.nongjinsuo.mimijinfu.beans.BaseVo;
import com.nongjinsuo.mimijinfu.config.AiMiCrowdFundingApplication;
import com.nongjinsuo.mimijinfu.config.Constants;
import com.nongjinsuo.mimijinfu.dao.http.JacksonRequest;
import com.nongjinsuo.mimijinfu.dao.http.RequestMap;
import com.nongjinsuo.mimijinfu.dialog.MyProgressDialog;
import com.nongjinsuo.mimijinfu.httpmodel.LoginModel;
import com.nongjinsuo.mimijinfu.util.LogUtil;
import com.nongjinsuo.mimijinfu.util.TextUtil;
import com.nongjinsuo.mimijinfu.util.ValidUtil;
import com.nongjinsuo.mimijinfu.widget.MyEditText;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author czz
 * @Description: (修改登录密码)
 */
public class UpdateLoginPasswordActivity extends AbstractActivity {
    @BindView(R.id.etPhone)
    MyEditText etPhone;
    @BindView(R.id.tvHqCode)
    TextView tvHqCode;
    @BindView(R.id.progressSmall)
    ProgressWheel progressSmall;
    @BindView(R.id.etCode)
    MyEditText etCode;
    @BindView(R.id.etPwd)
    MyEditText etPwd;
    @BindView(R.id.cbShowPwd)
    CheckBox cbShowPwd;
    @BindView(R.id.tvConfirm)
    TextView tvConfirm;
    private TimerTask task;
    private int recLen = 60;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_login_password);
        ButterKnife.bind(this);
        init();
        addListener();
        titleView.setText("修改登录密码");
    }

    @Override
    public void init() {
    }

    @Override
    public void addListener() {
        cbShowPwd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    //选择状态 显示明文--设置为可见的密码
                    etPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    etPwd.setSelection(etPwd.getText().toString().length());
                } else {
                    //默认状态显示密码--设置文本 要一起写才能起作用 InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD
                    etPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    etPwd.setSelection(etPwd.getText().toString().length());
                }
            }
        });
    }

    private void loadData(String phone, String code, String password) {
        final MyProgressDialog myProgressDialog = new MyProgressDialog(this, "正在修改密码");
        myProgressDialog.show();
        JacksonRequest<LoginModel> jacksonRequest = new JacksonRequest<>(RequestMap.getRevisepassword(phone,code,password), LoginModel.class, new Response.Listener<LoginModel>() {
            @Override
            public void onResponse(LoginModel baseVo) {
                myProgressDialog.dismiss();
                if (baseVo.code.equals(Constants.SUCCESS)) {
                    finish();
                }
                showShortToastMessage(baseVo.text);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                myProgressDialog.dismiss();
                if (volleyError != null && volleyError.getCause() != null) {
                    LogUtil.d("volleyError", volleyError.getCause().getMessage());
                }
            }
        });
       AiMiCrowdFundingApplication.context().addToRequestQueue(jacksonRequest,getClass().getName());
    }

    @OnClick({R.id.tvHqCode, R.id.tvConfirm})
    public void onClick(View view) {
        String phont;
        String validPhone;
        switch (view.getId()) {
            case R.id.tvHqCode:
                phont = etPhone.getText().toString().trim();
                validPhone = ValidUtil.validPhone(phont);
                if (TextUtil.IsNotEmpty(validPhone)) {
                    showShortToastMessage(validPhone);
                    return;
                }
                etCode.requestFocus();
                progressSmall.setVisibility(View.VISIBLE);
                tvHqCode.setVisibility(View.INVISIBLE);
                getCode(phont, 1);
                break;
            case R.id.tvConfirm:
                phont = etPhone.getText().toString().trim();
                validPhone = ValidUtil.validPhone(phont);
                if (TextUtil.IsNotEmpty(validPhone)) {
                    showShortToastMessage(validPhone);
                    return;
                }
                String code = etCode.getText().toString().trim();
                if (TextUtil.IsEmpty(code)) {
                    showShortToastMessage("请输入验证码");
                    return;
                }
                if (code.length()<5) {
                    showShortToastMessage("请输入正确的验证码");
                    return;
                }
                String pwd = etPwd.getText().toString().trim();
                String validPassword = ValidUtil.validPassword(pwd);
                if (TextUtil.IsNotEmpty(validPassword)) {
                    showShortToastMessage(validPassword);
                    return;
                }
                loadData(phont, code, pwd);
                break;
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        AiMiCrowdFundingApplication.context().cancelPendingRequests(getClass().getName());
    }
    /**
     * @param phone
     * @param sta   1为短信0为语音
     */
    public void getCode(String phone, final int sta) {
        JacksonRequest<BaseVo> jacksonRequest = new JacksonRequest<>(RequestMap.getCodeMap(phone, sta,0), BaseVo.class, new Response.Listener<BaseVo>() {
            @Override
            public void onResponse(BaseVo baseVo) {
                if (baseVo.code.equals(Constants.SUCCESS)) {
                    if (sta == 1) {
                        haveTime();
                    }
                }else{
                    showShortToastMessage(baseVo.getText());
                    progressSmall.setVisibility(View.GONE);
                    tvHqCode.setVisibility(View.VISIBLE);
                    tvHqCode.setClickable(true);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                if (volleyError != null && volleyError.getCause() != null) {
                    LogUtil.d("volleyError", volleyError.getCause().getMessage());
                }
            }
        });
        jacksonRequest.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 1, 1.0f));
       AiMiCrowdFundingApplication.context().addToRequestQueue(jacksonRequest,getClass().getName());
    }

    private void haveTime() {
        tvHqCode.setClickable(false);
        final Timer timer = new Timer();
        task = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() { // UI thread
                    public void run() {
                        recLen--;
                        progressSmall.setVisibility(View.GONE);
                        tvHqCode.setVisibility(View.VISIBLE);
                        tvHqCode.setText(recLen + "秒");
                        if (recLen < 1) {
                            timer.cancel();
                            tvHqCode.setClickable(true);
                            recLen = 60;
                            tvHqCode.setText("重新发送");
                        }
                    }
                });
            }
        };
        timer.schedule(task, 1000, 1000);
    }

}
