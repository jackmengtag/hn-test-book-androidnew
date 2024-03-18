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
import com.wwb.laobiao.cangye.bean.Fans.FansdeleteInfor;
import com.wwb.laobiao.cangye.bean.Message.MessageFansPageInfor;
import com.wwb.laobiao.cangye.bean.Message.MessageFansPageModel;
import com.wwb.laobiao.cangye.bean.Message.MessagecommentEreadInfor;
import com.wwb.laobiao.cangye.bean.Message.MessagepraisEreadInfor;
import com.wwb.laobiao.cangye.bean.Message.MessagepraisePageInfor;
import com.wwb.laobiao.cangye.bean.Message.MessagepraisePageModel;
import com.wwb.laobiao.instance.UserMessage;
import com.wwb.laobiao.message.adp.FansViewAdapter;
import com.wwb.laobiao.message.adp.ZanViewAdapter;
import com.wwb.laobiao.message.bean.FansDataModel;
import com.wwb.laobiao.message.bean.ZanDataModel;
import com.yangna.lbdsp.R;
import com.yangna.lbdsp.common.swiperefreshLayout.ToastUtil;

import java.util.ArrayList;
import java.util.List;

public class ZanActivity extends AppCompatActivity implements View.OnClickListener, UserMessage.Messageinterface {
    private boolean refreshType;
    private int page;
    private int oldListSize;
    private int newListSize;
    private int addListSize;
    private ZanViewAdapter adapter;
    RefreshLayout refreshLayout;
    RecyclerView rvMovieList;
    LinearLayout loading_view_ll;

    private List<ZanDataModel> mList= UserMessage.getInstance().ZanDataModels;
    private int maxpage=1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_can);
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

                        MessagepraisePageInfor user=new MessagepraisePageInfor();
                        user.setCurrentPage(String.format("%d",page));
                        user.setPageSize(String.format("%d",1));
                        UserMessage.getInstance().MessagepraisePage(ZanActivity.this,user, ZanActivity.this,1);
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

                        MessagepraisePageInfor user=new MessagepraisePageInfor();
                        user.setCurrentPage(String.format("%d",page));
                        user.setPageSize(String.format("%d",1));
                        UserMessage.getInstance().MessagepraisePage(ZanActivity.this,user, ZanActivity.this,1);
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
            adapter = new ZanViewAdapter(this, mList);
            rvMovieList.setAdapter(adapter);
        } else {
            adapter.notifyItemRangeInserted(mList.size() - addListSize, mList.size());
            adapter.notifyItemRangeChanged(mList.size() - addListSize, mList.size());
        }
        page++;
        rvMovieList.setVisibility(View.VISIBLE);
        loading_view_ll.setVisibility(View.GONE);
        // item条目的点击事件回调
        adapter.setItemClikListener(new ZanViewAdapter.OnItemClikListener() {

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
            case 1:
            {
                if(refreshType)
                {
                    mList.clear();
                    oldListSize = 0;
                    List<MessagepraisePageModel.record> records= (List<MessagepraisePageModel.record>) objx;
                    if(records.size()<1)
                    {
                        page=maxpage;
                    }
                    for(int i=0;i<records.size();i++)
                    {
                        MessagepraisePageModel.record tmp0 = records.get(i);
                        ZanDataModel tmp1=new ZanDataModel();
                        tmp1.accountId=tmp0.accountId;
                        tmp1.createTime=tmp0.createTime;
                        tmp1.logo=tmp0.logo;
                        tmp1.status=tmp0.status;
                        tmp1.nickName=tmp0.nickName;
                        tmp1.coverUrl=tmp0.coverUrl;
                        mList.add(tmp1);
                    }
                    onrefresh();
                    refreshLayout.finishRefresh();
                    refreshLayout.resetNoMoreData();//setNoMoreData(false);
                }
                else
                {
                    oldListSize = mList.size();
                    List<MessagepraisePageModel.record> records= (List<MessagepraisePageModel.record>) objx;
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
                        MessagepraisePageModel.record tmp0 = records.get(i);
                        ZanDataModel tmp1=new ZanDataModel();
                        tmp1.accountId=tmp0.accountId;
                        tmp1.createTime=tmp0.createTime;
                        tmp1.logo=tmp0.logo;
                        tmp1.status=tmp0.status;
                        tmp1.nickName=tmp0.nickName;
                        tmp1.coverUrl=tmp0.coverUrl;
                        mList.add(tmp1);
                    }
                    onrefresh();
                    refreshLayout.setEnableLoadMore(true);
                    refreshLayout.finishLoadMore();
                }
                if(page<maxpage)
                {
                    readzan();
                }

            }
            break;
            case 2:
                refreshLayout.autoRefresh();
                break;
        }
    }

    private void readzan() {
        MessagepraisEreadInfor user=new MessagepraisEreadInfor();
        for(int i=0;i<mList.size();i++)
        {
            ZanDataModel tmp0 = mList.get(i);
            if(tmp0.status.equals("0"))
            {
                user.ids.add(tmp0.id);
            }

        }
        if(user.ids.size()>0)
        {
            UserMessage.getInstance().MessagepraisEread(ZanActivity.this,user, ZanActivity.this,2);
        }
    }

}