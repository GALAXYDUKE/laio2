package com.bofsoft.laio.customerservice.DataClass.db;

import com.bofsoft.laio.data.BaseData;

/**
 * 1 教练准教车型 laio_admitcartype
 *
 * @author admin
 */
public class AdmitCarTypeData extends BaseData {
    private int Id;
    private String DM; // 类别代码
    private String MC; // 类别
    private String CarTypeIDs; // "1,3,4,5,6,7,8,9"
    private String CarTypes; // "A1、A3、B1、B2、C1、C2、C3、C4"
    private int IsDel; //

    public AdmitCarTypeData() {
    }

    public AdmitCarTypeData(int id, String dM, String mC, String carTypeIDs, String carTypes,
                            int isDel) {
        this.Id = id;
        this.DM = dM;
        this.MC = mC;
        this.CarTypeIDs = carTypeIDs;
        this.CarTypes = carTypes;
        this.IsDel = isDel;
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

    public String getCarTypeIDs() {
        return CarTypeIDs;
    }

    public void setCarTypeIDs(String carTypeIDs) {
        CarTypeIDs = carTypeIDs;
    }

    public String getCarTypes() {
        return CarTypes;
    }

    public void setCarTypes(String carTypes) {
        CarTypes = carTypes;
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
    // values.put("DM", DM);
    // values.put("MC", MC);
    // values.put("CarTypeIDs", CarTypeIDs);
    // values.put("CarTypes", CarTypes);
    // values.put("IsDel", IsDel);
    // return values;
    //
    // }

}
