package com.hmj.demo.coolweather.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Administrator on 2018/9/1 0001.
 */

public class ToastUtils {

    private static Toast longToast;
    private static Toast shortToast;

    public static void shortToast(Context context, String message) {
        if (shortToast == null) {
            synchronized (ToastUtils.class) {
                if (shortToast == null) {
                    shortToast = Toast.makeText(context.getApplicationContext(), message, Toast.LENGTH_SHORT);
                } else {
                    shortToast.setText(message);
                }
            }
        } else {
            shortToast.setText(message);
        }
        shortToast.show();
    }

    public static void longToast(Context context, String message) {
        if (longToast == null) {
            synchronized (ToastUtils.class) {
                if (longToast == null) {
                    longToast = Toast.makeText(context.getApplicationContext(), message, Toast.LENGTH_LONG);
                }else {
                    longToast.setText(message);
                }
            }
        } else {
            longToast.setText(message);
        }
        longToast.show();
    }

}
