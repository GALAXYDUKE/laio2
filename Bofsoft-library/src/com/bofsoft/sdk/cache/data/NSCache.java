package com.bofsoft.sdk.cache.data;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.bofsoft.sdk.cache.data.NSCacheProtos.Config;
import com.bofsoft.sdk.cache.data.NSCacheProtos.ConfigItem;
import com.bofsoft.sdk.config.BaseSysConfig;
import com.bofsoft.sdk.utils.FileUtils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class NSCache {

    private static final String TAG = NSCache.class.getSimpleName();

    private static final String CACHE_FOLDER = File.separator + "NSCache";

    private static final String CACHE_CONFIG = File.separator + "Config";

    private static Config config = null;

    private static Map<String, ConfigItem> configMap = new HashMap<String, NSCacheProtos.ConfigItem>();

    /**
     * 缓存模式
     * SHORT          短时间（5分钟）
     * MEDIUM         中等时间（2小时）
     * MEDIUM_LONG    中长时间（1天）
     * LONG           最长时间（7天）
     */
    public enum CacheModel {
        SHORT,
        MEDIUM,
        MEDIUM_LONG,
        LONG;

        public static final long CACHE_SHORT_TIMEOUT = 1000 * 60 * 5;

        public static final long CACHE_MEDIUM_TIMEOUT = 1000 * 60 * 60 * 2;

        public static final long CACHE_MEDIUM_LONG_TIMEOUT = 1000 * 60 * 60 * 24 * 1;

        public static final long CACHE_LONG_TIMEOUT = 1000 * 60 * 60 * 24 * 7;

        public static int getCacheIndex(CacheModel model) {
            if (model == SHORT)
                return 0;
            else if (model == MEDIUM)
                return 1;
            else if (model == MEDIUM_LONG)
                return 2;
            else if (model == LONG)
                return 3;
            else
                return 0;
        }

        public static CacheModel getCacheModel(int model) {
            switch (model) {
                case 0:
                    return SHORT;
                case 1:
                    return MEDIUM;
                case 2:
                    return MEDIUM_LONG;
                case 3:
                    return LONG;
                default:
                    return SHORT;
            }
        }

        public static long getCacheTime(int model) {
            return getCacheTime(getCacheModel(model));
        }

        public static long getCacheTime(CacheModel model) {
            if (model == CacheModel.SHORT)
                return CACHE_SHORT_TIMEOUT;
            else if (model == CacheModel.MEDIUM)
                return CACHE_MEDIUM_TIMEOUT;
            else if (model == CacheModel.MEDIUM_LONG)
                return CACHE_MEDIUM_LONG_TIMEOUT;
            else if (model == CacheModel.LONG)
                return CACHE_LONG_TIMEOUT;
            else
                return 0;
        }
    }

    /**
     * 缓存数据
     *
     * @param key
     * @param url     请求URL
     * @param content 数据
     * @param model   缓存模式
     */
    public static void push(String key, String url, String content, CacheModel model) {
        try {
            //判断缓存情况
            ConfigItem item = getConfigItem(key);
            if (item == null || isOutDate(key)) {
                if (item != null) {
                    FileUtils.deleteFile(new File(getPath(File.separator + key)));
                }
                ConfigItem builder = new ConfigItem();
                builder.setKey(key);
                builder.setCacheModel(CacheModel.getCacheIndex(model));
                builder.setCacheTime(System.currentTimeMillis());
                setConfig(key, builder);
            }
            String path = getPath(File.separator + key);
            FileUtils.writeTextFile(new File(path + File.separator + url), content);
        } catch (Exception e) {
            Log.e(TAG, "KEY : " + key + "缓存失败  " + e.toString());
        }
    }

    /**
     * 获取缓存数据
     *
     * @param key
     * @param url
     * @return
     */
    public static String pop(String key, String url) {
        try {
            String path = getPath(File.separator + key);
            return FileUtils.readTextFile(new File(path + File.separator + url));
        } catch (Exception e) {
            Log.e(TAG, "KEY : " + key + "读取缓存失败  " + e.toString());
        }
        return null;
    }

    /**
     * 缓存是否过期
     *
     * @param key
     * @return true 过期  false 未过期
     */
    public static boolean isOutDate(String key) {
        ConfigItem item = getConfigItem(key);
        if (item == null) return true;
        return (System.currentTimeMillis() - item.getCacheTime()) > CacheModel.getCacheTime(item.getCacheModel());
    }

    /**
     * 获取配置信息
     *
     * @return
     */
    private static Map<String, ConfigItem> getConfig() {
        if (config == null) {
            try {
                File file = new File(getPath("") + CACHE_CONFIG);
                config = JSON.parseObject(FileUtils.readTextFile(file), Config.class);
            } catch (Exception e) {
                Log.e(TAG, "读取配置信息失败:" + e.toString());
            }
        }
        if (config == null) {
            config = new Config();
        }
        if (configMap.size() <= 0) {
            for (ConfigItem item : config.getConfigInfo())
                configMap.put(item.getKey(), item);
        }
        return configMap;
    }

    /**
     * 获取配置信息
     *
     * @param key
     * @return
     */
    private static ConfigItem getConfigItem(String key) {
        return getConfig().get(key);
    }

    /**
     * 保存配置信息
     *
     * @param key
     * @param item null时清除缓存
     */
    private static void setConfig(String key, ConfigItem item) {
        getConfig();
        if (item == null) {
            //清除配置
            for (int i = 0; i < config.getConfigInfo().size(); i++) {
                if (key.equals(config.getConfigInfo().get(i).getKey())) {
                    Config temp = config.Clone();
                    temp.getConfigInfo().remove(i);
                    config = temp.Clone();
                    configMap.remove(key);

                    //清除缓存
                    FileUtils.deleteFile(new File(getPath(File.separator + key)));
                    break;
                }
            }
        } else {
            //记录缓存配置
            Config builder = config.Clone();
            for (int i = 0; i < config.getConfigInfo().size(); i++) {
                if (key.equals(config.getConfigInfo().get(i).getKey())) {
                    builder.getConfigInfo().remove(i);
                    configMap.remove(key);
                    break;
                }
            }
            builder.getConfigInfo().add(item);
            config = builder.Clone();
            configMap.put(item.getKey(), item);
        }
        try {
            FileUtils.writeTextFile(new File(getPath("") + CACHE_CONFIG), JSON.toJSONString(config));
        } catch (Exception e) {
            Log.e(TAG, "配置信息保存失败:" + e.toString());
        }
    }

    /**
     * 清除某个缓存
     *
     * @param key
     */
    public static void clear(String key) {
        setConfig(key, null);
    }

    /**
     * 清除所有缓存
     */
    public static void clearAll() {
        new Thread(new Runnable() {

            @Override
            public void run() {
                FileUtils.deleteFile(new File(getPath("")));
            }
        }).start();
    }

    /**
     * 整理缓存
     */
    public static void init() {
        getConfig();
        //只清理想不能使用的缓存
        deleteFile(new File(getPath("")));
    }

    private static void deleteFile(File file) {

        if (file.exists()) {
            if (file.isFile() && !CACHE_CONFIG.endsWith(file.getName()) && !CACHE_FOLDER.endsWith(file.getName())) {
                file.delete();
            } else if (file.isDirectory()) {
                File files[] = file.listFiles();
                for (int i = 0; i < files.length; i++) {
                    if (!configMap.containsKey(files[i].getName())) {
                        deleteFile(files[i]);
                    }
                }
            }
            if (!configMap.containsKey(file.getName()) && !CACHE_CONFIG.endsWith(file.getName()) && !CACHE_FOLDER.endsWith(file.getName()))
                file.delete();
        }
    }

    /**
     * 获取文件
     *
     * @param path
     * @return
     */
    private static String getPath(String path) {
        String filePath = null;
        if (BaseSysConfig.cacheSDPath != null && BaseSysConfig.cacheSDPath.length() > 0) {
            filePath = BaseSysConfig.cacheSDPath + CACHE_FOLDER + path;
        } else if (BaseSysConfig.cachePath != null && BaseSysConfig.cachePath.length() > 0) {
            filePath = BaseSysConfig.cachePath + CACHE_FOLDER + path;
        } else {
            Log.e(TAG, "内存卡不可用");
            return null;
        }
        FileUtils.CreateDir(filePath);
        return filePath;
    }
}
