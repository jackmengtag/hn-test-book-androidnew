package com.yangna.lbdsp.home.presenter;

import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;

import androidx.appcompat.app.AlertDialog;

import com.alibaba.sdk.android.vod.upload.model.UploadFileInfo;
import com.yangna.lbdsp.R;
import com.yangna.lbdsp.common.UrlConfig;
import com.yangna.lbdsp.common.base.BasePresenter;
import com.yangna.lbdsp.common.manager.ToastManager;
import com.yangna.lbdsp.common.net.MyObserver;
import com.yangna.lbdsp.common.net.NetWorks;
import com.yangna.lbdsp.common.swiperefreshLayout.ToastUtil;
import com.yangna.lbdsp.home.bean.OldVideoBean;
import com.yangna.lbdsp.home.impl.HomeView;
import com.yangna.lbdsp.home.model.AppSettingModel;
import com.yangna.lbdsp.home.model.VideoIdModel;
import com.yangna.lbdsp.login.bean.CreateFlie;
import com.yangna.lbdsp.login.model.FileModel;

public class HomePresenter extends BasePresenter {
    private HomeView homeViewl;


    public HomePresenter(Context context) {
        super(context);
    }

    @Override
    protected void detachView() {
        homeViewl = null;
    }

    public void setHomeViewl(HomeView homeViewl) {
        this.homeViewl = homeViewl;
    }

    /**
     * 请求上传凭证
     */
    public void getUpLoadImg(final Context context, String path ,String content, String int_duration,String title) {

        CreateFlie createFlie = new CreateFlie();
        createFlie.setDescription(content);
        createFlie.setFileName(path);
        createFlie.setFileSize(int_duration);
        createFlie.setTitle("title");

        NetWorks.getInstance().getFile(context, createFlie, new MyObserver<FileModel>() {
            @Override
            public void onNext(FileModel model) {
                try {
                    if (model.getBody().getUploadAddress() == null){
                        ToastManager.showToast(context, "阿里云上传凭证失效");
                    }
                    if (UrlConfig.RESULT_OK == model.getState() && model.getBody().getUploadAddress() != null) {
                        Log.d("shangchuan", "model==" + model);
                        homeViewl.onGetUploadAddresData(model,createFlie);
                    } else {
                        ToastManager.showToast(context, model.getMsg());
                    }
                    homeViewl.onGetDataLoading(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable e) {
                ToastManager.showToast(context, e);
            }
        });
    }

    /**
     * 上传视频到本地服务器
     */
    public void getUploadVideo(final Context context, FileModel fileModel, UploadFileInfo uploadFileInfo, AlertDialog alert2) {

        OldVideoBean oldVideoBean = new OldVideoBean();
        oldVideoBean.setOriginalVideoId(fileModel.getBody().getVideoId());
        oldVideoBean.setArea("柳州市");
        oldVideoBean.setCoverUrl(uploadFileInfo.getVodInfo().getCoverUrl());
        oldVideoBean.setUploadAddress(uploadFileInfo.getFilePath());
        oldVideoBean.setDescription(uploadFileInfo.getVodInfo().getDesc());
        oldVideoBean.setFileName(uploadFileInfo.getVodInfo().getFileName());
        oldVideoBean.setFileSize(uploadFileInfo.getVodInfo().getFileSize());
        oldVideoBean.setTitle(uploadFileInfo.getVodInfo().getTitle());
        oldVideoBean.setTag("分类");
        oldVideoBean.setVideoId(fileModel.getBody().getVideoId());

        NetWorks.getInstance().getUploadVideo(context, oldVideoBean, new MyObserver<VideoIdModel>() {
            @Override
            public void onNext(VideoIdModel model) {
                try {
                    if (UrlConfig.RESULT_OK == model.getState()) {
                        homeViewl.onGetUploadVideo(model);
                        ToastUtil.showToast("上传成功");
                    } else {
//                        alert2.setIcon(R.mipmap.ic_launcher);//设置对话框的图标
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
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable e) {
                ToastManager.showToast(context, e);
            }
        });
    }

    /**
     * 获取APP版本
     */
    public void getAppVersion() {


        NetWorks.getInstance().getAppVersion(context, new MyObserver<AppSettingModel>() {
            @Override
            public void onNext(AppSettingModel model) {
                try {
                    if (UrlConfig.RESULT_OK == model.getState()) {
                        homeViewl.onGetAppVersion(model);
                    } else {
                        ToastManager.showToast(context, model.getMsg());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable e) {
                ToastManager.showToast(context, e);
            }
        });
    }


    /**
     * loading页面
     */
    public void initLoadingView() {
        homeViewl.onGetDataLoading(false);
    }

}
