package com.yangna.lbdsp.mine.view;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.tabs.TabLayout;
import com.makeramen.roundedimageview.RoundedImageView;
import com.wwb.laobiao.cangye.view.AgreeFragment;
import com.yangna.lbdsp.R;
import com.yangna.lbdsp.common.UrlConfig;
import com.yangna.lbdsp.common.base.BaseApplication;
import com.yangna.lbdsp.common.base.BasePresenterFragment;
import com.yangna.lbdsp.common.eventbus.BusMallActual;
import com.yangna.lbdsp.common.eventbus.BusMineVideo;
import com.yangna.lbdsp.common.manager.UIManager;
import com.yangna.lbdsp.common.swiperefreshLayout.ToastUtil;
import com.yangna.lbdsp.common.utils.SpUtils;
import com.yangna.lbdsp.home.model.VideoListModel;
import com.yangna.lbdsp.login.model.UserInfoModel;
import com.yangna.lbdsp.login.view.LoginActivity;
import com.yangna.lbdsp.mall.view.MallOrderListActivity;
import com.yangna.lbdsp.mall.view.ShoppingCartActivity;
import com.yangna.lbdsp.mall.view.StoreManagementActivity;
import com.yangna.lbdsp.mine.adapter.TabPagerAdapter;
import com.yangna.lbdsp.mine.impl.MineView;
import com.yangna.lbdsp.mine.model.BasePageRequestParam;
import com.yangna.lbdsp.mine.presenter.MinePresenter;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class MineFragment extends BasePresenterFragment<MinePresenter> implements MineView {

    @BindView(R.id.mine_tab)
    TabLayout tabLayout;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.mine_vp)
    ViewPager vp;
    @BindView(R.id.mine_name)
    TextView mine_name;
    @BindView(R.id.mine_userid)
    TextView mine_userid;
    @BindView(R.id.user_shenfen)
    Button mine_identityAuth;
    @BindView(R.id.banner)
    RelativeLayout banne;
    @BindView(R.id.tv_little_mine_id)
    View mprmine;

    @BindView(R.id.iv_mine_change_avatar)
    View fillmine;
    @BindView(R.id.iv_little_user_center_avatar)
//    RoundedImageView roundedImageView;
            ImageView roundedImageView;
    @BindView(R.id.fl_setting)
    ImageView fl_setting;
    @BindView(R.id.mine_jianjie)//显示简介
            TextView jianjie;
    @BindView(R.id.mine_diqu)//显示地区
            TextView diqu;
    @BindView(R.id.user_age_text)//年龄
            TextView userAgeText;
    @BindView(R.id.user_constellation_text) //星座
            TextView userConstellationText;

    @BindView(R.id.user_shoping_card_view) //购物车
            View userShopingCardView;


    private SharedPreferences sharedPreferences = BaseApplication.getInstance().getSharedPreferences("AccountInfo", MODE_PRIVATE);//SharedPreferences 是一个轻量级的存储类，主要是保存一些小的数据，一些状态信息
    //滑动页中所添加碎片的集合
    private List<Fragment> fragments;
    //tab栏标题的集合
    private List<String> titleList;
    //Tab栏和滑动页关联适配器
    private TabPagerAdapter adapter;
    private AgreeFragment.AgreeFragmentInterface agreeFragmentInterface;
    private String IdentityAuth = "";
    private String id = "";//赋值用户id，传到下一页
    private String account = "";//用户信息
    private Boolean mRegister_SX_GRXX_Tag = false;

    private MyVideoFragment myVideoFragment;
    private MineLikeVideoFragment mineLikeVideoFragment;

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_mine;
    }

    @Override
    protected MinePresenter setPresenter() {
        return new MinePresenter(context);
    }

    @Override
    protected void initView() {
        super.initView();

        // 在当前的 页面 中注册 广播
        IntentFilter filter2 = new IntentFilter();//实例化IntentFilter
        filter2.addAction("SX_GRXX");//设置接收广播的类型 刷新个人信息
        getActivity().registerReceiver(this.broadcastReceiver_SX_GRXX, filter2);//动态注册 刷新个人信息 接收的一个广播
        mRegister_SX_GRXX_Tag = true;

//      mine_scroll.setTargetView(banne);
        mPresenter.setMineView(this);
        fragments = new ArrayList<>();
        titleList = new ArrayList<>();
        swipeRefreshLayout.setOnRefreshListener(() -> new CountDownTimer(1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                BasePageRequestParam requestParam=new BasePageRequestParam();
                requestParam.setPageSize("1");
                requestParam.setCurrentPage("4");
                mPresenter.getMineInfo(context);
                mPresenter.getUserLoveVideoList(context,requestParam);
                EventBus.getDefault().post(new BusMineVideo());
            }

            @Override
            public void onFinish() {
                swipeRefreshLayout.setRefreshing(false);
            }
        }.start());
        titleList.clear();
        titleList.add("作品");
        titleList.add("喜欢");

        myVideoFragment = new MyVideoFragment();
        mineLikeVideoFragment = new MineLikeVideoFragment();

        //添加fragment 的
        fragments.add(myVideoFragment);
        fragments.add(mineLikeVideoFragment);

        //将tab栏和滑动页关联起来
        adapter = new TabPagerAdapter(getChildFragmentManager(), fragments, titleList);
        vp.setAdapter(adapter);
        vp.setOffscreenPageLimit(1);


        //设置tablayout和viewpager可联动
        tabLayout.setupWithViewPager(vp);
        //设置tablayout可以滑动 宽度自适应屏幕大小
        tabLayout.setTabMode(TabLayout.MODE_FIXED);

        mPresenter.getMineInfo(context);
