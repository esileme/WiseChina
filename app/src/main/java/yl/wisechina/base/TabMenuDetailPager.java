package yl.wisechina.base;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.util.ArrayList;

import yl.wisechina.R;
import yl.wisechina.bean.NewsData;
import yl.wisechina.bean.TabData;
import yl.wisechina.global.GlobalContants;

/**
 * =================================
 * <p/>
 * Created by Administrator on 2016/9/17.
 * <p/>
 * 描述:侧边页签详情页
 */

public class TabMenuDetailPager extends BaseMenuDetailPager {


    NewsData.NewsTabData mTabData;

    private String mUrl;//获取的连接
    private TabData mTabDetailData;
    private ViewPager mViewPager;
    private BitmapUtils bitmapUtils;
    private ListView lvList;//新闻列表
    private ArrayList<TabData.TopNewsData> mTopNewsList;
    private ArrayList<TabData.TabNewsData> mNewsList;//新闻数据集合
    private NewsAdapter mNewsAdapter;//新闻列表的adapter


    public TabMenuDetailPager(Activity activity, NewsData.NewsTabData newsTabData) {
        super(activity);

        mTabData = newsTabData;
        mUrl = GlobalContants.SERVER_URL + mTabData.url;

    }

    @Override
    public View initViews() {
        View view = View.inflate(mActivity, R.layout.tab_detail_pager, null);
        View headerView = View.inflate(mActivity, R.layout.list_header_topnews, null);

        mViewPager = (ViewPager) headerView.findViewById(R.id.vp_news);
        lvList = (ListView) view.findViewById(R.id.lv_list);

        lvList.addHeaderView(headerView);

        return view;
    }

    @Override
    public void initData() {

        getDataFromServer();
    }

    //从服务器获取数据
    private void getDataFromServer() {

        HttpUtils utils = new HttpUtils();
        utils.send(HttpRequest.HttpMethod.GET, mUrl, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                System.out.println("頁簽詳情頁返回结果" + result);
                System.out.println("------------------------");
                parseData(result);

            }

            @Override
            public void onFailure(HttpException error, String msg) {
                Toast.makeText(mActivity, msg, Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        });

    }

    private void parseData(String result) {
        Gson gson = new Gson();
        mTabDetailData = gson.fromJson(result, TabData.class);
        System.out.println("页签详情解析：" + mTabDetailData);

        mTopNewsList = mTabDetailData.data.topnews;

        if (mTopNewsList != null) {
            //给头条新闻setadapter,在拿到数据后才设置，否则会空指针
            mViewPager.setAdapter(new TopNewsAdapter());
        }

        mNewsList = mTabDetailData.data.news;
        if (mNewsList != null) {

            mNewsAdapter = new NewsAdapter();
            lvList.setAdapter(mNewsAdapter);

        }


    }

    //头条新闻的adapter
    class TopNewsAdapter extends PagerAdapter {

        public TopNewsAdapter() {

            bitmapUtils = new BitmapUtils(mActivity);
            bitmapUtils.configDefaultLoadingImage(android.R.color.holo_red_dark);
        }

        @Override
        public int getCount() {
            return mTabDetailData.data.topnews.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView image = new ImageView(mActivity);


            TabData.TopNewsData topNewsData = mTabDetailData.data.topnews.get(position);//获取到图片的网址
            bitmapUtils.display(image, topNewsData.topimage);
            image.setScaleType(ImageView.ScaleType.FIT_XY);//设置填充图片

            container.addView(image);


            return image;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    class NewsAdapter extends BaseAdapter {

        private final BitmapUtils utils;

        //构造函数中初始化图片
        public NewsAdapter() {
            utils = new BitmapUtils(mActivity);
            utils.configDefaultLoadingImage(android.R.color.darker_gray);

        }

        @Override
        public int getCount() {
            return mNewsList.size();
        }

        @Override
        public TabData.TabNewsData getItem(int position) {
            return mNewsList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder holder;
            if (convertView == null) {
                convertView = View.inflate(mActivity, R.layout.list_news_item, null);

                holder = new ViewHolder();
                holder.ivPic = (ImageView) convertView.findViewById(R.id.iv_pic);
                holder.tvTitle = (TextView) convertView.findViewById(R.id.tv_title);
                holder.tvDate = (TextView) convertView.findViewById(R.id.tv_date);

                convertView.setTag(holder);//未设置标签

            } else {
                holder = (ViewHolder) convertView.getTag();
            }


            TabData.TabNewsData item = getItem(position);

            holder.tvTitle.setText(item.title);
            holder.tvDate.setText(item.pubdate);
            utils.display(holder.ivPic, item.listimage);

            return convertView;//返回成null。。。。

        }
    }

    static class ViewHolder {
        public TextView tvTitle;
        public TextView tvDate;
        public ImageView ivPic;
    }


}
