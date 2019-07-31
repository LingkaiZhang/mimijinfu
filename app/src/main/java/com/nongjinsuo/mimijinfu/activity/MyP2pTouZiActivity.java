package com.nongjinsuo.mimijinfu.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.widget.RadioGroup;

import com.nongjinsuo.mimijinfu.R;
import com.nongjinsuo.mimijinfu.fragment.MyP2pTouZiFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author czz
 * @Description: (我的投资-p2p)
 */
public class MyP2pTouZiActivity extends AbstractActivity {
    @BindView(R.id.radioGroup)
    RadioGroup radioGroup;
    private Fragment mCurrentFragment, myCrowdFundingFragment0, myCrowdFundingFragment1;
    public static final String SHOUYI = "shouyi";
    private String[] shouyiValue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mycrowdfunding);
        ButterKnife.bind(this);
        shouyiValue = getIntent().getStringArrayExtra(SHOUYI);
        init();
        addListener();
    }

    @Override
    public void init() {
        titleView.setText("定投宝");

        if (myCrowdFundingFragment0 == null) {
            myCrowdFundingFragment0 = MyP2pTouZiFragment.newInstance(0,shouyiValue);
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
                        myCrowdFundingFragment0 = MyP2pTouZiFragment.newInstance(0,shouyiValue);
                    }
                    switchContent(myCrowdFundingFragment0);
                } else {
                    if (myCrowdFundingFragment1 == null) {
                        myCrowdFundingFragment1 = MyP2pTouZiFragment.newInstance(1,shouyiValue);
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
