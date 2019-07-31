package com.nongjinsuo.mimijinfu.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.nongjinsuo.mimijinfu.activity.MessageActivity;
import com.nongjinsuo.mimijinfu.activity.NoticeActivity;
import com.nongjinsuo.mimijinfu.adapter.Project2Adapter;
import com.nongjinsuo.mimijinfu.util.TextUtil;
import com.pnikosis.materialishprogress.ProgressWheel;
import com.nongjinsuo.mimijinfu.R;
import com.nongjinsuo.mimijinfu.activity.IBase;
import com.nongjinsuo.mimijinfu.activity.LoginActivity;
import com.nongjinsuo.mimijinfu.activity.P2pDetilsActivity;
import com.nongjinsuo.mimijinfu.activity.ProjectBuyActivity;
import com.nongjinsuo.mimijinfu.activity.ProjectDetilsActivity;
import com.nongjinsuo.mimijinfu.activity.WebViewActivity;
import com.nongjinsuo.mimijinfu.activity.WebViewAndJsActivity;
import com.nongjinsuo.mimijinfu.adapter.P2pAdapter;
import com.nongjinsuo.mimijinfu.adapter.ProjectAdapter;
import com.nongjinsuo.mimijinfu.beans.BannerBoxVo;
import com.nongjinsuo.mimijinfu.beans.GuestVo;
import com.nongjinsuo.mimijinfu.beans.HomeIconVo;
import com.nongjinsuo.mimijinfu.beans.TrailerVo;
import com.nongjinsuo.mimijinfu.config.AiMiCrowdFundingApplication;
import com.nongjinsuo.mimijinfu.config.Constants;
import com.nongjinsuo.mimijinfu.config.SharedPreferenceUtil;
import com.nongjinsuo.mimijinfu.dao.http.JacksonRequest;
import com.nongjinsuo.mimijinfu.dao.http.RequestMap;
import com.nongjinsuo.mimijinfu.dao.http.Urls;
import com.nongjinsuo.mimijinfu.httpmodel.HomePageModel;
import com.nongjinsuo.mimijinfu.util.CountDownTask;
import com.nongjinsuo.mimijinfu.util.LogUtil;
import com.nongjinsuo.mimijinfu.util.NetworkUtils;
import com.nongjinsuo.mimijinfu.util.StatusBarUtil;
import com.nongjinsuo.mimijinfu.util.Util;
import com.nongjinsuo.mimijinfu.widget.EventBarEntity;
import com.nongjinsuo.mimijinfu.widget.MyListView;
import com.nongjinsuo.mimijinfu.widget.ObservableScrollView;
import com.nongjinsuo.mimijinfu.widget.VerticalSwitchTextView;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import de.greenrobot.event.EventBus;

/**
 * 首页
 */
