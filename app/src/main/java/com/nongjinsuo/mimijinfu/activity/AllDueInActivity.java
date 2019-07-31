package com.nongjinsuo.mimijinfu.activity;

import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.githang.groundrecycleradapter.GroupItemDecoration;
import com.githang.groundrecycleradapter.GroupRecyclerAdapter;
import com.nongjinsuo.mimijinfu.config.AiMiCrowdFundingApplication;
import com.nongjinsuo.mimijinfu.config.Constants;
import com.nongjinsuo.mimijinfu.dao.http.JacksonRequest;
import com.nongjinsuo.mimijinfu.dao.http.RequestMap;
import com.nongjinsuo.mimijinfu.util.LogUtil;
import com.nongjinsuo.mimijinfu.util.NetworkUtils;
import com.pnikosis.materialishprogress.ProgressWheel;
import com.nongjinsuo.mimijinfu.R;
import com.nongjinsuo.mimijinfu.adapter.InfoBeanViewHolder;
import com.nongjinsuo.mimijinfu.adapter.RoomBeanViewHolder;
import com.nongjinsuo.mimijinfu.httpmodel.SoonRoomEntity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * description: (全部待收)
 * autour: czz
 * date: 2017/12/4 0004 下午 4:40
 * update: 2017/12/4 0004
 */

public class AllDueInActivity extends AbstractActivity {
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.ivNoData)
    ImageView ivNoData;
    @BindView(R.id.tvNoData)
    TextView tvNoData;
    @BindView(R.id.tvRefresh)
    TextView tvRefresh;
    @BindView(R.id.rlNoDataView)
    RelativeLayout rlNoDataView;
     LayoutInflater layoutInflater;
    @Override
    public void init() {
        layoutInflater = LayoutInflater.from(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void addListener() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_due_in);
        ButterKnife.bind(this);
        titleView.setText("全部待收");
        init();
        addListener();
        loadDate(true);
    }

    /**
     * 加载数据
     *
     * @param isFirst
     */
    private void loadDate(final boolean isFirst) {
        if (isFirst && !NetworkUtils.isNetworkAvailable(this)) {
            netWorkFail(0);
            return;
        }
        if (isFirst && NetworkUtils.isNetworkAvailable(this)) {
            rlNoDataView.setVisibility(View.GONE);
            showLoading();
        }
        JacksonRequest<SoonRoomEntity> jacksonRequest = new JacksonRequest<>(RequestMap.waitBackMoney(),
                SoonRoomEntity.class, new Response.Listener<SoonRoomEntity>() {
            @Override
            public void onResponse(SoonRoomEntity baseVo) {
                cancleLoading();
                if (baseVo != null) {
                    if (baseVo.getCode().equals(Constants.SUCCESS)) {
                        if (baseVo.getResult().getBack().size() == 0) {
                            netWorkFail(2);
                            return;
                        }
                        GroupRecyclerAdapter<SoonRoomEntity.ResultBean.BackBean, InfoBeanViewHolder, RoomBeanViewHolder> recyclerAdapter =
                                new GroupRecyclerAdapter<SoonRoomEntity.ResultBean.BackBean, InfoBeanViewHolder, RoomBeanViewHolder>(baseVo.getResult().getBack()) {
                                    @Override
                                    protected InfoBeanViewHolder onCreateGroupViewHolder(ViewGroup parent) {
                                        return new InfoBeanViewHolder(layoutInflater.inflate(R.layout.item_jjkk_time, parent, false));
                                    }

                                    @Override
                                    protected RoomBeanViewHolder onCreateChildViewHolder(ViewGroup parent) {
                                        return new RoomBeanViewHolder(layoutInflater.inflate(R.layout.item_repayment_schedule, parent, false),AllDueInActivity.this);
                                    }

                                    @Override
                                    protected void onBindGroupViewHolder(InfoBeanViewHolder holder, int groupPosition) {
                                        holder.update(getGroup(groupPosition));
                                    }

                                    @Override
                                    protected void onBindChildViewHolder(RoomBeanViewHolder holder, int groupPosition, int childPosition) {
                                        holder.update(getGroup(groupPosition).getData().get(childPosition));
                                    }

                                    @Override
                                    protected int getChildCount(SoonRoomEntity.ResultBean.BackBean group) {
                                        return group.getData().size();
                                    }
                                };
                        recyclerView.setAdapter(recyclerAdapter);

                        GroupItemDecoration decoration = new GroupItemDecoration(recyclerAdapter);
//                        decoration.setGroupDivider(ResourcesCompat.getDrawable(getResources(), R.drawable.divider_height_16_dp, null));
//                        decoration.setTitleDivider(ResourcesCompat.getDrawable(getResources(), R.drawable.divider_white_header, null));
                        decoration.setChildDivider(ResourcesCompat.getDrawable(getResources(), R.drawable.divider_white_header, null));
                        recyclerView.addItemDecoration(decoration);
                    } else {
                        netWorkFail(1);
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                cancleLoading();
                netWorkFail(1);
                if (volleyError != null && volleyError.getCause() != null) {
                    LogUtil.d("volleyError", volleyError.getCause().getMessage());
                }
            }
        });
        jacksonRequest.setRetryPolicy(new DefaultRetryPolicy(10 * 1000, 2, 1.0f));
        AiMiCrowdFundingApplication.context().getRequestQueue().add(jacksonRequest);
    }

    private void netWorkFail(int flag) {
        recyclerView.setVisibility(View.GONE);
        rlNoDataView.setVisibility(View.VISIBLE);
        if (flag == 0) {
            tvNoData.setText(getString(R.string.str_no_network));
            Toast.makeText(this, getString(R.string.str_no_network), Toast.LENGTH_SHORT).show();
            tvRefresh.setVisibility(View.VISIBLE);
        } else if (flag == 1) {
            tvNoData.setText(getString(R.string.str_no_service));
            tvRefresh.setVisibility(View.VISIBLE);
//            Toast.makeText(this, getString(R.string.str_no_service), Toast.LENGTH_SHORT).show();
        } else {
            tvNoData.setText(getString(R.string.str_no_data));
            tvRefresh.setVisibility(View.GONE);
        }
    }
}
