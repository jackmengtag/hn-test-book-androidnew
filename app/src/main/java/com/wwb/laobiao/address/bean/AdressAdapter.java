package com.wwb.laobiao.address.bean;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.yangna.lbdsp.R;

import java.util.List;

//import com.qzy.laobiao.R;
//import com.qzy.laobiao.testview0.impl.Fruit;

public class AdressAdapter extends RecyclerView.Adapter<AdressAdapter.ViewHolder> {
    private List <AdressModel> mFruitList;
    public int msel;
    AdressAdapterinterface adressAdapterinterface;
    public void setinterface(AdressAdapterinterface AdressAdapterinterface) {
        this.adressAdapterinterface=AdressAdapterinterface;
    }

    //内部类，继承自RecyclerView.ViewHolder
    //接收一个View ，通常就是RecyclerView子项的最外层布局，
    //所以就可以用findViewById 来获取控件
    static class  ViewHolder extends RecyclerView.ViewHolder{

        ImageView imageViewsel;
        TextView text0;
        TextView textpost;
        TextView text1;
        Button textdelt;
        Button textedit;
        AdressAdapterinterface adressAdapterinterface;
        public ViewHolder(View View) {
            super(View);


            imageViewsel = (ImageView)View.findViewById(R.id.imageView_sel);
            text0 = (TextView) View.findViewById(R.id.textView_1);
            text1 = (TextView) View.findViewById(R.id.textView_1);
            textdelt = (Button) View.findViewById(R.id.textView_delet);
            textedit = (Button) View.findViewById(R.id.textView_edit);
            textpost = (TextView) View.findViewById(R.id.textViewpionstion);
            textdelt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(android.view.View v) {
                    if(adressAdapterinterface!=null)
                    {
                        adressAdapterinterface.delt(textpost.getText().toString());
                    }
                }
            });
            textedit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(android.view.View v) {
                    if(adressAdapterinterface!=null)
                    {
                        adressAdapterinterface.edit(textpost.getText().toString());
                    }
                }
            });
            imageViewsel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(android.view.View v) {
                    if(adressAdapterinterface!=null)
                    {
                        adressAdapterinterface.onsel(textpost.getText().toString());
                    }
                }
            });
             View.setBackgroundResource(R.drawable.bg_4e71ff_r_22);
//            imageViewsel.setBackgroundResource(R.drawable.w_xingxing);
//            imageViewsel.setImageDrawable( mview.getContext().getResources().getDrawable(R.mipmap.b_bk));
//            imageViewsel.setBackgroundResource();
        }
        public void setpostion(String s) {
            textpost.setText(s);
        }
        public void showAdress(AdressModel adress) {
            text0.setText(adress.mobile);
            text1.setText(adress.address);
        }
        public void setsel(boolean b) {
//            if(b)
//            {
//                  imageViewsel.setBackgroundResource(R.drawable.y_xingxing);
////                imageViewsel.setImageDrawable( mview.getContext().getResources().getDrawable(R.drawable.y_xingxing));
//            }
//            else
//            {
//                imageViewsel.setBackgroundResource(R.drawable.w_xingxing);
////              imageViewsel.setImageDrawable( mview.getContext().getResources().getDrawable(R.drawable.w_xingxing));
//            }
        }
    }
    //将要展示的数据传递进来，
    public AdressAdapter(List<AdressModel> list){
        this.mFruitList = list ;
    }
    //将fruit_item 布局加载进来，然后创建一个ViewHolder实例，
    //将加载的布局传给ViewHolder的构造函数中。就可以获取布局中的控件
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_adress_item,parent,false);
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
        holder.adressAdapterinterface=adressAdapterinterface;
        holder.setpostion(""+position);
        AdressModel adress = mFruitList.get(position);
        if(msel==position)
        {
            holder.setsel(true);
        }
        else
        {
            holder.setsel(false);
        }
        holder.showAdress(adress);
    }
    //他用于告诉RecyclerView有多少子项，直接返回数据源的长度就行了
    @Override
    public int getItemCount() {
        return mFruitList.size();
    }

    public interface AdressAdapterinterface {
        void edit(String toString);

        void delt(String toString);

        void onsel(String toString);
    }
}
