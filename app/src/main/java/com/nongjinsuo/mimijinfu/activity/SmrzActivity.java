package com.nongjinsuo.mimijinfu.activity;

import android.content.Intent;
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
import com.nongjinsuo.mimijinfu.httpmodel.UserverificationModel;
import com.nongjinsuo.mimijinfu.util.LogUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author czz
 * @Description: (实名认证)
 */
public class SmrzActivity extends AbstractActivity {

    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.tvSfz)
    TextView tvSfz;
    @BindView(R.id.tvQkh)
    TextView tvQkh;
    UserverificationModel.Verification verification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smrz);
        ButterKnife.bind(this);
        init();
        addListener();
        loadData();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        AiMiCrowdFundingApplication.context().cancelPendingRequests(getClass().getName());
    }
    private void loadData() {
        showLoading();
        JacksonRequest<UserverificationModel> jacksonRequest = new JacksonRequest<>(RequestMap.getUserverification(1), UserverificationModel.class, new Response.Listener<UserverificationModel>() {
            @Override
            public void onResponse(UserverificationModel baseVo) {
                cancleLoading();
                if (baseVo.code.equals(Constants.SUCCESS)) {
                    verification = baseVo.result;
                    if (verification.getVerificationstatus() == Constants.TYPE_BYHK) {
                        tvQkh.setText("去绑卡");
                        tvQkh.setVisibility(View.VISIBLE);
                    }
                    if (verification.getVerificationstatus()>Constants.TYPE_BYHK&&verification.getVerificationstatus()<Constants.TYPE_KHCG&&verification.getVerificationstatus()!=Constants.TYPE_JBK){
                        tvQkh.setText("去开户");
                        tvQkh.setVisibility(View.VISIBLE);
                    }
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
    @Override
    public void init() {
        titleView.setText("实名认证");
        UserverificationModel.Verification verification = (UserverificationModel.Verification) getIntent().getSerializableExtra(SecuritySettingsActivity.VERIFICATION);
        if (verification != null) {
            tvName.setText(verification.getIdentityName());
            tvSfz.setText(verification.getIdentityNo());
        }
    }

    @Override
    public void addListener() {

    }

    @OnClick(R.id.tvQkh)
    public void onClick() {
        finish();
        Intent intent = new Intent(SmrzActivity.this, AccountSettingActivity.class);
        startActivity(intent);
    }
}
