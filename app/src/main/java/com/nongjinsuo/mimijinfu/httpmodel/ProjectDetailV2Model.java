package com.nongjinsuo.mimijinfu.httpmodel;

/**
 * @author czz
 * @Description: (用一句话描述)
 */
public class ProjectDetailV2Model{

    /**
     * code : 200
     * text : 操作成功
     * result : {"verificationstatus":4,"cacheTime":"2018-01-25 17:16:33","isReturnUrl":"0","name":"H18010057","carImageFocus":"/Public/upfile/project/15167744351627151677442814511516774425_1092404175.jpg","bm":"15167744285030","projectClass":"2","carImages":"","carColor":"1","carEmissin":"1","carMileage":"0","carRegDate":"2018-01-24","carKey":"2","beginOnlineTime":"2018-01-24 14:14:00","actualInvestOverTime":null,"actualSaleTime":null,"actualVoteOverTime":null,"actualOverTime":null,"beginTime":"2018-01-24 14:14:00","actualDuedays":"0","actualRate":"","carDriveLicenseImage":"","CertificateImage":"","importImage":"","tradeCheckImage":"","goodOneImage":"","carCertificateImage":"","car":"宝马X5","classId":"2","ratioMoney":30,"needMoney":"1000","needUnit":"元","investMoney":"300元","buyBackMoney":"0元","carOwnerClass":null,"investprogress":30,"estimatecrowdfundingdays":"0-5天","estimateSaledays":"1-90天","minBuyMoney":"100元","availableMoney":700,"surplusMoney":"700","residueMoney":"700","residueUnit":"元","status":1,"surplustime":334647,"investPeopleNum":"3","rate":"11%","safeIntro":"90天未售,车商承诺按预期年化11%收益回购","partTitle":"平均期限","partSubTitle":"56天","partUrl":"","shareTitle":"H18010057","shareSummary":"H18010057","shareImage":"/Public/upfile/project/15167744353089.jpg","shareUrl":"/Wap/project/index/bm/15167744285030-1","detailUrl":"/Wap/project/index?bm=15167744285030","loginState":0,"loginDescr":""}
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

    public static class ResultBean {
        /**
         * verificationstatus : 4
         * cacheTime : 2018-01-25 17:16:33
         * isReturnUrl : 0
         * name : H18010057
         * carImageFocus : /Public/upfile/project/15167744351627151677442814511516774425_1092404175.jpg
         * bm : 15167744285030
         * projectClass : 2
         * carImages :
         * carColor : 1
         * carEmissin : 1
         * carMileage : 0
         * carRegDate : 2018-01-24
         * carKey : 2
         * beginOnlineTime : 2018-01-24 14:14:00
         * actualInvestOverTime : null
         * actualSaleTime : null
         * actualVoteOverTime : null
         * actualOverTime : null
         * beginTime : 2018-01-24 14:14:00
         * actualDuedays : 0
         * actualRate :
         * carDriveLicenseImage :
         * CertificateImage :
         * importImage :
         * tradeCheckImage :
         * goodOneImage :
         * carCertificateImage :
         * car : 宝马X5
         * classId : 2
         * ratioMoney : 30
         * needMoney : 1000
         * needUnit : 元
         * investMoney : 300元
         * buyBackMoney : 0元
         * carOwnerClass : null
         * investprogress : 30
         * estimatecrowdfundingdays : 0-5天
         * estimateSaledays : 1-90天
         * minBuyMoney : 100元
         * availableMoney : 700
         * surplusMoney : 700
         * residueMoney : 700
         * residueUnit : 元
         * status : 1
         * surplustime : 334647
         * investPeopleNum : 3
         * rate : 11%
         * safeIntro : 90天未售,车商承诺按预期年化11%收益回购
         * partTitle : 平均期限
         * partSubTitle : 56天
         * partUrl :
         * shareTitle : H18010057
         * shareSummary : H18010057
         * shareImage : /Public/upfile/project/15167744353089.jpg
         * shareUrl : /Wap/project/index/bm/15167744285030-1
         * detailUrl : /Wap/project/index?bm=15167744285030
         * loginState : 0
         * loginDescr :
         */

        private int verificationstatus;
        private String name;
        private String bm;
        private String beginTime;
        private String actualDuedays;
        private String actualRate;
        private String carDriveLicenseImage;
        private String CertificateImage;
        private String importImage;
        private String tradeCheckImage;
        private String goodOneImage;
        private String carCertificateImage;
        private String car;
        private String classId;
        private int ratioMoney;
        private String needMoney;
        private String needUnit;
        private String investMoney;
        private String buyBackMoney;
        private Object carOwnerClass;
        private int investprogress;
        private String estimatecrowdfundingdays;
        private String estimateSaledays;
        private String minBuyMoney;
        private int availableMoney;
        private String surplusMoney;
        private String residueMoney;
        private String residueUnit;
        private int status;
        private String statusname;
        private String surplustime;
        private String investPeopleNum;
        private String rate;
        private String safeIntro;
        private String partTitle;
        private String partSubTitle;
        private String partUrl;
        private String shareTitle;
        private String shareSummary;
        private String shareImage;
        private String shareUrl;
        private String detailUrl;
        private int loginState;
        private String loginDescr;
        private String investDuraDays;

        public String getInvestDuraDays() {
            return investDuraDays;
        }

