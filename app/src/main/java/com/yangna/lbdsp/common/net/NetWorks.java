package com.yangna.lbdsp.common.net;

import android.content.Context;

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
import com.wwb.laobiao.cangye.bean.Fans.FansselectAllModel;
import com.wwb.laobiao.cangye.bean.Fans.FansinsertInfor;
import com.wwb.laobiao.cangye.bean.Fans.FansselectAllInfor;
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
import com.yangna.lbdsp.home.bean.PubCommentBean;
import com.yangna.lbdsp.mall.bean.DeteShopCartBean;
import com.yangna.lbdsp.mall.bean.MallGoodsWPBLBaseModel;
import com.yangna.lbdsp.mall.bean.RequestShopProduct;
import com.yangna.lbdsp.home.model.AppSettingModel;
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

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * artifact  NetWorks
 */

public class NetWorks extends RetrofitUtils {

    private static NetWorks instance;
    private NetService service;

    /**
     * 构造
     */
    private NetWorks() {
        service = getRetrofit().create(NetService.class);
    }

    /**
     * 单例
     */
    public static NetWorks getInstance() {
        if (instance == null) {
            synchronized (NetWorks.class) {
                if (instance == null) {
                    instance = new NetWorks();
                }
            }
        }
        return instance;
    }

    /**
     * 通用订阅
     *
     * @param observable
     * @param observer
     * @param <T>
     */
    private <T extends BaseModel> void setSubscribe(Context context, Observable<T> observable, Observer<T> observer) {
        observable.subscribeOn(Schedulers.io())
                .flatMap(new RxHttpResult<>(context))
                .subscribeOn(Schedulers.newThread())//子线程访问网络
                .observeOn(AndroidSchedulers.mainThread())//回调到主线程
                .subscribe(observer);
    }


    //登录
    public void goLogin(Context context, User user, Observer<LoginModel> observer) {
        setSubscribe(context, service.goLogin(user), observer);
    }
//
//    //检查手机号是否存在
//    public void checkPhone(Context context, Map<String, String> map, Observer<BaseModel> observer) {
//        setSubscribe(context, service.checkPhone(map), observer);
//    }

    //检查短信是否输入正确
    public void checkPhoneCode(Context context, YzCode yzCode, Observer<YzCodeModel> observer) {
        setSubscribe(context, service.checkPhoneCode(yzCode), observer);
    }

    //意见反馈--上传图片
    public void getUploadImg(Context context, UserIconModel userIconModel, Observer<LoginModel> observer) {
        setSubscribe(context, service.getUploadImg(userIconModel), observer);
    }

    //创建唯一文件名
    public void getFile(Context context, CreateFlie createFlie, Observer<FileModel> observer) {
        setSubscribe(context, service.getFileName(createFlie), observer);
    }

    //获取用户信息
    public void getMineInfo(Context context, BasePageRequestParam requestParam, Observer<UserInfoModel> observer) {
        setSubscribe(context, service.getMineInfo(requestParam), observer);
    }

    public void getAllInfo(Context context, Search search, Observer<SearchModel> observer) {
        setSubscribe(context, service.getAllInfo(), observer);
    }

    public void gethongbao(Context context, MyObserver<HongbaoModel> observerx) {
        setSubscribe(context, service.gethongbao(), observerx);
    }

    public void getranking(Context context, MyObserver<RankingModel> observerx) {
        setSubscribe(context, service.getranking(), observerx);
    }

    public void countSubordinate(Context context, MyObserver<CountSubordinateModel> observerx) {
        setSubscribe(context, service.countSubordinate(), observerx);
    }

    public void getuserUpgrade(Context context, MyObserver<UserUpgradeModel> observerx) {
        setSubscribe(context, service.getUserUpgrade(), observerx);
    }

    public void getSubordinate(Context context, SubordinateInfor user, MyObserver<SubordinateModel> observerx) {
        setSubscribe(context, service.getSubordinate(user), observerx);
    }

