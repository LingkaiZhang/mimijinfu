package com.nongjinsuo.mimijinfu.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.nongjinsuo.mimijinfu.R;
import com.nongjinsuo.mimijinfu.beans.AvailableaVo;
import com.nongjinsuo.mimijinfu.beans.RedPacketBean;
import com.nongjinsuo.mimijinfu.config.AiMiCrowdFundingApplication;
import com.nongjinsuo.mimijinfu.config.Constants;
import com.nongjinsuo.mimijinfu.dao.http.JacksonRequest;
import com.nongjinsuo.mimijinfu.dao.http.RequestMap;
import com.nongjinsuo.mimijinfu.dao.http.Urls;
import com.nongjinsuo.mimijinfu.dialog.MyProgressDialog;
import com.nongjinsuo.mimijinfu.httpmodel.AvailableaModel;
import com.nongjinsuo.mimijinfu.httpmodel.ProductDetailModel;
import com.nongjinsuo.mimijinfu.util.LogUtil;
import com.nongjinsuo.mimijinfu.util.TextUtil;
import com.nongjinsuo.mimijinfu.util.Util;
import com.nongjinsuo.mimijinfu.widget.EventBarEntity;
import com.nongjinsuo.mimijinfu.widget.MyEditText;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * @author czz
 * @Description: (项目购买)
 */
public class ProjectBuyActivity extends AbstractActivity {
    @BindView(R.id.tvKgje)
    TextView tvKgje;
    @BindView(R.id.etRgje)
    MyEditText etRgje;
    @BindView(R.id.tvZhye)
    TextView tvZhye;
    @BindView(R.id.tvChongZhi)
    TextView tvChongZhi;
    @BindView(R.id.tvQrgm)
    TextView tvQrgm;
    @BindView(R.id.tvQt)
    TextView tvQt;
    @BindView(R.id.llPwdView)
    LinearLayout llPwdView;
    @BindView(R.id.llRg)
    LinearLayout llRg;

