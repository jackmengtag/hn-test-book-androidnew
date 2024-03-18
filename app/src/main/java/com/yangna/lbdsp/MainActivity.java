package com.yangna.lbdsp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationManager;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.alibaba.sdk.android.vod.upload.VODUploadCallback;
import com.alibaba.sdk.android.vod.upload.VODUploadClient;
import com.alibaba.sdk.android.vod.upload.VODUploadClientImpl;
import com.alibaba.sdk.android.vod.upload.model.UploadFileInfo;
import com.alibaba.sdk.android.vod.upload.model.VodInfo;
import com.gyf.immersionbar.ImmersionBar;
import com.shuiying.videomanage.EditVideoActivity;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.wwb.laobiao.MyObservable.Teacher;
import com.wwb.laobiao.cangye.model.Controller;
import com.wwb.laobiao.cangye.view.AgreeFragment;
import com.wwb.laobiao.cangye.view.DazhongFragment;
import com.wwb.laobiao.cangye.view.InstructionsFragment;
import com.wwb.laobiao.hongbao.view.HongbaoFragment;
import com.wwb.laobiao.video.ShuiYing;
import com.yangna.lbdsp.common.UrlConfig;
import com.yangna.lbdsp.common.base.BaseApplication;
import com.yangna.lbdsp.common.base.BasePresenterFragActivity;
import com.yangna.lbdsp.common.manager.ToastManager;
import com.yangna.lbdsp.common.manager.UIManager;
import com.yangna.lbdsp.common.utils.PermissionCallback;
import com.yangna.lbdsp.common.utils.PermissionUtils;
import com.yangna.lbdsp.common.utils.SpUtils;
import com.yangna.lbdsp.common.utils.UriTool;
import com.yangna.lbdsp.home.impl.HomeView;
import com.yangna.lbdsp.home.model.AppSettingModel;
import com.yangna.lbdsp.home.model.VideoIdModel;
import com.yangna.lbdsp.home.presenter.HomePresenter;
import com.yangna.lbdsp.home.view.HomeFragment;
import com.yangna.lbdsp.home.view.MallFragment;
import com.yangna.lbdsp.home.view.VideoRecordActivity;
import com.yangna.lbdsp.login.bean.CreateFlie;
import com.yangna.lbdsp.login.model.FileModel;
import com.yangna.lbdsp.login.view.LoginActivity;
import com.yangna.lbdsp.mine.view.MineFragment;
import com.yangna.lbdsp.msg.view.MessageFragment;
import com.yangna.lbdsp.videoCom.DataCreate;
import com.yangna.lbdsp.widget.AppUpdateProgressDialog;
import com.yangna.lbdsp.widget.DownloadService;
import com.yangna.lbdsp.widget.FragmentIndicator;
import com.yangna.lbdsp.widget.MenuDialog;
import com.yanzhenjie.permission.AndPermission;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Observable;
import java.util.Observer;

import butterknife.BindView;
import io.reactivex.functions.Consumer;

import static com.yangna.lbdsp.home.view.VideoRecordActivity.TYPE_IMAGE;
import static com.yangna.lbdsp.home.view.VideoRecordActivity.TYPE_VIDEO;

public class MainActivity extends BasePresenterFragActivity<HomePresenter> implements HomeView, FragmentIndicator.FragmentIndicatorInterface, FragmentIndicator.OnIndicateListener, AgreeFragment.AgreeFragmentInterface {

    private static final String KEEPUPSTR = "正在上传";
    private static final String KPNOTE = "你没有输入推广码，不能升星";
    @BindView(R.id.indicator)
    FragmentIndicator indicator;
    @BindView(R.id.video)
    VideoView videoView;
    //    @BindView(R.id.camera_img)
//    ImageView cameraImg;
    int REQUEST_VIDEO = 99;
    //fragment
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    private List<Fragment> mFragments;
    private HomeFragment homeFragment;
    private HongbaoFragment hongbaoFragment;
    private MallFragment mallShopsFragment;
    private MessageFragment messageFragment;
    private AgreeFragment agreeFragment;//
    private InstructionsFragment instructionsFragment;//InstructionsFragment
    private DazhongFragment dazhongFragment;//InstructionsFragment
    private MineFragment mineFragment;
    public static Display display;

