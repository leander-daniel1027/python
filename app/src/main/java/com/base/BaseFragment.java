package com.base;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.BuildConfig;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.solutionavenues.deedee.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;



public abstract class BaseFragment extends Fragment
        implements View.OnClickListener {

    FragmentManager childFm;
    private View view;
    private ProgressDialog progressDialog;
    private AlertDialog mErrorDialog;
    public AlertDialog mResponseDialog;
    public Dialog dialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        childFm = getChildFragmentManager();
        setupFragmentViewByResource(inflater, container);
        initializeComponent();
        return view;
    }

    @Override
    public void onClick(View v) {

    }

    public void checkEmptyData(TextView textView, String text) {
        if (!TextUtils.isEmpty(text)) {
            textView.setText(text);
        } else {
            textView.setText(getActivity().getResources().getString(R.string.not_availaible));
        }
    }

    public synchronized void hideKeyboard() {
        if (!isValidActivity()) return;
        View view = getActivity().getCurrentFocus();
        if (view == null) {
            view = new View(getActivity());
        }
        hideKeyboard(view);
    }

   /* public String getDeviceId(){
        String ts = Context.TELEPHONY_SERVICE;
        TelephonyManager mTelephonyMgr = (TelephonyManager) getActivity().getSystemService(ts);
        // String imsi = mTelephonyMgr.getSubscriberId();
        String imei = mTelephonyMgr.getDeviceId();
        return imei;
    }*/


    public synchronized void hideKeyboard(View view) {
        if (view == null) {
            return;
        }
        if (!isValidActivity()) return;
        InputMethodManager imm = (InputMethodManager) getActivity()
                .getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public void showProgressDialog() {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);
        if (progressDialog != null) {
            progressDialog.setMessage(getString(R.string.loading_please_wait));
            progressDialog.show();
        }
    }

    public void hideProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        notifyToActivity(2);
    }

    public long convertTimestampFromDate(String date, String format) {
        long timestamp;
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        Date dateNew = null;
        try {
            dateNew = formatter.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        timestamp = dateNew.getTime() / 1000;
        return timestamp;
    }


    public String convertDateFromTimestamp(long timestamp, String dateFormat) {
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(timestamp * 1000);
        String date = DateFormat.format(dateFormat, cal).toString();
        return date;
    }


    /**
     * get remaining days HH:MM:SS
     *
     * @param milliseconds
     * @return
     */
    public static String formatMilliToDayTime(long milliseconds) {
        String result = "";
        if (milliseconds > 0) {
            String time = String.format("%02d:%02d:%02d",
                    TimeUnit.MILLISECONDS.toHours(milliseconds) % 24,
                    TimeUnit.MILLISECONDS.toMinutes(milliseconds) % TimeUnit.HOURS.toMinutes(1),
                    TimeUnit.MILLISECONDS.toSeconds(milliseconds) % TimeUnit.MINUTES.toSeconds(1));
            long l = TimeUnit.MILLISECONDS.toDays(milliseconds);
            if (l > 0) {
                result = String.valueOf(l) + " Day " + time;
            } else {
                result = time;
            }
        } else {
            result = "00:00:00";
        }
        return result;
    }


    public LinearLayoutManager getVerticalLinearLayoutManager() {
        return new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
    }

    public LinearLayoutManager getGridLayoutManager(int column) {
        return new GridLayoutManager(getActivity(), column);
    }


    public void setupFragmentViewByResource(LayoutInflater inflater, @Nullable ViewGroup container) {

        view = inflater.inflate(getLayoutResourceId(), container, false);
    }

    @Nullable
    @Override
    public View getView() {
        return view;
    }

    public void notifyToActivity(int tag) {
        if (getActivity() == null) return;
        switch (tag) {
            case 1:
                ((BaseActivity) getActivity()).onCreateViewFragment(this);
                break;
            case 2:
                ((BaseActivity) getActivity()).onDestroyViewFragment(this);
                break;
        }
    }

    public abstract int getLayoutResourceId();

    public int getFragmentContainerResourceId(Fragment fragment) {
        return -1;
    }

    public abstract void initializeComponent();

    public abstract void reInitializeComponent();

    public void viewCreateFromBackStack() {

    }

    public FragmentManager getChildFm() {
        return childFm;
    }

    public FragmentTransaction getNewChildFragmentTransaction() {
        return getChildFm().beginTransaction();
    }

    public void clearBackStack(int pos) {
        if (getFragmentManager().getBackStackEntryCount() > pos) {
            try {
                getFragmentManager().popBackStackImmediate(
                        getFragmentManager().getBackStackEntryAt(pos).getId(),
                        FragmentManager.POP_BACK_STACK_INCLUSIVE);
            } catch (IllegalStateException e) {
            }
        }
    }

    public void clearChildFragmentBackStack() {
        clearChildFragmentBackStack(0);
    }

    public void clearChildFragmentBackStack(int pos) {
        if (childFm.getBackStackEntryCount() > pos) {
            try {
                childFm.popBackStack(childFm.getBackStackEntryAt(pos).getId(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
            } catch (IllegalStateException e) {
            }
        }
    }

    public boolean handleOnBackPress() {
        if (getChildFm().getBackStackEntryCount() > 0) {
            getChildFm().popBackStackImmediate();
            return true;
        }
        return false;
    }

    public void changeChildFragment(Fragment f, boolean addBackStack,
                                    boolean clearAll, int pos, boolean isReplace) {

        if (clearAll) {
            clearChildFragmentBackStack(pos);
        }
        if (getFragmentContainerResourceId(f) == -1) return;
        if (f != null) {
            try {
                FragmentTransaction ft = getNewChildFragmentTransaction();
                if (isReplace) {
                    ft.replace(getFragmentContainerResourceId(f), f, f.getClass().getSimpleName());
                } else {
                    ft.add(getFragmentContainerResourceId(f), f, f.getClass().getSimpleName());
                }
                if (addBackStack) {
                    ft.addToBackStack(f.getClass().getSimpleName());
                }
                ft.commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public void changeChildFragment(Fragment f, boolean addBackStack,
                                    boolean clearAll, int pos, int enterAnim, int exitAnim,
                                    int enterAnimBackStack, int exitAnimBackStack, boolean isReplace) {

        if (clearAll) {
            clearChildFragmentBackStack(pos);
        }
        if (getFragmentContainerResourceId(f) == -1) return;
        if (f != null) {
            try {
                FragmentTransaction ft = getNewChildFragmentTransaction();
                ft.setCustomAnimations(enterAnim, exitAnim, enterAnimBackStack, exitAnimBackStack);
                if (isReplace) {
                    ft.replace(getFragmentContainerResourceId(f), f, f.getClass().getSimpleName());
                } else {
                    ft.add(getFragmentContainerResourceId(f), f, f.getClass().getSimpleName());
                }
                if (addBackStack) {
                    ft.addToBackStack(f.getClass().getSimpleName());
                }
                ft.commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public boolean isValidActivity() {
        return getActivity() != null;
    }

    public boolean isValidString(String data) {
        return data != null && !data.trim().isEmpty();
    }

    public void printLog(String msg) {
        if (BuildConfig.DEBUG && msg != null) {
            Log.e(getClass().getSimpleName(), msg);
        }
    }


    public void displayToast(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    public void displayLog(String TAG, String method, String msg) {
        if (BuildConfig.DEBUG)
            Log.e(TAG, method + ": " + msg);

    }

    public void displayErrorDialog(String contentRes) {
        mErrorDialog = new AlertDialog.Builder(getActivity())
                .setTitle(getString(R.string.alert))
                .setMessage(contentRes)
                .setIcon(ContextCompat.getDrawable(getActivity(), R.mipmap.ic_launcher))
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




}
