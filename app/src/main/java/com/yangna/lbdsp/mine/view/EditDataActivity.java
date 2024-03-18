package com.yangna.lbdsp.mine.view;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
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
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.makeramen.roundedimageview.RoundedImageView;
import com.yangna.lbdsp.R;
import com.yangna.lbdsp.common.ServiceConfig;
import com.yangna.lbdsp.common.UrlConfig;
import com.yangna.lbdsp.common.base.BaseApplication;
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
import jp.wasabeef.glide.transformations.CropCircleTransformation;
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

/* 编辑个人资料页面 */
public class EditDataActivity extends AppCompatActivity {

    @BindView(R.id.genghuan_touxiang)//头像
//            RoundedImageView touxiang;
            ImageView touxiang;
    @BindView(R.id.shoujihao)//手机号
            TextView shoujihao;
    @BindView(R.id.genghuan_mingzi)//昵称
            EditText mingzi;
    @BindView(R.id.laobiaohao)//老表号
            TextView laobiaohao;
    @BindView(R.id.genghuan_jianjie)//简介
            EditText jianjie;
    @BindView(R.id.genghuan_diqu)//地区
            EditText diqu;
//    @BindView(R.id.yaoqignma)//邀请码
//            TextView yaoqignma;
//    @BindView(R.id.tuiguangma)//推广码
//            TextView tuiguangma;

