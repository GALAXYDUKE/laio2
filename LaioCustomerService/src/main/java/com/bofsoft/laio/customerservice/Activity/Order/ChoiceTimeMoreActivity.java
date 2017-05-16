package com.bofsoft.laio.customerservice.Activity.Order;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bofsoft.laio.common.CommandCodeTS;
import com.bofsoft.laio.customerservice.Activity.BaseVehicleActivity;
import com.bofsoft.laio.customerservice.Adapter.TrainProDataAdapter;
import com.bofsoft.laio.customerservice.Config.Config;
import com.bofsoft.laio.customerservice.DataClass.Order.ProTimeData;
import com.bofsoft.laio.customerservice.DataClass.Order.TrainProDataChoice;
import com.bofsoft.laio.customerservice.DataClass.Order.TrainProDetailData;
import com.bofsoft.laio.customerservice.R;
import com.bofsoft.laio.customerservice.Widget.CustomListView;
import com.bofsoft.laio.tcp.DataLoadTask;
import com.bofsoft.sdk.widget.base.Event;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * 学员端_获取某教练某类的培训产品可购买日期列表
 *
 * @author Administrator
 */

public class ChoiceTimeMoreActivity extends BaseVehicleActivity implements OnClickListener,
        OnItemClickListener {

    private TrainProDataAdapter adapter;
    private TrainProDetailData detailsData;

    private CustomListView customListView;

    private TrainProDataChoice dataChoice;

    private int ProType = 3;

    private TextView tvProData;
    private List<ProTimeData> list;

    private String ProDate; // 培训日期
    private int OrderId = 0; // 订单Id，默认为0，学员在修改培训订单时段时上传
    private int TestSubId = 0; // 培训科目Id，默认值为0不区分科目，1科目一、2科目二、3科目三、4科目四；
    private int ProTimes; // 可购买学时数
    private Button btnConfirm;

    @Override
    public void messageBack(int code, String result) {
        switch (code) {
            case CommandCodeTS.CMD_ONECOACHTRAINPROLIST_STU: // 学员端_获取某教练某类的培训产品可购买日期列表
                parseChoiceData(result);
                break;
            default:
                super.messageBack(code, result);
                break;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_protime_choice_more_state);
        ProType = getIntent().getIntExtra("ProType", ProType);
        OrderId = getIntent().getIntExtra("OrderId", OrderId);
        TestSubId = getIntent().getIntExtra("TestSubId", TestSubId);
        detailsData = (TrainProDetailData) getIntent().getSerializableExtra("detailsData");
        init();
    }

    private void init() {


        tvProData = (TextView) findViewById(R.id.tv_train_time);
        tvProData.setText("培训日期：" + detailsData.ProDate);

        customListView = (CustomListView) findViewById(R.id.list_choice_data);
        customListView.setOnItemClickListener(this);

        btnConfirm = (Button) findViewById(R.id.btn_confirm);
        btnConfirm.setOnClickListener(this);
        CMD_getProChoiceData();
    }

    /**
     * 获取教练购买日期列表
     */
    private void CMD_getProChoiceData() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("CoachUserUUID", detailsData.CoachUserUUID);
            jsonObject.put("ProType", ProType);
            jsonObject.put("OrderId", OrderId);
            jsonObject.put("TestSubId", TestSubId);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        showWaitDialog();
        DataLoadTask.getInstance().loadData(CommandCodeTS.CMD_ONECOACHTRAINPROLIST_STU,
                jsonObject.toString(), this);
    }

    /**
     * 解析返回的数据
     *
     * @param json
     */
    private void parseChoiceData(String json) {
        closeWaitDialog();
        dataChoice = JSON.parseObject(json, TrainProDataChoice.class);

        if (dataChoice != null) {
            list = dataChoice.GroupList;
            if (list.size() == 0) {
                showToastLongTime("没有更多日期选择");
            }
            adapter = new TrainProDataAdapter(list, ChoiceTimeMoreActivity.this);
            customListView.setAdapter(adapter);
        }
        // handler.sendEmptyMessage(100);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_confirm: // 确认后，把产品日期回传
                if (ProDate != null && ProTimes != 0) {
                    if (!detailsData.ProDate.equals(ProDate)) {
                        // showToastShortTime("已选定日期："+ ProDate);
                        Intent intent = new Intent();
                        intent.putExtra("ProDate", ProDate);
                        setResult(RESULT_OK, intent);
                    }
                }
                this.finish();
                break;
            default:
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ProDate = list.get(position).ProDate;
        ProTimes = list.get(position).ProTimes;
        tvProData.setText("培训日期：" + ProDate);
    }

    @Override
    protected void setTitleFunc() {
        setTitle("选择培训时段");
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
