package com.nongjinsuo.mimijinfu.fragment;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.nongjinsuo.mimijinfu.activity.FindPasswordActivity;
import com.nongjinsuo.mimijinfu.activity.ZxkfWebViewActivity;
import com.nongjinsuo.mimijinfu.util.PxAndDip;
import com.pnikosis.materialishprogress.ProgressWheel;
import com.nongjinsuo.mimijinfu.R;
import com.nongjinsuo.mimijinfu.activity.ActivityListActivity;
import com.nongjinsuo.mimijinfu.activity.IBase;
import com.nongjinsuo.mimijinfu.activity.NewsAndTrendsActivity;
import com.nongjinsuo.mimijinfu.activity.WebViewAndJsActivity;
import com.nongjinsuo.mimijinfu.adapter.ActivityAdapter;
import com.nongjinsuo.mimijinfu.config.AiMiCrowdFundingApplication;
import com.nongjinsuo.mimijinfu.config.Constants;
import com.nongjinsuo.mimijinfu.dao.http.JacksonRequest;
import com.nongjinsuo.mimijinfu.dao.http.RequestMap;
import com.nongjinsuo.mimijinfu.dao.http.Urls;
import com.nongjinsuo.mimijinfu.httpmodel.FindModel;
import com.nongjinsuo.mimijinfu.util.LogUtil;
import com.nongjinsuo.mimijinfu.util.NetworkUtils;
import com.nongjinsuo.mimijinfu.util.Util;
import com.nongjinsuo.mimijinfu.widget.MyListView;
import com.nongjinsuo.mimijinfu.widget.VerticalSwitchTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * 发现fragment
 */
public class FindFragment extends BaseFragment implements IBase, BGARefreshLayout.BGARefreshLayoutDelegate {
    @BindView(R.id.listview)
    MyListView listview;
    Unbinder unbinder;
    @BindView(R.id.ivStatusBar)
    ImageView ivStatusBar;
    @BindView(R.id.title_text)
    TextView titleText;
    @BindView(R.id.rlTitleBar)
    RelativeLayout rlTitleBar;
    @BindView(R.id.swipeRefreshLayout)
    BGARefreshLayout swipeRefreshLayout;
    @BindView(R.id.ll_mainview)
    LinearLayout llMainview;
    @BindView(R.id.tvZhzje)
    TextView tvZhzje;
    @BindView(R.id.tvYqzsy)
    TextView tvYqzsy;
    @BindView(R.id.ivBzzx)
    ImageView ivBzzx;
    @BindView(R.id.ivZxkf)
    ImageView ivZxkf;
    @BindView(R.id.llXwdt)
    RelativeLayout llXwdt;
    @BindView(R.id.scroll)
    ScrollView scroll;
    @BindView(R.id.ivNoData)
    ImageView ivNoData;
    @BindView(R.id.tvNoData)
    TextView tvNoData;
    @BindView(R.id.tvRefresh)
    TextView tvRefresh;
    @BindView(R.id.rlNoDataView)
    RelativeLayout rlNoDataView;
    @BindView(R.id.progress_wheel)
    ProgressWheel progressWheel;
    @BindView(R.id.tvMessage)
    VerticalSwitchTextView tvMessage;
    private FindModel findModel;
    private ActivityAdapter activityAdapter;

