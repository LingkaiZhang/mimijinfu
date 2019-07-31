package com.nongjinsuo.mimijinfu.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.pnikosis.materialishprogress.ProgressWheel;
import com.nongjinsuo.mimijinfu.R;
import com.nongjinsuo.mimijinfu.adapter.ActivityAdapter;
import com.nongjinsuo.mimijinfu.beans.ActivityBean;
import com.nongjinsuo.mimijinfu.config.AiMiCrowdFundingApplication;
import com.nongjinsuo.mimijinfu.config.Constants;
import com.nongjinsuo.mimijinfu.dao.http.JacksonRequest;
import com.nongjinsuo.mimijinfu.dao.http.RequestMap;
import com.nongjinsuo.mimijinfu.httpmodel.NewsModel;
import com.nongjinsuo.mimijinfu.util.LogUtil;
import com.nongjinsuo.mimijinfu.util.NetworkUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * description: (活动列表)
 * autour: czz
 * date: 2017/12/11 0011 下午 1:29
 * update: 2017/12/11 0011
 */

public class ActivityListActivity extends AbstractActivity implements BGARefreshLayout.BGARefreshLayoutDelegate {
    @BindView(R.id.listView)
    ListView listView;
    @BindView(R.id.swipeRefreshLayout)
    BGARefreshLayout swipeRefreshLayout;
    @BindView(R.id.progress_wheel)
    ProgressWheel progressWheel;
    @BindView(R.id.ivNoData)
    ImageView ivNoData;
    @BindView(R.id.tvNoData)
    TextView tvNoData;
    @BindView(R.id.tvRefresh)
    TextView tvRefresh;
    @BindView(R.id.rlNoDataView)
    RelativeLayout rlNoDataView;
    private int pageNum = 0;
    private ActivityAdapter newsAndTrendsAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_and_trends);
        ButterKnife.bind(this);
        titleView.setText("活动列表");
        init();
        addListener();
    }

    @Override
    public void init() {
        loadDate(true, true);
        swipeRefreshLayout.setDelegate(this);
        swipeRefreshLayout.setRefreshViewHolder(new BGANormalRefreshViewHolder(this, true));
    }

    @Override
    public void addListener() {

    }

    private void loadDate(boolean isRefresh, boolean isFirst) {
        if (isFirst && !NetworkUtils.isNetworkAvailable(this)) {
            netWorkFail(0);
            return;
        }
        if (!isFirst && !NetworkUtils.isNetworkAvailable(this)) {
            swipeRefreshLayout.endRefreshing();
            Toast.makeText(this, getString(R.string.str_no_network), Toast.LENGTH_SHORT).show();
            return;
        }
        if (isFirst && NetworkUtils.isNetworkAvailable(this)) {
            rlNoDataView.setVisibility(View.GONE);
            progressWheel.setVisibility(View.VISIBLE);
        }
        if (isRefresh) {
            swipeRefreshLayout.setIsShowLoadingMoreView(true);
            pageNum = 0;
        } else {
            pageNum++;
        }
        JacksonRequest<NewsModel> jacksonRequest = new JacksonRequest<>(RequestMap.findList(pageNum,"activity"), NewsModel.class, new Response.Listener<NewsModel>() {
            @Override
            public void onResponse(NewsModel baseVo) {
                progressWheel.setVisibility(View.GONE);
                swipeRefreshLayout.endRefreshing();
                swipeRefreshLayout.endLoadingMore();
                if (baseVo != null) {
                    if (baseVo.getCode().equals(Constants.SUCCESS)) {
                        if (baseVo.getResult().getData().size() == 0) {
                            if (pageNum == 0){
                                swipeRefreshLayout.setVisibility(View.GONE);
                                rlNoDataView.setVisibility(View.VISIBLE);
                                ivNoData.setImageResource(R.drawable.img_wnr_49);
                                tvNoData.setText(getString(R.string.str_no_data));
                                tvRefresh.setVisibility(View.GONE);
                            }
                        } else {
                            swipeRefreshLayout.setVisibility(View.VISIBLE);
                            List<ActivityBean> activityBeanList =  baseVo.getResult().getData();
                            if (pageNum == 0) {
                                if (newsAndTrendsAdapter ==null){
                                    newsAndTrendsAdapter = new ActivityAdapter(ActivityListActivity.this,activityBeanList);
                                    listView.setAdapter(newsAndTrendsAdapter);
                                }else{
                                    newsAndTrendsAdapter.setActivity(activityBeanList);
                                }
                                if (baseVo.getResult().getData().size() < 20) {
                                    swipeRefreshLayout.setIsShowLoadingMoreView(false);
                                }
                            } else {
                                newsAndTrendsAdapter.setMoreActivity(activityBeanList);
                            }
                        }
                        //判断当前账户是否在其它设备登录
//                        Util.loginPrompt(getActivity(), baseVo.result.loginPrompt);
                    } else {
                        if (pageNum == 0) {
                            swipeRefreshLayout.setVisibility(View.GONE);
                            rlNoDataView.setVisibility(View.VISIBLE);
                            ivNoData.setImageResource(R.drawable.img_wnr_49);
                            tvNoData.setText(ActivityListActivity.this.getString(R.string.str_no_data));
                            tvRefresh.setVisibility(View.GONE);
                        } else {
                            Toast.makeText(ActivityListActivity.this, "加载完成", Toast.LENGTH_SHORT).show();
                            swipeRefreshLayout.setIsShowLoadingMoreView(false);
                        }
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                swipeRefreshLayout.endRefreshing();
                swipeRefreshLayout.endLoadingMore();
                progressWheel.setVisibility(View.GONE);
                if (pageNum == 0) {
                    netWorkFail(1);
                }
                if (volleyError != null && volleyError.getCause() != null) {
                    LogUtil.d("volleyError", volleyError.getCause().getMessage());
                }
            }
        });
        jacksonRequest.setRetryPolicy(new DefaultRetryPolicy(10 * 1000, 2, 1.0f));
        AiMiCrowdFundingApplication.context().addToRequestQueue(jacksonRequest, getClass().getName());
    }

    private void netWorkFail(int flag) {
        swipeRefreshLayout.setVisibility(View.GONE);
        progressWheel.setVisibility(View.GONE);
        rlNoDataView.setVisibility(View.VISIBLE);
        ivNoData.setImageResource(R.drawable.img_wwl);
        if (flag == 0) {
            tvNoData.setText(getString(R.string.str_no_network));
            Toast.makeText(ActivityListActivity.this, getString(R.string.str_no_network), Toast.LENGTH_SHORT).show();
        } else {
            tvNoData.setText(getString(R.string.str_no_service));
//            Toast.makeText(getContext(), getString(R.string.str_no_service), Toast.LENGTH_SHORT).show();
        }
        tvRefresh.setVisibility(View.VISIBLE);
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        loadDate(true,false);
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        loadDate(false,false);
        return false;
    }
}
