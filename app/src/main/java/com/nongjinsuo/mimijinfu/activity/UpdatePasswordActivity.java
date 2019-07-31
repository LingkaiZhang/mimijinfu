package com.nongjinsuo.mimijinfu.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.nongjinsuo.mimijinfu.R;
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

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author czz
 * @Description: (重置密码)
 */
public class UpdatePasswordActivity extends AbstractActivity implements IBase, View.OnClickListener {

    @BindView(R.id.etPwd)
    MyEditText etPwd;
    @BindView(R.id.etPwdReqest)
    MyEditText etPwdReqest;
    @BindView(R.id.tvLogin)
    TextView tvLogin;
    private MyProgressDialog progressDialog;
    public static final String CODE = "code";
    public static final String MOBILE = "mobile";
    private String code;
    private String mobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        titleView.setText("重置密码");
        setContentView(R.layout.activity_update_password);
        ButterKnife.bind(this);
        init();
        addListener();
        code = getIntent().getStringExtra(CODE);
         mobile = getIntent().getStringExtra(MOBILE);
    }

    @Override
    public void init() {
        progressDialog = new MyProgressDialog(UpdatePasswordActivity.this);
    }

    @Override
    public void addListener() {
        tvLogin.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        String pwd;
        String validPhone;
        switch (view.getId()) {
            case R.id.ibBack:
                finish();
                break;
            case R.id.tvLogin:
                pwd = etPwd.getText().toString().trim();
                validPhone = ValidUtil.validPassword(pwd);
                if (TextUtil.IsNotEmpty(validPhone)) {
                    showShortToastMessage(validPhone);
                    return;
                }
                String pwdReqest = etPwdReqest.getText().toString().trim();
                if (TextUtil.IsEmpty(pwdReqest)){
                    showShortToastMessage("请再次输入密码");
                    return;
                }
                if (!pwd.equals(pwdReqest)){
                    showShortToastMessage("两次密码输入不一致");
                    return;
                }
                findPwd(pwd);
                break;
        }
    }


    private void dismissDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        AiMiCrowdFundingApplication.context().cancelPendingRequests(getClass().getName());
    }
    public void findPwd(String pwd) {
        progressDialog.setMessage("找回密码");
        progressDialog.show();
        JacksonRequest<LoginModel> jacksonRequest = new JacksonRequest<>(RequestMap.getEditpassword(mobile,code,pwd), LoginModel.class, new Response.Listener<LoginModel>() {
            @Override
            public void onResponse(LoginModel baseVo) {
                dismissDialog();
                showShortToastMessage(baseVo.text);
                if (baseVo.code.equals(Constants.SUCCESS)) {
                    finish();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                dismissDialog();
                if (volleyError != null && volleyError.getCause() != null) {
                    LogUtil.d("volleyError", volleyError.getCause().getMessage());
                }
            }
        });
        jacksonRequest.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 1, 1.0f));
       AiMiCrowdFundingApplication.context().addToRequestQueue(jacksonRequest,getClass().getName());
    }
}
