package com.wwb.laobiao.Search.adp;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.yangna.lbdsp.R;

import java.util.ArrayList;
import java.util.List;

//import com.qzy.laobiao.R;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.LinearViewHolder> {
    private Context mContext;
    private List<String> list=new ArrayList<>();
    public RecyclerViewAdapter(Context context) {
        this.mContext=context;
        for(int i=0;i<55;i++){
            list.add(String.format("%s-%s", i/10+1,i));
        }
    }
    @NonNull
    @Override
    public LinearViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new LinearViewHolder(LayoutInflater.from(mContext).inflate(R.layout.layout_staggere_grid_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull LinearViewHolder holder, int position) {

//         holder.textView.setText("num:"+position);
        {
//            String str7 = "<font color=\"#171717\">777777777777777777</font><font color=\"#7C1412\">555555555555555555555555天气不错</font>";
//            String str7 = "<font color=\"#171717\">一次一次一次一次一次一次一次一次一次一次一次一次一次一次一次一次一次</font><font color=\"#F3CC21\">#你好吗</font>";
//            holder.textView.setText(Html.fromHtml(str7));
//            holder.addview();
//            LinearLayoutHeadview linearLayoutheadview=new LinearLayoutHeadview(mContext);
//            holder.LinearLayoutshow(holder.layhead,linearLayoutheadview);
//
//            LinearLayoutBodyview linearLayoutBodyview=new LinearLayoutBodyview(mContext);
//            holder.LinearLayoutshow(holder.laybody,linearLayoutBodyview);
////
//            LinearLayoutBottomview linearLayoutbottomview=new LinearLayoutBottomview(mContext);
//            linearLayoutbottomview.setText("num:"+position);
//            holder.LinearLayoutshow(holder.laybottom,linearLayoutbottomview);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public class LinearViewHolder extends RecyclerView.ViewHolder{
//        private  TextView textView;
        private LinearLayout layhead;
        private LinearLayout laybody;
        private LinearLayout laybottom;
        private FragmentManager fragmentManager;//框架管理
        private FragmentTransaction fragmentTransaction;//Transaction 交易
        private List<Fragment> mFragments;//
        public LinearViewHolder(@NonNull View itemView) {
            super(itemView);
//            fragmentManager =;
//            textView=itemView.findViewById(R.id.textView);
            layhead=itemView.findViewById(R.id.head_iv);
            laybody=itemView.findViewById(R.id.rank10_iv);
            laybottom=itemView.findViewById(R.id.rule_iv);
//           mImageView=(ImageView) itemView.findViewById(R.id.iv);

        }

        public void addview() {
            LinearLayout layout =laybody;
            LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);// 定义布局管理器的参数
            layout.setOrientation(LinearLayout.VERTICAL);// 所有组件垂直摆放
            // 定义显示组件的布局管理器，为了简单，本次只定义一个TextView组件
            LinearLayout.LayoutParams text = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);// 定义文本显示组件
            TextView txt = new TextView(mContext);
            txt.setLayoutParams(text);// 配置文本显示组件的参数
            txt.setText("动态生成内容hhhhhhhhhhhhhhhhhhhhhhdddddddddse");// 配置显示文字
            txt.setTextSize(80);
            layout.addView(txt, text);
        }
        public void LinearLayoutshow(LinearLayout layout, View view) {
            LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);// 定义布局管理器的参数
            layout.setOrientation(LinearLayout.VERTICAL);// 所有组件垂直摆放
            // 定义显示组件的布局管理器，为了简单，本次只定义一个TextView组件
            LinearLayout.LayoutParams text = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);// 定义文本显示组件
              view.setLayoutParams(text);// 配置文本显示组件的参数
              layout.removeAllViews();
              layout.addView(view, text);
        }
        public void LinearLayoutshow1(LinearLayout layout, View view) {
            LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);// 定义布局管理器的参数
            layout.setOrientation(LinearLayout.HORIZONTAL);// 所有组件垂直摆放
            // 定义显示组件的布局管理器，为了简单，本次只定义一个TextView组件
            LinearLayout.LayoutParams text = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);// 定义文本显示组件
            view.setLayoutParams(text);// 配置文本显示组件的参数
            layout.removeAllViews();
            layout.addView(view, text);
        }
    }
}
