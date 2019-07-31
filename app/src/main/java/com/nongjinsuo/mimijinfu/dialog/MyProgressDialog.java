package com.nongjinsuo.mimijinfu.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.nongjinsuo.mimijinfu.R;
import com.nongjinsuo.mimijinfu.util.TextUtil;

/**
 * @author czz
 * @Description: (加载框)
 */
public class MyProgressDialog extends Dialog{

    TextView idTvLoadingmsg;
    private String message;

    public MyProgressDialog(Context context,String message) {
        super(context, R.style.progress_dialog);
        this.setContentView(R.layout.dialog_progress);
        setCancelable(true);
        setCanceledOnTouchOutside(false);
        this.message = message;
        idTvLoadingmsg = (TextView) findViewById(R.id.id_tv_loadingmsg);
        if (TextUtil.IsNotEmpty(message)){
            idTvLoadingmsg.setText(message);
            idTvLoadingmsg.setVisibility(View.VISIBLE);
        }else{
            idTvLoadingmsg.setVisibility(View.GONE);
        }
    }

    public MyProgressDialog(Context context){
        super(context, R.style.progress_dialog);
        this.setContentView(R.layout.dialog_progress);
        setCancelable(true);
        setCanceledOnTouchOutside(false);
        idTvLoadingmsg = (TextView) findViewById(R.id.id_tv_loadingmsg);
    }
    public void setMessage(String message) {
        idTvLoadingmsg.setText(message);
    }
}
