package com.hmj.demo.coolweather.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

public class DialogUtils {

    private static AlertDialog.Builder alertBuilder;
    private static ProgressDialog progressDialog;

    public static void showAlertDialog(Context context, final DialogParams params) {

        if (alertBuilder == null) {
            synchronized (DialogUtils.class) {
                if (alertBuilder == null) {
                    alertBuilder = new AlertDialog.Builder(context);
                }
            }
        }
        alertBuilder.setTitle(params.title)
                .setMessage(params.message)
                .setPositiveButton(params.positiveButtonValue, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        params.listener.onclickPositiveButton();
                    }
                })
                .setNegativeButton(params.negativeButtonValue, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        params.listener.onclickNegativeButton();
                    }
                })
                .setCancelable(false)
                .show();

    }

    public static void showProgressDialog(Context context, String title, String message){
        if (progressDialog == null) {
            synchronized (DialogUtils.class) {
                if (progressDialog == null) {
                    progressDialog = new ProgressDialog(context);
                }
            }
        }
        progressDialog.setTitle(title);
        progressDialog.setMessage(message);
        progressDialog.show();
        progressDialog.setCancelable(true);
    }

    public static class DialogParams {
        private String message;
        private String title;
        private String positiveButtonValue;
        private String negativeButtonValue;
        private OnClickListener listener;

        public DialogParams(String title, String message, String positiveButtonValue, String negativeButtonValue, OnClickListener listener) {
            this.title = title;
            this.message = message;
            this.positiveButtonValue = positiveButtonValue;
            this.negativeButtonValue = negativeButtonValue;
            this.listener = listener;
        }

    }

    public interface OnClickListener {

        void onclickPositiveButton();

        void onclickNegativeButton();
    }

}
