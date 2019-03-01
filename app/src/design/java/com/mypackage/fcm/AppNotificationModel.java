package com.mypackage.fcm;

import android.os.Bundle;

import com.fcm.NotificationModal;
import com.google.firebase.messaging.RemoteMessage;
import com.mypackage.fcm.AppNotificationMessagingService;

/**
 * Created by Azher on 1/5/18.
 */

public class AppNotificationModel extends NotificationModal {

    public AppNotificationModel(RemoteMessage remoteMessage) {
        super(remoteMessage);
    }

    public AppNotificationModel(Bundle bundle) {
        super(bundle);
    }

    public String getNotiType() {
        return getDataFromKey(AppNotificationMessagingService.KEY_NOTIFICATION_TYPE);
    }

    public String getTitle() {
        return getDataFromKey(AppNotificationMessagingService.KEY_NOTIFICATION_TITLE);
    }

    public String getMessage() {
        return getDataFromKey(AppNotificationMessagingService.KEY_NOTIFICATION_MESSAGE);
    }

    public String getProductId() {
        return getDataFromKey(AppNotificationMessagingService.KEY_NOTIFICATION_PRODUCT_ID);
    }

    public String getProductType() {
        return getDataFromKey(AppNotificationMessagingService.KEY_NOTIFICATION_PRODUCT_TYPE);
    }


}
