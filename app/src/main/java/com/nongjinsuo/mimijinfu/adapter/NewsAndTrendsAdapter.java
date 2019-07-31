package com.nongjinsuo.mimijinfu.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nongjinsuo.mimijinfu.R;
import com.nongjinsuo.mimijinfu.activity.WebViewAndJsActivity;
import com.nongjinsuo.mimijinfu.beans.ActivityBean;
import com.nongjinsuo.mimijinfu.dao.http.Urls;
import com.nongjinsuo.mimijinfu.util.ViewHolder;

import java.util.List;

/**
 * description: (新闻动态adapter)
 * autour: czz
 * date: 2017/12/11 0011 下午 1:38
 * update: 2017/12/11 0011
 */

public class NewsAndTrendsAdapter extends BaseAdapter {
    private Context context;
    private List<ActivityBean> dataBeen;
    public NewsAndTrendsAdapter(Context context,List<ActivityBean> dataBeen){
        this.context = context;
        this.dataBeen = dataBeen;
    }

    public List<ActivityBean> getDataBeen() {
        return dataBeen;
    }

    public void setDataBeen(List<ActivityBean> dataBeen) {
        this.dataBeen = dataBeen;
        notifyDataSetChanged();
    }
    public void setMoreDateBeen(List<ActivityBean> dataBeen){
        if (dataBeen!=null){
            this.dataBeen.addAll(dataBeen);
        }
        notifyDataSetChanged();

    }
    @Override
    public int getCount() {
        return dataBeen.size();
    }

    @Override
    public ActivityBean getItem(int position) {
        return dataBeen.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_news_trends,null);
        }
        final ActivityBean activityBean =  getItem(position);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,WebViewAndJsActivity.class);
                intent.putExtra(WebViewAndJsActivity.URL, activityBean.getActivityUrl());
                context.startActivity(intent);
            }
        });
        ImageView ivNewsImage = ViewHolder.get(convertView,R.id.ivNewsImage);
        TextView tvNewsTitle = ViewHolder.get(convertView,R.id.tvNewsTitle);
        TextView tvNewsContent = ViewHolder.get(convertView,R.id.tvNewsContent);
        View viewLine = ViewHolder.get(convertView,R.id.viewLine);
        Glide.with(context).load(Urls.PROJECT_URL+activityBean.getCoverImage()).placeholder(R.drawable.img_ltbmr).crossFade().into(ivNewsImage);
        tvNewsTitle.setText(activityBean.getTitle());
        tvNewsContent.setText(activityBean.getContent());
        viewLine.setVisibility(position == getCount()-1?View.GONE:View.VISIBLE);
        return convertView;
    }
}