    private SharedPreferences sp = BaseApplication.getInstance().getSharedPreferences("AccountInfo", MODE_PRIVATE);//SharedPreferences 是一个轻量级的存储类，主要是保存一些小的数据，一些状态信息
    private String accountName = "";//老表视频号，仅可修改一次
    private String inviteCode = "";//邀请码
    private String promotionCode = "";//推广码
    private String nickName = "";//昵称
    private String logo = "";//头像网址
    private String des = "";//描述简介
    private String area = "";//所在地区
    private String txicon_url = "";//获取自己头像返回头像网址
    private String grtxtpfilePath = "";// 手机上图片的路径 个人头像
    private String body_data = "";//修改个人信息返回值
    private LoadingDialog dialog;
    private File txtp;//头像图片
    private LQRPhotoSelectUtils grtxtpPhotoSelectUtils;//个人头像LOGO图片
    private AlertDialog alert1;
    private AlertDialog alert2;
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editdata);
        ButterKnife.bind(this);

        //有时间记得写篇文章发CSDN
        touxiang.setOnClickListener(new View.OnClickListener() {//正面点击
            @Override
            public void onClick(View v) {// 3、调用从图库选取图片方法
                PermissionGen.needPermission(EditDataActivity.this, LQRPhotoSelectUtils.RESULT_LOAD_IMAGE1, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE});
            }
        });

        //个人头像图片
        grtxtpPhotoSelectUtils = new LQRPhotoSelectUtils(this, new LQRPhotoSelectUtils.PhotoSelectListener() {
            @Override
            public void onFinish(File outputFile, Uri outputUri) {
                // 4、当拍照或从图库选取图片成功后回调
                grtxtpfilePath = outputFile.getAbsolutePath();
                Glide.with(EditDataActivity.this)
                        .load(outputUri)
                        .error(R.mipmap.user_icon) //异常时候显示的图片
                        .placeholder(R.mipmap.user_icon) //加载成功前显示的图片
                        .fallback(R.mipmap.user_icon) //url为空的时候,显示的图片
                        .apply(RequestOptions.circleCropTransform())//加载为圆形图片
                        .into(touxiang);
            }
        }, false);//true裁剪，false不裁剪

        /* 获取用户信息 */
        //返回值 2020年11月11日13:39:18
        //{
        //	"body": {
        //		"uuid": "1b63c671-de28-4ef8-90e6-32bf24d3df5e",
        //		"account": {
        //			"id": 8,                                    自增id
        //			"accountName": "9826566431",                老表视频号，仅可修改一次
        //			"mobile": "18978065947",                    注册手机号
        //			"createTime": 1598413744678,
        //			"inviteCode": "rS4e02uW",                   邀请码
        //			"status": "0",                              0正常1冻结2删除
        //			"nickName": "dztz",                         昵称
        //			"logo": "/",                                头像
        //			"des": "666",                               描述
        //			"area": "zzz",                              所在地区
        //			"identityAuth": "S",                        身份认证 U普通用户 G官方用户 V认证用户 S商家用户 E政府机构
        //			"identityName": null,                       认证名称
        //			"promotionCode": "KTAWKEoC",                推广码
        //			"relationStatus": null,                     关系状态
        //			"userLevel": null                           用户等级
        //		},
        //		"loginTime": "2020-11-11 11:02:18",
        //		"expireTime": "2020-11-14 01:27:46",
        //		"ipaddr": "125.73.102.239",
        //		"loginLocation": "广西 柳州",
        //		"browser": "Unknown",
        //		"os": "Unknown",
        //		"platform": null
        //	},
        //	"state": 200,
        //	"msg": "请求成功"
        //}

        if (sp.getString("logo", "").equals("") || sp.getString("logo", "").equals("/") || sp.getString("logo", "") == null) {//没有图片URL
            //不刷新图片显示，加载本地图片
            touxiang.setImageResource(R.mipmap.user_icon);
        } else {
            Glide.with((this))
                    .load(sp.getString("logo", ""))
                    .error(R.mipmap.user_icon) //异常时候显示的图片
                    .placeholder(R.mipmap.user_icon) //加载成功前显示的图片
                    .fallback(R.mipmap.user_icon) //url为空的时候,显示的图片
                    .apply(RequestOptions.circleCropTransform())//加载为圆形图片
                    .into(touxiang);//为了加载网络图片的两步方法
        }
        shoujihao.setText((Objects.requireNonNull(sp.getString("mobile", "1XXXXXXXXXX"))).replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2"));//在这里显示手机号，隐藏中间4位
        mingzi.setText(sp.getString("nickName", "默认昵称"));//显示昵称
        accountName = sp.getString("accountName", "老表视频号");
        laobiaohao.setText(accountName);//显示老表视频号
        jianjie.setText(sp.getString("des", "请输入简介"));
        diqu.setText(sp.getString("area", "请输入所在地区"));
//        inviteCode = sp.getString("inviteCode", "你没有邀请码");
//        yaoqignma.setText(inviteCode);
//        promotionCode = sp.getString("promotionCode", "你没有推广码");
//        tuiguangma.setText(promotionCode);

    }

    /* 进入手机文件夹选择图片需要这个方法 */
    @PermissionSuccess(requestCode = LQRPhotoSelectUtils.RESULT_LOAD_IMAGE1)
    public void selectPhoto1() {
        grtxtpPhotoSelectUtils.selectPhoto1();
    }

    /* 把用户选择好的图片显示在当前imageview中 */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == LQRPhotoSelectUtils.RESULT_LOAD_IMAGE1) {//自定义图库1
            grtxtpPhotoSelectUtils.attachToActivityForResult(requestCode, resultCode, data);
        } else if (requestCode == LQRPhotoSelectUtils.RESULT_LOAD_IMAGE2) {//自定义图库2
//            fmLqrPhotoSelectUtils.attachToActivityForResult(requestCode, resultCode, data);
        } else if (requestCode == LQRPhotoSelectUtils.RESULT_LOAD_IMAGE3) {//自定义图库3
//            scLqrPhotoSelectUtils.attachToActivityForResult(requestCode, resultCode, data);
        }
    }

    @OnClick(R.id.editdata_back)//退出当前页面
    public void doBack(View view) {
        finish();
    }

    /* 点击复制 邀请码 */
//    @OnClick(R.id.rl_fuzhiyaoqingma)
//    void doFuZhiYQM(View view) {
//        if (inviteCode.equals("邀请码") || inviteCode.equals("") || inviteCode == null) {
//            Toast.makeText(EditDataActivity.this, "获取 邀请码 失败\n" + "请联系后台客服处理", Toast.LENGTH_SHORT).show();
//        } else {
//            Toast.makeText(EditDataActivity.this, "复制 邀请码" + inviteCode + " 成功", Toast.LENGTH_LONG).show();
//            //获取剪贴板管理器：
//            ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
//            // 创建普通字符型ClipData
//            ClipData mClipData = ClipData.newPlainText("Label", inviteCode);
//            // 将ClipData内容放到系统剪贴板里。
//            cm.setPrimaryClip(mClipData);
//        }
//    }

    /* 点击复制 推广码 */
