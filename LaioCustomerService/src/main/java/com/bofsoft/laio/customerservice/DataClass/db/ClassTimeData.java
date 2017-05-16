package com.bofsoft.laio.customerservice.DataClass.db;

import com.bofsoft.laio.data.BaseData;

/**
 * 7 培训时段 laio_class_time
 *
 * @author admin
 */
public class ClassTimeData extends BaseData {
    private int Id;
    private int SellerID; //
    private String Name; // 时段班次名
    private String BTime; // 开始时间
    private String ETime; // 结束时间
    private int IsDel; //

    public ClassTimeData() {

    }

    public ClassTimeData(int id, int sellerID, String name, String bTime, String eTime, int isDel) {
        super();
        Id = id;
        SellerID = sellerID;
        Name = name;
        BTime = bTime;
        ETime = eTime;
        IsDel = isDel;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getIsDel() {
        return IsDel;
    }

    public void setIsDel(int isDel) {
        IsDel = isDel;
    }

    public int getSellerID() {
        return SellerID;
    }

    public void setSellerID(int sellerID) {
        SellerID = sellerID;
    }

    public String getBTime() {
        return BTime;
    }

    public void setBTime(String bTime) {
        BTime = bTime;
    }

    public String getETime() {
        return ETime;
    }

    public void setETime(String eTime) {
        ETime = eTime;
    }

    // public ContentValues getContentValues(){
    // ContentValues values = new ContentValues();
    // values.put("Id", Id);
    // values.put("SellerID", SellerID);
    // values.put("Name", Name);
    // values.put("BTime", BTime);
    // values.put("ETime", ETime);
    // values.put("IsDel", IsDel);
    //
    // return values;
    // }
    //

}
