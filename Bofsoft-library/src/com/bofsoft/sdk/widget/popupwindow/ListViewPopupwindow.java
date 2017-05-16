package com.bofsoft.sdk.widget.popupwindow;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bofsoft.sdk.R;

import java.util.ArrayList;
import java.util.List;

@SuppressLint({"NewApi", "InflateParams"})
public class ListViewPopupwindow<T> {

    @SuppressWarnings("unused")
    private Activity act;
    private NsPopupwindow disPopwindow;
    @SuppressWarnings("unused")
    private onChangeItemListener listener;

    private Adapter adapter;
    @SuppressWarnings("unused")
    private List<T> list = new ArrayList<T>();
    private ListView lv;
    private ListViewAdapter listviewAdapter;


    @SuppressWarnings({"unchecked", "rawtypes", "hiding"})
    public <T> void show(Activity act, final List list, Adapter adapter, final onChangeItemListener listener) {
        this.act = act;
        this.list = list;
        this.adapter = adapter;
        this.listener = listener;
        if (disPopwindow == null) {
            int gap = act.getResources().getDimensionPixelOffset(R.dimen.DP_20);

            View v = act.getLayoutInflater().inflate(R.layout.listview_popupwindow, null);
            lv = (ListView) v.findViewById(R.id.list);

            listviewAdapter = new ListViewAdapter();
            lv.setAdapter(listviewAdapter);

            TextView btn = (TextView) v.findViewById(R.id.btn);
            btn.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    close();
                }
            });

            RelativeLayout.LayoutParams lp =
                    new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                            RelativeLayout.LayoutParams.WRAP_CONTENT);
            lp.setMargins(gap, 0, gap, 0);
            lp.addRule(RelativeLayout.CENTER_IN_PARENT);
            disPopwindow = NsPopupwindow.getInstence(act, v, lp);
        }
        lv.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (listener != null)
                    listener.onClick(view, position, list.get(position));
            }
        });
        this.list = list;
        listviewAdapter.setData(list);
        listviewAdapter.notifyDataSetChanged();
        disPopwindow.show();
    }

    public interface onChangeItemListener {
        void onClick(View v, int position, Object data);
    }

    public interface Adapter {
        View getView(View v, int position, Object data, ViewGroup parent);
    }

    public void close() {
        if (disPopwindow != null)
            disPopwindow.close();
    }

    class ListViewAdapter extends BaseAdapter {

        private List<T> list = new ArrayList<T>();

        public void setData(List<T> list) {
            this.list = list;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (adapter != null) {
                convertView = adapter.getView(convertView, position, getItem(position), parent);
            }
            return convertView;
        }

    }

}
