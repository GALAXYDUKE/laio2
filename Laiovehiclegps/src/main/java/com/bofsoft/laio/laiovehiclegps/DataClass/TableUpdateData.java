package com.bofsoft.laio.laiovehiclegps.DataClass;

import com.bofsoft.laio.data.BaseData;

public class TableUpdateData extends BaseData {
  private int Id;
  private int ConfType;
  private String UpdateTime; // "2014-06-23 00:00:01"

  public int getId() {
    return Id;
  }

  public void setId(int id) {
    Id = id;
  }

  public int getConfType() {
    return ConfType;
  }

  public void setConfType(int confType) {
    ConfType = confType;
  }

  public String getUpdateTime() {
    return UpdateTime;
  }

  public void setUpdateTime(String updateTime) {
    UpdateTime = updateTime;
  }

  @Override
  public String toString() {
    return "TableUpdateTimeData=[Id = " + Id + ",ConfType = " + ConfType + ",UpdateTime= "
        + UpdateTime + "]";
  }

}
