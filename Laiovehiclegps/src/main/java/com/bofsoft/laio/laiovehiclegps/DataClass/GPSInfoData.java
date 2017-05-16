package com.bofsoft.laio.laiovehiclegps.DataClass;

import com.bofsoft.laio.data.BaseData;

/**
 * Created by szw on 2017/2/23.
 */

public class GPSInfoData extends BaseData {
    public String License;//	String	车牌号
    public String Deviceid;//	String	设备号
    public boolean Online;//	Bool	是否在线，如果设备正好连接到服务器上，返回true，如果不是返回false
    public double Latitude;//	Double	纬度
    public double Longitude;//	Double	经度
    public int Speed;//	Int	速度
    public int Direction;//	Int	方向角
    public String Datetime;//	String	数据发生的时间，格式为2017-01-01 01:02:03
    public int Status;//	Int	0表示正常，1表示有问题
    public String StatusContent;//	String	如果status为1，就会有该字段文字提示
    public String Address;//地址

    public String getLicense() {
        return License;
    }

    public void setLicense(String license) {
        License = license;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public double getLongitude() {
        return Longitude;
    }

    public void setLongitude(double longitude) {
        Longitude = longitude;
    }

    public double getLatitude() {
        return Latitude;
    }

    public void setLatitude(double latitude) {
        Latitude = latitude;
    }

    public String getDirectionStr(){
        if (Direction < 0 || Direction > 360) {
            return "方向错误";
        }
        if (Direction == 0 || Direction == 360) {
            return "正北方向";
        }
        if (Direction == 90) {
            return "正东方向";
        }
        if (Direction == 180) {
            return "正南方向";
        }
        if (Direction == 270) {
            return "正西方向";
        }
        if (Direction == 45) {
            return "东北方向";
        }
        if (Direction == 135) {
            return "东南方向";
        }
        if (Direction == 225) {
            return "西南方向";
        }
        if (Direction == 315) {
            return "西北方向";
        }
        if (Direction > 0 && Direction < 90) {
            return "北偏东" + Direction;
        }
        if (Direction > 90 && Direction < 180) {
            return "南偏东" + (Direction - 90);
        }
        if (Direction > 180 && Direction < 270) {
            return "南偏西" + (Direction - 180);
        }
        if (Direction > 270 && Direction < 360) {
            return "北偏西" + (Direction - 270);
        }
        return "";
    }
}
