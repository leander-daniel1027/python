package com.solutionavenues.deedee.locationservice;

import android.Manifest;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.helper.ConnectionDetector;
import com.solutionavenues.deedee.MyApplication;
import com.solutionavenues.deedee.R;
import com.solutionavenues.deedee.model.response.BaseWebResponseModel;
import com.solutionavenues.deedee.rest.ApiHitListener;
import com.solutionavenues.deedee.rest.ApiIds;
import com.solutionavenues.deedee.rest.RestClient;
import com.solutionavenues.deedee.util.Utils;


public class LocationMonitoringService extends Service implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener, ApiHitListener {


    private static long LOCATION_INTERVAL = 15 * 60 * 1000;
    private static long FASTEST_LOCATION_INTERVAL = 15 * 60 * 1000;
    private static final String TAG = LocationMonitoringService.class.getSimpleName();
    GoogleApiClient mLocationClient;
    LocationRequest mLocationRequest = new LocationRequest();
    RestClient mRestClient;

    public static final String ACTION_LOCATION_BROADCAST = LocationMonitoringService.class.getName() + "LocationBroadcast";
    public static final String EXTRA_LATITUDE = "extra_latitude";
    public static final String EXTRA_LONGITUDE = "extra_longitude";

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mLocationClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        String location_update_time = "15";
        if (MyApplication.getInstance().getUserPrefs().getLoggedInUser() != null) {
            location_update_time = MyApplication.getInstance().getUserPrefs().getLoggedInUser().getLo_time();
        }
        Utils.printLog("location_update_time", location_update_time);
      //  location_update_time = "1";
        if (!TextUtils.isEmpty(location_update_time)) {
            LOCATION_INTERVAL = Long.parseLong(location_update_time) * 60 * 1000;
            FASTEST_LOCATION_INTERVAL = Long.parseLong(location_update_time) * 60 * 1000;
        }

        mLocationRequest.setInterval(LOCATION_INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_LOCATION_INTERVAL);


        int priority = LocationRequest.PRIORITY_HIGH_ACCURACY; //by default
        //PRIORITY_BALANCED_POWER_ACCURACY, PRIORITY_LOW_POWER, PRIORITY_NO_POWER are the other priority modes


        mLocationRequest.setPriority(priority);
        mLocationClient.connect();

        //Make it stick to the notification panel so it is less prone to get cancelled by the Operating System.
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /*
     * LOCATION CALLBACKS
     */
    @Override
    public void onConnected(Bundle dataBundle) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

            Log.e(TAG, "== Error On onConnected() Permission not granted");
            //Permission not granted by user so cancel the further execution.

            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mLocationClient, mLocationRequest, this);

        Log.e(TAG, "Connected to Google API");
    }

    /*
     * Called by Location Services if the connection to the
     * location client drops because of an error.
     */
    @Override
    public void onConnectionSuspended(int i) {
        Log.e(TAG, "Connection suspended");
    }


    //to get the location change
    @Override
    public void onLocationChanged(Location location) {
        Log.e(TAG, "Location Changed " + location.getLatitude());
        if (location != null) {
            if (!String.valueOf(location.getLatitude()).equals(MyApplication.getInstance().getAppPrefs().getCurrentLatitude())) {
                MyApplication.getInstance().getAppPrefs().saveCurrentLatitude(String.valueOf(location.getLatitude()));
                MyApplication.getInstance().getAppPrefs().saveCurrentLongitude(String.valueOf(location.getLongitude()));
                if (MyApplication.getInstance().getUserPrefs().getLoggedInUser() != null) {
                    addLocation(location);
                }
            }
            /*if (MyApplication.getInstance().getOnLocationUpdate() != null) {
                MyApplication.getInstance().getOnLocationUpdate().onLocation(location);
            }*/
        }

    }

    private void sendMessageToUI(String lat, String lng) {

        Log.d(TAG, "Sending info...");

        Intent intent = new Intent(ACTION_LOCATION_BROADCAST);
        intent.putExtra(EXTRA_LATITUDE, lat);
        intent.putExtra(EXTRA_LONGITUDE, lng);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);

    }


    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.e(TAG, "Failed to connect to Google API");

    }

    // call to addLocation service
    private void addLocation(Location location) {
        if (mRestClient == null) {
            mRestClient = new RestClient();
        }
        if (ConnectionDetector.isNetAvail(this)) {
            String user_id = String.valueOf(MyApplication.getInstance().getUserPrefs().getLoggedInUser().getId());
            mRestClient.callback(this).addLocation(user_id, String.valueOf(location.getLatitude()), String.valueOf(location.getLongitude()));
        } else {
            Utils.printLog("addLocation: ", getString(R.string.error_internet_connection));
//            Utils.showToast(this, getString(R.string.error_internet_connection));
        }
    }


    @Override
    public void onSuccessResponse(int apiId, Object response) {
        if (apiId == ApiIds.ID_ADD_LOCATION) {
            BaseWebResponseModel responseModel = (BaseWebResponseModel) response;
            if (responseModel != null) {
                if (!responseModel.isError()) {
                    Utils.printLog("addLocation: ", getString(R.string.app_name)+" "+getString(R.string.location_added));
                }
            }
        }
    }

    @Override
    public void onFailResponse(int apiId, String error) {
        Utils.printLog("addLocation: ", getString(R.string.error_in_add_location));
        Utils.showToast(this, getString(R.string.app_name)+" "+getString(R.string.error_internet_connection));
    }
}