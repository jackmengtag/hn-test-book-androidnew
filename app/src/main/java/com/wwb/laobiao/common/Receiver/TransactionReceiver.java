package com.wwb.laobiao.common.Receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.wwb.laobiao.common.ChatUMessage;
import com.wwb.laobiao.common.ChatUtil;
import com.wwb.laobiao.usmsg.websocketclient.WebMainActivity;
import com.wwb.laobiao.usmsg.websocketclient.bean.ChatModel;
import com.wwb.laobiao.usmsg.websocketclient.bean.ChatUser;
import com.yangna.lbdsp.common.base.BaseApplication;
import com.yangna.lbdsp.common.swiperefreshLayout.ToastUtil;

public class TransactionReceiver extends BroadcastReceiver {
    private final static String TAG = TransactionReceiver.class.getSimpleName();
    public final static String TRANS_ACTION = "trans.action";
    private static final String TESTSTR ="测试对话" ;
    public static String TRANS_Chat="trans.data";
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (TRANS_ACTION.equals(action)) {
            Bundle bundleExtra = intent.getBundleExtra("bundle");
            if(bundleExtra==null)
            {
                return;
            }
            ChatUMessage chatUMessage= (ChatUMessage) bundleExtra.getSerializable(TRANS_Chat);
            if(chatUMessage==null)
            {
                chatUMessage=new ChatUMessage();
            }
            onChatdo(chatUMessage,context,intent);
            ToastUtil.showToast(chatUMessage.textstr+"onReceive"+chatUMessage.titlestr);
        }
    }
    private void onChatdo(ChatUMessage msg, Context context, Intent mintent) {
        ChatModel chatModel=new ChatModel();
        String customstr=msg.customstr;
        chatModel= ChatUtil.getInstance().getchatModel(customstr);
        if(chatModel!=null)
        {
            ChatUtil.getInstance().addchatModel(chatModel);
            ChatUser chatUser=new ChatUser();
            chatUser.ips=""+chatModel.getinviteAccountId();
            chatUser.tokens= BaseApplication.getInstance().getUserId();
            chatUser.setdecideId(chatModel.getdecideAccountId());
            chatUser.setinviteId(chatModel.getinviteAccountId());
            chatUser.sky=2;
            chatUser.settitle(chatUser.ips+"请求升星");
            Bundle bundle=new Bundle();
            bundle.putSerializable(ChatUser.KEYTRACY,chatUser);
            Intent intent=new Intent(context, WebMainActivity.class);
//            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            intent.putExtra("bundle",bundle);
            context.startActivity(intent);
        }
        else
        {
            if(msg.titlestr!=null)
            {
                if(msg.titlestr.equals(TESTSTR))
                {
                    chatModel=new ChatModel();
                    ChatUtil.getInstance().addchatModel(chatModel);
                    ChatUser chatUser=new ChatUser();
                    chatUser.ips=""+chatModel.getinviteAccountId();
                    chatUser.tokens= BaseApplication.getInstance().getUserId();
                    chatUser.setdecideId(chatModel.getdecideAccountId());
                    chatUser.setinviteId(chatModel.getinviteAccountId());
                    chatUser.sky=2;
                    chatUser.settitle(chatUser.ips+"请求升星（模拟）"+" ->"+msg.titlestr+" ->"+msg.textstr);
                    Bundle bundle=new Bundle();
                    bundle.putSerializable(ChatUser.KEYTRACY,chatUser);
                    Intent intent=new Intent(context, WebMainActivity.class);
//            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                    intent.putExtra("bundle",bundle);
                    context.startActivity(intent);
                }
            }

        }


    }


}
