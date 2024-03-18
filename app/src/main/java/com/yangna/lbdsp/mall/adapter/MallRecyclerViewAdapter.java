package com.yangna.lbdsp.mall.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.makeramen.roundedimageview.RoundedImageView;
import com.yangna.lbdsp.R;
import com.yangna.lbdsp.common.base.BaseApplication;
import com.yangna.lbdsp.mall.bean.MallRecordsModel;
import com.yangna.lbdsp.mall.model.MallModel;

import java.util.List;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

import static com.yangna.lbdsp.MainActivity.display;

/* 门店适配器 */
public class MallRecyclerViewAdapter extends RecyclerView.Adapter<MallRecyclerViewAdapter.ViewHolder> {

    private Context mContext;
    private List<MallRecordsModel> mList;
    private OnItemClickListener mOnItemClickListener;

//    public MallRecyclerViewAdapter(Context context) {
//        this.mContext = context;
//    }

    public MallRecyclerViewAdapter(Context context, List<MallRecordsModel> mList) {
        this.mList = mList;
        this.mContext = context;
    }

//    public void notifyAdapter(List<MallModel> mallModelList, boolean isAdd) {
//        if (!isAdd) {
//            this.mList = mallModelList;
//        } else {
//            this.mList.addAll(mallModelList);
//        }
//        notifyDataSetChanged();
//    }

//    public List<MallModel> getMallModel() {
//        if (mList == null) {
//            mList = new ArrayList<>();
//        }
//        return mList;
//    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.mall_item, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        MallRecordsModel data = mList.get(position);
//        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        ViewGroup.LayoutParams params = holder.sidazhanshitu.getLayoutParams();
        params.width = metrics.widthPixels - 60;
        params.height = (metrics.widthPixels - 100) / 5;//门店的四大商品首图，以等宽四个显示
        holder.sidazhanshitu.setLayoutParams(params);

        //设置图片圆角角度
        Glide.with(mContext).load(data.getShopLogo())
                .error(R.mipmap.ic_launcher) //异常时候显示的图片
                .placeholder(R.drawable.tjtp) //加载成功前显示的图片
                .fallback(R.mipmap.ic_launcher) //url为空的时候,显示的图片
                .apply(RequestOptions.circleCropTransform())//加载为圆形图片
                .into(holder.mall_icon);//为了加载网络图片的两步方法

