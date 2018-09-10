package com.zk.picassodemo;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.widget.ImageView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Downloader;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import com.squareup.picasso.Target;
import com.squareup.picasso.Transformation;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;


/**
 * author: ZK.
 * date:   On 2018-08-18.
 */
public class ImageLoader {

    private static final int MAX_DISK_CACHE_SIZE = 50 * 1024 * 1024; // 50MB

    private Builder mBuilder;

    private ImageLoader(Builder builder) {
        mBuilder = builder;
    }

    private ImageLoader() {

    }


    public enum CornerType {
        ALL,
        TOP_LEFT, TOP_RIGHT, BOTTOM_LEFT, BOTTOM_RIGHT,
        TOP, BOTTOM, LEFT, RIGHT,
        OTHER_TOP_LEFT, OTHER_TOP_RIGHT, OTHER_BOTTOM_LEFT, OTHER_BOTTOM_RIGHT,
        DIAGONAL_FROM_TOP_LEFT, DIAGONAL_FROM_TOP_RIGHT
    }

    public static class Builder {

        private int nullResId = -1;

        private Context context;

        private Picasso picasso;

        private Downloader downloader;

        private @DrawableRes
        int errResId = nullResId;

        private @DrawableRes
        int placeloaderId = nullResId;

        private String url;

        private ImageView imageView;

        private Target onLoadTarget;

        private Callback onLoadCallBack;

        private List<Transformation> transformations;

        private boolean booleanNoPlaceLoader = false;

        private int[] resizeArray;

        private boolean booleanCenterCrop = false;

        private boolean booleanCenterInside = true;

        private boolean booleanFit = false;

        private Picasso.Priority priority = Picasso.Priority.NORMAL;

        private boolean booleanBigImg = false;

        private boolean booleanCache = true;

        private boolean booleanOnlyCache = false;

        private boolean booleanIndicatorsEnabled = false;

        private boolean booleanLoggingEnabled = false;

        private Object[] roundCornerRadius;

        private boolean booleanCircle = false;


        public Builder(Context context, String url, ImageView imageView) {
            this.context = context;
            this.url = url;
            this.imageView = imageView;
        }

        public Builder(Picasso picasso, String url, ImageView imageView) {
            this.picasso = picasso;
            this.url = url;
            this.imageView = imageView;
        }

        public Builder(Context context, String url, Target loadTarget) {
            this.context = context;
            this.url = url;
            this.onLoadTarget = loadTarget;
        }

        public Builder(Picasso picasso, String url, Target loadTarget) {
            this.picasso = picasso;
            this.url = url;
            this.onLoadTarget = loadTarget;
        }

        private Picasso getPicasso() {
            return picasso;
        }


        /**
         * 是否显示图片加载来源指示器(有3种颜色，绿色表示从内存加载、蓝色表示从磁盘加载、红色表示从网络加载) ，debug模式开启，上线记得设置为false。默认为false
         */
        public Builder setBooleanIndicatorsEnabled(boolean booleanIndicatorsEnabled) {
            this.booleanIndicatorsEnabled = booleanIndicatorsEnabled;
            return this;
        }

