package com.hmj.demo.coolweather.utils;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import android.provider.MediaStore;

public class ImagePathUtils {

    private static String getImagePathOnKitKat(Context context, Uri uri) {
        String imagePath = null;
        if (DocumentsContract.isDocumentUri(context, uri)) {
            //如果是document类型的uri，则通过document id 处理
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1];
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePathBeforeKitKat(context, MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"),
                        Long.valueOf(docId));
                imagePath = getImagePathBeforeKitKat(context, contentUri, null);
            }

        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            //如果是content类型的uri，则使用普通方式处理
            imagePath = getImagePathBeforeKitKat(context, uri, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            //如果是file类型的uri，直接获取图片路径即可
            imagePath = uri.getPath();
        }
        return imagePath;
    }

    private static String getImagePathBeforeKitKat(Context context, Uri uri, String selection) {
        String imagePath = null;
        Cursor cursor = context.getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                imagePath = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                cursor.close();
            }
        }
        return imagePath;
    }

    public static String getImagePath(Context context, Uri uri, String selection) {
        if (Build.VERSION.SDK_INT >= 19) {
            //Android版本大于等于4.4
            return getImagePathOnKitKat(context, uri);
        } else {
            //Android版本小于4.4
            return getImagePathBeforeKitKat(context, uri, selection);
        }
    }

    public static String getImagePath(Context context, Uri uri) {
        if (Build.VERSION.SDK_INT >= 19) {
            //Android版本大于等于4.4
            return getImagePathOnKitKat(context, uri);
        } else {
            //Android版本小于4.4
            return getImagePathBeforeKitKat(context, uri, null);
        }
    }


}
