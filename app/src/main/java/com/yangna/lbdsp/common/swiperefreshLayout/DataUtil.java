package com.yangna.lbdsp.common.swiperefreshLayout;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * 生成数据工具类
 *
 * @author jingbin
 */
public class DataUtil {

    /**
     * 首页的数据
     */
    public static List<MainItemBean> getMainActivityList(Context context) {
        ArrayList<MainItemBean> list = new ArrayList<>();

        list.add(new MainItemBean("ByRecyclerView", null).setCategoryName());
//        list.add(new MainItemBean("基本使用", MainActivity.class, "1"));
//        list.add(new MainItemBean("基本使用", SimpleActivity.class, "1"));
//        list.add(new MainItemBean("使用 自带刷新 / SwipeRefreshLayout", RefreshActivity.class, "2"));
//        list.add(new MainItemBean("设置HeaderView，FooterView", HeaderFooterActivity.class, "3"));
//        list.add(new MainItemBean("设置StateView (加载中布局/空布局/错误布局)", StateViewActivity.class, "4"));
//        list.add(new MainItemBean("设置点击/长按事件 (item或item里的子View)", ItemClickActivity.class, "5"));
//        list.add(new MainItemBean("CoordinatorLayout + RecyclerView 使用示例", AppBarLayoutActivity.class, "6"));
//        list.add(new MainItemBean("自定义下拉刷新布局 / 加载更多布局", CustomLayoutActivity.class, "7"));
//        list.add(new MainItemBean("自定义加载更多布局 (横向)", CustomHorizontalLayoutActivity.class, "8"));
//        list.add(new MainItemBean("Item 悬浮置顶", StickyItemActivity.class, "9"));

        list.add(new MainItemBean("Skeleton", null).setCategoryName());
//        list.add(new MainItemBean("骨架图 list", SkeletonListActivity.class, "1"));
//        list.add(new MainItemBean("骨架图 grid", SkeletonGridActivity.class, "2"));
//        list.add(new MainItemBean("骨架图 headerView", SkeletonHeaderViewActivity.class, "3"));
//        list.add(new MainItemBean("骨架图 View", SkeletonViewActivity.class, "4"));

        list.add(new MainItemBean("Adapter", null).setCategoryName());
//        list.add(new MainItemBean("多类型列表 (线性/宫格/瀑布流)", MultiTypeItemActivity.class, "1"));
//        list.add(new MainItemBean("BaseListAdapter的使用 (ListView)", ListViewActivity.class, "2"));
//        list.add(new MainItemBean("使用DataBinding (RecyclerView / ListView)", DataBindingActivity.class, "3"));

        list.add(new MainItemBean("ItemDecoration", null).setCategoryName());
//        list.add(new MainItemBean("设置分割线 (线性布局)", DividerLinearActivity.class, "1"));
//        list.add(new MainItemBean("设置分割线 (宫格/瀑布流)", DividerGridActivity.class, "2"));

        return list;
    }

    /**
     * 一般item的数据
     */
    public static ArrayList<DataItemBean> getMultiData(Context context, int num) {
        ArrayList<DataItemBean> list = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            DataItemBean bean = new DataItemBean();
            if (
//                    i == 0||
                    i == 2 || i == 4
                            || i == 6 || i == 8
                            || i == 10 || i == 20
                            || i == 22 || i == 19
                            || i == 13 || i == 15
                            || i == 34 || i == 40
                            || i == 17 || i == 30) {
                bean.setType("title");
                bean.setDes("我是标题");
            } else {
                bean.setType("content");
                bean.setDes("我是内容");
            }
            list.add(bean);
        }
        return list;
    }

    /**
     * 一般item的数据
     */
    public static ArrayList<DataItemBean> get(Context context, int num) {
        ArrayList<DataItemBean> list = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            DataItemBean bean = new DataItemBean();
            bean.setTitle("数据展示");
            list.add(bean);
        }
        return list;
    }

    /**
     * 下一页数据 默认page至少 = 1
     */
    public static ArrayList<DataItemBean> getMore(Context context, int num, int page) {
        ArrayList<DataItemBean> list = new ArrayList<>();
        for (int i = num * (page - 1); i < num * (page); i++) {
            DataItemBean bean = new DataItemBean();
            bean.setTitle("数据展示");
            list.add(bean);
        }
        return list;
    }

}
