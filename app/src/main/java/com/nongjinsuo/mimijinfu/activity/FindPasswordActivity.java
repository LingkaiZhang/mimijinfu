package com.nongjinsuo.mimijinfu.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
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
import com.nongjinsuo.mimijinfu.dao.http.Urls;
import com.nongjinsuo.mimijinfu.dialog.MyProgressDialog;
import com.nongjinsuo.mimijinfu.util.LogUtil;
import com.nongjinsuo.mimijinfu.util.NetworkUtils;
import com.nongjinsuo.mimijinfu.util.TextUtil;
import com.nongjinsuo.mimijinfu.util.ValidUtil;
import com.nongjinsuo.mimijinfu.widget.MyEditText;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author czz
 * @Description: (找回密码)
 */
public class FindPasswordActivity extends AbstractActivity implements IBase, View.OnClickListener {

    @BindView(R.id.etPhone)
    MyEditText etPhone;
    @BindView(R.id.tvHqCode)
    TextView tvHqCode;
    @BindView(R.id.progressSmall)
    ProgressWheel progressSmall;
    @BindView(R.id.llHqyzmView)
    LinearLayout llHqyzmView;
    @BindView(R.id.etCode)
    MyEditText etCode;
    @BindView(R.id.llCodeView)
    LinearLayout llCodeView;
    @BindView(R.id.tvYyCode)
    TextView tvYyCode;
    @BindView(R.id.llYzm)
    LinearLayout llYzm;
    @BindView(R.id.tvLogin)
    TextView tvLogin;
    @BindView(R.id.rlLoginView)
    LinearLayout rlLoginView;
    @BindView(R.id.tvProblem)
    TextView tvProblem;
    private int recLen = 60;
    //屏幕高度
    private TimerTask task;
    private MyProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        titleView.setText("找回密码");
        setContentView(R.layout.activity_find_password);
        ButterKnife.bind(this);
        init();
        addListener();
    }

    @Override
    public void init() {
        etPhone.requestFocus();


        progressDialog = new MyProgressDialog(FindPasswordActivity.this);
    }

    @Override
    public void addListener() {
        findViewById(R.id.tvYyCode).setOnClickListener(this);
        findViewById(R.id.tvLogin).setOnClickListener(this);

        tvHqCode.setOnClickListener(this);
        tvProblem.setOnClickListener(this);
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

    @Override
    public void onClick(View view) {
        String phont;
        String validPhone;
        switch (view.getId()) {
            case R.id.ibBack:
                finish();
                break;
            case R.id.tvHqCode:
                phont = etPhone.getText().toString().trim();
                validPhone = ValidUtil.validPhone(phont);
                if (TextUtil.IsNotEmpty(validPhone)) {
                    showShortToastMessage(validPhone);
                    return;
                }
                if (!NetworkUtils.isNetworkAvailable(FindPasswordActivity.this)){
                    showShortToastMessage(getString(R.string.str_no_network));
                    return;
                }
                etCode.requestFocus();
                progressSmall.setVisibility(View.VISIBLE);
                tvHqCode.setVisibility(View.INVISIBLE);
                getCode(phont, 1);
                break;
            case R.id.tvYyCode:
                phont = etPhone.getText().toString().trim();
                validPhone = ValidUtil.validPhone(phont);
                if (TextUtil.IsNotEmpty(validPhone)) {
                    showShortToastMessage(validPhone);
                    return;
                }
                if (!NetworkUtils.isNetworkAvailable(FindPasswordActivity.this)){
                    showShortToastMessage(getString(R.string.str_no_network));
                    return;
                }
                etCode.requestFocus();
                getCode(phont, 0);
                break;
            case R.id.tvLogin:
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
                if (code.length() < 5) {
                    showShortToastMessage("请输入正确的验证码");
                    return;
                }
                mobilecode(phont,code);
                break;
            case R.id.tvProblem:
                Intent intent = new Intent(FindPasswordActivity.this, ZxkfWebViewActivity.class);
                intent.putExtra(ZxkfWebViewActivity.URL, Urls.ZXKF);
                intent.putExtra(ZxkfWebViewActivity.NOZXKF,false);
                startActivity(intent);
                break;
        }
    }
    protected void onDestroy() {
        super.onDestroy();
        AiMiCrowdFundingApplication.context().cancelPendingRequests(getClass().getName());
    }
    /**
     * @param phone
     * @param sta   1为短信0为语音
     */
    public void getCode(String phone, final int sta) {
        if (sta == 0) {
            progressDialog.setMessage("正在发送语音");
            progressDialog.show();
        }
        JacksonRequest<BaseVo> jacksonRequest = new JacksonRequest<>(RequestMap.getCodeMap(phone, sta,0), BaseVo.class, new Response.Listener<BaseVo>() {
            @Override
            public void onResponse(BaseVo baseVo) {
                dismissDialog();
                if (baseVo.code.equals(Constants.SUCCESS)) {
                    if (sta == 1) {
                        llYzm.setVisibility(View.VISIBLE);
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
                dismissDialog();
                if (volleyError != null && volleyError.getCause() != null) {
                    LogUtil.d("volleyError", volleyError.getCause().getMessage());
                }
            }
        });
        jacksonRequest.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 1, 1.0f));
       AiMiCrowdFundingApplication.context().addToRequestQueue(jacksonRequest,getClass().getName());
    }

    /**
     * @param phone
     */
    public void mobilecode(final String phone, final String code) {
        if (!NetworkUtils.isNetworkAvailable(FindPasswordActivity.this)){
            showShortToastMessage(getString(R.string.str_no_network));
            return;
        }
        progressDialog.setMessage("正在验证");
        progressDialog.show();
        JacksonRequest<BaseVo> jacksonRequest = new JacksonRequest<>(RequestMap.getMobilecode(phone, code), BaseVo.class, new Response.Listener<BaseVo>() {
            @Override
            public void onResponse(BaseVo baseVo) {
                dismissDialog();
                if (baseVo.code.equals(Constants.SUCCESS)) {
                    finish();
                    Intent intent = new Intent(FindPasswordActivity.this,UpdatePasswordActivity.class);
                    intent.putExtra(UpdatePasswordActivity.CODE,code);
                    intent.putExtra(UpdatePasswordActivity.MOBILE,phone);
                    startActivity(intent);
                }else{
                    showShortToastMessage(baseVo.getText());
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

    private void dismissDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }
}
