package com.solutionavenues.deedee.locationservice;

import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.os.IBinder;
import android.util.Log;

import com.google.android.gms.location.LocationListener;
import com.solutionavenues.deedee.util.GoogleApiClientHelper;


public class LocationService extends Service implements LocationListener {

    private static final String TAG = LocationService.class.getSimpleName();
    private GoogleApiClientHelper googleApiClientHelper;

    public LocationService() {

    }

    public GoogleApiClientHelper getGoogleApiClientHelper() {
        return googleApiClientHelper;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        googleApiClientHelper = new GoogleApiClientHelper(this);
    }

    @Override
    public IBinder onBind(Intent intent) {
        printLog("onBind");
        return null;
    }

    @Override
    public void onDestroy() {
        printLog("onDestroy");
        super.onDestroy();
    }


    public void stopService() {
        printLog("stopService");
        stopSelf();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        printLog("onStartCommand: " + intent);
        return Service.START_STICKY;

    }

    @Override
    public void onLocationChanged(Location location) {
        printLog("onLocationChanged");

    }

    private void printLog(String msg) {
            Log.e(TAG, msg );
    }

}
