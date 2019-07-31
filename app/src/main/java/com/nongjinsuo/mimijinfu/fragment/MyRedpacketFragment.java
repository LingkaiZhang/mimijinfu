package com.nongjinsuo.mimijinfu.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.nongjinsuo.mimijinfu.activity.WebViewAndJsActivity;
import com.nongjinsuo.mimijinfu.dao.http.Urls;
import com.pnikosis.materialishprogress.ProgressWheel;
import com.nongjinsuo.mimijinfu.R;
import com.nongjinsuo.mimijinfu.activity.IBase;
import com.nongjinsuo.mimijinfu.activity.MyRedPacketActivity;
import com.nongjinsuo.mimijinfu.adapter.MyRedPacketAdapter;
import com.nongjinsuo.mimijinfu.beans.RedPacketBean;
import com.nongjinsuo.mimijinfu.beans.RedPacketModel;
import com.nongjinsuo.mimijinfu.config.AiMiCrowdFundingApplication;
import com.nongjinsuo.mimijinfu.config.Constants;
import com.nongjinsuo.mimijinfu.dao.http.JacksonRequest;
import com.nongjinsuo.mimijinfu.dao.http.RequestMap;
import com.nongjinsuo.mimijinfu.util.LogUtil;
import com.nongjinsuo.mimijinfu.util.NetworkUtils;
import com.nongjinsuo.mimijinfu.util.PxAndDip;
import com.nongjinsuo.mimijinfu.util.TextUtil;
import com.nongjinsuo.mimijinfu.widget.EventBarEntity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.greenrobot.event.EventBus;

/**
 * description: (描述)
 * autour: czz
 * date: 2017/9/25 0025 下午 1:21
 * update: 2017/9/25 0025
 */

