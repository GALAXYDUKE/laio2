package com.bofsoft.laio.customerservice.Activity.Select;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.bofsoft.laio.customerservice.Activity.BaseVehicleActivity;
import com.bofsoft.laio.customerservice.Adapter.EnrollReasonAdapter;
import com.bofsoft.laio.customerservice.Config.Config;
import com.bofsoft.laio.customerservice.Config.ConfigallCostomerService;
import com.bofsoft.laio.customerservice.DataClass.db.EnrollReasonData;
import com.bofsoft.laio.customerservice.R;
import com.bofsoft.sdk.widget.base.Event;

import java.util.ArrayList;
import java.util.List;

/**
 * 报考原因
 *
 * @author admin
 */
public class SelectEnrollReasonActivity extends BaseVehicleActivity {

    private ListView mListTestSub;
    private List<EnrollReasonData> list;
    private EnrollReasonAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, true);
        setContentView(R.layout.activity_text_single_list);
        initView();
        getList();
    }

    public void initView() {

        mListTestSub = (ListView) findViewById(R.id.list_text_single);
        mAdapter = new EnrollReasonAdapter(this);
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
        // list =
        // PublicDBManager.getIntance(this).queryDataList(EnrollReasonData.class,TableManager.Type_Enroll_Reson,
        // null, null, null,null,null);
        list = new ArrayList<EnrollReasonData>();
        // Reason Integer 申请类型， 0初学， 1增驾
        EnrollReasonData data1 = new EnrollReasonData();
        data1.Id = 0;
        data1.MC = "初学";

        EnrollReasonData data2 = new EnrollReasonData();
        data2.Id = 1;
        data2.MC = "增驾";

        list.add(data1);
        list.add(data2);
        mAdapter.setmList(list);
    }

    @Override
    protected void setTitleFunc() {
        setTitle("报考原因");
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
