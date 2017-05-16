package com.bofsoft.laio.customerservice.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

import com.alibaba.fastjson.JSON;
import com.bofsoft.laio.common.CommandCodeTS;
import com.bofsoft.laio.customerservice.Activity.Select.SelectEnrollCarTypeActivity;
import com.bofsoft.laio.customerservice.Activity.Select.SelectEnrollReasonActivity;
import com.bofsoft.laio.customerservice.Activity.Select.SelectIdTypeActivity;
import com.bofsoft.laio.customerservice.Activity.Select.SelectStuSourceActivity;
import com.bofsoft.laio.customerservice.Config.Config;
import com.bofsoft.laio.customerservice.Config.ConfigallCostomerService;
import com.bofsoft.laio.customerservice.DataClass.BaseResponseStatusData;
import com.bofsoft.laio.customerservice.DataClass.Order.OrderList;
import com.bofsoft.laio.customerservice.DataClass.db.CarTypeData;
import com.bofsoft.laio.customerservice.DataClass.db.EnrollReasonData;
import com.bofsoft.laio.customerservice.DataClass.db.IdTypeData;
import com.bofsoft.laio.customerservice.DataClass.db.StuSourceData;
import com.bofsoft.laio.customerservice.Database.PublicDBManager;
import com.bofsoft.laio.customerservice.Database.TableManager;
import com.bofsoft.laio.customerservice.R;
import com.bofsoft.laio.customerservice.Widget.Widget_Image_Text_Btn;
import com.bofsoft.laio.customerservice.Widget.Widget_Multi_Text_Button;
import com.bofsoft.laio.tcp.DataLoadTask;
import com.bofsoft.sdk.widget.base.Event;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 学员录入
 *
 * @author admin
 */
public class StudentEnrollActivity extends BaseVehicleActivity implements OnClickListener {

    private final int Request_Code_IdType = 5;
    private final int Request_Code_CarType = 6;
    private final int Request_Code_EnrollReason = 7;
    private final int Request_Code_StuSource = 8;
//  private final int Request_Code_TrainType = 9;
//  private final int Request_Code_CoachCar = 10;

    private EditText mEdtName;
    private Widget_Multi_Text_Button mLayout_CardType;
    private EditText mEdtCardNum;
    private EditText mEdtPhone;
    private Widget_Multi_Text_Button mLayout_CarType;
    private Widget_Multi_Text_Button mLayout_Reason;
    private Widget_Multi_Text_Button mLayout_Scource;
    //  private Widget_Multi_Text_Button mLayout_TrainType;
//  private Widget_Multi_Text_Button mLayout_CoachCar;
    private Widget_Image_Text_Btn ITBtnSubmit;

    private IdTypeData mIdTypeData;
    private CarTypeData mCarTypeData;
    private EnrollReasonData mEnrollReasonData;
    private StuSourceData mStuSourceData;
    private OrderList.Order mOrder;
//  private TrainingTypeData mTrainingTypeData;
//  private CoachCarListData mCoachCarListData;

    @Override
    public void messageBack(int code, String result) {
        switch (code) {
            case CommandCodeTS.CMD_COACH_STUDENTINFOREG:
                parseSubmitInfo(result);
                break;
            default:
                super.messageBack(code, result);
                break;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, true);
        setContentView(R.layout.activity_student_enroll);

        mIdTypeData =
                PublicDBManager.getInstance(StudentEnrollActivity.this).queryById(IdTypeData.class,
                        TableManager.Type_IdType, 1, null);

        Intent intent = getIntent();
        String OrderStr = intent.getStringExtra(ConfigallCostomerService.Param_Key);
        mOrder = JSON.parseObject(OrderStr, OrderList.Order.class);
        mCarTypeData = PublicDBManager.getInstance(StudentEnrollActivity.this).queryBySelection(CarTypeData.class,
                TableManager.Laio_CarType, "DM=?", new String[]{mOrder.getCarType()});
        initView();

    }

