package com.nongjinsuo.mimijinfu.beans;

import java.util.List;

/**
 * description: (描述)
 * autour: czz
 * date: 2017/12/13 0013 下午 4:46
 * update: 2017/12/13 0013
 */

public class ReturnedMoneyModel {

    /**
     * code : 200
     * text : 操作成功
     * result : [{"day":[{"invest":2000,"receiveBack":0,"collectBack":0,"date":"2017-12-01","color":"blue","receiveBackMoney":0,"receiveBackUnit":"元","collectBackMoney":0,"collectBackUnit":"元","investMoney":2000,"investUnit":"元"},{"invest":2000,"receiveBack":0,"collectBack":0,"date":"2017-12-04","color":"blue","receiveBackMoney":0,"receiveBackUnit":"元","collectBackMoney":0,"collectBackUnit":"元","investMoney":2000,"investUnit":"元"},{"invest":1700,"receiveBack":0,"collectBack":0,"date":"2017-12-05","color":"blue","receiveBackMoney":0,"receiveBackUnit":"元","collectBackMoney":0,"collectBackUnit":"元","investMoney":1700,"investUnit":"元"},{"invest":800,"receiveBack":0,"collectBack":0,"date":"2017-12-06","color":"blue","receiveBackMoney":0,"receiveBackUnit":"元","collectBackMoney":0,"collectBackUnit":"元","investMoney":800,"investUnit":"元"},{"invest":2000,"receiveBack":0,"collectBack":0,"date":"2017-12-07","color":"blue","receiveBackMoney":0,"receiveBackUnit":"元","collectBackMoney":0,"collectBackUnit":"元","investMoney":2000,"investUnit":"元"},{"invest":600,"receiveBack":0,"collectBack":0,"date":"2017-12-08","color":"blue","receiveBackMoney":0,"receiveBackUnit":"元","collectBackMoney":0,"collectBackUnit":"元","investMoney":600,"investUnit":"元"},{"invest":6800,"receiveBack":0,"collectBack":0,"date":"2017-12-09","color":"blue","receiveBackMoney":0,"receiveBackUnit":"元","collectBackMoney":0,"collectBackUnit":"元","investMoney":6800,"investUnit":"元"},{"invest":5400,"receiveBack":0,"collectBack":0,"date":"2017-12-11","color":"blue","receiveBackMoney":0,"receiveBackUnit":"元","collectBackMoney":0,"collectBackUnit":"元","investMoney":5400,"investUnit":"元"}],"receiveBack":0,"collectBack":0,"invest":21300,"date":"2017-12","receiveBackMoney":0,"receiveBackUnit":"元","collectBackMoney":0,"collectBackUnit":"元","investMoney":2.13,"investUnit":"万"}]
     */

    private String code;
    private String text;
    private List<ResultBean> result;

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

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * day : [{"invest":2000,"receiveBack":0,"collectBack":0,"date":"2017-12-01","color":"blue","receiveBackMoney":0,"receiveBackUnit":"元","collectBackMoney":0,"collectBackUnit":"元","investMoney":2000,"investUnit":"元"},{"invest":2000,"receiveBack":0,"collectBack":0,"date":"2017-12-04","color":"blue","receiveBackMoney":0,"receiveBackUnit":"元","collectBackMoney":0,"collectBackUnit":"元","investMoney":2000,"investUnit":"元"},{"invest":1700,"receiveBack":0,"collectBack":0,"date":"2017-12-05","color":"blue","receiveBackMoney":0,"receiveBackUnit":"元","collectBackMoney":0,"collectBackUnit":"元","investMoney":1700,"investUnit":"元"},{"invest":800,"receiveBack":0,"collectBack":0,"date":"2017-12-06","color":"blue","receiveBackMoney":0,"receiveBackUnit":"元","collectBackMoney":0,"collectBackUnit":"元","investMoney":800,"investUnit":"元"},{"invest":2000,"receiveBack":0,"collectBack":0,"date":"2017-12-07","color":"blue","receiveBackMoney":0,"receiveBackUnit":"元","collectBackMoney":0,"collectBackUnit":"元","investMoney":2000,"investUnit":"元"},{"invest":600,"receiveBack":0,"collectBack":0,"date":"2017-12-08","color":"blue","receiveBackMoney":0,"receiveBackUnit":"元","collectBackMoney":0,"collectBackUnit":"元","investMoney":600,"investUnit":"元"},{"invest":6800,"receiveBack":0,"collectBack":0,"date":"2017-12-09","color":"blue","receiveBackMoney":0,"receiveBackUnit":"元","collectBackMoney":0,"collectBackUnit":"元","investMoney":6800,"investUnit":"元"},{"invest":5400,"receiveBack":0,"collectBack":0,"date":"2017-12-11","color":"blue","receiveBackMoney":0,"receiveBackUnit":"元","collectBackMoney":0,"collectBackUnit":"元","investMoney":5400,"investUnit":"元"}]
         * receiveBack : 0
         * collectBack : 0
         * invest : 21300
         * date : 2017-12
         * receiveBackMoney : 0
         * receiveBackUnit : 元
         * collectBackMoney : 0
         * collectBackUnit : 元
         * investMoney : 2.13
         * investUnit : 万
         */

