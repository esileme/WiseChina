package yl.wisechina.base.imp;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import yl.wisechina.base.BasePager;

/**
 * =================================
 * <p/>
 * Created by Administrator on 2016/9/15.
 * <p/>
 * 描述: 首页的实现
 */

public class SettingPager extends BasePager {


    public SettingPager(Activity activity) {
        super(activity);
    }

    @Override
    public void initData() {
        tvTitle.setText("设置中心");
        setSlidingMenuEnable(false);
        btnMenu.setVisibility(View.GONE);
        TextView text = new TextView(mActivity);
        text.setText("设置");
        text.setTextColor(Color.RED);
        text.setTextSize(26);
        text.setGravity(Gravity.CENTER);

        flContent.addView(text);
    }
}
