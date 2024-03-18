
package com.wwb.laobiao.cangye.Dialog.suozaidiqudlg;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yangna.lbdsp.R;

import java.util.ArrayList;

public class UsStyleAdapter extends BaseAdapter {

    private Context mContext;

    private ArrayList<UsStyleItem> mList;

    public UsStyleAdapter(Context context, ArrayList<UsStyleItem> list) {
        mContext = context;
        mList = list;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public UsStyleItem getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_dialog_style, null);
            holder = new ViewHolder();
            holder.tvName = (TextView) convertView.findViewById(R.id.tv_item_dialog_name);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        UsStyleItem item = getItem(position);
        holder.tvName.setText(item.mName);

        return convertView;
    }

    static class ViewHolder {
        TextView tvName;
    }

}
