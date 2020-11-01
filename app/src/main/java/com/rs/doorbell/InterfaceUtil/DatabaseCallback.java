package com.rs.doorbell.InterfaceUtil;

import android.net.Uri;

import com.rs.doorbell.ObjectUtil.RequestObject;

public interface DatabaseCallback {

    void onSuccess(Uri data, RequestObject requestObject);

    void onError(String data, RequestObject requestObject);

}
