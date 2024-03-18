package com.yangna.lbdsp.home.view;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gyf.immersionbar.ImmersionBar;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yangna.lbdsp.R;
import com.yangna.lbdsp.common.base.BaseApplication;
import com.yangna.lbdsp.common.base.BasePresenterActivity;
import com.yangna.lbdsp.common.swiperefreshLayout.ToastUtil;
import com.yangna.lbdsp.home.impl.MallGoodsView;
import com.yangna.lbdsp.home.presenter.MallGoodsPresenter;
import com.yangna.lbdsp.mall.adapter.MallGoodsAdapter;
import com.yangna.lbdsp.mall.adapter.MallGoodsGridAdapter;
import com.yangna.lbdsp.mall.bean.MallGoodsModel;
import com.yangna.lbdsp.mall.bean.MallGoodsWPBLBaseModel;
import com.yangna.lbdsp.mall.bean.MallGoodsWPBLRecords;
import com.yangna.lbdsp.mall.view.GoodsActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import okhttp3.MediaType;

/* 门店页面 */
public class MallActivity extends BasePresenterActivity<MallGoodsPresenter> implements MallGoodsView {

    @BindView(R.id.mall_goods_smartRefreshLayout)
    RefreshLayout mallgoods_SmartRefreshLayout;
    @BindView(R.id.mall_goods_recyclerview)
    RecyclerView mallgoods_recyclerview;
    @BindView(R.id.mall_icon)
    ImageView goodsIcon;
    @BindView(R.id.mall_name)
    TextView goodsName;

    private SharedPreferences sharedPreferences = BaseApplication.getInstance().getSharedPreferences("AccountInfo", Context.MODE_PRIVATE);//SharedPreferences 是一个轻量级的存储类，主要是保存一些小的数据，一些状态信息

