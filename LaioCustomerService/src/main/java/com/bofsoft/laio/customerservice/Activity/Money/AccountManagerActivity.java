package com.bofsoft.laio.customerservice.Activity.Money;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.alibaba.fastjson.JSON;
import com.bofsoft.laio.common.CommandCodeTS;
import com.bofsoft.laio.common.NetworkUtil;
import com.bofsoft.laio.customerservice.Activity.BaseVehicleActivity;
import com.bofsoft.laio.customerservice.Activity.Setting.BandingAliPayAccountActivity;
import com.bofsoft.laio.customerservice.Config.Config;
import com.bofsoft.laio.customerservice.Config.ConfigallCostomerService;
import com.bofsoft.laio.customerservice.DataClass.Order.AccountBalanceData;
import com.bofsoft.laio.customerservice.R;
import com.bofsoft.laio.customerservice.Tools.DialogUtils;
import com.bofsoft.laio.customerservice.Widget.Widget_Multi_Text_Button;
import com.bofsoft.laio.tcp.DataLoadTask;
import com.bofsoft.sdk.widget.base.Event;


/**
 * 账户管理
 *
 * @author yedong
 */
public class AccountManagerActivity extends BaseVehicleActivity implements OnClickListener {

    private final int Request_Code_Balance_Out = 10;
    private final int Request_Code_Set_PayPassword = 11;
    private final int Request_Code_Set_AliPay_Account = 12;

    private Widget_Multi_Text_Button btnBalanceDetails;
    private AccountBalanceData balanceData;
    private Widget_Multi_Text_Button btnBalanceOut;
    public boolean hasAccount=false;

    // private Widget_Multi_Text_Button btnMoneyBackRecord;

    @Override
    public void messageBack(int code, String result) {
        switch (code) {
            case CommandCodeTS.CMD_GETACCOUNTBALANCE_INTF:
                parseAccountBalance(result);
                break;
            case CommandCodeTS.CDM_GET_ALIPAYACCOUNT_LIST:
                if (result.contains("AlipayAccount")&&result.contains("IsDefault")) {
                    hasAccount=true;
                }
                break;
            default:
                super.messageBack(code, result);
                break;
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, true);
        setContentView(R.layout.activity_account_manager);

        initView();

    }

    public void initView() {

        btnBalanceDetails = (Widget_Multi_Text_Button) findViewById(R.id.btn_balanceDetails);
        btnBalanceOut = (Widget_Multi_Text_Button) findViewById(R.id.btn_BalanceOut);
        // btnApplyMoneyBack = (Widget_Multi_Text_Button)
        // findViewById(R.id.btn_ApplyMoneyBack);
        // btnMoneyBackRecord = (Widget_Multi_Text_Button)
        // findViewById(R.id.btn_MoneyBackRecord);

        btnBalanceDetails.setOnClickListener(this);
        btnBalanceOut.setOnClickListener(this);
        // btnMoneyBackRecord.setOnClickListener(this);

        getAccountBalance();
    }

