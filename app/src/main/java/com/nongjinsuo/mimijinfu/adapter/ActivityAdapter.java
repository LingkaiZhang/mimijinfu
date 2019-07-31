package com.nongjinsuo.mimijinfu.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nongjinsuo.mimijinfu.R;
import com.nongjinsuo.mimijinfu.activity.WebViewAndJsActivity;
import com.nongjinsuo.mimijinfu.beans.ActivityBean;
import com.nongjinsuo.mimijinfu.config.Constants;
import com.nongjinsuo.mimijinfu.dao.http.Urls;
import com.nongjinsuo.mimijinfu.util.PxAndDip;
import com.nongjinsuo.mimijinfu.util.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * description: (活动)
 * autour: czz
 * date: 2017/8/23 0023 下午 2:15
 * update: 2017/8/23 0023
 */

public class ActivityAdapter extends BaseAdapter {
    private Context context;
    private List<ActivityBean> activity = new ArrayList<>();
    private LayoutInflater inflater;
    private  LinearLayout.LayoutParams imgParams;
    public List<ActivityBean> getActivity() {
        return activity;
    }

    public void setActivity(List<ActivityBean> activity) {
        this.activity = activity;
        notifyDataSetChanged();

    }

    public void setMoreActivity(List<ActivityBean> activityBeanList) {
        if (activityBeanList != null) {
            this.activity.addAll(activityBeanList);
            notifyDataSetChanged();
        }
    }

    public ActivityAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        getImgViewParams(context);
    }

    private void getImgViewParams(Context context) {
        int imgWidth = Constants.WINDOW_WIDTH - PxAndDip.dip2px(context,32);
        int imgHeight = imgWidth*314/686;
        imgParams= new LinearLayout.LayoutParams(imgWidth,imgHeight);
    }

    public ActivityAdapter(Context context,List<ActivityBean> activity) {
        this.context = context;
        this.activity = activity;
        inflater = LayoutInflater.from(context);
        getImgViewParams(context);
    }

    @Override
    public int getCount() {
        return activity.size();
    }

    @Override
    public ActivityBean getItem(int i) {
        return activity.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null){
            view = inflater.inflate(R.layout.item_activity, null);
        }
        final ActivityBean  activityBean = getItem(i);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,WebViewAndJsActivity.class);
                intent.putExtra(WebViewAndJsActivity.URL, activityBean.getActivityUrl());
                context.startActivity(intent);
            }
        });
        ImageView simpleDraweeView = ViewHolder.get(view,R.id.iv_user_avatar);
        simpleDraweeView.setLayoutParams(imgParams);
        TextView tvActivityTitle = ViewHolder.get(view,R.id.tvActivityTitle);
        Glide.with(context).load(Urls.PROJECT_URL+activityBean.getActivityImage()).placeholder(R.drawable.img_hdmr).crossFade().into(simpleDraweeView);
        tvActivityTitle.setText(activityBean.getActivityName());
        return view;
    }
}
