package com.yangna.lbdsp.mall.view;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

//import com.alibaba.fastjson.JSONArray;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.gyf.immersionbar.ImmersionBar;
import com.wwb.laobiao.address.FukuanActivity;
//import com.wwb.laobiao.cangye.model.CanyedacongGood;
import com.wwb.laobiao.cangye.model.CanyedacongGood;
import com.wwb.laobiao.cangye.model.Controller;
import com.wwb.laobiao.instance.UserOrder;
import com.yangna.lbdsp.R;
import com.yangna.lbdsp.common.ServiceConfig;
import com.yangna.lbdsp.common.UrlConfig;
import com.yangna.lbdsp.common.base.AutoFlowLayout;
import com.yangna.lbdsp.common.base.BannerHolderView;
import com.yangna.lbdsp.common.base.BaseApplication;
import com.yangna.lbdsp.common.base.FlowAdapter;
import com.yangna.lbdsp.common.manager.BannerGlideImageLoader;
import com.yangna.lbdsp.common.swiperefreshLayout.ToastUtil;
import com.yangna.lbdsp.common.utils.SpUtils;
import com.yangna.lbdsp.home.adapter.CommentDialogSingleAdapter;
import com.yangna.lbdsp.mall.bean.SpecficationBean;
import com.yangna.lbdsp.mall.model.GoodsParticularsBaseModel;
import com.yangna.lbdsp.mall.model.GoodsParticularsModel;
import com.yangna.lbdsp.widget.dialog.InputTextMsgDialog;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static java.util.Arrays.asList;

/* 商品详情页面 */
public class GoodsActivity extends AppCompatActivity {

    //    @BindView(R.id.goods_back)
//    ImageView orderBack;
    //    @BindView(R.id.goods_banner)
//    Banner banner;
    @BindView(R.id.zhutu_convenientBanner)
    ConvenientBanner zhutu_Banner;//主页轮播图，不轮播而已
    @BindView(R.id.shangpin_xiangqing_list_ll)
    LinearLayout shangpin_xiangqing_ll;//商品详情拼接详细图
    @BindView(R.id.goods_price)
    TextView price;//价格
    @BindView(R.id.goods_name)
    TextView name;//名称
    @BindView(R.id.goods_particulars)
    TextView particulars;//详情
    @BindView(R.id.goods_repertory)
    TextView goodsRepertory;//库存
    @BindView(R.id.goods_sales_volume)
    TextView goodsSalesvolume;//销量
    @BindView(R.id.goods_use_information)
    TextView goodsUseInformation;//使用须知
//    @BindView(R.id.shangpin_xiangqing_list_ll)

    //    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private ArrayList<String> banner_list = new ArrayList<String>();
    private ArrayList<String> pictureUrl = new ArrayList<String>();//上一个页面传过来的pictureUrlList
    //    private List<String> detailImageUrls_list = new ArrayList<String>();//获取商品详情接口返回的商品详情图片List
    private JSONArray detailImageUrls;
    private String records_date;//商品详情返回值 全文
    private String sygymcgl_id = "";//上一个页面传过来的商品id
    private String goodsName = "";//上一个页面传过来的Name
    private String dianming = "";//上一个页面传过来的店名
    private String goodsPrice = "";//上一个页面传过来的Price
    private String productName = "";//返回的商品名称
    private String isOwnOperate = "";//返回的商品是否自营
    private String huohao = "";//商品货号
    private String repertory = "";//库存
    private String sales_volume = "";//销量
    private String use_information = "";//使用须知
    private int queidngtianjiadeshuliang = 1;//购物车确定添加的数量
    private int quedingtianjia_deguige = -1;//购物车确定添加的规格，数组内从0开始计数的规格
    //    private BottomSheetDialog bottomSheetDialog1;
    private Dialog dialog1;
    //    private BottomSheetDialog bottomSheetDialog2;
    private Dialog dialog2;
    //    private RecyclerView rv_dialog_lists;
    //    private AutoFlowLayout mFlowLayout;
    private CommentDialogSingleAdapter bottomSheetAdapter;
    private float slideOffsets = 0;
    private LayoutInflater mLayoutInflater;
    private JSONArray specficationJSONArray;
    private List<SpecficationBean> specfication_GuiGe_List = new ArrayList<SpecficationBean>();
    private Display display;
    private DisplayMetrics metrics;

    @SuppressLint("SetTextI18n")
    @Override/*创建时运行*/
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods);
        ButterKnife.bind(this);

//        ImmersionBar.with(this).fitsSystemWindows(true).statusBarColor(R.color.black_21).init();//强制性保留手机电池、时间、信号状态栏和设置底色
        Intent intent = getIntent();
        sygymcgl_id = intent.getStringExtra("id");//
        goodsName = intent.getStringExtra("goodsName");//
        dianming = intent.getStringExtra("dianming");//传过来的店名
        goodsPrice = intent.getStringExtra("goodsPrice");//
        pictureUrl = intent.getStringArrayListExtra("pictureUrlList");//

        /* 目前为了显示一张图片的轮播图。以后显示多张，并启动轮播 */
        banner_list = pictureUrl;