    public void getSuperior(Context context, SuperiorInfor user, MyObserver<SuperiorModel> observerx) {
        setSubscribe(context, service.getSuperior(user), observerx);
    }
    //2021 02 star
    // fans
    public void Fansinsert(Context context, FansinsertInfor user, MyObserver<FansinsertModel> observerx) {
        setSubscribe(context, service.Fansinsert(user), observerx);
    }
    public void Fansdelete(Context context, FansdeleteInfor user, MyObserver<FansdeleteModel> observerx) {
        setSubscribe(context, service.Fansdelete(user), observerx);
    }
    public void FansselectAll(Context context, FansselectAllInfor user, MyObserver<FansselectAllModel> observerx) {
        setSubscribe(context, service.FansselectAll(user), observerx);
    }
    //Friend
    public void Friendapply(Context context, FriendapplyInfor user, MyObserver<FriendapplyModel> observerx) {
        setSubscribe(context, service.Friendapply(user), observerx);
    }
    public void Friendconsent(Context context, FriendconsentInfor user, MyObserver<FriendconsentModel> observerx) {
        setSubscribe(context, service.Friendconsent(user), observerx);
    }
    public void Frienddelete(Context context, FrienddeleteInfor user, MyObserver<FrienddeleteModel> observerx) {
        setSubscribe(context, service.Frienddelete(user), observerx);
    }
    public void FriendfindUser(Context context, FriendfindUserInfor user, MyObserver<FriendfindUserModel> observerx) {
        setSubscribe(context, service.FriendfindUser(user), observerx);
    }    
    public void FriendselectAll(Context context, FriendselectAllInfor user, MyObserver<FriendselectAllModel> observerx) {
        setSubscribe(context, service.FriendselectAll(user), observerx);
    }
    public void FrienduntreatedApply(Context context, FrienduntreatedApplyInfor user, MyObserver<FrienduntreatedApplyModel> observerx) {
        setSubscribe(context, service.FrienduntreatedApply(user), observerx);
    }

    //msg  粉丝消息阅读
    public void MessageFansEread(Context context, MessageFansEreadInfor user, MyObserver<MessageFansEreadModel> observerx){
         	setSubscribe(context, service.MessageFansEread(user), observerx);
    }
    //粉丝列表
    public void  MessageFansPage(Context context, MessageFansPageInfor user, MyObserver<MessageFansPageModel> observerx){
			setSubscribe(context, service.MessageFansPage(user), observerx);
    }
    //查询消息数量
    public void  Messageamount(Context context, MessageamountInfor user, MyObserver<MessageamountModel> observerx){
			setSubscribe(context, service.Messageamount(user), observerx);
    }
    //评论消息阅读
    public void  MessagecommentEread(Context context, MessagecommentEreadInfor user, MyObserver<MessagecommentEreadModel> observerx){
			setSubscribe(context, service.MessagecommentEread(user), observerx);
    }
    //评论列表
    public void  MessagecommentPage(Context context, MessagecommentPageInfor user, MyObserver<MessagecommentPageModel> observerx){
			setSubscribe(context, service.MessagecommentPage(user), observerx);
    }
    //点赞消息阅读
    public void  MessagepraisEread(Context context, MessagepraisEreadInfor user, MyObserver<MessagepraisEreadModel> observerx){
			setSubscribe(context, service.MessagepraisEread(user), observerx);
    }
	//点赞列表
    public void  MessagepraisePage(Context context, MessagepraisePageInfor user, MyObserver<MessagepraisePageModel> observerx){
			setSubscribe(context, service.MessagepraisePage(user), observerx);
    } 

    //2021 02 end
    public void CreateOrder(Context context, OrderInfor user, MyObserver<OrderModel> observerx) {
        setSubscribe(context, service.CreateOrder(user), observerx);
    }
    public void CreateListOrder(Context context, OrderListInfor user, MyObserver<OrderListModel> observerx) {
        setSubscribe(context, service.CreateListOrder(user), observerx);
    }
    public void Orderdetail(Context context, PayInfor user, MyObserver<PayModel> observerx) {
        setSubscribe(context, service.PayOrder(user), observerx);
    }
    public void PayOrder(Context context, PayInfor user, MyObserver<PayModel> observerx) {
        setSubscribe(context, service.PayOrder(user), observerx);
    }
    public void addAdress(Context context, AdressInfor user, MyObserver<AdressModel> observerx) {
        setSubscribe(context, service.addAdress(user), observerx);
    }