        /**
         * 是否打开日志打印，debug模式建议开启，上线记得设置为false。默认为false
         */
        public Builder setBooleanLoggingEnabled(boolean booleanLoggingEnabled) {
            this.booleanLoggingEnabled = booleanLoggingEnabled;
            return this;
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


        public Builder setOnLoadCallBack(Callback onLoadCallBack) {
            this.onLoadCallBack = onLoadCallBack;
            return this;
        }

        public Builder addTransformation(Transformation transformation) {
            if (transformation != null) {
                if (transformations == null)
                    transformations = new ArrayList<>();
                transformations.add(transformation);
            }
            return this;
        }

        public Builder addTransformation(Transformation... transformation) {
            if (transformation != null && transformation.length > 0) {
                if (transformations == null)
                    transformations = new ArrayList<>();
                for (Transformation t : transformations) {
                    transformations.add(t);
                }
            }
            return this;
        }

        /**
         * 我们来想一下下面这种情况：你在ImageView中加载了一张图片，一段时间过后，你想在同样的ImageView中加载另一张图片。使用默认的设置，你在重新调用Picasso的时候，ImageView将会加载之前设置的placeholder
         * 的图片。如果这个ImageView在你的UI中很重要的话，在几秒钟内快速的改变ImageView的图片会看起来很不合适。一个比较好的解决方案是调用`.noPlaceholder()
         * `这个方法。在第二张图片加载之前，ImageView会一直显示第一张图片。这对用户来说会很友好。
         * <p>
         * placeholder和noPlaceholder 不能同时应用在同一个请求上，会抛异常。
         */
        public Builder setBooleanNoPlaceLoader(boolean booleanNoPlaceLoader) {
            this.booleanNoPlaceLoader = booleanNoPlaceLoader;
            return this;
        }


        /**
         * 设置圆角
         *
         * @param radius     圆角半径
         * @param cornerType 圆角类型
         * @return
         */
        public Builder setRoundCornerRadius(int radius, CornerType cornerType) {
            if (radius > 0 && cornerType != null) {
                roundCornerRadius = new Object[2];
                roundCornerRadius[0] = radius;
                roundCornerRadius[1] = cornerType;
            }
            return this;
        }

        /**
         * 是否是圆形
         */
        public Builder setBooleanCircle(boolean booleanCircle) {
            this.booleanCircle = booleanCircle;
            return this;
        }

        /**
         * 重新设置图片的尺寸,单位dp
         */
        public Builder setResizeArray(int[] resizeArray) {
            this.resizeArray = resizeArray;
            return this;
        }


        /**
         * 充满ImageView 的边界，居中裁剪.(需在调用resize或者fit时才能调用，否则会抛异常)
         */
        public Builder setBooleanCenterCrop(boolean booleanCenterCrop) {
            this.booleanCenterCrop = booleanCenterCrop;
            return this;
        }

        //让View将图片展示完全，可以用centerInside，但是如果图片尺寸小于View尺寸的话，是不能充满View边界的(需在调用resize或者fit时才能调用，否则会抛异常))
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

        /*
         *请求优先级:LOW、NORMAL、HIGH
         */
        public Builder setPriority(Picasso.Priority priority) {
            this.priority = priority;
            return this;
        }


        /**
         * 加载一张大图片的时候，如果再内存中保存一份，很容易造成OOM,这时候我们只希望有磁盘缓存，而不希望缓存到内存，默认为false
         */
        public Builder setBooleanBigImg(boolean booleanBigImg) {
            this.booleanBigImg = booleanBigImg;
            return this;
        }


        /**
         * 是否设置缓存，默认Picasso 内存缓存和磁盘缓存都开启了的
         */
        public Builder setBooleanCache(boolean booleanCache) {
            this.booleanCache = booleanCache;
            return this;
        }

        /**
         * 是否只从缓存获取，不管能否拿到结果，默认为false
         */
        public Builder setBooleanOnlyCache(boolean booleanOnlyCache) {
            this.booleanOnlyCache = booleanOnlyCache;
            return this;
        }

        public ImageLoader build() {
            Picasso picasso = getPicasso();
            if (picasso == null) {
                Picasso.Builder builder = new Picasso.Builder(context);
                if (downloader == null)
                    downloader = new OkHttp3Downloader(createCacheDir(context, context.getApplicationInfo().packageName
                            .replace(".", "-") + "-imagecache"), MAX_DISK_CACHE_SIZE);
                picasso =  builder.downloader(downloader).build();
            }
            picasso.setIndicatorsEnabled(booleanIndicatorsEnabled);
            picasso.setLoggingEnabled(booleanLoggingEnabled);

            RequestCreator requestCreator = picasso.load(url);
            if (errResId != nullResId)
                requestCreator.error(errResId);
            if (booleanNoPlaceLoader)
                requestCreator.noPlaceholder();
            else if (placeloaderId != nullResId)
                requestCreator.placeholder(placeloaderId);
            if (resizeArray != null && resizeArray.length > 1 && resizeArray[0] > 0 && resizeArray[1] > 0)
                requestCreator.resize(resizeArray[0], resizeArray[1]);
            if (booleanFit)
                requestCreator.fit();
            if (resizeArray != null || booleanFit) {
                if (booleanCenterCrop)
                    requestCreator.centerCrop();
                else if (booleanCenterInside) requestCreator.centerInside();
            }
            requestCreator.priority(priority);
            if (roundCornerRadius != null)
                requestCreator.transform(new RoundedCornersTransformation((int) roundCornerRadius[0], 0, getCornerType((CornerType)
                        roundCornerRadius[1])));
            if (booleanCircle)
                requestCreator.transform(new CropCircleTransformation());
            if (transformations != null && transformations.size() > 0)
                requestCreator.transform(transformations);
            if (booleanOnlyCache)
                requestCreator.networkPolicy(NetworkPolicy.OFFLINE);
            else {
                if (!booleanCache)
                    requestCreator.memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)//跳过内存缓存
                            .networkPolicy(NetworkPolicy.NO_CACHE, NetworkPolicy.NO_STORE);//跳过磁盘缓存
                else if (booleanBigImg)
                    requestCreator.memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE);
            }
            if (imageView != null) {
                requestCreator.into(imageView, onLoadCallBack);
            } else if (onLoadTarget != null)
                requestCreator.into(onLoadTarget);
            return new ImageLoader(this);
        }
    }


    private static RoundedCornersTransformation.CornerType getCornerType(CornerType cornerType) {
        switch (cornerType) {
            case ALL:
                return RoundedCornersTransformation.CornerType.ALL;
            case LEFT:
                return RoundedCornersTransformation.CornerType.LEFT;
            case TOP:
                return RoundedCornersTransformation.CornerType.TOP;
            case RIGHT:
                return RoundedCornersTransformation.CornerType.RIGHT;
            case BOTTOM:
                return RoundedCornersTransformation.CornerType.BOTTOM;
            case TOP_LEFT:
                return RoundedCornersTransformation.CornerType.TOP_LEFT;
            case TOP_RIGHT:
                return RoundedCornersTransformation.CornerType.TOP_RIGHT;
            case BOTTOM_LEFT:
                return RoundedCornersTransformation.CornerType.BOTTOM_LEFT;
            case BOTTOM_RIGHT:
                return RoundedCornersTransformation.CornerType.BOTTOM_RIGHT;
            case OTHER_TOP_LEFT:
                return RoundedCornersTransformation.CornerType.OTHER_TOP_LEFT;//除了左上角
            case OTHER_TOP_RIGHT:
                return RoundedCornersTransformation.CornerType.OTHER_TOP_RIGHT;
            case OTHER_BOTTOM_LEFT:
                return RoundedCornersTransformation.CornerType.OTHER_BOTTOM_LEFT;
            case OTHER_BOTTOM_RIGHT:
                return RoundedCornersTransformation.CornerType.OTHER_BOTTOM_RIGHT;
            case DIAGONAL_FROM_TOP_LEFT:
                return RoundedCornersTransformation.CornerType.DIAGONAL_FROM_TOP_LEFT;//上左斜线
            case DIAGONAL_FROM_TOP_RIGHT:
                return RoundedCornersTransformation.CornerType.DIAGONAL_FROM_TOP_RIGHT;
            default:
                return RoundedCornersTransformation.CornerType.ALL;
        }

    }

    public static File createCacheDir(Context context, String fileName) {
        File cache = new File(context.getApplicationContext().getCacheDir(), fileName);
        if (!cache.exists()) {
            cache.mkdirs();
        }
        return cache;
    }

}
