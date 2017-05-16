package com.bofsoft.laio.customerservice.DataClass.Order;

import java.io.Serializable;

/**
 * 培训产品时价格Data
 *
 * @author yedong
 */
public class TrainProPriceData implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    public int Id; // Integer 价格product_train_spec.Id
    public int SpecId; // Integer 培训产品规格ID，区分不同规格的产品，同一规格的产品价格体系一样，即可执行相同套餐价格。
    public int Unit; // Integer 数量
    public float Price; // Float 原价
    public float SalePrice; // Float 售价
}
