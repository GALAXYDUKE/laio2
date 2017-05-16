package com.bofsoft.laio.customerservice.DataClass.index;

import com.bofsoft.laio.data.BaseData;

import java.util.List;

/**
 * 产品管理列表
 *
 * @author admin
 */
public class ProManagerListData extends BaseData {
    // 请求参数
    // ProType Integer 产品类型，0招生类产品，1培训类产品；
    // Status Integer 招生产品状态，0全部、1销售中、-1已下架、-2已过期；
    // 培训产品状态，0全部、1销售中，2待付款，3已售出、-1已下架、-2已过期；
    // Page Integer 页码
    // PageNum Integer 每页多少条

    // 机构：
    // ProList直接返回的是XmlData。

    private List<ProManagerData> ProList; // String 产品列表
    private boolean More; // bool 是否还有数据

    public List<ProManagerData> getProList() {
        return ProList;
    }

    public void setProList(List<ProManagerData> proList) {
        ProList = proList;
    }

    public boolean isMore() {
        return More;
    }

    public void setMore(boolean more) {
        More = more;
    }
}
