package com.bofsoft.laio.customerservice.DataClass.db;

import com.bofsoft.laio.data.BaseData;

/**
 * 15 培训科目 laio_testsub
 *
 * @author admin
 */
public class TestSubData extends BaseData {
    private int Id;
    private String DM; // 科目对应编号
    private String MC; // 科目
    private int IsDel;

    public TestSubData() {

    }

    public TestSubData(int id, String dM, String mC, int isDel) {
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
