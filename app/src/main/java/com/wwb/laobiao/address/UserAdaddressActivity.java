package com.wwb.laobiao.address;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.wwb.laobiao.address.adp.UserAdressAdapter;
import com.wwb.laobiao.address.bean.AdressInfor;
import com.wwb.laobiao.address.bean.AdressModel;
import com.wwb.laobiao.address.bean.AdressUser;
import com.wwb.laobiao.address.bean.DelListAdressInfor;
import com.wwb.laobiao.address.bean.SelAdressModel;
import com.wwb.laobiao.address.bean.UserAdressModel;
import com.wwb.laobiao.cangye.model.Controller;
import com.wwb.laobiao.instance.UserOrder;
import com.yangna.lbdsp.R;
import com.yangna.lbdsp.common.base.BaseApplication;

import java.util.ArrayList;
import java.util.List;

public class UserAdaddressActivity extends AppCompatActivity implements View.OnClickListener, UserOrder.Orderinterface, UserAdressAdapter.LineIdexInterface {
    private RecyclerView recyclerView0;
    private List<SelAdressModel.Body.record> mlsit0= UserOrder.getInstance().adressUser.mlist;
    private UserAdressAdapter adapter0;
    private TextView textViewdelt;
    private TextView textViewnew;
    private String HeadSTR0="删除",HeadSTR1="取消";
    private String BotmSTR0="+新建收货地址",BotmSTR1="确认删除";
    private int delcnt;
    Handler myHandle = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                {
                    recyclerView0=findViewById(R.id.recyclerView0);
                    findViewById(R.id.account_balance_back).setOnClickListener(UserAdaddressActivity.this);
                    textViewnew=findViewById(R.id.textView_0);
                    findViewById(R.id.textView_0).setOnClickListener(UserAdaddressActivity.this);
                    textViewdelt=findViewById(R.id.textViewh0);
                    findViewById(R.id.textViewh0).setOnClickListener(UserAdaddressActivity.this);

                    initcodelist();
                    initrecy();
                    if(adapter0!=null)
                    {
                        adapter0.setckbf(false);
                        textViewdelt.setText(HeadSTR0);
                        adapter0.notifyDataSetChanged();
                    }
                    UserOrder.getInstance().distip(true);
                }
                break;

            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_adaddress);
        UserOrder.getInstance().protip("加载中",this);
        AdressModel mmt = UserOrder.getInstance().getseladress();
        if(mmt.getid()<1)
        {
            UserOrder.getInstance().selAdress(UserAdaddressActivity.this, new UserOrder.Orderinterface() {
                @Override
                public void onresh(int idex) {
                    if(idex>=0)
                    {
                        Message mm=new Message();
                        mm.what=0;
                        myHandle.sendMessageDelayed(mm,1);
                    }
                    else
                    {
                        onBackPressed();
                    }
                }
            });
        }
        else
        {
            Message mm=new Message();
            mm.what=0;
            myHandle.sendMessageDelayed(mm,1);
        }

    }
    private void initcodelist() {
        if(mlsit0==null)
        {
            mlsit0=new ArrayList<>();
//            {
//                SelAdressModel.Recbody adressInfor=new SelAdressModel.Recbody();
//                mlsit0.add(adressInfor);
//            }
//            {
//                AdressModel adressInfor=new AdressModel();
//                mlsit0.add(adressInfor);
//            }
        }
    }

    private void initrecy() {
        LinearLayoutManager layout1 = new LinearLayoutManager(this);
        layout1.setOrientation(RecyclerView.VERTICAL);
        recyclerView0.setLayoutManager(layout1);
        adapter0 = new UserAdressAdapter(mlsit0);
        recyclerView0.setAdapter(adapter0);
        adapter0.setInterface(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.account_balance_back:
            {
                 onBackPressed();
            }
                break;
            case R.id.textViewh0:
            {
//                if()
                //textViewdelt
                reposet();
                adapter0.notifyDataSetChanged();
            }
            break;
            case R.id.textView_0:
            {
//                Bundle bundle=new Bundle();
//                Intent intent=new Intent(this, EditUserAddressActivity.class);
//                intent.putExtra("bundle",bundle);//CmdLineActivity
//                startActivityForResult(intent,1);//   \\
                if(adapter0.getckbf())
                {
//                    if(adapter0.checklist.size()==mlsit0.size())
//                    {
//                        return;
//                    }
                    List<AdressInfor> mlist1=new ArrayList<>();
                    for(int i=0;i<mlsit0.size();i++)
                    {
//                        adapter0.getItemId(0);
//                        if(!adapter0.checklist.get(i))
//                        {
//                            continue;
//                        }
                        if(!adapter0.getcheckbf(i))
                        {
                            continue;
                        }
                        SelAdressModel.Body.record mm = mlsit0.get(i);

                        AdressInfor adressInfor=new AdressInfor();

                        adressInfor.setname(mm.consignee);
                        adressInfor.accountId=mm.accountId;
                        adressInfor.id=mm.id;
                        adressInfor.isDefault=mm.isDefault;
                        adressInfor.latitude=mm.latitude;
                        adressInfor.longitude=mm.longitude;
                        adressInfor.mobile=mm.mobile;
                        adressInfor.provinceId=mm.provinceId;
                        adressInfor.cityId=mm.cityId;
                        adressInfor.areaId=mm.areaId;
                        adressInfor.setaddress(mm.getaddress());
                        mlist1.add(adressInfor);
                    }
//                    delcnt=mlist1.size();
                    DelListAdressInfor mmmb=new DelListAdressInfor();
                    mmmb.ids=new ArrayList<>();
                    for(int i=mlist1.size()-1;i>=0;i--)
                    {
                        DelListAdressInfor mm=new   DelListAdressInfor();
                        mmmb.ids.add(mlist1.get(i).id+"");
                    }
//
                    if(mmmb.ids.size()>0)
                    {
                        UserOrder.getInstance().protip("删除",this);

                        UserOrder.getInstance().dellistAdress(this,mmmb,this);
                    }

                }
                else
                {
                    Bundle bundle=new Bundle();
                    Intent intent=new Intent(this, EditUserAddressActivity.class);
                    bundle.putInt(EditUserAddressActivity.KEYSKY,0);
                    intent.putExtras(bundle);
                    startActivityForResult(intent,0);
                }
                Log.d("usd","textView1");//BotmSTR0

            }
            break;
        }
    }

    private void reposet() {
//        textViewdelt.get
        if(adapter0.getckbf())
        {
            adapter0.setckbf(false);
            textViewdelt.setText(HeadSTR0);//BotmSTR0
            textViewnew.setText(BotmSTR0);
        }
        else
        {
            adapter0.setckbf(true);
            textViewdelt.setText(HeadSTR1);
            textViewnew.setText(BotmSTR1);
        }

    }
    @Override
    public void onBackPressed() {
        Intent intent = getIntent();
        Bundle bundle = new Bundle();//csel
        intent.putExtras(bundle);
        setResult(0,intent);
        super.onBackPressed();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if(adapter0!=null)
            {
                adapter0.notifyDataSetChanged();
            }
            if (0 == requestCode) {
// add

            }
            else if (1 == requestCode)
            {
 // edit
            }
        }
    }

    @Override
    public void onresh(int idex) {

//        Toast.makeText(UserAdaddressActivity.this,"-->"+idex,Toast.LENGTH_SHORT).show();
       if(idex==6)
       {
           delcnt--;
           if(delcnt<=0)
           {
               UserOrder.getInstance().selAdress(this,this);
           }
       }
      else  if(idex==7)
        {
            UserOrder.getInstance().selAdress(this,this);
        }
        else if(idex==3)//selAdress
        {
               UserOrder.getInstance().distip(true);
               adapter0.setckbf(false);
               textViewdelt.setText(HeadSTR0);//BotmSTR0
               textViewnew.setText(BotmSTR0);
               adapter0.notifyDataSetChanged();
        }
        else if(idex<0)
           {
               UserOrder.getInstance().selAdress(this,this);
           }

    }

    @Override
    public void show(int postion, String toString1) {
        SelAdressModel.Body.record mm = mlsit0.get(postion);

        AdressInfor adressInfor=new AdressInfor();

        adressInfor.setname(mm.consignee);
        adressInfor.accountId=mm.accountId;
        adressInfor.id=mm.id;
        adressInfor.isDefault=mm.isDefault;
        adressInfor.latitude=mm.latitude;
        adressInfor.longitude=mm.longitude;
        adressInfor.mobile=mm.mobile;
        adressInfor.provinceId=mm.provinceId;
        adressInfor.cityId=mm.cityId;
        adressInfor.areaId=mm.areaId;
        adressInfor.setaddress(mm.getaddress());

        if(UserOrder.getInstance().provinces.size()<1)
        {
            UserOrder.getInstance().shopProvincelist(this, new UserOrder.Orderinterface() {
                @Override
                public void onresh(int idex) {
                    if(idex>=0)
                    {
                        Bundle bundle=new Bundle();
                        Intent intent=new Intent(UserAdaddressActivity.this, EditUserAddressActivity.class);
                        bundle.putSerializable(AdressInfor.KEY,adressInfor);
                        intent.putExtras(bundle);
                        bundle.putInt(EditUserAddressActivity.KEYSKY,1);
//        bundle.putSerializable(Funbean.KEYCMDLS, funbean);  //getprovincespos
                        startActivityForResult(intent,0);
                    }
                    else
                    {

                    }
                }
            }, false);
        }
        else
        {
            Bundle bundle=new Bundle();
            Intent intent=new Intent(UserAdaddressActivity.this, EditUserAddressActivity.class);
            bundle.putSerializable(AdressInfor.KEY,adressInfor);
            intent.putExtras(bundle);
            bundle.putInt(EditUserAddressActivity.KEYSKY,1);
//        bundle.putSerializable(Funbean.KEYCMDLS, funbean);  //getprovincespos
            startActivityForResult(intent,0);
        }


    }

    @Override
    public void setcheck(int postion, Boolean sbf) {

    }
}