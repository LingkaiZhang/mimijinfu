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
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.nongjinsuo.mimijinfu.R;
import com.nongjinsuo.mimijinfu.adapter.MessageAdapter;
import com.nongjinsuo.mimijinfu.beans.MessageVo;
import com.nongjinsuo.mimijinfu.config.AiMiCrowdFundingApplication;
import com.nongjinsuo.mimijinfu.config.Constants;
import com.nongjinsuo.mimijinfu.config.SharedPreferenceUtil;
import com.nongjinsuo.mimijinfu.dao.http.JacksonRequest;
import com.nongjinsuo.mimijinfu.dao.http.RequestMap;
import com.nongjinsuo.mimijinfu.httpmodel.MessageModel;
import com.nongjinsuo.mimijinfu.util.LogUtil;
import com.nongjinsuo.mimijinfu.widget.EventBarEntity;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import de.greenrobot.event.EventBus;

/**
 * @author czz
 * @Description: (消息页面)
 */
public class MessageActivity extends AbstractActivity implements BGARefreshLayout.BGARefreshLayoutDelegate {
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
    private MessageAdapter messageAdapter;
    private int page;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        titleView.setText("消息");
        ButterKnife.bind(this);
        init();
        addListener();
        loadData(0, true);
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
                    MessageVo messageVo = messageAdapter.getItem(i);
                    if (messageVo.getClasses().equals(Constants.URL)) {
                        Intent intent = new Intent(MessageActivity.this, WebViewActivity.class);
                        intent.putExtra(WebViewActivity.URL, messageVo.getUrl());
                        startActivity(intent);
                    }else if(messageVo.getClasses().equals(Constants.INTER_URL)){
                        Intent intent = new Intent(MessageActivity.this, WebViewAndJsActivity.class);
                        intent.putExtra(WebViewAndJsActivity.URL, messageVo.getUrl());
                        startActivity(intent);
                    } else if (messageVo.getClasses().equals(Constants.PROJECT)) {
                        Intent intent = new Intent(MessageActivity.this, ProjectDetilsActivity.class);
                        intent.putExtra(Constants.PROJECTBM, messageVo.getProjectBm());
                        startActivity(intent);
                        overridePendingTransition(R.anim.activity_close_in_anim, R.anim.activity_close_out_anim);
                    } else if (messageVo.getClasses().equals(Constants.OPEN_MESSAGE)) {
                        Intent intent = new Intent(MessageActivity.this, SystemNotificationActivity.class);
                        intent.putExtra(SystemNotificationActivity.MESSAGEVO, messageVo);
                        startActivity(intent);
                    }
                    messagedetail(i, messageVo.getMessageId());
                }
            }
        });
    }

    private void messagedetail(final int position, String messageId) {
        JacksonRequest<MessageModel> jacksonRequest = new JacksonRequest<>(RequestMap.messagedetail(messageId), MessageModel.class, new Response.Listener<MessageModel>() {
            @Override
            public void onResponse(MessageModel baseVo) {
                cancleLoading();
                if (baseVo.code.equals(Constants.SUCCESS)) {
                    if (messageAdapter != null) {
                        messageAdapter.getItem(position).setIsRead(1);
                        messageAdapter.notifyDataSetChanged();
                        EventBus.getDefault().post(new EventBarEntity(EventBarEntity.UPDATE_HOMEPAGE_MESSAGE));
                    }
                }
            }
        }
                , new Response.ErrorListener()

        {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                cancleLoading();
                if (volleyError != null && volleyError.getCause() != null) {
                    LogUtil.d("volleyError", volleyError.getCause().getMessage());
                }
            }
        }

        );
        jacksonRequest.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 1, 1.0f));
       AiMiCrowdFundingApplication.context().addToRequestQueue(jacksonRequest,getClass().getName());
    }
    protected void onDestroy() {
        super.onDestroy();
        AiMiCrowdFundingApplication.context().cancelPendingRequests(getClass().getName());
    }
    private void loadData(final int page, boolean isFirst) {
        if (!SharedPreferenceUtil.isLogin(MessageActivity.this)) {
            rlNoDataView.setVisibility(View.VISIBLE);
            listView.setVisibility(View.GONE);
            return;
        }
        if (page == 0 && isFirst)
            showLoading();
        JacksonRequest<MessageModel> jacksonRequest = new JacksonRequest<>(RequestMap.getcentralMap(page), MessageModel.class, new Response.Listener<MessageModel>() {
            @Override
            public void onResponse(MessageModel baseVo) {
                cancleLoading();
                swipeRefreshLayout.endRefreshing();
                swipeRefreshLayout.endLoadingMore();
                if (baseVo.code.equals(Constants.SUCCESS)) {
                    if (baseVo.result.message.size() == 0) {
                        if (page == 0){
                            rlNoDataView.setVisibility(View.VISIBLE);
                            listView.setVisibility(View.GONE);
                        }
                    } else {
                        if (page == 0) {
                            messageAdapter = new MessageAdapter(MessageActivity.this, baseVo.result.message);
                            listView.setAdapter(messageAdapter);
                            listView.setVisibility(View.VISIBLE);
                            if (baseVo.result.message.size() < 20) {
                                swipeRefreshLayout.setIsShowLoadingMoreView(false);
                            }
                        } else {
                            messageAdapter.setMoreMessageVoList(baseVo.result.message);
                        }
                    }

                } else {
                    if (page == 0) {
                        rlNoDataView.setVisibility(View.VISIBLE);
                        listView.setVisibility(View.GONE);
                    } else {
                        Toast.makeText(MessageActivity.this, "加载完成", Toast.LENGTH_SHORT).show();
                        swipeRefreshLayout.setIsShowLoadingMoreView(false);
                    }

                }
            }
        }

                , new Response.ErrorListener()

        {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                cancleLoading();
                if (volleyError != null && volleyError.getCause() != null) {
                    LogUtil.d("volleyError", volleyError.getCause().getMessage());
                }
            }
        }

        );
        jacksonRequest.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 1, 1.0f));
       AiMiCrowdFundingApplication.context().addToRequestQueue(jacksonRequest,getClass().getName());
    }


    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        swipeRefreshLayout.setIsShowLoadingMoreView(true);
        page = 0;
        loadData(page, false);
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        if (!swipeRefreshLayout.ismIsShowLoadingMoreView()) {
            return false;
        }
        page++;
        loadData(page, false);
        return true;
    }
}
