package com.yangna.lbdsp.mall.adapter;

import android.view.View;
import android.widget.TextView;

import com.yangna.lbdsp.R;

public class GroupViewHolder extends CartViewHolder {
    public TextView textView;

    public GroupViewHolder(View itemView, int chekbox_id) {
        super(itemView, chekbox_id);
        textView = itemView.findViewById(R.id.tv);
    }
}
