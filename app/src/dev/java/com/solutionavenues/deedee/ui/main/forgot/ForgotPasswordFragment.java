package com.solutionavenues.deedee.ui.main.forgot;

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
import com.solutionavenues.deedee.rest.RestClient;

/**
 * Created by Azher on 18/6/18.
 */
public class ForgotPasswordFragment extends AppBaseFragment implements ApiHitListener {
    private TextView tv_submit;
    private EditText et_email;
    private RestClient mRestClient;

    @Override
    public int getLayoutResourceId() {
        return R.layout.fragment_forgot_password;
    }

    @Override
    public void initializeComponent() {
        mRestClient = new RestClient();
        et_email = getView().findViewById(R.id.et_email);
        tv_submit = getView().findViewById(R.id.tv_submit);
        tv_submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_submit:
                if (checkValidation()) {
                    forgotPassword();
                }
                break;
        }
    }

    // validation for ALL fields
    private boolean checkValidation() {
        boolean status = true;
        if (!RegexValidations.isEmailAddress(et_email, true, getActivity())) {
            status = false;
        }
        return status;
    }


    // call to forgotPassword service
    private void forgotPassword() {
        if (ConnectionDetector.isNetAvail(getActivity())) {
            displayProgressBar(false, getActivity());
            String email = et_email.getText().toString();
            mRestClient.callback(this).forgotPassword(email);
        } else {
            displayErrorDialog(getString(R.string.error_internet_connection));
        }
    }


    @Override
    public void onSuccessResponse(int apiId, Object response) {
        dismissProgressBar(getActivity());
        if (apiId == ApiIds.ID_FORGOT_PASSWORD) {
            BaseWebResponseModel responseModel = (BaseWebResponseModel) response;
            if (responseModel != null) {
                if (!responseModel.isError()) {
                    displayToast(responseModel.getMessage());
                    getActivity().onBackPressed();
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
