package com.bofsoft.laio.customerservice.Application;

import android.app.Application;
import android.content.Context;

import com.baidu.location.LocationClient;
import com.bofsoft.laio.common.ConfigAll;
import com.bofsoft.laio.common.Func;
import com.bofsoft.laio.customerservice.Config.Config;
import com.bofsoft.laio.customerservice.Database.PublicDBManager;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.decode.BaseImageDecoder;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;

import java.io.File;

/**
 * Created by szw on 2017/2/16.
 */

public class MyApplication extends Application {

    public static Context context = null;
    public LocationClient mLocationClient;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        Config.getInstance().init(context);

        PublicDBManager.getInstance(this);
        Func.startDataCenter(context);

        File cacheDir = new File(ConfigAll.APP_IMAGE_CACHE_PATH);

        ImageLoaderConfiguration config =
                new ImageLoaderConfiguration.Builder(context)
                        .memoryCacheExtraOptions(480, 800)// max width, max height，即保存的每个缓存文件的最大长宽
                        // default = device screen dimensions
                        .diskCacheExtraOptions(480, 800, null)// Can slow ImageLoader, use it carefully (Better don't use it)设置缓存的详细信息，最好不要设置这个
                        // .taskExecutor(...)
                        // .taskExecutorForCachedImages(...)
                        .threadPoolSize(3)// 线程池内加载的数量
                        // default
                        .threadPriority(Thread.NORM_PRIORITY - 1)// 线程优先级
                        // default
                        .tasksProcessingOrder(QueueProcessingType.FIFO)
                        // default
                        .denyCacheImageMultipleSizesInMemory()
                        // .memoryCache(new LruMemoryCache(2 * 1024 * 1024))
                        .memoryCache(new WeakMemoryCache()).memoryCacheSize(2 * 1024 * 1024)
                        .memoryCacheSizePercentage(13)
                        // default
                        .diskCache(new UnlimitedDiscCache(cacheDir))
                        // default
                        .diskCacheSize(50 * 1024 * 1024)//硬盘缓存50MB
                        .diskCacheFileCount(100)//缓存的File数量
                        .diskCacheFileNameGenerator(new HashCodeFileNameGenerator()) //将保存的时候的URI名称用HASHCODE加密
                        .imageDownloader(new BaseImageDownloader(context)) // default
                        .imageDecoder(new BaseImageDecoder(false)) // default
                        .defaultDisplayImageOptions(DisplayImageOptions.createSimple()) // default
                        // .writeDebugLogs()
                        .build();
        ImageLoader.getInstance().init(config);
    }
}
