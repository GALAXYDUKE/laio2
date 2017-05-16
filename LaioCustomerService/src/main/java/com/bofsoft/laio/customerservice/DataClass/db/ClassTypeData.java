package com.bofsoft.laio.customerservice.DataClass.db;

import com.bofsoft.laio.data.BaseData;

/**
 * 9 培训类型 laio_class_type
 *
 * @author admin
 */
public class ClassTypeData extends BaseData implements Comparable<ClassTypeData> {
    private int Id;
    private String Name; // 初学培训、补训、复训
    private int IsDel; //

    public ClassTypeData() {
    }

    public ClassTypeData(int id, String name, int isDel) {
        super();
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

    public void setName(String name) {
        Name = name;
    }

    public int getIsDel() {
        return IsDel;
    }

    public void setIsDel(int isDel) {
        IsDel = isDel;
    }

    @Override
    public int compareTo(ClassTypeData another) {
        if (another == null) {
            return -1;
        }
        return Integer.valueOf(this.Id).compareTo(Integer.valueOf(another.Id));
    }

}
