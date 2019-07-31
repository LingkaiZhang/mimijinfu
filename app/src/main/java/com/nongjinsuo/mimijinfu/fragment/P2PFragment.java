package com.nongjinsuo.mimijinfu.fragment;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.pnikosis.materialishprogress.ProgressWheel;
import com.nongjinsuo.mimijinfu.R;
import com.nongjinsuo.mimijinfu.activity.IBase;
import com.nongjinsuo.mimijinfu.adapter.P2pAdapter;
import com.nongjinsuo.mimijinfu.beans.InvestVo;
import com.nongjinsuo.mimijinfu.config.AiMiCrowdFundingApplication;
import com.nongjinsuo.mimijinfu.config.Constants;
import com.nongjinsuo.mimijinfu.dao.http.JacksonRequest;
import com.nongjinsuo.mimijinfu.dao.http.RequestMap;
import com.nongjinsuo.mimijinfu.httpmodel.InvestModel;
import com.nongjinsuo.mimijinfu.util.LogUtil;
import com.nongjinsuo.mimijinfu.util.NetworkUtils;
import com.nongjinsuo.mimijinfu.util.PxAndDip;
import com.nongjinsuo.mimijinfu.util.Util;
import com.nongjinsuo.mimijinfu.util.ViewHolder;
import com.nongjinsuo.mimijinfu.widget.EventBarEntity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import de.greenrobot.event.EventBus;


/**
 * p2p项目
 */

public class P2PFragment extends Fragment implements IBase, BGARefreshLayout.BGARefreshLayoutDelegate {
    @BindView(R.id.listView)
    ListView listView;
    @BindView(R.id.progress_wheel)
    ProgressWheel progressWheel;
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
    @BindView(R.id.llFilterView)
    LinearLayout llFilterView;
    @BindView(R.id.tvHkfs)
    TextView tvHkfs;
    @BindView(R.id.ivHkfsJt)
    ImageView ivHkfsJt;
    @BindView(R.id.llHkfs)
    LinearLayout llHkfs;
    @BindView(R.id.tvHkqx)
    TextView tvHkqx;
    @BindView(R.id.ivHkqxJt)
    ImageView ivHkqxJt;
    @BindView(R.id.llHkqx)
    LinearLayout llHkqx;
    private int status = 0;
    private int classId = 0;
    private P2pAdapter adapter;
    private int pageNum = 0;
    private int projectSwitchFlagNum = 0;
    public PopupWindow allStatusPopupWindow;
    public PopupWindow zhtjPopupWindow;
    public InvestModel projectListV2Model;
    private ShaiXuanAdapter shaiXuanAdapter;

    private boolean flagHkfs = true;
    private boolean flagHkqx = true;

