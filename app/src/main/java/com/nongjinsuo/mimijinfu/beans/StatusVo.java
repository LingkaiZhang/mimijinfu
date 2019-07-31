package com.nongjinsuo.mimijinfu.beans;

/**
 * @author czz
 * @Description: (用一句话描述)
 */
public class StatusVo {

    /**
     * status : 3
     * status_data : {"isInvest":0,"investMoney":0,"isVote":0,"userinvestprogress":0}
     */

    private int status;
    /**
     * isInvest : 0
     * investMoney : 0
     * isVote : 0
     * userinvestprogress : 0
     */

    private StatusDataBean status_data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public StatusDataBean getStatus_data() {
        return status_data;
    }

    public void setStatus_data(StatusDataBean status_data) {
        this.status_data = status_data;
    }

    private String shareTitle;
    private String shareSummary;
    private String shareUrl;
    private String detailUrl;
    private String shareImage;
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

    public String getShareImage() {
        return shareImage;
    }

    public void setShareImage(String shareImage) {
        this.shareImage = shareImage;
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

    public static class StatusDataBean {
        private int isInvest;
        private String investMoney;
        private int isVote;
        private String userinvestprogress;
        private String surplustime;
        private int isCertificate;
        private int isPayPwd;
        private int isBindBankcard;
        private String carSaleMoney;
        private String actualRate;
        private String surplusMoney;
        private int isbackStatus;

        public int getIsbackStatus() {
            return isbackStatus;
        }

        public void setIsbackStatus(int isbackStatus) {
            this.isbackStatus = isbackStatus;
        }

        public String getSurplusMoney() {
            return surplusMoney;
        }

        public void setSurplusMoney(String surplusMoney) {
            this.surplusMoney = surplusMoney;
        }

        public String getActualRate() {
            return actualRate;
        }

        public void setActualRate(String actualRate) {
            this.actualRate = actualRate;
        }

        public String getCarSaleMoney() {
            return carSaleMoney;
        }

        public void setCarSaleMoney(String carSaleMoney) {
            this.carSaleMoney = carSaleMoney;
        }

        public int getIsBindBankcard() {
            return isBindBankcard;
        }

        public void setIsBindBankcard(int isBindBankcard) {
            this.isBindBankcard = isBindBankcard;
        }

        public String getExpectInterest() {
            return expectInterest;
        }

        public void setExpectInterest(String expectInterest) {
            this.expectInterest = expectInterest;
        }

        private String expectInterest;

        public int getIsPayPwd() {
            return isPayPwd;
        }

        public void setIsPayPwd(int isPayPwd) {
            this.isPayPwd = isPayPwd;
        }

        public int getIsCertificate() {
            return isCertificate;
        }

        public void setIsCertificate(int isCertificate) {
            this.isCertificate = isCertificate;
        }

        public String getSurplustime() {
            return surplustime;
        }

        public void setSurplustime(String surplustime) {
            this.surplustime = surplustime;
        }

        public int getIsInvest() {
            return isInvest;
        }

        public void setIsInvest(int isInvest) {
            this.isInvest = isInvest;
        }

        public String getInvestMoney() {
            return investMoney;
        }

        public void setInvestMoney(String investMoney) {
            this.investMoney = investMoney;
        }

        public int getIsVote() {
            return isVote;
        }

        public void setIsVote(int isVote) {
            this.isVote = isVote;
        }

        public String getUserinvestprogress() {
            return userinvestprogress;
        }

        public void setUserinvestprogress(String userinvestprogress) {
            this.userinvestprogress = userinvestprogress;
        }
    }
}
