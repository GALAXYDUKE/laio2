package com.bofsoft.laio.customerservice.DataClass.db;

import com.bofsoft.laio.data.BaseData;

/**
 * 17 培训场地 laio_train_site
 *
 * @author admin
 */
public class TrainSiteData extends BaseData {
    private int Id;
    private String DM; //
    private String MC; //
    private int ProvinceId; //
    private String ProvinceDM; //
    private int CityId; //
    private String CityDM; //
    private int AreaId; //
    private String AreaDM; //
    private String Addr; //
    private String Lat; // 纬度
    private String Lng; // 经度
    private String Introduce; // 场地介绍
    private int IsDel;

    public TrainSiteData() {
        super();
    }

    public TrainSiteData(int id, String dM, String mC, int provinceId, String provinceDM, int cityId,
                         String cityDM, int areaId, String areaDM, String lat, String lng, String addr,
                         String introduce, int isDel) {
        super();
        Id = id;
        DM = dM;
        MC = mC;
        ProvinceId = provinceId;
        ProvinceDM = provinceDM;
        CityId = cityId;
        CityDM = cityDM;
        AreaId = areaId;
        AreaDM = areaDM;
        Lat = lat;
        Lng = lng;
        Introduce = introduce;
        IsDel = isDel;

    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getDM() {
        return DM;
    }

    public void setDM(String dM) {
        DM = dM;
    }

    public String getMC() {
        return MC;
    }

    public void setMC(String mC) {
        MC = mC;
    }

    public int getProvinceId() {
        return ProvinceId;
    }

    public void setProvinceId(int provinceId) {
        ProvinceId = provinceId;
    }

    public String getProvinceDM() {
        return ProvinceDM;
    }

    public void setProvinceDM(String provinceDM) {
        ProvinceDM = provinceDM;
    }

    public int getCityId() {
        return CityId;
    }

    public void setCityId(int cityId) {
        CityId = cityId;
    }

    public String getCityDM() {
        return CityDM;
    }

    public void setCityDM(String cityDM) {
        CityDM = cityDM;
    }

    public int getAreaId() {
        return AreaId;
    }

    public void setAreaId(int areaId) {
        AreaId = areaId;
    }

    public String getAreaDM() {
        return AreaDM;
    }

    public void setAreaDM(String areaDM) {
        AreaDM = areaDM;
    }

    public String getAddr() {
        return Addr;
    }

    public void setAddr(String addr) {
        Addr = addr;
    }

    public String getLat() {
        return Lat;
    }

    public void setLat(String lat) {
        Lat = lat;
    }

    public String getLng() {
        return Lng;
    }

    public void setLng(String lng) {
        Lng = lng;
    }

    public String getIntroduce() {
        return Introduce;
    }

    public void setIntroduce(String introduce) {
        Introduce = introduce;
    }

    public int getIsDel() {
        return IsDel;
    }

    public void setIsDel(int isDel) {
        IsDel = isDel;
    }

}
