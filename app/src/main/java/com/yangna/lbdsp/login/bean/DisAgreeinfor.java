package com.yangna.lbdsp.login.bean;

public class DisAgreeinfor {
    private long decideAccountId;//19
    private long inviteAccountId;//29

    public void setdecide(String userId) {
        try {
            decideAccountId=Long.valueOf(userId);
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    public void setinvite(String userId) {
        try {
            inviteAccountId=Long.valueOf(userId);
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    public void setdecideid(long decideAccountId) {
        this.decideAccountId=decideAccountId;
    }

    public void setinviteid(long inviteAccountId) {
        this.inviteAccountId=inviteAccountId;
    }
}
