package com.nongjinsuo.mimijinfu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.nongjinsuo.mimijinfu.R;
import com.nongjinsuo.mimijinfu.beans.TraderecodeVo;
import com.nongjinsuo.mimijinfu.util.TextUtil;
import com.nongjinsuo.mimijinfu.util.ViewHolder;

import java.util.List;

/**
 * @author czz
 * @Description: (我的记录)
 */
public class TansactionRecordAdapter extends BaseAdapter{
    private Context context;
    private List<TraderecodeVo> result;
    public TansactionRecordAdapter(Context context,List<TraderecodeVo> result){
        this.context =context;
        this.result = result;
    }
    @Override
    public int getCount() {
        return result.size();
    }

    @Override
    public TraderecodeVo getItem(int i) {
        return result.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public List<TraderecodeVo> getResult() {
        return result;
    }

    public void setResult(List<TraderecodeVo> result) {
        this.result = result;
    }
    public void setResultMore(List<TraderecodeVo> resultMore) {
        if (resultMore!=null){
            result.addAll(resultMore);
        }
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null){
            view = LayoutInflater.from(context).inflate(R.layout.item_tansaction_record, null);
        }
        TraderecodeVo item = getItem(i);
        TextView tvMonth = ViewHolder.get(view, R.id.tvMonth);
        TextView tvYear = ViewHolder.get(view, R.id.tvYear);
        TextView tvMoney = ViewHolder.get(view, R.id.tvMoney);
        TextView tvStatus = ViewHolder.get(view, R.id.tvStatus);
        TextView tvStatusName = ViewHolder.get(view, R.id.tvStatusName);
        TextView tvHb = ViewHolder.get(view, R.id.tvHb);
        View viewLine = ViewHolder.get(view,R.id.viewLine);
        String[] split = item.getPubTime().split("-");
        if (split!=null){
            tvMonth.setText(split[1]+split[2]);
            tvYear.setText(split[0]);
        }
        tvMoney.setText(item.getMoney());
        tvStatusName.setText(item.getStatus());
        tvStatus.setText(item.getSummary());
        if (item.getStylestatus() == 1){
            tvStatusName.setTextColor(context.getResources().getColor(R.color.color_green));
        }else if(item.getStylestatus() == 2){
            tvStatusName.setTextColor(context.getResources().getColor(R.color.color_login_btn));
        }else{
            tvStatusName.setTextColor(context.getResources().getColor(R.color.color_project_detail_jindu_hui));
        }
        if (i == getCount()-1){
            viewLine.setVisibility(View.INVISIBLE);
        }else{
            viewLine.setVisibility(View.VISIBLE);
        }
        //判断是否有红包
        if (TextUtil.IsNotEmpty(item.getRedpacketMoney())&&!(item.getRedpacketMoney().equals("0"))){
            tvHb.setText("红包"+item.getRedpacketMoney()+"元");
            tvHb.setVisibility(View.VISIBLE);
        }else{
            tvHb.setVisibility(View.GONE);
        }
        return view;
    }
}
