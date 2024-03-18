package com.wwb.laobiao.hongbao.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.wwb.laobiao.address.EditUserAddressActivity;
import com.wwb.laobiao.address.UserAdaddressActivity;
import com.wwb.laobiao.hongbao.impl.CouponsView;
import com.wwb.laobiao.instance.UserOrder;
//import com.wwb.laobiao.video.VideoActivity;
import com.yangna.lbdsp.R;
import com.yangna.lbdsp.common.base.BasePresenterFragment;
import com.yangna.lbdsp.common.manager.UIManager;
import com.yangna.lbdsp.login.bean.User;
import com.yangna.lbdsp.wxapi.WXPayActivity;
import com.yangna.lbdsp.wxapi.WXPayEntryActivity;

import butterknife.BindView;

//import com.qzy.laobiao.R;
//import com.qzy.laobiao.common.base.BasePresenterFragment;
//import com.qzy.laobiao.common.manager.UIManager;
//import com.qzy.laobiao.hongbao.impl.CouponsView;

/**
 * A simple {@link Fragment} subclass.
 */
public class HongbaoFragment extends BasePresenterFragment<CouponsPresenter> implements CouponsView {
    @BindView(R.id.renwu)
    TextView renwu;
    @BindView(R.id.topview_id)//topview_id
    ImageView topview;
    @BindView(R.id.quyaoqing_iv)
    Button quyaoqing;

    public HongbaoFragment() {
        // Required empty public constructo  close high
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_hongbao;
    }

    @Override
    protected CouponsPresenter setPresenter() {

        return new CouponsPresenter(context);
    }
    @Override
    protected void initView() {
        super.initView();
        mPresenter.sethongbaoview(this);
        quyaoqing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UIManager.switcher(context, UserAdaddressActivity.class);
//                UIManager.switcher(context, WXPayEntryActivity.class);
            }
        });
        topview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Bundle bundle=new Bundle();
//                Intent intent=new Intent(context, WXPayEntryActivity.class);
//                int pay=0;
//                bundle.putInt("pay",pay);
//                intent.putExtras(bundle);//bundle
//                startActivityForResult(intent,0);
//                UIManager.switcher(context, RankingActivity.class);
                UserOrder.getInstance().reshall(context);
            }
        });
        renwu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//              UIManager.switcher(getContext(), LoginActivity.class);
//              ToastManager.showToast(getContext(),"红包来啦1");//logo
//                mPresenter.gethongbao(getContext());
                Bundle bundle=new Bundle();
                Intent intent=new Intent(context, WXPayEntryActivity.class);
                int pay=1;
                bundle.putInt("pay",pay);
                intent.putExtras(bundle);//bundle
                startActivityForResult(intent,1);
            }
        });
//        qiandao.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mPresenter.gethongbao(getContext());
//                ToastManager.showToast(getContext(),"抢红包");
//            }
//        });
//        lookvideo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mPresenter.gethongbao(getContext());
//                ToastManager.showToast(getContext(),"看视频");
//            }
//        });
//        cntus.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mPresenter.gethongbao(getContext());
//                ToastManager.showToast(getContext(),"看视频");
//            }
//        });
    }
}