    public void initView() {

        mEdtName = (EditText) findViewById(R.id.edtName);
        mLayout_CardType = (Widget_Multi_Text_Button) findViewById(R.id.layout_cardType);
        mLayout_CardType.setTextRight("居民身份证");
        mIdTypeData = new IdTypeData();
        mIdTypeData.DM = "A"; // 这里加了默认居民身份证

        if (mIdTypeData != null && !TextUtils.isEmpty(mIdTypeData.MC)) {
            mLayout_CardType.setTextRight(mIdTypeData.MC);
        }

        mEdtCardNum = (EditText) findViewById(R.id.edtCardNum);
        mEdtPhone = (EditText) findViewById(R.id.edtPhone);
        mLayout_CarType = (Widget_Multi_Text_Button) findViewById(R.id.layout_CarType);
        mLayout_Reason = (Widget_Multi_Text_Button) findViewById(R.id.layout_Reason);
        mLayout_Scource = (Widget_Multi_Text_Button) findViewById(R.id.layout_Scource);

        mLayout_CarType.setTextRight(mCarTypeData.getDM());// 报考车型 默认值c1 id = 6
        mLayout_Reason.setTextRight("初学");// 报考原因 默认值初学 DM = 1
        mLayout_Scource.setTextRight("本地");// 报考来源 默认值本地 id = A

        mEnrollReasonData = new EnrollReasonData();
        mEnrollReasonData.DM = "A";
        mStuSourceData = new StuSourceData();
        mStuSourceData.DM = "A";

//    mLayout_TrainType = (Widget_Multi_Text_Button) findViewById(R.id.layout_TrainType);
//    mLayout_CoachCar = (Widget_Multi_Text_Button) findViewById(R.id.layout_CoachCar);
        ITBtnSubmit = (Widget_Image_Text_Btn) findViewById(R.id.itbtn_submit);

        mLayout_CardType.setOnClickListener(this);
//        mLayout_CarType.setOnClickListener(this);
        mLayout_Reason.setOnClickListener(this);
        mLayout_Scource.setOnClickListener(this);
//    mLayout_TrainType.setOnClickListener(this);
//    mLayout_CoachCar.setOnClickListener(this);
        ITBtnSubmit.setOnClickListener(this);
        mEdtPhone.setText(mOrder.getBuyerTel());
//        mEdtPhone.setEnabled(false);
        mEdtName.setText(mOrder.getBuyerName());
    }

    public void submitInfo() {
        String stuName = mEdtName.getText().toString().trim();
        String CardNum = mEdtCardNum.getText().toString().trim();
        String phone = mEdtPhone.getText().toString().trim();
        if (TextUtils.isEmpty(stuName)) {
            showToastShortTime("请填写姓名");
            return;
        }
        if (mIdTypeData == null) {
            showToastShortTime("请选择身份证明类型");
            return;
        }
        if (TextUtils.isEmpty(CardNum)) {
            showToastShortTime("请填写证件号码");
            return;
        }
        if (TextUtils.isEmpty(phone)) {
            showToastShortTime("请填联系电话");
            return;
        }

        if (mCarTypeData == null) {
            showToastShortTime("请选择报考车型");
            return;
        }
        if (mEnrollReasonData == null) {
            showToastShortTime("请选择报考原因");
            return;
        }
        if (mStuSourceData == null) {
            showToastShortTime("请选择学员来源");
            return;
        }
//    if (mTrainingTypeData == null) {
//      showToastShortTime("请选择培训模式");
//      return;
//    }

        JSONObject subObj = new JSONObject();
        try {
            subObj.put("OrderId", mOrder.getId());
            subObj.put("StuName", stuName);
            subObj.put("IDCardTypeDM", mIdTypeData.DM);
            subObj.put("IDCardNum", CardNum);
            subObj.put("Phone", phone);
            subObj.put("CarTypeID", mCarTypeData.getId());
            subObj.put("CarType", mCarTypeData.getDM());
            subObj.put("FromDM", mStuSourceData.DM);
            subObj.put("Reason", mEnrollReasonData.Id);

            // StuName String 学员姓名
            // IDCardTypeDM String 证件类型， A居民身份证,、C军官证、 D士兵证、F境外人员身份证明
            // IDCardNum String 证件号码
            // Phone String 联系电话
            // CarTypeID Integer 报考车型ID
            // CarType String 报考车型
            // FromDM String 生源， A本地,，B外地
            // Reason Integer 申请类型， 0初学， 1增驾
            // TrainingType Integer 培训模式,，0传统模式，1计时计程
            // CarID Integer 教练车ID
            // CarLicense String 教练车牌号

        } catch (JSONException e) {
            e.printStackTrace();
        }
        showWaitDialog();
        DataLoadTask.getInstance().loadData(CommandCodeTS.CMD_COACH_STUDENTINFOREG, subObj.toString(),
                this);
    }

