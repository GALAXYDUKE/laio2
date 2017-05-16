package com.bofsoft.laio.customerservice.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.bofsoft.laio.customerservice.DataClass.Order.TrainCheckTimesData;
import com.bofsoft.laio.customerservice.DataClass.Order.TrainProTimesData;
import com.bofsoft.laio.customerservice.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 培训产品时间段
 *
 * @author admin
 */
public class TrainDetailsTimeAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private List<TrainProTimesData> List;
    private List<Boolean> checkList = new ArrayList<Boolean>();
    public String checkTimeGUID = "";
    TrainProTimeChangerListener timeChangerListener;

    public interface TrainProTimeChangerListener {
        /**
         * 选择时间段改变监听
         *
         * @param checkList 选择时间段
         * @param timeGuid  选择时间段的Guid
         */
        void onTimeChangerListener(List<Boolean> checkList, String timeGuid);
    }

    public TrainDetailsTimeAdapter(Context context) {
        this.mContext = context;
        mInflater = LayoutInflater.from(context);
    }

    public List<TrainProTimesData> getList() {
        return List;
    }

    public void setList(List<TrainProTimesData> list) {
        this.List = list;
        setDefaultCheck();
        notifyDataSetChanged();
    }

    public void clear() {
        List.clear();
        clearCheckList();
        notifyDataSetChanged();
    }

    public void setCheckedList(List<Boolean> checkList) {
        if (List != null && checkList.size() <= List.size()) {
            this.checkList = checkList;
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
    public List<TrainProTimesData> getCheckListData() {
        List<TrainProTimesData> checkListData = new ArrayList<TrainProTimesData>();
        if (List != null && checkList != null) {
            int checkCount = checkList.size();
            for (int i = 0; i < List.size() && i < checkCount; i++) {
                if (checkList.get(i)) {
                    checkListData.add(List.get(i));
                }
            }
        }
        Collections.sort(checkListData);
        return checkListData;
    }

    public int getCheckSize() {
        int size = 0;
        for (Boolean data : checkList) {
            if (data) {
                size++;
            }
        }
        return size;
    }

    /**
     * 设置默认的选中状态
     */
    private void setDefaultCheck() {
        if (List != null && List.size() > 0) {
            checkList.clear();
            for (int i = 0; i < List.size(); i++) {
                checkList.add(i, false);
            }
        }
    }

    public String getStartTimeformString(TrainProTimesData timeData) {
        String time = "";
        if (timeData != null) {
            TrainCheckTimesData checkTimeData =
                    new TrainCheckTimesData(timeData.Name, timeData.TimeSpace);
            time = checkTimeData.getModStartTime();
        }
        return time;
    }

    public String getEndTimeformString(TrainProTimesData timeData) {
        String time = "";
        if (timeData != null) {
            TrainCheckTimesData checkTimeData =
                    new TrainCheckTimesData(timeData.Name, timeData.TimeSpace);
            time = checkTimeData.getModEndTime();
        }
        return time;
    }

    public boolean isCanSelect(TrainProTimesData timeData) {
        boolean iscan = false;
        int size = getCheckSize();
        if (size == 0) {
            iscan = true;
        } else {
            List<TrainProTimesData> checklistData = getCheckListData();
            if (getEndTimeformString(timeData).equals(getStartTimeformString(checklistData.get(0)))
                    || getStartTimeformString(timeData).equals(
                    getEndTimeformString(checklistData.get(size - 1)))) {
                if (checkTimeGUID.equals(timeData.GUID)) {
                    iscan = true;
                }
            }
        }
        return iscan;
    }

    public void clearCheckList() {
        checkTimeGUID = "";
        checkList.clear();
        List.clear();
        notifyDataSetChanged();
    }

    public void setTimeChangerListener(TrainProTimeChangerListener timeChangerListener) {
        this.timeChangerListener = timeChangerListener;
    }

    @Override
    public int getCount() {
        if (List == null) {
            return 0;
        }
        return List.size();
    }

    @Override
    public TrainProTimesData getItem(int position) {
        if (List == null) {
            return null;
        }
        return List.get(position);
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
            convertView = mInflater.inflate(R.layout.activity_train_pro_time_list_item, null);
            holder.textName = (TextView) convertView.findViewById(R.id.txt_name_time);
            holder.checkBox = (CheckBox) convertView.findViewById(R.id.checkBox_Time);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final TrainProTimesData data = List.get(position);
        holder.textName.setText(data.Name);

        if (checkList != null) {
            holder.checkBox.setChecked(checkList.get(position));
        }
        holder.checkBox.setFocusable(false);
        holder.checkBox.setClickable(false);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkList.get(position)) {
                    // 已选择
                    List<TrainProTimesData> list = getCheckListData();
                    if (list.get(0) == data || list.get(list.size() - 1) == data) {
                        checkList.set(position, false);
                        if (getCheckSize() == 0) {
                            checkTimeGUID = "";
                        }
                    }
                } else {
                    // 未选择
                    if (isCanSelect(data)) {
                        // 允许选择
                        checkList.set(position, true);
                        checkTimeGUID = data.GUID;
                    } else {
                        // 不允许选择
                        Toast.makeText(mContext, "必须选择连续的时间段", Toast.LENGTH_SHORT).show();
                    }
                }
                notifyDataSetChanged();
                timeChangerListener.onTimeChangerListener(checkList, checkTimeGUID);
            }
        });

        return convertView;
    }

    class ViewHolder {
        TextView textName;
        CheckBox checkBox;
    }

}
