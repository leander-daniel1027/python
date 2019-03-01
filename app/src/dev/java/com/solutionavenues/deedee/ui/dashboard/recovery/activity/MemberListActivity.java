package com.solutionavenues.deedee.ui.dashboard.recovery.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.helper.ConnectionDetector;
import com.solutionavenues.deedee.MyApplication;
import com.solutionavenues.deedee.R;
import com.solutionavenues.deedee.appBase.AppBaseActivity;
import com.solutionavenues.deedee.model.request.SubmitRecoveryDetailsRequestModel;
import com.solutionavenues.deedee.model.response.GroupMemberListModel;
import com.solutionavenues.deedee.model.response.RecoveryApplicationClosedModel;
import com.solutionavenues.deedee.model.response.SubmitRecoveryDetailsModel;
import com.solutionavenues.deedee.rest.ApiHitListener;
import com.solutionavenues.deedee.rest.ApiIds;
import com.solutionavenues.deedee.rest.BaseArguments;
import com.solutionavenues.deedee.rest.RestClient;
import com.solutionavenues.deedee.ui.dashboard.recovery.adapter.MemberListAdapter;

import java.util.ArrayList;

public class MemberListActivity extends AppBaseActivity implements ApiHitListener {

    private TextView tv_group_name;
    private TextView tv_title;
    private ImageView iv_back;
    private RecyclerView rv_members;
    private MemberListAdapter memberListAdapter;
    private ArrayList<GroupMemberListModel.DataBean.MembersBean> memberList;
    private RestClient mRestClient;
    public int group_id;

    @Override
    public int getLayoutResourceId() {
        return R.layout.activity_group_members;
    }

