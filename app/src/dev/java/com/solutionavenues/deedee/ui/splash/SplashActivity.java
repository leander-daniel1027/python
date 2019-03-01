package com.solutionavenues.deedee.ui.splash;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.solutionavenues.deedee.MyApplication;
import com.solutionavenues.deedee.R;
import com.solutionavenues.deedee.appBase.AppBaseActivity;
import com.solutionavenues.deedee.ui.dashboard.DashboardActivity;
import com.solutionavenues.deedee.ui.main.MainActivity;

public class SplashActivity extends AppBaseActivity {

    private boolean callOnResume = true;
    private int grantedPermissionsCount = 0;
    private AlertDialog mDialog;
    private static final String TAG = SplashActivity.class.getSimpleName();

    Handler splashHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            proceedFurther();
        }
    };

    private void proceedFurther() {
        if (MyApplication.getInstance().getUserPrefs().getLoggedInUser() != null) {
            goToapp();
        } else {
            goTonext();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        for (int i = 0; i < permissions.length; i++) {
            if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                grantedPermissionsCount++;
            }
        }
        if (permissions.length == grantedPermissionsCount) {
            goToForward();
        } else {
            displayResponseDialog(getString(R.string.alert), getString(R.string.if_you_are_not_allowing));
            Log.e("SplashActivity", "onRequestPermissionsResult: " + "permission denied");
        }
        return;

    }

    public void displayResponseDialog(String title, String content) {
        mDialog = new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(content)
                .setIcon(ContextCompat.getDrawable(this, R.mipmap.ic_launcher))
                .setCancelable(false)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        checkPermission();
                        dialogInterface.dismiss();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        finish();
                    }
                })
                .create();
        mDialog.show();
    }


    private void checkPermission() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (checkPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA,
                        Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.READ_CALENDAR, Manifest.permission.WRITE_CALENDAR, Manifest.permission.CALL_PHONE}, 99)) {
                    goToForward();
                }
            }
        });


    }

    @Override
    public int getLayoutResourceId() {
        return R.layout.activity_splash;
    }

    @Override
    public void initializeComponent() {
        checkPermission();
    }


    @Override
    protected void onStop() {
        super.onStop();
        printLog(getClass().getSimpleName() + " onStop");
    }

    private void goToForward() {
        splashHandler.sendEmptyMessageDelayed(1, 2000);
    }

    private void goToapp() {
        sendActivityFinish(SplashActivity.this, DashboardActivity.class);
    }

    private void goTonext() {
        sendActivity(SplashActivity.this, MainActivity.class);
        finish();
    }

   /* @Override
    protected void onResume() {
        super.onResume();
        goToForward();
    }*/


}
