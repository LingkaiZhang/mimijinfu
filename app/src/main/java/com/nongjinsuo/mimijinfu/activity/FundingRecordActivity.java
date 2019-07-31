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
import com.nongjinsuo.mimijinfu.beans.ProjectInvestVo;
import com.nongjinsuo.mimijinfu.config.AiMiCrowdFundingApplication;
import com.nongjinsuo.mimijinfu.config.Constants;
import com.nongjinsuo.mimijinfu.dao.http.JacksonRequest;
import com.nongjinsuo.mimijinfu.dao.http.RequestMap;
import com.nongjinsuo.mimijinfu.httpmodel.ProjectInvestModel;
import com.nongjinsuo.mimijinfu.util.LogUtil;
import com.nongjinsuo.mimijinfu.util.NetworkUtils;
import com.nongjinsuo.mimijinfu.util.ViewHolder;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author czz
 * @Description: (募集记录)
 */
public class FundingRecordActivity extends AbstractActivity {
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
    @BindView(R.id.tvPhone)
    TextView tvPhone;
    private String projectBm;
    public static final String ISRENGOU = "isRenGou";
    private boolean isRenGou = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_funding_record);
        ButterKnife.bind(this);
        projectBm = getIntent().getStringExtra(Constants.PROJECTBM);
        isRenGou = getIntent().getBooleanExtra(ISRENGOU, false);
        init();
        addListener();
        loadData();
    }

    @Override
    public void init() {
        if (isRenGou) {
            titleView.setText("认购记录");
            tvPhone.setText("认购人");
        } else {
            titleView.setText("认购记录");
        }
        listView = (ListView) findViewById(R.id.listView);
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
        JacksonRequest<ProjectInvestModel> jacksonRequest = new JacksonRequest<>(RequestMap.projectinvestrecode(projectBm), ProjectInvestModel.class, new Response.Listener<ProjectInvestModel>() {
            @Override
            public void onResponse(ProjectInvestModel baseVo) {
                cancleLoading();
                if (baseVo.getCode().equals(Constants.SUCCESS)) {
                    if (baseVo.result != null && baseVo.result.size() > 0) {
                        listView.setAdapter(new FundingRecordAdapter(baseVo.result));
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
    }

    @Override
    public void addListener() {

    }

    @OnClick(R.id.tvRefresh)
    public void onClick() {
        loadData();
    }

    private class FundingRecordAdapter extends BaseAdapter {
        private List<ProjectInvestVo> projectInvestVos;

        public FundingRecordAdapter(List<ProjectInvestVo> projectInvestVos) {
            this.projectInvestVos = projectInvestVos;
        }

        @Override
        public int getCount() {
            return projectInvestVos.size();
        }

        @Override
        public ProjectInvestVo getItem(int i) {
            return projectInvestVos.get(i);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if (view == null) {
                view = LayoutInflater.from(FundingRecordActivity.this).inflate(R.layout.item_funding_record, null);
            }
            TextView tvName = ViewHolder.get(view, R.id.tvName);
            TextView tvMoney = ViewHolder.get(view, R.id.tvMoney);
            TextView tvTime = ViewHolder.get(view, R.id.tvTime);
            ProjectInvestVo item = getItem(i);
            tvName.setText(item.getNickName());
            tvMoney.setText(item.getInvestMoney());
            tvTime.setText(item.getInvestTime());
            LinearLayout linear = ViewHolder.get(view, R.id.linear);
            if (i % 2 == 0) {
                linear.setBackgroundColor(getResources().getColor(R.color.white));
            } else {
                linear.setBackgroundColor(getResources().getColor(R.color.color_f8fafd));
            }
            return view;
        }
    }
}
