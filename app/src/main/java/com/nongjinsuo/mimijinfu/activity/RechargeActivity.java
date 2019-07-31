package com.nongjinsuo.mimijinfu.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.nongjinsuo.mimijinfu.R;
import com.nongjinsuo.mimijinfu.beans.BankVo;
import com.nongjinsuo.mimijinfu.beans.ChongZhiModel;
import com.nongjinsuo.mimijinfu.config.AiMiCrowdFundingApplication;
import com.nongjinsuo.mimijinfu.config.Constants;
import com.nongjinsuo.mimijinfu.dao.http.JacksonRequest;
import com.nongjinsuo.mimijinfu.dao.http.RequestMap;
import com.nongjinsuo.mimijinfu.dao.http.Urls;
import com.nongjinsuo.mimijinfu.dialog.MyProgressDialog;
import com.nongjinsuo.mimijinfu.httpmodel.UserverificationModel;
import com.nongjinsuo.mimijinfu.util.LogUtil;
import com.nongjinsuo.mimijinfu.util.TextUtil;
import com.nongjinsuo.mimijinfu.widget.CashierInputFilter;
import com.nongjinsuo.mimijinfu.widget.MyEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author czz
 * @Description: (充值)
 */
public class RechargeActivity extends AbstractActivity {
    @BindView(R.id.etJe)
    MyEditText etJe;
    BankVo bankVo;
    @BindView(R.id.tvChongZhi)
    TextView tvChongZhi;
    public static final int FINISH = 2;

    @BindView(R.id.tvXe)
    TextView tvXe;
    @BindView(R.id.tvZhye)
    TextView tvZhye;
    @BindView(R.id.rbKjcz)
    RadioButton rbKjcz;
    @BindView(R.id.rbWycz)
    RadioButton rbWycz;
    @BindView(R.id.rgCzType)
    RadioGroup rgCzType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge);
        ButterKnife.bind(this);
        init();
        addListener();
        loadData();

    }

    @Override
    public void init() {
        titleView.setText("充值");
        InputFilter[] filters = {new CashierInputFilter()};
        etJe.setFilters(filters);
        templateTextViewRight.setText("在线客服");
        templateTextViewRight.setVisibility(View.VISIBLE);
    }

    @Override
    public void addListener() {
        templateButtonLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(etJe.getWindowToken(), 0); //强制隐藏键盘
            }
        });
        templateTextViewRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(etJe.getWindowToken(), 0); //强制隐藏键盘

                Intent intent = new Intent(RechargeActivity.this, ZxkfWebViewActivity.class);
                intent.putExtra(ZxkfWebViewActivity.URL, Urls.ZXKF);
                intent.putExtra(ZxkfWebViewActivity.NOZXKF, false);
                startActivity(intent);
            }
        });
//        etJe.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//                if (TextUtil.IsNotEmpty(editable.toString())) {
//                    float aFloat = Float.parseFloat(editable.toString());
//                    try {
//                        if (aFloat >= 100 && aFloat <= Float.parseFloat(bankVo.getPerPayMaxNum())) {
//                            tvChongZhi.setBackgroundResource(R.drawable.shape_login_btn);
//                            tvChongZhi.setClickable(true);
//                        } else {
//                            tvChongZhi.setBackgroundResource(R.drawable.shape_login_btn_hui);
//                            tvChongZhi.setClickable(false);
//                        }
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                        tvChongZhi.setBackgroundResource(R.drawable.shape_login_btn_hui);
//                        tvChongZhi.setClickable(false);
//                    }
//                }
//            }
//
//        });
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
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.showSoftInput(etJe, InputMethodManager.SHOW_FORCED);
                    try {
                        bankVo = baseVo.result.getBindcard().get(0);
                    } catch (Exception e) {
                        e.printStackTrace();
                        bankVo = new BankVo();
                    }
                    tvXe.setText("单笔限额:" + bankVo.getPerPayMaxNum() + "元,单日限额:" + bankVo.getPerDayMaxNum() + "元");
                    tvZhye.setText(baseVo.result.getBalance());
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
        AiMiCrowdFundingApplication.context().addToRequestQueue(jacksonRequest, getClass().getName());
    }

    private void recharge(String amount) {
        final MyProgressDialog myProgressDialog = new MyProgressDialog(RechargeActivity.this, "正在充值");
        myProgressDialog.show();
        JacksonRequest<ChongZhiModel> jacksonRequest = new JacksonRequest<>(RequestMap.getRecharge(amount, bankVo.getCardId()), ChongZhiModel.class, new Response.Listener<ChongZhiModel>() {
            @Override
            public void onResponse(ChongZhiModel baseVo) {
                myProgressDialog.dismiss();
                if (baseVo.code.equals(Constants.SUCCESS)) {
                    Intent intent = new Intent(RechargeActivity.this, RechargeWebViewActivity.class);
                    intent.putExtra(RechargeWebViewActivity.URL, baseVo.result.redirect_url);
                    startActivityForResult(intent, FINISH);
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
        AiMiCrowdFundingApplication.context().addToRequestQueue(jacksonRequest, getClass().getName());
    }

    @OnClick(R.id.tvChongZhi)
    public void onClick() {
        String moneyStr = etJe.getText().toString().trim();
        if (TextUtil.IsEmpty(moneyStr)) {
            showShortToastMessage("请输入充值金额");
            return;
        }
        Float money = Float.parseFloat(moneyStr);
        if (money < 100) {
            showShortToastMessage("最低充值金额为100元");
            return;
        }
//        if (bankVo == null) {
//            showShortToastMessage("不能大于最大充值金额");
//            return;
//        }
        if (money > Float.parseFloat(bankVo.getPerPayMaxNum())) {
            showShortToastMessage("不能大于最大充值金额");
            return;
        }
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(etJe.getWindowToken(), 0); //强制隐藏键盘
        recharge(moneyStr);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == FINISH)
            finish();
    }
}
