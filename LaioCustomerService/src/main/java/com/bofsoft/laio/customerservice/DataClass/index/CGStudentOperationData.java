package com.bofsoft.laio.customerservice.DataClass.index;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 15. 教练车管预约/取消预约接口
 *
 * @author admin
 */
public class CGStudentOperationData {
    // 请求参数
    private String BatchDate; // 时间批次
    private String CarType; // 分配车型
    private String StudentId; // 学员ID
    private int Operation; // 操作类型，1选中，0取消

    // 返回参数

    private int StudentIdResult; // StudentId 学员ID
    private boolean IsSuccess; // 是否预约成功，1成功，0失败
    private String FailMsg; // 预约失败提示信息
    private String Topmsg; // 上报名额，当前名额，截止时间

    // 方法

    public String getCGStudentOperationData() throws JSONException {
        JSONObject js = new JSONObject();
        js.put("BatchDate", BatchDate);
        js.put("CarType", CarType);
        js.put("StudentId", StudentId);
        js.put("Operation", Operation);

        return js.toString();
    }

    public static CGStudentOperationData initData(JSONObject js) throws JSONException {
        CGStudentOperationData data = new CGStudentOperationData();
        data.StudentIdResult = js.getInt("StudentId");
        data.IsSuccess = js.getBoolean("IsSuccess");
        data.FailMsg = js.getString("FailMsg");
        data.Topmsg = js.getString("Topmsg");
        return data;
    }

    public String getBatchDate() {
        return BatchDate;
    }

    public void setBatchDate(String batchDate) {
        BatchDate = batchDate;
    }

    public String getCarType() {
        return CarType;
    }

    public void setCarType(String carType) {
        CarType = carType;
    }

    public String getStudentId() {
        return StudentId;
    }

    public void setStudentId(String studentId) {
        StudentId = studentId;
    }

    public int getOperation() {
        return Operation;
    }

    public void setOperation(int operation) {
        Operation = operation;
    }

    public int getStudentIdResult() {
        return StudentIdResult;
    }

    public void setStudentIdResult(int studentIdResult) {
        StudentIdResult = studentIdResult;
    }

    public boolean isIsSuccess() {
        return IsSuccess;
    }

    public void setIsSuccess(boolean isSuccess) {
        IsSuccess = isSuccess;
    }

    public String getFailMsg() {
        return FailMsg;
    }

    public void setFailMsg(String failMsg) {
        FailMsg = failMsg;
    }

    public String getTopmsg() {
        return Topmsg;
    }

    public void setTopmsg(String topmsg) {
        Topmsg = topmsg;
    }

}
