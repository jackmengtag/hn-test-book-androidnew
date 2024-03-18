package com.wwb.laobiao.Search.view;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.wwb.laobiao.Search.adp.HotGuessAdapter;
import com.wwb.laobiao.Search.impl.Search;
import com.wwb.laobiao.Search.impl.SearchModel;
import com.wwb.laobiao.Search.model.flowlayout.FlowLayout;
import com.wwb.laobiao.Search.model.flowlayout.TagAdapter;
import com.wwb.laobiao.Search.model.flowlayout.TagFlowLayout;
import com.wwb.laobiao.Search.model.flowlayout.db.RecordsDao;
import com.wwb.laobiao.Search.widget.HotFragment;
import com.yangna.lbdsp.R;
import com.yangna.lbdsp.common.net.MyObserver;
import com.yangna.lbdsp.common.net.NetWorks;
import com.yangna.lbdsp.home.view.HomeFragment;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

//import com.qzy.laobiao.R;
//import com.qzy.laobiao.common.manager.ToastManager;
//import com.qzy.laobiao.common.net.MyObserver;
//import com.qzy.laobiao.common.net.NetWorks;
//import com.qzy.laobiao.home.view.HomeFragment;
//import com.qzy.laobiao.testview0.adp.HotGuessAdapter;
//import com.qzy.laobiao.testview0.impl.Search;
//import com.qzy.laobiao.testview0.impl.SearchModel;
//import com.qzy.laobiao.testview0.model.flowlayout.FlowLayout;
//import com.qzy.laobiao.testview0.model.flowlayout.TagAdapter;
//import com.qzy.laobiao.testview0.model.flowlayout.TagFlowLayout;
//import com.qzy.laobiao.testview0.model.flowlayout.db.RecordsDao;
//import com.qzy.laobiao.testview0.widget.HotFragment;

public class SearchActivity1 extends AppCompatActivity implements HotGuessAdapter.HotGuessAdapterinterface{

    private RecordsDao mRecordsDao;
    //默然展示词条个数
    private final int DEFAULT_RECORD_NUMBER = 10;
    private List<String> recordList = new ArrayList<>();
    private TagAdapter mRecordsAdapter;
    private LinearLayout mHistoryContent;
    private HotFragment hotFragment;
//    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.fragment_search_test);
        setContentView(R.layout.fragment_search_main);
        //默认账号
        String username = "007";
        //初始化数据库
        mRecordsDao = new RecordsDao(this, username);
        final EditText editText = findViewById(R.id.edit_query);
        LinearLayout mlayflow=findViewById(R.id.fl_search_records_lay);
        final TagFlowLayout tagFlowLayout;
        {
            mlayflow.removeAllViews();
            tagFlowLayout=new TagFlowLayout(this);
            tagFlowLayout.setBackgroundColor(Color.BLACK);
            mlayflow.addView(tagFlowLayout);
        }
//        final TagFlowLayout tagFlowLayout = findViewById(R.id.fl_search_records);
        final ImageView clearAllRecords = findViewById(R.id.clear_all_records);
        final ImageView moreArrow = findViewById(R.id.iv_arrow);
        TextView search = findViewById(R.id.iv_search);
        ImageView clearSearch = findViewById(R.id.iv_clear_search);
        ImageView imageSearch = findViewById(R.id.iv_search_ctr);
        mHistoryContent = findViewById(R.id.ll_history_content);
        initData();
        //创建历史标签适配器
        //为标签设置对应的内容
        mRecordsAdapter = new TagAdapter<String>(recordList) {

            @Override
            public View getView(FlowLayout parent, int position, String s) {
                TextView tv = (TextView) LayoutInflater.from(SearchActivity1.this).inflate(R.layout.tv_history,
                        tagFlowLayout, false);
//				tv.setBackgroundColor(Color.GRAY);
                tv.setTextColor(Color.WHITE);
                //为标签设置对应的内容
                tv.setText(s);
                return tv;
            }
        };
        tagFlowLayout.setAdapter(mRecordsAdapter);
        tagFlowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public void onTagClick(View view, int position, FlowLayout parent) {
                //清空editText之前的数据
                editText.setText("");
                //将获取到的字符串传到搜索结果界面,点击后搜索对应条目内容
                editText.setText(recordList.get(position));
                editText.setSelection(editText.length());
            }
        });
        //删除某个条目
        tagFlowLayout.setOnLongClickListener(new TagFlowLayout.OnLongClickListener() {
            @Override
            public void onLongClick(View view, final int position) {
                showDialog("确定要删除该条历史记录？", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //删除某一条记录
                        mRecordsDao.deleteRecord(recordList.get(position));
                    }
                });
            }
        });

        //view加载完成时回调
        tagFlowLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                boolean isOverFlow = tagFlowLayout.isOverFlow();
                boolean isLimit = tagFlowLayout.isLimit();
                if (isLimit && isOverFlow) {
                    moreArrow.setVisibility(View.VISIBLE);
                } else {
                    moreArrow.setVisibility(View.GONE);
                }
            }
        });
        moreArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tagFlowLayout.setLimit(false);
                mRecordsAdapter.notifyDataChanged();
            }
        });
        //清除所有记录
        clearAllRecords.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog("确定要删除全部历史记录？", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        tagFlowLayout.setLimit(true);
                        //清除所有数据
                        mRecordsDao.deleteUsernameAllRecords();
                    }
                });
            }
        });
        search.setText("搜索");
	    search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//            String record = editText.getText().toString();
