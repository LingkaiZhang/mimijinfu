package com.nongjinsuo.mimijinfu.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.nongjinsuo.mimijinfu.widget.EventBarEntity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * @author czz
 * @Description: (安全设置)
 */
public class SecuritySettingsActivity extends AbstractActivity {
    @BindView(R.id.tvPhone)
    TextView tvPhone;
    @BindView(R.id.llBdsjh)
    LinearLayout llBdsjh;
    @BindView(R.id.ivSmrz)
    ImageView ivSmrz;
    @BindView(R.id.llSmrz)
    LinearLayout llSmrz;
    @BindView(R.id.ivYhk)
    ImageView ivYhk;
    @BindView(R.id.llYhk)
    LinearLayout llYhk;
    @BindView(R.id.llKqzjtg)
    LinearLayout llKqzjtg;
    @BindView(R.id.tvKqzitg)
    TextView tvKqzitg;
    @BindView(R.id.ivZjtg)
    ImageView ivZjtg;
    private UserverificationModel.Verification verification;
    public static final String VERIFICATION = "verification";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security_settings);
        ButterKnife.bind(this);
        init();
        addListener();
        loadData();
        EventBus.getDefault().register(this);
    }

    public void onEventMainThread(EventBarEntity entity) {
        if (entity.getType() == EventBarEntity.UPDATE_ANQUAN_SETTING) {
            loadData();
        } else if (entity.getType() == EventBarEntity.FINISH_ACTIVITY) {
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        AiMiCrowdFundingApplication.context().cancelPendingRequests(getClass().getName());
    }

    @Override
    public void init() {
        titleView.setText("安全设置");
    }

    @Override
    public void addListener() {

    }

    private void loadData() {
        showLoading();
        JacksonRequest<UserverificationModel> jacksonRequest = new JacksonRequest<>(RequestMap.getUserverification(1), UserverificationModel.class, new Response.Listener<UserverificationModel>() {
            @Override
            public void onResponse(UserverificationModel baseVo) {
                cancleLoading();
                if (baseVo.code.equals(Constants.SUCCESS)) {
                    verification = baseVo.result;
                    tvPhone.setText(verification.getMobile());
                    setStatusView(baseVo.result);
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

    private void setStatusView(UserverificationModel.Verification verification) {
        switch (verification.getVerificationstatus()) {
            case Constants.TYPE_SMRZ:
                ivSmrz.setVisibility(View.GONE);
                ivYhk.setVisibility(View.GONE);
                ivZjtg.setVisibility(View.GONE);
                tvKqzitg.setText("开启资金托管");
                break;
            case Constants.TYPE_BYHK:
                ivSmrz.setVisibility(View.VISIBLE);
                ivYhk.setVisibility(View.GONE);
                tvKqzitg.setText("开启资金托管");
                ivZjtg.setVisibility(View.GONE);
                break;
            case Constants.TYPE_ZFMM:
                ivSmrz.setVisibility(View.VISIBLE);
                ivYhk.setVisibility(View.VISIBLE);
                tvKqzitg.setText("开启资金托管");
                ivZjtg.setVisibility(View.GONE);
                break;
            case Constants.TYPE_SYKF:
                ivSmrz.setVisibility(View.VISIBLE);
                ivYhk.setVisibility(View.VISIBLE);
                tvKqzitg.setText("开启资金托管");
                ivZjtg.setVisibility(View.VISIBLE);
                break;
            case Constants.TYPE_KHCG:
                ivSmrz.setVisibility(View.VISIBLE);
                ivYhk.setVisibility(View.VISIBLE);
                tvKqzitg.setText("修改委托代扣");
                ivZjtg.setVisibility(View.VISIBLE);
                break;
            case Constants.TYPE_JBK:
                ivSmrz.setVisibility(View.VISIBLE);
                ivYhk.setVisibility(View.GONE);
                tvKqzitg.setText("修改委托代扣");
                ivZjtg.setVisibility(View.VISIBLE);
                break;
        }
    }

    @OnClick({R.id.llBdsjh, R.id.llSmrz, R.id.llYhk, R.id.llKqzjtg})
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.llBdsjh:
                intent = new Intent(SecuritySettingsActivity.this, PhoneActivity.class);
                intent.putExtra(VERIFICATION, verification);
                startActivity(intent);
                break;
            case R.id.llSmrz:
                if (verification != null) {
                    if (verification.getVerificationstatus() == Constants.TYPE_SMRZ) {
                        intent = new Intent(SecuritySettingsActivity.this, AccountSettingActivity.class);
                        startActivity(intent);
                    } else {
                        intent = new Intent(SecuritySettingsActivity.this, SmrzActivity.class);
                        intent.putExtra(VERIFICATION, verification);
                        startActivity(intent);
                    }
                }

                break;
            case R.id.llYhk:
                if (verification != null) {
                    if (verification.getVerificationstatus() <= Constants.TYPE_BYHK) {
                        intent = new Intent(SecuritySettingsActivity.this, AccountSettingActivity.class);
                        startActivity(intent);
                    } else if (verification.getVerificationstatus() == Constants.TYPE_JBK) {
                        intent = new Intent(SecuritySettingsActivity.this, AccountSettingActivity.class);
                        intent.putExtra(AccountSettingActivity.IS_KAI_HU, false);
                        startActivity(intent);
                    } else {
                        intent = new Intent(SecuritySettingsActivity.this, BankCardActivity.class);
                        intent.putExtra(VERIFICATION, verification);
                        startActivity(intent);
                    }
                }
                break;
            case R.id.llKqzjtg:
                if (verification != null) {
                    if (verification.getVerificationstatus() == Constants.TYPE_JBK) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(SecuritySettingsActivity.this);
                        builder.setMessage("请重新绑定银行卡");
                        builder.setNegativeButton("取消", null);
                        builder.setPositiveButton("去绑卡", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(SecuritySettingsActivity.this, AccountSettingActivity.class);
                                intent.putExtra(AccountSettingActivity.IS_KAI_HU, false);
                                startActivity(intent);
                            }
                        });
                        builder.show();
                        return;
                    }
                    if (verification.getVerificationstatus() <= Constants.TYPE_ZFMM) {
                        intent = new Intent(SecuritySettingsActivity.this, AccountSettingActivity.class);
                        startActivity(intent);
                    } else {
                        intent = new Intent(SecuritySettingsActivity.this, SetSinaPwdWebViewActivity.class);
                        intent.putExtra(SetSinaPwdWebViewActivity.URL, verification.getEditauthority_redirect_url());
                        intent.putExtra(SetSinaPwdWebViewActivity.IS_UPDATE_PWD, SetSinaPwdWebViewActivity.UPDATE_DAIKOU);
                        startActivity(intent);
                    }
                }
                break;
        }
    }

}
