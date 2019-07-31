package com.nongjinsuo.mimijinfu.fragment;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.nongjinsuo.mimijinfu.R;
import com.nongjinsuo.mimijinfu.activity.IBase;
import com.nongjinsuo.mimijinfu.activity.P2pDetilsActivity;
import com.nongjinsuo.mimijinfu.activity.ProjectDetilsActivity;
import com.nongjinsuo.mimijinfu.adapter.P2pAndProject2Adapter;
import com.nongjinsuo.mimijinfu.beans.DataBean;
import com.nongjinsuo.mimijinfu.beans.DingTouHuoTouModel;
import com.nongjinsuo.mimijinfu.config.AiMiCrowdFundingApplication;
import com.nongjinsuo.mimijinfu.config.Constants;
import com.nongjinsuo.mimijinfu.dao.http.JacksonRequest;
import com.nongjinsuo.mimijinfu.dao.http.RequestMap;
import com.nongjinsuo.mimijinfu.util.CountDownTask;
import com.nongjinsuo.mimijinfu.util.LogUtil;
import com.nongjinsuo.mimijinfu.util.NetworkUtils;
import com.nongjinsuo.mimijinfu.util.Util;
import com.nongjinsuo.mimijinfu.widget.EventBarEntity;
import com.nongjinsuo.mimijinfu.widget.PinnedHeaderExpandableListView;
import com.pnikosis.materialishprogress.ProgressWheel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import de.greenrobot.event.EventBus;

/**
 * description: (项目列表页-定投宝和活投宝-悬浮分组列表)
 * autour: czz
 * date: 2017/11/29 0029 上午 11:12
 * update: 2017/11/29 0029
 */

public class P2pAndProjectFragment2 extends BaseFragment implements IBase, BGARefreshLayout.BGARefreshLayoutDelegate {
    @BindView(R.id.ivStatusBar)
    ImageView ivStatusBar;
    @BindView(R.id.explistview)
    PinnedHeaderExpandableListView explistview;
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
    @BindView(R.id.swipeRefreshLayout)
    BGARefreshLayout swipeRefreshLayout;
    private P2pAndProject2Adapter adapter;

    public static P2pAndProjectFragment2 newInstance() {
        P2pAndProjectFragment2 p2pAndProjectFragment = new P2pAndProjectFragment2();
        return p2pAndProjectFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_p2p_and_project2, null);
        ButterKnife.bind(this, view);
        init();
        addListener();
        EventBus.getDefault().register(this);
        return view;
    }

    public void onEventMainThread(EventBarEntity event) {
        if (event.getType() == EventBarEntity.LOGIN_UPDATE_WEB) {
            loadDate(false);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (adapter!=null){
            adapter.cancelAllTimers();
        }

        EventBus.getDefault().unregister(this);
    }

    @Override
    public void init() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            ivStatusBar.setVisibility(View.VISIBLE);
            Util.setStatusBarHeight(getContext(), ivStatusBar);
        } else {
            ivStatusBar.setVisibility(View.GONE);
        }
        //设置悬浮头部VIEW
        explistview.setHeaderView(View.inflate(getActivity(), R.layout.group,
                null));
        loadDate(true);
        swipeRefreshLayout.setRefreshViewHolder(new BGANormalRefreshViewHolder(getContext(), false));
        swipeRefreshLayout.setDelegate(this);
        adapter = new P2pAndProject2Adapter(getActivity());
    }

    private void loadDate(boolean isFirst) {
        if (isFirst && !NetworkUtils.isNetworkAvailable(getContext())) {
            netWorkFail(0);
            return;
        }
        if (!isFirst && !NetworkUtils.isNetworkAvailable(getContext())) {
            swipeRefreshLayout.endRefreshing();
//            Toast.makeText(getContext(), getString(R.string.str_no_network), Toast.LENGTH_SHORT).show();
            return;
        }
        if (isFirst && NetworkUtils.isNetworkAvailable(getContext())) {
            rlNoDataView.setVisibility(View.GONE);
            progressWheel.setVisibility(View.VISIBLE);
        }
        JacksonRequest<DingTouHuoTouModel> jacksonRequest = new JacksonRequest<>(RequestMap.listAll(), DingTouHuoTouModel.class, new Response.Listener<DingTouHuoTouModel>() {
            @Override
            public void onResponse(DingTouHuoTouModel baseVo) {
                progressWheel.setVisibility(View.GONE);
                swipeRefreshLayout.setVisibility(View.VISIBLE);
                swipeRefreshLayout.endRefreshing();
                swipeRefreshLayout.endLoadingMore();
                if (baseVo.getCode().equals(Constants.SUCCESS)) {
                    if (baseVo.getResult().getList().size() > 0) {
                        adapter.setListBeanList(baseVo.getResult().getList());
                        explistview.setAdapter(adapter);
                        //遍历所有group,将所有项设置成默认展开
                        int groupCount = explistview.getCount();
                        for (int i = 0; i < groupCount; i++) {
                            explistview.expandGroup(i);
                        }
                    } else {
                        swipeRefreshLayout.setVisibility(View.GONE);
                        rlNoDataView.setVisibility(View.VISIBLE);
                        ivNoData.setImageResource(R.drawable.img_wnr_49);
                        tvNoData.setText(getString(R.string.str_no_data));
                        tvRefresh.setVisibility(View.GONE);
                    }

                } else {

                }
                //判断当前账户是否在其它设备登录
                Util.loginPrompt(getActivity(), baseVo.getResult().getLoginState(), baseVo.getResult().getLoginDescr());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                swipeRefreshLayout.endRefreshing();
                progressWheel.setVisibility(View.GONE);
                netWorkFail(1);
                if (volleyError != null && volleyError.getCause() != null) {
                    LogUtil.d("volleyError", volleyError.getCause().getMessage());
                }
            }
        });
        jacksonRequest.setRetryPolicy(new DefaultRetryPolicy(10 * 1000, 2, 1.0f));
        AiMiCrowdFundingApplication.context().addToRequestQueue(jacksonRequest, getClass().getName());
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

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
//            Toast.makeText(getContext(), getString(R.string.str_no_service), Toast.LENGTH_SHORT).show();
        }
        tvRefresh.setVisibility(View.VISIBLE);
    }

    @Override
    public void addListener() {
        explistview.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                // TODO Auto-generated method stub
                return true;
            }
        });
        explistview.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

//                if (!NetworkUtils.isNetworkAvailable(getActivity())) {
//                    Toast.makeText(getActivity(), getActivity().getString(R.string.str_no_network), Toast.LENGTH_SHORT).show();
//                    return false;
//                }
                if (adapter != null) {
                    DataBean investVo = adapter.getChild(groupPosition, childPosition);
                    if (groupPosition == 0) {
                        Intent intent = new Intent(getContext(), P2pDetilsActivity.class);
                        intent.putExtra(Constants.ID, investVo.getId());
                        intent.putExtra(Constants.PROJECTBM, investVo.getBm());
                        intent.putExtra(Constants.STATUS,investVo.getStatus());
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(getContext(), ProjectDetilsActivity.class);
                        intent.putExtra(Constants.PROJECTBM, investVo.getBm());
                        intent.putExtra(Constants.STATUS,investVo.getStatus());
                        startActivity(intent);
                    }
                }

                return false;
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        loadDate(false);
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        return false;
    }

    @OnClick(R.id.tvRefresh)
    public void onViewClicked() {
        loadDate(true);
    }
}
