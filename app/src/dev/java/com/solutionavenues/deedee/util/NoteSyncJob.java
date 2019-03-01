package com.solutionavenues.deedee.util;

import android.location.Location;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobManager;
import com.evernote.android.job.JobRequest;
import com.helper.ConnectionDetector;
import com.solutionavenues.deedee.MyApplication;
import com.solutionavenues.deedee.R;
import com.solutionavenues.deedee.model.response.BaseWebResponseModel;
import com.solutionavenues.deedee.rest.ApiHitListener;
import com.solutionavenues.deedee.rest.ApiIds;
import com.solutionavenues.deedee.rest.RestClient;

import java.util.Set;
import java.util.concurrent.TimeUnit;

public class NoteSyncJob extends Job implements ApiHitListener {

    public static final String TAG = "job_note_sync";

    @Override
    @NonNull
    protected Result onRunJob(@NonNull Params params) {
        if (MyApplication.getInstance().getUserPrefs().getLoggedInUser() != null) {
                    addLocation();
        }
        return Result.SUCCESS;
    }
    RestClient mRestClient;

    private void addLocation() {
        if (mRestClient == null) {
            mRestClient = new RestClient();
        }
        if (ConnectionDetector.isNetAvail(getContext())) {
            double currentLatitude=0.0, currentLongitude=0.0;
            if (MyApplication.getInstance().getAppPrefs().getCurrentLatitude()!=null && MyApplication.getInstance().getAppPrefs().getCurrentLongitude()!=null) {
                currentLatitude = Double.parseDouble(MyApplication.getInstance().getAppPrefs().getCurrentLatitude());
                currentLongitude = Double.parseDouble(MyApplication.getInstance().getAppPrefs().getCurrentLongitude());
            }
            if (currentLatitude!=0.0 && currentLongitude!=0.0){
                String user_id = String.valueOf(MyApplication.getInstance().getUserPrefs().getLoggedInUser().getId());
                mRestClient.callback(this).addLocation(user_id, String.valueOf(currentLatitude), String.valueOf(currentLongitude));

            }
            //            mRestClient.callback(this).addLocation(user_id, String.valueOf(location.getLatitude()), String.valueOf(location.getLongitude()));
        } else {
//            Utils.printLog("addLocation: ", getString(R.string.error_internet_connection));
//            Utils.showToast(this, getString(R.string.error_internet_connection));
        }
    }


    @Override
    public void onSuccessResponse(int apiId, Object response) {
        if (apiId == ApiIds.ID_ADD_LOCATION) {
            BaseWebResponseModel responseModel = (BaseWebResponseModel) response;
            if (responseModel != null) {
                if (!responseModel.isError()) {
//                    Utils.printLog("addLocation: ", getString(R.string.app_name) + " " + getString(R.string.location_added));
                }
            }
        }
    }

    @Override
    public void onFailResponse(int apiId, String error) {
//        Utils.printLog("addLocation: ", getString(R.string.error_in_add_location));
//        Utils.showToast(this, getString(R.string.app_name) + " " + getString(R.string.error_internet_connection));
    }
    public static void scheduleJob() {
         long INTERVAL = 15;
        try {
            String location_update_time = MyApplication.getInstance().getUserPrefs().getLoggedInUser().getLo_time();
            if (!TextUtils.isEmpty(location_update_time)) {
                INTERVAL = Long.parseLong(location_update_time);
            }
        } catch (Exception e) {
        }
        Set<JobRequest> jobRequests = JobManager.instance().getAllJobRequestsForTag(NoteSyncJob.TAG);
        if (!jobRequests.isEmpty()) {
            return;
        }
        new JobRequest.Builder(NoteSyncJob.TAG)
                .setPeriodic(TimeUnit.MINUTES.toMillis(INTERVAL), TimeUnit.MINUTES.toMillis(INTERVAL))
                .setUpdateCurrent(true) // calls cancelAllForTag(NoteSyncJob.TAG) for you
                .setRequiredNetworkType(JobRequest.NetworkType.CONNECTED)
                .setRequirementsEnforced(true)
                .build()
                .schedule();
    }
}