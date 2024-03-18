package com.yangna.lbdsp.login.view;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.content.ContextCompat;

import com.gyf.immersionbar.ImmersionBar;
import com.yangna.lbdsp.MainActivity;
import com.yangna.lbdsp.R;
import com.yangna.lbdsp.common.base.BaseApplication;
import com.yangna.lbdsp.common.base.BasePresenterActivity;
import com.yangna.lbdsp.common.ewmsm.CodeUtils;
import com.yangna.lbdsp.common.ewmsm.Intents;
import com.yangna.lbdsp.common.ewmsm.UriUtils;
import com.yangna.lbdsp.common.manager.AppLoadingManager;
import com.yangna.lbdsp.common.manager.ToastManager;
import com.yangna.lbdsp.common.manager.UIManager;
import com.yangna.lbdsp.common.utils.StatusBarUtils;
import com.yangna.lbdsp.common.utils.StringUtils;
import com.yangna.lbdsp.login.impl.LoginView;
import com.yangna.lbdsp.login.impl.UploadAddresView;
import com.yangna.lbdsp.login.model.FileModel;
import com.yangna.lbdsp.login.model.LoginModel;
import com.yangna.lbdsp.login.presenter.LoginPresenter;
import com.yangna.lbdsp.mine.view.PrivacyActivity;
import com.yangna.lbdsp.mine.view.ProtocolActivity;
import com.yangna.lbdsp.widget.ClearEditText;

import java.util.List;
import java.util.Objects;

import butterknife.ButterKnife;
import butterknife.OnClick;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

/* 登陆页 */
public class LoginActivity extends BasePresenterActivity<LoginPresenter> implements LoginView, UploadAddresView, EasyPermissions.PermissionCallbacks {
    private static final String TAG = "LoginActivity";
    private Button get_code_btn, login_btn, scan_code_btn;
    private ClearEditText phone, yzcode;
    public static final String KEY_TITLE = "key_title";
    public static final String KEY_IS_CONTINUOUS = "key_continuous_scan";
    private Class<?> cls;
    private String title;
    public static final int REQUEST_CODE_SCAN = 0X01;
    public static final int REQUEST_CODE_PHOTO = 0X02;
    public static final int RC_CAMERA = 0X01;
    public static final int RC_READ_PHOTO = 0X02;
    private boolean isContinuousScan;
    private String erweimajieguo = "";//别人给的二维码代理申请，先设为空“”
    private String getCode;
    private String city = "柳州";
    ClearEditText promotionCode;
    private AlertDialog alert3;

    /* 所要申请的权限 */
    String[] perms = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.INTERNET, Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_EXTERNAL_STORAGE,};

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private final int READ_CODE = 10;
    private int mTag = -1;

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_login;
    }

    @Override
    protected LoginPresenter setPresenter() {
        return new LoginPresenter(this);
    }

    @Override
    protected boolean enableLoading() {
        return true;
    }

    @Override
    protected void initView() {
        super.initView();
        ButterKnife.bind(this);
        BaseApplication.getInstance().CheckDeviceToken();
        ImmersionBar.with(this).init();//加了这一行后，登录页面导航栏变透明，不会显示白色
        mPresenter.setLoginView(this);
        mPresenter.setUploadAddresView(this);
        StatusBarUtils.setTranslucentStatus(context);
        //定位
        if (!EasyPermissions.hasPermissions(this, perms)) {
            EasyPermissions.requestPermissions(this, "必要的权限", 0, perms);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            System.out.println("SD卡写入权限ok");
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }

        promotionCode = findViewById(R.id.promotionCode);
        phone = findViewById(R.id.iphnone);
        yzcode = findViewById(R.id.yzcode);
        get_code_btn = findViewById(R.id.get_code_btn);
        login_btn = findViewById(R.id.login_btn);
        scan_code_btn = findViewById(R.id.scan_code_btn);
//        imageView = findViewById(R.id.home_imag);
//        imageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Map<String, Object> map = new HashMap<>();
//                map.put("city", city);
//                UIManager.switcher(LoginActivity.this, map, MainActivity.class);
//                finish();
////                goPhotoAlbum();
//            }
//        });
        /* 扫描邀请码 */
        scan_code_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert3 = new AlertDialog.Builder(LoginActivity.this).create();
//                alert3.setIcon(R.mipmap.ic_launcher);//设置对话框的图标
                alert3.setTitle("扫描添加邀请码");//设置对话框的标题
                alert3.setCancelable(true);//点击外部按钮和区域能取消
//                alert3.setMessage("扫描添加邀请码");//设置要显示的内容
                //添加中立按钮
                alert3.setButton(DialogInterface.BUTTON_NEUTRAL, "取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                            Toast.makeText(MyOfflineMemberActivity.this, "你点了中立按钮", Toast.LENGTH_SHORT).show();
                    }
                });
                //添加取消按钮
                alert3.setButton(DialogInterface.BUTTON_NEGATIVE, "相机扫描", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        cls = LoginCustomActivity.class;
                        title = "相机扫码";
                        checkCameraPermissions();
                    }
                });
                //添加确定按钮
                alert3.setButton(DialogInterface.BUTTON_POSITIVE, "图库识别", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        cls = LoginCustomActivity.class;
                        title = "图片扫码";
                        checkExternalStoragePermissions();
                    }
                });
                alert3.show();// 创建对话框并显示
            }
        });
        /* 获取短信验证码 */
        get_code_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (StringUtils.isEmpty(Objects.requireNonNull(phone.getText()).toString().trim()) || phone.getText().toString().trim().length() != 11) {
                    ToastManager.showToast(context, "请输入正确的手机号码");
                    return;
                }
