package com.bofsoft.laio.customerservice.Database;

import java.util.List;

/**
 * 数据库查询结果回调
 *
 * @param <T>
 * @author yedong
 */
public interface DBCallBackImp<T> {

    /**
     * 返回查询的T
     *
     * @param data
     */
    void onCallBackData(T data);

    /**
     * 返回查询的List<T>
     *
     * @param list
     */
    void onCallBackList(List<T> list);
}
