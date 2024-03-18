package com.yangna.lbdsp.home.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.makeramen.roundedimageview.RoundedImageView;
import com.yangna.lbdsp.R;
import com.yangna.lbdsp.common.base.BaseApplication;
import com.yangna.lbdsp.common.utils.TimeUtils;
import com.yangna.lbdsp.home.bean.FirstLevelBean;
import com.yangna.lbdsp.home.model.GiveCommentsModel;
import com.yangna.lbdsp.videoCom.VideoBean;
import com.yangna.lbdsp.widget.VerticalCommentLayout;

import java.util.List;

import io.reactivex.annotations.NonNull;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * @author ganhuanhui
 * 时间：2019/12/19 0019
 * 描述：
 */
public class CommentDialogSingleAdapter extends BaseQuickAdapter<FirstLevelBean, BaseViewHolder> {

    Context context;
    List<FirstLevelBean> videoInfoList;

    private VerticalCommentLayout.CommentItemClickListener mItemClickListener;

    public CommentDialogSingleAdapter(Context context,VerticalCommentLayout.CommentItemClickListener mItemClickListener) {
        super(R.layout.item_comment_new);
        this.context = context;
        this.mItemClickListener = mItemClickListener;
    }

    public void addData(List<FirstLevelBean> datas) {
//        int oldCount  = videoInfoList.size();
        videoInfoList.addAll(datas);
//        notifyItemRangeInserted(oldCount, datas.size());
        notifyDataSetChanged();
    }



    @Override
    protected void convert(@NonNull BaseViewHolder helper, FirstLevelBean content) {

//            LinearLayout ll_like = helper.getView(R.id.ll_like);
//            RelativeLayout rl_group = helper.getView(R.id.rl_group);

        RoundedImageView iv_header = helper.getView(R.id.iv_header);
        ImageView iv_like = helper.getView(R.id.iv_like);
        TextView tv_like_count = helper.getView(R.id.tv_like_count);
        TextView tv_user_name = helper.getView(R.id.tv_user_name);
        TextView tv_content = helper.getView(R.id.tv_content);
        TextView tv_time = helper.getView(R.id.tv_time);


        iv_like.setImageResource(content.getIsLike() == 0 ? R.mipmap.icon_topic_post_item_like : R.mipmap.icon_topic_post_item_like_blue);
        tv_like_count.setText(content.getLikeCount() + "");
        tv_like_count.setVisibility(content.getLikeCount() <= 0 ? View.GONE : View.VISIBLE);

        tv_time.setText(TimeUtils.formatDate(content.getCreateTime(), "yyyy年MM月dd日HH时mm分ss秒E"));
        tv_content.setText(content.getContents());
        tv_user_name.setText(content.getNickName());

        if (!content.getLogo().equals("/") && context != null && iv_header != null && iv_header.getContext() != null) {
            Glide.with(context)
                    .load(content.getLogo())
                    .error(R.mipmap.user_icon) //异常时候显示的图片
                    .placeholder(R.mipmap.user_icon) //加载成功前显示的图片
                    .fallback(R.mipmap.user_icon) //url为空的时候,显示的图片
                    .apply(RequestOptions.circleCropTransform())//加载为圆形图片
                    .into(iv_header);
        }


        if (content.getSecondLevelBeans() != null) {
            VerticalCommentLayout commentWidget = helper.getView(R.id.verticalCommentLayout);
            commentWidget.setVisibility(View.VISIBLE);
            int size = content.getSecondLevelBeans().size();
            commentWidget.setTotalCount(size + 10);
            commentWidget.setPosition(helper.getAdapterPosition());
            commentWidget.setOnCommentItemClickListener(mItemClickListener);
            int limit = helper.getAdapterPosition() + 1;
            commentWidget.addCommentsWithLimit(content.getSecondLevelBeans(), size, false);
//                rl_group.setTag(commentWidget);
        }

    }
}
