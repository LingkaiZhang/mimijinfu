package com.nongjinsuo.mimijinfu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nongjinsuo.mimijinfu.R;
import com.nongjinsuo.mimijinfu.beans.BankVo;
import com.nongjinsuo.mimijinfu.dao.http.Urls;
import com.nongjinsuo.mimijinfu.util.ViewHolder;

import java.util.ArrayList;

/**
 * @author czz
 * @Description: (银行数据适配器)
 */
public class BankCardAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<BankVo> result;
    private boolean isSelect;
    public BankCardAdapter(boolean isSelect , Context context, ArrayList<BankVo> result){
        this.context =context;
        this.result = result;
        this.isSelect = isSelect;
    }
    @Override
    public int getCount() {
        return result.size();
    }

    @Override
    public BankVo getItem(int i) {
        return result.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null){
           view = LayoutInflater.from(context).inflate(R.layout.include_bank_view,null);
        }
        BankVo item = getItem(i);
        ImageView ivBankIcon = ViewHolder.get(view, R.id.ivBankIcon);
        TextView tvBankName = ViewHolder.get(view, R.id.tvBankName);
        TextView tvBankQuota = ViewHolder.get(view, R.id.tvBankQuota);
        Glide.with(context).load(Urls.PROJECT_URL+item.getBankImage()).crossFade().into(ivBankIcon);
        tvBankName.setText(item.getBankName());
        tvBankQuota.setText("单笔限额"+item.getPerPayMaxNum()+"元,单日限额"+item.getPerDayMaxNum()+"元");
        return view;
    }
}
