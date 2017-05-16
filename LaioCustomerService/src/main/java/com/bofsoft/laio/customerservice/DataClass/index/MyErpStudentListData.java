package com.bofsoft.laio.customerservice.DataClass.index;

import com.bofsoft.laio.data.BaseData;

import java.util.ArrayList;
import java.util.List;

/**
 * Erp学员列表data
 *
 * @author yedong
 */
public class MyErpStudentListData extends BaseData {
    // 请求参数
    // private int StudentType; // 学员类型(0为搜索全部学员，其他学员传入学员状态类型，如：1为刚报名学员)
    // private String Condition; // 搜索条件(学员姓名或证件号码)，如果为空此条件不成立
    // private int Page;
    // private int PageNum;

    // 返回参数
    public List<MyErpStudentData> info = new ArrayList<MyErpStudentData>();
    public boolean more;
}
