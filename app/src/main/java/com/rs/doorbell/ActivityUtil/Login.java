package com.rs.doorbell.ActivityUtil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatCheckBox;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;


import com.rs.doorbell.ConstantUtil.Constant;
import com.rs.doorbell.FontUtil.Font;
import com.rs.doorbell.InterfaceUtil.ConnectionCallback;
import com.rs.doorbell.ManagementUtil.Management;
import com.rs.doorbell.ObjectUtil.DataObject;
import com.rs.doorbell.ObjectUtil.PrefObject;
import com.rs.doorbell.ObjectUtil.RequestObject;
import com.rs.doorbell.R;
import com.rs.doorbell.Utility.Utility;

import org.json.JSONException;
import org.json.JSONObject;

public class Login extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    private TextView txtMenu;
    private ImageView imageBack;
    private EditText editEmail;
    private EditText editPassword;
    private AppCompatCheckBox checkboxRemember;
    private TextView txtForgot;
    private TextView txtLogin;
    private TextView txtSignUp;
    private Management management;
    private PrefObject userData;
    private String TAG = Login.class.getName();
    private Switch switchPush;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Utility.changeAppTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initUI(); //Initialize UI

    }


    /**
     * <p>It initialize the UI</p>
     */
    private void initUI() {

        txtMenu = (TextView) findViewById(R.id.txt_menu);
        txtMenu.setText(Utility.getStringFromRes(this, R.string.login));

        switchPush = (Switch) findViewById(R.id.switch_push);
        switchPush.setVisibility(View.GONE);

        imageBack = (ImageView) findViewById(R.id.image_back);
        imageBack.setVisibility(View.VISIBLE);
        imageBack.setImageResource(R.drawable.ic_back);

        editEmail = (EditText) findViewById(R.id.edit_email);
        editPassword = (EditText) findViewById(R.id.edit_password);

        checkboxRemember = (AppCompatCheckBox) findViewById(R.id.checkbox_remember);
        checkboxRemember.setTypeface(Font.ubuntu_regular_font(this));  //Setting Font

        txtForgot = (TextView) findViewById(R.id.txt_forgot);
        txtLogin = (TextView) findViewById(R.id.txt_login);
        txtSignUp = (TextView) findViewById(R.id.txt_sign_up);


        management = new Management(this);
        userData = management.getPreferences(new PrefObject()
                .setRetrieveUserRemember(true)
                .setRetrieveUserCredential(true));

        //Check either userObject remember Email or Password

        if (userData.isUserRemember()) {

            checkboxRemember.setChecked(userData.isUserRemember());
            editEmail.setText(userData.getUserEmail());
            editPassword.setText(userData.getUserPassword());

        } else
            checkboxRemember.setChecked(false);

        txtLogin.setOnClickListener(this);
        txtSignUp.setOnClickListener(this);
        txtForgot.setOnClickListener(this);
        imageBack.setOnClickListener(this);
        checkboxRemember.setOnCheckedChangeListener(this);

    }


    /**
     * <p>It is used to send request to server for userObject authentication</p>
     */
    private void sendServerRequest() {

        showProgressSheet(this);
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

            jsonObject.accumulate("functionality", "rider_login");
            jsonObject.accumulate("email", editEmail.getText().toString());
            jsonObject.accumulate("password", editPassword.getText().toString());
            jsonObject.accumulate("device_id", Constant.Credentials.DEVICE_TOKEN);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        // 2. convert JSONObject to JSON to String
        json = jsonObject.toString();
        Utility.Logger("JSON", json);
        return json;

    }


    /**
     * <p>Used to show bottom sheet dialog for Cart Alert</p>
     */
    private void showProgressSheet(final Context context) {

        View view = getLayoutInflater().inflate(R.layout.progress_sheet_layout, null);

        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
        bottomSheetDialog.setContentView(view);
        bottomSheetDialog.show();

        TextView txtProgress = view.findViewById(R.id.txt_progress);
        txtProgress.setText(Utility.getStringFromRes(context,R.string.loging_user));

        management.sendRequestToServer(new RequestObject()
                .setContext(getApplicationContext())
                .setJson(getJson())
                .setConnection(Constant.CONNECTION.LOGIN)
                .setConnectionType(Constant.CONNECTION_TYPE.UI)
                .setConnectionCallback(new ConnectionCallback() {
                    @Override
                    public void onSuccess(Object data, RequestObject requestObject) {

                        if (bottomSheetDialog != null && bottomSheetDialog.isShowing()) {
                            bottomSheetDialog.dismiss();

                            if (data != null && requestObject != null) {

                                DataObject dataObject = (DataObject) data;

                                if (checkboxRemember.isChecked()) {

                                    management.savePreferences(new PrefObject()
                                            .setSaveUserRemember(true)
                                            .setUserRemember(true));

                                }

                                management.savePreferences(new PrefObject()
                                        .setSaveLogin(true)
                                        .setLogin(true));

                                management.savePreferences(new PrefObject()
                                        .setSaveUserCredential(true)
                                        .setUserId(dataObject.getUser_id())
                                        .setFirstName(dataObject.getUser_fName())
                                        .setUserPhone(dataObject.getPhone())
                                        .setUserPassword(dataObject.getUser_password())
                                        .setUserEmail(dataObject.getUser_email())
                                        .setPictureUrl(dataObject.getUser_picture()));

                                startActivity(new Intent(context,Base.class));
                                finish();

                            }

                        }
                    }

                    @Override
                    public void onError(String data, RequestObject requestObject) {
                        if (bottomSheetDialog != null && bottomSheetDialog.isShowing())
                            bottomSheetDialog.dismiss();

                        Utility.Toaster(context, data, Toast.LENGTH_SHORT);
                    }
                }));

    }


    @Override
    public void onClick(View v) {
        if (v == txtLogin) {

            if (Utility.isEmptyString(editEmail.getText().toString())) {
                Utility.Toaster(this, Utility.getStringFromRes(this, R.string.email_empty), Toast.LENGTH_LONG);
                return;
            }

            if (Utility.isEmptyString(editPassword.getText().toString())) {
                Utility.Toaster(this, Utility.getStringFromRes(this, R.string.password_empty), Toast.LENGTH_LONG);
                return;
            }

            sendServerRequest();

        }
        if (v == txtSignUp) {
            startActivity(new Intent(this, SignUp.class));
        }
        if (v == txtForgot) {
            startActivity(new Intent(this, ForgotPassword.class));
        }
        if (v == imageBack) {
            finish();
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (buttonView == checkboxRemember) {

        }
    }



}
