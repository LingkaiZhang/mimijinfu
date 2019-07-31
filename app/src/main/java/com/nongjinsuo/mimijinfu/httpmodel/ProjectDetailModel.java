package com.nongjinsuo.mimijinfu.httpmodel;

/**
 * @author czz
 * @Description: (用一句话描述)
 */
public class ProjectDetailModel{

    /**
     * code : 200
     * text : 返回成功
     * result : {"name":"第1224期-丰田XT","carImageFocus":"/Public/upfile/temp/2016-11-22/1479777767_75972259.png?560*360,/Public/upfile/temp/2016-11-22/1479777768_390807858.png?125*84,/Public/upfile/temp/2016-11-22/1479777769_217378936.png?125*84,/Public/upfile/temp/2016-11-22/1479777770_1506375408.png?125*84,/Public/upfile/temp/2016-11-22/1479777770_1550827457.png?125*84","needMoney":"200000","investMoney":"30000","buyBackMoney":"230000","rate":"","estimateSaledays":"0","carOwnerClass":"1","carOwner":"王秀","carCity":"北京","carOwnerBirth":"1993","carOwnerWork":"","estimatecrowdfundingdays":"0","minBuyMoney":"200","surplusMoney":170000,"investprogress":10}
     */

    private String code;
    private String text;
    /**
     * name : 第1224期-丰田XT
     * carImageFocus : /Public/upfile/temp/2016-11-22/1479777767_75972259.png?560*360,/Public/upfile/temp/2016-11-22/1479777768_390807858.png?125*84,/Public/upfile/temp/2016-11-22/1479777769_217378936.png?125*84,/Public/upfile/temp/2016-11-22/1479777770_1506375408.png?125*84,/Public/upfile/temp/2016-11-22/1479777770_1550827457.png?125*84
     * needMoney : 200000
     * investMoney : 30000
     * buyBackMoney : 230000
     * rate :
     * estimateSaledays : 0
     * carOwnerClass : 1
     * carOwner : 王秀
     * carCity : 北京
     * carOwnerBirth : 1993
     * carOwnerWork :
     * estimatecrowdfundingdays : 0
     * minBuyMoney : 200
     * surplusMoney : 170000
     * investprogress : 10
     */

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
        private String name;
        private String carImageFocus;
        private String needMoney;
        private String investMoney;
        private String buyBackMoney;
        private String rate;
        private String estimateSaledays;
        private int carOwnerClass;
        private String carOwner;
        private String carCity;
        private String carOwnerBirth;
        private String carOwnerWork;
        private String estimatecrowdfundingdays;
        private String minBuyMoney;
        private String surplusMoney;
        private String investprogress;
        private String surplustime;
        private int status;
        private String investPeopleNum;
        private String actualMoney;
        private String safeIntro;
        private int projectClass;
        private String needUnit;

//         "partTitle":"平均期限",
//         "partSubTitle":"56天",
//         "partUrl":"",
        private String partTitle;
        private String partSubTitle;
        private String partUrl;

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

        public String getNeedUnit() {
            return needUnit;
        }

        public void setNeedUnit(String needUnit) {
            this.needUnit = needUnit;
        }

        public int getProjectClass() {
            return projectClass;
        }

        public void setProjectClass(int projectClass) {
            this.projectClass = projectClass;
        }

        public String getSafeIntro() {
            return safeIntro;
        }

        public void setSafeIntro(String safeIntro) {
            this.safeIntro = safeIntro;
        }

        public String getActualMoney() {
            return actualMoney;
        }

        public void setActualMoney(String actualMoney) {
            this.actualMoney = actualMoney;
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

        public String getSurplustime() {
            return surplustime;
        }

        public void setSurplustime(String surplustime) {
            this.surplustime = surplustime;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCarImageFocus() {
            return carImageFocus;
        }

        public void setCarImageFocus(String carImageFocus) {
            this.carImageFocus = carImageFocus;
        }

        public String getNeedMoney() {
            return needMoney;
        }

        public void setNeedMoney(String needMoney) {
            this.needMoney = needMoney;
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

        public String getRate() {
            return rate;
        }

        public void setRate(String rate) {
            this.rate = rate;
        }

        public String getEstimateSaledays() {
            return estimateSaledays;
        }

        public void setEstimateSaledays(String estimateSaledays) {
            this.estimateSaledays = estimateSaledays;
        }

        public int getCarOwnerClass() {
            return carOwnerClass;
        }

        public void setCarOwnerClass(int carOwnerClass) {
            this.carOwnerClass = carOwnerClass;
        }

        public String getCarOwner() {
            return carOwner;
        }

        public void setCarOwner(String carOwner) {
            this.carOwner = carOwner;
        }

        public String getCarCity() {
            return carCity;
        }

        public void setCarCity(String carCity) {
            this.carCity = carCity;
        }

        public String getCarOwnerBirth() {
            return carOwnerBirth;
        }

        public void setCarOwnerBirth(String carOwnerBirth) {
            this.carOwnerBirth = carOwnerBirth;
        }

        public String getCarOwnerWork() {
            return carOwnerWork;
        }

        public void setCarOwnerWork(String carOwnerWork) {
            this.carOwnerWork = carOwnerWork;
        }

        public String getEstimatecrowdfundingdays() {
            return estimatecrowdfundingdays;
        }

        public void setEstimatecrowdfundingdays(String estimatecrowdfundingdays) {
            this.estimatecrowdfundingdays = estimatecrowdfundingdays;
        }

        public String getMinBuyMoney() {
            return minBuyMoney;
        }

        public void setMinBuyMoney(String minBuyMoney) {
            this.minBuyMoney = minBuyMoney;
        }

        public String getSurplusMoney() {
            return surplusMoney;
        }

        public void setSurplusMoney(String surplusMoney) {
            this.surplusMoney = surplusMoney;
        }

        public String getInvestprogress() {
            return investprogress;
        }

        public void setInvestprogress(String investprogress) {
            this.investprogress = investprogress;
        }
    }
}
