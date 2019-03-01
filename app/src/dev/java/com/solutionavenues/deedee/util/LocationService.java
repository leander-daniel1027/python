package com.solutionavenues.deedee.util;

import android.Manifest;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.helper.ConnectionDetector;
import com.solutionavenues.deedee.MyApplication;
import com.solutionavenues.deedee.R;
import com.solutionavenues.deedee.model.response.BaseWebResponseModel;
import com.solutionavenues.deedee.rest.ApiHitListener;
import com.solutionavenues.deedee.rest.ApiIds;
import com.solutionavenues.deedee.rest.RestClient;


public class LocationService extends Service implements ApiHitListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        LocationListener {
    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private double currentLatitude = 0, currentLongitude = 0;
    private long INTERVAL = 15 * 60000;

    private static final String TAG = LocationService.class.getSimpleName();

    public LocationService() {
    }

    boolean shouldCall = true;

    @Override
    public void onCreate() {
        super.onCreate();
        try {
            mGoogleApiClient.connect();
        } catch (Exception e) {
        }
        onLocationUpdate();
    }

    private void onLocationUpdate() {
        initGPS();
        showGPSalert();
    }

    private void showGPSalert() {
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(LocationService.this)
                    .addApi(LocationServices.API)
                    .addConnectionCallbacks(LocationService.this)
                    .addOnConnectionFailedListener(LocationService.this).build();
            mGoogleApiClient.connect();
        }
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_LOW_POWER);
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(10000);
        locationRequest.setSmallestDisplacement(2.0f);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);
        //**************************
        builder.setAlwaysShow(true); //LocationService.this is the key ingredient
        //**************************
        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient, builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult result) {
                final Status status = result.getStatus();
                final LocationSettingsStates state = result.getLocationSettingsStates();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        break;
                }
            }
        });
    }

    private void initGPS() {
        mGoogleApiClient = new GoogleApiClient.Builder(LocationService.this)
                .addConnectionCallbacks(LocationService.this)
                .addOnConnectionFailedListener(LocationService.this)
                .addApi(LocationServices.API)
                .build();
        try {
            mGoogleApiClient.connect();
        } catch (Exception e) {
        }
        Utils.printLog("Service", "mGoogleApiClient Initialited");
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(1000)
                .setMaxWaitTime(10000)
                .setSmallestDisplacement(2.0f)// 10 seconds, in milliseconds
                .setFastestInterval(10000); // 10 second, in milliseconds
        Utils.printLog("Service", "mLocationRequest Initialized");
    }

    int NOTIFICATION_ID = 1001;

    public int onStartCommand(Intent intent, int flags, int startId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForeground(NOTIFICATION_ID, getNotification());
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
//        callLogoutAsync();
        Utils.printLog("Service", "onTaskRemoved called");
        try {
            if (mGoogleApiClient.isConnected()) {
                LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient,
                        this);
                mGoogleApiClient.disconnect();
            }
        } catch (Exception e) {
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForeground(NOTIFICATION_ID, getNotification());
        }
        Intent restartService = new Intent(getApplicationContext(),
                LocationService.this.getClass());
        restartService.setPackage(getPackageName());
        PendingIntent restartServicePI = PendingIntent.getService(
                getApplicationContext(), 1, restartService,
                PendingIntent.FLAG_ONE_SHOT);
        //Restart the service once it has been killed android
        AlarmManager alarmService = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        alarmService.set(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime() + 100, restartServicePI);
    }

    @Override
    public void onConnected(Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        Utils.printLog("Service", "onConnected Initialited");
        if (location == null) {
            try {
                Utils.printLog("Service", "onConnected Initialited== null");
                LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, LocationService.this);
            } catch (Exception e) {
            }
        } else {
            currentLatitude = location.getLatitude();
            currentLongitude = location.getLongitude();
            if (location != null) {
                if (!String.valueOf(location.getLatitude()).equals(MyApplication.getInstance().getAppPrefs().getCurrentLatitude())) {
                    MyApplication.getInstance().getAppPrefs().saveCurrentLatitude(String.valueOf(location.getLatitude()));
                    MyApplication.getInstance().getAppPrefs().saveCurrentLongitude(String.valueOf(location.getLongitude()));
                    if (shouldCall) {
                        shouldCall = false;
//                     if (MyApplication.getInstance().getUserPrefs().getLoggedInUser() != null) {
//                         addLocation(location);
//                     }
                        NoteSyncJob.scheduleJob();
                    }
                }
            }
            try {
                Utils.printLog("Service", "onConnected Initialited== null");
                LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, LocationService.this);
            } catch (Exception e) {
            }
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Utils.printLog("Service", "onConnectionFailed Initialited" + connectionResult);
        if (connectionResult.hasResolution()) {
        } else {
            Log.e("Error", "Location services connection failed with code " + connectionResult.getErrorCode());
        }
    }

    @Override
    public void onLocationChanged(Location location) {
//        AppDelegate.showToast(this, "update lacation" + +location.getLatitude() + "," + location.getLongitude());
        currentLatitude = location.getLatitude();
        currentLongitude = location.getLongitude();
        if (location != null) {
            if (!String.valueOf(location.getLatitude()).equals(MyApplication.getInstance().getAppPrefs().getCurrentLatitude())) {
                MyApplication.getInstance().getAppPrefs().saveCurrentLatitude(String.valueOf(location.getLatitude()));
                MyApplication.getInstance().getAppPrefs().saveCurrentLongitude(String.valueOf(location.getLongitude()));
//                if (MyApplication.getInstance().getUserPrefs().getLoggedInUser() != null) {
////                    addLocation(location);
//                }
//                if (shouldCall) {
//                    if (MyApplication.getInstance().getUserPrefs().getLoggedInUser() != null) {
////                        addLocation(location);
//                    }
//                    NoteSyncJob.scheduleJob();
//                }
            }
        }
    }

    RestClient mRestClient;

    private void addLocation(Location location) {
        if (mRestClient == null) {
            mRestClient = new RestClient();
        }
        if (ConnectionDetector.isNetAvail(this)) {
            String user_id = String.valueOf(MyApplication.getInstance().getUserPrefs().getLoggedInUser().getId());
//            mRestClient.callback(this).addLocation(user_id, String.valueOf(currentLatitude), String.valueOf(currentLongitude));
            mRestClient.callback(this).addLocation(user_id, String.valueOf(location.getLatitude()), String.valueOf(location.getLongitude()));
        } else {
            Utils.printLog("addLocation: ", getString(R.string.error_internet_connection));
            Utils.showToast(this, getString(R.string.error_internet_connection));
        }
    }


    @Override
    public void onSuccessResponse(int apiId, Object response) {
        shouldCall = false;
        if (apiId == ApiIds.ID_ADD_LOCATION) {
            BaseWebResponseModel responseModel = (BaseWebResponseModel) response;
            if (responseModel != null) {
                if (!responseModel.isError()) {
                    Utils.printLog("addLocation: ", getString(R.string.app_name) + " " + getString(R.string.location_added));
                }
            }
        }
    }

    @Override
    public void onFailResponse(int apiId, String error) {
        Utils.printLog("addLocation: ", getString(R.string.error_in_add_location));
        Utils.showToast(this, getString(R.string.app_name) + " " + getString(R.string.error_internet_connection));
    }

    public Notification getNotification() {
        String CHANNEL_ID = "my_channel_01";// The id of the channel.
        CharSequence name = CHANNEL_ID;// The user-visible name of the channel.
        int importance = NotificationManager.IMPORTANCE_LOW;
        Notification notification = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle(getString(R.string.app_name))
                .setContentText("")
                .setChannelId(CHANNEL_ID).build();

        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);

            mNotificationManager.createNotificationChannel(mChannel);
        }
// Issue the notification.
        mNotificationManager.notify(1, notification);
        return notification;
    }
}
