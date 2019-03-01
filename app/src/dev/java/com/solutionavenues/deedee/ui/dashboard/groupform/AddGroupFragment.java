package com.solutionavenues.deedee.ui.dashboard.groupform;

import android.content.DialogInterface;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.helper.ConnectionDetector;
import com.solutionavenues.deedee.R;
import com.solutionavenues.deedee.appBase.AppBaseFragment;
import com.solutionavenues.deedee.helpers.RegexValidations;
import com.solutionavenues.deedee.model.request.AddGroupRequestModel;
import com.solutionavenues.deedee.model.response.BaseWebResponseModel;
import com.solutionavenues.deedee.model.response.CenterListResponseModel;
import com.solutionavenues.deedee.rest.ApiHitListener;
import com.solutionavenues.deedee.rest.ApiIds;
import com.solutionavenues.deedee.util.Utils;

import java.util.ArrayList;

/**
 * Created by Azher on 18/6/18.
 */
public class AddGroupFragment extends AppBaseFragment implements ApiHitListener {
    private TextView tv_submit;
    private EditText et_group_name, et_center_name;
    private ArrayList<CenterListResponseModel.DataBean> centerList = new ArrayList<>();
    private ArrayList<String> centerTitleList = new ArrayList<>();
    private int centerId;

    @Override
    public int getLayoutResourceId() {
        return R.layout.fragment_add_group;
    }

    @Override
    public void initializeComponent() {
        findViews();
       // getCenterList();
    }

    private void findViews() {
        et_group_name = getView().findViewById(R.id.et_group_name);
        et_center_name = getView().findViewById(R.id.et_center_name);
        tv_submit = getView().findViewById(R.id.tv_submit);
        tv_submit.setOnClickListener(this);
        et_center_name.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_submit:
                if (checkValidation()) {
                    addGroup();
                }
                break;
            case R.id.et_center_name:
                if (centerTitleList != null && centerTitleList.size() > 0) {
                    openCentersDialog();
                } else {
                    displayToast(getString(R.string.no_centers_available));
                }
                break;
        }

    }


    // validation for ALL fields
    private boolean checkValidation() {
        boolean status = true;
        if (!RegexValidations.hasText(et_group_name, getActivity(), getString(R.string.GROUP_NAME_MSG))) {
            return false;
        }
       /* if (!RegexValidations.hasText(et_center_name, getActivity(), getString(R.string.CENTER_NAME_MSG))) {
            return false;
        }*/
        return status;
    }

    private void openCentersDialog() {
        Utils.hideKeyboard(getActivity());
        Utils.showAlertDialog(getActivity(),
                getString(R.string.select_center),
                centerTitleList.toArray(new String[centerTitleList.size()]),
                onClickedCenterListner);
    }


    DialogInterface.OnClickListener onClickedCenterListner = new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int item) {
            if (centerList != null && centerList.size()>0) {
                centerId=centerList.get(item).getId();
            }
            et_center_name.setText("" + centerTitleList.toArray(new String[centerTitleList.size()])[item]);
        }
    };


    // call to getCenterList service
    private void getCenterList() {
        if (ConnectionDetector.isNetAvail(getActivity())) {
            displayProgressBar(false, getActivity());
            getRestClient().callback(this).getCenterList();
        } else {
            displayErrorDialog(getString(R.string.error_internet_connection));
        }
    }

    // call to addGroup service
    private void addGroup() {
        if (ConnectionDetector.isNetAvail(getActivity())) {
            displayProgressBar(false, getActivity());
            String group_name = et_group_name.getText().toString();
            String user_id = String.valueOf(getMyApplication().getUserPrefs().getLoggedInUser().getId());
            AddGroupRequestModel groupRequestModel = new AddGroupRequestModel();
            groupRequestModel.setCenter_id(String.valueOf(centerId));
            groupRequestModel.setName(group_name);
            groupRequestModel.setLat(getMyApplication().getAppPrefs().getCurrentLatitude());
            groupRequestModel.setLng(getMyApplication().getAppPrefs().getCurrentLongitude());
            groupRequestModel.setUser_id(user_id);
            getRestClient().callback(this).addGroup(groupRequestModel);
        } else {
            displayErrorDialog(getString(R.string.error_internet_connection));
        }
    }


    @Override
    public void onSuccessResponse(int apiId, Object response) {
        dismissProgressBar(getActivity());
        if (apiId == ApiIds.ID_ADD_GROUP) {
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
        } else if (apiId == ApiIds.ID_GET_CENTERS) {
            CenterListResponseModel responseModel = (CenterListResponseModel) response;
            if (responseModel != null) {
                if (!responseModel.isError()) {
                    centerList.clear();
                    centerTitleList.clear();
                    centerList = responseModel.getData();
                    for (int i = 0; i < responseModel.getData().size(); i++) {
                        centerTitleList.add(responseModel.getData().get(i).getName());
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
