package com.bofsoft.laio.customerservice.DataClass.Order;

import com.bofsoft.laio.data.BaseData;

/**
 * 工作台-设置-接送地址设置-获取
 *
 * @author admin
 */
public class TransferAddrData extends BaseData {

    private int AddrId; // 接送地址Id
    private String Addr; // 接送地址

    private double AddrLat; // Double 纬度
    private double AddrLng; // Double 经度

    private String CheckStatus; // 审核状态，1审核通过，0审核不通过审核不通过需要重新提交
    private String CheckMsg; // 审核意见，审核不通过时才显示审核意见

    public int getAddrId() {
        return AddrId;
    }

    public void setAddrId(int addrId) {
        AddrId = addrId;
    }

    public String getAddr() {
        return Addr;
    }

    public void setAddr(String addr) {
        Addr = addr;
    }

    public String getCheckStatus() {
        return CheckStatus;
    }

    public void setCheckStatus(String checkStatus) {
        CheckStatus = checkStatus;
    }

    public String getCheckMsg() {
        return CheckMsg;
    }

    public void setCheckMsg(String checkMsg) {
        CheckMsg = checkMsg;
    }

    public double getAddrLat() {
        return AddrLat;
    }

    public void setAddrLat(double addrLat) {
        AddrLat = addrLat;
    }

    public double getAddrLng() {
        return AddrLng;
    }

    public void setAddrLng(double addrLng) {
        AddrLng = addrLng;
    }

}
