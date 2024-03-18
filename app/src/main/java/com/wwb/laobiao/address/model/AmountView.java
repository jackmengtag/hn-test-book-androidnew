package com.wwb.laobiao.address.model;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.yangna.lbdsp.R;

////import com.qzy.laobiao.R;
////import com.qzy.laobiao.hongbao.widget.AvatarImageView;

public class AmountView extends LinearLayout implements View.OnClickListener, TextWatcher {
    private static final String TAG = "AmountView";

    private int amount = 1; //数量

    private int goods_storage = 1; //库存数

    private OnAmountChangeListener mListener; //数量变化的回调接口

    private EditText etAmount;//数量

    private TextView btnDecrease;//-按钮

    private TextView btnIncrease;//+按钮
    public AmountView(Context context) {

        this(context, null);

    }
//构造方法

    public AmountView(Context context, AttributeSet attrs) {

        super(context, attrs);

        LayoutInflater.from(context).inflate(R.layout.view_amount, this);

        etAmount =  findViewById(R.id.etAmount);

        btnDecrease = findViewById(R.id.btnDecrease);

        btnIncrease = findViewById(R.id.btnIncrease);

        btnDecrease.setOnClickListener(this);

        btnIncrease.setOnClickListener(this);

        etAmount.addTextChangedListener(this);

        //, AmountView_btnWidth, AmountView_tvWidth, AmountView_tvTextSize, AmountView_btnTextSize  ，是res/Values下的定义的  attrs.xml 内容，分别代表左右2边+-按钮的宽度 ，中间TextView的宽度，字体大小，btnTextSize

        TypedArray obtainStyledAttributes = getContext().obtainStyledAttributes(attrs, R.styleable.AmountView);

        int btnWidth = obtainStyledAttributes.getDimensionPixelSize(R.styleable.AmountView_btnWidth,          LayoutParams.WRAP_CONTENT);

        int tvWidth = obtainStyledAttributes.getDimensionPixelSize(R.styleable.AmountView_tvWidth, 80);

        int tvTextSize = obtainStyledAttributes.getDimensionPixelSize(R.styleable.AmountView_tvTextSize, 0);

        int btnTextSize = obtainStyledAttributes.getDimensionPixelSize(R.styleable.AmountView_btnTextSize, 0);

        obtainStyledAttributes.recycle();

        LayoutParams btnParams = new LayoutParams(btnWidth, LayoutParams.MATCH_PARENT);

        btnDecrease.setLayoutParams(btnParams);

        btnIncrease.setLayoutParams(btnParams);

        if (btnTextSize != 0) {

            btnDecrease.setTextSize(TypedValue.COMPLEX_UNIT_PX, btnTextSize);

            btnIncrease.setTextSize(TypedValue.COMPLEX_UNIT_PX, btnTextSize);

        }

        LayoutParams textParams = new LayoutParams(tvWidth, LayoutParams.MATCH_PARENT);

        etAmount.setLayoutParams(textParams);

        if (tvTextSize != 0) {

            etAmount.setTextSize(tvTextSize);

        }

    }

    //数量变化的回调接口

    public void setOnAmountChangeListener(OnAmountChangeListener onAmountChangeListener) {

        this.mListener = onAmountChangeListener;

    }

    //设置库存数量

    public void setGoods_storage(int goods_storage) {

        this.goods_storage = goods_storage;

    }

//加减按钮的点击事件，当数值改变时，调用OnAmountChangeListener回调接口

    @Override

    public void onClick(View v) {

        int i = v.getId();

        if (i == R.id.btnDecrease) {

            if (amount > 1) {

                amount--;

                etAmount.setText(amount + "");

            }

        } else if (i == R.id.btnIncrease) {

            if (amount < goods_storage) {

                amount++;

                etAmount.setText(amount + "");

            }

        }

        etAmount.clearFocus();



        if (mListener != null) {

            mListener.onAmountChange(this, amount);

        }

    }

    @Override

    public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

    @Override

    public void onTextChanged(CharSequence s, int start, int before, int count) {}


    @Override

    public void afterTextChanged(Editable s) {

        if (s.toString().isEmpty())

            return;

        amount = Integer.valueOf(s.toString());

        if (amount > goods_storage) {

            etAmount.setText(goods_storage + "");

            return;

        }

        if (mListener != null) {

            mListener.onAmountChange(this, amount);

        }

    }

    public void setmount(int amount) {
        this.amount=amount;
        etAmount.setText(amount + "");
    }

    public interface OnAmountChangeListener {

        void onAmountChange(View view, int amount);

    }

}
