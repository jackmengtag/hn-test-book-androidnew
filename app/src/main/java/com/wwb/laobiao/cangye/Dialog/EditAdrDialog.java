package com.wwb.laobiao.cangye.Dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.wwb.laobiao.address.bean.AdressAdapter;
import com.wwb.laobiao.address.bean.AdressModel;
import com.wwb.laobiao.cangye.adp.AdressAdapter0;
import com.yangna.lbdsp.R;

public class EditAdrDialog {
    private  Context context;
    private ImageView imageView0,imageView1,imageView2;
    private  AlertDialog adressDialog;
    private EditAdressinterface adressDialoginterface;
    private RecyclerView recyclerView;
    private AdressAdapter adapter;
    private ListView mlistview1;
    private AdressAdapter0 adressAdapter0;
    private Button okbt;
    private EditText ed00,ed0,ed1;
    private AdressModel adressModel;
    private String names="修改收货地址";

    public EditAdrDialog() {

    }
    public void onCreate(Context context) {
        {
            this.context=context;
            View view = LayoutInflater.from(context).inflate(
                    R.layout.dialog_edit_adress, null);
            adressDialog=new AlertDialog.Builder(context).setTitle("收货地址")
                    .setView(view).create();
            {
                TextView title = new TextView(context);
                title.setTextSize(20);
                title.setGravity(Gravity.CENTER);
//              title.setBackgroundResource(R.drawable.list);//\\
                title.setText(names);//
                adressDialog.setCustomTitle(title);
            }
            {
                imageView0=view.findViewById(R.id.imageView0);
                imageView1=view.findViewById(R.id.imageView1);
                imageView2=view.findViewById(R.id.imageView2);

                ed00=view.findViewById(R.id.editText00);
                ed0=view.findViewById(R.id.editText0);
                ed1=view.findViewById(R.id.editText1);
                okbt=view.findViewById(R.id.buttonok);
                okbt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        adressDialog.dismiss();
                        if(adressDialoginterface!=null)
                        {
                            if(adressModel==null)
                            {
                                adressModel=new AdressModel();
                            }
                            adressModel.setname(ed00.getText().toString());
                            adressModel.setaddress(ed1.getText().toString());
                            adressModel.setmobile(ed0.getText().toString());
                            adressDialoginterface.setedit(adressModel);
                        }
                    }
                });
                if(adressModel==null)
                {
                    adressModel=new AdressModel();
                }
                ed00.setText(adressModel.getname());//ed00
                ed0.setText(adressModel.getmobile());
                ed1.setText(adressModel.getaddress());//ed00

                ed00.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
                ed00.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        imageView0.setVisibility(View.VISIBLE);
                        imageView1.setVisibility(View.GONE);
                        imageView2.setVisibility(View.GONE);
                        return false;
                    }
                });

                ed0.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        imageView0.setVisibility(View.GONE);
                        imageView1.setVisibility(View.VISIBLE);
                        imageView2.setVisibility(View.GONE);
                        return false;
                    }
                });
                ed1.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        imageView0.setVisibility(View.GONE);
                        imageView1.setVisibility(View.GONE);
                        imageView2.setVisibility(View.VISIBLE);
                        return false;
                    }
                });
                imageView0.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ed00.setText("");
                    }
                });
                imageView1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ed0.setText("");
                    }
                });
                imageView2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ed1.setText("");
                    }
                });
            }
        }
    }
    public void show() {
        adressDialog.show();
//        WindowManager.LayoutParams layoutParams = adressDialog.getWindow().getAttributes();
//        layoutParams.gravity= Gravity.CENTER_VERTICAL;
//        layoutParams.width= WindowManager.LayoutParams.MATCH_PARENT;
//        layoutParams.height= WindowManager.LayoutParams.MATCH_PARENT;
//        adressDialog.getWindow().setAttributes(layoutParams);

    }
    public void setinterface(EditAdressinterface adressDialoginterface) {
        this.adressDialoginterface=adressDialoginterface;
    }

    public void dismiss() {
        adressDialog.dismiss();
    }

    public void setadrss(AdressModel adressUser) {
        this.adressModel=adressUser;
    }

    public void setname(String names) {
        this.names=names;
    }

    public interface EditAdressinterface {

        void setedit(AdressModel adressModel);
    }
}
