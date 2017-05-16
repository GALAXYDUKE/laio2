package com.bofsoft.laio.customerservice.DataClass.index;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 13. 教练获取车管预约批次信息
 *
 * @author admin
 */
public class CGBatchData {
    // 请求参数

    private List<Info> InfoList = new ArrayList<Info>();

    // 方法

    public String getCGBatchData() throws JSONException {
        JSONObject js = new JSONObject();

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

    public List<Info> getInfoList() {
        return InfoList;
    }

    public void setInfoList(List<Info> infoList) {
        InfoList = infoList;
    }

    public static class Info {
        // 请求参数

        // 返回参数

        private String BatchDate; // 时间批次
        private List<CarType> CarTypeList = new ArrayList<CarType>(); // 列表

        // 报考车型列表

        public String getDataInfo() throws JSONException {
            JSONObject js = new JSONObject();

            return js.toString();
        }

        public static Info InitData(JSONObject js) throws JSONException {
            Info tmp = new Info();
            tmp.BatchDate = js.getString("BatchDate");
            JSONArray jsonArray = js.getJSONArray("CarTypeList");
            if (jsonArray != null) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject ojb = jsonArray.getJSONObject(i);
                    CarType carType = new CarType();
                    carType.setCarType(ojb.getString("CarType"));
                    tmp.CarTypeList.add(carType);
                }
            }

            return tmp;
        }

        public static class CarType {
            private String CarType; // 车型

            public String getCarType() {
                return CarType;
            }

            public void setCarType(String carType) {
                CarType = carType;
            }

        }

        public String getBatchDate() {
            return BatchDate;
        }

        public void setBatchDate(String batchDate) {
            BatchDate = batchDate;
        }

        public List<CarType> getCarTypeList() {
            return CarTypeList;
        }

        public void setCarTypeList(List<CarType> carTypeList) {
            CarTypeList = carTypeList;
        }

    }
}
