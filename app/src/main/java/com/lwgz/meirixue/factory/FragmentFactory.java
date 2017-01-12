package com.lwgz.meirixue.factory;

import android.support.v4.app.Fragment;


import com.lwgz.meirixue.fragment.CategoryFragment;
import com.lwgz.meirixue.fragment.CircleFragment;
import com.lwgz.meirixue.fragment.HomeFragment;
import com.lwgz.meirixue.fragment.MineFragment;

import java.util.HashMap;

/**
 * Created by LiKe on 2016/11/28.
 */
public class FragmentFactory {
    //创建集合
    private static HashMap<Integer, Fragment> fragmentHashMap = new HashMap<>();

    //创建静态方法
    public static Fragment getFragment(int position) {
        Fragment fragment = fragmentHashMap.get(position);
        if (fragment != null) {
            return fragment;
        }

        switch (position) {
            case 0:
                fragment = new HomeFragment();
                break;

            case 1:
                fragment = new CategoryFragment();
                break;

            case 2:
                fragment = new CircleFragment();
                break;

            case 3:
                fragment = new MineFragment();
                break;
        }

        fragmentHashMap.put(position, fragment);
        return fragment;
    }
}
