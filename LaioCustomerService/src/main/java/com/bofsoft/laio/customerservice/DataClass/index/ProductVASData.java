package com.bofsoft.laio.customerservice.DataClass.index;

import com.bofsoft.laio.data.BaseData;

/**
 * 增值服务
 *
 * @author admin
 */
public class ProductVASData extends BaseData {
    private String Id; // Integer 教学项目
    private String Name; // String 增值服务名称
    private String Content; // String 增值服务内容描述
    private float Price; // Float 价格
    private int ClassType; // Integer 计费方式(仅培训类产品有这个值有效，招生类产品全为0)，0单个学时，1单个订单

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public float getPrice() {
        return Price;
    }

    public void setPrice(float price) {
        Price = price;
    }

    public int getClassType() {
        return ClassType;
    }

    public void setClassType(int classType) {
        ClassType = classType;
    }

}
