package com.yangna.lbdsp.mall.view;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.yangna.lbdsp.R;
import com.yangna.lbdsp.common.base.BaseModel;
import com.yangna.lbdsp.common.base.BasePresenterFragment;
import com.yangna.lbdsp.mall.impl.MallOrderView;
import com.yangna.lbdsp.mall.model.MallOrderListModel;
import com.yangna.lbdsp.mall.presenter.MallOrderListPresenter;


/**
 * A simple {@link Fragment} subclass.
 */
public class AfterFgFragment extends BasePresenterFragment<MallOrderListPresenter> implements MallOrderView {

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_after_fg;
    }

    public static MallOrderListFragment newInstance(int s) {
        MallOrderListFragment newFragment = new MallOrderListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("flag", s);
        newFragment.setArguments(bundle);
        return newFragment;
    }

    @Override
    protected void initView() {
        super.initView();

    }

    @Override
    public void onGetMallOrderData(MallOrderListModel model) {

    }

    @Override
    public void OnReturnGoods(BaseModel model) {

    }
}
