package com.nongjinsuo.mimijinfu.beans;

import java.io.Serializable;
import java.util.List;

/**
 * @author czz
 * @Description: (用一句话描述)
 */
public class UserProjectVo {

    /**
     * userproject : [{"projectBm":"14815990612290","carImageCover":"/Public/upfile/temp/2016-12-13/1481599040_249475205.jpg","period":"0010","name":" AMG GT R","investMoney":"100.00","needMoney":"1250000.00","status":"1","periodname":"0010  AMG GT R"},{"projectBm":"14810812627925","carImageCover":"/Public/upfile/temp/2016-12-07/1481081049_1834697154.jpg","period":"0004","name":"福特 ShelbyGT500","investMoney":"100.00","needMoney":"1080000.00","status":"1","periodname":"0004 福特 ShelbyGT500"},{"projectBm":"14810812627925","carImageCover":"/Public/upfile/temp/2016-12-07/1481081049_1834697154.jpg","period":"0004","name":"福特 ShelbyGT500","investMoney":"100.00","needMoney":"1080000.00","status":"1","periodname":"0004 福特 ShelbyGT500"}]
     * projectcount : 3
     * suminvestMoney : 300.00
     */

    private String projectcount;
    private String sumbackInterest;
    /**
     * projectBm : 14815990612290
     * carImageCover : /Public/upfile/temp/2016-12-13/1481599040_249475205.jpg
     * period : 0010
     * name :  AMG GT R
     * investMoney : 100.00
     * needMoney : 1250000.00
     * status : 1
     * periodname : 0010  AMG GT R
     */

    private List<UserprojectBean> userproject;

    public String getProjectcount() {
        return projectcount;
    }

    public void setProjectcount(String projectcount) {
        this.projectcount = projectcount;
    }

    public String getSumbackInterest() {
        return sumbackInterest;
    }

    public void setSumbackInterest(String sumbackInterest) {
        this.sumbackInterest = sumbackInterest;
    }

    public List<UserprojectBean> getUserproject() {
        return userproject;
    }

    public void setUserproject(List<UserprojectBean> userproject) {
        this.userproject = userproject;
    }

    public static class UserprojectBean implements Serializable{
        private String projectBm;
        private String carImageCover;
        private String period;
        private String name;
        private String investMoney;
        private String investMoney2;

        public String getInvestMoney2() {
            return investMoney2;
        }

        public void setInvestMoney2(String investMoney2) {
            this.investMoney2 = investMoney2;
        }

        private String needMoney;
        private int status;
        private String periodname;
        private String backInterest;
        private String backRate;
        private String investTime;
        private String actualInvestOverTime;
        private String actualDuedays;
        private String statusname;
        private String investMoneyNoUnit;
        private String investMoneyUnit;
        private String deadline;
        private String deadlineDescr;

        public String getDeadline() {
            return deadline;
        }

        public void setDeadline(String deadline) {
            this.deadline = deadline;
        }

        public String getDeadlineDescr() {
            return deadlineDescr;
        }

        public void setDeadlineDescr(String deadlineDescr) {
            this.deadlineDescr = deadlineDescr;
        }

        public String getInvestMoneyNoUnit() {
            return investMoneyNoUnit;
        }

        public void setInvestMoneyNoUnit(String investMoneyNoUnit) {
            this.investMoneyNoUnit = investMoneyNoUnit;
        }

        public String getInvestMoneyUnit() {
            return investMoneyUnit;
        }

        public void setInvestMoneyUnit(String investMoneyUnit) {
            this.investMoneyUnit = investMoneyUnit;
        }

        public String getStatusname() {
            return statusname;
        }

        public void setStatusname(String statusname) {
            this.statusname = statusname;
        }

        public String getActualDuedays() {
            return actualDuedays;
        }

        public void setActualDuedays(String actualDuedays) {
            this.actualDuedays = actualDuedays;
        }

        public String getActualInvestOverTime() {
            return actualInvestOverTime;
        }

        public void setActualInvestOverTime(String actualInvestOverTime) {
            this.actualInvestOverTime = actualInvestOverTime;
        }

        public String getInvestTime() {
            return investTime;
        }

        public void setInvestTime(String investTime) {
            this.investTime = investTime;
        }

        public String getBackRate() {
            return backRate;
        }

        public void setBackRate(String backRate) {
            this.backRate = backRate;
        }

        public String getBackInterest() {
            return backInterest;
        }

        public void setBackInterest(String backInterest) {
            this.backInterest = backInterest;
        }

        public String getProjectBm() {
            return projectBm;
        }

        public void setProjectBm(String projectBm) {
            this.projectBm = projectBm;
        }

        public String getCarImageCover() {
            return carImageCover;
        }

        public void setCarImageCover(String carImageCover) {
            this.carImageCover = carImageCover;
        }

        public String getPeriod() {
            return period;
        }

        public void setPeriod(String period) {
            this.period = period;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getInvestMoney() {
            return investMoney;
        }

        public void setInvestMoney(String investMoney) {
            this.investMoney = investMoney;
        }

        public String getNeedMoney() {
            return needMoney;
        }

        public void setNeedMoney(String needMoney) {
            this.needMoney = needMoney;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getPeriodname() {
            return periodname;
        }

        public void setPeriodname(String periodname) {
            this.periodname = periodname;
        }
    }
}
