package com.bofsoft.laio.customerservice.DataClass;

import com.bofsoft.laio.data.BaseData;

import java.util.List;

/**
 * 获取公共数据表更新时间信息，如果比本地新，就进行更新操作
 *
 * @author admin
 */
public class TableUpdateListData<T> extends BaseData {
    private int ConfType;
    private String UpdateTime;
    private List<T> info;

    public int getConfType() {
        return ConfType;
    }

    public void setConfType(int confType) {
        ConfType = confType;
    }

    public String getUpdateTime() {
        return UpdateTime;
    }

    public void setUpdateTime(String updateTime) {
        UpdateTime = updateTime;
    }

    public List<T> getInfo() {
        return info;
    }

    public void setInfo(List<T> info) {
        this.info = info;
    }

}