    private List<MallGoodsWPBLRecords> mallGoodsWPBLRecordsList = new ArrayList<>();
    private MallGoodsAdapter mallGoodsAdapter;
    private String shopId = "";//上一个页面传过来的shopId
    private String dianming = "";//上一个页面传过来的店名
    private int currentPage = 1;//本次刷新访问的第几页，包括首次访问
    private int page_amount;//当前总共有几页
    private int oldListSize;
    private int newListSize;
    private int addListSize;
    private boolean refreshType;//刷新参数，是否能刷新
    private int totalPage = 0;//总共能刷新几页，通过首次刷新后拿到的返回值计算出
    private String pageSize = "4";//pageSize手写的总页数，不写也行
    private String records_date;//门店内商品列表返回值 全文
    private boolean isLoadMore;//是否是底部加载更多
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_mall;
    }

    @Override
    protected MallGoodsPresenter setPresenter() {
        return new MallGoodsPresenter(this);
    }

    @Override
    protected void initView() {
        super.initView();
        ButterKnife.bind(this);
//        ImmersionBar.with(this).fitsSystemWindows(true).statusBarColor(R.color.black_21).init();//强制性保留手机电池、时间、信号状态栏和设置底色
        mPresenter.setMallGoodsView(this);
        Intent intent = getIntent();
        shopId = intent.getStringExtra("shopId");//传当前店铺id
        dianming = intent.getStringExtra("shopName");//传过来的店名
        goodsName.setText(intent.getStringExtra("shopName"));
        Glide.with(this).load(intent.getStringExtra("shopLogo"))
                .error(R.mipmap.ic_launcher) //异常时候显示的图片
                .placeholder(R.drawable.tjtp) //加载成功前显示的图片
                .fallback(R.mipmap.ic_launcher) //url为空的时候,显示的图片
                .apply(RequestOptions.circleCropTransform())//加载为圆形图片
                .into(goodsIcon);//为了加载网络图片的两步方法);

        /*开启自动加载功能（非必须）*/
        mallgoods_SmartRefreshLayout.setEnableAutoLoadMore(true);
        /*准备好第一次刷新状态*/
        mallgoods_SmartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull final RefreshLayout refreshLayout) {
                refreshLayout.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mallgoods_recyclerview.setVisibility(View.GONE);//隐藏一次
                        currentPage = 1;//清空一次当前页数，但是点击刷新按钮是清空所有页面数据，重新刷第一页列表，所以为1
                        page_amount = 0;//清空一次总页数
                        oldListSize = 0;//清空一次列表
                        newListSize = 0;//清空一次列表
                        addListSize = 0;//清空一次列表
                        refreshType = true;//手动设置为真，可刷新
                        mPresenter.getGoodsByShopId(context, shopId, currentPage, pageSize);
                        refreshLayout.finishRefresh();//完成刷新
                        refreshLayout.resetNoMoreData();//恢复没有更多数据的原始状态 setNoMoreData(false);
                    }
                }, 500);
            }
        });
        /*准备好加载更多的刷新状态*/
        mallgoods_SmartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull final RefreshLayout refreshLayout) {
                refreshLayout.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshType = false;//手动设置为假，并放在判断大小外面，才能保证列表连续，不然只能加载当前一页数据
                        if (currentPage > page_amount) {//如果页数大于当前总页数
                            ToastUtil.showToast("暂无更多的数据");
                            // 将不会再次触发加载更多事件
                            refreshLayout.finishLoadMoreWithNoMoreData();//完成加载并标记没有更多数据
                            return;//退出
                        }
                        oldListSize = 0;//清空一次列表
                        newListSize = 0;//清空一次列表
                        addListSize = 0;//清空一次列表
                        mPresenter.getGoodsByShopId(context, shopId, currentPage, pageSize);
                        refreshLayout.setEnableLoadMore(true);//设置是否启用上拉加载更多（默认启用）
                        refreshLayout.finishLoadMore();//完成加载
                    }
                }, 500);
            }
        });
        mallGoodsWPBLRecordsList.clear();
        currentPage = 1;//清空一次当前页数，但是点击刷新按钮是清空所有页面数据，重新刷第一页列表，所以为1
        page_amount = 0;//清空一次总页数
        oldListSize = 0;//清空一次列表
        newListSize = 0;//清空一次列表
        addListSize = 0;//清空一次列表
        refreshType = true;//手动设置为真，可刷新
        isLoadMore = false;//底部加载更多设为真
        currentPage = 1;//本次为第1页
        totalPage = 0;//总页数清空为0
        /* 启动联网获取商品列表 */
        mPresenter.getGoodsByShopId(context, shopId, currentPage, pageSize);
    }

    /* 退出当前页面 */
    @OnClick(R.id.mall_back)
    public void doBack(View view) {
        finish();
    }

    /* 跳转地图按钮 */
    @OnClick(R.id.tiaozhuan_ditu)
    public void doDiTu(View view){
        Intent intent = new Intent(MallActivity.this,LocationsimulatorActivity.class);
        startActivity(intent);

    }

    /* 我要开店 图片按钮 */
    @OnClick(R.id.mall_kaidian)
    public void doKDTP(View view) {//判断当前用户是否为商家
        String gongsizuojihaoma = "07723106005";
        //获取剪贴板管理器：
        ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        // 创建普通字符型ClipData
        ClipData mClipData = ClipData.newPlainText("Label", gongsizuojihaoma);
        // 将ClipData内容放到系统剪贴板里。
        cm.setPrimaryClip(mClipData);
        AlertDialog.Builder builder = new AlertDialog.Builder(MallActivity.this);
        builder.setTitle("请联系我们");
        builder.setMessage("公司热线：" + gongsizuojihaoma);
//        builder.setIcon(R.mipmap.ic_launcher);
        builder.setCancelable(true);//点击对话框以外的区域是否让对话框消失
        //设置正面按钮
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//                Toast.makeText(MallActivity.this, "你点击了确定", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Intent.ACTION_DIAL);
                Uri data = Uri.parse("tel:" + gongsizuojihaoma);
                intent.setData(data);
                startActivity(intent);
                dialog.dismiss();
            }
        });
        //设置反面按钮
//        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                Toast.makeText(MallActivity.this, "你点击了取消", Toast.LENGTH_SHORT).show();
//                dialog.dismiss();
//            }
//        });
        //设置中立按钮
//        builder.setNeutralButton("保密", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                Toast.makeText(MallActivity.this, "你选择了中立", Toast.LENGTH_SHORT).show();
//                dialog.dismiss();
//            }
//        });

        AlertDialog dialog = builder.create();      //创建AlertDialog对象
        //对话框显示的监听事件
