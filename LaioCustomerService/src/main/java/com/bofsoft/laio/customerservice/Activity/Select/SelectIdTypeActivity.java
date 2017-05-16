package com.bofsoft.laio.customerservice.Activity.Select;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.bofsoft.laio.customerservice.Activity.BaseVehicleActivity;
import com.bofsoft.laio.customerservice.Adapter.IdTypeAdapter;
import com.bofsoft.laio.customerservice.Config.Config;
import com.bofsoft.laio.customerservice.Config.ConfigallCostomerService;
import com.bofsoft.laio.customerservice.DataClass.db.IdTypeData;
import com.bofsoft.laio.customerservice.Database.DBCallBack;
import com.bofsoft.laio.customerservice.Database.PublicDBManager;
import com.bofsoft.laio.customerservice.Database.TableManager;
import com.bofsoft.laio.customerservice.R;
import com.bofsoft.sdk.widget.base.Event;

import java.util.List;

/**
 * 身份证明类型
 *
 * @author admin
 */
public class SelectIdTypeActivity extends BaseVehicleActivity {

    private ListView mList;
    private List<IdTypeData> list;
    private IdTypeAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, true);
        setContentView(R.layout.activity_text_single_list);
        initView();
        getList();
    }

    public void initView() {

        mList = (ListView) findViewById(R.id.list_text_single);
        mAdapter = new IdTypeAdapter(this);
        mList.setAdapter(mAdapter);
        mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
        // DBAsyncOperation.execute(new DBCallBack<Object>() {
        // @Override
        // public Object doThingInBack() {
        // list =
        // PublicDBManager.getIntance(SelectIdTypeActivity.this).queryDataList(IdTypeData.class,
        // TableManager.Type_IdType, null, null, null, null, null);
        // return null;
        // }
        //
        // @Override
        // public void onCallBack(Object result) {
        // mAdapter.setmList(list);
        // }
        // });

        PublicDBManager.getInstance(this).queryList(IdTypeData.class, TableManager.Laio_IdType,
                new DBCallBack<IdTypeData>() {
                    @Override
                    public void onCallBackList(List<IdTypeData> list) {
                        mAdapter.setmList(list);
                    }
                });

    }

    @Override
    protected void setTitleFunc() {
        setTitle("身份证类型");
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
