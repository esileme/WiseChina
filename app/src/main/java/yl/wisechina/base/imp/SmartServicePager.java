package yl.wisechina.base.imp;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.TextView;

import yl.wisechina.base.BasePager;

/**
 * =================================
 * <p/>
 * Created by Administrator on 2016/9/15.
 * <p/>
 * 描述: 首页的实现
 */

public class SmartServicePager extends BasePager {


    public SmartServicePager(Activity activity) {
        super(activity);
    }

    @Override
    public void initData() {
        tvTitle.setText("智慧服务");
        setSlidingMenuEnable(true);
        TextView text = new TextView(mActivity);
        text.setText("智慧服务");
        text.setTextColor(Color.RED);
        text.setTextSize(26);
        text.setGravity(Gravity.CENTER);

        flContent.addView(text);
    }
}
