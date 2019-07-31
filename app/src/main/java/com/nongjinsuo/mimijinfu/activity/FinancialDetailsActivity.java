package com.nongjinsuo.mimijinfu.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.nongjinsuo.mimijinfu.R;
import com.nongjinsuo.mimijinfu.config.AiMiCrowdFundingApplication;
import com.nongjinsuo.mimijinfu.config.Constants;
import com.nongjinsuo.mimijinfu.dao.http.JacksonRequest;
import com.nongjinsuo.mimijinfu.dao.http.RequestMap;
import com.nongjinsuo.mimijinfu.httpmodel.UserMoneyModel;
import com.nongjinsuo.mimijinfu.widget.PieChart;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author czz
 * @Description: (资金明细)
 */
public class FinancialDetailsActivity extends AbstractActivity {
    @BindView(R.id.pieChart)
    PieChart pieChart;
    @BindView(R.id.tvZtje)
    TextView tvZtje;
    @BindView(R.id.tvDjje)
    TextView tvDjje;
    @BindView(R.id.tvKyje)
    TextView tvKyje;
    @BindView(R.id.tvZzc)
    TextView tvZzc;
    @BindView(R.id.tvCqgsy)
    TextView tvCqgsy;

    @BindView(R.id.tvZrSy)
    TextView tvZrSy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_financia_details);
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
        JacksonRequest<UserMoneyModel> jacksonRequest = new JacksonRequest<>(RequestMap.userAsset(), UserMoneyModel.class, new Response.Listener<UserMoneyModel>() {
            @Override
            public void onResponse(UserMoneyModel baseVo) {
                cancleLoading();
                if (baseVo.getCode().equals(Constants.SUCCESS)) {
                    tvZzc.setText(baseVo.result.getAsset() + "");
                    tvZtje.setText(baseVo.result.getStayBackCapital() + "");
                    tvDjje.setText(baseVo.result.getFreezeMoney() + "");
                    tvKyje.setText(baseVo.result.getTotalBalance() + "");
                    tvCqgsy.setText(baseVo.result.getTotalPotInterest() + "元");
                    tvZrSy.setText("+" + baseVo.result.getPotYestodayInterest() + "");
                    double stayBackCapital = Double.parseDouble(baseVo.result.getStayBackCapital());
                    double freezeMoney = Double.parseDouble(baseVo.result.getFreezeMoney());
                    double balance = Double.parseDouble(baseVo.result.getBalance());
                    double asset = Double.parseDouble(baseVo.result.getAsset());
                    float ztje = (float) (stayBackCapital / asset);
                    float djje = (float) (freezeMoney / asset);
                    float keyje = (float) (balance / asset);
                    if (ztje < 0) {
                        ztje = 0;
                    }
                    if (djje < 0) {
                        djje = 0;
                    }
                    if (keyje < 0) {
                        keyje = 0;
                    }
                    pieChart.initSrc(new float[]{ztje, djje, keyje}, new String[]{"#FF4a4a", "#2e2b39",
                            "#ffc655"}, new PieChart.OnItemClickListener() {
                        @Override
                        public void click(int position) {
                        }
                    });
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
       AiMiCrowdFundingApplication.context().addToRequestQueue(jacksonRequest,getClass().getName());
    }

}
