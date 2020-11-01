package com.rs.doorbell.ActivityUtil;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.rs.doorbell.AdapterUtil.HomeTabPager;
import com.rs.doorbell.ConstantUtil.Constant;
import com.rs.doorbell.FragmentUtil.CurrentOrder;
import com.rs.doorbell.FragmentUtil.OrderHistory;
import com.rs.doorbell.ManagementUtil.Management;
import com.rs.doorbell.ObjectUtil.PagerTabObject;
import com.rs.doorbell.ObjectUtil.PrefObject;
import com.rs.doorbell.ObjectUtil.RequestObject;
import com.rs.doorbell.R;
import com.rs.doorbell.Utility.Utility;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Base extends AppCompatActivity implements View.OnClickListener,CompoundButton.OnCheckedChangeListener  {
    private String TAG = Base.class.getName();
    private TextView txtMenu;
    private ImageView imageSetting;
    private Management management;
    private PrefObject prefObject;
    private ArrayList<PagerTabObject> objectArrayList = new ArrayList<>();
    private ViewPager viewPager;
    private TabLayout layoutTab;
    private HomeTabPager homeTabPager;
    public static Switch switchPush;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Utility.changeAppTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        initUI(); //Initialize UI

    }

    /**
     * <p>It initialize the UI</p>
     */
    private void initUI() {

        txtMenu = findViewById(R.id.txt_menu);
        txtMenu.setText(Utility.getStringFromRes(this, R.string.app_name));




        imageSetting = findViewById(R.id.image_setting);
        imageSetting.setImageResource(R.drawable.ic_menu_setting);
        imageSetting.setVisibility(View.VISIBLE);
        switchPush = (Switch) findViewById(R.id.switch_push);
        switchPush.setVisibility(View.VISIBLE);
        management = new Management(this);


        prefObject = management.getPreferences(new PrefObject()
                .setRetrieveUserCredential(true)
                .setRetrieveRiderStatus(true)
                .setRetrieveLogin(true));


        if (prefObject.isRiderStatus()) {
            switchPush.setChecked(true);
        }
        else
            switchPush.setChecked(false);

        //Adding Fragment into Pager Adapter

        objectArrayList.add(new PagerTabObject()
                .setTitle(Utility.getStringFromRes(this, R.string.current_order))
                .setFragment(new CurrentOrder()));

        objectArrayList.add(new PagerTabObject()
                .setTitle(Utility.getStringFromRes(this, R.string.order_history))
                .setFragment(new OrderHistory()));

        switchPush.setOnCheckedChangeListener(this);

        //Initialize Viewpager

        viewPager = findViewById(R.id.view_pager);
        homeTabPager = new HomeTabPager(getSupportFragmentManager(), objectArrayList);
        viewPager.setAdapter(homeTabPager);

        //Setup Tab Layout with Pager Fragment

        layoutTab = findViewById(R.id.layout_tab);
        layoutTab.setupWithViewPager(viewPager);

        for (int i = 0; i < layoutTab.getTabCount(); i++) {

            TextView tv = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab_item_layout, null);
            //tv.setTextColor(Utility.getColourFromRes(this, R.color.));
            tv.setText(objectArrayList.get(i).getTitle());
            layoutTab.getTabAt(i).setCustomView(tv);

        }

        imageSetting.setOnClickListener(this);


    }


    @Override
    public void onClick(View v) {
        if (v == imageSetting) {
            startActivity(new Intent(this, Setting.class));
        }
    }


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (buttonView.getId() == switchPush.getId()) {

            if (isChecked) {
                sendServerRequest(true);
                management.savePreferences(new PrefObject()
                        .setSaveRiderStatus(true)
                        .setRiderStatus(true));
            } else {
                sendServerRequest(false);
                management.savePreferences(new PrefObject()
                        .setSaveRiderStatus(true)
                        .setRiderStatus(false));
            }

        }
    }


    private void sendServerRequest(boolean enableRider) {

        management.sendRequestToServer(new RequestObject()
                .setContext(this)
                .setJson(getJson(prefObject.getUserId(), enableRider))
                .setConnection(Constant.CONNECTION.ENABLE_DISABLE_RIDER)
                .setConnectionType(Constant.CONNECTION_TYPE.BACKGROUND));

    }

    private String getJson(String rider_id, boolean enableRider) {
        String json = null;

        // 1. build jsonObject
        JSONObject jsonObject = new JSONObject();
        try {

            if (enableRider) {
                jsonObject.accumulate("functionality", "activate_rider");
            } else {
                jsonObject.accumulate("functionality", "deactivate_rider");
            }
            jsonObject.accumulate("rider_id", rider_id);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        // 2. convert JSONObject to JSON to String
        json = jsonObject.toString();
        Utility.Logger(TAG, "JSON " + json);

        return json;
    }
}
