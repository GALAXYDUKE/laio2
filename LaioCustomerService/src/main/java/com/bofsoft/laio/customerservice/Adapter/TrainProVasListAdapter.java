package com.bofsoft.laio.customerservice.Adapter;

import android.content.Context;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.bofsoft.laio.customerservice.Activity.BaseVehicleActivity;
import com.bofsoft.laio.customerservice.DataClass.db.ClassTypeData;
import com.bofsoft.laio.customerservice.DataClass.index.ProductVASData;
import com.bofsoft.laio.customerservice.Database.DBCallBack;
import com.bofsoft.laio.customerservice.Database.PublicDBManager;
import com.bofsoft.laio.customerservice.Database.TableManager;
import com.bofsoft.laio.customerservice.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 培训产品增值服务
 *
 * @author admin
 */
public class TrainProVasListAdapter extends BaseAdapter {
    Context mContext;
    LayoutInflater mInflater;
    List<ProductVASData> list;
    List<ProductVASData> checkState = new ArrayList<ProductVASData>();
    List<ClassTypeData> classTypeList;
    SparseArray<String> classTypeArray = new SparseArray<String>();

    public TrainProVasListAdapter(Context context) {
        this.mContext = context;
        mInflater = LayoutInflater.from(context);

        PublicDBManager.getInstance(mContext).queryList(ClassTypeData.class,
                TableManager.Laio_Class_Type, new DBCallBack<ClassTypeData>() {
                    @Override
                    public void onCallBackList(List<ClassTypeData> list) {
                        initClassTypeArray(list);
                    }
                });
    }

    public void initClassTypeArray(List<ClassTypeData> classList) {
        if (classList != null) {
            for (ClassTypeData data : classList) {
                classTypeArray.put(data.getId(), data.getName());
            }
        }
    }

    public List<ProductVASData> getList() {
        return list;
    }

    public void setList(List<ProductVASData> list) {
        this.checkState.clear();
        this.list = list;
        notifyDataSetChanged();
    }

    public void setClassTypeList(List<ClassTypeData> classTypeList) {
        this.classTypeList = classTypeList;
    }

    /**
     * 清除选择项
     */
    public void clearCheckState() {
        checkState.clear();
    }

    public List<ProductVASData> getCheckState() {
        return this.checkState;
    }

    @Override
    public int getCount() {
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    @Override
    public ProductVASData getItem(int position) {
        if (list == null) {
            return null;
        }
        return list.get(position);
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
            holder.textSerContent = (TextView) convertView.findViewById(R.id.txt_ServerContent_Vas);
            holder.checkBox = (CheckBox) convertView.findViewById(R.id.checkBox_Vas);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final ProductVASData data = list.get(position);
        String className = classTypeArray.get(data.getClassType());
        if (!TextUtils.isEmpty(className)) {
            holder.textName.setText(data.getName() + "(" + className + ")");
        } else {
            holder.textName.setText(data.getName());
        }

        holder.textPrice.setText(mContext.getString(R.string.account, data.getPrice()));
        holder.textSerContent.setText(data.getContent());
        holder.checkBox.setFocusable(false);
        holder.checkBox.setChecked(checkState.contains(data));
        holder.checkBox.setClickable(false);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (classTypeList == null || classTypeList.size() < 1) {
                    ((BaseVehicleActivity) mContext).showToastShortTime("请选择课程类型");
                } else {
                    if (checkState.contains(data)) {
                        checkState.remove(data);
                    } else {
                        checkState.add(data);
                    }
                    notifyDataSetChanged();
                }

            }
        });
        return convertView;
    }

    class ViewHolder {
        TextView textName;
        TextView textPrice;
        TextView textSerContent;
        CheckBox checkBox;
    }

}
