package com.nongjinsuo.mimijinfu.httpmodel;

import com.nongjinsuo.mimijinfu.beans.BankVo;
import com.nongjinsuo.mimijinfu.beans.BaseVo;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author czz
 * @Description: (用一句话描述)
 */
public class UserverificationModel extends BaseVo{

    public Verification result;


    public class Verification implements Serializable{
        private int verificationstatus;
        private String identityName;
        private String redirect_url;
        private String identityNo;
        private String mobile;
        private ArrayList<BankVo> bindcard;
        private String editpaypwd_redirect_url;
        private String editauthority_redirect_url;
        private int domoneycount;
        private String balance;
        private String ticket;
        private String cardMobile;
        private String maxWithdraw;//当前可提现金额

        public String getMaxWithdraw() {
            return maxWithdraw;
        }

        public void setMaxWithdraw(String maxWithdraw) {
            this.maxWithdraw = maxWithdraw;
        }

        public String getTicket() {
            return ticket;
        }

        public void setTicket(String ticket) {
            this.ticket = ticket;
        }

        public String getCardMobile() {
            return cardMobile;
        }

        public void setCardMobile(String cardMobile) {
            this.cardMobile = cardMobile;
        }

        public String getBalance() {
            return balance;
        }

        public void setBalance(String balance) {
            this.balance = balance;
        }

        public int getDomoneycount() {
            return domoneycount;
        }

        public void setDomoneycount(int domoneycount) {
            this.domoneycount = domoneycount;
        }

        public String getEditpaypwd_redirect_url() {
            return editpaypwd_redirect_url;
        }

        public void setEditpaypwd_redirect_url(String editpaypwd_redirect_url) {
            this.editpaypwd_redirect_url = editpaypwd_redirect_url;
        }

        public String getEditauthority_redirect_url() {
            return editauthority_redirect_url;
        }

        public void setEditauthority_redirect_url(String editauthority_redirect_url) {
            this.editauthority_redirect_url = editauthority_redirect_url;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public ArrayList<BankVo> getBindcard() {
            return bindcard;
        }

        public void setBindcard(ArrayList<BankVo> bindcard) {
            this.bindcard = bindcard;
        }

        public String getIdentityNo() {
            return identityNo;
        }

        public void setIdentityNo(String identityNo) {
            this.identityNo = identityNo;
        }

        public String getRedirect_url() {
            return redirect_url;
        }

        public void setRedirect_url(String redirect_url) {
            this.redirect_url = redirect_url;
        }

        public String getIdentityName() {
            return identityName;
        }

        public void setIdentityName(String identityName) {
            this.identityName = identityName;
        }

        public int getVerificationstatus() {
            return verificationstatus;
        }

        public void setVerificationstatus(int verificationstatus) {
            this.verificationstatus = verificationstatus;
        }
    }
}
