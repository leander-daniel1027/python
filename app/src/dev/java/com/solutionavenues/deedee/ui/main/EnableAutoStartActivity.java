package com.solutionavenues.deedee.ui.main;


import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;

import com.solutionavenues.deedee.MyApplication;
import com.solutionavenues.deedee.R;
import com.solutionavenues.deedee.appBase.AppBaseActivity;
import com.solutionavenues.deedee.ui.splash.SplashActivity;

/**
 * Created by Azher on 18/6/18.
 */
public class EnableAutoStartActivity extends AppBaseActivity {
    @Override
    public int getLayoutResourceId() {
        return R.layout.activity_splash;
    }

    @Override
    public void initializeComponent() {
    }

    @Override
    protected void onResume() {
        super.onResume();
        openBatteryManager();
    }

    public void openBatteryManager() {
        if (MyApplication.getInstance().getAppPrefs().getAutoStart()) {
            goToSplash();
            return;
        }

        MyApplication.getInstance().getAppPrefs().saveAutoStart(true);
        try {
            Intent intent = new Intent();
            String str = Build.MANUFACTURER;

            printLog("SplashActivity", "your devices is " + str);
            if ("xiaomi".equalsIgnoreCase(str)) {
                intent.setComponent(
                        new ComponentName("com.miui.securitycenter",
                                "com.miui.permcenter.autostart.AutoStartManagementActivity"));
            } else if ("oppo".equalsIgnoreCase(str)) {
                intent.setComponent(
                        new ComponentName("com.coloros.safecenter",
                                "com.coloros.safecenter.permission.startup.StartupAppListActivity"));
            } else if ("Letv".equalsIgnoreCase(str)) {
                intent.setComponent(
                        new ComponentName("com.letv.android.letvsafe",
                                "com.letv.android.letvsafe.AutobootManageActivity"));
            } else if ("vivo".equalsIgnoreCase(str)) {
                intent.setComponent(
                        new ComponentName("com.vivo.permissionmanager",
                                "com.vivo.permissionmanager.activity.BgStartUpManagerActivity"));
            } else if ("huawei".equalsIgnoreCase(str) ||
                    "Honor".equalsIgnoreCase(str)) {
                intent.setComponent(
                        new ComponentName("com.huawei.systemmanager",
                                "com.huawei.systemmanager.optimize.process.ProtectActivity"));
            }
            if (getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY).size() > 0) {
                startActivity(intent);
                showToast("Please allow " + getResources().getString(R.string.app_name) + " to run in background. If allowed please go back and continue.");
            } else {
                goToSplash();
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    private void goToSplash() {
        sendActivityWithoutAnim(EnableAutoStartActivity.this, SplashActivity.class);
    }

}
