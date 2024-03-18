package com.wwb.laobiao.message;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.wwb.laobiao.cangye.bean.Message.MessageFansEreadInfor;
import com.wwb.laobiao.cangye.bean.Message.MessagecommentEreadInfor;
import com.wwb.laobiao.cangye.bean.Message.MessagecommentPageInfor;
import com.wwb.laobiao.cangye.bean.Message.MessagecommentPageModel;
import com.wwb.laobiao.cangye.bean.Message.MessagepraisePageInfor;
import com.wwb.laobiao.cangye.bean.Message.MessagepraisePageModel;
import com.wwb.laobiao.instance.UserMessage;
import com.wwb.laobiao.message.adp.PinglunViewAdapter;
import com.wwb.laobiao.message.adp.ZanViewAdapter;
import com.wwb.laobiao.message.bean.FansDataModel;
import com.wwb.laobiao.message.bean.MovieDataModel;
import com.wwb.laobiao.message.bean.PinglunDataModel;
import com.wwb.laobiao.message.bean.ZanDataModel;
import com.yangna.lbdsp.R;
import com.yangna.lbdsp.common.swiperefreshLayout.ToastUtil;

import java.util.ArrayList;
import java.util.List;

public class PinglunActivity extends AppCompatActivity implements View.OnClickListener, UserMessage.Messageinterface {
    private boolean refreshType;
    private int page;
    private int oldListSize;
    private int newListSize;
    private int addListSize;
    private PinglunViewAdapter adapter;
    RefreshLayout refreshLayout;
    RecyclerView rvMovieList;
    LinearLayout loading_view_ll;

    private List<PinglunDataModel> mList= UserMessage.getInstance().PinglunDataModels;
    private int maxpage=1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pinglun);
        findViewById(R.id.imageView_back).setOnClickListener(this);
        refreshLayout=findViewById(R.id.refreshLayout);
        rvMovieList=findViewById(R.id.rvMovieList);
        loading_view_ll=findViewById(R.id.loading_view_ll);
        initdata();
    }

    private void readpinglun() {
//        MessageFansEreadInfor user=new MessageFansEreadInfor();
        MessagecommentEreadInfor user=new MessagecommentEreadInfor();

        for(int i=0;i<mList.size();i++)
        {
            PinglunDataModel tmp0 = mList.get(i);
            if(tmp0.status.equals("0"))
            {
                user.ids.add(tmp0.id);
            }

        }
        if(user.ids.size()>0)
        {
            UserMessage.getInstance().MessagecommentEread(PinglunActivity.this,user, PinglunActivity.this,5);
        }

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

                        MessagecommentPageInfor user=new MessagecommentPageInfor();
                        user.setCurrentPage(String.format("%d",page));
                        user.setPageSize(String.format("%d",1));
                        UserMessage.getInstance().MessagecommentPage(PinglunActivity.this,user, PinglunActivity.this,4);
                    }
                }, 1000);
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull final RefreshLayout refreshLayout) {
                refreshLayout.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshType = false;
                        if (page >= maxpage) {
                            ToastUtil.showToast("暂无更多的数据啦");
                            // 将不会再次触发加载更多事件
                            refreshLayout.finishLoadMoreWithNoMoreData();
                            return;
                        }

                        MessagecommentPageInfor user=new MessagecommentPageInfor();
                        user.setCurrentPage(String.format("%d",page));
                        user.setPageSize(String.format("%d",1));
                        UserMessage.getInstance().MessagecommentPage(PinglunActivity.this,user, PinglunActivity.this,4);
                    }
                }, 100);
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
            adapter = new PinglunViewAdapter(this, mList);
            rvMovieList.setAdapter(adapter);
        } else {
            adapter.notifyItemRangeInserted(mList.size() - addListSize, mList.size());
            adapter.notifyItemRangeChanged(mList.size() - addListSize, mList.size());
        }
        page++;
        rvMovieList.setVisibility(View.VISIBLE);
        loading_view_ll.setVisibility(View.GONE);
        // item条目的点击事件回调
        adapter.setItemClikListener(new PinglunViewAdapter.OnItemClikListener() {

            // 短按点击事件回调
            @Override
            public void onItemClik(View view, int position) {
//                String videoTitle = mList.get(position).getDownLoadName();
//                ToastUtil.showToast(videoTitle);
            }
            // 长按点击事件回调
            @Override
            public void onItemLongClik(View view, int position) {

            }

            @Override
            public void onItemDo(View view, int position) {

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
            return;
        }
        switch (idex)
        {
            case 4:
            {
                if(refreshType)
                {
                    mList.clear();
                    oldListSize = 0;
                    List<MessagecommentPageModel.record> records= (List<MessagecommentPageModel.record>) objx;
                    if(records.size()<1)
                    {
//                        refreshLayout.finishRefresh();
//                        refreshLayout.resetNoMoreData();//setNoMoreData(false);
                        page=maxpage;
//                        return;
                    }
                    for(int i=0;i<records.size();i++)
                    {
                        MessagecommentPageModel.record tmp0 = records.get(i);
                        PinglunDataModel tmp1=new PinglunDataModel();
                        tmp1.accountId=tmp0.accountId;
                        tmp1.createTime=tmp0.createTime;
                        tmp1.logo=tmp0.logo;
                        tmp1.status=tmp0.status;
                        tmp1.nickName=tmp0.nickName;
                        tmp1.parent=tmp0.parent;
                        tmp1.contents=tmp0.contents;
                        mList.add(tmp1);
                    }
                    onrefresh();
                    refreshLayout.finishRefresh();
                    refreshLayout.resetNoMoreData();//setNoMoreData(false);
                }
                else
                {
                    oldListSize = mList.size();
                    List<MessagecommentPageModel.record> records= (List<MessagecommentPageModel.record>) objx;
                    if(records.size()<1)
                    {
//                        refreshLayout.finishRefresh();
//                        refreshLayout.resetNoMoreData();//setNoMoreData(false);
                        page=maxpage;
//                        return;
                    }
                    for(int i=0;i<records.size();i++)
                    {
                        MessagecommentPageModel.record tmp0 = records.get(i);
                        PinglunDataModel tmp1=new PinglunDataModel();
                        tmp1.accountId=tmp0.accountId;
                        tmp1.createTime=tmp0.createTime;
                        tmp1.logo=tmp0.logo;
                        tmp1.status=tmp0.status;
                        tmp1.nickName=tmp0.nickName;
                        tmp1.parent=tmp0.parent;
                        tmp1.contents=tmp0.contents;
                        mList.add(tmp1);
                    }
                    onrefresh();
                    refreshLayout.setEnableLoadMore(true);
                    refreshLayout.finishLoadMore();
                }
                readpinglun();
            }
            break;
            case 5:
                refreshLayout.autoRefresh();
                break;
        }
    }
}