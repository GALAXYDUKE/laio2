package com.bofsoft.laio.customerservice.Activity.Select;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.bofsoft.laio.common.CommandCodeTS;
import com.bofsoft.laio.customerservice.Activity.BaseVehicleActivity;
import com.bofsoft.laio.customerservice.Adapter.EnrollCarTypeAdapter;
import com.bofsoft.laio.customerservice.Config.Config;
import com.bofsoft.laio.customerservice.Config.ConfigallCostomerService;
import com.bofsoft.laio.customerservice.DataClass.BaseListData;
import com.bofsoft.laio.customerservice.DataClass.index.EnrollCarTypeData;
import com.bofsoft.laio.customerservice.R;
import com.bofsoft.laio.tcp.DataLoadTask;
import com.bofsoft.sdk.widget.base.Event;

/**
 * 报考车型（服务器获取）
 *
 * @author admin
 */
public class SelectEnrollCarTypeActivity extends BaseVehicleActivity {

    private ListView mListTestSub;
    private EnrollCarTypeAdapter mAdapter;

    @Override
    public void messageBack(int code, String result) {
        switch (code) {
            case CommandCodeTS.CMD_COACH_GETREGCARTYPELIST:
                parseList(result);
                break;
            default:
                super.messageBack(code, result);
                break;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, true);
        setContentView(R.layout.activity_text_single_list);
        initView();
        getList();
    }

    public void initView() {

        mListTestSub = (ListView) findViewById(R.id.list_text_single);
        mAdapter = new EnrollCarTypeAdapter(this);
        mListTestSub.setAdapter(mAdapter);
        mListTestSub.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.putExtra(ConfigallCostomerService.Result_Key, mAdapter.getItem(position));
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    public void getList() {
        showWaitDialog();
        DataLoadTask.getInstance().loadData(CommandCodeTS.CMD_COACH_GETREGCARTYPELIST, null, this);
    }

    public void parseList(String result) {
        closeWaitDialog();
        BaseListData<EnrollCarTypeData> data =
                JSON.parseObject(result, new TypeReference<BaseListData<EnrollCarTypeData>>() {
                });
        mAdapter.setmList(data.info);
    }

    @Override
    protected void setTitleFunc() {
        setTitle("报考车型");
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