        private String receiveBack;
        private String collectBack;
        private String invest;
        private String date;
        private String receiveBackMoney;
        private String receiveBackUnit;
        private String collectBackMoney;
        private String collectBackUnit;
        private double investMoney;
        private String investUnit;


        private List<DayBean> day;

        public String getReceiveBack() {
            return receiveBack;
        }

        public void setReceiveBack(String receiveBack) {
            this.receiveBack = receiveBack;
        }

        public String getCollectBack() {
            return collectBack;
        }

        public void setCollectBack(String collectBack) {
            this.collectBack = collectBack;
        }

        public String getInvest() {
            return invest;
        }

        public void setInvest(String invest) {
            this.invest = invest;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getReceiveBackMoney() {
            return receiveBackMoney;
        }

        public void setReceiveBackMoney(String receiveBackMoney) {
            this.receiveBackMoney = receiveBackMoney;
        }

        public String getReceiveBackUnit() {
            return receiveBackUnit;
        }

        public void setReceiveBackUnit(String receiveBackUnit) {
            this.receiveBackUnit = receiveBackUnit;
        }

        public String getCollectBackMoney() {
            return collectBackMoney;
        }

        public void setCollectBackMoney(String collectBackMoney) {
            this.collectBackMoney = collectBackMoney;
        }

        public String getCollectBackUnit() {
            return collectBackUnit;
        }

        public void setCollectBackUnit(String collectBackUnit) {
            this.collectBackUnit = collectBackUnit;
        }

        public double getInvestMoney() {
            return investMoney;
        }

        public void setInvestMoney(double investMoney) {
            this.investMoney = investMoney;
        }

        public String getInvestUnit() {
            return investUnit;
        }

        public void setInvestUnit(String investUnit) {
            this.investUnit = investUnit;
        }

        public List<DayBean> getDay() {
            return day;
        }

        public void setDay(List<DayBean> day) {
            this.day = day;
        }

        public static class DayBean {
            /**
             * invest : 2000
             * receiveBack : 0
             * collectBack : 0
             * date : 2017-12-01
             * color : blue
             * receiveBackMoney : 0
             * receiveBackUnit : 元
             * collectBackMoney : 0
             * collectBackUnit : 元
             * investMoney : 2000
             * investUnit : 元
             */

            private String invest;
            private String receiveBack;
            private String collectBack;
            private String date;
            private String color;
            private String receiveBackMoney;
            private String receiveBackUnit;
            private String collectBackMoney;
            private String collectBackUnit;
            private String investMoney;
            private String investUnit;

            public String getInvest() {
                return invest;
            }

            public void setInvest(String invest) {
                this.invest = invest;
            }

            public String getReceiveBack() {
                return receiveBack;
            }

            public void setReceiveBack(String receiveBack) {
                this.receiveBack = receiveBack;
            }

            public String getCollectBack() {
                return collectBack;
            }

            public void setCollectBack(String collectBack) {
                this.collectBack = collectBack;
            }

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public String getColor() {
                return color;
            }

            public void setColor(String color) {
                this.color = color;
            }

            public String getReceiveBackMoney() {
                return receiveBackMoney;
            }

            public void setReceiveBackMoney(String receiveBackMoney) {
                this.receiveBackMoney = receiveBackMoney;
            }

            public String getReceiveBackUnit() {
                return receiveBackUnit;
            }

            public void setReceiveBackUnit(String receiveBackUnit) {
                this.receiveBackUnit = receiveBackUnit;
            }

            public String getCollectBackMoney() {
                return collectBackMoney;
            }

            public void setCollectBackMoney(String collectBackMoney) {
                this.collectBackMoney = collectBackMoney;
            }

            public String getCollectBackUnit() {
                return collectBackUnit;
            }

            public void setCollectBackUnit(String collectBackUnit) {
                this.collectBackUnit = collectBackUnit;
            }

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
        }
    }
}
