package com.yangna.lbdsp.login.bean;

public class Agreeinfor {
    private long decideAccountId;
    private long inviteAccountId;

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

    public void setdecideid(long getdecideAccountId) {
        decideAccountId=getdecideAccountId;
    }

    public void setinviteid(long getinviteAccountId) {
        inviteAccountId=getinviteAccountId;
    }
}
