package com.lwgz.meirixue.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;


import com.lwgz.meirixue.activity.LoginActivity;
import com.lwgz.meirixue.base.BaseData;
import com.lwgz.meirixue.base.BaseFragment;
import com.lwgz.meirixue.interfaces.IResetShowingPageListener;
import com.lwgz.meirixue.utils.NetUtils;

import com.lwgz.meirixue.view.ShowingPage;

import java.util.HashMap;


/**
 * author by LiKe on 2017/1/10.
 */

public class CategoryFragment extends BaseFragment {
    private String data;
    private boolean isOnline = true;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (!NetUtils.isHaveNet()) {
            isOnline = false;
        }
    }

    @Override
    protected void onLoad() {
        showingPage.setIResetShowingPageListener(new IResetShowingPageListener() {
            @Override
            public void onResetClick(View view) {
                isOnline = true;
                initData();
            }
        });
        if (isOnline) {
            initData();
        }else {
            showCurrentPage(ShowingPage.StateType.STATE_LOAD_ERROR);
        }
    }

    private void initData() {

        HashMap<String, String> map = new HashMap<>();
        map.put("key", "96efc220a4196fafdfade0c9d1e897ac");
        map.put("qq", "111111111");
        new BaseData() {
            @Override
            public void setResultData(String data) {
                CategoryFragment.this.data = data;
                showCurrentPage(ShowingPage.StateType.STATE_LOAD_SUCCESS);

            }

            @Override
            protected void setResulttError(ShowingPage.StateType stateLoadError) {
                showCurrentPage(ShowingPage.StateType.STATE_LOAD_ERROR);
            }
        }.postData(false, false, "http://japi.juhe.cn/", "qqevaluate/qq", map, BaseData.NORMALTIME);
    }

    @Override
    public View createSuccessView() {
        TextView textView = new TextView(getActivity());
        textView.setText(data);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), LoginActivity.class));
            }
        });
        return textView;
    }
}
