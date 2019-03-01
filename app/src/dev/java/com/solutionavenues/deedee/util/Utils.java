package com.solutionavenues.deedee.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.provider.CalendarContract;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.google.android.gms.common.GoogleApiAvailability;
import com.solutionavenues.deedee.R;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Currency;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.regex.Pattern;

/**
 * Created by Azher on 28/6/18.
 */
public class Utils {


    private static ProgressDialog progressDialog;
    private static AlertDialog mErrorDialog;

    public static void showProgressDialog(final Context context, boolean cancelable) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setCanceledOnTouchOutside(cancelable);
        progressDialog.setCancelable(cancelable);
        progressDialog.setMessage(context.getString(R.string.loading_please_wait));
        progressDialog.show();
    }

    public static void dismissProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

    public static void showSnackbar(Context context, String message) {
        Snackbar.make((((Activity) context).findViewById(android.R.id.content)), message, Snackbar.LENGTH_LONG).show();
    }

    public static void showAlertBoxForConfirmation(Context context,
                                                   String msg,
                                                   DialogInterface.OnClickListener okListener,
                                                   DialogInterface.OnClickListener cancelListener) {

        new AlertDialog.Builder(context).setTitle(null).setMessage(msg)
                .setPositiveButton("YES", okListener)
                .setNegativeButton("NO", cancelListener)
                .show().setCancelable(false);
    }

    public static void showAlertDialog(Context context, String message,
                                       String[] arrayList,
                                       DialogInterface.OnClickListener onClickedListener) {
        android.support.v7.app.AlertDialog.Builder dialogBuilder = new android.support.v7.app.AlertDialog.Builder(context);
        dialogBuilder.setTitle(message);
        dialogBuilder.setItems(arrayList, onClickedListener);
        //Create alert dialog object via builder
        android.support.v7.app.AlertDialog alertDialogObject = dialogBuilder.create();
        //Show the dialog
        alertDialogObject.show();
    }

    public interface ListItemClickListener {
        void onItemClick(int position);
    }

    public static void showListDialog(Context context, CharSequence[] list, int titleId, final ListItemClickListener listItemClickListener) {
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(context);
        builder.setTitle(titleId);
        builder.setItems(list, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (listItemClickListener != null) {
                    listItemClickListener.onItemClick(which);
                }
            }
        });
        builder.setNegativeButton(android.R.string.cancel, null);
        final android.support.v7.app.AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    public interface DialogPositiveButtonClickListener {
        void onPositiveClick(DialogInterface dialog);
    }

    public interface DialogNegativeButtonClickListener {
        void onNegativeClick(DialogInterface dialog);
    }

    public static void showMessageDialogWithListener(Context context, String message,
                                                     int positiveBtnId,
                                                     int negativeBtnId,
                                                     final DialogPositiveButtonClickListener dialogPositiveButtonClickListener,
                                                     final DialogNegativeButtonClickListener dialogNegativeButtonClickListener) {
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(context);
        builder.setMessage(message);
        builder.setCancelable(false);
        if (dialogPositiveButtonClickListener != null) {
            builder.setPositiveButton(context.getString(positiveBtnId), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (dialogPositiveButtonClickListener != null) {
                        dialogPositiveButtonClickListener.onPositiveClick(dialog);
                    } else {
                        dialog.dismiss();
                    }
                }
            });
        }
        if (dialogNegativeButtonClickListener != null) {
            builder.setNegativeButton(context.getString(negativeBtnId), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialogNegativeButtonClickListener.onNegativeClick(dialog);
                }
            });
        }
        android.support.v7.app.AlertDialog dialog = builder.create();
        dialog.show();
    }


    public static int getGooglePlayServicesAvailableStatus(Context context) {
        GoogleApiAvailability api = GoogleApiAvailability.getInstance();
        return api.isGooglePlayServicesAvailable(context);
    }

    public static void showAlertBox(Context context, String msg, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(context).setTitle(null).setMessage(msg).setPositiveButton("OK", okListener).show().
                setCancelable(false);
    }

    public static void showToast(Context context, String message) {
        Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.BOTTOM, 0, 0);
        toast.show();
    }

    public final static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    public final static boolean isValidMobile(String phone) {
        return (!TextUtils.isEmpty(phone) && phone.length() == 10 && android.util.Patterns.PHONE.matcher(phone).matches());
    }

    public static boolean isValidateAadharNumber(String aadharNumber) {
        Pattern aadharPattern = Pattern.compile("\\d{12}");
        boolean isValidAadhar = aadharPattern.matcher(aadharNumber).matches();
        if (isValidAadhar) {
            isValidAadhar = VerhoeffAlgorithm.validateVerhoeff(aadharNumber);
        }
        return isValidAadhar;
    }

    public final static boolean isValidPinCode(String pincode) {
        return (!TextUtils.isEmpty(pincode) && pincode.length() == 6);
    }


    public static boolean isValidPanCard(String pancard) {
        Pattern panPattern = Pattern.compile("[a-z]{5}[0-9]{4}[a-z]{1}");
        boolean isValidPanCard = panPattern.matcher(pancard).matches();
        return (!TextUtils.isEmpty(pancard) && pancard.length() == 10 && isValidPanCard);
    }

    public static String formatDecimal(double value) {
        DecimalFormat precision = new DecimalFormat("0.0");
        return precision.format(value);
    }

    public static void hideKeyboard(Activity activity) {
        try {
            View view = activity.getCurrentFocus();
            if (view != null) {
                InputMethodManager inputManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void makePhoneCall(String number,Context context) {
        try {
            Intent my_callIntent = new Intent(Intent.ACTION_CALL);
            my_callIntent.setData(Uri.parse("tel:" + number));
            //here the word 'tel' is important for making a call...
            context.startActivity(my_callIntent);
        } catch (ActivityNotFoundException e) {
            showToast(context,context.getResources().getString(R.string.phone_call_error));
        }
    }

    public static String getAge(int day, int month, int year){
        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();
        dob.set(year, month, day);
        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);
        /*if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)){
            age--;
        }*/
        age=age+1;
        Integer ageInt = new Integer(age);
        String ageS = ageInt.toString();
        return ageS;
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static String getDeviceId(Context context) {
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }


    public static long convertTimestampFromDate(String date, String format) {
        long timestamp;
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        Date dateNew = null;
        try {
            dateNew = formatter.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        timestamp = dateNew.getTime();
        return timestamp;
    }

    public static String formatCurrency(double loanAmount) {
        NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("en", "in"));
        format.setCurrency(Currency.getInstance("INR"));
        String result = format.format(loanAmount);
        return result;
    }

    public String getFileNameFromPath(String path) {
        String filename = path.substring(path.lastIndexOf("/") + 1);
        return filename;
    }

    public static int getValidInteger(String s) {
        int validInteger = 0;
        try {
            validInteger = Integer.parseInt(s);
        } catch (NumberFormatException ex) {
        }

        return validInteger;
    }


    public static void addReminder(Context context, long calId, String date,
                                   String format, String where, String desc, String title) {
        if (calId == -1) {
            return;
        }
        long start = convertTimestampFromDate(date, format);
        long end = convertTimestampFromDate(date, format) + 12 * 60 * 60 * 1000;
        ContentValues values = new ContentValues();
        values.put(CalendarContract.Events.DTSTART, start);
        values.put(CalendarContract.Events.DTEND, end);
        values.put(CalendarContract.Events.TITLE, title);
        values.put(CalendarContract.Events.EVENT_LOCATION, where);
        values.put(CalendarContract.Events.CALENDAR_ID, calId);
        values.put(CalendarContract.Events.EVENT_TIMEZONE, TimeZone.getDefault().getID());
        values.put(CalendarContract.Events.DESCRIPTION, desc);
        values.put(CalendarContract.Events.ACCESS_LEVEL, CalendarContract.Events.ACCESS_PRIVATE);
        //  values.put(CalendarContract.Events.ALL_DAY, false);
        @SuppressLint("MissingPermission")
        Uri uri = context.getContentResolver().insert(CalendarContract.Events.CONTENT_URI, values);
        if (uri != null) {
            long id = Long.parseLong(uri.getLastPathSegment());
            Log.e("event_id", "addReminder: " + id);
            ContentValues value = new ContentValues();
            value.put(CalendarContract.Reminders.EVENT_ID, id);
            value.put(CalendarContract.Reminders.METHOD, CalendarContract.Reminders.METHOD_ALERT);
            value.put(CalendarContract.Reminders.MINUTES, 5); // minutes
            Uri uris = context.getContentResolver().insert(Uri.parse("content://com.android.calendar/reminders"), value);
        }
    }

    public static ArrayList<String> getLastYearList(int limit) {
        ArrayList<String> list = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR);
        list.add(currentYear + "");
        for (int i = 0; i < limit; i++) {
            currentYear = currentYear-1;
            list.add((currentYear) + "");

        }
        return list;
    }

    public static void displayErrorDialog(String contentRes, Context context) {
        mErrorDialog = new AlertDialog.Builder(context)
                .setTitle(context.getString(R.string.alert))
                .setMessage(contentRes)
                .setIcon(ContextCompat.getDrawable(context, R.mipmap.ic_launcher))
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

    public static void printLog(String tag,String s) {
        Log.e(tag, "printLog: "+s);
    }

}