    public void delAdress(Context context, DelAdressInfor user, MyObserver<AdressModel> observerx) {
        setSubscribe(context, service.delAdress(user), observerx);
    }

    public void DelListAdressInfor(Context context, DelListAdressInfor user, MyObserver<BaseModel> observerx) {
        setSubscribe(context, service.DelListAdressInfor(user), observerx);
    }
    public void selAdress(Context context, SelAdressInfor user, MyObserver<SelAdressModel> observerx) {
        setSubscribe(context, service.selAdress(user), observerx);
    }
    public void upAdress(Context context, UpAdressInfor user, MyObserver<AdressModel> observerx) {
        setSubscribe(context, service.upAdress(user), observerx);
    }
    public void shopProvincelist(Context context, ProvinceInfor user, MyObserver<ProvinceModel> observerx) {
        setSubscribe(context, service.shopProvincelist(user), observerx);
    }
    public void shopCitylist(Context context, CityInfor user, MyObserver<CityModel> observerx) {
        setSubscribe(context, service.shopCitylist(user), observerx);
    }
    public void shopArealist(Context context, AreaInfor user, MyObserver<AreaModel> observerx) {
        setSubscribe(context, service.shopArealist(user), observerx);
    }
    public void insertContract(Context context, ContractInfor user, MyObserver<ContractModel> observerx) {
        setSubscribe(context, service.insertContract(user), observerx);
    }

    public void getContractByaccountId(Context context, ContractByaccountIdInfor user, MyObserver<ContractByaccountIdModel> observerx) {
        setSubscribe(context, service.getContractByaccountId(user), observerx);
    }

    //上传用户视频信息
    public void getUploadVideo(Context context, OldVideoBean oldVideoBean, Observer<VideoIdModel> observer) {
        setSubscribe(context, service.getUploadVideo(oldVideoBean), observer);
    }

    //获取版本
    public void getAppVersion(Context context, Observer<AppSettingModel> observer) {
        setSubscribe(context, service.getAppVersion(), observer);
    }

    //点赞
    public void getGiveLike(Context context, GiveLikeBean giveLikeBean, Observer<GiveLikeModel> observer) {
        setSubscribe(context, service.getGiveLike(giveLikeBean), observer);
    }

    //获取评论
    public void getComments(Context context, GiveComments giveComments, Observer<GiveCommentsModel> observer) {
        setSubscribe(context, service.getComments(giveComments), observer);
    }

    //写评论
    public void WriteComment(Context context, PubCommentBean pubCommentBean, Observer<GiveLikeModel> observer) {
        setSubscribe(context, service.WriteComment(pubCommentBean), observer);
    }

    //点赞
    public void getStatisticalVideo(Context context, GiveLikeBean user, Observer<VideoDetailModel> observer) {
        setSubscribe(context, service.getStatisticalVideo(user), observer);
    }

    //获取首页视频
    public void getGainVideo(Context context, GainVideoBean gainVideoBean, Observer<VideoListModel> observer) {
        setSubscribe(context, service.getGainVideo(gainVideoBean), observer);
    }

    //获取推介视频
    public void getPromoteVideo(Context context, GainVideoBean gainVideoBean, Observer<VideoListModel> observer) {
        setSubscribe(context, service.getPromoteVideo(gainVideoBean), observer);
    }

    //获取自己视频
    public void getMineVideo(Context context, GainVideoBean gainVideoBean, Observer<VideoListModel> observer) {
        setSubscribe(context, service.getMineVideo(gainVideoBean), observer);
    }

    //看视频获取金币
    public void getVideoWelfare(Context context, Observer<VideoWelfareModel> observer) {
        setSubscribe(context, service.getVideoWelfare(), observer);
    }

    //注册
    public void getRegister(Context context, User user, Observer<LoginModel> observer) {
        setSubscribe(context, service.getRegister(user), observer);
    }


    public void getSearch(Context context, Search search, MyObserver<SearchModel> observerx) {
        setSubscribe(context, service.getSearch(search), observerx);
    }

