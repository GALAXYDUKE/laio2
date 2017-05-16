package com.bofsoft.laio.customerservice.DataClass;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 注册获取验证码
 * 
 * @author admin
 *
 */
public class RegisterGetAuthCodeData {
  // 请求参数
  private String Mobile; // 注册手机号
  private int MobileType; // 用户使用手机类型：1:Android;2：IOS；
  private int VerifyType; // 验证码类型，1 注册账号，2 重置账号登录密码，3 重置账号支付密码，6 ERP账号验证手机号码
  private int ObjectType; // 用户类型： 0:学员 1:教练

  // 返回参数
  private int Code; // 提交状态，1成功，0不成功
  private String Content; // 显示的消息

  // 方法

  /**
   * @return 客户端登陆参数
   * @throws JSONException
   */
  public String getAuthCodeData() throws JSONException {
    JSONObject js = new JSONObject();
    js.put("Mobile", Mobile);
    js.put("MobileType", MobileType);
    js.put("VerifyType", VerifyType);
    js.put("ObjectType", ObjectType);

    return js.toString();
  }

  /**
   * 解析返回string
   * 
   * @param js
   * @return
   * @throws JSONException
   */
  public static RegisterGetAuthCodeData InitData(JSONObject js) throws JSONException {
    RegisterGetAuthCodeData tmp = new RegisterGetAuthCodeData();
    tmp.Code = js.getInt("Code");
    tmp.Content = js.getString("Content");

    return tmp;
  }

  public String getMobile() {
    return Mobile;
  }

  public void setMobile(String mobile) {
    Mobile = mobile;
  }

  public int getMobileType() {
    return MobileType;
  }

  public void setMobileType(int mobileType) {
    MobileType = mobileType;
  }

  public int getVerifyType() {
    return VerifyType;
  }

  public void setVerifyType(int verifyType) {
    VerifyType = verifyType;
  }

  public int getObjectType() {
    return ObjectType;
  }

  public void setObjectType(int objectType) {
    ObjectType = objectType;
  }

  public int getCode() {
    return Code;
  }

  public void setCode(int code) {
    Code = code;
  }

  public String getContent() {
    return Content;
  }

  public void setContent(String content) {
    Content = content;
  }

}
