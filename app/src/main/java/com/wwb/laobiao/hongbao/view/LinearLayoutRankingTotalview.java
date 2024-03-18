package com.wwb.laobiao.hongbao.view;

import android.content.Context;
import android.os.Build;
import android.text.Html;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.wwb.laobiao.hongbao.adapter.Ranking;
import com.wwb.laobiao.hongbao.adapter.RankingAdapter;
import com.wwb.laobiao.hongbao.bean.RankingModel;
import com.yangna.lbdsp.R;

import java.util.ArrayList;
import java.util.List;

//import com.qzy.laobiao.R;
//import com.qzy.laobiao.hongbao.adapter.Ranking;
//import com.qzy.laobiao.hongbao.adapter.RankingAdapter;
//import com.qzy.laobiao.hongbao.bean.RankingModel;

public class LinearLayoutRankingTotalview extends LinearLayout {
    private LayoutInflater inflater;
    private LinearLayoutHeadviewnob1 linearLayoutHeadviewnob1;
    private LinearLayoutHeadviewnob2 linearLayoutHeadviewnob2;
    private LinearLayoutHeadviewnob3 linearLayoutHeadviewnob3;
    private TextView textView;
    private RecyclerView recyclerView;//比心榜比赛规则
//    private String str7="1参赛对象：App内的全体用户\n" +
//            "2参赛形式：单个视频号\n" +
//            "3.报名方式：用户在App内发布的所有视频即视为自动参赛\n" +
//            "4.竞赛流程\n" +
//            "(1)报名阶段：11月1日-11月30日,登录老表短视频App并发布视频即可进行自动报名。\n" +
//            "(2)选拔赛阶段：12月1日-12月21日，根据点赞量进行排名。\n" +
//            "(3)冠军赛阶段：12月30日-31日根据最后一天点赞量最高的选手即可获得冠军、亚军和季军。\n" +
//            "(4)发布奖励：冠亚季军分出排名后，奖励将在工作日的三天内自动发往视频账号的账户，由用户自行进行提现。\n" +
//            "奖励如下：\n" +
//            "一等奖30000元。\n" +
//            "二等奖15000元。\n" +
//            "三等奖10000元。\n" +
//            "\n" +
//            "参赛规则\n" +
//            "1.\t每个用户只选取点赞量最高的一条视频参加决赛。\n" +
//            "2.\t参赛作品内容不限，但要求为个人原创，作品涉及的著作权、肖像权等法律责任由用户自负。\n" +
//            "3.\t参赛作品不得出现广告内容、外部商业网站链接，且内容积极健康，无任何涉黄涉暴、反党反社会等违规情节。如有给他人造成经济损失及对社会产生不良影响，由参赛用户个人承担一切由此引起的法律责任。\n" +
//            "4.\t参赛用户需进行并完成实名认证。参赛用户之间需和平共处，公平竞争，允许公开友好的拉票行为，不得使用不当手段获取其他用户的点赞。\n" +
//            "5.\t所有的参赛作品无论获奖与否，大赛组织者有根据需要，进行编辑、发表的权利。\n" +
//            "6.\t最终解释权归老表短视频App所有，如有疑问可拨打0772-3106005。\n";
private String str7="1参赛对象：App内的全体用户<br />\n" +
        "2参赛形式：单个视频号<br />\n" +
        "3.报名方式：用户在App内发布的所有视频即视为自动参赛<br />\n" +
        "4.竞赛流程<br />\n" +
        "(1)报名阶段：11月1日-11月30日,登录老表短视频App并发布视频即可进行自动报名。<br />\n" +
        "(2)选拔赛阶段：12月1日-12月21日，根据点赞量进行排名。<br />\n" +
        "(3)冠军赛阶段：12月30日-31日根据最后一天点赞量最高的选手即可获得冠军、亚军和季军。<br />\n" +
        "(4)发布奖励：冠亚季军分出排名后，奖励将在工作日的三天内自动发往视频账号的账户，由用户自行进行提现。<br />\n" +
        "奖励如下：<font color=\"#cc3300\"><br />\n" +
        "一等奖30000元。<br />\n" +
        "二等奖15000元。<br />\n" +
        "三等奖10000元。<br />\n" +
        "</font><br />\n" +
        "参赛规则<br />\n" +
        "1.\t每个用户只选取点赞量最高的一条视频参加决赛。<br />\n" +
        "2.\t参赛作品内容不限，但要求为个人原创，作品涉及的著作权、肖像权等法律责任由用户自负。<br />\n" +
        "3.\t参赛作品不得出现广告内容、外部商业网站链接，且内容积极健康，无任何涉黄涉暴、反党反社会等违规情节。如有给他人造成经济损失及对社会产生不良影响，由参赛用户个人承担一切由此引起的法律责任。<br />\n" +
        "4.\t参赛用户需进行并完成实名认证。参赛用户之间需和平共处，公平竞争，允许公开友好的拉票行为，不得使用不当手段获取其他用户的点赞。<br />\n" +
        "5.\t所有的参赛作品无论获奖与否，大赛组织者有根据需要，进行编辑、发表的权利。<br />\n" +
        "6.\t最终解释权归老表短视频App所有，如有疑问可拨打0772-3106005。<br />\n";
    private ArrayList<Ranking> rankings;
    public LinearLayoutRankingTotalview(Context context) {
        super(context);
        init(context,null);
    }
    public LinearLayoutRankingTotalview(Context context, @Nullable AttributeSet attrs) {
        super(context,attrs);
        init(context,attrs);
    }
    private void init(Context context, AttributeSet attrs) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.layout_ranking_total, this);
        textView=findViewById(R.id.iv_rule);
