package com.wwb.laobiao.address.adp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.wwb.laobiao.address.bean.AdressModel;
import com.wwb.laobiao.address.bean.DelAdressInfor;
import com.wwb.laobiao.address.bean.SelAdressModel;
import com.wwb.laobiao.address.bean.UserAdress;
import com.wwb.laobiao.instance.UserOrder;
import com.yangna.lbdsp.R;

import java.util.ArrayList;
import java.util.List;

public class UserAdressAdapter extends RecyclerView.Adapter<UserAdressAdapter.ViewHolder> {
    private List< SelAdressModel.Body.record> mFruitList;
    private LineIdexInterface lineIdexInterface;
    private Context context;
    private Boolean ckbf;
    public List<Boolean> mlist=new ArrayList<>();
    public void setInterface(LineIdexInterface lineIdexInterface) {
        this.lineIdexInterface=lineIdexInterface;
    }
    public void setckbf(boolean ckbf) {
       this.ckbf=ckbf;
    }
    public Boolean getckbf() {
        return this.ckbf;
    }

    public boolean getcheckbf(int post) {
       if(mlist==null)
       {
           return false;
       }
       if(mlist.size()!=mFruitList.size())
       {
           return false;
       }

        return  mlist.get(post);
    }

    //内部类，继承自RecyclerView.ViewHolder
    //接收一个View ，通常就是RecyclerView子项的最外层布局，
    //所以就可以用findViewById 来获取控件
    static class  ViewHolder extends RecyclerView.ViewHolder{
        public List<Boolean> mlist;
        private LineIdexInterface lineIdexInterface;
        TextView textidex;
        ImageView imageView;
        TextView text1;
        TextView text2;
        TextView text3;
        TextView text4;
        TextView text01;
        CheckBox checkBox;
//         <CheckBox
//        android:id="@+id/checkBox"
        int postion;

        private Context context;
        public ViewHolder(View View) {
            super(View);
            context=View.getContext();
//          recyclerView0=View.findViewById(R.id.recyclerView_0);
            textidex=View.findViewById(R.id.textView0);
            text01=View.findViewById(R.id.textView01);//广西柳州柳南区
            text1=View.findViewById(R.id.textView1);//广西柳州柳南区
            text2=View.findViewById(R.id.textView2);//广西柳南区航生路北33号
            text3=View.findViewById(R.id.textView3);//"周生"
            text4=View.findViewById(R.id.textView4);//13667899787
            checkBox=View.findViewById(R.id.checkBox);//13667899787
            imageView=View.findViewById(R.id.imageView0);
            // \\
            imageView.setOnClickListener(new android.view.View.OnClickListener() {
                @Override
                public void onClick(android.view.View v) {
// \\
                    if(lineIdexInterface!=null)
                    {
                        lineIdexInterface.show(postion,"");
                    }
                }
            });
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            if(mlist.size()>postion)
                            {
                                if(!isChecked)
                                {
                                    mlist.set(postion,false);
                                }
                                else
                                {
                                    mlist.set(postion,true);
                                }
                            }
//                    if(lineIdexInterface!=null)
//                    {
//                        lineIdexInterface.setcheck(postion,isChecked);
//                    }
                }
            });
        }
        public void show( SelAdressModel.Body.record userAdressModel) {
            AdressModel adressModel=new AdressModel();

            adressModel.setid(userAdressModel.id);
            adressModel.Areid=userAdressModel.areaId;
            adressModel.CityId=userAdressModel.cityId;
            adressModel.ProvinceId=userAdressModel.provinceId;
            adressModel.provinceName=userAdressModel.provinceName;
            adressModel.cityName=userAdressModel.cityName;
            adressModel.areaName=userAdressModel.areaName;

            adressModel.setaddress(userAdressModel.getaddress());
            adressModel.setmobile(userAdressModel.getmobile());
            adressModel.setname(userAdressModel.getname());

            text1.setText(adressModel.getaddress0());
            text2.setText(userAdressModel.getaddress());
            text3.setText(userAdressModel.getname());
            text4.setText(userAdressModel.getmobile());//textView01
//     if(checkBox) text01
            if(userAdressModel.isDefault==0)
            {
                text01.setVisibility(View.GONE);//GONE
            }
            else
            {
                text01.setVisibility(View.VISIBLE);
            }
        }
        public void setInterface(LineIdexInterface lineIdexInterface) {
            this.lineIdexInterface=lineIdexInterface;
        }
    }
    //将要展示的数据传递进来，
    public UserAdressAdapter(List< SelAdressModel.Body.record> list){
        this.mFruitList = list ;//UserAdress  SelAdressModel.Recbody
        this.mlist=new ArrayList<>();

    }
    //将fruit_item 布局加载进来，然后创建一个ViewHolder实例，
    //将加载的布局传给ViewHolder的构造函数中。就可以获取布局中的控件  layout_item_funline
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_address_item,parent,false);
        ViewHolder holder = new ViewHolder(view);
        holder.setInterface(lineIdexInterface);
        return holder;
    }
    //这个方法会在屏幕滚动的时候执行
    // 通过position获取到Fruit 的实例，然后给布局上的控件进行赋值，
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if((mFruitList==null))
           {
               return;
           }
//        Tracy tracy=mFruitList.get(position);
         if(mlist==null)
         {
             mlist=new ArrayList<>();
         }
         if(mlist.size()!=mFruitList.size())
         {
             mlist.clear();
             for(int i=0;i<mFruitList.size();i++)
             {
                 Boolean ee=false;
                 mlist.add(ee);
             }
         }
          holder.mlist=mlist;
          holder.postion=position;
          holder.textidex.setText(""+position);
          holder.show(mFruitList.get(position));
          if(ckbf)
          {
//            checklist.set(position,holder.checkBox.isChecked());
              holder.checkBox.setVisibility(View.VISIBLE);
              holder.imageView.setVisibility(View.GONE);
          }
          else
          {
              holder.checkBox.setVisibility(View.GONE);
              holder.imageView.setVisibility(View.VISIBLE);
          }

        holder.checkBox.setChecked(mlist.get(position));

    }
    //他用于告诉RecyclerView有多少子项，直接返回数据源的长度就行了
    @Override
    public int getItemCount() {
        return mFruitList.size();
    }
    public  interface LineIdexInterface {
        void show(int postion, String toString1);
        void setcheck(int postion, Boolean sbf);
    }
}
