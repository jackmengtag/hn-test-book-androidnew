package com.wwb.laobiao.cangye.view;

import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.wwb.laobiao.cangye.model.Controller;
import com.yangna.lbdsp.R;
import com.yangna.lbdsp.common.base.BasePresenterFragment;

import butterknife.BindView;

/**
 *
 * 消息fragment
 */
public class AgreeFragment extends BasePresenterFragment {
    @BindView(R.id.iv_content)
    TextView tcontent;//  \\
    @BindView(R.id.iv_ok)
    Button buttonok;
    @BindView(R.id.iv_cancel)
    Button buttoncancel;
    public AgreeFragmentInterface  agreeFragmentInterface;
    private String str7="点击确定则表示同意<font color=\"#59899F\">用户须知</font>";
    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_agree;
    }

    @Override
    protected void initView() {
        super.initView();
        buttonok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            if(agreeFragmentInterface!=null)
               { // \\
                agreeFragmentInterface.onshow(1);
               }
            }
        });
        buttoncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(agreeFragmentInterface!=null)
                {
                    agreeFragmentInterface.onshow(4);
                }
                //ArXCZ-ErQADnn0QY_BN5pdx2LPKhjAJsz-4cwkw2E1xF
                Controller controller=new Controller();
                controller.Disagree(context);
            }
        });
        tcontent.setText(Html.fromHtml(str7));
    }
    public void setinterface(AgreeFragmentInterface agreeFragmentInterface) {
        this.agreeFragmentInterface=agreeFragmentInterface;
    }
    public interface AgreeFragmentInterface {
        void onshow(int idex);
    }
}
