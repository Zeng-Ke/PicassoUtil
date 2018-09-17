package com.zk.picassodemo;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import com.squareup.picasso.Transformation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

/**
 * author: ZK.
 * date:   On 2018-08-22.
 */
public class ImageLoaderUtils {

    private static boolean isDebug = false;


    private static @DrawableRes
    int res_id_icon_placeloader = R.mipmap.default_portrait;

    private static @DrawableRes
    int res_id_normal_placeloader = R.mipmap.icon_pic_loding;


    private static OkHttp3Downloader iconDownloader;
    private static Picasso iconPicasso;
    private static Picasso imagePicasso;


    public static void init(Context context, boolean debug) {
        iconDownloader = new OkHttp3Downloader(ImageLoader.createCacheDir(context, "tginfo_icon_cache"));
        iconPicasso = new Picasso
                .Builder(context)
                .downloader(iconDownloader)
                .build();
        imagePicasso = new Picasso
                .Builder(context)
                .downloader(new OkHttp3Downloader(ImageLoader.createCacheDir(context, "tginfo_image_cache"), 500 * 1024 * 1024))
                .build();
        isDebug = debug;
    }


    public static Picasso getImgPicasso() {
        return imagePicasso;
    }


    public static void invalidateIcon(@NonNull String url) {
        iconPicasso.invalidate(url);
    }

    public static void invalidateImage(@NonNull String url) {
        imagePicasso.invalidate(url);
    }


    /**
     * 加载圆头像
     */
    public static void loadCircleIcon(String url, ImageView imageView) {
        loadCircleIcon(url, imageView, true);
    }

    /**
     * 加载圆角头像
     */
    public static void loadRoundCornerIcon(String url, ImageView imageView, int radius, ImageLoader.CornerType cornerType) {
        loadIcon(url, imageView, true, radius, cornerType);
    }

    /**
     * 加载矩形头像
     */
    public static void loadRectIcon(String url, ImageView imageView) {
        loadIcon(url, imageView, true, 0, null);
    }


    public static void loadCircleIcon(String url, ImageView imageView, boolean cache) {
        loadIcon(url, imageView, res_id_icon_placeloader, res_id_icon_placeloader, cache, true, 0, null, false);
    }

    public static void loadIcon(String url, ImageView imageView, boolean cache, int radius, ImageLoader.CornerType cornerType) {
        loadIcon(url, imageView, res_id_icon_placeloader, res_id_icon_placeloader, cache, false, radius, cornerType, false);
    }

    public static void loadNormalImage(String url, ImageView imageView) {
        loadNormalImage(url, imageView, res_id_normal_placeloader, res_id_normal_placeloader);
    }

    public static void loadNormalImage(String url, ImageView imageView, boolean cache) {
        loadImage(url, imageView, res_id_normal_placeloader, res_id_normal_placeloader, cache, 0, null, false, null);
    }

    public static void loadNormalImage(String url, ImageView imageView, @DrawableRes int placeLoaderResId, @DrawableRes int errorResId) {
        loadImage(url, imageView, placeLoaderResId, errorResId, true, 0, null, false, null);
    }


    /**
     * @param onLoadCallBack 不要使用匿名内部类，因为内部使用弱引用很大可能会被回收掉
     */
    public static void loadNormalImage(String url, ImageView imageView, LoadCallBack onLoadCallBack) {
        loadImage(url, imageView, res_id_normal_placeloader, res_id_normal_placeloader, true, 0, null, false, onLoadCallBack);
    }

    public static void loadNormalImage(String url, Target target) {
        loadImage(url, target, true, 0, null, false);
    }


    public static void loadNormalImage(String url, ImageView imageView, @DrawableRes int placeLoaderResId, @DrawableRes int errorResId,
                                       LoadCallBack onLoadCallBack) {
        loadImage(url, imageView, placeLoaderResId, errorResId, true, 0, null, false, onLoadCallBack, new Transformation[0]);
    }

    public static void loadRoundCornerImage(String url, ImageView loadTarget, int cornerRadius, ImageLoader.CornerType cornerType) {
        loadImage(url, loadTarget, res_id_normal_placeloader, res_id_normal_placeloader, true, cornerRadius,
                cornerType, false, null);
    }

    public static void loadRoundCornerImage(String url, ImageView imageView, @DrawableRes int placeLoaderResId, @DrawableRes int
            errorResId, int cornerRadius, ImageLoader.CornerType cornerType, LoadCallBack onLoadCallBack) {
        loadImage(url, imageView, placeLoaderResId, errorResId, true, cornerRadius, cornerType, false, onLoadCallBack, new
                Transformation[0]);
    }

    public static void loadRoundCornerImage(String url, ImageView imageView, @DrawableRes int placeLoaderResId, @DrawableRes int
            errorResId, int cornerRadius, ImageLoader.CornerType cornerType) {
        loadImage(url, imageView, placeLoaderResId, errorResId, true, cornerRadius, cornerType, false, null);
    }


    public static void loadRoundCornerImage(String url, int cornerRadius, ImageLoader.CornerType cornerType, Target loadTarget) {
        loadImage(url, loadTarget, true, cornerRadius, cornerType, false, new Transformation[0]);
    }

    public static void updateCircleIcon(String url, ImageView imageView) {
        loadIcon(url, imageView, res_id_icon_placeloader, res_id_icon_placeloader, true, true, 0, null, true);
    }

    public static void updateNormalImage(String url, ImageView imageView) {
        loadImage(url, imageView, res_id_normal_placeloader, res_id_normal_placeloader, true, 0, null, true, null);
    }


