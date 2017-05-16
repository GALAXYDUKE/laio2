package com.bofsoft.laio.customerservice.DataClass.db;

import com.bofsoft.laio.data.BaseData;

/**
 * 18 招生班次基础信息 laio_carmodel
 *
 * @author admin
 */
public class ClassRegData extends BaseData {
    private int Id;
    private String Name; // 班次名
    private int IsDel; //

    public ClassRegData() {
    }

    public ClassRegData(int id, String name, int isDel) {
        Id = id;
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

    /**
     * @param name
     */
    public void setName(String name) {
        Name = name;
    }

    public int getIsDel() {
        return IsDel;
    }

    public void setIsDel(int isDel) {
        IsDel = isDel;
    }

}