@SuppressWarnings("unused")
public class HomePageFragment extends BaseFragment implements IBase, BGARefreshLayout.BGARefreshLayoutDelegate {
    @BindView(R.id.ivStatusBarTop)
    ImageView ivStatusBarTop;
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.listView)
    MyListView listView;
    @BindView(R.id.scroll)
    ObservableScrollView scroll;
    @BindView(R.id.swipeRefreshLayout)
    BGARefreshLayout swipeRefreshLayout;
    @BindView(R.id.progress_wheel)
    ProgressWheel progressWheel;
    @BindView(R.id.ivNoData)
    ImageView ivNoData;
    @BindView(R.id.tvNoData)
    TextView tvNoData;
    @BindView(R.id.rlNoDataView)
    RelativeLayout rlNoDataView;
    @BindView(R.id.ivStatusBar)
    ImageView ivStatusBar;
    @BindView(R.id.title_text)
    TextView titleText;
    @BindView(R.id.rlTitleBar)
    RelativeLayout rlTitleBar;
    @BindView(R.id.tvRefresh)
    TextView tvRefresh;
    @BindView(R.id.ivMessagePic)
    ImageView ivMessagePic;
    @BindView(R.id.ivMessageDian)
    ImageView ivMessageDian;
    @BindView(R.id.RlMessage)
    RelativeLayout RlMessage;
    @BindView(R.id.lvTztj)
    MyListView lvTztj;
    @BindView(R.id.tvMessage)
    VerticalSwitchTextView tvMessage;
    @BindView(R.id.llMessage)
    LinearLayout llMessage;
    @BindView(R.id.ivHomeIcon1)
    ImageView ivHomeIcon1;
    @BindView(R.id.ivHomeIcon2)
    ImageView ivHomeIcon2;
    @BindView(R.id.ivHomeIcon3)
    ImageView ivHomeIcon3;
    @BindView(R.id.ivHomeIcon4)
    ImageView ivHomeIcon4;
    @BindView(R.id.tvYqnhsy)
    TextView tvYqnhsy;
    @BindView(R.id.tvYqnhsyStr)
    TextView tvYqnhsyStr;
    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.tvDescr)
    TextView tvDescr;
    @BindView(R.id.tvQt)
    TextView tvQt;
    @BindView(R.id.tvFootViewNhsy)
    TextView tvFootViewNhsy;
    @BindView(R.id.tvMoreZc)
    TextView tvMoreZc;
    @BindView(R.id.ivBaoZhang)
    ImageView ivBaoZhang;
    @BindView(R.id.llXkzxView)
    LinearLayout llXkzxView;
    @BindView(R.id.llDtb)
    LinearLayout llDtb;
    private View inflate;
    private ArrayList<String> noticeList = new ArrayList<>();
    private List<String> images = new ArrayList<>();
    private int bannerHeight;

    public static HomePageFragment newInstance() {
        return new HomePageFragment();
    }

    public HomePageModel baseVos;
    private Project2Adapter adapter;
    private P2pAdapter p2pAdapter = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        inflate = inflater.inflate(R.layout.fragment_homepage, null);
        ButterKnife.bind(this, inflate);
        init();
        addListener();
        loadDate(true);
        getMessageNum();
        EventBus.getDefault().register(this);
        return inflate;
    }

    public void onEventMainThread(EventBarEntity event) {
        if (event.getType() == EventBarEntity.UPDATE_HOMEPAGE_MESSAGE) {
            getMessageNum();
        } else if (event.getType() == EventBarEntity.UPDATE_HOMEPAGE) {
            loadDate(false);
        } else if (event.getType() == EventBarEntity.UPDATE_HOMEPAGE_FIRST) {
            loadDate(true);
        }
    }

    private void getMessageNum() {
        JacksonRequest<HomePageModel> jacksonRequest = new JacksonRequest<>(RequestMap.getMessageNum(), HomePageModel.class, new Response.Listener<HomePageModel>() {
            @Override
            public void onResponse(HomePageModel baseVo) {
                if (baseVo.code.equals(Constants.SUCCESS)) {
                    if (baseVo.result.messageNum == 0) {
                        ivMessageDian.setVisibility(View.GONE);
                    } else {
                        ivMessageDian.setVisibility(View.VISIBLE);
                    }
                } else {
                    ivMessageDian.setVisibility(View.GONE);
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

    public void loadDate(boolean isFirst) {
        AiMiCrowdFundingApplication.context().cancelPendingRequests(getClass().getName());
        if (isFirst && !NetworkUtils.isNetworkAvailable(getContext())) {
            netWorkFail(0);
            return;
        }
        if (!isFirst && !NetworkUtils.isNetworkAvailable(getContext())) {
//            swipeRefreshLayout.setRefreshing(false);
            swipeRefreshLayout.endRefreshing();
//            Toast.makeText(getContext(), getString(R.string.str_no_network), Toast.LENGTH_SHORT).show();
            return;
        }
        if (isFirst && NetworkUtils.isNetworkAvailable(getContext())) {
            rlNoDataView.setVisibility(View.GONE);
            progressWheel.setVisibility(View.VISIBLE);
        }
        JacksonRequest<HomePageModel> jacksonRequest = new JacksonRequest<>(RequestMap.getHomefilefundMap(), HomePageModel.class, new Response.Listener<HomePageModel>() {
            @Override
            public void onResponse(HomePageModel baseVo) {
                progressWheel.setVisibility(View.GONE);
                swipeRefreshLayout.endRefreshing();
                if (baseVo.code.equals(Constants.SUCCESS)) {
                    swipeRefreshLayout.setVisibility(View.VISIBLE);
                    rlNoDataView.setVisibility(View.GONE);
                    baseVos = baseVo;

                    initBanner(baseVo.result.bannerBox);

                    inithomeIcon(baseVo.result.homeIcon);

                    setMessage(baseVo);

                    setGuest(baseVo.result.newGuest);

                    //投资推荐
                    if (baseVo.result.project == null || baseVo.result.project.size() == 0) {
                        llDtb.setVisibility(View.GONE);
                    } else {
                        llDtb.setVisibility(View.VISIBLE);
                        if (p2pAdapter == null) {
                            p2pAdapter = new P2pAdapter(getActivity(), baseVo.result.project);
                            lvTztj.setAdapter(p2pAdapter);
                        } else {
                            p2pAdapter.setInvestList(baseVo.result.project);
                        }
                    }

                    adapter.setCaseList(baseVo.result.car);
                    tvFootViewNhsy.setText(baseVo.result.carInterest);
                    //判断当前账户是否在其它设备登录
                    Util.loginPrompt(getActivity(), baseVo.result.loginState, baseVo.result.loginDescr);
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

    private CountDownTask mCountDownTask;

//    private void startCountDown() {
//        mCountDownTask = CountDownTask.create();
//        adapter.setCountDownTask(mCountDownTask);
//    }
//
//    private void cancelCountDown() {
//        adapter.setCountDownTask(null);
//        mCountDownTask.cancel();
//    }

    @Override
    public void onResume() {
        super.onResume();
//        startCountDown();
        startAuto();
//        loadDate(false);
    }

    @Override
    public void onPause() {
        super.onPause();
//        cancelCountDown();
        stopAuto();
    }

    /**
     * 设置新课专享
     *
     * @param newGuest
     */
    private void setGuest(GuestVo newGuest) {
        if (newGuest != null) {
            if (newGuest.getBm().equals("0")) {
                llXkzxView.setVisibility(View.GONE);
            } else {
                llXkzxView.setVisibility(View.VISIBLE);
                tvYqnhsy.setText(newGuest.getProjectRate());
                tvName.setText(newGuest.getName());
                tvDescr.setText(newGuest.getDescr());
            }
        } else {
            llXkzxView.setVisibility(View.GONE);
        }
    }

    /**
     * 设置公告消息
     *
     * @param baseVo
     */
    private void setMessage(HomePageModel baseVo) {
        if (baseVo.result.notice != null && baseVo.result.notice.size() > 0) {
            tvMessage.setVisibility(View.VISIBLE);
            llMessage.setVisibility(View.VISIBLE);
            noticeList.clear();
            for (int i = 0; i < baseVo.result.notice.size(); i++) {
                noticeList.add(baseVo.result.notice.get(i).getTitle());
            }
            tvMessage.setTextContent(noticeList);
        } else {
            llMessage.setVisibility(View.GONE);
        }
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

    /**
     * 设置首页四个按钮
     *
     * @param homeIcon
     */
    private void inithomeIcon(List<HomeIconVo> homeIcon) {
        if (homeIcon == null)
            return;
        if (homeIcon.size() < 4)
            return;
        Glide.with(getActivity()).load(Urls.PROJECT_URL + homeIcon.get(0).image).crossFade().into(ivHomeIcon1);
        Glide.with(getActivity()).load(Urls.PROJECT_URL + homeIcon.get(1).image).crossFade().into(ivHomeIcon2);
        Glide.with(getActivity()).load(Urls.PROJECT_URL + homeIcon.get(2).image).crossFade().into(ivHomeIcon3);
        Glide.with(getActivity()).load(Urls.PROJECT_URL + homeIcon.get(3).image).crossFade().into(ivHomeIcon4);

    }

    @Override
    public void init() {
        //设置banner的高度
        bannerHeight = (int) (Constants.WINDOW_WIDTH * 350.0 / 750);
        banner.setLayoutParams(new LinearLayout.LayoutParams(Constants.WINDOW_WIDTH, bannerHeight));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            ivStatusBar.setVisibility(View.VISIBLE);
            Util.setStatusBarHeight(getContext(), ivStatusBar);
        } else {
            ivStatusBar.setVisibility(View.GONE);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (Util.isMIUI()) {
                ivStatusBarTop.setVisibility(View.GONE);
            } else if (Util.isFlyme()) {
                ivStatusBarTop.setVisibility(View.GONE);
            } else {
                ivStatusBarTop.setVisibility(View.VISIBLE);
                Util.setStatusBarHeight(getContext(), ivStatusBarTop);
            }
        }
        listView.setFocusable(false);
        lvTztj.setFocusable(false);
//        swipeRefreshLayout.setColorSchemeResources(R.color.color_hot_sale_red);
        swipeRefreshLayout.setRefreshViewHolder(new BGANormalRefreshViewHolder(getContext(), true));
//募集项目
        adapter = new Project2Adapter(getActivity());
        listView.setAdapter(adapter);
//        startCountDown();
    }

    /**
     * 设置首页轮播
     *
     * @param bannerBox
     */
    private void initBanner(List<BannerBoxVo> bannerBox) {
        if (bannerBox == null || bannerBox.size() == 0) {
            banner.setVisibility(View.GONE);
            return;
        }
        images.clear();
        for (BannerBoxVo banner : bannerBox) {
            images.add(banner.image);
        }
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        banner.setImages(images);
        //设置banner动画效果
        banner.setBannerAnimation(Transformer.Default);
        //设置标题集合（当banner样式有显示title时）
//        banner.setBannerTitles(Arrays.asList(titles));
        //设置自动轮播，默认为true
        banner.isAutoPlay(true);
        //设置轮播时间
        banner.setDelayTime(3000);
        //设置指示器位置（当banner模式中有指示器时）
        banner.setIndicatorGravity(BannerConfig.CIRCLE_INDICATOR | BannerConfig.CIRCLE_INDICATOR);
        banner.start();
    }

    @Override
    public void addListener() {
        swipeRefreshLayout.setDelegate(this);
        scroll.setScrollViewListener(new ObservableScrollView.ScrollViewListener() {
            @Override
            public void onScrollChanged(ObservableScrollView scrollView, int x, int scrollY, int oldx, int oldy) {
                //      Log.i("TAG","y--->"+y+"    height-->"+height);
                if (scrollY > 0) {
                    if (scrollY <= bannerHeight) {
                        float scale = (float) scrollY / bannerHeight;
                        float alpha = (225 * scale);
                        if (alpha > 120) {
                            StatusBarUtil.setStatusBar(true, getActivity());
                            ivMessagePic.setImageResource(R.drawable.img_xxh);
                        } else {
                            ivMessagePic.setImageResource(R.drawable.img_xxb);
                            StatusBarUtil.setStatusBar(false, getActivity());
                        }
//                            if (alpha!=0){
//                                alpha-=50;
//                            }
                        titleText.setTextColor(Color.argb((int) (alpha), 14, 14, 14));
                        rlTitleBar.setBackgroundColor(Color.argb((int) (alpha), 255, 255, 255));
                        ivStatusBar.setBackgroundColor(Color.argb((int) (alpha), 255, 255, 255));
                    }
                } else {
                    titleText.setTextColor(Color.argb(0, 14, 14, 14));
                    rlTitleBar.setBackgroundColor(Color.argb(0, 255, 255, 255));
                    ivStatusBar.setBackgroundColor(Color.argb(0, 255, 255, 255));
                }
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
                    TrailerVo item = adapter.getItem(i);
                    Intent intent = new Intent(getActivity(), ProjectDetilsActivity.class);
                    intent.putExtra(Constants.PROJECTBM, item.getBm());
                    intent.putExtra(Constants.STATUS,item.getStatus());
                    startActivity(intent);
                    getActivity().overridePendingTransition(R.anim.activity_close_in_anim, R.anim.activity_close_out_anim);
                }
            }
        });
        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                if (!NetworkUtils.isNetworkAvailable(getContext())) {
                    Toast.makeText(getContext(), getString(R.string.str_no_network), Toast.LENGTH_SHORT).show();
                    return;
                }
                if (baseVos != null) {
                    BannerBoxVo bannerBoxVo = baseVos.result.bannerBox.get(position);
                    if (bannerBoxVo.classId.equals(Constants.PROJECT)) {
                        Intent intent = new Intent(getActivity(), ProjectDetilsActivity.class);
                        intent.putExtra(Constants.PROJECTBM, bannerBoxVo.projectBm);
                        startActivity(intent);
                        getActivity().overridePendingTransition(R.anim.activity_close_in_anim, R.anim.activity_close_out_anim);
                    } else if (bannerBoxVo.classId.equals(Constants.URL)) {
                        Intent intent = new Intent(getActivity(), WebViewActivity.class);
                        intent.putExtra(WebViewActivity.URL, bannerBoxVo.url);
                        startActivity(intent);
                    } else if (bannerBoxVo.classId.equals(Constants.INTER_URL)) {
                        Intent intent = new Intent(getActivity(), WebViewAndJsActivity.class);
                        intent.putExtra(WebViewAndJsActivity.URL, bannerBoxVo.url);
                        startActivity(intent);
                    }
                }
            }
        });
    }

    @OnClick({R.id.tvRefresh, R.id.tvMoreZc, R.id.ivBaoZhang, R.id.llXkzx, R.id.ivHomeIcon1, R.id.ivHomeIcon2, R.id.ivHomeIcon3, R.id.ivHomeIcon4, R.id.tvMessage, R.id.llLtb, R.id.RlMessage})
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.tvRefresh:
                loadDate(true);
                break;
            case R.id.tvMoreZc:
                EventBus.getDefault().post(new EventBarEntity(EventBarEntity.UPDATE_PROJECT_VIEW, 1));
                break;
            case R.id.ivBaoZhang:
                if (!NetworkUtils.isNetworkAvailable(getContext())) {
                    Toast.makeText(getContext(), getString(R.string.str_no_network), Toast.LENGTH_SHORT).show();
                    return;
                }
                intent = new Intent(getActivity(), WebViewActivity.class);
                intent.putExtra(WebViewActivity.URL, Urls.BAO_ZHANG);
                startActivity(intent);
                break;
            case R.id.llXkzx:
                if (!NetworkUtils.isNetworkAvailable(getContext())) {
                    Toast.makeText(getContext(), getString(R.string.str_no_network), Toast.LENGTH_SHORT).show();
                    return;
                }
                if (baseVos == null)
                    return;
                GuestVo guestVo1 = baseVos.result.newGuest;
                intent = new Intent(getActivity(), P2pDetilsActivity.class);
                intent.putExtra(Constants.PROJECTBM, guestVo1.getBm());
                intent.putExtra(Constants.STATUS, guestVo1.getStatus());
                intent.putExtra(Constants.ID, guestVo1.getId());
                startActivity(intent);
                break;
            case R.id.ivHomeIcon1:
                if (!NetworkUtils.isNetworkAvailable(getContext())) {
                    Toast.makeText(getContext(), getString(R.string.str_no_network), Toast.LENGTH_SHORT).show();
                    return;
                }
                if (baseVos == null)
                    return;
                HomeIconVo homeIconVo = baseVos.result.homeIcon.get(0);
                intent = new Intent(getActivity(), WebViewAndJsActivity.class);
                intent.putExtra(WebViewAndJsActivity.URL, homeIconVo.url);
                startActivity(intent);
                break;
            case R.id.ivHomeIcon2:
                if (!NetworkUtils.isNetworkAvailable(getContext())) {
                    Toast.makeText(getContext(), getString(R.string.str_no_network), Toast.LENGTH_SHORT).show();
                    return;
                }
                if (baseVos == null)
                    return;
                HomeIconVo homeIconVo1 = baseVos.result.homeIcon.get(1);
                intent = new Intent(getActivity(), WebViewAndJsActivity.class);
                intent.putExtra(WebViewAndJsActivity.URL, homeIconVo1.url);
                startActivity(intent);
                break;
            case R.id.ivHomeIcon3:
                if (!NetworkUtils.isNetworkAvailable(getContext())) {
                    Toast.makeText(getContext(), getString(R.string.str_no_network), Toast.LENGTH_SHORT).show();
                    return;
                }
                if (baseVos == null)
                    return;
                HomeIconVo homeIconVo2 = baseVos.result.homeIcon.get(2);
                intent = new Intent(getActivity(), WebViewAndJsActivity.class);
                intent.putExtra(WebViewAndJsActivity.URL, homeIconVo2.url);
                startActivity(intent);
                break;
            case R.id.ivHomeIcon4:
                if (!NetworkUtils.isNetworkAvailable(getContext())) {
                    Toast.makeText(getContext(), getString(R.string.str_no_network), Toast.LENGTH_SHORT).show();
                    return;
                }
                if (baseVos == null)
                    return;
                HomeIconVo homeIconVo3 = baseVos.result.homeIcon.get(3);
                intent = new Intent(getActivity(), WebViewAndJsActivity.class);
                intent.putExtra(WebViewAndJsActivity.URL, homeIconVo3.url);
                startActivity(intent);
                break;
            case R.id.tvMessage:
                if (!NetworkUtils.isNetworkAvailable(getContext())) {
                    Toast.makeText(getContext(), getString(R.string.str_no_network), Toast.LENGTH_SHORT).show();
                    return;
                }
                intent = new Intent(getActivity(), NoticeActivity.class);
                startActivity(intent);
                break;
            case R.id.llLtb:
                if (!NetworkUtils.isNetworkAvailable(getContext())) {
                    Toast.makeText(getContext(), getString(R.string.str_no_network), Toast.LENGTH_SHORT).show();
                    return;
                }
                intent = new Intent(getActivity(), WebViewAndJsActivity.class);
                intent.putExtra(WebViewAndJsActivity.URL, Urls.WHATCAR);
                startActivity(intent);
                break;
            case R.id.RlMessage:
                if (!NetworkUtils.isNetworkAvailable(getContext())) {
                    Toast.makeText(getContext(), getString(R.string.str_no_network), Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!SharedPreferenceUtil.isLogin(getActivity())) {
                    intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                    return;
                }
                intent = new Intent(getActivity(), MessageActivity.class);
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    public class GlideImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            /**
             常用的图片加载库：

             Universal Image Loader：一个强大的图片加载库，包含各种各样的配置，最老牌，使用也最广泛。
             Picasso: Square出品，必属精品。和OkHttp搭配起来更配呦！
             Volley ImageLoader：Google官方出品，可惜不能加载本地图片~
             Fresco：Facebook出的，天生骄傲！不是一般的强大。
             Glide：Google推荐的图片加载库，专注于流畅的滚动。
             */

            Glide.with(AiMiCrowdFundingApplication.context()).load(Urls.PROJECT_URL + path).placeholder(R.drawable.img_banner).crossFade().into(imageView);
        }
    }


    public void stopAuto() {
        if (banner != null) {
            banner.stopAutoPlay();
        }
    }

    public void startAuto() {
        if (banner != null) {
            banner.startAutoPlay();
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (adapter!=null){
            adapter.cancelAllTimers();
        }
        EventBus.getDefault().unregister(this);
        AiMiCrowdFundingApplication.context().cancelPendingRequests(getClass().getName());
    }
}