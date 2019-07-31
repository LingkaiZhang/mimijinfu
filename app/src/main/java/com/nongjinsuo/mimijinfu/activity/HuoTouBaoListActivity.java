package com.nongjinsuo.mimijinfu.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import com.nongjinsuo.mimijinfu.R;
import com.nongjinsuo.mimijinfu.fragment.P2PFragment;
import com.nongjinsuo.mimijinfu.fragment.ProjectFragment;

/**
 * description: (活投宝页面)
 * autour: czz
 * date: 2018/1/16 0016 上午 11:05
 * update: 2018/1/16 0016
 */

public class HuoTouBaoListActivity extends AbstractActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        titleView.setText("活投宝");
        setContentView(R.layout.activity_dingtoubao_list);
        init();
    }

    @Override
    public void init() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, ProjectFragment.newInstance());
        fragmentTransaction.commitAllowingStateLoss();
    }

    @Override
    public void addListener() {

    }
}
