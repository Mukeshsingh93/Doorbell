package com.rs.doorbell.ActivityUtil;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.rs.doorbell.ConstantUtil.Constant;
import com.rs.doorbell.InterfaceUtil.ConnectionCallback;
import com.rs.doorbell.ManagementUtil.Management;
import com.rs.doorbell.ObjectUtil.RequestObject;
import com.rs.doorbell.R;
import com.rs.doorbell.Utility.Utility;

import org.json.JSONException;
import org.json.JSONObject;

public class ForgotPassword extends AppCompatActivity implements View.OnClickListener, ConnectionCallback {
    private TextView txtMenu;
    private ImageView imageBack;
    private TextView appName;
    private TextView txtInformation;
    private EditText editEmail;
    private TextView txtForgot;
    private Management management;
    private Switch switchPush;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Utility.changeAppTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        initUI(); //Initialize UI

    }

    /**
     * <p>It initialize the UI</p>
     */
    private void initUI() {

        txtMenu = (TextView) findViewById(R.id.txt_menu);
        txtMenu.setText(Utility.getStringFromRes(this, R.string.forgot_password));

        switchPush = (Switch) findViewById(R.id.switch_push);
        switchPush.setVisibility(View.GONE);

        imageBack = (ImageView) findViewById(R.id.image_back);
        imageBack.setVisibility(View.VISIBLE);
        imageBack.setImageResource(R.drawable.ic_back);

        management = new Management(this);

        appName = (TextView) findViewById(R.id.app_name);
        txtInformation = (TextView) findViewById(R.id.txt_information);
        editEmail = (EditText) findViewById(R.id.edit_email);
        txtForgot = (TextView) findViewById(R.id.txt_forgot);

        txtForgot.setOnClickListener(this);
        imageBack.setOnClickListener(this);

    }

    /**
     * <p>It is used to send request to server for userObject authentication</p>
     */
    private void sendServerRequest() {

        management.sendRequestToServer(new RequestObject()
                .setJson(getJson())
                .setConnectionType(Constant.CONNECTION_TYPE.UI)
                .setConnection(Constant.CONNECTION.FORGOT)
                .setConnectionCallback(this));
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

            jsonObject.accumulate("functionality", "forgot_rider_password");
            jsonObject.accumulate("email", editEmail.getText().toString());


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
        if (v == txtForgot) {

            if (Utility.isEmptyString(editEmail.getText().toString())) {
                Utility.Toaster(this, Utility.getStringFromRes(this, R.string.email_empty), Toast.LENGTH_LONG);
                return;
            }

            sendServerRequest();

        }
        if (v == imageBack) {
            finish();
        }
    }


    @Override
    public void onSuccess(Object data, RequestObject requestObject) {

        if (requestObject != null) {

            Utility.Toaster(this,Utility.getStringFromRes(this,R.string.password_sent_email),Toast.LENGTH_SHORT);
            editEmail.setText(null);
        }

    }

    @Override
    public void onError(String data, RequestObject requestObject) {
        Utility.Toaster(this,data,Toast.LENGTH_SHORT);

    }


}
