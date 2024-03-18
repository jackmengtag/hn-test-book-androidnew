package com.yangna.lbdsp.common.net;


import com.hn.book.bean.HnBook;
import com.hn.book.model.BookDetailResultModel;
import com.hn.book.model.BookListModel;
import com.hn.book.param.DeleteListParam;
import com.hn.book.param.DeleteParam;
import com.hn.book.param.QueryDetailParam;
import com.hn.book.param.QueryListParam;
import com.wwb.laobiao.Search.impl.Search;
import com.wwb.laobiao.Search.impl.SearchModel;
import com.wwb.laobiao.address.bean.AdressInfor;
import com.wwb.laobiao.address.bean.AdressModel;
import com.wwb.laobiao.address.bean.AreaInfor;
import com.wwb.laobiao.address.bean.AreaModel;
import com.wwb.laobiao.address.bean.CityInfor;
import com.wwb.laobiao.address.bean.CityModel;
import com.wwb.laobiao.address.bean.DelAdressInfor;
import com.wwb.laobiao.address.bean.DelListAdressInfor;
import com.wwb.laobiao.address.bean.ProvinceInfor;
import com.wwb.laobiao.address.bean.ProvinceModel;
import com.wwb.laobiao.address.bean.SelAdressInfor;
import com.wwb.laobiao.address.bean.SelAdressModel;
import com.wwb.laobiao.address.bean.UpAdressInfor;
import com.wwb.laobiao.cangye.bean.ContractByaccountIdInfor;
import com.wwb.laobiao.cangye.bean.ContractByaccountIdModel;
import com.wwb.laobiao.cangye.bean.ContractInfor;
import com.wwb.laobiao.cangye.bean.ContractModel;
import com.wwb.laobiao.cangye.bean.Fans.FansdeleteInfor;
import com.wwb.laobiao.cangye.bean.Fans.FansdeleteModel;
import com.wwb.laobiao.cangye.bean.Fans.FansinsertModel;
import com.wwb.laobiao.cangye.bean.Fans.FansselectAllInfor;
import com.wwb.laobiao.cangye.bean.Fans.FansselectAllModel;
import com.wwb.laobiao.cangye.bean.Fans.FansinsertInfor;
import com.wwb.laobiao.cangye.bean.Friend.FriendapplyInfor;
import com.wwb.laobiao.cangye.bean.Friend.FriendapplyModel;
import com.wwb.laobiao.cangye.bean.Friend.FriendconsentInfor;
import com.wwb.laobiao.cangye.bean.Friend.FriendconsentModel;
import com.wwb.laobiao.cangye.bean.Friend.FrienddeleteInfor;
import com.wwb.laobiao.cangye.bean.Friend.FrienddeleteModel;
import com.wwb.laobiao.cangye.bean.Friend.FriendfindUserInfor;
import com.wwb.laobiao.cangye.bean.Friend.FriendfindUserModel;
import com.wwb.laobiao.cangye.bean.Friend.FriendselectAllInfor;
import com.wwb.laobiao.cangye.bean.Friend.FriendselectAllModel;
import com.wwb.laobiao.cangye.bean.Friend.FrienduntreatedApplyInfor;
import com.wwb.laobiao.cangye.bean.Friend.FrienduntreatedApplyModel;
import com.wwb.laobiao.cangye.bean.Message.MessageFansEreadInfor;
import com.wwb.laobiao.cangye.bean.Message.MessageFansEreadModel;
import com.wwb.laobiao.cangye.bean.Message.MessageFansPageInfor;
import com.wwb.laobiao.cangye.bean.Message.MessageFansPageModel;
import com.wwb.laobiao.cangye.bean.Message.MessageamountInfor;
import com.wwb.laobiao.cangye.bean.Message.MessageamountModel;
import com.wwb.laobiao.cangye.bean.Message.MessagecommentEreadInfor;
import com.wwb.laobiao.cangye.bean.Message.MessagecommentEreadModel;
import com.wwb.laobiao.cangye.bean.Message.MessagecommentPageInfor;
import com.wwb.laobiao.cangye.bean.Message.MessagecommentPageModel;
import com.wwb.laobiao.cangye.bean.Message.MessagepraisEreadInfor;
import com.wwb.laobiao.cangye.bean.Message.MessagepraisEreadModel;
import com.wwb.laobiao.cangye.bean.Message.MessagepraisePageInfor;
import com.wwb.laobiao.cangye.bean.Message.MessagepraisePageModel;
import com.wwb.laobiao.cangye.bean.OrderInfor;
import com.wwb.laobiao.cangye.bean.OrderListInfor;
import com.wwb.laobiao.cangye.bean.OrderListModel;
import com.wwb.laobiao.cangye.bean.OrderModel;
import com.wwb.laobiao.cangye.bean.PayInfor;
import com.wwb.laobiao.cangye.bean.PayModel;
import com.wwb.laobiao.cangye.bean.SubordinateInfor;
import com.wwb.laobiao.cangye.bean.SubordinateModel;
import com.wwb.laobiao.cangye.bean.SuperiorInfor;
import com.wwb.laobiao.cangye.bean.SuperiorModel;
import com.wwb.laobiao.cangye.bean.UserUpgradeModel;
import com.wwb.laobiao.cangye.model.CountSubordinateModel;
import com.wwb.laobiao.hongbao.bean.RankingModel;
import com.wwb.laobiao.hongbao.model.HongbaoModel;
import com.yangna.lbdsp.common.base.BaseModel;
import com.yangna.lbdsp.home.bean.GainVideoBean;
import com.yangna.lbdsp.home.bean.GiveComments;
import com.yangna.lbdsp.home.bean.GiveLikeBean;
import com.yangna.lbdsp.home.bean.OldVideoBean;
import com.yangna.lbdsp.mall.bean.DeteShopCartBean;
import com.yangna.lbdsp.mall.bean.MallGoodsWPBLBaseModel;
import com.yangna.lbdsp.mall.bean.RequestShopProduct;
import com.yangna.lbdsp.home.bean.PubCommentBean;
import com.yangna.lbdsp.home.model.AppSettingModel;
import com.yangna.lbdsp.home.model.DateleVideoModel;
import com.yangna.lbdsp.home.model.GiveCommentsModel;
import com.yangna.lbdsp.home.model.GiveLikeModel;
import com.yangna.lbdsp.home.model.VideoDetailModel;
import com.yangna.lbdsp.home.model.VideoIdModel;
import com.yangna.lbdsp.home.model.VideoListModel;
import com.yangna.lbdsp.home.model.VideoWelfareModel;
import com.yangna.lbdsp.login.bean.Agreeinfor;
import com.yangna.lbdsp.login.bean.CreateFlie;
import com.yangna.lbdsp.login.bean.DisAgreeinfor;
import com.yangna.lbdsp.login.bean.User;
import com.yangna.lbdsp.login.bean.YzCode;
import com.yangna.lbdsp.login.model.AgreeModel;
import com.yangna.lbdsp.login.model.DisAgreeModel;
import com.yangna.lbdsp.login.model.FileModel;
import com.yangna.lbdsp.login.model.LoginModel;
import com.yangna.lbdsp.login.model.UserIconModel;
import com.yangna.lbdsp.login.model.UserInfoModel;
import com.yangna.lbdsp.login.model.YzCodeModel;
import com.yangna.lbdsp.mall.bean.MallOrderTagBean;
import com.yangna.lbdsp.mall.bean.ShopCartBean;
import com.yangna.lbdsp.mall.bean.SuggestBean;
import com.yangna.lbdsp.mall.model.ProductDetailResultModel;
import com.yangna.lbdsp.mall.model.MallOrderListModel;
import com.yangna.lbdsp.mall.model.ShopPingCartModel;
import com.yangna.lbdsp.mall.model.TWebshopProductDetail;
import com.yangna.lbdsp.mall.param.RequestProductDetailParam;
import com.yangna.lbdsp.mine.model.BasePageRequestParam;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * artifact  接口
 */

