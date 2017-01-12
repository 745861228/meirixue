package com.lwgz.meirixue.fragment;

import android.view.View;

import com.lwgz.meirixue.base.BaseFragment;
import com.lwgz.meirixue.utils.NetUtils;
import com.lwgz.meirixue.view.ShowingPage;

/**
 * author by LiKe on 2017/1/10.
 */

public class CircleFragment extends BaseFragment{
    @Override
    protected void onLoad() {
        int netWorkType = NetUtils.getNetWorkType(getActivity());
        if (netWorkType == NetUtils.NETWORKTYPE_INVALID){
            showCurrentPage(ShowingPage.StateType.STATE_LOAD_ERROR);
        }
    }

    @Override
    public View createSuccessView() {

        return null;
    }
}
