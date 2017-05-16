package com.bofsoft.laio.customerservice.DataClass.db;

import com.bofsoft.laio.data.BaseData;

/**
 * 5 地城市对应经纬度 laio_city_gps
 *
 * @author admin
 */
public class CityGpsData extends BaseData {
    private int Id;
    private int CityId;
    private String Lat; //
    private String Lng; //
    private int IsDel; //

    public CityGpsData() {
    }

    public CityGpsData(int id, int cityId, String lat, String lng, int isDel) {
        super();
        Id = id;
        CityId = cityId;
        Lat = lat;
        Lng = lng;
        IsDel = isDel;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getCityId() {
        return CityId;
    }

    public void setCityId(int cityId) {
        CityId = cityId;
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

    public int getIsDel() {
        return IsDel;
    }

    public void setIsDel(int isDel) {
        IsDel = isDel;
    }

    @Override
    public String toString() {
        return "CityGpsData=[Id = " + Id + ",CityId = " + CityId + ",Lat= " + Lat + ",Lng= " + Lng
                + ",IsDel = " + IsDel + "]";
    }

    // public ContentValues getContentValues(){
    // ContentValues values = new ContentValues();
    // values.put("Id", Id);
    // values.put("CityId", CityId);
    // values.put("Lat", Lat);
    // values.put("Lng", Lng);
    // values.put("IsDel", IsDel);
    //
    // return values;
    // }

}
