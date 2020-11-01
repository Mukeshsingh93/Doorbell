package com.rs.doorbell.ActivityUtil;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.SquareCap;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rs.doorbell.ConstantUtil.Constant;
import com.rs.doorbell.ObjectUtil.DataObject;
import com.rs.doorbell.ObjectUtil.UserObject;
import com.rs.doorbell.R;
import com.rs.doorbell.Utility.Utility;
import com.mapbox.api.directions.v5.DirectionsCriteria;
import com.mapbox.api.directions.v5.MapboxDirections;
import com.mapbox.api.directions.v5.models.DirectionsResponse;
import com.mapbox.api.directions.v5.models.DirectionsRoute;
import com.mapbox.core.constants.Constants;
import com.mapbox.geojson.LineString;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.Mapbox;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.google.android.gms.maps.model.JointType.ROUND;

public class RouteAndDirection extends AppCompatActivity implements OnMapReadyCallback , View.OnClickListener {
    private String TAG = RouteAndDirection.class.getName();
    private MapView mapView;
    private GoogleMap googleMap;
    private DatabaseReference rootReference;
    private DatabaseReference userRef;
    private ValueEventListener userEventListener;
    private DataObject dataObject;
    private TextView txtMenu;
    private ImageView imageBack;
    private Switch switchPush;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_view);

        getIntentData(); //Retrieve Intent Data
        initUI(savedInstanceState); //Initialize UI

    }

    /**
     * <p>It is used to get Intent Data</p>
     */
    private void getIntentData() {
        dataObject = getIntent().getParcelableExtra(Constant.IntentKey.RESTAURANT_DETAIL);
    }


    /**
     * <p>It initialize the UI</p>
     */

    private void initUI(Bundle savedInstanceState) {

        txtMenu = findViewById(R.id.txt_menu);
        txtMenu.setText(Utility.getStringFromRes(this, R.string.route_direction));

        switchPush = (Switch) findViewById(R.id.switch_push);
        switchPush.setVisibility(View.GONE);

        imageBack = findViewById(R.id.image_back);
        imageBack.setVisibility(View.VISIBLE);
        imageBack.setImageResource(R.drawable.ic_back);

        mapView = (MapView) findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);

        mapView.onResume(); // needed to get the map to display immediately

        try {
            MapsInitializer.initialize(this);
        } catch (Exception e) {
            e.printStackTrace();
        }


        rootReference = FirebaseDatabase.getInstance("https://ozzy-271015.firebaseio.com/").getReference();

        userRef = rootReference.child(dataObject.getOrder_id()).child("userObject");
        userEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Utility.Logger(TAG, "UserEventListener");
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                UserObject value = dataSnapshot.getValue(UserObject.class);

                if (googleMap != null) {

                    googleMap.addMarker(new MarkerOptions().position(new LatLng(value.getUser_latitude(), value.getUser_longitude())).
                            flat(true).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_current_pointer)));

                    //Find Directions & Route between Source
                    // along with Directions

                    findRoute(dataObject.getRider_latitude(), dataObject.getRider_longitude(), value.getUser_latitude(), value.getUser_longitude());

                }


                Utility.Logger(TAG, "Value is: " + value.toString());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                // Failed to read value
                Utility.Logger(TAG, "Failed to read value in RiderEvent = " + databaseError.toException());

            }
        };
        userRef.addListenerForSingleValueEvent(userEventListener);

        mapView.getMapAsync(this);

        imageBack.setOnClickListener(this);


    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

        this.googleMap = googleMap;


    }


    /**
     * <p>It is used to find Route & Directions
     * between two locations</p>
     *
     * @param start_latitude
     * @param start_longitude
     * @param end_latitude
     * @param end_longitude
     */
    private void findRoute(final double start_latitude, final double start_longitude, double end_latitude, double end_longitude) {

        Point sourcePoint = Point.fromLngLat(start_longitude, start_latitude);
        Point endPoint = Point.fromLngLat(end_longitude, end_latitude);

        MapboxDirections client = MapboxDirections.builder()
                .origin(sourcePoint)
                .destination(endPoint)
                .overview(DirectionsCriteria.OVERVIEW_FULL)
                .profile(DirectionsCriteria.PROFILE_DRIVING)
                .accessToken(Mapbox.getAccessToken())
                .build();

        client.enqueueCall(new Callback<DirectionsResponse>() {
            @Override
            public void onResponse(Call<DirectionsResponse> call, Response<DirectionsResponse> response) {

                if (response.body() == null) {
                    Utility.Logger(TAG, "No routes found, make sure you set the right user and access token.");
                    return;
                } else if (response.body().routes().size() < 1) {
                    Utility.Logger(TAG, "No routes found");
                    return;
                }

                // Retrieve the directions route from the API response
                DirectionsRoute currentRoute = response.body().routes().get(0);
                List<LatLng> polyLineList = new ArrayList<>();
                LineString lineString = LineString.fromPolyline(currentRoute.geometry(), Constants.PRECISION_6);

                for (int i = 0; i < lineString.coordinates().size(); i++) {

                    double latitude = lineString.coordinates().get(i).latitude();
                    double longitude = lineString.coordinates().get(i).longitude();
                    LatLng location = new LatLng(latitude,longitude);
                    polyLineList.add(location);

                }


                LatLngBounds.Builder builder = new LatLngBounds.Builder();
                for (LatLng latLng : polyLineList) {
                    builder.include(latLng);
                }
                LatLngBounds bounds = builder.build();
                CameraUpdate mCameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, 2);
                googleMap.animateCamera(mCameraUpdate);

                PolylineOptions polylineOptions = new PolylineOptions();
                polylineOptions.color(Color.BLACK);
                polylineOptions.width(5);
                polylineOptions.startCap(new SquareCap());
                polylineOptions.endCap(new SquareCap());
                polylineOptions.jointType(ROUND);
                polylineOptions.addAll(polyLineList);
                googleMap.addPolyline(polylineOptions);

                googleMap.addMarker(new MarkerOptions().position(new LatLng(start_latitude, start_longitude)).
                        flat(true).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_small_car)));


            }

            @Override
            public void onFailure(Call<DirectionsResponse> call, Throwable throwable) {

                Utility.Logger(TAG, "Error: " + throwable.getMessage());

            }
        });

    }

    @Override
    public void onClick(View v) {
        if (v==imageBack){
            finish();
        }
    }
}

