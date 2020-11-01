package com.rs.doorbell.FragmentUtil;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rs.doorbell.AdapterUtil.OrderAdapter;
import com.rs.doorbell.ConstantUtil.Constant;
import com.rs.doorbell.InterfaceUtil.ConnectionCallback;
import com.rs.doorbell.ManagementUtil.Management;
import com.rs.doorbell.ObjectUtil.DataObject;
import com.rs.doorbell.ObjectUtil.EmptyObject;
import com.rs.doorbell.ObjectUtil.PrefObject;
import com.rs.doorbell.ObjectUtil.ProgressObject;
import com.rs.doorbell.ObjectUtil.RequestObject;
import com.rs.doorbell.R;
import com.rs.doorbell.Utility.Utility;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class OrderHistory extends Fragment implements ConnectionCallback,  SwipeRefreshLayout.OnRefreshListener {
    private String TAG = CurrentOrder.class.getSimpleName();
    private LinearLayoutManager layoutManager;
    private RecyclerView recyclerViewCurrent;
    private OrderAdapter orderAdapter;
    private Management management;
    private ArrayList<Object> objectArrayList = new ArrayList<>();
    private PrefObject prefObject;
    private DatabaseReference rootReference;
    private DatabaseReference riderRef;
    private SwipeRefreshLayout pullToRefresh;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_current_order, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initUI(view); //Initialize UI
    }


    /**
     * <p>It initialize the UI</p>
     */
    private void initUI(View view) {

        management = new Management(getActivity());
        prefObject = management.getPreferences(new PrefObject()
                .setRetrieveUserCredential(true));

        pullToRefresh = view.findViewById(R.id.pullToRefresh);

        layoutManager = new LinearLayoutManager(getActivity(), 1, false);
        recyclerViewCurrent = view.findViewById(R.id.recycler_view_current);
        recyclerViewCurrent.setLayoutManager(layoutManager);

        rootReference = FirebaseDatabase.getInstance("https://ozzy-271015.firebaseio.com/").getReference();

        orderAdapter = new OrderAdapter(getActivity(), objectArrayList);
        recyclerViewCurrent.setAdapter(orderAdapter);

        pullToRefresh.setOnRefreshListener(this);

    }


    /**
     * <p>It is used to send request to Server</p>
     */
    private void sendRequestToServer() {

        management.sendRequestToServer(new RequestObject()
                .setContext(getActivity())
                .setJson(getJson(prefObject.getUserId()))
                .setConnection(Constant.CONNECTION.RIDER_HISTORY_ORDER)
                .setConnectionType(Constant.CONNECTION_TYPE.UI)
                .setConnectionCallback(this));


    }


    /**
     * <p> It is used to convert Object
     * into Json</p>
     *
     * @param
     * @return
     */
    private String getJson(String rider_id) {
        String json = null;

        // 1. build jsonObject
        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.accumulate("functionality", "rider_history_order");
            jsonObject.accumulate("rider_id", rider_id);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        // 2. convert JSONObject to JSON to String
        json = jsonObject.toString();
        Utility.Logger(TAG, "JSON " + json);

        return json;
    }


    @Override
    public void onSuccess(Object data, RequestObject requestObject) {
        if (data != null && requestObject != null) {

            DataObject dtObject = (DataObject) data;
            objectArrayList.clear();
            objectArrayList.addAll(dtObject.getObjectArrayList());
            orderAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onError(String data, RequestObject requestObject) {
        objectArrayList.clear();
        objectArrayList.add(new EmptyObject()
                .setTitle(Utility.getStringFromRes(getActivity(), R.string.no_order))
                .setDescription(Utility.getStringFromRes(getActivity(), R.string.no_order_history_tagline))
                .setPlaceHolderIcon(R.drawable.em_no_shopping));
        orderAdapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        super.onResume();

        objectArrayList.clear();
        objectArrayList.add(new ProgressObject());
        orderAdapter.notifyDataSetChanged();

        sendRequestToServer();  //Send Request to Server

    }

    @Override
    public void onRefresh() {

        objectArrayList.clear();
        objectArrayList.add(new ProgressObject());
        orderAdapter.notifyDataSetChanged();

        sendRequestToServer();
        pullToRefresh.setRefreshing(false);
    }

}