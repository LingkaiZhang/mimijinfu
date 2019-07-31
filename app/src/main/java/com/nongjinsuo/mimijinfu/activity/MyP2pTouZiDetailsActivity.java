package com.nongjinsuo.mimijinfu.activity;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nongjinsuo.mimijinfu.R;
import com.nongjinsuo.mimijinfu.config.Constants;
import com.nongjinsuo.mimijinfu.dao.http.Urls;
import com.nongjinsuo.mimijinfu.httpmodel.MyP2pTouZiModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * description: (我的p2p投资详情)
 * autour: czz
 * date: 2017/12/1 0001 上午 11:00
 * update: 2017/12/1 0001
 */

public class MyP2pTouZiDetailsActivity extends AbstractActivity {
    public static final String USERPROJECTBEAN = "userprojectBean";
    @BindView(R.id.tvTzsj)
    TextView tvTzsj;
    @BindView(R.id.tvTzje)
    TextView tvTzje;
    @BindView(R.id.tvYqnh)
    TextView tvYqnh;
    @BindView(R.id.tvXmzq)
    TextView tvXmzq;
    @BindView(R.id.tvDqr)
    TextView tvDqr;
    @BindView(R.id.tvYqysbx)
    TextView tvYqysbx;
    @BindView(R.id.tvYsbx)
    TextView tvYsbx;
    @BindView(R.id.tvDsbx)
    TextView tvDsbx;
    MyP2pTouZiModel.ResultBean.UserprojectBean userprojectBean;
    @BindView(R.id.llTzht)
    LinearLayout llTzht;
    @BindView(R.id.tvTzhtLook)
    TextView tvTzhtLook;
    @BindView(R.id.tvHkjhLook)
    TextView tvHkjhLook;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myp2ptouzi_details);
        context = this;
        ButterKnife.bind(this);
        init();
        addListener();
    }


    @Override
    public void init() {
        userprojectBean = (MyP2pTouZiModel.ResultBean.UserprojectBean) getIntent().getSerializableExtra(USERPROJECTBEAN);
        titleView.setText(userprojectBean.getName());
        tvTzsj.setText(userprojectBean.getInvestTime());
        tvTzje.setText(userprojectBean.getInvestMoney2());
        tvYqnh.setText(userprojectBean.getProjectRate() + "%");
        tvXmzq.setText(userprojectBean.getProjectDay() + userprojectBean.getProjectUnit());
        tvDqr.setText(userprojectBean.getOverTime());
        tvYqysbx.setText(userprojectBean.getRevenue() + "元");
        tvYsbx.setText(userprojectBean.getReceive() + "元");
        tvDsbx.setText(userprojectBean.getCollect() + "元");
        if (userprojectBean.getState() == 5) {
            llTzht.setVisibility(View.GONE);
            tvTzhtLook.setText(userprojectBean.getDuraSn()+"/"+userprojectBean.getDuraNum());
            tvHkjhLook.setText("");
        } else {
            llTzht.setVisibility(View.VISIBLE);
            if (userprojectBean.getState() == 1){
                tvTzhtLook.setText("满标后查看");
                tvHkjhLook.setText("满标后查看");
            }else{
                tvHkjhLook.setText(userprojectBean.getDuraSn()+"/"+userprojectBean.getDuraNum());
                tvTzhtLook.setText("");
            }
        }
    }

    @Override
    public void addListener() {

    }

    @OnClick({R.id.llTzht, R.id.llHkjh, R.id.tvLookDetail})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.llTzht:
                if (userprojectBean.getState() !=1){
                    if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        new AlertDialog.Builder(context)
                                .setMessage("查看合同需要读取存储权限")
                                .setPositiveButton("设置", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                        intent.setData(Uri.parse("package:" + context.getPackageName()));
                                        context.startActivity(intent);

                                    }
                                })
                                .setNegativeButton("取消", null)
                                .show();
                        return;
                    }
                    String pdfUrl = Urls.HTXQ_DTB + userprojectBean.getProjectBm() + "-" + Constants.userid;
                    intent = new Intent(context, PDFViewActivity.class);
                    intent.putExtra(PDFViewActivity.URL, pdfUrl);
                    intent.putExtra(PDFViewActivity.PROJECTBM_AND_USERID, userprojectBean.getProjectBm() + "-" + Constants.userid);
                    context.startActivity(intent);
                }
                break;
            case R.id.llHkjh:
                if (userprojectBean.getState() !=1){
                    intent = new Intent(context, RepaymentScheduleActivity.class);
                    intent.putExtra(Constants.PROJECTBM, userprojectBean.getProjectBm());
                    intent.putExtra(RepaymentScheduleActivity.STATUS, RepaymentScheduleActivity.ACTIVE_USER);
                    startActivity(intent);
                }
                break;
            case R.id.tvLookDetail:
                intent = new Intent(this, P2pDetilsActivity.class);
                intent.putExtra(Constants.PROJECTBM, userprojectBean.getProjectBm());
                intent.putExtra(Constants.STATUS, userprojectBean.getStatus());
                intent.putExtra(Constants.ID, userprojectBean.getId());
                startActivity(intent);
                break;
        }
    }

    class ItemContentAdapter extends BaseAdapter {
        private String[] str;

        public ItemContentAdapter(String[] str) {
            this.str = str;
        }

        @Override
        public int getCount() {
            return str.length;
        }

        @Override
        public String getItem(int position) {
            return str[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = LayoutInflater.from(MyP2pTouZiDetailsActivity.this).inflate(R.layout.item_touzi_details, null);
            TextView tvTitle = (TextView) view.findViewById(R.id.tvTitle);
            tvTitle.setText(str[position]);
            return view;
        }
    }
}
