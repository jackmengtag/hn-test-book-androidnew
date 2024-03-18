package com.wwb.laobiao.cangye.Dialog;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.wwb.laobiao.common.Utilswwb;
import com.wwb.laobiao.hongbao.generateqrcode.ImageUtil;
import com.yangna.lbdsp.R;
import com.yangna.lbdsp.common.swiperefreshLayout.ToastUtil;

public class ShopDialogErweima {

    private  AlertDialog comdlg;
    private  ImageView imageView;
    public   Bitmap qrcode_bitmap;
    private String ErweimaUrl="http://www.lbdsp.com/code/lbdspsk.png";
    private ShopDialog.ShopDialoginterface shopDialoginterface;

    public ShopDialogErweima()
        {
///FukuanActivity
        }
    public void Oncreate(Activity context) {
        View view = LayoutInflater.from(context).inflate(
                R.layout.dialog_erweima, null);
        comdlg=new AlertDialog.Builder(context).setCancelable(false).setTitle("付款二维码")
                .setView(view).create();

        {
            Window window = comdlg.getWindow();
            //设置弹出位置
            window.setGravity(Gravity.BOTTOM);
            //设置弹出动画
            //window.setWindowAnimations(R.style.main_menu_animStyle);
            //设置对话框大小
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        Button mbok =view.findViewById(R.id.iv_ok);
        mbok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(shopDialoginterface!=null)
                {
                    shopDialoginterface.onsel(0);
                }
                comdlg.dismiss();
            }
        });
        imageView =view.findViewById(R.id.imageViewshop);
        imageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                imgChooseDialog();
                return false;
            }
        });
        if(qrcode_bitmap!=null)
        {
            Utilswwb utilswwb=new Utilswwb();
            utilswwb.setcb(new Utilswwb.Utilswwbinterface() {
                @Override
                public void setback(Bitmap bitmap) {
                    if(bitmap==null)
                    {
                        showtip("二维码加载有误");
                        if(shopDialoginterface!=null)
                        {
                            shopDialoginterface.onsel(1);
                        }
                        comdlg.dismiss();
                    }
                    else
                    {
                        qrcode_bitmap=bitmap;
                        imageView.setImageBitmap(qrcode_bitmap);
                        comdlg.show();
                    }

                }
            });
        }
        else
        {
            comdlg.show();
        }
    }

    /**
     * 长按二维码图片弹出选择框（保存或分享）
     */
    private void imgChooseDialog(){
        if(qrcode_bitmap==null)
        {
            showtip("二维码加载有误");
            return;
        }
        androidx.appcompat.app.AlertDialog.Builder choiceBuilder = new androidx.appcompat.app.AlertDialog.Builder(comdlg.getContext());
        choiceBuilder.setCancelable(false);
        choiceBuilder
                .setTitle("选择")
                .setSingleChoiceItems(new String[]{"存储至手机", "分享"}, -1,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if(qrcode_bitmap==null)
                                {
                                    showtip("二维码加载有误");
                                    dialog.dismiss();
                                    return;
                                }
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
        boolean isSaveSuccess = ImageUtil.saveImageToGallery(comdlg.getContext(), bitmap,fileName);
        if (isSaveSuccess) {
            Toast.makeText(comdlg.getContext(), "图片已保存至本地", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(comdlg.getContext(), "保存图片失败，请稍后重试", Toast.LENGTH_SHORT).show();
        }
    }
    /**
     * 分享图片(直接将bitamp转换为Uri)
     * @param bitmap
     */
    private void shareImg(Bitmap bitmap){
        Uri uri = Uri.parse(MediaStore.Images.Media.insertImage(comdlg.getContext().getContentResolver(), bitmap, null,null));
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("image/*");//设置分享内容的类型
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        intent = Intent.createChooser(intent, "分享");
        comdlg.getContext().startActivity(intent);
    }

    private void showtip(String strtip) {
        ToastUtil.showToast(strtip);
        androidx.appcompat.app.AlertDialog.Builder choiceBuilder = new androidx.appcompat.app.AlertDialog.Builder(comdlg.getContext());
        choiceBuilder.setCancelable(false);
        choiceBuilder
                .setTitle(strtip)
                .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        choiceBuilder.create();
        choiceBuilder.show();
    }

    public void setinterface(ShopDialog.ShopDialoginterface shopDialoginterface) {
        this.shopDialoginterface=shopDialoginterface;
    }

    public void Onshow() {
    }
}
