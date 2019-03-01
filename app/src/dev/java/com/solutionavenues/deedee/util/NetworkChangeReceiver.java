package com.solutionavenues.deedee.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.helper.ConnectionDetector;
import com.solutionavenues.deedee.R;
import com.solutionavenues.deedee.database.tables.LeadTable;
import com.solutionavenues.deedee.model.request.AddLeadRequestModel;
import com.solutionavenues.deedee.model.response.LeadResponseModel;
import com.solutionavenues.deedee.rest.ApiHitListener;
import com.solutionavenues.deedee.rest.ApiIds;
import com.solutionavenues.deedee.rest.RestClient;

import java.util.ArrayList;

public class NetworkChangeReceiver extends BroadcastReceiver implements ApiHitListener {
ApiHitListener apiHitListener;
    @Override
    public void onReceive(final Context context, final Intent intent) {
        final ConnectivityManager connMgr = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        final android.net.NetworkInfo wifi = connMgr
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        final android.net.NetworkInfo mobile = connMgr
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if (isOnline(context)) {
            apiHitListener=this;
//submitAppForm();
        }
    }
    ArrayList<String> local_id=new ArrayList();
    private void submitAppForm() {
        LeadTable leadTable = new LeadTable();
         ArrayList<AddLeadRequestModel> localLeadList = new ArrayList<>();
        localLeadList = leadTable.getAllLeads();
        for (int i=0; i<localLeadList.size();i++) {
            local_id.add(localLeadList.get(i).local_id);
            new RestClient().callback(apiHitListener).submitAppForm(localLeadList.get(i));
        }
    }
    public boolean isOnline(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        //should check null because in airplane mode it will be null
        return (netInfo != null && netInfo.isConnected());
    }

    @Override
    public void onSuccessResponse(int apiId, Object response) {
        if (apiId == ApiIds.ID_SUBMIT_APPFORM) {
            LeadResponseModel responseModel = (LeadResponseModel) response;
            if (responseModel != null) {
//                if (!responseModel.isError()) {
//                    LeadTable leadTable = new LeadTable();
//
//                    if (allLeadImages != null && allLeadImages.size() > 0) {
//                        uploadImage(allLeadImages.get(0), false);
//                    } else {
//                        dismissProgressBar(getActivity());
//                        displayAlertDialog(getString(R.string.message), responseModel.getMessage(), this);
//                    }
//                } else {
//                    dismissProgressBar(getActivity());
//                    displayErrorDialog(responseModel.getMessage());
//                }
            }
        }
    }

    @Override
    public void onFailResponse(int apiId, String error) {

    }
}