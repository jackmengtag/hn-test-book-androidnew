package com.wwb.laobiao.address;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.taobao.accs.ChannelService;
import com.wwb.laobiao.address.bean.AdressInfor;
import com.wwb.laobiao.address.bean.CityInfor;
import com.wwb.laobiao.address.bean.SelAdressModel;
import com.wwb.laobiao.address.bean.UpAdressInfor;
import com.wwb.laobiao.instance.UserOrder;
import com.wwb.laobiao.video.ShuiYing;
import com.yangna.lbdsp.MainActivity;
import com.yangna.lbdsp.R;
import com.yangna.lbdsp.common.swiperefreshLayout.ToastUtil;

import java.util.ArrayList;

public class EditUserAddressActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener, UserOrder.Orderinterface {

    public static final String KEYSKY ="KEYSKY" ;//0 add 1 edit
    private int sky;
    private Context context;
    Switch mSwitch1;
    private Spinner spinner0,spinner1,spinner2;
    private  ArrayList<String> mlistmode0= UserOrder.getInstance().Provincelist;
    private  ArrayList<String> mlistmode1=UserOrder.getInstance().Citylist;
    private  ArrayList<String> mlistmode2=UserOrder.getInstance().Arealist;
    private String Provincestr;
    private String Citystr;
    private EditText editText1,editText2,editText3;
    private ArrayAdapter<String> myAdapter0;
    private ArrayAdapter<String> myAdapter1;
    private ArrayAdapter<String> myAdapter2;
    private AdressInfor useraddr;
    @SuppressLint("HandlerLeak")
    Handler myHandle = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 3:
                {

                }
                    break;
                case 2:
                {
                    if( msg.obj  instanceof String)
                    {
                        UserOrder.getInstance().shopArealist(context, (String) msg.obj,EditUserAddressActivity.this);
                    }
                }
                    break;
                case 1:
                {
                    if(Citystr==null)
                    {
                        Citystr="";
                    }
                    int sel=UserOrder.getInstance().getcitypos(useraddr.cityId);
                    spinner1.setSelection(sel);

                }
                    break;
                case 0:
                {
                    //        shopProvincelistm listmode0

                    if(mlistmode0.size()<1)
                    {
                        UserOrder.getInstance().protip("加载中",EditUserAddressActivity.this);
                        UserOrder.getInstance().shopProvincelist(context,EditUserAddressActivity.this,false);
                    }
                    else
                    {
                        Provincestr="";
                        int sel=UserOrder.getInstance().getprovincespos(useraddr.provinceId);
                        if(sel<0)
                        {
                            sel=1;
                        }
                        spinner0.setSelection(sel);

                    }
                }
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_address);
        context=this;
        Activitytransfer();

        findViewById(R.id.textView1).setOnClickListener(EditUserAddressActivity.this);
        findViewById(R.id.account_balance_back).setOnClickListener(EditUserAddressActivity.this);
        editText1=findViewById(R.id.editText1);
        editText2=findViewById(R.id.editText2);
        editText3=findViewById(R.id.editText3);

        mSwitch1 =findViewById(R.id.switch1);
        initswitch();
        spinner0 =findViewById(R.id.spinner1);
        spinner1 =findViewById(R.id.spinner2);
        spinner2 =findViewById(R.id.spinner3);
        {
            Provincestr=null;
            myAdapter0= new ArrayAdapter<String>(context,
                    android.R.layout.simple_spinner_dropdown_item, mlistmode0);
            spinner0.setAdapter(myAdapter0);
            spinner0.setOnItemSelectedListener(EditUserAddressActivity.this);
        }
        reshcity();
        reshara();
        reshaddress();
    }

    private void initswitch() {
        mSwitch1.setChecked(false);
//        mSwitch1.setSwitchTextAppearance(this,R.style.s_false);
//        mSwitch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                //控制开关字体颜色
//                if (b) {
//                    mSwitch1.setSwitchTextAppearance(EditUserAddressActivity.this,R.style.s_true);
//                }else {
//                    mSwitch1.setSwitchTextAppearance(EditUserAddressActivity.this,R.style.s_false);
//                }
//            }
//        });
    }

    private void reshaddress() {
        if(sky==0)
        {
            useraddr=new AdressInfor();
        }
        editText1.setText(useraddr.getname());
        if(useraddr.mobile=="")
        {

        }
        else
        {
            editText2.setText(useraddr.mobile);
        }

        if(useraddr.address=="")
        {

        }
        else
        {
            editText3.setText(useraddr.address);
        }


        if(useraddr.isDefault==0)
        {
            mSwitch1.setChecked(false);
        }
        else
        {
            mSwitch1.setChecked(true);
        }

//        shopProvincelistmlistmode0

              Message mm=new Message();
              mm.what=0;
              myHandle.sendMessageDelayed(mm,100);
    }
    private void reshcity() {
        {
            Citystr=null;
            myAdapter1 = new ArrayAdapter<String>(context,
                    android.R.layout.simple_spinner_dropdown_item, mlistmode1);
            spinner1.setAdapter(myAdapter1);
            spinner1.setOnItemSelectedListener(EditUserAddressActivity.this);
            if(myAdapter2!=null)
            {
                mlistmode2.clear();
                myAdapter2.notifyDataSetChanged();
            }
        }
    }
    private void reshara() {
        {
            myAdapter2 = new ArrayAdapter<String>(context,
                    android.R.layout.simple_spinner_dropdown_item, mlistmode2);
            spinner2.setAdapter(myAdapter2);
            spinner2.setOnItemSelectedListener(EditUserAddressActivity.this);
        }
    }
    private void Activitytransfer() {
        Intent intent = getIntent();
        Bundle bundle= intent.getExtras();
        if(bundle!=null)
        {
//     sky=bundle.getInt(KEYSKY,-1);   \\
            useraddr= (AdressInfor) bundle.getSerializable(AdressInfor.KEY);
        }
        if(useraddr==null)
        {
            sky=0;
            useraddr=new AdressInfor();
        }
        else
        {
            sky=1;
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.textView1://保存
            {

                  if(sky==0)//add
                  {
                      String Str0=ckoutedit();
                      if(Str0!=null)
                      {
                          Toast.makeText(context,"",Toast.LENGTH_SHORT).show();
                          UserOrder.getInstance().notetip(Str0,EditUserAddressActivity.this);
                          return;
                      }
                      UserOrder.getInstance().protip("添加地址中",this);
                      useraddr.setname(editText1.getText().toString());
                      useraddr.setmobile(editText2.getText().toString());
                      useraddr.setaddress(editText3.getText().toString());
                      if (mSwitch1.isChecked())
                      {
                          useraddr.isDefault = 1;
//                          for(int i=0;i<UserOrder.getInstance().adressUser.mlist.size();i++)
//                          {
//                              if(UserOrder.getInstance().adressUser.mlist.get(i).isDefault>0)
//                              {
//                                  SelAdressModel.Recbody mm = UserOrder.getInstance().adressUser.mlist.get(i);
//                                  AdressInfor adressInfor=new AdressInfor();
//                                  adressInfor.setname(mm.consignee);
//                                  adressInfor.accountId=mm.accountId;
//                                  adressInfor.id=mm.id;
//                                  adressInfor.isDefault=mm.isDefault;
//                                  adressInfor.latitude=mm.latitude;
//                                  adressInfor.longitude=mm.longitude;
//                                  adressInfor.mobile=mm.mobile;
//                                  adressInfor.provinceId=mm.provinceId;
//                                  adressInfor.cityId=mm.cityId;
//                                  adressInfor.areaId=mm.areaId;
//                                  adressInfor.isDefault=0;
//                                  UserOrder.getInstance().upAdress(context, adressInfor, new UserOrder.Orderinterface() {
//                                      @Override
//                                      public void onresh(int idex) {
//                                          if(idex>0)
//                                          {
//                                              UserOrder.getInstance().addadress(context,useraddr,this);
//                                          }
//                                      }
//                                  });
//                                  return;
//                              }
//                          }
                      }
                      else  //
                      {
                          useraddr.isDefault = 0;
                      }
                      UserOrder.getInstance().addadress(context,useraddr,this);
                  }
                  else  if(sky==1)//edit
                  {
                      UserOrder.getInstance().protip("修改地址中",this);
                      useraddr.setname(editText1.getText().toString());
                      useraddr.setmobile(editText2.getText().toString());
                      useraddr.setaddress(editText3.getText().toString());
                      if (mSwitch1.isChecked())
                      {
                          useraddr.isDefault = 1;
                      }
                      else
                      {
                          useraddr.isDefault = 0;
                      }
                      UserOrder.getInstance().upAdress(context,useraddr,this);
                  }
            }
            break;
            case R.id.account_balance_back:
            {
                finish();
            }
            break;

        }
    }

    private String ckoutedit() {
//        useraddr.setname(editText1.getText().toString());
//        useraddr.setmobile(editText2.getText().toString());
//        useraddr.setaddress(editText3.getText().toString());


        String string0;
        String Keystr0="请输入";
         string0=editText1.getText().toString();
         if(string0==null)
         {
             return "你没有输入收货人名字";
         }
         else if(string0.indexOf(Keystr0)>=0)
         {
             return string0;
         }
         else if(string0.length()<1)
         {
             return "你没有输入收货人名字";
         }

        string0=editText2.getText().toString();
        if(string0==null)
        {
            return "你没有输入收货人手机号码";
        }
        else if(string0.indexOf(Keystr0)>=0)
        {
            return string0;
        }
        else if(string0.length()!=11)
        {
            return "你输入收货人手机号码有误";
        }

        string0=editText3.getText().toString();
        if(string0==null)
        {
            return "你没有输入详细地址";
        }
        else if(string0.indexOf(Keystr0)>=0)
        {
            return string0;
        }
        else if(string0.length()<1)
        {
            return "你没有输入详细地址";
        }



        return null;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {

            case R.id.spinner1://shopProvincelist
                TextView vv= (TextView) view;
                if(vv!=null)
                {
                    useraddr.provinceId=""+UserOrder.getInstance().provinces.get(position).provinceId;
                    if(Provincestr!=null)
                    {
                        if(!vv.getText().toString().equals(Provincestr))
                        {
                            UserOrder.getInstance().shopCitylist(context,UserOrder.getInstance().getProvinceCode(vv.getText().toString()),this,false);
                        }
                    }
                    else
                    {
//                        UserOrder.getInstance().shopCitylist(context,UserOrder.getInstance().getProvinceCode(vv.getText().toString()),this,false);
                    }
                    Provincestr=vv.getText().toString();
                }
                break;
            case R.id.spinner2://shopCitylist
                TextView vv1= (TextView) view;
                if(vv1!=null)
                {
                     useraddr.cityId=""+UserOrder.getInstance().Citys.get(position).cityId;
//                    UserOrder.getInstance().shopArealist(context,UserOrder.getInstance().getCityCode(vv1.getText().toString()),this);
                     if(Citystr!=null)
                     {
                         if(!vv1.getText().toString().equals(Citystr))
                         {
                             UserOrder.getInstance().shopArealist(context,UserOrder.getInstance().getCityCode(vv1.getText().toString()),this);
                         }
                     }
                     else
                     {
//                         UserOrder.getInstance().shopArealist(context,UserOrder.getInstance().getCityCode(vv1.getText().toString()),this);
                         if(UserOrder.getInstance().Citys.size()==1)
                         {
                                           Message mm=new Message();
                                            mm.obj=UserOrder.getInstance().Citys.get(0).cityCode;
                                            mm.what=2;
                                            myHandle.sendMessageDelayed(mm,1);
                         }
                     }
                    Citystr=vv1.getText().toString();
                }
                break;
            case R.id.spinner3://shopArealist
                useraddr.areaId=""+UserOrder.getInstance().Areas.get(position).areaId;
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onresh(int idex) {
//        Toast.makeText(EditUserAddressActivity.this,"-->"+idex,Toast.LENGTH_SHORT).show();
          if(idex==2)
          {
              reshara();//Citystr
          }
       else if(idex==0)
        {
            UserOrder.getInstance().distip(true);
            Provincestr="";
            Citystr="";
            {
                Provincestr=null;
                myAdapter0= new ArrayAdapter<String>(context,
                        android.R.layout.simple_spinner_dropdown_item, mlistmode0);
                spinner0.setAdapter(myAdapter0);
                spinner0.setOnItemSelectedListener(EditUserAddressActivity.this);
                if(myAdapter1!=null)
                {
                    myAdapter1.clear();
                    myAdapter1.notifyDataSetChanged();
                }

            }
//            spinner0.setSelection(1);
//            myAdapter0.notifyDataSetChanged();
        }
          else if(idex==1)
          {
//              reshcity();
              myAdapter1 = new ArrayAdapter<String>(context,
                      android.R.layout.simple_spinner_dropdown_item, mlistmode1);
              spinner1.setAdapter(myAdapter1);
              spinner1.setOnItemSelectedListener(EditUserAddressActivity.this);
              if(myAdapter2!=null)
              {
                  mlistmode2.clear();
                  myAdapter2.notifyDataSetChanged();
              }
              Message mm=new Message();
              if(mlistmode1.size()>0)
              {
                  mm.obj=  mlistmode1.get(0);
              }
              mm.what=1;
              myHandle.sendMessageDelayed(mm,1);
          }
          else if(idex==3)//selAdress
          {
//                UserOrder.getInstance().protip("删除",this);
                UserOrder.getInstance().distip(true);
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putInt("sky",sky);
                intent.putExtras(bundle);
                setResult(Activity.RESULT_OK, intent);
                finish();
          }
          else if(idex==4)//add
          {
//              selAdress(context,null); upAdress
              UserOrder.getInstance().selAdress(context,this);
          }
          else if(idex==5)//upAdress
          {
              UserOrder.getInstance().selAdress(context,this);
          }
    }
}