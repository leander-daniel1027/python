package com.solutionavenues.deedee.ui.dashboard;

import android.app.ActivityManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.base.BaseFragment;
import com.solutionavenues.deedee.MyApplication;
import com.solutionavenues.deedee.R;
import com.solutionavenues.deedee.appBase.AppBaseActivity;
import com.solutionavenues.deedee.helpers.ToolBarHelper;
import com.solutionavenues.deedee.interfaces.OnBackClickListener;
import com.solutionavenues.deedee.locationservice.AppBaseLocationService;
import com.solutionavenues.deedee.locationservice.YourService;
import com.solutionavenues.deedee.rest.RestClient;
import com.solutionavenues.deedee.ui.dashboard.home.HomeFragment;
import com.solutionavenues.deedee.util.LocationService;
import com.solutionavenues.deedee.util.Utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;

/**
 * Created by Azher on 18/6/18.
 */
public class DashboardActivity extends AppBaseActivity {
    private String TAG = "DashboardActivity";
    private Context context = DashboardActivity.this;
    private boolean isServiceBound = false;
    ToolBarHelper toolBarHelper;
    private AppBaseLocationService locationService;


    public List<OnBackClickListener> backClickListener = new ArrayList<>();

    private YourService mYourService;
    private Intent mServiceIntent;
    private static final int PERMISSION_REQUEST_CODE = 1001;


    public void addBackClickListener(OnBackClickListener onBackClickListener) {
        backClickListener.add(onBackClickListener);
    }

    public void removeBackClickListener(OnBackClickListener onBackClickListener) {
        backClickListener.remove(onBackClickListener);
    }

    Handler backStackHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                BaseFragment baseFragment = getLatestFragment();
                toolBarHelper.onCreateViewFragment(baseFragment);
                baseFragment.viewCreateFromBackStack();
            }
        }
    };
    private RestClient mRestClient;

    @Override
    public int getLayoutResourceId() {
        return R.layout.activity_dashboard;
    }

    @Override
    public void initializeComponent() {
        toolBarHelper = new ToolBarHelper(this);
        mRestClient = new RestClient();
        MyApplication.getInstance().setDashboardActivity(this);
        getFm().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                backStackHandler.removeMessages(1);
                backStackHandler.sendEmptyMessageDelayed(1, 100);
            }
        });
        clearFragmentBackStack();
        addHomeFragment();

    }

    private void setInitialAlarm() {
        Calendar calSet = Calendar.getInstance();
        int currentHour = calSet.get(Calendar.HOUR_OF_DAY);
        if (currentHour < MyApplication.getInstance().hourArr[0]) {
            calSet.set(Calendar.HOUR_OF_DAY, MyApplication.getInstance().hourArr[0]);
            MyApplication.getInstance().setAlarm(calSet, MyApplication.getInstance().hourArr[0]);
        } else if (currentHour < MyApplication.getInstance().hourArr[1]) {
            calSet.set(Calendar.HOUR_OF_DAY, MyApplication.getInstance().hourArr[1]);
            MyApplication.getInstance().setAlarm(calSet, MyApplication.getInstance().hourArr[1]);
            if (!AppBaseLocationService.isRunning(this)) {
                MyApplication.getInstance().startLocationService();
            }
        } else {
            calSet.set(Calendar.HOUR_OF_DAY, MyApplication.getInstance().hourArr[0]);
            calSet.add(Calendar.DATE, 1);
            MyApplication.getInstance().setAlarm(calSet, MyApplication.getInstance().hourArr[0]);
        }
    }

    private void addHomeFragment() {
        HomeFragment fragment = new HomeFragment();
        int enterAnimation = R.anim.translate_right_to_left_anim;
        int exitAnimation = 0;
        int enterAnimationBackStack = 0;
        int exitAnimationBackStack = R.anim.translate_left_to_right_medium;
        changeFragment(fragment, true, false, 0,
                enterAnimation, exitAnimation, enterAnimationBackStack, exitAnimationBackStack, false);
    }

    @Override
    public int getFragmentContainerResourceId() {
        return R.id.main_container;
    }

    @Override
    public void onCreateViewFragment(BaseFragment baseFragment) {
        toolBarHelper.onCreateViewFragment(baseFragment);
    }

    @Override
    public void onDestroyViewFragment(BaseFragment baseFragment) {
        toolBarHelper.onDestroyViewFragment(baseFragment);
    }

    public ToolBarHelper getToolBarHelper() {
        return toolBarHelper;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void triggerOnBackClick() {
        for (OnBackClickListener backClickListener : backClickListener) {
            backClickListener.onBackClick();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

    }


    @Override
    protected void onResume() {
        super.onResume();
        if (checkPermission()) {
            if (checkLocationEnable())
                callService();
        } else {
            requestPermission();
        }
    }

    private boolean checkLocationEnable() {
        LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        boolean gps_enabled = false;
        boolean network_enabled = false;
        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception ex) {
        }

        try {
            network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception ex) {
        }

        if (!gps_enabled && !network_enabled) {
            // notify user
            new AlertDialog.Builder(context)
                    .setMessage(R.string.gps_network_not_enabled)
                    .setPositiveButton(R.string.open_location_settings, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                            context.startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                        }
                    })
                    .setNegativeButton(R.string.cancel, null)
                    .show();
        }

        return gps_enabled && network_enabled;
    }


    private void startLocationService() {
        //Start location sharing service to app server.........
        Intent intent = new Intent(this, LocationService.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(intent);
        } else
            startService(intent);
        Utils.printLog(TAG, "Location Service Start");
    }

    public void stopLocationService() {
        //Stop location sharing service to app server.........
        Intent intent = new Intent(this, LocationService.class);
        stopService(intent);
        Utils.printLog(TAG, "Location Service Stop");
    }


    public void showProgress() {
        displayProgressBar(false);
    }


    // check location service running status
    public boolean isMyServiceRunning() {
        ActivityManager manager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service1 : manager.getRunningServices(Integer.MAX_VALUE)) {

            if ((getPackageName() + ".locationservice.LocationMonitoringService").equals(service1.service.getClassName())) {
                Utils.printLog(TAG, "isMyServiceRunning = true");
//                displayShortToast("Service Running");
                return true;
            }
        }
        Utils.printLog(TAG, "isMyServiceRunning = false");
        return false;
    }


    private boolean checkPermission() {
        int result1 = ContextCompat.checkSelfPermission(getApplicationContext(), ACCESS_FINE_LOCATION);
        int result2 = ContextCompat.checkSelfPermission(getApplicationContext(), ACCESS_COARSE_LOCATION);

        return result1 == PackageManager.PERMISSION_GRANTED
                && result2 == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION}, PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0) {
                    boolean fineLocationAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean crossLocationAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    if (fineLocationAccepted && crossLocationAccepted) {
                        if (checkLocationEnable())
                            callService();
                    }
                }
                break;
        }
    }

    private void callService() {
        mYourService = new YourService();
        mServiceIntent = new Intent(this, mYourService.getClass());
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!isMyServiceRunning2(mYourService.getClass())) {
                    startService(mServiceIntent);
                }
            }
        }, 2000);
    }

    private boolean isMyServiceRunning2(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        if (manager != null) {
            for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
                if (serviceClass.getName().equals(service.service.getClassName())) {
                    Log.e("Service status", "Running");
                    return true;
                }
            }
        }
        Log.e("Service status", "Not running");
        return false;
    }


}
