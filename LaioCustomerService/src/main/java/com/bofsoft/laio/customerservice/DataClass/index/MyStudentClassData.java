package com.bofsoft.laio.customerservice.DataClass.index;

import java.util.ArrayList;
import java.util.List;

/**
 * 教练获取学员状态分类统计列表
 *
 * @author admin
 */
public class MyStudentClassData {
    // 请求参数(无)
    public List<Info> info = new ArrayList<Info>();

    public class Info {
        public int StuType; // 学员类型（与基础数据表bd_stustatus中Id进行对应）
        public String StuTypeName; // 学员类型描述含人数信息
        public int StuNo; // 学员人数
    }
}
