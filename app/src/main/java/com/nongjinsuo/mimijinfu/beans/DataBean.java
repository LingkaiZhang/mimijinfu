package com.nongjinsuo.mimijinfu.beans;

/**
 * description: (描述)
 * autour: czz
 * date: 2018/1/17 0017 上午 9:42
 * update: 2018/1/17 0017
 */

public  class DataBean {
    /**
     * id : 664
     * name : 6月 D18010014
     * bm : 815156547146501
     * needMoney : 30000.00
     * projectDay : 6
     * projectUnit : 2
     * investMoney : 0.00
     * investPeopleNum : 0
     * status : 1
     * projectRate : 9
     * pubTime : 2018-01-11 15:11:54
     * actualInvestOverTime : 0000-00-00 00:00:00
     * backMoneyClass : 1
     * basicRate : 9
     * plusRate :
     * projectClass : 2
     * backMoneyTime : 0000-00-00 00:00:00
     * interestDate : 0000-00-00
     * residueMoney : 3
     * residueUnit : 万
     * residueMoneyDescr : 剩余3万
     * nameSimplify : 定期 6月
     * statusDescr : 去投资
     * backMoneyClassName : 先息后本
     */

    private String id;
    private String projectDay;
    private String projectUnit;
    private String projectRate;
    private String pubTime;
    private String actualInvestOverTime;
    private String backMoneyClass;
    private String basicRate;
    private String plusRate;
    private String backMoneyTime;
    private String interestDate;
    private String residueMoneyDescr;
    private String nameSimplify;
    private String statusDescr;
    private String backMoneyClassName;

    private String bm;
    private String period;
    private String name;
    private String beginTime;
    private String projectClass;
    private String buyBackMoney;
    private String carImageCover;
    private String needMoney;
    private String needUnit;
    private String investMoney;
    private String investUnit;
    private String residueMoney;
    private String residueUnit;
    private int investprogress;
    private int status;
    private String statusname;
    private String surplustime;
    private String rate;
    private String duetime;
    private String raterangemin;
    private String raterangemax;
    private String descr;
    private Object dueDays;
    private String investPeopleNum;
    private String actualRate;
    private String investUsedTime;
    private String deadline;
    private String deadlineDescr;
    private long beginTimeStamp;

    public long getBeginTimeStamp() {
        return beginTimeStamp;
    }

    public void setBeginTimeStamp(long beginTimeStamp) {
        this.beginTimeStamp = beginTimeStamp;
    }

    public String getDeadlineDescr() {
        return deadlineDescr;
    }

