package com.yangna.lbdsp.msg.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.wwb.laobiao.cangye.bean.Message.MessageFansEreadInfor;
import com.wwb.laobiao.cangye.bean.Message.MessageamountInfor;
import com.wwb.laobiao.cangye.bean.Message.MessageamountModel;
import com.wwb.laobiao.cangye.view.AgreeFragment;
import com.wwb.laobiao.instance.UserMessage;
import com.wwb.laobiao.message.ZanActivity;
import com.wwb.laobiao.message.FensiActivity;
import com.wwb.laobiao.message.PinglunActivity;
import com.wwb.laobiao.usmsg.model.ChatMsgAdapter0;
import com.wwb.laobiao.usmsg.websocketclient.WebMainActivity;
import com.wwb.laobiao.usmsg.websocketclient.bean.ChatModel;
import com.wwb.laobiao.usmsg.websocketclient.bean.ChatUser;
import com.yangna.lbdsp.R;
import com.yangna.lbdsp.common.base.BaseApplication;
import com.yangna.lbdsp.common.base.BasePresenterFragment;
import com.yangna.lbdsp.msg.adapter.UsMsgAdapter;
import com.yangna.lbdsp.msg.model.UsMsgModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 *
 * 消息fragment
 */
public class MessageFragment extends BasePresenterFragment implements ChatMsgAdapter0.MsgInterface, View.OnClickListener, UserMessage.Messageinterface {
//    @BindView(R.id.iv_hdtx0)
//    TextView title_txt;
//    @BindView(R.id.xiaoxi_lv)
//    ListView mlistview1;
    @BindView(R.id.iv_layhead0)
     RelativeLayout layhead0;
    @BindView(R.id.iv_layhead1)
     RelativeLayout layhead1;
	@BindView(R.id.iv_layhead2)
     RelativeLayout layhead2;
    @BindView(R.id.imageView_back)
    ImageView imageViewback;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;
    @BindView(R.id.iv_hdtx0)
    TextView hdtx0;
    @BindView(R.id.iv_hdtx1)
    TextView hdtx1;
    @BindView(R.id.iv_hdtx2)
    TextView hdtx2;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;//imageView_erweima