    public static FindFragment newInstance() {
        return new FindFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_find, container, false);
        unbinder = ButterKnife.bind(this, view);
        init();
        addListener();
        loadDate(true);
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        AiMiCrowdFundingApplication.context().cancelPendingRequests(getClass().getName());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void init() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            ivStatusBar.setVisibility(View.VISIBLE);
            Util.setStatusBarHeight(getContext(), ivStatusBar);
        } else {
            ivStatusBar.setVisibility(View.GONE);
        }
        listview.setFocusable(false);
        swipeRefreshLayout.setRefreshViewHolder(new BGANormalRefreshViewHolder(getContext(), true));
    }

    @Override
    public void addListener() {
        swipeRefreshLayout.setDelegate(this);
        int imgWidth = Constants.WINDOW_WIDTH/2- PxAndDip.dip2px(getActivity(),24);
        int imgHeight = imgWidth* 130/330;
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(imgWidth,imgHeight);
        layoutParams.rightMargin = PxAndDip.dip2px(getActivity(),8);
        LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(imgWidth,imgHeight);
        layoutParams2.leftMargin = PxAndDip.dip2px(getActivity(),8);
        ivBzzx.setLayoutParams(layoutParams);
        ivZxkf.setLayoutParams(layoutParams2);
    }

    /**
     * 无网络活服务器繁忙
     *
     * @param flag
     */
    private void netWorkFail(int flag) {
        swipeRefreshLayout.setVisibility(View.GONE);
        progressWheel.setVisibility(View.GONE);
        rlNoDataView.setVisibility(View.VISIBLE);
        ivNoData.setImageResource(R.drawable.img_wwl);
        if (flag == 0) {
            tvNoData.setText(getString(R.string.str_no_network));
            Toast.makeText(getContext(), getString(R.string.str_no_network), Toast.LENGTH_SHORT).show();
        } else {
            tvNoData.setText(getString(R.string.str_no_service));
            Toast.makeText(getContext(), getString(R.string.str_no_service), Toast.LENGTH_SHORT).show();
        }
        tvRefresh.setVisibility(View.VISIBLE);
    }

    private void loadDate(boolean isFirst) {
        if (isFirst && !NetworkUtils.isNetworkAvailable(getContext())) {
            netWorkFail(0);
            return;
        }
        if (!isFirst && !NetworkUtils.isNetworkAvailable(getContext())) {
//            swipeRefreshLayout.setRefreshing(false);
            swipeRefreshLayout.endRefreshing();
            Toast.makeText(getContext(), getString(R.string.str_no_network), Toast.LENGTH_SHORT).show();
            return;
        }
        if (isFirst && NetworkUtils.isNetworkAvailable(getContext())) {
            rlNoDataView.setVisibility(View.GONE);
            progressWheel.setVisibility(View.VISIBLE);
        }
        JacksonRequest<FindModel> jacksonRequest = new JacksonRequest<>(RequestMap.find(), FindModel.class, new Response.Listener<FindModel>() {
            @Override
            public void onResponse(FindModel baseVo) {
                findModel = baseVo;
                progressWheel.setVisibility(View.GONE);
                swipeRefreshLayout.endRefreshing();
                if (baseVo.getCode().equals(Constants.SUCCESS)) {
                    swipeRefreshLayout.setVisibility(View.VISIBLE);
                    rlNoDataView.setVisibility(View.GONE);
                    FindModel.ResultBean resultBean = baseVo.getResult();
                    tvZhzje.setText(resultBean.getPlatformTotalDealNum());
                    tvYqzsy.setText(resultBean.getPlatformTotalInterest());
                    setMessage(baseVo.getResult().getNews());
                    activityAdapter = new ActivityAdapter(getActivity());
                    activityAdapter.setActivity(baseVo.getResult().getActivity());
                    listview.setAdapter(activityAdapter);
                } else {
                    netWorkFail(1);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                swipeRefreshLayout.endRefreshing();
                netWorkFail(1);
                if (volleyError != null && volleyError.getCause() != null) {
                    LogUtil.d("volleyError", volleyError.getCause().getMessage());
                }
            }
        });
        jacksonRequest.setRetryPolicy(new DefaultRetryPolicy(10 * 1000, 2, 1.0f));
        AiMiCrowdFundingApplication.context().addToRequestQueue(jacksonRequest, getClass().getName());
    }

    private ArrayList<String> noticeList = new ArrayList<>();

    /**
     * 设置新闻消息
     *
     * @param baseVo
     */
    private void setMessage(List<FindModel.ResultBean.NewsBean> baseVo) {
        if (baseVo != null && baseVo.size() > 0) {
            tvMessage.setVisibility(View.VISIBLE);
            noticeList.clear();
            for (int i = 0; i < baseVo.size(); i++) {
                noticeList.add(baseVo.get(i).getTitle());
            }
            tvMessage.setTextContent(noticeList);

        }
    }

    @OnClick({R.id.ivBzzx, R.id.ivZxkf, R.id.llXwdt, R.id.llSssj, R.id.tvRefresh, R.id.llActivity, R.id.tvMessage})
    public void onViewClicked(View view) {
        if (!NetworkUtils.isNetworkAvailable(getActivity())) {
            Toast.makeText(getActivity(), getString(R.string.str_no_network), Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent;
        switch (view.getId()) {
            case R.id.ivBzzx:
                if (findModel != null) {
                    intent = new Intent(getActivity(), WebViewAndJsActivity.class);
                    intent.putExtra(WebViewAndJsActivity.URL, Urls.PROJECT_URL + findModel.getResult().getHelpUrl());
                    startActivity(intent);
                }
                break;
            case R.id.ivZxkf:
                if (findModel != null) {
                    intent = new Intent(getActivity(), ZxkfWebViewActivity.class);
                    intent.putExtra(ZxkfWebViewActivity.URL,Urls.PROJECT_URL + findModel.getResult().getKfUrl());
                    intent.putExtra(ZxkfWebViewActivity.NOZXKF,false);
                    startActivity(intent);
                }
                break;
            case R.id.llXwdt:
            case R.id.tvMessage:
                intent = new Intent(getActivity(), NewsAndTrendsActivity.class);
                startActivity(intent);
                break;
            case R.id.llSssj:
                if (findModel != null) {
                    intent = new Intent(getActivity(), WebViewAndJsActivity.class);
                    intent.putExtra(WebViewAndJsActivity.URL, Urls.PROJECT_URL + findModel.getResult().getRealDataUrl());
                    startActivity(intent);
                }
                break;
            case R.id.tvRefresh:
                loadDate(true);
                break;
            case R.id.llActivity:
                intent = new Intent(getActivity(), ActivityListActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        loadDate(false);
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        return false;
    }
}
