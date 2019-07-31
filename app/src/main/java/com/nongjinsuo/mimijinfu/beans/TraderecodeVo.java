package com.nongjinsuo.mimijinfu.beans;

import java.util.List;

/**
 * @author czz
 * @Description: (用一句话描述)
 */
public class TraderecodeVo {


    private List<TraderecodeVo> tradeRecode;

    public List<TraderecodeVo> getTradeRecode() {
        return tradeRecode;
    }

    public void setTradeRecode(List<TraderecodeVo> tradeRecode) {
        this.tradeRecode = tradeRecode;
    }

    /**
         * money : 10000.00
         * summary : 充值
         * status : 充值成功
         * pubTime : 2016-12-17
         * classId : deposit
         * className : 充值
         */

        private String money;
        private String summary;
        private String status;
        private String pubTime;
        private String classId;
        private int stylestatus;//1 为成功 2 为失败，3 冻结或者处理中
        private String redpacketMoney;

        public String getRedpacketMoney() {
            return redpacketMoney;
        }

        public void setRedpacketMoney(String redpacketMoney) {
            this.redpacketMoney = redpacketMoney;
        }

        public int getStylestatus() {
            return stylestatus;
        }

        public void setStylestatus(int stylestatus) {
            this.stylestatus = stylestatus;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public String getSummary() {
            return summary;
        }

        public void setSummary(String summary) {
            this.summary = summary;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getPubTime() {
            return pubTime;
        }

        public void setPubTime(String pubTime) {
            this.pubTime = pubTime;
        }

        public String getClassId() {
            return classId;
        }

        public void setClassId(String classId) {
            this.classId = classId;
        }

}
