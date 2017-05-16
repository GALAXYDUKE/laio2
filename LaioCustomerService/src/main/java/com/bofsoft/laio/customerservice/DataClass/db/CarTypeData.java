package com.bofsoft.laio.customerservice.DataClass.db;

import com.bofsoft.laio.data.BaseData;

/**
 * 2 教练车型 laio_carmodel
 *
 * @author admin
 */
public class CarTypeData extends BaseData {
    private int Id;
    private String DM; // 车型id
    private String MC; // 车型
    private int IsDel; //

    public CarTypeData() {
    }

    public CarTypeData(int id, String dM, String mC, int isDel) {
        super();
        Id = id;
        DM = dM;
        MC = mC;
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

    public int getIsDel() {
        return IsDel;
    }

    public void setIsDel(int isDel) {
        IsDel = isDel;
    }

    @Override
    public String toString() {
        return "CarTypeData=[Id = " + Id + ",DM = " + DM + ",MC= " + MC + ",IsDel = " + IsDel + "]";
    }

    // public ContentValues getContentValues(){
    // ContentValues values = new ContentValues();
    // values.put("Id", Id);
    // values.put("DM", DM);
    // values.put("MC", MC);
    // values.put("IsDel", IsDel);
    //
    // return values;
    // }

}
