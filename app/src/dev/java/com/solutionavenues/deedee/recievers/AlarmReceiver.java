package com.solutionavenues.deedee.recievers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.solutionavenues.deedee.MyApplication;
import com.solutionavenues.deedee.locationservice.AppBaseLocationService;

import java.util.Calendar;

/**
 * Created by Azher on 12/5/18.
 */

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Calendar calSet = Calendar.getInstance();
        int currentHour = calSet.get(Calendar.HOUR_OF_DAY);
        Log.e("AlarmReceiver", "onReceive: " + currentHour);
        if (currentHour == MyApplication.getInstance().hourArr[0]) {
            if (!AppBaseLocationService.isRunning(context)) {
                MyApplication.getInstance().startLocationService();
            }
        } else if (currentHour == MyApplication.getInstance().hourArr[1]) {
            if (AppBaseLocationService.isRunning(context)) {
                MyApplication.getInstance().stopLocationService();
            }
        }
        MyApplication.getInstance().prepareNextAlarm();
    }


}
