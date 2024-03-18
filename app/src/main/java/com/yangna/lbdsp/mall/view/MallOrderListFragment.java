package com.yangna.lbdsp.mall.view;

import android.os.Bundle;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.yangna.lbdsp.R;
import com.yangna.lbdsp.common.base.BaseModel;
import com.yangna.lbdsp.common.base.BasePresenterFragment;
import com.yangna.lbdsp.common.eventbus.BusMallActual;
import com.yangna.lbdsp.common.swiperefreshLayout.ToastUtil;
import com.yangna.lbdsp.mall.adapter.MallOrderListAdapter;
import com.yangna.lbdsp.mall.impl.ClickListener;
import com.yangna.lbdsp.mall.impl.MallOrderView;
import com.yangna.lbdsp.mall.model.MallOrderListModel;
import com.yangna.lbdsp.mall.presenter.MallOrderListPresenter;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;

/**
 * 订单管理
 */

public class MallOrderListFragment extends BasePresenterFragment<MallOrderListPresenter> implements MallOrderView {

    @BindView(R.id.lv)
    ListView lv;
    @BindView(R.id.refresh)
    SwipeRefreshLayout refresh;
    @BindView(R.id.no_data_ll)
    LinearLayout noDataLl;

    private static final String TAG = "MallOrderListFragment";

    private int currentPage = 1;
    private MallOrderListAdapter adapter;

    private int oilStatus = 10;

    public static MallOrderListFragment newInstance(int s) {
        MallOrderListFragment newFragment = new MallOrderListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("flag", s);
        newFragment.setArguments(bundle);
        return newFragment;
    }

    @Override
    protected boolean enableEventBus() {
        return true;
    }

    //EventBus接收消息
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getEventBus(BusMallActual busMallActual) {
        mPresenter.getMallAllOrder(context,oilStatus);
    }


    @Override
    protected MallOrderListPresenter setPresenter() {
        return new MallOrderListPresenter(context);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_mall_order_list;
    }


    @Override
    protected void initView() {
        super.initView();
        mPresenter.setMallOrderView(this);

        assert getArguments() != null;
        oilStatus = getArguments().getInt("flag", 10);

        ClickListener deleteClick = new ClickListener() {
            @Override
            public void onClick(String orderSn) {
                mPresenter.OnReturnGoods(context,orderSn);
            }
        };

        adapter = new MallOrderListAdapter(context,deleteClick);
        lv.setAdapter(adapter);
        mPresenter.getMallAllOrder(context, oilStatus);
        Log.i(TAG, "oilStatus" + oilStatus);
        //刷新
        refresh.setOnRefreshListener(() -> new CountDownTimer(1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mPresenter.getMallAllOrder(context, oilStatus);
            }

            @Override
            public void onFinish() {
                refresh.setRefreshing(false);
            }
        }.start());

    }

    @Override
    public void onGetMallOrderData(MallOrderListModel model) {
        if (model.getBody().getRecords().size() <= 0 && currentPage == 1) {
            noDataLl.setVisibility(View.VISIBLE);
            refresh.setVisibility(View.GONE);
        } else {
            noDataLl.setVisibility(View.GONE);
            refresh.setVisibility(View.VISIBLE);

            if (1 == currentPage) {
                adapter.setDates(model.getBody().getRecords());
            }
            else {
                adapter.setDates(model.getBody().getRecords());
            }
            if (model.getBody().getRecords() != null && model.getBody().getRecords().size() > 0) {
                currentPage++;
            }
        }
    }

    @Override
    public void OnReturnGoods(BaseModel model) {
        ToastUtil.showToast("正在处理");
    }

}
