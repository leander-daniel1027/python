package com.mypackage.ui.splash;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.util.Base64;
import android.util.Log;

import com.mypackage.ui.main.MainActivity;
import com.solutionavenues.deedee.R;
import com.solutionavenues.deedee.appBase.AppBaseActivity;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SplashActivity extends AppBaseActivity {

    private static final String TAG = SplashActivity.class.getSimpleName();

    @Override
    public int getLayoutResourceId() {
        return R.layout.activity_splash;
    }

    @Override
    public void initializeComponent() {
        final Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();

    }



    private void generateFacebookHashKey() {
        //Generate KeyHash For Facebook
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    getApplication().getPackageName(),
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());

                Log.e(TAG, "KeyHash==  " + Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
    }
}
