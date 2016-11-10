package yl.wisechina.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * =================================
 * <p/>
 * Created by Administrator on 2016/9/17.
 * <p/>
 * 描述:头条新闻的viewpager，请求父控件不拦截事件
 */

public class TopNewsViewPager extends ViewPager {


    public TopNewsViewPager(Context context) {
        super(context);
    }

    public TopNewsViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    //请求父控件以及以上是否拦截
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        getParent().requestDisallowInterceptTouchEvent(true);
        return super.dispatchTouchEvent(ev);
    }
}
