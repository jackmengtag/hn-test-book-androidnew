package com.yangna.lbdsp.mall.view;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.yangna.lbdsp.R;
import com.yangna.lbdsp.common.ServiceConfig;
import com.yangna.lbdsp.common.UrlConfig;
import com.yangna.lbdsp.common.base.LQRPhotoSelectUtils;
import com.yangna.lbdsp.common.base.LoadingDialog;
import com.yangna.lbdsp.common.utils.SpUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kr.co.namee.permissiongen.PermissionFail;
import kr.co.namee.permissiongen.PermissionGen;
import kr.co.namee.permissiongen.PermissionSuccess;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/* 上传商品页面 */
public class UploadGoodsActivity extends AppCompatActivity/*BasePresenterFragActivity*/ {

    @BindView(R.id.upload_goods_back)
    ImageView uploadGoodsBack;
    //    @BindView(R.id.tianjiashangpingtupian)
//    LinearLayout TJSPTP;//添加商品图片按钮
    @BindView(R.id.daishangchuanshangpintupian)
    ImageView tianjiashangpingtupian;//添加商品图片按钮
    //    @BindView(R.id.goods_banner)
//    Banner banner;
    @BindView(R.id.shangpin_migncheng)//商品名称
            EditText shagnpinmingcheng;
    @BindView(R.id.shangpin_shuliang)//商品数量
            EditText shagnpinshuliang;
    @BindView(R.id.shangpin_jiage)//商品价格
            EditText shangpinjiage;
    @BindView(R.id.radioGroup_status)//商品类型状态单选按钮组
            RadioGroup statusRG;
    @BindView(R.id.radioBT_shipin)//食品
            RadioButton shipinRBT;
    @BindView(R.id.radioBT_yongpin)//用品
            RadioButton yongpinRBT;
    @BindView(R.id.radioBT_qita)//其他
            RadioButton qitaRBT;

