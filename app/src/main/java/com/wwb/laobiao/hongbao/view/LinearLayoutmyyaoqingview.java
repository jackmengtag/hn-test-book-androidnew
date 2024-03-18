package com.wwb.laobiao.hongbao.view;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;

import com.wwb.laobiao.hongbao.adapter.Yaoqing;
import com.wwb.laobiao.hongbao.generateqrcode.ImageUtil;
import com.yangna.lbdsp.R;

//import com.qzy.laobiao.R;
//import com.qzy.laobiao.hongbao.adapter.Yaoqing;
//import com.qzy.laobiao.hongbao.generateqrcode.ImageUtil;

public class LinearLayoutmyyaoqingview extends LinearLayout {
    private LayoutInflater inflater;
    private TextView textView;
    private Context context;
    private String str7="<font color=\"#FAF7F7\">这些小而美的景点虽然不大，但是花个两天，看看山，听听水，休闲度过两天时光，好好收拾心情面对下一周的工作还是非常合适惬意的！\\n\" +\n" +
            "            \"上一期节目，我们介绍了崇左大新骆越田园！一说到大新，可能一下能想到的是闻名天下的德天瀑布，是意境悠远的名仕田园！</font>" +
            "<font color=\"#F3CC21\">#广西旅游#崇左崇左崇左崇左崇左崇左</font>";
    Bitmap qrcode_bitmap;//生成的二维码  button_tip_iv
    private ImageView iv_qrcode;
//    public String erweiurl="https://www.baidu.com/";

    public LinearLayoutmyyaoqingview(Context context) {
        super(context);
        init(context,null);
        this.context=context;
    }
    public LinearLayoutmyyaoqingview(Context context, @Nullable AttributeSet attrs) {
        super(context,attrs);
        init(context,attrs);
    }
    private void init(Context context, AttributeSet attrs) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.layout_myyaoqing_item, this);
        iv_qrcode=findViewById(R.id.iv_qrcode);
//        generateQrcodeAndDisplay("https://www.baidu.com/");//"https://www.baidu.com/"
        textView=findViewById(R.id.textView_iv);
        //textView.setText(Html.fromHtml(str7));
        iv_qrcode.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                imgChooseDialog();
                return true;
            }
        });
    }
    /**
     * 长按二维码图片弹出选择框（保存或分享）
     */
    private void imgChooseDialog(){
        AlertDialog.Builder choiceBuilder = new AlertDialog.Builder(getContext());
        choiceBuilder.setCancelable(false);
        choiceBuilder
                .setTitle("选择")
                .setSingleChoiceItems(new String[]{"存储至手机", "分享"}, -1,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which) {
                                    case 0://存储
                                        saveImg(qrcode_bitmap);
                                        break;
                                    case 1:// 分享
                                        shareImg(qrcode_bitmap);
                                        break;
                                    default:
                                        break;
                                }
                                dialog.dismiss();
                            }
                        })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        choiceBuilder.create();
        choiceBuilder.show();
    }
    /**
     * 保存图片至本地
     * @param bitmap
     */
    private void saveImg(Bitmap bitmap){
        String fileName = "qr_"+System.currentTimeMillis() + ".jpg";
        boolean isSaveSuccess = ImageUtil.saveImageToGallery(getContext(), bitmap,fileName);
        if (isSaveSuccess) {
            Toast.makeText(getContext(), "图片已保存至本地", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getContext(), "保存图片失败，请稍后重试", Toast.LENGTH_SHORT).show();
        }
    }
    /**
     * 分享图片(直接将bitamp转换为Uri)
     * @param bitmap
     */
    private void shareImg(Bitmap bitmap){
        Uri uri = Uri.parse(MediaStore.Images.Media.insertImage(getContext().getContentResolver(), bitmap, null,null));
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("image/*");//设置分享内容的类型
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        intent = Intent.createChooser(intent, "分享");
        getContext().startActivity(intent);
    }
    public LinearLayoutmyyaoqingview(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public LinearLayoutmyyaoqingview(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context,attrs);
    }
    public void setyaoqing(Yaoqing yaoqing) {
        textView.setText(yaoqing.name);
        if(yaoqing.bmp!=null)
        {
            iv_qrcode.setImageBitmap(yaoqing.bmp);
        }
        qrcode_bitmap=yaoqing.bmp;
//       imgChooseDialog();
    }
}
