package com.fcm;

import android.util.Log;

import com.solutionavenues.deedee.BuildConfig;
import com.solutionavenues.deedee.MyApplication;

import java.util.ArrayList;
import java.util.List;




public class PushNotificationHelper {


    MyApplication myApplication;
    List<PushNotificationHelperListener> notificationHelperListenerList = new ArrayList<>();

    public PushNotificationHelper(MyApplication myApplication) {
        this.myApplication = myApplication;
    }

    public void onDestroy() {
        clearNotificationHelperListener();
    }

    public void printLog(String msg) {
        if (msg == null) return;
        if (BuildConfig.DEBUG)
            Log.e(getClass().getSimpleName(), msg);
    }

    public void addNotificationHelperListener(PushNotificationHelperListener notificationHelperListener) {
        if (!this.notificationHelperListenerList.contains(notificationHelperListener))
            this.notificationHelperListenerList.add(notificationHelperListener);
    }

    public void removeNotificationHelperListener(PushNotificationHelperListener notificationHelperListener) {
        this.notificationHelperListenerList.remove(notificationHelperListener);
    }

    public void clearNotificationHelperListener() {
        this.notificationHelperListenerList.clear();
    }

    public synchronized void triggerNotificationListener(NotificationModal notificationModal) {
        if (this.notificationHelperListenerList == null) return;

        for (PushNotificationHelperListener pushNotificationHelperListener : notificationHelperListenerList) {
            pushNotificationHelperListener.onPushNotificationReceived(notificationModal);
        }
    }

    public interface PushNotificationHelperListener {
        void onPushNotificationReceived(NotificationModal notificationModal);
    }
}
