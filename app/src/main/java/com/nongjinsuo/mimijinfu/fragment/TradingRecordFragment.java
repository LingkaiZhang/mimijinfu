package com.nongjinsuo.mimijinfu.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.pnikosis.materialishprogress.ProgressWheel;
import com.nongjinsuo.mimijinfu.R;
import com.nongjinsuo.mimijinfu.activity.IBase;
import com.nongjinsuo.mimijinfu.adapter.TansactionRecordAdapter;
import com.nongjinsuo.mimijinfu.config.AiMiCrowdFundingApplication;
import com.nongjinsuo.mimijinfu.config.Constants;
import com.nongjinsuo.mimijinfu.dao.http.JacksonRequest;
import com.nongjinsuo.mimijinfu.dao.http.RequestMap;
import com.nongjinsuo.mimijinfu.httpmodel.TraderecodeModel;
import com.nongjinsuo.mimijinfu.util.LogUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * description: (交易记录-根据不同状态)
 * autour: czz
 * date: 2017/12/1 0001 下午 3:18
 * update: 2017/12/1 0001
 */

public class TradingRecordFragment extends BaseFragment implements BGARefreshLayout.BGARefreshLayoutDelegate,IBase {
    @BindView(R.id.listView)
    ListView listView;
    @BindView(R.id.swipeRefreshLayout)
    BGARefreshLayout swipeRefreshLayout;
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
    Unbinder unbinder;
    private int page;
    private TansactionRecordAdapter tansactionRecordAdapter;
    private String classId;
    public static TradingRecordFragment newInstance(String classId){
        TradingRecordFragment tradingRecordFragment = new TradingRecordFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.CLASSID,classId);
        tradingRecordFragment.setArguments(bundle);
        return tradingRecordFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        classId = getArguments().getString(Constants.CLASSID);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trading_record, null);
        unbinder = ButterKnife.bind(this, view);
        init();
        addListener();
        loadDate(true,true);
        return view;

    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        loadDate(false, true);
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        if (!swipeRefreshLayout.ismIsShowLoadingMoreView()) {
            return false;
        }
        loadDate(false, false);
        return true;
    }

    private void loadDate(boolean isFirst, boolean isRefresh) {
        rlNoDataView.setVisibility(View.GONE);
        listView.setVisibility(View.VISIBLE);
        if (isFirst)
            progressWheel.setVisibility(View.VISIBLE);
        if (isRefresh) {
            swipeRefreshLayout.setIsShowLoadingMoreView(true);
            page = 0;
        } else {
            page++;
        }
        JacksonRequest<TraderecodeModel> jacksonRequest = new JacksonRequest<>(RequestMap.traderecode(page, classId), TraderecodeModel.class, new Response.Listener<TraderecodeModel>() {
            @Override
            public void onResponse(TraderecodeModel baseVo) {
                progressWheel.setVisibility(View.GONE);
                swipeRefreshLayout.endRefreshing();
                swipeRefreshLayout.endLoadingMore();
                listView.setVisibility(View.VISIBLE);
                rlNoDataView.setVisibility(View.GONE);
                if (baseVo.code.equals(Constants.SUCCESS)) {
                    if (page == 0) {
                        if (baseVo.result.getTradeRecode().size()>0){
                            if (tansactionRecordAdapter == null) {
                                tansactionRecordAdapter = new TansactionRecordAdapter(getActivity(), baseVo.result.getTradeRecode());
                                listView.setAdapter(tansactionRecordAdapter);
                            } else {
                                tansactionRecordAdapter.setResult(baseVo.result.getTradeRecode());
                                tansactionRecordAdapter.notifyDataSetChanged();
                            }
                            if (baseVo.result.getTradeRecode().size() < 20) {
                                swipeRefreshLayout.setIsShowLoadingMoreView(false);
                            }
                        }else{
                            rlNoDataView.setVisibility(View.VISIBLE);
                            listView.setVisibility(View.GONE);
                        }
                    } else {
                        tansactionRecordAdapter.setResultMore(baseVo.result.getTradeRecode());
                    }
                } else {
                    if (page == 0) {
                        rlNoDataView.setVisibility(View.VISIBLE);
                        listView.setVisibility(View.GONE);
                    } else {
                        swipeRefreshLayout.setIsShowLoadingMoreView(false);
                    }
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
        AiMiCrowdFundingApplication.context().addToRequestQueue(jacksonRequest, getClass().getName());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        AiMiCrowdFundingApplication.context().cancelPendingRequests(getClass().getName());
    }

    @Override
    public void init() {
        swipeRefreshLayout.setRefreshViewHolder(new BGANormalRefreshViewHolder(getActivity(), true));
        swipeRefreshLayout.setDelegate(this);
        swipeRefreshLayout.setPullDownRefreshEnable(false);
    }

    @Override
    public void addListener() {

    }
}
