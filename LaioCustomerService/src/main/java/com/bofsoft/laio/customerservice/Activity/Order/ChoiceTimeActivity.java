package com.bofsoft.laio.customerservice.Activity.Order;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bofsoft.laio.common.CommandCodeTS;
import com.bofsoft.laio.customerservice.Activity.BaseVehicleActivity;
import com.bofsoft.laio.customerservice.Adapter.TrainDetailsAddrAdapter;
import com.bofsoft.laio.customerservice.Adapter.TrainDetailsTimeAdapter;
import com.bofsoft.laio.customerservice.Adapter.TrainDetailsVasAdapter;
import com.bofsoft.laio.customerservice.Config.Config;
import com.bofsoft.laio.customerservice.Config.ConfigallCostomerService;
import com.bofsoft.laio.customerservice.DataClass.Order.TrainProDetailData;
import com.bofsoft.laio.customerservice.R;
import com.bofsoft.laio.customerservice.Widget.CustomGridView;
import com.bofsoft.laio.customerservice.Widget.CustomListView;
import com.bofsoft.laio.tcp.DataLoadTask;
import com.bofsoft.sdk.widget.base.Event;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.List;

/**
 * 选择培训时间段
 *
 * @author admin
 */
public class ChoiceTimeActivity extends BaseVehicleActivity implements OnClickListener,
        TrainDetailsTimeAdapter.TrainProTimeChangerListener {

    public static final int REQUEST_CODE_CHOICE_PRODATA = 1001;

    private CustomGridView mGridTime;
    private CustomListView mGridVas;
    private CustomListView mListTranferAddr;

    private TrainDetailsTimeAdapter mTimeAdapter;
    private TrainDetailsVasAdapter mProVasAdapter;
    private TrainDetailsAddrAdapter mTrainAddrAdapter;

    private TextView tvTimeState, tvServiceState, tvAddressState;

    // private TextView mTxtTrainFee;
    // private TextView mTxtVasFee;
    // private TextView mTxtTotalFee;

    private int ProType = 3; // 获取产品详情时产品类型
    private int DateType = 0; // Integer ProDate不为空时有效，
    // 0返回ProDate当天，-1返回ProDate前一天，1返回ProDate后一天学时产品
    private TextView tvProData;
    private LinearLayout layoutMore;
    private Button btnConfirm;

    private TrainProDetailData detailsData;
    // private FindTeacherData findData;
    private String CoachUserUUID;
    List<Boolean> timeCheckList;
    List<Boolean> vasCheckList;
    List<Boolean> addCheckList;
    String checkGuid = "";
    // private String ProData;

    private int OrderId = 0;
    private int TestSubId = 0; // 培训科目Id，默认值为0不区分科目，1科目一、2科目二、3科目三、4科目四；

    private String newProData;

    @Override
    public void messageBack(int code, String result) {
        switch (code) {
            case CommandCodeTS.CMD_ONEDAYONECOACHTRAINPRO_STU: // 返回产品详情
                closeWaitDialog();
                parseProDetail(result);
                break;
            default:
                super.messageBack(code, result);
                break;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_protime_choice_state);
        Intent intent = getIntent();
        OrderId = getIntent().getIntExtra("OrderId", OrderId);
        TestSubId = getIntent().getIntExtra("TestSubId", TestSubId);
        detailsData = (TrainProDetailData) intent.getSerializableExtra("detailsData");
        timeCheckList = (List<Boolean>) intent.getSerializableExtra(ConfigallCostomerService.Param_Key);
        vasCheckList = (List<Boolean>) intent.getSerializableExtra(ConfigallCostomerService.Param_Key_Two);
        addCheckList = (List<Boolean>) intent.getSerializableExtra(ConfigallCostomerService.Param_Key_Three);
        checkGuid = getIntent().getStringExtra("Guid");
        // findData = (FindTeacherData) intent.getSerializableExtra("findData");
        CoachUserUUID = intent.getStringExtra("UUID");
        ProType = intent.getIntExtra("ProType", 0);
        if (detailsData != null) {
            // ProData = detailsData.ProDate;
            init();
        }
    }

    /**
     * 初始化界面
     */
    private void init() {

        tvProData = (TextView) findViewById(R.id.tv_train_time);
        tvProData.setText("培训日期：" + detailsData.ProDate);

        layoutMore = (LinearLayout) findViewById(R.id.layout_more);
        layoutMore.setOnClickListener(this);

        mGridTime = (CustomGridView) findViewById(R.id.GridTime);
        mTimeAdapter = new TrainDetailsTimeAdapter(this);
        mTimeAdapter.setTimeChangerListener(this);
        mGridTime.setAdapter(mTimeAdapter);

        mGridVas = (CustomListView) findViewById(R.id.GridVas);
        mProVasAdapter = new TrainDetailsVasAdapter(this);
        mGridVas.setAdapter(mProVasAdapter);

        mListTranferAddr = (CustomListView) findViewById(R.id.list_TransferAddr);
        mTrainAddrAdapter = new TrainDetailsAddrAdapter(this);
        mListTranferAddr.setAdapter(mTrainAddrAdapter);

        tvTimeState = (TextView) findViewById(R.id.txtTimeState);
        tvServiceState = (TextView) findViewById(R.id.txtVasState);
        tvAddressState = (TextView) findViewById(R.id.txtTransferAddrState);

        mTimeAdapter.setList(detailsData.TimesList);
        mTimeAdapter.checkTimeGUID = checkGuid;
        mTimeAdapter.setCheckedList(timeCheckList);

        mProVasAdapter.setList(detailsData.VasList);
        mProVasAdapter.setCheckList(vasCheckList, checkGuid);

        mTrainAddrAdapter.setList(detailsData.AddrList);
        mTrainAddrAdapter.setCheckList(addCheckList, checkGuid);

        btnConfirm = (Button) findViewById(R.id.btn_confirm);
        btnConfirm.setOnClickListener(this);

        hasMore();
    }

    /**
     * 加载图片
     */
    public void loadView() {
        if (detailsData != null) {
            // mTimeAdapter.clearCheckList();
            // mProVasAdapter.clearCheckList();
            // mTrainAddrAdapter.clearCheckList();
            mTimeAdapter.setList(detailsData.TimesList);
            mProVasAdapter.setList(detailsData.VasList);
            mTrainAddrAdapter.setList(detailsData.AddrList);
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.layout_more:
                intent = new Intent(this, ChoiceTimeMoreActivity.class);
                intent.putExtra("detailsData", detailsData);
                intent.putExtra("ProType", ProType);
                intent.putExtra("OrderId", OrderId);
                intent.putExtra("TestSubId", TestSubId);
                startActivityForResult(intent, REQUEST_CODE_CHOICE_PRODATA);
                break;
            case R.id.btn_confirm:
                if (mTimeAdapter.getCheckList().size() != 0) {
                    intent = new Intent();
                    intent.putExtra("timeList", (Serializable) mTimeAdapter.getCheckList());
                    intent.putExtra("vasList", (Serializable) mProVasAdapter.getCheckList());
                    intent.putExtra("addList", (Serializable) mTrainAddrAdapter.getCheckList());
                    intent.putExtra("detailsData", detailsData);
                    intent.putExtra("Guid", checkGuid);
                    intent.putExtra("ProData", detailsData.ProDate);
                    intent.putExtra("mProVasNum", mProVasAdapter.getList().size());
                    setResult(RESULT_OK, intent);
                }
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    public void onTimeChangerListener(List<Boolean> checkList, String timeGuid) {
        mProVasAdapter.setGuid(timeGuid);
        mTrainAddrAdapter.setGuid(timeGuid);
        checkGuid = timeGuid;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_CHOICE_PRODATA:
                    newProData = data.getStringExtra("ProDate");
                    if (newProData != null) {
                        if (!detailsData.ProDate.equals(newProData)) {
                            tvProData.setText("培训日期：" + newProData);
                            CMD_getProDetail();
                        }
                    }
                    break;

                default:
                    break;
            }
        }
    }

    /**
     * 获取产品详情 时间段选择后重新获取产品详情
     */
    public void CMD_getProDetail() {
        if (!TextUtils.isEmpty(CoachUserUUID)) {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("ProType", ProType);
                jsonObject.put("CoachUserUUID", CoachUserUUID);
                jsonObject.put("ProDate", newProData);
                jsonObject.put("DateType", DateType);
                jsonObject.put("OrderId", OrderId);
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            showWaitDialog();
            DataLoadTask.getInstance().loadData(CommandCodeTS.CMD_ONEDAYONECOACHTRAINPRO_STU,
                    jsonObject.toString(), this);

        }
    }

    /**
     * 判断时间段 增值服务 接送地址 是否存在可选项
     */
    public void hasMore() {
        if (detailsData.TimesList == null || detailsData.TimesList.size() < 1) {
            tvTimeState.setVisibility(View.VISIBLE);
            mGridTime.setVisibility(View.GONE);
        } else {
            tvTimeState.setVisibility(View.GONE);
            mGridTime.setVisibility(View.VISIBLE);
        }
        if (detailsData.VasList == null || detailsData.VasList.size() < 1) {
            tvServiceState.setVisibility(View.VISIBLE);
            mGridVas.setVisibility(View.GONE);
        } else {
            tvServiceState.setVisibility(View.GONE);
            mGridVas.setVisibility(View.VISIBLE);
        }
        if (detailsData.AddrList == null || detailsData.AddrList.size() < 1) {
            tvAddressState.setVisibility(View.VISIBLE);
            mListTranferAddr.setVisibility(View.GONE);
        } else {
            tvAddressState.setVisibility(View.GONE);
            mListTranferAddr.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 解析产品详情
     *
     * @param result
     */
    public void parseProDetail(String result) {
        detailsData = JSON.parseObject(result, TrainProDetailData.class);
        if (detailsData != null) {
            loadView();
        }
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
