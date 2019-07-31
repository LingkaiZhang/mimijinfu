package com.nongjinsuo.mimijinfu.beans;

/**
 * @author czz
 * @Description: (用一句话描述)
 */
public class CentralVo {


    /**
     * asset : {"headImg":"/Public/upfile/userimg/default.png?6529","mobile":"17120000000","nickName":"*春光","totalInterest":"231.20","interest":"0.00","interestCash":"0.00","interestNetloan":"0.00","asset":"64412.92","totalBalance":"18212.92","balance":"18212.92","freezeMoney":"46200.00","freezeWithdraw":"0.00","freezeInvest":"46200.00","totalInvestMoney":"600.00","investMoney":"0.00","investMoneyNetloan":"600.00","totalStayBackCapital":"0.00","stayBackCapital":"0.00","stayBackCapitalNetloan":"0.00","stayBackInterest":"0.00","stayBackMoney":"612.93","investProjectNum":"140","investNetloanNum":"2","waitingProjectNum":"0","waitingNetloanNum":"0","potInterest":"231.20","potWithdraw":"207.85","potYestodayInterest":"1.79","potUpdateTime":"2017-12-07","loginTime":"2017-12-07 15:46:17","minimumInvestment":"5","redPacketCount":"3","backMoneyCount":"0"}
     * invest :
     */

    private AssetBean asset;
    private String invest;
    private int loginState;
    private String loginDescr;

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

    public AssetBean getAsset() {
        return asset;
    }

    public void setAsset(AssetBean asset) {
        this.asset = asset;
    }

    public String getInvest() {
        return invest;
    }

    public void setInvest(String invest) {
        this.invest = invest;
    }

    public static class AssetBean {
        /**
         * headImg : /Public/upfile/userimg/default.png?6529
         * mobile : 17120000000
         * nickName : *春光
         * totalInterest : 231.20
         * interest : 0.00
         * interestCash : 0.00
         * interestNetloan : 0.00
         * asset : 64412.92
         * totalBalance : 18212.92
         * balance : 18212.92
         * freezeMoney : 46200.00
         * freezeWithdraw : 0.00
         * freezeInvest : 46200.00
         * totalInvestMoney : 600.00
         * investMoney : 0.00
         * investMoneyNetloan : 600.00
         * totalStayBackCapital : 0.00
         * stayBackCapital : 0.00
         * stayBackCapitalNetloan : 0.00
         * stayBackInterest : 0.00
         * stayBackMoney : 612.93
         * investProjectNum : 140
         * investNetloanNum : 2
         * waitingProjectNum : 0
         * waitingNetloanNum : 0
         * potInterest : 231.20
         * potWithdraw : 207.85
         * potYestodayInterest : 1.79
         * potUpdateTime : 2017-12-07
         * loginTime : 2017-12-07 15:46:17
         * minimumInvestment : 5
         * redPacketCount : 3
         * backMoneyCount : 0
         */

        private String headImg;
        private String mobile;
        private String nickName;
        private String totalInterest;
        private String interest;
        private String interestCash;
        private String interestNetloan;
        private String asset;
        private String totalBalance;
        private String balance;
        private String freezeMoney;
        private String freezeWithdraw;
        private String freezeInvest;
        private String totalInvestMoney;
        private String investMoney;
        private String investMoneyNetloan;
        private String totalStayBackCapital;
        private String stayBackCapital;
        private String stayBackCapitalNetloan;
        private String stayBackInterest;
        private String stayBackMoney;
        private String investProjectNum;
        private String investNetloanNum;
        private String waitingProjectNum;
        private String waitingNetloanNum;
        private String potInterest;
        private String potWithdraw;
        private String potYestodayInterest;
        private String potUpdateTime;
        private String loginTime;
        private String minimumInvestment;
        private int redPacketCount;
        private int backMoneyCount;
        private String registerTime;
        private String registerDay;
        private int assetHiddenStatus;

        public int getAssetHiddenStatus() {
            return assetHiddenStatus;
        }

        public void setAssetHiddenStatus(int assetHiddenStatus) {
            this.assetHiddenStatus = assetHiddenStatus;
        }

        public String getRegisterDay() {
            return registerDay;
        }

        public void setRegisterDay(String registerDay) {
            this.registerDay = registerDay;
        }

        public String getRegisterTime() {
            return registerTime;
        }

        public void setRegisterTime(String registerTime) {
            this.registerTime = registerTime;
        }

        public String getHeadImg() {
            return headImg;
        }