interface NetService {

    // 登录
    // 返回值
    // {
    //	"body": "eyJhbGciOiJIUzUxMiJ9.eyJsb2dpbl9hY2NvdW50X2tleSI6IjIyOTgyNjJmLTE0NzUtNDY2ZC04OTE4LTI0YzgxYzA5Y2E3MiJ9.GQviaNOf1p6_XAzTY3OiXJozVXBT4maQs8AtlfAGx-UQzbKLz5Q1VgPieH6DV2cQf90eHflefKurzrdy0prbwg",
    //	"state": 200,
    //	"msg": "请求成功"
    //}
    @POST("/account/loginOrRegister")
//account
    Observable<LoginModel> goLogin(@Body User user);

    // 注册
    @POST("/account/register")
    Observable<LoginModel> getRegister(@Body User user);

    // 获取验证码
    // 返回值
    // {
    //    "body": true,
    //    "state": 200,
    //    "msg": "请求成功"
    //}
    @POST("/auth/getVerifyCode")
    Observable<YzCodeModel> checkPhoneCode(@Body YzCode yzCode);

    // 图片上传
    @POST("/auth/uploadFile")
    Observable<LoginModel> getUploadImg(@Body UserIconModel userIconModel);

    // 创建唯一文件名
    @POST("/auth/createVodAuth")
    Observable<FileModel> getFileName(@Body CreateFlie createFlie);


