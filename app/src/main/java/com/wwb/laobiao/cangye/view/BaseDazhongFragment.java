package com.wwb.laobiao.cangye.view;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.wwb.laobiao.MyObservable.Teacher;
import com.wwb.laobiao.cangye.adp.FensiAdapter;
import com.wwb.laobiao.cangye.bean.Fensibean;
import com.wwb.laobiao.cangye.bean.SubordinateInfor;
import com.wwb.laobiao.cangye.bean.SubordinateModel;
import com.wwb.laobiao.cangye.model.CountSubordinateModel;
import com.wwb.laobiao.hongbao.generateqrcode.QRCodeUtil;
import com.yangna.lbdsp.R;
import com.yangna.lbdsp.common.base.BaseApplication;
import com.yangna.lbdsp.common.base.BasePresenterFragment;
import com.yangna.lbdsp.common.net.MyObserver;
import com.yangna.lbdsp.common.net.NetWorks;
import com.yangna.lbdsp.common.swiperefreshLayout.ToastUtil;

import java.util.ArrayList;

import butterknife.BindView;

/**
 *
 * ??fragment
 */
public class BaseDazhongFragment extends BasePresenterFragment {
    public static final int MAXXING =20 ;
    public static final String KUPOK ="刷新完成" ;
    @BindView(R.id.imageView01)
    ImageView imageView01;
    @BindView(R.id.imageView02)
    ImageView imageView02;
    @BindView(R.id.imageView03)
    ImageView imageView03;
    @BindView(R.id.imageView04)
    ImageView imageView04;
    @BindView(R.id.imageView05)
    ImageView imageView05;
    @BindView(R.id.imageView11)
    ImageView imageView06;
    @BindView(R.id.imageView12)
    ImageView imageView07;
    @BindView(R.id.imageView13)
    ImageView imageView08;
    @BindView(R.id.imageView14)
    ImageView imageView09;
    @BindView(R.id.imageView15)
    ImageView imageView10;
	@BindView(R.id.imageView21)
    ImageView imageView11;
    @BindView(R.id.imageView22)
    ImageView imageView12;
    @BindView(R.id.imageView23)
    ImageView imageView13;
    @BindView(R.id.imageView24)
    ImageView imageView14;
    @BindView(R.id.imageView25)
    ImageView imageView15;
    @BindView(R.id.imageView31)
    ImageView imageView16;
    @BindView(R.id.imageView32)
    ImageView imageView17;
    @BindView(R.id.imageView33)
    ImageView imageView18;
    @BindView(R.id.imageView34)
    ImageView imageView19;
    @BindView(R.id.imageView35)
    ImageView imageView20;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;
    @BindView(R.id.textView_toatl)
    TextView TextView_toatl;
    @BindView(R.id.textView_tuiguang)
    TextView textView_tuiguang;

