package com.nongjinsuo.mimijinfu.httpmodel;

import java.io.Serializable;
import java.util.List;

/**
 * description: (描述)
 * autour: czz
 * date: 2017/12/8 0008 下午 6:10
 * update: 2017/12/8 0008
 */

public class MyP2pTouZiModel {

    /**
     * code : 200
     * text : 操作成功
     * result : {"userproject":[{"projectBm":"815124431999784","period":"D20171205-0001","needMoney":"33300.00","actualInvestOverTime":"0000-00-00 00:00:00","investMoney":5000,"surplusMoney":"27400.00","interestDate":"0000-00-00","id":"505","name":"定期 3月 D20171205-0001","duraSn":"0","duraNum":"3","nextBackMoneyDate":null,"investTime":"2017-12-08 10:35:10","backInterest":0,"backStatus":"0","status":"1","overTime":"2018-03-05 11:06:39","state":1,"stateName":"项目上线","investBtn":"立即投资","periodname":"定期 3月 D20171205-0001","projectClass":null,"backRate":"%","investMoneyUnit":"元","backInterestUnit":"","dueDays":17508,"nextBackTime":"1970-02-01","restMoney":2.74,"restMoneyUnit":"万","interestTime":"1970-01-01"},{"projectBm":"815126306382094","period":"D20171207-0004","needMoney":"1000.00","actualInvestOverTime":"2017-12-07 15:11:56","investMoney":100,"surplusMoney":"0.00","interestDate":"2017-12-08","id":"520","name":"定期 3月 D20171207-0004","duraSn":"0","duraNum":"3","nextBackMoneyDate":"2018-01-08","investTime":"2017-12-07 15:11:17","backInterest":0,"backStatus":"0","status":"2","overTime":"2018-03-08 00:00:00","state":3,"stateName":"开始计息","investBtn":"回款中（0/3）","periodname":"定期 3月 D20171207-0004","projectClass":null,"backRate":"%","investMoneyUnit":"元","backInterestUnit":"","dueDays":17508,"nextBackTime":"2018-01-08","restMoney":0,"restMoneyUnit":"","interestTime":"2017-12-08"},{"projectBm":"815125530161004","period":"D20171206-0004","needMoney":"1000.00","actualInvestOverTime":"2017-12-07 14:40:53","investMoney":900,"surplusMoney":"0.00","interestDate":"2017-12-08","id":"514","name":"定期 12月 D20171206-0004","duraSn":"0","duraNum":"12","nextBackMoneyDate":"2018-01-08","investTime":"2017-12-07 14:40:52","backInterest":0,"backStatus":"0","status":"2","overTime":"2018-12-08 00:00:00","state":3,"stateName":"开始计息","investBtn":"回款中（0/12）","periodname":"定期 12月 D20171206-0004","projectClass":null,"backRate":"%","investMoneyUnit":"元","backInterestUnit":"","dueDays":17508,"nextBackTime":"2018-01-08","restMoney":0,"restMoneyUnit":"","interestTime":"2017-12-08"},{"projectBm":"815125455084320","period":"X20171206-0001","needMoney":"50000.00","actualInvestOverTime":"0000-00-00 00:00:00","investMoney":100,"surplusMoney":"49500.00","interestDate":"0000-00-00","id":"509","name":"新手标 6月 X20171206-0001","duraSn":"0","duraNum":"6","nextBackMoneyDate":null,"investTime":"2017-12-07 11:53:00","backInterest":0,"backStatus":"0","status":"1","overTime":"2018-06-06 15:31:48","state":1,"stateName":"项目上线","investBtn":"立即投资","periodname":"新手标 6月 X20171206-0001","projectClass":null,"backRate":"%","investMoneyUnit":"元","backInterestUnit":"","dueDays":17508,"nextBackTime":"1970-02-01","restMoney":4.95,"restMoneyUnit":"万","interestTime":"1970-01-01"},{"projectBm":"815125612355193","period":"D20171206-0005","needMoney":"1000.00","actualInvestOverTime":"2017-12-06 19:57:34","investMoney":100,"surplusMoney":"0.00","interestDate":"2017-12-07","id":"516","name":"定期 3月 D20171206-0005","duraSn":"0","duraNum":"3","nextBackMoneyDate":"2018-01-07","investTime":"2017-12-06 19:56:19","backInterest":0,"backStatus":"0","status":"2","overTime":"2018-03-07 00:00:00","state":4,"stateName":"回款中","investBtn":"回款中（0/3）","periodname":"定期 3月 D20171206-0005","projectClass":null,"backRate":"%","investMoneyUnit":"元","backInterestUnit":"","dueDays":17508,"nextBackTime":"2018-01-07","restMoney":0,"restMoneyUnit":"","interestTime":"2017-12-07"},{"projectBm":"815123847058677","period":"D20171204-0007","needMoney":"1000.00","actualInvestOverTime":"2017-12-04 18:54:06","investMoney":500,"surplusMoney":"0.00","interestDate":"2017-12-05","id":"502","name":"定期 3月 D20171204-0007","duraSn":"1","duraNum":"3","nextBackMoneyDate":"2018-02-05","investTime":"2017-12-04 18:52:16","backInterest":5.21,"backStatus":"3","status":"2","overTime":"2018-03-05 00:00:00","state":4,"stateName":"回款中","investBtn":"回款中（1/3）","periodname":"定期 3月 D20171204-0007","projectClass":null,"backRate":"%","investMoneyUnit":"元","backInterestUnit":"元","dueDays":17508,"nextBackTime":"2018-02-05","restMoney":0,"restMoneyUnit":"","interestTime":"2017-12-05"},{"projectBm":"815121288734446","period":"D20171201-0011","needMoney":"1200.00","actualInvestOverTime":"2017-12-04 10:05:26","investMoney":1000,"surplusMoney":"0.00","interestDate":"2017-12-05","id":"495","name":"定期 3月 D20171201-0011","duraSn":"0","duraNum":"3","nextBackMoneyDate":"2018-01-05","investTime":"2017-12-04 10:05:01","backInterest":0,"backStatus":"0","status":"2","overTime":"2018-03-05 00:00:00","state":4,"stateName":"回款中","investBtn":"回款中（0/3）","periodname":"定期 3月 D20171201-0011","projectClass":null,"backRate":"%","investMoneyUnit":"元","backInterestUnit":"","dueDays":17508,"nextBackTime":"2018-01-05","restMoney":0,"restMoneyUnit":"","interestTime":"2017-12-05"}],"projectcount":7,"sumbackInterest":7700}
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
         * userproject : [{"projectBm":"815124431999784","period":"D20171205-0001","needMoney":"33300.00","actualInvestOverTime":"0000-00-00 00:00:00","investMoney":5000,"surplusMoney":"27400.00","interestDate":"0000-00-00","id":"505","name":"定期 3月 D20171205-0001","duraSn":"0","duraNum":"3","nextBackMoneyDate":null,"investTime":"2017-12-08 10:35:10","backInterest":0,"backStatus":"0","status":"1","overTime":"2018-03-05 11:06:39","state":1,"stateName":"项目上线","investBtn":"立即投资","periodname":"定期 3月 D20171205-0001","projectClass":null,"backRate":"%","investMoneyUnit":"元","backInterestUnit":"","dueDays":17508,"nextBackTime":"1970-02-01","restMoney":2.74,"restMoneyUnit":"万","interestTime":"1970-01-01"},{"projectBm":"815126306382094","period":"D20171207-0004","needMoney":"1000.00","actualInvestOverTime":"2017-12-07 15:11:56","investMoney":100,"surplusMoney":"0.00","interestDate":"2017-12-08","id":"520","name":"定期 3月 D20171207-0004","duraSn":"0","duraNum":"3","nextBackMoneyDate":"2018-01-08","investTime":"2017-12-07 15:11:17","backInterest":0,"backStatus":"0","status":"2","overTime":"2018-03-08 00:00:00","state":3,"stateName":"开始计息","investBtn":"回款中（0/3）","periodname":"定期 3月 D20171207-0004","projectClass":null,"backRate":"%","investMoneyUnit":"元","backInterestUnit":"","dueDays":17508,"nextBackTime":"2018-01-08","restMoney":0,"restMoneyUnit":"","interestTime":"2017-12-08"},{"projectBm":"815125530161004","period":"D20171206-0004","needMoney":"1000.00","actualInvestOverTime":"2017-12-07 14:40:53","investMoney":900,"surplusMoney":"0.00","interestDate":"2017-12-08","id":"514","name":"定期 12月 D20171206-0004","duraSn":"0","duraNum":"12","nextBackMoneyDate":"2018-01-08","investTime":"2017-12-07 14:40:52","backInterest":0,"backStatus":"0","status":"2","overTime":"2018-12-08 00:00:00","state":3,"stateName":"开始计息","investBtn":"回款中（0/12）","periodname":"定期 12月 D20171206-0004","projectClass":null,"backRate":"%","investMoneyUnit":"元","backInterestUnit":"","dueDays":17508,"nextBackTime":"2018-01-08","restMoney":0,"restMoneyUnit":"","interestTime":"2017-12-08"},{"projectBm":"815125455084320","period":"X20171206-0001","needMoney":"50000.00","actualInvestOverTime":"0000-00-00 00:00:00","investMoney":100,"surplusMoney":"49500.00","interestDate":"0000-00-00","id":"509","name":"新手标 6月 X20171206-0001","duraSn":"0","duraNum":"6","nextBackMoneyDate":null,"investTime":"2017-12-07 11:53:00","backInterest":0,"backStatus":"0","status":"1","overTime":"2018-06-06 15:31:48","state":1,"stateName":"项目上线","investBtn":"立即投资","periodname":"新手标 6月 X20171206-0001","projectClass":null,"backRate":"%","investMoneyUnit":"元","backInterestUnit":"","dueDays":17508,"nextBackTime":"1970-02-01","restMoney":4.95,"restMoneyUnit":"万","interestTime":"1970-01-01"},{"projectBm":"815125612355193","period":"D20171206-0005","needMoney":"1000.00","actualInvestOverTime":"2017-12-06 19:57:34","investMoney":100,"surplusMoney":"0.00","interestDate":"2017-12-07","id":"516","name":"定期 3月 D20171206-0005","duraSn":"0","duraNum":"3","nextBackMoneyDate":"2018-01-07","investTime":"2017-12-06 19:56:19","backInterest":0,"backStatus":"0","status":"2","overTime":"2018-03-07 00:00:00","state":4,"stateName":"回款中","investBtn":"回款中（0/3）","periodname":"定期 3月 D20171206-0005","projectClass":null,"backRate":"%","investMoneyUnit":"元","backInterestUnit":"","dueDays":17508,"nextBackTime":"2018-01-07","restMoney":0,"restMoneyUnit":"","interestTime":"2017-12-07"},{"projectBm":"815123847058677","period":"D20171204-0007","needMoney":"1000.00","actualInvestOverTime":"2017-12-04 18:54:06","investMoney":500,"surplusMoney":"0.00","interestDate":"2017-12-05","id":"502","name":"定期 3月 D20171204-0007","duraSn":"1","duraNum":"3","nextBackMoneyDate":"2018-02-05","investTime":"2017-12-04 18:52:16","backInterest":5.21,"backStatus":"3","status":"2","overTime":"2018-03-05 00:00:00","state":4,"stateName":"回款中","investBtn":"回款中（1/3）","periodname":"定期 3月 D20171204-0007","projectClass":null,"backRate":"%","investMoneyUnit":"元","backInterestUnit":"元","dueDays":17508,"nextBackTime":"2018-02-05","restMoney":0,"restMoneyUnit":"","interestTime":"2017-12-05"},{"projectBm":"815121288734446","period":"D20171201-0011","needMoney":"1200.00","actualInvestOverTime":"2017-12-04 10:05:26","investMoney":1000,"surplusMoney":"0.00","interestDate":"2017-12-05","id":"495","name":"定期 3月 D20171201-0011","duraSn":"0","duraNum":"3","nextBackMoneyDate":"2018-01-05","investTime":"2017-12-04 10:05:01","backInterest":0,"backStatus":"0","status":"2","overTime":"2018-03-05 00:00:00","state":4,"stateName":"回款中","investBtn":"回款中（0/3）","periodname":"定期 3月 D20171201-0011","projectClass":null,"backRate":"%","investMoneyUnit":"元","backInterestUnit":"","dueDays":17508,"nextBackTime":"2018-01-05","restMoney":0,"restMoneyUnit":"","interestTime":"2017-12-05"}]
         * projectcount : 7
         * sumbackInterest : 7700
         */

