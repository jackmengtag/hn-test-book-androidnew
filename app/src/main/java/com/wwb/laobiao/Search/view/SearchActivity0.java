package com.wwb.laobiao.Search.view;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.gyf.immersionbar.ImmersionBar;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.wwb.laobiao.Search.impl.Search;
import com.wwb.laobiao.Search.impl.SearchModel;
import com.yangna.lbdsp.R;
import com.yangna.lbdsp.common.base.BasePresenterFragActivity;
import com.yangna.lbdsp.common.manager.ToastManager;
import com.yangna.lbdsp.common.manager.UIManager;
import com.yangna.lbdsp.common.net.MyObserver;
import com.yangna.lbdsp.common.net.NetWorks;
import com.yangna.lbdsp.home.impl.HomeView;
import com.yangna.lbdsp.home.model.AppSettingModel;
import com.yangna.lbdsp.home.model.VideoIdModel;
import com.yangna.lbdsp.home.presenter.HomePresenter;
import com.yangna.lbdsp.login.bean.CreateFlie;
import com.yangna.lbdsp.login.model.FileModel;
import com.yangna.lbdsp.login.view.LoginActivity;
import com.yangna.lbdsp.widget.FragmentIndicator;
import com.yangna.lbdsp.widget.MenuDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.functions.Consumer;


public class SearchActivity0 extends BasePresenterFragActivity<HomePresenter> implements HomeView, FragmentIndicator.FragmentIndicatorInterface, FragmentIndicator.OnIndicateListener {
//    @BindView(R.id.indicator)
//    FragmentIndicator indicator;//索引

    //fragment
    private FragmentManager fragmentManager;//框架管理
    private FragmentTransaction fragmentTransaction;//Transaction 交易
    private FragmentManager fragmentManager1;//框架管理
    private FragmentTransaction fragmentTransaction1;//Transaction 交易

//    private LinearLayoutManager linearLayout;//框架管理
//    private LinearTransaction linearLayout;//框架管理

    private List<Fragment> mFragments;//
    private List<Fragment> mFragments1;//
//    private HomeFragment homeFragment;
    private TestFragment testFragment;
//    private HongbaoFragment hongbaoFragment;
//    private MessageFragment messageFragment;
//    private MineFragment mineFragment;

//    private RxPermissions rxPermissions;//权限
    @BindView(R.id.layid0)
    LinearLayout mhdlay;//
//    @BindView(R.id.iv_search)
//    LinearLayout mhdlay;//iv_search
    /**
     * 跳转编辑、录制选择弹窗
     */
    private MenuDialog mMenuDialog;
    private SearchFragment searchFragment;

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_test1;
    }

    @Override
    protected HomePresenter setPresenter() {
        //回调
        return new HomePresenter(this);
    }

    @Override
    protected void initView() {
        super.initView();
        mPresenter.setHomeViewl(this);
        ImmersionBar.with(this).supportActionBar(true).init();
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        mFragments = new ArrayList<Fragment>();
        fragmentManager1 = getSupportFragmentManager();
        fragmentTransaction1 = fragmentManager1.beginTransaction();
        mFragments1 = new ArrayList<Fragment>();

        testFragment = new TestFragment();
        showFragment(testFragment);
        searchFragment=new SearchFragment();//searchFragment
        SearchViewListener mmsearch=new SearchViewListener() {
            @Override
            public void onRefreshAutoComplete(String text) {

            }
            @Override
            public void onSearch(String text) {

            }
            @Override
            public void close() {
//                onDestroy();
                onBackPressed();
            }
        };
        searchFragment.setSearchViewListener(mmsearch);
        showFragment1(searchFragment);
    }
    private void inittest() {
        Search search= new Search();
        search.setcurrentPage(0);
        search.setkeyword("广西柳州");
        MyObserver<SearchModel> observerx=new MyObserver<SearchModel>() {

            @Override
            public void onNext(SearchModel searchModel) {
                ToastManager.showToast(context, "search ok");
            }
            @Override
            public void onError(Throwable e) {
                 ToastManager.showToast(context, "search err");
            }
        };
        NetWorks.getInstance().getSearch(context,search,observerx);
    }

    private void showFragment1(Fragment showFragment) {
// 友盟  \\layoutsort
//   MobclickAgent.onEvent(this, showFragment.getClass().getSimpleName(), "menu"); layoutsort.setVisibility(View.VISIBLE);
        if (showFragment == null) {
            return;
        }
        fragmentTransaction1 = fragmentManager1.beginTransaction();
        if (!mFragments1.contains(showFragment)) {
            mFragments1.add(showFragment);
            fragmentTransaction1.add(R.id.fragment0, showFragment);
        }
        for (Fragment fragment : mFragments1) {
            if (!showFragment.equals(fragment)) {
                fragmentTransaction1.hide(fragment);
            }
        }
        fragmentTransaction1.show(showFragment);
        fragmentTransaction1.commit();
    }
    @Override
    public boolean hasLogin() {
        return true;
    }

    @Override
    public void goLogin(int tag) {
        UIManager.switcher(context, LoginActivity.class);
    }
    @Override
    public void onIndicate(View v, int id) {
        switch (id) {
            case 1:
                UIManager.switcher(context, LoginActivity.class);
                break;
        }
    }
    @Override
    public void onBackPressed() {
//        if(editbf)
        {
        }
        super.onBackPressed();
    }
    /**
     * 动态显示Fragment  onDestroy
     *
     * @param showFragment 要增加的fragment
     */
    private void showFragment(Fragment showFragment) {
        //友盟
//        MobclickAgent.onEvent(this, showFragment.getClass().getSimpleName(), "menu");
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
            }
        }
        fragmentTransaction.show(showFragment);
        fragmentTransaction.commit();
    }
    private LayoutInflater inflater;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == Activity.RESULT_OK) {}
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

    }

    @Override
    public void onGetUploadVideo(VideoIdModel videoIdModel) {

    }

    @Override
    public void onGetAppVersion(AppSettingModel appSettingModel) {

    }
}