//        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
//            @Override
//            public void onShow(DialogInterface dialog) {
//                Log.e("MallActivity", "对话框显示了");
//            }
//        });
        //对话框消失的监听事件
//        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
//            @Override
//            public void onCancel(DialogInterface dialog) {
//                Log.e("MallActivity", "对话框消失了");
//            }
//        });
        dialog.show();//显示对话框
    }

    /* 我要开店 文字按钮 */
    @OnClick(R.id.mall_woyaokaidian)
    public void doKDWZ(View view) {//判断当前用户是否为商家
        String gongsizuojihaoma = "07723106005";
        //获取剪贴板管理器：
        ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        // 创建普通字符型ClipData
        ClipData mClipData = ClipData.newPlainText("Label", gongsizuojihaoma);
        // 将ClipData内容放到系统剪贴板里。
        cm.setPrimaryClip(mClipData);
        AlertDialog.Builder builder = new AlertDialog.Builder(MallActivity.this);
        builder.setTitle("请联系我们");
        builder.setMessage("公司热线：" + gongsizuojihaoma);
//        builder.setIcon(R.mipmap.ic_launcher);
        builder.setCancelable(true);//点击对话框以外的区域是否让对话框消失
        //设置正面按钮
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//                Toast.makeText(MallActivity.this, "你点击了确定", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Intent.ACTION_DIAL);
                Uri data = Uri.parse("tel:" + gongsizuojihaoma);
                intent.setData(data);
                startActivity(intent);
                dialog.dismiss();
            }
        });
        //设置反面按钮
//        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                Toast.makeText(MallActivity.this, "你点击了取消", Toast.LENGTH_SHORT).show();
//                dialog.dismiss();
//            }
//        });
        //设置中立按钮
//        builder.setNeutralButton("保密", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                Toast.makeText(MallActivity.this, "你选择了中立", Toast.LENGTH_SHORT).show();
//                dialog.dismiss();
//            }
//        });

        AlertDialog dialog = builder.create();      //创建AlertDialog对象
        //对话框显示的监听事件
//        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
//            @Override
//            public void onShow(DialogInterface dialog) {
//                Log.e("MallActivity", "对话框显示了");
//            }
//        });
        //对话框消失的监听事件
//        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
//            @Override
//            public void onCancel(DialogInterface dialog) {
//                Log.e("MallActivity", "对话框消失了");
//            }
//        });
        dialog.show();//显示对话框
    }

    /* 我要开店 三个点按钮 */
    @OnClick(R.id.mall_caidan)
    public void doKDSGD(View view) {//判断当前用户是否为商家
        String gongsizuojihaoma = "07723106005";
        //获取剪贴板管理器：
        ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        // 创建普通字符型ClipData
        ClipData mClipData = ClipData.newPlainText("Label", gongsizuojihaoma);
        // 将ClipData内容放到系统剪贴板里。
        cm.setPrimaryClip(mClipData);
        AlertDialog.Builder builder = new AlertDialog.Builder(MallActivity.this);
        builder.setTitle("请联系我们");
        builder.setMessage("公司热线：" + gongsizuojihaoma);
//        builder.setIcon(R.mipmap.ic_launcher);
        builder.setCancelable(true);//点击对话框以外的区域是否让对话框消失
        //设置正面按钮
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//                Toast.makeText(MallActivity.this, "你点击了确定", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Intent.ACTION_DIAL);
                Uri data = Uri.parse("tel:" + gongsizuojihaoma);
                intent.setData(data);
                startActivity(intent);
                dialog.dismiss();
            }
        });
        //设置反面按钮
//        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                Toast.makeText(MallActivity.this, "你点击了取消", Toast.LENGTH_SHORT).show();
//                dialog.dismiss();
//            }
//        });
        //设置中立按钮
//        builder.setNeutralButton("保密", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                Toast.makeText(MallActivity.this, "你选择了中立", Toast.LENGTH_SHORT).show();
//                dialog.dismiss();
//            }
//        });

        AlertDialog dialog = builder.create();      //创建AlertDialog对象
        //对话框显示的监听事件
//        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
//            @Override
//            public void onShow(DialogInterface dialog) {
//                Log.e("MallActivity", "对话框显示了");
//            }
//        });
        //对话框消失的监听事件
