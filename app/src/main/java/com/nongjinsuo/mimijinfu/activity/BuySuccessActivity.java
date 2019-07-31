package com.nongjinsuo.mimijinfu.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.nongjinsuo.mimijinfu.R;
import com.nongjinsuo.mimijinfu.beans.AvailableaVo;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author czz
 * @Description: (认购成功)
 */
public class BuySuccessActivity extends AbstractActivity {
    @BindView(R.id.tvMoney)
    TextView tvMoney;
    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.tvFhxmxq)
    TextView tvFhxmxq;
    public static final String AVAILABLEAVO = "AvailableaVo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_success);
        ButterKnife.bind(this);
        init();
        addListener();
    }

    @Override
    public void init() {
        titleView.setText("认购成功");
        AvailableaVo availableaVo = (AvailableaVo) getIntent().getSerializableExtra(AVAILABLEAVO);
        if (availableaVo!=null){
            tvMoney.setText(availableaVo.getGmamount()+"元");
            tvName.setText("您已成功认购\n"+availableaVo.getName());
        }
    }

    @Override
    public void addListener() {

    }

    @OnClick(R.id.tvFhxmxq)
    public void onClick() {
        finish();
    }
}
