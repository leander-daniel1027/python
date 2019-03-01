package com.solutionavenues.deedee.ui.dashboard.centerform;

import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

import com.helper.ConnectionDetector;
import com.solutionavenues.deedee.R;
import com.solutionavenues.deedee.appBase.AppBaseFragment;
import com.solutionavenues.deedee.helpers.RegexValidations;
import com.solutionavenues.deedee.model.request.AddCenterRequestModel;
import com.solutionavenues.deedee.model.response.AddCenterResponseModel;
import com.solutionavenues.deedee.model.response.BranchListResponseModel;
import com.solutionavenues.deedee.model.response.LatLngFromAddressModel;
import com.solutionavenues.deedee.rest.ApiHitListener;
import com.solutionavenues.deedee.rest.ApiIds;
import com.solutionavenues.deedee.ui.dashboard.centerform.adapter.DialogCenterListAdapter;
import com.solutionavenues.deedee.util.Utils;

import java.util.ArrayList;

/**
 * Created by Azher on 18/6/18.
 */
public class AddCenterFragment extends AppBaseFragment implements ApiHitListener {
    private TextView tv_submit;
    private EditText et_center_name, et_center_owner_name, et_center_owner_contant, et_branch_name;
    private ArrayList<BranchListResponseModel.DataBean> branchList = new ArrayList<>();
    private ArrayList<String> branchTitleList = new ArrayList<>();
    private String location_address = "";
    private String location_latitude = "";
    private String location_longitude = "";
    private int branch_id;

    @Override
    public int getLayoutResourceId() {
        return R.layout.fragment_add_center;
    }

    @Override
    public void initializeComponent() {
        findViews();
        getBranchList();
    }

