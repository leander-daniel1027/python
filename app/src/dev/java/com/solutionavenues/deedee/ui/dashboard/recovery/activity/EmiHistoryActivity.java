package com.solutionavenues.deedee.ui.dashboard.recovery.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.helper.ConnectionDetector;
import com.solutionavenues.deedee.R;
import com.solutionavenues.deedee.appBase.AppBaseActivity;
import com.solutionavenues.deedee.model.response.GetEmiHistoryModel;
import com.solutionavenues.deedee.rest.ApiHitListener;
import com.solutionavenues.deedee.rest.ApiIds;
import com.solutionavenues.deedee.rest.BaseArguments;
import com.solutionavenues.deedee.rest.RestClient;
import com.solutionavenues.deedee.ui.dashboard.recovery.adapter.EmiHistoryAdapter;

import java.util.ArrayList;

public class EmiHistoryActivity extends AppBaseActivity implements ApiHitListener {

    private TextView tv_member_name;
    private TextView tv_title;
    private ImageView iv_back;
    private RecyclerView rv_emis;
    private EmiHistoryAdapter emiHistoryAdapter;
    private ArrayList<GetEmiHistoryModel.DataBean.EmiDataBean> emiList;
    private RestClient mRestClient;
    public String group_id;
    public int application_area_id;

    @Override
    public int getLayoutResourceId() {
        return R.layout.activity_emi_history;
    }

    @Override
    public void initializeComponent() {
        findViews();
        init();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            group_id = bundle.getString(BaseArguments.ARG_GROUP_ID);
            application_area_id = bundle.getInt(BaseArguments.ARG_APPLICATION_AREA_ID);
            getEmiHistory(group_id,application_area_id);
        }

    }

    private void init() {
        mRestClient = new RestClient();
        iv_back.setOnClickListener(this);
        rv_emis.setLayoutManager(new LinearLayoutManager(this));
        emiList = new ArrayList<>();
        setAdapter();
    }

    private void setAdapter() {
        emiHistoryAdapter = new EmiHistoryAdapter(this, emiList, this);
        rv_emis.setAdapter(emiHistoryAdapter);
    }

    private void findViews() {
        rv_emis = findViewById(R.id.rv_emis);
        iv_back = findViewById(R.id.iv_back);
        tv_member_name = findViewById(R.id.tv_member_name);
        tv_title = findViewById(R.id.tv_title);
        tv_title.setText(getString(R.string.emi_collection));
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;

        }

    }


    // call to getEmiHistory service
    private void getEmiHistory(String group_id, int application_area_id) {
        if (ConnectionDetector.isNetAvail(this)) {
            displayProgressBar(false);
            mRestClient.callback(this).getEmiHistory(group_id, getCurrentDate(), String.valueOf(application_area_id));
        } else {
            showToast(getString(R.string.error_internet_connection));
        }
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
        if (apiId == ApiIds.ID_EMI_HISTORY) {
            GetEmiHistoryModel responseModel = (GetEmiHistoryModel) response;
            if (responseModel != null) {
                if (responseModel.isIsSuccess()) {
                    emiList = responseModel.getData().getEmi_data();
                    tv_member_name.setText(responseModel.getData().getUser_data().getCustomer_name());
                    emiHistoryAdapter.emiList = emiList;
                    emiHistoryAdapter.notifyDataSetChanged();
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
