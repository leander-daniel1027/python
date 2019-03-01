package com.solutionavenues.deedee.appBase;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.Window;

import com.base.BaseActivity;
import com.solutionavenues.deedee.R;
import com.solutionavenues.deedee.model.UserModel;
import com.solutionavenues.deedee.util.Constants;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;


/**
 * Created by ubuntu on 20/2/18.
 */

public abstract class AppBaseActivity extends BaseActivity implements View.OnClickListener {

    private AlertDialog mErrorDialog;

    @Override
    public void onClick(View v) {

    }

    private Dialog alertDialogProgressBar;
    private AlertDialog mDialog;
    public void sendActivity(Context context, Class<?> className) {
        Intent intent = new Intent(context, className);
        intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
        overridePendingTransition(R.anim.alpha_visible_anim, R.anim.alpha_gone_anim);
    }

    public void sendActivityWithoutAnim(Context context, Class<?> className) {
        Intent intent = new Intent(context, className);
        intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
  public void sendActivity(Context context, Class<?> className, Bundle args) {
        Intent intent = new Intent(context, className);
        intent.putExtras(args);
        intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
        overridePendingTransition(R.anim.alpha_visible_anim, R.anim.alpha_gone_anim);
    }

    public void sendActivityFinish(Context context, Class<?> className) {
        Intent intent = new Intent(context, className);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK).
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
        overridePendingTransition(R.anim.alpha_visible_anim, R.anim.alpha_gone_anim);
        finish();
    }

    public UserModel getUserModel() {
        return null;

    }
    public void setUserModel(UserModel userModel) {
    }

    public String getCurrentDate() {
        String date = new SimpleDateFormat(Constants.SERVER_DATE_FORMAT, Locale.getDefault()).format(new Date());
        return date;
    }

    public void displayErrorDialog(String contentRes) {
        mErrorDialog = new AlertDialog.Builder(this)
                .setTitle(getString(R.string.alert))
                .setMessage(contentRes)
                .setIcon(ContextCompat.getDrawable(this, R.mipmap.ic_launcher))
                .setCancelable(false)
                .setNegativeButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .create();
        mErrorDialog.show();
    }
 public void displayMessageDialog(String contentRes) {
        mErrorDialog = new AlertDialog.Builder(this)
                .setTitle(getString(R.string.message))
                .setMessage(contentRes)
                .setIcon(ContextCompat.getDrawable(this, R.mipmap.ic_launcher))
                .setCancelable(false)
                .setNegativeButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .create();
        mErrorDialog.show();
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

    public void displayProgressBar(boolean isCancellable) {
        alertDialogProgressBar = new Dialog(this,
                R.style.YourCustomStyle);
        alertDialogProgressBar.setCancelable(false);
        alertDialogProgressBar
                .requestWindowFeature(Window.FEATURE_NO_TITLE);
        alertDialogProgressBar.setContentView(R.layout.lay_progress_dialog_main);

        alertDialogProgressBar.getWindow().setBackgroundDrawable(
                new ColorDrawable(Color.TRANSPARENT));
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                alertDialogProgressBar.show();
            }
        });
    }

    public void dismissProgressBar() {
        if (alertDialogProgressBar != null) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    alertDialogProgressBar.dismiss();
                }
            });
        }
    }

     public void printLog(String tag,String s) {
         Log.i(tag, "printLog: "+s);
    }



    public boolean checkPermissions(String[] perms, int requestCode) {
        ArrayList<String> permsArray = new ArrayList<>();
        boolean hasPerms = true;
        for (String perm : perms) {
            if (ContextCompat.checkSelfPermission(this, perm) != PackageManager.PERMISSION_GRANTED) {
                permsArray.add(perm);
                hasPerms = false;
            }
        }
        if (!hasPerms) {
            String[] permsString = new String[permsArray.size()];
            for (int i = 0; i < permsArray.size(); i++) {
                permsString[i] = permsArray.get(i);
            }
            ActivityCompat.requestPermissions(this, permsString, 99);
            return false;
        } else
            return true;
    }

}
