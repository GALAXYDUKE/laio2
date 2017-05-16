package com.bofsoft.laio.customerservice.DataClass.db;

import com.bofsoft.laio.data.BaseData;

/**
 * 8 培训班次（可自定义） laio_class_train
 *
 * @author admin
 */
public class ClassTrainData extends BaseData {
    private int Id;
    private int SellerID; //
    private String Name; // 培训班次名
    private int IsDel; //

    public ClassTrainData() {
    }

    public ClassTrainData(int id, int sellerID, String name, int isDel) {
        super();
        Id = id;
        SellerID = sellerID;
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

    public int getSellerID() {
        return SellerID;
    }

    public void setSellerID(int sellerID) {
        SellerID = sellerID;
    }

    // public ContentValues getContentValues(){
    // ContentValues values = new ContentValues();
    // values.put("Id", Id);
    // values.put("SellerID", SellerID);
    // values.put("Name", Name);
    // values.put("IsDel", IsDel);
    //
    // return values;
    // }

}