    @Override
    public void initializeComponent() {
        findViews();
        init();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            group_id = bundle.getInt(BaseArguments.ARG_GROUP_ID);
            getRecoveryMembers(group_id);
        }

    }

    private void init() {
        mRestClient = new RestClient();
        iv_back.setOnClickListener(this);
        rv_members.setLayoutManager(new LinearLayoutManager(this));
        memberList = new ArrayList<>();
        setAdapter();
    }

    private void setAdapter() {
        memberListAdapter = new MemberListAdapter(this, memberList, this);
        rv_members.setAdapter(memberListAdapter);
    }

    private void findViews() {
        rv_members = findViewById(R.id.rv_members);
        iv_back = findViewById(R.id.iv_back);
        tv_group_name = findViewById(R.id.tv_group_name);
        tv_title = findViewById(R.id.tv_title);
        tv_title.setText(getString(R.string.emi_collection));
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_submit:
                int pos = (int) v.getTag();
                SubmitRecoveryDetailsRequestModel requestModel = getRecoveryData(pos);
                submitRecoveryCollection(requestModel);
                break;
            case R.id.tv_view_details:
                int pos_view = (int) v.getTag();
                Bundle bundle = new Bundle();
                bundle.putString(BaseArguments.ARG_GROUP_ID, memberList.get(pos_view).getGroup_id());
                bundle.putInt(BaseArguments.ARG_APPLICATION_AREA_ID, memberList.get(pos_view).getApplication_area_id());
                sendActivity(MemberListActivity.this, EmiHistoryActivity.class, bundle);
                break;
            case R.id.tv_dead:
                int pos_dead = (int) v.getTag();
                applicationClosed(String.valueOf(memberList.get(pos_dead).getApplication_area_id()));
                break;

        }

    }


    // call to applicationClosed service
    private void applicationClosed(String application_area_id) {
        if (ConnectionDetector.isNetAvail(this)) {
            displayProgressBar(false);
            mRestClient.callback(this).applicationClosed(application_area_id);
        } else {
            showToast(getString(R.string.error_internet_connection));
        }
    }

    // call to getRecoveryMembers service
    private void getRecoveryMembers(int group_id) {
        if (ConnectionDetector.isNetAvail(this)) {
            displayProgressBar(false);
            mRestClient.callback(this).getRecoveryMembers(
                    String.valueOf(group_id), getCurrentDate());
        } else {
            showToast(getString(R.string.error_internet_connection));
        }
    }

    // call to submitRecoveryCollection service
    private void submitRecoveryCollection(SubmitRecoveryDetailsRequestModel requestModel) {
        if (ConnectionDetector.isNetAvail(this)) {
            displayProgressBar(false);
            mRestClient.callback(this).submitRecoveryCollection(requestModel);
        } else {
            showToast(getString(R.string.error_internet_connection));
        }
    }


    public SubmitRecoveryDetailsRequestModel getRecoveryData(int position) {
        View itemView = rv_members.findViewHolderForAdapterPosition(position).itemView;
        EditText et_collected_amount = itemView.findViewById(R.id.et_collected_amount);
        EditText et_collected_delay_charge = itemView.findViewById(R.id.et_collected_delay_charge);
        CheckBox cb_signature = itemView.findViewById(R.id.cb_signature);
        CheckBox cb_collected_self = itemView.findViewById(R.id.cb_collected_self);
        CheckBox cb_present = itemView.findViewById(R.id.cb_present);
        TextView tv_collected_from = itemView.findViewById(R.id.tv_collected_from);

        SubmitRecoveryDetailsRequestModel requestModel = new SubmitRecoveryDetailsRequestModel();
        requestModel.setUser_id(MyApplication.getInstance().getUserPrefs().getLoggedInUser().getId());
        requestModel.setRecovery_id(memberList.get(position).getEmiData().getRecovery_id());
        requestModel.setCollected_amount(et_collected_amount.getText().toString());
        requestModel.setSingnature(getCheckStatus(cb_signature));
        requestModel.setCollect_from(tv_collected_from.getText().toString());
        requestModel.setCollect_from_self(getCheckStatus(cb_collected_self));
        requestModel.setCollected_date(getCurrentDate());
        requestModel.setPresent_on_center(getCheckStatus(cb_present));
        requestModel.setDelay_charge(et_collected_delay_charge.getText().toString());
        requestModel.setApplication_area_id(memberList.get(position).getApplication_area_id());
        return requestModel;
    }

    private String getCheckStatus(CheckBox checkBox) {
        if (checkBox.isChecked()) {
            return getString(R.string.yes);
        } else
            return getString(R.string.no);
    }


    @Override
    public void onSuccessResponse(int apiId, Object response) {
        dismissProgressBar();
        if (apiId == ApiIds.ID_GET_RECOVERY_MEMBERS) {
            GroupMemberListModel responseModel = (GroupMemberListModel) response;
            if (responseModel != null) {
                if (responseModel.isIsSuccess()) {
                    memberList = responseModel.getData().getMembers();
                    tv_group_name.setText(responseModel.getData().getGroup_data().getGroup_name());
                    memberListAdapter.membersList = memberList;
                    memberListAdapter.notifyDataSetChanged();
                } else {
                    showToast(responseModel.getMessage());
                }
            } else {
                dismissProgressBar();
            }
        } else if (apiId == ApiIds.ID_SUBMIT_RECOVERY_DETAILS) {
            SubmitRecoveryDetailsModel responseModel = (SubmitRecoveryDetailsModel) response;
            if (responseModel != null) {
                if (responseModel.isIsSuccess()) {
                    displayMessageDialog(responseModel.getMessage());
                } else {
                    showToast(responseModel.getMessage());
                }
            } else {
                dismissProgressBar();
            }
        } else if (apiId == ApiIds.ID_APPLICATION_CLOSED) {
            RecoveryApplicationClosedModel responseModel = (RecoveryApplicationClosedModel) response;
            if (responseModel != null) {
                if (responseModel.isIsSuccess()) {
                    displayMessageDialog(responseModel.getMessage());
                    getRecoveryMembers(group_id);
                } else {
                    showToast(responseModel.getMessage());
                }
            } else {
                dismissProgressBar();
            }
        }
    }

    @Override
    public void onFailResponse(int apiId, String error) {
        dismissProgressBar();
        displayErrorDialog(error);
    }
}
