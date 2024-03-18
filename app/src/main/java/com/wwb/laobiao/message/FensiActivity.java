package com.wwb.laobiao.message;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import com.wwb.laobiao.cangye.bean.Fans.FansdeleteInfor;
import com.wwb.laobiao.cangye.bean.Fans.FansinsertInfor;
import com.wwb.laobiao.cangye.bean.Message.MessageFansEreadInfor;
import com.wwb.laobiao.cangye.bean.Message.MessageFansPageInfor;
import com.wwb.laobiao.cangye.bean.Message.MessageFansPageModel;
import com.wwb.laobiao.cangye.bean.Message.MessagecommentEreadInfor;
import com.wwb.laobiao.instance.UserMessage;
import com.wwb.laobiao.message.adp.FansViewAdapter;
import com.wwb.laobiao.message.bean.FansDataModel;
import com.wwb.laobiao.usmsg.websocketclient.WebMainActivity;
import com.wwb.laobiao.usmsg.websocketclient.bean.ChatModel;
import com.wwb.laobiao.usmsg.websocketclient.bean.ChatUser;
import com.yangna.lbdsp.R;
import com.yangna.lbdsp.common.UrlConfig;
import com.yangna.lbdsp.common.base.BaseApplication;
import com.yangna.lbdsp.common.swiperefreshLayout.ToastUtil;
import com.yangna.lbdsp.common.utils.SpUtils;

import java.util.ArrayList;
import java.util.List;


public class FensiActivity extends AppCompatActivity implements View.OnClickListener, UserMessage.Messageinterface {
    private boolean refreshType;
    private int page;
    private int oldListSize;
    private int newListSize;
    private int addListSize;
    private FansViewAdapter adapter;
    RefreshLayout refreshLayout;
    RecyclerView rvMovieList;
    LinearLayout loading_view_ll;

