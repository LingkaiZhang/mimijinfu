package com.nongjinsuo.mimijinfu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nongjinsuo.mimijinfu.R;
import com.nongjinsuo.mimijinfu.beans.NoticeVo;
import com.nongjinsuo.mimijinfu.util.ViewHolder;

import java.util.List;

/**
 * @author czz
 * @Description: (消息Adapter)
 */
public class NoticeAdapter extends BaseAdapter {
    private Context context;
    private List<NoticeVo> messageVoList;

    public NoticeAdapter(Context context, List<NoticeVo> messageVoList) {
        this.context = context;
        this.messageVoList = messageVoList;
    }

    public List<NoticeVo> getMessageVoList() {
        return messageVoList;
    }

    public void setMessageVoList(List<NoticeVo> messageVoList) {
        this.messageVoList = messageVoList;
    }
    public void setMessageVoListMore(List<NoticeVo> messageVoListMore) {
        if (messageVoListMore!=null){
            messageVoList.addAll(messageVoListMore);
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        return messageVoList.size();
    }

    @Override
    public NoticeVo getItem(int i) {
        return messageVoList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        NoticeVo item = getItem(i);
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_message, null);
        }
        ImageView ivMessageIcon = ViewHolder.get(view, R.id.ivMessageIcon);
        ivMessageIcon.setImageResource(R.drawable.img_ggg);

        TextView tvMessageTitle = ViewHolder.get(view, R.id.tvMessageTitle);
        TextView tvMessageContent = ViewHolder.get(view, R.id.tvMessageContent);
        TextView tvMessageTime = ViewHolder.get(view, R.id.tvMessageTime);
//        TextView tvLookDetail = ViewHolder.get(view, R.id.tvLookDetail);
        View viewLine =  ViewHolder.get(view,R.id.viewLine);
        tvMessageTitle.setText("公告");
        tvMessageContent.setText(item.getTitle());
        tvMessageTime.setText(item.getPubTime());
//        if (TextUtil.IsNotEmpty(item.getClasses())){
//            tvLookDetail.setVisibility(View.VISIBLE);
//        }else{
//            tvLookDetail.setVisibility(View.GONE);
//        }
        if (i == getCount()-1){
            viewLine.setVisibility(View.GONE);
        }else{
            viewLine.setVisibility(View.VISIBLE);
        }
        return view;
    }

}
