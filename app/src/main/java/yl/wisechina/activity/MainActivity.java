package yl.wisechina.activity;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;

import yl.wisechina.R;
import yl.wisechina.base.BasePager;
import yl.wisechina.base.imp.GovAffairsPager;
import yl.wisechina.base.imp.HomePager;
import yl.wisechina.base.imp.NewsCenterPager;
import yl.wisechina.base.imp.SettingPager;
import yl.wisechina.base.imp.SmartServicePager;
import yl.wisechina.bean.NewsData;


public class MainActivity extends Activity {

    @ViewInject(R.id.rg_group)
    private RadioGroup rgGroup;

    @ViewInject(R.id.vp_content)
    private ViewPager mViewPager;

    @ViewInject(R.id.left_listview)
    private ListView lvList;

    @ViewInject(R.id.dl_main)
    private DrawerLayout dlMain;

    @ViewInject(R.id.rl_left)
    private RelativeLayout rl_left;

    private int mCurrentPos;//记录当前点击的位置


    private ArrayList<BasePager> mPagerList;//定义五个基本的viewPager
    private ArrayList<NewsData.NewsMenuData> mMenuList;//定义侧边栏从服务器拿到的数据的集合
    private MenuAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();

    }

    private void initViews() {
        //rgGroup = (RadioGroup) findViewById(R.id.rg_group);
        ViewUtils.inject(this);

        //给侧边栏设置是否滑动
        //dlMain.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        //dlMain.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);


        initData();


    }

    private void initData() {
        rgGroup.check(R.id.rb_home);


        //加载五个pager
        mPagerList = new ArrayList<BasePager>();
        /*for (int i = 0; i < 5; i++) {
            BasePager pager = new BasePager(this);
            mPagerList.add(pager);
        }*/

        mPagerList.add(new HomePager(this));
        mPagerList.add(new NewsCenterPager(this));
        mPagerList.add(new SmartServicePager(this));
        mPagerList.add(new GovAffairsPager(this));
        mPagerList.add(new SettingPager(this));


        mViewPager.setAdapter(new ContentAdapter());


        rgGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_home:
                        mViewPager.setCurrentItem(0);//第二个参数smoothScroll  滑动动画true为有
                        break;
                    case R.id.rb_news:
                        mViewPager.setCurrentItem(1);
                        break;
                    case R.id.rb_smart:
                        mViewPager.setCurrentItem(2);
                        break;
                    case R.id.rb_gov:
                        mViewPager.setCurrentItem(3);
                        break;
                    case R.id.rb_setting:
                        mViewPager.setCurrentItem(4);
                        break;
                }
            }
        });

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                mPagerList.get(position).initData();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mPagerList.get(0).initData();//如果在addOnPageChangeListener中刷新数据，则首次进入不会初始化数据，需要手动进行初始化


        //侧边栏的listview添加点击事件
        lvList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mCurrentPos = position;//将當前的位置传递给点击的位置
                adapter.notifyDataSetChanged();

                setCurrentMenuDetailPager1(position);
                dlMain.closeDrawer(rl_left);//侧边栏点击后，让侧边栏消失

            }
        });


    }

    /**
     * 设置当前菜单详情页
     *
     * @param position
     */
    private void setCurrentMenuDetailPager1(int position) {


        NewsCenterPager pager = getNewsCenterPager();
        pager.setCurrentMenuDetailPager(position);


        //pager.initData();// 初始化当前页面的数据
    }

    /**
     * 初始化底部菜单数据
     */
    class ContentAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mPagerList.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            BasePager pager = mPagerList.get(position);
            container.addView(pager.mRootView);
            //pager.initData();//初始 化数据,在此处会预加载下一页的数据
            return mPagerList.get(position).mRootView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    /**
     * 初始化侧边栏的数据
     */
    class MenuAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mMenuList.size();
        }

        @Override
        public NewsData.NewsMenuData getItem(int position) {
            return mMenuList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View view = View.inflate(MainActivity.this, R.layout.list_menu_item, null);

            TextView tvTitle = (TextView) view.findViewById(R.id.tv_title);
            NewsData.NewsMenuData newsMenuData = getItem(position);
            tvTitle.setText(newsMenuData.title);

            if (mCurrentPos == position) {
                //给选定的item设置颜色
                System.out.println("当前位置:" + position);
                tvTitle.setEnabled(true);

            } else {
                tvTitle.setEnabled(false);
            }

            return view;
        }
    }


    /**
    * 设置网络数据
    */
    public void setMenuData(NewsData data) {
        System.out.println("侧边栏拿到的数据" + data);
        mMenuList = data.data;
        adapter = new MenuAdapter();
        //添加adapter，在listview里面的item点击事件中，设置adapter通知信息改变，这样，发出通知后才回刷新数据
        lvList.setAdapter(adapter);//在拿到数据后，才能setAdapter，否则会有数量空指针的错误


    }

    /**
     * 获取newscenterpager的方法
     */
    public NewsCenterPager getNewsCenterPager() {
        return (NewsCenterPager) mPagerList.get(1);

    }

    /**
     * 主菜单的button点击事件
     *
     * @param view
     */
    public void onClick(View view) {
        dlMain.openDrawer(rl_left);
        //dlMain.closeDrawer(rl_left);
    }


}