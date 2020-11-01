package com.rs.doorbell.ActivityUtil;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
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
import com.rs.doorbell.ObjectUtil.DataObject;
import com.rs.doorbell.ObjectUtil.PrefObject;
import com.rs.doorbell.ObjectUtil.RequestObject;
import com.rs.doorbell.R;
import com.rs.doorbell.Utility.Utility;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MyBankDetail extends AppCompatActivity implements View.OnClickListener, ConnectionCallback {
    private String TAG = MyBankDetail.class.getSimpleName();
    private TextView txtMenu;
    private ImageView imageBack;
    private ArrayList<Object> objectArrayList = new ArrayList<>();
    private boolean isUpdate;
    private Management management;
    private PrefObject prefObject;
    private EditText editHolderName;
    private EditText editAccountNo;
    private EditText editTransistNo;
    private EditText editBankNo;
    private TextView txtDone;
    private String holderId;
    private String accountId;
    private String transistId;
    private String bankId;
    private Switch switchPush;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_detail);


        initUI(); //Initialize UI

    }


    /**
     * <p>It initialize the UI</p>
     */
    private void initUI() {

        management = new Management(this);
        prefObject = management.getPreferences(new PrefObject()
                .setRetrieveUserCredential(true));

        txtMenu = findViewById(R.id.txt_menu);
        txtMenu.setText(Utility.getStringFromRes(this, R.string.my_bank_detail));

        switchPush = (Switch) findViewById(R.id.switch_push);
        switchPush.setVisibility(View.GONE);

        imageBack = findViewById(R.id.image_back);
        imageBack.setImageResource(R.drawable.ic_back);
        imageBack.setVisibility(View.VISIBLE);

        editHolderName = (EditText) findViewById(R.id.edit_holder_name);
        editAccountNo = (EditText) findViewById(R.id.edit_account_no);
        editTransistNo = (EditText) findViewById(R.id.edit_transist_no);
        editBankNo = (EditText) findViewById(R.id.edit_bank_no);
        txtDone = (TextView) findViewById(R.id.txt_done);


        sendServerRequest();  //Send Request to Server

        txtDone.setOnClickListener(this);
        imageBack.setOnClickListener(this);

    }


    /**
     * <p>It is used to send Request to Server</p>
     */
    private void sendServerRequest() {

        management.sendRequestToServer(new RequestObject()
                .setContext(this)
                .setJson(getJson(prefObject.getUserId()))
                .setConnection(Constant.CONNECTION.RIDER_BANK_DETAIL)
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

            jsonObject.accumulate("functionality", "retrieve_rider_bank_detail");
            jsonObject.accumulate("rider_id", rider_id);


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
    private String getUpdateBankDetaiJson(String rider_id, boolean isUpdateBankDetail) {
        String json = null;

        // 1. build jsonObject
        JSONObject jsonObject = new JSONObject();
        try {

            if (isUpdateBankDetail) {
                jsonObject.accumulate("functionality", "update_bank_detail");
                jsonObject.accumulate("holder_name_id", holderId);
                jsonObject.accumulate("account_no_id", accountId);
                jsonObject.accumulate("transist_no_id", transistId);
                jsonObject.accumulate("bank_no_id", bankId);
            } else
                jsonObject.accumulate("functionality", "insert_bank_detail");


            jsonObject.accumulate("rider_id", rider_id);
            jsonObject.accumulate("holder_name", editHolderName.getText().toString());
            jsonObject.accumulate("account_no", editAccountNo.getText().toString());
            jsonObject.accumulate("transist_no", editTransistNo.getText().toString());
            jsonObject.accumulate("bank_no", editBankNo.getText().toString());


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
        if (v == imageBack) {
            finish();
        }
        if (v == txtDone) {

            if (Utility.isEmptyString(editHolderName.getText().toString())){
                Utility.Toaster(this,Utility.getStringFromRes(this,R.string.empty_holder_name), Toast.LENGTH_SHORT);
                return;
            }

            if (Utility.isEmptyString(editAccountNo.getText().toString())){
                Utility.Toaster(this,Utility.getStringFromRes(this,R.string.empty_account_no), Toast.LENGTH_SHORT);
                return;
            }

            if (Utility.isEmptyString(editTransistNo.getText().toString())){
                Utility.Toaster(this,Utility.getStringFromRes(this,R.string.empty_transist_no), Toast.LENGTH_SHORT);
                return;
            }

            if (Utility.isEmptyString(editBankNo.getText().toString())){
                Utility.Toaster(this,Utility.getStringFromRes(this,R.string.empty_bank_no), Toast.LENGTH_SHORT);
                return;
            }

            showProgressSheet(this);
        }
    }

    @Override
    public void onSuccess(Object data, RequestObject requestObject) {
        if (data != null && requestObject != null) {

            objectArrayList.clear();
            DataObject dataObject = (DataObject) data;

            objectArrayList.addAll(dataObject.getObjectArrayList());
            if (objectArrayList.size() > 0) {
                isUpdate = true;

                editAccountNo.setText(((DataObject) objectArrayList.get(0)).getAccount_no());
                editHolderName.setText(((DataObject) objectArrayList.get(0)).getAccount_holder_name());
                editTransistNo.setText(((DataObject) objectArrayList.get(0)).getBank_transist_no());
                editBankNo.setText(((DataObject) objectArrayList.get(0)).getBank_no());

                holderId = ((DataObject) objectArrayList.get(0)).getAccount_holder_id();
                accountId = ((DataObject) objectArrayList.get(0)).getAccount_no_id();
                transistId = ((DataObject) objectArrayList.get(0)).getBank_transist_id();
                bankId = ((DataObject) objectArrayList.get(0)).getBank_no_id();

            } else {

                isUpdate = false;

            }


        }
    }

    @Override
    public void onError(String data, RequestObject requestObject) {
        isUpdate = false;

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
        txtProgress.setText(!isUpdate ? Utility.getStringFromRes(context, R.string.adding_bank_detail)
                : Utility.getStringFromRes(context, R.string.update_bank_detail));

        management.sendRequestToServer(new RequestObject()
                .setContext(getApplicationContext())
                .setJson(getUpdateBankDetaiJson(prefObject.getUserId(), isUpdate))
                .setConnection(Constant.CONNECTION.RIDER_BANK_DETAIL)
                .setConnectionType(Constant.CONNECTION_TYPE.UI)
                .setConnectionCallback(new ConnectionCallback() {
                    @Override
                    public void onSuccess(Object data, RequestObject requestObject) {

                        if (bottomSheetDialog != null && bottomSheetDialog.isShowing()) {
                            bottomSheetDialog.dismiss();

                            objectArrayList.clear();
                            DataObject dataObject = (DataObject) data;

                            objectArrayList.addAll(dataObject.getObjectArrayList());
                            if (objectArrayList.size() > 0) {
                                isUpdate = true;

                                editAccountNo.setText(((DataObject) objectArrayList.get(0)).getAccount_no());
                                editHolderName.setText(((DataObject) objectArrayList.get(0)).getAccount_holder_name());
                                editTransistNo.setText(((DataObject) objectArrayList.get(0)).getBank_transist_no());
                                editBankNo.setText(((DataObject) objectArrayList.get(0)).getBank_no());

                                holderId = ((DataObject) objectArrayList.get(0)).getAccount_holder_id();
                                accountId = ((DataObject) objectArrayList.get(0)).getAccount_no_id();
                                transistId = ((DataObject) objectArrayList.get(0)).getBank_transist_id();
                                bankId = ((DataObject) objectArrayList.get(0)).getBank_no_id();

                            } else {

                                isUpdate = false;

                            }

                        }
                    }

                    @Override
                    public void onError(String data, RequestObject requestObject) {
                        if (bottomSheetDialog != null && bottomSheetDialog.isShowing())
                            bottomSheetDialog.dismiss();

                        isUpdate = false;

                    }
                }));

    }


}
