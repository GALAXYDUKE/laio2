package com.bofsoft.laio.customerservice.DataClass;

import com.bofsoft.laio.data.BaseData;

import java.util.List;

public class BasePageResponseData<E> extends BaseData {
    public boolean more;
    public List<E> info;
}
