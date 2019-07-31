package com.nongjinsuo.mimijinfu.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.nongjinsuo.mimijinfu.R;
import com.nongjinsuo.mimijinfu.activity.LoginActivity;
import com.nongjinsuo.mimijinfu.activity.P2pDetilsActivity;
import com.nongjinsuo.mimijinfu.activity.ProjectBuyActivity;
import com.nongjinsuo.mimijinfu.beans.InvestVo;
import com.nongjinsuo.mimijinfu.config.Constants;
import com.nongjinsuo.mimijinfu.config.SharedPreferenceUtil;
import com.nongjinsuo.mimijinfu.util.NetworkUtils;
import com.nongjinsuo.mimijinfu.util.TextUtil;
import com.nongjinsuo.mimijinfu.util.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * @author czz
 * @time 2016/10/24$ 11:10$
 * @Description: (热卖Adapter)
 */
public class P2pAdapter extends BaseAdapter {

    private Context context;
    private List<InvestVo> investList = new ArrayList<>();

    public P2pAdapter(Context context) {
        this.context = context;
    }

    public P2pAdapter(Context context, List<InvestVo> investList) {
        this.context = context;
        this.investList = investList;
    }

    public void setInvestList(List<InvestVo> investList) {
        this.investList = investList;
        notifyDataSetChanged();
    }

    public void setMoreInvestList(List<InvestVo> investList) {
        this.investList.addAll(investList);
        notifyDataSetChanged();
    }

    public List<InvestVo> getInvestList() {
        return investList;
    }

    @Override
    public int getCount() {
        return investList.size();
    }

    @Override
    public InvestVo getItem(int i) {
        return investList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_touzituijian, null);
        }
        TextView tvNhsy = ViewHolder.get(view, R.id.tvNhsy);
        TextView tvTime = ViewHolder.get(view, R.id.tvTime);
        TextView tvShengyu = ViewHolder.get(view, R.id.tvShengyu);
        TextView tvType = ViewHolder.get(view, R.id.tvType);
        TextView tvQtz = ViewHolder.get(view, R.id.tvQtz);
        TextView tvPlusRate = ViewHolder.get(view, R.id.tvPlusRate);
        final InvestVo investVo = getItem(i);
        tvNhsy.setText(investVo.getBasicRate());
        if (TextUtil.IsNotEmpty(investVo.getPlusRate())){
            tvPlusRate.setText(investVo.getPlusRate());
            tvPlusRate.setVisibility(View.VISIBLE);
        }else{
            tvPlusRate.setVisibility(View.GONE);
        }
        tvTime.setText(investVo.getNameSimplify());
        tvShengyu.setText(investVo.getResidueMoneyDescr());
        tvQtz.setText(investVo.getStatusDescr());
        tvType.setText(investVo.getBackMoneyClassName());
        if (investVo.getStatus() == 1) {
            tvQtz.setBackgroundResource(R.drawable.shape_qiangtou_btn);
            tvQtz.setTextColor(context.getResources().getColor(R.color.white));
        }else if(investVo.getStatus() == 2){
            tvQtz.setBackgroundResource(R.drawable.shape_qiangtou_bg_hui_btn);
            tvQtz.setTextColor(context.getResources().getColor(R.color.white));
        } else {
            tvQtz.setBackgroundResource(R.drawable.shape_qiangtou_hui_btn);
            tvQtz.setTextColor(context.getResources().getColor(R.color.color_8f8f8f));
        }
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!NetworkUtils.isNetworkAvailable(context)) {
                    Toast.makeText(context, context.getString(R.string.str_no_network), Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(context, P2pDetilsActivity.class);
                intent.putExtra(Constants.ID, investVo.getId());
                intent.putExtra(Constants.PROJECTBM, investVo.getBm());
                intent.putExtra(Constants.STATUS, investVo.getStatus());
                context.startActivity(intent);
            }
        });
        View viewLine = ViewHolder.get(view, R.id.viewLine);
        viewLine.setVisibility(i == getCount() - 1 ? View.GONE : View.VISIBLE);
        return view;
    }

}