//        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
//            @Override
//            public void onCancel(DialogInterface dialog) {
//                Log.e("MallActivity", "对话框消失了");
//            }
//        });
        dialog.show();//显示对话框
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        mGoodsDataAllList.clear();//清空列表
//        Log.e("销毁门店内", "商品列表页");
    }

    @Override/* 获取成功商品列表返回值 */
    public void onGetMallGoodsData(MallGoodsWPBLBaseModel result/*本次返回的列表值*/) {
        page_amount = (result.getBody().getTotalRecords() % Integer.valueOf(pageSize) == 0) ? (result.getBody().getTotalRecords() / Integer.valueOf(pageSize)) : (result.getBody().getTotalRecords() / Integer.valueOf(pageSize) + 1);//计算出来的总共能加载几页
        if (result.getBody().getRecords().size() == 0) {//本次返回的列表为0个条目，就退出该方法
            return;
        } else {
            if (refreshType && mallGoodsWPBLRecordsList != null) {//如果刷新状态为真，且
                mallGoodsWPBLRecordsList.clear();
                oldListSize = 0;
            } else {
                assert mallGoodsWPBLRecordsList != null;
                oldListSize = mallGoodsWPBLRecordsList.size();
            }
            mallGoodsWPBLRecordsList.addAll(result.getBody().getRecords());//数据载入数组总列表
            newListSize = mallGoodsWPBLRecordsList.size();
            addListSize = newListSize - oldListSize;
            if (refreshType) {
                // 设置RecyclerView样式为竖直线性布局
                final StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2/*按几列排布*/, StaggeredGridLayoutManager.VERTICAL);
                mallgoods_recyclerview.setLayoutManager(layoutManager);//getContext()可能有问题
                mallGoodsAdapter = new MallGoodsAdapter(MallActivity.this, mallGoodsWPBLRecordsList);//列表绑定适配器
                mallgoods_recyclerview.setAdapter(mallGoodsAdapter);
            } else {
                mallGoodsAdapter.notifyItemRangeInserted(mallGoodsWPBLRecordsList.size() - addListSize, mallGoodsWPBLRecordsList.size());
                mallGoodsAdapter.notifyItemRangeChanged(mallGoodsWPBLRecordsList.size() - addListSize, mallGoodsWPBLRecordsList.size());
            }
            currentPage++;
            mallgoods_recyclerview.setVisibility(View.VISIBLE);//显示 RecyclerView 布局

            /* item条目的点击事件回调 */
            mallGoodsAdapter.setmOnItemClickListener(new MallGoodsAdapter.OnItemClickListener() {
                @Override
                public void onItemClickListener(View view, int position) {
                    String id = mallGoodsWPBLRecordsList.get(position).getId();
                    String goodsName = mallGoodsWPBLRecordsList.get(position).getProductName();
                    String goodsPrice = mallGoodsWPBLRecordsList.get(position).getProductPrice();
                    List<String> tpsz = mallGoodsWPBLRecordsList.get(position).getMainImageUrls();
                    ArrayList<String> pictureUrlList = new ArrayList<String>();//轮播图
                    if (tpsz.size() == 0) {
                        for (int i = 0; i < 2; i++) {
                            pictureUrlList.add("https://bbs-fd.zol-img.com.cn/t_s800x5000/g6/M00/01/06/ChMkKmACcJKIbktFAAAMCxJMgToAAIX8wP_zB4AAAwj625.jpg");//没数组就写一个真实的url底图
                        }
                    } else {
                        for (int i = 0; i < tpsz.size(); i++) {
                            pictureUrlList.add(mallGoodsWPBLRecordsList.get(position).getMainImageUrls().get(i));//有数组就循环拿图
                        }
                    }
                    String goodsCount = mallGoodsWPBLRecordsList.get(position).getStockCount();
                    Intent goods = new Intent(MallActivity.this, GoodsActivity.class);
                    goods.putExtra("id", id);
                    goods.putExtra("goodsName", goodsName);
                    goods.putExtra("dianming", dianming);//传店名
                    goods.putExtra("goodsPrice", goodsPrice);
                    goods.putExtra("pictureUrlList", pictureUrlList);
                    goods.putExtra("goodsCount", goodsCount);
                    startActivity(goods);
                }
            });

        }
    }

}
