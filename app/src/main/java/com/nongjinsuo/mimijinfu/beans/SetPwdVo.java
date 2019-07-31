package com.nongjinsuo.mimijinfu.beans;

/**
 * @author czz
 * @Description: (用一句话描述)
 */
public class SetPwdVo {

    /**
     * passWordStatus : 1
     * payPwdStatus : 0
     */

    private int passWordStatus;
    private int payPwdStatus;
    private int bankStatus;
    private int riskGrade;
    private int cardId;

    public int getCardId() {
        return cardId;
    }

    public void setCardId(int cardId) {
        this.cardId = cardId;
    }

    public int getRiskGrade() {
        return riskGrade;
    }

    public void setRiskGrade(int riskGrade) {
        this.riskGrade = riskGrade;
    }

    public int getPassWordStatus() {
        return passWordStatus;
    }

    public void setPassWordStatus(int passWordStatus) {
        this.passWordStatus = passWordStatus;
    }

    public int getPayPwdStatus() {
        return payPwdStatus;
    }

    public void setPayPwdStatus(int payPwdStatus) {
        this.payPwdStatus = payPwdStatus;
    }

    public int getBankStatus() {
        return bankStatus;
    }

    public void setBankStatus(int bankStatus) {
        this.bankStatus = bankStatus;
    }
}