    @BindView(R.id.tvServiceFailContent)
    TextView tvServiceFailContent;
    @BindView(R.id.rlServiceFail)
    RelativeLayout rlServiceFail;
    @BindView(R.id.tvKyhb)
    TextView tvKyhb;
    @BindView(R.id.llYqsy)
    LinearLayout llYqsy;
    @BindView(R.id.tvFwht)
    TextView tvFwht;
    @BindView(R.id.tvYqsy)
    TextView tvYqsy;
    private String projectBm;
    private AvailableaVo availableaVo;
    private ArrayList<RedPacketBean> redPacket;
    private AlertDialog.Builder noServiceBuilder;
    private RedPacketBean redPacketBean;//选中的红包对象
    private boolean bsyhbFlag = false;
    public static final String BUYTYPE = "buyType";
    public static final String GMJE = "gmje";//定投宝 认购金额
    public static final String YJSY = "yjsy";//定投宝 预计收益
    public static final int BUY_P2P = 1;
    private int buyType;
    private String interfaceStr = "investProject";
    private String gmje;
    public static final String PRODUCTBEAN = "productBean";
    private ProductDetailModel.ResultBean productBean;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_buy);
        ButterKnife.bind(this);
        templateTextViewRight.setText("在线客服");
        templateTextViewRight.setVisibility(View.VISIBLE);
        init();
        addListener();
        loadData(true);
        EventBus.getDefault().register(this);
    }

    public void onEventMainThread(EventBarEntity event) {
        if (event.getType() == EventBarEntity.UPDATE_XMRG) {
            loadData(false);
        } else if (event.getType() == EventBarEntity.SELECT_HB) {
            bsyhbFlag = false;
            redPacketBean = event.getRedPacketBean();
            tvKyhb.setText(Html.fromHtml("<font color='" + getResources().getColor(R.color.color_login_btn) + "'>" + redPacketBean.getMoney() + "元</font>"));
            String inputMoneyStr =  etRgje.getText().toString();
            double inputMoney = Double.parseDouble(inputMoneyStr);
            setYqsy(inputMoney);
        } else if (event.getType() == EventBarEntity.SELECT_BSYHB) {
            redPacketBean = null;
            tvKyhb.setText("不使用红包");
            bsyhbFlag = true;
            String inputMoneyStr =  etRgje.getText().toString();
            double inputMoney = Double.parseDouble(inputMoneyStr);
            setYqsy(inputMoney);
        }
    }

    /**
     * 设置预期收益
     * @param inputMoney
     */
    private void setYqsy(double inputMoney) {
        if (buyType != 0){
            if (inputMoney >= 0&&inputMoney<100&&redPacketBean == null){
                tvYqsy.setText("0元");
            }else if(inputMoney >=100&& redPacketBean == null){
                tvYqsy.setText(Util.calBackMoney(productBean.getBackMoneyClass(), productBean.getProjectRate(),inputMoney , productBean.getProjectDay())+"元");
            }else if(inputMoney<100&& redPacketBean!=null){
                tvYqsy.setText(redPacketBean.getMoney()+"元");
            }else{
                if (!bsyhbFlag){
                    tvYqsy.setText(Util.calBackMoney(productBean.getBackMoneyClass(), productBean.getProjectRate(),inputMoney , productBean.getProjectDay())+"元+"+redPacketBean.getMoney()+"元");
                }else{
                    tvYqsy.setText(Util.calBackMoney(productBean.getBackMoneyClass(), productBean.getProjectRate(),inputMoney , productBean.getProjectDay())+"元");
                }
            }
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
        projectBm = getIntent().getStringExtra(Constants.PROJECTBM);
        gmje = getIntent().getStringExtra(GMJE);
        etRgje.setText(gmje);
        buyType = getIntent().getIntExtra(BUYTYPE, 0);
        productBean = (ProductDetailModel.ResultBean) getIntent().getSerializableExtra(PRODUCTBEAN);
        if (buyType == 0) {
            titleView.setText("项目认购");
            interfaceStr = "investProject";
            llYqsy.setVisibility(View.INVISIBLE);
            tvFwht.setText("《活投宝投资服务合同》");
        } else {
            titleView.setText("投资认购");
            interfaceStr = "investProduct";
            llYqsy.setVisibility(View.VISIBLE);
            tvFwht.setText("《定投宝投资服务合同》");
        }
//        InputFilter[] filters = {new CashierInputFilter()};
//        etRgje.setFilters(filters);
        noServiceBuilder = new AlertDialog.Builder(this);
        noServiceBuilder.setMessage(getString(R.string.str_no_service_toast));
        noServiceBuilder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                loadData(false);
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void addListener() {
        etRgje.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int iL) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {

                getHongBao(editable.toString());
            }

        });
        templateTextViewRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProjectBuyActivity.this, ZxkfWebViewActivity.class);
                intent.putExtra(ZxkfWebViewActivity.URL, Urls.ZXKF);
                intent.putExtra(ZxkfWebViewActivity.NOZXKF, false);
                startActivity(intent);
            }
        });
    }

    /**
     * 根据金额获取红包
     * @param editable
     */
    private void getHongBao(String editable) {
        if (TextUtil.IsNotEmpty(editable.toString())) {
            //根据输入的金额 筛选投资红包
            double inputMoney = Double.parseDouble(editable.toString());
            if (!bsyhbFlag){
                if (redPacket != null && redPacket.size() > 0) {
                    redPacketBean = null;
                    for (int i = 0; i < redPacket.size(); i++) {
                        RedPacketBean packetBean = redPacket.get(i);
                        double xyMoney = Double.parseDouble(packetBean.getMinUse());
                        if (inputMoney >= xyMoney) {
                            redPacketBean = packetBean;
                        }
                    }
                    if (redPacketBean != null) {
                        tvKyhb.setText(Html.fromHtml("<font color='" + getResources().getColor(R.color.color_login_btn) + "'>" + redPacketBean.getMoney() + "元</font>"));
                    } else {
                        tvKyhb.setText("无可用红包");
                    }
                }
                setYqsy(inputMoney);
            }else{
                redPacketBean = null;
                tvKyhb.setText("不使用红包");
                setYqsy(inputMoney);
            }
        } else {
            if (!bsyhbFlag){
                tvYqsy.setText("0元");
                if (redPacketBean != null) {
                    tvKyhb.setText(Html.fromHtml("<font color='" + getResources().getColor(R.color.color_login_btn) + "'>" + redPacket.size() + "</font>个红包"));
                } else {
                    tvKyhb.setText("无可用红包");
                }
            }else{
                redPacketBean = null;
                tvKyhb.setText("不使用红包");
            }
        }
    }

    MyProgressDialog myProgressDialog = null;

    private void loadData(boolean isFirst) {
        if (isFirst) {
            rlServiceFail.setVisibility(View.GONE);
            showLoading();
        } else {
            redPacketBean = null;
            etRgje.setText("");
            myProgressDialog = new MyProgressDialog(ProjectBuyActivity.this, "刷新购买金额");
            myProgressDialog.setCancelable(false);
            myProgressDialog.show();
            myProgressDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
                @Override
                public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                    return true;
                }
            });
        }
        JacksonRequest<AvailableaModel> jacksonRequest = new JacksonRequest<>(RequestMap.availableamount(projectBm), AvailableaModel.class, new Response.Listener<AvailableaModel>() {
            @Override
            public void onResponse(AvailableaModel baseVo) {
                cancleLoading();
                if (myProgressDialog != null) {
                    myProgressDialog.dismiss();
                }
                llRg.setVisibility(View.VISIBLE);
                if (baseVo.code.equals(Constants.SUCCESS)) {
                    availableaVo = baseVo.result;
                    tvKgje.setText(baseVo.result.getAvailableamount());
                    tvZhye.setText("账户余额:" + baseVo.result.getBalance());
                    redPacket = baseVo.result.getRedPacket();
                    Constants.redPacketBeens = redPacket;
                    if (TextUtil.IsEmpty(gmje)){
                        if (redPacket != null && redPacket.size() > 0) {
                            tvKyhb.setText(Html.fromHtml("<font color='" + getResources().getColor(R.color.color_login_btn) + "'>" + redPacket.size() + "</font>个红包"));
                        } else {
                            tvKyhb.setText("无可用红包");
                        }
                    }else{
                        getHongBao(gmje);
                    }
                } else {
                    llRg.setVisibility(View.GONE);
                    tvServiceFailContent.setText(getString(R.string.str_fwrsgd));
                    rlServiceFail.setVisibility(View.VISIBLE);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                if (myProgressDialog != null) {
                    myProgressDialog.dismiss();
                }
                cancleLoading();
                llRg.setVisibility(View.GONE);
                tvServiceFailContent.setText(Util.getErrorMesg(volleyError));
                rlServiceFail.setVisibility(View.VISIBLE);
                if (volleyError != null && volleyError.getCause() != null) {
                    LogUtil.d("volleyError", volleyError.getCause().getMessage());
                }
            }
        });
        jacksonRequest.setRetryPolicy(new DefaultRetryPolicy(10 * 1000, 1, 1.0f));
        AiMiCrowdFundingApplication.context().addToRequestQueue(jacksonRequest, getClass().getName());
    }

    private void purchase(String amount, String cardId, String projectBm, String orderFrom) {
        final MyProgressDialog myProgressDialog = new MyProgressDialog(ProjectBuyActivity.this, "正在购买");
        myProgressDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                return true;
            }
        });
        myProgressDialog.show();
        String redPacketId = "";
        if (redPacketBean != null) {
            redPacketId = redPacketBean.getId();
        }
        JacksonRequest<AvailableaModel> jacksonRequest = new JacksonRequest<>(RequestMap.getPurchase(interfaceStr, amount, cardId, projectBm, orderFrom, redPacketId), AvailableaModel.class, new Response.Listener<AvailableaModel>() {
            @Override
            public void onResponse(AvailableaModel baseVo) {
                myProgressDialog.dismiss();
                showShortToastMessage(baseVo.getText());
                if (baseVo.code.equals(Constants.SUCCESS)) {
                    EventBus.getDefault().post(new EventBarEntity(EventBarEntity.UPDATE_PROPERTY));
                    EventBus.getDefault().post(new EventBarEntity(EventBarEntity.LOGIN_UPDATE_WEB));
                    EventBus.getDefault().post(new EventBarEntity(EventBarEntity.UPDATE_HOMEPAGE_MESSAGE));
                    EventBus.getDefault().post(new EventBarEntity(EventBarEntity.UPDATE_HOMEPAGE));
                    Intent intent = new Intent(ProjectBuyActivity.this, BuySuccessActivity.class);
                    intent.putExtra(BuySuccessActivity.AVAILABLEAVO, baseVo.result);
                    startActivity(intent);
                    finish();
                } else if (baseVo.code.equals(Constants.ERROR)) {

                } else {
                    noServiceBuilder.show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                myProgressDialog.dismiss();
                noServiceBuilder.show();
                if (volleyError != null && volleyError.getCause() != null) {
                    LogUtil.d("volleyError", volleyError.getCause().getMessage());
                }
            }
        });
        jacksonRequest.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 1, 1.0f));
        AiMiCrowdFundingApplication.context().addToRequestQueue(jacksonRequest, getClass().getName());
    }

    private void availableamount(final double money) {
        final MyProgressDialog myProgressDialog = new MyProgressDialog(ProjectBuyActivity.this, "核对购买金额");
        myProgressDialog.setCancelable(false);
        myProgressDialog.show();
        JacksonRequest<AvailableaModel> jacksonRequest = new JacksonRequest<>(RequestMap.availableamount(projectBm), AvailableaModel.class, new Response.Listener<AvailableaModel>() {
            @Override
            public void onResponse(AvailableaModel baseVo) {
                myProgressDialog.dismiss();
                if (baseVo.code.equals(Constants.SUCCESS)) {
                    tvKgje.setText(baseVo.result.getAvailableamount());
                    tvZhye.setText("账户余额:" + baseVo.result.getBalance());
                    if (money > baseVo.result.getAvailableamount_q()) {
                        showShortToastMessage("项目可购金额不足");
                        return;
                    }
                    if (money > baseVo.result.getBalance_q()) {
                        showShortToastMessage("用户余额不足");
                        return;
                    }
                    purchase(money + "", availableaVo.getCardId(), projectBm, Constants.ANDROID);
                } else {
                    noServiceBuilder.show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                myProgressDialog.dismiss();
                noServiceBuilder.show();
                if (volleyError != null && volleyError.getCause() != null) {
                    LogUtil.d("volleyError", volleyError.getCause().getMessage());
                }
            }
        });
        jacksonRequest.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 1, 1.0f));
        AiMiCrowdFundingApplication.context().addToRequestQueue(jacksonRequest, getClass().getName());
    }

    @OnClick({R.id.tvChongZhi, R.id.tvQrgm, R.id.tvQt, R.id.llKyhb,R.id.tvFwht})
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.tvFwht:
                intent = new Intent(ProjectBuyActivity.this,WebViewAndJsActivity.class);
                if (buyType == 0) {
                    intent.putExtra(WebViewAndJsActivity.URL,Urls.AGREEMENT2);
                }else{
                    intent.putExtra(WebViewAndJsActivity.URL,Urls.AGREEMENT);
                }
                startActivity(intent);
                break;
            case R.id.tvChongZhi:
                intent= new Intent(ProjectBuyActivity.this, RechargeActivity.class);
                startActivity(intent);
                break;
            case R.id.tvQrgm:
                if (availableaVo == null) {
                    showShortToastMessage("数据错误");
                    return;
                }
                String moneyStr = etRgje.getText().toString().trim();
                if (TextUtil.IsEmpty(moneyStr)) {
                    showShortToastMessage("请输入购买金额");
                    return;
                }
                double money = Double.parseDouble(moneyStr);
                if (money < 100) {
                    showShortToastMessage("最低购买金额为100元");
                    return;
                }
                if (money % 100 != 0) {
                    showShortToastMessage("认购金额为100的整数倍");
                    return;
                }
                if (money > availableaVo.getBalance_q()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ProjectBuyActivity.this);
                    builder.setMessage("账户余额不足");
                    builder.setNegativeButton("取消", null);
                    builder.setPositiveButton("去充值", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intent = new Intent(ProjectBuyActivity.this, RechargeActivity.class);
                            startActivity(intent);
                        }
                    });
                    builder.show();
                    return;
                }
                if (availableaVo.getPerAuthPay() != 0 && money > availableaVo.getPerAuthPay()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ProjectBuyActivity.this);
                    builder.setMessage("投资金额超过单笔交易限额：" + ((int) availableaVo.getPerAuthPay()) + "，需修改新浪委托扣款限额");
                    builder.setNegativeButton("取消", null);
                    builder.setPositiveButton("去修改", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intent = new Intent(ProjectBuyActivity.this, SetSinaPwdWebViewActivity.class);
                            intent.putExtra(SetSinaPwdWebViewActivity.IS_UPDATE_PWD, SetSinaPwdWebViewActivity.UPDATE_DAIKOU);
                            intent.putExtra(SetSinaPwdWebViewActivity.URL, availableaVo.getEditauthority_redirect_url());
                            startActivity(intent);
                        }
                    });
                    builder.show();
                    return;
                }
                if (availableaVo.getPerDayAuthPay() != 0 && availableaVo.getPerDayAuthPay() - availableaVo.getTodayInvestMoney() < money) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ProjectBuyActivity.this);
                    builder.setMessage("投资金额超过当日交易限额：" + ((int) availableaVo.getPerDayAuthPay()) + "，需修改新浪委托扣款限额");
                    builder.setNegativeButton("取消", null);
                    builder.setPositiveButton("去修改", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intent = new Intent(ProjectBuyActivity.this, SetSinaPwdWebViewActivity.class);
                            intent.putExtra(SetSinaPwdWebViewActivity.IS_UPDATE_PWD, SetSinaPwdWebViewActivity.UPDATE_DAIKOU);
                            intent.putExtra(SetSinaPwdWebViewActivity.URL, availableaVo.getEditauthority_redirect_url());
                            startActivity(intent);
                        }
                    });
                    builder.show();
                    return;
                }
                //确认可购买金额和用户余额
                availableamount(money);
                break;
            case R.id.tvQt:
                if (availableaVo != null) {
                    if (availableaVo.getAvailableamount_q() >= 100) {
                        if (availableaVo.getBalance_q() < 100) {
                            showShortToastMessage("您的余额小于最低投资金额100元");
                            etRgje.setText("0");
                            etRgje.setSelection(etRgje.getText().toString().length());
                        } else {
                            int ktje = ((int) availableaVo.getBalance_q());
                            int yhye = ((int) availableaVo.getAvailableamount_q());
                            if (availableaVo.getBalance_q() < availableaVo.getAvailableamount_q()) {
                                if (ktje % 100 == 0) {
                                    etRgje.setText(ktje + "");
                                } else {
                                    int ysMoney = ktje % 100;
                                    etRgje.setText((ktje - ysMoney) + "");
                                }
                            } else {

                                if (yhye % 100 == 0) {
                                    etRgje.setText(yhye + "");
                                } else {
                                    int ysMoney = yhye % 100;
                                    etRgje.setText((yhye - ysMoney) + "");
                                }
                            }
                            etRgje.setSelection(etRgje.getText().length());
                        }
                    } else {
                        showShortToastMessage("可投金额错误");
                    }
                }
                break;
            case R.id.llKyhb:
                if (redPacket != null && redPacket.size() > 0) {
                    Intent intent1 = new Intent(ProjectBuyActivity.this, MyRedPacketActivity.class);
                    intent1.putExtra(MyRedPacketActivity.SELECT_MYREDPACKET, true);
                    intent1.putExtra(MyRedPacketActivity.FLAG_BSYHB, bsyhbFlag);
                    intent1.putExtra(MyRedPacketActivity.TZ_MONEY, etRgje.getText().toString());
                    if (redPacketBean != null) {
                        intent1.putExtra(MyRedPacketActivity.REDPACKETBEAN_ID, redPacketBean);
                    }
                    startActivity(intent1);
                } else {
                    showShortToastMessage("无可用红包");
                }
                break;
        }
    }

    @OnClick(R.id.tvServiceRefresh)
    public void onClick() {
        loadData(true);
    }
}
