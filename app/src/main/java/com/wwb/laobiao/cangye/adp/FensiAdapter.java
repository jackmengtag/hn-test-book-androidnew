package com.wwb.laobiao.cangye.adp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.wwb.laobiao.cangye.bean.Fensibean;
import com.yangna.lbdsp.R;

import java.util.List;

public class FensiAdapter extends RecyclerView.Adapter<FensiAdapter.ViewHolder> {
    private List <Fensibean> mFruitList;
    private int showflat;

    public void setshow(int sflat) {
        this.showflat=sflat;
    }

    //内部类，继承自RecyclerView.ViewHolder
    //接收一个View ，通常就是RecyclerView子项的最外层布局，
    //所以就可以用findViewById 来获取控件
    static class  ViewHolder extends RecyclerView.ViewHolder{
        View instance;
        ImageView imageViewName;
        TextView text0;
        TextView text1;
        TextView textidex;
        public ViewHolder(View View) {
            super(View);
            instance=View;
            imageViewName = (ImageView)View.findViewById(R.id.imageView);
            text0 = (TextView) View.findViewById(R.id.textView01);
            text1 = (TextView) View.findViewById(R.id.textView02);
            textidex = (TextView) View.findViewById(R.id.iv_idex);
        }
        public void onshow(Fensibean fensibean,int idex) {
//            if()
            textidex.setText(""+idex);
            if(fensibean.headbf)
            {
                imageViewName.setVisibility(View.VISIBLE);
                text0.setVisibility(View.GONE);
            }
            else
            {
                imageViewName.setVisibility(View.GONE);
            }
            text0.setText(fensibean.getName());
            text1.setText(fensibean.getnumstr());
        }

        public void onhide() {
        }
    }
    //将要展示的数据传递进来，
    public FensiAdapter(List<Fensibean> list){
        this.mFruitList = list ;
    }
    //将fruit_item 布局加载进来，然后创建一个ViewHolder实例，
    //将加载的布局传给ViewHolder的构造函数中。就可以获取布局中的控件
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_fensi_item,parent,false);
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
//        if(position>4)
//        {
//            if(showflat==1)
//            {
//                holder.instance.setVisibility(View.VISIBLE);
//            }
//            else
//            {
//                holder.instance.setVisibility(View.GONE);
//            }
//
//        }
          holder.onshow(mFruitList.get(position),position);
    }
    //他用于告诉RecyclerView有多少子项，直接返回数据源的长度就行了
    @Override
    public int getItemCount() {

        return mFruitList.size();

    }
}
