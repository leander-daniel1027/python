package com.solutionavenues.deedee.helpers;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.base.BaseFragment;
import com.helper.ConnectionDetector;
import com.solutionavenues.deedee.MyApplication;
import com.solutionavenues.deedee.R;
import com.solutionavenues.deedee.model.response.BaseWebResponseModel;
import com.solutionavenues.deedee.rest.ApiHitListener;
import com.solutionavenues.deedee.rest.ApiIds;
import com.solutionavenues.deedee.rest.RestClient;
import com.solutionavenues.deedee.ui.dashboard.DashboardActivity;
import com.solutionavenues.deedee.ui.dashboard.appform.ApplicationFormFragment;
import com.solutionavenues.deedee.ui.dashboard.appform.myleads.LeadListFragment;
import com.solutionavenues.deedee.ui.dashboard.centerform.AddCenterFragment;
import com.solutionavenues.deedee.ui.dashboard.cgt.AddCGTFormFragment;
import com.solutionavenues.deedee.ui.dashboard.enquiry.AddEnquiryFragment;
import com.solutionavenues.deedee.ui.dashboard.enquiry.EnquiryListFragment;
import com.solutionavenues.deedee.ui.dashboard.fi.AddFiFragment;
import com.solutionavenues.deedee.ui.dashboard.fi.FiListFragment;
import com.solutionavenues.deedee.ui.dashboard.groupform.AddGroupFragment;
import com.solutionavenues.deedee.ui.dashboard.groupform.MyGroupListFragment;
import com.solutionavenues.deedee.ui.dashboard.grt.AddGRTFragment;
import com.solutionavenues.deedee.ui.dashboard.home.HomeFragment;
import com.solutionavenues.deedee.ui.dashboard.locality.LocalityFragment;
import com.solutionavenues.deedee.ui.dashboard.setting.ChangePasswordFragment;
import com.solutionavenues.deedee.ui.dashboard.setting.SettingFragment;
import com.solutionavenues.deedee.ui.dashboard.telecaller.AddAppointmentFragment;
import com.solutionavenues.deedee.ui.main.MainActivity;


/**
 * Created by Sunil kumar yadav on 27/2/18.
 */

public class ToolBarHelper implements View.OnClickListener, ApiHitListener {

    DashboardActivity dashboardActivity;
    RelativeLayout ll_tool_bar;
    ImageView iv_logout;
    ImageView iv_back;
    TextView tv_title;
    public TextView tv_save_continue;
    private RestClient mRestClient;

    public ToolBarHelper(DashboardActivity dashboardActivity) {
        this.dashboardActivity = dashboardActivity;
        initializeComponent();
    }

