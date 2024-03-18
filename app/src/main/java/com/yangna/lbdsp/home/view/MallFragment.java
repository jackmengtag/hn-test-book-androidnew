package com.yangna.lbdsp.home.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.gson.Gson;
import com.gyf.immersionbar.ImmersionBar;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yangna.lbdsp.R;
import com.yangna.lbdsp.common.ServiceConfig;
import com.yangna.lbdsp.common.UrlConfig;
import com.yangna.lbdsp.common.base.BaseApplication;
import com.yangna.lbdsp.common.base.BasePresenterFragment;
import com.yangna.lbdsp.common.manager.BannerGlideImageLoader;
import com.yangna.lbdsp.common.swiperefreshLayout.ByRecyclerView;
import com.yangna.lbdsp.common.swiperefreshLayout.ToastUtil;
import com.yangna.lbdsp.common.utils.LogUtils;
import com.yangna.lbdsp.common.utils.SpUtils;
import com.yangna.lbdsp.mall.adapter.MallRecyclerViewAdapter;
import com.yangna.lbdsp.mall.adapter.StoreManagementGoods2Adapter;
import com.yangna.lbdsp.mall.bean.MallBaseNewModel;
import com.yangna.lbdsp.mall.bean.MallRecordsModel;
import com.yangna.lbdsp.mall.model.MallBaseModel;
import com.yangna.lbdsp.mall.model.MallModel;
import com.yangna.lbdsp.mall.view.StoreManagementActivity;
import com.youth.banner.Banner;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/* 首页门店列表 */
public class MallFragment extends BasePresenterFragment {

    @BindView(R.id.mall_management_smartRefreshLayout)
    RefreshLayout mallmanagement_SmartRefreshLayout;
    @BindView(R.id.mall_management_recyclerview)
    RecyclerView mallmanagement_RecyclerView;

    @BindView(R.id.mall_laichi)
    TextView laichi;//
    @BindView(R.id.mall_laiyong)
    TextView laiyong;//
    @BindView(R.id.mall_laiwan)
    TextView laiwan;//

