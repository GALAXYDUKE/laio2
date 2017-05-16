package com.bofsoft.laio.customerservice.DataClass;

import android.content.ContentValues;

import com.bofsoft.laio.data.BaseData;

import java.io.Serializable;

public class SchoolData extends BaseData implements Serializable, Comparable<SchoolData> {
    private static final long serialVersionUID = 1L;
  /*
   * CREATE TABLE `laio_school` ( `Id` INTEGER, `DistrictDM` int(11) NOT NULL DEFAULT '0' ,
   * `DistrictMC` varchar(50) NOT NULL DEFAULT '0', `Weight` int(11) NOT NULL DEFAULT '0', `Num`
   * int(11) NOT NULL DEFAULT '0', `DM` char(4) NOT NULL DEFAULT '0', `MC` varchar(50) NOT NULL
   * DEFAULT '0', `IsDel` tinyint(3) NOT NULL DEFAULT '0' , PRIMARY KEY (`Id`) );
   */

    private int Id;
    private String DistrictDM; // 所属城市id
    private String DistrictMC; // 所属城市
    private int Weight; //
    private String Num; // 驾校编号
    private String DM;
    private String MC; // 驾校名称
    private int IsDel; // 是否删除

    public SchoolData() {

    }

    public SchoolData(int Id, String DistrictDM, String DistrictMC, int Weight, String Num,
                      String DM, String MC, int IsDel) {

        this.Id = Id;
        this.DistrictDM = DistrictDM;
        this.DistrictMC = DistrictMC;
        this.Weight = Weight;
        this.Num = Num;
        this.DM = DM;
        this.MC = MC;
        this.IsDel = IsDel;

    }


    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getDistrictDM() {
        return DistrictDM;
    }

    public void setDistrictDM(String districtDM) {
        DistrictDM = districtDM;
    }

    public String getDistrictMC() {
        return DistrictMC;
    }

    public void setDistrictMC(String districtMC) {
        DistrictMC = districtMC;
    }

    public int getWeight() {
        return Weight;
    }

    public void setWeight(int weight) {
        Weight = weight;
    }

    public String getNum() {
        return Num;
    }

    public void setNum(String num) {
        Num = num;
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
        StringBuilder builder = new StringBuilder();
        builder.append("SchoolData[ ").append("Id=" + Id).append(",DistrictDM=" + DistrictDM)
                .append(",DistrictMC=" + DistrictMC).append(",Weight=" + Weight).append(",Num=" + Weight)
                .append(",DM=" + DM).append(",MC=" + MC).append(",IsDel=" + IsDel);

        return builder.toString();
    }

    @Override
    public int compareTo(SchoolData another) {
        return Num.compareToIgnoreCase(another.Num);
    }

    public ContentValues getContentValues() {
        ContentValues values = new ContentValues();
        values.put("Id", Id);
        values.put("DistrictDM", DistrictDM);
        values.put("DistrictMC", DistrictMC);
        values.put("Weight", Weight);
        values.put("Num", Num);
        values.put("DM", DM);
        values.put("MC", MC);
        values.put("IsDel", IsDel);

        return values;
    }


}