//        Log.d("商品详情页", "主图轮播图banner_list.size() = " + banner_list.size() + " 张图片");
        display = getWindowManager().getDefaultDisplay();
        metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        ViewGroup.LayoutParams params = zhutu_Banner.getLayoutParams();
        params.width = metrics.widthPixels;
        params.height = metrics.widthPixels;//商品的轮播图，以正方形显示
        /* 初始化商品图片轮播 */
        zhutu_Banner.setPages(new CBViewHolderCreator() {
            @Override
            public Object createHolder() {
                return new BannerHolderView();
            }
        }, banner_list)
                //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
                .setPageIndicator(new int[]{R.mipmap.dian_hui, R.mipmap.dian_bai})
                //设置指示器的方向
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.ALIGN_PARENT_RIGHT);

        name.setText(goodsName);//显示传过来的商品名称
        price.setText("￥" + goodsPrice);//显示传过来的商品价格
    }

    @Override/* 启动时运行 */
    protected void onStart() {
        super.onStart();
        new Thread(runnableXiangQing).start();
    }

    /* 子线程中的Handler 获取商品详情 联网判断 account/good/getGoodsDetailByGoodsId 是否联网成功 */
    private Handler mXiangQingHT_net_judge;

    /* 子线程：使用POST方法向服务器发送 goodsId 参数完成  */
    private Runnable runnableXiangQing = new Runnable() {
        @Override
        public void run() {
            records_date = "";//请求之前先清空一次
            String chaochangTOKEN = (String) Objects.requireNonNull(SpUtils.get(GoodsActivity.this, UrlConfig.USERID, ""));
            try {
                MediaType JSON = MediaType.parse("application/json; charset=utf-8");
                JSONObject json = new JSONObject();
                json.put("goodsId", sygymcgl_id);
                RequestBody body = RequestBody.create(JSON, String.valueOf(json));
//                Log.i("发送JSON", String.valueOf(json));
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
//                        .url(ServiceConfig.getRootUrl() + "/account/good/getGoodsDetailByGoodsId")//获取商品详情 端口
                        .url(ServiceConfig.getRootUrl() + "/api/tWebshopProduct/getGoodsDetailByGoodsId")//获取商品详情 端口
//                    .addHeader("_USER", uuid)//参数全部放Header里面
                        .addHeader("Authorization", chaochangTOKEN)
                        .post(body)
                        .build();
                client.newCall(request).enqueue(new Callback() {
                    @SuppressLint("HandlerLeak")
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {//未知联网错误，全部显示
                        if (!GoodsActivity.this.isFinishing()) {
//                            Log.e("获取商品详情 未知联网错误", "失败 e=" + e);
                            Looper.prepare();
                            mXiangQingHT_net_judge = new Handler() {
                                @Override
                                public void handleMessage(@NonNull Message msg) {
                                    super.handleMessage(msg);
                                    //
                                }
                            };
                            mXiangQingHT_net_judge.sendEmptyMessage(1);
                            Looper.loop();
                        }
                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        if (!GoodsActivity.this.isFinishing()) {
                            assert response.body() != null;
                            records_date = response.body().string();//获取到数据
                            try {
                                jsongetGoodsByShopId(records_date);//把数据传入解析josn数据方法
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

    /* 解析 获取商品详情 返回值，存集合 */
    @SuppressLint("HandlerLeak")
    private void jsongetGoodsByShopId(String date) throws JSONException {//响应200代码后才会来到这里，但是还要再次判断"_CODE"=200的
        Message msg = new Message();//处理完成后给handler发送消息
        if (date != null && !date.equals("")) {//判断数据是空
            /*  门店内商品列表 /account/good/getGoodsDetailByGoodsId 接口返回值 */
            /* 2021年1月19日16:35:32 新版商品详情接口 /api/tWebshopProduct/getGoodsDetailByGoodsId */
            //{
            //    "body":{
            //        "id":"757f85ab42183f46a1b92a8ce9420b0a3a84",
            //        "productName":"wbtp",                             商品名称
            //        "productUnite":"ye",                              商品单位
            //        "shelfNumber":"645364536453643",                  商品货号
            //        "brandId":"1350308066834309122",                  品牌id
            //        "productTypeId":"1350307196952436738",            商品类型id
            //        "stockCount":580,                                 库存
            //        "limitShoppingCount":2,                           限购数
            //        "salesCount":70,                                  销量
            //        "deployTime":"2021-01-19 00:00:00",               发布时间
            //        "createTime":null,                                创建时间
            //        "productPrice":34.0,                              商品价格
            //        "productKeyword":"wb",                            搜索关键词
            //        "productDesc":"kkb",                              商品描述
            //        "cityPrice":5.0,                                  商城价
            //        "marketPrice":2.0,                                市场价
            //        "alarmCount":1,                                   警戒数
            //        "categoryId":null,                                类别
            //        "isOpenSpecification":1,                          是否开启规格(0=未开启，1=开启)
            //        "carriageId":null,                                运费模板id
            //        "isOnShelf":1,                                    是否上架
            //        "isVirtualProduct":1,                             是否虚拟商品（0=不是，1=是虚拟商品）
            //        "isLongTimeValid":0,                              虚拟商品用，是否长期有效（0=长期有效，1=自定义日期）
            //        "startTime":"2021-01-19 00:00:00",                开始时间
            //        "endTime":"2021-01-19 00:00:00",                  结束时间
            //        "isSupportRetrunMoney":1,                         是否支持退款(0=支持退款，1=不支持退款)
            //        "isVerificationCodeRightNow":0,                   核销码生效时间(0=立即生效，1=付款完几小时生效，2=次日生效)
            //        "afterPaymentHoursCount":1,                       核销码付款几小时
            //        "useDes":"my",                                    使用须知
            //        "shopId":"1351004305464053762",                   所属店铺id
            //        "productScore":null,                              商品评分
            //        "deployType":2,                                   发布类型(0=普通，1=精选，2=活动，3=动态，4=上新,5=猜你喜欢)
            //        "appraiseCount":null,                             评价数
            //        "goodAppraiseRate":null,                          好评率
            //        "viewCount":null,                                 浏览量
            //        "productTag":null,                                产品标签
            //        "appraiseList":[                                  评价列表
            //        ],
            //        "mainImageUrls":[                                 商品主图
            //            "http://lbdsp.com/1611029086402VagaDG.jpg",
            //            "http://lbdsp.com/16110290835553QB9tq.jpg",
            //            "http://lbdsp.com/1611029081240o6qncg.jpg",
            //            "http://lbdsp.com/1611029078773aH9JKx.jpg",
            //            "http://lbdsp.com/1611029076326iSHUi5.jpg",
            //            "http://lbdsp.com/1611029073821xfKSPs.jpg"
            //        ],
            //        "isOwnOperate":null,                              是否自营
            //        "detailImageUrls":[                               商品详情图片
            //            "http://lbdsp.com/1611029069708urPQVu.jpg",
            //            "http://lbdsp.com/16110290548386uU4bm.jpg",
            //            "http://lbdsp.com/1611029052289OahCvK.jpg",
            //            "http://lbdsp.com/1611029047447M00iR2.jpg",
            //            "http://lbdsp.com/1611029041714AlS1Mp.jpg",
            //            "http://lbdsp.com/1611029038599kRZ4W2.jpg",
            //            "http://lbdsp.com/1611029035965q6Fs9o.jpg",
            //            "http://lbdsp.com/1611029033913G0Olfb.jpg",
            //            "http://lbdsp.com/1611029031828lMiXHr.jpg"
            //        ],
            //        "productTags":[                                   商品标签
            //            null,
            //            null,
            //            null,
            //            null,
            //            null,
            //            null,
            //            null,
            //            null,
            //            null,
            //            null
            //        ]
            //    },
            //    "state":200,
            //    "msg":"请求成功"
            //}
            /* 2021年1月23日22:37:15 新版商品详情接口 /api/tWebshopProduct/getGoodsDetailByGoodsId */
            //{
            //	"body": {
            //		"id": "13e1e94055cfb64a2128ed56083feb0d92ac",
            //		"productName": "万达冰淇淋",
            //		"productUnite": "个",
            //		"shelfNumber": "9527",
            //		"brandId": "1350308066834309122",
            //		"productTypeId": "1350306400957423618",
            //		"stockCount": 9527,
            //		"limitShoppingCount": 9527,
            //		"salesCount": 9527,
            //		"deployTime": "2021-01-01 00:00:00",
            //		"createTime": null,
            //		"productPrice": 9527.0,
            //		"productKeyword": "9527",
            //		"productDesc": "9527",
            //		"cityPrice": 9527.0,
            //		"marketPrice": 9527.0,
            //		"alarmCount": 9527,
            //		"categoryId": null,
            //		"isOpenSpecification": 1,
            //		"carriageId": null,
            //		"isOnShelf": 1,
            //		"isVirtualProduct": 1,
            //		"isLongTimeValid": 0,
            //		"startTime": "2021-01-01 00:00:00",
            //		"endTime": "2021-01-02 00:00:00",
            //		"isSupportRetrunMoney": 0,
            //		"isVerificationCodeRightNow": 0,
            //		"afterPaymentHoursCount": 11,
            //		"useDes": "11",
            //		"shopId": "1350303633785524226",
            //		"productScore": null,
            //		"deployType": 0,
            //		"appraiseCount": null,
            //		"goodAppraiseRate": null,
            //		"viewCount": null,
            //		"productTag": null,
            //		"appraiseList": [],
            //		"mainImageUrls": ["http://lbdsp.com/1610777915205n0Ywey.png", "http://lbdsp.com/1610777918746oRuDDO.png", "http://lbdsp.com/1610777922410sucBt6.png", "http://lbdsp.com/1610777930338de26n9.JPG", "http://lbdsp.com/1610777932680yn22XE.JPG", "http://lbdsp.com/16107779350384j9GTh.JPG"],
            //		"isOwnOperate": null,
            //		"detailImageUrls": ["http://lbdsp.com/1610777938246Rcucrv.jpg", "http://lbdsp.com/1610777942624qyvpTs.png", "http://lbdsp.com/1610777945433L2h2C6.JPG", "http://lbdsp.com/1610777949727NaeFnQ.png", "http://lbdsp.com/1610777953279XsucfE.JPG", "http://lbdsp.com/1610777957946cfS3vO.jpg", "http://lbdsp.com/1610777960515wErajw.JPG", "http://lbdsp.com/1610777962856V5Eqw3.JPG", "http://lbdsp.com/1610777965640d3CdFZ.jpg"],
            //		"productTags": [null, null, null, null, null, null, null, null, null, null],
            //		"specficationList": [{                                      规格列表
            //			"id": "1",
            //			"ruleName": "黄色",
            //			"createTime": null,
            //			"productId": "13e1e94055cfb64a2128ed56083feb0d92ac",
            //			"typeId": "1",
            //			"productPrice": 10.0
            //		}, {
            //			"id": "1352901497267249154",
            //			"ruleName": "白色",
            //			"createTime": null,
            //			"productId": "13e1e94055cfb64a2128ed56083feb0d92ac",
            //			"typeId": "1",
            //			"productPrice": 9.9
            //		}, {
            //			"id": "2",
            //			"ruleName": "蓝色",
            //			"createTime": null,
            //			"productId": "13e1e94055cfb64a2128ed56083feb0d92ac",
            //			"typeId": "1",
            //			"productPrice": 12.0
            //		}, {
            //			"id": "3",
            //			"ruleName": "黄色",
            //			"createTime": null,
            //			"productId": "13e1e94055cfb64a2128ed56083feb0d92ac",
            //			"typeId": "1",
            //			"productPrice": 15.0
            //		}]
            //	},
            //	"state": 200,
            //	"msg": "请求成功"
            //}
            JSONObject jsonObject1 = new JSONObject(date);
            String body = jsonObject1.optString("body", "");//获取返回数据中 body 的值
            if (body == null || body.equals("")) {
//                Log.e("获取商品详情 失败》》》", "返回date值：" + date);
                msg.what = 2;//失败
                mXiangQingHT.sendMessage(msg);
            } else {//
                JSONObject jsonObject2 = new JSONObject(body);
                productName = jsonObject2.optString("productName", "");//获取返回数据中 productName 商品名称 的值
                isOwnOperate = jsonObject2.optString("isOwnOperate", "");//获取返回数据中 isOwnOperate 是否自营 的值
                repertory = jsonObject2.optString("stockCount", "");//获取返回数据中 stockCount 库存 的值
                sales_volume = jsonObject2.optString("salesCount", "");//获取返回数据中 salesCount 销量 的值
                use_information = jsonObject2.optString("useDes", "");//获取返回数据中 useDes 使用须知 的值
                huohao = jsonObject2.optString("shelfNumber", "");//获取返回数据中 shelfNumber 商品货号 的值
                String productDesc = jsonObject2.optString("productDesc", "");//获取返回数据中 productDesc 商品描述 的值

                if (productDesc == null || productDesc.equals("")) {
//                    Log.e("获取商品详情 失败》》》", "返回date值：" + date);
                    msg.what = 2;//失败
                    mXiangQingHT.sendMessage(msg);
                } else {
                    Log.w("获取商品详情 成功》》》", "返回records值：" + date);
                    records_date = productDesc;//临时赋值 商品描述
                    detailImageUrls = jsonObject2.optJSONArray("detailImageUrls");//获取返回数据中 detailImageUrls 商品详情图片数组 的值
                    specficationJSONArray = jsonObject2.optJSONArray("specficationList");//获取返回数据中 specficationList 规格列表数组 的值
                    for (int i = 0; i < specficationJSONArray.length(); i++) {
                        JSONObject object = specficationJSONArray.getJSONObject(i);
//                        try {
                        SpecficationBean data = new SpecficationBean();
                        data.setId(object.optString("id", ""));
                        data.setRuleName(object.optString("ruleName", ""));
                        data.setCreateTime(object.optString("createTime", ""));
                        data.setProductId(object.optString("productId", ""));
                        data.setTypeId(object.optString("typeId", ""));
                        data.setProductPrice(object.optString("productPrice", ""));
                        specfication_GuiGe_List.add(data);
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
                    }
//                    Log.w("获取商品详情图成功", "返回 detailImageUrls总数值：" + detailImageUrls.length());
                    msg.what = 1;
                    mXiangQingHT.sendMessage(msg);
                }
            }
        } else {
//            Log.e("获取商品详情 失败", "没有任何返回值");
            msg.what = 2;//失败
            mXiangQingHT.sendMessage(msg);
        }
    }

    /* 子线程 获取商品详情 判断的Handler 返回值判断 */
    @SuppressLint("HandlerLeak")
    private Handler mXiangQingHT = new Handler() {
        @SuppressLint("SetTextI18n")
        @Override
        public void handleMessage(Message msg) {//在子线程中通知主线程，让主线程做更新操作
            switch (msg.what) {
                case 1://获取成功
                    particulars.setText(records_date);//赋值 商品描述
                    goodsRepertory.setText(repertory);//赋值 库存
                    goodsSalesvolume.setText(sales_volume);//赋值 销量
                    goodsUseInformation.setText(use_information);//赋值 使用须知
                    if (isOwnOperate.equals("") || isOwnOperate == null) {
                        name.setText("自营" + productName);
//                        String str = "自营" + productName + "他营";
//                        int bstart = str.indexOf("自营");
//                        int bend = bstart + "自营".length();
//                        int fstart = str.indexOf("他营");
//                        int fend = fstart + "他营".length();
//                        SpannableStringBuilder style = new SpannableStringBuilder(str);
//                        style.setSpan(new BackgroundColorSpan(Color.RED), bstart, bend, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//                        style.setSpan(new ForegroundColorSpan(Color.RED), fstart, fend, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
//                        name.setText(style);
                    } else {
                        name.setText(productName);
                    }
//                    Display display = getWindowManager().getDefaultDisplay();
//                    DisplayMetrics metrics = new DisplayMetrics();
//                    display.getMetrics(metrics);
//                    Log.w("拿到的宽度", "metrics.widthPixels = " + metrics.widthPixels);
//                    LinearLayout group = (LinearLayout) findViewById(R.id.shangpin_xiangqing_list_ll);
                    ImageView[] imageViews = new ImageView[detailImageUrls.length()];
                    for (int i = 0; i < detailImageUrls.length(); i++) {
                        ImageView imageView = new ImageView(GoodsActivity.this);
                        imageViews[i] = imageView;
//                        ViewGroup.LayoutParams para = imageView.getLayoutParams();
                        try {//https://www.cnblogs.com/widgetbox/p/13405241.html 方法有用 Glide加载时等比例缩放图片至屏幕宽度（通过加载下来的图片动态设置）
                            Glide.with(GoodsActivity.this).asBitmap().load(detailImageUrls.get(i)).skipMemoryCache(true)/*禁止内存缓存*/.into(new SimpleTarget<Bitmap>(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL) {
                                @Override
                                public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                    int imageWidth = resource.getWidth();
                                    int imageHeight = resource.getHeight();
                                    int height = metrics.widthPixels * imageHeight / imageWidth;
                                    ViewGroup.LayoutParams para = imageView.getLayoutParams();
                                    para.height = height;
                                    para.width = metrics.widthPixels;//屏幕的宽度，没有工具类自己从网上搜
                                    imageView.setImageBitmap(resource);
                                }
                            });
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        shangpin_xiangqing_ll.addView(imageView);
                    }
                    break;
                case 2://获取失败
                    Toast toast = Toast.makeText(GoodsActivity.this, "获取商品详情 失败", Toast.LENGTH_LONG);
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

    /* 添加进购物车 */
    @SuppressLint("SetTextI18n")
    @OnClick(R.id.tv_add_cart)
    public void doAddCart(View view0) {
        if (dialog1 != null) {
            dialog1.show();
            return;
        }
        // 以特定的风格创建一个dialog
        dialog1 = new Dialog(this, R.style.ShowDialog);
        // 加载dialog布局view
        View purchase = LayoutInflater.from(this).inflate(R.layout.dialog_purchase, null);
//        view = View.inflate(this, R.layout.dialog_add_cart, null);//半弹窗布局
        ImageView iv_dialog_close = (ImageView) purchase.findViewById(R.id.dialog_add_cart_iv_close);//弹窗布局右上角的打X关闭图片
        AutoFlowLayout mFlowLayout = (AutoFlowLayout) purchase.findViewById(R.id.afl_cotent);//流式布局
        ImageView goods_mainIv = (ImageView) purchase.findViewById(R.id.dialog_add_cart_iv_main);
        Glide.with(this).load(banner_list.get(0))
                .error(R.mipmap.ic_launcher) //异常时候显示的图片
                .placeholder(R.drawable.tjtp) //加载成功前显示的图片
                .fallback(R.mipmap.ic_launcher) //url为空的时候,显示的图片
//                .apply(RequestOptions.circleCropTransform())//加载为圆形图片
                .into(goods_mainIv);//为了加载网络图片的两步方法
        TextView goods_name = (TextView) purchase.findViewById(R.id.add_cart_name);
        goods_name.setText(goodsName);
        TextView goods_huohao = (TextView) purchase.findViewById(R.id.add_cart_num);
        goods_huohao.setText("货号：" + huohao);
        TextView goods_kucun = (TextView) purchase.findViewById(R.id.add_cart_kuncun);
        goods_kucun.setText("库存" + repertory + "件");
        EditText tianjiashu_et = (EditText) purchase.findViewById(R.id.cart_tianjiashu);
        tianjiashu_et.setText("" + queidngtianjiadeshuliang);
        TextView xianshi_guige_jiage = (TextView) purchase.findViewById(R.id.xuanze_guige_jiage);
        xianshi_guige_jiage.setText("¥" + goodsPrice);//显示当前规格价格，默认先显示传过来的总价
        ImageView jianshuliang_iv = (ImageView) purchase.findViewById(R.id.cart_jianshuliang);
        jianshuliang_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                queidngtianjiadeshuliang = Integer.parseInt(tianjiashu_et.getText().toString());
                if (queidngtianjiadeshuliang > 1) {
                    queidngtianjiadeshuliang -= 1;
                }
                tianjiashu_et.setText("" + queidngtianjiadeshuliang);
            }
        });
        ImageView jiashuliang_iv = (ImageView) purchase.findViewById(R.id.cart_jiashuliang);
        jiashuliang_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                queidngtianjiadeshuliang = Integer.parseInt(tianjiashu_et.getText().toString());
                if (queidngtianjiadeshuliang < Integer.parseInt(repertory)) {
                    queidngtianjiadeshuliang += 1;
                }
                tianjiashu_et.setText("" + queidngtianjiadeshuliang);
            }
        });
        Button bt_addCart = purchase.findViewById(R.id.buy_btn);//确定添加购物车按钮
        bt_addCart.setText("确定添加");
        mLayoutInflater = LayoutInflater.from(this);//照抄别人的Demo，不懂有啥用，不写会报错
        iv_dialog_close.setOnClickListener(v -> dialog1.dismiss());//设置关闭销毁弹窗
        /* 触发联网，添加进购物车 */
        bt_addCart.setOnClickListener(v -> {
            if (quedingtianjia_deguige == -1 && specfication_GuiGe_List.size() != 0) {//已选默认值为-1但是规格总数量不为0
                Toast toast = Toast.makeText(GoodsActivity.this, "请选择商品规格", Toast.LENGTH_LONG);
                LinearLayout layout = (LinearLayout) toast.getView();
                TextView tv = (TextView) layout.getChildAt(0);
                tv.setTextSize(20);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            } else {
                new Thread(runnableTianJiaGouWuChe).start();
            }
        });
        /* 开始把拿到的规格列表显示出来 */
        mFlowLayout.setAdapter(new FlowAdapter(specfication_GuiGe_List) {
            @Override
            public View getView(int position) {
                View item = mLayoutInflater.inflate(R.layout.special_item, null);
                TextView tvAttrTag = (TextView) item.findViewById(R.id.tv_attr_tag);
                tvAttrTag.setText(specfication_GuiGe_List.get(position).getRuleName());
                return item;
            }
        });
        /* 用户选择了哪个规格 */
        mFlowLayout.setOnItemClickListener(new AutoFlowLayout.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View view) {
                quedingtianjia_deguige = position;
                xianshi_guige_jiage.setText("¥" + specfication_GuiGe_List.get(position).getProductPrice());//并显示当前规格的价格
            }
        });

        initPurchaseViews(purchase, dialog1);
        // 设置外部点击 取消dialog
        dialog1.setCancelable(true);
        // 获得窗体对象
        Window window = dialog1.getWindow();
        // 设置窗体的对齐方式
        window.setGravity(Gravity.BOTTOM);
        // 设置窗体动画
        window.setWindowAnimations(R.style.AnimBottom);
        // 设置窗体的padding
        window.getDecorView().setPadding(0, 200, 0, 0);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);

        dialog1.setContentView(purchase);
        dialog1.show();

        /* 小秦用的bottomSheetDialog，每一行是死马作用也没懂 */
//        bottomSheetDialog1 = new BottomSheetDialog(this, R.style.dialog);
//        bottomSheetDialog1.setContentView(view);
//        bottomSheetDialog1.setCanceledOnTouchOutside(true);
//        final BottomSheetBehavior mDialogBehavior = BottomSheetBehavior.from((View) view.getParent());
//        mDialogBehavior.setPeekHeight(getWindowHeight());
//        mDialogBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
//            @Override
//            public void onStateChanged(@NonNull View bottomSheet, int newState) {
//                if (newState == BottomSheetBehavior.STATE_HIDDEN) {
//                    mDialogBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
//                } else if (newState == BottomSheetBehavior.STATE_SETTLING) {
//                    if (slideOffsets <= -0.28) {
//                        bottomSheetDialog1.dismiss();
//                    }
//                }
//            }
//
//            @Override
//            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
//                slideOffsets = slideOffset;
//            }
//        });
    }

    private int getWindowHeight() {
        Resources res = getResources();
        DisplayMetrics displayMetrics = res.getDisplayMetrics();
        return displayMetrics.heightPixels;
    }

    /* 子线程中的Handler 添加进购物车 联网判断 /tWebshopShoppingCard/create 是否联网成功 */
    private Handler mTianJiaGouWuCheHT_net_judge;

    /* 子线程：使用POST方法向服务器发送 productId、productNumber、productSpecficationId 参数完成  */
    private Runnable runnableTianJiaGouWuChe = new Runnable() {
        @Override
        public void run() {
            records_date = "";//请求之前先清空一次
            String chaochangTOKEN = (String) Objects.requireNonNull(SpUtils.get(GoodsActivity.this, UrlConfig.USERID, ""));
            MediaType JSON = MediaType.parse("application/json; charset=utf-8");
            try {
                JSONObject json = new JSONObject();
                json.put("productId", sygymcgl_id);
                json.put("productNumber", queidngtianjiadeshuliang);//添加数量
                json.put("productSpecficationId", (specfication_GuiGe_List.size() == 0) ? "" : specfication_GuiGe_List.get(quedingtianjia_deguige).getId());//没有就空，有就获取当前点击的值
                RequestBody body = RequestBody.create(JSON, String.valueOf(json));
//                Log.i("发送JSON", String.valueOf(json));
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(ServiceConfig.getRootUrl() + "/tWebshopShoppingCard/create")//添加进购物车 端口
                        .addHeader("Authorization", chaochangTOKEN)
                        .post(body)
                        .build();
                client.newCall(request).enqueue(new Callback() {
                    @SuppressLint("HandlerLeak")
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {//未知联网错误，全部显示
                        if (!GoodsActivity.this.isFinishing()) {
//                            Log.e("添加进购物车 未知联网错误", "失败 e=" + e);
                            Looper.prepare();
                            mTianJiaGouWuCheHT_net_judge = new Handler() {
                                @Override
                                public void handleMessage(@NonNull Message msg) {
                                    super.handleMessage(msg);
                                    Toast toast = Toast.makeText(GoodsActivity.this, "获取商品详情失败 \n" + e, Toast.LENGTH_LONG);
                                    LinearLayout layout = (LinearLayout) toast.getView();
                                    TextView tv = (TextView) layout.getChildAt(0);
                                    tv.setTextSize(20);
                                    toast.setGravity(Gravity.CENTER, 0, 0);
                                    toast.show();
                                }
                            };
                            mTianJiaGouWuCheHT_net_judge.sendEmptyMessage(1);
                            Looper.loop();
                        }
                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        if (!GoodsActivity.this.isFinishing()) {
                            assert response.body() != null;
                            records_date = response.body().string();//获取到数据
                            try {
                                jsonTianJiaGouWuChe(records_date);//把数据传入解析josn数据方法
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

    /* 解析 添加进购物车 返回值，存集合 */
    @SuppressLint("HandlerLeak")
    private void jsonTianJiaGouWuChe(String date) throws JSONException {
        //{
        //	"body": true,
        //	"state": 200,
        //	"msg": "请求成功"
        //}
        Message msg = new Message();//处理完成后给handler发送消息
        if (date != null && !date.equals("")) {//判断数据是空
            JSONObject jsonObject3 = new JSONObject(date);
            String state = jsonObject3.optString("state", "");//获取返回数据中 state 的值
            if (state.equals("200")) {
//                Log.w("添加进购物车 成功", "返回数据=" + date);
                msg.what = 1;//成功
                mTianJiaGouWuCheHT.sendMessage(msg);
            } else {
//                Log.e("添加进购物车 失败", "返回数据=" + date);
                msg.what = 2;//失败
                mTianJiaGouWuCheHT.sendMessage(msg);
            }
        } else {
//            Log.e("添加进购物车 失败", "没有任何返回值");
            msg.what = 2;//失败
            mTianJiaGouWuCheHT.sendMessage(msg);
        }
    }

    /* 子线程 添加进购物车 判断的Handler 返回值判断 */
    @SuppressLint("HandlerLeak")
    private Handler mTianJiaGouWuCheHT = new Handler() {
        @SuppressLint("SetTextI18n")
        @Override
        public void handleMessage(Message msg) {//在子线程中通知主线程，让主线程做更新操作
            switch (msg.what) {
                case 1://成功
                    dialog1.dismiss();//关闭弹窗
                    Toast toast_chenggong = Toast.makeText(GoodsActivity.this, "添加进购物车 成功", Toast.LENGTH_SHORT);
                    LinearLayout layout_chenggong = (LinearLayout) toast_chenggong.getView();
                    TextView tv_chenggong = (TextView) layout_chenggong.getChildAt(0);
                    tv_chenggong.setTextSize(20);
                    toast_chenggong.setGravity(Gravity.CENTER, 0, 0);
                    toast_chenggong.show();
                    break;
                case 2://失败
                    Toast toast = Toast.makeText(GoodsActivity.this, "添加进购物车 失败", Toast.LENGTH_LONG);
                    LinearLayout layout = (LinearLayout) toast.getView();
                    TextView tv = (TextView) layout.getChildAt(0);
                    tv.setTextSize(20);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    break;
                default:
                    break;
            }
        }
    };

    /* 立即购买 */
    @OnClick(R.id.tv_buy_now)
    public void doBuyNow(View view0) {
        if (dialog2 != null) {
            dialog2.show();
            return;
        }
        // 以特定的风格创建一个dialog
        dialog2 = new Dialog(this, R.style.ShowDialog);
        // 加载dialog布局view
        View purchase = LayoutInflater.from(this).inflate(R.layout.dialog_purchase, null);

//        view = View.inflate(this, R.layout.dialog_add_cart, null);//半弹窗布局
        ImageView iv_dialog_close = (ImageView) purchase.findViewById(R.id.dialog_add_cart_iv_close);//弹窗布局右上角的打X关闭图片
        AutoFlowLayout mFlowLayout = (AutoFlowLayout) purchase.findViewById(R.id.afl_cotent);//流式布局
        ImageView goods_mainIv = (ImageView) purchase.findViewById(R.id.dialog_add_cart_iv_main);
        Glide.with(this).load(banner_list.get(0))
                .error(R.mipmap.ic_launcher) //异常时候显示的图片
                .placeholder(R.drawable.tjtp) //加载成功前显示的图片
                .fallback(R.mipmap.ic_launcher) //url为空的时候,显示的图片
//                .apply(RequestOptions.circleCropTransform())//加载为圆形图片
                .into(goods_mainIv);//为了加载网络图片的两步方法
        TextView goods_name = (TextView) purchase.findViewById(R.id.add_cart_name);
        goods_name.setText(goodsName);
        TextView goods_huohao = (TextView) purchase.findViewById(R.id.add_cart_num);
        goods_huohao.setText("货号：" + huohao);
        TextView goods_kucun = (TextView) purchase.findViewById(R.id.add_cart_kuncun);
        goods_kucun.setText("库存" + repertory + "件");
        EditText tianjiashu_et = (EditText) purchase.findViewById(R.id.cart_tianjiashu);
        tianjiashu_et.setText("" + queidngtianjiadeshuliang);
        TextView xianshi_guige_jiage = (TextView) purchase.findViewById(R.id.xuanze_guige_jiage);
        xianshi_guige_jiage.setText("¥" + goodsPrice);//显示当前规格价格，默认先显示传过来的总价
        ImageView jianshuliang_iv = (ImageView) purchase.findViewById(R.id.cart_jianshuliang);
        jianshuliang_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                queidngtianjiadeshuliang = Integer.parseInt(tianjiashu_et.getText().toString());
                if (queidngtianjiadeshuliang > 1) {
                    queidngtianjiadeshuliang -= 1;
                }
                tianjiashu_et.setText("" + queidngtianjiadeshuliang);
            }
        });
        ImageView jiashuliang_iv = (ImageView) purchase.findViewById(R.id.cart_jiashuliang);
        jiashuliang_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                queidngtianjiadeshuliang = Integer.parseInt(tianjiashu_et.getText().toString());
                if (queidngtianjiadeshuliang < Integer.parseInt(repertory)) {
                    queidngtianjiadeshuliang += 1;
                }
                tianjiashu_et.setText("" + queidngtianjiadeshuliang);
            }
        });
        Button bt_addCart = purchase.findViewById(R.id.buy_btn);//立即购买
        bt_addCart.setText("立即购买");
        mLayoutInflater = LayoutInflater.from(this);//照抄别人的Demo，不懂有啥用，不写会报错
        iv_dialog_close.setOnClickListener(v -> dialog2.dismiss());//设置关闭销毁弹窗
        /* 不用联网，立即购买 */
        bt_addCart.setOnClickListener(v -> {
            if (quedingtianjia_deguige == -1 && specfication_GuiGe_List.size() != 0) {//已选默认值为-1但是规格总数量不为0
                Toast toast = Toast.makeText(GoodsActivity.this, "请选择商品规格", Toast.LENGTH_LONG);
                LinearLayout layout = (LinearLayout) toast.getView();
                TextView tv = (TextView) layout.getChildAt(0);
                tv.setTextSize(20);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            } else {
                UserOrder.getInstance().goodid = sygymcgl_id;
                UserOrder.getInstance().goodsName = goodsName;
                UserOrder.getInstance().goodsPrice = goodsPrice;
                UserOrder.getInstance().pictureUrl = banner_list.get(0);
                //			"id": "3",
                //			"ruleName": "黄色",
                //			"createTime": null,
                //			"productId": "13e1e94055cfb64a2128ed56083feb0d92ac",
                //			"typeId": "1",
                //			"productPrice": 15.0
                Intent intent = new Intent(this, FukuanActivity.class);
                intent.putExtra("goodid", sygymcgl_id);
                intent.putExtra("goodsName", goodsName);
                intent.putExtra("dianming", dianming);//传店名
                intent.putExtra("guige_id", (specfication_GuiGe_List.size() == 0) ? "" : specfication_GuiGe_List.get(quedingtianjia_deguige).getId());//传规格id
                intent.putExtra("guige_ruleName", (specfication_GuiGe_List.size() == 0) ? "" : specfication_GuiGe_List.get(quedingtianjia_deguige).getRuleName());//传规格名称
                intent.putExtra("guige_productPrice", (specfication_GuiGe_List.size() == 0) ? "" : specfication_GuiGe_List.get(quedingtianjia_deguige).getProductPrice());//传规格价格
                intent.putExtra("guige_number", queidngtianjiadeshuliang);//传当前购买数量
                intent.putExtra("goodsPrice", goodsPrice);
                intent.putExtra("pictureUrl", banner_list.get(0));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                this.startActivityForResult(intent, 0);
                dialog2.dismiss();//关闭弹窗
            }
        });
        /* 开始把拿到的规格列表显示出来 */
        mFlowLayout.setAdapter(new FlowAdapter(specfication_GuiGe_List) {
            @Override
            public View getView(int position) {
                View item = mLayoutInflater.inflate(R.layout.special_item, null);
                TextView tvAttrTag = (TextView) item.findViewById(R.id.tv_attr_tag);
                tvAttrTag.setText(specfication_GuiGe_List.get(position).getRuleName());
                return item;
            }
        });
        /* 用户选择了哪个规格 */
        mFlowLayout.setOnItemClickListener(new AutoFlowLayout.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View view) {
                quedingtianjia_deguige = position;
                xianshi_guige_jiage.setText("¥" + specfication_GuiGe_List.get(position).getProductPrice());//并显示当前规格的价格
            }
        });

        /* 小秦用的bottomSheetDialog，每一行是死马作用也没懂 */
//        bottomSheetDialog2 = new BottomSheetDialog(this, R.style.dialog);
//        bottomSheetDialog2.setContentView(view);
//        bottomSheetDialog2.setCanceledOnTouchOutside(true);
//        final BottomSheetBehavior mDialogBehavior = BottomSheetBehavior.from((View) view.getParent());
//        mDialogBehavior.setPeekHeight(getWindowHeight());
//        mDialogBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
//            @Override
//            public void onStateChanged(@NonNull View bottomSheet, int newState) {
//                if (newState == BottomSheetBehavior.STATE_HIDDEN) {
//                    mDialogBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
//                } else if (newState == BottomSheetBehavior.STATE_SETTLING) {
//                    if (slideOffsets <= -0.28) {
//                        bottomSheetDialog2.dismiss();
//                    }
//                }
//            }
//
//            @Override
//            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
//                slideOffsets = slideOffset;
//            }
//        });

        initPurchaseViews(purchase, dialog2);
        // 设置外部点击 取消dialog
        dialog2.setCancelable(true);
        // 获得窗体对象
        Window window = dialog2.getWindow();
        // 设置窗体的对齐方式
        window.setGravity(Gravity.BOTTOM);
        // 设置窗体动画
        window.setWindowAnimations(R.style.AnimBottom);
        // 设置窗体的padding
        window.getDecorView().setPadding(0, 200, 0, 0);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);

        dialog2.setContentView(purchase);
        dialog2.show();
    }

    private void initPurchaseViews(View purchase, final Dialog dialog) {
        purchase.findViewById(R.id.rootView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        purchase.findViewById(R.id.llInner).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    /* 退出当前页面 */
    @OnClick(R.id.goods_back)
    public void doBack(View view) {
        finish();
    }

    @Override/* 销毁时运行 */
    public void onDestroy() {
        super.onDestroy();
        Glide.get(this).clearMemory();//清除内存缓存
//        Log.e("商品详情页", "已销毁");
    }

}