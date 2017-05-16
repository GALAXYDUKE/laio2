package com.bofsoft.laio.customerservice.DataClass.db;

import android.content.ContentValues;

import com.bofsoft.laio.data.BaseData;

/**
 * 16 培训科目对应培训项目 laio_testsub_list
 *
 * @author admin
 */
public class TestSubListData extends BaseData {
    private int Id;
    private int TestSubID; //
    private String TypeNum; //
    private String ParNum; //
    private int LevelNum; //
    private int IsDel;

    public TestSubListData() {

    }

    public TestSubListData(int id, int testSubID, String typeNum, String parNum, int levelNum,
                           int isDel) {
        Id = id;
        TestSubID = testSubID;
        TypeNum = typeNum;
        ParNum = parNum;
        LevelNum = levelNum;
        IsDel = isDel;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getTestSubID() {
        return TestSubID;
    }

    public void setTestSubID(int testSubID) {
        TestSubID = testSubID;
    }

    public String getTypeNum() {
        return TypeNum;
    }

    public void setTypeNum(String typeNum) {
        TypeNum = typeNum;
    }

    public String getParNum() {
        return ParNum;
    }

    public void setParNum(String parNum) {
        ParNum = parNum;
    }

    public int getLevelNum() {
        return LevelNum;
    }

    public void setLevelNum(int levelNum) {
        LevelNum = levelNum;
    }

    public int getIsDel() {
        return IsDel;
    }

    public void setIsDel(int isDel) {
        IsDel = isDel;
    }

    public ContentValues getContentValues() {
        ContentValues values = new ContentValues();
        values.put("Id", Id);
        values.put("TestSubID", TestSubID);
        values.put("TypeNum", TypeNum);
        values.put("ParNum", ParNum);
        values.put("LevelNum", LevelNum);
        values.put("IsDel", IsDel);

        return values;
    }

}