    private void initializeComponent() {
        mRestClient = new RestClient();
        ll_tool_bar = dashboardActivity.findViewById(R.id.ll_tool_bar);
        iv_logout = dashboardActivity.findViewById(R.id.iv_logout);
        iv_back = dashboardActivity.findViewById(R.id.iv_back);
        tv_title = dashboardActivity.findViewById(R.id.tv_title);
        tv_save_continue = dashboardActivity.findViewById(R.id.tv_save_continue);
        tv_title.setAllCaps(true);
        iv_back.setVisibility(View.GONE);


        tv_save_continue.setOnClickListener(this);
        iv_logout.setOnClickListener(this);
        iv_back.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                dashboardActivity.onBackPressed();
                break;
            case R.id.iv_logout:
                showLogoutConfirmation();
                break;
            case R.id.tv_save_continue:
                if (dashboardActivity.getLatestFragment() != null &&
                        dashboardActivity.getLatestFragment() instanceof ApplicationFormFragment) {
                    ((ApplicationFormFragment) dashboardActivity.getLatestFragment()).saveData();
                }
                if (dashboardActivity.getLatestFragment() != null &&
                        dashboardActivity.getLatestFragment() instanceof AddFiFragment) {
                    ((AddFiFragment) dashboardActivity.getLatestFragment()).saveData();
                }
                break;
        }
    }

    // call to logout service
    private void logoutUser() {
        if (ConnectionDetector.isNetAvail(dashboardActivity)) {
            dashboardActivity.displayProgressBar(false);
            mRestClient.callback(this).logoutUser(String.valueOf(MyApplication.getInstance().getUserPrefs().getLoggedInUser().getId()));
        } else {
            dashboardActivity.showToast(dashboardActivity.getString(R.string.error_internet_connection));
        }
    }

    // method to exit dialog from app.
    public void showLogoutConfirmation() {
        AlertDialog.Builder builder = new AlertDialog.Builder(dashboardActivity);
        builder.setMessage(dashboardActivity.getString(R.string.are_you_sure_you_want_to_logout))
                .setCancelable(false)
                .setPositiveButton(dashboardActivity.getString(R.string.yes), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        logoutUser();
                    }
                })
                .setNegativeButton(dashboardActivity.getString(R.string.no), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private Resources getResources() {
        return dashboardActivity.getResources();
    }

    private boolean isViewVisible(View view) {
        return view.getVisibility() == View.VISIBLE;
    }

    private boolean isViewHide(View view) {
        return view.getVisibility() == View.GONE || view.getVisibility() == View.INVISIBLE;
    }

    public void onCreateViewFragment(BaseFragment baseFragment) {
        dashboardActivity.hideKeyboard();

        if (baseFragment instanceof HomeFragment) {
            tv_title.setVisibility(View.GONE);
            iv_back.setVisibility(View.GONE);
            tv_save_continue.setVisibility(View.GONE);
            iv_logout.setVisibility(View.VISIBLE);
            // tv_title.setText(getResources().getString(R.string.home));

        } else if (baseFragment instanceof AddEnquiryFragment) {
            tv_title.setVisibility(View.VISIBLE);
            iv_back.setVisibility(View.VISIBLE);
            tv_save_continue.setVisibility(View.GONE);
            iv_logout.setVisibility(View.GONE);
            tv_title.setText(getResources().getString(R.string.enquiry));
        } else if (baseFragment instanceof SettingFragment) {
            tv_title.setVisibility(View.VISIBLE);
            iv_back.setVisibility(View.VISIBLE);
            iv_logout.setVisibility(View.GONE);
            tv_save_continue.setVisibility(View.GONE);
            tv_title.setText(getResources().getString(R.string.setting));
        } else if (baseFragment instanceof ChangePasswordFragment) {
            tv_title.setVisibility(View.VISIBLE);
            iv_back.setVisibility(View.VISIBLE);
            iv_logout.setVisibility(View.GONE);
            tv_save_continue.setVisibility(View.GONE);
            tv_title.setText(getResources().getString(R.string.change_password));
        } else if (baseFragment instanceof LocalityFragment) {
            tv_title.setVisibility(View.VISIBLE);
            iv_back.setVisibility(View.VISIBLE);
            tv_save_continue.setVisibility(View.GONE);
            iv_logout.setVisibility(View.GONE);
            tv_title.setText(getResources().getString(R.string.locality));
        } else if (baseFragment instanceof EnquiryListFragment) {
            tv_title.setVisibility(View.VISIBLE);
            iv_back.setVisibility(View.VISIBLE);
            tv_save_continue.setVisibility(View.GONE);
            iv_logout.setVisibility(View.GONE);
            tv_title.setText(getResources().getString(R.string.my_enquiries));
        } else if (baseFragment instanceof ApplicationFormFragment) {
            tv_title.setVisibility(View.VISIBLE);
            iv_back.setVisibility(View.VISIBLE);
            tv_save_continue.setVisibility(View.VISIBLE);
            iv_logout.setVisibility(View.GONE);
            tv_title.setText(getResources().getString(R.string.application_form));
        } else if (baseFragment instanceof AddCenterFragment) {
            tv_title.setVisibility(View.VISIBLE);
            iv_back.setVisibility(View.VISIBLE);
            tv_save_continue.setVisibility(View.GONE);
            iv_logout.setVisibility(View.GONE);
            tv_title.setText(getResources().getString(R.string.add_center));
        } else if (baseFragment instanceof AddGroupFragment) {
            tv_title.setVisibility(View.VISIBLE);
            iv_back.setVisibility(View.VISIBLE);
            tv_save_continue.setVisibility(View.GONE);
            iv_logout.setVisibility(View.GONE);
            tv_title.setText(getResources().getString(R.string.add_group));
        } else if (baseFragment instanceof LeadListFragment) {
            tv_title.setVisibility(View.VISIBLE);
            iv_back.setVisibility(View.VISIBLE);
            tv_save_continue.setVisibility(View.GONE);
            iv_logout.setVisibility(View.GONE);
            tv_title.setText(getResources().getString(R.string.my_leads));
        } else if (baseFragment instanceof AddFiFragment) {
            tv_title.setVisibility(View.VISIBLE);
            iv_back.setVisibility(View.VISIBLE);
            tv_save_continue.setVisibility(View.VISIBLE);
            iv_logout.setVisibility(View.GONE);
            tv_title.setText(getResources().getString(R.string.fi));
        } else if (baseFragment instanceof AddAppointmentFragment) {
            tv_title.setVisibility(View.VISIBLE);
            iv_back.setVisibility(View.VISIBLE);
            tv_save_continue.setVisibility(View.GONE);
            iv_logout.setVisibility(View.GONE);
            tv_title.setText(getResources().getString(R.string.telecalling));
        } else if (baseFragment instanceof FiListFragment) {
            tv_title.setVisibility(View.VISIBLE);
            iv_back.setVisibility(View.VISIBLE);
            tv_save_continue.setVisibility(View.GONE);
            iv_logout.setVisibility(View.GONE);
            tv_title.setText(getResources().getString(R.string.added_fi));
        } else if (baseFragment instanceof AddCGTFormFragment) {
            tv_title.setVisibility(View.VISIBLE);
            iv_back.setVisibility(View.VISIBLE);
            tv_save_continue.setVisibility(View.GONE);
            iv_logout.setVisibility(View.GONE);
            tv_title.setText(getResources().getString(R.string.cgt));
        } else if (baseFragment instanceof AddGRTFragment) {
            tv_title.setVisibility(View.VISIBLE);
            iv_back.setVisibility(View.VISIBLE);
            tv_save_continue.setVisibility(View.GONE);
            iv_logout.setVisibility(View.GONE);
            tv_title.setText(getResources().getString(R.string.grt));
        }else if (baseFragment instanceof MyGroupListFragment) {
            tv_title.setVisibility(View.VISIBLE);
            iv_back.setVisibility(View.VISIBLE);
            tv_save_continue.setVisibility(View.GONE);
            iv_logout.setVisibility(View.GONE);
            tv_title.setText(getResources().getString(R.string.my_group));
        }

    }

    public void onDestroyViewFragment(BaseFragment baseFragment) {

    }

    private void goToLogin() {
        Intent intent = new Intent(dashboardActivity, MainActivity.class);
        dashboardActivity.startActivity(intent);
        dashboardActivity.overridePendingTransition(R.anim.alpha_visible_anim, R.anim.alpha_gone_anim);
        dashboardActivity.finish();
    }


    @Override
    public void onSuccessResponse(int apiId, Object response) {
        dashboardActivity.dismissProgressBar();
        if (apiId == ApiIds.ID_LOGOUT) {
            BaseWebResponseModel responseModel = (BaseWebResponseModel) response;
            if (responseModel != null) {
                if (!responseModel.isError()) {
                    MyApplication.getInstance().getUserPrefs().saveLoggedInUser(null);
                    dashboardActivity.displayToast(responseModel.getMessage());
//                    if (dashboardActivity.isMyServiceRunning()) {
//                        dashboardActivity.stopLocationService();
//                    }
//                    dashboardActivity.startLo
                    goToLogin();
                } else {
                    dashboardActivity.showToast(responseModel.getMessage());
                }
            }
        }
    }

    @Override
    public void onFailResponse(int apiId, String error) {
        dashboardActivity.dismissProgressBar();


    }


}
