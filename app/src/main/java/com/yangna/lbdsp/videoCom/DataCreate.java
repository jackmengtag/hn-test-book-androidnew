package com.yangna.lbdsp.videoCom;


import com.yangna.lbdsp.R;

import java.util.ArrayList;

/**
 * create by libo
 * create on 2020-06-03
 * description 本地数据创建，代替接口获取数据
 */
public class DataCreate {
    public static ArrayList<VideoBean> datas = new ArrayList<>();
    public static ArrayList<VideoBean.UserBean> userList = new ArrayList<>();

    public void initData() {

        VideoBean videoBeanOne = new VideoBean();
        videoBeanOne.setDistance(7.9f);
        videoBeanOne.setFocused(false);
        videoBeanOne.setShareCount(4252);

        VideoBean.UserBean userBeanOne = new VideoBean.UserBean();
        userBeanOne.setUid(1);
        userBeanOne.setHead(R.mipmap.ic_launcher);
        userBeanOne.setNickName("南京街坊");
        userBeanOne.setSign("你们喜欢的话题，就是我们采访的内容");
        userBeanOne.setSubCount(119323);
        userBeanOne.setFocusCount(482);
        userBeanOne.setFansCount(32823);
        userBeanOne.setWorkCount(42);
        userBeanOne.setDynamicCount(42);
        userBeanOne.setLikeCount(821);

        userList.add(userBeanOne);
        videoBeanOne.setUserBean(userBeanOne);

        VideoBean videoBeanTwo = new VideoBean();
        videoBeanTwo.setDistance(19.7f);
        videoBeanTwo.setFocused(true);
        videoBeanTwo.setShareCount(5892);

        VideoBean.UserBean userBeanTwo = new VideoBean.UserBean();
        userBeanTwo.setUid(2);
        userBeanTwo.setHead(R.mipmap.ic_launcher);
        userBeanTwo.setNickName("民生直通车");
        userBeanTwo.setSign("直通现场新闻，直击社会热点，深入事件背后，探寻事实真相");
        userBeanTwo.setSubCount(20323234);
        userBeanTwo.setFocusCount(244);
        userBeanTwo.setFansCount(1938232);
        userBeanTwo.setWorkCount(123);
        userBeanTwo.setDynamicCount(123);
        userBeanTwo.setLikeCount(344);

        userList.add(userBeanTwo);
        videoBeanTwo.setUserBean(userBeanTwo);

        VideoBean videoBeanThree = new VideoBean();
        videoBeanThree.setDistance(15.9f);
        videoBeanThree.setFocused(false);
        videoBeanThree.setShareCount(982);

        VideoBean.UserBean userBeanThree = new VideoBean.UserBean();
        userBeanThree.setUid(3);
        userBeanThree.setHead(R.mipmap.ic_launcher);
        userBeanThree.setNickName("七叶篮球");
        userBeanThree.setSign("老科的视频会一直保留，想他了就回来看看");
        userBeanThree.setSubCount(1039232);
        userBeanThree.setFocusCount(159);
        userBeanThree.setFansCount(29232323);
        userBeanThree.setWorkCount(171);
        userBeanThree.setDynamicCount(173);
        userBeanThree.setLikeCount(1724);

        userList.add(userBeanThree);
        videoBeanThree.setUserBean(userBeanThree);

        VideoBean videoBeanFour = new VideoBean();
        videoBeanFour.setDistance(25.2f);
        videoBeanFour.setFocused(false);
        videoBeanFour.setShareCount(8924);

        VideoBean.UserBean userBeanFour = new VideoBean.UserBean();
        userBeanFour.setUid(4);
        userBeanFour.setHead(R.mipmap.ic_launcher);
        userBeanFour.setNickName("一只爱修图的剪辑师");
        userBeanFour.setSign("接剪辑，活动拍摄，修图单\n 合作私信");
        userBeanFour.setSubCount(2689424);
        userBeanFour.setFocusCount(399);
        userBeanFour.setFansCount(360829);
        userBeanFour.setWorkCount(562);
        userBeanFour.setDynamicCount(570);
        userBeanFour.setLikeCount(4310);

        userList.add(userBeanFour);
        videoBeanFour.setUserBean(userBeanFour);

        VideoBean videoBeanFive = new VideoBean();
        videoBeanFive.setDistance(9.2f);
        videoBeanFive.setFocused(false);
        videoBeanFive.setShareCount(8923);

        VideoBean.UserBean userBeanFive = new VideoBean.UserBean();
        userBeanFive.setUid(5);
        userBeanFive.setHead(R.mipmap.ic_launcher);
        userBeanFive.setNickName("国际网球联合会");
        userBeanFive.setSign("ITF国际网球联合会负责制定统一的网球规则，在世界范围内普及网球运动");
        userBeanFive.setSubCount(1002342);
        userBeanFive.setFocusCount(87);
        userBeanFive.setFansCount(520232);
        userBeanFive.setWorkCount(89);
        userBeanFive.setDynamicCount(122);
        userBeanFive.setLikeCount(9);

        userList.add(userBeanFive);
        videoBeanFive.setUserBean(userBeanFive);

        VideoBean videoBeanSix = new VideoBean();
        videoBeanSix.setDistance(16.4f);
        videoBeanSix.setFocused(true);
        videoBeanFive.setShareCount(424);

        VideoBean.UserBean userBeanSix = new VideoBean.UserBean();
        userBeanSix.setUid(6);
        userBeanSix.setHead(R.mipmap.ic_launcher);
        userBeanSix.setNickName("罗鑫颖");
        userBeanSix.setSign("一个行走在Tr与剪辑之间的人\n 有什么不懂的可以来直播间问我");
        userBeanSix.setSubCount(29342320);
        userBeanSix.setFocusCount(67);
        userBeanSix.setFansCount(7028323);
        userBeanSix.setWorkCount(5133);
        userBeanSix.setDynamicCount(5159);
        userBeanSix.setLikeCount(0);

        userList.add(userBeanSix);
        videoBeanSix.setUserBean(userBeanSix);

        VideoBean videoBeanSeven = new VideoBean();
        videoBeanSeven.setDistance(16.4f);
        videoBeanSeven.setFocused(false);
        videoBeanSeven.setShareCount(3812);

        VideoBean.UserBean userBeanSeven = new VideoBean.UserBean();
        userBeanSeven.setUid(7);
        userBeanSeven.setHead(R.mipmap.ic_launcher);
        userBeanSeven.setNickName("Sean");
        userBeanSeven.setSign("云深不知处");
        userBeanSeven.setSubCount(471932);
        userBeanSeven.setFocusCount(482);
        userBeanSeven.setFansCount(371423);
        userBeanSeven.setWorkCount(242);
        userBeanSeven.setDynamicCount(245);
        userBeanSeven.setLikeCount(839);

        userList.add(userBeanSeven);
        videoBeanSeven.setUserBean(userBeanSeven);

        VideoBean videoBeanEight = new VideoBean();
        videoBeanEight.setDistance(8.4f);
        videoBeanEight.setFocused(false);
        videoBeanEight.setShareCount(982);

        VideoBean.UserBean userBeanEight = new VideoBean.UserBean();
        userBeanEight.setUid(8);
        userBeanEight.setHead(R.mipmap.ic_launcher);
        userBeanEight.setNickName("曹小宝");
        userBeanEight.setSign("一个晒娃狂魔麻麻，平日里没啥爱好！喜欢拿着手机记录孩子成长片段，风格不喜勿喷！");
        userBeanEight.setSubCount(1832342);
        userBeanEight.setFocusCount(397);
        userBeanEight.setFansCount(1394232);
        userBeanEight.setWorkCount(164);
        userBeanEight.setDynamicCount(167);
        userBeanEight.setLikeCount(0);

        userList.add(userBeanEight);
        videoBeanEight.setUserBean(userBeanEight);


        datas.add(videoBeanFive);
        datas.add(videoBeanSix);
        datas.add(videoBeanSeven);
        datas.add(videoBeanEight);
        datas.add(videoBeanOne);
        datas.add(videoBeanTwo);
        datas.add(videoBeanThree);
        datas.add(videoBeanFour);


    }
}