    public void loadView() {
        if (balanceData != null) {
            btnBalanceDetails.setTextRight(getString(R.string.account_curr_balance,
                    String.valueOf(balanceData.Balance)));
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.btn_balanceDetails: // 余额明细
                intent = new Intent(AccountManagerActivity.this, AccountBalanceListActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_BalanceOut: // 余额转出
                balanceOut();
                break;
            // case R.id.btn_ApplyMoneyBack:
            // intent = new Intent(AccountManagerActivity.this,
            // AccountBalanceListActivity.class);
            // startActivity(intent);
            // break;
            // case R.id.btn_MoneyBackRecord:
            // intent = new Intent(AccountManagerActivity.this,
            // AccountBalanceListActivity.class);
            // startActivity(intent);
            // break;

            default:
                break;
        }

    }

    /**
     * 余额转出<br>
     * PayPwdIsSet 支付密码，0未设置，1已设置<br>
     * AlipayIsVerify 余额转出到的支付宝账户状态，1已验证，0已提交，-1未提交，-2审核失败
     */
    public void balanceOut() {
        if (NetworkUtil.isNetworkAvailable(AccountManagerActivity.this) == false) {
            showPrompt("提示", "无法连接到网络,请检查网络设置");
            return;
        }
        if (hasAccount){
            Intent intent = new Intent(AccountManagerActivity.this, BalanceOutActivity.class);
            intent.putExtra("BalanceOut", balanceData.Balance);
           startActivityForResult(intent, Request_Code_Balance_Out);
        }else{
            if ( ConfigallCostomerService.accountStatus != null) {
                String payStatus = "";
                switch (ConfigallCostomerService.accountStatus.AlipayIsVerify) {
                    case 1:
                        // if (ConfigallStu.accountStatus.PayPwdIsSet == 0) {
                        // payStatus = "您的支付密码未设置,是否立即设置？";
                        // showDialog(payStatus, Request_Code_Set_PayPassword);
                        //
                        // } else if (ConfigallStu.accountStatus.PayPwdIsSet == 1) {
                        Intent intent = new Intent(AccountManagerActivity.this, BalanceOutActivity.class);
                        intent.putExtra("BalanceOut", balanceData.Balance);
                        startActivityForResult(intent, Request_Code_Balance_Out);
                        // break;
                        // }
                        break;
                    case 0:
                        showPrompt("您的余额转出支付宝账户正在验证，请通过验证后再转出");
                        break;
                    case -1:
                        payStatus = "余额转出支付宝账号未设置,是否立即设置？";
                        showDialog(payStatus, Request_Code_Set_AliPay_Account);
                        break;
                    case -2:
                        payStatus = "余额转出支付宝账号审核失败,是否重新设置？";
                        showDialog(payStatus, Request_Code_Set_AliPay_Account);
                        break;

                    default:
                        break;
                }
            }
        }

    }

    public void showDialog(String content, final int requestCode) {
        DialogUtils.showDialog(this, getString(R.string.tip), content, getString(R.string.cancle),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }, getString(R.string.ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = null;
                        switch (requestCode) {
                            case Request_Code_Set_PayPassword:
                                intent = new Intent(AccountManagerActivity.this, SetPayPasswdActivity.class);
                                startActivityForResult(intent, requestCode);
                                break;
                            case Request_Code_Set_AliPay_Account:
                                intent =
                                        new Intent(AccountManagerActivity.this, BandingAliPayAccountActivity.class);
                                startActivityForResult(intent, requestCode);
                                break;

                            default:
                                break;
                        }

                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case Request_Code_Balance_Out:
                    getAccountBalance();
                    break;
                case Request_Code_Set_PayPassword:
                    getAccountStatusInfo();
                    break;
                case Request_Code_Set_AliPay_Account:
                    getAccountStatusInfo();
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * 获取账户余额
     */
    public void getAccountBalance() {
        showWaitDialog();
        DataLoadTask.getInstance().loadData(CommandCodeTS.CMD_GETACCOUNTBALANCE_INTF, null, this);
        DataLoadTask.getInstance().loadData(CommandCodeTS.CDM_GET_ALIPAYACCOUNT_LIST, null, this);
    }

    /**
     * 解析账户余额
     *
     * @param result
     */
    public void parseAccountBalance(String result) {
        closeWaitDialog();
        balanceData = JSON.parseObject(result, AccountBalanceData.class);
        loadView();
    }

    @Override
    protected void setTitleFunc() {
        setTitle("帐户余额");
    }

    @Override
    protected void setActionBarButtonFunc() {
        addLeftButton(Config.abBack.clone());
    }

    @Override
    protected void actionBarButtonCallBack(int id, View v, Event e) {
        switch (id) {
            case 0:
                finish();
                break;
        }
    }

}
