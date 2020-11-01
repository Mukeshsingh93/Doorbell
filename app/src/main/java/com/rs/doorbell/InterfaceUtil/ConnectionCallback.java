package com.rs.doorbell.InterfaceUtil;

import com.rs.doorbell.ObjectUtil.RequestObject;

public interface ConnectionCallback {

    void onSuccess(Object data, RequestObject requestObject);

    void onError(String data, RequestObject requestObject);


}
