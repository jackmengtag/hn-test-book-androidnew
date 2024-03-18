package com.wwb.laobiao.usmsg.websocketclient.bean;

import android.util.Log;

import java.io.Serializable;

public class ChatModel implements Serializable {
    private static final String TAG = ChatModel.class.getName();
    private long decideAccountId;
    private long inviteAccountId;
    private int type;
    private  String Instructions;
    public ChatModel()
    {
        decideAccountId=10029;
        inviteAccountId=10018;
    }
    public void setdecideAccountId(String userId) {
        try {
            decideAccountId=Long.valueOf(userId);
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
    public void settype(String substring) {
        try {
            type=Integer.valueOf(substring);
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    public void setinviteAccountIdId(String substring) {
        try {
            inviteAccountId=Long.valueOf(substring);
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
    public void show() {
        Log.e(TAG,"decide"+decideAccountId+"invite"+inviteAccountId+"type"+type);
    }

    public long getdecideAccountId() {
        return decideAccountId;
    }
    public long getinviteAccountId() {
        return inviteAccountId;
    }

    public String getInstructions() {
        return Instructions;
    }
}
