package com.nongjinsuo.mimijinfu.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.widget.RadioGroup;

import com.nongjinsuo.mimijinfu.R;
import com.nongjinsuo.mimijinfu.fragment.MyRedpacketFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * description: (历史红包)
 * autour: czz
 * date: 2017/9/22 0022 下午 2:57
 * update: 2017/9/22 0022
 */

public class HistoryMyRedPacketActivity extends AbstractActivity {
    @BindView(R.id.radioGroup)
    RadioGroup radioGroup;
    private Fragment mCurrentFragment, myCrowdFundingFragment0, myCrowdFundingFragment1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_redpacket);
        ButterKnife.bind(this);
        titleView.setText("历史红包");
        init();
        addListener();
    }

    @Override
    public void init() {
        if (myCrowdFundingFragment0 == null) {
            myCrowdFundingFragment0 = MyRedpacketFragment.newInstance(MyRedpacketFragment.YSYHB);
        }
        switchContent(myCrowdFundingFragment0);
    }

    @Override
    public void addListener() {
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (radioGroup.getCheckedRadioButtonId() == R.id.rbCyxm) {
                    if (myCrowdFundingFragment0 == null) {
                        myCrowdFundingFragment0 = MyRedpacketFragment.newInstance(MyRedpacketFragment.YSYHB);
                    }
                    switchContent(myCrowdFundingFragment0);
                } else {
                    if (myCrowdFundingFragment1 == null) {
                        myCrowdFundingFragment1 = MyRedpacketFragment.newInstance(MyRedpacketFragment.YGQHB);
                    }
                    switchContent(myCrowdFundingFragment1);
                }
            }
        });
    }

    private void switchContent(Fragment fragment) {
        if (mCurrentFragment == null) {
            mCurrentFragment = fragment;
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.frameLayout, mCurrentFragment);
            transaction.commitAllowingStateLoss();
        } else {
            if (mCurrentFragment != fragment) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                if (!fragment.isAdded()) { // 先判断是否被add过
                    transaction.hide(mCurrentFragment).add(R.id.frameLayout, fragment).commitAllowingStateLoss(); // 隐藏当前的fragment，add下一个到Activity中
                } else {
                    transaction.hide(mCurrentFragment).show(fragment).commitAllowingStateLoss(); // 隐藏当前的fragment，显示下一个
                }

                mCurrentFragment = fragment;
            }
        }
    }
}
