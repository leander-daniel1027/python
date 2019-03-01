package com.solutionavenues.deedee.ui.dashboard.recovery.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.helper.ConnectionDetector;
import com.solutionavenues.deedee.MyApplication;
import com.solutionavenues.deedee.R;
import com.solutionavenues.deedee.appBase.AppBaseActivity;
import com.solutionavenues.deedee.model.response.RecoveryGroupModel;
import com.solutionavenues.deedee.rest.ApiHitListener;
import com.solutionavenues.deedee.rest.ApiIds;
import com.solutionavenues.deedee.rest.BaseArguments;
import com.solutionavenues.deedee.rest.RestClient;
import com.solutionavenues.deedee.ui.dashboard.recovery.adapter.GroupListAdapter;
import com.solutionavenues.deedee.util.Utils;

import java.util.ArrayList;

public class RecoveryGroupsActivity extends AppBaseActivity implements ApiHitListener {

    private TextView tv_title;
    private ImageView iv_back;
    private RecyclerView rv_groups;
    private GroupListAdapter groupListAdapter;
    private ArrayList<RecoveryGroupModel.DataBean> groupList;
    private RestClient mRestClient;

    @Override
    public int getLayoutResourceId() {
        return R.layout.activity_recovery_groups;
    }

    @Override
    public void initializeComponent() {
        findViews();
        init();
        getRecoveryGroups();
    }

    private void init() {
        mRestClient = new RestClient();
        iv_back.setOnClickListener(this);
        rv_groups.setLayoutManager(new LinearLayoutManager(this));
        groupList = new ArrayList<>();
        setAdapter();
    }

    private void setAdapter() {
        groupListAdapter = new GroupListAdapter(this, groupList, this);
        rv_groups.setAdapter(groupListAdapter);
    }

    private void findViews() {
        rv_groups = findViewById(R.id.rv_groups);
        iv_back = findViewById(R.id.iv_back);
        tv_title = findViewById(R.id.tv_title);
        tv_title.setText(getString(R.string.emi_collection));
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_call:
                int pos_call= (int) v.getTag();
                String number=groupList.get(pos_call).getLeader_contact();
                Utils.makePhoneCall(number,this);
                break;
            case R.id.iv_location:

                break;
            case R.id.iv_view:
                int pos= (int) v.getTag();
                int group_id=groupList.get(pos).getGroup_id();
                Bundle bundle=new Bundle();
                bundle.putInt(BaseArguments.ARG_GROUP_ID,group_id);
                sendActivity(RecoveryGroupsActivity.this, MemberListActivity.class,bundle);
                break;


        }

    }


    // call to getRecoveryGroups service
    private void getRecoveryGroups() {
        if (ConnectionDetector.isNetAvail(this)) {
            displayProgressBar(false);
            mRestClient.callback(this).getRecoveryGroups(
                    String.valueOf(MyApplication.getInstance()
                            .getUserPrefs().getLoggedInUser().getId()), getCurrentDate());
        } else {
            showToast(getString(R.string.error_internet_connection));
        }
    }


    @Override
    public void onSuccessResponse(int apiId, Object response) {
        if (apiId == ApiIds.ID_GET_RECOVERY_GROUPS) {
            dismissProgressBar();
            RecoveryGroupModel responseModel = (RecoveryGroupModel) response;
            if (responseModel != null) {
                if (responseModel.isIsSuccess()) {
                    groupList = responseModel.getData();
                    groupListAdapter.groupList = groupList;
                    groupListAdapter.notifyDataSetChanged();
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
