package com.bofsoft.laio.customerservice.DataClass.Order;

import com.bofsoft.laio.data.BaseData;

import java.util.List;

/**
 * 工作台-设置-接送地址设置-获取
 *
 * @author admin
 */
public class TransferAddrListData extends BaseData {

    private List<TransferAddrData> info;

    public List<TransferAddrData> getInfo() {
        return info;
    }

    public void setInfo(List<TransferAddrData> info) {
        this.info = info;
    }

}
