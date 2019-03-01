package com.solutionavenues.deedee.appBase;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.base.BaseFragment;
import com.solutionavenues.deedee.MyApplication;
import com.solutionavenues.deedee.R;
import com.solutionavenues.deedee.interfaces.OnOkClickListener;
import com.solutionavenues.deedee.rest.RestClient;
import com.solutionavenues.deedee.rest.RestService;
import com.solutionavenues.deedee.ui.dashboard.DashboardActivity;
import com.solutionavenues.deedee.ui.dashboard.appform.ApplicationFormFragment;
import com.solutionavenues.deedee.ui.dashboard.fi.AddFiFragment;
import com.solutionavenues.deedee.ui.dashboard.grt.AddGRTFragment;
import com.solutionavenues.deedee.ui.dashboard.locality.LocalityFragment;
import com.solutionavenues.deedee.util.Constants;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


/**
 * Created by ubuntu on 20/2/18.
 */

public abstract class AppBaseFragment extends BaseFragment {

    public Dialog alertDialogProgressBar;
    RestClient restClient;

    @Override
    public void reInitializeComponent() {
    }


    public DashboardActivity getDashBoardActivity() {
        return ((DashboardActivity) getActivity());
    }

    public MyApplication getMyApplication() {
        return MyApplication.getInstance();
    }

    public Dialog displayProgressBar(boolean isCancellable, Activity activity) {

        alertDialogProgressBar = new Dialog(getActivity(),
                R.style.YourCustomStyle);
        alertDialogProgressBar.setCancelable(false);
        alertDialogProgressBar
                .requestWindowFeature(Window.FEATURE_NO_TITLE);
        alertDialogProgressBar.setContentView(R.layout.lay_progress_dialog_main);

        alertDialogProgressBar.getWindow().setBackgroundDrawable(
                new ColorDrawable(Color.TRANSPARENT));
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                alertDialogProgressBar.show();
            }
        });
        return alertDialogProgressBar;
    }

    public Dialog dismissProgressBar(Activity activity) {
        if (alertDialogProgressBar != null) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    alertDialogProgressBar.dismiss();
                }
            });
        }
        return alertDialogProgressBar;
    }

    public RestClient getRestClient() {
        if (restClient == null) {
            restClient = new RestClient();
        }
        return restClient;
    }

    public String getCurrentDate() {
        String date = new SimpleDateFormat(Constants.SERVER_DATE_FORMAT, Locale.getDefault()).format(new Date());
        return date;
    }


    public String getRadioButtonValue(RadioGroup radioGroup) {
        int selectedId = radioGroup.getCheckedRadioButtonId();
        String value;
        RadioButton radioButton = getView().findViewById(selectedId);
        value = String.valueOf(radioButton.getText());
        return value;
    }

    public String getRadioButtonStateWithValue(RadioGroup radioGroup,String s) {
        int selectedId = radioGroup.getCheckedRadioButtonId();
        String value;
        String state="0";
        RadioButton radioButton = getView().findViewById(selectedId);
        value = String.valueOf(radioButton.getText());
        if (value.equalsIgnoreCase(s)) {
            state="1";
        }
        return state;
    }

    public void setCheckBox(CheckBox checkBox,String value) {
        if (value.equalsIgnoreCase("1")) {
            checkBox.setChecked(true);
        }
    }

    public String getRadioButtonState(RadioButton radioButton) {
        String value="0";
        if (radioButton.isChecked()) {
            value="1";
        }
        return value;
    }


    public void setCheckedRadioButton(RadioGroup group,String text){
        if (TextUtils.isEmpty(text)) {
            return;
        }
        for (int i = 0; i < group.getChildCount(); i++) {
            if (((RadioButton)group.getChildAt(i)).getText().toString().equalsIgnoreCase(text)) {
                ((RadioButton)group.getChildAt(i)).setChecked(true);
                break;
            }
        }
    }

    public int getCheckBoxValue(CheckBox checkBox) {
        int value=0;
        if (checkBox.isChecked()) {
            value=1;
        }
        return value;
    }

    public String getImageActualPath(String pathFound) {
        File file = new File(pathFound);
        if (file.exists()) {
            return Uri.fromFile(file).toString();
        } else {
            String imageUrl = RestService.IMAGE_BASE_URL + pathFound;
            return imageUrl;
        }
    }

    public void displayAlertDialog(String title, String contentRes, final OnOkClickListener okClickListener) {
        mResponseDialog = new AlertDialog.Builder(getActivity())
                .setTitle(title)
                .setMessage(contentRes)
                .setIcon(ContextCompat.getDrawable(getActivity(), R.mipmap.ic_launcher))
                .setCancelable(false)
                .setNegativeButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Fragment fragment = getDashBoardActivity().getLatestFragment();
                        if (fragment instanceof ApplicationFormFragment) {
                            getActivity().onBackPressed();
                        }
                        if (fragment instanceof AddFiFragment) {
                            getActivity().onBackPressed();
                        }
                        if (fragment instanceof AddGRTFragment) {
                            getActivity().onBackPressed();
                        }
                        if (fragment instanceof LocalityFragment) {
                            getActivity().onBackPressed();
                        }
                    }
                })
                .create();
        mResponseDialog.show();
    }

    public Bitmap getBitmapFromPath(String imagePath) {
        Uri uri = Uri.fromFile(new File(imagePath));
        Bitmap bitmap = null;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }


    // open date picker dialog
    public void openDatePicker(boolean isMinimumDate, DatePickerDialog.OnDateSetListener dateSetListener) {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                dateSetListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
        dpd.show(getActivity().getFragmentManager(), "Datepickerdialog");
        calendar.setTimeInMillis(System.currentTimeMillis() - 1000);
        if (isMinimumDate) {
            dpd.setMinDate(calendar);
        } else {
            dpd.setMaxDate(calendar);
        }
        dpd.setVersion(DatePickerDialog.Version.VERSION_2);
    }

/*
    public void setLanguage(String lng, android.content.Context context) {
        java.util.Locale mLocale = new java.util.Locale(lng);
        java.util.Locale.setDefault(mLocale);
        android.content.res.Configuration config = new android.content.res.Configuration();
        config.locale = mLocale;
        context.getResources().updateConfiguration(config,
                context.getResources().getDisplayMetrics());
            MyApplication.getInstance().appPrefs.saveCurrentLanguage(lng);

    }*/

}
