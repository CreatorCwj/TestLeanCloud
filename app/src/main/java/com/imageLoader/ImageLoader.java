package com.imageLoader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.imageLoader.listener.ImageProgressStateListener;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.util.UIUtils;

/**
 * Created by cwj on 16/1/14.
 * ImageLoader管理器
 */
public class ImageLoader {

    private static void initImageLodaer(Context context, DisplayImageOptions options) {
        ImageLoaderConfiguration configuration = ImageConfiguration.getConfiguration(context, options);
        com.nostra13.universalimageloader.core.ImageLoader.getInstance().init(configuration);
    }

    /**
     * InitConfig:传入不同的参数来初始化ImageLoader框架,只能初始化一次
     * <p>
     * 参数:loading/failImageId:加载中和加载失败的图片的drawableId
     * loading/failImage:加载中和加载失败的图片的drawable
     * defaultImage/Id:加载中和加载失败使用统一的图片
     */
    public static void initConfig(Context context) {
        DisplayImageOptions options = ImageDisplayOptions.getOptions();
        initImageLodaer(context, options);
    }

    public static void initConfig(Context context, int loadingImageId, Drawable failImage) {
        DisplayImageOptions options = ImageDisplayOptions.getOptions(loadingImageId, failImage);
        initImageLodaer(context, options);
    }

    public static void initConfig(Context context, Drawable loadingImage, int failImageId) {
        DisplayImageOptions options = ImageDisplayOptions.getOptions(loadingImage, failImageId);
        initImageLodaer(context, options);
    }

    public static void initConfig(Context context, int loadingImageId, int failImageId) {
        DisplayImageOptions options = ImageDisplayOptions.getOptions(loadingImageId, failImageId);
        initImageLodaer(context, options);
    }

    public static void initConfig(Context context, Drawable loadingImage, Drawable failImage) {
        DisplayImageOptions options = ImageDisplayOptions.getOptions(loadingImage, failImage);
        initImageLodaer(context, options);
    }

    public static void initConfig(Context context, int defaultImageId) {
        DisplayImageOptions options = ImageDisplayOptions.getOptions(defaultImageId);
        initImageLodaer(context, options);
    }

    public static void initConfig(Context context, Drawable defaultImage) {
        DisplayImageOptions options = ImageDisplayOptions.getOptions(defaultImage);
        initImageLodaer(context, options);
    }

    /**
     * 加载图片:最简单的方式
     *
     * @param imageView
     * @param imgUrl
     */
    public static void loadImage(ImageView imageView, String imgUrl) {
        com.nostra13.universalimageloader.core.ImageLoader.getInstance()
                .displayImage(imgUrl, imageView);
    }

