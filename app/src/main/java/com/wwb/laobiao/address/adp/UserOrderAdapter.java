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

import com.wwb.laobiao.address.bean.Ordercreate;
import com.wwb.laobiao.address.bean.SelAdressModel;
import com.wwb.laobiao.address.model.AmountView;
import com.wwb.laobiao.instance.UserOrder;
import com.yangna.lbdsp.R;
import com.yangna.lbdsp.common.manager.BannerGlideImageLoader;
import com.youth.banner.BannerConfig;

import java.util.ArrayList;
import java.util.List;

public class UserOrderAdapter extends RecyclerView.Adapter<UserOrderAdapter.ViewHolder> {
    private List< Ordercreate> mFruitList;
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
        TextView text1;
        private com.youth.banner.Banner banner;
        private TextView txgoadress;
        private TextView txgoodsName;
        private TextView txgoodsPrice;
        private TextView txshopname;
        private TextView txrule;
        private AmountView amountView;
        int postion;
        private ArrayList<String> banner_list = new ArrayList<String>();

        private Context context;



        public ViewHolder(View View) {
            super(View);
            context=View.getContext();
//          recyclerView0=View.findViewById(R.id.recyclerView_0);
            banner =View.findViewById(R.id.goods_banner);
            txgoodsName =View.findViewById(R.id.textView_goodsName);
            txgoodsPrice =View.findViewById(R.id.textView_goodsPrice);
            txshopname =View.findViewById(R.id.textView_shop_name_iv);
            txrule =View.findViewById(R.id.textView_rule);
//            txgoodsnum =View.findViewById(R.id.textView_goodsnum);
            amountView =View.findViewById(R.id.amount_view);
            amountView.setGoods_storage(1000);

        }
        public void show(Ordercreate ordercreate) {
//            String str0=UserOrder.getInstance().getprovincename(userAdressModel.provinceId);
//            str0+=UserOrder.getInstance().getcityname(userAdressModel.cityId);
//            str0+=UserOrder.getInstance().getarename(userAdressModel.areaId);
//            text1.setText(str0);
//            text2.setText(userAdressModel.getaddress());
//            text3.setText(userAdressModel.getname());
//            text4.setText(userAdressModel.getmobile());//textView01
////     if(checkBox) text01
//            if(userAdressModel.isDefault==0)
//            {
//                text01.setVisibility(View.GONE);//GONE
//            }
//            else
//            {
//                text01.setVisibility(View.VISIBLE);
//            }
//            txrule.setText("规格:");
            txrule.setText(String.format("规格:%s",ordercreate.ruleName));
            txshopname.setText(ordercreate.dianming);
            txgoodsPrice.setText(String.format("%4.2f",ordercreate.fPrice*ordercreate.amount));
            txgoodsName.setText(ordercreate.goodsName);
            {
                banner_list.clear();
                banner_list.add(ordercreate.pictureUrl);
                banner.setBannerStyle(BannerConfig.RIGHT);
                banner.setImageLoader(new BannerGlideImageLoader()).setImages(banner_list).start();
                banner.stopAutoPlay();
            }
            amountView.setmount(ordercreate.amount);
            amountView.setOnAmountChangeListener(new AmountView.OnAmountChangeListener() {
                @Override
                public void onAmountChange(android.view.View view, int amount) {
//                    mFruitList.get(postion).amount=amount;
                    if(ordercreate!=null)
                    {
                        ordercreate.amount=amount;
                        txgoodsPrice.setText(String.format("%4.2f",ordercreate.fPrice*ordercreate.amount));
                        if(lineIdexInterface!=null)
                        {
                            lineIdexInterface.reshval(postion,ordercreate.fPrice*ordercreate.amount);
                        }
                    }
                }
            });
        }
        public void setInterface(LineIdexInterface lineIdexInterface) {
            this.lineIdexInterface=lineIdexInterface;
        }
    }
    //将要展示的数据传递进来，
    public UserOrderAdapter(List<Ordercreate> list){
        this.mFruitList = list ;//UserAdress  SelAdressModel.Recbody
        this.mlist=new ArrayList<>();
    }
    //将fruit_item 布局加载进来，然后创建一个ViewHolder实例，
    //将加载的布局传给ViewHolder的构造函数中。就可以获取布局中的控件  layout_item_funline
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_shop,parent,false);
        ViewHolder holder = new ViewHolder(view);
        holder.setInterface(lineIdexInterface);
        return holder;
    }
    //这个方法会在屏幕滚动的时候执行  dianming
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
          holder.show(mFruitList.get(position));
    }
    //他用于告诉RecyclerView有多少子项，直接返回数据源的长度就行了
    @Override
    public int getItemCount() {
        return mFruitList.size();
    }
    public  interface LineIdexInterface {
        void show(int postion, String toString1);
        void reshval(int postion,Object  object);
    }
}