//        userShopingCardView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent sa = new Intent(getActivity(), ShoppingCartActivity.class);
//                startActivity(sa);
//            }
//        });

        fl_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sa = new Intent(getActivity(), SettingActivity.class);
                startActivity(sa);
            }
        });
        mprmine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.getMineInfo(context);
                Intent aba = new Intent(getActivity(), AccountBalanceActivity.class);
                startActivity(aba);
            }
        });
        fillmine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                mPresenter.getTestInfo(context);
                UIManager.switcher(context, MallOrderListActivity.class);
//                UIManager.switcher(context, ShoppingCartActivity.class);
            }
        });
        userShopingCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                mPresenter.getTestInfo(context);
//                UIManager.switcher(context, MallOrderListActivity.class);
                UIManager.switcher(context, ShoppingCartActivity.class);
            }
        });
        roundedImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                mPresenter.getAllInfo(context);
            }
        });
    }

    public BroadcastReceiver broadcastReceiver_SX_GRXX = new BroadcastReceiver() {//实例化 刷新个人信息 接收广播
        @Override//收到广播后，会自动回调onReceive(..)方法
        public void onReceive(Context context, Intent intent) {//刷新个人信息，这里开始本页面的各项子线程重新启动
            SharedPreferences sp = BaseApplication.getInstance().getSharedPreferences("AccountInfo", MODE_PRIVATE);//SharedPreferences 是一个轻量级的存储类，主要是保存一些小的数据，一些状态信息
            if (sp.getString("logo", "").equals("") || sp.getString("logo", "").equals("/") || sp.getString("logo", "") == null) {//没有图片URL
                //不刷新图片显示，加载本地图片
                Glide.with((getActivity())).load(sp.getString("logo", ""))
                        .error(R.mipmap.user_icon) //异常时候显示的图片
                        .placeholder(R.mipmap.user_icon) //加载成功前显示的图片
                        .fallback(R.mipmap.user_icon) //url为空的时候,显示的图片
                        .apply(RequestOptions.circleCropTransform())//加载为圆形图片
                        .into(roundedImageView);//为了加载网络图片的两步方法
            } else {
                Glide.with((getActivity())).load(sp.getString("logo", ""))
                        .error(R.mipmap.user_icon) //异常时候显示的图片
                        .placeholder(R.mipmap.user_icon) //加载成功前显示的图片
                        .fallback(R.mipmap.user_icon) //url为空的时候,显示的图片
                        .apply(RequestOptions.circleCropTransform())//加载为圆形图片
                        .into(roundedImageView);//为了加载网络图片的两步方法
            }
            mine_name.setText(sp.getString("nickName", "老表"));
            jianjie.setText(sp.getString("des", "简介"));
            diqu.setText(sp.getString("area", "地区"));
        }
    };

    @SuppressLint({"UseRequireInsteadOfGet", "SetTextI18n"})
    @Override
    public void onGetMineInfo(UserInfoModel model) {
        IdentityAuth = model.getBody().getAccount().getIdentityAuth();
        if (IdentityAuth.equals("") || IdentityAuth == null) {//拿不到真实用户类型，就让用户重新登录
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
        } else {//拿到真实用户类型，就本地记录一次
            String chaochangTOKEN = (String) Objects.requireNonNull(SpUtils.get(getActivity(), UrlConfig.USERID, ""));
            /* 获取用户信息 */
            //返回值 2020年11月11日13:39:18
            //{
            //	"body": {
            //		"uuid": "1b63c671-de28-4ef8-90e6-32bf24d3df5e",
            //		"account": {
            //			"id": 8,                                    自增id
            //			"accountName": "9826566431",                老表视频号，仅可修改一次
            //			"mobile": "18978065947",                    注册手机号
            //			"createTime": 1598413744678,
            //			"inviteCode": "rS4e02uW",                   邀请码
            //			"status": "0",                              0正常1冻结2删除
            //			"nickName": "dztz",                         昵称
            //			"logo": "/",                                头像
            //			"des": "666",                               描述
            //			"area": "zzz",                              所在地区
            //			"identityAuth": "S",                        身份认证 U普通用户 G官方用户 V认证用户 S商家用户 E政府机构
            //			"identityName": null,                       认证名称
            //			"promotionCode": "KTAWKEoC",                推广码
            //			"relationStatus": null,                     关系状态
            //			"userLevel": null                           用户等级
            //		},
            //		"loginTime": "2020-11-11 11:02:18",
            //		"expireTime": "2020-11-14 01:27:46",
            //		"ipaddr": "125.73.102.239",
            //		"loginLocation": "广西 柳州",
            //		"browser": "Unknown",
            //		"os": "Unknown",
            //		"platform": null
            //	},
            //	"state": 200,
            //	"msg": "请求成功"
            //}
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("logo", model.getBody().getAccount().getLogo());
            editor.putString("mobile", model.getBody().getAccount().getMobile());
            editor.putString("nickName", model.getBody().getAccount().getNickName());
            editor.putString("accountName", model.getBody().getAccount().getAccountName());
            editor.putString("des", model.getBody().getAccount().getDes());
            editor.putString("area", model.getBody().getAccount().getArea());
            editor.putString("promotionCode", model.getBody().getAccount().getPromotionCode());
            editor.putString("inviteCode", model.getBody().getAccount().getInviteCode());
            editor.putString("identityAuth", model.getBody().getAccount().getIdentityAuth());
            editor.apply();

            if (model.getBody().getAccount().getLogo().equals("") || model.getBody().getAccount().getLogo().equals("/") || model.getBody().getAccount().getLogo() == null) {//没有图片URL
                //不刷新图片显示
            } else {
//            RequestOptions options = new RequestOptions().placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher);//两张固定图片而已
                Glide.with(Objects.requireNonNull(getActivity())).load(model.getBody().getAccount().getLogo())
                        .error(R.mipmap.user_icon) //异常时候显示的图片
                        .placeholder(R.mipmap.user_icon) //加载成功前显示的图片
                        .fallback(R.mipmap.user_icon) //url为空的时候,显示的图片
                        .apply(RequestOptions.circleCropTransform())//加载为圆形图片
                        .into(roundedImageView);//为了加载网络图片的两步方法
            }
            mine_name.setText(model.getBody().getAccount().getNickName());
            mine_userid.setText("老表号：" + model.getBody().getAccount().getAccountName());
            jianjie.setText(model.getBody().getAccount().getDes());
            diqu.setText(model.getBody().getAccount().getArea());
            if (null!=model.getBody().getAccount().getUserAge()){
                userAgeText.setText(model.getBody().getAccount().getUserAge());
            }
            if (null!=model.getBody().getAccount().getConstellation()){
                userConstellationText.setText(model.getBody().getAccount().getConstellation());
            }
            id = model.getBody().getAccount().getId();
            if (model.getBody().getAccount().getIdentityAuth().equals("S")) {//判断用户类型，显示对应按钮
                mine_identityAuth.setVisibility(View.VISIBLE);
                mine_identityAuth.setText("店铺管理");
            } else {
                mine_identityAuth.setVisibility(View.GONE);
            }

            //发送广播，刷新个人信息
            Intent intent2 = new Intent();
            intent2.setAction("SX_GRXX");
            getActivity().sendBroadcast(intent2);
        }
    }

    @Override
    public void onUserLoveVideo(VideoListModel VideoListModel) {


    }

    public void setinterface(AgreeFragment.AgreeFragmentInterface agreeFragmentInterface) {
        this.agreeFragmentInterface = agreeFragmentInterface;
    }

    /*  待修复MineFragment返回刷新 */

    //上传商品 不一定是上传商品，也有其他特殊用户的功能
    @OnClick(R.id.user_shenfen)
    void doTeShuYongHuGongNeng(View view) {
        if (IdentityAuth.equals("S")) {//等于商户，进入商户页面
//            Intent intent = new Intent(getActivity(), UploadGoodsActivity.class);
//            intent.putExtra("id", id);
//            startActivity(intent);
            Intent intent = new Intent(getActivity(), StoreManagementActivity.class);
            intent.putExtra("id", id);
            startActivity(intent);
        }
    }
}