        holder.mall_name.setText(data.getShopName());//设置门店昵称
//        if (data.getEnterpriseCertification().equals("0")) {//企业认证与否
//            holder.mall_qyrz.setVisibility(View.GONE);
//        } else if (data.getEnterpriseCertification() == null || data.getEnterpriseCertification().equals("")) {//后台没给
//            holder.mall_qyrz.setVisibility(View.GONE);
//        } else {
//            holder.mall_qyrz.setImageResource(R.drawable.qyfw);
//            holder.mall_qyrz.setVisibility(View.VISIBLE);
//        }
        if (data.getShopLevelId() == null || data.getShopLevelId().equals("")) {//后台没给
            holder.mall_xx1.setVisibility(View.GONE);
            holder.mall_xx2.setVisibility(View.GONE);
            holder.mall_xx3.setVisibility(View.GONE);
            holder.mall_xx4.setVisibility(View.GONE);
            holder.mall_xx5.setVisibility(View.GONE);
        } else if (data.getShopLevelId().equals("1.0") || data.getShopLevelId().equals("1")) {//门店等级显示
            holder.mall_xx1.setImageResource(R.drawable.xing2);
            holder.mall_xx2.setImageResource(R.drawable.xing0);
            holder.mall_xx3.setImageResource(R.drawable.xing0);
            holder.mall_xx4.setImageResource(R.drawable.xing0);
            holder.mall_xx5.setImageResource(R.drawable.xing0);
        } else if (data.getShopLevelId().equals("1.5")) {
            holder.mall_xx1.setImageResource(R.drawable.xing2);
            holder.mall_xx2.setImageResource(R.drawable.xing1);
            holder.mall_xx3.setImageResource(R.drawable.xing0);
            holder.mall_xx4.setImageResource(R.drawable.xing0);
            holder.mall_xx5.setImageResource(R.drawable.xing0);
        } else if (data.getShopLevelId().equals("2.0") || data.getShopLevelId().equals("2")) {
            holder.mall_xx1.setImageResource(R.drawable.xing2);
            holder.mall_xx2.setImageResource(R.drawable.xing2);
            holder.mall_xx3.setImageResource(R.drawable.xing0);
            holder.mall_xx4.setImageResource(R.drawable.xing0);
            holder.mall_xx5.setImageResource(R.drawable.xing0);
        } else if (data.getShopLevelId().equals("2.5")) {
            holder.mall_xx1.setImageResource(R.drawable.xing2);
            holder.mall_xx2.setImageResource(R.drawable.xing2);
            holder.mall_xx3.setImageResource(R.drawable.xing1);
            holder.mall_xx4.setImageResource(R.drawable.xing0);
            holder.mall_xx5.setImageResource(R.drawable.xing0);
        } else if (data.getShopLevelId().equals("3.0") || data.getShopLevelId().equals("3")) {
            holder.mall_xx1.setImageResource(R.drawable.xing2);
            holder.mall_xx2.setImageResource(R.drawable.xing2);
            holder.mall_xx3.setImageResource(R.drawable.xing2);
            holder.mall_xx4.setImageResource(R.drawable.xing0);
            holder.mall_xx5.setImageResource(R.drawable.xing0);
        } else if (data.getShopLevelId().equals("3.5")) {
            holder.mall_xx1.setImageResource(R.drawable.xing2);
            holder.mall_xx2.setImageResource(R.drawable.xing2);
            holder.mall_xx3.setImageResource(R.drawable.xing2);
            holder.mall_xx4.setImageResource(R.drawable.xing1);
            holder.mall_xx5.setImageResource(R.drawable.xing0);
        } else if (data.getShopLevelId().equals("4.0") || data.getShopLevelId().equals("4")) {
            holder.mall_xx1.setImageResource(R.drawable.xing2);
            holder.mall_xx2.setImageResource(R.drawable.xing2);
            holder.mall_xx3.setImageResource(R.drawable.xing2);
            holder.mall_xx4.setImageResource(R.drawable.xing2);
            holder.mall_xx5.setImageResource(R.drawable.xing0);
        } else if (data.getShopLevelId().equals("4.5")) {
            holder.mall_xx1.setImageResource(R.drawable.xing2);
            holder.mall_xx2.setImageResource(R.drawable.xing2);
            holder.mall_xx3.setImageResource(R.drawable.xing2);
            holder.mall_xx4.setImageResource(R.drawable.xing2);
            holder.mall_xx5.setImageResource(R.drawable.xing1);
        } else if (data.getShopLevelId().equals("5.0") || data.getShopLevelId().equals("5")) {
            holder.mall_xx1.setImageResource(R.drawable.xing2);
            holder.mall_xx2.setImageResource(R.drawable.xing2);
            holder.mall_xx3.setImageResource(R.drawable.xing2);
            holder.mall_xx4.setImageResource(R.drawable.xing2);
            holder.mall_xx5.setImageResource(R.drawable.xing2);
        }
//        if (data.getFocus() == null || data.getFocus().equals("")) {
//            holder.mall_gz.setText("数据缺失");
//            holder.mall_gz.setBackgroundResource(R.drawable.bg_mall_yiguanzhu);
//        } else if (data.getFocus().equals("0")) {
//            holder.mall_gz.setText("关注");
//            holder.mall_gz.setBackgroundResource(R.drawable.bg_mall_guanzhu);
//        } else {
//            holder.mall_gz.setText("已关注");
//            holder.mall_gz.setBackgroundResource(R.drawable.bg_mall_yiguanzhu);
//        }
        holder.mall_fsgzs.setText("粉丝 " + data.getShopSubscribeCount());//设置门店昵称
//        if (data.getFirstFigure1() == null || data.getFirstFigure1().equals("")) {
//        } else {
//            Glide.with(mContext).load(data.getFirstFigure1())
//                    .error(R.mipmap.ic_launcher) //异常时候显示的图片
//                    .placeholder(R.drawable.tjtp) //加载成功前显示的图片
//                    .fallback(R.mipmap.ic_launcher) //url为空的时候,显示的图片
////                    .bitmapTransform(new CropCircleTransformation(BaseApplication.getContext()))//显示为圆形图片
//                    .into(holder.mall_shoutu1);//为了加载网络图片的两步方法
//        }
//        if (data.getFirstFigure2() == null || data.getFirstFigure2().equals("")) {
//        } else {
//            Glide.with(mContext).load(data.getFirstFigure2())
//                    .error(R.mipmap.ic_launcher) //异常时候显示的图片
//                    .placeholder(R.drawable.tjtp) //加载成功前显示的图片
//                    .fallback(R.mipmap.ic_launcher) //url为空的时候,显示的图片
////                    .bitmapTransform(new CropCircleTransformation(BaseApplication.getContext()))//显示为圆形图片
//                    .into(holder.mall_shoutu2);//为了加载网络图片的两步方法
//        }
//        if (data.getFirstFigure3() == null || data.getFirstFigure3().equals("")) {
//        } else {
//            Glide.with(mContext).load(data.getFirstFigure3())
//                    .error(R.mipmap.ic_launcher) //异常时候显示的图片
//                    .placeholder(R.drawable.tjtp) //加载成功前显示的图片
//                    .fallback(R.mipmap.ic_launcher) //url为空的时候,显示的图片
////                    .bitmapTransform(new CropCircleTransformation(BaseApplication.getContext()))//显示为圆形图片
//                    .into(holder.mall_shoutu3);//为了加载网络图片的两步方法
//        }
//        if (data.getFirstFigure4() == null || data.getFirstFigure4().equals("")) {
//        } else {
//            Glide.with(mContext).load(data.getFirstFigure4())
//                    .error(R.mipmap.ic_launcher) //异常时候显示的图片
//                    .placeholder(R.drawable.tjtp) //加载成功前显示的图片
//                    .fallback(R.mipmap.ic_launcher) //url为空的时候,显示的图片
////                    .bitmapTransform(new CropCircleTransformation(BaseApplication.getContext()))//显示为圆形图片
//                    .into(holder.mall_shoutu4);//为了加载网络图片的两步方法
//        }
        if (mOnItemClickListener != null) {
            // 设置item条目短按点击事件的监听
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickListener.onItemClickListener(holder.itemView, pos);
                }
            });
            // 设置item条目长按点击事件的监听
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickListener.onItemLongClick(holder.itemView, pos);
                    return false;
                }
            });
        }
    }

    @Override
    public int getItemCount() {//返回数目
        return mList.size();
    }


    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    // 定义item条目点击事件接口
    public interface OnItemClickListener {
        void onItemClickListener(View view, int position);

        void onItemLongClick(View view, int position);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout sidazhanshitu;
        private ImageView mall_icon;
        private TextView mall_name;
        private ImageView mall_qyrz;
        private ImageView mall_xx1;
        private ImageView mall_xx2;
        private ImageView mall_xx3;
        private ImageView mall_xx4;
        private ImageView mall_xx5;
        private Button mall_gz;
        private TextView mall_fsgzs;
        private ImageView mall_shoutu1;
        private ImageView mall_shoutu2;
        private ImageView mall_shoutu3;
        private ImageView mall_shoutu4;

        ViewHolder(View itemView) {
            super(itemView);
            sidazhanshitu = (LinearLayout) itemView.findViewById(R.id.sidazhanshitu);
            mall_icon = (ImageView) itemView.findViewById(R.id.mall_icon);
            mall_name = (TextView) itemView.findViewById(R.id.mall_name);
            mall_qyrz = (ImageView) itemView.findViewById(R.id.mall_qyrz);
            mall_xx1 = (ImageView) itemView.findViewById(R.id.mall_xx1);
            mall_xx2 = (ImageView) itemView.findViewById(R.id.mall_xx2);
            mall_xx3 = (ImageView) itemView.findViewById(R.id.mall_xx3);
            mall_xx4 = (ImageView) itemView.findViewById(R.id.mall_xx4);
            mall_xx5 = (ImageView) itemView.findViewById(R.id.mall_xx5);
            mall_gz = (Button) itemView.findViewById(R.id.mall_gz);
            mall_fsgzs = (TextView) itemView.findViewById(R.id.mall_fsgzs);
            mall_shoutu1 = (ImageView) itemView.findViewById(R.id.mall_shoutu1);
            mall_shoutu2 = (ImageView) itemView.findViewById(R.id.mall_shoutu2);
            mall_shoutu3 = (ImageView) itemView.findViewById(R.id.mall_shoutu3);
            mall_shoutu4 = (ImageView) itemView.findViewById(R.id.mall_shoutu4);
        }
    }

}
