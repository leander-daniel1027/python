package com.solutionavenues.deedee.ui.dashboard.enquiry;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.helper.ConnectionDetector;
import com.solutionavenues.deedee.R;
import com.solutionavenues.deedee.appBase.AppBaseFragment;
import com.solutionavenues.deedee.database.tables.EnquiryTable;
import com.solutionavenues.deedee.imagePicker.FileInformation;
import com.solutionavenues.deedee.imagePicker.ImagePickDialog;
import com.solutionavenues.deedee.model.request.AddEditEnquiryRequestModel;
import com.solutionavenues.deedee.model.response.AddEnquiryResponseModel;
import com.solutionavenues.deedee.model.response.EnquiryListResponseModel;
import com.solutionavenues.deedee.model.response.UploadImageResponseModel;
import com.solutionavenues.deedee.model.response.UserArea;
import com.solutionavenues.deedee.rest.ApiHitListener;
import com.solutionavenues.deedee.rest.ApiIds;
import com.solutionavenues.deedee.rest.BaseArguments;
import com.solutionavenues.deedee.rest.RestService;
import com.solutionavenues.deedee.rest.RetrofitUtils;
import com.solutionavenues.deedee.util.Constants;
import com.solutionavenues.deedee.util.Utils;
import com.squareup.picasso.Picasso;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import okhttp3.MultipartBody;

/**
 * Created by Azher on 18/6/18.
 */
