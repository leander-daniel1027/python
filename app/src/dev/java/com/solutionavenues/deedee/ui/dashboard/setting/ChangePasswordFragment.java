package com.solutionavenues.deedee.ui.dashboard.setting;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.helper.ConnectionDetector;
import com.solutionavenues.deedee.R;
import com.solutionavenues.deedee.appBase.AppBaseFragment;
import com.solutionavenues.deedee.helpers.RegexValidations;
import com.solutionavenues.deedee.model.response.BaseWebResponseModel;
import com.solutionavenues.deedee.rest.ApiHitListener;
import com.solutionavenues.deedee.rest.ApiIds;

/**
 * Created by Azher on 18/6/18.
 */
public class ChangePasswordFragment extends AppBaseFragment implements ApiHitListener {
    private TextView tv_submit;
    private EditText et_old_password, et_new_password, et_confirm_password;


    @Override
    public int getLayoutResourceId() {
        return R.layout.fragment_change_password;
    }

    @Override
    public void initializeComponent() {

        et_old_password = getView().findViewById(R.id.et_old_password);
        et_new_password = getView().findViewById(R.id.et_new_password);
        et_confirm_password = getView().findViewById(R.id.et_confirm_password);
        tv_submit = getView().findViewById(R.id.tv_submit);
        tv_submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_submit:
                if (checkValidation()) {
                    changePassword();
                }
                break;


        }

    }


    // validation for ALL fields
    private boolean checkValidation() {
        boolean status = true;
        if (!RegexValidations.hasText(et_old_password, getActivity(), getString(R.string.OLD_PASSWORD_MSG))) {
            return false;
        }
        if (!RegexValidations.isValidPassword(et_new_password, false, getActivity())) {
            return false;
        }
        if (!RegexValidations.isValidPassword(et_confirm_password, true, getActivity())) {
            return false;
        }
        if (!et_new_password.getText().toString().equals(et_confirm_password.getText().toString())) {
            displayToast(getString(R.string.password_not_matched));
            return false;
        }
        return status;
    }


    // call to changePassword service
    private void changePassword() {
        if (ConnectionDetector.isNetAvail(getActivity())) {
            displayProgressBar(false, getActivity());
            String old_password = et_old_password.getText().toString();
            String new_password = et_new_password.getText().toString();
            String user_id = String.valueOf(getMyApplication().getUserPrefs().getLoggedInUser().getId());
            getRestClient().callback(this).changePassword(user_id, old_password, new_password);
        } else {
            displayErrorDialog(getString(R.string.error_internet_connection));
        }
    }


    @Override
    public void onSuccessResponse(int apiId, Object response) {
        dismissProgressBar(getActivity());
        if (apiId == ApiIds.ID_CHANGE_PASSWORD) {
            BaseWebResponseModel responseModel = (BaseWebResponseModel) response;
            if (responseModel != null) {
                if (!responseModel.isError()) {
                    displayToast(responseModel.getMessage());
                    try {
                        getDashBoardActivity().onBackPressed();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    displayErrorDialog(responseModel.getMessage());
                }
            }
        }
    }

    @Override
    public void onFailResponse(int apiId, String error) {
        dismissProgressBar(getActivity());
        displayErrorDialog(error);

    }


}
