package com.bofsoft.laio.customerservice.DataClass.db;

import com.bofsoft.laio.data.BaseData;

/**
 * 科目对应培训项目
 *
 * @author admin
 */
public class TestSubInfoData extends BaseData {
    public int Id;
    public int TestSubId; // 科目Id，bd_testsub.Id
    public String Item; // 教学项目
    public String Content; // 教学内容
    public String Goal; // 教学目标
    public int IsDel; // 是否已删除

    public TestSubInfoData() {
    }

    public TestSubInfoData(int id, int testSubId, String item, String content, String goal, int isDel) {
        super();
        Id = id;
        TestSubId = testSubId;
        Item = item;
        Content = content;
        Goal = goal;
        IsDel = isDel;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getTestSubId() {
        return TestSubId;
    }

    public void setTestSubId(int testSubId) {
        TestSubId = testSubId;
    }

    public String getItem() {
        return Item;
    }

    public void setItem(String item) {
        Item = item;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getGoal() {
        return Goal;
    }

    public void setGoal(String goal) {
        Goal = goal;
    }

    public int getIsDel() {
        return IsDel;
    }

    public void setIsDel(int isDel) {
        IsDel = isDel;
    }

    @Override
    public String toString() {
        return "TestSubInfoData [Id=" + Id + ", TestSubId=" + TestSubId + ", Item=" + Item
                + ", Content=" + Content + ", Goal=" + Goal + ", IsDel=" + IsDel + "]";
    }

}
