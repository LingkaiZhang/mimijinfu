package com.nongjinsuo.mimijinfu.beans;

import java.io.Serializable;

/**
 * @author czz
 * @Description: (银行卡)
 */
public class BankVo implements Serializable{


    /**
     * bankName : 工商银行
     * bankImage :
     * perPayMaxNum : 50000
     * perDayMaxNum : 50000
     * bankId : 1
     * perMonthMaxNum : 0
     * firstPayMaxNum : 50000
     * perCashMaxNum : 50000
     * perDayCashMaxNum : 500000
     */

    private String bankName;
    private String bankImage;
    private String perPayMaxNum;
    private String perDayMaxNum;
    private String bankId;
    private String perMonthMaxNum;
    private String firstPayMaxNum;
    private String perCashMaxNum;
    private String perDayCashMaxNum;
    private String cardNo;
    private String identityName;
    private String perBuyMaxNum;
    private String bankNo;
    private String lastCardNo;
    private String cardId;

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getLastCardNo() {
        return lastCardNo;
    }

    public void setLastCardNo(String lastCardNo) {
        this.lastCardNo = lastCardNo;
    }

    public String getPerBuyMaxNum() {
        return perBuyMaxNum;
    }

    public void setPerBuyMaxNum(String perBuyMaxNum) {
        this.perBuyMaxNum = perBuyMaxNum;
    }

    public String getBankNo() {
        return bankNo;
    }

    public void setBankNo(String bankNo) {
        this.bankNo = bankNo;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getIdentityName() {
        return identityName;
    }

    public void setIdentityName(String identityName) {
        this.identityName = identityName;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankImage() {
        return bankImage;
    }

    public void setBankImage(String bankImage) {
        this.bankImage = bankImage;
    }

    public String getPerPayMaxNum() {
        return perPayMaxNum;
    }

    public void setPerPayMaxNum(String perPayMaxNum) {
        this.perPayMaxNum = perPayMaxNum;
    }

    public String getPerDayMaxNum() {
        return perDayMaxNum;
    }

    public void setPerDayMaxNum(String perDayMaxNum) {
        this.perDayMaxNum = perDayMaxNum;
    }

    public String getBankId() {
        return bankId;
    }

    public void setBankId(String bankId) {
        this.bankId = bankId;
    }

    public String getPerMonthMaxNum() {
        return perMonthMaxNum;
    }

    public void setPerMonthMaxNum(String perMonthMaxNum) {
        this.perMonthMaxNum = perMonthMaxNum;
    }

    public String getFirstPayMaxNum() {
        return firstPayMaxNum;
    }

    public void setFirstPayMaxNum(String firstPayMaxNum) {
        this.firstPayMaxNum = firstPayMaxNum;
    }

    public String getPerCashMaxNum() {
        return perCashMaxNum;
    }

    public void setPerCashMaxNum(String perCashMaxNum) {
        this.perCashMaxNum = perCashMaxNum;
    }

    public String getPerDayCashMaxNum() {
        return perDayCashMaxNum;
    }

    public void setPerDayCashMaxNum(String perDayCashMaxNum) {
        this.perDayCashMaxNum = perDayCashMaxNum;
    }
}
