package yl.wisechina.base.menudetail;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import yl.wisechina.base.BaseMenuDetailPager;

/**
 * =================================
 * <p/>
 * Created by Administrator on 2016/9/16.
 * <p/>
 * 描述:
 */

public class InteractMenuDetailPager extends BaseMenuDetailPager {
    public InteractMenuDetailPager(Activity activity) {
        super(activity);
    }

    @Override
    public View initViews() {
        TextView text = new TextView(mActivity);
        text.setText("互动详情页-侧栏");
        text.setTextColor(Color.RED);
        text.setTextSize(26);
        text.setGravity(Gravity.CENTER);

        return text;
    }
}