//        textView.setText(str7);
        textView.setText(Html.fromHtml(str7));
        recyclerView=findViewById(R.id.iv_recy);
        LinearLayoutManager layout = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layout);

        rankings=new ArrayList<>();
        RankingAdapter adapter = new RankingAdapter(rankings);
//        getranking(rankings);
        recyclerView.setAdapter(adapter);
//        recyclerView.setBackground();
        linearLayoutHeadviewnob1=findViewById(R.id.iv_rank1_head);
        linearLayoutHeadviewnob2=findViewById(R.id.iv_rank2_head);
        linearLayoutHeadviewnob3=findViewById(R.id.iv_rank3_head);
        setheadBackground();
    }
    private void setheadBackground() {
        linearLayoutHeadviewnob1.setImageResource(R.drawable.wxbg);
        linearLayoutHeadviewnob2.setImageResource(R.drawable.id_014);
        linearLayoutHeadviewnob3.setImageResource(R.drawable.id_014);
    }
    private void getranking(List<Ranking> rankings) {
        {
            Ranking ranking=new Ranking();
            ranking.ranknum=4;
            ranking.name="谁知女人心";
            ranking.usid="888666";
            ranking.diancannum=988899;
            rankings.add(ranking);
        }
        {
            Ranking ranking=new Ranking();
            ranking.ranknum=5;
            ranking.name="谁知女人心";
            ranking.usid="888666";
            ranking.diancannum=99999999;
            rankings.add(ranking);
        }
        {
            Ranking ranking=new Ranking();
            ranking.ranknum=6;
            ranking.name="谁知女人心";
            ranking.usid="888666";
            ranking.diancannum=99999999;
            rankings.add(ranking);
        }
        {
            Ranking ranking=new Ranking();
            ranking.ranknum=7;
            ranking.name="谁知女人心";
            ranking.usid="888666";
            ranking.diancannum=99999999;
            rankings.add(ranking);
        }
        {
            Ranking ranking=new Ranking();
            ranking.ranknum=8;
            ranking.name="谁知女人心";
            ranking.usid="888666";
            ranking.diancannum=99999999;
            rankings.add(ranking);
        }
        {
            Ranking ranking=new Ranking();
            ranking.ranknum=9;
            ranking.name="谁知女人心";
            ranking.usid="888666";
            ranking.diancannum=99999999;
            rankings.add(ranking);
        }
        {
            Ranking ranking=new Ranking();
            ranking.ranknum=10;
            ranking.name="谁知女人心";
            ranking.usid="888666";
            ranking.diancannum=99999999;
            rankings.add(ranking);
        }
    }

    public LinearLayoutRankingTotalview(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public LinearLayoutRankingTotalview(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context,attrs);
    }
    public void setranking(RankingModel rankingModel) {
        if(rankingModel==null)
        {
            return;
        }
        if(rankingModel.body==null)
        {
            return;
        }
        rankings.clear();
        for(int i=3;i<rankingModel.body.size();i++)
        {
            Ranking ranking=new Ranking();
            ranking.ranknum=i+1;
            ranking.name=rankingModel.body.get(i).nickName;
            ranking.usid=String.format("%d",rankingModel.body.get(i).accountId);
            ranking.diancannum=rankingModel.body.get(i).praiseTotal;
            rankings.add(ranking);
        }
        RankingAdapter adapter= (RankingAdapter) recyclerView.getAdapter();
        adapter.notifyDataSetChanged();

        if(rankingModel.body.size()<1)
        {
            return;
        }
        linearLayoutHeadviewnob1.setranking(rankingModel.body.get(0));
        if(rankingModel.body.size()<2)
        {
            return;
        }
        linearLayoutHeadviewnob2.setranking(rankingModel.body.get(1));
        if(rankingModel.body.size()<3)
        {
            return;
        }
        linearLayoutHeadviewnob3.setranking(rankingModel.body.get(2));
    }
}
