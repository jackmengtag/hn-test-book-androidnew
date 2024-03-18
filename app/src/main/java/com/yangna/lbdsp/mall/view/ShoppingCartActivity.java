package com.yangna.lbdsp.mall.view;

import android.content.Intent;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.wwb.laobiao.address.FukuanActivity;
import com.yangna.lbdsp.R;
import com.yangna.lbdsp.common.base.BaseModel;
import com.yangna.lbdsp.common.base.BasePresenterActivity;
import com.yangna.lbdsp.common.manager.UIManager;
import com.yangna.lbdsp.common.swiperefreshLayout.ToastUtil;
import com.yangna.lbdsp.mall.adapter.ShoppingCartAdapter;
import com.yangna.lbdsp.mall.bean.CartItemBean;
import com.yangna.lbdsp.mall.bean.GoodsBean;
import com.yangna.lbdsp.mall.bean.ICartItem;
import com.yangna.lbdsp.mall.bean.NormalBean;
import com.yangna.lbdsp.mall.bean.ShopBean;
import com.yangna.lbdsp.mall.impl.CartOnCheckChangeListener;
import com.yangna.lbdsp.mall.impl.OnCheckChangeListener;
import com.yangna.lbdsp.mall.impl.ShopPingCratView;
import com.yangna.lbdsp.mall.model.ShopPingCartModel;
import com.yangna.lbdsp.mall.presenter.ShopPingCartPresenter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

public class ShoppingCartActivity extends BasePresenterActivity<ShopPingCartPresenter> implements ShopPingCratView, View.OnClickListener {

    @BindView(R.id.recycler)
    RecyclerView recyclerView;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.tv_edit)
    TextView mTvEdit;
    @BindView(R.id.checkbox_all)
    CheckBox mCheckBoxAll;
    @BindView(R.id.tv_total_price)
    TextView mTvTotal;
    @BindView(R.id.btn_go_to_pay)
    Button mBtnSubmit;

    //    @BindView(R.id.cart_refreshlayout)
