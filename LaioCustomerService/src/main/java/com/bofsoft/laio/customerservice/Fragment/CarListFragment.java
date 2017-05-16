package com.bofsoft.laio.customerservice.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.bofsoft.laio.customerservice.Adapter.MyItemRecyclerViewAdapter;
import com.bofsoft.laio.customerservice.DataClass.GPSInfoData;
import com.bofsoft.laio.customerservice.DataClass.GPSInfoList;
import com.bofsoft.laio.customerservice.R;
import com.bofsoft.laio.customerservice.Widget.LoadMoreRecyclerView;
import com.bofsoft.laio.tcp.IResponseListener;
import com.bofsoft.sdk.widget.base.BaseFragment;
import com.bofsoft.sdk.widget.base.Event;

/**
 * Created by szw on 2017/2/20.
 */

public class CarListFragment extends BaseFragment implements View.OnClickListener, IResponseListener {

    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;
    private MyItemRecyclerViewAdapter myItemRecyclerViewAdapter;
    private LoadMoreRecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private EditText et_carlist_search;
    private int page = 0;
    private final int sum = 10;//每页10条
    private GPSInfoList tempGpsInfoList;
//    private Handler handler = new Handler();

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public CarListFragment() {
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, true);
        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
        tempGpsInfoList = new GPSInfoList();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        tempGpsInfoList.InfoList.addAll(HomeFragment.carListData.InfoList);
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);
        et_carlist_search = (EditText) view.findViewById(R.id.et_carlist_search);
        set_eSearch_TextChanged();
        recyclerView = (LoadMoreRecyclerView) view.findViewById(R.id.list);
        recyclerView.setHasFixedSize(true);

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                tempGpsInfoList.InfoList.clear();
                tempGpsInfoList.InfoList.addAll(HomeFragment.carListData.InfoList);
                page = 0;
                myItemRecyclerViewAdapter.setData(tempGpsInfoList.InfoList);
                swipeRefreshLayout.setRefreshing(false);
                recyclerView.setAutoLoadMoreEnable(hasMore(page, tempGpsInfoList.InfoList.size()));
                myItemRecyclerViewAdapter.notifyDataSetChanged();
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        myItemRecyclerViewAdapter = new MyItemRecyclerViewAdapter(tempGpsInfoList.InfoList);
        recyclerView.setAdapter(myItemRecyclerViewAdapter);
        recyclerView.setAutoLoadMoreEnable(hasMore(page, tempGpsInfoList.InfoList.size()));
        recyclerView.setLoadMoreListener(new LoadMoreRecyclerView.LoadMoreListener() {
            @Override
            public void onLoadMore() {
                recyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        page++;
                        myItemRecyclerViewAdapter.addDatas(tempGpsInfoList.InfoList, page);
                        swipeRefreshLayout.setRefreshing(false);
                        recyclerView.notifyMoreFinish(hasMore(page, tempGpsInfoList.InfoList.size()));
                    }
                }, 1000);
            }
        });
        myItemRecyclerViewAdapter.setOnItemClickListener(new MyItemRecyclerViewAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, GPSInfoData data) {
                //整个item的点击事件
            }
        });
        myItemRecyclerViewAdapter.notifyDataSetChanged();

        return view;
    }

    private boolean hasMore(int page, int size) {
//        if ((page+1)*sum<size){
//            return true;
//        }
        //因车辆列表已经全部拿到，所以一次加载完成，不需要加载更多
        return false;
    }

    private void set_eSearch_TextChanged() {
        et_carlist_search.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                // TODO Auto-generated method stub
                //handler.post(eChanged);
                new Handler(Looper.getMainLooper()).post(eChanged);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
                /**这是文本框改变之后 会执行的动作
                 * 因为我们要做的就是，在文本框改变的同时，我们的listview的数据也进行相应的变动，并且如一的显示在界面上。
                 * 所以这里我们就需要加上数据的修改的动作了。
                 */
                if (s.length() == 0) {
//                    ivDeleteText.setVisibility(View.GONE);//当文本框为空时，则叉叉消失
                } else {
//                    ivDeleteText.setVisibility(View.VISIBLE);//当文本框不为空时，出现叉叉

                }
            }
        });

    }

    Runnable eChanged = new Runnable() {

        @Override
        public void run() {
            // TODO Auto-generated method stub
            String data = et_carlist_search.getText().toString();
            changeData(data);
        }
    };

    private void changeData(String s) {
        tempGpsInfoList.InfoList.clear();
        if (s.length() == 0) {
            tempGpsInfoList.InfoList.addAll(HomeFragment.carListData.InfoList);
        } else {
            for (int i = 0; i < HomeFragment.carListData.InfoList.size(); i++) {
                if (HomeFragment.carListData.InfoList.get(i).License.toUpperCase().contains(s.toUpperCase())) {
                    tempGpsInfoList.InfoList.add(HomeFragment.carListData.InfoList.get(i));
                }
            }
        }
        swipeRefreshLayout.setRefreshing(false);
        page = 0;
        myItemRecyclerViewAdapter.setData(tempGpsInfoList.InfoList);
        recyclerView.setAutoLoadMoreEnable(hasMore(page, tempGpsInfoList.InfoList.size()));
        recyclerView.getAdapter().notifyDataSetChanged();
//        myItemRecyclerViewAdapter.notifyDataSetChanged();
    }

//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnListFragmentInteractionListener) {
//            mListener = (OnListFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnListFragmentInteractionListener");
//        }
//    }

    public void getTempGpsInfoList(GPSInfoList data) {
        tempGpsInfoList = data;
    }

    @Override
    public void onDetach() {
        super.onDetach();
//        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
//    public interface OnListFragmentInteractionListener {
//        // TODO: Update argument type and name
//        void onListFragmentInteraction(DummyItem item);
//    }
    @Override
    public void onClick(View v) {

    }

    @Override
    public void messageBack(int code, String result) {

    }

    @Override
    public void messageBack(int code, int lenght, int tcplenght) {

    }

    @Override
    public void messageBackFailed(int errorCode, String error) {

    }

    @Override
    protected void setTitleFunc() {
        setTitle("车辆列表");
    }

    @Override
    protected void setActionBarButtonFunc() {

    }

    @Override
    protected void actionBarButtonCallBack(int id, View v, Event e) {

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        //改fragment隐藏时true 显示时false
    }
}
