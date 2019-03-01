package com.solutionavenues.deedee.ui.main.login;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.fcm.NotificationPrefs;
import com.helper.ConnectionDetector;
import com.solutionavenues.deedee.R;
import com.solutionavenues.deedee.appBase.AppBaseFragment;
import com.solutionavenues.deedee.helpers.RegexValidations;
import com.solutionavenues.deedee.model.response.LoginResponseModel;
import com.solutionavenues.deedee.rest.ApiHitListener;
import com.solutionavenues.deedee.rest.ApiIds;
import com.solutionavenues.deedee.rest.RestClient;
import com.solutionavenues.deedee.ui.dashboard.DashboardActivity;
import com.solutionavenues.deedee.ui.main.MainActivity;
import com.solutionavenues.deedee.ui.main.forgot.ForgotPasswordFragment;


/**
 * Created by Azher on 18/6/18.
 */
public class LoginFragment extends AppBaseFragment implements ApiHitListener {
    private TextView tv_forgot_password, tv_login;
    private EditText et_user_name, et_password;
    private RestClient mRestClient;

    @Override
    public int getLayoutResourceId() {
        return R.layout.fragment_login;
    }

    @Override
    public void initializeComponent() {
        mRestClient = new RestClient();
        et_password = getView().findViewById(R.id.et_password);
        et_user_name = getView().findViewById(R.id.et_user_name);
        tv_forgot_password = getView().findViewById(R.id.tv_forgot_password);
        tv_login = getView().findViewById(R.id.tv_login);
        tv_forgot_password.setOnClickListener(this);
        tv_login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_login:
                if (checkValidation()) {
                    loginUser();
                }
                break;
            case R.id.tv_forgot_password:
                addForgotFragment();
                break;

        }

    }


    // call to login service
    private void loginUser() {
        if (ConnectionDetector.isNetAvail(getActivity())) {
            ((MainActivity)getActivity()).displayProgressBar(false);
            String email = et_user_name.getText().toString();
            String password = et_password.getText().toString();
            String device_token = NotificationPrefs.getInstance(getActivity()).getNotificationToken();
            mRestClient.callback(this).loginUser(email, password, device_token);
        } else {
            displayErrorDialog(getString(R.string.error_internet_connection));
        }
    }

    private void addForgotFragment() {
        ForgotPasswordFragment fragment = new ForgotPasswordFragment();
        int enterAnimation = R.anim.translate_right_to_left_anim;
        int exitAnimation = 0;
        int enterAnimationBackStack = 0;
        int exitAnimationBackStack = R.anim.translate_left_to_right_medium;
        ((MainActivity) getActivity()).changeFragment(fragment, true, false, 0,
                enterAnimation, exitAnimation, enterAnimationBackStack, exitAnimationBackStack, false);

    }

    private void goToDashboard() {
        Intent intent = new Intent(getActivity(), DashboardActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK).
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getActivity().startActivity(intent);
        getActivity().overridePendingTransition(R.anim.alpha_visible_anim, R.anim.alpha_gone_anim);
        getActivity().finish();
    }

    // validation for ALL fields
    private boolean checkValidation() {
        boolean status = true;
        if (!RegexValidations.hasText(et_user_name, getActivity(), getString(R.string.username_email_required))) {
            status = false;
        }
        if (!RegexValidations.isValidPassword(et_password, false, getActivity())) {
            status = false;
        }
        return status;
    }


    @Override
    public void onSuccessResponse(int apiId, Object response) {
        ((MainActivity)getActivity()).dismissProgressBar();
        if (apiId == ApiIds.ID_LOGIN) {
            LoginResponseModel responseModel = (LoginResponseModel) response;

            if (responseModel != null) {
                if (!responseModel.isError()) {
                    getMyApplication().getUserPrefs().saveLoggedInUser(responseModel.getData());
                    Log.e("Role Type", getMyApplication().getUserPrefs().getLoggedInUser().getRole_type());
                    displayToast(responseModel.getMessage());
                    goToDashboard();
                }else{
                    displayErrorDialog(responseModel.getMessage());
                }
            }
        }
    }

    @Override
    public void onFailResponse(int apiId, String error) {
        ((MainActivity)getActivity()).dismissProgressBar();
        displayErrorDialog(error);

    }
}
