package com.solutionavenues.deedee.ui.dashboard.telecaller;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.helper.ConnectionDetector;
import com.solutionavenues.deedee.R;
import com.solutionavenues.deedee.appBase.AppBaseFragment;
import com.solutionavenues.deedee.helpers.RegexValidations;
import com.solutionavenues.deedee.model.request.AddAppointmentRequestModel;
import com.solutionavenues.deedee.model.response.AppointmentListResponseModel;
import com.solutionavenues.deedee.model.response.BaseWebResponseModel;
import com.solutionavenues.deedee.rest.ApiHitListener;
import com.solutionavenues.deedee.rest.ApiIds;
import com.solutionavenues.deedee.ui.dashboard.telecaller.adapter.AppointmentListAdapter;
import com.solutionavenues.deedee.util.Constants;
import com.solutionavenues.deedee.util.Utils;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.ArrayList;

/**
 * Created by Azher on 18/6/18.
 */
public class AddAppointmentFragment extends AppBaseFragment implements ApiHitListener, DatePickerDialog.OnDateSetListener {
    private TextView tv_save;
    private EditText et_remark, et_place, et_date;
    private ArrayList<AppointmentListResponseModel.DataBean> appointmentList = new ArrayList<>();
    String user_id;
    AppointmentListAdapter appointmentListAdapter;
    private RecyclerView rv_appointment;
    private DatePickerDialog dpd;

    public void setLead_id(String lead_id) {
        this.lead_id = lead_id;
    }

    public String lead_id;

    @Override
    public int getLayoutResourceId() {
        return R.layout.fragment_add_appointment;
    }

    @Override
    public void initializeComponent() {
        user_id = String.valueOf(getMyApplication().getUserPrefs().getLoggedInUser().getId());
        findViews();
        setAdapter();
        getAppointmentList();
    }


    private void findViews() {
        rv_appointment = getView().findViewById(R.id.rv_appointment);
        rv_appointment.setLayoutManager(new LinearLayoutManager(getActivity()));
        et_remark = getView().findViewById(R.id.et_remark);
        et_place = getView().findViewById(R.id.et_place);
        et_date = getView().findViewById(R.id.et_date);
        tv_save = getView().findViewById(R.id.tv_save);
        tv_save.setOnClickListener(this);
        et_date.setOnClickListener(this);
    }

    private void setAdapter() {
        appointmentListAdapter = new AppointmentListAdapter(getActivity(), appointmentList, this);
        rv_appointment.setAdapter(appointmentListAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_save:
                if (checkValidation()) {
                    addAppointment();
                }
                break;
            case R.id.et_date:
                openDatePicker(true, this);
                break;
            case R.id.tv_delete:
                int pos = (int) v.getTag();
                String id = String.valueOf(appointmentList.get(pos).getId());
                deleteAppointment(id);
                break;
        }

    }


    // validation for ALL fields
    private boolean checkValidation() {
        boolean status = true;
        if (!RegexValidations.hasText(et_date, getActivity(), getString(R.string.please_enter_date))) {
            return false;
        }
        if (!RegexValidations.hasText(et_place, getActivity(), getString(R.string.please_enter_place))) {
            return false;
        }
        if (!RegexValidations.hasText(et_remark, getActivity(), getString(R.string.please_enter_remark))) {
            return false;
        }
        return status;
    }


    // call to getAppointmentList service
    private void getAppointmentList() {
        if (ConnectionDetector.isNetAvail(getActivity())) {
            displayProgressBar(false, getActivity());
            getRestClient().callback(this).getAppointment(user_id, lead_id);
        } else {
            displayErrorDialog(getString(R.string.error_internet_connection));
        }
    }

    // call to deleteAppointment service
    private void deleteAppointment(String id) {
        if (ConnectionDetector.isNetAvail(getActivity())) {
            displayProgressBar(false, getActivity());
            getRestClient().callback(this).deleteAppointment(id, user_id);
        } else {
            displayErrorDialog(getString(R.string.error_internet_connection));
        }
    }

    // call to addAppointment service
    private void addAppointment() {
        if (ConnectionDetector.isNetAvail(getActivity())) {
            displayProgressBar(false, getActivity());
            String date = et_date.getText().toString();
            String place = et_place.getText().toString();
            String remark = et_remark.getText().toString();
            AddAppointmentRequestModel requestModel = new AddAppointmentRequestModel();
            requestModel.setUser_id(user_id);
            requestModel.setLead_id(lead_id);
            requestModel.setTele_date(date);
            requestModel.setPlace(place);
            requestModel.setRemark(remark);
            getRestClient().callback(this).addAppointment(requestModel);
        } else {
            displayErrorDialog(getString(R.string.error_internet_connection));
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        dpd = (DatePickerDialog) getActivity().getFragmentManager().findFragmentByTag("Datepickerdialog");
        if (dpd != null) dpd.setOnDateSetListener(this);
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String selected_date = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
        et_date.setText(selected_date);

    }


    @Override
    public void onSuccessResponse(int apiId, Object response) {
        dismissProgressBar(getActivity());
        if (apiId == ApiIds.ID_ADD_APPOINTMENT) {
            BaseWebResponseModel responseModel = (BaseWebResponseModel) response;
            if (responseModel != null) {
                if (!responseModel.isError()) {
                    displayToast(responseModel.getMessage());
                    Utils.addReminder(getActivity(), 3, et_date.getText().toString(),
                            Constants.SERVER_DATE_FORMAT, et_place.getText().toString(), et_remark.getText().toString(), getString(R.string.appointment));
                    et_remark.getText().clear();
                    et_place.getText().clear();
                    et_date.getText().clear();
                    getAppointmentList();
                } else {
                    displayErrorDialog(responseModel.getMessage());
                }
            }
        } else if (apiId == ApiIds.ID_GET_APPOINTMENT) {
            AppointmentListResponseModel responseModel = (AppointmentListResponseModel) response;
            if (responseModel != null) {
                if (!responseModel.isError()) {
                    appointmentList = responseModel.getData();
                    appointmentListAdapter.appointmentList = appointmentList;
                    appointmentListAdapter.notifyDataSetChanged();
                } else {
                    displayErrorDialog(responseModel.getMessage());
                }
            }
        } else if (apiId == ApiIds.ID_DELETE_APPOINTMENT) {
            BaseWebResponseModel responseModel = (BaseWebResponseModel) response;
            if (responseModel != null) {
                if (!responseModel.isError()) {
                    displayToast(responseModel.getMessage());
                    getAppointmentList();
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
