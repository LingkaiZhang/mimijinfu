package com.nongjinsuo.mimijinfu.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.nongjinsuo.mimijinfu.R;
import com.nongjinsuo.mimijinfu.beans.BackMoneyAllModel;
import com.nongjinsuo.mimijinfu.beans.BackMoneyBean;
import com.nongjinsuo.mimijinfu.config.AiMiCrowdFundingApplication;
import com.nongjinsuo.mimijinfu.config.Constants;
import com.nongjinsuo.mimijinfu.dao.http.JacksonRequest;
import com.nongjinsuo.mimijinfu.dao.http.RequestMap;
import com.nongjinsuo.mimijinfu.util.LogUtil;
import com.nongjinsuo.mimijinfu.util.NetworkUtils;
import com.nongjinsuo.mimijinfu.util.ViewHolder;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author czz
 * @Description: (回款计划)
 */
public class RepaymentScheduleActivity extends AbstractActivity {
    @BindView(R.id.listView)
    ListView listView;
    @BindView(R.id.ivNoData)
    ImageView ivNoData;
    @BindView(R.id.tvNoData)
    TextView tvNoData;
    @BindView(R.id.tvRefresh)
    TextView tvRefresh;
    @BindView(R.id.rlNoDataView)
    RelativeLayout rlNoDataView;
    @BindView(R.id.llFundingRecordTitle)
    LinearLayout llFundingRecordTitle;
    @BindView(R.id.tvZhk)
    TextView tvZhk;
    @BindView(R.id.tvHkfs)
    TextView tvHkfs;
    private String projectBm;
    private LayoutInflater layoutInflater;
    public static final String STATUS = "status";//0 总金额回款计划 1 当前用户回款计划
    public static final int ACTIVE_USER = 1;//当前用户
    private int status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repayment_schedule);
        ButterKnife.bind(this);
        projectBm = getIntent().getStringExtra(Constants.PROJECTBM);
        status = getIntent().getIntExtra(STATUS, 0);
        init();
        addListener();
        loadData();
    }

    @Override
    public void init() {
        titleView.setText("回款计划");
        listView = (ListView) findViewById(R.id.listView);
        layoutInflater = LayoutInflater.from(RepaymentScheduleActivity.this);
    }

    protected void onDestroy() {
        super.onDestroy();
        AiMiCrowdFundingApplication.context().cancelPendingRequests(getClass().getName());
    }

    private void loadData() {
        if (!NetworkUtils.isNetworkAvailable(this)) {
            listView.setVisibility(View.GONE);
            rlNoDataView.setVisibility(View.VISIBLE);
            tvNoData.setText(getString(R.string.str_no_network));
            ivNoData.setImageResource(R.drawable.img_wwl);
            return;
        }
        showLoading();
        rlNoDataView.setVisibility(View.GONE);
        listView.setVisibility(View.VISIBLE);
        JacksonRequest<BackMoneyAllModel> jacksonRequest = new JacksonRequest<>(RequestMap.backMoney(projectBm, status), BackMoneyAllModel.class, new Response.Listener<BackMoneyAllModel>() {
            @Override
            public void onResponse(BackMoneyAllModel baseVo) {
                cancleLoading();
                if (baseVo.getCode().equals(Constants.SUCCESS)) {
                    BackMoneyAllModel.ResultBean resultBean = baseVo.getResult();
                    tvZhk.setText("总回款："+resultBean.getBackSumMoney()+resultBean.getBackSumUnit());
                    tvHkfs.setText("方式："+resultBean.getBackMoneyClass());
                    if (baseVo.getResult() != null && baseVo.getResult().getBackMoney().size() > 0) {
                        listView.setAdapter(new RepaymentScheduleAdapter(baseVo.getResult().getBackMoney()));
                    } else {
                        noData();
                    }
                } else {
                    noData();
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

    private void noData() {
        listView.setVisibility(View.GONE);
        rlNoDataView.setVisibility(View.VISIBLE);
        tvNoData.setText(getString(R.string.str_no_data));
        ivNoData.setImageResource(R.drawable.img_wnr_49);
        tvRefresh.setVisibility(View.GONE);
        llFundingRecordTitle.setVisibility(View.GONE);
    }

    @Override
    public void addListener() {

    }

    @OnClick(R.id.tvRefresh)
    public void onClick() {
        loadData();
    }

    private class RepaymentScheduleAdapter extends BaseAdapter {
        private List<BackMoneyBean> backMoney;

        public RepaymentScheduleAdapter(List<BackMoneyBean> backMoney) {
            this.backMoney = backMoney;
        }

        @Override
        public int getCount() {
            return backMoney.size();
        }

        @Override
        public BackMoneyBean getItem(int i) {
            return backMoney.get(i);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if (view == null) {
                view = layoutInflater.inflate(R.layout.item_repayment_schedule, null);
            }
           BackMoneyBean backMoneyBean = getItem(i);
            TextView tvCount = ViewHolder.get(view, R.id.tvCount);
            TextView tvDate = ViewHolder.get(view, R.id.tvDate);
            TextView tvStatus = ViewHolder.get(view, R.id.tvStatus);

            TextView tvYhbj = ViewHolder.get(view, R.id.tvYhbj);
            TextView tvYhbjUnit = ViewHolder.get(view, R.id.tvYhbjUnit);

            TextView tvYhlx = ViewHolder.get(view, R.id.tvYhlx);
            TextView tvYhlxUnit = ViewHolder.get(view, R.id.tvYhlxUnit);

            TextView tvBxzj = ViewHolder.get(view, R.id.tvBxzj);
            TextView tvBxzjUnit = ViewHolder.get(view, R.id.tvBxzjUnit);
            tvCount.setText(backMoneyBean.getSn() + "/" + backMoneyBean.getDuraNum());
            tvDate.setText(backMoneyBean.getBackTime());
            if (backMoneyBean.getPayStatus() == 0) {
                tvStatus.setText("待回款");
                tvCount.setBackgroundColor(getResources().getColor(R.color.color_ff3947));
                tvStatus.setTextColor(getResources().getColor(R.color.color_ff3947));
            } else {
                tvStatus.setText("回款完成");
                tvStatus.setTextColor(getResources().getColor(R.color.color_homepage_quanbu));
                tvCount.setBackgroundColor(getResources().getColor(R.color.dialog_line));
            }
            tvYhbj.setText(backMoneyBean.getCapitalMoney());
            tvYhbjUnit.setText(backMoneyBean.getCapitalUnit());
            tvYhlx.setText(backMoneyBean.getInterestMoney() + "");
            tvYhlxUnit.setText(backMoneyBean.getInterestUnit());
            tvBxzj.setText(backMoneyBean.getBackMoneyMoney() + "");
            tvBxzjUnit.setText(backMoneyBean.getBackMoneyUnit());

            return view;
        }
    }
}
