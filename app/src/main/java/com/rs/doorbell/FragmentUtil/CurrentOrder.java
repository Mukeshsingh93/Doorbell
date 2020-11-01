package com.rs.doorbell.FragmentUtil;

import android.content.Intent;
import android.location.Location;
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
import com.rs.doorbell.ActivityUtil.RouteNavigation;
import com.rs.doorbell.AdapterUtil.OrderAdapter;
import com.rs.doorbell.ConstantUtil.Constant;
import com.rs.doorbell.InterfaceUtil.ConnectionCallback;
import com.rs.doorbell.InterfaceUtil.OrderCallback;
import com.rs.doorbell.ManagementUtil.Management;
import com.rs.doorbell.ObjectUtil.DataObject;
import com.rs.doorbell.ObjectUtil.EmptyObject;
import com.rs.doorbell.ObjectUtil.PrefObject;
import com.rs.doorbell.ObjectUtil.ProgressObject;
import com.rs.doorbell.ObjectUtil.RequestObject;
import com.rs.doorbell.ObjectUtil.RiderObject;
import com.rs.doorbell.ObjectUtil.UserObject;
import com.rs.doorbell.R;
import com.rs.doorbell.Utility.Utility;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import io.nlopez.smartlocation.OnLocationUpdatedListener;
import io.nlopez.smartlocation.SmartLocation;
import io.nlopez.smartlocation.location.config.LocationParams;


public class CurrentOrder extends Fragment implements OrderCallback, ConnectionCallback, OnLocationUpdatedListener, SwipeRefreshLayout.OnRefreshListener {
    private String TAG = "TARUN CURRENT";
    private LinearLayoutManager layoutManager;
    private RecyclerView recyclerViewCurrent;
    private OrderAdapter orderAdapter;
    private Management management;
    private ArrayList<Object> objectArrayList = new ArrayList<>();
    private PrefObject prefObject;
    private DatabaseReference rootReference;
    private DatabaseReference riderRef;
    private SmartLocation smartLocation;
    private double rider_longitude;
    private double rider_latitude;
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

        orderAdapter = new OrderAdapter(getActivity(), objectArrayList, this);
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
                .setConnection(Constant.CONNECTION.RIDER_CURRENT_ORDER)
                .setConnectionType(Constant.CONNECTION_TYPE.UI)
                .setConnectionCallback(this));


    }


    private void sendRequestToServerForLocation(String cur_lat,String cur_lng) {

        management.sendRequestToServer(new RequestObject()
                .setContext(getActivity())
                .setJson(getJson(prefObject.getUserId(),cur_lat,cur_lng))
                .setConnection(Constant.CONNECTION.update_rider_location)
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

            jsonObject.accumulate("functionality", "rider_current_order");
            jsonObject.accumulate("rider_id", rider_id);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        // 2. convert JSONObject to JSON to String
        json = jsonObject.toString();
        Utility.Logger(TAG, "JSON " + json);

        return json;
    }

    private String getJson(String rider_id,String cur_lat,String cur_long) {
        String json = null;

        // 1. build jsonObject
        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.accumulate("functionality", "update_rider_location");
            jsonObject.accumulate("cur_lat", cur_lat);
            jsonObject.accumulate("cur_long", cur_long);
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
    public void onOrderClickListener(int position) {
//        DataObject dataObject = (DataObject) objectArrayList.get(position);
//
//        Intent intent = new Intent(getActivity(), RouteAndDirection.class);
//        intent.putExtra(Constant.IntentKey.RESTAURANT_DETAIL, dataObject.setRider_latitude(rider_latitude).setRider_longitude(rider_longitude));
//        startActivity(intent);

    }

    @Override
    public void onOrderAcceptListener(int position) {
        DataObject dataObject = (DataObject) objectArrayList.get(position);

        riderRef = rootReference.child(dataObject.getOrder_id()).child("riderObject");
        riderRef.child("user_id").setValue(Integer.parseInt(prefObject.getUserId()));
        riderRef.child("user_name").setValue(prefObject.getFirstName());
        riderRef.child("user_picture").setValue(prefObject.getPictureUrl());
        riderRef.child("rider_latitude").setValue(rider_latitude);
        riderRef.child("rider_longitude").setValue(rider_longitude);

        dataObject.setRider_latitude(rider_latitude);
        dataObject.setRider_longitude(rider_longitude);

        Intent intent = new Intent(getActivity(), RouteNavigation.class);
        intent.putExtra(Constant.IntentKey.RESTAURANT_DETAIL, dataObject);
        intent.putExtra(Constant.IntentKey.USER, new UserObject().setUser_name(dataObject.getCustomer_name()).setUser_picture(dataObject.getCustomer_picture()).setUser_latitude(Double.parseDouble(dataObject.getObject_latitude())).setUser_longitude(Double.parseDouble(dataObject.getObject_longitude())));
        intent.putExtra(Constant.IntentKey.RIDER, new RiderObject().setUser_picture(prefObject.getPictureUrl()).setRider_latitude(rider_latitude).setRider_longitude(rider_longitude));
        startActivity(intent);


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
                .setDescription(Utility.getStringFromRes(getActivity(), R.string.no_order_tagline))
                .setPlaceHolderIcon(R.drawable.em_no_shopping));
        orderAdapter.notifyDataSetChanged();
    }


    /**
     * <p>It is used to start location & get userObject current location</p>
     */
    private void startLocation() {

        smartLocation = new SmartLocation.Builder(getActivity()).logging(true).build();
        smartLocation.location().config(LocationParams.BEST_EFFORT).oneFix().start(this);

    }


    @Override
    public void onLocationUpdated(Location location) {
        if (location != null) {

            Utility.Logger(TAG, "Location = Latitude (" + location.getLatitude() + "," + location.getLongitude() + ")");
            rider_latitude = location.getLatitude();
            rider_longitude = location.getLongitude();

            sendRequestToServerForLocation(""+rider_latitude,""+rider_longitude);

        }
    }

    @Override
    public void onResume() {
        super.onResume();


        objectArrayList.clear();
        objectArrayList.add(new ProgressObject());
        orderAdapter.notifyDataSetChanged();

        startLocation();
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