package com.wwb.laobiao.hongbao.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.yangna.lbdsp.R;

import java.util.List;

//import com.qzy.laobiao.R;

public class RankingAdapter extends RecyclerView.Adapter<RankingAdapter.ViewHolder> {
    private List <Ranking> mFruitList;
    //内部类，继承自RecyclerView.ViewHolder
    //接收一个View ，通常就是RecyclerView子项的最外层布局，
    //所以就可以用findViewById 来获取控件
    static class  ViewHolder extends RecyclerView.ViewHolder{
        TextView textName;
        TextView diancannum;
        private Context mContext;

        public ViewHolder(View View) {
            super(View);
            textName = (TextView) View.findViewById(R.id.textView_name_iv);
            diancannum = (TextView) View.findViewById(R.id.textView_dianzan_iv);
        }

        public void show(Ranking Ranking) {
            String str0 =getranknumstr(Ranking);
            str0=String.format("%s %s(用户ID：%s)",str0,Ranking.name,Ranking.usid);
            textName.setText(str0);
            if(Ranking.diancannum>10000)
            {
                 float fv=Ranking.diancannum/10000;
                diancannum.setText(String.format("%4.2fw",fv));
            }
            else
            {
                diancannum.setText(String.format("%d",Ranking.diancannum));
            }

        }
        private String getranknumstr(Ranking ranking) {
            if(ranking.ranknum==1)
            {
                return   String.format("第一名");
            }
            else if(ranking.ranknum==2)
            {
                return   String.format("第二名");
            }
            else if(ranking.ranknum==3)
            {
                return   String.format("第三名");
            }
            else if(ranking.ranknum==4)
            {
                return   String.format("第四名");
            }
            else if(ranking.ranknum==5)
            {
                return   String.format("第五名");
            }
            else if(ranking.ranknum==6)
            {
                return   String.format("第六名");
            }
            else if(ranking.ranknum==7)
            {
                return   String.format("第七名");
            }
            else if(ranking.ranknum==8)
            {
                return   String.format("第八名");
            }
            else if(ranking.ranknum==9)
            {
                return   String.format("第九名");
            }
            else if(ranking.ranknum==10)
            {
                return   String.format("第十名");
            }

            return "";
        }

    }

    //将要展示的数据传递进来，
    public RankingAdapter(List<Ranking> list){
        this.mFruitList = list ;
    }
    //将fruit_item 布局加载进来，然后创建一个ViewHolder实例，
    //将加载的布局传给ViewHolder的构造函数中。就可以获取布局中的控件
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_bixin_ranking_item,parent,false);
        ViewHolder holder = new ViewHolder(view);

        return holder;
    }
    //这个方法会在屏幕滚动的时候执行
    // 通过position获取到Fruit 的实例，然后给布局上的控件进行赋值，
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
//        Fruit fruit = mFruitList.get(position);
//        holder.imageView.setImageResource(fruit.getImageId());
//        holder.TextName.setText(fruit.getName());
         holder.show(mFruitList.get(position));
    }
    //他用于告诉RecyclerView有多少子项，直接返回数据源的长度就行了
    @Override
    public int getItemCount() {
        return mFruitList.size();
    }
}
