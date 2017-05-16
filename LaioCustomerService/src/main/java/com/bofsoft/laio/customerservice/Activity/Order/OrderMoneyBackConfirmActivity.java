package com.bofsoft.laio.customerservice.Activity.Order;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.alibaba.fastjson.JSON;
import com.bofsoft.laio.common.CommandCodeTS;
import com.bofsoft.laio.customerservice.Activity.BaseVehicleActivity;
import com.bofsoft.laio.customerservice.Config.Config;
import com.bofsoft.laio.customerservice.Config.ConfigallCostomerService;
import com.bofsoft.laio.customerservice.DataClass.Order.OrdMoneyBackConfirmData;
import com.bofsoft.laio.customerservice.R;
import com.bofsoft.laio.customerservice.Widget.Widget_Image_Text_Btn;
import com.bofsoft.laio.tcp.DataLoadTask;
import com.bofsoft.sdk.widget.base.Event;

import org.json.JSONObject;


/**
 * 订单确认退款
 *
 * @author admin
 */
public class OrderMoneyBackConfirmActivity extends BaseVehicleActivity implements OnClickListener {

    private RadioGroup mRadioGroup;
    private RadioButton mRadioButton1;
    private RadioButton mRadioButton2;
    private EditText mEdtReason;
    private Widget_Image_Text_Btn mBtnMoneyBack;

    private int OrderId = -1;
    private String Reason;
    private int Pass = 0; // Integer 是否通过，0不通过，1通过

    @Override
    public void messageBack(int code, String result) {
        switch (code) {
            case CommandCodeTS.CMD_ORDERREFUNDAFFIRM_COACH:
                parseOrderCancle(result);
                break;
            default:
                super.messageBack(code, result);
                break;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_money_back_confirm);

        Intent intent = getIntent();
        OrderId = intent.getIntExtra(ConfigallCostomerService.Param_Key, OrderId);

        initView();
    }

    public void initView() {
        mRadioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        mRadioButton1 = (RadioButton) findViewById(R.id.radioButton1);
        mRadioButton2 = (RadioButton) findViewById(R.id.radioButton2);
        mEdtReason = (EditText) findViewById(R.id.edtReason);

        mBtnMoneyBack = (Widget_Image_Text_Btn) findViewById(R.id.btn_MoneyBack);

        mBtnMoneyBack.setOnClickListener(this);
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radioButton1:
                        Pass = 0;
                        break;
                    case R.id.radioButton2:
                        Pass = 1;
                        break;
                    default:
                        break;
                }

            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btn_MoneyBack:
                Reason = mEdtReason.getText().toString().trim();
                if (Pass == 0) {
                    if (Reason.equals("")) {
                        showPrompt("请输入您不同意的原因");
                        return;
                    }
                }
                orderMoneyBack();
                break;

            default:
                break;
        }
    }

    /**
     * 确认退款
     */
    public void orderMoneyBack() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("OrderId", OrderId);
            jsonObject.put("Pass", Pass);
            jsonObject.put("Reason", Reason);
        } catch (Exception e) {
            e.printStackTrace();
        }

        showWaitDialog();
        DataLoadTask.getInstance().loadData(CommandCodeTS.CMD_ORDERREFUNDAFFIRM_COACH,
                jsonObject.toString(), this);
    }

    public void parseOrderCancle(String result) {
        closeWaitDialog();
        OrdMoneyBackConfirmData data = JSON.parseObject(result, OrdMoneyBackConfirmData.class);
        if (data.Code == 1) {
            showPrompt(data.Content, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    setResult(RESULT_OK);
                    OrderMoneyBackConfirmActivity.this.finish();
                }
            });
        } else {
            showPrompt(data.Content);
        }
    }

    @Override
    protected void setTitleFunc() {
        setTitle("确认退款");
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