    /**
     * 加载图片:实时返回进度和状态(缓存加载时不会有进度)
     *
     * @param imageView
     * @param imgUrl
     */
    public static void loadImage(final ImageView imageView, final String imgUrl, final ImageProgressStateListener callback) {
        com.nostra13.universalimageloader.core.ImageLoader.getInstance()
                .displayImage(imgUrl, imageView, null, new ImageLoadingListener() {
                    @Override
                    public void onLoadingStarted(String imageUri, View view) {
                        if (callback != null)
                            callback.onLoadingStarted(imageView, imgUrl);
                    }

                    @Override
                    public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                        if (callback != null) {
                            callback.onLoadingFailed(imageView, imgUrl, failReason.getType());
                            callback.onLoadingFinally(imageView, imgUrl);
                        }
                    }

                    @Override
                    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                        Log.i("ImageLoader-ImageSize", "w:" + loadedImage.getWidth() + " h:" + loadedImage.getHeight());
                        if (callback != null) {
                            callback.onLoadingComplete(imageView, imgUrl, loadedImage);
                            callback.onLoadingFinally(imageView, imgUrl);
                        }
                    }

                    @Override
                    public void onLoadingCancelled(String imageUri, View view) {
                        if (callback != null) {
                            callback.onLoadingCancelled(imageView, imgUrl);
                            callback.onLoadingFinally(imageView, imgUrl);
                        }
                    }
                }, new ImageLoadingProgressListener() {
                    @Override
                    public void onProgressUpdate(String imageUri, View view, int current, int total) {
                        int result = 0;
                        double progress;
                        if (total != 0) {
                            progress = ((double) current / total) * 100;
                            result = (int) progress;
                        }
                        //result有时会超出100(current>total),不知道为啥。。。
                        if (result > 100)
                            result = 100;
                        Log.i("ImageLoader-Progress", result + "%");
                        if (callback != null)
                            callback.onProgress(imageView, imgUrl, current, total, result);
                    }
                });
    }

    /**
     * 加载图片:指定DP尺寸
     * 直接加载在ImageView中
     *
     * @param imageView
     * @param imgUrl
     */
    public static void loadImageToSizeFromDp(ImageView imageView, String imgUrl, int width, int height) {
        loadImageToSizeFromDp(imageView, imgUrl, width, height, null);
    }

    /**
     * 加载图片:指定PX尺寸(如果超过实际大小则加载实际大小,可以控制ImageView的scaleType来拉伸等操作)
     * 直接加载在ImageView中
     *
     * @param imageView
     * @param imgUrl
     */
    public static void loadImageToSize(final ImageView imageView, String imgUrl, int width, int height) {
        loadImageToSize(imageView, imgUrl, width, height, null);
    }

    /**
     * 加载图片:指定DP尺寸
     * 有回调方法
     *
     * @param imageView
     * @param imgUrl
     */
    public static void loadImageToSizeFromDp(ImageView imageView, String imgUrl, int width, int height, final ImageProgressStateListener listener) {
        int pxWidth = UIUtils.dp2px(imageView.getContext(), width);
        int pxHeight = UIUtils.dp2px(imageView.getContext(), height);
        loadImageToSize(imageView, imgUrl, pxWidth, pxHeight, listener);
    }

    /**
     * 加载图片:指定PX尺寸(如果超过实际大小则加载实际大小,可以控制ImageView的scaleType来拉伸等操作)
     * 有回调方法
     * 不需要imageView时传null即可
     * 缓存取时没有进度
     *
     * @param imageView
     * @param imgUrl
     */
    public static void loadImageToSize(@Nullable final ImageView imageView, final String imgUrl, int width, int height, final ImageProgressStateListener listener) {
        ImageSize imageSize = new ImageSize(width, height);
        com.nostra13.universalimageloader.core.ImageLoader.getInstance()
                .loadImage(imgUrl, imageSize, null, new ImageLoadingListener() {
                    @Override
                    public void onLoadingStarted(String imageUri, View view) {
                        if (listener != null)
                            listener.onLoadingStarted(imageView, imgUrl);
                    }

                    @Override
                    public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                        if (listener != null) {
                            listener.onLoadingFailed(imageView, imgUrl, failReason.getType());
                            listener.onLoadingFinally(imageView, imgUrl);
                        }
                    }

                    @Override
                    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                        Log.i("ImageLoader-ImageSize", "w:" + loadedImage.getWidth() + " h:" + loadedImage.getHeight());
                        if (listener != null) {
                            listener.onLoadingComplete(imageView, imgUrl, loadedImage);
                            listener.onLoadingFinally(imageView, imgUrl);
                        } else if (imageView != null) {
                            imageView.setImageBitmap(loadedImage);
                        }
                    }

                    @Override
                    public void onLoadingCancelled(String imageUri, View view) {
                        if (listener != null) {
                            listener.onLoadingCancelled(imageView, imgUrl);
                            listener.onLoadingFinally(imageView, imgUrl);
                        }
                    }
                }, new ImageLoadingProgressListener() {
                    @Override
                    public void onProgressUpdate(String imageUri, View view, int current, int total) {
                        int result = 0;
                        double progress;
                        if (total != 0) {
                            progress = ((double) current / total) * 100;
                            result = (int) progress;
                        }
                        Log.i("ImageLoader-Progress", result + "%");
                        if (listener != null)
                            listener.onProgress(imageView, imgUrl, current, total, result);
                    }
                });
    }

    /**
     * 取消某个imageview的图片加载请求
     *
     * @param imageView
     */
    public static void cancelLoad(ImageView imageView) {
        com.nostra13.universalimageloader.core.ImageLoader.getInstance().cancelDisplayTask(imageView);
    }

    /**
     * 取消全部的图片加载请求
     */
    public static void cancelAllLoad() {
        com.nostra13.universalimageloader.core.ImageLoader.getInstance().stop();
    }

    /**
     * 暂停全部的图片加载请求
     */
    public static void pause() {
        com.nostra13.universalimageloader.core.ImageLoader.getInstance().pause();
    }

    /**
     * 恢复全部的图片加载请求
     */
    public static void resume() {
        com.nostra13.universalimageloader.core.ImageLoader.getInstance().resume();
    }

    /**
     * 清除文件缓存
     */
    public static void clearDiskCache() {
        com.nostra13.universalimageloader.core.ImageLoader.getInstance().clearDiskCache();
    }

    /**
     * 清除内存缓存
     */
    public static void clearMemoryCache() {
        com.nostra13.universalimageloader.core.ImageLoader.getInstance().clearMemoryCache();
    }

    /**
     * 是否已经初始化
     *
     * @return
     */
    public static boolean isInitial() {
        return com.nostra13.universalimageloader.core.ImageLoader.getInstance().isInited();
    }

}