    private String shangdianIDData = "";//获取自己商店id返回数据
    private String id = "";//自己商店id，还没拿到的时候
    private LoadingDialog dialog;
    private ArrayList<String> banner_list = new ArrayList<String>();
    private LQRPhotoSelectUtils sptpPhotoSelectUtils;//商品图片
    private String sptpfilePath = "";// 手机上图片的路径
    private String sptpURL = "";// 图片接口/auth/uploadFile返回的图片url 键名pictureUrl 返回后的值
    private String goodsName = "";//商品名称
    private String cateId = "";//商品分类ID 分类id.1食品  2用品  剩余默认为0 键名cateId
    private String goodsAmount = "";//商品数量
    private String goodsPrice = "";//商品价格
    private String putAwayGoodsData = "";//上传商品5个键值对成功返回值
    private File sptp;//商品图片
    private AlertDialog alert4;
    private int digits = 2;//小数点后的限制位数
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    @Override//创建时运行
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_goods);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        id = intent.getStringExtra("shopId");//获取上一页传过来的id

        //有时间记得写篇文章发CSDN
        tianjiashangpingtupian.setOnClickListener(new View.OnClickListener() {//正面点击
            @Override
            public void onClick(View v) {// 3、调用从图库选取图片方法
                PermissionGen.needPermission(UploadGoodsActivity.this, LQRPhotoSelectUtils.RESULT_LOAD_IMAGE1, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE});
            }
        });

        //商品图片
        sptpPhotoSelectUtils = new LQRPhotoSelectUtils(this, new LQRPhotoSelectUtils.PhotoSelectListener() {
            @Override
            public void onFinish(File outputFile, Uri outputUri) {
                // 4、当拍照或从图库选取图片成功后回调
                sptpfilePath = outputFile.getAbsolutePath();
                Glide.with(UploadGoodsActivity.this).load(outputUri).into(tianjiashangpingtupian);
            }
        }, false);//true裁剪，false不裁剪

        //商品类型状态
        statusRG.setOnCheckedChangeListener(new RadioGroupListener());

        //控制可以输入0,但不能输入0开头的数字  https://blog.csdn.net/u012080791/article/details/78041186
        shangpinjiage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            //Android-EditText两种方法限制输入两位小数 https://www.jianshu.com/p/dc2419de0abf
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //删除“.”后面超过2位后的数据
                if (s.toString().contains(".")) {
                    if (s.length() - 1 - s.toString().indexOf(".") > digits) {
                        s = s.toString().subSequence(0, s.toString().indexOf(".") + digits + 1);
                        shangpinjiage.setText(s);
                        shangpinjiage.setSelection(s.length()); //光标移到最后
                    }
                }
                //如果"."在起始位置,则起始位置自动补0
                if (s.toString().trim().substring(0).equals(".")) {
                    s = "0" + s;
                    shangpinjiage.setText(s);
                    shangpinjiage.setSelection(2);
                }
                //如果起始位置为0,且第二位跟的不是".",则无法后续输入
                if (s.toString().startsWith("0") && s.toString().trim().length() > 1) {
                    if (!s.toString().substring(1, 2).equals(".")) {
                        shangpinjiage.setText(s.subSequence(0, 1));
                        shangpinjiage.setSelection(1);
                        return;
                    }
                }
            }

            //控制可以输入0,但不能输入0开头的数字  https://blog.csdn.net/u012080791/article/details/78041186
            @Override
            public void afterTextChanged(Editable s) {
//                String text = s.toString();
//                int len = s.toString().length();
//                if (len > 1 && text.startsWith("0")) {
//                    s.replace(0, 1, "");
//                }
            }
        });

        //https://blog.csdn.net/u012080791/article/details/78041186 Android edittext设置只能输入整数，并且第一个输入0后，不再输入其他数字
        shagnpinshuliang.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            //            EditText搭配android:inputType="numberSigned"使用
            @Override
            public void afterTextChanged(Editable s) {
                String text = s.toString();
                int len = s.toString().length();
                if (len > 1 && text.startsWith("0")) {
                    s.replace(0, 1, "");
                }
            }
        });

    }

    @Override//启动时运行
    protected void onStart() {
        super.onStart();
        //拿到自己商店的id
//        new Thread(runnableGetShopByAccountId).start();
    }

    //子线程中的Handler 获取自己的商店id 联网判断  shop/shop/getShopByAccountId 是否联网成功
    private Handler mGetShopByAccountIdHT_net_judge;

    /* 子线程：使用POST方法向服务器发送 id  */
    private Runnable runnableGetShopByAccountId = new Runnable() {
        @Override
        public void run() {
            String ACCOUNT = (String) Objects.requireNonNull(SpUtils.get(UploadGoodsActivity.this, UrlConfig.ACCOUNT, ""));
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
                        if (!UploadGoodsActivity.this.isFinishing()) {
                            //dialog.dismiss();//销毁加载框
                            Log.e("获取AccountId未知联网错误", "失败 e=" + e);
                            Looper.prepare();
                            mGetShopByAccountIdHT_net_judge = new Handler() {
                                @Override
                                public void handleMessage(@NonNull Message msg) {
                                    super.handleMessage(msg);
                                    alert4 = new AlertDialog.Builder(UploadGoodsActivity.this).create();
//                                    alert4.setIcon(R.mipmap.ic_launcher);//设置对话框的图标
                                    alert4.setTitle("未知联网错误");//设置对话框的标题
                                    alert4.setCancelable(false);
                                    alert4.setMessage(e + "\n\n请联系后台客服处理");//设置要显示的内容
                                    //添加确定按钮
                                    alert4.setButton(DialogInterface.BUTTON_POSITIVE, "确定", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
//                                //结束所有Activity，返回登录页
                                        }
                                    });
                                    alert4.show();// 创建对话框并显示
                                }
                            };
                            mGetShopByAccountIdHT_net_judge.sendEmptyMessage(1);
                            Looper.loop();
                        }
                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        if (!UploadGoodsActivity.this.isFinishing()) {
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
        //{
        //  "body": {
        //    "id": 8,
        //    "accountId": 8,
        //    "shopName": "Dickies",
        //    "address": "万达",
        //    "mapAddress": "万达",
        //    "status": "1",
        //    "createTime": 20201024,
        //    "shopLogoUrl": "http://img30.360buyimg.com/popshop/g15/M00/00/1F/rBEhWVG5Q5gIAAAAAAAQDuUoPvcAAAMHgP_5ZQAABAm961.gif",
        //    "enterpriseCertification": "0",
        //    "mallLevel": "3.5",
        //    "focus": "0",
        //    "firstFigure1": "http://img10.360buyimg.com/n3/jfs/t1/147131/20/10679/203858/5f8426bdE4cf174a4/8ee7ad620741c09f.jpg",
        //    "firstFigure2": "http://img11.360buyimg.com/n3/jfs/t1/145099/31/1828/149445/5efae9a6E7acc3900/68633a55d8e0214f.jpg",
        //    "firstFigure3": "http://img10.360buyimg.com/n3/jfs/t1/139306/25/2505/188958/5f06a7e9E38c936e4/017f7a95830a86c9.jpg",
        //    "firstFigure4": "http://img13.360buyimg.com/n3/jfs/t1/95382/16/8918/104078/5e0b2757Ee932e271/f89ab05104237ff6.jpg",
        //    "license": "74",
        //    "otherLicense": "75"
        //  },
        //  "state": 200,
        //  "msg": "请求成功"
        //}
        if (date != null && !date.equals("")) {//判断数据是空
            Log.i("获取自己的商店id 有返回值", "date = " + date);
            JSONObject jsonObject = new JSONObject(date);
            String waiceng = jsonObject.optString("body", "");//获取返回数据中 body 的值
            if (waiceng == null || waiceng.equals("")) {
                Log.e("获取自己的商店id失败》》》", "返回records值：" + date);
                msg.what = 2;//失败
                mGetShopByAccountIdHT.sendMessage(msg);
            } else {//
                JSONObject jsonObject1 = new JSONObject(waiceng);
                String neiceng = jsonObject1.optString("accountId", "");//获取返回数据中 accountId 的值
                id = neiceng;
                Log.w("获取自己的商店id成功》》》", "返回accountId值：" + neiceng);
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
                case 1://获取成功
//                    parsingMenDianListJson();
                    break;
                case 2://获取失败
                    Toast toast = Toast.makeText(UploadGoodsActivity.this, "获取自己的商店id失败", Toast.LENGTH_LONG);
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

    //查询状态监听
    class RadioGroupListener implements RadioGroup.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId == shipinRBT.getId()) {//食品
                cateId = "1";//
            } else if (checkedId == yongpinRBT.getId()) {//用品
                cateId = "2";//
            } else if (checkedId == qitaRBT.getId()) {//其他
                cateId = "0";//
            } else {
                cateId = "";//不点击就设置个默认，强制提醒用户选择
            }
        }
    }

    @PermissionFail(requestCode = LQRPhotoSelectUtils.REQ_SELECT_PHOTO)
    private void showTip2() {
        //        Toast.makeText(getApplicationContext(), "不给我权限是吧，那就别玩了", Toast.LENGTH_SHORT).show();
        //创建对话框创建器
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //设置对话框显示小图标
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        //设置标题
        builder.setTitle("权限申请");
        //设置正文
        builder.setMessage("在设置-应用-Hpay-权限 中开启相机、存储权限，才能正常使用拍照或图片选择功能");
        //添加确定按钮点击事件
        builder.setPositiveButton("去设置", new DialogInterface.OnClickListener() {//点击完确定后，触发这个事件
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //这里用来跳到手机设置页，方便用户开启权限
                Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
                intent.setData(Uri.parse("package:" + UploadGoodsActivity.this.getPackageName()));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        //添加取消按钮点击事件
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        //使用构建器创建出对话框对象
        AlertDialog tupianduqu_dialog = builder.create();
        tupianduqu_dialog.show();//显示对话框
    }

    /* 进入手机文件夹选择图片需要这个方法 */
    @PermissionSuccess(requestCode = LQRPhotoSelectUtils.RESULT_LOAD_IMAGE1)
    public void selectPhoto1() {
        sptpPhotoSelectUtils.selectPhoto1();
    }

    //退出当前页面
    @OnClick(R.id.upload_goods_back)
    public void doBack(View view) {
        finish();
    }

    /* 把用户选择好的图片显示在当前imageview中 */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == LQRPhotoSelectUtils.RESULT_LOAD_IMAGE1) {//自定义图库1
            sptpPhotoSelectUtils.attachToActivityForResult(requestCode, resultCode, data);
        } else if (requestCode == LQRPhotoSelectUtils.RESULT_LOAD_IMAGE2) {//自定义图库2
//            fmLqrPhotoSelectUtils.attachToActivityForResult(requestCode, resultCode, data);
        } else if (requestCode == LQRPhotoSelectUtils.RESULT_LOAD_IMAGE3) {//自定义图库3
//            scLqrPhotoSelectUtils.attachToActivityForResult(requestCode, resultCode, data);
        }
    }

    /* 上传商品 页面 点击上传按钮 */
    @OnClick(R.id.user_shenfen)
    public void doShangChuanShangPin(View view) {//拿到TOKEN和4个处理过的信息，和文件路径
        goodsName = shagnpinmingcheng.getText().toString();//名称
        goodsAmount = shagnpinshuliang.getText().toString();//数量
        goodsPrice = shangpinjiage.getText().toString();//价格
//        quanju_shenfenzhenghao = shenfenzhenghao_et.getText().toString();//分类
        if (id == null || id.equals("")) {
            Toast toast = Toast.makeText(this, "获取自己商店ID失败，请联系后台客服处理", Toast.LENGTH_LONG);//
            LinearLayout layout = (LinearLayout) toast.getView();
            TextView tv = (TextView) layout.getChildAt(0);
            tv.setTextSize(25);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        } else {
            if (goodsName == null || goodsName.equals("")) {
                Toast toast = Toast.makeText(this, "请输入商品名称", Toast.LENGTH_SHORT);//
                LinearLayout layout = (LinearLayout) toast.getView();
                TextView tv = (TextView) layout.getChildAt(0);
                tv.setTextSize(25);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            } else {
                if (goodsName.length() > 50) {
                    Toast toast = Toast.makeText(this, "商品名称不能超过50个字！", Toast.LENGTH_SHORT);//
                    LinearLayout layout = (LinearLayout) toast.getView();
                    TextView tv = (TextView) layout.getChildAt(0);
                    tv.setTextSize(25);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                } else {
                    if (goodsAmount.equals("") || goodsAmount == null) {
                        Toast toast = Toast.makeText(this, "请输入商品数量", Toast.LENGTH_SHORT);//
                        LinearLayout layout = (LinearLayout) toast.getView();
                        TextView tv = (TextView) layout.getChildAt(0);
                        tv.setTextSize(25);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    } else {
                        if (goodsPrice == null || goodsPrice.equals("")) {
                            Toast toast = Toast.makeText(this, "请输入商品价格", Toast.LENGTH_SHORT);//
                            LinearLayout layout = (LinearLayout) toast.getView();
                            TextView tv = (TextView) layout.getChildAt(0);
                            tv.setTextSize(25);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                        } else {
                            if (cateId == null || cateId.equals("")) {
                                Toast toast = Toast.makeText(this, "请选择商品分类", Toast.LENGTH_SHORT);//
                                LinearLayout layout = (LinearLayout) toast.getView();
                                TextView tv = (TextView) layout.getChildAt(0);
                                tv.setTextSize(25);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();
                            } else {
//                        if (quanju_shi_id == null || quanju_shi_id.equals("")) {
//                            Toast toast = Toast.makeText(this, "请选择市/县", Toast.LENGTH_SHORT);//
//                            LinearLayout layout = (LinearLayout) toast.getView();
//                            TextView tv = (TextView) layout.getChildAt(0);
//                            tv.setTextSize(25);
//                            toast.setGravity(Gravity.CENTER, 0, 0);
//                            toast.show();
//                        } else {
//                            if (startDate == null || startDate.equals("")) {
//                                Toast toast = Toast.makeText(this, "请选择身份证起始有效期", Toast.LENGTH_SHORT);//
//                                LinearLayout layout = (LinearLayout) toast.getView();
//                                TextView tv = (TextView) layout.getChildAt(0);
//                                tv.setTextSize(25);
//                                toast.setGravity(Gravity.CENTER, 0, 0);
//                                toast.show();
//                            } else {
//                                if (endDate == null || endDate.equals("")) {
//                                    Toast toast = Toast.makeText(this, "请选择身份证截止有效期", Toast.LENGTH_SHORT);//
//                                    LinearLayout layout = (LinearLayout) toast.getView();
//                                    TextView tv = (TextView) layout.getChildAt(0);
//                                    tv.setTextSize(25);
//                                    toast.setGravity(Gravity.CENTER, 0, 0);
//                                    toast.show();
//                                } else {
//                                    if (zmfilePath == null || zmfilePath.equals("")) {
//                                        Toast toast = Toast.makeText(this, "请选择身份证正面照片", Toast.LENGTH_SHORT);//
//                                        LinearLayout layout = (LinearLayout) toast.getView();
//                                        TextView tv = (TextView) layout.getChildAt(0);
//                                        tv.setTextSize(25);
//                                        toast.setGravity(Gravity.CENTER, 0, 0);
//                                        toast.show();
//                                    } else {
//                                        if (fmfilePath == null || fmfilePath.equals("")) {
//                                            Toast toast = Toast.makeText(this, "请选择身份证反面照片", Toast.LENGTH_SHORT);//
//                                            LinearLayout layout = (LinearLayout) toast.getView();
//                                            TextView tv = (TextView) layout.getChildAt(0);
//                                            tv.setTextSize(25);
//                                            toast.setGravity(Gravity.CENTER, 0, 0);
//                                            toast.show();
//                                        } else {
                                if (sptpfilePath == null || sptpfilePath.equals("")) {
                                    Toast toast = Toast.makeText(this, "请选择商品图片", Toast.LENGTH_SHORT);//
                                    LinearLayout layout = (LinearLayout) toast.getView();
                                    TextView tv = (TextView) layout.getChildAt(0);
                                    tv.setTextSize(25);
                                    toast.setGravity(Gravity.CENTER, 0, 0);
                                    toast.show();
                                } else {
                                    //把价钱最后的一个小数点砍掉先
                                    goodsPrice = goodsPrice.endsWith(".") ? goodsPrice.substring(0, goodsPrice.length() - 1) : goodsPrice;
                                    // 可以启动线程了
                                    sptp = new File(LQRPhotoSelectUtils.compressImage(sptpfilePath));//压缩图片
                                    //加载弹窗
                                    LoadingDialog.Builder loadBuilder = new LoadingDialog.Builder(this)
                                            .setMessage("加载中...")
                                            .setCancelable(true)//返回键是否可点击
                                            .setCancelOutside(false);//窗体外是否可点击
                                    dialog = loadBuilder.create();
                                    dialog.show();//显示弹窗
                                    new Thread(runnableUploadGoodsPicture).start();//点击 上传商品
                                }
//                                        }
//                                    }
//                                }
//                            }
//                        }
                            }
                        }
                    }
                }
            }
        }
    }

    /* 上传商品图片 专用Handler */
    public Handler uploadGoodsPictureHT_net_judge;

    /* 子线程：通过POST方法向服务器实时发送TOKEN，上传上传商品图片，  */
    public Runnable runnableUploadGoodsPicture = new Runnable() {
        @Override
        public void run() {
//            try {
//                JSONObject json = new JSONObject();
            OkHttpClient client = new OkHttpClient();
            RequestBody image1 = RequestBody.create(MediaType.parse("image/jpg"), sptp);
            RequestBody body = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("file", URLEncoder.encode(sptp.getPath()), image1)//转码后OKHTTP3也才可以用中文 URLEncoder.encode()
                    .build();
            Request request = new Request.Builder()
                    .url(ServiceConfig.getRootUrl() + "/auth/uploadFile")//上传 上传商品图片 端口 网址命名跟本页面有冲突，直接用完整路径定位法
                    .post(body)
                    .build();
            client.newCall(request).enqueue(new Callback() {
                @SuppressLint("HandlerLeak")
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {//任何错误
                    if (!isFinishing()) {
                        dialog.dismiss();//销毁加载框
                        Log.e("未知联网错误", "UploadGoodsActivity —— /auth/uploadFile 失败 e=" + e);
                        Looper.prepare();
                        uploadGoodsPictureHT_net_judge = new Handler() {
                            @Override
                            public void handleMessage(Message existence) {
                                super.handleMessage(existence);
                                alert4 = new AlertDialog.Builder(UploadGoodsActivity.this).create();
//                                alert4.setIcon(R.mipmap.ic_launcher);//设置对话框的图标
                                alert4.setTitle("未知联网错误");//设置对话框的标题
                                alert4.setCancelable(false);
                                alert4.setMessage(e + "\n\n请联系后台客服处理");//设置要显示的内容
                                //添加确定按钮
                                alert4.setButton(DialogInterface.BUTTON_POSITIVE, "确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
//                                //结束所有Activity，返回登录页
                                    }
                                });
                                alert4.show();// 创建对话框并显示
                            }
                        };
                        uploadGoodsPictureHT_net_judge.sendEmptyMessage(1);
                        Looper.loop();
                    }
                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    if (!isFinishing()) {
                        dialog.dismiss();//销毁加载框
                        assert response.body() != null;
                        String date = response.body().string();//获取到数据
                        jsonJXUploadGoodsPicture(date);//把数据传入解析josn数据方法
                    }
                }
            });
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }

        }
    };

    /* 解析存集合 上传商品图片 返回数据 */
    @SuppressLint("HandlerLeak")
    private void jsonJXUploadGoodsPicture(String date) {
        //{
        //	"body": "http://lbdsp.com/16040239966561mBWxp.jpg",
        //	"state": 200,
        //	"msg": "请求成功"
        //}
        if (date != null && !date.equals("")) {//判断数据是空
            try {
                Log.i("上传商品图片 有返回值", "date = " + date);
                JSONObject jsonObject = new JSONObject(date);//将字符串转换成jsonObject对象
                String body = jsonObject.getString("body");//获取返回数据中 body 的值
                Message message = new Message();
                if (body != null && !body.equals("")) {//如果返回的body不为空，则拿5个键值对访问第二个接口
                    sptpURL = body;
                    message.what = 1;//成功
                    UploadGoodsPictureHandler.sendMessage(message);
                } else {//操作失败
                    Log.e("上传商品图片 失败》》》", "UploadGoodsActivity —— /auth/uploadFile 返回date值：" + date);
//                    Looper.prepare();
//                    informationCreateHT_net_judge = new Handler() {
//                        @Override
//                        public void handleMessage(Message existence) {
//                            super.handleMessage(existence);
//                            alert4 = new AlertDialog.Builder(AuthenticationActivity.this).create();
//                            alert4.setIcon(R.drawable.mainlogo);//设置对话框的图标
//                            alert4.setTitle("上传身份证验证数据 失败");//设置对话框的标题
//                            alert4.setCancelable(false);
//                            alert4.setMessage(_MESSAGE + "\n\n请联系后台客服处理");//设置要显示的内容
//                            //添加确定按钮
//                            alert4.setButton(DialogInterface.BUTTON_POSITIVE, "确定", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
////                                //结束所有Activity，返回登录页
//                                }
//                            });
//                            alert4.show();// 创建对话框并显示
//                        }
//                    };
//                    informationCreateHT_net_judge.sendEmptyMessage(2);
//                    Looper.loop();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            Log.e("上传商品图片 无返回值", "date = " + date);
        }
    }

    /* memberHandler运行在主线程中(UI线程中)，它与子线程可以通过Message对象来传递数据 */
    @SuppressLint("HandlerLeak")
    private Handler UploadGoodsPictureHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1://上传商品图片，成功 启动第二线程上传商品5个键值对

                    //加载弹窗
                    LoadingDialog.Builder loadBuilder = new LoadingDialog.Builder(UploadGoodsActivity.this)
                            .setMessage("加载中...")
                            .setCancelable(true)//返回键是否可点击
                            .setCancelOutside(false);//窗体外是否可点击
                    dialog = loadBuilder.create();
                    dialog.show();//显示弹窗
                    new Thread(runnablePutAwayGoods).start();//点击 上传商品

                    break;
                case 2://上传商品图片 失败
//                    Toast.makeText(AuthenticationActivity.this, "验证失败，请勿重复验证", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    };

    //子线程中的Handler 上传商品5个键值对 联网判断  account/good/putAwayGoods 是否联网成功
    private Handler mPutAwayGoodsHT_net_judge;

    /* 子线程：使用POST方法向服务器发送 、、 三个参数完成 修改昵称 */
    private Runnable runnablePutAwayGoods = new Runnable() {
        @Override
        public void run() {
            String chaochangTOKEN = (String) Objects.requireNonNull(SpUtils.get(UploadGoodsActivity.this, UrlConfig.USERID, ""));
            try {
                JSONObject json = new JSONObject();
                json.put("pictureUrl", sptpURL);
                json.put("goodsName", goodsName);
                json.put("goodsAmount", goodsAmount);
                json.put("goodsPrice", goodsPrice);
                json.put("cateId", cateId);
                json.put("shopId", id);
                json.put("status", "1");
                RequestBody body = RequestBody.create(JSON, String.valueOf(json));
                Log.i("发送JSON", String.valueOf(json));
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(ServiceConfig.getRootUrl() + "/account/good/putAwayGoods")//上传商品6个键值对 端口
//                    .addHeader("_USER", uuid)//参数全部放Header里面
//                    .addHeader("Authorization", chaochangTOKEN)
                        .post(body)
                        .build();
                client.newCall(request).enqueue(new Callback() {
                    @SuppressLint("HandlerLeak")
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {//未知联网错误，全部显示
                        if (!UploadGoodsActivity.this.isFinishing()) {
                            dialog.dismiss();//销毁加载框
                            Log.e("putAwayGoods 未知联网错误", "失败 e=" + e);
                            Looper.prepare();
                            mPutAwayGoodsHT_net_judge = new Handler() {
                                @Override
                                public void handleMessage(@NonNull Message msg) {
                                    super.handleMessage(msg);
                                    alert4 = new AlertDialog.Builder(UploadGoodsActivity.this).create();
//                                    alert4.setIcon(R.mipmap.ic_launcher);//设置对话框的图标
                                    alert4.setTitle("未知联网错误");//设置对话框的标题
                                    alert4.setCancelable(false);
                                    alert4.setMessage(e + "\n\n请联系后台客服处理");//设置要显示的内容
                                    //添加确定按钮
                                    alert4.setButton(DialogInterface.BUTTON_POSITIVE, "确定", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
//                                //结束所有Activity，返回登录页
                                        }
                                    });
                                    alert4.show();// 创建对话框并显示
                                }
                            };
                            mPutAwayGoodsHT_net_judge.sendEmptyMessage(1);
                            Looper.loop();
                        }
                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        if (!UploadGoodsActivity.this.isFinishing()) {
                            dialog.dismiss();//销毁加载框
                            assert response.body() != null;
                            putAwayGoodsData = response.body().string();//获取到数据
                            try {
                                jsonPutAwayGoods(putAwayGoodsData);//把数据传入解析josn数据方法
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

    /* 解析 上传商品6个键值对 返回值，存集合 */
    @SuppressLint("HandlerLeak")
    private void jsonPutAwayGoods(String date) throws JSONException {//响应200代码后才会来到这里，但是还要再次判断"_CODE"=200的
        Message msg = new Message();//处理完成后给handler发送消息//处理完成后给handler发送消息
        if (date != null && !date.equals("")) {//判断数据是空
            Log.i("上传商品6个键值对 有返回值", "date = " + date);
            JSONObject jsonObject = new JSONObject(date);
            String state = jsonObject.optString("state", "");//获取返回数据中 records 的值
            if (state == null || state.equals("")) {
                Log.e("上传商品6个键值对失败》》》", "返回records值：" + date);
                msg.what = 2;//失败
                mPutAwayGoodsHT.sendMessage(msg);
            } else {//
                Log.w("上传商品6个键值对成功》》》", "返回records值：" + date);
                msg.what = 1;
                mPutAwayGoodsHT.sendMessage(msg);
            }
        } else {
            Log.e("上传商品6个键值对失败", "没有任何返回值");
            msg.what = 2;//失败
            mPutAwayGoodsHT.sendMessage(msg);
        }
    }

    //子线程 上传商品5个键值对 判断的Handler 返回值判断
    @SuppressLint("HandlerLeak")
    private Handler mPutAwayGoodsHT = new Handler() {
        @Override
        public void handleMessage(Message msg) {//在子线程中通知主线程，让主线程做更新操作
            switch (msg.what) {
                case 1://上传成功
//                    AlertDialog.Builder builder = new AlertDialog.Builder(UploadGoodsActivity.this);
//                    builder.setMessage("上传商品 成功")
//                            .setCancelable(false)
//                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int id) {
//                                    finish();//结束页面
//                                }
//                            });
//                    alert4 = builder.create();
//                    alert4.show();
                    alert4 = new AlertDialog.Builder(UploadGoodsActivity.this).create();
//                    alert4.setIcon(R.mipmap.ic_launcher);//设置对话框的图标
                    alert4.setTitle("上传商品 成功");//设置对话框的标题
                    alert4.setCancelable(false);
//                            alert.setMessage("");//设置要显示的内容
                    //添加确定按钮
                    alert4.setButton(DialogInterface.BUTTON_POSITIVE, "确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //发送广播，刷新店铺信息
                            finish();//结束页面
                        }
                    });
                    alert4.show();// 创建对话框并显示
                    break;
                case 2://获取失败
                    Toast toast = Toast.makeText(UploadGoodsActivity.this, "上传商品失败", Toast.LENGTH_LONG);
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

}
