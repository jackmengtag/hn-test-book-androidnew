package com.yangna.lbdsp.mall.view;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yangna.lbdsp.R;
import com.yangna.lbdsp.common.ServiceConfig;
import com.yangna.lbdsp.common.UrlConfig;
import com.yangna.lbdsp.common.base.BaseApplication;
import com.yangna.lbdsp.common.base.LoadingDialog;
import com.yangna.lbdsp.common.swiperefreshLayout.ToastUtil;
import com.yangna.lbdsp.common.utils.SpUtils;
import com.yangna.lbdsp.mall.adapter.StoreManagementGoods2Adapter;
import com.yangna.lbdsp.mall.adapter.StoreManagementGoodsAdapter;
import com.yangna.lbdsp.mall.bean.MallGoodsBaseModel;
import com.yangna.lbdsp.mall.bean.MallGoodsModel;
import com.yangna.lbdsp.mall.bean.Records;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/* 门店管理页面 */
public class StoreManagementActivity extends AppCompatActivity {

    @BindView(R.id.store_management_smartRefreshLayout)
    RefreshLayout storeManagementSmartRefreshLayout;
    @BindView(R.id.store_management_recyclerview)
    RecyclerView storeManagementRecyclerView;
    @BindView(R.id.store_management_back)
    ImageView storeManagementBack;
    @BindView(R.id.store_management_icon)
    ImageView storeManagementIcon;
    @BindView(R.id.store_management_name)
    TextView storeManagementName;
    @BindView(R.id.store_management_change)
    TextView shangchuanshangpin_tv;

    private int i = 1;
//    private List<MallGoodsModel> mGoodsDataList = new ArrayList<>();
    private List<Records> oList2 = new ArrayList<>();
    private int oldListSize2;
    private int newListSize2;
    private int addListSize2;
    private StoreManagementGoods2Adapter smg_adapter = null;
    private String shopId = "";//商店Id
    private int currentPage = 1;//本次刷新访问的第几页，包括首次访问
    private String pageSize = "4";//pageSize手写的总页数，不写也行
    private int totalPage = 0;//总共能刷新几页，通过首次刷新后拿到的返回值计算出
    private String records_date;//门店内商品列表返回值 全文
    private String id = "";//自己的id，
    private String shangdianIDData = "";//获取自己商店id返回数据
    private String icon_url = "";//获取自己商店id返回头像网址
    private String storeName = "";//获取自己商店id返回店名
    private boolean sxsplb = false;//是否刷新商品列表
    private boolean refreshType;//刷新参数，是否能刷新
    private Boolean mRegister_SX_DPXX_Tag = false;
    private AlertDialog alert1;
    private AlertDialog alert2;
    private int page_amount;//当前总共有几页
    private LoadingDialog dialog;
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    /* 创建时运行 */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_management);
        ButterKnife.bind(this);

        // 在当前的 页面 中注册 广播
        IntentFilter filter = new IntentFilter();//实例化IntentFilter
        filter.addAction("SX_DPXX");//设置接收广播的类型 刷新店铺信息
        registerReceiver(this.broadcastReceiver_SX_DPXX, filter);//动态注册 刷新店铺信息 接收的一个广播
        mRegister_SX_DPXX_Tag = true;

        Intent intent = getIntent();
        id = intent.getStringExtra("id");//获取上一页传过来的id，基本的用户id

        // 开启自动加载功能（非必须）
        storeManagementSmartRefreshLayout.setEnableAutoLoadMore(true);
        //准备好第一次刷新状态
        storeManagementSmartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull final RefreshLayout refreshLayout) {
                refreshLayout.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        storeManagementRecyclerView.setVisibility(View.GONE);//隐藏一次？也能实现连续刷新
                        currentPage = 1;//清空一次当前页数，但是点击刷新按钮是清空所有页面数据，重新刷第一页列表，所以为1
                        page_amount = 0;//清空一次总页数
                        oldListSize2 = 0;//清空一次列表
                        newListSize2 = 0;//清空一次列表
                        addListSize2 = 0;//清空一次列表
                        refreshType = true;//手动设置为真，可刷新
                        //加载弹窗
//                        LoadingDialog.Builder loadBuilder = new LoadingDialog.Builder(StoreManagementActivity.this)
//                                .setMessage("加载中...")
//                                .setCancelable(true)//返回键是否可点击
//                                .setCancelOutside(false);//窗体外是否可点击
//                        dialog = loadBuilder.create();
//                        dialog.show();//显示弹窗
                        new PostThreadGetGoodsByShopId(shopId, String.valueOf(currentPage), pageSize).start();
                        refreshLayout.finishRefresh();//完成刷新
                        refreshLayout.resetNoMoreData();//恢复没有更多数据的原始状态 setNoMoreData(false);
                    }
                }, 500);
            }
        });
        //准备好加载更多的刷新状态
        storeManagementSmartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
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
                        oldListSize2 = 0;//清空一次列表
                        newListSize2 = 0;//清空一次列表
                        addListSize2 = 0;//清空一次列表
                        //使用POST方法向服务器发送数据
                        new PostThreadGetGoodsByShopId(shopId, String.valueOf(currentPage), pageSize).start();
                        refreshLayout.setEnableLoadMore(true);//设置是否启用上拉加载更多（默认启用）
                        refreshLayout.finishLoadMore();//完成加载
                    }
                }, 500);
            }
        });
        //触发自动刷新
        //storeManagementSmartRefreshLayout.autoRefresh();

        /* 开启自动加载功能（非必须） */
        //storeManagementSmartRefreshLayout.setEnableAutoLoadMore(true);

        shangchuanshangpin_tv.setVisibility(View.GONE);//先隐藏
