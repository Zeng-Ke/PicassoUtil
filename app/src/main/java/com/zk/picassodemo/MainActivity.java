package com.zk.picassodemo;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;


public class MainActivity extends AppCompatActivity {

    private ImageLoaderUtils.Target mTarget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ImageView imageView1 = findViewById(R.id.image1);
        final ImageView imageView2 = findViewById(R.id.image2);
        final ImageView imageView3 = findViewById(R.id.image3);
        final ImageView imageView4 = findViewById(R.id.image4);

       // ImageLoaderUtils.invalidateImage("http://user.tgnet.com/User/Face/6485485?default=0&big=1");

        ImageLoaderUtils.loadNormalImage("http://img.pconline.com" +
                ".cn/images/upload/upc/tx/wallpaper/1207/26/c1/12558965_1343293543277_320x480.jpg", imageView1,false);
        mTarget = new ImageLoaderUtils.Target() {

            @Override
            public void onBitmapLoaded(Bitmap bitmap, @ImageLoaderUtils.LoadedFrom int from) {
                imageView2.setImageDrawable(new BitmapDrawable(bitmap));
            }

            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        };
        ImageLoaderUtils.loadNormalImage("http://img.pconline.com" +
                ".cn/images/upload/upc/tx/wallpaper/1207/26/c1/12558965_1343293543277_320x480.jpg", mTarget);


        ImageLoaderUtils.loadCircleIcon("https://raw.githubusercontent" +
                ".com/aryarohit07/PicassoFaceDetectionTransformation/master/images/original_image3.jpg", imageView3);


        ImageLoaderUtils.updateNormalImage("https://raw.githubusercontent" +
                ".com/aryarohit07/PicassoFaceDetectionTransformation/master/images/original_image3.jpg", imageView4);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ImageLoaderUtils.cancleTargetRequest(mTarget);
    }
}
