package com.rs.doorbell.ActivityUtil;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.rs.doorbell.ConstantUtil.Constant;
import com.rs.doorbell.InterfaceUtil.ConnectionCallback;
import com.rs.doorbell.ManagementUtil.Management;
import com.rs.doorbell.ObjectUtil.DataObject;
import com.rs.doorbell.ObjectUtil.RequestObject;
import com.rs.doorbell.R;
import com.rs.doorbell.Utility.Utility;

import org.json.JSONException;
import org.json.JSONObject;

public class AboutUs extends AppCompatActivity implements View.OnClickListener, ConnectionCallback {
    private TextView txtMenu;
    private Management management;
    private ImageView imageMenu;
    private WebView webviewAbout;
    private String TAG = AboutUs.class.getName();
    private Switch switchPush;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Utility.changeAppTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        initUI(); //Initialize UI
    }


    /**
     * <p>It initialize the UI</p>
     */
    private void initUI() {

        management = new Management(this);

        txtMenu = findViewById(R.id.txt_menu);
        txtMenu.setText(Utility.getStringFromRes(this, R.string.about_us));

        switchPush = (Switch) findViewById(R.id.switch_push);
        switchPush.setVisibility(View.GONE);

        imageMenu = findViewById(R.id.image_back);
        imageMenu.setVisibility(View.VISIBLE);
        imageMenu.setImageResource(R.drawable.ic_back);

        webviewAbout = (WebView) findViewById(R.id.webview_about);

        management.sendRequestToServer(new RequestObject()
                .setJson(getJson())
                .setConnectionType(Constant.CONNECTION_TYPE.UI)
                .setConnection(Constant.CONNECTION.PRIVACY_POLICY)
                .setConnectionCallback(this));


        imageMenu.setOnClickListener(this);

    }


    /**
     * <p>It is used to convert data into json format for POST type Request</p>
     *
     * @return
     */
    public String getJson() {
        String json = "";

        // 1. build jsonObject
        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.accumulate("functionality", "privacy");


        } catch (JSONException e) {
            e.printStackTrace();
        }

        // 2. convert JSONObject to JSON to String
        json = jsonObject.toString();
        Utility.Logger("JSON", json);
        return json;

    }

    @Override
    public void onClick(View v) {
        if (v == imageMenu) {
            finish();
        }
    }

    @Override
    public void onSuccess(Object data, RequestObject requestObject) {
        if (data != null && requestObject != null) {

            DataObject object = (DataObject) data;
            /*webviewAbout.loadData(object.getPrivacyPolicy()
                    , "text/html", "UTF-8");*/

        }
    }

    @Override
    public void onError(String data, RequestObject requestObject) {
        Utility.Logger(TAG, "Error = " + data);
    }
}