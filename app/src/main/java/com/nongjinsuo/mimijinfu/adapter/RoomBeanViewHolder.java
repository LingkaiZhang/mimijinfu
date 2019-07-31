/*
 * Copyright (c) 2017. Xi'an iRain IOT Technology service CO., Ltd (ShenZhen). All Rights Reserved.
 */

package com.nongjinsuo.mimijinfu.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nongjinsuo.mimijinfu.R;
import com.nongjinsuo.mimijinfu.beans.BackMoneyBean;

public class RoomBeanViewHolder extends RecyclerView.ViewHolder {
    TextView tvCount;
    TextView tvDate;
    TextView tvStatus;
    TextView tvYhbj;
    TextView tvYhbjStr;
    TextView tvYhbjUnit;
    TextView tvYhlx;
    TextView tvYhlxStr;
    TextView tvYhlxUnit;
    LinearLayout llBxzj;
    private Context context;

    public RoomBeanViewHolder(View itemView, Context context) {
        super(itemView);
        this.context = context;
        tvCount = (TextView) itemView.findViewById(R.id.tvCount);
        tvDate = (TextView) itemView.findViewById(R.id.tvDate);
        tvStatus = (TextView) itemView.findViewById(R.id.tvStatus);

        tvYhbj = (TextView) itemView.findViewById(R.id.tvYhbj);
        tvYhbjStr = (TextView) itemView.findViewById(R.id.tvYhbjStr);
        tvYhbjUnit = (TextView) itemView.findViewById(R.id.tvYhbjUnit);

        tvYhlx = (TextView) itemView.findViewById(R.id.tvYhlx);
        tvYhlxStr = (TextView) itemView.findViewById(R.id.tvYhlxStr);
        tvYhlxUnit = (TextView) itemView.findViewById(R.id.tvYhlxUnit);

        llBxzj = (LinearLayout) itemView.findViewById(R.id.llBxzj);

    }

    public void update(BackMoneyBean backMoneyBean) {
        tvYhbj.setText(backMoneyBean.getBackMoneyMoney() + "");
        tvYhbjUnit.setText(backMoneyBean.getBackMoneyUnit());
        tvYhbjStr.setText("金额");

        tvYhlx.setText(backMoneyBean.getBackTime() + "");
        tvYhlxUnit.setVisibility(View.GONE);
        tvYhlxStr.setText("回款时间");
        tvCount.setText(backMoneyBean.getSn() + "/" + backMoneyBean.getDuraNum());
        tvCount.setBackgroundColor(context.getResources().getColor(R.color.colorAccent));
        tvDate.setText(backMoneyBean.getName());
        llBxzj.setVisibility(View.INVISIBLE);
        tvStatus.setText("待回款");
        tvStatus.setTextColor(context.getResources().getColor(R.color.color_ff3947));
    }
}
