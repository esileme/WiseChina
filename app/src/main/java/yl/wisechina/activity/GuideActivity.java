package yl.wisechina.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;

import yl.wisechina.R;

/**
 * 在导航页面添加viewPager，通过viewpager的滑动来展示图片
 */
public class GuideActivity extends Activity {

    private ViewPager vpGuide;
    private static final int[] mImageIds = new int[]{R.mipmap.guide_1, R.mipmap.guide_2, R.mipmap.guide_3};
    private ArrayList<ImageView> mImageViewLists;
    private LinearLayout llPointGroup;
    private int mPointWidth = 10;
    private View viewRedPoint;
    private Button btnStart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        vpGuide = (ViewPager) findViewById(R.id.vp_guide);
        llPointGroup = (LinearLayout) findViewById(R.id.ll_point_group);
        viewRedPoint = findViewById(R.id.view_redPoint);
        btnStart = (Button) findViewById(R.id.button_start);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences sp = getSharedPreferences("config", MODE_PRIVATE);
                sp.edit().putBoolean("is_user_guide_showed", true).commit();

                startActivity(new Intent(GuideActivity.this, MainActivity.class));
                finish();
            }
        });


        GuideAdapter adapter = new GuideAdapter();
        vpGuide.setAdapter(adapter);
        vpGuide.addOnPageChangeListener(new GuidePageListener());

        initViews();
    }

    private void initViews() {

        ///设置viewpager的图片
        mImageViewLists = new ArrayList<ImageView>();

        for (int mImageId : mImageIds) {
            ImageView imageView = new ImageView(this);
            imageView.setBackgroundResource(mImageId);
            mImageViewLists.add(imageView);
        }
        ///设置viewpager的小圆点
        for (int i = 0; i < mImageIds.length; i++) {
            View point = new View(this);
            point.setBackgroundResource(R.drawable.shape_point_gray);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(10, 10);
            if (i > 0) {
                params.leftMargin = 10;
            }
            point.setLayoutParams(params);
            llPointGroup.addView(point);

            /*//获取两个远点的宽度
            int width = llPointGroup.getChildAt(1).getRight() - llPointGroup.getChildAt(0).getRight();
            System.out.println("原点距离"+width);*/

            //获取试图树
            llPointGroup.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                @Override
                public void onGlobalLayout() {
                    System.out.println("layout 结束");
                    llPointGroup.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
            });

        }


    }

    /**
     * 设置adapter
     */
    private class GuideAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mImageIds.length;
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(mImageViewLists.get(position));
            return mImageViewLists.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    /**
     * 小圆点的滑动改变事件
     */
    private class GuidePageListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            System.out.println("当前位置" + position + ";位置百分比" + positionOffset + "移动距离" + positionOffsetPixels);
            int len = (mPointWidth * positionOffsetPixels) + position * mPointWidth;
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) viewRedPoint.getLayoutParams();
            params.leftMargin = len;

            viewRedPoint.setLayoutParams(params);//重新给小圆点设置参数

        }

        @Override
        public void onPageSelected(int position) {
            if (position == mImageIds.length - 1) {
                btnStart.setVisibility(View.VISIBLE);
            }

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }


    }


}
