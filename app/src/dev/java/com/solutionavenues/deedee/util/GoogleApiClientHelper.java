package com.solutionavenues.deedee.util;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Places;
import com.solutionavenues.deedee.MyApplication;
import com.solutionavenues.deedee.locationservice.LocationService;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Manish Kumar
 * @since 6/9/17
 */


public class GoogleApiClientHelper implements
        GoogleApiClient.OnConnectionFailedListener,
        GoogleApiClient.ConnectionCallbacks,
        LocationListener {

    public static final String TAG = "GoogleApiClientHelper";

   // public static long UPDATE_INTERVAL = 10*60*1000;
    public static long UPDATE_INTERVAL = 10*1000;
   // public static long FASTEST_UPDATE_INTERVAL = 10*60*1000;
    public static long FASTEST_UPDATE_INTERVAL = 10*1000;
    public static final float UPDATE_DISPLACEMENT = 0.0f;


    private GoogleApiClient mGoogleApiClient;
    Context context;
    private LocationRequest mLocationRequest;
    private boolean mRequestingLocationUpdates = false;
    public List<LocationListener> locationListeners = new ArrayList<>();

    public GoogleApiClientHelper(Context context) {
        this.context = context;
        if (context instanceof LocationListener) {
            this.locationListeners.add((LocationListener) context);
        }
        mRequestingLocationUpdates = false;
        createLocationRequest();
        buildGoogleApiClient(context);
    }

    public void addLocationListener(LocationListener locationListener) {
        if (locationListener != null) {
            locationListeners.add(locationListener);
        }
    }

    public void removeLocationListener(LocationListener locationListener) {
        if (locationListener != null) {
            locationListeners.remove(locationListener);
        }
    }

    public void clearLocationListeners() {
        locationListeners.clear();
    }

    public GoogleApiClient getGoogleApiClient() {
        return mGoogleApiClient;
    }

    protected synchronized void buildGoogleApiClient(Context context) {
        printLog("buildGoogleApiClient");
        mGoogleApiClient = new GoogleApiClient.Builder(context)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .addApi(Places.GEO_DATA_API)
                .build();
        mGoogleApiClient.connect();
    }

    protected void createLocationRequest() {
        printLog("createLocationRequest");
        String location_update_time = MyApplication.getInstance().getUserPrefs().getLoggedInUser().getLo_time();
        /*if (!TextUtils.isEmpty(location_update_time)) {
            UPDATE_INTERVAL= Long.parseLong(location_update_time)*60*1000;
            FASTEST_UPDATE_INTERVAL= Long.parseLong(location_update_time)*60*1000;
        }*/
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setInterval(UPDATE_INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL);
        mLocationRequest.setSmallestDisplacement(UPDATE_DISPLACEMENT);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        printLog("onConnected");

        startLocationUpdates();
    }

    @Override
    public void onConnectionSuspended(int i) {
        printLog("onConnectionSuspended i=" + i);
        if (mGoogleApiClient != null) {
            stopLocationUpdates();
            mGoogleApiClient.connect();
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        if (context instanceof LocationService) {
            ((LocationService) context).stopService();
        }
        printLog("onConnectionFailed");
    }

    @Override
    public void onLocationChanged(Location location) {
        if (location != null)
            triggerLocationListeners(location);

    }

    private void triggerLocationListeners(Location location) {
        for (LocationListener listener : locationListeners) {
            if (listener != null) {
                listener.onLocationChanged(location);
            }
        }
    }

    public void startLocationUpdates() {
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()
                && !mRequestingLocationUpdates) {

            if (ActivityCompat.
                    checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            printLog("startLocationUpdates");
            mRequestingLocationUpdates = true;
            try {
                LocationServices.FusedLocationApi.requestLocationUpdates(
                        mGoogleApiClient, mLocationRequest, this);
            } catch (IllegalStateException e) {
                mRequestingLocationUpdates = false;
            }
        }
    }

    public void stopLocationUpdates() {
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()
                && mRequestingLocationUpdates) {
            printLog("stopLocationUpdates");
            mRequestingLocationUpdates = false;
            LocationServices.FusedLocationApi.removeLocationUpdates(
                    mGoogleApiClient, this);
        }
    }

    public void destroy() {
        stopLocationUpdates();
    }

    private void printLog(String msg) {
        if (msg != null) {
            Log.e(TAG, msg);
        }
    }

    public Location getLatestLocation() {
        if (ActivityCompat.checkSelfPermission(context,
                Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context,
                Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            return null;
        }
        return LocationServices.FusedLocationApi
                .getLastLocation(mGoogleApiClient);
    }

    public boolean isConnected() {
        return mGoogleApiClient != null && mGoogleApiClient.isConnected();
    }

    public LocationRequest getmLocationRequest() {
        return mLocationRequest;
    }
}
