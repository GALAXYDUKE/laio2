package com.bofsoft.laio.customerservice.DataClass.db;

import com.bofsoft.laio.data.BaseData;

/**
 * 6 日期班次 laio_class_date
 *
 * @author admin
 */
public class ClassDateData extends BaseData {
    private int Id;
    private String Name; // 日期班次名
    private int IsDel; //

    public ClassDateData() {
    }

    public ClassDateData(int id, String name, int isDel) {
        super();
        Id = id;
        Name = name;
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

    // public ContentValues getContentValues(){
    // ContentValues values = new ContentValues();
    // values.put("Id", Id);
    // values.put("Name", Name);
    // values.put("IsDel", IsDel);
    //
    // return values;
    // }

}
