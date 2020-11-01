package com.rs.doorbell.ActivityUtil;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.rs.doorbell.ConstantUtil.Constant;
import com.rs.doorbell.ManagementUtil.Management;
import com.rs.doorbell.ObjectUtil.PrefObject;
import com.rs.doorbell.ObjectUtil.RequestObject;
import com.rs.doorbell.R;
import com.rs.doorbell.Utility.Utility;
import com.makeramen.roundedimageview.RoundedImageView;

import org.json.JSONException;
import org.json.JSONObject;

public class Setting extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    private String TAG = Setting.class.getSimpleName();
    private RoundedImageView imageUser;
    private TextView txtName;
    private TextView txtEmail;
    private TextView txtDividerLogout;
    private RelativeLayout layoutProfile;
    private RelativeLayout layoutDocuments;
    private RelativeLayout layoutBankDetail;
    private RelativeLayout layoutLogout;
    private LinearLayout layoutEdit;
    private Switch switchPush,switchPush1;
    private Management management;
    private PrefObject prefObject;
    private String pictureUrl;
    private TextView txtMenu;
    private ImageView imageBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        initUI(); //Initialize UI


    }


    /**
     * <p>It initialize the UI</p>
     */
    private void initUI() {

        txtMenu = findViewById(R.id.txt_menu);
        txtMenu.setText(Utility.getStringFromRes(this, R.string.setting));



        imageBack = findViewById(R.id.image_back);
        imageBack.setVisibility(View.VISIBLE);
        imageBack.setImageResource(R.drawable.ic_back);

        management = new Management(this);
        prefObject = management.getPreferences(new PrefObject()
                .setRetrieveUserCredential(true)
                .setRetrieveRiderStatus(true)
                .setRetrieveLogin(true));

        imageUser = (RoundedImageView) findViewById(R.id.image_user);
        txtName = (TextView) findViewById(R.id.txt_name);
        txtEmail = (TextView) findViewById(R.id.txt_email);
        txtDividerLogout = findViewById(R.id.txt_divider_logout);

        layoutEdit = (LinearLayout) findViewById(R.id.layout_edit);
        layoutProfile = (RelativeLayout) findViewById(R.id.layout_profile);
        layoutDocuments = (RelativeLayout) findViewById(R.id.layout_documents);
        layoutBankDetail = (RelativeLayout) findViewById(R.id.layout_bank_detail);
        layoutLogout = (RelativeLayout) findViewById(R.id.layout_logout);
        switchPush = (Switch) findViewById(R.id.switch_push1);
        switchPush1 = (Switch) findViewById(R.id.switch_push);
        switchPush1.setVisibility(View.GONE);

        if (prefObject.isLogin()) {
            layoutLogout.setVisibility(View.VISIBLE);
            layoutEdit.setVisibility(View.VISIBLE);
            txtDividerLogout.setVisibility(View.VISIBLE);
        }

        if (prefObject.isRiderStatus()) {
            switchPush.setChecked(true);
        }
        else
            switchPush.setChecked(false);


        layoutProfile.setOnClickListener(this);
        layoutDocuments.setOnClickListener(this);
        layoutBankDetail.setOnClickListener(this);
        layoutLogout.setOnClickListener(this);
        imageBack.setOnClickListener(this);
        switchPush.setOnCheckedChangeListener(this);


    }

    @Override
    public void onClick(View v) {
        if (v == layoutProfile) {
            startActivity(new Intent(this, UserProfile.class));
        }
        if (v == layoutDocuments) {
            startActivity(new Intent(this, MyDocuments.class));
        }
        if (v == layoutBankDetail) {
            startActivity(new Intent(this, MyBankDetail.class));
        }
        if (v == layoutLogout) {

            management.savePreferences(new PrefObject()
                    .setSaveLogin(true)
                    .setLogin(false));

            startActivity(new Intent(this, Login.class));
            finish();

        }
        if (v==imageBack){
            finish();
        }


    }

    /**
     * <p>It is used to send Request to Server</p>
     */
    private void sendServerRequest(boolean enableRider) {

        management.sendRequestToServer(new RequestObject()
                .setContext(this)
                .setJson(getJson(prefObject.getUserId(), enableRider))
                .setConnection(Constant.CONNECTION.ENABLE_DISABLE_RIDER)
                .setConnectionType(Constant.CONNECTION_TYPE.BACKGROUND));

    }


    /**
     * <p> It is used to convert Object
     * into Json</p>
     *
     * @param
     * @return
     */
    private String getJson(String rider_id, boolean enableRider) {
        String json = null;

        // 1. build jsonObject
        JSONObject jsonObject = new JSONObject();
        try {

            if (enableRider) {
                Base.switchPush.setChecked(true);
                jsonObject.accumulate("functionality", "activate_rider");
            } else {
                Base.switchPush.setChecked(false);
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

    @Override
    protected void onResume() {
        super.onResume();

        txtName.setText(prefObject.getFirstName());
        txtEmail.setText(prefObject.getUserEmail());
        pictureUrl = Constant.ServerInformation.PICTURE_URL + prefObject.getPictureUrl();
        Utility.Logger(TAG, "Picture = " + pictureUrl);

//        try {
//
//            Glide.with(this).load(pictureUrl).apply(new RequestOptions()
//                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
//                    .placeholder(R.drawable.app_icon)
//                    .error(R.drawable.app_icon))
//                    .into(imageUser);
//
//        } catch (IllegalArgumentException ex) {
//            Utility.Logger("Glide-tag", String.valueOf(imageUser.getTag()));
//        }

    }

}