public class AddEnquiryFragment extends AppBaseFragment implements
        ApiHitListener, DatePickerDialog.OnDateSetListener {

    private static String TAG = "AddEnquiryFragment";
    private EditText et_applicant_name,
            et_applicant_father_name,
            et_applicant_mother_name,
            et_spouse_name, et_applicant_dob, et_doc1_number, et_doc_type1,
            et_doc2_number, et_doc_type2,et_pincode,et_loan_amount,
            et_applicant_mobile1, et_applicant_mobile2, et_applicant_address,et_current_address_area;
    private ImageView iv_id_image, iv_aadhar_image;
    private TextView tv_submit, tv_my_enquiries;
    private DatePickerDialog dpd;
    private String selected_date;
    private Calendar calendar;
    private String filePathId = null;
    private String filePathAadhar = null;
    private String aadharServerFileName = "";
    private String idServerFileName = "";
    private ArrayList<String> userArealist = new ArrayList<>();

    List<com.solutionavenues.deedee.model.response.UserAreaList> UserAreaList ;

    public void setRequestModel(AddEditEnquiryRequestModel requestModel) {
        this.requestModel = requestModel;
    }

    public void setEditEnquiryModel(EnquiryListResponseModel.DataBean editEnquiryModel) {
        this.editEnquiryModel = editEnquiryModel;
    }

    public void setEnquiryType(String enquiryType) {
        this.enquiryType = enquiryType;
    }

    public AddEditEnquiryRequestModel requestModel;
    public EnquiryListResponseModel.DataBean editEnquiryModel;
    public String enquiryType;
    EnquiryTable enquiryTable;

    @Override
    public int getLayoutResourceId() {
        return R.layout.fragment_add_enquiry;
    }

    @Override
    public void initializeComponent() {
        enquiryTable = new EnquiryTable();
        iv_id_image = getView().findViewById(R.id.iv_id_image);
        iv_aadhar_image = getView().findViewById(R.id.iv_aadhar_image);
        et_applicant_name = getView().findViewById(R.id.et_applicant_name);
        et_applicant_father_name = getView().findViewById(R.id.et_applicant_father_name);
        et_applicant_mother_name = getView().findViewById(R.id.et_applicant_mother_name);
        et_spouse_name = getView().findViewById(R.id.et_spouse_name);
        et_applicant_dob = getView().findViewById(R.id.et_applicant_dob);
        et_doc1_number = getView().findViewById(R.id.et_doc1_number);
        et_doc_type1 = getView().findViewById(R.id.et_doc_type1);
        et_applicant_mobile1 = getView().findViewById(R.id.et_applicant_mobile1);
        et_applicant_mobile2 = getView().findViewById(R.id.et_applicant_mobile2);
        et_applicant_address = getView().findViewById(R.id.et_applicant_address);
        et_current_address_area = getView().findViewById(R.id.et_current_address_area);
        et_doc2_number = getView().findViewById(R.id.et_doc2_number);
        et_doc_type2 = getView().findViewById(R.id.et_doc_type2);
        tv_my_enquiries = getView().findViewById(R.id.tv_my_enquiries);
        et_pincode = getView().findViewById(R.id.et_pincode);
        et_loan_amount = getView().findViewById(R.id.et_loan_amount);
        tv_my_enquiries = getView().findViewById(R.id.tv_my_enquiries);
        tv_submit = getView().findViewById(R.id.tv_submit);

        et_doc_type1.setText("Aadhar Card");
        et_doc_type2.setText("Voter Id");

        tv_my_enquiries.setOnClickListener(this);
        iv_id_image.setOnClickListener(this);
        iv_aadhar_image.setOnClickListener(this);
        tv_submit.setOnClickListener(this);
        et_applicant_dob.setOnClickListener(this);
        et_doc_type1.setOnClickListener(onDoc1ClickListener);
        et_doc_type2.setOnClickListener(onDoc2ClickListener);
        et_current_address_area.setOnClickListener(this);

        if (!TextUtils.isEmpty(enquiryType)) {
            tv_my_enquiries.setVisibility(View.GONE);
            if (enquiryType.equalsIgnoreCase(Constants.SERVER_ENQUIRY)) {
                setServerEnquiryData(editEnquiryModel);
            } else if (enquiryType.equalsIgnoreCase(Constants.LOCAL_ENQUIRY)) {
                setLocalEnquiryData(requestModel);
            }
        }
        getLeadsFromServer(true);

    }
    // call to getLeadsFromServer service
    private void getLeadsFromServer(boolean progressVisibility) {
        if (ConnectionDetector.isNetAvail(getActivity())) {
            if (progressVisibility) {
                displayProgressBar(false, getActivity());
            }
            String user_id = String.valueOf(getMyApplication().getUserPrefs().getLoggedInUser().getId());
            getRestClient().callback(this).getUserAreaList(user_id);
        } else {
            displayErrorDialog(getString(R.string.error_internet_connection));
        }
    }
    private void setServerEnquiryData(EnquiryListResponseModel.DataBean dataBean) {
        if (dataBean != null) {
            et_applicant_name.setText(dataBean.getApplicant_name());
            et_applicant_father_name.setText(dataBean.getApplicant_father_name());
            et_applicant_mother_name.setText(dataBean.getApplicant_mother_name());
            et_spouse_name.setText(dataBean.getSpouse_name());
            et_applicant_dob.setText(dataBean.getApplicant_dob().split("T")[0]);
            et_current_address_area.setText(dataBean.getArea_id());
            et_applicant_mobile1.setText(dataBean.getApplicant_mobile1());
            et_applicant_mobile2.setText(dataBean.getApplicant_mobile2());
            et_doc2_number.setText(dataBean.getApplicant_aadhar_no());
            et_doc1_number.setText(dataBean.getApplicant_id_number());
            et_doc_type1.setText(dataBean.getApplicant_id_type());
            et_doc_type2.setText(dataBean.getApplicant_aadhar_type());
            et_applicant_address.setText(dataBean.getApplicant_address());
            et_loan_amount.setText(dataBean.getLoan_amount());
            et_pincode.setText(dataBean.getPincode());
            if (!TextUtils.isEmpty(dataBean.getApplicant_aadhar_image())) {
                Picasso.with(getActivity())
                        .load(RestService.IMAGE_BASE_URL + dataBean.getApplicant_aadhar_image())
                        .placeholder(R.mipmap.loading_default)
                        .error(R.mipmap.error_default)
                        .into(iv_aadhar_image);
            } else {
                iv_aadhar_image.setImageResource(R.mipmap.default_image);
            }

            if (!TextUtils.isEmpty(dataBean.getApplicant_id_image())) {
                Picasso.with(getActivity())
                        .load(RestService.IMAGE_BASE_URL + dataBean.getApplicant_id_image())
                        .placeholder(R.mipmap.loading_default)
                        .error(R.mipmap.error_default)
                        .into(iv_id_image);
            } else {
                iv_id_image.setImageResource(R.mipmap.default_image);
            }
        }
    }

    private void setLocalEnquiryData(AddEditEnquiryRequestModel requestModel) {
        if (requestModel != null) {
            et_applicant_name.setText(requestModel.getApplicant_name());
            et_applicant_father_name.setText(requestModel.getApplicant_father_name());
            et_applicant_mother_name.setText(requestModel.getApplicant_mother_name());
            et_spouse_name.setText(requestModel.getSpouse_name());
            et_applicant_dob.setText(requestModel.getApplicant_dob());
            et_current_address_area.setText(requestModel.getArea_id());
            et_applicant_mobile1.setText(requestModel.getApplicant_mobile1());
            et_applicant_mobile2.setText(requestModel.getApplicant_mobile2());
            et_doc2_number.setText(requestModel.getApplicant_aadhar_no());
            et_doc1_number.setText(requestModel.getApplicant_id_number());
            et_doc_type1.setText(requestModel.getApplicant_id_type());
            et_doc_type2.setText(requestModel.getApplicant_aadhar_type());
            et_applicant_address.setText(requestModel.getApplicant_address());
            et_loan_amount.setText(requestModel.getLoan_amount());
            et_pincode.setText(requestModel.getPincode());
            if (!TextUtils.isEmpty(requestModel.getApplicant_aadhar_image())) {
                Bitmap bitmap = BitmapFactory.decodeFile(requestModel.getApplicant_aadhar_image());
                iv_aadhar_image.setImageBitmap(bitmap);
                filePathAadhar = requestModel.getApplicant_aadhar_image();
            } else {
                iv_aadhar_image.setImageResource(R.mipmap.default_image);
            }

            if (!TextUtils.isEmpty(requestModel.getApplicant_id_image())) {
                Bitmap bitmap = BitmapFactory.decodeFile(requestModel.getApplicant_id_image());
                iv_id_image.setImageBitmap(bitmap);
                filePathId = requestModel.getApplicant_id_image();
            } else {
                iv_id_image.setImageResource(R.mipmap.default_image);
            }


        }
    }

    // validation for ALL fields
    private boolean checkValidation(String enquiryType) {
        boolean status = true;
        if (TextUtils.isEmpty(et_applicant_name.getText().toString())) {
            displayErrorDialog(getString(R.string.please_enter_applicant_name));
            status = false;
        } else if (TextUtils.isEmpty(et_applicant_father_name.getText().toString())) {
            displayErrorDialog(getString(R.string.please_enter_applicant_father_name));
            status = false;
        } else if (TextUtils.isEmpty(et_applicant_mother_name.getText().toString())) {
            displayErrorDialog(getString(R.string.please_enter_applicant_mother_name));
            status = false;
        } else if (TextUtils.isEmpty(et_spouse_name.getText().toString())) {
            displayErrorDialog(getString(R.string.please_enter_applicant_spouse_name));
            status = false;
        } else if (TextUtils.isEmpty(et_applicant_dob.getText().toString())) {
            displayErrorDialog(getString(R.string.please_enter_applicant_dob));
            status = false;
        }else if (TextUtils.isEmpty(et_applicant_mobile1.getText().toString())) {
            displayErrorDialog(getString(R.string.please_enter_applicant_mobile_1));
            status = false;
        } else if (TextUtils.isEmpty(et_applicant_mobile2.getText().toString())) {
            displayErrorDialog(getString(R.string.please_enter_applicant_mobile_2));
            status = false;
        }else if (TextUtils.isEmpty(et_loan_amount.getText().toString())) {
            displayErrorDialog(getString(R.string.please_enter_loan_amount));
            status = false;
        }else if (TextUtils.isEmpty(et_pincode.getText().toString())) {
            displayErrorDialog(getString(R.string.please_enter_pincode));
            status = false;
        }else if (TextUtils.isEmpty(et_applicant_address.getText().toString())) {
            displayErrorDialog(getString(R.string.please_enter_applicant_address));
            status = false;
        }else if (TextUtils.isEmpty(et_doc_type1.getText().toString())) {
            displayErrorDialog(getString(R.string.please_enter_doc_type1));
            status = false;
        }else if (TextUtils.isEmpty(et_doc_type2.getText().toString())) {
            displayErrorDialog(getString(R.string.please_enter_doc_type2));
            status = false;
        }  else if (TextUtils.isEmpty(et_doc1_number.getText().toString())) {
            displayErrorDialog(getString(R.string.please_enter_doc_type1_number));
            status = false;
        } else if (TextUtils.isEmpty(et_doc2_number.getText().toString())) {
            displayErrorDialog(getString(R.string.please_enter_doc_type2_number));
            status = false;
        }
        else if (TextUtils.isEmpty(enquiryType) || enquiryType.equalsIgnoreCase(Constants.LOCAL_ENQUIRY)) {
            if (TextUtils.isEmpty(filePathId)) {
                displayErrorDialog(getString(R.string.please_select_id_image));
                status = false;
            } else if (TextUtils.isEmpty(filePathAadhar)) {
                displayErrorDialog(getString(R.string.please_select_aadhar_image));
                status = false;
            }
        }

        return status;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_submit:
                if (checkValidation(enquiryType)) {
                    addEditEnquiry();
                }
                break;
            case R.id.et_applicant_dob:
                openDatePicker();
                break;
            case R.id.iv_id_image:
                openPicker(iv_id_image);
                break;
            case R.id.iv_aadhar_image:
                openPicker(iv_aadhar_image);
                break;
            case R.id.tv_my_enquiries:
                addEnquiryListFragment();
                break;

            case R.id.et_current_address_area:
                if (UserAreaList != null && UserAreaList.size() > 0) {
                    openUserAreaListDialog();
                } else {
                    displayToast(getString(R.string.no_option_available));
                }
                break;
        }

    }
    private void openUserAreaListDialog() {
        Utils.hideKeyboard(getActivity());
        Utils.showAlertDialog(getActivity(),
                getString(R.string.user_area_list),
                userArealist.toArray(new String[userArealist.size()]),
                onClickedUserArealistListner);
    }
    DialogInterface.OnClickListener onClickedUserArealistListner = new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int item) {
            //  user_area_list_id = us.get(item).getId();
            et_current_address_area.setText("" + userArealist.toArray(new String[userArealist.size()])[item]);
        }
    };
    private void addEditEnquiry() {
        if (ConnectionDetector.isNetAvail(getActivity())) {
            displayProgressBar(false, getActivity());
            if (TextUtils.isEmpty(filePathId) && TextUtils.isEmpty(filePathAadhar)) {
                if (editEnquiryModel != null) {
                    addEnquiry(editEnquiryModel.getApplicant_id_image(), editEnquiryModel.getApplicant_aadhar_image());
                }
            } else if (!TextUtils.isEmpty(filePathId)) {
                uploadImage(filePathId, Constants.APPLICANT_ID_IMAGE_TYPE);
            } else if (!TextUtils.isEmpty(filePathAadhar)) {
                uploadImage(filePathAadhar, Constants.AADHAR_IMAGE_TYPE);
            }
        } else {

            AddEditEnquiryRequestModel enquiryRequestModel = getEnquiryData();
            enquiryRequestModel.setApplicant_aadhar_image(filePathAadhar);
            enquiryRequestModel.setApplicant_id_image(filePathId);
            long id = enquiryTable.insert(enquiryRequestModel);
            Log.e(TAG, "enq_insert_id: " + id);
            if (id != -1) {
                displayErrorDialog(getString(R.string.you_are_offline));
                Log.e(TAG, "enq_data_size: " + enquiryTable.getAllEnquiries().size());
            }

        }
    }

    View.OnClickListener onDoc1ClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Utils.hideKeyboard(getActivity());
            Utils.showAlertDialog(getActivity(),
                    getString(R.string.select_document_type),
                    getResources().getStringArray(R.array.enquiry_docuemnt_types),
                    onClickedDoc1Listner);
        }
    };

    DialogInterface.OnClickListener onClickedDoc1Listner = new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int item) {
            et_doc_type1.setText("" + getResources().getStringArray(R.array.enquiry_docuemnt_types)[item]);
        }
    };

    View.OnClickListener onDoc2ClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Utils.hideKeyboard(getActivity());
            Utils.showAlertDialog(getActivity(),
                    getString(R.string.select_document_type),
                    getResources().getStringArray(R.array.enquiry_docuemnt_types),
                    onClickedDoc2Listner);
        }
    };

    DialogInterface.OnClickListener onClickedDoc2Listner = new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int item) {
            et_doc_type2.setText("" + getResources().getStringArray(R.array.enquiry_docuemnt_types)[item]);
        }
    };


    // open date picker dialog
    private void openDatePicker() {
        calendar = Calendar.getInstance();
        int year = calendar.get( Calendar.YEAR)-18;
        dpd = DatePickerDialog.newInstance(
                AddEnquiryFragment.this,
                year,
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
        dpd.show(getActivity().getFragmentManager(), "Datepickerdialog");
        calendar.add(Calendar.YEAR , -18);

        dpd.setMaxDate(calendar);
        dpd.setVersion(DatePickerDialog.Version.VERSION_2);
    }


    @Override
    public void onResume() {
        super.onResume();
        dpd = (DatePickerDialog) getActivity().getFragmentManager().findFragmentByTag("Datepickerdialog");
        if (dpd != null) dpd.setOnDateSetListener(this);
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        selected_date = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
        et_applicant_dob.setText(selected_date);
    }

    private void openPicker(final ImageView imageView) {
        ImagePickDialog picDialog = ImagePickDialog.getNewInstance(false);
        picDialog.setProfilePicDialogListner(new ImagePickDialog.ProfilePicDialogListner() {
            @Override
            public void onProfilePicSelected(FileInformation fileInformation) {
                Log.e(TAG, "onProfilePicSelected: " + fileInformation.getFilePath());
                if (imageView.getId() == iv_aadhar_image.getId()) {
                    filePathAadhar = fileInformation.getBitmapPathForUpload(getActivity());
                    iv_aadhar_image.setImageBitmap(fileInformation.getThumbBitmap(getActivity()));
                } else if (imageView.getId() == iv_id_image.getId()) {
                    filePathId = fileInformation.getBitmapPathForUpload(getActivity());
                    iv_id_image.setImageBitmap(fileInformation.getThumbBitmap(getActivity()));
                }

            }

            @Override
            public void onProfilePicRemoved() {
                iv_id_image.setImageResource(R.mipmap.default_image);
            }
        });
        picDialog.show(getChildFm(), picDialog.getClass().getSimpleName());
    }


    private void addEnquiryListFragment() {
        Bundle b = new Bundle();
        b.putSerializable("array", (Serializable) UserAreaList);
        EnquiryListFragment fragment = new EnquiryListFragment();
        fragment.setArguments(b);
        int enterAnimation = R.anim.translate_right_to_left_anim;
        int exitAnimation = 0;
        int enterAnimationBackStack = 0;
        int exitAnimationBackStack = R.anim.translate_left_to_right_medium;
        try {
            getDashBoardActivity().changeFragment(fragment, true, false, 0,
                    enterAnimation, exitAnimation, enterAnimationBackStack, exitAnimationBackStack, false);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private void uploadImage(String filePath, String type) {
        if (ConnectionDetector.isNetAvail(getActivity())) {
            MultipartBody.Part part = RetrofitUtils.createFilePart(BaseArguments.ARG_FILE,
                    filePath, RetrofitUtils.MEDIA_TYPE_IMAGE_PNG);
            getRestClient().callback(this).uploadImage(part, type);
        }
    }

    // call to addEnquiry service
    private void addEnquiry(String idServerFileName, String aadharServerFileName) {
        if (ConnectionDetector.isNetAvail(getActivity())) {
            // displayProgressBar(false, getActivity());
            AddEditEnquiryRequestModel addEnquiryRequestModel = getEnquiryData();
            addEnquiryRequestModel.setApplicant_id_image(idServerFileName);
            addEnquiryRequestModel.setApplicant_aadhar_image(aadharServerFileName);
           /* String enquiryJson = new Gson().toJson(addEnquiryRequestModel);
            Log.e(TAG, "addEnquiry: " + enquiryJson);*/
            getRestClient().callback(this).addEnquiry(addEnquiryRequestModel);
        } else {
            displayErrorDialog(getString(R.string.error_internet_connection));
        }
    }

    private AddEditEnquiryRequestModel getEnquiryData() {
        String user_id = String.valueOf(getMyApplication().getUserPrefs().getLoggedInUser().getId());
        AddEditEnquiryRequestModel enquiryRequestModel = new AddEditEnquiryRequestModel();
        enquiryRequestModel.setApplicant_name(et_applicant_name.getText().toString());
        enquiryRequestModel.setApplicant_father_name(et_applicant_father_name.getText().toString());
        enquiryRequestModel.setApplicant_mother_name(et_applicant_mother_name.getText().toString());
        enquiryRequestModel.setSpouse_name(et_spouse_name.getText().toString());
        enquiryRequestModel.setApplicant_dob(et_applicant_dob.getText().toString());
        int areaid = userArealist.indexOf(et_current_address_area.getText().toString());
        int areaNameId = UserAreaList.get(areaid).getId();
        enquiryRequestModel.setArea_id(String.valueOf(areaNameId));
        enquiryRequestModel.setArea_name(et_current_address_area.getText().toString());
        enquiryRequestModel.setApplicant_mobile1(et_applicant_mobile1.getText().toString());
        enquiryRequestModel.setApplicant_mobile2(et_applicant_mobile2.getText().toString());
        enquiryRequestModel.setApplicant_address(et_applicant_address.getText().toString());
        enquiryRequestModel.setApplicant_aadhar_no(et_doc2_number.getText().toString());
        enquiryRequestModel.setApplicant_id_number(et_doc1_number.getText().toString());
        enquiryRequestModel.setApplicant_aadhar_type(et_doc_type2.getText().toString());
        enquiryRequestModel.setApplicant_id_type(et_doc_type1.getText().toString());
        enquiryRequestModel.setAdded_form(et_applicant_address.getText().toString());
        enquiryRequestModel.setPincode(et_pincode.getText().toString());
        enquiryRequestModel.setLoan_amount(et_loan_amount.getText().toString());
        enquiryRequestModel.setUser_id(user_id);
        if (!TextUtils.isEmpty(enquiryType)) {
            if (enquiryType.equalsIgnoreCase(Constants.SERVER_ENQUIRY)) {
                enquiryRequestModel.setId(editEnquiryModel.getId());
            }
        }
        return enquiryRequestModel;
    }


    @Override
    public void onSuccessResponse(int apiId, Object response) {
        if (apiId == ApiIds.ID_ADD_ENQUIRY) {
            dismissProgressBar(getActivity());
            AddEnquiryResponseModel responseModel = (AddEnquiryResponseModel) response;
            if (responseModel != null) {
                if (!responseModel.isError()) {
                    displayToast(responseModel.getMessage());
                    if (!TextUtils.isEmpty(enquiryType)) {
                        if (enquiryType.equalsIgnoreCase(Constants.LOCAL_ENQUIRY)) {
                            if (enquiryTable != null) {
                                enquiryTable.delete(Integer.parseInt(requestModel.getId()));
                            }
                        }
                    }
                    addEnquiryListFragment();
                } else {
                    displayErrorDialog(responseModel.getMessage());
                }
            }
        } else if (apiId == ApiIds.ID_UPLOAD_IMAGE) {
            UploadImageResponseModel responseModel = (UploadImageResponseModel) response;
            if (responseModel != null) {
                if (!responseModel.isError()) {
                    // displayToast(responseModel.getMessage());
                    if (responseModel.getData().getType().equalsIgnoreCase(Constants.APPLICANT_ID_IMAGE_TYPE)) {
                        idServerFileName = responseModel.getData().getName();
                        if (!TextUtils.isEmpty(filePathAadhar)) {
                            uploadImage(filePathAadhar, Constants.AADHAR_IMAGE_TYPE);
                        } else {
                            addEnquiry(idServerFileName, editEnquiryModel.getApplicant_aadhar_image());
                        }
                    } else if (responseModel.getData().getType().equalsIgnoreCase(Constants.AADHAR_IMAGE_TYPE)) {
                        aadharServerFileName = responseModel.getData().getName();
                        if (TextUtils.isEmpty(filePathId)) {
                            addEnquiry(editEnquiryModel.getApplicant_id_image(), aadharServerFileName);
                        } else if (!TextUtils.isEmpty(filePathId) && !TextUtils.isEmpty(filePathAadhar)) {
                            addEnquiry(idServerFileName, aadharServerFileName);
                        }
                    }
                } else {
                    displayErrorDialog(responseModel.getMessage());
                }
            } else {
                dismissProgressBar(getActivity());
                displayErrorDialog(getString(R.string.something_went_wrong));
            }
        }else  if (apiId == ApiIds.ID_USER_AREA_LIST) {
            dismissProgressBar(getActivity());
            UserArea responseModel = (UserArea) response;
            UserAreaList = responseModel.getData();
            if (responseModel != null && responseModel.getData() != null) {
                for (int i = 0; i <responseModel.getData().size() ; i++) {
                    userArealist.add(responseModel.getData().get(i).getArea());
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