        private int projectcount;
        private int sumbackInterest;
        private String investMoney;
        private String investUnit;
        private String backInterestMoney;
        private String backInterestUnit;
        private String count;

        public String getInvestMoney() {
            return investMoney;
        }

        public void setInvestMoney(String investMoney) {
            this.investMoney = investMoney;
        }

        public String getInvestUnit() {
            return investUnit;
        }

        public void setInvestUnit(String investUnit) {
            this.investUnit = investUnit;
        }

        public String getBackInterestMoney() {
            return backInterestMoney;
        }

        public void setBackInterestMoney(String backInterestMoney) {
            this.backInterestMoney = backInterestMoney;
        }

        public String getBackInterestUnit() {
            return backInterestUnit;
        }

        public void setBackInterestUnit(String backInterestUnit) {
            this.backInterestUnit = backInterestUnit;
        }

        public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
        }

        private List<UserprojectBean> userproject;

        public int getProjectcount() {
            return projectcount;
        }

        public void setProjectcount(int projectcount) {
            this.projectcount = projectcount;
        }

        public int getSumbackInterest() {
            return sumbackInterest;
        }

        public void setSumbackInterest(int sumbackInterest) {
            this.sumbackInterest = sumbackInterest;
        }

        public List<UserprojectBean> getUserproject() {
            return userproject;
        }

        public void setUserproject(List<UserprojectBean> userproject) {
            this.userproject = userproject;
        }

        public static class UserprojectBean implements Serializable{
            /**
             * projectBm : 815124431999784
             * period : D20171205-0001
             * needMoney : 33300.00
             * actualInvestOverTime : 0000-00-00 00:00:00
             * investMoney : 5000
             * surplusMoney : 27400.00
             * interestDate : 0000-00-00
             * id : 505
             * name : 定期 3月 D20171205-0001
             * duraSn : 0
             * duraNum : 3
             * nextBackMoneyDate : null
             * investTime : 2017-12-08 10:35:10
             * backInterest : 0
             * backStatus : 0
             * status : 1
             * overTime : 2018-03-05 11:06:39
             * state : 1
             * stateName : 项目上线
             * investBtn : 立即投资
             * periodname : 定期 3月 D20171205-0001
             * projectClass : null
             * backRate : %
             * investMoneyUnit : 元
             * backInterestUnit :
             * dueDays : 17508
             * nextBackTime : 1970-02-01
             * restMoney : 2.74
             * restMoneyUnit : 万
             * interestTime : 1970-01-01
             */

            private String projectBm;
            private String period;
            private String needMoney;
            private String actualInvestOverTime;
            private String investMoney;
            private String investMoney2;
            private String surplusMoney;
            private String interestDate;
            private String id;
            private String name;
            private String duraSn;
            private String duraNum;
            private Object nextBackMoneyDate;
            private String investTime;
            private String backInterest;
            private String backStatus;
            private String status;
            private String overTime;
            private int state;
            private String stateName;
            private String investBtn;
            private String periodname;
            private Object projectClass;
            private String backRate;
            private String investMoneyUnit;
            private String backInterestUnit;
            private int dueDays;
            private String nextBackTime;
            private double restMoney;
            private String restMoneyUnit;
            private String interestTime;
            private String projectRate;
            private String projectDay;
            private String projectUnit;
            private String revenue;
            private String receive;
            private String collect;

            public String getInvestMoney2() {
                return investMoney2;
            }

            public void setInvestMoney2(String investMoney2) {
                this.investMoney2 = investMoney2;
            }

            public String getProjectRate() {
                return projectRate;
            }

            public void setProjectRate(String projectRate) {
                this.projectRate = projectRate;
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

            public String getRevenue() {
                return revenue;
            }

            public void setRevenue(String revenue) {
                this.revenue = revenue;
            }

            public String getReceive() {
                return receive;
            }

            public void setReceive(String receive) {
                this.receive = receive;
            }

            public String getCollect() {
                return collect;
            }

            public void setCollect(String collect) {
                this.collect = collect;
            }

            public String getProjectBm() {
                return projectBm;
            }

            public void setProjectBm(String projectBm) {
                this.projectBm = projectBm;
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

            public String getActualInvestOverTime() {
                return actualInvestOverTime;
            }

            public void setActualInvestOverTime(String actualInvestOverTime) {
                this.actualInvestOverTime = actualInvestOverTime;
            }

            public String getInvestMoney() {
                return investMoney;
            }

            public void setInvestMoney(String investMoney) {
                this.investMoney = investMoney;
            }

            public String getSurplusMoney() {
                return surplusMoney;
            }

            public void setSurplusMoney(String surplusMoney) {
                this.surplusMoney = surplusMoney;
            }

            public String getInterestDate() {
                return interestDate;
            }

            public void setInterestDate(String interestDate) {
                this.interestDate = interestDate;
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

            public String getDuraSn() {
                return duraSn;
            }

            public void setDuraSn(String duraSn) {
                this.duraSn = duraSn;
            }

            public String getDuraNum() {
                return duraNum;
            }

            public void setDuraNum(String duraNum) {
                this.duraNum = duraNum;
            }

            public Object getNextBackMoneyDate() {
                return nextBackMoneyDate;
            }

            public void setNextBackMoneyDate(Object nextBackMoneyDate) {
                this.nextBackMoneyDate = nextBackMoneyDate;
            }

            public String getInvestTime() {
                return investTime;
            }

            public void setInvestTime(String investTime) {
                this.investTime = investTime;
            }

            public String getBackInterest() {
                return backInterest;
            }

            public void setBackInterest(String backInterest) {
                this.backInterest = backInterest;
            }

            public String getBackStatus() {
                return backStatus;
            }

            public void setBackStatus(String backStatus) {
                this.backStatus = backStatus;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getOverTime() {
                return overTime;
            }

            public void setOverTime(String overTime) {
                this.overTime = overTime;
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

            public String getPeriodname() {
                return periodname;
            }

            public void setPeriodname(String periodname) {
                this.periodname = periodname;
            }

            public Object getProjectClass() {
                return projectClass;
            }

            public void setProjectClass(Object projectClass) {
                this.projectClass = projectClass;
            }

            public String getBackRate() {
                return backRate;
            }

            public void setBackRate(String backRate) {
                this.backRate = backRate;
            }

            public String getInvestMoneyUnit() {
                return investMoneyUnit;
            }

            public void setInvestMoneyUnit(String investMoneyUnit) {
                this.investMoneyUnit = investMoneyUnit;
            }

            public String getBackInterestUnit() {
                return backInterestUnit;
            }

            public void setBackInterestUnit(String backInterestUnit) {
                this.backInterestUnit = backInterestUnit;
            }

            public int getDueDays() {
                return dueDays;
            }

            public void setDueDays(int dueDays) {
                this.dueDays = dueDays;
            }

            public String getNextBackTime() {
                return nextBackTime;
            }

            public void setNextBackTime(String nextBackTime) {
                this.nextBackTime = nextBackTime;
            }

            public double getRestMoney() {
                return restMoney;
            }

            public void setRestMoney(double restMoney) {
                this.restMoney = restMoney;
            }

            public String getRestMoneyUnit() {
                return restMoneyUnit;
            }

            public void setRestMoneyUnit(String restMoneyUnit) {
                this.restMoneyUnit = restMoneyUnit;
            }

            public String getInterestTime() {
                return interestTime;
            }

            public void setInterestTime(String interestTime) {
                this.interestTime = interestTime;
            }
        }
    }
}
