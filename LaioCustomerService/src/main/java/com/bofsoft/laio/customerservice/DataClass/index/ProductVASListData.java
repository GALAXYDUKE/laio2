package com.bofsoft.laio.customerservice.DataClass.index;

import com.bofsoft.laio.data.BaseData;

import java.util.List;

/**
 * 获取增值服务（含招生和培训类增值服务）列表
 *
 * @author admin
 */
public class ProductVASListData extends BaseData {
    // 请求参数
    // VasType Integer 增值服务类型，0招生类增值服务，1培训类型增值服务；
    // VasClass Integer 服务对象分类：
    // VaseType为0时，0为一费制，1为计时计程；
    // VasType为1时，0为返回全部，1初学培训，2补训，3陪练；
    // 注：VasType为1时，教练请求时本参数始终为0，1、2、3用于学员请求。
    // DistrictDM String 培训区域代码，VasType为1时上传前端选择的区划代码，为0时为教练入驻时填的区划；

    private int VasType; // Integer 增值服务类型，0招生类增值服务，1培训类型增值服务；
    private int VasClass; // Integer 服务对象分类：
    // VaseType为0时，0为一费制，1为计时计程；
    // VasType为1时，0为返回全部，1初学培训，2补训，3陪练；
    // 注：VasType为1时，教练请求时本参数始终为0，1、2、3用于学员请求。

    private List<ProductVASData> VasList;

    public int getVasType() {
        return VasType;
    }

    public void setVasType(int vasType) {
        VasType = vasType;
    }

    public int getVasClass() {
        return VasClass;
    }

    public void setVasClass(int vasClass) {
        VasClass = vasClass;
    }

    public List<ProductVASData> getVasList() {
        return VasList;
    }

    public void setVasList(List<ProductVASData> vasList) {
        VasList = vasList;
    }

}
