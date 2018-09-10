package com.zk.picassodemo;

import android.app.Application;

/**
 * author: ZK.
 * date:   On 2018-08-22.
 */
public class MApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ImageLoaderUtils.init(this, true);
    }
}
