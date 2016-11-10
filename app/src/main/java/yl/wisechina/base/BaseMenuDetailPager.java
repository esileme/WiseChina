package yl.wisechina.base;

import android.app.Activity;
import android.view.View;

/**
 * =================================
 * <p/>
 * Created by Administrator on 2016/9/16.
 * <p/>
 * 描述:
 */

public abstract class BaseMenuDetailPager {

    public Activity mActivity;

    public View mRootView;

    public BaseMenuDetailPager(Activity activity) {
        mActivity = activity;
        mRootView=initViews();
    }

    public abstract View initViews();

    public void initData() {

    }

}