    public static void loadIcon(String url, ImageView imageView, @DrawableRes int placeLoaderResId, @DrawableRes int errorResId, boolean
            cache, boolean circle, int radius, ImageLoader.CornerType cornerType, boolean updateImg) {
        new ImageLoader.Builder(iconPicasso, url, imageView)
                .setBooleanIndicatorsEnabled(isDebug)
                .setBooleanLoggingEnabled(isDebug)
                .setBooleanCircle(circle)
                .setRoundCornerRadius(radius, cornerType)
                .setBooleanCache(cache)
                .setPlaceloaderId(placeLoaderResId)
                .setErrResId(errorResId)
                .setBooleanUpdateImg(updateImg)
                .setBooleanFit(true)
                .setBooleanCenterCrop(true)
                .build();
    }

    /**
     * @param url
     * @param imageView
     * @param placeLoaderResId
     * @param errorResId
     * @param cache
     * @param cornerRadius
     * @param cornerType
     * @param onLoadCallBack   不要使用匿名内部类，因为内部使用弱引用很大可能会被回收掉
     * @param transformations
     */
    public static void loadImage(String url, ImageView imageView, @DrawableRes int placeLoaderResId, @DrawableRes int errorResId,
                                 boolean cache, int cornerRadius, ImageLoader.CornerType cornerType, boolean updateImg, LoadCallBack
                                         onLoadCallBack, Transformation... transformations) {
        new ImageLoader.Builder(imagePicasso, url, imageView)
                .setBooleanIndicatorsEnabled(isDebug)
                .setBooleanLoggingEnabled(isDebug)
                .setBooleanCenterCrop(true)
                .setPlaceloaderId(placeLoaderResId)
                .setErrResId(errorResId)
                .setRoundCornerRadius(cornerRadius, cornerType)
                .setBooleanFit(true)
                .setBooleanUpdateImg(updateImg)
                .setBooleanCenterCrop(true)
                .setBooleanCache(cache)
                .addTransformation(transformations)
                .setOnLoadCallBack(onLoadCallBack == null ? null : onLoadCallBack.getCallback())
                .build();
    }


    /**
     * @param url
     * @param target          不要使用匿名内部类，因为内部使用弱引用很大可能会被回收掉
     * @param cache
     * @param cornerRadius
     * @param cornerType
     * @param transformations
     */
    public static void loadImage(String url, Target target, boolean cache, int cornerRadius, ImageLoader.CornerType cornerType,
                                 boolean updateImg, Transformation... transformations) {
        new ImageLoader.Builder(imagePicasso, url, target.getTarget())
                .setBooleanIndicatorsEnabled(isDebug)
                .setBooleanLoggingEnabled(isDebug)
                .setRoundCornerRadius(cornerRadius, cornerType)
                .setBooleanCache(cache)
                .setBooleanUpdateImg(updateImg)
                .addTransformation(transformations)
                .build();
    }


    public static void cancleTargetRequest(List<Target> targets) {
        if (targets == null || targets.size() == 0)
            return;
        for (Target target : targets) {
            Picasso.get().cancelRequest(target.getTarget());
        }
    }

    public static void cancleTargetRequest(Target target) {
        if (target != null)
            Picasso.get().cancelRequest(target.target);
    }

    public static void cancleRequests(List<ImageView> imageViews) {
        if (imageViews == null || imageViews.size() == 0)
            return;
        for (ImageView imageView : imageViews) {
            Picasso.get().cancelRequest(imageView);
        }
    }

    public static void cancleRequests(ImageView imageView) {
        if (imageView != null)
            Picasso.get().cancelRequest(imageView);
    }


    public static class LoadCallBack {

        private Callback callback;


        public LoadCallBack() {
            this.callback = new Callback() {
                @Override
                public void onSuccess() {
                    LoadCallBack.this.onSuccess();
                }

                @Override
                public void onError(Exception e) {
                    LoadCallBack.this.onError(e);
                }
            };
        }

        public Callback getCallback() {
            return callback;
        }

        public void onSuccess() {
        }

        public void onError(Exception e) {
        }
    }


    public static class Target {

        private com.squareup.picasso.Target target;


        public Target() {
            this.target = new com.squareup.picasso.Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    @LoadedFrom int loadedFrom = NETWORK;
                    switch (from) {
                        case MEMORY:
                            loadedFrom = MEMORY;
                            break;
                        case DISK:
                            loadedFrom = DISK;
                            break;
                        case NETWORK:
                            loadedFrom = NETWORK;
                            break;
                    }
                    com.zk.picassodemo.ImageLoaderUtils.Target.this.onBitmapLoaded(bitmap, loadedFrom);
                }

                @Override
                public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                    com.zk.picassodemo.ImageLoaderUtils.Target.this.onBitmapFailed(e, errorDrawable);
                }

                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {
                    com.zk.picassodemo.ImageLoaderUtils.Target.this.onPrepareLoad(placeHolderDrawable);
                }
            };
        }

        public com.squareup.picasso.Target getTarget() {
            return target;
        }

        public void onBitmapLoaded(Bitmap bitmap, @LoadedFrom int from) {

        }

        public void onBitmapFailed(Exception e, Drawable errorDrawable) {

        }

        public void onPrepareLoad(Drawable placeHolderDrawable) {

        }


    }


    private static final int MEMORY = 0x101;
    private static final int DISK = 0x102;
    private static final int NETWORK = 0x103;

    @IntDef({MEMORY, DISK, NETWORK})
    @Retention(RetentionPolicy.SOURCE)
    public @interface LoadedFrom {


    }


}
