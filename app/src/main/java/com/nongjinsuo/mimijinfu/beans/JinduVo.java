package com.nongjinsuo.mimijinfu.beans;

/**
 * @author czz
 * @Description: (用一句话描述)
 */
public class JinduVo {


    /**
     * userInvestInfo : {"investMoney":"300.00","investInterest":0,"isInvest":1}
     * status : 1
     * beginTime : 2017-06-08 12:00:00
     * beginOnlineTime : 2017-06-08 11:50:00
     * avialInvestMoney : 700
     * beginOnlineDescr : 30分钟预告已完成
     * actualInvestOverTime : 2017-06-08 12:06:48
     * actualInvestTime : 6分钟48秒
     * dueDays : 1
     * backMoney : 0.00
     * actualVoteOverTime : 2017-06-08 13:21:14
     * surplustime : 6分钟48秒
     * actualSaleTime : 2017-06-08 13:21:14
     */

    private UserInvestInfoBean userInvestInfo;
    private int status;
    private String beginTime;
    private String beginOnlineTime;
    private String avialInvestMoney;
    private String beginOnlineDescr;
    private String actualInvestOverTime;
    private String actualInvestTime;
    private String dueDays;
    private String backMoney;
    private String actualVoteOverTime;
    private String surplustime;
    private String actualSaleTime;
    private String backMoneyTime;
    private String actualOverTime;
    private String statusDescr1;
    private String statusDescr2;

    public String getStatusDescr1() {
        return statusDescr1;
    }

    public void setStatusDescr1(String statusDescr1) {
        this.statusDescr1 = statusDescr1;
    }

    public String getStatusDescr2() {
        return statusDescr2;
    }

    public void setStatusDescr2(String statusDescr2) {
        this.statusDescr2 = statusDescr2;
    }

    public String getActualOverTime() {
        return actualOverTime;
    }

    public void setActualOverTime(String actualOverTime) {
        this.actualOverTime = actualOverTime;
    }

    public String getBackMoneyTime() {
        return backMoneyTime;
    }

    public void setBackMoneyTime(String backMoneyTime) {
        this.backMoneyTime = backMoneyTime;
    }

    public UserInvestInfoBean getUserInvestInfo() {
        return userInvestInfo;
    }

    public void setUserInvestInfo(UserInvestInfoBean userInvestInfo) {
        this.userInvestInfo = userInvestInfo;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getBeginOnlineTime() {
        return beginOnlineTime;
    }

    public void setBeginOnlineTime(String beginOnlineTime) {
        this.beginOnlineTime = beginOnlineTime;
    }

    public String getAvialInvestMoney() {
        return avialInvestMoney;
    }

    public void setAvialInvestMoney(String avialInvestMoney) {
        this.avialInvestMoney = avialInvestMoney;
    }

    public String getBeginOnlineDescr() {
        return beginOnlineDescr;
    }

    public void setBeginOnlineDescr(String beginOnlineDescr) {
        this.beginOnlineDescr = beginOnlineDescr;
    }

    public String getActualInvestOverTime() {
        return actualInvestOverTime;
    }

    public void setActualInvestOverTime(String actualInvestOverTime) {
        this.actualInvestOverTime = actualInvestOverTime;
    }

    public String getActualInvestTime() {
        return actualInvestTime;
    }

    public void setActualInvestTime(String actualInvestTime) {
        this.actualInvestTime = actualInvestTime;
    }

    public String getDueDays() {
        return dueDays;
    }

    public void setDueDays(String dueDays) {
        this.dueDays = dueDays;
    }

    public String getBackMoney() {
        return backMoney;
    }

    public void setBackMoney(String backMoney) {
        this.backMoney = backMoney;
    }

    public String getActualVoteOverTime() {
        return actualVoteOverTime;
    }

    public void setActualVoteOverTime(String actualVoteOverTime) {
        this.actualVoteOverTime = actualVoteOverTime;
    }

    public String getSurplustime() {
        return surplustime;
    }

    public void setSurplustime(String surplustime) {
        this.surplustime = surplustime;
    }

    public String getActualSaleTime() {
        return actualSaleTime;
    }

    public void setActualSaleTime(String actualSaleTime) {
        this.actualSaleTime = actualSaleTime;
    }

    public static class UserInvestInfoBean {
        /**
         * investMoney : 300.00
         * investInterest : 0
         * isInvest : 1
         */

        private String investMoney;
        private String investInterest;
        private int isInvest;

        public String getInvestMoney() {
            return investMoney;
        }

        public void setInvestMoney(String investMoney) {
            this.investMoney = investMoney;
        }

        public String getInvestInterest() {
            return investInterest;
        }

        public void setInvestInterest(String investInterest) {
            this.investInterest = investInterest;
        }

        public int getIsInvest() {
            return isInvest;
        }

        public void setIsInvest(int isInvest) {
            this.isInvest = isInvest;
        }
    }
}