    //同意
    public void goAgree(Context context, Agreeinfor user, Observer<AgreeModel> observer) {
        setSubscribe(context, service.goAgree(user), observer);
    }

    //不同意
    public void goDisAgree(Context context, DisAgreeinfor user, Observer<DisAgreeModel> observer) {
        setSubscribe(context, service.goDisAgree(user), observer);
    }

    //获取店铺商品列表
    public void getGoodsByShopId(Context context, RequestShopProduct shopProduct, Observer<MallGoodsWPBLBaseModel> observer) {
        setSubscribe(context, service.getGoodsByShopId(shopProduct), observer);
    }


    //获取订单列表
    public void getMallAllOrder(Context context, MallOrderTagBean mallOrderTagBean, Observer<MallOrderListModel> observer) {
        setSubscribe(context, service.getMallAllOrder(mallOrderTagBean), observer);
    }

    //创建退货订单
    public void OnReturnGoods(Context context, SuggestBean suggestBean, Observer<BaseModel> observer) {
        setSubscribe(context, service.OnReturnGoods(suggestBean), observer);
    }

    //评价商品
    public void getSuggest(Context context, SuggestBean suggestBean, Observer<BaseModel> observer) {
        setSubscribe(context, service.getSuggest(suggestBean), observer);
    }

    //购物车列表
    public void getShopCart(Context context, ShopCartBean shopCartBean, Observer<ShopPingCartModel> observer) {
        setSubscribe(context, service.getShopCart(shopCartBean), observer);
    }

    //购物车列表
    public void deteShopCart(Context context, DeteShopCartBean deteShopCartBean, Observer<BaseModel> observer) {
        setSubscribe(context, service.deteShopCart(deteShopCartBean), observer);
    }


    //获取自己点赞过的视频
    public void getStatisticeVideo(Context context, BasePageRequestParam requestParam, Observer<VideoListModel> observer) {
        setSubscribe(context, service.getStatisticeVideo(requestParam), observer);
    }

    //获取商品详情
    public void getGoodsDetailByGoodsId(Context context, RequestProductDetailParam requestParam, Observer<ProductDetailResultModel> observer) {
        setSubscribe(context, service.getGoodsDetailByGoodsId(requestParam), observer);
    }


    public void addBook(Context context, HnBook book, MyObserver<BaseModel> observerx) {
        setSubscribe(context, service.addBook(book), observerx);
    }

    public void delBook(Context context, DeleteParam deleteParam, MyObserver<BaseModel> observerx) {
        setSubscribe(context, service.deleteBook(deleteParam), observerx);
    }

    public void deleteBookList(Context context, DeleteListParam deleteListParam, MyObserver<BaseModel> observerx) {
        setSubscribe(context, service.deleteBookList(deleteListParam), observerx);
    }

    public void getBookList(Context context, QueryListParam queryListParam, MyObserver<BookListModel> observerx) {
        setSubscribe(context, service.getBookList(queryListParam), observerx);
    }

    public void updateBook(Context context, HnBook book, MyObserver<BaseModel> observerx) {
        setSubscribe(context, service.updateBook(book), observerx);
    }

