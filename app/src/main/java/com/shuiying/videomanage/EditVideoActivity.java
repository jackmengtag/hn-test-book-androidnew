package com.shuiying.videomanage;

import androidx.activity.OnBackPressedDispatcherOwner;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.shuiying.videomanage.mediacodec.VideoClipper;
import com.shuiying.videomanage.ui.MagicFilterType;
import com.shuiying.videomanage.view.BaseImageView;
import com.shuiying.videomanage.view.SyStickerView;
import com.shuiying.videomanage.view.VideoPreviewView;
import com.yangna.lbdsp.MainActivity;
import com.yangna.lbdsp.R;

import java.io.IOException;
import java.util.ArrayList;

import static com.shuiying.videomanage.utils.StaticFinalValues.STORAGE_TEMP_VIDEO_PATH;

public class EditVideoActivity extends AppCompatActivity {
    private String mInputVideoPath="/storage/emulated/0/s.mp4";
    private String mOutVideoPath="/storage/emulated/0/out.mp4";
    private FrameLayout content_root;
    private VideoPreviewView mVideoView;
    private String path="/storage/emulated/0/VideoRecorder/s.mp4"; String content=""; String duration="";String title="";
    private ArrayList<BaseImageView> mViews=new ArrayList<>();
    private AlertDialog alertDialog;
    private Handler mhandler=new Handler(){
        public void handleMessage(Message msg) {
            {
                switch(msg.what)
                {
                    case 0://mlay
                    {
                        if(msg.obj instanceof String)
                        {
                            Toast.makeText(EditVideoActivity.this,(String)msg.obj, Toast.LENGTH_SHORT).show();
                        }

//                        if(alertDialog.isShowing())
//                        {
//                            alertDialog.setTitle("上传就绪 ...");//设置对话框的标题
//                        }
                        Log.e(TAG, mInputVideoPath);
                    }
                    break;
                    case 1://mlay
                    {
//                        Toast.makeText(EditVideoActivity.this,"上传", Toast.LENGTH_SHORT).show();
//                        if(alertDialog.isShowing())
//                        {
//                            alertDialog.setTitle("上传就绪 ...");//设置对话框的标题
////                            alertDialog.dismiss();
//                        }
                        Log.e(TAG, mInputVideoPath);
                        Intent intent = new Intent();
                        Bundle bundle = new Bundle();
                        path=mOutVideoPath;
                        bundle.putString("path",path);
                        bundle.putString("content",content);
                        bundle.putString("duration",duration);
                        bundle.putString("title",title);

                        intent.putExtras(bundle);
                        setResult(Activity.RESULT_OK, intent);

                        onBackPressed();
                    }
                    break;
                    case 2://mlay
                    {

//                        alertDialog = new AlertDialog.Builder(EditVideoActivity.this).create();
//                        alertDialog.setIcon(R.mipmap.ic_launcher);//设置对话框的图标
//                        alertDialog.setTitle("上传就绪");//设置对话框的标题
//                        alertDialog.setCancelable(false);
//                        //添加确定按钮
//                        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "取消", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                EditVideoActivity.this.finish();
//                            }
//                        });
//                        alertDialog.show();// 创建对话框并显示

                        Message message = new Message();
                        message.what = 3;
                        mhandler.sendMessageDelayed(message,100);

                    }
                    break;
                    case 3://mlay
                    {
//                        Toast.makeText(EditVideoActivity.this,"covideo", Toast.LENGTH_SHORT).show();
                        covideo();
                    }
                    break;
                    case 4://mlay
                    {
                        Toast.makeText(EditVideoActivity.this,"失败", Toast.LENGTH_SHORT).show();
                    }
                    break;

                }
            }
        };
    };
//    private SyStickerView syStickerView;
private static final String TAG = "EditVideoActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApplication.mContext= this;
        Log.e(TAG, "onCreate: ");
        //   Intent intent = getIntent();

        setContentView(R.layout.activity_edit_video);
        DisplayMetrics mDisplayMetrics = getApplicationContext().getResources()
                .getDisplayMetrics();
        MyApplication.screenHeight=mDisplayMetrics.heightPixels;
        MyApplication.screenWidth=mDisplayMetrics.widthPixels;

        content_root=findViewById(R.id.view_root);
        {
//          mVideoView=new VideoPreviewView(this);
            mVideoView=findViewById(R.id.videoPreviewView);
//            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
//            content_root.addView(mVideoView,lp);
        }

        {
            SyStickerView syStickerView=new SyStickerView(this);
            syStickerView.setScreen(100,100);
            syStickerView.setImageResource(R.drawable.laobiao);
            syStickerView.setGifId(R.raw.aini);
            content_root.addView(syStickerView);
            mViews.add(syStickerView);
        }
        String mVideoPath=mInputVideoPath;
        ArrayList<String> srcList = new ArrayList<>();
        srcList.add(mVideoPath);
        mVideoView.setVideoPath(srcList);

        Activitytransfer();

    }
// OnBackPressedDispatcherOwner
@Override
public void onBackPressed() {

    super.onBackPressed();
}
    private void Activitytransfer() {
        Intent intent = getIntent();
//        Bundle bundleExtra = intent.getBundleExtra("bundle");
        Bundle bundle= intent.getExtras();
        if(bundle!=null)
        {
            title = (String) bundle.getString("title");
            content = (String) bundle.getString("content");
            path = (String) bundle.getString("path");
            duration = (String) bundle.getString("duration");
            mInputVideoPath=path;
//            mOutVideoPath=path;
            Log.e(TAG, mInputVideoPath);
            Message message = new Message();
            message.what = 2;
            mhandler.sendMessageDelayed(message,2000);
        }

    }

    private void covideo() {
        {
            String mVideoPath=mInputVideoPath;///storage/emulated/0/ych/123.mp4
            VideoClipper clipper = new VideoClipper();
            clipper.setInputVideoPath(mVideoPath);
//        String outputPath = STORAGE_TEMP_VIDEO_PATH;
            clipper.setFilterType(MagicFilterType.NONE);
//             clipper.setFilterType(MagicFilterType.HUDSON);
            clipper.setOutputVideoPath(mOutVideoPath);
            clipper.setOnVideoCutFinishListener(new VideoClipper.OnVideoCutFinishListener() {
                @Override
                public void onFinish() {
//                       VideoViewPlayerActivity.launch(VideoEditActivity.this,outputPath);
//                       VideoPlayerActivity2.launch(VideoEditActivity.this,outputPath);
//                       mHandler.sendEmptyMessage(VIDEO_CUT_FINISH);
//                       Toast.makeText(Main3Activity.this,"onFinish",Toast.LENGTH_SHORT).show();
                    Message message = new Message();
                    message.what = 1;
//                       message.obj = percent;
//                       mhandler.sendMessage(message);
                    mhandler.sendMessageDelayed(message,1000);
                }
                @Override
                public void onProgress(float percent) {
//                       Message message = new Message();
//                       message.what = CLIP_VIDEO_PERCENT;
//                       message.obj = percent;
//                       mHandler.sendMessage(message);
                    Message message = new Message();
                    message.what = 0;
                    message.obj = percent;
                    mhandler.sendMessage(message);
                }
            });
            try {
                clipper.clipVideo(0,mVideoView.getVideoDuration()*1000,mViews,getResources());
            } catch (Exception e) {
                e.printStackTrace();
            }
//        try {
//            clipper.clipVideo(0, mVideoView.getVideoDuration() * 1000, mViews, getResources());
////                    progressDialog = new PopupManager(mContext).showLoading();
////                   mPopVideoLoadingFl.setVisibility(View.VISIBLE);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        }
    }
}