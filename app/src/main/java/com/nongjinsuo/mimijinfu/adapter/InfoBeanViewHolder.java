/*
 * Copyright (c) 2017. Xi'an iRain IOT Technology service CO., Ltd (ShenZhen). All Rights Reserved.
 */

package com.nongjinsuo.mimijinfu.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.nongjinsuo.mimijinfu.R;
import com.nongjinsuo.mimijinfu.httpmodel.SoonRoomEntity;


public class InfoBeanViewHolder extends RecyclerView.ViewHolder {
    private final TextView tvName;

    public InfoBeanViewHolder(View itemView) {
        super(itemView);
        tvName = (TextView) itemView.findViewById(R.id.tvName);
    }

    public void update(SoonRoomEntity.ResultBean.BackBean team) {
        tvName.setText(team.getDate());
    }
}
