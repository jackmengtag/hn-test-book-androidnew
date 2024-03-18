package com.yangna.lbdsp.mine.view;


import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yangna.lbdsp.R;
import com.yangna.lbdsp.common.base.BasePresenterFragment;
import com.yangna.lbdsp.home.model.VideoListModel;
import com.yangna.lbdsp.mine.adapter.WorkAdapter;
import com.yangna.lbdsp.mine.impl.PersonalView;
import com.yangna.lbdsp.mine.model.BasePageRequestParam;
import com.yangna.lbdsp.mine.presenter.HomepagePresenter;

import java.util.ArrayList;

import butterknife.BindView;

/**
 *
 */
public class MineLikeVideoFragment extends BasePresenterFragment<HomepagePresenter> implements PersonalView {

    @BindView(R.id.mine_like_recyclerView)
    RecyclerView recyclerView;
    private WorkAdapter workAdapter;
    private RecyclerView.LayoutManager gridlayoutManager;

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_mine_like_video;
    }

    @Override
    protected void initView() {
        super.initView();
        BasePageRequestParam requestParam=new BasePageRequestParam();
        requestParam.setPageSize("1");
        requestParam.setCurrentPage("4");
        mPresenter.setPersonalView(this);
        mPresenter.getUserLoveVideoList(context,requestParam);
        gridlayoutManager = new GridLayoutManager(getContext(), 3);
        recyclerView.setLayoutManager(gridlayoutManager);
        workAdapter = new WorkAdapter(getActivity(), new ArrayList<>());
        recyclerView.setAdapter(workAdapter);
    }

    @Override
    protected HomepagePresenter setPresenter() {
        return new HomepagePresenter(context);
    }

    @Override
    public void onInitData(VideoListModel VideoListModel) {

    }

    @Override
    public void onMineVideo(VideoListModel videoListModel) {
        
    }

    @Override
    public void onUserLoveVideo(VideoListModel videoListModel) {
        workAdapter.setDataList(videoListModel.getBody().getRecords());
        workAdapter.setNewData(videoListModel.getBody().getRecords());
    }
}
