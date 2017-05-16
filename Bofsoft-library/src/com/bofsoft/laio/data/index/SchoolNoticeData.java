package com.bofsoft.laio.data.index;

import com.bofsoft.laio.data.BaseData;

/**
 * 驾校通知
 *
 * @author yedong
 */
public class SchoolNoticeData extends BaseData {
    private static final long serialVersionUID = 1L;
    public int _id; // 消息本地数据Id
    public String title; // 通知标题
    public String msg; // 通知内容（isget=0时为简略信息，isget=1时为详细信息）
    public String puddate; // 日期
    public int isread; // 是否阅读（0未读，1已读）
    public int msgid; // 消息Id
    public int isget; // 是否获取信息详情（0未获取，1已获取）
}