////                 ToastManager.showToast(getApplicationContext(),""+record);
                search.setText("取消");
                String record = editText.getText().toString();
                if (!TextUtils.isEmpty(record)) {
                    //添加数据
                    mRecordsDao.addRecords(record);
                }
                Onsearch(record);
                search.setText("搜索");
            }
        });
        imageSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String record = editText.getText().toString();
                if (!TextUtils.isEmpty(record)) {
                    //添加数据
                    mRecordsDao.addRecords(record);
                }
                 Onsearch(record);
            }
        });
        clearSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //清除搜索历史
                editText.setText("");
            }
        });
        mRecordsDao.setNotifyDataChanged(new RecordsDao.NotifyDataChanged() {
            @Override
            public void notifyDataChanged() {
                initData();
            }
        });
        initredian();
    }
    //fragment
    private FragmentManager fragmentManager;//框架管理
    private FragmentTransaction fragmentTransaction;//Transaction 交易
    private List<Fragment> mFragments;//
    private HomeFragment homeFragment;
    private void initredian() {
//lay_gusshot_iv
//        LinearLayout mlayhis=findViewById(R.id.lay_gusshot_iv);
//        mlayhis.removeAllViews();//
//        LinearLayoutGusshot linearLayoutGusshot=new LinearLayoutGusshot(this);
//        mlayhis.addView(linearLayoutGusshot);
         fragmentManager = getSupportFragmentManager();
         fragmentTransaction = fragmentManager.beginTransaction();
         mFragments = new ArrayList<>();
         hotFragment = new HotFragment();
         showFragment(hotFragment, R.id.framelayout1);
         hotFragment.setinterface(this);
    }
    private void Onsearch(String String0) {
        if(String0.isEmpty())
        {
            return;
        }
        Search search= new Search();
        search.setcurrentPage(0);
        search.setkeyword(String0);
        MyObserver<SearchModel> observerx=new MyObserver<SearchModel>() {
            @Override
            public void onNext(SearchModel searchModel) {
//                ToastManager.showToast(getApplicationContext(), searchModel.getMsg()+"body="+searchModel.getbody());
               // ToastManager.showToast(getApplicationContext(), searchModel.getMsg()+" "+searchModel.getState());
                searchModel.show();
            }
            @Override
            public void onError(Throwable e) {
//                ToastManager.showToast(getApplicationContext(), "search err");
                System.out.println(e.getMessage());
            }
            @Override
            public void onSubscribe(Disposable d) {
//                super.onSubscribe(d);
//                ToastManager.showToast(getApplicationContext(), "onSubscribe");
                System.out.println("onSubscribe");
            }

            @Override
            public void onComplete() {
//                super.onComplete();
//                ToastManager.showToast(getApplicationContext(), "onComplete");
                System.out.println("onComplete");
            }
        };
        NetWorks.getInstance().getSearch(getApplicationContext(),search,observerx);
    }
    private void showFragment(Fragment showFragment,int id) {
        //友盟
//        MobclickAgent.onEvent(this, showFragment.getClass().getSimpleName(), "menu");
        if (showFragment == null) {
            return;
        }
        fragmentTransaction = fragmentManager.beginTransaction();
        if (!mFragments.contains(showFragment)) {
            mFragments.add(showFragment);
            fragmentTransaction.add(id, showFragment);
        }
        for (Fragment fragment : mFragments) {
            if (!showFragment.equals(fragment)) {
                fragmentTransaction.hide(fragment);
            }
        }
        fragmentTransaction.show(showFragment);
        fragmentTransaction.commit();
    }
    private void showDialog(String dialogTitle, @NonNull DialogInterface.OnClickListener onClickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(SearchActivity1.this);
        builder.setMessage(dialogTitle);
        builder.setPositiveButton("确定", onClickListener);
        builder.setNegativeButton("取消", null);
        builder.create().show();
    }

    private void initData() {
        Observable.create(new ObservableOnSubscribe<List<String>>() {
            @Override
            public void subscribe(ObservableEmitter<List<String>> emitter) throws Exception {
                emitter.onNext(mRecordsDao.getRecordsByNumber(DEFAULT_RECORD_NUMBER));
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<String>>() {
                    @Override
                    public void accept(List<String> s) throws Exception {
                        recordList.clear();
                        recordList = s;
                        if (null == recordList || recordList.size() == 0) {
                            mHistoryContent.setVisibility(View.GONE);
                        } else {
                            mHistoryContent.setVisibility(View.VISIBLE);
                        }
                        if (mRecordsAdapter != null) {
                            mRecordsAdapter.setData(recordList);
                            mRecordsAdapter.notifyDataChanged();
                        }
                    }
                });
    }

    @Override
    protected void onDestroy() {
        if(mRecordsDao!=null)
        {
            mRecordsDao.closeDatabase();
            mRecordsDao.removeNotifyDataChanged();
        }
        super.onDestroy();
    }

    @Override
    public void showname(String string) {
        EditText editText = findViewById(R.id.edit_query);
        editText.setText(string);
    }
}
