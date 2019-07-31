package com.nongjinsuo.mimijinfu.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.flyco.tablayout.SlidingTabLayout;
import com.nongjinsuo.mimijinfu.R;
import com.nongjinsuo.mimijinfu.config.Constants;
import com.nongjinsuo.mimijinfu.fragment.TradingRecordFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author czz
 * @Description: (交易记录)
 */
public class TansactionRecordActivity extends AbstractActivity {


    @BindView(R.id.tabs)
    SlidingTabLayout tabs;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    private MinePagerAdapter minePagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tansaction_record);
        ButterKnife.bind(this);
        init();
        addListener();
    }

    @Override
    public void init() {
        titleView.setText("交易记录");
        minePagerAdapter = new MinePagerAdapter(getSupportFragmentManager());
        viewPager.setOffscreenPageLimit(Constants.ZC_TYPE.length);
        viewPager.setAdapter(minePagerAdapter);
        tabs.setViewPager(viewPager, Constants.ZC_TYPE);
        viewPager.setCurrentItem(0);
    }

    @Override
    public void addListener() {

    }

    /**
     * ViewPager的PagerAdapter
     */
    public class MinePagerAdapter extends FragmentPagerAdapter {
        Fragment[] fragments;

        public MinePagerAdapter(FragmentManager fm) {
            super(fm);
            fragments = new Fragment[Constants.CLASS_ID.length];
            for (int i = 0;i<Constants.CLASS_ID.length;i++){
                fragments[i] = TradingRecordFragment.newInstance(Constants.CLASS_ID[i]);
            }
        }

        @Override
        public Fragment getItem(int position) {
            return fragments[position];
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return Constants.ZC_TYPE[position];
        }

        @Override
        public int getCount() {
            return fragments.length;
        }
    }

}
