package com.nongjinsuo.mimijinfu.fragment;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.nongjinsuo.mimijinfu.R;
import com.nongjinsuo.mimijinfu.activity.IBase;
import com.nongjinsuo.mimijinfu.util.Util;
import com.nongjinsuo.mimijinfu.widget.EventBarEntity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import de.greenrobot.event.EventBus;

/**
 * description: (p2p和项目页面)
 * autour: czz
 * date: 2017/11/29 0029 上午 11:12
 * update: 2017/11/29 0029
 */

public class P2pAndProjectFragment extends BaseFragment implements IBase {
    @BindView(R.id.ivStatusBar)
    ImageView ivStatusBar;
    @BindView(R.id.rbP2PProject)
    RadioButton rbP2PProject;
    @BindView(R.id.rbZcProject)
    RadioButton rbZcProject;
    @BindView(R.id.rlTitleBar)
    RadioGroup rlTitleBar;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    Unbinder unbinder;
    private P2pAndProjectPagerAdapter p2pAndProjectPagerAdapter;

    public static P2pAndProjectFragment newInstance() {
        P2pAndProjectFragment p2pAndProjectFragment = new P2pAndProjectFragment();
        return p2pAndProjectFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_p2p_and_project, null);
        unbinder = ButterKnife.bind(this, view);
        init();
        addListener();
        EventBus.getDefault().register(this);
        return view;
    }

    public void onEventMainThread(EventBarEntity event) {
        if (event.getType() == EventBarEntity.UPDATE_PROJECT_VIEW) {
            viewPager.setCurrentItem(event.getUpdateType());
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
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
        p2pAndProjectPagerAdapter = new P2pAndProjectPagerAdapter(getFragmentManager());
        viewPager.setAdapter(p2pAndProjectPagerAdapter);
    }

    @Override
    public void addListener() {
        rlTitleBar.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if (checkedId == R.id.rbP2PProject) {
                    viewPager.setCurrentItem(0);
                } else {
                    viewPager.setCurrentItem(1);
                }
            }
        });
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    rbP2PProject.setChecked(true);
                } else {
                    rbZcProject.setChecked(true);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    /**
     * ViewPager的PagerAdapter
     */
    public class P2pAndProjectPagerAdapter extends FragmentPagerAdapter {
        Fragment[] fragments;

        public Fragment[] getFragments() {
            return fragments;
        }

        public void setFragments(Fragment[] fragments) {
            this.fragments = fragments;
        }

        public P2pAndProjectPagerAdapter(FragmentManager fm) {
            super(fm);
            fragments = new Fragment[]{P2PFragment.newInstance(), ProjectFragment.newInstance()};
        }

        @Override
        public Fragment getItem(int position) {
            return fragments[position];
        }


        @Override
        public int getCount() {
            return fragments.length;
        }


    }
}
