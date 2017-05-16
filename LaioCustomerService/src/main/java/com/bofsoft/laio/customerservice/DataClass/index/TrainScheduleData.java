package com.bofsoft.laio.customerservice.DataClass.index;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 培训进度
 *
 * @author admin
 */
public class TrainScheduleData {
    // 请求参数
    private int DPStuId; // DP中的学员ID（返回列表中的StudentId）,教练客户端查询需要传此参数

    // 返回参数
    private List<Info> InfoList = new ArrayList<Info>();
    private String StuName; // 学员姓名

    // 方法

    public String getTrainScheduleData() throws JSONException {
        JSONObject js = new JSONObject();
        js.put("DPStuId", DPStuId);
        return js.toString();
    }

    public static List<Info> initDataList(JSONObject js) throws JSONException {
        List<Info> tempList = new ArrayList<Info>();
        JSONArray tmpinfo = js.getJSONArray("info");
        for (int i = 0; i < tmpinfo.length(); i++) {
            tempList.add(Info.InitData(tmpinfo.getJSONObject(i)));
        }
        return tempList;
    }

    public int getDPStuId() {
        return DPStuId;
    }

    public void setDPStuId(int dPStuId) {
        DPStuId = dPStuId;
    }

    public String getStuName() {
        return StuName;
    }

    public void setStuName(String stuName) {
        StuName = stuName;
    }

    public List<Info> getInfoList() {
        return InfoList;
    }

    public void setInfoList(List<Info> infoList) {
        InfoList = infoList;
    }

    public static class Info {
        // 请求参数

        // 返回参数

        private String JiaoXueXiangMu; // 教学项目
        private String BiaoZhunYaoQiu; // 标准要求
        private String XueXiQinKuang; // 学习情况
        private String ChaZi; // 差值
        private boolean IsOver; // 是否完成

        public String getDataInfo() throws JSONException {
            JSONObject js = new JSONObject();

            return js.toString();
        }

        public static Info InitData(JSONObject js) throws JSONException {
            Info tmp = new Info();
            tmp.JiaoXueXiangMu = js.getString("JiaoXueXiangMu");
            tmp.BiaoZhunYaoQiu = js.getString("BiaoZhunYaoQiu");
            tmp.XueXiQinKuang = js.getString("XueXiQinKuang");
            tmp.ChaZi = js.getString("ChaZi");
            tmp.IsOver = js.getBoolean("IsOver");
            return tmp;
        }

        public String getJiaoXueXiangMu() {
            return JiaoXueXiangMu;
        }

        public void setJiaoXueXiangMu(String jiaoXueXiangMu) {
            JiaoXueXiangMu = jiaoXueXiangMu;
        }

        public String getBiaoZhunYaoQiu() {
            return BiaoZhunYaoQiu;
        }

        public void setBiaoZhunYaoQiu(String biaoZhunYaoQiu) {
            BiaoZhunYaoQiu = biaoZhunYaoQiu;
        }

        public String getXueXiQinKuang() {
            return XueXiQinKuang;
        }

        public void setXueXiQinKuang(String xueXiQinKuang) {
            XueXiQinKuang = xueXiQinKuang;
        }

        public String getChaZi() {
            return ChaZi;
        }

        public void setChaZi(String chaZi) {
            ChaZi = chaZi;
        }

        public boolean isIsOver() {
            return IsOver;
        }

        public void setIsOver(boolean isOver) {
            IsOver = isOver;
        }

    }

}
