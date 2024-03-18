package com.hn.book.view;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.hn.book.adapter.BookListAdapter;
import com.hn.book.impl.BookListView;
import com.hn.book.model.BookDetailResultModel;
import com.hn.book.model.BookListModel;
import com.hn.book.presenter.BookListPresenter;
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

public class BookListFragment extends BasePresenterFragment<BookListPresenter> implements BookListView {

    @BindView(R.id.lv)
    ListView lv;
    @BindView(R.id.refresh)
    SwipeRefreshLayout refresh;
    @BindView(R.id.no_data_ll)
    LinearLayout noDataLl;

    private static final String TAG = "BookListFragment";

    private int currentPage = 1;
    private BookListAdapter adapter;

    private int oilStatus = 10;

    public static BookListFragment newInstance(int s) {
        BookListFragment newFragment = new BookListFragment();
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
    public void getEventBus(String id) {
        mPresenter.getBookListByParam(context);
    }


    @Override
    protected BookListPresenter setPresenter() {
        return new BookListPresenter(context);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_book_list;
    }


    @Override
    protected void initView() {
        super.initView();
        mPresenter.setBookListView(this);

        assert getArguments() != null;
        oilStatus = getArguments().getInt("flag", 10);

        ClickListener deleteClick = new ClickListener() {
            @Override
            public void onClick(String id) {
                mPresenter.deleteBookById(context,id);
            }
        };

        adapter = new BookListAdapter(context,deleteClick);
        lv.setAdapter(adapter);
        mPresenter.getBookListByParam(context);
        Log.i(TAG, "oilStatus" + oilStatus);
        //刷新
        refresh.setOnRefreshListener(() -> new CountDownTimer(1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mPresenter.getBookListByParam(context);
            }

            @Override
            public void onFinish() {
                refresh.setRefreshing(false);
            }
        }.start());

    }





    @Override
    public void getBookDetailById(BookDetailResultModel model) {

    }

    @Override
    public void addBook(BaseModel model) {

    }

    @Override
    public void deleteBook(BaseModel model) {

    }

    @Override
    public void deleteBookList(BaseModel model) {

    }

    @Override
    public void getBookList(BookListModel.BookModel bookModel) {
        if (bookModel.getRecords().size() <= 0 && currentPage == 1) {
            noDataLl.setVisibility(View.VISIBLE);
            refresh.setVisibility(View.GONE);
        } else {
            noDataLl.setVisibility(View.GONE);
            refresh.setVisibility(View.VISIBLE);

            if (1 == currentPage) {
                adapter.setDates(bookModel.getRecords());
            }
            else {
                adapter.setDates(bookModel.getRecords());
            }
            if (bookModel.getRecords() != null && bookModel.getRecords().size() > 0) {
                currentPage++;
            }
        }
    }

    @Override
    public void updateBook(BaseModel model) {

    }
}