    public void parseSubmitInfo(String result) {
        closeWaitDialog();
        BaseResponseStatusData data = JSON.parseObject(result, BaseResponseStatusData.class);
        if (data != null) {
            switch (data.Code) {
                case 1:
                    // showPrompt(data.Content, new
                    // DialogInterface.OnClickListener() {
                    // @Override
                    // public void onClick(DialogInterface dialog, int which) {
                    // finish();
                    // }
                    // });
                    showToastShortTime("学员录入成功");
                    new Thread() {
                        public void run() {
                            try {
                                Thread.sleep(2000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            finish();
                        }

                    }.start();
                    break;
                case 0:
                    showPrompt(data.Content);
                    break;

                default:
                    break;
            }
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.layout_cardType:
                intent = new Intent(this, SelectIdTypeActivity.class);
                startActivityForResult(intent, Request_Code_IdType);
                break;
            case R.id.layout_CarType:
                intent = new Intent(this, SelectEnrollCarTypeActivity.class);
                startActivityForResult(intent, Request_Code_CarType);
                break;
            case R.id.layout_Reason:
                intent = new Intent(this, SelectEnrollReasonActivity.class);
                startActivityForResult(intent, Request_Code_EnrollReason);
                break;
            case R.id.layout_Scource:
                intent = new Intent(this, SelectStuSourceActivity.class);
                startActivityForResult(intent, Request_Code_StuSource);
                break;
//      case R.id.layout_TrainType:
//        intent = new Intent(this, SelectTrainingTypeActivity.class);
//        startActivityForResult(intent, Request_Code_TrainType);
//        break;
//      case R.id.layout_CoachCar:
//        intent = new Intent(this, CarListActivity.class);
//        intent.putExtra(ConfigallTea.Param_Key, true);
//        startActivityForResult(intent, Request_Code_CoachCar);
//        break;
            case R.id.itbtn_submit:
                submitInfo();
                break;

            default:
                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            switch (requestCode) {
                case Request_Code_IdType:
                    mIdTypeData = (IdTypeData) data.getSerializableExtra(ConfigallCostomerService.Result_Key);
                    mLayout_CardType.setTextRight(mIdTypeData.MC);
                    break;
//                case Request_Code_CarType:
//                    mCarTypeData = (EnrollCarTypeData) data.getSerializableExtra(ConfigallCostomerService.Result_Key);
//                    mLayout_CarType.setTextRight(mCarTypeData.CarType);
//                    break;
                case Request_Code_EnrollReason:
                    mEnrollReasonData = (EnrollReasonData) data.getSerializableExtra(ConfigallCostomerService.Result_Key);
                    mLayout_Reason.setTextRight(mEnrollReasonData.MC);
                    break;
                case Request_Code_StuSource:
                    mStuSourceData = (StuSourceData) data.getSerializableExtra(ConfigallCostomerService.Result_Key);
                    mLayout_Scource.setTextRight(mStuSourceData.MC);
                    break;
//        case Request_Code_TrainType:
//          mTrainingTypeData = (TrainingTypeData) data.getSerializableExtra(ConfigallCostomerService.Result_Key);
//          mLayout_TrainType.setTextRight(mTrainingTypeData.MC);
//          break;
//        case Request_Code_CoachCar:
//          mCoachCarListData = (CoachCarListData) data.getSerializableExtra(ConfigallCostomerService.Result_Key);
//          mLayout_CoachCar.setTextRight(mCoachCarListData.CarLicence);
//          break;

                default:
                    break;
            }
        }
    }

    @Override
    protected void setTitleFunc() {
        setTitle("学员录入");
    }

    @Override
    protected void setActionBarButtonFunc() {
        addLeftButton(Config.abBack.clone());
    }

    @Override
    protected void actionBarButtonCallBack(int id, View v, Event e) {
        switch (id) {
            case 0:
                setResult(RESULT_CANCELED);
                finish();
                break;
        }
    }

}
