package com.solutionavenues.deedee;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.evernote.android.job.JobManager;
import com.fcm.PushNotificationHelper;
import com.google.android.gms.common.ConnectionResult;
import com.solutionavenues.deedee.database.DatabaseHelper;
import com.solutionavenues.deedee.interfaces.OnLocationUpdate;
import com.solutionavenues.deedee.locationservice.AppBaseLocationService;
import com.solutionavenues.deedee.perfrences.AppPrefs;
import com.solutionavenues.deedee.perfrences.UserPrefs;
import com.solutionavenues.deedee.recievers.AlarmReceiver;
import com.solutionavenues.deedee.ui.dashboard.DashboardActivity;
import com.solutionavenues.deedee.util.NoteJobCreator;
import com.solutionavenues.deedee.util.Utils;

import org.acra.ACRA;
import org.acra.ReportField;
import org.acra.ReportingInteractionMode;
import org.acra.annotation.ReportsCrashes;

import java.text.SimpleDateFormat;
import java.util.Calendar;


@ReportsCrashes(
        mailTo = "sigmatechinfosolutions020@gmail.com",
        customReportContent = {ReportField.APP_VERSION_CODE, ReportField.APP_VERSION_NAME, ReportField.ANDROID_VERSION, ReportField.PHONE_MODEL, ReportField.CUSTOM_DATA, ReportField.STACK_TRACE, ReportField.LOGCAT},
        mode = ReportingInteractionMode.TOAST,
        resToastText = R.string.crash_toast_text)
public class MyApplication extends Application {

    private static final String TAG = "MyApplication";
    private static MyApplication instance;
    public AppPrefs appPrefs;


    public OnLocationUpdate getOnLocationUpdate() {
        return onLocationUpdate;
    }

    public void setOnLocationUpdate(OnLocationUpdate onLocationUpdate) {
        this.onLocationUpdate = onLocationUpdate;
    }

    public OnLocationUpdate onLocationUpdate;

    public void setDashboardActivity(DashboardActivity dashboardActivity) {
        this.dashboardActivity = dashboardActivity;
    }

    public DashboardActivity dashboardActivity;

    public UserPrefs getUserPrefs() {
        return userPrefs;
    }

    public void setUserPrefs(UserPrefs userPrefs) {
        this.userPrefs = userPrefs;
    }

    public UserPrefs userPrefs;
    private PushNotificationHelper pushNotificationHelper;
    public int hourArr[] = {9, 20};


    public DatabaseHelper getDatabasehelper() {
        if (databasehelper == null) {
            databasehelper = new DatabaseHelper(this);
            databasehelper.createDatabase();
        }
        return databasehelper;
    }

    public DatabaseHelper databasehelper;

    public AppPrefs getAppPrefs() {
        return appPrefs;
    }


    public static MyApplication getInstance() {
        return instance;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        JobManager.create(this).addJobCreator(new NoteJobCreator());
        pushNotificationHelper = new PushNotificationHelper(this);
        appPrefs = new AppPrefs(this);
        userPrefs = new UserPrefs(this);
//        Fabric.with(this, new Crashlytics());
        ACRA.init(this);
        // the following line is important

    }


    public void startLocationService() {
        if (Utils.getGooglePlayServicesAvailableStatus(this) != ConnectionResult.SUCCESS) return;
        try {
            Intent intent = new Intent(this, AppBaseLocationService.class);
            startService(intent);
            Log.e(TAG, "startLocationService: " + "Location Service Started");
        } catch (IllegalStateException e) {

        }

    }

    public void stopLocationService() {
        try {
            Intent intent = new Intent(this, AppBaseLocationService.class);
            stopService(intent);
        } catch (IllegalStateException e) {

        }

    }


    public synchronized static String getTimeString(long timestamp) {
        Calendar d = Calendar.getInstance();
        d.setTimeInMillis(timestamp);
        if (d != null) {
            SimpleDateFormat daytimeformate = new SimpleDateFormat("hh:mm a");
            return daytimeformate.format(d.getTime());
        }
        return "";
    }

    public synchronized static long convertToDateOnlyTime(long timestamp) {
        Calendar d = Calendar.getInstance();
        d.setTimeInMillis(timestamp);
        d.set(Calendar.HOUR_OF_DAY, 0);
        d.set(Calendar.MINUTE, 0);
        d.set(Calendar.SECOND, 0);
        d.set(Calendar.MILLISECOND, 0);
        return d.getTimeInMillis();
    }

    public PushNotificationHelper getPushNotificationHelper() {
        return pushNotificationHelper;
    }

    public static synchronized void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public void prepareNextAlarm() {
        Calendar calendar = Calendar.getInstance();
        int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
        int nextHour = 0;
        if (currentHour <= hourArr[0]) {
            nextHour = hourArr[1];
            calendar.set(Calendar.HOUR_OF_DAY, nextHour);
            setAlarm(calendar, nextHour);
        } else if (currentHour <= hourArr[1]) {
            nextHour = hourArr[0];
            calendar.set(Calendar.HOUR_OF_DAY, nextHour);
            setAlarm(calendar, nextHour);
        }

    }

    public void setAlarm(Calendar targetCal, int alarmId) {
        targetCal.set(Calendar.MINUTE, 0);
        targetCal.set(Calendar.SECOND, 0);
        Intent intent = new Intent(this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                this, alarmId, intent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, targetCal.getTimeInMillis(),
                pendingIntent);
        Log.e("App", "setAlarm: " + targetCal.getTime());
    }


}