    // 获取视频统计数
    @POST("/api/video/getVideoStatistice")
    Observable<VideoDetailModel> getStatisticalVideo(@Body GiveLikeBean giveLikeBean);


    // 删除自己的视频
    @POST("/api/video/deleteSelfVideo")
    Observable<DateleVideoModel> deletMindVideo(@Body GiveLikeBean giveLikeBean);


    //获取用户信息
//    @POST("/api/account/getAccountInfo")
//    Observable<UserInfoModel> getUserInfo();

    //获取用户信息
    //返回值 2020年11月15日21:06:10
    //{
    //	"body": {
    //		"uuid": "99a409bc-486c-4237-b7da-d15e95ee085c",
    //		"account": {
    //			"id": 8,                                    自增id
    //			"accountName": "9826566431",                老表视频号，仅可修改一次
    //			"mobile": "18978065947",                    注册手机号
    //			"createTime": 1598413744678,
    //			"inviteCode": "rS4e02uW",                   邀请码
    //			"status": "0",                              0正常1冻结2删除
    //			"nickName": "cat",                          昵称
    //			"logo": "/",                                头像
    //			"des": null,                                描述
    //			"area": "lz",                               所在地区
    //			"identityAuth": "U",                        身份认证 U普通用户 G官方用户 V认证用户 S商家用户 E政府机构
    //			"identityName": null,                       认证名称
    //			"promotionCode": null,                      推广码
    //			"relationStatus": null                      关系状态
    //			"userLevel": "0"
    //		},
    //		"loginTime": "2020-10-28 09:31:12",
    //		"expireTime": "2020-10-30 21:31:19",
    //		"ipaddr": "124.226.60.117",
    //		"loginLocation": "广西 柳州",
    //		"browser": "Unknown",
    //		"os": "Unknown",
    //		"platform": null
    //	},
    //	"state": 200,
    //	"msg": "请求成功"
    //}
    @POST("/api/account/getAccountInfo")
    Observable<UserInfoModel> getMineInfo(@Body BasePageRequestParam requestParam);

    @POST("/api/account/openRenWuRedpack")
    Observable<SearchModel> getTestInfo(@Body Search search);

    @POST("/account/searchAccount")
    Observable<SearchModel> getAllInfo();

    // 获取首页视频
    @POST("/api/video/findHomeVideo")
    Observable<UserInfoModel> getHomeVideo();
    // 获取好友视频
//    @POST("/api/video/findFriendsVideo")
//    Observable<VideoIdModel> getUploadVideo();

    // 获取视频统计数
//    @POST("/api/video/getVideoStatistice")
//    Observable<StatisticalVideoModel> getStatisticalVideo(@Body GiveLikeBean giveLikeBean);

    // 上传自己视频
    @POST("/api/video/insertSelfVideo")
    Observable<VideoIdModel> getUploadVideo(@Body OldVideoBean videoBean);

    @POST("/api/account/openRenWuRedpack")
    Observable<HongbaoModel> gethongbao();

    @POST("/video/video/getVideoRankingList")
    Observable<RankingModel> getranking();

    @FormUrlEncoded
    @POST("/api/account/getAccountInfo")
    Observable<UserInfoModel> getMineInfotest(@FieldMap Map<String, String> map);

    //搜索 (@Body User user)  POST /auth/getVerifyCode  //POST /auth/getVerifyCode
///account/searchAccoun
    @Headers("Accept:application/json")
    @POST("/account/searchAccount")
    Observable<SearchModel> getSearch(@Body Search search);

    @POST("/account/getSubordinate")
    Observable<SubordinateModel> getSubordinate(@Body SubordinateInfor user);

    @POST("/account/getSuperior")
    Observable<SuperiorModel> getSuperior(@Body SuperiorInfor user);

