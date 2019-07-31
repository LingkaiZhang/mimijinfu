package com.nongjinsuo.mimijinfu.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
import com.nongjinsuo.mimijinfu.activity.IBase;
import com.nongjinsuo.mimijinfu.activity.MyP2pTouZiActivity;
import com.nongjinsuo.mimijinfu.activity.MyP2pTouZiDetailsActivity;
import com.nongjinsuo.mimijinfu.adapter.MyP2pTouziAdapter;
import com.nongjinsuo.mimijinfu.config.AiMiCrowdFundingApplication;
import com.nongjinsuo.mimijinfu.config.Constants;
import com.nongjinsuo.mimijinfu.dao.http.JacksonRequest;
import com.nongjinsuo.mimijinfu.dao.http.RequestMap;
import com.nongjinsuo.mimijinfu.httpmodel.MyP2pTouZiModel;
import com.nongjinsuo.mimijinfu.util.LogUtil;
import com.nongjinsuo.mimijinfu.util.NetworkUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * @author czz
 * @Description: (我的p2p投资)
 */
public class MyP2pTouZiFragment extends Fragment implements IBase, BGARefreshLayout.BGARefreshLayoutDelegate {
    @BindView(R.id.tvCyxm)
    TextView tvCyxm;
    @BindView(R.id.tvCyje)
    TextView tvCyje;
    @BindView(R.id.listView)
    ListView listView;
    @BindView(R.id.progress_wheel)
    ProgressWheel progressWheel;
    @BindView(R.id.ivNoData)
    ImageView ivNoData;
    @BindView(R.id.tvNoData)
    TextView tvNoData;
    @BindView(R.id.rlNoDataView)
    RelativeLayout rlNoDataView;
    @BindView(R.id.tvRefresh)
    TextView tvRefresh;
    @BindView(R.id.llTopView)
    RelativeLayout llTopView;
    @BindView(R.id.swipeRefreshLayout)
    BGARefreshLayout swipeRefreshLayout;
    private View inflate;
    public static final String BACKSTATUS = "backStatus";
    private int backStatus;
    private MyP2pTouziAdapter myCrowdFundingAdapter;
    private int page = 0;
    private String[] shouyiValue;

    public static MyP2pTouZiFragment newInstance(int backStatus, String[] shouyiValue) {
        MyP2pTouZiFragment myCrowdFundingFragment = new MyP2pTouZiFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(BACKSTATUS, backStatus);
        bundle.putStringArray(MyP2pTouZiActivity.SHOUYI, shouyiValue);
        myCrowdFundingFragment.setArguments(bundle);
        return myCrowdFundingFragment;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            backStatus = getArguments().getInt(BACKSTATUS);
            shouyiValue = getArguments().getStringArray(MyP2pTouZiActivity.SHOUYI);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        inflate = inflater.inflate(R.layout.fragment_mycrowdfunding, null);
        ButterKnife.bind(this, inflate);
        init();
        addListener();
        return inflate;
    }

    @Override
    public void init() {
        loadDate(true, true);

        swipeRefreshLayout.setRefreshViewHolder(new BGANormalRefreshViewHolder(getContext(), true));
        swipeRefreshLayout.setDelegate(this);
        swipeRefreshLayout.setPullDownRefreshEnable(true);

    }

