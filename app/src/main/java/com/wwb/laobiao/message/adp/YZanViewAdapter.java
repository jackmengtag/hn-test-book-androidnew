package com.wwb.laobiao.message.adp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.wwb.laobiao.message.bean.ZanDataModel;
import com.yangna.lbdsp.R;

import java.util.List;

public class YZanViewAdapter extends RecyclerView.Adapter<YZanViewAdapter.ViewHolder> {

    private Context mContext;
    private List<ZanDataModel> mList;
    private ZanDataModel data;
    private OnItemClikListener mOnItemClikListener;

    public YZanViewAdapter(Context context, List<ZanDataModel> mList) {
        this.mContext = context;
        this.mList = mList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_fensi, parent, false);
        ViewHolder holder = new ViewHolder(view);//item_fensi  item_movie_layout
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        data = mList.get(position);
//        RequestOptions options = new RequestOptions()
//                .placeholder(R.drawable.img_default_movie)
//                .error(R.drawable.img_default_movie);
//        Glide.with(mContext).load(data.getDownimgurl()).apply(options).into(holder.home_item_movie_list_pic);
//        holder.home_item_movie_list_title.setText(data.getDownLoadName());
//        holder.home_item_movie_list_director.setText(data.getDirector());
//        holder.home_item_movie_list_Starring.setText(data.getStarring());
//        holder.home_item_movie_list_type.setText(data.getType());
//        holder.home_item_movie_list_regions.setText(data.getRegions());

        if (mOnItemClikListener != null) {
            // 设置item条目短按点击事件的监听
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClikListener.onItemClik(holder.itemView, pos);
                }
            });

            // 设置item条目长按点击事件的监听
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClikListener.onItemLongClik(holder.itemView, pos);
                    return false;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView home_item_movie_list_pic;
        private TextView home_item_movie_list_title;
        private TextView home_item_movie_list_director;
        private TextView home_item_movie_list_Starring;
        private TextView home_item_movie_list_type;
        private TextView home_item_movie_list_regions;

        public ViewHolder(View itemView) {
            super(itemView);
//            home_item_movie_list_pic = (ImageView) itemView.findViewById(R.id.home_item_movie_list_pic);
//            home_item_movie_list_title = (TextView) itemView.findViewById(R.id.home_item_movie_list_title);
//            home_item_movie_list_director = (TextView) itemView.findViewById(R.id.home_item_movie_list_director);
//            home_item_movie_list_Starring = (TextView) itemView.findViewById(R.id.home_item_movie_list_Starring);
//            home_item_movie_list_type = (TextView) itemView.findViewById(R.id.home_item_movie_list_type);
//            home_item_movie_list_regions = (TextView) itemView.findViewById(R.id.home_item_movie_list_regions);
        }
    }

    // 定义item条目点击事件接口
    public interface OnItemClikListener {
        void onItemClik(View view, int position);

        void onItemLongClik(View view, int position);
    }

    public void setItemClikListener(OnItemClikListener mOnItemClikListener) {
        this.mOnItemClikListener = mOnItemClikListener;
    }

}
