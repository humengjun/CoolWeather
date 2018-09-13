package com.hmj.demo.coolweather.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class ActivityUtils {

    private static List<Activity> allActivityList = new ArrayList<>();

    public static void startEmptyActivity(Context context, Class<?> cls) {
        context.startActivity(new Intent(context, cls));
    }

    public static void startParamsActivity(Context context, Class<?> cls, Bundle bundle) {
        Intent intent = new Intent(context, cls);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    public static void startEmptyActivityForResult(Activity activity, Class<?> cls, int requestCode) {
        activity.startActivityForResult(new Intent(activity, cls), requestCode);
    }

    public static void startParamsActivityForResult(Activity activity, Class<?> cls, Bundle bundle, int requestCode) {
        Intent intent = new Intent(activity, cls);
        intent.putExtras(bundle);
        activity.startActivityForResult(intent, requestCode);
    }

    public static boolean addActivity(Activity activity) {
        return allActivityList.add(activity);
    }

    public static boolean removeActivity(Activity activity) {
        return allActivityList.remove(activity);
    }

    public static boolean removeAllActivity() {
        allActivityList.clear();
        return allActivityList.size() == 0 ? true : false;
    }

    public static void finishAllActivity(){
        for (Activity activity:allActivityList) {
            activity.finish();
        }
        removeAllActivity();
    }

}
