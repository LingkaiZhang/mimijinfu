package com.nongjinsuo.mimijinfu.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.nongjinsuo.mimijinfu.R;
import com.nongjinsuo.mimijinfu.beans.BankVo;
import com.nongjinsuo.mimijinfu.beans.UnbindVo;
import com.nongjinsuo.mimijinfu.config.AiMiCrowdFundingApplication;
import com.nongjinsuo.mimijinfu.config.Constants;
import com.nongjinsuo.mimijinfu.dao.http.JacksonRequest;
import com.nongjinsuo.mimijinfu.dao.http.RequestMap;
import com.nongjinsuo.mimijinfu.dao.http.Urls;
import com.nongjinsuo.mimijinfu.dialog.MyProgressDialog;
import com.nongjinsuo.mimijinfu.httpmodel.UserverificationModel;
import com.nongjinsuo.mimijinfu.util.LogUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author czz
 * @Description: (银行卡)
 */
public class BankCardActivity extends AbstractActivity {
    @BindView(R.id.ivBankIcon)
    ImageView ivBankIcon;
    @BindView(R.id.tvBankName)
    TextView tvBankName;
    @BindView(R.id.tvBankUserName)
    TextView tvBankUserName;
    @BindView(R.id.tvBankCard)
    TextView tvBankCard;
    BankVo bankVo;
    @BindView(R.id.tvQkh)
    TextView tvQkh;
    UserverificationModel.Verification verification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bankcard);
        ButterKnife.bind(this);
        init();
        addListener();
        loadData();
    }

    private void loadData() {
        showLoading();
        JacksonRequest<UserverificationModel> jacksonRequest = new JacksonRequest<>(RequestMap.getUserverification(1), UserverificationModel.class, new Response.Listener<UserverificationModel>() {
            @Override
            public void onResponse(UserverificationModel baseVo) {
                cancleLoading();
                if (baseVo.code.equals(Constants.SUCCESS)) {
                    verification = baseVo.result;
                    if (verification.getVerificationstatus() < Constants.TYPE_KHCG && verification.getVerificationstatus() != Constants.TYPE_JBK) {
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
        titleView.setText("银行卡");
        templateTextViewRight.setText("解绑");
        templateTextViewRight.setVisibility(View.VISIBLE);
        UserverificationModel.Verification verification = (UserverificationModel.Verification) getIntent().getSerializableExtra(SecuritySettingsActivity.VERIFICATION);
        if (verification != null) {
            bankVo = verification.getBindcard().get(0);
            Glide.with(AiMiCrowdFundingApplication.context()).load(Urls.PROJECT_URL + bankVo.getBankImage()).crossFade().into(ivBankIcon);
            tvBankName.setText(bankVo.getBankName());
//            String substring = bankVo.getCardNo().substring(bankVo.getCardNo().length() - 4, bankVo.getCardNo().length());
            tvBankCard.setText(bankVo.getCardNo());
            tvBankUserName.setText(bankVo.getIdentityName());
        }
    }

    @Override
    public void addListener() {
        templateTextViewRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(BankCardActivity.this);
                builder.setMessage("是否解绑此卡？解绑后可绑定新的银行卡");
                builder.setNegativeButton("取消", null);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        isUnbindbank();

                    }
                });
              builder.show();
            }
        });
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        AiMiCrowdFundingApplication.context().cancelPendingRequests(getClass().getName());
    }

    private void isUnbindbank() {
        final MyProgressDialog myProgressDialog = new MyProgressDialog(this, "正在解绑");
        myProgressDialog.show();
        JacksonRequest<UnbindVo> jacksonRequest = new JacksonRequest<>(RequestMap.isunbindbank(bankVo.getCardId()), UnbindVo.class, new Response.Listener<UnbindVo>() {
            @Override
            public void onResponse(UnbindVo baseVo) {
                myProgressDialog.dismiss();
                if (baseVo.code.equals(Constants.SUCCESS)) {
                    verification.setTicket(baseVo.getResult().getTicket());
                    verification.setCardMobile(baseVo.getResult().getCardMobile());
                    Intent intent = new Intent(BankCardActivity.this, UnbindBankCardActivity.class);
                    intent.putExtra(UnbindBankCardActivity.VERIFICATION,verification);
                    startActivity(intent);
                    finish();
                } else {
                    showShortToastMessage(baseVo.getText());
                }
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
        jacksonRequest.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 1, 1.0f));
        AiMiCrowdFundingApplication.context().addToRequestQueue(jacksonRequest,getClass().getName());
    }

    @OnClick(R.id.tvQkh)
    public void onClick() {
        finish();
        Intent intent = new Intent(BankCardActivity.this, AccountSettingActivity.class);
        startActivity(intent);
    }
}