        public void setHeadImg(String headImg) {
            this.headImg = headImg;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public String getTotalInterest() {
            return totalInterest;
        }

        public void setTotalInterest(String totalInterest) {
            this.totalInterest = totalInterest;
        }

        public String getInterest() {
            return interest;
        }

        public void setInterest(String interest) {
            this.interest = interest;
        }

        public String getInterestCash() {
            return interestCash;
        }

        public void setInterestCash(String interestCash) {
            this.interestCash = interestCash;
        }

        public String getInterestNetloan() {
            return interestNetloan;
        }

        public void setInterestNetloan(String interestNetloan) {
            this.interestNetloan = interestNetloan;
        }

        public String getAsset() {
            return asset;
        }

        public void setAsset(String asset) {
            this.asset = asset;
        }

        public String getTotalBalance() {
            return totalBalance;
        }

        public void setTotalBalance(String totalBalance) {
            this.totalBalance = totalBalance;
        }

        public String getBalance() {
            return balance;
        }

        public void setBalance(String balance) {
            this.balance = balance;
        }

        public String getFreezeMoney() {
            return freezeMoney;
        }

        public void setFreezeMoney(String freezeMoney) {
            this.freezeMoney = freezeMoney;
        }

        public String getFreezeWithdraw() {
            return freezeWithdraw;
        }

        public void setFreezeWithdraw(String freezeWithdraw) {
            this.freezeWithdraw = freezeWithdraw;
        }

        public String getFreezeInvest() {
            return freezeInvest;
        }

        public void setFreezeInvest(String freezeInvest) {
            this.freezeInvest = freezeInvest;
        }

        public String getTotalInvestMoney() {
            return totalInvestMoney;
        }

        public void setTotalInvestMoney(String totalInvestMoney) {
            this.totalInvestMoney = totalInvestMoney;
        }

        public String getInvestMoney() {
            return investMoney;
        }

        public void setInvestMoney(String investMoney) {
            this.investMoney = investMoney;
        }

        public String getInvestMoneyNetloan() {
            return investMoneyNetloan;
        }

        public void setInvestMoneyNetloan(String investMoneyNetloan) {
            this.investMoneyNetloan = investMoneyNetloan;
        }

        public String getTotalStayBackCapital() {
            return totalStayBackCapital;
        }

        public void setTotalStayBackCapital(String totalStayBackCapital) {
            this.totalStayBackCapital = totalStayBackCapital;
        }

        public String getStayBackCapital() {
            return stayBackCapital;
        }

        public void setStayBackCapital(String stayBackCapital) {
            this.stayBackCapital = stayBackCapital;
        }

        public String getStayBackCapitalNetloan() {
            return stayBackCapitalNetloan;
        }

        public void setStayBackCapitalNetloan(String stayBackCapitalNetloan) {
            this.stayBackCapitalNetloan = stayBackCapitalNetloan;
        }

        public String getStayBackInterest() {
            return stayBackInterest;
        }

        public void setStayBackInterest(String stayBackInterest) {
            this.stayBackInterest = stayBackInterest;
        }

        public String getStayBackMoney() {
            return stayBackMoney;
        }

        public void setStayBackMoney(String stayBackMoney) {
            this.stayBackMoney = stayBackMoney;
        }

        public String getInvestProjectNum() {
            return investProjectNum;
        }

        public void setInvestProjectNum(String investProjectNum) {
            this.investProjectNum = investProjectNum;
        }

        public String getInvestNetloanNum() {
            return investNetloanNum;
        }

        public void setInvestNetloanNum(String investNetloanNum) {
            this.investNetloanNum = investNetloanNum;
        }

        public String getWaitingProjectNum() {
            return waitingProjectNum;
        }

        public void setWaitingProjectNum(String waitingProjectNum) {
            this.waitingProjectNum = waitingProjectNum;
        }

        public String getWaitingNetloanNum() {
            return waitingNetloanNum;
        }

        public void setWaitingNetloanNum(String waitingNetloanNum) {
            this.waitingNetloanNum = waitingNetloanNum;
        }

        public String getPotInterest() {
            return potInterest;
        }

        public void setPotInterest(String potInterest) {
            this.potInterest = potInterest;
        }

        public String getPotWithdraw() {
            return potWithdraw;
        }

        public void setPotWithdraw(String potWithdraw) {
            this.potWithdraw = potWithdraw;
        }

        public String getPotYestodayInterest() {
            return potYestodayInterest;
        }

        public void setPotYestodayInterest(String potYestodayInterest) {
            this.potYestodayInterest = potYestodayInterest;
        }

        public String getPotUpdateTime() {
            return potUpdateTime;
        }

        public void setPotUpdateTime(String potUpdateTime) {
            this.potUpdateTime = potUpdateTime;
        }

        public String getLoginTime() {
            return loginTime;
        }

        public void setLoginTime(String loginTime) {
            this.loginTime = loginTime;
        }

        public String getMinimumInvestment() {
            return minimumInvestment;
        }

        public void setMinimumInvestment(String minimumInvestment) {
            this.minimumInvestment = minimumInvestment;
        }

        public int getRedPacketCount() {
            return redPacketCount;
        }

        public void setRedPacketCount(int redPacketCount) {
            this.redPacketCount = redPacketCount;
        }

        public int getBackMoneyCount() {
            return backMoneyCount;
        }

        public void setBackMoneyCount(int backMoneyCount) {
            this.backMoneyCount = backMoneyCount;
        }
    }
}
