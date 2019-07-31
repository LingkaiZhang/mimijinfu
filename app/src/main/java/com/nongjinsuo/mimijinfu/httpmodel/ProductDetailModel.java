package com.nongjinsuo.mimijinfu.httpmodel;

import java.io.Serializable;

/**
 * description: (描述)
 * autour: czz
 * date: 2017/12/7 0007 下午 4:45
 * update: 2017/12/7 0007
 */

public class ProductDetailModel {


    /**
     * code : 200
     * text : 操作成功
     * result : {"id":"709","name":"3月 D18010047","period":"D18010047","needMoney":"1400","projectDay":"3","bm":"815167855585725","caseId":"117","overTime":"2018-04-24 17:19:18","projectClass":"2","backMoneyClass":"1","backMoneyTime":"0000-00-00 00:00:00","caseClass":"1","projectUnit":"2","investMoney":"0","investPeopleNum":"0","duraNum":"3","interestDate":"0000-00-00","nextBackMoneyDate":null,"status":"1","accountId":"900000001","projectRate":"10.50","pubTime":"2018-01-24 17:19:18","actualInvestOverTime":"0000-00-00 00:00:00","duraSn":"0","beginTime":"2018-01-24 17:19:23","expectInvestOverTime":"2018-01-29 17:19:00","actualOverTime":null,"identityName":"王先生","identityNo":"1****************X","city":"北京","age":"26","sex":"1","hangye":"金融","monthIncome":"250000","otherLoan":"1","creditLevel":"2","company":"上海缘心公司","state":1,"stateName":"项目上线","investBtn":"立即投资","onlineDate":"01-24 17:19","investOverDate":"","beginInterestDate":"","beginInterestTime":"- -","backMoneyDate":"","overDate":"","backMoney":0,"ratioMoney":0,"needUnit":"元","investUnit":"元","residueMoney":"1400","residueUnit":"元","userInvestMoney":"0","userInvestUnit":"元","income":"262.5","backMoneyClassName":"先息后本","caseClassName":"房屋抵押","assure":"机构代偿","productUnit":"个月","shareTitle":"3月 D18010047","shareSummary":"期待年回报率10.50%","shareImage":"/Public/home/images/logo.png","shareUrl":"/Wap/product/index/id/709-1","detailUrl":"/Wap/product/index/id/709-0","loginState":0,"loginDescr":""}
     */

