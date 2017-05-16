package com.bofsoft.laio.customerservice.DataClass.db;

import com.bofsoft.laio.data.BaseData;

/**
 * 11 报考原因 laio_enroll_reson
 *
 * @author admin
 */
public class EnrollReasonData extends BaseData {
    public int Id;
    public String DM; // 原因对应编号
    public String MC; // 原因
    public int IsDel;

    public EnrollReasonData() {

    }

    public EnrollReasonData(int id, String dM, String mC, int isDel) {
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

}
