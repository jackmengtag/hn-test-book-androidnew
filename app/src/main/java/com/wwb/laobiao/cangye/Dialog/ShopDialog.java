package com.wwb.laobiao.cangye.Dialog;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.SimpleTarget;
import com.wwb.laobiao.address.bean.AdressModel;
import com.wwb.laobiao.address.bean.AdressUser;
import com.wwb.laobiao.address.bean.SelAdressInfor;
import com.wwb.laobiao.address.bean.SelAdressModel;
import com.wwb.laobiao.cangye.model.Controller;
import com.wwb.laobiao.common.Utilswwb;
import com.wwb.laobiao.hongbao.generateqrcode.ImageUtil;
import com.wwb.laobiao.hongbao.generateqrcode.QRCodeUtil;
import com.yangna.lbdsp.R;
import com.yangna.lbdsp.common.base.BaseApplication;
import com.yangna.lbdsp.common.manager.BannerGlideImageLoader;
import com.yangna.lbdsp.common.manager.ToastManager;
import com.yangna.lbdsp.common.net.MyObserver;
import com.yangna.lbdsp.common.net.NetWorks;
import com.yangna.lbdsp.common.swiperefreshLayout.ToastUtil;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

import java.util.ArrayList;
import java.util.Objects;

import butterknife.BindView;

public class ShopDialog {
    private static final String TITLESTR = "购买商品";
//    private  ImageView imageView;
    private String ErweimaUrl="http://www.lbdsp.com/code/lbdspsk.png";
//    private String ErweimaUrl="http://8.129.110.148/code/lbdspsk.png";
    private  Uri uribitmap;
    public AlertDialog comdlg;
    private ShopDialoginterface shopDialoginterface;
    private Activity context;
    public Bitmap qrcode_bitmap;
    private Spinner spinner1;
    ArrayList<String>  mlistmode0;
    private  Handler mhandler=new Handler(){
        public void handleMessage(Message msg) {
            {
                switch(msg.what)
                {
                    case 0:
                    {

                    }
                    break;
                }
            }
        };
    };
    private ArrayAdapter<String> myAdapter;
    private AdressDialog adressDialog;
    public AdressUser adressUser;
    TextView medit;
    TextView mtext;
    private String goodsName;
    private float fgoodsPrice;
    private String pictureUrl;
    private Banner banner;
    private ArrayList<String> banner_list = new ArrayList<String>();
//    @BindView(R.id.goods_banner)
//    Banner banner;
  public ShopDialog()
    {
    }
    @SuppressLint("CheckResult")
    public void Oncreate(Activity context) {
        adressUser=new AdressUser();
        this.context=context;

        View view = LayoutInflater.from(context).inflate(
                R.layout.dialog_shop, null);
        comdlg=new AlertDialog.Builder(context).setCancelable(false).setTitle(TITLESTR)
                .setView(view).create();
//        comdlg.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        mtext =view.findViewById(R.id.textView_shop_iv);
        Button mbedit =view.findViewById(R.id.button_edit);
        {
            banner_list.add(pictureUrl);
            banner =view.findViewById(R.id.goods_banner);
            banner.setBannerStyle(BannerConfig.RIGHT);
            banner.setImageLoader(new BannerGlideImageLoader()).setImages(banner_list).start();
            banner.stopAutoPlay();
        }
        mbedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditaddrDialog();
//                if(shopDialoginterface!=null)
//                {
//                    shopDialoginterface.onsel(2);
//                }
//                comdlg.dismiss();
            }
        });
        spinner1 = view.findViewById(R.id.Spinner_addr);
        {
            mlistmode0=new ArrayList<>();
            myAdapter = new ArrayAdapter<String>(context,
                    android.R.layout.simple_spinner_item, mlistmode0);
            spinner1.setAdapter(myAdapter);
            spinner1.setSelection(0);

        }
        {
            medit =view.findViewById(R.id.editTextnum);
            medit.setText(""+BaseApplication.getInstance().getUserLevel()+1);//BaseApplication.getInstance().SetUserLevel
//            medit.addTextChangedListener(new TextWatcher() {
//                @Override
//                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//                }
//
//                @Override
//                public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//                }
//                @Override
//                public void afterTextChanged(Editable s) {
//                    int sel=getval(medit.getText().toString());
//                    mtext.setText(String.format("%s(%4.2f元)",goodsName,sel*fgoodsPrice));
//                }
//            });
            Button addbt =view.findViewById(R.id.buttonadd);
            addbt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int sel=getval( medit.getText().toString());
                    sel++;
                    setval(sel);
                }
            });
            addbt =view.findViewById(R.id.buttonmin);
            addbt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int sel=getval( medit.getText().toString());
                    sel--;
                    if(sel<1)
                    {
                        sel=1;
                    }
                    setval(sel);
                }
            });
        }
        Button mbok =view.findViewById(R.id.iv_ok);
        mbok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onsetsel();
              if(shopDialoginterface!=null)
              {
                  shopDialoginterface.onsel(1);
              }
                comdlg.dismiss();
            }
        });
        Button mbconcel=view.findViewById(R.id.iv_cancel);
        mbconcel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(shopDialoginterface!=null)
                {
                    shopDialoginterface.onsel(0);
                }
                comdlg.dismiss();
            }
        });
