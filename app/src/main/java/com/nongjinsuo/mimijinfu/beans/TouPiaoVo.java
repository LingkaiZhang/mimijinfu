package com.nongjinsuo.mimijinfu.beans;

/**
 * @author czz
 * @Description: (用一句话描述)
 */
public class TouPiaoVo {

    /**
     * needMoney : 2500000.00
     * carSaleMoney : 220
     * actualVoteBeginTime : 1970-01-01
     * beginTime : 2016-12-07
     * investMoney : 890000.00
     * opposeNum : 10%
     * supportNum : 70%
     * endDays : 17142
     * income : 2323
     * voteproportion : 70%
     */

    private String needMoney;
    private String carSaleMoney;
    private String actualVoteBeginTime;
    private String beginTime;
    private String investMoney;
    private String opposeNum;
    private String supportNum;
    private String endDays;
    private String income;
    private String voteproportion;
    private String period;
    private String voteBm;
    private int isvote;
    private int isinvest;
    private String endtime;
    private int vote;
    private int status;
    private String projectname;
    private String actualRate; //实际年化

    public String getActualRate() {
        return actualRate;
    }

    public void setActualRate(String actualRate) {
        this.actualRate = actualRate;
    }

    public String getProjectname() {
        return projectname;
    }

    public void setProjectname(String projectname) {
        this.projectname = projectname;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    private String expectVoteOverTime;

    public String getExpectVoteOverTime() {
        return expectVoteOverTime;
    }

    public void setExpectVoteOverTime(String expectVoteOverTime) {
        this.expectVoteOverTime = expectVoteOverTime;
    }

    public int getVote() {
        return vote;
    }

    public void setVote(int vote) {
        this.vote = vote;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public int getIsinvest() {
        return isinvest;
    }

    public void setIsinvest(int isinvest) {
        this.isinvest = isinvest;
    }

    public int getIsvote() {
        return isvote;
    }

    public void setIsvote(int isvote) {
        this.isvote = isvote;
    }

    public String getVoteBm() {
        return voteBm;
    }

    public void setVoteBm(String voteBm) {
        this.voteBm = voteBm;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getNeedMoney() {
        return needMoney;
    }

    public void setNeedMoney(String needMoney) {
        this.needMoney = needMoney;
    }

    public String getCarSaleMoney() {
        return carSaleMoney;
    }

    public void setCarSaleMoney(String carSaleMoney) {
        this.carSaleMoney = carSaleMoney;
    }

    public String getActualVoteBeginTime() {
        return actualVoteBeginTime;
    }

    public void setActualVoteBeginTime(String actualVoteBeginTime) {
        this.actualVoteBeginTime = actualVoteBeginTime;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getInvestMoney() {
        return investMoney;
    }

    public void setInvestMoney(String investMoney) {
        this.investMoney = investMoney;
    }

    public String getOpposeNum() {
        return opposeNum;
    }

    public void setOpposeNum(String opposeNum) {
        this.opposeNum = opposeNum;
    }

    public String getSupportNum() {
        return supportNum;
    }

    public void setSupportNum(String supportNum) {
        this.supportNum = supportNum;
    }

    public String getEndDays() {
        return endDays;
    }

    public void setEndDays(String endDays) {
        this.endDays = endDays;
    }

    public String getIncome() {
        return income;
    }

    public void setIncome(String income) {
        this.income = income;
    }

    public String getVoteproportion() {
        return voteproportion;
    }

    public void setVoteproportion(String voteproportion) {
        this.voteproportion = voteproportion;
    }
}
