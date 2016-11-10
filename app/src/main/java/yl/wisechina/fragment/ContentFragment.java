package yl.wisechina.fragment;


import android.view.View;

import yl.wisechina.R;

/**
 * 主fragment
 */
public class ContentFragment extends BaseFragment {


    @Override
    public View initViews() {
        View view = View.inflate(mActivity, R.layout.fragment_content, null);

        return view;
    }
}