//         imageView =view.findViewById(R.id.imageViewshop);
//        imageView.setImageDrawable(view.getResources().getDrawable(R.drawable.goods_shili));
        //qrcode_bitmap=generateQrcodeAndDisplay("http://39.108.150.225:8080/swagger-ui.html#/");
     //  uribitmap = Uri.parse(MediaStore.Images.Media.insertImage(context.getContentResolver(), qrcode_bitmap, null,null));
//        Glide.get(context).clearMemory();
//        RequestOptions options = new RequestOptions().placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher);//两张固定图片而已
//      options = new RequestOptions().skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE);
//        Glide.with(Objects.requireNonNull(context)).load(ErweimaUrl).apply(options).into(imageView);//为了加载网络图片的两步方法
//      options = new RequestOptions().placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher);//两张固定图片而已
//      qrcode_bitmap = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
        Utilswwb utilswwb=new Utilswwb();
        utilswwb.setcb(new Utilswwb.Utilswwbinterface() {
            @Override
            public void setback(Bitmap bitmap) {
                qrcode_bitmap=bitmap;
//                imageView.setImageBitmap(qrcode_bitmap);
            }
        });
        utilswwb.setImageURL(ErweimaUrl);
//      qrcode_bitmap = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
//        imageView.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View view) {
//               // imgChooseDialog();
//                return false;
//            }
//        });
// weifor();
    }
    private void weifor() {
        for(int i=0;i<10;i++)
        {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(comdlg.isShowing())
            {
                return;
            }
        }
    }
    private void setval(int sel) {
        medit.setText(""+sel);
        mtext.setText(String.format("%s(%4.2f元)",goodsName,sel*fgoodsPrice));
    }
    private int getval(String toString) {
        try {
            return  Integer.valueOf(toString);
        } catch (Exception e) {
            // TODO: handle exception
        }
         return 0;
    }
    private void onsetsel() {
        int sel=spinner1.getSelectedItemPosition();
        if(sel<0)
        {
            adressUser.id=-1;
            return;//amount
        }
        adressUser.amount=getval(medit.getText().toString());
        if(adressUser.amount<1)
        {
//            ToastUtil.showToast("没有选择购买");
            showtip("没有选择购买");
        }
        adressUser.setsel(sel);
    }

    private void setmap() {
        //qrcode_bitmap = ((BitmapDrawable)imageView.getDrawable()).getBitmap();  //
        mlistmode0.clear();
        for(int i=0;i<adressUser.mlist.size();i++)
        {
            mlistmode0.add(""+adressUser.mlist.get(i).getmobile()+"  "+adressUser.mlist.get(i).getaddress());
        }
        spinner1.setSelection(adressUser.msel);
        myAdapter.notifyDataSetChanged();
    }
    private void selAdress() {
//        adressUser.mlist.add(adressModel);
//        adressAdapter0.notifyDataSetChanged();//addAdress
        MyObserver<SelAdressModel> observerx= new MyObserver<SelAdressModel>() {
            @Override
            public void onNext(SelAdressModel superiorModel) {

            }
            @Override
            public void onError(Throwable e) {
                ToastManager.showToast(context, "onEr");
            }
        };
//        SelAdressInfor selAdressInfor=new SelAdressInfor();
//        selAdressInfor.setaccountId(BaseApplication.getInstance().getAccountId());
//        NetWorks.getInstance().selAdress(context,selAdressInfor,observerx);
    }
    private void EditaddrDialog(){
        adressDialog=new AdressDialog();//adressUser
        adressDialog.setadrss(adressUser);
        adressDialog.onCreate(comdlg.getContext());
        adressDialog.setinterface(new AdressDialog.AdressDialoginterface() {
            @Override
            public void onsel(int idex, int flat) {
                if(idex==0)
                {
                    selAdress();
                    adressDialog.dismiss();//  //
                }
                else  if(idex==1)
                {
                    mlistmode0.clear();
                    selAdress();
                }

            }
        });//            adressDialog.dismiss();
        adressDialog.show();
//        AdrDialog adrDialog =new AdrDialog();
//        adrDialog.onCreate(comdlg.getContext());
//        adrDialog.show();

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
            Toast.makeText(context, "图片已保存至本地", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(context, "保存图片失败，请稍后重试", Toast.LENGTH_SHORT).show();
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

    private Bitmap generateQrcodeAndDisplay(String content) {
        // content ="https://www.baidu.com/";
        String str_width = "650";
        String str_height = "650";
        int width;
        int height;
        if (str_width.length() <= 0 || str_height.length() <= 0) {
            width = 650;
            height = 650;
        } else {
            width = Integer.parseInt(str_width);
            height = Integer.parseInt(str_height);
        }

        if (content.length() <= 0) {

            return null;
        }
        String error_correction_level="";
        String margin="";
        int color_black= Color.BLACK;
        int color_white=Color.WHITE;
        Bitmap logoBitmap=null;
        Bitmap blackBitmap=null;
        return QRCodeUtil.createQRCodeBitmap(content, width, height, "UTF-8",
                error_correction_level, margin, color_black, color_white, logoBitmap, 0.2F, blackBitmap);

    }
    public void Onshow() {
//        imgChooseDialog();  //AddressActivity
        if(BaseApplication.getInstance().getAccountId()<0)
        {
            Controller Controller=new Controller();
            Controller.setinterface(new com.wwb.laobiao.cangye.model.Controller.Controllerinterface() {
                @Override
                public void onsel(Context context, int idxe) {

                    StartselAdress();
                }
            });
            Controller.reinit(context);
        }
        else
        {
            StartselAdress();
        }

    }

    private void StartselAdress() {
        MyObserver<SelAdressModel> observerx= new MyObserver<SelAdressModel>() {
            @Override
            public void onNext(SelAdressModel superiorModel) {}
            @Override
            public void onError(Throwable e) {
                ToastManager.showToast(context, "onEr");
            }
        };
//        SelAdressInfor selAdressInfor=new SelAdressInfor();
//        selAdressInfor.setaccountId(BaseApplication.getInstance().getAccountId());
//        NetWorks.getInstance().selAdress(context,selAdressInfor,observerx);
    }
    public void setinterface(ShopDialoginterface shopDialoginterface) {
        this.shopDialoginterface=shopDialoginterface;
    }

    public void setaddr(AdressUser adressUser) {
        this.adressUser=adressUser;
    }

    public void setname(String goodsName) {
        this.goodsName=goodsName;
    }

    public void setgoodsPrice(String goodsPrice) {
        try {
            fgoodsPrice=  Float.valueOf(goodsPrice);
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    public void setpictureUrl(String pictureUrl) {
        this.pictureUrl=pictureUrl;
    }

    public void onresh() {
    }
    public interface ShopDialoginterface {
        void onsel(int idex);
    }
    private void showtip(String strtip) {
        ToastUtil.showToast(strtip);
        androidx.appcompat.app.AlertDialog.Builder choiceBuilder = new androidx.appcompat.app.AlertDialog.Builder(context);
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
}