        public void setInvestDuraDays(String investDuraDays) {
            this.investDuraDays = investDuraDays;
        }

        public String getStatusname() {
            return statusname;
        }

        public void setStatusname(String statusname) {
            this.statusname = statusname;
        }

        public int getVerificationstatus() {
            return verificationstatus;
        }

        public void setVerificationstatus(int verificationstatus) {
            this.verificationstatus = verificationstatus;
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
        public String getBeginTime() {
            return beginTime;
        }

        public void setBeginTime(String beginTime) {
            this.beginTime = beginTime;
        }

        public String getActualDuedays() {
            return actualDuedays;
        }

        public void setActualDuedays(String actualDuedays) {
            this.actualDuedays = actualDuedays;
        }

        public String getActualRate() {
            return actualRate;
        }

        public void setActualRate(String actualRate) {
            this.actualRate = actualRate;
        }

        public String getCarDriveLicenseImage() {
            return carDriveLicenseImage;
        }

        public void setCarDriveLicenseImage(String carDriveLicenseImage) {
            this.carDriveLicenseImage = carDriveLicenseImage;
        }

        public String getCertificateImage() {
            return CertificateImage;
        }

        public void setCertificateImage(String CertificateImage) {
            this.CertificateImage = CertificateImage;
        }

        public String getImportImage() {
            return importImage;
        }

        public void setImportImage(String importImage) {
            this.importImage = importImage;
        }

        public String getTradeCheckImage() {
            return tradeCheckImage;
        }

        public void setTradeCheckImage(String tradeCheckImage) {
            this.tradeCheckImage = tradeCheckImage;
        }

        public String getGoodOneImage() {
            return goodOneImage;
        }

        public void setGoodOneImage(String goodOneImage) {
            this.goodOneImage = goodOneImage;
        }

        public String getCarCertificateImage() {
            return carCertificateImage;
        }

        public void setCarCertificateImage(String carCertificateImage) {
            this.carCertificateImage = carCertificateImage;
        }

        public String getCar() {
            return car;
        }

        public void setCar(String car) {
            this.car = car;
        }

        public String getClassId() {
            return classId;
        }

        public void setClassId(String classId) {
            this.classId = classId;
        }

        public int getRatioMoney() {
            return ratioMoney;
        }

        public void setRatioMoney(int ratioMoney) {
            this.ratioMoney = ratioMoney;
        }

        public String getNeedMoney() {
            return needMoney;
        }

        public void setNeedMoney(String needMoney) {
            this.needMoney = needMoney;
        }

        public String getNeedUnit() {
            return needUnit;
        }

        public void setNeedUnit(String needUnit) {
            this.needUnit = needUnit;
        }

        public String getInvestMoney() {
            return investMoney;
        }

        public void setInvestMoney(String investMoney) {
            this.investMoney = investMoney;
        }

        public String getBuyBackMoney() {
            return buyBackMoney;
        }

        public void setBuyBackMoney(String buyBackMoney) {
            this.buyBackMoney = buyBackMoney;
        }

        public Object getCarOwnerClass() {
            return carOwnerClass;
        }

        public void setCarOwnerClass(Object carOwnerClass) {
            this.carOwnerClass = carOwnerClass;
        }

        public int getInvestprogress() {
            return investprogress;
        }

        public void setInvestprogress(int investprogress) {
            this.investprogress = investprogress;
        }

        public String getEstimatecrowdfundingdays() {
            return estimatecrowdfundingdays;
        }

        public void setEstimatecrowdfundingdays(String estimatecrowdfundingdays) {
            this.estimatecrowdfundingdays = estimatecrowdfundingdays;
        }

        public String getEstimateSaledays() {
            return estimateSaledays;
        }

        public void setEstimateSaledays(String estimateSaledays) {
            this.estimateSaledays = estimateSaledays;
        }

        public String getMinBuyMoney() {
            return minBuyMoney;
        }

        public void setMinBuyMoney(String minBuyMoney) {
            this.minBuyMoney = minBuyMoney;
        }

        public int getAvailableMoney() {
            return availableMoney;
        }

        public void setAvailableMoney(int availableMoney) {
            this.availableMoney = availableMoney;
        }

        public String getSurplusMoney() {
            return surplusMoney;
        }

        public void setSurplusMoney(String surplusMoney) {
            this.surplusMoney = surplusMoney;
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

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getSurplustime() {
            return surplustime;
        }

        public void setSurplustime(String surplustime) {
            this.surplustime = surplustime;
        }

        public String getInvestPeopleNum() {
            return investPeopleNum;
        }

        public void setInvestPeopleNum(String investPeopleNum) {
            this.investPeopleNum = investPeopleNum;
        }

        public String getRate() {
            return rate;
        }

        public void setRate(String rate) {
            this.rate = rate;
        }

        public String getSafeIntro() {
            return safeIntro;
        }

        public void setSafeIntro(String safeIntro) {
            this.safeIntro = safeIntro;
        }

        public String getPartTitle() {
            return partTitle;
        }

        public void setPartTitle(String partTitle) {
            this.partTitle = partTitle;
        }

        public String getPartSubTitle() {
            return partSubTitle;
        }

        public void setPartSubTitle(String partSubTitle) {
            this.partSubTitle = partSubTitle;
        }

        public String getPartUrl() {
            return partUrl;
        }

        public void setPartUrl(String partUrl) {
            this.partUrl = partUrl;
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
