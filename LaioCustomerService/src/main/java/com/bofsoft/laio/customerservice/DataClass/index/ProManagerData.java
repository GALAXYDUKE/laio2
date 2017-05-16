package com.bofsoft.laio.customerservice.DataClass.index;

import com.bofsoft.laio.data.BaseData;

/**
 * 产品管理列表data
 *
 * @author admin
 */
public class ProManagerData extends BaseData {
    private int Id; // Integer 产品Id
    private int ProType; // Integer 产品类型，0招生类产品，1培训类产品；
    private String Name; // Sring 产品名
    private String Intro; // String 产品简介
    private int Status; // Integer 招生产品状态，1销售中、-1已下架、-2已过期；

    // 培训产品状态，1销售中，2待付款，3已售出、-1已下架、-2已过期；
    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getProType() {
        return ProType;
    }

    public void setProType(int proType) {
        ProType = proType;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getIntro() {
        return Intro;
    }

    public void setIntro(String intro) {
        Intro = intro;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }

}