    private String records_date;//门店列表返回值 全文
    //    private String mapAddress = "";//门店三个类别
    private List<MallRecordsModel> mallList = new ArrayList<>();
    private MallRecyclerViewAdapter mallRecyclerViewAdapter = null;
    private int oldListSize;
    private int newListSize;
    private int addListSize;
    private int pageSize = 4;//pageSize手写的总页数，不写也行
    private int currentPage = 1;//本次刷新访问的第几页，包括首次访问
    private int page_amount = 0;//当前总共有几页，用返回值算出来的
    private boolean refreshType;//刷新参数，是否能刷新
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_mall;
    }

    @Override
    protected void initView() {
        super.initView();
        ImmersionBar.with(this).fitsSystemWindows(true).statusBarColor(R.color.black_21).init();//强制性保留手机电池、时间、信号状态栏和设置底色
        // 开启自动加载功能（非必须）
        mallmanagement_SmartRefreshLayout.setEnableAutoLoadMore(true);
        // 准备好第一次刷新状态
        mallmanagement_SmartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull final RefreshLayout refreshLayout) {
                refreshLayout.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mallmanagement_RecyclerView.setVisibility(View.GONE);//从头刷新的时候隐藏一次
                        currentPage = 1;
                        page_amount = 0;//清空一次总页数
                        oldListSize = 0;
                        newListSize = 0;
                        addListSize = 0;
                        refreshType = true;//手动设置为真，可刷新
                        new Thread(runnableMenDian).start();
                        refreshLayout.finishRefresh();//完成刷新
                        refreshLayout.resetNoMoreData();//恢复没有更多数据的原始状态
                    }
                }, 500);
            }
        });
        // 准备好加载更多的刷新状态
        mallmanagement_SmartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull final RefreshLayout refreshLayout) {
                refreshLayout.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshType = false;//手动设置为假，并放在判断大小外面，才能保证列表连续，不然只能加载当前一页数据
                        if (mallList.size() >= page_amount) {//如果列表个数大于等于返回的总个数
                            ToastUtil.showToast("暂无更多的数据");
                            // 将不会再次触发加载更多事件
                            refreshLayout.finishLoadMoreWithNoMoreData();//完成加载并标记没有更多数据
                            return;//退出
                        }
                        oldListSize = 0;
                        newListSize = 0;
                        addListSize = 0;
                        new Thread(runnableMenDian).start();
                        refreshLayout.setEnableLoadMore(true);//设置是否启用上拉加载更多（默认启用）
                        refreshLayout.finishLoadMore();//完成加载
                    }
                }, 500);
            }
        });

        mallRecyclerViewAdapter = new MallRecyclerViewAdapter(getActivity(), mallList);
        mallList.clear();
        currentPage = 1;
        page_amount = 0;//清空一次总页数
        oldListSize = 0;
        newListSize = 0;
        addListSize = 0;
        refreshType = true;//手动设置为真，可刷新
        new Thread(runnableMenDian).start();
    }

    /* 来吃 */
    @OnClick(R.id.mall_laichi)
    public void doLC(View view) {//点击切换刷新来吃店铺列表
        laichi.setTextColor(Color.parseColor("#F26161"));
        laichi.setBackgroundResource(R.drawable.bg_btn_pink);
        laiyong.setTextColor(Color.parseColor("#444444"));
        laiyong.setBackgroundResource(R.drawable.bg_btn_white);
        laiwan.setTextColor(Color.parseColor("#444444"));
        laiwan.setBackgroundResource(R.drawable.bg_btn_white);

//        rvMovieList.setVisibility(View.GONE);//隐藏一次？也能实现连续刷新
//        page = 1;//清空一次当前页数，并设为1，因为至少为1页
        page_amount = 0;//清空一次总页数
        oldListSize = 0;//清空一次列表
        newListSize = 0;//清空一次列表
        addListSize = 0;//清空一次列表
//        way = "0";//查询总 设置为0
//        mapAddress = "万达";
        mallmanagement_RecyclerView.setVisibility(View.GONE);
        mallList.clear();
        mallRecyclerViewAdapter.notifyDataSetChanged();
        new Thread(runnableMenDian).start();
    }

    /* 来用 */
    @OnClick(R.id.mall_laiyong)//
    public void doLY(View view) {//点击切换刷新来用店铺列表
        laichi.setTextColor(Color.parseColor("#444444"));
        laichi.setBackgroundResource(R.drawable.bg_btn_white);
        laiyong.setTextColor(Color.parseColor("#F26161"));
        laiyong.setBackgroundResource(R.drawable.bg_btn_pink);
        laiwan.setTextColor(Color.parseColor("#444444"));
        laiwan.setBackgroundResource(R.drawable.bg_btn_white);

//        rvMovieList.setVisibility(View.GONE);//隐藏一次？也能实现连续刷新
//        page = 1;//清空一次当前页数，并设为1，因为至少为1页
        page_amount = 0;//清空一次总页数
        oldListSize = 0;//清空一次列表
        newListSize = 0;//清空一次列表
        addListSize = 0;//清空一次列表
//        way = "1";//查询支付宝设置为1
//        mapAddress = "恒大";
        mallmanagement_RecyclerView.setVisibility(View.GONE);
        mallList.clear();
        mallRecyclerViewAdapter.notifyDataSetChanged();
        new Thread(runnableMenDian).start();
    }

    /* 来玩 */
    @OnClick(R.id.mall_laiwan)//
    public void doLW(View view) {//点击切换刷新来玩店铺列表
        laichi.setTextColor(Color.parseColor("#444444"));
        laichi.setBackgroundResource(R.drawable.bg_btn_white);
        laiyong.setTextColor(Color.parseColor("#444444"));
        laiyong.setBackgroundResource(R.drawable.bg_btn_white);
        laiwan.setTextColor(Color.parseColor("#F26161"));
        laiwan.setBackgroundResource(R.drawable.bg_btn_pink);

//        rvMovieList.setVisibility(View.GONE);//隐藏一次？？也能实现连续刷新
//        page = 1;//清空一次页数，并设为1，因为至少为1页
        page_amount = 0;//清空一次总页数
        oldListSize = 0;//清空一次列表
        newListSize = 0;//清空一次列表
        addListSize = 0;//清空一次列表
//        way = "2";//查询微信设置为2
//        mapAddress = "声福";
        mallmanagement_RecyclerView.setVisibility(View.GONE);
        mallList.clear();
        mallRecyclerViewAdapter.notifyDataSetChanged();
        new Thread(runnableMenDian).start();
    }


    public void show() {
    }

    public void hide() {
    }
    /* 子线程中的Handler 获取门店 联网判断 account/shop/getShopByAddress 是否联网成功 */
    private Handler mMenDianHT_net_judge;

    /* 子线程：使用POST方法向服务器发送 、、 个参数完成 获取门店 */
    private Runnable runnableMenDian = new Runnable() {
        @Override
        public void run() {
            records_date = "";//请求之前先清空一次
            String chaochangTOKEN = (String) Objects.requireNonNull(SpUtils.get(getActivity(), UrlConfig.USERID, ""));
            try {
                JSONObject json = new JSONObject();
                json.put("currentPage", currentPage);
                json.put("pageSize", pageSize);
                RequestBody body = RequestBody.create(JSON, String.valueOf(json));
//                Log.i("发送JSON获取门店", String.valueOf(json));
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
//                        .url(ServiceConfig.getRootUrl() + "/account/shop/getShopByAddress")//获取门店 端口
                        .url(ServiceConfig.getRootUrl() + "/tWebshopShop")//获取门店 端口
                        .addHeader("Authorization", chaochangTOKEN)
                        .post(body)
                        .build();
                client.newCall(request).enqueue(new Callback() {
                    @SuppressLint("HandlerLeak")
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {//未知联网错误，全部显示
                        if (!getActivity().isFinishing()) {
//                            Log.e("tWebshopShop 未知联网错误", "失败 e=" + e);
                            Looper.prepare();
                            mMenDianHT_net_judge = new Handler() {
                                @Override
                                public void handleMessage(@NonNull Message msg) {
                                    super.handleMessage(msg);
                                    //
                                }
                            };
                            mMenDianHT_net_judge.sendEmptyMessage(1);
                            Looper.loop();
                        }
                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        if (!getActivity().isFinishing()) {
                            assert response.body() != null;
                            records_date = response.body().string();//获取到数据
                            try {
                                jsongetShopByAddress(records_date);//把数据传入解析josn数据方法
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

    /* 解析 获取门店 返回值，存集合 */
    @SuppressLint("HandlerLeak")
    private void jsongetShopByAddress(String date) throws JSONException {//响应200代码后才会来到这里，但是还要再次判断"_CODE"=200的
//        Log.w("门店列表页返回值", "返回date值：" + date);
        Message msg = new Message();//处理完成后给handler发送消息//处理完成后给handler发送消息
        if (date != null && !date.equals("")) {//判断数据是空
            /* 2021年1月14日22:33:43 总店铺列表接口 /tWebshopShop */
            //{
            //  "body": {
            //    "currentPage": 2,
            //    "maxResults": 4,
            //    "totalPages": 2,
            //    "totalRecords": 8,
            //    "records": [
            //
            //      {
            //        "id": "2",
            //        "shopName": "东环水果店",
            //        "startTime": null,
            //        "endTime": null,
            //        "createTime": "2020-10-06 16:39:47",
            //        "chargingMode": null,
            //        "shopLevelId": "1",
            //        "status": 0,
            //        "shopAccount": null,
            //        "companyName": null,
            //        "companyAddress": "A区2号",
            //        "businessCertification": "450203600116452",
            //        "operationRange": "水果",
            //        "organizationCode": null,
            //        "score": null,
            //        "currentPackage": null,
            //        "numberOfProductsThatCanBeReleased": null,
            //        "provinceId": "2",
            //        "cityId": "2",
            //        "areaId": "110101",
            //        "streetId": "110101001",
            //        "shopSaledCount": null,
            //        "shopSubscribeCount": null,
            //        "newArrivalDate": null,
            //        "userId": null,
            //        "longitude": 109.41105,
            //        "latitude": 24.303792,
            //        "isBranchCompany": null,
            //        "shopLogo": null,
            //        "deleted": 0
            //      }
            //    ]
            //  },
            //  "state": 200,
            //  "msg": "请求成功"
            //}
            JSONObject jsonObject = new JSONObject(date);
            String body = jsonObject.optString("body", "");//获取返回数据中 body 的值
            if (body == null || body.equals("")) {
//                Log.e("获取门店页失败》》》", "返回records值：" + date);
                msg.what = 2;//失败
                mMenDianHT.sendMessage(msg);
            } else {//
                JSONObject jsonObject2 = new JSONObject(body);
                String records = jsonObject2.optString("records", "");//获取返回数据中 records 的值
                if (records == null || records.equals("")) {
//                    Log.e("获取门店页失败》》》", "返回records值：" + date);
                    msg.what = 2;//失败
                    mMenDianHT.sendMessage(msg);
                } else {
//                    Log.w("获取门店页成功》》》", "返回records值：" + date);
                    int totalRecords = jsonObject2.optInt("totalRecords", 0);//获取返回数据中 totalRecords 的值
//                    page_amount = (totalRecords % pageSize == 0) ? (totalRecords / pageSize) : (int) (Math.floor(totalRecords / pageSize) + 1);
//                    Log.w("计算得的page_amount值", "page_amount = " + page_amount);
                    page_amount = totalRecords;//临时赋值，总个数赋值给总页数
                    msg.what = 1;
                    mMenDianHT.sendMessage(msg);
                }
            }
        } else {
//            Log.e("获取门店页失败", "没有任何返回值");
            msg.what = 2;//失败
            mMenDianHT.sendMessage(msg);
        }
    }

    /* 子线程 获取门店 判断的Handler 返回值判断 */
    @SuppressLint("HandlerLeak")
    private Handler mMenDianHT = new Handler() {
        @Override
        public void handleMessage(Message msg) {//在子线程中通知主线程，让主线程做更新操作
            switch (msg.what) {
                case 1://获取成功
                    parsingMenDianListJson();
                    break;
                case 2://获取失败
                    Toast toast = Toast.makeText(getActivity(), "获取门店列表失败", Toast.LENGTH_LONG);
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

    /* 解析显示门店列表数据 */
    private void parsingMenDianListJson() {
        try {
            if (refreshType && mallList != null) {//如果刷新状态为真，且
                mallList.clear();
                oldListSize = 0;
            } else {
                assert mallList != null;
                oldListSize = mallList.size();
            }
            Gson gson = new Gson();
            MallBaseNewModel mallBaseNewModel = gson.fromJson(records_date, MallBaseNewModel.class);
            if (records_date.equals("") || records_date == null) {
//                Log.e("获取门店失败", "没有任何返回值");
                return;
            } else {
                List<MallRecordsModel> mallModelList = mallBaseNewModel.getBody().getRecords();
                for (MallRecordsModel records : mallModelList) {
                    MallRecordsModel data = new MallRecordsModel();
                    data.setId(records.getId());
                    data.setShopName(records.getShopName());
                    data.setStartTime(records.getStartTime());
                    data.setEndTime(records.getEndTime());
                    data.setCreateTime(records.getCreateTime());
                    data.setChargingMode(records.getChargingMode());
                    data.setShopLevelId(records.getShopLevelId());
                    data.setStatus(records.getStatus());
                    data.setShopAccount(records.getShopAccount());
                    data.setCompanyName(records.getCompanyName());
                    data.setCompanyAddress(records.getCompanyAddress());
                    data.setBusinessCertification(records.getBusinessCertification());
                    data.setOperationRange(records.getOperationRange());
                    data.setOrganizationCode(records.getOrganizationCode());
                    data.setScore(records.getScore());
                    data.setCurrentPackage(records.getCurrentPackage());
                    data.setNumberOfProductsThatCanBeReleased(records.getNumberOfProductsThatCanBeReleased());
                    data.setProvinceId(records.getProvinceId());
                    data.setCityId(records.getCityId());
                    data.setAreaId(records.getAreaId());
                    data.setStreetId(records.getStreetId());
                    data.setShopSaledCount(records.getShopSaledCount());
                    data.setShopSubscribeCount(records.getShopSubscribeCount());
                    data.setNewArrivalDate(records.getNewArrivalDate());
                    data.setUserId(records.getUserId());
                    data.setLongitude(records.getLongitude());
                    data.setLatitude(records.getLatitude());
                    data.setIsBranchCompany(records.getIsBranchCompany());
                    data.setShopLogo(records.getShopLogo());
                    data.setDeleted(records.getDeleted());
                    //键值对加进列表
                    mallList.add(data);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        newListSize = mallList.size();
        addListSize = newListSize - oldListSize;

        if (refreshType) {
            // 设置RecyclerView样式为竖直线性布局
            mallmanagement_RecyclerView.setLayoutManager(new LinearLayoutManager(BaseApplication.getContext(), LinearLayoutManager.VERTICAL, false));//getContext()可能有问题
            mallRecyclerViewAdapter = new MallRecyclerViewAdapter(BaseApplication.getContext(), mallList);//列表绑定适配器
            mallmanagement_RecyclerView.setAdapter(mallRecyclerViewAdapter);
        } else {
            mallRecyclerViewAdapter.notifyItemRangeInserted(mallList.size() - addListSize, mallList.size());
            mallRecyclerViewAdapter.notifyItemRangeChanged(mallList.size() - addListSize, mallList.size());
        }
        currentPage++;//自增一页
        mallmanagement_RecyclerView.setVisibility(View.VISIBLE);//显示 RecyclerView 布局

        /* item条目的点击事件回调 */
        mallRecyclerViewAdapter.setOnItemClickListener(new MallRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(View view, int position) {
//                Log.e("点击了", "第" + position + "个");
                String mendian_Id = mallList.get(position).getId();
                String mendian_name = mallList.get(position).getShopName();
                String mendian_logo = mallList.get(position).getShopLogo();
                Intent shopId = new Intent(getActivity(), MallActivity.class);
                shopId.putExtra("shopId", mendian_Id);
                shopId.putExtra("shopName", mendian_name);
                shopId.putExtra("shopLogo", mendian_logo);
                startActivity(shopId);
            }

            @Override
            public void onItemLongClick(View view, int position) {
//                Log.e("点击了", "第" + position + "个");
            }
        });
    }

}
