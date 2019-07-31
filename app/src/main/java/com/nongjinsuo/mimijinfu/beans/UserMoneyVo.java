package com.nongjinsuo.mimijinfu.beans;

/**
 * @author czz
 * @Description: (用一句话描述)
 */
public class UserMoneyVo {


    /**
     * asset : 52062.50
     * balance : 49162.50
     * freezeMoney : 200.00
     * stayBackCapital : 2700.00
     */

    private String asset;
    private String balance;
    private String freezeMoney;
    private String stayBackCapital;
    private String potInterest;
    private String potYestodayInterest;
    private String totalBalance;
    private String totalPotInterest;

    public String getAsset() {
        return asset;
    }

    public void setAsset(String asset) {
        this.asset = asset;
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

    public String getStayBackCapital() {
        return stayBackCapital;
    }

    public void setStayBackCapital(String stayBackCapital) {
        this.stayBackCapital = stayBackCapital;
    }

    public String getPotInterest() {
        return potInterest;
    }

    public void setPotInterest(String potInterest) {
        this.potInterest = potInterest;
    }

    public String getPotYestodayInterest() {
        return potYestodayInterest;
    }

    public void setPotYestodayInterest(String potYestodayInterest) {
        this.potYestodayInterest = potYestodayInterest;
    }

    public String getTotalBalance() {
        return totalBalance;
    }

    public void setTotalBalance(String totalBalance) {
        this.totalBalance = totalBalance;
    }

    public String getTotalPotInterest() {
        return totalPotInterest;
    }

    public void setTotalPotInterest(String totalPotInterest) {
        this.totalPotInterest = totalPotInterest;
    }
}
