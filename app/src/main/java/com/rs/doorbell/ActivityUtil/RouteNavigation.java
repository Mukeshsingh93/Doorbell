package com.rs.doorbell.ActivityUtil;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rs.doorbell.ConstantUtil.Constant;
import com.rs.doorbell.ManagementUtil.Management;
import com.rs.doorbell.ObjectUtil.ChattingObject;
import com.rs.doorbell.ObjectUtil.DataObject;
import com.rs.doorbell.ObjectUtil.PrefObject;
import com.rs.doorbell.ObjectUtil.RequestObject;
import com.rs.doorbell.ObjectUtil.RiderObject;
import com.rs.doorbell.ObjectUtil.TimeParameter;
import com.rs.doorbell.ObjectUtil.UserObject;
import com.rs.doorbell.R;
import com.rs.doorbell.Utility.Utility;
import com.mapbox.api.directions.v5.DirectionsCriteria;
import com.mapbox.api.directions.v5.models.DirectionsResponse;
import com.mapbox.api.directions.v5.models.DirectionsRoute;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.services.android.navigation.ui.v5.NavigationView;
import com.mapbox.services.android.navigation.ui.v5.NavigationViewOptions;
import com.mapbox.services.android.navigation.ui.v5.OnNavigationReadyCallback;
import com.mapbox.services.android.navigation.ui.v5.listeners.NavigationListener;
import com.mapbox.services.android.navigation.v5.navigation.MapboxNavigation;
import com.mapbox.services.android.navigation.v5.navigation.NavigationRoute;
import com.mapbox.services.android.navigation.v5.routeprogress.ProgressChangeListener;
import com.mapbox.services.android.navigation.v5.routeprogress.RouteProgress;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RouteNavigation extends AppCompatActivity implements View.OnClickListener, ProgressChangeListener, OnNavigationReadyCallback, NavigationListener {
    private String TAG = "TARUN";
    private Management management;
    private PrefObject userData;
    private MapboxNavigation mapboxNavigation;
    private NavigationView navigationView;
    protected PowerManager.WakeLock mWakeLock;
    private DatabaseReference rootReference;
    private DatabaseReference userRef;
    private DatabaseReference riderRef;
    private DatabaseReference trackingRef;
    private DatabaseReference chatRef;
    private ValueEventListener userEventListener;
    private ChildEventListener chatChildEventListener;
    private DataObject dataObject;
    private double user_latitude;
    private double user_longitude;
    private Point sourcePoint;
    private Point endPoint;
    private boolean isNavigationCancelledExplicitly = false;
    private TextView txtCounter;
    private int messageCounter = 0;
    private LinearLayout layoutCounter;
    private LinearLayout layoutCall;
    private LinearLayout layoutDone;
    private LinearLayout layoutInformation;
    private RiderObject riderObject;
    private UserObject userObject;
    private int user_id;
    private boolean isTriggerNotification = true;
    public Location running_location;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Utility.changeAppTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_navigation);

        awakeScreen();  //Activate WakeLock
        getIntentData(); //Retrieve Intent Data
        initUI(savedInstanceState); //Initialize UI

    }

    /**
     * <p>It is used to retrieve Intent Data</p>
     */
    private void getIntentData() {
        dataObject = getIntent().getParcelableExtra(Constant.IntentKey.RESTAURANT_DETAIL);
        userObject = getIntent().getParcelableExtra(Constant.IntentKey.USER);
        riderObject = getIntent().getParcelableExtra(Constant.IntentKey.RIDER);
    }


    /**
     * <p>It initialize the UI</p>
     */
    private void initUI(Bundle savedInstanceState) {

        management = new Management(this);
        userData = management.getPreferences(new PrefObject()
                .setRetrieveUserCredential(true));

        mapboxNavigation = new MapboxNavigation(this, Mapbox.getAccessToken());
        navigationView = findViewById(R.id.navigationView);
        navigationView.onCreate(savedInstanceState);

        txtCounter = findViewById(R.id.txt_counter);
        layoutCounter = findViewById(R.id.layout_counter);
        layoutCall = findViewById(R.id.layout_call);
        layoutDone = findViewById(R.id.layout_done);
        layoutInformation = findViewById(R.id.layout_information);

        rootReference = FirebaseDatabase.getInstance("https://ozzy-271015.firebaseio.com/").getReference();
        userRef = rootReference.child(dataObject.getOrder_id()).child("userObject");
        userEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Utility.Logger(TAG, "UserEventListener");
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                UserObject value = dataSnapshot.getValue(UserObject.class);
                user_latitude = Double.parseDouble(dataObject.getUser_lattitude());
                user_longitude = Double.parseDouble(dataObject.getUser_longitude());
//                Utility.Logger(TAG, "Value is: " + value.toString());

                sourcePoint = Point.fromLngLat(dataObject.getRider_longitude(), dataObject.getRider_latitude());
                endPoint = Point.fromLngLat(user_longitude, user_latitude);
                user_id = Integer.parseInt(dataObject.getUser_id());

                CameraPosition initialPosition = new CameraPosition.Builder()
                        .target(new LatLng(sourcePoint.latitude(), sourcePoint.longitude()))
                        .zoom(16)
                        .build();

                navigationView.initialize(RouteNavigation.this, initialPosition);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                // Failed to read value
                Utility.Logger(TAG, "Failed to read value in RiderEvent = " + databaseError.toException());

            }
        };
        userRef.addListenerForSingleValueEvent(userEventListener);

        riderRef = rootReference.child(dataObject.getOrder_id()).child("riderObject");
        trackingRef = rootReference.child(dataObject.getOrder_id()).child("trackingObject");

        chatRef = rootReference.child(dataObject.getOrder_id()).child("userChattingObject");
        chatChildEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                ChattingObject chattingObject = dataSnapshot.getValue(ChattingObject.class);
                if (!chattingObject.read)
                    return;

                if (!chattingObject.from)
                    return;

                messageCounter++;
                txtCounter.setText(String.valueOf(messageCounter));
                layoutCounter.setVisibility(View.VISIBLE);

                Utility.Logger(TAG, "Counter = " + messageCounter);

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        layoutCall.setOnClickListener(this);
        layoutDone.setOnClickListener(this);
        layoutInformation.setOnClickListener(this);

    }


    /**
     * <p>It will make the screen be always on until this Activity gets destroyed</p>
     */
    @SuppressLint("InvalidWakeLockTag")
    private void awakeScreen() {
        final PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        this.mWakeLock = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "My Tag");
        this.mWakeLock.acquire();
    }

    @Override
    public void onStart() {
        super.onStart();
        navigationView.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        navigationView.onResume();

        if (chatRef != null && chatChildEventListener != null) {
            chatRef.addChildEventListener(chatChildEventListener);
            messageCounter = 0;
        }

    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        navigationView.onLowMemory();
    }

    @Override
    public void onBackPressed() {
        // If the navigation view didn't need to do anything, call super
        //super.onBackPressed();
       /* if (!navigationView.onBackPressed()) {
            super.onBackPressed();
        }*/
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        navigationView.onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        navigationView.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public void onPause() {
        super.onPause();
        navigationView.onPause();

        if (chatRef != null && chatChildEventListener != null)
            chatRef.removeEventListener(chatChildEventListener);

    }

    @Override
    public void onStop() {
        super.onStop();
        navigationView.onStop();
    }

    @Override
    protected void onDestroy() {
        this.mWakeLock.release();
        super.onDestroy();
        navigationView.onDestroy();

        if (userRef != null)
            userRef.removeEventListener(userEventListener);


    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if (newConfig.orientation == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {

        }

    }

    @Override
    public void onProgressChange(Location location, RouteProgress routeProgress) {

        // api hit here for update location
        running_location = location;
        String remainingDistance = Utility.getRoundedValue(Utility.convertMeterIntoKm(String.valueOf(routeProgress.distanceRemaining()))) + " km";
        String remainingDuration = Utility.secondIntoTime(new TimeParameter((int) routeProgress.durationRemaining(), true));

        Utility.Logger(TAG, "Remaining Distance = " + remainingDistance + " Remaining Duration = " + remainingDuration);

        trackingRef.child("remaining_duration").setValue(remainingDuration);
        trackingRef.child("remaining_distance").setValue(remainingDistance);
        trackingRef.child("latitude").setValue(location.getLatitude());
        trackingRef.child("longitude").setValue(location.getLongitude());

        if (routeProgress.distanceRemaining() < 30) {

            Utility.Logger(TAG, "Distance is Lower than 10 meter");

            if (isTriggerNotification) {

                management.sendRequestToServer(new RequestObject()
                        .setContext(this)
                        .setJson(getNotificationJson(String.valueOf(user_id)))
                        .setConnection(Constant.CONNECTION.USER_NOTIFICATION)
                        .setConnectionType(Constant.CONNECTION_TYPE.BACKGROUND));

                isTriggerNotification = false;
            }


        }


    }

    @Override
    public void onNavigationReady(boolean isRunning) {

        Utility.Logger(TAG, "Navigation Status = " + isRunning);
        fetchRoute(); //Fetch Route When Navigation Ready

    }

    @Override
    public void onCancelNavigation() {
        showAlertSheet(this);
    }

    @Override
    public void onNavigationFinished() {
        Utility.Logger(TAG, "onNavigationFinished");

        if (isNavigationCancelledExplicitly) {
            return;
        }

        management.sendRequestToServer(new RequestObject()
                .setContext(this)
                .setJson(getJson(dataObject.getOrder_id(), false))
                .setConnection(Constant.CONNECTION.ORDER_STATUS)
                .setConnectionType(Constant.CONNECTION_TYPE.BACKGROUND)
                .setConnectionCallback(null));

        finish();

    }

    @Override
    public void onNavigationRunning() {
        Utility.Logger(TAG, "onNavigationRunning");

        management.sendRequestToServer(new RequestObject()
                .setContext(this)
                .setJson(getJson(dataObject.getOrder_id(), true))
                .setConnection(Constant.CONNECTION.ORDER_STATUS)
                .setConnectionType(Constant.CONNECTION_TYPE.BACKGROUND)
                .setConnectionCallback(null));


    }


    /**
     * <p>It is used to fetch Route from
     * Server</p>
     */
    private void fetchRoute() {

        NavigationRoute.builder(RouteNavigation.this)
                .accessToken(Mapbox.getAccessToken())
                .origin(sourcePoint)
                .destination(endPoint)
                .alternatives(true)
                .profile(DirectionsCriteria.PROFILE_DRIVING)
                .language(Locale.ENGLISH)
                .voiceUnits(DirectionsCriteria.METRIC)
                .build().getRoute(new Callback<DirectionsResponse>() {
            @Override
            public void onResponse(Call<DirectionsResponse> call, Response<DirectionsResponse> response) {

                if (response.isSuccessful()) {
                    DirectionsRoute currentRoute = response.body().routes().get(0);
                    startNavigation(currentRoute);

                } else {
                    Utility.Logger(TAG, "Response = " + response.message());
                }

            }

            @Override
            public void onFailure(Call<DirectionsResponse> call, Throwable t) {
                Utility.Logger(TAG, t.getMessage());
                t.printStackTrace();
            }
        });

    }


    /**
     * <p>It is used to start Navigation</p>
     *
     * @param directionsRoute
     */
    private void startNavigation(DirectionsRoute directionsRoute) {

        riderRef.child("direction_route").setValue(directionsRoute.geometry());
        NavigationViewOptions options = NavigationViewOptions.builder()
                .directionsRoute(directionsRoute)
                .shouldSimulateRoute(false)
                .navigationListener(this)
                .progressChangeListener(this)
                .build();

        navigationView.startNavigation(options);

    }


    /**
     * <p>Used to show bottom sheet dialog for Cart Alert</p>
     */
    private void showAlertSheet(final Context context) {

        View view = getLayoutInflater().inflate(R.layout.cart_alert_sheet_layout, null);

        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
        bottomSheetDialog.setContentView(view);
        bottomSheetDialog.show();

        final TextView txtDone = (TextView) view.findViewById(R.id.txt_done);
        TextView txtCancel = (TextView) view.findViewById(R.id.txt_cancel);

        txtDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (bottomSheetDialog.isShowing())
                    bottomSheetDialog.dismiss();

                isNavigationCancelledExplicitly = true;
                navigationView.stopNavigation();
                finish();

            }
        });

        txtCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (bottomSheetDialog.isShowing())
                    bottomSheetDialog.dismiss();

            }
        });

    }


    /**
     * <p>Used to show bottom sheet dialog for Cart Alert</p>
     */
    private void showOrderCompletionAlertSheet(final Context context) {

        View view = getLayoutInflater().inflate(R.layout.order_alert_sheet_layout, null);

        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
        bottomSheetDialog.setContentView(view);
        bottomSheetDialog.show();

        final TextView txtDone = (TextView) view.findViewById(R.id.txt_done);
        TextView txtCancel = (TextView) view.findViewById(R.id.txt_cancel);

        txtDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (bottomSheetDialog.isShowing())
                    bottomSheetDialog.dismiss();

                isNavigationCancelledExplicitly = false;
                navigationView.stopNavigation();
                finish();

            }
        });

        txtCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (bottomSheetDialog.isShowing())
                    bottomSheetDialog.dismiss();

            }
        });

    }


    /**
     * <p>Used to show bottom sheet dialog for Order Address Information</p>
     */
    private void showOrderBillingSheet(final Context context) {

        View view = getLayoutInflater().inflate(R.layout.billing_information_sheet_layout, null);

        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
        bottomSheetDialog.setContentView(view);
        bottomSheetDialog.show();

        EditText editCustomerName = (EditText) view.findViewById(R.id.edit_customer_name);
        EditText editBuilding = (EditText) view.findViewById(R.id.edit_building);
        EditText editStreetAddress = (EditText) view.findViewById(R.id.edit_street_address);
        EditText editArea = (EditText) view.findViewById(R.id.edit_area);
        EditText editUnit = (EditText) view.findViewById(R.id.edit_unit);
        EditText editComment = (EditText) view.findViewById(R.id.edit_comment);
        EditText editPayment = (EditText) view.findViewById(R.id.edit_payment);
        EditText editType = (EditText) view.findViewById(R.id.edit_type);
        final EditText edit_customer_phone = (EditText) view.findViewById(R.id.edit_customer_phone);

        editCustomerName.setText(dataObject.getCustomer_name());
        editStreetAddress.setText(dataObject.getStreetAddressName());
        editBuilding.setText(dataObject.getBuildingName());
        editArea.setText(dataObject.getAreaName());
        editUnit.setText(dataObject.getUnitName());
        editComment.setText(dataObject.getRiderNote());
        editPayment.setText(dataObject.getOrder_price());
        editType.setText(dataObject.getPaymentType());
        edit_customer_phone.setText(dataObject.getPhoneno());

        edit_customer_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:"+edit_customer_phone.getText().toString()));
                callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(callIntent);
            }
        });

        editCustomerName.setEnabled(false);
        editBuilding.setEnabled(false);
        editStreetAddress.setEnabled(false);
        editArea.setEnabled(false);
        editUnit.setEnabled(false);
        editComment.setEnabled(false);
        editPayment.setEnabled(false);
        editType.setEnabled(false);




    }


    /**
     * <p> It is used to convert Object
     * into Json</p>
     *
     * @param
     * @return
     */
    private String getJson(String order_id, boolean isNavigationStarted) {
        String json = null;

        // 1. build jsonObject
        JSONObject jsonObject = new JSONObject();
        try {

            if (isNavigationStarted){
                jsonObject.accumulate("functionality", "push_order_completed_status");

            }else{
                jsonObject.accumulate("functionality", "push_order_delivered_status");
                jsonObject.accumulate("cur_lat", running_location.getLatitude());
                jsonObject.accumulate("cur_long", running_location.getLongitude());
                jsonObject.accumulate("rider_id", userData.getUserId());
            }


            jsonObject.accumulate("order_id", order_id);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        // 2. convert JSONObject to JSON to String
        json = jsonObject.toString();
        Utility.Logger(TAG, "JSON " + json);

        return json;
    }


    /**
     * <p> It is used to convert Object
     * into Json</p>
     *
     * @param
     * @return
     */
    private String getNotificationJson(String user_id) {
        String json = null;

        // 1. build jsonObject
        JSONObject jsonObject = new JSONObject();
        try {


            jsonObject.accumulate("functionality", "send_user_notification");
            jsonObject.accumulate("user_id", user_id);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        // 2. convert JSONObject to JSON to String
        json = jsonObject.toString();
        Utility.Logger(TAG, "JSON " + json);

        return json;
    }


    @Override
    public void onClick(View v) {
        if (v == layoutCall) {

            messageCounter = 0;
            layoutCounter.setVisibility(View.GONE);

            Intent intent = new Intent(getApplicationContext(), Chatting.class);
            intent.putExtra(Constant.IntentKey.RESTAURANT_DETAIL, dataObject.getOrder_id());
            intent.putExtra(Constant.IntentKey.USER, userObject);
            intent.putExtra(Constant.IntentKey.RIDER, riderObject);
            startActivity(intent);

        }
        if (v == layoutDone) {
            showOrderCompletionAlertSheet(this);
        }
        if (v == layoutInformation) {
            showOrderBillingSheet(this);
        }
    }


}



