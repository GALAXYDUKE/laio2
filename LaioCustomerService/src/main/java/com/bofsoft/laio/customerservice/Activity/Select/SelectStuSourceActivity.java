package com.bofsoft.laio.customerservice.Activity.Select;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.bofsoft.laio.customerservice.Activity.BaseVehicleActivity;
import com.bofsoft.laio.customerservice.Adapter.StuSourceAdapter;
import com.bofsoft.laio.customerservice.Config.Config;
import com.bofsoft.laio.customerservice.Config.ConfigallCostomerService;
import com.bofsoft.laio.customerservice.DataClass.db.StuSourceData;
import com.bofsoft.laio.customerservice.R;
import com.bofsoft.sdk.widget.base.Event;

import java.util.ArrayList;
import java.util.List;

/**
 * 学员来源
 *
 * @author admin
 */
public class SelectStuSourceActivity extends BaseVehicleActivity {

    private ListView mListTestSub;
    private List<StuSourceData> list;
    private StuSourceAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, true);
        setContentView(R.layout.activity_text_single_list);
        initView();
        getList();
    }

    public void initView() {

        mListTestSub = (ListView) findViewById(R.id.list_text_single);
        mAdapter = new StuSourceAdapter(this);
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
        list = new ArrayList<StuSourceData>();
        StuSourceData data1 = new StuSourceData();
        data1.Id = 0;
        data1.MC = "本地";
        data1.DM = "A";

        StuSourceData data2 = new StuSourceData();
        data2.Id = 1;
        data2.MC = "外地";
        data2.DM = "B";

        list.add(data1);
        list.add(data2);

        mAdapter.setList(list);
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
