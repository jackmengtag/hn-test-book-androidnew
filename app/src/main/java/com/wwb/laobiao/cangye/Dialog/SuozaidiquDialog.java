package com.wwb.laobiao.cangye.Dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.scwang.smartrefresh.layout.util.DensityUtil;
import com.wwb.laobiao.address.bean.AdressAdapter;
import com.wwb.laobiao.address.bean.AdressModel;
import com.wwb.laobiao.cangye.adp.AdressAdapter0;
import com.yangna.lbdsp.R;

public class SuozaidiquDialog {
    private  Context context;
    private ImageView imageView0,imageView1,imageView2;
    private  AlertDialog adressDialog;
    private EditAdressinterface adressDialoginterface;
    private RecyclerView recyclerView;
    private AdressAdapter adapter;
    private ListView mlistview1;
    private AdressAdapter0 adressAdapter0;
    private Button okbt;
    private TextView textView0,textView1,textView2;
    private AdressModel adressModel;
    private String names="修改收货地址";

    public SuozaidiquDialog() {

    }
    public void onCreate(Context context) {
        {
            this.context=context;
            View view = LayoutInflater.from(context).inflate(
                    R.layout.dialog_suozaidiqu, null);
            adressDialog=new AlertDialog.Builder(context)
                    .setView(view).create();
            {
                Window window = adressDialog.getWindow();
//                //设置弹出位置
                window.setGravity(Gravity.BOTTOM);
//                //设置弹出动画
//                //window.setWindowAnimations(R.style.main_menu_animStyle);
//                //设置对话框大小
                window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);


//                window.getDecorView().setPadding(0, 0, 0, 0);
//                WindowManager.LayoutParams attributes = window.getAttributes();
//                attributes.width = WindowManager.LayoutParams.MATCH_PARENT;
//                attributes.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
//                // 一定要重新设置, 才能生效
//                window.setAttributes(attributes);
            }
            {
//                Window window = adressDialog.getWindow();
//                WindowManager.LayoutParams lp = window.getAttributes();
//
//                lp.width = DensityUtil.dp2px(200);
//                lp.height = DensityUtil.dp2px(200);
//                window.setGravity(Gravity.CENTER);
//                window.setAttributes(lp);
//                window.setContentView(v);
            }
            {
                TextView title = new TextView(context);
                title.setTextSize(20);
                title.setGravity(Gravity.CENTER);
//              title.setBackgroundResource(R.drawable.list);//\\
                title.setText(names);//
                adressDialog.setCustomTitle(title);//
            }
            {
                textView0=view.findViewById(R.id.textView0);
                textView0.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onchangeset(v);
                    }
                });
                textView1=view.findViewById(R.id.textView1);
                textView1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onchangeset(v);
                    }
                });
                textView2=view.findViewById(R.id.textView2);
                textView2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onchangeset(v);
                    }
                });
            }


        }
    }

    private void onchangeset(View v) {
        if(v.getId()==textView0.getId())
        {
            textView2.setBackgroundResource(R.drawable.bg_4e71ff_r_5);
            textView1.setBackgroundResource(R.drawable.bg_4e71ff_r_5);
            textView0.setBackgroundResource(R.drawable.bg_btn_pink);
        }
        else  if(v.getId()==textView1.getId())
        {
            textView2.setBackgroundResource(R.drawable.bg_4e71ff_r_5);
            textView0.setBackgroundResource(R.drawable.bg_4e71ff_r_5);
            textView1.setBackgroundResource(R.drawable.bg_btn_pink);
        }
        else  if(v.getId()==textView2.getId())
        {
            textView1.setBackgroundResource(R.drawable.bg_4e71ff_r_5);
            textView0.setBackgroundResource(R.drawable.bg_4e71ff_r_5);
            textView2.setBackgroundResource(R.drawable.bg_btn_pink);
        }

        LocationSelDialog locationSelDialog;

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
