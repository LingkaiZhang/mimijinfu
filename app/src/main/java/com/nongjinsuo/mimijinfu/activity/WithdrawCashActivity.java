package com.nongjinsuo.mimijinfu.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.Html;
import android.text.InputFilter;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.kyleduo.switchbutton.SwitchButton;
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
import com.nongjinsuo.mimijinfu.util.TimeUtil;
import com.nongjinsuo.mimijinfu.widget.CashierInputFilter;
import com.nongjinsuo.mimijinfu.widget.MyEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author czz
 * @Description: (提现)
 */
public class WithdrawCashActivity extends AbstractActivity {
    @BindView(R.id.ivBankIcon)
    ImageView ivBankIcon;
    @BindView(R.id.tvBankName)
    TextView tvBankName;
    @BindView(R.id.tvBankCard)
    TextView tvBankCard;
    @BindView(R.id.etPhone)
    MyEditText etPhone;
    @BindView(R.id.tvSxf)
    TextView tvSxf;
    @BindView(R.id.tvMftx)
    TextView tvMftx;
    @BindView(R.id.tvQrzc)
    TextView tvQrzc;
    @BindView(R.id.tvYjdzsj)
    TextView tvYjdzsj;
    @BindView(R.id.tvZhye)
    TextView tvZhye;
    BankVo bankVo;
    UserverificationModel userverificationModel;

    @BindView(R.id.tvCqgsy)
    TextView tvCqgsy;
    @BindView(R.id.sb_code)
    SwitchButton sbCode;
    private String ispiggybank = "1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdraw_cash);
        ButterKnife.bind(this);
        init();
        addListener();
    }

    @Override
    public void init() {
        titleView.setText("提现");
        templateTextViewRight.setText("提现说明");
        templateTextViewRight.setVisibility(View.VISIBLE);
        InputFilter[] filters = {new CashierInputFilter()};
        etPhone.setFilters(filters);
        loadData();
//        tvYjdzsj.setText(Html.fromHtml("预计到账时间<font color='" + getResources().getColor(R.color.color_project_detail_red_btn) + "'>1-2</font>个工作日"));

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
                    imm.showSoftInput(etPhone, InputMethodManager.SHOW_FORCED);
                    userverificationModel = baseVo;
                    try {
                        bankVo = baseVo.result.getBindcard().get(0);
                    } catch (Exception e) {
                        e.printStackTrace();
                        bankVo = new BankVo();
                    }
                    if (userverificationModel.result.getDomoneycount() < 3) {
                        tvSxf.setText("2.00元");
                        tvSxf.getPaint().setAntiAlias(true);//抗锯齿
                        tvSxf.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG); //中划线
                        tvSxf.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);  // 设置中划线并加清晰
                    } else {
                        tvSxf.setText("2.00元");
                    }
                    etPhone.setHint("可提现金额" + baseVo.result.getBalance() + "元");
                    tvCqgsy.setText(baseVo.result.getMaxWithdraw()+"元");
                    Glide.with(WithdrawCashActivity.this).load(Urls.PROJECT_URL + bankVo.getBankImage()).crossFade().into(ivBankIcon);
                    tvBankName.setText(bankVo.getBankName());
                    tvBankCard.setText(bankVo.getLastCardNo());
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
       AiMiCrowdFundingApplication.context().addToRequestQueue(jacksonRequest,getClass().getName());

        //判断当前时间
        int hour = TimeUtil.getHourNum();
        if (hour < 15) {
            tvYjdzsj.setText(Html.fromHtml("<font color='" + getResources().getColor(R.color.color_project_detail_red_btn) + "'>15:00</font>前提现,预计明日(" + TimeUtil.getDayForNumStr(1) + ")到账"));
        } else {
            tvYjdzsj.setText(Html.fromHtml("<font color='" + getResources().getColor(R.color.color_project_detail_red_btn) + "'>15:00</font>后提现,预计后日(" + TimeUtil.getDayForNumStr(2) + ")到账"));
        }
    }
    @Override
    public void addListener() {
        templateButtonLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(etPhone.getWindowToken(), 0); //强制隐藏键盘
            }
        });
        templateTextViewRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(etPhone.getWindowToken(), 0); //强制隐藏键盘
                Intent intent = new Intent(WithdrawCashActivity.this, WebViewActivity.class);
                intent.putExtra(WebViewActivity.URL, Urls.TXSM);
                startActivity(intent);
            }
        });
        sbCode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    ispiggybank = "1";
                }else{
                    ispiggybank = "0";
                }
        }
        });
//        etPhone.addTextChangedListener(new TextWatcher() {
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
//                        if (aFloat > 0 && aFloat <= Float.parseFloat(userverificationModel.result.getBalance())) {
//                            tvQrzc.setBackgroundResource(R.drawable.shape_login_btn);
//                            tvQrzc.setClickable(true);
//                        } else {
//                            tvQrzc.setBackgroundResource(R.drawable.shape_login_btn_hui);
//                            tvQrzc.setClickable(false);
//                        }
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                        tvQrzc.setBackgroundResource(R.drawable.shape_login_btn_hui);
//                        tvQrzc.setClickable(false);
//                    }
//                }
//            }
//
//        });
    }

    private void recharge(String amount) {
        final MyProgressDialog myProgressDialog = new MyProgressDialog(WithdrawCashActivity.this, "正在提现");
        myProgressDialog.show();
        JacksonRequest<ChongZhiModel> jacksonRequest = new JacksonRequest<>(RequestMap.getWithdraw(amount, bankVo.getCardId(),ispiggybank), ChongZhiModel.class, new Response.Listener<ChongZhiModel>() {
            @Override
            public void onResponse(ChongZhiModel baseVo) {
                myProgressDialog.dismiss();
                if (baseVo.code.equals(Constants.SUCCESS)) {
                    Intent intent = new Intent(WithdrawCashActivity.this, RechargeWebViewActivity.class);
                    intent.putExtra(RechargeWebViewActivity.URL, baseVo.result.redirect_url);
                    startActivityForResult(intent, RechargeActivity.FINISH);
                }else{
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

    @OnClick(R.id.tvQrzc)
    public void onClick() {
        String moneyStr = etPhone.getText().toString().trim();
        if (TextUtil.IsEmpty(moneyStr)) {
            showShortToastMessage("请输入提现金额");
            return;
        }
        float aFloat = Float.parseFloat(moneyStr.toString());
        if (aFloat == 0) {
            showShortToastMessage("提现金额必须大于0元");
            return;
        }
        if (userverificationModel.result == null) {
            return;
        }
        if (aFloat > Float.parseFloat(userverificationModel.result.getBalance())) {
            showShortToastMessage("不能大于可提现金额");
            return;
        }
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(etPhone.getWindowToken(), 0); //强制隐藏键盘
        recharge(moneyStr);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RechargeActivity.FINISH)
            finish();
    }
}
