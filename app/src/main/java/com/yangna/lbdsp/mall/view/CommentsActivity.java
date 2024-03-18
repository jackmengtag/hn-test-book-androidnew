package com.yangna.lbdsp.mall.view;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.yangna.lbdsp.R;
import com.yangna.lbdsp.common.base.BaseModel;
import com.yangna.lbdsp.common.base.BasePresenterActivity;
import com.yangna.lbdsp.common.eventbus.BusMallActual;
import com.yangna.lbdsp.common.manager.ToastManager;
import com.yangna.lbdsp.common.utils.AppUtils;
import com.yangna.lbdsp.common.utils.StringUtils;
import com.yangna.lbdsp.home.bean.CommMsg;
import com.yangna.lbdsp.mall.impl.SuggestView;
import com.yangna.lbdsp.mall.presenter.SuggestPresenter;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

public class CommentsActivity extends BasePresenterActivity<SuggestPresenter> implements SuggestView {
    @BindView(R.id.suggest_edt)
    EditText suggestEdt;
    @BindView(R.id.camera_img)

    ImageView cameraImg;
    private String serviceImgUrl = "";

    private String ordersn = "";

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_comments;
    }

    @Override
    protected SuggestPresenter setPresenter() {
        return new SuggestPresenter(this);
    }

    @Override
    protected void initView() {
        super.initView();
        ordersn = getIntent().getStringExtra("ordersn");
        mPresenter.setSuggestView(this);
        titleManager.setTitleTxt("发表评价");
        titleManager.setLeftLayout(0, R.mipmap.back_left_img);
    }

    @OnClick({R.id.camera_img, R.id.sure_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.camera_img:
                break;
            case R.id.sure_btn:
                String suggest = suggestEdt.getText().toString();
                if (StringUtils.isEmpty(suggest) && StringUtils.isEmpty(serviceImgUrl)) {
                    ToastManager.showToast(context, "请输入反馈内容或者上传图片");
                    return;
                }else {
                    mPresenter.getSuggest(context, suggest, ordersn);
                }

        }
    }


    @Override
    public void onSuggestData(BaseModel model) {
        AppUtils.hintKbTwo(context);
        ToastManager.showToast(context, "感谢您的评价");
        finish();
        EventBus.getDefault().post(new BusMallActual());
    }
}
