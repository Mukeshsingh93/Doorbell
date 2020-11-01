package com.rs.doorbell.ActivityUtil;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.rs.doorbell.AdapterUtil.PictureUploaderAdapter;
import com.rs.doorbell.ConstantUtil.Constant;
import com.rs.doorbell.InterfaceUtil.ConnectionCallback;
import com.rs.doorbell.ManagementUtil.Management;
import com.rs.doorbell.ObjectUtil.Base64Object;
import com.rs.doorbell.ObjectUtil.DataObject;
import com.rs.doorbell.ObjectUtil.EmptyObject;
import com.rs.doorbell.ObjectUtil.PictureObject;
import com.rs.doorbell.ObjectUtil.PrefObject;
import com.rs.doorbell.ObjectUtil.ProgressObject;
import com.rs.doorbell.ObjectUtil.RequestObject;
import com.rs.doorbell.R;
import com.rs.doorbell.Utility.Utility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class MyDocuments extends AppCompatActivity implements View.OnClickListener, ConnectionCallback {
    private String TAG = MyDocuments.class.getSimpleName();
    private TextView txtMenu;
    private ImageView imageAdd;
    private ImageView imageBack;
    private ImageView imageDone;
    private RecyclerView recyclerViewPicture;
    private PictureUploaderAdapter pictureUploaderAdapter;
    private ArrayList<Object> objectArrayList = new ArrayList<>();
    private Bitmap selectedBitmap;
    private GridLayoutManager gridLayoutManager;
    private boolean isFirstTime = true;
    private String base64;
    private Management management;
    private PrefObject prefObject;
    private Switch switchPush;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_uploader);


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
        txtMenu.setText(Utility.getStringFromRes(this, R.string.document_uploader));

        switchPush = (Switch) findViewById(R.id.switch_push);
        switchPush.setVisibility(View.GONE);

        imageBack = findViewById(R.id.image_back);
        imageBack.setImageResource(R.drawable.ic_back);
        imageBack.setVisibility(View.VISIBLE);

        imageDone = findViewById(R.id.image_favourite);
        imageDone.setImageResource(R.drawable.ic_tick);


        imageAdd = findViewById(R.id.image_setting);
        imageAdd.setImageResource(R.drawable.ic_add);
        imageAdd.setVisibility(View.VISIBLE);

        objectArrayList.add(new ProgressObject());

        gridLayoutManager = new GridLayoutManager(this, 2, LinearLayoutManager.VERTICAL, false);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (objectArrayList.get(position) instanceof EmptyObject) {
                    return 2;
                } else if (objectArrayList.get(position) instanceof PictureObject) {
                    return 1;
                } else
                    return 2;
            }
        });

        recyclerViewPicture = (RecyclerView) findViewById(R.id.recycler_view_picture);
        recyclerViewPicture.setLayoutManager(gridLayoutManager);

        pictureUploaderAdapter = new PictureUploaderAdapter(this, objectArrayList) {
            @Override
            public void onDelete(int position) {

                management.sendRequestToServer(new RequestObject()
                        .setContext(getApplicationContext())
                        .setJson(getDeleteJson(((PictureObject) objectArrayList.get(position)).getId()))
                        .setConnection(Constant.CONNECTION.DELETE_DOCUMENT)
                        .setConnectionType(Constant.CONNECTION_TYPE.BACKGROUND));

                objectArrayList.remove(position);
                if (objectArrayList.size() <= 0) {
                    objectArrayList.add(new EmptyObject()
                            .setTitle(Utility.getStringFromRes(getApplicationContext(), R.string.upload_picture))
                            .setDescription(Utility.getStringFromRes(getApplicationContext(), R.string.upload_picture_tagline))
                            .setPlaceHolderIcon(R.drawable.em_no_picture));
                    isFirstTime = true;
                }

                pictureUploaderAdapter.notifyDataSetChanged();
            }
        };
        recyclerViewPicture.setAdapter(pictureUploaderAdapter);

        sendServerRequest();  //Send Request to Server

        imageAdd.setOnClickListener(this);
        imageBack.setOnClickListener(this);
        imageDone.setOnClickListener(this);
    }


    /**
     * <p>It is used to send Request to Server</p>
     */
    private void sendServerRequest() {

        management.sendRequestToServer(new RequestObject()
                .setContext(this)
                .setJson(getJson(prefObject.getUserId()))
                .setConnection(Constant.CONNECTION.RIDER_DOCUMENT)
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

            jsonObject.accumulate("functionality", "retrieve_rider_documents");
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
    private String getDeleteJson(String document_id) {
        String json = null;

        // 1. build jsonObject
        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.accumulate("functionality", "delete_document");
            jsonObject.accumulate("document_id", document_id);


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
    private String getAddingPictureJson(String rider_id) {
        String json = null;

        // 1. build jsonObject
        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.accumulate("functionality", "add_document");
            jsonObject.accumulate("rider_id", rider_id);
            jsonObject.accumulate("document_picture", convertPicturesIntoJsonArray());


        } catch (JSONException e) {
            e.printStackTrace();
        }

        // 2. convert JSONObject to JSON to String
        json = jsonObject.toString();
        Utility.Logger(TAG, "JSON " + json);

        return json;
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
            //selectedBitmap = getResizeBitmap(photo);
            base64 = Utility.base64Converter(new Base64Object(true, false, photo));


            if (isFirstTime) {
                //objectArrayList.clear();
                isFirstTime = false;
            }

            objectArrayList.add(new PictureObject().setPicture(base64).setNewlyAdded(true).setStatus("3"));
            imageDone.setVisibility(View.VISIBLE);
            pictureUploaderAdapter.notifyDataSetChanged();
        }

        if (requestCode == Constant.RequestCode.REQUEST_CODE_GALLERY && resultCode == RESULT_OK) {
            Uri selectedImage = data.getData();

            if (isFirstTime) {
                //objectArrayList.clear();
                isFirstTime = false;
            }
            try {

                selectedBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                base64 = Utility.base64Converter(new Base64Object(true, false, selectedBitmap));
                objectArrayList.add(new PictureObject().setPicture(base64).setNewlyAdded(true).setStatus("3"));

            } catch (IOException e) {
                e.printStackTrace();
            }

            imageDone.setVisibility(View.VISIBLE);
            pictureUploaderAdapter.notifyDataSetChanged();

        }


    }

    @Override
    public void onClick(View v) {
        if (v == imageAdd) {
            onPictureSelector(this);
        }
        if (v == imageBack) {
            setResult(RESULT_CANCELED);
            finish();
        }
        if (v == imageDone) {
            showProgressSheet(this);

        }
    }

    @Override
    public void onSuccess(Object data, RequestObject requestObject) {
        if (data != null && requestObject != null) {

            objectArrayList.clear();
            DataObject dataObject = (DataObject) data;

            if (requestObject.getConnection() == Constant.CONNECTION.RIDER_DOCUMENT) {
                for (int i = 0; i < dataObject.getObjectArrayList().size(); i++) {
                    objectArrayList.add(new PictureObject()
                            .setId(dataObject.getObjectArrayList().get(i).getRider_document_id())
                            .setPicture(dataObject.getObjectArrayList().get(i).getRider_document())
                            .setStatus(dataObject.getObjectArrayList().get(i).getDocument_status())
                            .setBase64(false));
                }

            }

            pictureUploaderAdapter.notifyDataSetChanged();

        }
    }

    @Override
    public void onError(String data, RequestObject requestObject) {

        objectArrayList.clear();
        objectArrayList.add(new EmptyObject()
                .setTitle(Utility.getStringFromRes(this, R.string.no_document))
                .setDescription(Utility.getStringFromRes(this, R.string.no_document_tagline))
                .setPlaceHolderIcon(R.drawable.em_no_picture));
        pictureUploaderAdapter.notifyDataSetChanged();

    }

    /**
     * <p>It is used to convert Pictures into Json Array</p>
     *
     * @return
     */
    private JSONArray convertPicturesIntoJsonArray() {

        JSONArray jsonArray = new JSONArray();

        for (int i = 0; i < objectArrayList.size(); i++) {

            PictureObject pictures = (PictureObject) objectArrayList.get(i);
            if (!pictures.isNewlyAdded())
                continue;

            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("picture", pictures.getPicture());
            } catch (JSONException e) {
                e.printStackTrace();
            }

            jsonArray.put(jsonObject);

        }

        return jsonArray;
    }


    /**
     * <p>Used to show bottom sheet dialog for Cart Alert</p>
     */
    private void showProgressSheet(final Context context) {

        View view = getLayoutInflater().inflate(R.layout.progress_sheet_layout, null);

        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
        bottomSheetDialog.setContentView(view);
        bottomSheetDialog.show();

        management.sendRequestToServer(new RequestObject()
                .setContext(getApplicationContext())
                .setJson(getAddingPictureJson(prefObject.getUserId()))
                .setConnection(Constant.CONNECTION.RIDER_ADD_DOCUMENT)
                .setConnectionType(Constant.CONNECTION_TYPE.UI)
                .setConnectionCallback(new ConnectionCallback() {
                    @Override
                    public void onSuccess(Object data, RequestObject requestObject) {

                        if (bottomSheetDialog != null && bottomSheetDialog.isShowing()) {
                            bottomSheetDialog.dismiss();

                            objectArrayList.clear();
                            DataObject dataObject = (DataObject) data;

                            for (int i = 0; i < dataObject.getObjectArrayList().size(); i++) {
                                objectArrayList.add(new PictureObject()
                                        .setId(dataObject.getObjectArrayList().get(i).getRider_document_id())
                                        .setPicture(dataObject.getObjectArrayList().get(i).getRider_document())
                                        .setStatus(dataObject.getObjectArrayList().get(i).getDocument_status())
                                        .setBase64(false));


                            }

                            imageDone.setVisibility(View.GONE);
                            pictureUploaderAdapter.notifyDataSetChanged();

                        }
                    }

                    @Override
                    public void onError(String data, RequestObject requestObject) {
                        if (bottomSheetDialog != null && bottomSheetDialog.isShowing())
                            bottomSheetDialog.dismiss();

                        imageDone.setVisibility(View.GONE);
                    }
                }));

    }


}
