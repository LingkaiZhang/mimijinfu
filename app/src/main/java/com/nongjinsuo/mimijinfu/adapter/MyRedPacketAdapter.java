package com.nongjinsuo.mimijinfu.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nongjinsuo.mimijinfu.R;
import com.nongjinsuo.mimijinfu.beans.RedPacketBean;
import com.nongjinsuo.mimijinfu.fragment.MyRedpacketFragment;
import com.nongjinsuo.mimijinfu.util.TextUtil;
import com.nongjinsuo.mimijinfu.util.ViewHolder;
import com.nongjinsuo.mimijinfu.widget.EventBarEntity;

import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * description: (我的红包)
 * autour: czz
 * date: 2017/9/22 0022 上午 11:26
 * update: 2017/9/22 0022
 */

public class MyRedPacketAdapter extends BaseAdapter {
    private Context context;
    private int type;
    private boolean isSelect;
    private List<RedPacketBean> redPacket;
    public String redpackeId;
    private String tzMoney;//投资金额 只有选红包的时候有用

    public MyRedPacketAdapter(Context context, int type, List<RedPacketBean> redPacket, boolean isSelect, String redpackeId, String tzMoney) {
        this.context = context;
        this.type = type;
        this.redPacket = redPacket;
        this.isSelect = isSelect;
        this.redpackeId = redpackeId;
        this.tzMoney = tzMoney;
    }
    public MyRedPacketAdapter(Context context, int type, List<RedPacketBean> redPacket) {
        this.context = context;
        this.type = type;
        this.redPacket = redPacket;
    }

    @Override
    public int getCount() {
        return redPacket.size();
    }

    @Override
    public RedPacketBean getItem(int position) {
        return redPacket.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_myredpacket, null);
        }
        TextView tvMoney = ViewHolder.get(convertView, R.id.tvMoney);
        TextView tvMoneyYuan = ViewHolder.get(convertView, R.id.tvMoneyYuan);
        TextView tvDbtz = ViewHolder.get(convertView, R.id.tvDbtz);
        TextView tvKyMoney = ViewHolder.get(convertView, R.id.tvKyMoney);
        TextView tvKy = ViewHolder.get(convertView, R.id.tvKy);

        TextView tvTime = ViewHolder.get(convertView, R.id.tvTime);
        ImageView ivLjsy = ViewHolder.get(convertView, R.id.ivLjsy);

        TextView tvRuleDescr = ViewHolder.get(convertView, R.id.tvRuleDescr);
        Resources resources = context.getResources();
        RedPacketBean redPacketBean = getItem(position);
        tvRuleDescr.setText(redPacketBean.getRuleDescr());
        tvMoney.setText(redPacketBean.getMoney());
        tvKyMoney.setText(redPacketBean.getMinUse());
        tvTime.setText("限" + redPacketBean.getExpireTime() + "前使用");
        if (type == MyRedpacketFragment.MYHB) {
            styleKy(tvMoney, tvMoneyYuan, tvDbtz, tvKyMoney, tvKy, tvTime, resources);
            if (isSelect) {
                if (TextUtil.IsNotEmpty(redpackeId) && redpackeId.equals(redPacketBean.getId())) {
                    ivLjsy.setImageResource(R.drawable.icon_yxz);
                } else {
                    ivLjsy.setImageResource(R.drawable.icon_yxza);
                }
                double kyMoney = Double.parseDouble(redPacketBean.getMinUse());//限制金额
                double tzMoney1 = 0;
                if (TextUtil.IsNotEmpty(tzMoney))
                    tzMoney1 = Double.parseDouble(tzMoney);//投资金额
                if (tzMoney1 >= kyMoney) {
                    ivLjsy.setVisibility(View.VISIBLE);
                    styleKy(tvMoney, tvMoneyYuan, tvDbtz, tvKyMoney, tvKy, tvTime, resources);
                } else {
                    ivLjsy.setVisibility(View.GONE);
                    styleYyorGq(tvMoney, tvMoneyYuan, tvDbtz, tvKyMoney, tvKy, tvTime, resources);
                }
            } else {
                ivLjsy.setImageResource(R.drawable.img_ljsy);
                ivLjsy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ((Activity)context).finish();
                        EventBus.getDefault().post(new EventBarEntity(EventBarEntity.PROJECT_ZC));
                    }
                });
            }
        } else if (type == MyRedpacketFragment.YSYHB) {
            styleYyorGq(tvMoney, tvMoneyYuan, tvDbtz, tvKyMoney, tvKy, tvTime, resources);
            ivLjsy.setImageResource(R.drawable.img_ysy);
        } else {
            styleYyorGq(tvMoney, tvMoneyYuan, tvDbtz, tvKyMoney, tvKy, tvTime, resources);
            ivLjsy.setImageResource(R.drawable.img_ygq);
        }
        return convertView;
    }

    private void styleKy(TextView tvMoney, TextView tvMoneyYuan, TextView tvDbtz, TextView tvKyMoney, TextView tvKy, TextView tvTime, Resources resources) {
        tvMoney.setTextColor(resources.getColor(R.color.color_project_detail_red_btn));
        tvMoneyYuan.setTextColor(resources.getColor(R.color.color_project_detail_red_btn));
        tvDbtz.setTextColor(resources.getColor(R.color.color_212121));
        tvKyMoney.setTextColor(resources.getColor(R.color.color_project_detail_red_btn));
        tvKy.setTextColor(resources.getColor(R.color.color_212121));
        tvTime.setTextColor(resources.getColor(R.color.color_212121));
    }

    private void styleYyorGq(TextView tvMoney, TextView tvMoneyYuan, TextView tvDbtz, TextView tvKyMoney, TextView tvKy, TextView tvTime, Resources resources) {
        tvMoney.setTextColor(resources.getColor(R.color.color_6b6b6b));
        tvMoneyYuan.setTextColor(resources.getColor(R.color.color_6b6b6b));
        tvDbtz.setTextColor(resources.getColor(R.color.color_a7a7a7));
        tvKyMoney.setTextColor(resources.getColor(R.color.color_6b6b6b));
        tvKy.setTextColor(resources.getColor(R.color.color_a7a7a7));
        tvTime.setTextColor(resources.getColor(R.color.color_a7a7a7));
    }
}
