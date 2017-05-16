package com.bofsoft.laio.customerservice.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.bofsoft.laio.customerservice.DataClass.Order.ProAddrData;
import com.bofsoft.laio.customerservice.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 培训产品详情地址列表
 *
 * @author admin
 */
public class TrainDetailsAddrAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private List<ProAddrData> mList;
    private List<Boolean> checkList = new ArrayList<Boolean>();
    public String checkAddrGUID = "";

    public TrainDetailsAddrAdapter(Context context) {
        this.mContext = context;
        mInflater = LayoutInflater.from(context);
    }

    /**
     * 设置可以选择的Guid
     *
     * @param vasGuid
     */
    public void setGuid(String addrGuid) {
        if (addrGuid.equals("") || !addrGuid.equals(checkAddrGUID)) {
            checkAddrGUID = addrGuid;
            setDefaultCheck();
            notifyDataSetChanged();
        }
    }

    public List<ProAddrData> getList() {
        return mList;
    }

    public void setList(List<ProAddrData> list) {
        this.mList = list;
        setDefaultCheck();
        notifyDataSetChanged();
    }

    /**
     * 设置选中状态及选中的guid
     *
     * @param list
     * @param checkGuid
     */
    public void setCheckList(List<Boolean> list, String checkGuid) {
        boolean isChange = false;
        if (checkGuid.equals("") || !checkGuid.equals(checkAddrGUID)) {
            checkAddrGUID = checkGuid;
            setDefaultCheck();
            isChange = true;
        }
        if (mList != null && list.size() <= mList.size()) {
            this.checkList = list;
            isChange = true;
        }
        if (isChange) {
            notifyDataSetChanged();
        }
    }

    /**
     * 获取列表的选中状态
     *
     * @return
     */
    public List<Boolean> getCheckList() {
        return this.checkList;
    }

    /**
     * 获取选中的数据
     *
     * @return
     */
    public List<ProAddrData> getCheckListData() {
        List<ProAddrData> checkListData = new ArrayList<ProAddrData>();
        if (mList != null && checkList != null) {
            for (int i = 0; i < mList.size(); i++) {
                if (checkList.get(i)) {
                    checkListData.add(mList.get(i));
                }
            }
        }
        return checkListData;
    }

    public void clearCheckList() {
        checkAddrGUID = "";
        setDefaultCheck();
        notifyDataSetChanged();
    }

    /**
     * 设置默认的选中状态
     */
    private void setDefaultCheck() {
        if (mList != null && mList.size() > 0) {
            checkList.clear();
            for (int i = 0; i < mList.size(); i++) {
                checkList.add(i, false);
            }
        }
    }

    /**
     * 设置
     *
     * @param positon
     */
    private void setCheckItem(int positon) {
        if (mList != null && mList.size() > 0) {
            for (int i = 0; i < mList.size(); i++) {
                checkList.set(i, false);
            }
            checkList.set(positon, true);
        }
    }

    @Override
    public int getCount() {
        if (mList == null) {
            return 0;
        }
        return mList.size();
    }

    @Override
    public ProAddrData getItem(int position) {
        if (mList == null) {
            return null;
        }
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    ViewHolder holder = null;

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.activity_text_checkbox_list_item, null);
            holder.chkBox = (CheckBox) convertView.findViewById(R.id.CheckState);
            holder.textName = (TextView) convertView.findViewById(R.id.txt_Name);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final ProAddrData data = mList.get(position);
        holder.textName.setText(data.Addr);
        holder.chkBox.setFocusable(false);
        holder.chkBox.setClickable(false);

        if (checkList.size() != 0) {
            holder.chkBox.setChecked(checkList.get(position));
        }

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkAddrGUID == null || checkAddrGUID.equals("")) {
                    Toast.makeText(mContext, "请选择培训时间", Toast.LENGTH_SHORT).show();
                } else {
                    if (data.GUID.equalsIgnoreCase(checkAddrGUID)) {
                        setCheckItem(position);
                        notifyDataSetChanged();
                    } else {
                        Toast.makeText(mContext, "只能选择所选时间段对应的地址", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });

        return convertView;
    }

    class ViewHolder {
        CheckBox chkBox;
        TextView textName;
    }

}
