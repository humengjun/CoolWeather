package com.hmj.demo.coolweather.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.InputStream;

public class BitmapCompression {

    private static final String TAG = "BitmapCompression";

    /**
     * 原始尺寸
     */
    public static Bitmap getOriginalBitmapFromResource(Context context, int resourceId) {
        return BitmapFactory.decodeResource(context.getResources(), resourceId);
    }

    public static Bitmap getOriginalBitmapFromFile(String path) {
        return BitmapFactory.decodeFile(path);
    }

    public static Bitmap getOriginalBitmapFromStream(InputStream stream) {
        return BitmapFactory.decodeStream(stream);
    }

    /**
     * 等比缩放
     * 设置图片解码方式
     * RGB_565  5+6+5=16位   2字节
     * ARGB_4444    4+4+4+4=16位 2字节
     * ARGB_8888(默认)    8+8+8+8=32位 4字节
     * ALPHA_8  8位  1字节
     */
    public static Bitmap getZoomBitmapFromResource(Context context, int resourceId, int screenWidth, int screenHeight, Bitmap.Config config) {
        //创建位图工厂的配置参数
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;//不去真正解析位图，但仍然能获取到图片的宽高
        options.inPreferredConfig = config;
        BitmapFactory.decodeResource(context.getResources(), resourceId, options);
        options = getZoomOptions(screenWidth, screenHeight, options);
        return BitmapFactory.decodeResource(context.getResources(), resourceId, options);
    }

    public static Bitmap getZoomBitmapFromFile(String path, int screenWidth, int screenHeight, Bitmap.Config config) {
        //创建位图工厂的配置参数
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;//不去真正解析位图，但仍然能获取到图片的宽高
        options.inPreferredConfig = config;
        BitmapFactory.decodeFile(path, options);
        options = getZoomOptions(screenWidth, screenHeight, options);
        return BitmapFactory.decodeFile(path, options);
    }

    public static Bitmap getZoomBitmapFromStream(InputStream stream, int screenWidth, int screenHeight, Bitmap.Config config) {
        //创建位图工厂的配置参数
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;//不去真正解析位图，但仍然能获取到图片的宽高
        options.inPreferredConfig = config;
        BitmapFactory.decodeStream(stream, null, options);
        options = getZoomOptions(screenWidth, screenHeight, options);
        return BitmapFactory.decodeStream(stream, null, options);
    }

    private static BitmapFactory.Options getZoomOptions(int screenWidth, int screenHeight, BitmapFactory.Options options) {
        //获取图片的宽高
        int mWidth = options.outWidth;
        int mHeight = options.outHeight;
        Log.d(TAG, "图片的宽：" + mWidth + ",图片的高：" + mHeight);
        //计算缩放比
        int scale = 1;//定义一个变量  缩放比
        int scaleX = mWidth / screenWidth;
        int scaleY = mHeight / screenHeight;
        if (scaleX > scaleY && scaleX > scale) {
            scale = scaleX;
        }
        if (scaleY > scaleX && scaleY > scale) {
            scale = scaleY;
        }
        Log.d(TAG, "图片的缩放比为：" + scale);
        options.inSampleSize = scale;
        options.inJustDecodeBounds = false;//真正解析位图
        return options;
    }

//    /**
//     * 加载部分图片
//     */
//    private static Bitmap getLoadPartBitmap(String path) {
//        try {
//            FileInputStream fis = new FileInputStream(path);
//            BitmapRegionDecoder bitmapRegionDecoder =
//                    BitmapRegionDecoder.newInstance(fis, false);
//            //创建位图工厂的配置参数
//            BitmapFactory.Options options = new BitmapFactory.Options();
//            //获取图片的宽高
//            int mWidth = options.outWidth;
//            int mHeight = options.outHeight;
//
//            //设置显示图片的中心区域
//            options.inPreferredConfig = Bitmap.Config.ARGB_4444;
//            return bitmapRegionDecoder.decodeRegion(new Rect(mWidth / 2 - 100, mHeight / 2 - 100,
//                    mWidth / 2 + 100, mHeight / 2 + 100), options);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

}
