package yl.wisechina.base.imp;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.util.ArrayList;

import yl.wisechina.activity.MainActivity;
import yl.wisechina.base.BaseMenuDetailPager;
import yl.wisechina.base.BasePager;
import yl.wisechina.base.menudetail.InteractMenuDetailPager;
import yl.wisechina.base.menudetail.NewsMenuDetailPager;
import yl.wisechina.base.menudetail.PhotoMenuDetailPager;
import yl.wisechina.base.menudetail.TopicMenuDetailPager;
import yl.wisechina.bean.NewsData;
import yl.wisechina.global.GlobalContants;

/**
 * =================================
 * <p/>
 * Created by Administrator on 2016/9/15.
 * <p/>
 * 描述: 首页的实现
 */

public class NewsCenterPager extends BasePager {

    public ArrayList<BaseMenuDetailPager> mPagers;//定义四个侧边栏数据
    private NewsData mNewsData;


    public NewsCenterPager(Activity activity) {
        super(activity);
    }


    @Override
    public void initData() {
        tvTitle.setText("新闻中心");
        setSlidingMenuEnable(true);
        TextView text = new TextView(mActivity);
        text.setText("新闻");
        text.setTextColor(Color.RED);
        text.setTextSize(26);
        text.setGravity(Gravity.CENTER);

        flContent.addView(text);

        getDataFromServer();
    }

    /**
     * 从服务器拿去数据
     */
    public void getDataFromServer() {
        HttpUtils utils = new HttpUtils();
        utils.send(HttpRequest.HttpMethod.GET, GlobalContants.CATEGORIES_URL, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                System.out.println("返回结果" + result);
                System.out.println("------------------------");
                parseData(result);

            }

            @Override
            public void onFailure(HttpException e, String msg) {
                Toast.makeText(mActivity, msg, Toast.LENGTH_SHORT).show();
                e.printStackTrace();

            }
        });
    }

    /**
     * 解析网络数据
     */
    private void parseData(String result) {
        Gson gson = new Gson();
        mNewsData = gson.fromJson(result, NewsData.class);
        System.out.println("解析结果" + mNewsData);

        //刷新侧边栏数据
        //将此处的数据传递给MainActivity,先在MainActivity中写一个借口用来调用，然后在此处获取MainActivity对象通过对象.set数据
        MainActivity mainUi = (MainActivity) this.mActivity;
        mainUi.setMenuData(mNewsData);

        //刷新加载侧边栏数据
        mPagers = new ArrayList<BaseMenuDetailPager>();
        mPagers.add(new NewsMenuDetailPager(mActivity, mNewsData.data.get(0).children));
        mPagers.add(new TopicMenuDetailPager(mActivity));
        mPagers.add(new PhotoMenuDetailPager(mActivity));
        mPagers.add(new InteractMenuDetailPager(mActivity));


        //默认第一个
        setCurrentMenuDetailPager(0);


    }

    /**
     * 设置当前菜单详情页
     */
    public void setCurrentMenuDetailPager(int position) {
        BaseMenuDetailPager pager = mPagers.get(position);
        flContent.removeAllViews();
        flContent.addView(pager.mRootView);

        // 设置当前页的标题
        NewsData.NewsMenuData menuData = mNewsData.data.get(position);
        tvTitle.setText(menuData.title);


        pager.initData();//初始化页签详情页的数据

    }


}
