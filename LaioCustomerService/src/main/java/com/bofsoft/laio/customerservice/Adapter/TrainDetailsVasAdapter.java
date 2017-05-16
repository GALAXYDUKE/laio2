package com.bofsoft.laio.customerservice.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.bofsoft.laio.customerservice.DataClass.Order.ProVasData;
import com.bofsoft.laio.customerservice.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 培训产品增值服务
 *
 * @author admin
 */
public class TrainDetailsVasAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private List<ProVasData> mList;
    private List<Boolean> checkList = new ArrayList<Boolean>();
    public String checkVasGUID = "";

    public TrainDetailsVasAdapter(Context context) {
        this.mContext = context;
        mInflater = LayoutInflater.from(context);
    }

    /**
     * 设置可以选择的Guid
     *
     * @param vasGuid
     */
    public void setGuid(String vasGuid) {
        if (vasGuid.equals("") || !vasGuid.equals(checkVasGUID)) {
            checkVasGUID = vasGuid;
            setDefaultCheck();
            notifyDataSetChanged();
        }
    }

    public List<ProVasData> getList() {
        return mList;
    }

    public void setList(List<ProVasData> list) {
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
        if (checkGuid.equals("") || !checkGuid.equals(checkVasGUID)) {
            checkVasGUID = checkGuid;
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
    public List<ProVasData> getCheckListData() {
        List<ProVasData> checkListData = new ArrayList<ProVasData>();
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
        checkVasGUID = "";
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

    @Override
    public int getCount() {
        if (mList == null) {
            return 0;
        }
        return mList.size();
    }

    @Override
    public ProVasData getItem(int position) {
        if (mList == null) {
            return null;
        }
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.activity_provas_list_item, null);
            holder.textName = (TextView) convertView.findViewById(R.id.txt_name_Vas);
            holder.textPrice = (TextView) convertView.findViewById(R.id.txtPrice_Vas);
            holder.textContent = (TextView) convertView.findViewById(R.id.txt_ServerContent_Vas);
            holder.checkBox = (CheckBox) convertView.findViewById(R.id.checkBox_Vas);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final ProVasData data = mList.get(position);
        holder.textName.setText(data.Name);
        String priceString = mContext.getString(R.string.account, data.Price);
        holder.textPrice.setText(priceString);
        holder.textContent.setText(data.Content);
        holder.checkBox.setChecked(checkList.get(position));
        holder.checkBox.setClickable(false);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkVasGUID == null || checkVasGUID.equals("")) {
                    Toast.makeText(mContext, "请选择培训时间", Toast.LENGTH_SHORT).show();
                } else {
                    if (!checkList.get(position)) {
                        if (data.GUID.equalsIgnoreCase(checkVasGUID)) {
                            checkList.set(position, true);
                            notifyDataSetChanged();
                        } else {
                            Toast.makeText(mContext, "只能选择所选时间段对应的增值服务内容", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        checkList.set(position, false);
                        notifyDataSetChanged();
                    }
                }
            }
        });
        return convertView;
    }

    class ViewHolder {
        TextView textName;
        TextView textPrice;
        TextView textContent;
        CheckBox checkBox;
    }

}