public class MyRedpacketFragment extends BaseFragment implements IBase {
    @BindView(R.id.listView)
    ListView listView;
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
    Unbinder unbinder;
    private int type;
    public static final int MYHB = 0;//未使用红包
    public static final int YSYHB = 1;//已使用红包
    public static final int YGQHB = 2;//已过期红包
    private boolean isSelect = false;
    private String tzMoney;
    private MyRedPacketAdapter myRedPacketAdapter;
    private TextView tvKyhbNum;
    private RedPacketBean redPacketBean;
    private int redPacketBeanNum = 0;
    private ImageView ivBsyhb;
    private boolean bsyhbFlag;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            type = getArguments().getInt(Constants.TYPE);
            isSelect = getArguments().getBoolean(MyRedPacketActivity.SELECT_MYREDPACKET);
            bsyhbFlag = getArguments().getBoolean(MyRedPacketActivity.FLAG_BSYHB);
            redPacketBean = getArguments().getParcelable(MyRedPacketActivity.REDPACKETBEAN_ID);
            tzMoney = getArguments().getString(MyRedPacketActivity.TZ_MONEY);
        }
    }

    public static MyRedpacketFragment newInstance(int type) {
        MyRedpacketFragment myRedpacketFragment = new MyRedpacketFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.TYPE, type);
        myRedpacketFragment.setArguments(bundle);
        return myRedpacketFragment;
    }

    public static MyRedpacketFragment newInstance(int type, boolean isSelect, RedPacketBean redpackeId, String tzMoney, boolean bsyhbFlag) {
        MyRedpacketFragment myRedpacketFragment = new MyRedpacketFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.TYPE, type);
        bundle.putParcelable(MyRedPacketActivity.REDPACKETBEAN_ID, redpackeId);
        bundle.putBoolean(MyRedPacketActivity.SELECT_MYREDPACKET, isSelect);
        bundle.putBoolean(MyRedPacketActivity.FLAG_BSYHB, bsyhbFlag);
        bundle.putString(MyRedPacketActivity.TZ_MONEY, tzMoney);
        myRedpacketFragment.setArguments(bundle);
        return myRedpacketFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_myredpacket, null);
        unbinder = ButterKnife.bind(this, view);
        init();
        addListener();
        return view;
    }

    @Override
    public void init() {
        if (!isSelect) {
            View view = new View(getActivity());
            view.setLayoutParams(new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, PxAndDip.dip2px(getActivity(), 10)));
            listView.addHeaderView(view);
            loadDate(true);
        } else {
            progressWheel.setVisibility(View.GONE);
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.headview_bxhb, null);
            listView.addHeaderView(view);
            view.findViewById(R.id.llSelectBsy).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getActivity().finish();
                    EventBus.getDefault().post(new EventBarEntity(EventBarEntity.SELECT_BSYHB));
                    if (myRedPacketAdapter != null) {
                        myRedPacketAdapter.redpackeId = "-";
                        myRedPacketAdapter.notifyDataSetChanged();
                    }
                    ivBsyhb.setImageResource(R.drawable.icon_yxz);
                }
            });
            tvKyhbNum = (TextView) view.findViewById(R.id.tvKyhbNum);
            ivBsyhb = (ImageView) view.findViewById(R.id.ivBsyhb);
            view.findViewById(R.id.tvHbsm).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), WebViewAndJsActivity.class);
                    intent.putExtra(WebViewAndJsActivity.URL, Urls.REDPACKET);
                    startActivity(intent);
                }
            });
            ArrayList<RedPacketBean> list = new ArrayList<>();
            list.addAll(Constants.redPacketBeens);
            String redPacketBeanId = "";
            if (bsyhbFlag) {
                ivBsyhb.setImageResource(R.drawable.icon_yxz);
            } else {
                ivBsyhb.setImageResource(R.drawable.icon_yxza);
            }
            if (redPacketBean != null) {
                redPacketBeanId = redPacketBean.getId();
                for (int i = 0;i<list.size();i++){
                    if (list.get(i).getId().equals(redPacketBeanId)){
                        list.remove(list.get(i));
                    }
                }
                list.add(0, redPacketBean);
            }
            if (Constants.redPacketBeens != null) {
                for (int i = 0; i < list.size(); i++) {
                    RedPacketBean packetBean = list.get(i);
                    double inputMoney = 0;
                    if (TextUtil.IsNotEmpty(tzMoney))
                        inputMoney = Double.parseDouble(tzMoney);
                    double xyMoney = Double.parseDouble(packetBean.getMinUse());
                    if (inputMoney >= xyMoney) {
                        redPacketBeanNum++;
                    }
                }
                myRedPacketAdapter = new MyRedPacketAdapter(getActivity(), type, list, isSelect, redPacketBeanId, tzMoney);
                listView.setAdapter(myRedPacketAdapter);
                tvKyhbNum.setText("有" + redPacketBeanNum + "个红包可用");
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void addListener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (isSelect) {
                    if (position > 0) {
                        RedPacketBean item = myRedPacketAdapter.getItem(position - 1);
                        double kyMoney = Double.parseDouble(item.getMinUse());//限制金额
                        double tzMoney1 = 0;
                        if (TextUtil.IsNotEmpty(tzMoney))
                            tzMoney1 = Double.parseDouble(tzMoney);//投资金额
                        if (tzMoney1 >= kyMoney) {
                            getActivity().finish();
                            myRedPacketAdapter.redpackeId = item.getId();
                            myRedPacketAdapter.notifyDataSetChanged();
                            EventBus.getDefault().post(new EventBarEntity(EventBarEntity.SELECT_HB, item));
                        } else {
                            Toast.makeText(getActivity(), "投资金额小于红包限额", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.tvRefresh)
    public void onViewClicked() {
    }


    private void netWorkFail(int flag) {
        progressWheel.setVisibility(View.GONE);
        rlNoDataView.setVisibility(View.VISIBLE);
        ivNoData.setImageResource(R.drawable.img_wwl);
        if (flag == 0) {
            tvNoData.setText(getString(R.string.str_no_network));
            Toast.makeText(getActivity(), getString(R.string.str_no_network), Toast.LENGTH_SHORT).show();
        } else {
            tvNoData.setText(getString(R.string.str_no_service));
            Toast.makeText(getActivity(), getString(R.string.str_no_service), Toast.LENGTH_SHORT).show();
        }
        tvRefresh.setVisibility(View.VISIBLE);
    }

    private void loadDate(boolean isFirst) {
        if (isFirst && !NetworkUtils.isNetworkAvailable(getActivity())) {
            netWorkFail(0);
            return;
        }
        if (!isFirst && !NetworkUtils.isNetworkAvailable(getActivity())) {
            Toast.makeText(getActivity(), getString(R.string.str_no_network), Toast.LENGTH_SHORT).show();
            return;
        }
        if (isFirst && NetworkUtils.isNetworkAvailable(getActivity())) {
            rlNoDataView.setVisibility(View.GONE);
            progressWheel.setVisibility(View.VISIBLE);
        }

        JacksonRequest<RedPacketModel> jacksonRequest = new JacksonRequest<>(RequestMap.redPacket(type), RedPacketModel.class, new Response.Listener<RedPacketModel>() {
            @Override
            public void onResponse(RedPacketModel baseVo) {
                progressWheel.setVisibility(View.GONE);
                if (baseVo != null) {
                    List<RedPacketBean> list = baseVo.getResult().getRedPacket();
                    if (baseVo.getCode().equals(Constants.SUCCESS)) {
                        if (list != null && list.size() > 0) {
                            myRedPacketAdapter = new MyRedPacketAdapter(getActivity(), type, list);
                            listView.setAdapter(myRedPacketAdapter);
//                            listView.addFooterView(view);
                        } else {
                            rlNoDataView.setVisibility(View.VISIBLE);
                            ivNoData.setImageResource(R.drawable.img_wnr_49);
                            tvNoData.setText(getString(R.string.str_no_data));
                            tvRefresh.setVisibility(View.GONE);
                        }
                    } else {
                        netWorkFail(1);
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                netWorkFail(1);
                if (volleyError != null && volleyError.getCause() != null) {
                    LogUtil.d("volleyError", volleyError.getCause().getMessage());
                }
            }
        });
        jacksonRequest.setRetryPolicy(new DefaultRetryPolicy(10 * 1000, 2, 1.0f));
        AiMiCrowdFundingApplication.context().addToRequestQueue(jacksonRequest, getClass().getName());
    }

}
