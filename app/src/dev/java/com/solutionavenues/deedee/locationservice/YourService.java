package com.solutionavenues.deedee.locationservice;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.UUID;


public class YourService extends Service implements ApiHitListener {
    private static final long PROVIDER_GPS_UPDATE_TIME_INTERVAL = 1000 * 5;
    private static final String TAG = "LocationService";
    GoogleApiClient mGoogleApiClient;
    private NotificationManager m_notificationManager = null;

    @Override
    public void onCreate() {
        super.onCreate();
        setGoogleApi();
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private void startMyOwnForeground() {
        String NOTIFICATION_CHANNEL_ID = "com.solutionavenues.deedee";
        String channelName = "DeeDee scanning in progress";
        NotificationChannel chan = new NotificationChannel(NOTIFICATION_CHANNEL_ID, channelName, NotificationManager.IMPORTANCE_NONE);
        chan.setLightColor(Color.BLUE);
        chan.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);

        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.createNotificationChannel(chan);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);
        Notification notification = notificationBuilder.setOngoing(true)
                .setContentTitle(getText(R.string.app_name))
                .build();
        startForeground(1, notification);
    }


    @Override
    public int onStartCommand(final Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        m_notificationManager = (NotificationManager) getApplication().getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startMyOwnForeground();
        }
        return START_STICKY;
    }

    private void setGoogleApi() {
        // we build google api client
        mGoogleApiClient = new GoogleApiClient.Builder(this).
                addApi(LocationServices.API).
                addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                    @Override
                    public void onConnected(@Nullable Bundle bundle) {
                        setFuseApi();
                    }

                    @Override
                    public void onConnectionSuspended(int i) {

                    }
                }).
                addOnConnectionFailedListener(new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

                    }
                }).build();

        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
    }

    private void setFuseApi() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        if (location != null) {
            Log.e("setFuseApi", "Latitude : " + location.getLatitude() + "\nLongitude : " + location.getLongitude());
        } else {
            Log.e(TAG, "setFuseApi: no location found");
        }
        startLocationUpdates();
    }

    private void startLocationUpdates() {
        Log.e(TAG, "startLocationUpdates: ");
        LocationRequest locationRequestTimeInterval = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(PROVIDER_GPS_UPDATE_TIME_INTERVAL)
                .setFastestInterval(PROVIDER_GPS_UPDATE_TIME_INTERVAL);

        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "You need to enable permissions to display location !", Toast.LENGTH_SHORT).show();
        }

        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, locationRequestTimeInterval, new
                LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
                        // Handle your Time based updates
                        Log.e(TAG, "Location changed Time based");
                        if (location != null) {
                            Log.d(TAG, "== location != null");
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                            getAddress(latitude, longitude);
                            if (latitude > 0 && longitude > 0) {
                                Log.e("Sunil : ", "time : " + Calendar.getInstance().getTime() + " latitude = " + latitude + " longitude = " + longitude);
                                if (MyApplication.getInstance().getUserPrefs().getLoggedInUser() != null) {
                                    addLocation(location);
                                } else {
                                    stopedService();
                                }
                            }
                        }
                    }
                });
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public double latitude = 0f, longitude = 0f;
    public String city = "", location = "", url = "";

    private void getAddress(double latitude, double longitude) {
        try {
            Geocoder geocoder;
            List<Address> addresses;
            geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
            addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            city = addresses.get(0).getLocality();
            location = addresses.get(0).getAddressLine(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void stopedService() {
        if (mGoogleApiClient != null)
            mGoogleApiClient.disconnect();
        stopSelf();
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

}