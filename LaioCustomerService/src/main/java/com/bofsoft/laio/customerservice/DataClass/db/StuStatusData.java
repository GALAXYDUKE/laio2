package com.bofsoft.laio.customerservice.DataClass.db;

import com.bofsoft.laio.data.BaseData;

/**
 * 学员状态基础表
 *
 * @author admin
 */
public class StuStatusData extends BaseData {
    public int Id;
    public String MC; // 状态名称
    public String Unit; // 展示单位
    public int SeqType; // 展示顺序
    public int IsDel;

    public StuStatusData() {
    }

    public StuStatusData(int id, String mC, String unit, int seqType, int isDel) {
        super();
        Id = id;
        MC = mC;
        Unit = unit;
        SeqType = seqType;
        IsDel = isDel;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getMC() {
        return MC;
    }

    public void setMC(String mC) {
        MC = mC;
    }

    public String getUnit() {
        return Unit;
    }

    public void setUnit(String unit) {
        Unit = unit;
    }

    public int getSeqType() {
        return SeqType;
    }

    public void setSeqType(int seqType) {
        SeqType = seqType;
    }

    public int getIsDel() {
        return IsDel;
    }

    public void setIsDel(int isDel) {
        IsDel = isDel;
    }

    @Override
    public String toString() {
        return "StuStatusData [Id=" + Id + ", MC=" + MC + ", Unit=" + Unit + ", SeqType=" + SeqType
                + ", IsDel=" + IsDel + "]";
    }

}
