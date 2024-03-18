package com.wwb.laobiao.usmsg.websocketclient.bean;

import java.io.Serializable;

public class ChatUser implements Serializable {
    public static final String KEYTRACY = "KEYTRACY";
    public String ips="";
    public String tokens="";
    public String message;
    public String title;
    private long decideAccountId;
    private long inviteAccountId;
    public int sky;

    public  ChatUser()
    {
        decideAccountId=-1;
        inviteAccountId=-1;
        sky=0;
        title="聊天 ";
        ips="188";
        tokens="";
  //      tsettokens(18);
    }
    public String getName(int idex) {

        return  "chat to"+ips;
    }

    public int getIcon(int idex) {
     return 0;
    }

    public void settokens(int idex) {
//        if(idex==18)
//        {
//            tokens="eyJhbGciOiJIUzUxMiJ9.eyJsb2dpbl9hY2NvdW50X2tleSI6IjhhMTBlMmZjLTE2MDgtNGM1MS1hYTI1LTVmMTI3ZTZkYjYzYyJ9.eRI830Mxy4N8Lo8dbSTvPQR6jYVI_0Cb2cfCZb5SU39HBYYYlmbLljMpIU-686gW9yj-ShwHmw_2868PZsPuFw";
//        }
//        else if(idex==19)
//        {
//            tokens="eyJhbGciOiJIUzUxMiJ9.eyJsb2dpbl9hY2NvdW50X2tleSI6ImIxMjI4ZGY5LTNmMDYtNGIwZC05YzZhLTg4MWU2YmE3OGFkOSJ9.yLkSGVdptgLaONPVl6OYBz4l9zqDAltR67Ub6P4U-OvjU-mtyRq5TxHLVLPBSSSQxtor7LjZ6MNHKLTm5FXHiA";
//        }
        if(idex==18)
        {
            tokens="eyJhbGciOiJIUzUxMiJ9.eyJsb2dpbl9hY2NvdW50X2tleSI6IjhhMTBlMmZjLTE2MDgtNGM1MS1hYTI1LTVmMTI3ZTZkYjYzYyJ9.eRI830Mxy4N8Lo8dbSTvPQR6jYVI_0Cb2cfCZb5SU39HBYYYlmbLljMpIU-686gW9yj-ShwHmw_2868PZsPuFw";
        }
        else if(idex==19)
        {
            tokens="eyJhbGciOiJIUzUxMiJ9.eyJsb2dpbl9hY2NvdW50X2tleSI6ImIxMjI4ZGY5LTNmMDYtNGIwZC05YzZhLTg4MWU2YmE3OGFkOSJ9.yLkSGVdptgLaONPVl6OYBz4l9zqDAltR67Ub6P4U-OvjU-mtyRq5TxHLVLPBSSSQxtor7LjZ6MNHKLTm5FXHiA";
        }
    }

    public void setmsg(String message) {
        sky=-1;
        String userhd="userId=";
        if(message.indexOf(userhd)>0)
        {
            this.message=getusermsg(message);
            return;
        }
        String hdstr0="content\":\"";
        int len0 = message.indexOf(hdstr0);
        if (len0 < 0)
        {
            this.message=message;
        }
        int len1=message.indexOf("\",\"resultMsg");
        if (len0 +hdstr0.length()> len1)
        {
            sky=0;
            this.message=message;
        }
    }

    private String getusermsg(String message) {
        sky=1;
        //User(userId=19, userName=admin, userAvatar=/, msg=会里)
        String userIdstr=String.format("userId=%s",ips);
        if(message.indexOf(userIdstr)<0)
        {
            sky=-2;
            return message;
        }
        String hdstr0="msg=";
        int len0=message.indexOf("(");
        if(len0<0)
        {
            return message;
        }
        int len1=message.lastIndexOf(")");
        if(len0+hdstr0.length()>len1)
        {
            return message;
        }
        message=message.substring(len0,len1);
        len0=message.indexOf(hdstr0);
        message=message.substring(len0+hdstr0.length(),message.length());
        return message;
    }

    public void settitle(String s) {
        title=s;
    }

    public long getdecideid() {
       return  decideAccountId;
    }

    public long getinviteid() {
        return  inviteAccountId;
    }

    public void setdecideId(long getdecideAccountId) {
        this.decideAccountId=getdecideAccountId;
    }
    public void setinviteId(long getinviteAccountId) {
        this.inviteAccountId=getinviteAccountId;
    }
}
