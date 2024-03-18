package com.wwb.laobiao.common;

import com.wwb.laobiao.usmsg.websocketclient.bean.ChatModel;

import java.util.ArrayList;
import java.util.List;

public class ChatUtil {
    private static ChatUtil instance;
    private List<ChatModel> models;

    /**
     * 构造
     */
    private ChatUtil() {
        models=new ArrayList<>();
    }
    /**
     * 单例
     */
    public static ChatUtil getInstance() {
        if (instance == null) {
            synchronized (ChatUtil.class) {
                if (instance == null) {
                    instance = new ChatUtil();
                }
            }
        }
        return instance;
    }
    public boolean addqingqiu(String customstr) {
//        ChatUser chatUser;
////        ChatModel chatModel;
////        List<ChatModel> models=this.models;
          if(models==null)
          {
              models=new ArrayList<>();
          }
        ChatModel chatModel=new ChatModel();
        //{"decideAccountId":24,"inviteAccountId":23,"type":"0"}
      String decideAccountIdkey="\"decideAccountId\":";
      String inviteAccountIdkey="\"inviteAccountId\":";
      String typekey="\"type\":\"";
      int len0=0;
      int len1=0;
        len0=customstr.indexOf(decideAccountIdkey);if(len0<0){
            return false;
        }
        len0+=decideAccountIdkey.length();
        len1=customstr.indexOf(",");if(len0>=len1){
            return false;
        }
        chatModel.setdecideAccountId(customstr.substring(len0,len1));

        if(customstr.length()<=len1+1){
            return false;
        }
        customstr=customstr.substring(len1+1,customstr.length());

        len0=customstr.indexOf(inviteAccountIdkey);if(len0<0){
            return false;
        }
        len0+=inviteAccountIdkey.length();
        len1=customstr.indexOf(",");if(len0>=len1){
            return false;
        }
        chatModel.setinviteAccountIdId(customstr.substring(len0,len1));

        if(customstr.length()<=len1+1){
            return false;
        }
        customstr=customstr.substring(len1+1,customstr.length());

        len0=customstr.indexOf(typekey);if(len0<0){
            return false;
        }
        len0+=typekey.length();
        len1=customstr.indexOf("\"}");if(len0>=len1){
            return false;
        }
        chatModel.settype(customstr.substring(len0,len1));

        chatModel.show();
        models.add(chatModel);
        return true;

    }
    public ChatModel getlastchat() {
       if(models!=null)
       {
           if(models.size()>0)
           {
           return  models.get(models.size()-1);
           }

       }
       return null;
    }

    public ChatModel getselchat(int selectItem) {
        if(models!=null)
        {
            if(models.size()>0)
            {
                return  models.get(models.size()-1);
            }

        }
        return null;
    }

    public ChatModel getchatModel(String customstr) {
        ChatModel chatModel=new ChatModel();
        //{"decideAccountId":24,"inviteAccountId":23,"type":"0"}
        String decideAccountIdkey="\"decideAccountId\":";
        String inviteAccountIdkey="\"inviteAccountId\":";
        String typekey="\"type\":\"";
        int len0=0;
        int len1=0;
        len0=customstr.indexOf(decideAccountIdkey);if(len0<0){
            return null;
        }
        len0+=decideAccountIdkey.length();
        len1=customstr.indexOf(",");if(len0>=len1){
            return null;
        }
        chatModel.setdecideAccountId(customstr.substring(len0,len1));

        if(customstr.length()<=len1+1){
            return null;
        }
        customstr=customstr.substring(len1+1,customstr.length());

        len0=customstr.indexOf(inviteAccountIdkey);if(len0<0){
            return null;
        }
        len0+=inviteAccountIdkey.length();
        len1=customstr.indexOf(",");if(len0>=len1){
            return null;
        }
        chatModel.setinviteAccountIdId(customstr.substring(len0,len1));

        if(customstr.length()<=len1+1){
            return null;
        }
        customstr=customstr.substring(len1+1,customstr.length());

        len0=customstr.indexOf(typekey);if(len0<0){
            return null;
        }
        len0+=typekey.length();
        len1=customstr.indexOf("\"}");if(len0>=len1){
            return null;
        }
        chatModel.settype(customstr.substring(len0,len1));
        return chatModel;
    }
    public void addchatModel(ChatModel chatModel) {
        if(models==null)
        {
            models=new ArrayList<>();
        }
        for(int i=0;i<models.size();i++)
        {
            if(models.get(i).getinviteAccountId()==chatModel.getinviteAccountId())
            {
                models.set(i,chatModel);
               break;
            }
        }
        models.add(chatModel);
    }

    public void removeinvite(long getinviteid) {
        if(models==null)
        {
            return;
        }
        for(int i=0;i<models.size();i++)
        {
          if(models.get(i).getinviteAccountId()==getinviteid)
            {
                removeinvite(i);
            }
        }
    }

    public List<ChatModel> getchatlist() {
        return models;
    }
}
