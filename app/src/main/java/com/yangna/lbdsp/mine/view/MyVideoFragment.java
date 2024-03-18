package com.yangna.lbdsp.mine.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yangna.lbdsp.R;
import com.yangna.lbdsp.common.base.BasePresenterFragment;
import com.yangna.lbdsp.common.eventbus.BusMallActual;
import com.yangna.lbdsp.common.eventbus.BusMineVideo;
import com.yangna.lbdsp.home.model.VideoListModel;
import com.yangna.lbdsp.mine.adapter.WorkAdapter;
import com.yangna.lbdsp.mine.impl.PersonalView;
import com.yangna.lbdsp.mine.presenter.HomepagePresenter;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 * @author Administrator
 */
public class MyVideoFragment extends BasePresenterFragment<HomepagePresenter> implements PersonalView {
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;
    private WorkAdapter workAdapter;
    private RecyclerView.LayoutManager gridlayoutManager;

    private static Integer videoType;


    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_my_video;
    }

    @Override
    protected void initView() {
        super.initView();
        mPresenter.setPersonalView(this);
        mPresenter.getMineInfo(context);
        gridlayoutManager = new GridLayoutManager(getContext(), 3);
        recyclerView.setLayoutManager(gridlayoutManager);
        workAdapter = new WorkAdapter(getActivity(), new ArrayList<>());
        recyclerView.setAdapter(workAdapter);

    }

    @Override
    protected boolean enableEventBus() {
        return true;
    }

    //EventBus接收消息
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getEventBus(BusMineVideo busMineVideo) {
            mPresenter.getMineInfo(context);

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
        workAdapter.setDataList(videoListModel.getBody().getRecords());
        workAdapter.setNewData(videoListModel.getBody().getRecords());
    }

    @Override
    public void onUserLoveVideo(VideoListModel videoListModel) {
        workAdapter.setDataList(videoListModel.getBody().getRecords());
        workAdapter.setNewData(videoListModel.getBody().getRecords());
    }

}