    public void setDeadlineDescr(String deadlineDescr) {
        this.deadlineDescr = deadlineDescr;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getBuyBackMoney() {
        return buyBackMoney;
    }

    public void setBuyBackMoney(String buyBackMoney) {
        this.buyBackMoney = buyBackMoney;
    }

    public String getCarImageCover() {
        return carImageCover;
    }

    public void setCarImageCover(String carImageCover) {
        this.carImageCover = carImageCover;
    }

    public String getNeedUnit() {
        return needUnit;
    }

    public void setNeedUnit(String needUnit) {
        this.needUnit = needUnit;
    }

    public String getInvestUnit() {
        return investUnit;
    }

    public void setInvestUnit(String investUnit) {
        this.investUnit = investUnit;
    }

    public int getInvestprogress() {
        return investprogress;
    }

    public void setInvestprogress(int investprogress) {
        this.investprogress = investprogress;
    }

    public String getStatusname() {
        return statusname;
    }

    public void setStatusname(String statusname) {
        this.statusname = statusname;
    }

    public String getSurplustime() {
        return surplustime;
    }

    public void setSurplustime(String surplustime) {
        this.surplustime = surplustime;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getDuetime() {
        return duetime;
    }

    public void setDuetime(String duetime) {
        this.duetime = duetime;
    }

    public String getRaterangemin() {
        return raterangemin;
    }

    public void setRaterangemin(String raterangemin) {
        this.raterangemin = raterangemin;
    }

    public String getRaterangemax() {
        return raterangemax;
    }

    public void setRaterangemax(String raterangemax) {
        this.raterangemax = raterangemax;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public Object getDueDays() {
        return dueDays;
    }

    public void setDueDays(Object dueDays) {
        this.dueDays = dueDays;
    }

    public String getActualRate() {
        return actualRate;
    }

    public void setActualRate(String actualRate) {
        this.actualRate = actualRate;
    }

    public String getInvestUsedTime() {
        return investUsedTime;
    }

    public void setInvestUsedTime(String investUsedTime) {
        this.investUsedTime = investUsedTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBm() {
        return bm;
    }

    public void setBm(String bm) {
        this.bm = bm;
    }

    public String getNeedMoney() {
        return needMoney;
    }

    public void setNeedMoney(String needMoney) {
        this.needMoney = needMoney;
    }

    public String getProjectDay() {
        return projectDay;
    }

    public void setProjectDay(String projectDay) {
        this.projectDay = projectDay;
    }

    public String getProjectUnit() {
        return projectUnit;
    }

    public void setProjectUnit(String projectUnit) {
        this.projectUnit = projectUnit;
    }

    public String getInvestMoney() {
        return investMoney;
    }

    public void setInvestMoney(String investMoney) {
        this.investMoney = investMoney;
    }

    public String getInvestPeopleNum() {
        return investPeopleNum;
    }

    public void setInvestPeopleNum(String investPeopleNum) {
        this.investPeopleNum = investPeopleNum;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getProjectRate() {
        return projectRate;
    }

    public void setProjectRate(String projectRate) {
        this.projectRate = projectRate;
    }

    public String getPubTime() {
        return pubTime;
    }

    public void setPubTime(String pubTime) {
        this.pubTime = pubTime;
    }

    public String getActualInvestOverTime() {
        return actualInvestOverTime;
    }

    public void setActualInvestOverTime(String actualInvestOverTime) {
        this.actualInvestOverTime = actualInvestOverTime;
    }

    public String getBackMoneyClass() {
        return backMoneyClass;
    }

    public void setBackMoneyClass(String backMoneyClass) {
        this.backMoneyClass = backMoneyClass;
    }

    public String getBasicRate() {
        return basicRate;
    }

    public void setBasicRate(String basicRate) {
        this.basicRate = basicRate;
    }

    public String getPlusRate() {
        return plusRate;
    }

    public void setPlusRate(String plusRate) {
        this.plusRate = plusRate;
    }

    public String getProjectClass() {
        return projectClass;
    }

    public void setProjectClass(String projectClass) {
        this.projectClass = projectClass;
    }

    public String getBackMoneyTime() {
        return backMoneyTime;
    }

    public void setBackMoneyTime(String backMoneyTime) {
        this.backMoneyTime = backMoneyTime;
    }

    public String getInterestDate() {
        return interestDate;
    }

    public void setInterestDate(String interestDate) {
        this.interestDate = interestDate;
    }

    public String getResidueMoney() {
        return residueMoney;
    }

    public void setResidueMoney(String residueMoney) {
        this.residueMoney = residueMoney;
    }

    public String getResidueUnit() {
        return residueUnit;
    }

    public void setResidueUnit(String residueUnit) {
        this.residueUnit = residueUnit;
    }

    public String getResidueMoneyDescr() {
        return residueMoneyDescr;
    }

    public void setResidueMoneyDescr(String residueMoneyDescr) {
        this.residueMoneyDescr = residueMoneyDescr;
    }

    public String getNameSimplify() {
        return nameSimplify;
    }

    public void setNameSimplify(String nameSimplify) {
        this.nameSimplify = nameSimplify;
    }

    public String getStatusDescr() {
        return statusDescr;
    }

    public void setStatusDescr(String statusDescr) {
        this.statusDescr = statusDescr;
    }

    public String getBackMoneyClassName() {
        return backMoneyClassName;
    }

    public void setBackMoneyClassName(String backMoneyClassName) {
        this.backMoneyClassName = backMoneyClassName;
    }
}