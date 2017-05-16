package com.bofsoft.laio.customerservice.DataClass.db;

import com.bofsoft.laio.data.BaseData;

/**
 * 12 节假日 laio_holiday
 *
 * @author admin
 */
public class HolidayData extends BaseData {
    private int Id;
    private String Holiday; //
    private String HolidayDate; //
    private String Remark; //
    private int IsDel;

    public HolidayData() {

    }

    public HolidayData(int id, String holiday, String holidayDate, String remark, int isDel) {
        super();
        Id = id;
        Holiday = holiday;
        HolidayDate = holidayDate;
        Remark = remark;
        IsDel = isDel;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getHoliday() {
        return Holiday;
    }

    public void setHoliday(String holiday) {
        Holiday = holiday;
    }

    public String getHolidayDate() {
        return HolidayDate;
    }

    public void setHolidayDate(String holidayDate) {
        HolidayDate = holidayDate;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }

    public int getIsDel() {
        return IsDel;
    }

    public void setIsDel(int isDel) {
        IsDel = isDel;
    }

    // public ContentValues getContentValues(){
    // ContentValues values = new ContentValues();
    // values.put("Id", Id);
    // values.put("Holiday", Holiday);
    // values.put("HolidayDate", HolidayDate);
    // values.put("Remark", Remark);
    // values.put("IsDel", IsDel);
    //
    // return values;
    // }

}
