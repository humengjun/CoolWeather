package com.hmj.demo.coolweather.utils;

import android.support.design.widget.Snackbar;
import android.view.View;

public class SnackbarUtils {

    private static Snackbar shortSnackbar;
    private static Snackbar longSnackbar;

    public static void shortShow(View view, String message,
                                 String actionName, View.OnClickListener listener) {
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT)
                .setAction(actionName, listener)
                .show();

    }

    public static void longShow(View view, String message,
                                String actionName, View.OnClickListener listener) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG)
                .setAction(actionName, listener)
                .show();

    }
}