    UsMsgAdapter msgAdapter;
    List<UsMsgModel>msgModels=new ArrayList<>();
    private List<String> popupMenuItemList = new ArrayList<>();
    private List<ChatModel> models;
    private AgreeFragment.AgreeFragmentInterface agreeFragmentInterface;

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_message;
    }

    @Override
    protected void initView() {
        super.initView();
        initdata();
		initlay();
        initRecyclerView();
        setRefreshEvent();
    }

    private void setRefreshEvent() {
        if(swipeRefreshLayout!=null)
        {
            swipeRefreshLayout.setOnRefreshListener(() -> new CountDownTimer(1000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
//                    regetusid();
                }
                @Override
                public void onFinish() {
                    initdata();
                    swipeRefreshLayout.setRefreshing(false);
                }
            }.start());
        }
    }

    private void initdata() {
        MessageamountInfor user=new MessageamountInfor();
        UserMessage.getInstance().Messageamount(context,user, MessageFragment.this,1);
    }

    private void initRecyclerView() {
        LinearLayoutManager layout1 = new LinearLayoutManager(getContext());
        layout1.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layout1);

        msgModels.clear();
        if(recyclerView!=null)
        {
            {
                UsMsgModel mm=new UsMsgModel();
                mm.name="小姐姐";
                msgModels.add(mm);
            }
            {
                UsMsgModel mm=new UsMsgModel();
                mm.name="爸爸";
                msgModels.add(mm);
            }
        }
        msgAdapter=new UsMsgAdapter(msgModels);
        recyclerView.setAdapter(msgAdapter);
        msgAdapter.setInterface(new UsMsgAdapter.LineIdexInterface() {
            @Override
            public void show(int postion, String toString1) {
                if(toString1.equals("0"))
                {
                    ChatModel chatModel= null;
                    if(chatModel==null)
                    {
                        chatModel=new ChatModel();
                    }
                    ChatUser chatUser=new ChatUser();
                    chatUser.ips=""+chatModel.getdecideAccountId();
                    chatUser.tokens= BaseApplication.getInstance().getUserId();
                    chatUser.setdecideId(chatModel.getdecideAccountId());
                    chatUser.setinviteId(chatModel.getinviteAccountId());
//                chatUser.sky=2;
                    chatUser.settitle(chatUser.ips+" "+chatModel.getdecideAccountId());
                    Bundle bundle=new Bundle();
                    bundle.putSerializable(ChatUser.KEYTRACY,chatUser);
                    Intent intent=new Intent(getContext(), WebMainActivity.class);
//            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                    intent.putExtra("bundle",bundle);
                    context.startActivity(intent);
                }
                else    if(toString1.equals("1"))
                {







                }
                Log.e("msg"," "+toString1);
            }
        });
        Log.e("msg","initRecyclerView");
    }
    private void initlay() {
		layhead0.setOnClickListener(this);
		layhead1.setOnClickListener(this);
		layhead2.setOnClickListener(this);
    }

    public void setinterface(AgreeFragment.AgreeFragmentInterface agreeFragmentInterface) {
        this.agreeFragmentInterface=agreeFragmentInterface;
    }
    @Override
    public void onsel(int selectItem) {
///mlist
        if(models==null)
        {
            return;
        }
        if(selectItem>=models.size())
        {
            return;
        }
//        if(0==1)
//        {
//            ChatModel chatModel= ChatUtil.getInstance().getselchat(selectItem);
//            if(chatModel==null)
//            {
//                chatModel=new ChatModel();
//            }
//            ChatUser chatUser=new ChatUser();
//            chatUser.ips=""+chatModel.getdecideAccountId();
//            chatUser.tokens= BaseApplication.getInstance().getUserId();
//            chatUser.setdecideId(chatModel.getdecideAccountId());
//            chatUser.setinviteId(chatModel.getinviteAccountId());
////            chatUser.sky=1;
////            chatUser.settitle(chatUser.ips+"请求升星"+chatModel.getdecideAccountId());
//            chatUser.settitle(chatUser.ips+"请求升星"+chatModel.getdecideAccountId());
//            Bundle bundle=new Bundle();
//            bundle.putSerializable(ChatUser.KEYTRACY,chatUser);
//            Intent intent=new Intent(getContext(), WebMainActivity.class);
////            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
////            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
//            intent.putExtra("bundle",bundle);
//            context.startActivity(intent);
////            startActivityForResult(intent,0);
//        }

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 0:
                if (data != null) {
                    Bundle bundle = data.getExtras();
//                    XiaoxiLvModel ttmp= (XiaoxiLvModel) bundle.getSerializable(XiaoxiLvModel.SER_KEY);
//                    int sel=data.getIntExtra(XiaoxiLvModel.CHAR_SEL,0);
//                    if((sel>0)&&(sel<mlist.size()))
//                    {
//                        mlist.set(sel,ttmp);
//                    }
                }
                break;
            case 1: {

            }
            break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.iv_layhead0:
            {

                Intent intent = new Intent(context, ZanActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivityForResult(intent, 0);

            }
			break;
			case R.id.iv_layhead1:
            {
                Intent intent = new Intent(context, FensiActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivityForResult(intent, 0);

            }
			break;
			case R.id.iv_layhead2:
            {
                Intent intent = new Intent(context, PinglunActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivityForResult(intent, 0);


//				                Intent intent = new Intent(context, FukuanActivity.class);
//                intent.putExtra("goodid", goodid);
//                intent.putExtra("goodsName", goodsName);
//                intent.putExtra("goodsPrice", goodsPrice);
//                intent.putExtra("pictureUrl", pictureUrl);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                context.startActivityForResult(intent, 0);

            }
			break;
        }
    }

    @Override
    public void onresh(int idex, Object objx) {
            switch (idex)
            {
                case 1:
                    LinearLayout mlay=null;
                    mlay= (LinearLayout) hdtx0.getParent();

                    MessageamountModel.Body mbody= (MessageamountModel.Body) objx;
                    if(!mbody.praise.equals("0"))
                    {
                        mlay.setVisibility(View.VISIBLE);
                        hdtx0.setText("+"+mbody.praise);
                    }
                    else
                    {
                        mlay.setVisibility(View.GONE);
                        hdtx0.setText(mbody.praise);
                    }

                    mlay= (LinearLayout) hdtx1.getParent();
                    if(!mbody.fans.equals("0"))
                    {
                        mlay.setVisibility(View.VISIBLE);
                        hdtx1.setText("+"+mbody.fans);
                    }
                    else
                    {
                        mlay.setVisibility(View.GONE);
                        hdtx1.setText(mbody.fans);
                    }

                    mlay= (LinearLayout) hdtx2.getParent();
                    if(!mbody.comment.equals("0"))
                    {
                        mlay.setVisibility(View.VISIBLE);
                        hdtx2.setText("+"+mbody.comment);
                    }
                    else
                    {
                        mlay.setVisibility(View.GONE);
                        hdtx2.setText(mbody.comment);
                    }
                    break;
            }
    }
}
