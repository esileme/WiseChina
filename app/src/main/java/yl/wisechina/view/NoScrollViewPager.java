package yl.wisechina.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * =================================
 * <p/>
 * Created by Administrator on 2016/9/15.
 * <p/>
 * 描述:将touchevent事件返回false，不滑动
 */

public class NoScrollViewPager extends ViewPager {
    public NoScrollViewPager(Context context) {
        super(context);
    }

    public NoScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    //重写，什么都不做
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        //应该可以在这添加滑动切换底部按钮，待实现
        return false;
    }

    //返回false，表示不拦截任何事件
    @Override
    public boolean onInterceptHoverEvent(MotionEvent event) {
        return false;
    }
}