    @POST("/api/tWebshopOrder/create")
    Observable<OrderModel> CreateOrder(@Body OrderInfor user);
    // 2021 02 start
    //fans  ......start
    @POST("/tAccountFans/delete")
    Observable<FansdeleteModel> Fansdelete(@Body FansdeleteInfor user);
    @POST("/tAccountFans/insert")
    Observable<FansinsertModel> Fansinsert(@Body FansinsertInfor user);
    @POST("/tAccountFans/selectAll")
    Observable<FansselectAllModel> FansselectAll(@Body FansselectAllInfor user);
    //fans  ......end
    //Friend  ......start
    @POST("/api/tAccountFriend/apply")
    Observable<FriendapplyModel> Friendapply(@Body FriendapplyInfor user);
    @POST("/api/tAccountFriend/consent")
    Observable<FriendconsentModel> Friendconsent(@Body FriendconsentInfor user);
    @POST("/api/tAccountFriend/delete")
    Observable<FrienddeleteModel> Frienddelete(@Body FrienddeleteInfor user);
    @POST("/api/tAccountFriend/findUser")
    Observable<FriendfindUserModel> FriendfindUser(@Body FriendfindUserInfor user);
    @POST("/api/tAccountFriend/selectAll")
    Observable<FriendselectAllModel> FriendselectAll(@Body FriendselectAllInfor user);
    @POST("/api/tAccountFriend/untreatedApply")
    Observable<FrienduntreatedApplyModel> FrienduntreatedApply(@Body FrienduntreatedApplyInfor user);
    //Friend  ......end
    //msg  粉丝消息阅读
    @POST("/api/tAccountMessage/FansEread")
    Observable<MessageFansEreadModel> MessageFansEread(@Body MessageFansEreadInfor user);
    //粉丝列表
	@POST("/api/tAccountMessage/FansPage")
    Observable<MessageFansPageModel> MessageFansPage(@Body MessageFansPageInfor user);
    //查询消息数量
	@POST("/api/tAccountMessage/amount")
    Observable<MessageamountModel> Messageamount(@Body MessageamountInfor user);
    //评论消息阅读
	@POST("/api/tAccountMessage/commentEread")
    Observable<MessagecommentEreadModel> MessagecommentEread(@Body MessagecommentEreadInfor user);
    //评论列表
	@POST("/api/tAccountMessage/commentPage")
    Observable<MessagecommentPageModel> MessagecommentPage(@Body MessagecommentPageInfor user);
    //点赞消息阅读
    @POST("/api/tAccountMessage/praisEread")
    Observable<MessagepraisEreadModel> MessagepraisEread(@Body MessagepraisEreadInfor user);
	//点赞列表
	@POST("/api/tAccountMessage/praisePage")
    Observable<MessagepraisePageModel> MessagepraisePage(@Body MessagepraisePageInfor user);
    //msg
    // 2021 02 end
    @POST("/api/tWebshopOrder/createList")
    Observable<OrderListModel> CreateListOrder(@Body OrderListInfor user);

    @POST("/api/tWebshopOrder/pay")
    Observable<PayModel> PayOrder(@Body PayInfor user);

    @POST("/contract/contract/insertContract")
    Observable<ContractModel> insertContract(@Body ContractInfor user);//insertContract

    @POST("/contract/contract/getContractByaccountId")
    Observable<ContractByaccountIdModel> getContractByaccountId(@Body ContractByaccountIdInfor user);//insertContract

    @POST("/api/userUpgrade")
    Observable<UserUpgradeModel> getUserUpgrade();

    @POST("/account/dealwithAgreement")
//Agree
    Observable<AgreeModel> goAgree(@Body Agreeinfor user);

    @POST("/account/dealwithDisagreement")
//DisAgree
    Observable<DisAgreeModel> goDisAgree(@Body DisAgreeinfor user);

    @POST("/api/account/countSubordinate")
    Observable<CountSubordinateModel> countSubordinate();


    // 点赞
    @POST("/api/video/praiseVideo")
    Observable<GiveLikeModel> getGiveLike(@Body GiveLikeBean giveLikeBean);

    // 获取视频评论
    @POST("/api/video/findVideoComment")
    Observable<GiveCommentsModel> getComments(@Body GiveComments giveComments);

    // 用户写视频评论
    @POST("/api/video/insertVideoComment")
    Observable<GiveLikeModel> WriteComment(@Body PubCommentBean pubCommentBean);

