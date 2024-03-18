package com.yangna.lbdsp.mall.view;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
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
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
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

/*修改商店名称和LOGO页面*/
public class ModifyStoreNamePictureActivity extends AppCompatActivity {

    @BindView(R.id.daishangchuan_shangdianlogo)//添加商店图片按钮
            ImageView shangdianlogo;
    @BindView(R.id.shagndian_mingcheng)
    EditText shangdianName;

    private String shopId = "";//上一个页面传过来的shopId 直接套用上一个页面的值
    private String records_date;//修改商店信息返回值 全文
    private String id = "";//自己商店id，已经拿到上一页传过来的
    private String icon_url = "";//获取自己商店id返回头像网址
    private String storeName = "";//待设置自己商店名
    private LoadingDialog dialog;
    private AlertDialog alert1;
    private AlertDialog alert2;
    private LQRPhotoSelectUtils sdtpPhotoSelectUtils;//商店LOGO图片
    private String sdtpfilePath = "";// 手机上图片的路径
    private File sdtp;//商店图片
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    @Override//创建时运行
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_store_name_picture);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        id = intent.getStringExtra("id");//获取上一页传过来的基本用户id
//        shopId = intent.getStringExtra("shopId");//获取上一页传过来的id

        //有时间记得写篇文章发CSDN
        shangdianlogo.setOnClickListener(new View.OnClickListener() {//正面点击
            @Override
            public void onClick(View v) {// 3、调用从图库选取图片方法
                PermissionGen.needPermission(ModifyStoreNamePictureActivity.this, LQRPhotoSelectUtils.RESULT_LOAD_IMAGE1, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE});
            }
        });

        //商店图片
        sdtpPhotoSelectUtils = new LQRPhotoSelectUtils(this, new LQRPhotoSelectUtils.PhotoSelectListener() {
            @Override
            public void onFinish(File outputFile, Uri outputUri) {
                // 4、当拍照或从图库选取图片成功后回调
                sdtpfilePath = outputFile.getAbsolutePath();
                Glide.with(ModifyStoreNamePictureActivity.this).load(outputUri).into(shangdianlogo);
            }
        }, false);//true裁剪，false不裁剪
    }

    @OnClick(R.id.modify_store_name_picture_back)//退出当前Activity
    public void doTC(View view) {
        finish();
    }

    /* 进入手机文件夹选择图片需要这个方法 */
    @PermissionSuccess(requestCode = LQRPhotoSelectUtils.RESULT_LOAD_IMAGE1)
    public void selectPhoto1() {
        sdtpPhotoSelectUtils.selectPhoto1();
    }

    /* 把用户选择好的图片显示在当前imageview中 */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == LQRPhotoSelectUtils.RESULT_LOAD_IMAGE1) {//自定义图库1
            sdtpPhotoSelectUtils.attachToActivityForResult(requestCode, resultCode, data);
        } else if (requestCode == LQRPhotoSelectUtils.RESULT_LOAD_IMAGE2) {//自定义图库2
//            fmLqrPhotoSelectUtils.attachToActivityForResult(requestCode, resultCode, data);
        } else if (requestCode == LQRPhotoSelectUtils.RESULT_LOAD_IMAGE3) {//自定义图库3
//            scLqrPhotoSelectUtils.attachToActivityForResult(requestCode, resultCode, data);
        }
    }

    /* 点击提交图片和店铺名 */
    @OnClick(R.id.tijiao_tupian_mingcheng)
    public void doXGMCHTP(View view) {//修改店铺名称和LOGO，目前后台接口、数据架构没搞好，先做这两个明显的手机端数据
        storeName = shangdianName.getText().toString();//名称
        if (storeName == null || storeName.equals("")) {
            Toast toast = Toast.makeText(this, "请输入商店名称", Toast.LENGTH_SHORT);//
            LinearLayout layout = (LinearLayout) toast.getView();
            TextView tv = (TextView) layout.getChildAt(0);
            tv.setTextSize(25);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        } else {
            if (storeName.length() > 50) {
                Toast toast = Toast.makeText(this, "商店名称不能超过50个字！", Toast.LENGTH_SHORT);//
                LinearLayout layout = (LinearLayout) toast.getView();
                TextView tv = (TextView) layout.getChildAt(0);
                tv.setTextSize(25);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            } else {
                if (sdtpfilePath == null || sdtpfilePath.equals("")) {
                    Toast toast = Toast.makeText(this, "请选择商店图片", Toast.LENGTH_SHORT);//
                    LinearLayout layout = (LinearLayout) toast.getView();
                    TextView tv = (TextView) layout.getChildAt(0);
                    tv.setTextSize(25);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                } else {
                    // 可以启动线程了
                    sdtp = new File(LQRPhotoSelectUtils.compressImage(sdtpfilePath));//压缩图片
                    //加载弹窗
                    LoadingDialog.Builder loadBuilder = new LoadingDialog.Builder(this)
                            .setMessage("加载中...")
                            .setCancelable(true)//返回键是否可点击
                            .setCancelOutside(false);//窗体外是否可点击
                    dialog = loadBuilder.create();
                    dialog.show();//显示弹窗
                    new Thread(runnableUploadShopPicture).start();//点击 上传商店图片和名称
                }
            }
        }
    }

    /* 上传商店图片 专用Handler */
    public Handler uploadShopPictureHT_net_judge;

    /* 子线程：通过POST方法向服务器实时发送TOKEN，上传上传商店图片，  */
    public Runnable runnableUploadShopPicture = new Runnable() {
        @Override
        public void run() {
            OkHttpClient client = new OkHttpClient();
            RequestBody image1 = RequestBody.create(MediaType.parse("image/jpg"), sdtp);
            RequestBody body = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("file", URLEncoder.encode(sdtp.getPath()), image1)//转码后OKHTTP3也才可以用中文 URLEncoder.encode()
                    .build();
            Request request = new Request.Builder()
                    .url(ServiceConfig.getRootUrl() + "/auth/uploadFile")//上传 上传商店图片 端口 网址命名跟本页面有冲突，直接用完整路径定位法
                    .post(body)
                    .build();
            client.newCall(request).enqueue(new Callback() {
                @SuppressLint("HandlerLeak")
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {//任何错误
                    if (!isFinishing()) {
                        dialog.dismiss();//销毁加载框
                        Log.e("未知联网错误", "ModifyStoreNamePictureActivity —— /auth/uploadFile 失败 e=" + e);
                        Looper.prepare();
                        uploadShopPictureHT_net_judge = new Handler() {
                            @Override
                            public void handleMessage(Message existence) {
                                super.handleMessage(existence);
                                alert1 = new AlertDialog.Builder(ModifyStoreNamePictureActivity.this).create();
//                                alert1.setIcon(R.mipmap.ic_launcher);//设置对话框的图标
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
                        uploadShopPictureHT_net_judge.sendEmptyMessage(1);
                        Looper.loop();
                    }
                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    if (!isFinishing()) {
                        dialog.dismiss();//销毁加载框
                        assert response.body() != null;
                        String date = response.body().string();//获取到数据
                        jsonJXUploadShopPicture(date);//把数据传入解析josn数据方法
                    }
                }
            });
        }
    };

    /* 解析存集合 上传商店图片 返回数据 */
    @SuppressLint("HandlerLeak")
    private void jsonJXUploadShopPicture(String date) {
        //{
        //	"body": "http://lbdsp.com/16040239966561mBWxp.jpg",
        //	"state": 200,
        //	"msg": "请求成功"
        //}
        if (date != null && !date.equals("")) {//判断数据是空
            try {
                Log.i("上传商店图片 有返回值", "date = " + date);
                JSONObject jsonObject = new JSONObject(date);//将字符串转换成jsonObject对象
                String body = jsonObject.getString("body");//获取返回数据中 body 的值
                Message message = new Message();
                if (body != null && !body.equals("")) {//如果返回的body不为空，则拿2个键值对访问第二个接口
                    icon_url = body;
                    message.what = 1;//成功
                    UploadShopPictureHandler.sendMessage(message);
                } else {//操作失败
                    Log.e("上传商店图片 失败》》》", "ModifyStoreNamePictureActivity —— /auth/uploadFile 返回date值：" + date);
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
            Log.e("上传商店图片 无返回值", "date = " + date);
        }
    }

    /* memberHandler运行在主线程中(UI线程中)，它与子线程可以通过Message对象来传递数据 */
    @SuppressLint("HandlerLeak")
    private Handler UploadShopPictureHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1://上传商店图片，成功 启动第二线程上传商店2个键值对
                    //加载弹窗
                    LoadingDialog.Builder loadBuilder = new LoadingDialog.Builder(ModifyStoreNamePictureActivity.this)
                            .setMessage("加载中...")
                            .setCancelable(true)//返回键是否可点击
                            .setCancelOutside(false);//窗体外是否可点击
                    dialog = loadBuilder.create();
                    dialog.show();//显示弹窗
                    new Thread(runnableEditShop).start();//点击 修改自己店铺信息
                    break;
                case 2://上传商店图片 失败
//                    Toast.makeText(AuthenticationActivity.this, "验证失败，请勿重复验证", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    };

    //子线程中的Handler 修改自己店铺信息 联网判断  /shop/shop/editShop 是否联网成功
    private Handler mEditShopHT_net_judge;

    /* 子线程：使用POST方法向服务器发送 参数完成 修改自己店铺信息 */
    private Runnable runnableEditShop = new Runnable() {
        @Override
        public void run() {
            String chaochangTOKEN = (String) Objects.requireNonNull(SpUtils.get(ModifyStoreNamePictureActivity.this, UrlConfig.USERID, ""));
            try {
                JSONObject json = new JSONObject();
                json.put("accountId", id);//上一页传过来的id
                json.put("address", "万达");//2020年11月4日15:51:36 现在先写死万达
                json.put("createTime", "0");
                json.put("enterpriseCertification", "0");
                json.put("focus", "0");
//                json.put("id", id);
                json.put("license", "0");
                json.put("mallLevel", "1");
                json.put("mapAddress", "万达");
                json.put("otherLicense", "0");
                json.put("shopLogoUrl", icon_url);
                json.put("shopName", storeName);
                json.put("status", "0");
                RequestBody body = RequestBody.create(JSON, String.valueOf(json));
                Log.i("发送JSON", String.valueOf(json));
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(ServiceConfig.getRootUrl() + "/shop/shop/editShop")//修改自己店铺信息 端口
//                    .addHeader("_USER", uuid)//参数全部放Header里面
//                    .addHeader("Authorization", chaochangTOKEN)
                        .post(body)
                        .build();
                client.newCall(request).enqueue(new Callback() {
                    @SuppressLint("HandlerLeak")
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {//未知联网错误，全部显示
                        if (!ModifyStoreNamePictureActivity.this.isFinishing()) {
                            dialog.dismiss();//销毁加载框
                            Log.e("putAwayGoods 未知联网错误", "失败 e=" + e);
                            Looper.prepare();
                            mEditShopHT_net_judge = new Handler() {
                                @Override
                                public void handleMessage(@NonNull Message msg) {
                                    super.handleMessage(msg);
                                    alert2 = new AlertDialog.Builder(ModifyStoreNamePictureActivity.this).create();
//                                    alert2.setIcon(R.mipmap.ic_launcher);//设置对话框的图标
                                    alert2.setTitle("未知联网错误");//设置对话框的标题
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
                            mEditShopHT_net_judge.sendEmptyMessage(1);
                            Looper.loop();
                        }
                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        if (!ModifyStoreNamePictureActivity.this.isFinishing()) {
                            dialog.dismiss();//销毁加载框
                            assert response.body() != null;
                            records_date = response.body().string();//获取到数据
                            try {
                                jsonEditShop(records_date);//把数据传入解析josn数据方法
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

    /* 解析 修改自己店铺信息 返回值，存集合 */
    @SuppressLint("HandlerLeak")
    private void jsonEditShop(String date) throws JSONException {//响应200代码后才会来到这里，但是还要再次判断"_CODE"=200的
        Message msg = new Message();//处理完成后给handler发送消息//处理完成后给handler发送消息
        if (date != null && !date.equals("")) {//判断数据是空
            Log.i("修改自己店铺信息 有返回值", "date = " + date);
            JSONObject jsonObject = new JSONObject(date);
            String state = jsonObject.optString("state", "");//获取返回数据中 records 的值
            if (state == null || state.equals("")) {
                Log.e("上传商店2个键值对失败》》》", "返回records值：" + date);
                msg.what = 2;//失败
                mEditShopHT.sendMessage(msg);
            } else {//
                if (state.equals("200")) {
                    Log.w("上传商店2个键值对成功》》》", "返回records值：" + date);
                    msg.what = 1;
                    mEditShopHT.sendMessage(msg);
                } else {
                    Log.e("上传商店2个键值对失败》》》", "返回records值：" + date);
                    msg.what = 2;//失败
                    mEditShopHT.sendMessage(msg);
                }
            }
        } else {
            Log.e("上传商店2个键值对失败", "没有任何返回值");
            msg.what = 2;//失败
            mEditShopHT.sendMessage(msg);
        }
    }

    //子线程 修改自己店铺信息 判断的Handler 返回值判断
    @SuppressLint("HandlerLeak")
    private Handler mEditShopHT = new Handler() {
        @Override
        public void handleMessage(Message msg) {//在子线程中通知主线程，让主线程做更新操作
            switch (msg.what) {
                case 1://获取成功
//                    parsingMenDianListJson();
                    alert2 = new AlertDialog.Builder(ModifyStoreNamePictureActivity.this).create();
//                    alert2.setIcon(R.mipmap.ic_launcher);//设置对话框的图标
                    alert2.setTitle("修改 成功");//设置对话框的标题
                    alert2.setCancelable(false);
//                            alert.setMessage("");//设置要显示的内容
                    //添加确定按钮
                    alert2.setButton(DialogInterface.BUTTON_POSITIVE, "确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //发送广播，刷新店铺信息
                            Intent intent2 = new Intent();
                            intent2.setAction("SX_DPXX");
                            ModifyStoreNamePictureActivity.this.sendBroadcast(intent2);
                            finish();//结束页面
                        }
                    });
                    alert2.show();// 创建对话框并显示
                    break;
                case 2://获取失败
                    Toast toast = Toast.makeText(ModifyStoreNamePictureActivity.this, "修改店铺信息失败", Toast.LENGTH_LONG);
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
