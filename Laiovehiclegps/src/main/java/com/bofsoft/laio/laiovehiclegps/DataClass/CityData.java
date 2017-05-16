package com.bofsoft.laio.laiovehiclegps.DataClass;

import com.bofsoft.laio.data.BaseData;

/**
 * 4 全国地级市列表 laio_city
 * 
 * @author admin
 *
 */
public class CityData extends BaseData implements Comparable<CityData> {

  private static final long serialVersionUID = 1L;

  private int Id; // id
  private String DirName; // 拼音首字母
  /**
   * 在查询直辖市的驾校时，不能直接通过Laio_school表中的DistrictDM相等来判断， 要用直辖市地区代码的前三位来进行匹配。
   */
  private String DM; // 城市对应的Id 6位数字
  private String MC; // 城市名
  private int Proid; // 省份id
  private int Hot; // 热门城市
  private String Firstletter; // 首字母
  private String Spell; // 拼音
  private int IsDel; // 是否删除


  public CityData() {}

  public CityData(int Id, String DirName, String DM, String MC, int Proid, int Hot,
                  String Firstletter, String Spell, int IsDel) {
    super();
    this.Id = Id;
    this.DirName = DirName;
    this.DM = DM;
    this.MC = MC;
    this.Proid = Proid;
    this.Hot = Hot;
    this.Firstletter = Firstletter;
    this.Spell = Spell;
    this.IsDel = IsDel;
  }


  public int getId() {
    return Id;
  }

  public void setId(int id) {
    Id = id;
  }

  public String getDirName() {
    return DirName;
  }

  public void setDirName(String dirName) {
    DirName = dirName;
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

  public int getProid() {
    return Proid;
  }

  public void setProid(int proId) {
    Proid = proId;
  }

  public int getHot() {
    return Hot;
  }

  public void setHot(int hot) {
    Hot = hot;
  }

  public String getFirstletter() {
    return Firstletter;
  }

  public void setFirstletter(String firstletter) {
    Firstletter = firstletter;
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

  @Override
  public String toString() {
    return "CityData [id=" + Id + ", dirName=" + DirName + ", dM=" + DM + ", mC=" + MC + ", proId="
        + Proid + ", hot=" + Hot + ", Firstletter=" + Firstletter + ", spell=" + Spell + ", isDel="
        + IsDel + "]";
  }

  @Override
  public int compareTo(CityData another) {
    return Spell.compareToIgnoreCase(another.Spell);
  }

}
