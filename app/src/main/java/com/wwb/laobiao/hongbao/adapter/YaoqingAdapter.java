package com.wwb.laobiao.hongbao.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.wwb.laobiao.hongbao.view.LinearLayoutUseryaoqingview;
import com.wwb.laobiao.hongbao.view.LinearLayoutmyyaoqingview;
import com.yangna.lbdsp.R;

import java.util.List;

//import com.qzy.laobiao.R;
//import com.qzy.laobiao.hongbao.view.LinearLayoutUseryaoqingview;
//import com.qzy.laobiao.hongbao.view.LinearLayoutmyyaoqingview;

public class YaoqingAdapter extends RecyclerView.Adapter<YaoqingAdapter.ViewHolder> {
    private List <Yaoqing> mFruitList;
    //内部类，继承自RecyclerView.ViewHolder
    //接收一个View ，通常就是RecyclerView子项的最外层布局，
    //所以就可以用findViewById 来获取控件
    static class  ViewHolder extends RecyclerView.ViewHolder{
        LinearLayout mlay;
        TextView textName;
        private Context mContext;

        public ViewHolder(View View) {
            super(View);
            mlay = (LinearLayout) View.findViewById(R.id.lay_iv);
            textName = (TextView) View.findViewById(R.id.textView_name_iv);
        }
        public void setname(String s) {
            if(textName!=null)
            {
                textName.setText(s);//FruitAdapter
            }
        }
        public void show(Yaoqing yaoqing) {
           if(yaoqing.sky==0)
           {
               LinearLayoutmyyaoqingview linearLayoutheadview=new LinearLayoutmyyaoqingview(mContext);
               LinearLayoutshow(linearLayoutheadview);
               linearLayoutheadview.setyaoqing(yaoqing);
           }
           else
           {
               LinearLayoutUseryaoqingview linearLayoutUseryaoqingview=new LinearLayoutUseryaoqingview(mContext);
               LinearLayoutshow(linearLayoutUseryaoqingview);
               linearLayoutUseryaoqingview.setyaoqing(yaoqing);
           }
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

    //将要展示的数据传递进来，
    public YaoqingAdapter(List<Yaoqing> list){
        this.mFruitList = list ;
    }
    //将fruit_item 布局加载进来，然后创建一个ViewHolder实例，
    //将加载的布局传给ViewHolder的构造函数中。就可以获取布局中的控件
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_yaoqing_item,parent,false);
        ViewHolder holder = new ViewHolder(view);
        holder.mContext=parent.getContext();
        return holder;
    }
    //这个方法会在屏幕滚动的时候执行
    // 通过position获取到Fruit 的实例，然后给布局上的控件进行赋值，
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
//        Fruit fruit = mFruitList.get(position);
//        holder.imageView.setImageResource(fruit.getImageId());
//        holder.TextName.setText(fruit.getName());
        holder.setname("n:"+position);
        holder.show(mFruitList.get(position));
    }
    //他用于告诉RecyclerView有多少子项，直接返回数据源的长度就行了
    @Override
    public int getItemCount() {
        return mFruitList.size();
    }
}
