package com.nongjinsuo.mimijinfu.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.nongjinsuo.mimijinfu.R;
import com.nongjinsuo.mimijinfu.config.SharedPreferenceUtil;
import com.nongjinsuo.mimijinfu.util.LogUtil;
import com.nongjinsuo.mimijinfu.util.update.UpdateVersionService;

import java.util.ArrayList;
import java.util.List;


public class IndexGuideActivity extends FragmentActivity {

    private ViewPager viewPager;
    private int pageSelected;
    private int num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indexguide);

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setAdapter(new ViewAdapter());
        viewPager.setOnPageChangeListener(new OnPageChangeListener() {

            @Override
            public void onPageSelected(int arg0) {
                pageSelected = arg0;
                LogUtil.d("onPageSelected--" + arg0 + "");
                num = 0;
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
                LogUtil.d("onPageScrolled--" + arg0 + "--" + arg2);
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
                LogUtil.d("onPageScrollStateChanged--" + arg0 + "");
                if (pageSelected == 2 && arg0 == 0) {
                    if (num == 1) {
//						toMainActivity();
                    }
                    num++;
                }
            }

        });
    }

    public void toMainActivity() {
        Intent intent = new Intent();
        intent.setClass(IndexGuideActivity.this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.activity_close_in_anim, R.anim.activity_close_out_anim);
        finish();
    }

    class ViewAdapter extends PagerAdapter {
        List<View> list = new ArrayList<>();

        public ViewAdapter() {

            list.add(LayoutInflater.from(IndexGuideActivity.this).inflate(R.layout.layout_index1, null));
            list.add(LayoutInflater.from(IndexGuideActivity.this).inflate(R.layout.layout_index2, null));
            list.add(LayoutInflater.from(IndexGuideActivity.this).inflate(R.layout.layout_index3, null));
            View view = LayoutInflater.from(IndexGuideActivity.this).inflate(R.layout.layout_index4, null);
            View ivLjty = view.findViewById(R.id.ivLjty);
            ivLjty.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferenceUtil.saveInt(IndexGuideActivity.this, SharedPreferenceUtil.VERSION_CODE, UpdateVersionService.getVersionCode(IndexGuideActivity.this));
                    toMainActivity();
                }
            });
            list.add(view);
        }

        @Override
        public int getCount() {
            return 4;
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            container.addView(list.get(position));
            return list.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}