    private String code;
    private String text;
    private ResultBean result;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean implements Serializable{
        /**
         * id : 709
         * name : 3月 D18010047
         * period : D18010047
         * needMoney : 1400
         * projectDay : 3
         * bm : 815167855585725
         * caseId : 117
         * overTime : 2018-04-24 17:19:18
         * projectClass : 2
         * backMoneyClass : 1
         * backMoneyTime : 0000-00-00 00:00:00
         * caseClass : 1
         * projectUnit : 2
         * investMoney : 0
         * investPeopleNum : 0
         * duraNum : 3
         * interestDate : 0000-00-00
         * nextBackMoneyDate : null
         * status : 1
         * accountId : 900000001
         * projectRate : 10.50
         * pubTime : 2018-01-24 17:19:18
         * actualInvestOverTime : 0000-00-00 00:00:00
         * duraSn : 0
         * beginTime : 2018-01-24 17:19:23
         * expectInvestOverTime : 2018-01-29 17:19:00
         * actualOverTime : null
         * identityName : 王先生
         * identityNo : 1****************X
         * city : 北京
         * age : 26
         * sex : 1
         * hangye : 金融
         * monthIncome : 250000
         * otherLoan : 1
         * creditLevel : 2
         * company : 上海缘心公司
         * state : 1
         * stateName : 项目上线
         * investBtn : 立即投资
         * onlineDate : 01-24 17:19
         * investOverDate :
         * beginInterestDate :
         * beginInterestTime : - -
         * backMoneyDate :
         * overDate :
         * backMoney : 0
         * ratioMoney : 0
         * needUnit : 元
         * investUnit : 元
         * residueMoney : 1400
         * residueUnit : 元
         * userInvestMoney : 0
         * userInvestUnit : 元
         * income : 262.5
         * backMoneyClassName : 先息后本
         * caseClassName : 房屋抵押
         * assure : 机构代偿
         * productUnit : 个月
         * shareTitle : 3月 D18010047
         * shareSummary : 期待年回报率10.50%
         * shareImage : /Public/home/images/logo.png
         * shareUrl : /Wap/product/index/id/709-1
         * detailUrl : /Wap/product/index/id/709-0
         * loginState : 0
         * loginDescr :
         */
        private int verificationstatus;
        private String id;
        private String name;
        private String period;
        private String needMoney;
        private int projectDay;
        private String bm;
        private String caseId;
        private String overTime;
        private String projectClass;
        private int backMoneyClass;
        private String backMoneyTime;
        private String caseClass;
        private String projectUnit;
        private String investMoney;
        private String investPeopleNum;
        private String duraNum;
        private String interestDate;
        private Object nextBackMoneyDate;
        private String status;
        private String accountId;
        private double projectRate;
        private String pubTime;
        private String actualInvestOverTime;
        private String duraSn;
        private String beginTime;
        private String expectInvestOverTime;
        private Object actualOverTime;
        private String identityName;
        private String identityNo;
        private String city;
        private String age;
        private String sex;
        private String hangye;
        private String monthIncome;
        private String otherLoan;
        private String creditLevel;
        private String company;
        private int state;
        private String stateName;
        private String investBtn;
        private String onlineDate;
        private String investOverDate;
        private String beginInterestDate;
        private String beginInterestTime;
        private String backMoneyDate;
        private String overDate;
        private String backMoney;
        private int ratioMoney;
        private String needUnit;
        private String investUnit;
        private String residueMoney;
        private String residueUnit;
        private String userInvestMoney;
        private String userInvestUnit;
        private String income;
        private String backMoneyClassName;
        private String caseClassName;
        private String assure;
        private String productUnit;
        private String shareTitle;
        private String shareSummary;
        private String shareImage;
        private String shareUrl;
        private String detailUrl;
        private int loginState;
        private String loginDescr;
        private String minBuyMoney;
        private String investDuraDays;
        private String borrowerUse;

        public String getBorrowerUse() {
            return borrowerUse;
        }

        public void setBorrowerUse(String borrowerUse) {
            this.borrowerUse = borrowerUse;
        }

        public String getMinBuyMoney() {
            return minBuyMoney;
        }

        public void setMinBuyMoney(String minBuyMoney) {
            this.minBuyMoney = minBuyMoney;
        }

        public String getInvestDuraDays() {
            return investDuraDays;
        }

        public void setInvestDuraDays(String investDuraDays) {
            this.investDuraDays = investDuraDays;
        }

        public int getVerificationstatus() {
            return verificationstatus;
        }

        public void setVerificationstatus(int verificationstatus) {
            this.verificationstatus = verificationstatus;
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

        public int getProjectDay() {
            return projectDay;
        }

        public void setProjectDay(int projectDay) {
            this.projectDay = projectDay;
        }

        public String getBm() {
            return bm;
        }

        public void setBm(String bm) {
            this.bm = bm;
        }

        public String getCaseId() {
            return caseId;
        }

        public void setCaseId(String caseId) {
            this.caseId = caseId;
        }

        public String getOverTime() {
            return overTime;
        }

        public void setOverTime(String overTime) {
            this.overTime = overTime;
        }

        public String getProjectClass() {
            return projectClass;
        }

        public void setProjectClass(String projectClass) {
            this.projectClass = projectClass;
        }

        public int getBackMoneyClass() {
            return backMoneyClass;
        }

        public void setBackMoneyClass(int backMoneyClass) {
            this.backMoneyClass = backMoneyClass;
        }

        public String getBackMoneyTime() {
            return backMoneyTime;
        }

        public void setBackMoneyTime(String backMoneyTime) {
            this.backMoneyTime = backMoneyTime;
        }

        public String getCaseClass() {
            return caseClass;
        }

        public void setCaseClass(String caseClass) {
            this.caseClass = caseClass;
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

        public String getDuraNum() {
            return duraNum;
        }

        public void setDuraNum(String duraNum) {
            this.duraNum = duraNum;
        }

        public String getInterestDate() {
            return interestDate;
        }

        public void setInterestDate(String interestDate) {
            this.interestDate = interestDate;
        }

        public Object getNextBackMoneyDate() {
            return nextBackMoneyDate;
        }

        public void setNextBackMoneyDate(Object nextBackMoneyDate) {
            this.nextBackMoneyDate = nextBackMoneyDate;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getAccountId() {
            return accountId;
        }

        public void setAccountId(String accountId) {
            this.accountId = accountId;
        }

        public double getProjectRate() {
            return projectRate;
        }

        public void setProjectRate(double projectRate) {
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

        public String getDuraSn() {
            return duraSn;
        }

        public void setDuraSn(String duraSn) {
            this.duraSn = duraSn;
        }

        public String getBeginTime() {
            return beginTime;
        }

        public void setBeginTime(String beginTime) {
            this.beginTime = beginTime;
        }

        public String getExpectInvestOverTime() {
            return expectInvestOverTime;
        }

        public void setExpectInvestOverTime(String expectInvestOverTime) {
            this.expectInvestOverTime = expectInvestOverTime;
        }

        public Object getActualOverTime() {
            return actualOverTime;
        }

        public void setActualOverTime(Object actualOverTime) {
            this.actualOverTime = actualOverTime;
        }

        public String getIdentityName() {
            return identityName;
        }

        public void setIdentityName(String identityName) {
            this.identityName = identityName;
        }

        public String getIdentityNo() {
            return identityNo;
        }

        public void setIdentityNo(String identityNo) {
            this.identityNo = identityNo;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getHangye() {
            return hangye;
        }

        public void setHangye(String hangye) {
            this.hangye = hangye;
        }

        public String getMonthIncome() {
            return monthIncome;
        }

        public void setMonthIncome(String monthIncome) {
            this.monthIncome = monthIncome;
        }

        public String getOtherLoan() {
            return otherLoan;
        }

        public void setOtherLoan(String otherLoan) {
            this.otherLoan = otherLoan;
        }

        public String getCreditLevel() {
            return creditLevel;
        }

        public void setCreditLevel(String creditLevel) {
            this.creditLevel = creditLevel;
        }

        public String getCompany() {
            return company;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public String getStateName() {
            return stateName;
        }

        public void setStateName(String stateName) {
            this.stateName = stateName;
        }

        public String getInvestBtn() {
            return investBtn;
        }

        public void setInvestBtn(String investBtn) {
            this.investBtn = investBtn;
        }

        public String getOnlineDate() {
            return onlineDate;
        }

        public void setOnlineDate(String onlineDate) {
            this.onlineDate = onlineDate;
        }

        public String getInvestOverDate() {
            return investOverDate;
        }

        public void setInvestOverDate(String investOverDate) {
            this.investOverDate = investOverDate;
        }

        public String getBeginInterestDate() {
            return beginInterestDate;
        }

        public void setBeginInterestDate(String beginInterestDate) {
            this.beginInterestDate = beginInterestDate;
        }

        public String getBeginInterestTime() {
            return beginInterestTime;
        }

        public void setBeginInterestTime(String beginInterestTime) {
            this.beginInterestTime = beginInterestTime;
        }

        public String getBackMoneyDate() {
            return backMoneyDate;
        }

        public void setBackMoneyDate(String backMoneyDate) {
            this.backMoneyDate = backMoneyDate;
        }

        public String getOverDate() {
            return overDate;
        }

        public void setOverDate(String overDate) {
            this.overDate = overDate;
        }

        public String getBackMoney() {
            return backMoney;
        }

        public void setBackMoney(String backMoney) {
            this.backMoney = backMoney;
        }

        public int getRatioMoney() {
            return ratioMoney;
        }

        public void setRatioMoney(int ratioMoney) {
            this.ratioMoney = ratioMoney;
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

        public String getUserInvestMoney() {
            return userInvestMoney;
        }

        public void setUserInvestMoney(String userInvestMoney) {
            this.userInvestMoney = userInvestMoney;
        }

        public String getUserInvestUnit() {
            return userInvestUnit;
        }

        public void setUserInvestUnit(String userInvestUnit) {
            this.userInvestUnit = userInvestUnit;
        }

        public String getIncome() {
            return income;
        }

        public void setIncome(String income) {
            this.income = income;
        }

        public String getBackMoneyClassName() {
            return backMoneyClassName;
        }

        public void setBackMoneyClassName(String backMoneyClassName) {
            this.backMoneyClassName = backMoneyClassName;
        }

        public String getCaseClassName() {
            return caseClassName;
        }

        public void setCaseClassName(String caseClassName) {
            this.caseClassName = caseClassName;
        }

        public String getAssure() {
            return assure;
        }

        public void setAssure(String assure) {
            this.assure = assure;
        }

        public String getProductUnit() {
            return productUnit;
        }

        public void setProductUnit(String productUnit) {
            this.productUnit = productUnit;
        }

        public String getShareTitle() {
            return shareTitle;
        }

        public void setShareTitle(String shareTitle) {
            this.shareTitle = shareTitle;
        }

        public String getShareSummary() {
            return shareSummary;
        }

        public void setShareSummary(String shareSummary) {
            this.shareSummary = shareSummary;
        }

        public String getShareImage() {
            return shareImage;
        }

        public void setShareImage(String shareImage) {
            this.shareImage = shareImage;
        }

        public String getShareUrl() {
            return shareUrl;
        }

        public void setShareUrl(String shareUrl) {
            this.shareUrl = shareUrl;
        }

        public String getDetailUrl() {
            return detailUrl;
        }

        public void setDetailUrl(String detailUrl) {
            this.detailUrl = detailUrl;
        }

        public int getLoginState() {
            return loginState;
        }

        public void setLoginState(int loginState) {
            this.loginState = loginState;
        }

        public String getLoginDescr() {
            return loginDescr;
        }

        public void setLoginDescr(String loginDescr) {
            this.loginDescr = loginDescr;
        }
    }
}

