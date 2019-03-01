package com.solutionavenues.deedee.ui.dashboard.cgt;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.helper.ConnectionDetector;
import com.solutionavenues.deedee.R;
import com.solutionavenues.deedee.appBase.AppBaseFragment;
import com.solutionavenues.deedee.imagePicker.FileInformation;
import com.solutionavenues.deedee.imagePicker.ImagePickDialog;
import com.solutionavenues.deedee.model.request.AddCGTRequestModel;
import com.solutionavenues.deedee.model.response.BaseWebResponseModel;
import com.solutionavenues.deedee.model.response.GroupListResponseModel;
import com.solutionavenues.deedee.model.response.UploadImageResponseModel;
import com.solutionavenues.deedee.rest.ApiHitListener;
import com.solutionavenues.deedee.rest.ApiIds;
import com.solutionavenues.deedee.rest.BaseArguments;
import com.solutionavenues.deedee.rest.RetrofitUtils;

import okhttp3.MultipartBody;

/**
 * Created by Azher on 20/6/18.
 */
public class AddCGTFormFragment extends AppBaseFragment implements ApiHitListener {
    private static final String TAG = "AddCGTFormFragment";
    private TextView tv_submit;
    private RadioGroup rg_question1, rg_question2, rg_question3, rg_question4, rg_day_type;
    private ImageView iv_group_photo;
    private String filePathGroupPhoto;

    public void setGroupData(GroupListResponseModel.DataBean groupData) {
        this.groupData = groupData;
    }
    public GroupListResponseModel.DataBean groupData;


    @Override
    public int getLayoutResourceId() {
        return R.layout.fragment_add_cgt;
    }

    @Override
    public void initializeComponent() {
        findViews();
    }

    private void findViews() {
        rg_question1 = getView().findViewById(R.id.rg_question1);
        rg_question2 = getView().findViewById(R.id.rg_question2);
        rg_question3 = getView().findViewById(R.id.rg_question3);
        rg_question4 = getView().findViewById(R.id.rg_question4);
        rg_day_type = getView().findViewById(R.id.rg_day_type);
        iv_group_photo = getView().findViewById(R.id.iv_group_photo);
        iv_group_photo.setOnClickListener(this);
        tv_submit = getView().findViewById(R.id.tv_submit);
        tv_submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_submit:
                if (!TextUtils.isEmpty(filePathGroupPhoto)) {
                    addCGT();
                } else {
                    displayToast(getString(R.string.please_select_group_photo));
                }

                break;
            case R.id.iv_group_photo:
                openPicker();
                break;

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

    // call to addCGT service
    private void addCGT() {
        if (ConnectionDetector.isNetAvail(getActivity())) {
            displayProgressBar(false, getActivity());
            String user_id = String.valueOf(getMyApplication().getUserPrefs().getLoggedInUser().getId());
            AddCGTRequestModel addCGTRequestModel = new AddCGTRequestModel();
            addCGTRequestModel.setUser_id(user_id);
            addCGTRequestModel.setLead_id("");
            addCGTRequestModel.setQuestion_a(getRadioButtonStateWithValue(rg_question1, getString(R.string.yes)));
            addCGTRequestModel.setQuestion_b(getRadioButtonStateWithValue(rg_question2, getString(R.string.yes)));
            addCGTRequestModel.setQuestion_c(getRadioButtonStateWithValue(rg_question3, getString(R.string.yes)));
            addCGTRequestModel.setQuestion_d(getRadioButtonStateWithValue(rg_question4, getString(R.string.yes)));
            addCGTRequestModel.setDay1(getRadioButtonStateWithValue(rg_day_type, getString(R.string.day1)));
            addCGTRequestModel.setDay2(getRadioButtonStateWithValue(rg_day_type, getString(R.string.day2)));
            addCGTRequestModel.setFile(filePathGroupPhoto);
            getRestClient().callback(this).addCGT(addCGTRequestModel);
        } else {
            displayErrorDialog(getString(R.string.error_internet_connection));
        }
    }


    private void uploadImage(String filePath, String type) {
        if (ConnectionDetector.isNetAvail(getActivity())) {
            MultipartBody.Part part = RetrofitUtils.createFilePart(BaseArguments.ARG_FILE,
                    filePath, RetrofitUtils.MEDIA_TYPE_IMAGE_PNG);
            getRestClient().callback(this).uploadImage(part, type);
        }
    }

    private void openPicker() {
        ImagePickDialog picDialog = ImagePickDialog.getNewInstance(false);
        picDialog.setProfilePicDialogListner(new ImagePickDialog.ProfilePicDialogListner() {
            @Override
            public void onProfilePicSelected(FileInformation fileInformation) {
                Log.e(TAG, "onProfilePicSelected: " + fileInformation.getFilePath());
                filePathGroupPhoto = fileInformation.getBitmapPathForUpload(getActivity());
                iv_group_photo.setImageBitmap(fileInformation.getThumbBitmap(getActivity()));

            }

            @Override
            public void onProfilePicRemoved() {
                iv_group_photo.setImageResource(R.mipmap.default_image);
            }
        });
        picDialog.show(getChildFm(), picDialog.getClass().getSimpleName());
    }


    @Override
    public void onSuccessResponse(int apiId, Object response) {
        if (apiId == ApiIds.ID_ADD_CGT) {
            BaseWebResponseModel responseModel = (BaseWebResponseModel) response;
            if (responseModel != null) {
                if (!responseModel.isError()) {
                    uploadImage(filePathGroupPhoto, "");
                } else {
                    dismissProgressBar(getActivity());
                }
            } else {
                dismissProgressBar(getActivity());
            }
        } else if (apiId == ApiIds.ID_UPLOAD_IMAGE) {
            dismissProgressBar(getActivity());
            UploadImageResponseModel responseModel = (UploadImageResponseModel) response;
            if (responseModel != null) {
                if (!responseModel.isError()) {
                    try {
                        getDashBoardActivity().onBackPressed();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    dismissProgressBar(getActivity());
                }
            } else {
                dismissProgressBar(getActivity());
            }
        }
    }

    @Override
    public void onFailResponse(int apiId, String error) {
        dismissProgressBar(getActivity());
        displayErrorDialog(error);

    }


}