    private List<FansDataModel> mList=UserMessage.getInstance().FansDataModels;
    private int maxpage=1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fensi);
        findViewById(R.id.imageView_back).setOnClickListener(this);
        refreshLayout=findViewById(R.id.refreshLayout);
        rvMovieList=findViewById(R.id.rvMovieList);
        loading_view_ll=findViewById(R.id.loading_view_ll);
        initdata();
    }

    private void initdata() {
        // 开启自动加载功能（非必须）
        refreshLayout.setEnableAutoLoadMore(true);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull final RefreshLayout refreshLayout) {
                refreshLayout.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshType = true;
                        page = 1;
                        mList.clear();
                        oldListSize = 0;
                        MessageFansPageInfor user=new MessageFansPageInfor();
                        user.setCurrentPage(String.format("%d",page));
                        user.setPageSize(String.format("%d",10));
                        UserMessage.getInstance().MessageFansPage(FensiActivity.this,user,FensiActivity.this,1);
                    }
                }, 10);
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull final RefreshLayout refreshLayout) {
                refreshLayout.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshType = false;
                        if (page >=maxpage) {
                            ToastUtil.showToast("暂无更多的数据啦");
                            // 将不会再次触发加载更多事件
                            refreshLayout.finishLoadMoreWithNoMoreData();
                            return;
                        }
                        MessageFansPageInfor user=new MessageFansPageInfor();
                        user.setCurrentPage(String.format("%d",page));
                        user.setPageSize(String.format("%d",1));
                        UserMessage.getInstance().MessageFansPage(FensiActivity.this,user,FensiActivity.this,1);
                    }
                }, 10);
            }
        });
        //触发自动刷新
        refreshLayout.autoRefresh();
    }
    private void onrefresh() {//  FansDataModel
        newListSize = mList.size();
        addListSize = newListSize - oldListSize;

        if (refreshType) {
            // 设置RecyclerView样式为竖直线性布局
            rvMovieList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
            adapter = new FansViewAdapter(this, mList);
            rvMovieList.setAdapter(adapter);
        } else {
            adapter.notifyItemRangeInserted(mList.size() - addListSize, mList.size());
            adapter.notifyItemRangeChanged(mList.size() - addListSize, mList.size());
        }
        page++;
        rvMovieList.setVisibility(View.VISIBLE);
        loading_view_ll.setVisibility(View.GONE);
        // item条目的点击事件回调
        adapter.setItemClikListener(new FansViewAdapter.OnItemClikListener() {

            // 短按点击事件回调
            @Override
            public void onItemClik(View view, int position) {
//                String videoTitle = mList.get(position).getDownLoadName();
//                ToastUtil.showToast(videoTitle);//  \\
//                ChatModel chatModel= null;
//                if(chatModel==null)
//                {
//                    chatModel=new ChatModel();
//                }
//                ChatUser chatUser=new ChatUser();
//                chatUser.ips=""+chatModel.getdecideAccountId();
//                chatUser.tokens= BaseApplication.getInstance().getUserId();
//                chatUser.setdecideId(chatModel.getdecideAccountId());
//                chatUser.setinviteId(chatModel.getinviteAccountId());
////                chatUser.sky=2;
//                chatUser.settitle(chatUser.ips+" "+chatModel.getdecideAccountId());
//                Bundle bundle=new Bundle();
//                bundle.putSerializable(ChatUser.KEYTRACY,chatUser);
//                Intent intent=new Intent(FensiActivity.this, WebMainActivity.class);
////            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
////            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
//                intent.putExtra("bundle",bundle);
//                FensiActivity.this.startActivity(intent);

                FansDataModel model = mList.get(position);
                ChatUser chatUser=new ChatUser();
                chatUser.ips=""+model.accountId;
                //chatUser.setinviteId(chatModel.getinviteAccountId());(String) SpUtils.get(getApplicationContext(), UrlConfig.USERID, "")
//                chatUser.tokens= BaseApplication.getInstance().getUserId();
                chatUser.tokens= (String) SpUtils.get(getApplicationContext(), UrlConfig.USERID, "");
                Log.e("fensi", ""+chatUser.tokens);
                chatUser.settitle(model.nickName);
                Bundle bundle=new Bundle();
                bundle.putSerializable(ChatUser.KEYTRACY,chatUser);
                Intent intent=new Intent(FensiActivity.this, WebMainActivity.class);
                intent.putExtra("bundle",bundle);
                startActivityForResult(intent,0);

            }
            // 长按点击事件回调
            @Override
            public void onItemLongClik(View view, int position) {

            }

            @Override
            public void onItemDo(View view, int position) {
                FansDataModel tmp0 = mList.get(position);
                if(tmp0.mutual.equals("0"))
                {
                    FansinsertInfor user=new FansinsertInfor();
                    user.userId=tmp0.id;
                    UserMessage.getInstance().Fansinsert(FensiActivity.this,user,FensiActivity.this,2);
                }
                else
                {
                    FansdeleteInfor user=new FansdeleteInfor();
                    user.ids=new ArrayList<>();
                    user.ids.add(tmp0.id);
                    UserMessage.getInstance().Fansdelete(FensiActivity.this,user,FensiActivity.this,2);
                }


            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageView_back:
                finish();
                break;
        }
    }
    @Override
    public void onresh(int idex,Object objx) {
        if(idex<0)
        {
            rvMovieList.setVisibility(View.VISIBLE);
            loading_view_ll.setVisibility(View.GONE);
            refreshLayout.finishRefresh();
            refreshLayout.resetNoMoreData();//setNoMoreData(false);
            return;
        }
        switch (idex)
        {
            case 1:
            {
                 if(refreshType)
                 {
                     mList.clear();
                     oldListSize = 0;
                     List<MessageFansPageModel.record> records= (List<MessageFansPageModel.record>) objx;
                     if(records.size()<1)
                     {
                         page=maxpage;
//
//                         rvMovieList.setVisibility(View.VISIBLE);
//                         loading_view_ll.setVisibility(View.GONE);
//                         refreshLayout.finishRefresh();
//                         refreshLayout.resetNoMoreData();//setNoMoreData(false);
//
//                         return;
                     }
                     for(int i=0;i<records.size();i++)
                        {
                            MessageFansPageModel.record tmp0 = records.get(i);
                            FansDataModel tmp1=new FansDataModel();
                            tmp1.accountId=tmp0.accountId;
                            tmp1.id=tmp0.id;
                            tmp1.createTime=tmp0.createTime;
                            tmp1.logo=tmp0.logo;
                            tmp1.status=tmp0.status;
                            tmp1.nickName=tmp0.nickName;//mutual
                            tmp1.mutual=tmp0.mutual;
                            mList.add(tmp1);
                        }
                     onrefresh();
                     refreshLayout.finishRefresh();
                     refreshLayout.resetNoMoreData();//setNoMoreData(false);
                 }
                 else
                 {
                     oldListSize = mList.size();
                     List<MessageFansPageModel.record> records= (List<MessageFansPageModel.record>) objx;
                     if(records.size()<1)
                     {
                         page=maxpage;
                         rvMovieList.setVisibility(View.VISIBLE);
                         loading_view_ll.setVisibility(View.GONE);
                         refreshLayout.setEnableLoadMore(true);
                         refreshLayout.finishLoadMore();
                         return;
                     }
                     for(int i=0;i<records.size();i++)
                     {
                         MessageFansPageModel.record tmp0 = records.get(i);
                         FansDataModel tmp1=new FansDataModel();
                         tmp1.accountId=tmp0.accountId;
                         tmp1.id=tmp0.id;
                         tmp1.createTime=tmp0.createTime;
                         tmp1.logo=tmp0.logo;
                         tmp1.status=tmp0.status;
                         tmp1.nickName=tmp0.nickName;
                         tmp1.mutual=tmp0.mutual;
                         mList.add(tmp1);
                     }
                     onrefresh();
                     refreshLayout.setEnableLoadMore(true);
                     refreshLayout.finishLoadMore();
                 }
                 fansRead();
            }
                break;
            case 2:
                refreshLayout.autoRefresh();
                break;
        }
    }
    private void fansRead() {
        MessageFansEreadInfor user=new MessageFansEreadInfor();
        for(int i=0;i<mList.size();i++)
          {
              FansDataModel tmp0 = mList.get(i);
              if(tmp0.status.equals("0"))
              {
                  user.ids.add(tmp0.id);
              }

          }
        if(user.ids.size()>0)
        {
            UserMessage.getInstance().MessageFansEread(FensiActivity.this,user, FensiActivity.this,2);
        }

    }
}

