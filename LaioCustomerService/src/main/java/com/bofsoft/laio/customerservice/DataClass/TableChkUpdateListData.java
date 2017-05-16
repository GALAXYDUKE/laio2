package com.bofsoft.laio.customerservice.DataClass;

import com.bofsoft.laio.data.BaseData;

import java.util.List;

/**
 * 获取公共数据表更新时间信息，如果比本地新，就进行更新操作
 *
 * @author admin
 */
public class TableChkUpdateListData extends BaseData {

    private List<TableUpdateData> info;

    public List<TableUpdateData> getInfo() {
        return info;
    }

    public void setInfo(List<TableUpdateData> info) {
        this.info = info;
    }

}
