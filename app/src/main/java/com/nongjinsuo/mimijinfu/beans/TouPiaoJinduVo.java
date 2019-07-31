package com.nongjinsuo.mimijinfu.beans;

import java.util.List;

/**
 * @author czz
 * @Description: (用一句话描述)
 */
public class TouPiaoJinduVo {

    /**
     * status : 3
     * beginTime : 2016-11-10 00:00:00
     * actualInvestOverTime : null
     * actualSaleTime : 1970-01-01 08:00:00
     * arrvote : [{"carSaleMoney":"买方出价","supportNum":"支持率","pubTime":"项目进展时间"}]
     * actualVoteOverTime : 实际投票结束时间
     * actualOverTime : 实际项目结束时间
     */

    private int status;
    private String beginTime;
    private String actualInvestOverTime;
    private String actualSaleTime;
    private String actualVoteOverTime;
    private String actualOverTime;
    /**
     * carSaleMoney : 买方出价
     * supportNum : 支持率
     * pubTime : 项目进展时间
     */

    private List<ArrvoteBean> arrvote;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getActualInvestOverTime() {
        return actualInvestOverTime;
    }

    public void setActualInvestOverTime(String actualInvestOverTime) {
        this.actualInvestOverTime = actualInvestOverTime;
    }

    public String getActualSaleTime() {
        return actualSaleTime;
    }

    public void setActualSaleTime(String actualSaleTime) {
        this.actualSaleTime = actualSaleTime;
    }

    public String getActualVoteOverTime() {
        return actualVoteOverTime;
    }

    public void setActualVoteOverTime(String actualVoteOverTime) {
        this.actualVoteOverTime = actualVoteOverTime;
    }

    public String getActualOverTime() {
        return actualOverTime;
    }

    public void setActualOverTime(String actualOverTime) {
        this.actualOverTime = actualOverTime;
    }

    public List<ArrvoteBean> getArrvote() {
        return arrvote;
    }

    public void setArrvote(List<ArrvoteBean> arrvote) {
        this.arrvote = arrvote;
    }

    public static class ArrvoteBean {
        private String carSaleMoney;
        private String supportNum;
        private String pubTime;
        private String actualRate;

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

        public String getSupportNum() {
            return supportNum;
        }

        public void setSupportNum(String supportNum) {
            this.supportNum = supportNum;
        }

        public String getPubTime() {
            return pubTime;
        }

        public void setPubTime(String pubTime) {
            this.pubTime = pubTime;
        }
    }
}