    public static P2PFragment newInstance() {
        return new P2PFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_p2p, null);
        ButterKnife.bind(this, inflate);
        init();
        addListener();
        loadDate(true, true);
        EventBus.getDefault().register(this);
        return inflate;
    }

    public void onEventMainThread(EventBarEntity event) {
        if (event.getType() == EventBarEntity.LOGIN_UPDATE_WEB) {
            loadDate(true, true);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        AiMiCrowdFundingApplication.context().cancelPendingRequests(getClass().getName());
    }

    @Override
    public void init() {
        adapter = new P2pAdapter(getActivity());
        swipeRefreshLayout.setRefreshViewHolder(new BGANormalRefreshViewHolder(getContext(), true));
    }

    @OnClick(R.id.tvRefresh)
    public void onClick() {
        loadDate(true, true);
    }

    private void loadDate(boolean isRefresh, boolean isFirst) {
        if (isFirst && !NetworkUtils.isNetworkAvailable(getContext())) {
            netWorkFail(0);
            return;
        }
        if (!isFirst && !NetworkUtils.isNetworkAvailable(getContext())) {
            swipeRefreshLayout.endRefreshing();
            Toast.makeText(getContext(), getString(R.string.str_no_network), Toast.LENGTH_SHORT).show();
            return;
        }
        if (isFirst && NetworkUtils.isNetworkAvailable(getContext())) {
            rlNoDataView.setVisibility(View.GONE);
            progressWheel.setVisibility(View.VISIBLE);
        }
        if (isRefresh) {
            swipeRefreshLayout.setIsShowLoadingMoreView(true);
            pageNum = 0;
        } else {
            pageNum++;
        }
        JacksonRequest<InvestModel> jacksonRequest = new JacksonRequest<>(RequestMap.listProduct(pageNum, status, classId), InvestModel.class, new Response.Listener<InvestModel>() {
            @Override
            public void onResponse(InvestModel baseVo) {
                projectListV2Model = baseVo;
                progressWheel.setVisibility(View.GONE);
                swipeRefreshLayout.endRefreshing();
                swipeRefreshLayout.endLoadingMore();
                if (baseVo != null) {
                    if (baseVo.code.equals(Constants.SUCCESS)) {
                        if (baseVo.result.getRegular().size() == 0) {
                            if (pageNum == 0){
                                swipeRefreshLayout.setVisibility(View.GONE);
                                rlNoDataView.setVisibility(View.VISIBLE);
                                ivNoData.setImageResource(R.drawable.img_wnr_49);
                                tvNoData.setText(getString(R.string.str_no_data));
                                tvRefresh.setVisibility(View.GONE);
                            }else{
                                swipeRefreshLayout.setIsShowLoadingMoreView(false);
                            }
                        } else {
                            swipeRefreshLayout.setVisibility(View.VISIBLE);
                            if (pageNum == 0) {
                                setData(baseVo.result.getRegular());
                                if (baseVo.result.getRegular().size() < 10) {
                                    swipeRefreshLayout.setIsShowLoadingMoreView(false);
                                }
                            } else {
                                adapter.setMoreInvestList(baseVo.result.getRegular());
                            }
                        }
                        //判断当前账户是否在其它设备登录
//                        Util.loginPrompt(getActivity(), baseVo.result.loginPrompt);
                    } else {
                        if (pageNum == 0) {
                            swipeRefreshLayout.setVisibility(View.GONE);
                            rlNoDataView.setVisibility(View.VISIBLE);
                            ivNoData.setImageResource(R.drawable.img_wnr_49);
                            tvNoData.setText(getActivity().getString(R.string.str_no_data));
                            tvRefresh.setVisibility(View.GONE);
                        } else {
                            Toast.makeText(getContext(), "加载完成", Toast.LENGTH_SHORT).show();
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
            Toast.makeText(getContext(), getString(R.string.str_no_network), Toast.LENGTH_SHORT).show();
        } else {
            tvNoData.setText(getString(R.string.str_no_service));
//            Toast.makeText(getContext(), getString(R.string.str_no_service), Toast.LENGTH_SHORT).show();
        }
        tvRefresh.setVisibility(View.VISIBLE);
    }

    private void setData(List<InvestVo> result) {
        if (adapter.getInvestList() != null) {
            adapter.getInvestList().removeAll(adapter.getInvestList());
        }
        adapter.setInvestList(result);
        listView.setAdapter(adapter);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void addListener() {
        llHkfs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hkfsJt(flagHkfs);
            }
        });
        llHkqx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hkqxJt(flagHkqx);
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (!NetworkUtils.isNetworkAvailable(getContext())) {
                    Toast.makeText(getContext(), getString(R.string.str_no_network), Toast.LENGTH_SHORT).show();
                    return;
                }
                if (adapter != null) {
//                    TrailerVo item = adapter.getItem(i);
//                    Intent intent = new Intent(getActivity(), ProjectDetilsActivity.class);
//                    intent.putExtra(Constants.PROJECTBM, item.getBm());
//                    startActivity(intent);
//                    getActivity().overridePendingTransition(R.anim.activity_close_in_anim, R.anim.activity_close_out_anim);
                }
            }
        });
        swipeRefreshLayout.setDelegate(this);
    }

    /**
     * 回款方式打开或关闭
     *
     * @param bool
     */
    public void hkfsJt(boolean bool) {
        if (bool) {
            ivHkfsJt.setImageResource(R.drawable.icon_jb);
            flagHkfs = false;
            showAllStatusPopupWindow();
        } else {
            flagHkfs = true;
            ivHkfsJt.setImageResource(R.drawable.icon_ja);
            if (allStatusPopupWindow!=null)
            allStatusPopupWindow.dismiss();
        }
    }

    /**
     * 回款期限打开或关闭
     *
     * @param bool
     */
    public void hkqxJt(boolean bool) {
        if (bool) {
            ivHkqxJt.setImageResource(R.drawable.icon_jb);
            flagHkqx = false;
            showZhtjPopupWindow();
        } else {
            flagHkqx = true;
            ivHkqxJt.setImageResource(R.drawable.icon_ja);
            if (zhtjPopupWindow!=null)
            zhtjPopupWindow.dismiss();
        }
    }

    private void showAllStatusPopupWindow() {
        if (allStatusPopupWindow != null) {
            allStatusPopupWindow.showAsDropDown(llFilterView, 0, 2);
            return;
        }
        /* 一个自定义的布局，作为显示的内容 */
        View contentView = LayoutInflater.from(getContext()).inflate(
                R.layout.popwindow_project_shaixuan, null);
        // 设置按钮的点击事件
        ListView listView = (ListView) contentView.findViewById(R.id.listView);
        shaiXuanAdapter = new ShaiXuanAdapter(Constants.HKFS);
        shaiXuanAdapter.setSelect(projectSwitchFlagNum);
        listView.setAdapter(shaiXuanAdapter);
        allStatusPopupWindow = new PopupWindow(contentView,
                Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT - PxAndDip.dip2px(getActivity(), 84f) - Util.getStatusBarHeight(getContext()), true);
        allStatusPopupWindow.setFocusable(true);
        // 控制popupwindow的宽度和高度自适应
//        listView.measure(View.MeasureSpec.UNSPECIFIED,
//                View.MeasureSpec.UNSPECIFIED);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                status = i;
                shaiXuanAdapter.setSelect(i);
                if (i == 0) {
                    tvHkfs.setText("回款方式");
                } else {
                    tvHkfs.setText(Constants.HKFS[i]);
                }
                hkfsJt(false);
                swipeRefreshLayout.setIsShowLoadingMoreView(true);
                loadDate(true, true);
                allStatusPopupWindow.dismiss();
            }
        });
        View viewBg = contentView.findViewById(R.id.viewBg);
        viewBg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                allStatusPopupWindow.dismiss();
            }
        });
        allStatusPopupWindow.setTouchable(true);
        allStatusPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                hkqxJt(false);
                hkfsJt(false);
            }
        });
        allStatusPopupWindow.setTouchInterceptor(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                return false;
                // 这里如果返回true的话，touch事件将被拦截
                // 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
            }
        });

        // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
        // 我觉得这里是API的一个bug
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0);
        // 设置SelectPicPopupWindow弹出窗体的背景
        allStatusPopupWindow.setBackgroundDrawable(dw);
        // 设置好参数之后再show
        allStatusPopupWindow.showAsDropDown(llFilterView, 0, 2);
    }

    private void showZhtjPopupWindow() {
        if (zhtjPopupWindow != null) {
            zhtjPopupWindow.showAsDropDown(llFilterView, 0, 2);
            return;
        }
        // 一个自定义的布局，作为显示的内容
        View contentView = LayoutInflater.from(getContext()).inflate(
                R.layout.popwindow_project_shaixuan, null);
        // 设置按钮的点击事件
        ListView listView = (ListView) contentView.findViewById(R.id.listView);
        final ShaiXuanAdapter shaiXuanAdapter = new ShaiXuanAdapter(Constants.HKQX);
        listView.setAdapter(shaiXuanAdapter);
        zhtjPopupWindow = new PopupWindow(contentView,
                Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT - PxAndDip.dip2px(getActivity(), 84f) - Util.getStatusBarHeight(getContext()), true);
        zhtjPopupWindow.setFocusable(true);
        // 控制popupwindow的宽度和高度自适应
//        listView.measure(View.MeasureSpec.UNSPECIFIED,
//                View.MeasureSpec.UNSPECIFIED);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                shaiXuanAdapter.setSelect(i);
                if (i == 0) {
                    tvHkqx.setText("回款期限");
                } else {
                    tvHkqx.setText(Constants.HKQX[i]);
                }
                classId = Constants.HKQX_NUM[i];
                swipeRefreshLayout.setIsShowLoadingMoreView(true);
                loadDate(true, true);
                zhtjPopupWindow.dismiss();
            }
        });
        contentView.findViewById(R.id.viewBg).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                zhtjPopupWindow.dismiss();
            }
        });
        zhtjPopupWindow.setTouchable(true);
        zhtjPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                hkqxJt(false);
                hkfsJt(false);
            }
        });
        zhtjPopupWindow.setTouchInterceptor(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                return false;
                // 这里如果返回true的话，touch事件将被拦截
                // 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
            }
        });

        // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
        // 我觉得这里是API的一个bug
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0);
        // 设置SelectPicPopupWindow弹出窗体的背景
        zhtjPopupWindow.setBackgroundDrawable(dw);
        // 设置好参数之后再show
        zhtjPopupWindow.showAsDropDown(llFilterView, 0, 2);
    }


    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
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
    public void onDestroyView() {
        super.onDestroyView();
    }

    private class ShaiXuanAdapter extends BaseAdapter {
        private String[] str;
        private int select;

        public void setSelect(int select) {
            this.select = select;
        }

        public ShaiXuanAdapter(String[] str) {
            this.str = str;
        }

        @Override
        public int getCount() {
            return str.length;
        }

        @Override
        public String getItem(int i) {
            return str[i];
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if (view == null) {
                view = LayoutInflater.from(getActivity()).inflate(R.layout.item_shaixuan, null);
            }
            TextView tvName = ViewHolder.get(view, R.id.tvName);
            ImageView viewLine = ViewHolder.get(view, R.id.viewLine);
            tvName.setText(getItem(i));
            if (select == i) {
                tvName.setTextColor(getResources().getColor(R.color.color_login_btn));
            } else {
                tvName.setTextColor(getResources().getColor(R.color.color_homepage_quanbu));
            }

            if (i == getCount() - 1) {
                viewLine.setVisibility(View.GONE);
            } else {
                viewLine.setVisibility(View.VISIBLE);
            }
            return view;
        }
    }
}
