package yl.wisechina.base;

import android.app.Activity;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import yl.wisechina.R;
import yl.wisechina.activity.MainActivity;

/**
 * =================================
 * <p/>
 * Created by Administrator on 2016/9/15.
 * <p/>
 * 描述:viewpager的基类
 */

public class BasePager {

    public Activity mActivity;
    public View mRootView;

    public TextView tvTitle;//标题
    public FrameLayout flContent;//内容

    public ImageButton btnMenu;

    public BasePager(Activity activity) {
        mActivity = activity;

        initViews();

    }

    public void initViews() {
        mRootView = View.inflate(mActivity, R.layout.base_pager, null);

        tvTitle = (TextView) mRootView.findViewById(R.id.tv_title);
        flContent = (FrameLayout) mRootView.findViewById(R.id.fl_content);
        btnMenu = (ImageButton) mRootView.findViewById(R.id.ib_main);

    }

    public void initData() {

    }

    //侧边栏是否开启

    public void setSlidingMenuEnable(boolean enable) {

        MainActivity mainUi = (MainActivity) mActivity;
        DrawerLayout dl_main = (DrawerLayout) mainUi.findViewById(R.id.dl_main);
        if (enable) {
            dl_main.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        } else {
            dl_main.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        }

    }

}
