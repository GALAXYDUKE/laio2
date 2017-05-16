package com.bofsoft.laio.customerservice.DataClass.index;

import com.bofsoft.laio.data.BaseData;

/**
 * 10 行政区划 laio_district
 *
 * @author admin
 */
public class DataReportListData extends BaseData {
    private int Id;
    private String DM; // 区域对应编号
    private String MC; // 区域
    private String FirstLetter; // 首字母
    private String Spell; // 拼音
    private int IsDel;

    public DataReportListData() {
    }

    public DataReportListData(int id, String dM, String mC, String firstLetter, String spell,
                              int isDel) {
        super();
        Id = id;
        DM = dM;
        MC = mC;
        FirstLetter = firstLetter;
        Spell = spell;
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

    public String getFirstLetter() {
        return FirstLetter;
    }

    public void setFirstLetter(String firstLetter) {
        FirstLetter = firstLetter;
    }

    public String getSpell() {
        return Spell;
    }

    public void setSpell(String spell) {
        Spell = spell;
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
    // values.put("DM", DM);
    // values.put("MC", MC);
    // values.put("FirstLetter", FirstLetter);
    // values.put("Spell", Spell);
    // values.put("IsDel", IsDel);
    //
    // return values;
    // }

}
