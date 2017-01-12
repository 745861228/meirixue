package com.lwgz.meirixue.base;

/**
 * author by LiKe on 2017/1/11.
 */

import android.text.TextUtils;

import com.lwgz.meirixue.application.MyApplication;
import com.lwgz.meirixue.utils.CommonUtils;
import com.lwgz.meirixue.manager.HttpMangerUtils;
import com.lwgz.meirixue.utils.Md5;
import com.lwgz.meirixue.utils.ToastUtil;
import com.lwgz.meirixue.view.ShowingPage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public abstract class BaseData {

    private final File fileDir;
    public static final int NOTIME = 0;
    public static final int NORMALTIME = 3 * 24 * 60 * 60 * 1000;


    //缓存数据存到哪里？
    public BaseData() {
        //找到存储路径
        File cacheDir = CommonUtils.getContext().getCacheDir();
        fileDir = new File(cacheDir, "meiru");
        if (!fileDir.exists()) {
            //创建文件夹
            fileDir.mkdir();
        }
    }

    /**
     * @param baseUrl   //基础路径
     * @param path      //请求的实体内容
     * @param validTime //有效时间
     */
    public void getData(String baseUrl, String path, int validTime) {
        //先判断有效时间
        if (validTime == 0) {
            //直接请求网络，要最新数据
            getDataFromNet(baseUrl, path, validTime);
        } else {
            //从本地获取
            String data = getDataFromLocal(path, validTime);
            if (TextUtils.isEmpty(data)) {
                //如果为空，请求网络
                getDataFromNet(baseUrl, path, validTime);
            } else {
                //拿到了数据，返回数据
                setResultData(data);
            }
        }
    }


    /**
     * 从网络获取数据
     *
     * @param path
     * @param validTime
     */
    private void getDataFromNet(String baseUrl, final String path, final int validTime) {
        HttpMangerUtils.getData(baseUrl, path, new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                //将数据抽象出去
                setResultData(response.body());
                //将数据写入本地缓存
                writeDataToLocal(path, validTime, response.body());
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                ToastUtil.show(MyApplication.getContext(), "请求出错，代码" + t.getMessage());
                setResulttError(ShowingPage.StateType.STATE_LOAD_ERROR);
            }
        });

    }

    /**
     * @param isReadCookie 判断是否读取Cookie
     * @param isSaveCookie 判断是否保存Cookie
     * @param baseUrl      设置基础url
     * @param path         标准url
     * @param argsMap      请求参数实体内容
     * @param validTime    设置超时时间
     */
    public void postData(boolean isReadCookie, boolean isSaveCookie, String baseUrl, String path, HashMap<String, String> argsMap, int validTime) {
        Set<String> strings = argsMap.keySet();
        StringBuilder stringBuilder = new StringBuilder();
        for (String key : strings) {
            stringBuilder.append(key).append(argsMap.get(key));
        }
        //先判断有效时间
        if (validTime == 0) {
            //直接请求网络，要最新数据
            postDataFromNet(isReadCookie, isSaveCookie, baseUrl, path, stringBuilder.toString(), argsMap, validTime);
        } else {
            //从本地获取
            String data = getDataFromLocal(baseUrl + path + stringBuilder.toString(), validTime);
            if (TextUtils.isEmpty(data)) {
                //如果为空，请求网络
                postDataFromNet(isReadCookie, isSaveCookie, baseUrl, path, stringBuilder.toString(), argsMap, validTime);
            } else {
                //拿到了数据，返回数据
                setResultData(data);
            }
        }
    }


    /**
     * @param isReadCookie 判断是否读取Cookie
     * @param isSaveCookie 判断是否保存Cookie
     * @param baseUrl      设置基础url
     * @param path         标准url
     * @param keyValues    请求数据的参数key+values
     * @param argsMap      请求参数实体内容
     * @param validTime    设置超时时间
     */
    private void postDataFromNet(boolean isReadCookie, boolean isSaveCookie, final String baseUrl, final String path, final String keyValues, final HashMap<String, String> argsMap, final int validTime) {
        HttpMangerUtils.postMethod(isReadCookie, isSaveCookie, baseUrl, path, argsMap, new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                //将数据抽象出去
                setResultData(response.body());

                //将数据写入本地
                writeDataToLocal(baseUrl + path + keyValues, validTime, response.body());
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                ToastUtil.show(MyApplication.getContext(), "请求出错，代码" + t.getMessage());
                setResulttError(ShowingPage.StateType.STATE_LOAD_ERROR);
            }
        });
    }


    private String getDataFromLocal(String path, int validTime) {
        //读取文件信息
        //读时间
        try {
            File file = new File(fileDir, Md5.Md5(path));
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String s = bufferedReader.readLine();
            long time = Long.parseLong(s);
            //和当前时间进行比较
            //111-110
            if (System.currentTimeMillis() < time) {
                //将信息读出来
                StringBuilder builder = new StringBuilder();
                String line = null;
                while ((line = bufferedReader.readLine()) != null) {
                    builder.append(line);
                }
                bufferedReader.close();
                return builder.toString();
            } else {
                //无效
                return null;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }


    public abstract void setResultData(String data);

    protected abstract void setResulttError(ShowingPage.StateType stateLoadError);

    /**
     * 将数据写到本地
     *
     * @param path
     * @param
     * @param validTime
     * @param
     */
    private void writeDataToLocal(String path, int validTime, String data) {
        //每一条请求，都是生成一个文件   dawedfakwehfaowehfoaw
        try {
            File file = new File(fileDir, Md5.Md5(path));
            //写流信息
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));

            bufferedWriter.write(System.currentTimeMillis() + validTime + "\r\n");
            //从网络上请求的数据
            bufferedWriter.write(data);

            bufferedWriter.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
