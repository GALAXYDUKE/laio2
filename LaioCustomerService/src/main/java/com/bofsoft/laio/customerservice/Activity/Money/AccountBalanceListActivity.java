package com.bofsoft.laio.customerservice.Activity.Money;

import android.os.Bundle;
import android.view.View;

import com.bofsoft.laio.common.CommandCodeTS;
import com.bofsoft.laio.customerservice.Activity.BaseVehicleActivity;
import com.bofsoft.laio.customerservice.Adapter.AccountBalanceAdapter;
import com.bofsoft.laio.customerservice.Config.Config;
import com.bofsoft.laio.customerservice.R;
import com.bofsoft.laio.widget.CustomPullRefreshListView;
import com.bofsoft.sdk.widget.base.Event;

import org.json.JSONObject;


/**
 * 账户收支明细列表
 *
 * @author yedong
 */
public class AccountBalanceListActivity extends BaseVehicleActivity {

    public int Type = 0; // 收支类型，0全部 1收入 -1支出

    private AccountBalanceAdapter mAdapter;
    private CustomPullRefreshListView mListView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, true);
        setContentView(R.layout.activity_account_balance_list);
        initView();
    }

    public void initView() {

        mListView = (CustomPullRefreshListView) findViewById(R.id.pull_listView);
        mAdapter = new AccountBalanceAdapter(this, mListView);
        CMD_getData();
    }

    public void CMD_getData() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("Type", Type);

        } catch (Exception e) {
            e.printStackTrace();
        }
        mAdapter.loadData(jsonObject, CommandCodeTS.CMD_GETACCOUNTASSETINOUTLIST_INTF);
    }


    @Override
    protected void setTitleFunc() {
        setTitle("收支明细");
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
