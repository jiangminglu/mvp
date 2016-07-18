package com.luffy.mvp.util;

import android.widget.Toast;

import com.luffy.mvp.app.MApp;

/**
 * Created by jiangminglu on 16/7/6.
 */
public class ToastUtil {

    public static void showToast(String msg){
        Toast.makeText(MApp.getInstance(),msg,Toast.LENGTH_SHORT).show();
    }
}
