package com.wwb.laobiao.video;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import VideoHandle.EpDraw;
import VideoHandle.EpEditor;
import VideoHandle.EpVideo;
import VideoHandle.OnEditorListener;

public class ShuiYing {
    private final Context context;
    public String videoUrl,savePath;
    Handler handler;
    videoupInfo videoupInfo;

    public ShuiYing(Context context){
        this.context=context;
        videoUrl="/storage/emulated/0/s.mp4";
        choseSavePath(context);
    }
    public static void copyFilesFassets(Context context, String oldPath, String newPath) {
        try {
            String fileNames[] = context.getAssets().list(oldPath);
            if (fileNames.length > 0) {
                File file = new File(newPath);
                file.mkdirs();
                for (String fileName : fileNames) {
                    copyFilesFassets(context, oldPath + "/" + fileName, newPath + "/" + fileName);
                }
            } else {
                InputStream is = context.getAssets().open(oldPath);
                File ff = new File(newPath);
                if (!ff.exists()) {
                    FileOutputStream fos = new FileOutputStream(ff);
                    byte[] buffer = new byte[1024];
                    int byteCount = 0;
                    while ((byteCount = is.read(buffer)) != -1) {
                        fos.write(buffer, 0, byteCount);
                    }
                    fos.flush();
                    is.close();
                    fos.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void run() {
        testmusic();
    }
    private void choseSavePath(Context context) {
        savePath = Environment.getExternalStorageDirectory().getPath() + "/lbdsp/";
        File file = new File(savePath);
        if (!file.exists()) {
            file.mkdirs();
        }
        File file0 = new File(savePath+"msyh.ttf");
        if (!file0.exists())
        {
            copyFilesFassets(context, "Ress", savePath);
        }
        File file1 = new File(savePath+"laobiaoshuiying0.png");
        if (!file1.exists())
        {
            copyFilesFassets(context, "images", savePath);
        }
    }
    private void testmusic(){
        final String outPath = "/storage/emulated/0/music.mp4";
        EpEditor.music(videoUrl, "/storage/emulated/0/DownLoad/huluwa.aac", outPath, 1.0f, 1.0f, new OnEditorListener() {
            @Override
            public void onSuccess() {
//                Toast.makeText(context, "编辑完成:"+outPath, Toast.LENGTH_SHORT).show();
                Intent v = new Intent(Intent.ACTION_VIEW);
                v.setDataAndType(Uri.parse(outPath), "video/mp4");
                context.startActivity(v);
            }

            @Override
            public void onFailure() {
//                Toast.makeText(context, "编辑失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onProgress(float v) {

            }
        });
    }
    /**
     * 开始编辑
     */
    public void execVideo(final Handler handler,String AccountName){
        this.handler=handler;
        if(videoUrl != null && !"".equals(videoUrl)){
            EpVideo epVideo = new EpVideo(videoUrl);//&brvbar :  //zhuanyi
            epVideo.addText(2,80,14,"white",savePath+"msyh.ttf","老表号 "+AccountName);//white 老表视频号
            {
                String filePath=savePath+"laobiaoshuiying0.png";//老表视频号
              epVideo.addDraw(new EpDraw(filePath,2,2,80,80,false,0,30));//最后两个参数为显示的起始时间和持续时间
            }
            final String outPath = "/storage/emulated/0/laobiao.mp4";//"/storage/emulated/0/s.mp4"
            EpEditor.exec(epVideo, new EpEditor.OutputOption(outPath), new OnEditorListener() {
                @Override
                public void onSuccess() {//编辑完成
                     if(handler!=null)
                     {
                         if(videoupInfo==null)
                         {
                             Message mmsg=new Message();
                             mmsg.what=201;
                             mmsg.obj=outPath;
                             handler.sendMessageDelayed(mmsg,100);
                         }
                         else
                         {
                             Message mmsg=new Message();
                             mmsg.what=202;
                             videoupInfo.path=outPath;
                             mmsg.obj=videoupInfo;
                             handler.sendMessageDelayed(mmsg,100);
                         }

                     }
                }
                @Override
                public void onFailure() {
//					Toast.makeText(EditActivity.this, "编辑失败", Toast.LENGTH_SHORT).show();
//                  mProgressDialog.dismiss();
                    if(handler!=null)
                    {
//                      Toast.makeText(EditActivity.this, "编辑完成:"+outPath, Toast.LENGTH_SHORT).show();
                        Message mmsg=new Message();
                        mmsg.what=200;
                        mmsg.obj="编辑失败";
                        handler.sendMessageDelayed(mmsg,100);
                    }
                }

                @Override
                public void onProgress(float v) {
//                    mProgressDialog.setProgress((int) (v * 100));
                    if(handler!=null)
                    {
//                      Toast.makeText(EditActivity.this, "编辑完成:"+outPath, Toast.LENGTH_SHORT).show();
                        Message mmsg=new Message();
                        mmsg.what=200;
                        mmsg.obj= String.format(" %4.2f",v);
                        handler.sendMessageDelayed(mmsg,1);
                    }
                }
            });
        }else{
//            Toast.makeText(this, "选择一个视频", Toast.LENGTH_SHORT).show();
        }
    }

    public void setvideoinfor(String content, String duration, String title) {
        videoupInfo=new videoupInfo();
        videoupInfo.content=content;
        videoupInfo.duration=duration;
        videoupInfo.title=title;
    }

    public class videoupInfo {
        public   Activity context;
        public    String path;
        public   String content;
        public   String duration;
        public    String title;
    }
}
