package com.wwb.laobiao.Search.adp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.RecyclerView;

import com.wwb.laobiao.Search.widget.LinearLayoutBodyview;
import com.wwb.laobiao.Search.widget.LinearLayoutBottomview;
import com.wwb.laobiao.Search.widget.LinearLayoutHeadview;
import com.yangna.lbdsp.R;

import java.util.ArrayList;

//import com.qzy.laobiao.R;
//import com.qzy.laobiao.testview0.view.SearchIndexView;
//import com.qzy.laobiao.testview0.widget.LinearLayoutBodyview;
//import com.qzy.laobiao.testview0.widget.LinearLayoutBottomview;
//import com.qzy.laobiao.testview0.widget.LinearLayoutHeadview;

public class SearchVideoAdapter extends RecyclerView.Adapter<SearchVideoAdapter.ViewHolder> {
    private  ArrayList<SearchVideoView> searchIndexViews;
    private Context mContext;
    public SearchVideoAdapter(ArrayList<SearchVideoView> searchIndexViews, Context mContext) {
        this.mContext=mContext;
        this.searchIndexViews=searchIndexViews;
    }

    static class  ViewHolder extends RecyclerView.ViewHolder{
        private LinearLayout mlay;
        public ViewHolder(View View) {
            super(View);
            mlay=itemView.findViewById(R.id.search_index_id);
        }
        public void LinearLayoutshow(View view) {
            LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);// 定义布局管理器的参数
            mlay.setOrientation(LinearLayout.VERTICAL);// 所有组件垂直摆放
            // 定义显示组件的布局管理器，为了简单，本次只定义一个TextView组件
            LinearLayout.LayoutParams text = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);// 定义文本显示组件
            view.setLayoutParams(text);// 配置文本显示组件的参数
            mlay.removeAllViews();
            mlay.addView(view, text);
        }
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_search_video,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
       if(searchIndexViews!=null)
       {
           if(searchIndexViews.size()>position)
           {
               SearchVideoView mvv = searchIndexViews.get(position);
               switch (mvv.sky)
               {
                   case 0:
                       LinearLayoutHeadview linearLayoutheadview=new LinearLayoutHeadview(mContext);
                       holder.LinearLayoutshow(linearLayoutheadview);
                       break;
                   case 1:
                       LinearLayoutBodyview linearLayoutBodyview=new LinearLayoutBodyview(mContext);
                       holder.LinearLayoutshow(linearLayoutBodyview);
                       break;
                   case 2:
                       LinearLayoutBottomview linearLayoutbottomview=new LinearLayoutBottomview(mContext);
                       linearLayoutbottomview.setText("num:"+position);
                       holder.LinearLayoutshow(linearLayoutbottomview);
                       break;
               }
           }
       }
       else
       {
           if(position%3==0)
           {
               LinearLayoutHeadview linearLayoutheadview=new LinearLayoutHeadview(mContext);
               holder.LinearLayoutshow(linearLayoutheadview);
           }
           else if(position%3==1)
           {
//            holder.mlay.setBackgroundColor(Color.GRAY);
               LinearLayoutBodyview linearLayoutBodyview=new LinearLayoutBodyview(mContext);
               holder.LinearLayoutshow(linearLayoutBodyview);
           }
           else if(position%3==2)
           {
               LinearLayoutBottomview linearLayoutbottomview=new LinearLayoutBottomview(mContext);
               linearLayoutbottomview.setText("num:"+position);
               holder.LinearLayoutshow(linearLayoutbottomview);
           }
       }


    }
    //他用于告诉RecyclerView有多少子项，直接返回数据源的长度就行了
    @Override
    public int getItemCount() {
        if(searchIndexViews==null)
        {
           return  11;
        }
        return searchIndexViews.size();
    }
}
