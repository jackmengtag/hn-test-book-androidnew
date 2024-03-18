package com.yangna.lbdsp.mine.view;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yangna.lbdsp.R;
import com.yangna.lbdsp.common.base.BaseFragment;
import com.yangna.lbdsp.mine.adapter.WorkAdapter;
import com.yangna.lbdsp.videoCom.DataCreate;

import butterknife.BindView;

/**
 * create on 2020-05-19
 * description 个人作品fragment
 */
public class WorkFragment extends BaseFragment {
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;
    private WorkAdapter workAdapter;
    private RecyclerView.LayoutManager gridlayoutManager;

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_work;
    }

    @Override
    protected void initView() {
        super.initView();
        gridlayoutManager = new GridLayoutManager(getContext(), 3);
        recyclerView.setLayoutManager(gridlayoutManager);
        workAdapter = new WorkAdapter(getContext(), DataCreate.datas);
        recyclerView.setAdapter(workAdapter);
    }
}
