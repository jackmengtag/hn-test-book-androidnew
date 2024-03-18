package com.wwb.laobiao.address;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.wwb.laobiao.Search.impl.Fruit;
import com.yangna.lbdsp.R;

import java.util.List;

//import com.qzy.laobiao.R;
//import com.qzy.laobiao.testview0.adp.FruitAdapter;
//import com.qzy.laobiao.testview0.adp.RecyclerViewAdapter;
//import com.qzy.laobiao.testview0.impl.Fruit;

public class LinearLayoutDialogTitle extends LinearLayout {
    private LayoutInflater inflater;
    private Button buttonadd;
    private ImageView imageView;
    private RecyclerView recyclerView;
    List<Fruit> fruitList;
    private LinearLayoutDialogTitleinterface linearLayoutDialogTitleinterface;
    private AlertDialog adressDialog;
//                        <Button
//                        android:id="@+id/buttonadd_iv"

    public LinearLayoutDialogTitle(Context context) {
        super(context);
        init(context,null);
    }
    public LinearLayoutDialogTitle(Context context, @Nullable AttributeSet attrs) {
        super(context,attrs);
        init(context,attrs);
    }
    private void init(Context context, AttributeSet attrs) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.layout_dlg_title, this);
        buttonadd=findViewById(R.id.buttonadd_iv);
        imageView=findViewById(R.id.imageViewhd);
//                        <ImageView
//        android:id="@+id/imageViewhd"
        buttonadd.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
               if(linearLayoutDialogTitleinterface!=null)
               {
                   linearLayoutDialogTitleinterface.onadd(0);
               }
            }
        });
        if(imageView!=null)
        {
            imageView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(linearLayoutDialogTitleinterface!=null)
                    {
                        linearLayoutDialogTitleinterface.onadd(1);
                    }
                }
            });
        }
    }
    public LinearLayoutDialogTitle(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public LinearLayoutDialogTitle(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context,attrs);
    }
    public void setinterface(LinearLayoutDialogTitleinterface linearLayoutDialogTitleinterface) {
        this.linearLayoutDialogTitleinterface=linearLayoutDialogTitleinterface;
    }

    public void setdlg(AlertDialog adressDialog) {
        this.adressDialog=adressDialog;
    }

    public interface LinearLayoutDialogTitleinterface {
        void onadd(int flat);
    }


}
