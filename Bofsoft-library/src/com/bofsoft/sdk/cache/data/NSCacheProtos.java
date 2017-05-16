package com.bofsoft.sdk.cache.data;

import java.util.ArrayList;
import java.util.List;

public final class NSCacheProtos {
    public static class Config implements Cloneable {
        private List<ConfigItem> configInfo = new ArrayList<>();

        public List<ConfigItem> getConfigInfo() {
            return configInfo;
        }

        public void setConfigInfo(List<ConfigItem> configInfo) {
            this.configInfo = configInfo;
        }

        @Override
        public Object clone() throws CloneNotSupportedException {
            return super.clone();
        }

        public Config Clone() {
            try {
                return (Config) clone();
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    public static class ConfigItem implements Cloneable {
        private String key;
        private long cacheTime;
        private Integer cacheModel;

        @Override
        public Object clone() throws CloneNotSupportedException {
            return super.clone();
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public long getCacheTime() {
            return cacheTime;
        }

        public void setCacheTime(long cacheTime) {
            this.cacheTime = cacheTime;
        }

        public Integer getCacheModel() {
            return cacheModel;
        }

        public void setCacheModel(Integer cacheModel) {
            this.cacheModel = cacheModel;
        }
    }
}