//                CountDownButton btn = new CountDownButton(60000, 1000, LoginActivity.this, get_code_btn, 1);
//                btn.start();
                mPresenter.sendCode(context, phone.getText().toString().trim(), get_code_btn);
            }
        });
        /* 登录 */
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (StringUtils.isEmpty(Objects.requireNonNull(phone.getText()).toString().trim()) || phone.getText().toString().trim().length() != 11) {
                    ToastManager.showToast(context, "请输入正确的手机号码");
                    return;
                }
                if (StringUtils.isEmpty(Objects.requireNonNull(yzcode.getText()).toString())) {
                    ToastManager.showToast(context, "请输入验证码");
                    return;
                } else {
//                    UIManager.switcher(LoginActivity.this, VideoListActivity.class);//ClearEditText
                    String codestr = "";
                    if (promotionCode != null) {
                        codestr = promotionCode.getText().toString();
                    }
                    mPresenter.initLoadingView();
                    mPresenter.goLogin(context, city, phone.getText().toString().trim(), yzcode.getText().toString().trim(), codestr);
                }
            }
        });
        if (getIntent() != null) {
            mTag = getIntent().getIntExtra("tag", -1);
        }
    }

    /* 检测拍摄权限 */
    @AfterPermissionGranted(RC_CAMERA)
    private void checkCameraPermissions() {
        String[] perms = {Manifest.permission.CAMERA};
        if (EasyPermissions.hasPermissions(this, perms)) {//有权限
            startScan(cls, title);
        } else {
            EasyPermissions.requestPermissions(this, "App扫码需要用到拍摄权限", RC_CAMERA, perms);
        }
    }

    /**
     * 扫码
     *
     * @param cls
     * @param title
     */
    private void startScan(Class<?> cls, String title) {
        ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeCustomAnimation(this, R.anim.in, R.anim.out);
        Intent intent = new Intent(this, cls);
        intent.putExtra(KEY_TITLE, title);
        intent.putExtra(KEY_IS_CONTINUOUS, isContinuousScan);
        ActivityCompat.startActivityForResult(this, intent, REQUEST_CODE_SCAN, optionsCompat.toBundle());
    }

    /* 检测图片访问权限 */
    @AfterPermissionGranted(RC_READ_PHOTO)
    private void checkExternalStoragePermissions() {
        String[] perms = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(this, perms)) {//有权限
            startPhotoCode();
        } else {
            EasyPermissions.requestPermissions(this, "App需要用到读写权限", RC_READ_PHOTO, perms);
        }
    }

    private void startPhotoCode() {
        Intent pickIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(pickIntent, REQUEST_CODE_PHOTO);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            switch (requestCode) {
                case REQUEST_CODE_SCAN:
                    String result = data.getStringExtra(Intents.Scan.RESULT);
                    erweimajieguo = result;
                    promotionCode.setText("" + result);//不能加中文
                    break;
                case REQUEST_CODE_PHOTO:
                    parsePhoto(data);
                    break;
            }
        }
    }

    private void parsePhoto(Intent data) {
        final String path = UriUtils.getImagePath(this, data);
        Log.d("本地扫描二维码", "路径：" + path);
        if (TextUtils.isEmpty(path)) {
            return;
        }
        //异步解析
        asyncThread(new Runnable() {
            @Override
            public void run() {
                final String result = CodeUtils.parseCode(path);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("异步解析", "本地扫描二维码：" + result);
                        erweimajieguo = result;
                        promotionCode.setText("" + result);//不能加中文
                    }
                });

            }
        });
    }

    private void asyncThread(Runnable runnable) {
        new Thread(runnable).start();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    // 加载中
    @Override
    public void onGetDataLoading(boolean isSuccess) {
        if (isSuccess) {
            Log.i(TAG, "加载状态" + isSuccess);
            appLoadingManager.networkSuccess();
        } else {
            appLoadingManager.initLoadingView();
        }
    }


    //请求失败
    @Override
    public void onNetLoadingFail() {
        appLoadingManager.networkFail(new AppLoadingManager.I0nClickListener() {
            @Override
            public void onConfirm() {

            }
        });
    }

    @Override/* 登陆成功返回值 */
    public void onGetLoginData(LoginModel model) {
        BaseApplication.getInstance().setUserId(model.getBody());
//        mPresenter.getUserInfo(context,2);
        UIManager.switcher(context, MainActivity.class);
        ToastManager.showToast(context, "登录成功");
        Intent data = new Intent();
        data.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);//清空所有界面，重新生成，更新最新版用户数据
        data.putExtra("tag", mTag);
        setResult(Activity.RESULT_OK, data);
        finish();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    /*用户协议*/
    @OnClick(R.id.oil_register_agreement_tv)
    public void doYHXY(View view) {
        Intent intent = new Intent(this, ProtocolActivity.class);
        startActivity(intent);
    }

    /*隐私政策*/
    @OnClick(R.id.oil_register_yinsi_tv)
    public void doYSZC(View view) {
        Intent intent = new Intent(this, PrivacyActivity.class);
        startActivity(intent);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("登录页面已销毁", "LoginActivity onDestroy()");
    }

    @Override
    public void onGetUploadAddresData(FileModel fileModel) {

    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {

    }
}

