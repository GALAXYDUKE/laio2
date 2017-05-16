package com.bofsoft.laio.customerservice.DataClass.index;

import java.util.ArrayList;
import java.util.List;

public class OnlineApplyClassifyData {
    // 请求参数(无)
    public List<Info> GroupList = new ArrayList<Info>();

    public class Info {
        public String GroupName; // 分类名称含人数
        public int Status; // 状态，0培训中，1可返款(培训已完成)，2已返款
        public int Total; // 学员人数
    }
}
