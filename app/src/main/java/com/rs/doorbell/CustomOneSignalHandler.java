/*
package com.ozzy.meal4u;

import android.content.Context;
import android.content.Intent;

import com.google.gson.Gson;
import com.ozzy.meal4u.ActivityUtil.CustomNotificationOpener;
import com.ozzy.meal4u.ActivityUtil.Splash;
import com.ozzy.meal4u.ConstantUtil.Constant;
import com.ozzy.meal4u.JsonUtil.NotificationUtil.AdditionalData;
import com.ozzy.meal4u.JsonUtil.NotificationUtil.NotificationJson;
import com.ozzy.meal4u.Utility.Utility;
import com.onesignal.OSNotificationOpenResult;
import com.onesignal.OneSignal;

public class CustomOneSignalHandler implements OneSignal.NotificationOpenedHandler {
    private Context context;

    public CustomOneSignalHandler(Context context) {
        this.context = context;
    }

    @Override
    public void notificationOpened(OSNotificationOpenResult result) {

        Utility.Logger(CustomOneSignalHandler.class.getName(), "Status = " + result.toJSONObject().toString());

        Gson gson = new Gson();
        NotificationJson notificationJson = gson.fromJson(result.toJSONObject().toString(), NotificationJson.class);
        AdditionalData additionalData = notificationJson.getNotification().getPayload().getAdditionalData();

        if (additionalData != null) {

            String postId = notificationJson.getNotification().getPayload().getAdditionalData().getPostId();
            Utility.Logger(CustomOneSignalHandler.class.getName(), "Post ID = " + postId);

            if (Utility.isEmptyString(postId)) {

                Utility.Logger(CustomOneSignalHandler.class.getName(), "Empty Post ID");
                Intent intent = new Intent(context, Splash.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

                return;
            }


            Intent intent = new Intent(context, CustomNotificationOpener.class);
            Utility.Logger(CustomOneSignalHandler.class.getName(),intent.toString());
            intent.putExtra(Constant.IntentKey.ARTIST_ID, postId);
            intent.putExtra(Constant.IntentKey.POST_TYPE, Constant.PostType.POST_TYPE);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);

        } else {

            Utility.Logger(CustomOneSignalHandler.class.getName(), "Empty Data");
            Intent intent = new Intent(context, Splash.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);

        }


    }


}
*/
