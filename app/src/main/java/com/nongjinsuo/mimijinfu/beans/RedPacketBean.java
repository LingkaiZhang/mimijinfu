package com.nongjinsuo.mimijinfu.beans;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * description: (描述)
 * autour: czz
 * date: 2017/9/25 0025 下午 3:48
 * update: 2017/9/25 0025
 */

public class RedPacketBean implements Parcelable {
    /**
     * id : 34
     * money : 10
     * getTime : 2017-09-22 14:09:26
     * expireTime : 2017-10-02 14:09:26
     * descr : 单笔投资满500元可用
     * minUse : 500
     */

    private String id;
    private String money;
    private String getTime;
    private String expireTime;
    private String descr;
    private String minUse;
    private String ruleDescr;

    public String getRuleDescr() {
        return ruleDescr;
    }

    public void setRuleDescr(String ruleDescr) {
        this.ruleDescr = ruleDescr;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getGetTime() {
        return getTime;
    }

    public void setGetTime(String getTime) {
        this.getTime = getTime;
    }

    public String getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(String expireTime) {
        this.expireTime = expireTime;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public String getMinUse() {
        return minUse;
    }

    public void setMinUse(String minUse) {
        this.minUse = minUse;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.money);
        dest.writeString(this.getTime);
        dest.writeString(this.expireTime);
        dest.writeString(this.descr);
        dest.writeString(this.minUse);
    }

    public RedPacketBean() {
    }

    protected RedPacketBean(Parcel in) {
        this.id = in.readString();
        this.money = in.readString();
        this.getTime = in.readString();
        this.expireTime = in.readString();
        this.descr = in.readString();
        this.minUse = in.readString();
    }

    public static final Parcelable.Creator<RedPacketBean> CREATOR = new Parcelable.Creator<RedPacketBean>() {
        @Override
        public RedPacketBean createFromParcel(Parcel source) {
            return new RedPacketBean(source);
        }

        @Override
        public RedPacketBean[] newArray(int size) {
            return new RedPacketBean[size];
        }
    };
}
