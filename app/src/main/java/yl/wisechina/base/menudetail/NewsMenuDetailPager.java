package yl.wisechina.base.menudetail;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import com.viewpagerindicator.TabPageIndicator;

import java.util.ArrayList;

import yl.wisechina.R;
import yl.wisechina.base.BaseMenuDetailPager;
import yl.wisechina.base.TabMenuDetailPager;
import yl.wisechina.bean.NewsData;

/**
 * =================================
 * <p/>
 * Created by Administrator on 2016/9/16.
 * <p/>
 * 描述:
 */

public class NewsMenuDetailPager extends BaseMenuDetailPager {

    private ViewPager mViewPager;

    private ArrayList<TabMenuDetailPager> mPagerList;


    private ArrayList<NewsData.NewsTabData> mNewsTabData;//11个也签网络数据
    private TabPageIndicator mIndicator;


    public NewsMenuDetailPager(Activity activity, ArrayList<NewsData.NewsTabData> children) {
        super(activity);

        mNewsTabData = children;
    }

    @Override
    public View initViews() {

        View view = View.inflate(mActivity, R.layout.news_menu_detail, null);
        mViewPager = (ViewPager) view.findViewById(R.id.vp_menu_detail);



        //初始化viewpagerindicator
        mIndicator = (TabPageIndicator) view.findViewById(R.id.indicator);


        return view;
    }

    @Override
    public void initData() {

        mPagerList = new ArrayList<TabMenuDetailPager>();

        //初始化也签数据
        for (int i = 0; i < mNewsTabData.size(); i++) {

            ///重点
            TabMenuDetailPager pager = new TabMenuDetailPager(mActivity, mNewsTabData.get(i));
            mPagerList.add(pager);
        }

        mViewPager.setAdapter(new MenuDetailAdapter());

        mIndicator.setViewPager(mViewPager);//只有在setAdapter后才能初始化此方法
    }


    class MenuDetailAdapter extends PagerAdapter {


        //此方法为viewPagerIndicator中的方法，在此调用，将title显示到indicator里
        @Override
        public CharSequence getPageTitle(int position) {
            return mNewsTabData.get(position).title;
        }

        @Override
        public int getCount() {
            return mPagerList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            TabMenuDetailPager pager = mPagerList.get(position);
            container.addView(pager.mRootView);

            pager.initData();
            return pager.mRootView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    //跳转到下一个页面
    public void nextPage(View view) {
        /*int currentItem = mViewPager.getCurrentItem();

        mViewPager.setCurrentItem(++currentItem);*/

    }


}