    // 获取首页视频
    @POST("/video/video/findHomeVideo")
    Observable<VideoListModel> getGainVideo(@Body GainVideoBean gainVideoBean);

    // 获取推介视频
    @POST("/tVideoRecommend")
    Observable<VideoListModel> getPromoteVideo(@Body GainVideoBean gainVideoBean);

    // 观看视频福利
    @POST("/api/wallet/add")
    Observable<VideoWelfareModel> getVideoWelfare();

    // 获取自己的视频
    @POST("/api/video/findSelfVideo")
    Observable<VideoListModel> getMineVideo(@Body GainVideoBean gainVideoBean);

    // 获取自己的视频
    @POST("/api/video/getStatisticeVideo")
    Observable<VideoListModel> getStatisticeVideo(@Body BasePageRequestParam requestParam);

    //获取最新版本查询
    @POST("/versions/newest")
    Observable<AppSettingModel> getAppVersion();

    @POST("/account/account/addAdress")
    Observable<AdressModel> addAdress(@Body AdressInfor user);//

    @POST("account/account/delAdress")
    Observable<AdressModel> delAdress(@Body DelAdressInfor user);//

    @POST("account/account/delAdress")
    Observable<BaseModel> DelListAdressInfor(@Body DelListAdressInfor user);//

    @POST("/account/account/selAdress")
    Observable<SelAdressModel> selAdress(@Body SelAdressInfor user);//

    @POST("/account/account/upAdress")
    Observable<AdressModel> upAdress(@Body UpAdressInfor user);//

    @POST("/tWebshopProvince/list")
    Observable<ProvinceModel> shopProvincelist(@Body ProvinceInfor user);//

    @POST("/tWebshopCity/list")
    Observable<CityModel> shopCitylist(@Body CityInfor user);//

    @POST("/tWebshopArea/list")
    Observable<AreaModel> shopArealist(@Body AreaInfor user);//

//    @POST("/account/good/getGoodsByShopId")
    //    @POST("/account/good/getGoodsByShopId")
    @POST("/api/tWebshopProduct")
    Observable<MallGoodsWPBLBaseModel> getGoodsByShopId(@Body RequestShopProduct shopProduct);//

    //获取订单列表
    @POST("/api/tWebshopOrder")
    Observable<MallOrderListModel> getMallAllOrder(@Body MallOrderTagBean mallOrderTagBean);//

    // 创建退货订单
    @POST("/api/tWebshopReturnGoods/create")
    Observable<BaseModel> OnReturnGoods(@Body SuggestBean suggestBean);//

    // 评价商品
    @POST("/api/tWebshopOrder")
    Observable<BaseModel> getSuggest(@Body SuggestBean suggestBean);//

    // 新增评论
    @POST("/api/tWebshopReturnGoods/create")
    Observable<BaseModel> OrderReview(@Body SuggestBean suggestBean);//

    // 退货列表
    @POST("/api/tWebshopReturnGoods")
    Observable<BaseModel> OrderReviewList(@Body SuggestBean suggestBean);//

    // 购物车列表
    @POST("/tWebshopShoppingCard/selectAll")
    Observable<ShopPingCartModel> getShopCart(@Body ShopCartBean shopCartBean);//

    // 删除购物车商品
    @POST("/tWebshopShoppingCard/delete")
    Observable<BaseModel> deteShopCart(@Body DeteShopCartBean deleShopCartBean);//

    @POST("/api/tWebshopProduct/getGoodsDetailByGoodsId")
    Observable<ProductDetailResultModel> getGoodsDetailByGoodsId(@Body RequestProductDetailParam requestParam);


    @POST("/book/hnBook/add")
    Observable<BaseModel> addBook(@Body HnBook book);//

    @POST("/book/hnBook/delete")
    Observable<BaseModel> deleteBook(@Body DeleteParam deleteParam);//

    @POST("/book/hnBook/deleteBatch")
    Observable<BaseModel> deleteBookList(@Body DeleteListParam deleteListParam);//

    @POST("/book/hnBook/list")
    Observable<BookListModel> getBookList(@Body QueryListParam queryListParam);//

    @POST("/book/hnBook/edit")
    Observable<BaseModel> updateBook(@Body HnBook book);//

    @POST("/book/hnBook/queryById")
    Observable<BookDetailResultModel> getBookDetailById(@Body QueryDetailParam queryDetailParam);//


}
