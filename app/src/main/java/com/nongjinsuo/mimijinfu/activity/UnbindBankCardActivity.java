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
import com.nongjinsuo.mimijinfu.beans.BankVo;
import com.nongjinsuo.mimijinfu.beans.BaseVo;
import com.nongjinsuo.mimijinfu.beans.UnbindVo;
import com.nongjinsuo.mimijinfu.config.AiMiCrowdFundingApplication;
import com.nongjinsuo.mimijinfu.config.Constants;
import com.nongjinsuo.mimijinfu.dao.http.JacksonRequest;
import com.nongjinsuo.mimijinfu.dao.http.RequestMap;
import com.nongjinsuo.mimijinfu.dao.http.Urls;
import com.nongjinsuo.mimijinfu.dialog.MyProgressDialog;
import com.nongjinsuo.mimijinfu.httpmodel.UserverificationModel;
import com.nongjinsuo.mimijinfu.util.LogUtil;
import com.nongjinsuo.mimijinfu.util.TextUtil;
import com.nongjinsuo.mimijinfu.widget.EventBarEntity;
import com.nongjinsuo.mimijinfu.widget.MyEditText;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * @author czz
 * @Description: (解绑银行卡)
 */
public class UnbindBankCardActivity extends AbstractActivity {
    @BindView(R.id.tvPhone)
    TextView tvPhone;
    @BindView(R.id.etCode)
    MyEditText etCode;
    @BindView(R.id.tvHqCode)
    TextView tvHqCode;
    @BindView(R.id.progressSmall)
    ProgressWheel progressSmall;
    @BindView(R.id.llHqyzmView)
    LinearLayout llHqyzmView;
    @BindView(R.id.tvPhoneName)
    TextView tvPhoneName;
    private TimerTask task;
    private int recLen = 60;

    public static final String VERIFICATION = "verification";
    private UserverificationModel.Verification verification;
    private BankVo bankVo;

    @Override
    public void init() {
        titleView.setText("解绑银行卡");
        templateTextViewRight.setText("在线客服");
        templateTextViewRight.setVisibility(View.VISIBLE);
        verification= (UserverificationModel.Verification) getIntent().getSerializableExtra(VERIFICATION);
        bankVo = verification.getBindcard().get(0);
        if (verification!=null){
            if (TextUtil.IsNotEmpty(verification.getCardMobile())) {
                tvPhoneName.setVisibility(View.VISIBLE);
                tvPhone.setText(verification.getCardMobile());
            } else {
                tvPhone.setVisibility(View.GONE);
                tvPhoneName.setText("为保证您的银行卡安全，请输入验证码");
            }
        }

    }

    @Override
    public void addListener() {
        templateTextViewRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UnbindBankCardActivity.this, ZxkfWebViewActivity.class);
                intent.putExtra(ZxkfWebViewActivity.URL, Urls.ZXKF);
                intent.putExtra(ZxkfWebViewActivity.NOZXKF,false);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unbindbankcard);
        ButterKnife.bind(this);
        init();
        addListener();
        haveTime();
    }

    @OnClick({R.id.llHqyzmView, R.id.tvConfirm})
    public void onClick(View view) {
        String phont;
        String validPhone;
        switch (view.getId()) {
            case R.id.llHqyzmView:
                etCode.requestFocus();
                progressSmall.setVisibility(View.VISIBLE);
                tvHqCode.setVisibility(View.INVISIBLE);
                isUnbindbank();
                break;
            case R.id.tvConfirm:
                String valid_code =  etCode.getText().toString();
                if (TextUtil.IsEmpty(valid_code)){
                    showShortToastMessage("请输入验证码");
                    return;
                }
                if (valid_code.length()<5) {
                    showShortToastMessage("请输入正确的验证码");
                    return;
                }
                unbindbank(valid_code);
                break;
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        AiMiCrowdFundingApplication.context().cancelPendingRequests(getClass().getName());
    }
    private void isUnbindbank() {
        if (bankVo==null){
            return;
        }
        JacksonRequest<UnbindVo> jacksonRequest = new JacksonRequest<>(RequestMap.isunbindbank(bankVo.getCardId()), UnbindVo.class, new Response.Listener<UnbindVo>() {
            @Override
            public void onResponse(UnbindVo baseVo) {
                showShortToastMessage(baseVo.getText());
                if (baseVo.code.equals(Constants.SUCCESS)) {
                    verification.setTicket(baseVo.getResult().getTicket());
                    verification.setCardMobile(baseVo.getResult().getCardMobile());
                    haveTime();
                } else {
                    progressSmall.setVisibility(View.GONE);
                    tvHqCode.setVisibility(View.VISIBLE);
                    llHqyzmView.setClickable(true);
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
        llHqyzmView.setClickable(false);
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
                            llHqyzmView.setClickable(true);
                            recLen = 60;
                            tvHqCode.setText("重新发送");
                        }
                    }
                });
            }
        };
        timer.schedule(task, 0, 1000);
    }
    private void unbindbank(String valid_code) {
        if (verification==null||bankVo==null){
            return;
        }
        final MyProgressDialog myProgressDialog = new MyProgressDialog(this, "确认解绑");
        myProgressDialog.show();
        JacksonRequest<BaseVo> jacksonRequest = new JacksonRequest<>(RequestMap.unbindbank(bankVo.getCardId(),verification.getTicket(),valid_code), BaseVo.class, new Response.Listener<BaseVo>() {
            @Override
            public void onResponse(BaseVo baseVo) {
                myProgressDialog.dismiss();
                if (baseVo.code.equals(Constants.SUCCESS)) {
                    EventBus.getDefault().post(new EventBarEntity(EventBarEntity.UPDATE_ANQUAN_SETTING));
                    EventBus.getDefault().post(new EventBarEntity(EventBarEntity.UPDATE_PROPERTY));
                    finish();
                    Intent intent = new Intent(UnbindBankCardActivity.this, AccountSettingActivity.class);
                    intent.putExtra(AccountSettingActivity.IS_KAI_HU, false);
                    startActivity(intent);
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
}
