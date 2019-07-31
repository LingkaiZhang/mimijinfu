package com.nongjinsuo.mimijinfu.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.nongjinsuo.mimijinfu.R;
import com.nongjinsuo.mimijinfu.beans.MessageVo;
import com.nongjinsuo.mimijinfu.beans.NoticeVo;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author czz
 * @Description: (系统通知)
 */
public class SystemNotificationActivity extends AbstractActivity {

    @BindView(R.id.tvTime)
    TextView tvTime;
    @BindView(R.id.tvMessage)
    TextView tvMessage;
    public static final String MESSAGEVO = "messageVo";
    public static final String NOTICEVO = "noticeVo";
    private  MessageVo messageVo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        messageVo= (MessageVo) getIntent().getSerializableExtra(MESSAGEVO);

        setContentView(R.layout.activity_system_notification);
        ButterKnife.bind(this);
        init();
        addListener();
    }

    @Override
    public void init() {
        if (messageVo!=null){
            titleView.setText("系统通知");
            tvTime.setText(messageVo.getPubTime());
            tvMessage.setText(messageVo.getContent());
        }else{
            NoticeVo noticeVo = (NoticeVo) getIntent().getSerializableExtra(NOTICEVO);
            if (noticeVo!=null){
                titleView.setText("公告");
                tvTime.setText(noticeVo.getPubTime());
                tvMessage.setText(noticeVo.getContent());
            }
        }
    }

    @Override
    public void addListener() {

    }
}
