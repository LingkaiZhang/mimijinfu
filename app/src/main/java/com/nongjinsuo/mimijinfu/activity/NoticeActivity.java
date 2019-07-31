package com.nongjinsuo.mimijinfu.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.nongjinsuo.mimijinfu.R;
import com.nongjinsuo.mimijinfu.adapter.NoticeAdapter;
import com.nongjinsuo.mimijinfu.beans.NoticeVo;
import com.nongjinsuo.mimijinfu.config.AiMiCrowdFundingApplication;
import com.nongjinsuo.mimijinfu.config.Constants;
import com.nongjinsuo.mimijinfu.dao.http.JacksonRequest;
import com.nongjinsuo.mimijinfu.dao.http.RequestMap;
import com.nongjinsuo.mimijinfu.httpmodel.NoticeModel;
import com.nongjinsuo.mimijinfu.util.LogUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * @author czz
 * @Description: (公告消息)
 */
public class NoticeActivity extends AbstractActivity implements BGARefreshLayout.BGARefreshLayoutDelegate {
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
    @BindView(R.id.swipeRefreshLayout)
    BGARefreshLayout swipeRefreshLayout;
    private NoticeAdapter messageAdapter;
    private int page = 0;
    private static  RequestQueue mRequestQueue = Volley.newRequestQueue(AiMiCrowdFundingApplication.context());
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        titleView.setText("公告");
        ButterKnife.bind(this);
        init();
        addListener();
        loadData(true,true);
    }

    @Override
    public void init() {
        swipeRefreshLayout.setRefreshViewHolder(new BGANormalRefreshViewHolder(this, true));
        swipeRefreshLayout.setDelegate(this);
        swipeRefreshLayout.setPullDownRefreshEnable(false);
    }

    @Override
    public void addListener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (messageAdapter != null) {
                    NoticeVo noticeVo = messageAdapter.getItem(i);
                    if (noticeVo.getClasses().equals("url")) {
                        Intent intent = new Intent(NoticeActivity.this, WebViewActivity.class);
                        intent.putExtra(WebViewActivity.URL, noticeVo.getUrl());
                        startActivity(intent);
                    } else if (noticeVo.getClasses().equals("project")) {
                        Intent intent = new Intent(NoticeActivity.this, ProjectDetilsActivity.class);
                        intent.putExtra(Constants.PROJECTBM, noticeVo.getProjectBm());
                        startActivity(intent);
                        overridePendingTransition(R.anim.activity_close_in_anim, R.anim.activity_close_out_anim);
                    } else if (noticeVo.getClasses().equals("openMessage")) {
                        Intent intent = new Intent(NoticeActivity.this, SystemNotificationActivity.class);
                        intent.putExtra(SystemNotificationActivity.NOTICEVO, noticeVo);
                        startActivity(intent);
                    }
                }
            }
        });
    }
    protected void onDestroy() {
        super.onDestroy();
        AiMiCrowdFundingApplication.context().cancelPendingRequests(getClass().getName());
    }
    private void loadData(boolean isRefresh, boolean isFirst) {
        if (isFirst)
            showLoading();
        if (isRefresh) {
            page = 0;
        } else {
            page++;
        }
        JacksonRequest<NoticeModel> jacksonRequest = new JacksonRequest<>(RequestMap.notice(page), NoticeModel.class, new Response.Listener<NoticeModel>() {
            @Override
            public void onResponse(NoticeModel baseVo) {
                cancleLoading();
                swipeRefreshLayout.endRefreshing();
                swipeRefreshLayout.endLoadingMore();
                if (baseVo.code.equals(Constants.SUCCESS)) {
                    if (baseVo.result.size() == 0) {
                        if (page == 0){
                            rlNoDataView.setVisibility(View.VISIBLE);
                            swipeRefreshLayout.setVisibility(View.GONE);
                        }
                    } else {
                        if (page == 0){
                            messageAdapter = new NoticeAdapter(NoticeActivity.this, baseVo.result);
                            listView.setAdapter(messageAdapter);
                            if (baseVo.result.size()<20){
                                swipeRefreshLayout.setIsShowLoadingMoreView(false);
                            }
                        }else{
                            messageAdapter.setMessageVoListMore(baseVo.result);
                        }
                    }
                } else {
                    if (page == 0){
                        rlNoDataView.setVisibility(View.VISIBLE);
                        swipeRefreshLayout.setVisibility(View.GONE);
                    }else{
                        swipeRefreshLayout.setIsShowLoadingMoreView(false);
                        Toast.makeText(NoticeActivity.this,"加载完成",Toast.LENGTH_SHORT).show();
                    }
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
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        swipeRefreshLayout.setIsShowLoadingMoreView(true);
        loadData(true,false);
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        if (!swipeRefreshLayout.ismIsShowLoadingMoreView()){
            return false;
        }
        loadData(false,false);
        return true;
    }
}
