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
import com.nongjinsuo.mimijinfu.beans.MessageVo;
import com.nongjinsuo.mimijinfu.dao.http.Urls;
import com.nongjinsuo.mimijinfu.util.ViewHolder;

import java.util.List;

/**
 * @author czz
 * @Description: (消息Adapter)
 */
public class MessageAdapter extends BaseAdapter {
    private Context context;
    private List<MessageVo> messageVoList;
    public MessageAdapter(Context context,List<MessageVo> messageVoList) {
        this.context = context;
        this.messageVoList =messageVoList;
    }

    public List<MessageVo> getMessageVoList() {
        return messageVoList;
    }

    public void setMessageVoList(List<MessageVo> messageVoList) {
        this.messageVoList = messageVoList;
        notifyDataSetChanged();
    }
    public void setMoreMessageVoList(List<MessageVo> messageVoList){
        if (messageVoList!=null){
            this.messageVoList.addAll(messageVoList);
            notifyDataSetChanged();
        }
    }
    @Override
    public int getCount() {
        return messageVoList.size();
    }

    @Override
    public MessageVo getItem(int i) {
        return messageVoList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        MessageVo item = getItem(i);
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_message, null);
        }
        ImageView ivMessageIcon = ViewHolder.get(view, R.id.ivMessageIcon);
        TextView tvMessageTitle = ViewHolder.get(view, R.id.tvMessageTitle);
        TextView tvMessageContent = ViewHolder.get(view, R.id.tvMessageContent);
        TextView tvMessageTime = ViewHolder.get(view, R.id.tvMessageTime);
//        TextView tvLookDetail = ViewHolder.get(view, R.id.tvLookDetail);
        ImageView ivMessageDian = ViewHolder.get(view, R.id.ivMessageDian);

        View viewLine =  ViewHolder.get(view,R.id.viewLine);
        //设置不同消息的图标
//        if (item.getClassId() == 1){
////            tvLookDetail.setVisibility(View.VISIBLE);
//            ivMessageIcon.setImageResource(R.drawable.img_hbjl);
//        }else if(item.getClassId() == 2){
////            tvLookDetail.setVisibility(View.VISIBLE);
//            ivMessageIcon.setImageResource(R.drawable.img_tz);
//        }else{
////            tvLookDetail.setVisibility(View.GONE);
//        }
        Glide.with(context).load(Urls.PROJECT_URL+item.getImage()).crossFade().into(ivMessageIcon);
        //设置已读和未读状态
        if (item.getIsRead() == 0){
            ivMessageDian.setVisibility(View.VISIBLE);
        }else{
            ivMessageDian.setVisibility(View.GONE);
        }
        tvMessageTitle.setText(item.getClassname());
        tvMessageContent.setText(item.getContent());
        tvMessageTime.setText(item.getPubTime());
        if (i == getCount()-1){
            viewLine.setVisibility(view.GONE);
        }else{
            viewLine.setVisibility(view.VISIBLE);
        }
        return view;
    }

}
