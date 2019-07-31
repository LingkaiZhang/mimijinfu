package com.nongjinsuo.mimijinfu.adapter;

import android.content.Context;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nongjinsuo.mimijinfu.R;
import com.nongjinsuo.mimijinfu.beans.UserProjectVo;
import com.nongjinsuo.mimijinfu.config.AiMiCrowdFundingApplication;
import com.nongjinsuo.mimijinfu.config.Constants;
import com.nongjinsuo.mimijinfu.dao.http.Urls;
import com.nongjinsuo.mimijinfu.util.ViewHolder;

import java.io.File;
import java.util.List;

/**
 * @author czz
 * @time 2016/10/24$ 11:10$
 * @Description: (我的募集数据适配器)
 */
public class MyCrowdFundingAdapter2 extends BaseAdapter {

    private Context context;
    private List<UserProjectVo.UserprojectBean> caseList;
    private int backStatus;

    public MyCrowdFundingAdapter2(Context context, List<UserProjectVo.UserprojectBean> caseList, int backStatus) {
        this.context = context;
        this.caseList = caseList;
        this.backStatus = backStatus;
    }


    public void setMoreCaseList(List<UserProjectVo.UserprojectBean> caseListMore) {
        if (caseListMore != null) {
            caseList.addAll(caseListMore);
            notifyDataSetChanged();
        }
    }

    private void deleteAllFiles(File root) {
        File files[] = root.listFiles();
        if (files != null)
            for (File f : files) {
                if (f.isDirectory()) { // 判断是否为文件夹
                    deleteAllFiles(f);
                    try {
                        f.delete();
                    } catch (Exception e) {
                    }
                } else {
                    if (f.exists()) { // 判断是否存在
                        deleteAllFiles(f);
                        try {
                            f.delete();
                        } catch (Exception e) {
                        }
                    }
                }
            }
    }

    @Override
    public int getCount() {
        return caseList.size();
    }

    @Override
    public UserProjectVo.UserprojectBean getItem(int i) {
        return caseList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    /**
     * 获取app的根目录
     *
     * @return 文件缓存根路径
     */
    public static String getDiskCacheRootDir() {
        File diskRootFile;
        if (existsSdcard()) {
            diskRootFile = AiMiCrowdFundingApplication.context().getExternalCacheDir();
        } else {
            diskRootFile = AiMiCrowdFundingApplication.context().getCacheDir();
        }
        String cachePath;
        if (diskRootFile != null) {
            cachePath = diskRootFile.getPath();
        } else {
            throw new IllegalArgumentException("disk is invalid");
        }
        return cachePath;
    }

    /**
     * +
     * 判断外置sdcard是否可以正常使用
     *
     * @return
     */
    public static Boolean existsSdcard() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) || !Environment.isExternalStorageRemovable();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_my_project_touzi, null);
        }
        final UserProjectVo.UserprojectBean item = getItem(i);
        TextView tvName = ViewHolder.get(view, R.id.tvName);

        TextView tvZdhgj = ViewHolder.get(view, R.id.tvZdhgj);
        TextView tvZdhgjDanWei = ViewHolder.get(view, R.id.tvZdhgjDanWei);

        TextView tvMbje = ViewHolder.get(view, R.id.tvMbje);
        TextView tvMbjeDanWei = ViewHolder.get(view, R.id.tvMbjeDanWei);

        TextView tvXmzeStr = ViewHolder.get(view, R.id.tvXmzeStr);
        TextView tvStrSyl = ViewHolder.get(view, R.id.tvStrSyl);
        TextView tvStatusUnit = ViewHolder.get(view, R.id.tvStatusUnit);


        tvName.setText(item.getName());
        tvZdhgj.setText(item.getInvestMoneyNoUnit());
        tvZdhgjDanWei.setText(item.getInvestMoneyUnit());
        tvXmzeStr.setText("投资金额");
        tvStrSyl.setText(item.getDeadlineDescr());
        tvMbje.setText(item.getDeadline());
        tvStatusUnit.setText(item.getStatusname());
        if (item.getStatus() == Constants.STATUS_5_ZCJS){
            tvStrSyl.setText("本期收益");
            tvMbje.setText(item.getBackInterest());
            tvMbjeDanWei.setVisibility(View.VISIBLE);
            tvMbjeDanWei.setText("元");
            tvStatusUnit.setTextColor(context.getResources().getColor(R.color.color_8f8f8f));
        }else{
//            tvStrSyl.setText("认购时间");
//            String[] str = item.getInvestTime().split(" ");
//            tvMbje.setText(str[0]);
            tvMbjeDanWei.setVisibility(View.GONE);

        }
        return view;
    }
}