    private void findViews() {
        et_center_name = getView().findViewById(R.id.et_center_name);
        et_center_owner_name = getView().findViewById(R.id.et_center_owner_name);
        et_center_owner_contant = getView().findViewById(R.id.et_center_owner_contant);
        et_branch_name = getView().findViewById(R.id.et_branch_name);
        tv_submit = getView().findViewById(R.id.tv_submit);
        tv_submit.setOnClickListener(this);
        et_branch_name.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_submit:
                if (checkValidation()) {
                    location_latitude = getMyApplication().getAppPrefs().getCurrentLatitude();
                    location_longitude = getMyApplication().getAppPrefs().getCurrentLongitude();
                    String latlng = location_latitude + "," + location_longitude;
                    getLatLngAddress(latlng);
                }
                break;
            case R.id.et_branch_name:
                if (branchTitleList != null && branchTitleList.size() > 0) {
                    openBranchDialog();
                } else {
                    displayToast(getString(R.string.no_branches_available));
                }
                break;


        }

    }


    // validation for ALL fields
    private boolean checkValidation() {
        boolean status = true;
        if (!RegexValidations.hasText(et_center_name, getActivity(), getString(R.string.CENTER_NAME_MSG))) {
            return false;
        }
        if (!RegexValidations.hasText(et_center_owner_name, getActivity(), getString(R.string.CENTER_OWNER_NAME_MSG))) {
            return false;
        }
        if (!RegexValidations.hasText(et_center_owner_contant, getActivity(), getString(R.string.CENTER_OWNER_CONTACT_MSG))) {
            return false;
        }
        if (!RegexValidations.hasText(et_branch_name, getActivity(), getString(R.string.BRANCH_NAME_MSG))) {
            return false;
        }
        return status;
    }

    private void openBranchDialog() {
        Utils.hideKeyboard(getActivity());
        Utils.showAlertDialog(getActivity(),
                getString(R.string.select_branch),
                branchTitleList.toArray(new String[branchTitleList.size()]),
                onClickedBranchListner);
    }


    DialogInterface.OnClickListener onClickedBranchListner = new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int item) {
            branch_id = branchList.get(item).getId();
            et_branch_name.setText("" + branchTitleList.toArray(new String[branchTitleList.size()])[item]);
        }
    };

    // Method for getting Address from lat long through google API
    private void getLatLngAddress(String latlng) {
        displayProgressBar(false, getActivity());
        if (ConnectionDetector.isNetAvail(getActivity())) {
            getRestClient().callback(this).getLatLngAddress(latlng);
        } else {
            displayErrorDialog(getString(R.string.error_internet_connection));
        }

    }

    // call to getBranchList service
    private void getBranchList() {
        if (ConnectionDetector.isNetAvail(getActivity())) {
            displayProgressBar(false, getActivity());
            getRestClient().callback(this).getBranchList();
        } else {
            displayErrorDialog(getString(R.string.error_internet_connection));
        }
    }

    // call to addCenter service
    private void addCenter() {
        if (ConnectionDetector.isNetAvail(getActivity())) {
            String center_name = et_center_name.getText().toString();
            String center_owner_name = et_center_owner_name.getText().toString();
            String center_owner_contant = et_center_owner_contant.getText().toString();
            String user_id = String.valueOf(getMyApplication().getUserPrefs().getLoggedInUser().getId());
            AddCenterRequestModel addCenterRequestModel = new AddCenterRequestModel();
            addCenterRequestModel.setUser_id(user_id);
            addCenterRequestModel.setName(center_name);
            addCenterRequestModel.setCenter_owner(center_owner_name);
            addCenterRequestModel.setCenter_owner_contact(center_owner_contant);
            addCenterRequestModel.setLatitude(location_latitude);
            addCenterRequestModel.setLongitude(location_longitude);
            addCenterRequestModel.setBranche_id(String.valueOf(branch_id));
            addCenterRequestModel.setCenter_location(location_address);
            getRestClient().callback(this).addCenter(addCenterRequestModel);
        } else {
            displayErrorDialog(getString(R.string.error_internet_connection));
        }
    }


    public void showCentersDialog(AddCenterResponseModel responseModel) {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_nearby_centers);

        TextView tv_message = dialog.findViewById(R.id.tv_message);
        TextView tv_ok = dialog.findViewById(R.id.tv_ok);
        tv_message.setText(responseModel.getMessage());

        RecyclerView rv_nearbycenters = dialog.findViewById(R.id.rv_nearbycenters);
        rv_nearbycenters.setLayoutManager(new LinearLayoutManager(getActivity()));
        if (responseModel.getData() != null && responseModel.getData().size() > 0) {
            DialogCenterListAdapter centerListAdapter = new DialogCenterListAdapter(getActivity(), responseModel.getData());
            rv_nearbycenters.setAdapter(centerListAdapter);
        }

        tv_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();

    }


    @Override
    public void onSuccessResponse(int apiId, Object response) {
        if (apiId == ApiIds.ID_ADD_CENTER) {
            dismissProgressBar(getActivity());
            AddCenterResponseModel responseModel = (AddCenterResponseModel) response;
            if (responseModel != null) {
                if (!responseModel.isError()) {
                    displayToast(responseModel.getMessage());
                    try {
                        getDashBoardActivity().onBackPressed();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    if (responseModel.getCode() == 2) {
                        if (responseModel != null) {
                            showCentersDialog(responseModel);
                        }
                    } else {
                        displayErrorDialog(responseModel.getMessage());
                    }
                }
            }
        } else if (apiId == ApiIds.ID_GET_BRANCHES) {
            dismissProgressBar(getActivity());
            BranchListResponseModel responseModel = (BranchListResponseModel) response;
            if (responseModel != null) {
                if (!responseModel.isError()) {
                    branchList.clear();
                    branchTitleList.clear();
                    branchList = responseModel.getData();
                    for (int i = 0; i < responseModel.getData().size(); i++) {
                        branchTitleList.add(responseModel.getData().get(i).getName());
                    }
                } else {
                    displayErrorDialog(responseModel.getMessage());
                }
            }
        } else if (apiId == ApiIds.ID_GET_LATLNG_ADDRESS) {
            LatLngFromAddressModel lngFromAddressModel = (LatLngFromAddressModel) response;
            if (lngFromAddressModel != null && lngFromAddressModel.getStatus().equalsIgnoreCase("ok")) {
                location_address = lngFromAddressModel.getResults().get(0).getFormatted_address();
                printLog(location_address);
            }
            addCenter();
        }
    }

    @Override
    public void onFailResponse(int apiId, String error) {
        dismissProgressBar(getActivity());
        displayErrorDialog(error);

    }


}
