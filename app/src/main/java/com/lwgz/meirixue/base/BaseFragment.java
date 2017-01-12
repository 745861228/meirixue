package com.lwgz.meirixue.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lwgz.meirixue.utils.LogUtils;
import com.lwgz.meirixue.view.ShowingPage;

/**
 * author by LiKe on 2017/1/10.
 */

public abstract class BaseFragment extends Fragment {

    public ShowingPage showingPage;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //进行加载
        //进行抽象
        //因为我们也不知道如何展示--继续抽象
        showingPage = new ShowingPage(getActivity()) {
            @Override
            protected View createSuccessView() {
                return BaseFragment.this.createSuccessView();
            }

            @Override
            protected void onLoad() {
                new Thread(){
                    @Override
                    public void run() {
                        super.run();
                        BaseFragment.this.onLoad();
                    }
                }.start();
            }
        };
        return showingPage;
    }

    /**
     * 加载
     */
    protected abstract void onLoad();

    /**
     * 展示成功界面
     *
     * @return
     */
    public abstract View createSuccessView();

    /**
     * 对外提供方法，调用展示界面
     *
     * @param stateType
     */
    public void showCurrentPage(ShowingPage.StateType stateType) {
        //调用showingPage的方法
        if (showingPage != null) {
                showingPage.showCurrentPage(stateType);
        }
    }
}
