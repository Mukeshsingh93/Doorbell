package com.rs.doorbell.ActivityUtil;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.signature.ObjectKey;
import com.rs.doorbell.ConstantUtil.Constant;
import com.rs.doorbell.CustomUtil.GlideApp;
import com.rs.doorbell.InterfaceUtil.ConnectionCallback;
import com.rs.doorbell.ManagementUtil.Management;
import com.rs.doorbell.ObjectUtil.Base64Object;
import com.rs.doorbell.ObjectUtil.PrefObject;
import com.rs.doorbell.ObjectUtil.RequestObject;
import com.rs.doorbell.R;
import com.rs.doorbell.Utility.Utility;
import com.makeramen.roundedimageview.RoundedImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class UserProfile extends AppCompatActivity implements View.OnClickListener {
    private TextView txtMenu;
    private ImageView imageBack;
    private RoundedImageView imageProfile;
    private EditText editFName;
    private EditText editLName;
    private EditText editEmail;
    private EditText editPhone;
    private EditText editPassword;
    private EditText editBio;
    private TextView txtUpdate;
    private Management management;
    private PrefObject userData;
    private Bitmap selectedBitmap;
    private boolean isPictureSelected;
    private String userPicture = "null";
    private String pictureUrl;
    private Switch switchPush;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Utility.changeAppTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);


        initUI(); //Initialize UI

    }

    /**
     * <p>It initialize the UI</p>
     */
    private void initUI() {

        txtMenu = (TextView) findViewById(R.id.txt_menu);
        txtMenu.setText(Utility.getStringFromRes(this, R.string.profile));

        switchPush = (Switch) findViewById(R.id.switch_push);
        switchPush.setVisibility(View.GONE);

        imageBack = (ImageView) findViewById(R.id.image_back);
        imageBack.setVisibility(View.VISIBLE);
        imageBack.setImageResource(R.drawable.ic_back);

        management = new Management(this);

        imageProfile = (RoundedImageView) findViewById(R.id.image_profile);
        editFName = (EditText) findViewById(R.id.edit_fName);
        ///editLName = (EditText) findViewById(R.id.edit_lName);
        editEmail = (EditText) findViewById(R.id.edit_email);
        editPhone = (EditText) findViewById(R.id.edit_phone);
        editPassword = (EditText) findViewById(R.id.edit_password);
        txtUpdate = (TextView) findViewById(R.id.txt_update);

        userData = management.getPreferences(new PrefObject()
                .setRetrieveUserCredential(true));

        editFName.setText(userData.getFirstName());
        //editLName.setText(userData.getLastName());
        editEmail.setText(userData.getUserEmail());
        editPassword.setText(userData.getUserPassword());
        editPhone.setText(userData.getUserPhone());

        Utility.Logger("Picture Url", Constant.ServerInformation.PICTURE_URL + userData.getPictureUrl());

        pictureUrl = Constant.ServerInformation.PICTURE_URL + userData.getPictureUrl();
        GlideApp.with(this).load(pictureUrl)
                .apply(new RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true)
                        .placeholder(R.drawable.profile_picture)
                        .error(R.drawable.profile_picture)
                        .signature(new ObjectKey(System.currentTimeMillis())))
                .into(imageProfile);

        editEmail.setEnabled(false);
        editPhone.setEnabled(false);

        txtUpdate.setOnClickListener(this);
        imageBack.setOnClickListener(this);
        imageProfile.setOnClickListener(this);

    }


    /**
     * <p>It is used to send request to server for updating profile</p>
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

            jsonObject.accumulate("functionality", "update_rider");
            jsonObject.accumulate("rider_id", userData.getUserId());
            jsonObject.accumulate("first_name", editFName.getText().toString());
            ///jsonObject.accumulate("last_name", editLName.getText().toString());
            jsonObject.accumulate("phone", editPhone.getText().toString());
            jsonObject.accumulate("email", editEmail.getText().toString());
            jsonObject.accumulate("password", editPassword.getText().toString());
            jsonObject.accumulate("picture", userPicture);


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
        if (v == imageBack) {
            finish();
        }
        if (v == txtUpdate) {
            sendServerRequest();
        }
        if (v == imageProfile) {
            onPictureSelector(this);
        }
    }


    /**
     * <p>It trigger dialog for picture selection </p>
     *
     * @param context
     */
    private void onPictureSelector(Context context) {
        final Dialog dialog = new Dialog(context);
        dialog.getWindow().addFlags(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        dialog.setContentView(R.layout.custom_dialog_layout);

        LinearLayout layout_camera = (LinearLayout) dialog.findViewById(R.id.layout_camera);
        layout_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSelectCamera();
                dialog.dismiss();
            }
        });

        LinearLayout layout_gallery = (LinearLayout) dialog.findViewById(R.id.layout_gallery);
        layout_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onSelectGallery();
                dialog.dismiss();
            }
        });

        dialog.show();


    }


    /**
     * <P>It is used to initialize Camera for picture capture</P>
     */
    private void onSelectCamera() {
        Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(takePicture, Constant.RequestCode.REQUEST_CODE_CAMERA);//zero can be replaced with any action code
    }


    /**
     * <p>It is used to open Gallery for picture selection</p>
     */
    private void onSelectGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(intent, Constant.RequestCode.REQUEST_CODE_GALLERY);//one can be replaced with any action code
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Constant.RequestCode.REQUEST_CODE_CAMERA && resultCode == RESULT_OK) {

            Bitmap photo = (Bitmap) data.getExtras().get("data");
            selectedBitmap = photo;
            imageProfile.setImageBitmap(photo);
            isPictureSelected = true;

            userPicture = Utility.base64Converter(new Base64Object(true, false, selectedBitmap));

        }

        if (requestCode == Constant.RequestCode.REQUEST_CODE_GALLERY && resultCode == RESULT_OK) {
            Uri selectedImage = data.getData();
            imageProfile.setImageURI(selectedImage);

            try {
                selectedBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
            } catch (IOException e) {
                e.printStackTrace();
            }

            userPicture = Utility.base64Converter(new Base64Object(true, false, selectedBitmap));

            isPictureSelected = true;
        }


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
        txtProgress.setText(Utility.getStringFromRes(context,R.string.updating_profile));

        management.sendRequestToServer(new RequestObject()
                .setContext(getApplicationContext())
                .setJson(getJson())
                .setConnection(Constant.CONNECTION.UPDATE)
                .setConnectionType(Constant.CONNECTION_TYPE.UI)
                .setConnectionCallback(new ConnectionCallback() {
                    @Override
                    public void onSuccess(Object data, RequestObject requestObject) {

                        if (bottomSheetDialog != null && bottomSheetDialog.isShowing()) {
                            bottomSheetDialog.dismiss();

                            if (data != null && requestObject != null) {

                                management.savePreferences(new PrefObject()
                                        .setSaveUserCredential(true)
                                        .setUserId(userData.getUserId())
                                        .setFirstName(editFName.getText().toString())
                                        .setUserPhone(editPhone.getText().toString())
                                        .setUserPassword(editPassword.getText().toString())
                                        .setUserEmail(userData.getUserEmail())
                                        .setPictureUrl(userData.getPictureUrl()));

                                GlideApp.with(context).load(pictureUrl)
                                        .apply(new RequestOptions()
                                                .diskCacheStrategy(DiskCacheStrategy.NONE)
                                                .skipMemoryCache(true)
                                                .placeholder(R.drawable.profile_picture)
                                                .error(R.drawable.profile_picture)
                                                .signature(new ObjectKey(System.currentTimeMillis())))
                                        .into(imageProfile);

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


}