//        mGoodsDataList.clear();
        //根据id拿id，这个写后台的人太菜了，导致ID要通过这种方法拿！2020年11月3日16:21:36
        new Thread(runnableGetShopByAccountId).start();
        sxsplb = true;//刷新商品列表设置为真
//        storeManagementSwipeRefreshLayout.setRefreshing(true);//自动刷新列表，这里暂时不用
    }

    /* 恢复时运行  */
//    @Override
//    public void onResume() {
//        super.onResume();
////        storeManagementRecyclerView.setVisibility(View.GONE);
////        mGoodsDataList.clear();
//        //根据id拿id，这个写后台的人太菜了，导致ID要通过这种方法拿！2020年11月3日16:21:36
//        new Thread(runnableGetShopByAccountId).start();
//    }

    public BroadcastReceiver broadcastReceiver_SX_DPXX = new BroadcastReceiver() {//实例化 刷新店铺信息 接收广播
        @Override//收到广播后，会自动回调onReceive(..)方法
        public void onReceive(Context context, Intent intent) {//刷新店铺信息，这里开始本页面的各项子线程重新启动
            Log.d("广播刷新店铺信息", "当前店铺信息");
            new Thread(runnableGetShopByAccountId).start();
            sxsplb = false;//刷新商品列表设置为真
//            SharedPreferences sp = BaseApplication.getInstance().getSharedPreferences("AccountInfo", MODE_PRIVATE);//SharedPreferences 是一个轻量级的存储类，主要是保存一些小的数据，一些状态信息
//            if (sp.getString("logo", "").equals("") || sp.getString("logo", "").equals("/") || sp.getString("logo", "") == null) {//没有图片URL
//                //不刷新图片显示，加载本地图片
//                roundedImageView.setImageResource(R.mipmap.user_icon);
//            } else {
////            RequestOptions options = new RequestOptions().placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher);//两张固定图片而已
//                Glide.with((getActivity())).load(sp.getString("logo", "")).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(roundedImageView);//为了加载网络图片的两步方法
//            }
//            mine_name.setText(sp.getString("nickName", "老表"));
//            jianjie.setText(sp.getString("des", "简介"));
//            diqu.setText(sp.getString("area", "地区"));
        }
    };

    /* 上层Activity返回刷新设定 */
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        Log.e("三码状态", "requestCode=" + requestCode + "resultCode=" + resultCode + "data=" + data);
//        switch (requestCode) {
//            case 1:
//                if (resultCode == RESULT_OK) {
//                    String result = data.getStringExtra("return");
//                    Log.d("上传商品返回", "不刷新店铺信息: " + result);
//                    //可能加个刷新商品列表的功能
//
//                } else if (resultCode == 5) {//指定返回
//                    String result = data.getStringExtra("return");
//                    Log.e("修改店铺信息返回", "刷新店铺信息:  " + result);
//                    new Thread(runnableGetShopByAccountId).start();
//                    sxsplb = false;//刷新商品列表设置为假
//                }
//                break;
//        }
//    }

    //子线程中的Handler 获取自己的商店id 联网判断  shop/shop/getShopByAccountId 是否联网成功
    private Handler mGetShopByAccountIdHT_net_judge;

    /* 子线程：使用POST方法向服务器发送 id  */
    private Runnable runnableGetShopByAccountId = new Runnable() {
        @Override
        public void run() {
            String ACCOUNT = (String) Objects.requireNonNull(SpUtils.get(StoreManagementActivity.this, UrlConfig.ACCOUNT, ""));
            try {
                JSONObject json = new JSONObject();
                json.put("accountId", id);
                RequestBody body = RequestBody.create(JSON, String.valueOf(json));
                Log.i("发送JSON", String.valueOf(json));
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(ServiceConfig.getRootUrl() + "/shop/shop/getShopByAccountId")//获取自己的商店id 端口
//                    .addHeader("_USER", uuid)//参数全部放Header里面
//                    .addHeader("Authorization", chaochangTOKEN)
                        .post(body)
                        .build();
                client.newCall(request).enqueue(new Callback() {
                    @SuppressLint("HandlerLeak")
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {//未知联网错误，全部显示
                        if (!StoreManagementActivity.this.isFinishing()) {
                            //dialog.dismiss();//销毁加载框
                            Log.e("获取AccountId未知联网错误", "失败 e=" + e);
                            Looper.prepare();
                            mGetShopByAccountIdHT_net_judge = new Handler() {
                                @Override
                                public void handleMessage(@NonNull Message msg) {
                                    super.handleMessage(msg);
                                    alert1 = new AlertDialog.Builder(StoreManagementActivity.this).create();
//                                    alert1.setIcon(R.mipmap.ic_launcher);//设置对话框的图标
                                    alert1.setTitle("未知联网错误");//设置对话框的标题
                                    alert1.setCancelable(false);
                                    alert1.setMessage(e + "\n\n请联系后台客服处理");//设置要显示的内容
                                    //添加确定按钮
                                    alert1.setButton(DialogInterface.BUTTON_POSITIVE, "确定", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
//                                //结束所有Activity，返回登录页
                                        }
                                    });
                                    alert1.show();// 创建对话框并显示
                                }
                            };
                            mGetShopByAccountIdHT_net_judge.sendEmptyMessage(1);
                            Looper.loop();
                        }
                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        if (!StoreManagementActivity.this.isFinishing()) {
                            //dialog.dismiss();//销毁加载框
                            assert response.body() != null;
                            shangdianIDData = response.body().string();//获取到数据
                            try {
                                jsonGetShopByAccountId(shangdianIDData);//把数据传入解析josn数据方法
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

    /* 解析 获取自己的商店id 返回值，存集合 */
    @SuppressLint("HandlerLeak")
    private void jsonGetShopByAccountId(String date) throws JSONException {//响应200代码后才会来到这里，但是还要再次判断"_CODE"=200的
        Message msg = new Message();//处理完成后给handler发送消息//处理完成后给handler发送消息
        //2020年11月15日23:50:22
        //{
        //	"body": {
        //		"id": 33,                                                   商店id
        //		"accountId": 1,
        //		"shopName": "扬纳",
        //		"address": "万达",
        //		"mapAddress": "万达",
        //		"status": "0",
        //		"createTime": 0,
        //		"shopLogoUrl": "http://lbdsp.com/1605454085950JMeFkc.jpg",
        //		"enterpriseCertification": "0",
        //		"mallLevel": "1",
        //		"focus": "0",
        //		"firstFigure1": null,
        //		"firstFigure2": null,
        //		"firstFigure3": null,
        //		"firstFigure4": null,
        //		"license": "0",
        //		"otherLicense": "0"
        //	},
        //	"state": 200,
        //	"msg": "请求成功"
        //}
        if (date != null && !date.equals("")) {//判断数据是空
            Log.i("获取自己的商店id 有返回值", "date = " + date);
            JSONObject jsonObject = new JSONObject(date);
            String body = jsonObject.getString("body");//获取返回数据中 body 的值
            if (body == null) {
                Log.e("获取自己的商店id 失败》》》", "返回body值null：" + date);
                //不显示上传商品按钮，不刷新店铺列表
                msg.what = 2;//失败
                mGetShopByAccountIdHT.sendMessage(msg);
            } else if (body.equals("")) {//
                Log.e("获取自己的商店id 失败》》》", "返回body值中文双引号空：" + date);
                //不显示上传商品按钮，不刷新店铺列表
                msg.what = 2;//失败
                mGetShopByAccountIdHT.sendMessage(msg);
            } else {
                Log.w("获取自己的商店id 成功》》》", "返回body值不是null也不是中文双引号空：" + date);
                JSONObject jsonObject1 = new JSONObject(body);
                shopId = jsonObject1.optString("id", "");//获取返回数据中 id 的值
                icon_url = jsonObject1.optString("shopLogoUrl", "");//获取返回数据中 shopLogoUrl 的值
                storeName = jsonObject1.optString("shopName", "");//获取返回数据中 shopName 的值
                msg.what = 1;
                mGetShopByAccountIdHT.sendMessage(msg);
            }
        } else {
            Log.e("获取自己的商店id失败", "没有任何返回值");
            msg.what = 2;//失败
            mGetShopByAccountIdHT.sendMessage(msg);
        }
    }

    //子线程 获取自己的商店id 判断的Handler 返回值判断
    @SuppressLint("HandlerLeak")
    private Handler mGetShopByAccountIdHT = new Handler() {
        @Override
        public void handleMessage(Message msg) {//在子线程中通知主线程，让主线程做更新操作
            switch (msg.what) {
                case 1://获取成功，可以显示头像和店名了
//                    RequestOptions options = new RequestOptions().placeholder(R.drawable.tjtp).error(R.drawable.kaidian);//两张固定图片而已
                    Glide.with(StoreManagementActivity.this)
                            .load(icon_url)
                            .error(R.mipmap.ic_launcher) //异常时候显示的图片
                            .placeholder(R.drawable.tjtp) //加载成功前显示的图片
                            .fallback(R.drawable.tjtp) //url为空的时候,显示的图片
                            .apply(RequestOptions.circleCropTransform())//加载为圆形图片
                            .into(storeManagementIcon);//为了加载网络图片的两步方法
                    storeManagementName.setText(storeName);
//                    shopId = id;//传当前店铺id
                    shangchuanshangpin_tv.setVisibility(View.VISIBLE);//
//                    storeManagementSwipeRefreshLayout.setVisibility(View.VISIBLE);//
                    if (sxsplb) {//刷新商品列表为真
//                        new Thread(runnableShangPin).start();//启动第二线程，拿当前店铺商品列表
                        currentPage = 1;//清空一次当前页数，但是点击刷新按钮是清空所有页面数据，重新刷第一页列表，所以为1
                        page_amount = 0;//清空一次总页数
                        oldListSize2 = 0;//清空一次列表
                        newListSize2 = 0;//清空一次列表
                        addListSize2 = 0;//清空一次列表
                        refreshType = true;//手动设置为真，可刷新
                        //加载弹窗
//                        LoadingDialog.Builder loadBuilder = new LoadingDialog.Builder(StoreManagementActivity.this)
//                                .setMessage("加载中...")
//                                .setCancelable(true)//返回键是否可点击
//                                .setCancelOutside(false);//窗体外是否可点击
//                        dialog = loadBuilder.create();
//                        dialog.show();//显示弹窗
                        new PostThreadGetGoodsByShopId(shopId, String.valueOf(currentPage), pageSize).start();
                    } else {
                        //不刷新
                    }
                    break;
                case 2://获取失败
                    shangchuanshangpin_tv.setVisibility(View.GONE);//隐藏
//                    storeManagementSwipeRefreshLayout.setVisibility(View.GONE);//隐藏
                    Toast toast = Toast.makeText(StoreManagementActivity.this, "获取自己的商店id失败", Toast.LENGTH_LONG);
                    LinearLayout layout = (LinearLayout) toast.getView();
                    TextView tv = (TextView) layout.getChildAt(0);
                    tv.setTextSize(25);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    break;
                default:
                    break;
            }
        }
    };

    //子线程中的Handler 获取商品 联网判断 account/shop/getShopByAddress 是否联网成功
    private Handler mShangPinHT_net_judge;

    /* 子线程：使用POST方法向服务器发送 、、 三个参数完成 获取商品列表 */
//    private Runnable runnableShangPin = new Runnable() {
//        @Override
//        public void run() {
//            records_date = "";//请求之前先清空一次
//            String chaochangTOKEN = (String) Objects.requireNonNull(SpUtils.get(StoreManagementActivity.this, UrlConfig.USERID, ""));
//
//            RequestShopProduct shopProduct = new RequestShopProduct();
//            shopProduct.setShopId(shopId);
//            shopProduct.setCurrentPage(String.valueOf(currentPage));
//            shopProduct.setPageSize(pageSize);
//
//            NetWorks.getInstance().getGoodsByShopId(StoreManagementActivity.this, shopProduct, new MyObserver<ShopGoodsResult>() {
//                @Override
//                public void onNext(ShopGoodsResult result) {
////                    storeManagementSwipeRefreshLayout.setRefreshing(false);
//                    if (UrlConfig.RESULT_OK == result.getState()) {
//                        if (refreshType && mGoodsDataList != null) {//如果刷新状态为真，且
//                            mGoodsDataList.clear();
//                            oldListSize2 = 0;
//                        } else {
//                            assert mGoodsDataList != null;
//                            oldListSize2 = mGoodsDataList.size();
//                        }
//                        for (TGoods mallGoodsModel : result.getBody().getRecords()) {
//                            MallGoodsModel data = new MallGoodsModel();
//                            data.setId(mallGoodsModel.getId());
//                            data.setShopId(mallGoodsModel.getShopId());
//                            data.setCateId(mallGoodsModel.getCateId());
//                            data.setGoodsName(mallGoodsModel.getGoodsName());
//                            data.setGoodsPrice(mallGoodsModel.getGoodsPrice());
//                            data.setGoodsAmount(mallGoodsModel.getGoodsAmount());
//                            data.setCoverVideoId(mallGoodsModel.getCoverVideoId());
//                            data.setCoverVideoUrl(mallGoodsModel.getCoverVideoUrl());
//                            data.setStatus(mallGoodsModel.getStatus());
//                            data.setPictureUrl(mallGoodsModel.getPictureUrl());
//                            //键值对加进列表
//                            mGoodsDataList.add(data);
//                        }
//                        if (mGoodsDataList.size() == 0) {
//                            Log.e("获取门店内商品失败", "当前门店shopId：" + shopId + " 没有商品");
//                            Toast toast = Toast.makeText(StoreManagementActivity.this, "没有数据", Toast.LENGTH_SHORT);
//                            LinearLayout layout = (LinearLayout) toast.getView();
//                            TextView tv = (TextView) layout.getChildAt(0);
//                            tv.setTextSize(15);
//                            toast.setGravity(Gravity.CENTER, 0, 0);
//                            toast.show();
//                        } else {
//                            totalPage = result.getBody().getTotalPages();//拿到总页数
//                            newListSize2 = mGoodsDataList.size();
//                            addListSize2 = newListSize2 - oldListSize2;
//                            if (refreshType) {
//                                storeManagementRecyclerView.setLayoutManager(new LinearLayoutManager(StoreManagementActivity.this, LinearLayoutManager.VERTICAL, false));
//                                storeManagementGoodsAdapter = new StoreManagementGoodsAdapter(StoreManagementActivity.this, mGoodsDataList);
//                                storeManagementRecyclerView.setAdapter(storeManagementGoodsAdapter);
//                            } else {
////                                storeManagementGoodsAdapter.notifyItemRangeInserted(mGoodsDataList.size() - addListSize2, mGoodsDataList.size());
//                                storeManagementGoodsAdapter.notifyItemRangeChanged(mGoodsDataList.size() - addListSize2, mGoodsDataList.size());
//                            }
//                            ++currentPage;//本次加载成功后再页数加1
//                            storeManagementRecyclerView.setVisibility(View.VISIBLE);
//                            /* 设置点击条目效果 */
////                            storeManagementRecyclerView.setOnItemClickListener(new ByRecyclerView.OnItemClickListener() {
////                                @Override
////                                public void onClick(View v, int position) {
////                    String mendian_Id = mGoodsDataList.get(position).getId();
////                    Intent shopId = new Intent(MallActivity.this, GoodsActivity.class);
////                    shopId.putExtra("shopId", mendian_Id);
////                    startActivity(shopId);
////                    UIManager.switcher(getContext(), MallActivity.class);//点击跳转到下一页
////                                }
////                            });
//                            /* 设置长按条目效果 */
////                            storeManagementRecyclerView.setOnItemLongClickListener(new ByRecyclerView.OnItemLongClickListener() {
////                                @Override
////                                public boolean onLongClick(View v, int position) {
////                                    Toast toast = Toast.makeText(StoreManagementActivity.this, "长按提示", Toast.LENGTH_LONG);
////                                    LinearLayout layout = (LinearLayout) toast.getView();
////                                    TextView tv = (TextView) layout.getChildAt(0);
////                                    tv.setTextSize(15);
////                                    toast.setGravity(Gravity.CENTER, 0, 0);
////                                    toast.show();
////                                    return false;
////                                }
////                            });
//                        }
//                    } else {
//                        Log.e("获取门店内商品失败", "当前门店shopId：" + shopId + " 没有商品，" + result.getBody().toString() + result.getState() + result.getMsg().toString());
//                        Toast toast = Toast.makeText(StoreManagementActivity.this, result.getMsg(), Toast.LENGTH_SHORT);
//                        LinearLayout layout = (LinearLayout) toast.getView();
//                        TextView tv = (TextView) layout.getChildAt(0);
//                        tv.setTextSize(15);
//                        toast.setGravity(Gravity.CENTER, 0, 0);
//                        toast.show();
//                    }
//                }
//
//                @Override
//                public void onError(Throwable e) {
////                    storeManagementSwipeRefreshLayout.setRefreshing(false);
//                    ToastManager.showToast(StoreManagementActivity.this, e);
//                }
//            });
////            try {
////                JSONObject json = new JSONObject();
////                json.put("shopId", shopId);
////                RequestBody body = RequestBody.create(JSON, String.valueOf(json));
////                Log.i("发送JSON", String.valueOf(json));
////                OkHttpClient client = new OkHttpClient();
////                Request request = new Request.Builder()
////                        .url(ServiceConfig.getRootUrl() + "/account/good/getGoodsByShopId")//门店内商品列表 端口
//////                    .addHeader("_USER", uuid)//参数全部放Header里面
//////                    .addHeader("Authorization", chaochangTOKEN)
////                        .post(body)
////                        .build();
////                client.newCall(request).enqueue(new Callback() {
////                    @SuppressLint("HandlerLeak")
////                    @Override
////                    public void onFailure(@NonNull Call call, @NonNull IOException e) {//未知联网错误，全部显示
////                        if (!StoreManagementActivity.this.isFinishing()) {
////                            Log.e("getGoodsByShopId 未知联网错误", "失败 e=" + e);
////                            Looper.prepare();
////                            mShangPinHT_net_judge = new Handler() {
////                                @Override
////                                public void handleMessage(@NonNull Message msg) {
////                                    super.handleMessage(msg);
////                                    //
////                                }
////                            };
////                            mShangPinHT_net_judge.sendEmptyMessage(1);
////                            Looper.loop();
////                        }
////                    }
////
////                    @Override
////                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
////                        if (!StoreManagementActivity.this.isFinishing()) {
////                            assert response.body() != null;
////                            records_date = response.body().string();//获取到数据
////                            try {
////                                jsongetGoodsByShopId(records_date);//把数据传入解析josn数据方法
////                            } catch (JSONException e) {
////                                e.printStackTrace();
////                            }
////                        }
////                    }
////                });
////
////            } catch (JSONException e) {
////                e.printStackTrace();
////            }
//        }
//    };

    /* 解析 获取门店内商品列表 返回值，存集合 */
//    @SuppressLint("HandlerLeak")
//    private void jsongetGoodsByShopId(String date) throws JSONException {//响应200代码后才会来到这里，但是还要再次判断"_CODE"=200的
//        Message msg = new Message();//处理完成后给handler发送消息//处理完成后给handler发送消息
//        if (date != null && !date.equals("")) {//判断数据是空
//            // 门店内商品列表 /account/good/getGoodsByShopId 接口返回值
//            //{
//            //  "currentPage": 0,
//            //  "maxResults": 10,
//            //  "totalPages": 0,
//            //  "totalRecords": 0,
//            //  "records": [
//            //    {
//            //      "id": 1,
//            //      "shopId": 1,                                            门店id
//            //      "cateId": 1,                                            分类id.1食品  2用品  剩余默认为0
//            //      "goodsName": "apple",                                   商品名称
//            //      "goodsPrice": 29.8,                                     商品价格
//            //      "goodsAmount": 1,                                       商品数量
//            //      "coverVideoId": 1,                                      商品介绍视频
//            //      "coverVideoUrl": 1,                                     商品介绍视频链接
//            //      "status": "1",                                          商品状态0正常1下架2删除
//            //      "pictureUrl": "http://img11.360buyimg.com/n3/4408a.jpg" 图片路径
//            //    }
//            //  ]
//            //}
//            JSONObject jsonObject = new JSONObject(date);
//            String records = jsonObject.optString("records", "");//获取返回数据中 records 的值
//            if (records == null || records.equals("")) {
//                Log.e("获取门店内商品列表失败》》》", "返回records值：" + date);
//                msg.what = 2;//失败
//                mShangPinHT.sendMessage(msg);
//            } else {//
//                Log.w("获取门店内商品列表成功》》》", "返回records值：" + date);
//                msg.what = 1;
//                mShangPinHT.sendMessage(msg);
//            }
//        } else {
//            Log.e("获取门店内商品列表失败", "没有任何返回值");
//            msg.what = 2;//失败
//            mShangPinHT.sendMessage(msg);
//        }
//    }

    /* 子线程 获取门店内商品列表 判断的Handler 返回值判断 */
//    @SuppressLint("HandlerLeak")
//    private Handler mShangPinHT = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {//在子线程中通知主线程，让主线程做更新操作
//            switch (msg.what) {
//                case 1://获取成功
//                    parsingShangPinListJson();
//                    break;
//                case 2://获取失败
//                    Toast toast = Toast.makeText(StoreManagementActivity.this, "获取门店内商品列表失败", Toast.LENGTH_LONG);
//                    LinearLayout layout = (LinearLayout) toast.getView();
//                    TextView tv = (TextView) layout.getChildAt(0);
//                    tv.setTextSize(25);
//                    toast.setGravity(Gravity.CENTER, 0, 0);
//                    toast.show();
//                    break;
//                default:
//                    break;
//            }
//        }
//    };

    /* 解析显示商品列表数据 */
//    private void parsingShangPinListJson() {
//        try {
//            Gson gson = new Gson();
//            MallGoodsBaseModel mallGoodsBaseModel = gson.fromJson(records_date, MallGoodsBaseModel.class);
//            if (records_date.equals("")) {
//                Log.e("获取门店内商品失败", "没有任何返回值");
//                storeManagementSwipeRefreshLayout.setRefreshing(false);
//                return;
//            } else {
//                List<MallGoodsModel> mallGoodsModelList = mallGoodsBaseModel.getRecords();
//                for (MallGoodsModel mallGoodsModel : mallGoodsModelList) {
//                    MallGoodsModel data = new MallGoodsModel();
//                    data.setId(mallGoodsModel.getId());
//                    data.setShopId(mallGoodsModel.getShopId());
//                    data.setCateId(mallGoodsModel.getCateId());
//                    data.setGoodsName(mallGoodsModel.getGoodsName());
//                    data.setGoodsPrice(mallGoodsModel.getGoodsPrice());
//                    data.setGoodsAmount(mallGoodsModel.getGoodsAmount());
//                    data.setCoverVideoId(mallGoodsModel.getCoverVideoId());
//                    data.setCoverVideoUrl(mallGoodsModel.getCoverVideoUrl());
//                    data.setStatus(mallGoodsModel.getStatus());
//                    data.setPictureUrl(mallGoodsModel.getPictureUrl());
//                    //键值对加进列表
//                    mGoodsDataList.add(data);
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        if (mGoodsDataList.size() == 0) {
//            Log.e("获取门店内商品失败", "当前门店shopId：" + shopId + " 没有商品");
//            Toast toast = Toast.makeText(StoreManagementActivity.this, "没有数据", Toast.LENGTH_SHORT);
//            LinearLayout layout = (LinearLayout) toast.getView();
//            TextView tv = (TextView) layout.getChildAt(0);
//            tv.setTextSize(15);
//            toast.setGravity(Gravity.CENTER, 0, 0);
//            toast.show();
//        } else {
//            storeManagementRecyclerView.setVisibility(View.VISIBLE);
//            storeManagementRecyclerView.setLayoutManager(new LinearLayoutManager(StoreManagementActivity.this, LinearLayoutManager.VERTICAL, false));
//            storeManagementRecyclerView.setAdapter(storeManagementGoodsAdapter);
//
//            //设置点击条目效果
//            storeManagementRecyclerView.setOnItemClickListener(new ByRecyclerView.OnItemClickListener() {
//                @Override
//                public void onClick(View v, int position) {
////                    String mendian_Id = mGoodsDataList.get(position).getId();
////                    Intent shopId = new Intent(MallActivity.this, GoodsActivity.class);
////                    shopId.putExtra("shopId", mendian_Id);
////                    startActivity(shopId);
////                UIManager.switcher(getContext(), MallActivity.class);//点击跳转到下一页
//                }
//            });
//
//        }
//    }


    //子线程中的Handler 获取店铺内商品列表 /account/good/getGoodsByShopId 是否联网成功
    private Handler mGetGoodsByShopIdHT_net_judge;

    /* 子线程：使用POST方法向服务器发送 、、，获取店铺内商品列表 /account/good/getGoodsByShopId */
    class PostThreadGetGoodsByShopId extends Thread {
        String shopId;
        String currentPage;
        String pageSize;

        PostThreadGetGoodsByShopId(String shopId, String currentPage, String pageSize) {
            this.shopId = shopId;
            this.currentPage = currentPage;
            this.pageSize = pageSize;
        }

        @SuppressLint("SimpleDateFormat")
        @Override
        public void run() {
            records_date = "";//每次请求4条之前，先写空当前的4条
            Log.w("本次 getGoodsByShopId 查询", "正在请求第" + currentPage + "页");
            try {
                JSONObject json = new JSONObject();
                json.put("shopId", shopId);
                json.put("currentPage", currentPage);
                json.put("pageSize", pageSize);
                RequestBody body = RequestBody.create(JSON, String.valueOf(json));
                OkHttpClient client = new OkHttpClient();
                client.newBuilder().connectTimeout(3, TimeUnit.SECONDS);
                client.newBuilder().readTimeout(3, TimeUnit.SECONDS);
                client.newBuilder().writeTimeout(5, TimeUnit.SECONDS);
                Request request = new Request.Builder()
                        .url(ServiceConfig.getRootUrl() + "/account/good/getGoodsByShopId")//我的代理提成记录 端口
                        .post(body)
                        .build();
                client.newCall(request).enqueue(new Callback() {
                    @SuppressLint("HandlerLeak")
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {
                        if (!isFinishing()) {
                            if (dialog != null && dialog.isShowing()) {//有就销毁等待框
                                dialog.dismiss();
                            }
                            //未知联网错误，全部显示
                            Log.e("getGoodsByShopId 联网错误", "失败 e=" + e);
                            Looper.prepare();
                            mGetGoodsByShopIdHT_net_judge = new Handler() {
                                @Override
                                public void handleMessage(Message existence) {
                                    super.handleMessage(existence);
                                    alert2 = new AlertDialog.Builder(StoreManagementActivity.this).create();
//                                    alert2.setIcon(R.mipmap.ic_launcher);//设置对话框的图标
                                    alert2.setTitle("获取店铺内商品列表 联网错误");//设置对话框的标题
                                    alert2.setCancelable(false);
                                    alert2.setMessage(e + "\n\n请联系后台客服处理");//设置要显示的内容
                                    //添加确定按钮
                                    alert2.setButton(DialogInterface.BUTTON_POSITIVE, "确定", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
//                                //结束所有Activity，返回登录页
                                        }
                                    });
                                    alert2.show();// 创建对话框并显示
                                }
                            };
                            mGetGoodsByShopIdHT_net_judge.sendEmptyMessage(1);
                            Looper.loop();
                        }
                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        if (!isFinishing()) {
                            if (dialog != null && dialog.isShowing()) {//有就销毁等待框
                                dialog.dismiss();
                            }
                            String refreshToken = response.header("refreshToken", "");// 不存在就为null，存在就更新token
                            assert refreshToken != null;
                            assert response.body() != null;
                            records_date = response.body().string();//获取到数据，每次都是4，不管够不够4
                            try {
                                jsonGetGoodsByShopId();//把数据传入解析josn数据方法
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    /* 解析返回值 获取店铺内商品列表 */
    @SuppressLint("HandlerLeak")
    private void jsonGetGoodsByShopId() throws JSONException {
        // {
        //	"body": {
        //		"currentPage": 1,
        //		"maxResults": 20,
        //		"totalPages": 5,
        //		"totalRecords": 20,
        //		"records": [{
        //			"id": 1342397081007190018,
        //			"shopId": 68,
        //			"cateId": 0,
        //			"goodsName": "充电电池",
        //			"goodsPrice": 36.00,
        //			"goodsAmount": 52,
        //			"coverVideoId": null,
        //			"coverVideoUrl": null,
        //			"status": "1",
        //			"pictureUrl": "http://lbdsp.com/1609126220417YiiiMh.jpg"
        //		}]
        //	},
        //	"state": 200,
        //	"msg": "请求成功"
        //}
        /* 门店内商品列表 /account/good/getGoodsByShopId 接口返回值 */
        //{
        //  "currentPage": 0,
        //  "maxResults": 10,
        //  "totalPages": 0,
        //  "totalRecords": 0,
        //  "records": [
        //    {
        //      "id": 1,                                                传过去当商品id
        //      "shopId": 1,                                            门店id
        //      "cateId": 1,                                            分类id.1食品  2用品  剩余默认为0
        //      "goodsName": "apple",                                   商品名称
        //      "goodsPrice": 29.8,                                     商品价格
        //      "goodsAmount": 1,                                       商品数量
        //      "coverVideoId": 1,                                      商品介绍视频
        //      "coverVideoUrl": 1,                                     商品介绍视频链接
        //      "status": "1",                                          商品状态0正常1下架2删除
        //      "pictureUrl": "http://img11.360buyimg.com/n3/4408a.jpg" 图片路径
        //    }
        //  ]
        //}
        if (records_date != null) {//判断数据是空
            JSONObject jsonObject = new JSONObject(records_date);
            int _CODE = jsonObject.optInt("state", 0);//200
            String _MESSAGE = jsonObject.optString("msg", null);//请求成功
            Message message = new Message();
            if (_CODE == 200) {//如果返回的值是200，则查询连接成功
                Log.w("getGoodsByShopId 查询成功", "返回值 = " + _MESSAGE);
//                underlingCount_QJ = jsonObject.optInt("underlingCount", 0);//返回的条数，这次刷新的数值，短时间内都要用！！！
//                int chushu = underlingCount_QJ / 50;//计算完整的50条有几页
//                int yushu = ((underlingCount_QJ % 50) > 0) ? 1 : 0;//计算最后不够50条的末页有没有，有就是1页，没有就是0
//                page_amount = chushu + yushu;//完整页+末页=本次联网请求的总页数
//                Log.w("共有underlingCount_QJ", underlingCount_QJ + "条数据");
//                Log.w("共有page_amount", page_amount + "页");
                message.what = 1;
                mGetGoodsByShopIdHT_back.sendMessage(message);
            } else {//获取店铺内商品列表 失败，待处理
                Log.e("getGoodsByShopId 查询失败", "state = " + _CODE + " records_date = " + records_date);
                Looper.prepare();
                mGetGoodsByShopIdHT_net_judge = new Handler() {
                    @Override
                    public void handleMessage(Message existence) {
                        super.handleMessage(existence);
                        alert2 = new AlertDialog.Builder(StoreManagementActivity.this).create();
//                        alert2.setIcon(R.mipmap.ic_launcher);//设置对话框的图标
                        alert2.setTitle("获取店铺内商品列表 失败");//设置对话框的标题
                        alert2.setCancelable(false);
                        alert2.setMessage(_MESSAGE + "\n\n请联系后台客服处理");//设置要显示的内容
                        //添加确定按钮
                        alert2.setButton(DialogInterface.BUTTON_POSITIVE, "确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
//                                //结束所有Activity，返回登录页
                            }
                        });
                        alert2.show();// 创建对话框并显示
                    }
                };
                mGetGoodsByShopIdHT_net_judge.sendEmptyMessage(4);
                Looper.loop();
            }
        }
    }

    /* underling_reveal 运行在主线程中(UI线程中)，它与子线程可以通过Message对象来传递数据 */
    @SuppressLint("HandlerLeak")
    private Handler mGetGoodsByShopIdHT_back = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1://解析
                    parsingGoodsDataListJson();//
                    break;
                default:
                    break;
            }
        }
    };

    /* 分析 商品列表 列表，只解析数据，只在本地显示。不联网，不加载新数据 */
    private void parsingGoodsDataListJson() {//只解析数据，不联网，不加载新数据
        try {
            if (refreshType && oList2 != null) {//如果刷新状态为真，且
                oList2.clear();
                oldListSize2 = 0;
            } else {
                assert oList2 != null;
                oldListSize2 = oList2.size();
            }
            /* 使用Google的Gson开始解析json数据 */
            // {
            //	"body": {
            //		"currentPage": 1,
            //		"maxResults": 20,
            //		"totalPages": 5,
            //		"totalRecords": 20,
            //		"records": [{
            //			"id": 1342397081007190018,
            //			"shopId": 68,
            //			"cateId": 0,
            //			"goodsName": "充电电池",
            //			"goodsPrice": 36.00,
            //			"goodsAmount": 52,
            //			"coverVideoId": null,
            //			"coverVideoUrl": null,
            //			"status": "1",
            //			"pictureUrl": "http://lbdsp.com/1609126220417YiiiMh.jpg"
            //		}]
            //	},
            //	"state": 200,
            //	"msg": "请求成功"
            //}
            /* 门店内商品列表 /account/good/getGoodsByShopId 接口返回值 */
            Gson gson = new Gson();
            MallGoodsBaseModel mallGoodsBaseModel = gson.fromJson(records_date, MallGoodsBaseModel.class);
            Log.w("records_date 的数据", "records_date = " + records_date);
            int code = mallGoodsBaseModel.getState();
            if (code == 200) {
                Log.w("获取店铺内商品列表", "成功！！！");
                List<Records> recordsList = mallGoodsBaseModel.getBody().getRecords();
                page_amount = mallGoodsBaseModel.getBody().getTotalPages();
                if (recordsList.size() == 0) {//没有值
                    Toast.makeText(StoreManagementActivity.this, "没有商品！", Toast.LENGTH_SHORT).show();
                } else {
                    Log.i("至少有一条", "！！！！");
                    for (Records records : recordsList) {//这里的for是Java中forEach, 用来遍历数组的。for(int i : d) 就是遍历int型数组d的 每一次访问数组d的时候读取的数据放入int型的i中。
                        Records data = new Records();
                        data.setId(records.getId());//
                        data.setShopId(records.getShopId());//
                        data.setCateId(records.getCateId());//
                        data.setGoodsName(records.getGoodsName());//
                        data.setGoodsPrice(records.getGoodsPrice());//
                        data.setGoodsAmount(records.getGoodsAmount());//
                        data.setCoverVideoId(records.getCoverVideoId());//
                        data.setCoverVideoUrl(records.getCoverVideoUrl());//
                        data.setStatus(records.getStatus());//拿到状态
                        data.setPictureUrl(records.getPictureUrl());//
                        //键值对加进列表
                        oList2.add(data);
                    }
                }
            } else {
                Log.e("获取店铺内商品列表", "失败！！！");
                return;//退出本方法
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        newListSize2 = oList2.size();
        addListSize2 = newListSize2 - oldListSize2;

//        if (status.equals("1")) {//待审核列表
        if (refreshType) {
            // 设置RecyclerView样式为竖直线性布局
            storeManagementRecyclerView.setLayoutManager(new LinearLayoutManager(StoreManagementActivity.this, LinearLayoutManager.VERTICAL, false));//getContext()可能有问题
            smg_adapter = new StoreManagementGoods2Adapter(StoreManagementActivity.this, oList2);//列表绑定适配器
            storeManagementRecyclerView.setAdapter(smg_adapter);
        } else {
            smg_adapter.notifyItemRangeInserted(oList2.size() - addListSize2, oList2.size());
            smg_adapter.notifyItemRangeChanged(oList2.size() - addListSize2, oList2.size());
        }
        currentPage++;
        storeManagementRecyclerView.setVisibility(View.VISIBLE);//显示 RecyclerView 布局

        /* item条目的点击事件回调 */
        smg_adapter.setItemClickListener(new StoreManagementGoods2Adapter.OnItemClickListener() {
            //position计数从0开始
            @Override
            public void onItemClickListener(View view, int position) {
                Log.e("点击了", "第" + position + "个");
            }

            @Override
            public void onItemClickLongListener(View view, int position) {
                Log.e("点击了", "第" + position + "个");
            }
        });
    }

    /* 结束本页 */
    @OnClick(R.id.store_management_back)
    public void doBack(View view) {
        finish();
    }

    /* 上传商品 */
    @OnClick(R.id.store_management_change)
    public void doSCSP(View view) {
        Intent scsp = new Intent(StoreManagementActivity.this, UploadGoodsActivity.class);
        scsp.putExtra("shopId", shopId);
        startActivity(scsp);
    }

    @OnClick(R.id.xiugaidianpumingchenghelogo)
    public void doXGDPMCHTX(View view) {//修改店铺名称和LOGO，目前后台接口、数据架构没搞好，先做这两个明显的手机端数据
        Intent shopId = new Intent(StoreManagementActivity.this, ModifyStoreNamePictureActivity.class);
        shopId.putExtra("id", id);
        startActivity(shopId);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mRegister_SX_DPXX_Tag) {
            this.unregisterReceiver(this.broadcastReceiver_SX_DPXX);//销毁 隐形登录成功后 广播 这个地方解绑广
            mRegister_SX_DPXX_Tag = false;
        }
    }

}