    private RxPermissions rxPermissions;
    public int mTabTag;
    private AlertDialog alert2;
    private AlertDialog alertDialog;
    private AppUpdateProgressDialog dialog = null;
    private ServiceConnection conn;
    private int mMaxProgress;//百分比
    /**
     * 跳转编辑、录制选择弹窗
     */
    private MenuDialog mMenuDialog;

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_main;
    }

    @Override
    protected HomePresenter setPresenter() {
        //回调
        return new HomePresenter(this);
    }

    @SuppressLint("NewApi")
    @Override
    protected void initView() {
        super.initView();
        //获取存储卡权限
        setRxPermissions();
        getLocation();//定位权限判断
        mMaxProgress = 100;
        rxPermissions = new RxPermissions(context);
        display = getWindowManager().getDefaultDisplay();
        new DataCreate().initData();
        mPresenter.setHomeViewl(this);
        ImmersionBar.with(this).supportActionBar(true).init();
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        mFragments = new ArrayList<>();
        homeFragment = new HomeFragment();
        showFragment(homeFragment);


        if (!BaseApplication.getInstance().isLogin()) {
            UIManager.switcher(context, LoginActivity.class);
        } else {
            mPresenter.getAppVersion();
        }

        //设置fragment切换
        indicator.setFragmentIndicatorInterface(this);
        indicator.setOnIndicateListener(this);
        indicator.setIndicator(0);


       Log.e("当前用户token", "MainActivity生成时：" + (String) SpUtils.get(getApplicationContext(), UrlConfig.USERID, ""));

    }


    @Override
    public boolean hasLogin() {
        return true;
    }


    @Override
    public void goLogin(int tag) {
//        Intent intent = new Intent();
//        intent.setClass(MainActivity.this, VideoActivity.class);
//        startActivityForResult(intent, 999);
        for (Fragment fragment : mFragments) {
            fragment.onPause();
        }
        try {
            Intent intent = new Intent();
            intent.setClass(MainActivity.this, LoginActivity.class);
            intent.putExtra("tag", tag);
            startActivityForResult(intent, 0);
            //设置切换动画，从右边进入，左边退出
            overridePendingTransition(R.anim.push_right_in, R.anim.push_left_out);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onIndicate(View v, int id) {
        Teacher.getInstance().deleteObservers();
        switch (id) {
            case 0:
                mTabTag = 0;
                if (homeFragment == null) {
                    homeFragment = new HomeFragment();
                }
                showFragment(homeFragment);
                break;
            case 1:
                if (!BaseApplication.getInstance().isLogin()) {
                    goLogin(1);
                    return;
                }
                mTabTag = 1;
                if (mallShopsFragment == null) {
                    mallShopsFragment = new MallFragment();
                }
                showFragment(mallShopsFragment);
                break;
            case 2:
                if (!BaseApplication.getInstance().isLogin()) {
                    goLogin(2);
                    return;
                }
                mTabTag = 2;
                if (messageFragment == null) {
                    messageFragment = new MessageFragment();
                }
                showFragment(messageFragment);
                break;
            case 3:
                if (!BaseApplication.getInstance().isLogin()) {
                    goLogin(3);
                    return;
                }
//                setTransactionToolbar(true);
                mTabTag = 3;
                if (mineFragment == null) {
                    mineFragment = new MineFragment();
                    mineFragment.setinterface(this);
                }
                showFragment(mineFragment);
                break;
            case 4:
                if (mMenuDialog == null) {
                    if (!BaseApplication.getInstance().isLogin()) {
                        goLogin(2);
                        return;
                    } else {
                        initMenuDialog();
                    }
                }
                mMenuDialog.show();
        }
    }

    /**
     * 动态显示Fragment
     *
     * @param showFragment 要增加的fragment
     */
    private void showFragment(Fragment showFragment) {
        if (showFragment == null) {
            return;
        }
        fragmentTransaction = fragmentManager.beginTransaction();
        if (!mFragments.contains(showFragment)) {
            mFragments.add(showFragment);
            fragmentTransaction.add(R.id.fragment1, showFragment);
        }
        for (Fragment fragment : mFragments) {
            if (!showFragment.equals(fragment)) {
                fragmentTransaction.hide(fragment);
                fragment.onPause();
            }
        }
        fragmentTransaction.show(showFragment);
        showFragment.onResume();
        fragmentTransaction.commit();
        //友盟
//        MobclickAgent.onEvent(this, showFragment.getClass().getSimpleName(), "menu");
    }

    /**
     * 初始化进入录制、编辑的菜单弹窗
     */
    private void initMenuDialog() {
        mMenuDialog = new MenuDialog(MainActivity.this);
        mMenuDialog.setOnMenuItemClickListener(new MenuDialog.OnMenuItemClickListener() {
            @Override
            public void onEditroClick() {
                //右
                PermissionUtils.requestPermission(MainActivity.this, new PermissionCallback() {
                    @Override
                    public void success() {
                        startActivityForResult(new Intent(MainActivity.this, VideoRecordActivity.class), REQUEST_VIDEO);
                    }
                }, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO);

            }

            @Override
            public void onRecorderClick() {
                rxPermissions
                        .requestEach(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        .subscribe(new Consumer<Permission>() {
                            @Override
                            public void accept(Permission permission) throws Exception {
                                if (permission.granted) {//同意
                                    selectVideo();
                                } else if (permission.shouldShowRequestPermissionRationale) {//拒绝未勾选不再提示
                                    ToastManager.showToast(context, "您拒接访问存储权限，同意后方可上传图片");
                                } else {//拒绝并且勾选不再提示
                                    ToastManager.showToast(context, "您拒接访问存储权限，请在设置-应用-" + getString(R.string.app_name) + "-权限中打开存储权限");
                                }
                            }
                        });
            }
        });
    }

    //激活相册操作
    private void selectVideo() {
        if (Build.BRAND.equals("Huawei")) {
            Intent intentPic = new Intent(Intent.ACTION_PICK,
                    MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intentPic, 2);
        }
        if (Build.BRAND.equals("Xiaomi")) {//是否是小米设备,是的话用到弹窗选取入口的方法去选取视频
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "video/*");
            startActivityForResult(Intent.createChooser(intent, "选择要导入的视频"), 2);
        } else {//直接跳到系统相册去选取视频
            Intent intent = new Intent();
            if (Build.VERSION.SDK_INT < 19) {
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("video/*");
            } else {
                intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("video/*");
            }
            startActivityForResult(Intent.createChooser(intent, "选择要导入的视频"), 2);
        }
    }    //入口是getLocation

    /* 定位：权限判断 */
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void getLocation() {//检查定位权限
        ArrayList<String> permissions = new ArrayList<>();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        }
        //判断
        if (permissions.size() == 0) {//有权限，直接获取定位
            getLocationLL();
        } else {//没有权限，获取定位权限
            requestPermissions(permissions.toArray(new String[permissions.size()]), 2);
            Log.d("MainActivity拿定位权限", "没有定位权限");
        }
    }

    /* 定位：获取经纬度 */
    private void getLocationLL() {
        Log.d("MainActivity拿定位权限", "获取定位权限1 - 开始");
        Location location = getLastKnownLocation();
        if (location != null) {
            //传递经纬度给网页
            String result = "{code: '0',type:'2',data: {longitude: '" + location.getLongitude() + "',latitude: '" + location.getLatitude() + "'}}";
//            wvShow.loadUrl("javascript:callback(" + result + ");");
            //日志
            String locationStr = "维度：" + location.getLatitude() + "\n" + "经度：" + location.getLongitude();
            Log.d("MainActivity拿定位权限", "经纬度：" + locationStr);
        } else {
            Toast.makeText(this, "位置信息获取失败", Toast.LENGTH_SHORT).show();
            Log.d("MainActivity拿定位权限", "获取定位权限7 - " + "位置获取失败");
        }
    }

    /* 定位：得到位置对象 */
    private Location getLastKnownLocation() {//获取地理位置管理器
        LocationManager mLocationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
        List<String> providers = mLocationManager.getProviders(true);
        Location bestLocation = null;
        for (String provider : providers) {
//            Location l = mLocationManager.getLastKnownLocation(provider);
            Location l = null;
            if (l == null) {
                continue;
            }
            if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                // Found best last known location: %s", l);
                bestLocation = l;
            }
        }
        return bestLocation;
    }

    /**
     * 定位：权限监听
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 2://定位
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d("MainActivity拿定位权限", "同意定位权限");
                    getLocationLL();
                } else {
                    Toast.makeText(this, "未同意获取定位权限", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }

    @SuppressLint("CheckResult")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (2 == requestCode) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, PublishActivity.class);
                Bundle bundle = new Bundle();
                Uri uri = Objects.requireNonNull(data).getData();
                bundle.putParcelable("uri", uri);
                intent.putExtras(bundle);//
                startActivityForResult(intent, 688);
            }
            if (requestCode == REQUEST_VIDEO) {
                String path = data.getStringExtra("path");
                String imgPath = data.getStringExtra("imagePath");
                int type = data.getIntExtra("type", -1);
                if (type == TYPE_VIDEO) {
//                    mTvResult.setText("视频地址：" + path + "缩略图地址：" + imgPath);
                    addshuiying(context, path, "录制视频上传的暂无内容", "150000", "暂时也没标题");
//                    mPresenter.getUpLoadImg(context, path, "录制视频上传的暂无内容", "150000", "暂时也没标题");
                } else if (type == TYPE_IMAGE) {
//                    mTvResult.setText("图片地址：" + imgPath);
                }
            } else if (requestCode == 688 && null != data) {
                Bundle bundle = data.getExtras();
                String title = (String) bundle.get("title");
                String content = (String) bundle.get("content");
                Uri uri = bundle.getParcelable("uri");
                if (uri != null) {
                    String path = UriTool.getFilePathByUri(context, uri);
                    Log.d("path", "path==" + path);
                    MediaMetadataRetriever mmr = new MediaMetadataRetriever();//实例化MediaMetadataRetriever对象
                    File file = new File(path);
                    mmr.setDataSource(file.getAbsolutePath());
                    Bitmap bitmap = mmr.getFrameAtTime();//获得视频第一帧的Bitmap对象
                    String duration = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);//时长(毫秒)
                    Log.d("ddd", "bitmap==" + bitmap);
                    Log.d("ddd", "duration==" + duration);
                    int int_duration = Integer.parseInt(duration);
                    if (int_duration > 30000) {
                        Toast.makeText(getApplicationContext(), "视频时长已超过30秒，请重新选择", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    addshuiying(context, path, content, duration, title);
//                    mPresenter.getUpLoadImg(context,path,content,duration,title);
                }
            } else if (0 == requestCode) {
                if (data != null) {
                    int tag = data.getIntExtra("tag", -1);
                    if (tag != -1) {
                        mTabTag = tag;
                    }
                    Teacher.getInstance().deleteObservers();
                    switch (mTabTag) {
                        case 0:
                            if (homeFragment == null) {
                                homeFragment = new HomeFragment();
                            }
                            showFragment(homeFragment);
                            mTabTag = 0;
                            break;
                        case 1:
//                            if (hongbaoFragment == null) {
//                                hongbaoFragment = new com.wwb.laobiao.hongbao.view.HongbaoFragment();
//                            }
//                            showFragment(hongbaoFragment);
                            if (mallShopsFragment == null) {
                                mallShopsFragment = new MallFragment();
                            }
                            showFragment(mallShopsFragment);
                            mTabTag = 1;
                            break;
                        case 2:
                            if (messageFragment == null) {
                                messageFragment = new MessageFragment();
                            }
                            showFragment(messageFragment);
                            mTabTag = 2;
                            break;
                        case 3:
                            if (mineFragment == null) {
                                mineFragment = new MineFragment();
                            }
                            showFragment(mineFragment);
                            mTabTag = 3;
                            break;
                    }
                }
            } else if (requestCode == 788 && null != data) {

                alert2 = new AlertDialog.Builder(MainActivity.this).create();
//                alert2.setIcon(R.mipmap.ic_launcher);//设置对话框的图标
                alert2.setCancelable(false);
                alert2.setButton(DialogInterface.BUTTON_POSITIVE, "确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alert2.setTitle("正在上传");
                alert2.show();
                Bundle bundle = data.getExtras();
                String title = bundle.getString("title");
                String content = bundle.getString("content");
                String path = bundle.getString("path");
                String duration = bundle.getString("duration");
                mPresenter.getUpLoadImg(context, path, content, duration, title);
            }
        }
    }

    private void addshuiying(Activity context, String path, String content, String duration, String title) {
        //水印
        Controller controller = new Controller();
        controller.setinterface(new Controller.Controllerinterface() {
            @Override
            public void onsel(Context context, int idxe) {
                if (idxe == 0) {
                    ShuiYing shuiYing = new ShuiYing(MainActivity.this);
                    shuiYing.videoUrl = path;
                    shuiYing.setvideoinfor(content, duration, title);
                    shuiYing.execVideo(myHandle, BaseApplication.getInstance().getAccountName());

                    alert2 = new AlertDialog.Builder(MainActivity.this).create();
//                    alert2.setIcon(R.mipmap.ic_launcher);//设置对话框的图标
                    alert2.setCancelable(false);
//                    alert2.setButton(DialogInterface.BUTTON_POSITIVE, "确定", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//
//                        }
//                    });
                    alert2.setTitle("开始视频处理");
                    alert2.show();
                } else {
                    Log.d("MainActivity", "err");
                }

            }
        });
        controller.reinit(context);

    }

    //申请存储权限
    @SuppressLint("CheckResult")
    private void setRxPermissions() {
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions
                .requestEach(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE)
                .subscribe(new Consumer<Permission>() {
                    @Override
                    public void accept(Permission permission) throws Exception {
                        if (permission.granted) {//同意

                        } else if (permission.shouldShowRequestPermissionRationale) {//拒绝未勾选不再提示

                        } else {//拒绝并且勾选不再提示

                        }
                    }
                });
    }

    @Override
    public void onGetDataLoading(boolean isSuccess) {

    }

    @Override
    public void onNetLoadingFail() {

    }

    @Override
    public void onGetUploadAddresData(FileModel fileModel, CreateFlie createFlie) {
        final VODUploadClient uploader = new VODUploadClientImpl(getApplicationContext());
        if (alert2 != null) {
            alert2.dismiss();
        }
        alert2 = new AlertDialog.Builder(MainActivity.this).create();
        VODUploadCallback callback = new VODUploadCallback() {
            @Override
            public void onUploadSucceed(UploadFileInfo info) {
                Log.e("播放视频过程", "" + "获取首页视频");
                mPresenter.getUploadVideo(context, fileModel, info, alert2);
            }

            @Override
            public void onUploadFailed(UploadFileInfo info, String code, String message) {
                Log.e("shangchuan", "Failed" + code);
            }

            @Override
            public void onUploadProgress(UploadFileInfo info, long uploadedSize, long totalSize) {
                super.onUploadProgress(info, uploadedSize, totalSize);


            }

            @Override
            public void onUploadTokenExpired() {
                //凭证5分钟过期，过期后需要重新获取
                // 重新刷新上传凭证:RefreshUploadVideo
//                String uploadAuth = aa;
//                uploader.resumeWithAuth(uploadAuth);
                Toast.makeText(MainActivity.this, "凭证过期", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onUploadRetry(String code, String message) {
                Toast.makeText(MainActivity.this, "上传开始重试", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onUploadRetryResume() {
                super.onUploadRetryResume();
            }

            @Override
            public void onUploadStarted(UploadFileInfo uploadFileInfo) {
                uploader.setUploadAuthAndAddress(uploadFileInfo, fileModel.getBody().getUploadAuth(), fileModel.getBody().getUploadAddress());
                Log.e("shangchuan", "getUploadAuth=" + fileModel.getBody().getUploadAuth());
                Log.e("shangchuan", "getUploadAddress=" + fileModel.getBody().getUploadAddress());
            }
        };
        uploader.init(callback);
        String imagPath = "https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=3448936851,681826596&fm=26&gp=0.jpg";
        VodInfo vodInfo = new VodInfo();
        vodInfo.setTitle(createFlie.getTitle());
        vodInfo.setDesc(createFlie.getDescription());
        vodInfo.setCateId(19);
        vodInfo.setCoverUrl(imagPath);
        vodInfo.setFileName("老表你好");
        vodInfo.setFileSize(createFlie.getFileSize());
        uploader.setPartSize(1 * 1024 * 1024);
        uploader.addFile(createFlie.getFileName(), vodInfo);
        Log.e("shangchuan", "path=" + createFlie.getFileName());
        vodInfo.setIsProcess(true);
        uploader.start();
    }

    @Override
    public void onGetUploadVideo(VideoIdModel videoIdModel) {
        if (videoIdModel != null) {
            if (videoIdModel.isBody()) {
//                alert2.setIcon(R.mipmap.ic_launcher);//设置对话框的图标
                alert2.setTitle("上传成功");//设置对话框的标题
                alert2.setCancelable(false);
//               alert.setMessage("");//设置要显示的内容
                //添加确定按钮
                alert2.setButton(DialogInterface.BUTTON_POSITIVE, "确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //结束页面
                    }
                });
                alert2.show();// 创建对话框并显示
                Teacher.getInstance().postMessage(videoIdModel.getMsg());

                return;
            }
        }
//        alert2.setIcon(R.mipmap.ic_launcher);//设置对话框的图标
        alert2.setTitle("上传失败");//设置对话框的标题
        alert2.setCancelable(false);
//                            alert.setMessage("");//设置要显示的内容
        //添加确定按钮
        alert2.setButton(DialogInterface.BUTTON_POSITIVE, "确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //结束页面
            }
        });
        alert2.show();// 创建对话框并显示
    }

    //版本更新
    @Override
    public void onGetAppVersion(AppSettingModel model) {
        //服务器上最新的版本
        int sVersion = Integer.parseInt(model.getBody().getVersions().replace(".", ""));
        //本地版本
        int appVersion = Integer.parseInt(BaseApplication.getInstance().AppVersion.replace(".", ""));
        Log.e("sVersion", model.getBody().getConstraint());
        Log.e("appVersion", BaseApplication.getInstance().AppVersion);
        if (model != null && model.getBody() != null) {
            try {
                //本地版本 < 服务器最低版本 修改为强制更新
                if (model.getBody().getConstraint().equals("0") && sVersion > appVersion) {
                    alertDialog = new AlertDialog.Builder(MainActivity.this).create();
//                    alertDialog.setIcon(R.mipmap.ic_launcher);//设置对话框的图标
                    alertDialog.setTitle("检测到新版本！");//设置对话框的标题
                    alertDialog.setCancelable(false);
//                            alert.setMessage("");//设置要显示的内容
                    //添加确定按钮
                    alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            downApk(model);
                        }
                    });
                    alertDialog.show();// 创建对话框并显示
                }
                //不强制升级
                else if (sVersion > appVersion && model.getBody().getConstraint().equals("1")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("提示");
                    //设置提示消息
                    builder.setMessage("检测到新版本，请问是否更新");
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //点击确定按钮之后的回调
                            downApk(model);
                        }
                    });
                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //点击取消按钮之后的回调
                        }
                    });
                    builder.setNeutralButton("忽略", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //点击中间的按钮的回调
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();// 创建对话框并显示
                }
                //版本回退,手机版本号小于服务器上的版本号，并且需要强制更新
                else if (appVersion < sVersion && model.getBody().getConstraint().equals("1")) {
                    alertDialog = new AlertDialog.Builder(MainActivity.this).create();
//                    alertDialog.setIcon(R.mipmap.ic_launcher);//设置对话框的图标
                    alertDialog.setTitle("检测到新版本！");//设置对话框的标题
                    alertDialog.setCancelable(false);
//                            alert.setMessage("");//设置要显示的内容
                    //添加确定按钮
                    alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            downApk(model);
                        }
                    });
                    alertDialog.show();// 创建对话框并显示
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void downApk(AppSettingModel model) {
        dialog = new AppUpdateProgressDialog(this);
        //正在下载时，不可关闭dialog
        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
                    Toast.makeText(MainActivity.this, "正在下载请稍后", Toast.LENGTH_SHORT).show();
                    return true;
                } else {
                    return false;
                }
            }
        });
        dialog.show();
        if (conn == null) {
            conn = new ServiceConnection() {
                @Override
                public void onServiceConnected(ComponentName name, IBinder service) {
                    DownloadService.DownloadBinder binder = (DownloadService.DownloadBinder) service;
                    DownloadService myService = binder.getService();
                    myService.downApk(model, new DownloadService.DownloadCallback() {
                        @Override
                        public void onPrepare() {

                        }

                        @Override
                        public void onProgress(int progress) {
                            //显示进度条
                            if (progress <= mMaxProgress) {//progress 下载进度  mMaxProgress//百分比
                                try {
                                    Message msg = myHandle.obtainMessage();
                                    msg.what = 100;
                                    msg.obj = progress;
                                    myHandle.sendMessage(msg);
                                    Thread.sleep(200);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                        @Override
                        public void onComplete(File file) {
                            showComplete(file);
                        }

                        @Override
                        public void onFail(String msg) {
                            Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
                        }
                    });
                }

                @Override
                public void onServiceDisconnected(ComponentName name) {

                }
            };
            Intent intent = new Intent(context, DownloadService.class);
            context.bindService(intent, conn, Service.BIND_AUTO_CREATE);
        }
    }

    Handler myHandle = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 100:
                    int progress = (int) msg.obj;
                    dialog.setProgress(progress);
                    if (100 == progress) {
                        dialog.dismiss();
                        Toast.makeText(MainActivity.this, "下载完成，跳转到安装界面", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case 200:
//                    Toast.makeText(MainActivity.this, ":"+msg.obj, Toast.LENGTH_SHORT).show();
                    if (alert2 != null) {
                        alert2.setTitle("视频处理中...");
                    }
                    break;
                case 201:
//                    Toast.makeText(MainActivity.this, "编辑完成:"+msg.obj, Toast.LENGTH_SHORT).show();
                    if (alert2 != null) {
                        alert2.setTitle("视频处理完成");
                    }
                    break;
                case 202:

                    if (msg.obj instanceof ShuiYing.videoupInfo) {
                        if (alert2 != null) {
                            alert2.setTitle("视频处理完成");
                            alert2.dismiss();
                        }

                        ShuiYing.videoupInfo videoupInfo = (ShuiYing.videoupInfo) msg.obj;
//                          Toast.makeText(MainActivity.this, "编辑完成:"+videoupInfo.path, Toast.LENGTH_SHORT).show();
                        mPresenter.getUpLoadImg(context, videoupInfo.path, videoupInfo.content, videoupInfo.duration, videoupInfo.title);
                    } else {
                        Toast.makeText(MainActivity.this, "编辑完成:", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    };

    public void showComplete(File file) {
        AndPermission.with(this)
                .install()
                .file(file)
                .start();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (indicator != null) {
            indicator.setIndicator(mTabTag);
        }
    }

    @Override
    public void onshow(int idex) {
        Teacher.getInstance().deleteObservers();
        switch (idex) {
            case 0:
                // agree
                if (!BaseApplication.getInstance().isLogin()) {
                    UIManager.switcher(context, LoginActivity.class);
                }
                if (agreeFragment == null) {
                    agreeFragment = new AgreeFragment();
                    agreeFragment.setinterface(this);
                }
                showFragment(agreeFragment);
                break;
            case 1:
                if (!BaseApplication.getInstance().isLogin()) {
                    UIManager.switcher(context, LoginActivity.class);
                }
                if (instructionsFragment == null) {
                    instructionsFragment = new InstructionsFragment();
                    instructionsFragment.setinterface(this);
                }
                showFragment(instructionsFragment);
                break;
            case 2:
                //....dazhongFragment
                if (!BaseApplication.getInstance().isLogin()) {
                    UIManager.switcher(context, LoginActivity.class);
                }
                if (dazhongFragment == null) {
                    dazhongFragment = new DazhongFragment();
                    // instructionsFragment.setinterface(this);
                } else {
                    dazhongFragment.regetusid();
                }
                showFragment(dazhongFragment);
                break;
            case 3:
                //....dazhongFragment
                if (!BaseApplication.getInstance().isLogin()) {
                    UIManager.switcher(context, LoginActivity.class);
                }
                if (messageFragment == null) {
                    messageFragment = new MessageFragment();
                    messageFragment.setinterface(this);
                }
                showFragment(messageFragment);
                break;// indicator.setIndicator(0);
            case 4:
                //....dazhongFragment
                indicator.setIndicator(0);
                mTabTag = 0;
                if (homeFragment == null) {
                    homeFragment = new HomeFragment();
                }
                showFragment(homeFragment);
                break;// indicator.setIndicator(0);
            case 5: {
                mTabTag = 0;
                if (homeFragment == null) {
                    homeFragment = new HomeFragment();
                }
                showFragment(homeFragment);
//                homeFragment.home_vp.setCurrentItem(2);
            }
            break;
            case 6: {
                mTabTag = 3;
                if (mineFragment == null) {
                    mineFragment = new MineFragment();
                    mineFragment.setinterface(this);
                }
                showFragment(mineFragment);
            }
            break;
            case 7: {

                if (mMenuDialog == null) {
                    if (!BaseApplication.getInstance().isLogin()) {
                        goLogin(2);
                        return;
                    } else {
                        initMenuDialog();
                    }
                }
                mMenuDialog.show();
                Observer Observer = new Observer() {
                    @Override
                    public void update(Observable o, Object data) {
                        if (data instanceof String) {

                            mTabTag = 0;
                            if (homeFragment == null) {
                                homeFragment = new HomeFragment();
                            }
                            showFragment(homeFragment);
//                            homeFragment.home_vp.setCurrentItem(2);
                        }
                    }
                };
                Teacher.getInstance().deleteObservers();
                Teacher.getInstance().addObserver(Observer);
            }
            break;
        }
    }
}

