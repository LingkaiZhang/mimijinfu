package com.nongjinsuo.mimijinfu.activity;

import android.os.Bundle;
import android.view.View;
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
 * @Description: (更换绑定手机号)
 */
public class ReplacePhoneActivity extends AbstractActivity {
    @BindView(R.id.view2_etPhone)
    MyEditText view2EtPhone;
    @BindView(R.id.tvHqCode)
    TextView tvHqCode;
    @BindView(R.id.progressSmall)
    ProgressWheel progressSmall;
    @BindView(R.id.view2_etCode)
    MyEditText view2EtCode;
    @BindView(R.id.tvWcxg)
    TextView tvWcxg;
    private int recLen = 60;
    private TimerTask task;
    MyProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_replace_phone);
        ButterKnife.bind(this);
        init();
        addListener();
        progressDialog = new MyProgressDialog(ReplacePhoneActivity.this);
    }

    @Override
    public void init() {
        titleView.setText("更换绑定手机号");
    }

    @Override
    public void addListener() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AiMiCrowdFundingApplication.context().cancelPendingRequests(getClass().getName());
    }

    @OnClick({R.id.tvHqCode, R.id.tvWcxg})
    public void onClick(View view) {
        String phont;
        String validPhone;
        switch (view.getId()) {
            case R.id.tvHqCode:
                phont = view2EtPhone.getText().toString().trim();
                validPhone = ValidUtil.validPhone(phont);
                if (TextUtil.IsNotEmpty(validPhone)) {
                    showShortToastMessage(validPhone);
                    return;
                }
                progressSmall.setVisibility(View.VISIBLE);
                tvHqCode.setVisibility(View.INVISIBLE);
                getCode(phont, 1);
                break;
            case R.id.tvWcxg:
                phont = view2EtPhone.getText().toString().trim();
                validPhone = ValidUtil.validPhone(phont);
                if (TextUtil.IsNotEmpty(validPhone)) {
                    showShortToastMessage(validPhone);
                    return;
                }
                String code = view2EtCode.getText().toString().trim();
                if (TextUtil.IsEmpty(code)) {
                    showShortToastMessage("请输入验证码");
                    return;
                }
                editmobile(phont, code);
                break;
        }
    }

    public void editmobile(final String phone, String code) {
        progressDialog.setMessage("修改手机号");
        progressDialog.show();
        JacksonRequest<LoginModel> jacksonRequest = new JacksonRequest<>(RequestMap.editmobile(phone, code), LoginModel.class, new Response.Listener<LoginModel>() {
            @Override
            public void onResponse(LoginModel baseVo) {
                dismissDialog();
                showShortToastMessage(baseVo.text);
                if (baseVo.code.equals(Constants.SUCCESS)) {
                    EventBus.getDefault().post(new EventBarEntity(EventBarEntity.UPDATE_ANQUAN_SETTING));
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

    private void dismissDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    /**
     * @param phone
     * @param sta   1为短信0为语音
     */
    public void getCode(String phone, final int sta) {
        JacksonRequest<BaseVo> jacksonRequest = new JacksonRequest<>(RequestMap.getCodeMap(phone, sta, 1), BaseVo.class, new Response.Listener<BaseVo>() {
            @Override
            public void onResponse(BaseVo baseVo) {
                if (baseVo.code.equals(Constants.SUCCESS)) {
                    haveTime();
                } else {
                    progressSmall.setVisibility(View.GONE);
                    tvHqCode.setVisibility(View.VISIBLE);
                    showShortToastMessage(baseVo.getText());
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