//    @OnClick(R.id.rl_fuzhituiguangma)
//    void doFuZhiTGM(View view) {
//        if (promotionCode.equals("推广码") || promotionCode.equals("") || promotionCode == null) {
//            Toast.makeText(EditDataActivity.this, "获取 推广码 失败\n" + "请联系后台客服处理", Toast.LENGTH_SHORT).show();
//        } else {
//            Toast.makeText(EditDataActivity.this, "复制 推广码" + promotionCode + " 成功", Toast.LENGTH_LONG).show();
//            //获取剪贴板管理器：
//            ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
//            // 创建普通字符型ClipData
//            ClipData mClipData = ClipData.newPlainText("Label", promotionCode);
//            // 将ClipData内容放到系统剪贴板里。
//            cm.setPrimaryClip(mClipData);
//        }
//    }

    /* 点击复制 老表号 */
    @OnClick(R.id.rl_fuzhilaobiaohao)
    void doFuZhiLBH(View view) {
        if (accountName.equals("老表视频号") || accountName.equals("") || accountName == null) {
            Toast.makeText(EditDataActivity.this, "获取 老表视频号 失败\n" + "请联系后台客服处理", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(EditDataActivity.this, "复制 老表视频号" + accountName + " 成功", Toast.LENGTH_LONG).show();
            //获取剪贴板管理器：
            ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            // 创建普通字符型ClipData
            ClipData mClipData = ClipData.newPlainText("Label", accountName);
            // 将ClipData内容放到系统剪贴板里。
            cm.setPrimaryClip(mClipData);
        }
    }

    /* 点击提交头像图片 */
    @OnClick(R.id.btn_quedingxiugai)
    public void doXGGRXX(View view) {//修改个人信息昵称、简介、地区和头像LOGO
        nickName = mingzi.getText().toString();//获取昵称输入框
        if (nickName == null || nickName.equals("") || nickName.equals("默认昵称")) {
            Toast toast = Toast.makeText(this, "请输入昵称", Toast.LENGTH_SHORT);//
            LinearLayout layout = (LinearLayout) toast.getView();
            TextView tv = (TextView) layout.getChildAt(0);
            tv.setTextSize(25);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        } else {
            if (nickName.length() > 16) {
                Toast toast = Toast.makeText(this, "昵称不能超过16个字！", Toast.LENGTH_SHORT);//
                LinearLayout layout = (LinearLayout) toast.getView();
                TextView tv = (TextView) layout.getChildAt(0);
                tv.setTextSize(25);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            } else {
                des = jianjie.getText().toString();//获取简介描述输入框
                if (des == null || des.equals("") || des.equals("请输入简介")) {
                    Toast toast = Toast.makeText(this, "请输入简介", Toast.LENGTH_SHORT);//
                    LinearLayout layout = (LinearLayout) toast.getView();
                    TextView tv = (TextView) layout.getChildAt(0);
                    tv.setTextSize(25);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                } else {
                    if (des.length() > 200) {
                        Toast toast = Toast.makeText(this, "简介不能超过200个字！", Toast.LENGTH_SHORT);//
                        LinearLayout layout = (LinearLayout) toast.getView();
                        TextView tv = (TextView) layout.getChildAt(0);
                        tv.setTextSize(25);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    } else {
                        area = diqu.getText().toString();//获取地区城市经纬度输入框
                        if (area == null || area.equals("") || area.equals("请输入所在地区")) {
                            Toast toast = Toast.makeText(this, "请输入地区城市", Toast.LENGTH_SHORT);//
                            LinearLayout layout = (LinearLayout) toast.getView();
                            TextView tv = (TextView) layout.getChildAt(0);
                            tv.setTextSize(25);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                        } else {
                            if (area.length() > 200) {
                                Toast toast = Toast.makeText(this, "地区城市不能超过200个字！", Toast.LENGTH_SHORT);//
                                LinearLayout layout = (LinearLayout) toast.getView();
                                TextView tv = (TextView) layout.getChildAt(0);
                                tv.setTextSize(25);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();
                            } else {
                                if (grtxtpfilePath == null || grtxtpfilePath.equals("")) {//没改过图片
                                    /* 设置为当前旧头像图片URI，或者远程LOGO图片URI */
                                    txicon_url = sp.getString("logo", "https://bbs-fd.zol-img.com.cn/t_s800x5000/g6/M00/0E/0A/ChMkKV_IlcaIbfauAAB_fryqqFUAAGM_wP7FDYAAH-W889.jpg");
//                                    Toast toast = Toast.makeText(this, "请选择头像图片", Toast.LENGTH_SHORT);//
//                                    LinearLayout layout = (LinearLayout) toast.getView();
//                                    TextView tv = (TextView) layout.getChildAt(0);
//                                    tv.setTextSize(25);
//                                    toast.setGravity(Gravity.CENTER, 0, 0);
//                                    toast.show();
                                    //加载弹窗
                                    LoadingDialog.Builder loadBuilder = new LoadingDialog.Builder(this)
                                            .setMessage("加载中...")
                                            .setCancelable(true)//返回键是否可点击
                                            .setCancelOutside(false);//窗体外是否可点击
                                    dialog = loadBuilder.create();
                                    dialog.show();//显示弹窗
                                    new Thread(runnableEditGeRenXinXi).start();//点击 修改自己个人信息
                                } else {//改过图片了
                                    // 可以启动线程了
                                    txtp = new File(LQRPhotoSelectUtils.compressImage(grtxtpfilePath));//压缩图片
                                    //加载弹窗
                                    LoadingDialog.Builder loadBuilder = new LoadingDialog.Builder(this)
                                            .setMessage("加载中...")
                                            .setCancelable(true)//返回键是否可点击
                                            .setCancelOutside(false);//窗体外是否可点击
                                    dialog = loadBuilder.create();
                                    dialog.show();//显示弹窗
                                    new Thread(runnableUploadTouXiangPicture).start();//点击 上传头像图片和名称
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /* 上传头像图片 专用Handler */
    public Handler uploadTouXiangPictureHT_net_judge;

    /* 子线程：通过POST方法向服务器实时发送TOKEN，上传上传头像图片，  */
    public Runnable runnableUploadTouXiangPicture = new Runnable() {
        @Override
        public void run() {
            OkHttpClient client = new OkHttpClient();
            RequestBody image1 = RequestBody.create(MediaType.parse("image/jpg"), txtp);
            RequestBody body = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("file", URLEncoder.encode(txtp.getPath()), image1)//转码后OKHTTP3也才可以用中文 URLEncoder.encode()
                    .build();
            Request request = new Request.Builder()
                    .url(ServiceConfig.getRootUrl() + "/auth/uploadFile")//上传 上传头像图片 端口 网址命名跟本页面有冲突，直接用完整路径定位法
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
                        uploadTouXiangPictureHT_net_judge = new Handler() {
                            @Override
                            public void handleMessage(Message existence) {
                                super.handleMessage(existence);
                                alert1 = new AlertDialog.Builder(EditDataActivity.this).create();
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
                        uploadTouXiangPictureHT_net_judge.sendEmptyMessage(1);
                        Looper.loop();
                    }
                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    if (!isFinishing()) {
                        dialog.dismiss();//销毁加载框
                        assert response.body() != null;
                        String date = response.body().string();//获取到数据
                        jsonJXUploadTouXiangPicture(date);//把数据传入解析josn数据方法
                    }
                }
            });
        }
    };

    /* 解析存集合 上传头像图片 返回数据 */
    @SuppressLint("HandlerLeak")
    private void jsonJXUploadTouXiangPicture(String date) {
        //{
        //	"body": "http://lbdsp.com/16040239966561mBWxp.jpg",
        //	"state": 200,
        //	"msg": "请求成功"
        //}
        if (date != null && !date.equals("")) {//判断数据是空
            try {
                Log.i("上传头像图片 有返回值", "date = " + date);
                JSONObject jsonObject = new JSONObject(date);//将字符串转换成jsonObject对象
                String body = jsonObject.getString("body");//获取返回数据中 body 的值
                Message message = new Message();
                if (body != null && !body.equals("")) {//如果返回的body不为空，则拿2个键值对访问第二个接口
                    txicon_url = body;
                    message.what = 1;//成功
                    UploadTouXiangPictureHandler.sendMessage(message);
                } else {//操作失败
                    Log.e("上传头像图片 失败》》》", "EditDataActivity —— /auth/uploadFile 返回date值：" + date);
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
            Log.e("上传头像图片 无返回值", "date = " + date);
        }
    }

    /* memberHandler运行在主线程中(UI线程中)，它与子线程可以通过Message对象来传递数据 */
    @SuppressLint("HandlerLeak")
    private Handler UploadTouXiangPictureHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1://上传头像图片，成功 启动第二线程上传头像2个键值对
                    //加载弹窗
                    LoadingDialog.Builder loadBuilder = new LoadingDialog.Builder(EditDataActivity.this)
                            .setMessage("加载中...")
                            .setCancelable(true)//返回键是否可点击
                            .setCancelOutside(false);//窗体外是否可点击
                    dialog = loadBuilder.create();
                    dialog.show();//显示弹窗
                    new Thread(runnableEditGeRenXinXi).start();//点击 修改自己个人信息
                    break;
                case 2://上传头像图片 失败
//                    Toast.makeText(AuthenticationActivity.this, "验证失败，请勿重复验证", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    };

    //子线程中的Handler 修改自己个人信息 联网判断  /api/account/updateAccountInfo 是否联网成功
    private Handler mEditGeRenXinXiHT_net_judge;

    /* 子线程：使用POST方法向服务器发送 参数完成 修改自己个人信息 */
    private Runnable runnableEditGeRenXinXi = new Runnable() {
        @Override
        public void run() {
            String chaochangTOKEN = (String) Objects.requireNonNull(SpUtils.get(EditDataActivity.this, UrlConfig.USERID, ""));
            try {
                JSONObject json = new JSONObject();
                json.put("area", area);//地区
                json.put("des", des);//简介
                json.put("logo", txicon_url);//不一定是返回的最新头像URL
                json.put("nickName", nickName);//昵称
                RequestBody body = RequestBody.create(JSON, String.valueOf(json));
                Log.i("发送JSON", String.valueOf(json));
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(ServiceConfig.getRootUrl() + "/api/account/updateAccountInfo")//修改自己个人信息 端口
//                    .addHeader("_USER", uuid)//参数全部放Header里面
                        .addHeader("Authorization", chaochangTOKEN)//更新用户信息需要加入头参数
                        .post(body)
                        .build();
                client.newCall(request).enqueue(new Callback() {
                    @SuppressLint("HandlerLeak")
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {//未知联网错误，全部显示
                        if (!EditDataActivity.this.isFinishing()) {
                            dialog.dismiss();//销毁加载框
                            Log.e("putAwayGoods 未知联网错误", "失败 e=" + e);
                            Looper.prepare();
                            mEditGeRenXinXiHT_net_judge = new Handler() {
                                @Override
                                public void handleMessage(@NonNull Message msg) {
                                    super.handleMessage(msg);
                                    alert2 = new AlertDialog.Builder(EditDataActivity.this).create();
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
                            mEditGeRenXinXiHT_net_judge.sendEmptyMessage(1);
                            Looper.loop();
                        }
                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        if (!EditDataActivity.this.isFinishing()) {
                            dialog.dismiss();//销毁加载框
                            assert response.body() != null;
                            body_data = response.body().string();//获取到数据
                            try {
                                jsonEditGeRenXinXi(body_data);//把数据传入解析josn数据方法
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

    /* 解析 修改自己个人信息 返回值，存集合 */
    @SuppressLint("HandlerLeak")
    private void jsonEditGeRenXinXi(String date) throws JSONException {//响应200代码后才会来到这里，但是还要再次判断"_CODE"=200的
        Message msg = new Message();//处理完成后给handler发送消息//处理完成后给handler发送消息
        if (date != null && !date.equals("")) {//判断数据是空
            //{
            //  "body": {
            //    "uuid": "8e3f469d-7c47-4869-af2d-523e95ecd289",
            //    "account": {
            //      "id": 8,
            //      "accountName": "9826566431",
            //      "mobile": "18978065947",
            //      "createTime": 1598413744678,
            //      "inviteCode": "rS4e02uW",
            //      "status": "0",
            //      "nickName": "硬糖少女",
            //      "logo": "http://i0.hdslb.com/bfs/face/af0942505b84134ac683e4ae8a16edf4932cecef.jpg",
            //      "des": "六六六",
            //      "area": "兰州",
            //      "identityAuth": "S",
            //      "identityName": null,
            //      "promotionCode": "KTAWKEoC",
            //      "relationStatus": null,
            //      "userLevel": null
            //    },
            //    "loginTime": "2020-11-12 16:52:19",
            //    "expireTime": "2020-11-15 04:52:51",
            //    "ipaddr": "124.226.61.21",
            //    "loginLocation": "广西 柳州",
            //    "browser": "Unknown",
            //    "os": "Unknown",
            //    "platform": null
            //  },
            //  "state": 200,
            //  "msg": "请求成功"
            //}
            Log.i("修改自己个人信息 有返回值", "date = " + date);
            JSONObject jsonObject = new JSONObject(date);
            String state = jsonObject.optString("state", "");//获取返回数据中 state 的值
            String body = jsonObject.optString("body", "");//获取返回数据中 body 的值
            if (state == null || state.equals("")) {
                Log.e("上传头像4个键值对失败》》》", "返回records值：" + date);
                msg.what = 2;//失败
                mEditGeRenXinXiHT.sendMessage(msg);
            } else {//
                if (state.equals("200")) {
                    Log.w("上传头像4个键值对成功》》》", "返回records值：" + date);
                    JSONObject jo = new JSONObject(body);
                    String account = jo.optString("account", "");
                    JSONObject jo2 = new JSONObject(account);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("logo", jo2.optString("logo"));
                    editor.putString("nickName", jo2.optString("nickName"));
                    editor.putString("des", jo2.optString("des"));
                    editor.putString("area", jo2.optString("area"));
                    editor.apply();
                    msg.what = 1;
                    mEditGeRenXinXiHT.sendMessage(msg);
                } else {
                    Log.e("上传头像4个键值对失败》》》", "返回records值：" + date);
                    msg.what = 2;//失败
                    mEditGeRenXinXiHT.sendMessage(msg);
                }
            }
        } else {
            Log.e("上传头像4个键值对失败", "没有任何返回值");
            msg.what = 2;//失败
            mEditGeRenXinXiHT.sendMessage(msg);
        }
    }

    //子线程 修改自己个人信息 判断的Handler 返回值判断
    @SuppressLint("HandlerLeak")
    private Handler mEditGeRenXinXiHT = new Handler() {
        @Override
        public void handleMessage(Message msg) {//在子线程中通知主线程，让主线程做更新操作
            switch (msg.what) {
                case 1://获取成功
//                    parsingMenDianListJson();
                    alert2 = new AlertDialog.Builder(EditDataActivity.this).create();
//                    alert2.setIcon(R.mipmap.ic_launcher);//设置对话框的图标
                    alert2.setTitle("修改 成功");//设置对话框的标题
                    alert2.setCancelable(false);
//                            alert.setMessage("");//设置要显示的内容
                    //添加确定按钮
                    alert2.setButton(DialogInterface.BUTTON_POSITIVE, "确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            //发送广播，刷新个人信息
                            Intent intent2 = new Intent();
                            intent2.setAction("SX_GRXX");
                            EditDataActivity.this.sendBroadcast(intent2);
                            finish();//结束页面
                        }
                    });
                    alert2.show();// 创建对话框并显示
                    break;
                case 2://获取失败
                    Toast toast = Toast.makeText(EditDataActivity.this, "修改个人信息失败", Toast.LENGTH_LONG);
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
