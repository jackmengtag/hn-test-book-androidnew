package com.wwb.laobiao.Search.adp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.wwb.laobiao.Search.impl.Hotguss;
import com.yangna.lbdsp.R;

import java.util.List;

//import com.qzy.laobiao.R;
//import com.qzy.laobiao.testview0.impl.Hotguss;

public class HotGuessAdapter extends RecyclerView.Adapter<HotGuessAdapter.ViewHolder> {
    private List <Hotguss> mFruitList;
    private HotGuessAdapterinterface hotGuessAdapterinterface;

    //内部类，继承自RecyclerView.ViewHolder
    //接收一个View ，通常就是RecyclerView子项的最外层布局，
    //所以就可以用findViewById 来获取控件
    static class  ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageViewName;
        TextView textName0;
        TextView textName1;
        LinearLayout linearLayout0;
        LinearLayout linearLayout1;
        public  HotGuessAdapterinterface hotGuessAdapterinterface;

        public ViewHolder(View View) {
            super(View);
            imageViewName = (ImageView)View.findViewById(R.id.imageView0);
            textName0 = (TextView) View.findViewById(R.id.textView0);
            linearLayout0=(LinearLayout) View.findViewById(R.id.layid0);
            textName1 = (TextView) View.findViewById(R.id.textView1);
            linearLayout1=(LinearLayout) View.findViewById(R.id.layid1);
            linearLayout0.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(android.view.View v) {
                    {
                        hotGuessAdapterinterface.showname(textName0.getText().toString());
                    }
                }
            });
            linearLayout1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(android.view.View v) {
                    {
                        hotGuessAdapterinterface.showname(textName1.getText().toString());
                    }
                }
            });
        }
        public void setHotGuessAdapterinterface(HotGuessAdapterinterface hotGuessAdapterinterface) {
            this.hotGuessAdapterinterface=hotGuessAdapterinterface;
        }
        public void setname0(String name) {
            textName0.setText(name);
        }
        public void setname1(String name) {
            textName1.setText(name);
        }
    }
    //将要展示的数据传递进来，
    public HotGuessAdapter(List<Hotguss> list){
        this.mFruitList = list ;
    }
    //将fruit_item 布局加载进来，然后创建一个ViewHolder实例，
    //将加载的布局传给ViewHolder的构造函数中。就可以获取布局中的控件
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_hot_guess_item,parent,false);
        ViewHolder holder = new ViewHolder(view);
        holder.setHotGuessAdapterinterface(hotGuessAdapterinterface);
        return holder;
    }
    //这个方法会在屏幕滚动的时候执行
    // 通过position获取到Fruit 的实例，然后给布局上的控件进行赋值，
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
//        Fruit fruit = mFruitList.get(position);
//        holder.imageView.setImageResource(fruit.getImageId());
//        holder.TextName.setText(fruit.getName());
        holder.setname0("n:"+position);
        if(mFruitList!=null)
        {
            Hotguss mhotguss = mFruitList.get(position);
            holder.setname0(mhotguss.getName(0));
            holder.setname1(mhotguss.getName(1));
        }
    }
    //他用于告诉RecyclerView有多少子项，直接返回数据源的长度就行了
    @Override
    public int getItemCount() {
        return mFruitList.size();
    }
    public void setHotGuessAdapterinterface(HotGuessAdapterinterface hotGuessAdapterinterface)
    {
        this.hotGuessAdapterinterface=hotGuessAdapterinterface;
    }
    public interface HotGuessAdapterinterface {
        void showname(String toString);
    }
}
