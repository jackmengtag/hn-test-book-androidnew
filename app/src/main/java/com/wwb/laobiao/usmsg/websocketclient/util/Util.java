package com.wwb.laobiao.usmsg.websocketclient.util;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

public class Util {
//    public static final String ws = "ws://8.129.110.148:8081/";//
    public static final String ws = "ws://8.129.110.148:8081/";//ServiceConfig
//    public static final String ws = "ws://39.108.150.225:8080/";  dd
    //public static final String ws =ws://8.129.110.148:8081/{110}/{eyJhbGciOiJIUzUxMiJ9.eyJsb2dpbl9hY2NvdW50X2tleSI6IjEyNWQyMzk1LWYxY2MtNDljNC04ZjUwLTdkZTNmNjNiMzYxMiJ9.vuJCps_T6bMYF2nHo5fwgDWTsDMghF5shrDDJbRsGvvUgsmeCeWRHpBM1pMlX2r-lgZF4LJ-FSGZXWICexOtMQ}
//    public static final String ws = "ws://39.108.150.225:8081/19/eyJhbGciOiJIUzUxMiJ9.eyJsb2dpbl9hY2NvdW50X2tleSI6ImIxMjI4ZGY5LTNmMDYtNGIwZC05YzZhLTg4MWU2YmE3OGFkOSJ9.yLkSGVdptgLaONPVl6OYBz4l9zqDAltR67Ub6P4U-OvjU-mtyRq5TxHLVLPBSSSQxtor7LjZ6MNHKLTm5FXHiA";//websocket测试地址  39.108.150.225:8081
//    public static final String ws = "ws://echo.websocket.org";//websocket测试地址  39.108.150.225:8081
//    public static final String ws = "ws://39.108.150.225:8080/websocket/onOpen";//websocket测试地址
    public static void showToast(Context ctx, String msg) {
        Toast.makeText(ctx, msg, Toast.LENGTH_LONG).show();
    }
    public static String getwburl(String ips, String tokens) {
//        return  ws;
//        String str0=String.format("%s{%s}/{%s}",ws,ips,tokens);
        String str0=String.format("%s%s/%s",ws,ips,tokens);
        Log.e("ws",str0);
        return str0;
    }
    public static String getsendmsg(String ips,String msg) {
        return  String.format("{\"targetUserId\":\"%s\",\n" +
                " \"content\":\"%s\"}",ips,msg);

    }
    public static String getrecmsg(String message) {
        String userhd="userId=";
        if(message.indexOf(userhd)>0)
        {
            return  getusermsg(message);
        }
        String hdstr0="content\":\"";
        int len0 = message.indexOf(hdstr0);
        if (len0 < 0)
        {
            return "t:"+message;
        }
        int len1=message.indexOf("\",\"resultMsg");
        if (len0 +hdstr0.length()> len1)
        {
            return message;
        }
        return  message.substring(len0+hdstr0.length(),len1);
    }

    private static String getusermsg(String message) {
//        User(userId=19, userName=admin, userAvatar=/, msg=uuuu日本)
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
}
