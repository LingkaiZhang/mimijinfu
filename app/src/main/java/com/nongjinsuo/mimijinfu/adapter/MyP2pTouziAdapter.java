package com.nongjinsuo.mimijinfu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.nongjinsuo.mimijinfu.R;
import com.nongjinsuo.mimijinfu.config.Constants;
import com.nongjinsuo.mimijinfu.httpmodel.MyP2pTouZiModel;
import com.nongjinsuo.mimijinfu.util.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * @author czz
 * @time 2016/10/24$ 11:10$
 * @Description: (我的募集数据适配器)
 */
public class MyP2pTouziAdapter extends BaseAdapter {

    private Context context;
    private List<MyP2pTouZiModel.ResultBean.UserprojectBean> caseList = new ArrayList<>();
    public MyP2pTouziAdapter(Context context, List<MyP2pTouZiModel.ResultBean.UserprojectBean> caseList) {
        this.context = context;
        this.caseList = caseList;
    }


    public void setMoreCaseList(List<MyP2pTouZiModel.ResultBean.UserprojectBean> caseListMore) {
        if (caseListMore != null) {
            caseList.addAll(caseListMore);
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        return caseList.size();
    }

    @Override
    public MyP2pTouZiModel.ResultBean.UserprojectBean getItem(int i) {
        return caseList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_my_p2p_touzi, null);
        }
        MyP2pTouZiModel.ResultBean.UserprojectBean userprojectBean =  getItem(i);
        TextView tvName = ViewHolder.get(view,R.id.tvName);
        TextView tvStatusUnit = ViewHolder.get(view,R.id.tvStatusUnit);
        TextView tvTzje = ViewHolder.get(view,R.id.tvTzje);
        TextView tvTzjeDanWei = ViewHolder.get(view,R.id.tvTzjeDanWei);
        TextView tvQxr = ViewHolder.get(view,R.id.tvQxr);
        TextView tvQxrDanWei = ViewHolder.get(view,R.id.tvQxrDanWei);
        TextView tvQxrStr = ViewHolder.get(view,R.id.tvQxrStr);
        tvName.setText(userprojectBean.getName());
        tvStatusUnit.setText(userprojectBean.getStateName());
        tvTzje.setText(userprojectBean.getInvestMoney());
        tvTzjeDanWei.setText(userprojectBean.getInvestMoneyUnit());
        if (userprojectBean.getState() == Constants.STATUS_5_ZCJS){
            tvQxr.setText(userprojectBean.getBackInterest());
            tvQxrDanWei.setText(userprojectBean.getBackInterestUnit());
            tvQxrDanWei.setVisibility(View.VISIBLE);
            tvQxrStr.setText("本期收益");
            tvStatusUnit.setTextColor(context.getResources().getColor(R.color.color_8f8f8f));
        }else{
            if (userprojectBean.getState() == Constants.STATUS_1_ZCZ){
                tvQxr.setText("- -");
            }else{
                tvQxr.setText(userprojectBean.getInterestTime());
            }
            tvQxrDanWei.setVisibility(View.GONE);
            tvQxrStr.setText("起息日");
            tvStatusUnit.setTextColor(context.getResources().getColor(R.color.color_ff3947));
        }
        return view;
    }
}
