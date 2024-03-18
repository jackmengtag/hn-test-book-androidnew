package com.wwb.laobiao.cangye.Dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.wwb.laobiao.address.bean.AdressAdapter;
import com.wwb.laobiao.address.bean.AdressUser;
import com.yangna.lbdsp.R;

public class AdressDialog2 {
    private  Context context;
    private  AlertDialog adressDialog;
    private AdressDialoginterface adressDialoginterface;
    private RecyclerView recyclerView;
    private AdressUser adressUser;
    private AdressAdapter adapter;

    public AdressDialog2() {

    }
    public void onCreate(Context context) {
        {
            this.context=context;
            View view = LayoutInflater.from(context).inflate(
                    R.layout.dialog_address, null);
            adressDialog=new AlertDialog.Builder(context).setTitle("收货地址")
                    .setView(view).create();
            recyclerView=view.findViewById(R.id.recyclerview);
            Button mbok =view.findViewById(R.id.buttonok);
            mbok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(adressDialoginterface!=null)
                    {
                        adressDialoginterface.onsel(1);
                    }
                }
            });
            {
                TextView title = new TextView(context);
                title.setTextSize(20);
                title.setGravity(Gravity.CENTER);
//            title.setBackgroundResource(R.drawable.list);
                title.setText("收货地址");
                adressDialog.setCustomTitle(title);
            }

            adressDialog.setCancelable(false);
            {
                WindowManager.LayoutParams layoutParams = adressDialog.getWindow().getAttributes();
                layoutParams.gravity= Gravity.CENTER_VERTICAL;
                layoutParams.width= WindowManager.LayoutParams.MATCH_PARENT;
                layoutParams.height= WindowManager.LayoutParams.MATCH_PARENT;
                adressDialog.getWindow().getDecorView().setPadding(0, 0, 0, 0);
                adressDialog.getWindow().setAttributes(layoutParams);
            }
            LinearLayoutManager layout = new LinearLayoutManager(context);
            recyclerView.setLayoutManager(layout);
//            initrecy();

        }
    }
    private void initrecy() {

    }

    private void onedit(int pos) {
//        adapter.msel=adressUser.msel;
//        initrecy();
    }
    private void ondelt(int pos) {
//        adressUser.mlist.remove(pos);
//        adapter.msel=adressUser.msel;
//        initrecy();
    }
    private void onmrsel(int pos) {
//        adressUser.msel=pos;
//        adapter.msel=adressUser.msel;
//        initrecy();
    }
    public void show() {
        adressDialog.show();
    }
    public void setinterface(AdressDialoginterface adressDialoginterface) {
        this.adressDialoginterface=adressDialoginterface;
    }

    public void dismiss() {
        adressDialog.dismiss();
    }

    public void setadrss(AdressUser adressUser) {
        this.adressUser=adressUser;
    }



    public interface AdressDialoginterface {
        void onsel(int idex);
    }
}
