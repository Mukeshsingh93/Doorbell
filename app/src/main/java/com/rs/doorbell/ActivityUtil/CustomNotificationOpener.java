/*
package com.ozzy.meal4u.ActivityUtil;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.ozzy.meal4u.ConstantUtil.Constant;
import com.ozzy.meal4u.InterfaceUtil.ConnectionCallback;
import com.ozzy.meal4u.ManagementUtil.Management;
import com.ozzy.meal4u.ObjectUtil.ArtistHeaderObject;
import com.ozzy.meal4u.ObjectUtil.DataObject;
import com.ozzy.meal4u.ObjectUtil.PrefObject;
import com.ozzy.meal4u.ObjectUtil.RequestObject;
import com.ozzy.meal4u.ObjectUtil.WallpaperHeaderObject;
import com.ozzy.meal4u.R;
import com.ozzy.meal4u.Utility.Utility;

import org.json.JSONException;
import org.json.JSONObject;

public class CustomNotificationOpener extends AppCompatActivity implements ConnectionCallback {

    private String TAG = CustomNotificationOpener.class.getName();
    private String postId = "0";
    private String postType;
    private Management management;
    private Constant.CONNECTION connection;
    private Constant.CONNECTION_TYPE connectionType;
    private String functionality;
    private boolean isSendRequest = false;
    private String postName;
    private String userId;
    private String json;
    private PrefObject prefObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deeplink_viewer);

        getIntentData(); //Retrieve Intent Data
        initUI(); //Initialize UI

    }


    */
/**
     * <p>It is used to retrieve Intent Data</p>
     *//*

    private void getIntentData() {

        Intent mainIntent = getIntent();
        postId = mainIntent.getStringExtra(Constant.IntentKey.ARTIST_ID);
        postType = mainIntent.getStringExtra(Constant.IntentKey.POST_TYPE);

    }


    */
/**
     * <p>It is used to retrieve Intent Data</p>
     *//*

    private void initUI() {

        Utility.Logger(TAG,"Working");
        management = new Management(getApplicationContext());
        prefObject = management.getPreferences(new PrefObject()
                .setRetrieveParallaxMode(true));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED
                    || checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED
                    || checkSelfPermission(Manifest.permission.ACCESS_NETWORK_STATE) == PackageManager.PERMISSION_DENIED
                    || checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED
            ) {

                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                        , Manifest.permission.ACCESS_NETWORK_STATE
                        , Manifest.permission.CAMERA

                }, Constant.RequestCode.PERMISSION_REQUEST_CODE);

            } else {

                checkPreference();

            }

        } else {


            checkPreference();

        }


    }

    */
/**
     * <p>It is used to convert Object into Json</p>
     *
     * @param
     * @return
     *//*

    private String getJson(String functionality, String postID) {
        String json = null;

        // 1. build jsonObject
        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.accumulate("functionality", functionality);
            jsonObject.accumulate("post_id", postID);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        // 2. convert JSONObject to JSON to String
        json = jsonObject.toString();
        Utility.Logger(TAG, "JSON " + json);

        return json;
    }


    */
