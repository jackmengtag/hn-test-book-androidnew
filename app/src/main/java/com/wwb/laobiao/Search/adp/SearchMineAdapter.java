package com.wwb.laobiao.Search.adp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.yangna.lbdsp.R;

import java.util.ArrayList;

//import com.qzy.laobiao.R;

public class SearchMineAdapter extends RecyclerView.Adapter<SearchMineAdapter.ViewHolder> {
    private  ArrayList<SearchMineView> searchIndexViews;
    private Context mContext;
    public SearchMineAdapter(ArrayList<SearchMineView> searchIndexViews, Context mContext) {
        this.mContext=mContext;
        this.searchIndexViews=searchIndexViews;
    }
    static class  ViewHolder extends RecyclerView.ViewHolder{
        private LinearLayout mlay;//mine_name
        private TextView minename,index;
        public ViewHolder(View View) {
            super(View);
            mlay=itemView.findViewById(R.id.search_index_id);
            minename=View.findViewById(R.id.mine_name);
            index=View.findViewById(R.id.idex_iv);
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
        public void setshow(SearchMineView searchMineView) {
            minename.setText(searchMineView.name);
        }

        public void setpos(int position) {
            index.setText(""+position);
        }
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_search_mine,parent,false);
        ViewHolder holder = new ViewHolder(view);


        return holder;
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if(searchIndexViews!=null)
        {
            holder.setpos(position);
            holder.setshow(searchIndexViews.get(position));
        }
    }
    //他用于告诉RecyclerView有多少子项，直接返回数据源的长度就行了
    @Override
    public int getItemCount() {
        if(searchIndexViews==null)
        {
           return  3;
        }
        return searchIndexViews.size();
    }
}