//    SwipeRefreshLayout cart_refreshlayout;
    private static final String TAG = "购物车Activity";
    private boolean isEditing;//是否处于编辑状态
    private int totalCount;//购物车商品ChildItem的总数量，店铺条目不计算在内
    private int totalCheckedCount;//勾选的商品总数量，店铺条目不计算在内
    private double totalPrice;//勾选的商品总价格
    private String productId; //商品id
    private String productName; //商品名称
    private String productPrice; //商品价格
    private String imageUrl; //商品图片
    private int productNumber; //商品数量
    private String productSpecficationId; //商品规格

    ShoppingCartAdapter mAdapter;
    List<ICartItem> cartItemBeans = new ArrayList<>();
    private ArrayList<String> ids = new ArrayList<>();
    private ShopPingCartModel shopPingCartModel;
    List<ShopPingCartModel> data_gouwuche = new ArrayList<>();
    List<ShopPingCartModel> data_fukuan = new ArrayList<>();//发送到付款页面的数据

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_shopping_cart;
    }

    @Override
    protected ShopPingCartPresenter setPresenter() {
        return new ShopPingCartPresenter(this);
    }

    @Override
    protected void initView() {
        super.initView();
        mTvEdit.setOnClickListener(this);
        mCheckBoxAll.setOnClickListener(this);
        mBtnSubmit.setOnClickListener(this);
        mPresenter.setShopPingCratView(this);
        mPresenter.getShopCartList(context);//获取购物车列表
        mTvTitle.setText(getString(R.string.cart, 0));
        mBtnSubmit.setText(getString(R.string.go_settle_X, 0));
        mTvTotal.setText(getString(R.string.rmb_X, 0.00));
        mAdapter = new ShoppingCartAdapter(this, cartItemBeans);
        mAdapter.setOnCheckChangeListener(new CartOnCheckChangeListener(recyclerView, mAdapter) {
            @Override
            public void onCalculateChanged(ICartItem cartItemBean) {
                calculate();
            }
        });
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // 给列表注册 ContextMenu 事件。
        // 同时如果想让ItemView响应长按弹出菜单，需要在item xml布局中设置 android:longClickable="true"
        registerForContextMenu(recyclerView);
//        cart_refreshlayout.setColorSchemeResources(R.color.color_link);
//        cart_refreshlayout.setOnRefreshListener(() -> new CountDownTimer(1000, 1000) {
//            @Override
//            public void onTick(long millisUntilFinished) {
//                mPresenter.getShopCartList(context);
//            }
//
//            @Override
//            public void onFinish() {
//                cart_refreshlayout.setRefreshing(false);
//            }
//        }.start());
    }

    /**
     * 统计操作<br>
     * 1.先清空全局计数器<br>
     * 2.遍历所有子元素，只要是被选中状态的，就进行相关的计算操作<br>
     * 3.给相关的 textView 进行数据填充
     */
    private void calculate() {
//        ids.clear();//每次选择之前先清空一次计数
        totalCheckedCount = 0;
        totalCount = 0;
        totalPrice = 0.00;
        int notChildTotalCount = 0;
        if (mAdapter.getData() != null) {
            for (ICartItem iCartItem : mAdapter.getData()) {
                if (iCartItem.getItemType() == ICartItem.TYPE_CHILD) {
                    totalCount++;
                    if (iCartItem.isChecked()) {
                        totalCheckedCount++;
                        ids.add(((GoodsBean) iCartItem).getId());
                        totalPrice += ((GoodsBean) iCartItem).getGoods_price() * ((GoodsBean) iCartItem).getProductNumber();
                    }
                } else {
                    notChildTotalCount++;
                }
            }
            Log.i(TAG, "一共选中" + ids.size() + "个商品");
        }
        mTvTitle.setText(getString(R.string.cart, totalCount));
        mBtnSubmit.setText(getString(isEditing ? R.string.delete_X : R.string.go_settle_X, totalCheckedCount));
        mTvTotal.setText(getString(R.string.rmb_X, totalPrice));
        if (mCheckBoxAll.isChecked() && (totalCheckedCount == 0 || (totalCheckedCount + notChildTotalCount) != mAdapter.getData().size())) {
            mCheckBoxAll.setChecked(false);
        }
        if (totalCheckedCount != 0 && (!mCheckBoxAll.isChecked()) && (totalCheckedCount + notChildTotalCount) == mAdapter.getData().size()) {
            mCheckBoxAll.setChecked(true);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //编辑按钮事件
            case R.id.tv_edit:
                isEditing = !isEditing;
                mTvEdit.setText(getString(isEditing ? R.string.edit_done : R.string.edit));
                mBtnSubmit.setText(getString(isEditing ? R.string.delete_X : R.string.go_settle_X, totalCheckedCount));
                break;
            //提交订单 & 删除选中（编辑状态）
            case R.id.btn_go_to_pay:
                submitEvent();
                break;
            case R.id.checkbox_all:
                mAdapter.checkedAll(((CheckBox) v).isChecked());
                break;
            default:
                break;
        }
    }

    private void submitEvent() {
        if (isEditing) {
            if (totalCheckedCount == 0) {
                Toast.makeText(this, "请勾选你要删除的商品", Toast.LENGTH_SHORT).show();
            } else {
                mAdapter.removeChecked();
//                mPresenter.deteShopCart(context, ids);
                Log.i(TAG, "删除了" + ids.size() + "个商品");
                Log.i(TAG, "删除了" + ids.get(0) + "个商品");
                mPresenter.deteShopCart(context, ids);
            }
        } else {
            if (totalCheckedCount == 0) {
                Toast.makeText(this, "你还没有选择任何商品", Toast.LENGTH_SHORT).show();
            } else {
//                Toast.makeText(this, new StringBuilder().append("你选择了").append(totalCheckedCount).append("件商品").append("共计 ").append(totalPrice).append("元"), Toast.LENGTH_SHORT).show();
                Log.i(TAG, "进入创建订单界面");
                Intent intent = new Intent();
                intent.setClass(context, FukuanActivity.class);
                intent.putExtra("data", (Serializable) data_gouwuche);
                context.startActivity(intent);
            }
        }
    }

    /**
     * 数据初始化尤其重要
     * 1. childItem 数据全部在 GroupItem 数据的下方，数据顺序严格按照对应关系；
     * 2. GroupItem 下的 ChildItem 数据不能为空；
     * 3. 初始化时如果不需要，所有类型的条目都可以不设置ID，GroupItem也不用设置setChilds()；
     * <p>
     * 列表操作时数据动态的变化设置：
     * 1. 通过 CartAdapter 的 addData、setNewData；
     * 2. 单个添加各个条目可以通过对应的 add 方法；
     * 3. 单独添加一个 GroupItem ,可以把它的 ChildItem 数据放到 setChilds 中。
     *
     * @return
     */
    private List<CartItemBean> getData() {
        ArrayList<CartItemBean> cartItemBeans = new ArrayList<>();
        NormalBean normalBean = new NormalBean();
        normalBean.setMarkdownNumber(6);
        cartItemBeans.add(normalBean);
//        for (int i = 0; i < 10; i++) {
//            ShopBean shopBean = new ShopBean();
//            shopBean.setShop_name("解忧杂货铺 第" + (i + 1) + "分店");
//            shopBean.setItemType(CartItemBean.TYPE_GROUP);
//            cartItemBeans.add(shopBean);
//
//            for (int j = 0; j < (i + 5); j++) {
//                GoodsBean goodsBean = new GoodsBean();
//                goodsBean.setGoods_name("忘忧水 " + (j + 1) + " 代");
//                goodsBean.setItemType(CartItemBean.TYPE_CHILD);
//                goodsBean.setItemId((j + 1) * 10 + j);
//                goodsBean.setGoods_price(j + 1);
//                goodsBean.setGroupId(i);
//                cartItemBeans.add(goodsBean);
//            }
//        }
        return cartItemBeans;
    }

    @Override
    public void onGetShopCartList(ShopPingCartModel shopPingCartModel) {
        cartItemBeans.clear();
        data_gouwuche.add(shopPingCartModel);
        Log.i(TAG, "已经到达回调准备添加" + shopPingCartModel.getBody().getRecords().size() + "个数据");
        for (int i = 0; i < shopPingCartModel.getBody().getRecords().size(); i++) {
            ShopBean shopBean = new ShopBean();
            shopBean.setShop_name(shopPingCartModel.getBody().getRecords().get(i).getShopName());
            shopBean.setItemType(CartItemBean.TYPE_GROUP);
            cartItemBeans.add(shopBean);
            for (int j = 0; j < shopPingCartModel.getBody().getRecords().get(i).getCardProduct().size(); j++) {
                GoodsBean goodsBean = new GoodsBean();
                goodsBean.setGoods_name(shopPingCartModel.getBody().getRecords().get(i).getCardProduct().get(j).getProductName());
                goodsBean.setItemType(CartItemBean.TYPE_CHILD);
                goodsBean.setId(shopPingCartModel.getBody().getRecords().get(i).getCardProduct().get(j).getId());
                goodsBean.setItemId((j + 1) * 10 + j);
                goodsBean.setGoods_price(shopPingCartModel.getBody().getRecords().get(i).getCardProduct().get(j).getProductPrice());
                goodsBean.setImageUrl(shopPingCartModel.getBody().getRecords().get(i).getCardProduct().get(j).getImageUrl());
                goodsBean.setGroupId(i);
                goodsBean.setProductNumber(shopPingCartModel.getBody().getRecords().get(i).getCardProduct().get(j).getProductNumber());
                cartItemBeans.add(goodsBean);
            }
            mAdapter.setNewData(cartItemBeans);
        }
    }

    @Override
    public void onDeteShopCart(BaseModel baseModel) {
        ToastUtil.showToast("删除成功");
        data_gouwuche.clear();//清空一次当前总购物车列表
        mPresenter.getShopCartList(context);//删除成功后再刷新获取一次当前购物车列表获取购物车列表
    }

}
