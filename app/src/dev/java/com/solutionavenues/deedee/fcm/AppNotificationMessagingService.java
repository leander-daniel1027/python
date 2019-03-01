package com.solutionavenues.deedee.fcm;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import com.fcm.NotificationMessagingService;
import com.fcm.NotificationModal;
import com.google.firebase.messaging.RemoteMessage;
import com.solutionavenues.deedee.MyApplication;
import com.solutionavenues.deedee.R;
import com.solutionavenues.deedee.ui.dashboard.DashboardActivity;


/**
 * @author Manish Kumar
 * @since 14/12/17
 */


public class AppNotificationMessagingService extends NotificationMessagingService implements AppNotificationType {

    public static final String KEY_NOTIFICATION_TITLE = "title";
    public static final String KEY_NOTIFICATION_MESSAGE = "message";
    public static final String KEY_NOTIFICATION_PRODUCT_ID = "product_id";
    public static final String KEY_NOTIFICATION_PRODUCT_TYPE = "product_type";
    public static final String KEY_NOTIFICATION_TYPE = "notification_type";

    @Override
    public void pushMessageReceived(NotificationModal notificationModal) {
        super.pushMessageReceived(notificationModal);
        AppNotificationModel appNotificationModel = (AppNotificationModel) notificationModal;
        if (appNotificationModel != null) {
            if (isAppIsInBackground()) {
                sendNotification(appNotificationModel);
            } else {
                MyApplication.getInstance().getPushNotificationHelper().
                        triggerNotificationListener(notificationModal);
            }
        }
    }

    @Override
    public NotificationModal getNotificationModal(RemoteMessage remoteMessage) {
        return new AppNotificationModel(remoteMessage);
    }

    private void sendNotification(AppNotificationModel notificationModal) {
        String title = notificationModal.getTitle();
        String message = notificationModal.getMessage();

        Intent intent = new Intent(this, DashboardActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),
                        R.mipmap.ic_launcher))
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            notificationBuilder.setColor(getResources().getColor(R.color.colorPrimaryDark));
        }

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0, notificationBuilder.build());
    }
}
