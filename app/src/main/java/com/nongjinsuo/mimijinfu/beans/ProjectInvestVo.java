package com.nongjinsuo.mimijinfu.beans;

/**
 * @author czz
 * @Description: (用一句话描述)
 */
public class ProjectInvestVo {

    /**
     * nickName : 5081
     * investMoney : 100
     * investTime : 2016-12-22 16:16:35
     */

    private String nickName;
    private String investMoney;
    private String investTime;
    private String actualMoney;

    public String getActualMoney() {
        return actualMoney;
    }

    public void setActualMoney(String actualMoney) {
        this.actualMoney = actualMoney;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getInvestMoney() {
        return investMoney;
    }

    public void setInvestMoney(String investMoney) {
        this.investMoney = investMoney;
    }

    public String getInvestTime() {
        return investTime;
    }

    public void setInvestTime(String investTime) {
        this.investTime = investTime;
    }
}
