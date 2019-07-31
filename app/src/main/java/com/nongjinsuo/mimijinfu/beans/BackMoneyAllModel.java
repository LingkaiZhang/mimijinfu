package com.nongjinsuo.mimijinfu.beans;

import java.util.List;

/**
 * description: (描述)
 * autour: czz
 * date: 2017/12/12 0012 上午 10:18
 * update: 2017/12/12 0012
 */

public class BackMoneyAllModel {
    /**
     * code : 200
     * text : 操作成功
     * result : {"backMoney":[{"id":"1124","capital":"0.00","interest":"1.04","backMoney":"1.04","backTime":"2018-01-08","duraNum":"3","payStatus":1,"sn":"1","capitalMoney":0,"capitalUnit":"元","interestMoney":1.04,"interestUnit":"元","backMoneyMoney":1.04,"backMoneyUnit":"元"},{"id":"1125","capital":"0.00","interest":"1.04","backMoney":"1.04","backTime":"2018-02-08","duraNum":"3","payStatus":1,"sn":"2","capitalMoney":0,"capitalUnit":"元","interestMoney":1.04,"interestUnit":"元","backMoneyMoney":1.04,"backMoneyUnit":"元"},{"id":"1126","capital":"100.00","interest":"1.05","backMoney":"101.05","backTime":"2018-03-08","duraNum":"3","payStatus":1,"sn":"3","capitalMoney":100,"capitalUnit":"元","interestMoney":1.05,"interestUnit":"元","backMoneyMoney":101.05,"backMoneyUnit":"元"}]}
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
        private String backMoneyClass;
        private String backSumMoney;
        private String backSumUnit;

        public String getBackMoneyClass() {
            return backMoneyClass;
        }

        public void setBackMoneyClass(String backMoneyClass) {
            this.backMoneyClass = backMoneyClass;
        }

        public String getBackSumMoney() {
            return backSumMoney;
        }

        public void setBackSumMoney(String backSumMoney) {
            this.backSumMoney = backSumMoney;
        }

        public String getBackSumUnit() {
            return backSumUnit;
        }

        public void setBackSumUnit(String backSumUnit) {
            this.backSumUnit = backSumUnit;
        }

        private List<BackMoneyBean> backMoney;

        public List<BackMoneyBean> getBackMoney() {
            return backMoney;
        }

        public void setBackMoney(List<BackMoneyBean> backMoney) {
            this.backMoney = backMoney;
        }

    }
}
