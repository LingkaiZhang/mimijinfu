package com.nongjinsuo.mimijinfu.beans;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author czz
 * @Description: (用一句话描述)
 */
public class AvailableaVo implements Serializable {

    /**
     * availableamount : 1000000
     * balance : 117053.00
     */

    private String availableamount;
    private String balance;
    private String cardId;
    private String name;
    private String gmamount;

    private double balance_q;
    private double availableamount_q;
    private double perAuthPay;//单笔最大限额
    private double perDayAuthPay;//单日最大限额
    private double todayInvestMoney;//今日投资金额，当用户设置无限额 ，默认为0，当用户设置限额时，返回今日的投资总金额。
    private String editauthority_redirect_url;
    private ArrayList<RedPacketBean> redpacket;

    public ArrayList<RedPacketBean> getRedPacket() {
        return redpacket;
    }

    public void setRedPacket(ArrayList<RedPacketBean> redPacket) {
        this.redpacket = redPacket;
    }

    public String getEditauthority_redirect_url() {
        return editauthority_redirect_url;
    }

    public void setEditauthority_redirect_url(String editauthority_redirect_url) {
        this.editauthority_redirect_url = editauthority_redirect_url;
    }

    public double getTodayInvestMoney() {
        return todayInvestMoney;
    }

    public void setTodayInvestMoney(double todayInvestMoney) {
        this.todayInvestMoney = todayInvestMoney;
    }

    public double getPerAuthPay() {
        return perAuthPay;
    }

    public void setPerAuthPay(double perAuthPay) {
        this.perAuthPay = perAuthPay;
    }

    public double getPerDayAuthPay() {
        return perDayAuthPay;
    }

    public void setPerDayAuthPay(double perDayAuthPay) {
        this.perDayAuthPay = perDayAuthPay;
    }

    public double getBalance_q() {
        return balance_q;
    }

    public void setBalance_q(double balance_q) {
        this.balance_q = balance_q;
    }

    public double getAvailableamount_q() {
        return availableamount_q;
    }

    public void setAvailableamount_q(double availableamount_q) {
        this.availableamount_q = availableamount_q;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGmamount() {
        return gmamount;
    }

    public void setGmamount(String gmamount) {
        this.gmamount = gmamount;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getAvailableamount() {
        return availableamount;
    }

    public void setAvailableamount(String availableamount) {
        this.availableamount = availableamount;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }
}
