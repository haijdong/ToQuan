package com.whale.nangua.toquan.utils;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.LruCache;

/**
 * Created by nangua on 2016/7/19.
 */
public class NGImageLoader {

    /**
     * 图片缓存技术的核心类，用于缓存所有下载好的图片，
     * 在程序内存达到设定值时会将最少最近使用的图片移除掉
     */
    private static LruCache<String, Bitmap> mMemoryCache;

    /**
     * 自身实例，使用单例模式
     */
    private static NGImageLoader mNGImageLoader;

    private NGImageLoader() {
        //获取应用程序最大可用内存
        int maxMemory = (int) Runtime.getRuntime().maxMemory();
        int cacheSize = maxMemory / 8;
        //设置图片缓存大小为程序最大可用内存的1/8
        mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount();
            }
        };
    }

    /**
     * 单例防止并发
     *
     * @return
     */
    public static NGImageLoader getInstance() {
        if (mNGImageLoader == null) {
            mNGImageLoader = new NGImageLoader();
        }
        return mNGImageLoader;
    }

    /**
     * 将一张图片存储到LruCache中
     *
     * @param key    LruCache的键，这里传入图片url地址
     * @param bitmap LruCache的键，这里传入从网上下载的Bitmap对象
     */
    public void addBitmapToMemoryCache(String key, Bitmap bitmap) {
        if (getBitmapFromMemoryCache(key) == null) {
            mMemoryCache.put(key, bitmap);
        }
    }

    /**
     * 从LruCache中获取一张图片，如果不存在就返回null。
     *
     * @param key LruCache的键，这里传入图片的URL地址。
     * @return 对应传入键的Bitmap对象，或者null。
     */
    public Bitmap getBitmapFromMemoryCache(String key) {
        return mMemoryCache.get(key);
    }

    /**
     * 计算缩减比例
     * 使用BitmapFactory.Options为了防止直接使用Bitmap的get长宽方法所造成的内存溢出
     * BitmapFactory.Options这个类，有一个字段叫做 inJustDecodeBounds 。
     * SDK中对这个成员的说明是这样的：
     * If set to true, the decoder will return null (no bitmap), but the out…
     * 也就是说，如果我们把它设为true，那么BitmapFactory.decodeFile(String path, Options opt)并不会真的返回一个Bitmap给你，它仅仅会把它的宽，高取回来给你，这样就不会占用太多的内存，也就不会那么频繁的发生OOM了。
     * 通过inSampleSize  这个成员变量。
     * 我们可以根据图片实际的宽高和我们期望的宽高来计算得到这个值。
     * options.inSampleSize = options.outWidth / 200;  图片长宽方向缩小倍数
     * 其他节约内存的字段：
     * options.inDither=false;     不进行图片抖动处理
     * options.inPreferredConfig=null;  设置让解码器以最佳方式解码
     * 下面两个字段需要组合使用
     * options.inPurgeable = true;
     * options.inInputShareable = true;
     *
     * @param options
     * @param reqWidth
     * @return
     */
    public static int calculateInSampleSize(BitmapFactory.Options options,
                                            int reqWidth) {
        //源图片的宽度
        final int width = options.outWidth;
        int inSampleSize = 1;
        //如果原图片宽度大于要求的宽度
        if (width > reqWidth) {
            //计算出实际宽度和目标宽度的比率
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = widthRatio;
        }
        return inSampleSize;
    }

    /**
     * 这个方法用来进行缩小操作，保证最后图片的宽和高小于等于目标的宽和高
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    public static int calculateInSampleSize(BitmapFactory.Options options,
                                            int reqWidth, int reqHeight) {
        final int height = options.outHeight;   //这里是屏幕高度
        final int width = options.outWidth; //屏幕宽度
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            // 计算出实际宽高和目标宽高的比率
            final int heightRatio = Math.round((float) reqHeight / (float) height);
            final int widthRatio = Math.round((float) reqWidth / (float) width);
            // 选择宽和高中最小的比率作为inSampleSize的值，这样可以保证最终图片的宽和高
            // 一定都会小于等于目标的宽和高。
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }

    /**
     * 根据宽度裁剪
     * 根据路径从内存中获取图片
     * @param pathName
     * @param reqWidth
     * @return
     */
    public static Bitmap decodeSampledBitmapFromResource(String pathName,
                                                         int reqWidth) {
        //第一次解析将inJustDecodeBounds设置为true，来获取图片大小
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;//只计算宽高，不得到图片实例，节省内存
        BitmapFactory.decodeFile(pathName, options);
        //调用上面定义的方法计算inSampleSize的值
        options.inSampleSize = calculateInSampleSize(options, reqWidth);
        //使用获取到的inSampleSize的值再次解析图片
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(pathName, options);
    }

    public static Bitmap decodeSampledBitmapFromResource(String pathName,
                                                         int reqWidth ,int reqHeight) {
        //第一次解析将inJustDecodeBounds设置为true，来获取图片大小
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;//只计算宽高，不得到图片实例，节省内存
        BitmapFactory.decodeFile(pathName, options);
        //调用上面定义的方法计算inSampleSize的值
        options.inSampleSize = calculateInSampleSize(options, reqWidth,reqHeight);
        //使用获取到的inSampleSize的值再次解析图片
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(pathName, options);
    }

    /**
     * 加载大图缩略图
     *  mImageView.setImageBitmap(
        decodeSampledBitmapFromResource(getResources(), R.id.myimage, 100, 100));
     * @param res
     * @param resId
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
                                                         int reqWidth, int reqHeight) {
        // 第一次解析将inJustDecodeBounds设置为true，来获取图片大小
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);
        // 调用上面定义的方法计算inSampleSize值
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        // 使用获取到的inSampleSize值再次解析图片
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }
}
