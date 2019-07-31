package com.nongjinsuo.mimijinfu.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.nongjinsuo.mimijinfu.R;
import com.nongjinsuo.mimijinfu.beans.CentralVo;
import com.nongjinsuo.mimijinfu.config.AiMiCrowdFundingApplication;
import com.nongjinsuo.mimijinfu.config.Constants;
import com.nongjinsuo.mimijinfu.dao.http.JacksonRequest;
import com.nongjinsuo.mimijinfu.dao.http.RequestMap;
import com.nongjinsuo.mimijinfu.httpmodel.CentralModel;
import com.nongjinsuo.mimijinfu.util.PxAndDip;
import com.nongjinsuo.mimijinfu.widget.PieChart;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author czz
 * @Description: (资产详情)
 */
public class FinancialDetails2Activity extends AbstractActivity {


    @BindView(R.id.pieChart)
    PieChart pieChart;
    @BindView(R.id.tvZzc)
    TextView tvZzc;
    @BindView(R.id.tvP2pMoney)
    TextView tvP2pMoney;
    @BindView(R.id.tvYeMoney)
    TextView tvYeMoney;
    @BindView(R.id.tvZcMoney)
    TextView tvZcMoney;
    @BindView(R.id.tvDjMoney)
    TextView tvDjMoney;
    @BindView(R.id.tvZsy)
    TextView tvZsy;
    @BindView(R.id.my_progress)
    ProgressBar myProgress;
    @BindView(R.id.tvP2pLcMoney)
    TextView tvP2pLcMoney;
    @BindView(R.id.tvZctzMoney)
    TextView tvZctzMoney;
    @BindView(R.id.tvYelx)
    TextView tvYelx;
    @BindView(R.id.tvHbjl)
    TextView tvHbjl;
    @BindView(R.id.tvZcTime)
    TextView tvZcTime;
    @BindView(R.id.tvDayNum)
    TextView tvDayNum;
    @BindView(R.id.ivP2pLc)
    ImageView ivP2pLc;
    @BindView(R.id.ivZctz)
    ImageView ivZctz;
    @BindView(R.id.llSyzl)
    LinearLayout llSyzl;
    @BindView(R.id.tvLjcz)
    TextView tvLjcz;
    @BindView(R.id.tvLjtx)
    TextView tvLjtx;
    @BindView(R.id.tvLjtz)
    TextView tvLjtz;
    @BindView(R.id.tvJsr)
    TextView tvJsr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_financia2_details);
        ButterKnife.bind(this);
        init();
        addListener();
        loadData();
    }

    @Override
    public void init() {
        titleView.setText("资金明细");
    }

    @Override
    public void addListener() {

    }

    protected void onDestroy() {
        super.onDestroy();
        AiMiCrowdFundingApplication.context().cancelPendingRequests(getClass().getName());
    }

    private void loadData() {
        showLoading();
        JacksonRequest<CentralModel> jacksonRequest = new JacksonRequest<>(RequestMap.getCentralhome(), CentralModel.class, new Response.Listener<CentralModel>() {
            @Override
            public void onResponse(CentralModel baseVo) {
                cancleLoading();
                if (baseVo.getCode().equals(Constants.SUCCESS)) {
                    CentralVo.AssetBean assetBean = baseVo.result.getAsset();
                    setAsset(assetBean);

                    tvZsy.setText(assetBean.getTotalInterest() + "元");
                    tvP2pLcMoney.setText("定投宝：" + assetBean.getInterestNetloan() + "元");
                    tvZctzMoney.setText("活投宝：" + assetBean.getInterest() + "元");

                    double p2pSy = Double.parseDouble(assetBean.getInterestNetloan());
                    double zctz = Double.parseDouble(assetBean.getInterest());
                    double zsy = p2pSy + zctz;
                    if (zsy == 0) {
                        llSyzl.setBackgroundColor(getResources().getColor(R.color.background_color));
                        ivP2pLc.setVisibility(View.GONE);
                        ivZctz.setVisibility(View.GONE);
                    } else {
                        //计算p2p的宽度
                        int imgWidth = (int) ((Constants.WINDOW_WIDTH - PxAndDip.dip2px(FinancialDetails2Activity.this, 32)) * p2pSy / zsy);
                        ivP2pLc.setLayoutParams(new LinearLayout.LayoutParams(imgWidth, PxAndDip.dip2px(FinancialDetails2Activity.this, 8)));
                    }
                    tvYelx.setText(assetBean.getPotInterest() + "元");
                    tvHbjl.setText(assetBean.getInterestCash() + "元");
                    tvZcTime.setText(assetBean.getRegisterTime());
                    tvDayNum.setText(assetBean.getRegisterDay());
//                    tvLjcz.setText();
                    tvLjtz.setText(assetBean.getTotalInvestMoney());
                    tvJsr.setText(assetBean.getTotalInterest());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                if (volleyError != null && volleyError.getCause() != null) {
                }
            }
        });
        jacksonRequest.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 1, 1.0f));
        AiMiCrowdFundingApplication.context().addToRequestQueue(jacksonRequest, getClass().getName());
    }

    /**
     * 设置资产
     *
     * @param assetBean
     */
    private void setAsset(CentralVo.AssetBean assetBean) {
        tvZzc.setText(assetBean.getAsset());
        tvP2pMoney.setText("定投宝：" + assetBean.getStayBackCapitalNetloan());
        tvYeMoney.setText("余额：" + assetBean.getTotalBalance());
        tvZcMoney.setText("活投宝：" + assetBean.getStayBackCapital());
        tvDjMoney.setText("冻结：" + assetBean.getFreezeMoney());

        double asset = Double.parseDouble(assetBean.getAsset());

        double stayBackCapital = Double.parseDouble(assetBean.getStayBackCapitalNetloan());
        double freezeMoney = Double.parseDouble(assetBean.getTotalBalance());
        double balance = Double.parseDouble(assetBean.getStayBackCapital());
        double doubleFreeze = Double.parseDouble(assetBean.getFreezeMoney());

        float p2pMoney = (float) (stayBackCapital / asset);
        float yeMoney = (float) (freezeMoney / asset);
        float zcMoney = (float) (balance / asset);
        float djMoney = (float) (doubleFreeze / asset);

        if (p2pMoney < 0) {
            p2pMoney = 0;
        }
        if (yeMoney < 0) {
            yeMoney = 0;
        }
        if (zcMoney < 0) {
            zcMoney = 0;
        }
        if (djMoney < 0) {
            djMoney = 0;
        }
        pieChart.initSrc(new float[]{p2pMoney, yeMoney, zcMoney, djMoney}, new String[]{getString(R.string.str_p2p_color), getString(R.string.str_ye_color),
                getString(R.string.str_zc_color), getString(R.string.str_dj_color)}, new PieChart.OnItemClickListener() {
            @Override
            public void click(int position) {
            }
        });
    }

}