    @BindView(R.id.iv_more)
    TextView more;
    public FensiAdapter adapter;
    ArrayList<Fensibean> fruitList=new ArrayList<Fensibean>();
    public long Usid;
    public static final String HigherXingStr="刷新星级粉丝";
    private boolean bfresh=false;
    public AgreeFragment.AgreeFragmentInterface agreeFragmentInterface;
    ImageView getxingview(int idex) {
        switch (idex)
	        {
				case 0:
				return imageView01;
				case 1:
				return imageView02;
				case 2:
				return imageView03;
				case 3:
				return imageView04;
				case 4:
				return imageView05;
				case 5:
				return imageView06;
				case 6:
				return imageView07;
				case 7:
				return imageView08;
				case 8:
				return imageView09;
				case 9:
				return imageView10;
				case 10:
				return imageView11;
				case 11:
				return imageView12;
				case 12:
				return imageView13;
				case 13:
				return imageView14;
				case 14:
				return imageView15;
				case 15:
				return imageView16;
				case 16:
				return imageView17;
				case 17:
				return imageView18;
				case 18:
				return imageView19;
				case 19:
				return imageView20;
	        }
        return imageView20;
    }
   public    void setAgreeFragmentInterface(AgreeFragment.AgreeFragmentInterface agreeFragmentInterface)
    {
        this.agreeFragmentInterface=agreeFragmentInterface;
    }
    void reshfensi() {
        if(bfresh)
        {
//            ToastManager.showToast(context, "??????,???");
            bfresh=false;
            return;
        }
        getfensi();
    }
    private void getfensi() {
        if(bfresh)
        {
//            ToastManager.showToast(context, "??????,???");
            return;
        }
        if(fruitList==null)
        {
            fruitList=new ArrayList<>();
        }
        fruitList.clear();
        bfresh=true;
        MyObserver<SubordinateModel> observerx= new MyObserver<SubordinateModel>() {
            @Override
            public void onNext(SubordinateModel superiorModel) {
                if(superiorModel==null)
                {
//                    ToastManager.showToast(context, "null");//D:\
                    return;
                }
                else
                {
//                    ToastManager.showToast(context, superiorModel.getMsg());
                }
                superiorModel.show();
                fruitList.clear();
                int ncnt=0;
                for(int i=0;i<superiorModel.getnum();i++)
                {
                    Fensibean fruit = new Fensibean();
                    fruit.setname((i+1)+"星级粉丝");
                    fruit.setnum(superiorModel.getBody().get(i));
                    ncnt+=superiorModel.getBody().get(i);
                    fruitList.add(fruit);
                }
                noreshxing(ncnt);
                gettuiguangzongshu();
                bfresh=false;
            }
            @Override
            public void onError(Throwable e) {
//                ToastManager.showToast(context, "onEr"+e.getMessage());
//                lvstep=21;
//                lvmax=-1;
                noreshxing(-1);
                bfresh=false;
            }
        };
        SubordinateInfor user=new SubordinateInfor();
        user.setaccountId(BaseApplication.getInstance().getAccountId());//getSubordinate //tier  Usid
//        user.settier(lvstep);//getSubordinate //
        NetWorks.getInstance().getSubordinate(context,user,observerx);
    }

    private void gettuiguangzongshu() {
        MyObserver<CountSubordinateModel> observerx= new MyObserver<CountSubordinateModel>() {
            @Override
            public void onNext(CountSubordinateModel superiorModel) {
                int cnt=superiorModel.getbody();
                textView_tuiguang.setText(""+ cnt);//countSubordinate

            }
            @Override
            public void onError(Throwable e) {
            }
        };
        SubordinateInfor user=new SubordinateInfor();
        user.setaccountId(BaseApplication.getInstance().getAccountId());//getSubordinate //tier  Usid
        NetWorks.getInstance().countSubordinate(context,observerx);
    }
    private void noreshxing(int rcnt) {
        if(TextView_toatl!=null)
        {
            if(rcnt<0)
            {
                TextView_toatl.setText("--");
            }
            else
            {
                TextView_toatl.setText(""+rcnt);
            }

        }
        if(adapter!=null)
        {
            adapter.notifyDataSetChanged();
        }
        else
        {
            initlistview();
        }
        Teacher.getInstance().postMessage(KUPOK);
    }

    public void setinterface(AgreeFragment.AgreeFragmentInterface agreeFragmentInterface)
    {
        this.agreeFragmentInterface=agreeFragmentInterface;
    }
    private void initlistview() {
        LinearLayoutManager layout = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layout);
        adapter = new FensiAdapter(fruitList);
        recyclerView.setAdapter(adapter);
    }
    public void showtip(String strtip) {
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
    public Bitmap generateQrcodeAndDisplay(String content) {
        // content ="https://www.baidu.com/";
        if(content==null)
        {
            return null;
        }
        String str_width = "180";
        String str_height = "180";
        int width;
        int height;
        if (str_width.length() <= 0 || str_height.length() <= 0) {
            width = 180;
            height = 180;
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
}