/**
     * <p>It is used to convert Object into Json</p>
     *
     * @param
     * @return
     *//*

    private String getCoinJson(String functionality, String coinType, String userID, String coin) {
        String json = null;

        // 1. build jsonObject
        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.accumulate("functionality", functionality);
            jsonObject.accumulate("coinType", coinType);
            jsonObject.accumulate("user_id", userID);
            jsonObject.accumulate("coin", coin);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        // 2. convert JSONObject to JSON to String
        json = jsonObject.toString();
        Utility.Logger("JSON", json);

        return json;
    }


    @Override
    public void onSuccess(Object data, RequestObject requestObject) {

        if (data != null && requestObject != null) {

            DataObject dtObject = (DataObject) data;

            if (dtObject.getWallpaperList().size() <= 0)
                return;

            DataObject dataObject = dtObject.getWallpaperList().get(0);

            if (requestObject.getConnection() == Constant.CONNECTION.SPECIFIC_AUTHOR_DETAIL) {

                ArtistHeaderObject artistHeaderObject = new ArtistHeaderObject()
                        .setId(dataObject.getId()).setAuthorName(dataObject.getTitle())
                        .setAuthorWork(dataObject.getAuthorWork()).setAuthorDescription(dataObject.getAuthorDescription())
                        .setPostCount(dataObject.getPostCount()).setDownloadCount(dataObject.getDownloadCount())
                        .setReviewCount(dataObject.getReviewCount()).setAuthorPicture(dataObject.getOriginalUrl())
                        .setLoginType(dataObject.getLoginType());

                Intent intent = new Intent(getApplicationContext(), Author.class);
                intent.putExtra(Constant.IntentKey.ARTIST_DETAIL, artistHeaderObject);
                intent.putExtra(Constant.IntentKey.BACK_ACTION, true);
                startActivity(intent);
                finish();

            } else if (requestObject.getConnection() == Constant.CONNECTION.SPECIFIC_POST) {

                WallpaperHeaderObject bookHeaderObject = new WallpaperHeaderObject()
                        .setPostId(dataObject.getId()).setPostName(dataObject.getTitle())
                        .setPostDescription(dataObject.getDescription()).setPostAuthorName(dataObject.getArtistName())
                        .setPostDownloadCount(dataObject.getDownloads()).setPostReviewCount(dataObject.getComments())
                        .setPostTag(dataObject.getTags()).setPostRating(dataObject.getRating()).setPostType(dataObject.getPostType())
                        .setPostImage(dataObject.getOriginalUrl()).setPostLowResImage(dataObject.getCoverUrl()).setPostUrl(dataObject.getPostUrl())
                        .setArtistDetail(dataObject.getArtistDetail());


                Intent intent = null;

                if (prefObject.isParallaxMode())

                    intent = new Intent(getApplicationContext(), RestaurantDetail.class);   //Todo Parallax RestaurantDetail
                else
                    intent = new Intent(getApplicationContext(), RestaurantDetail.class);

                intent.putExtra(Constant.IntentKey.BACK_ACTION, true);
                intent.putExtra(Constant.IntentKey.RESTAURANT_DETAIL, bookHeaderObject);
                startActivity(intent);
                finish();

            }

        }
    }

    @Override
    public void onError(String data, RequestObject requestObject) {
        startActivity(new Intent(getApplicationContext(), Base.class));
        finish();
    }

    */
/**
     * <p>It is used to check preference</p>
     *//*

    private void checkPreference() {


        if (postType.equalsIgnoreCase(Constant.PostType.POST_TYPE)) {

            functionality = "retrieve_specific_post";
            connection = Constant.CONNECTION.SPECIFIC_POST;
            json = getJson(functionality, postId);
            connectionType = Constant.CONNECTION_TYPE.UI;
            isSendRequest = true;

        } else if (postType.equalsIgnoreCase(Constant.PostType.CATEGORY_TYPE)) {

            isSendRequest = false;

            Intent intent = new Intent(getApplicationContext(), CategorizedWallpaper.class);
            intent.putExtra(Constant.IntentKey.CATEGORY, postName);
            intent.putExtra(Constant.IntentKey.CATEGORY_ID, postId);
            intent.putExtra(Constant.IntentKey.BACK_ACTION, true);
            startActivity(intent);
            finish();

            return;

        } else if (postType.equalsIgnoreCase(Constant.PostType.REFER_USER)) {

            functionality = "manage_coin";
            connection = Constant.CONNECTION.MANAGE_COIN;
            json = getCoinJson(functionality, "add_coin", userId, Constant.Credentials.REFER_CREDIT);
            connectionType = Constant.CONNECTION_TYPE.BACKGROUND;
            //isSendRequest = true;

        } else if (postType.equalsIgnoreCase(Constant.PostType.AUTHOR_TYPE)) {

            functionality = "specific_author_detail";
            connection = Constant.CONNECTION.SPECIFIC_AUTHOR_DETAIL;
            json = getJson(functionality, postId);
            connectionType = Constant.CONNECTION_TYPE.UI;
            //isSendRequest = true;

        }


        //Send request to Server

        management.sendRequestToServer(new RequestObject()
                .setContext(getApplicationContext())
                .setJson(json)
                .setConnection(connection)
                .setConnectionType(connectionType)
                .setConnectionCallback(this));


        if (postType.equalsIgnoreCase(Constant.PostType.REFER_USER)) {
            startActivity(new Intent(getApplicationContext(), Base.class));
        }


    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);


        if (requestCode == Constant.RequestCode.PERMISSION_REQUEST_CODE) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                    Utility.Toaster(this, Utility.getStringFromRes(this, R.string.external_storage_permission), Toast.LENGTH_SHORT);
                    return;
                }

                if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                    Utility.Toaster(this, Utility.getStringFromRes(this, R.string.external_storage_permission), Toast.LENGTH_SHORT);
                    return;
                }

                if (checkSelfPermission(Manifest.permission.ACCESS_NETWORK_STATE) == PackageManager.PERMISSION_DENIED) {
                    Utility.Toaster(this, Utility.getStringFromRes(this, R.string.read_phone_state_permission), Toast.LENGTH_SHORT);
                    return;
                }

                if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED) {
                    Utility.Toaster(this, Utility.getStringFromRes(this, R.string.need_camera_permission), Toast.LENGTH_SHORT);
                    return;
                }


                checkPreference();


            }


        }


    }


}
*/
