package com.wwb.laobiao.Search.view;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yangna.lbdsp.R;
import com.yangna.lbdsp.common.base.BasePresenterFragment;
import com.yangna.lbdsp.common.manager.UIManager;

import butterknife.BindView;

//import com.qzy.laobiao.R;
//import com.qzy.laobiao.common.base.BasePresenterFragment;
//import com.qzy.laobiao.common.manager.UIManager;

public class SearchFragment extends BasePresenterFragment {
    //https://blog.csdn.net/qq_21153627/article/details/70156831
//    @BindView(R.id.search_id)
//    TextView searchView;
//    @BindView(R.id.button2)
//     Button mine_userid;
//                <ImageView
//    android:visibility="gone"
//    android:id="@+id/search_iv_search"
//    @BindView(R.id.search_iv_search)
//    ImageView searchsearch;
//    @BindView(R.id.search_iv_delete)
//    ImageView ivDelete;
//    @BindView(R.id.search_et_input)
//    EditText etInput;
//    @BindView(R.id.search_btn_back)
//    Button searchback;
    @BindView(R.id.edit_query)
       EditText etInput;
    @BindView(R.id.iv_search)
    TextView textView;
//    @BindView(R.id.text_query)
//    TextView ttInput;
    @BindView(R.id.layid0)
    LinearLayout mlay0;
    private View.OnClickListener SearchFragmentclkls=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.search_et_input:
//                    lvTips.setVisibility(VISIBLE);
                    break;
                case R.id.search_iv_delete:
//                    etInput.setText("");
//                    ivDelete.setVisibility(GONE);
                    break;
                case R.id.search_btn_back:
//                    ((Activity) mContext).finish();
                    break;
            }
        }
    };

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_search;
    }
    @Override
    protected void initView() {
        super.initView();
//        LinearLayoutSearch linearLayoutSearch=new LinearLayoutSearch(getContext());
//        mlay0.removeAllViews();
//        Layoutshow(linearLayoutSearch);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // UIManager.switcher(context, TestActivity2.class);
                if(mListener!=null)
                {
                    mListener.close();
                }
            }
        });
        mlay0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UIManager.switcher(context, SearchActivity1.class);
            }
        });
        etInput.setFocusable(false);
        etInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UIManager.switcher(context, SearchActivity1.class);
            }
        });
//        ttInput.setVisibility(View.VISIBLE);
//        etInput.setVisibility(View.GONE);
//        etInput.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                UIManager.switcher(context, TestActivity2.class);
//                return false;
//            }
//        });
//        mlay0;
//        searchsearch.setOnClickListener(SearchFragmentclkls);  fragment_search
//        ivDelete.setOnClickListener(SearchFragmentclkls);
//        {
//            etInput.setTextColor(Color.WHITE);
//            etInput.addTextChangedListener(new EditChangedListener());
//            etInput.setOnClickListener(SearchFragmentclkls);
//            etInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//                @Override
//                public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
//                    if (actionId == EditorInfo.IME_ACTION_SEARCH) {
//                        lvTips.setVisibility(GONE);
//                        notifyStartSearching(etInput.getText().toString());
//                    }
//                    return true;
//                }
//            });
//        }
    }

    private void Layoutshow(View view) {
        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);// 定义布局管理器的参数
        mlay0.setOrientation(LinearLayout.VERTICAL);// 所有组件垂直摆放
        // 定义显示组件的布局管理器，为了简单，本次只定义一个TextView组件
        LinearLayout.LayoutParams text = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);// 定义文本显示组件
        view.setLayoutParams(text);// 配置文本显示组件的参数
        mlay0.removeAllViews();
        mlay0.addView(view, text);
    }
    private SearchViewListener mListener;
    /**
     * 设置搜索回调接口
     *
     * @param listener 监听者
     */
    public void setSearchViewListener(SearchViewListener listener) {
        mListener = listener;
    }
    private void notifyStartSearching(String toString) {
        if (mListener != null) {
//            mListener.onSearch(etInput.getText().toString());
        }
        //隐藏软键盘
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    private class EditChangedListener implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }
        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            if (!"".equals(charSequence.toString())) {
//                ivDelete.setVisibility(View.VISIBLE);
//                searchsearch.setVisibility(View.GONE);
//                lvTips.setVisibility(View.VISIBLE);
//                if (mAutoCompleteAdapter != null && lvTips.getAdapter() != mAutoCompleteAdapter) {
//                    lvTips.setAdapter(mAutoCompleteAdapter);
//                }
                //更新autoComplete数据
                if (mListener != null) {
                    mListener.onRefreshAutoComplete(charSequence + "");
                }
            } else {
//                ivDelete.setVisibility(View.GONE);
//                searchsearch.setVisibility(View.VISIBLE);

//                if (mHintAdapter != null) {
//                    lvTips.setAdapter(mHintAdapter);
//                }
//                lvTips.setVisibility(GONE);
            }
        }
        @Override
        public void afterTextChanged(Editable editable) {
        }
    }
}