    public void getBookDetailById(Context context, QueryDetailParam queryDetailParam, MyObserver<BookDetailResultModel> observerx) {
        setSubscribe(context, service.getBookDetailById(queryDetailParam), observerx);
    }

//
//    //修改登录密码
//    public void getSetLoginPwd(Context context, Map<String, String> map, Observer<BaseModel> observer) {
//        setSubscribe(context, service.getSetLoginPwd(map), observer);
//    }
//
//    //首页最新充值消息
//    public void getHomeLoop(Context context, Map<String, String> map, Observer<HomeLoopModel> observer) {
//        setSubscribe(context, service.getHomeLoop(map), observer);
//    }
//
//    //首页最新banner
//    public void getHomeBanner(Context context, Map<String, String> map, Observer<HomeBannerModel> observer) {
//        setSubscribe(context, service.getHomeBanner(map), observer);
//    }
//
//    //首页按钮
//    public void getHomeNewCardView(Context context, Map<String, String> map, Observer<HomeCardvMode> observer) {
//        setSubscribe(context ,service.getHomeNewCardView(map), observer);
//    }
//
//
//    //首页最新充值套餐
//    public void getHomeRecharge(Context context, Map<String, String> map, Observer<HomeModel> observer) {
//        setSubscribe(context, service.getHomeRecharge(map), observer);
//    }
//
//    //首页最新充值套餐
//    public void getHomeNewRecharge(Context context, Map<String, String> map, Observer<HomeModel> observer) {
//        setSubscribe(context, service.getHomeNewRecharge(map), observer);
//    }
//
//
//
//    //获取充值列表
//    public void getRechargeList(Context context, Map<String, String> map, Observer<HomeModel> observer) {
//        setSubscribe(context, service.getRechargeList(map), observer);
//    }
//
//    //即时充值
//    public void getRecharge(Context context, Map<String, String> map, Observer<RechargeOrderModel> observer) {
//        setSubscribe(context, service.getRecharge(map), observer);
//    }
//
//    //套餐充值
//    public void getRechargePackage(Context context, Map<String, String> map, Observer<RechargeOrderModel> observer) {
//        setSubscribe(context, service.getRechargePackage(map), observer);
//    }
//
//    //我的
//    public void getMine(Context context, Map<String, String> map, Observer<MineModel> observer) {
//        setSubscribe(context, service.getMine(map), observer);
//    }
//
//    //添加加油卡
//    public void getAddOil(Context context, Map<String, String> map, Observer<BaseModel> observer) {
//        setSubscribe(context, service.getAddOil(map), observer);
//    }
//
//    //删除加油卡
//    public void getDeleteOilCard(Context context, Map<String, String> map, Observer<BaseModel> observer) {
//        setSubscribe(context, service.getDeleteOilCard(map), observer);
//    }
//
//    //我的优惠券
//    public void getAllCouponsList(Context context, Map<String, String> map, Observer<CouponsListModel> observer) {
//        setSubscribe(context, service.getAllCouponsList(map), observer);
//    }
//
//    //选择优惠券
//    public void getUseCoupons(Context context, Map<String, String> map, Observer<CouponsListModel> observer) {
//        setSubscribe(context, service.getUseCoupons(map), observer);
//    }
//
//    //APP版本
//    public void getAppVersion(Context context, Map<String, String> map, Observer<AppSettingModel> observer) {
//        setSubscribe(context, service.getAppVersion(map), observer);
//    }
//
//    //个人中心
//    public void getMineInfo(Context context, Map<String, String> map, Observer<MineInfoModel> observer) {
//        setSubscribe(context, service.getMineInfo(map), observer);
//    }
//
//    //个人中心 ---修改生日 性别
//    public void getUpdateMineInfo(Context context, Map<String, String> map, Observer<BaseModel> observer) {
//        setSubscribe(context, service.getUpdateMineInfo(map), observer);
//    }
//
//    //个人中心 ---修改性别
//    public void getUpdateBirthdayMineInfo(Context context, Map<String, String> map, Observer<BaseModel> observer) {
//        setSubscribe(context, service.getUpdateBirthdayMineInfo(map), observer);
//    }
//
//    //我的积分
//    public void getIntegralUsage(Context context, Map<String, String> map, MyObserver<MineIntegralModel> observer) {
//        setSubscribe(context, service.getIntegralUsage(map), observer);
//    }
//
//    //全部加油计划
//    public void getOilPlan(Context context, Map<String, String> map, Observer<OilPlanModel> observer) {
//        setSubscribe(context, service.getOilPlan(map), observer);
//    }
//
//    //加油计划弹窗
//    public void getOilPlanTime(Context context, Map<String, String> map, Observer<DialogOilPlanTime> observer) {
//        setSubscribe(context, service.getOilPlanTime(map), observer);
//    }
//
//    //全部订单
//    public void getOrderList(Context context, Map<String, String> map, Observer<OrderListModel> observer) {
//        setSubscribe(context, service.getOrderList(map), observer);
//    }
//
//    //油卡充值记录
//    public void getOilOrderRecordList(Context context, Map<String, String> map, Observer<OrderListModel> observer) {
//        setSubscribe(context, service.getOilOrderRecordList(map), observer);
//    }
//
//    //订单详情
//    public void getOrderDetails(Context context, Map<String, String> map, Observer<OrderDetailsModel> observer) {
//        setSubscribe(context, service.getOrderDetails(map), observer);
//    }
//
//    //收货地址
//    public void getAddressList(Context context, Map<String, String> map, Observer<AddressListModel> observer) {
//        setSubscribe(context, service.getAddressList(map), observer);
//    }
//
//    //添加收货地址
//    public void getAddressAdd(Context context, Map<String, String> map, Observer<BaseModel> observer) {
//        setSubscribe(context, service.getAddressAdd(map), observer);
//    }
//
//    //修改收货地址
//    public void getAddressModify(Context context, Map<String, String> map, Observer<BaseModel> observer) {
//        setSubscribe(context, service.getAddressModify(map), observer);
//    }
//
//    //删除收货地址
//    public void getAddressDelete(Context context, Map<String, String> map, Observer<BaseModel> observer) {
//        setSubscribe(context, service.getAddressDelete(map), observer);
//    }
//
//    //官方公告
//    public void getNoticeList(Context context, Map<String, String> map, Observer<NoticeModel> observer) {
//        setSubscribe(context, service.getNoticeList(map), observer);
//    }
//
//    //消息中心
//    public void getNewNotice(Context context, Map<String, String> map, Observer<MessageCenterModel> observer) {
//        setSubscribe(context, service.getNewNotice(map), observer);
//    }
//
//    //邀请人
//    public void getInvitationList(Context context, Map<String, String> map, Observer<InvitationListModel> observer) {
//        setSubscribe(context, service.getInvitationList(map), observer);
//    }
//
//    //活动
//    public void getFindList(Context context, Map<String, String> map, MyObserver<ActivitiesCenterModel> observer) {
//        setSubscribe(context, service.getFindList(map), observer);
//    }
//
//    //活动中心
//    public void getActivitiesList(Context context, Map<String, String> map, Observer<ActivitiesCenterModel> observer) {
//        setSubscribe(context, service.getActivitiesList(map), observer);
//    }
//
//    //油价
//    public void getOilPrice(Context context, Map<String, String> map, Observer<OilPriceModel> observer) {
//        setSubscribe(context, service.getOilPrice(map), observer);
//    }
//
//    //新闻
//    public void getAllNews(Context context, Map<String, String> map, Observer<NewsModel> observer) {
//        setSubscribe(context, service.getAllNews(map), observer);
//    }
//
//    //新闻详情
//    public void getOneNews(Context context, Map<String, String> map, Observer<OneNewModel> observer) {
//        setSubscribe(context, service.getOneNews(map), observer);
//    }
//
//    //连连支付
//    public void getRechargePay(Context context, Map<String, String> map, Observer<LianLianPayModel> observer) {
//        setSubscribe(context, service.getRechargePay(map), observer);
//    }
//    //余额支付
//    public void getRechargePay_ye(Context context, Map<String, String> map, Observer<LianLianPayModel> observer) {
//        setSubscribe(context, service.getRechargePay_ye(map), observer);
//    }
//
//    // 支付宝--油卡充值
//    public void getRechargeOilZFBPay(Context context, Map<String, String> map, Observer<ZFBPayModel> observer) {
//        setSubscribe(context, service.getRechargeOilZFBPay(map), observer);
//    }
//
//    //支付宝
//    public void getRechargeZFBPay(Context context, Map<String, String> map, Observer<ZFBPayModel> observer) {
//        setSubscribe(context, service.getRechargeZFBPay(map), observer);
//    }
//
//    //微信支付 -- 油卡充值
//    public void getRechargeOilWXPay(Context context, Map<String, String> map, Observer<WXPayModel> observer) {
//        setSubscribe(context, service.getRechargeOilWXPay(map), observer);
//    }
//
//    //微信支付
//    public void getRechargeWXPay(Context context, Map<String, String> map, Observer<WXPayModel> observer) {
//        setSubscribe(context, service.getRechargeWXPay(map), observer);
//    }
//
//    //微信支付
//    public void getPointPay(Context context, Map<String, String> map, Observer<WXPayModel> observer) {
//        setSubscribe(context, service.getPointPay(map), observer);
//    }
//


}
