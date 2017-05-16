package com.bofsoft.laio.laiovehiclegps.Config;

import android.content.Context;

import com.bofsoft.laio.laiovehiclegps.DataClass.CityData;
import com.bofsoft.laio.laiovehiclegps.R;
import com.bofsoft.sdk.config.BaseSysConfig;
import com.bofsoft.sdk.widget.button.IconButton;
import com.bofsoft.sdk.widget.button.ImageTextButton;

public class Config extends BaseSysConfig {

  private static Config _inst = null;

  // 用户城市信息
  public static CityData CITY_DATA;
  // 用户区信息
//  public static DistrictData DIS_DATA;

  public static ImageTextButton abBack;
  public static ImageTextButton abMsg;
  public static ImageTextButton abAddr;
  public static IconButton abAdd;
  public static ImageTextButton abRefrish;
  public static ImageTextButton abClose;
  public static ImageTextButton abSearch;

  /**
   * 获取静态实例方法
   * 
   * @return
   */
  public static Config getInstance() {
    // 无竞争调用，无需加同步保护Ï
    if (null == _inst) {
      _inst = new Config();
      return _inst;
    }
    return _inst;
  }

  /**
   * 只能内部调用的构造函数
   */
  protected Config() {
    super();
  }

  /**
   * 必须执行的初始化方法
   */
  public void init(Context con) {
    super.init(con);

    abBack = new ImageTextButton(getContext(), 0, null, R.mipmap.ns_back);
//    abMsg = new ImageTextButton(getContext(), 10, null, R.drawable.ns_msg);
//    abAddr = new ImageTextButton(getContext(), 20, "地区", R.drawable.ns_trian_bottom);
//    abAddr.setContentGravity(Gravity.BOTTOM);
//    abAddr.setImageAlign(ImageAlign.RIGHT);
//    abAdd = new IconButton(getContext(), 1, R.drawable.add);
//    abRefrish = new ImageTextButton(getContext(), 30, null, R.drawable.refresh_ico);
//    abClose = new ImageTextButton(getContext(), 40, null, R.drawable.close_ico);
//    abSearch = new ImageTextButton(getContext(), 50, null, R.drawable.search1);

//    getCityData();
//    getDisData();
  }

//  public static void saveCityData(CityData city) {
//    try {
//      spHelper.putString("city", new String(SerializeUtil.getInstence().toBateArray(city),
//          "ISO-8859-1"));
//      CITY_DATA = city;
//    } catch (UnsupportedEncodingException e) {
//      e.printStackTrace();
//    }
//  }

//  public static void saveDisData(DistrictData dis) {
//    try {
//      spHelper.putString("dis", new String(SerializeUtil.getInstence().toBateArray(dis),
//          "ISO-8859-1"));
//      DIS_DATA = dis;
//    } catch (UnsupportedEncodingException e) {
//      e.printStackTrace();
//    }
//  }

//  public CityData getCityData() {
//    String cityStr = spHelper.getString("city", null);
//    if (cityStr != null) {
//      try {
//        CITY_DATA = (CityData) SerializeUtil.getInstence().toObject(cityStr.getBytes("ISO-8859-1"));
//      } catch (UnsupportedEncodingException e) {
//        e.printStackTrace();
//      }
//    }
//    return CITY_DATA;
//  }

//  public DistrictData getDisData() {
//    String cityStr = spHelper.getString("dis", null);
//    if (cityStr != null) {
//      try {
//        DIS_DATA =
//            (DistrictData) SerializeUtil.getInstence().toObject(cityStr.getBytes("ISO-8859-1"));
//      } catch (UnsupportedEncodingException e) {
//        e.printStackTrace();
//      }
//    }
//    return DIS_DATA;
//  }

  @Override
  protected String getIP() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  protected int getPORT() {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  protected Integer getActionBarHeight() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  protected Integer getActionBarDrawableResources() {
    return null;
  }

  @Override
  protected Integer getActionBarColor() {
    return getResources().getColor(R.color.green_bg);
  }

  @Override
  protected Integer getActionBarTitleColor() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  protected Integer getActionBarTitleSize() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  protected Integer getActionBarTexButtonNormalColor() {
    return null;
  }

  @Override
  protected Integer getActionBarTexButtonDownColor() {
    return null;
  }

  @Override
  protected Integer getActionBarTexButtonSize() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  protected Integer getActionBarButtonNormalColor() {
    return getResources().getColor(R.color.actionbar_btn_normal);
  }

  @Override
  protected Integer getActionBarButtonDownColor() {
    return getResources().getColor(R.color.actionbar_btn_down);
  }

  @Override
  protected Integer[] getDefaultImage() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  protected Integer getActivityAnimLeftInResId() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  protected Integer getActivityAnimRightInResId() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  protected Integer getActivityAnimLeftOutResId() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  protected Integer getActivityAnimRightOutResId() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  protected Integer getLoadingResId() {
    return R.mipmap.ic_launcher;
  }

}
