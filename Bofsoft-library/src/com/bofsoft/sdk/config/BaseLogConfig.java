package com.bofsoft.sdk.config;

public abstract class BaseLogConfig {

    private Boolean isConsole;

    private Boolean isFile;

    private String name;

    private String path;

    private String tag;

    private LOG_LEVEL level;

    private String format;

    private static BaseLogConfig self;

    public static BaseLogConfig getInstence() {
        return self != null ? self : (self = getInstence());
    }

    protected abstract BaseLogConfig setInstence();

    /**
     * 日志显示级别
     */
    public enum LOG_LEVEL {
        ALL, DEBUG, INFO, WARW, ERROR
    }

    public boolean isConsole() {
        return isConsole != null ? isConsole : (isConsole = setConsole());
    }

    public boolean isFile() {
        return isFile != null ? isFile : (isFile = setFile());
    }

    public String getName() {
        return name != null ? name : (name = setName() != null ? setName() : "log.txt");
    }

    // public String getPath() {
    // return path != null ? path : (path = setPath() != null ? setPath() :
    // BaseSysConfig.filesSDPath);
    // }

    public String getTag() {
        return tag != null ? tag : (tag = setTag() != null ? setTag() : BaseSysConfig.packagePath);
    }

    public LOG_LEVEL getLevel() {
        return level != null ? level : (level = setLevel() != null ? setLevel() : LOG_LEVEL.ALL);
    }

    public String getFormat() {
        return format != null ? format : (format = setFormat() != null ? setFormat() : "");
    }

    /**
     * 是否控制台输出
     */
    protected abstract boolean setConsole();

    /**
     * 是否宝存日志
     */
    protected abstract boolean setFile();

    /**
     * 日志名称
     */
    protected abstract String setName();

    /**
     * 日志路径，默认项目目录
     */
    protected abstract String setPath();

    /**
     * 默认tag
     */
    protected abstract String setTag();

    /**
     * 日志显示级别 ALL < DEBUG < INFO < WARN < ERROR
     */
    protected abstract LOG_LEVEL setLevel();

    /**
     * 日志显示格式 不可用
     */
    protected abstract String setFormat();
}
