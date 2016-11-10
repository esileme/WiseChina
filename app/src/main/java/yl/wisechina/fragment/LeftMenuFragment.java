package yl.wisechina.fragment;


import android.view.View;

import yl.wisechina.R;

/**
 * 左侧边栏
 */
public class LeftMenuFragment extends BaseFragment {

    @Override
    public View initViews() {
        View view = View.inflate(mActivity, R.layout.fragment_left_menu, null);

        return view;
    }

}
