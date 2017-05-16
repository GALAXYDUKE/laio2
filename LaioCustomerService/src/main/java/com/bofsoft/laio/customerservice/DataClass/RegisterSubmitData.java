package com.bofsoft.laio.customerservice.DataClass;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 注册提交信息
 * 
 * @author admin
 *
 */
public class RegisterSubmitData {
  // 请求参数
  private String Mobile; // 注册手机号
  private String VerifyCode; // 验证码
  private String Password; // 登录密码
  private int ObjectType; // 用户类型： 0:学员 1:教练

  // 返回参数
  private int Code; // 提交状态，1成功，0不成功
  private String Content; // 显示的消息

  // 方法

  /**
   * @return 客户端登陆参数
   * @throws JSONException
   */
  public String getSubmitData() throws JSONException {
    JSONObject js = new JSONObject();
    js.put("Mobile", Mobile);
    js.put("VerifyCode", VerifyCode);
    js.put("Password", Password);
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
  public static RegisterSubmitData InitData(JSONObject js) throws JSONException {
    RegisterSubmitData tmp = new RegisterSubmitData();
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

  public String getVerifyCode() {
    return VerifyCode;
  }

  public void setVerifyCode(String verifyCode) {
    VerifyCode = verifyCode;
  }

  public String getPassword() {
    return Password;
  }

  public void setPassword(String password) {
    Password = password;
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
