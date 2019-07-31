package com.nongjinsuo.mimijinfu.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.nongjinsuo.mimijinfu.R;
import com.nongjinsuo.mimijinfu.beans.RedPacketBean;
import com.nongjinsuo.mimijinfu.config.Constants;
import com.nongjinsuo.mimijinfu.fragment.MyRedpacketFragment;

import butterknife.ButterKnife;

/**
 * description: (我的红包)
 * autour: czz
 * date: 2017/9/22 0022 上午 10:38
 * update: 2017/9/22 0022
 */

public class MyRedPacketActivity extends AbstractActivity {

    private int type;
    public static final String SELECT_MYREDPACKET = "select_myredpacket";//选择红包标示
    public static final String REDPACKETBEAN_ID = "redpacketbean_id";//选中的红包id
    public static final String TZ_MONEY = "tz_money";//投资金额
    public static final String FLAG_BSYHB = "flag_bsyhb";//不是用红包
    private boolean selectBool;
    private boolean bsyhbFlag;
    String tzMoney;
    RedPacketBean redPacketBean;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myredpacket);
        ButterKnife.bind(this);
        titleView.setText("奖励红包");
        selectBool = getIntent().getBooleanExtra(SELECT_MYREDPACKET,false);
        bsyhbFlag = getIntent().getBooleanExtra(FLAG_BSYHB,false);
        redPacketBean = getIntent().getParcelableExtra(REDPACKETBEAN_ID);
        tzMoney = getIntent().getStringExtra(TZ_MONEY);
        type = getIntent().getIntExtra(Constants.TYPE, MyRedpacketFragment.MYHB);
        if (!selectBool) {
            templateTextViewRight.setVisibility(View.VISIBLE);
            templateTextViewRight.setText("历史红包");
        } else {
            templateTextViewRight.setVisibility(View.GONE);
        }
        init();
        addListener();
    }

    @Override
    public void init() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, MyRedpacketFragment.newInstance(type,selectBool,redPacketBean,tzMoney,bsyhbFlag));
        fragmentTransaction.commitAllowingStateLoss();
    }

    @Override
    public void addListener() {
        templateTextViewRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyRedPacketActivity.this, HistoryMyRedPacketActivity.class);
                startActivity(intent);
            }
        });
    }
}
