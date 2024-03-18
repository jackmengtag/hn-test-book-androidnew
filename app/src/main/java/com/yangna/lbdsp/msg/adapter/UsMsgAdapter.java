package com.yangna.lbdsp.msg.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.wwb.laobiao.address.bean.AdressModel;
import com.wwb.laobiao.address.bean.SelAdressModel;
import com.wwb.laobiao.hongbao.widget.AvatarImageView;
import com.wwb.laobiao.popuplist.PopupList;
import com.yangna.lbdsp.R;
import com.yangna.lbdsp.msg.model.UsMsgModel;

import java.util.ArrayList;
import java.util.List;

public class UsMsgAdapter extends RecyclerView.Adapter<UsMsgAdapter.ViewHolder> {
    private List<UsMsgModel> mFruitList;
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
        ImageView imageView;
        LinearLayout konglay;
        TextView text1;//msg_0
        TextView text2;//msg_1
        TextView text3;//msg_2  kong
        AvatarImageView headimage;
        int postion;
        private List<String> popupMenuItemList = new ArrayList<>();



        private Context context;
        public ViewHolder(View View) {
            super(View);
            this.popupMenuItemList.clear();
            this.popupMenuItemList.add("删除");
            context=View.getContext();
            headimage=View.findViewById(R.id.jmui_avatar_iv);
            konglay=View.findViewById(R.id.kong);
            text1=View.findViewById(R.id.msg_0);//广西柳州柳南区
            text2=View.findViewById(R.id.msg_1);//广西柳南区航生路北33号
            text3=View.findViewById(R.id.msg_2);//"周生"
//            text4=View.findViewById(R.id.textView4);//13667899787
//            checkBox=View.findViewById(R.id.checkBox);//13667899787
//            imageView=View.findViewById(R.id.imageView0);
//            // \\
            konglay.setOnClickListener(new android.view.View.OnClickListener() {
                @Override
                public void onClick(android.view.View v) {
// \\
                    if(lineIdexInterface!=null)
                    {
                        lineIdexInterface.show(postion,"0");
                    }
                }
             });
            konglay.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(android.view.View view) {

                    int[] location = new int[2];
                    view.getLocationOnScreen(location);
                    PopupList popupList = new PopupList(view.getContext());
                    popupList.showPopupListWindow(view, postion, location[0] + view.getWidth() / 2,
                            location[1], popupMenuItemList, new PopupList.PopupListListener() {
                                @Override
                                public boolean showPopupList(View adapterView, View contextView, int contextPosition) {
                                    return true;
                                }

                                @Override
                                public void onPopupListClick(View contextView, int contextPosition, int position) {
                                    Toast.makeText(contextView.getContext(), contextPosition + "," + position, Toast.LENGTH_SHORT).show();
                                        if(lineIdexInterface!=null)
                                        {
                                            lineIdexInterface.show(contextPosition,"1");
                                        }
                                }
                            });

                    return true;
                }
            });
//            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                @Override
//                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                            if(mlist.size()>postion)
//                            {
//                                if(!isChecked)
//                                {
//                                    mlist.set(postion,false);
//                                }
//                                else
//                                {
//                                    mlist.set(postion,true);
//                                }
//                            }
////                    if(lineIdexInterface!=null)
////                    {
////                        lineIdexInterface.setcheck(postion,isChecked);
////                    }
//                }
//            });
        }
        public void show(UsMsgModel usMsgModel) {
            text1.setText(usMsgModel.name);//instructions
            text2.setText(usMsgModel.instructions);//instructions
            text3.setText(usMsgModel.time);//instructions

        }
        public void setInterface(LineIdexInterface lineIdexInterface) {
            this.lineIdexInterface=lineIdexInterface;
        }
    }
    //将要展示的数据传递进来，
    public UsMsgAdapter(List<UsMsgModel> list){
        this.mFruitList = list ;//UserAdress  SelAdressModel.Recbody
        this.mlist=new ArrayList<>();


    }
    //将fruit_item 布局加载进来，然后创建一个ViewHolder实例，
    //将加载的布局传给ViewHolder的构造函数中。就可以获取布局中的控件  layout_item_funline
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message,parent,false);
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
    }
}
