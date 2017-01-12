package com.lwgz.meirixue.fragment;

import android.view.View;
import android.widget.TextView;

import com.lwgz.meirixue.base.BaseData;
import com.lwgz.meirixue.base.BaseFragment;
import com.lwgz.meirixue.view.ShowingPage;

/**
 * author by LiKe on 2017/1/10.
 */

public class HomeFragment extends BaseFragment {

    private TextView textView;
    private String data;


    @Override
    protected void onLoad() {
        new BaseData() {
            @Override
            public void setResultData(final String data) {
                HomeFragment.this.data = data;
                showCurrentPage(ShowingPage.StateType.STATE_LOAD_SUCCESS);
            }

            @Override
            protected void setResulttError(ShowingPage.StateType stateLoadError) {
                showCurrentPage(ShowingPage.StateType.STATE_LOAD_ERROR);
            }
        }.getData("http://www.baidu.com", "http://www.baidu.com", 200000);

    }


    @Override
    public View createSuccessView() {
        textView = new TextView(getActivity());
        textView.setText(data);
        return textView;
    }
}