    @Override
    public void addListener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (myCrowdFundingAdapter != null) {
                    Intent intent = new Intent(getActivity(), MyP2pTouZiDetailsActivity.class);
                    MyP2pTouZiModel.ResultBean.UserprojectBean userprojectBean = myCrowdFundingAdapter.getItem(i);
                    intent.putExtra(MyP2pTouZiDetailsActivity.USERPROJECTBEAN, userprojectBean);
                    startActivity(intent);
                }
            }
        });
    }

    private void loadDate(boolean isRefresh, boolean isFirst) {
        if (!NetworkUtils.isNetworkAvailable(getActivity())) {
            progressWheel.setVisibility(View.GONE);
            rlNoDataView.setVisibility(View.VISIBLE);
            ivNoData.setImageResource(R.drawable.img_wwl);
            tvNoData.setText(getString(R.string.str_no_network));
            tvRefresh.setVisibility(View.VISIBLE);
            Toast.makeText(getContext(), getString(R.string.str_no_network), Toast.LENGTH_SHORT).show();
            return;
        }
        if (isFirst)
            progressWheel.setVisibility(View.VISIBLE);
        if (isRefresh) {
            page = 0;
        } else {
            page++;
        }
        rlNoDataView.setVisibility(View.GONE);
        JacksonRequest<MyP2pTouZiModel> jacksonRequest = new JacksonRequest<>(RequestMap.userNetloan(page, backStatus), MyP2pTouZiModel.class, new Response.Listener<MyP2pTouZiModel>() {
            @Override
            public void onResponse(MyP2pTouZiModel baseVo) {
                progressWheel.setVisibility(View.GONE);
                swipeRefreshLayout.endRefreshing();
                swipeRefreshLayout.endLoadingMore();
                if (baseVo.getCode().equals(Constants.SUCCESS)) {
                    rlNoDataView.setVisibility(View.GONE);
                    if (page == 0) {
                        llTopView.setVisibility(View.VISIBLE);
                        myCrowdFundingAdapter = new MyP2pTouziAdapter(getActivity(), baseVo.getResult().getUserproject());
                        listView.setAdapter(myCrowdFundingAdapter);
                        MyP2pTouZiModel.ResultBean resultBean = baseVo.getResult();
                        if (backStatus == 0) {
                            tvCyxm.setText("持有资金: " + resultBean.getInvestMoney() + resultBean.getInvestUnit());
                            tvCyje.setText("待收收益: " + shouyiValue[1] + "元");
                        } else {
                            tvCyxm.setText("累计投资: " + resultBean.getInvestMoney() + resultBean.getInvestUnit());
                            tvCyje.setText("累计收益: " + resultBean.getBackInterestMoney() + resultBean.getBackInterestUnit());
                        }
                        if (baseVo.getResult().getUserproject().size() > 0 && baseVo.getResult().getUserproject().size() < 10) {
                            swipeRefreshLayout.setIsShowLoadingMoreView(false);
                        } else if (baseVo.getResult().getUserproject().size() == 0) {
                            listView.setVisibility(View.GONE);
                            rlNoDataView.setVisibility(View.VISIBLE);
                            ivNoData.setImageResource(R.drawable.img_wnr_49);
                            tvNoData.setText(getString(R.string.str_no_data));
                            tvRefresh.setVisibility(View.GONE);
                            llTopView.setVisibility(View.GONE);
                        }
                    } else {
                        if (myCrowdFundingAdapter != null) {
                            myCrowdFundingAdapter.setMoreCaseList(baseVo.getResult().getUserproject());
                        }
                    }
                } else {
                    if (page == 0) {
                        listView.setVisibility(View.GONE);
                        rlNoDataView.setVisibility(View.VISIBLE);
                        ivNoData.setImageResource(R.drawable.img_wnr_49);
                        tvNoData.setText(getString(R.string.str_no_data));
                        tvRefresh.setVisibility(View.GONE);
                        llTopView.setVisibility(View.GONE);
                    } else {
                        Toast.makeText(getActivity(), "加载完成", Toast.LENGTH_SHORT).show();
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

    @OnClick(R.id.tvRefresh)
    public void onClick() {
        loadDate(true, true);
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        swipeRefreshLayout.setIsShowLoadingMoreView(true);
        loadDate(true, false);
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        if (!swipeRefreshLayout.ismIsShowLoadingMoreView()) {
            return false;
        }
        loadDate(false, false);
        return true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        AiMiCrowdFundingApplication.context().cancelPendingRequests(getClass().getName());
    }
}
