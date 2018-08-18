package com.zk.picassodemo;

import android.content.Context;
import android.support.annotation.IdRes;
import android.widget.ImageView;

import com.squareup.picasso.Downloader;
import com.squareup.picasso.OkHttp3Downloader;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import com.squareup.picasso.Target;
import com.squareup.picasso.Transformation;

import java.io.File;

/**
 * author: ZK.
 * date:   On 2018-08-18.
 */
public class ImageLoader {

    private static final int MAX_DISK_CACHE_SIZE = 50 * 1024 * 1024; // 50MB

    private RequestCreator mRequestCreator;

    private ImageLoader(RequestCreator requestCreator) {
        mRequestCreator = requestCreator;
    }

    private ImageLoader() {

    }

    public static class Builder {

        private Context context;

        private Picasso picasso;

        private Downloader downloader;

        private @IdRes
        int errResId;

        private @IdRes
        int placeloaderId;

        private String url;

        private ImageView imageView;

        private Target target;

        private Transformation transformation;

        private boolean booleanNoPlaceLoader = false;

        private boolean booleanNoFade = false;

        private int[] resizeArray;

        private boolean booleanCenterCrop = false;

        private boolean booleanCenterInside = false;

        private boolean booleanFit = false;

        private Picasso.Priority priority;


        public Builder(Context context) {
            this.context = context;
        }

        public Builder(Picasso picasso) {
            this.context = context;
        }

        public Picasso getPicasso() {
            return picasso;
        }

        public Builder setDownloader(Downloader downloader) {
            this.downloader = downloader;
            return this;
        }

        public Builder setErrResId(int errResId) {
            this.errResId = errResId;
            return this;
        }

        public Builder setPlaceloaderId(int placeloaderId) {
            this.placeloaderId = placeloaderId;
            return this;
        }

        public Builder setUrl(String url) {
            this.url = url;
            return this;
        }

        public Builder setImageView(ImageView imageView) {
            this.imageView = imageView;
            return this;
        }

        //设置加载回调
        public Builder setTarget(Target target) {
            this.target = target;
            return this;
        }

        public Builder setTransformation(Transformation transformation) {
            this.transformation = transformation;
            return this;
        }

        /**
         * 我们来想一下下面这种情况：你在ImageView中加载了一张图片，一段时间过后，你想在同样的ImageView中加载另一张图片。使用默认的设置，你在重新调用Picasso的时候，ImageView将会加载之前设置的placeholder
         * 的图片。如果这个ImageView在你的UI中很重要的话，在几秒钟内快速的改变ImageView的图片会看起来很不合适。一个比较好的解决方案是调用`.noPlaceholder()
         * `这个方法。在第二张图片加载之前，ImageView会一直显示第一张图片。这对用户来说会很友好。
         */
        public Builder setBooleanNoPlaceLoader(boolean booleanNoPlaceLoader) {
            this.booleanNoPlaceLoader = booleanNoPlaceLoader;
            return this;
        }

        //为false时，加载图片到ImageView时，有一个渐入过度的效果
        public Builder setBooleanNoFade(boolean booleanNoFade) {
            this.booleanNoFade = booleanNoFade;
            return this;
        }

        //重新设置图片的尺寸,单位dp
        public Builder setResizeArray(int[] resizeArray) {
            this.resizeArray = resizeArray;
            return this;
        }

        //充满ImageView 的边界，居中裁剪
        public Builder setBooleanCenterCrop(boolean booleanCenterCrop) {
            this.booleanCenterCrop = booleanCenterCrop;
            return this;
        }

        //让View将图片展示完全，可以用centerInside，但是如果图片尺寸小于View尺寸的话，是不能充满View边界的
        public Builder setBooleanCenterInside(boolean booleanCenterInside) {
            this.booleanCenterInside = booleanCenterInside;
            return this;
        }

        /**
         * 自动测量我们的View的大小，然后内部调用reszie方法把图片裁剪到View的大小,最好配合前面的centerCrop使用
         * 1，fit 只对ImageView 有效
         * 2，使用fit时，ImageView 宽和高不能为wrap_content,很好理解，因为它要测量宽高。
         */
        public Builder setBooleanFit(boolean booleanFit) {
            this.booleanFit = booleanFit;
            return this;
        }

        //请求优先级:LOW、NORMAL、HIGH
        public Builder setPriority(Picasso.Priority priority) {
            this.priority = priority;
            return this;
        }

        private Context getContext() {
            return context;
        }

        private Downloader getDownloader() {
            return downloader;
        }

        private int getErrResId() {
            return errResId;
        }

        private int getPlaceloaderId() {
            return placeloaderId;
        }

        private String getUrl() {
            return url;
        }

        private ImageView getImageView() {
            return imageView;
        }

        private Target getTarget() {
            return target;
        }

        private Transformation getTransformation() {
            return transformation;
        }

        private boolean isBooleanNoPlaceLoader() {
            return booleanNoPlaceLoader;
        }


        private boolean isBooleanNoFade() {
            return booleanNoFade;
        }

        private int[] getResizeArray() {
            return resizeArray;
        }

        private boolean isBooleanCenterCrop() {
            return booleanCenterCrop;
        }

        private boolean isBooleanCenterInside() {
            return booleanCenterInside;
        }

        private boolean isBooleanFit() {
            return booleanFit;
        }

        private Picasso.Priority getPriority() {
            return priority;
        }

        public ImageLoader build() {

            Picasso picasso = getPicasso();
            if (picasso == null) {
                Picasso.Builder builder = new Picasso.Builder(getContext());
                Downloader downloader = getDownloader();
                if (downloader == null)
                    downloader = new OkHttp3Downloader(createCacheDir(getContext(), getContext().getApplicationInfo().packageName
                            .replace(".", "-") + "-imagecache"), MAX_DISK_CACHE_SIZE);
                builder.downloader(downloader).build();
            }

            return new ImageLoader();
        }
    }


    public void load(String url, ImageView imageView) {

    }


    static File createCacheDir(Context context, String fileName) {
        File cache = new File(context.getApplicationContext().getCacheDir(), fileName);
        if (!cache.exists()) {
            cache.mkdirs();
        }
        return cache;
    }

}
