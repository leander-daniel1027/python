package com.solutionavenues.deedee.ui.dashboard.fi;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.helper.ConnectionDetector;
import com.solutionavenues.deedee.R;
import com.solutionavenues.deedee.appBase.AppBaseFragment;
import com.solutionavenues.deedee.database.tables.FiTable;
import com.solutionavenues.deedee.helpers.RegexValidations;
import com.solutionavenues.deedee.imagePicker.FileInformation;
import com.solutionavenues.deedee.imagePicker.ImagePickDialog;
import com.solutionavenues.deedee.interfaces.OnOkClickListener;
import com.solutionavenues.deedee.model.request.AddFiRequestModel;
import com.solutionavenues.deedee.model.request.AddLeadRequestModel;
import com.solutionavenues.deedee.model.response.BaseWebResponseModel;
import com.solutionavenues.deedee.model.response.UploadImageResponseModel;
import com.solutionavenues.deedee.rest.ApiHitListener;
import com.solutionavenues.deedee.rest.ApiIds;
import com.solutionavenues.deedee.rest.BaseArguments;
import com.solutionavenues.deedee.rest.RetrofitUtils;
import com.solutionavenues.deedee.util.Utils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import okhttp3.MultipartBody;

/**
 * Created by Azher on 18/6/18.
 */
public class AddFiFragment extends AppBaseFragment implements ApiHitListener, OnOkClickListener, TextWatcher {
    private static final String TAG = "AddFiFragment";
    private TextView[] tabArr;
    private LinearLayout[] viewArr;
    private TextView tv_loan_personal_info, tv_appraisal, tv_feedback,
            tv_common_compliance;

    private LinearLayout ll_personal_info, ll_appraisal,
            ll_common_compliance, ll_feedback;
    private LinearLayout ll_outstanding_loan_container;

    private HorizontalScrollView hsv_form_header;
    String filePathApplicantSign, filePathGroupPic, filePathOfficerSign;

    public void setAddLeadRequestModel(AddLeadRequestModel addLeadRequestModel) {
        this.addLeadRequestModel = addLeadRequestModel;
    }

    public AddLeadRequestModel addLeadRequestModel;

    /*Personal views*/
    private EditText et_k_number, et_name_of_applicant, et_father_name, et_spouse_name,
            et_residential_address, et_city, et_pincode, et_living_since,
            et_mobile, et_mobile2, et_name_of_landlord, et_landlord_no, et_purpose_of_loan,
            et_loan_amount, et_family_member, et_adult, et_child, et_dependent,
            et_income_applicant, et_applicant_source, et_applicant_income_amount,
            et_income_coapplicant, et_coapplicant_source, et_coapplicant_income_amount,
            et_income_member1, et_member1_source, et_member1_income_amount,
            et_income_member2, et_member2_source, et_member2_income_amount,
            et_school_name, et_school_contact, et_school_address, et_native_address,
            et_village_name, et_state, et_district, et_village_pincode, et_name_of_coapplicant,
            et_relation, et_coapplicant_mobile, et_rent_amount, et_native_living_since, et_native_phone1, et_native_phone2;
    private RadioGroup rg_customer_type, rg_current_house_type, rg_rent_agreement,
            rg_rent_paid_timely;

    /*Appraisal views*/
    private EditText et_monthly_income_a, et_no_of_existing_mfi_b, et_all_loans_outstanding_c,
            et_monthly_loan_repayment_d, et_loan_applied_at_e, et_monthly_loan_repayment_f, et_overdues_with_g,
            et_monthly_household_surplus_h;

    /*Compliance View*/
    private CheckBox cb_compliance_a, cb_compliance_b, cb_compliance_c,
            cb_risk_a, cb_risk_b, cb_risk_c, cb_risk_d, cb_risk_e, cb_risk_f,
            cb_risk_g, cb_risk_h;

    /*Feedback Views*/
    private EditText et_member_name, et_bill_name, et_relationship, et_feedback, et_address, et_member_name2,
            et_feedback2, et_address2, et_comment, et_field_officer_name;
    private RadioGroup rg_remark, rg_cheque, rg_remark2;
    private CheckBox cb_declaration;
    private ImageView iv_applicant_signature, iv_group_photo, iv_officer_signature;
    AddFiRequestModel personalBean;
    private ArrayList<String> allFiImages = new ArrayList<>();
    int user_id;
    FiTable fiTable;
    private int local_id = -1;
    private String server_fi_id;
    ArrayList<String> yearList;
    public AddFiRequestModel fiDataForEdit;
    private Dialog loadViewDialog;

    public void setLoadViewDialog(Dialog loadViewDialog) {
        this.loadViewDialog = loadViewDialog;
        alertDialogProgressBar = loadViewDialog;
    }

    public void setFiDataForEdit(AddFiRequestModel dataBean) {
        fiDataForEdit = dataBean;
    }


    @Override
    public int getLayoutResourceId() {
        return R.layout.fragment_fi_form;
    }

    @Override
    public void initializeComponent() {
        yearList = Utils.getLastYearList(50);
        fiTable = new FiTable();
        user_id = getMyApplication().getUserPrefs().getLoggedInUser().getId();
        hsv_form_header = getView().findViewById(R.id.hsv_form_header);
        /*Header Tab Views*/
        tv_loan_personal_info = getView().findViewById(R.id.tv_loan_personal_info);
        tv_appraisal = getView().findViewById(R.id.tv_appraisal);
        tv_feedback = getView().findViewById(R.id.tv_feedback);
        tv_common_compliance = getView().findViewById(R.id.tv_common_compliance);

        /*Header Views*/
        ll_personal_info = getView().findViewById(R.id.ll_personal_info);
        ll_appraisal = getView().findViewById(R.id.ll_appraisal);
        ll_feedback = getView().findViewById(R.id.ll_feedback);
        ll_common_compliance = getView().findViewById(R.id.ll_common_compliance);

        /*click listeners to views*/
        tv_loan_personal_info.setOnClickListener(this);
        tv_appraisal.setOnClickListener(this);
        tv_feedback.setOnClickListener(this);
        tv_common_compliance.setOnClickListener(this);


        tabArr = new TextView[]{tv_loan_personal_info, tv_appraisal,
                tv_common_compliance, tv_feedback};
        viewArr = new LinearLayout[]{ll_personal_info, ll_appraisal,
                ll_common_compliance, ll_feedback};
        setTabVisibility(tv_loan_personal_info, ll_personal_info);
        initPersonalViews();
        initAppraisalAndOutstandingViews();
        initComplianceViews();
        initFeedbackViews();
        if (loadViewDialog != null && loadViewDialog.isShowing()) {
            loadViewDialog.dismiss();
        }
        setLeadDataToFi();

        if (fiDataForEdit != null) {
            if (fiDataForEdit.isLocalFi() && !TextUtils.isEmpty(fiDataForEdit.getLocal_id()) &&
                    !fiDataForEdit.getLocal_id().equalsIgnoreCase("null")) {
                local_id = Integer.parseInt(fiDataForEdit.getLocal_id());
                Log.e(TAG, "local_lead: " + local_id);
            } else {
                server_fi_id = fiDataForEdit.getId();
            }
            setDataHandler.post(setDataRunnable);
        }
    }

    Handler setDataHandler = new Handler();

    Runnable setDataRunnable = new Runnable() {
        @Override
        public void run() {
            setAllFiData(fiDataForEdit);
        }
    };

    private void setAllFiData(AddFiRequestModel fiDataForEdit) {
        setPersonalData(fiDataForEdit);
        setAppraisalData(fiDataForEdit);
        setOutstandingLoanData(fiDataForEdit);
        setComplianceData(fiDataForEdit);
        setRiskData(fiDataForEdit);
        setFeedbackData(fiDataForEdit);

    }

    private void setLeadDataToFi() {
        if (addLeadRequestModel != null) {
            et_name_of_applicant.setText(addLeadRequestModel.getLoanPersonelInfo().getName());
            et_k_number.setText(addLeadRequestModel.getLoanPersonelInfo().getK_number());
            et_mobile.setText(addLeadRequestModel.getAddressInfo().getMobile_contact());
            et_residential_address.setText(addLeadRequestModel.getAddressInfo().getAddress());
            et_loan_applied_at_e.setText(addLeadRequestModel.getLoanPersonelInfo().getLoan_amount());
        }
    }

    private void initPersonalViews() {
        et_name_of_applicant = getView().findViewById(R.id.et_name_of_applicant);
        et_k_number = getView().findViewById(R.id.et_k_number);
        et_father_name = getView().findViewById(R.id.et_father_name);
        et_spouse_name = getView().findViewById(R.id.et_spouse_name);
        et_residential_address = getView().findViewById(R.id.et_residential_address);
        et_city = getView().findViewById(R.id.et_city);
        et_pincode = getView().findViewById(R.id.et_pincode);
        et_living_since = getView().findViewById(R.id.et_living_since);
        et_mobile = getView().findViewById(R.id.et_mobile);
        et_mobile2 = getView().findViewById(R.id.et_mobile2);
        et_name_of_landlord = getView().findViewById(R.id.et_name_of_landlord);
        et_landlord_no = getView().findViewById(R.id.et_landlord_no);
        rg_customer_type = getView().findViewById(R.id.rg_customer_type);
        rg_current_house_type = getView().findViewById(R.id.rg_current_house_type);

        rg_current_house_type.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                View radioButton = radioGroup.findViewById(i);
                int index = radioGroup.indexOfChild(radioButton);
                int checkedRadioButtonId = rg_current_house_type.getCheckedRadioButtonId();
                if (checkedRadioButtonId == R.id.rb_owned) {
                    // No item selected
                    getView().findViewById(R.id.layout_landload).setVisibility(View.GONE);
                    getView().findViewById(R.id.layout_rent_amount).setVisibility(View.GONE);
                    getView().findViewById(R.id.layout_rant_agreement).setVisibility(View.GONE);
                    getView().findViewById(R.id.layout_isrant_paidtimely).setVisibility(View.GONE);

                } else {
                    if (checkedRadioButtonId == R.id.rb_rented) {
                        getView().findViewById(R.id.layout_landload).setVisibility(View.VISIBLE);
                        getView().findViewById(R.id.layout_rent_amount).setVisibility(View.VISIBLE);
                        getView().findViewById(R.id.layout_rant_agreement).setVisibility(View.VISIBLE);
                        getView().findViewById(R.id.layout_isrant_paidtimely).setVisibility(View.VISIBLE);

                    }
                }
            }
        });
        rg_rent_agreement = getView().findViewById(R.id.rg_rent_agreement);
        rg_rent_paid_timely = getView().findViewById(R.id.rg_rent_paid_timely);
        et_purpose_of_loan = getView().findViewById(R.id.et_purpose_of_loan);
        et_loan_amount = getView().findViewById(R.id.et_loan_amount);
        et_family_member = getView().findViewById(R.id.et_family_member);
        et_adult = getView().findViewById(R.id.et_adult);
        et_child = getView().findViewById(R.id.et_child);
        et_dependent = getView().findViewById(R.id.et_dependent);
        et_income_applicant = getView().findViewById(R.id.et_income_applicant);
        et_applicant_source = getView().findViewById(R.id.et_applicant_source);
        et_applicant_income_amount = getView().findViewById(R.id.et_applicant_income_amount);
        et_coapplicant_income_amount = getView().findViewById(R.id.et_coapplicant_income_amount);
        et_income_coapplicant = getView().findViewById(R.id.et_income_coapplicant);
        et_coapplicant_source = getView().findViewById(R.id.et_coapplicant_source);
        et_income_member1 = getView().findViewById(R.id.et_income_member1);
        et_member1_source = getView().findViewById(R.id.et_member1_source);
        et_member1_income_amount = getView().findViewById(R.id.et_member1_income_amount);
        et_income_member2 = getView().findViewById(R.id.et_income_member2);
        et_member2_source = getView().findViewById(R.id.et_member2_source);
        et_member2_income_amount = getView().findViewById(R.id.et_member2_income_amount);
        et_school_name = getView().findViewById(R.id.et_school_name);
        et_school_contact = getView().findViewById(R.id.et_school_contact);
        et_school_address = getView().findViewById(R.id.et_school_address);
        et_native_address = getView().findViewById(R.id.et_native_address);
        et_village_name = getView().findViewById(R.id.et_village_name);
        et_state = getView().findViewById(R.id.et_state);
        et_district = getView().findViewById(R.id.et_district);
        et_village_pincode = getView().findViewById(R.id.et_village_pincode);
        et_name_of_coapplicant = getView().findViewById(R.id.et_name_of_coapplicant);
        et_relation = getView().findViewById(R.id.et_relation);
        et_coapplicant_mobile = getView().findViewById(R.id.et_coapplicant_mobile);
        et_rent_amount = getView().findViewById(R.id.et_rent_amount);
        et_native_living_since = getView().findViewById(R.id.et_native_living_since);
        et_native_phone1 = getView().findViewById(R.id.et_native_phone1);
        et_native_phone2 = getView().findViewById(R.id.et_native_phone2);
        et_living_since.setOnClickListener(this);
        et_native_living_since.setOnClickListener(this);
        et_applicant_income_amount.addTextChangedListener(this);
        et_coapplicant_income_amount.addTextChangedListener(this);
        et_member1_income_amount.addTextChangedListener(this);
        et_member2_income_amount.addTextChangedListener(this);
    }

    private void initAppraisalAndOutstandingViews() {
        ll_outstanding_loan_container = getView().findViewById(R.id.ll_outstanding_loan_container);
        et_monthly_income_a = getView().findViewById(R.id.et_monthly_income_a);
        et_no_of_existing_mfi_b = getView().findViewById(R.id.et_no_of_existing_mfi_b);
        et_all_loans_outstanding_c = getView().findViewById(R.id.et_all_loans_outstanding_c);
        et_monthly_loan_repayment_d = getView().findViewById(R.id.et_monthly_loan_repayment_d);
        et_loan_applied_at_e = getView().findViewById(R.id.et_loan_applied_at_e);
        et_monthly_loan_repayment_f = getView().findViewById(R.id.et_monthly_loan_repayment_f);
        et_overdues_with_g = getView().findViewById(R.id.et_overdues_with_g);
        et_monthly_household_surplus_h = getView().findViewById(R.id.et_monthly_household_surplus_h);
        addOutstandingLoanView();
    }

    private void initComplianceViews() {
        cb_compliance_a = getView().findViewById(R.id.cb_compliance_a);
        cb_compliance_b = getView().findViewById(R.id.cb_compliance_b);
        cb_compliance_c = getView().findViewById(R.id.cb_compliance_c);
        cb_risk_a = getView().findViewById(R.id.cb_risk_a);
        cb_risk_b = getView().findViewById(R.id.cb_risk_b);
        cb_risk_c = getView().findViewById(R.id.cb_risk_c);
        cb_risk_d = getView().findViewById(R.id.cb_risk_d);
        cb_risk_e = getView().findViewById(R.id.cb_risk_e);
        cb_risk_f = getView().findViewById(R.id.cb_risk_f);
        cb_risk_g = getView().findViewById(R.id.cb_risk_g);
        cb_risk_h = getView().findViewById(R.id.cb_risk_h);

    }

    private void initFeedbackViews() {
        et_member_name = getView().findViewById(R.id.et_member_name);
        et_bill_name = getView().findViewById(R.id.et_bill_name);
        et_relationship = getView().findViewById(R.id.et_relationship);
        et_feedback = getView().findViewById(R.id.et_feedback);
        et_address = getView().findViewById(R.id.et_address);
        et_member_name2 = getView().findViewById(R.id.et_member_name2);
        et_feedback2 = getView().findViewById(R.id.et_feedback2);
        et_address2 = getView().findViewById(R.id.et_address2);
        cb_declaration = getView().findViewById(R.id.cb_declaration);
        rg_remark = getView().findViewById(R.id.rg_remark);
        rg_cheque = getView().findViewById(R.id.rg_cheque);
        rg_remark2 = getView().findViewById(R.id.rg_remark2);
        et_comment = getView().findViewById(R.id.et_comment);
        iv_applicant_signature = getView().findViewById(R.id.iv_applicant_signature);
        iv_group_photo = getView().findViewById(R.id.iv_group_photo);
        iv_officer_signature = getView().findViewById(R.id.iv_officer_signature);
        et_field_officer_name = getView().findViewById(R.id.et_field_officer_name);

        iv_applicant_signature.setOnClickListener(this);
        iv_group_photo.setOnClickListener(this);
        iv_officer_signature.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_loan_personal_info:
                manageSaveAndSubmitButton(false, tv_loan_personal_info, ll_personal_info);
                break;
            case R.id.tv_appraisal:
                if (validationPersonalInfo())
                    manageSaveAndSubmitButton(false, tv_appraisal, ll_appraisal);
                break;
            case R.id.tv_feedback:
                if (validationPersonalInfo())
                    manageSaveAndSubmitButton(false, tv_feedback, ll_feedback);
                break;
            case R.id.tv_common_compliance:
                if (validationPersonalInfo())
                    manageSaveAndSubmitButton(false, tv_common_compliance, ll_common_compliance);
                break;
            case R.id.iv_applicant_signature:
                openPicker(iv_applicant_signature);
                break;
            case R.id.iv_group_photo:
                openPicker(iv_group_photo);
                break;
            case R.id.iv_officer_signature:
                openPicker(iv_officer_signature);
                break;
            case R.id.et_living_since:
                openLivingSinceDialog(v);
                break;
            case R.id.et_native_living_since:
                openLivingSinceDialog(v);
                break;

        }
    }


    private boolean validationPersonalInfo() {
        boolean status = true;
        if (!RegexValidations.hasText(et_name_of_applicant, getActivity(), getString(R.string.please_enter_applicant_name))) {
            status = false;
        } else if (!RegexValidations.hasText(et_spouse_name, getActivity(), getString(R.string.please_enter_applicant_spouse_name))) {
            status = false;
        } else if (!RegexValidations.hasText(et_father_name, getActivity(), getString(R.string.please_enter_applicant_father_name))) {
            status = false;
        } else if (!RegexValidations.hasText(et_residential_address, getActivity(), getString(R.string.please_enter_applicant_address))) {
            status = false;
        } else if (!RegexValidations.hasText(et_city, getActivity(), getString(R.string.please_enter_city))) {
            status = false;
        } else if (!RegexValidations.hasText(et_pincode, getActivity(), getString(R.string.please_enter_pincode))) {
            status = false;
        } else if (!RegexValidations.hasText(et_living_since, getActivity(), getString(R.string.please_select_living_since))) {
            status = false;
        } else if (!RegexValidations.hasText(et_mobile, getActivity(), getString(R.string.please_enter_applicant_mobile_1))) {
            status = false;
        } else if (!RegexValidations.hasText(et_mobile2, getActivity(), getString(R.string.please_enter_applicant_mobile_2))) {
            status = false;
        } else if (!RegexValidations.hasText(et_purpose_of_loan, getActivity(), getString(R.string.username_email_required))) {
            status = false;
        } else if (!RegexValidations.hasText(et_family_member, getActivity(), getString(R.string.please_enter_no_of_family_members))) {
            status = false;
        } else if (!RegexValidations.hasText(et_adult, getActivity(), getString(R.string.please_enter_audit))) {
            status = false;
        } else if (!RegexValidations.hasText(et_name_of_coapplicant, getActivity(), getString(R.string.please_enter_coapplicant_name))) {
            status = false;
        } else if (!RegexValidations.hasText(et_income_applicant, getActivity(), getString(R.string.please_enter_applicant_name))) {
            status = false;
        } else if (!RegexValidations.hasText(et_applicant_source, getActivity(), getString(R.string.please_enter_applicant_source_of_income))) {
            status = false;
        } else if (!RegexValidations.hasText(et_applicant_income_amount, getActivity(), getString(R.string.please_enter_applicant_amount))) {
            status = false;
        } else if (!RegexValidations.hasText(et_income_coapplicant, getActivity(), getString(R.string.please_enter_coapplicant_name))) {
            status = false;
        } else if (!RegexValidations.hasText(et_coapplicant_source, getActivity(), getString(R.string.please_enter_coapplicant_source_of_income))) {
            status = false;
        } else if (!RegexValidations.hasText(et_coapplicant_income_amount, getActivity(), getString(R.string.please_enter_coapplicant_source_of_income))) {
            status = false;
        }
        if (!status) {
            Utils.showToast(getContext(), "Please Add Required Fields.");
        }
        return status;
    }

    View currentClickedView;

    private void openLivingSinceDialog(View view) {
        currentClickedView = view;
        Utils.hideKeyboard(getActivity());
        Utils.showAlertDialog(getActivity(),
                getString(R.string.living_since),
                yearList.toArray(new String[yearList.size()]),
                onClickedLivingListner);
    }


    DialogInterface.OnClickListener onClickedLivingListner = new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int item) {
            if (currentClickedView.getId() == et_living_since.getId()) {
                et_living_since.setText("" + yearList.toArray(new String[yearList.size()])[item]);
            } else if (currentClickedView.getId() == et_native_living_since.getId()) {
                et_native_living_since.setText("" + yearList.toArray(new String[yearList.size()])[item]);
            }
        }
    };


    private void manageSaveAndSubmitButton(boolean navigateNext, TextView visibleTv, LinearLayout visibleLl) {
        int currentVisibleLayoutPosition = getVisibleLayout();
        if (local_id == -1) {
            insertDataInFiTable();
        } else {
            updateDataInFiTable(currentVisibleLayoutPosition);
        }
        if (navigateNext) {
            if (currentVisibleLayoutPosition != tabArr.length - 1) {
                hsv_form_header.scrollBy(120, 0);
                setTabVisibility(tabArr[currentVisibleLayoutPosition + 1], viewArr[currentVisibleLayoutPosition + 1]);
            }
        } else {
            setTabVisibility(visibleTv, visibleLl);
        }
    }

    private int getVisibleLayout() {
        int pos = 0;
        for (int i = 0; i < viewArr.length; i++) {
            if (viewArr[i].getVisibility() == View.VISIBLE) {
                pos = i;
                return pos;
            }
        }
        return pos;
    }

    public void setTabVisibility(TextView textView, LinearLayout view) {
        textView.setTextColor(getResources().getColor(R.color.colorAccent));
        textView.setBackgroundColor(getResources().getColor(R.color.colorWhite));
        view.setVisibility(View.VISIBLE);
        for (int i = 0; i < tabArr.length; i++) {
            if (textView.getId() != tabArr[i].getId()) {
                tabArr[i].setTextColor(getResources().getColor(R.color.colorWhite));
                tabArr[i].setBackgroundColor(getResources().getColor(R.color.semi_black));
            } else {
                try {
                    if (i == tabArr.length - 1) {
                        getDashBoardActivity().getToolBarHelper().tv_save_continue.setText(getString(R.string.submit));
                    } else {
                        getDashBoardActivity().getToolBarHelper().tv_save_continue.setText(getString(R.string.save_and_continue));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (view.getId() != viewArr[i].getId()) {
                viewArr[i].setVisibility(View.GONE);
            }
        }
    }

    private void updateDataInFiTable(int visibleLayoutPosition) {
        // TextView visibleTab = tabArr[visibleLayoutPosition];
        String columnToUpdate = "";
        String dataToUpdate = "";
        AddFiRequestModel fiModel = getAllFiData();
        columnToUpdate = fiTable.FI_DATA;
        dataToUpdate = fiTable.getStringJson(fiModel);

      /*  if (visibleTab.getText().toString().equalsIgnoreCase(getString(R.string.personal_info))) {
            personalBean=getPersonalData();
            columnToUpdate = fiTable.FI_DATA;
            dataToUpdate = fiTable.getStringJson(personalBean);
        }else if (visibleTab.getText().toString().equalsIgnoreCase(getString(R.string.appraisal))) {
            personalBean.setFie_appraisal(getAppraisalData());
            personalBean.setFie_loan_details(getOutstandingLoanData());
            columnToUpdate = fiTable.FI_DATA;
            dataToUpdate = fiTable.getStringJson(personalBean);
        }else if (visibleTab.getText().toString().equalsIgnoreCase(getString(R.string.common_compliance_and_risk))) {
            personalBean.setFie_compliance(getComplianceData());
            personalBean.setFie_risk_evaluation(getRiskData());
            columnToUpdate = fiTable.FI_DATA;
            dataToUpdate = fiTable.getStringJson(personalBean);
        }else if (visibleTab.getText().toString().equalsIgnoreCase(getString(R.string.comments_and_feedback))) {
            personalBean.setFie_feedback(getFeedbackData());
            columnToUpdate = fiTable.FI_DATA;
            dataToUpdate = fiTable.getStringJson(personalBean);
        }*/
        int updateId = fiTable.updateFiColumn(local_id, columnToUpdate, dataToUpdate);
        Log.e(TAG, "updateDataInFiTable: " + local_id + " update_id: " + updateId);
    }


    private void insertDataInFiTable() {
        local_id = (int) fiTable.insert(getAllFiData());
        Log.e(TAG, "fi_insert_id: " + local_id);
        if (local_id != -1) {
            // displayErrorDialog(getString(R.string.you_are_offline));
            // Log.e(TAG, "lead_data_size: " + leadTable.getAllLeads().size());
        }
    }


    public void saveData() {
        try {
            if (getDashBoardActivity().getToolBarHelper().tv_save_continue.getText().toString().equalsIgnoreCase(getString(R.string.submit))) {

                if (iv_applicant_signature.getTag() == null) {
                    Utils.showToast(getContext(), getString(R.string.upload_group_photo));
                    return;
                } else if (!cb_declaration.isChecked()) {
                    Utils.displayErrorDialog(getString(R.string.check_declaration_box), getActivity());
                    return;
                }
                AddFiRequestModel fiModel = getAllFiData();
                if (fiDataForEdit != null && !fiDataForEdit.isLocalFi()) {
                    fiModel.setId(fiDataForEdit.getId());
                    editFi(fiModel);
                } else {
                    addFi(fiModel);
                }


            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (validationPersonalInfo())
            manageSaveAndSubmitButton(true, null, null);
    }

    private AddFiRequestModel getAllFiData() {
        personalBean = getPersonalData();
        personalBean.setFie_appraisal(getAppraisalData());
        personalBean.setFie_loan_details(getOutstandingLoanData());
        personalBean.setFie_compliance(getComplianceData());
        personalBean.setFie_risk_evaluation(getRiskData());
        personalBean.setFie_feedback(getFeedbackData());
        return personalBean;
    }


    @Override
    public boolean handleOnBackPress() {
        if (dialog == null || !dialog.isShowing()) {
            if (mResponseDialog != null && mResponseDialog.isShowing()) {
                mResponseDialog.dismiss();
                return false;
            }
            showBackDialog();
            return true;
        } else {
            dialog.dismiss();
            return false;
        }
    }

    private void showBackDialog() {
        try {
            if (dialog != null && dialog.isShowing())
                return;
            dialog = new Dialog(getActivity());
            dialog.requestWindowFeature(android.view.Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.custom_alert_dialog);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            dialog.getWindow().setLayout((int) (getResources().getDisplayMetrics().widthPixels * 0.80), WindowManager.LayoutParams.WRAP_CONTENT);
            TextView tv_cancel = dialog.findViewById(R.id.tv_cancel);
            TextView tv_yes = dialog.findViewById(R.id.tv_yes);
            TextView tv_no = dialog.findViewById(R.id.tv_no);
            tv_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    dialog.dismiss();
                }
            });
            tv_yes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    if (fiTable.isValueExist(fiTable.LOCAL_ID, local_id)) {
                        fiTable.updateFiRecord(local_id, getAllFiData());
                    }
                    getActivity().onBackPressed();
                }
            });
            tv_no.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    fiTable.delete(local_id);
                    getActivity().onBackPressed();
                }
            });

            dialog.setCancelable(false);
            dialog.show();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /*add dynamic outstanding loan view*/
    private void addOutstandingLoanView() {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View view = inflater.inflate(R.layout.layout_outstanding_loan_view, null);
        ImageView iv_add_view = view.findViewById(R.id.iv_add_view);
        ImageView iv_remove_view = view.findViewById(R.id.iv_remove_view);
        iv_remove_view.setTag(ll_outstanding_loan_container.getChildCount());
        ll_outstanding_loan_container.addView(view);
        iv_remove_view.setOnClickListener(onOutstandingLoanDeleteListener);
        iv_add_view.setOnClickListener(onOutstandingLoanAddListener);


    }

    View.OnClickListener onOutstandingLoanAddListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            addOutstandingLoanView();
            onRefreshExistingLoan();
        }
    };

    View.OnClickListener onOutstandingLoanDeleteListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ll_outstanding_loan_container.removeViewAt(Integer.parseInt(v.getTag().toString()));
            onRefreshExistingLoan();
        }
    };

    private void onRefreshExistingLoan() {
        for (int i = 0; i < ll_outstanding_loan_container.getChildCount(); i++) {
            View view = ll_outstanding_loan_container.getChildAt(i);
            ImageView iv_remove_view = view.findViewById(R.id.iv_remove_view);
            ImageView iv_add_view = view.findViewById(R.id.iv_add_view);
            iv_remove_view.setTag(i);
            iv_remove_view.setVisibility(View.VISIBLE);
            if (i == 0) {
                iv_remove_view.setVisibility(View.GONE);
            }
            if (i == ll_outstanding_loan_container.getChildCount() - 1) {
                iv_add_view.setVisibility(View.VISIBLE);
            } else {
                iv_add_view.setVisibility(View.GONE);
            }
        }
    }


    private void setPersonalData(AddFiRequestModel fiRequestModel) {
        et_name_of_applicant.setText(fiRequestModel.getName_of_applicant());
        et_k_number.setText(fiRequestModel.getK_number());
        et_father_name.setText(fiRequestModel.getName_of_father());
        et_spouse_name.setText(fiRequestModel.getName_of_husband());
        et_residential_address.setText(fiRequestModel.getResidential_address());
        et_city.setText(fiRequestModel.getName_of_city());
        et_pincode.setText(fiRequestModel.getPincode());
        et_living_since.setText(fiRequestModel.getLiving_since());
        et_mobile.setText(fiRequestModel.getMobile());
        if (personalBean != null)
            et_mobile2.setText(personalBean.getLiving_phone_2());
        et_landlord_no.setText(fiRequestModel.getLandlord_mobile());
        et_purpose_of_loan.setText(fiRequestModel.getPurpose_of_loan());
        et_loan_amount.setText(fiRequestModel.getLoan_amount());
        et_family_member.setText(fiRequestModel.getNumber_of_member());
        et_name_of_landlord.setText(fiRequestModel.getName_of_landlord());
        et_adult.setText(fiRequestModel.getAdult());
        et_child.setText(fiRequestModel.getChild());
        et_dependent.setText(fiRequestModel.getDependent());
        et_income_applicant.setText(fiRequestModel.getIncome_applicant());
        et_applicant_source.setText(fiRequestModel.getApplicant_income_source());
        et_applicant_income_amount.setText(fiRequestModel.getApplicant_income_amount());
        et_income_coapplicant.setText(fiRequestModel.getIncome_coapplicant());
        et_coapplicant_source.setText(fiRequestModel.getCoapplicant_income_source());
        et_coapplicant_income_amount.setText(fiRequestModel.getCoapplicant_income_amount());
        et_income_member1.setText(fiRequestModel.getIncome_member1());
        et_member1_source.setText(fiRequestModel.getMember1_income_source());
        et_member1_income_amount.setText(fiRequestModel.getMember1_income_amount());
        et_income_member1.setText(fiRequestModel.getIncome_member2());
        et_member2_source.setText(fiRequestModel.getMember2_income_source());
        et_member2_income_amount.setText(fiRequestModel.getMember2_income_amount());
        et_school_name.setText(fiRequestModel.getChild_school_name());
        et_school_contact.setText(fiRequestModel.getChild_school_phone());
        et_school_address.setText(fiRequestModel.getChild_school_address());
        et_native_address.setText(fiRequestModel.getNative_address());
        et_village_name.setText(fiRequestModel.getNative_village());
        et_state.setText(fiRequestModel.getNative_state());
        et_district.setText(fiRequestModel.getNative_district());
        et_village_pincode.setText(fiRequestModel.getNative_pincode());
        et_name_of_coapplicant.setText(fiRequestModel.getName_of_co_applicant());
        et_relation.setText(fiRequestModel.getCo_applicant_relation());
        et_coapplicant_mobile.setText(fiRequestModel.getCo_applicant_mobile());
        et_rent_amount.setText(fiRequestModel.getRent_amount());
        et_native_living_since.setText(fiRequestModel.getNative_living_since());
        et_native_phone1.setText(fiRequestModel.getNative_phone1());
        et_native_phone2.setText(fiRequestModel.getNative_phone2());
        setCheckedRadioButton(rg_customer_type, fiRequestModel.getCustomer_type());
        setCheckedRadioButton(rg_current_house_type, fiRequestModel.getHouse_type());
        setCheckedRadioButton(rg_rent_agreement, fiRequestModel.getRent_agreement_available());
        setCheckedRadioButton(rg_rent_paid_timely, fiRequestModel.getIs_rent_paid_timely());


    }

    private void setAppraisalData(AddFiRequestModel fiRequestModel) {
        et_monthly_income_a.setText(fiRequestModel.getFie_appraisal().getAppraisal_a());
        et_no_of_existing_mfi_b.setText(fiRequestModel.getFie_appraisal().getAppraisal_b());
        et_all_loans_outstanding_c.setText(fiRequestModel.getFie_appraisal().getAppraisal_c());
        et_monthly_loan_repayment_d.setText(fiRequestModel.getFie_appraisal().getAppraisal_d());
        et_loan_applied_at_e.setText(fiRequestModel.getFie_appraisal().getAppraisal_e());
        et_monthly_loan_repayment_f.setText(fiRequestModel.getFie_appraisal().getAppraisal_f());
        et_overdues_with_g.setText(fiRequestModel.getFie_appraisal().getAppraisal_g());
        et_monthly_household_surplus_h.setText(fiRequestModel.getFie_appraisal().getAppraisal_h());
    }


    private void setOutstandingLoanData(AddFiRequestModel fiRequestModel) {

        if (fiRequestModel.getFie_loan_details() != null &&
                fiRequestModel.getFie_loan_details().size() > 0) {
            ll_outstanding_loan_container.removeAllViews();
            for (int i = 0; i < fiRequestModel.getFie_loan_details().size(); i++) {
                View view = LayoutInflater.from(getActivity()).inflate(R.layout.layout_outstanding_loan_view, null);

                EditText et_coapplicant_name = view.findViewById(R.id.et_coapplicant_name);
                EditText et_loan_amount = view.findViewById(R.id.et_loan_amount);
                EditText et_tenure = view.findViewById(R.id.et_tenure);
                EditText et_emi_amount = view.findViewById(R.id.et_emi_amount);
                EditText et_installment_due = view.findViewById(R.id.et_installment_due);
                ImageView iv_remove_view = view.findViewById(R.id.iv_remove_view);
                ImageView iv_add_view = view.findViewById(R.id.iv_add_view);
                iv_remove_view.setTag(i);
                iv_add_view.setTag(i);
                iv_remove_view.setOnClickListener(onOutstandingLoanDeleteListener);
                iv_add_view.setOnClickListener(onOutstandingLoanAddListener);
                if (i > 0) {
                    iv_remove_view.setVisibility(View.VISIBLE);
                } else {
                    iv_remove_view.setVisibility(View.GONE);
                }
                if (i == fiRequestModel.getFie_loan_details().size() - 1) {
                    iv_add_view.setVisibility(View.VISIBLE);
                } else {
                    iv_add_view.setVisibility(View.GONE);
                }
                AddFiRequestModel.FieLoanDetailsBean fieLoanDetailsBean = fiRequestModel.getFie_loan_details().get(i);
                et_coapplicant_name.setText(fieLoanDetailsBean.getCo_name());
                et_loan_amount.setText(fieLoanDetailsBean.getLoan_amount());
                et_tenure.setText(fieLoanDetailsBean.getTenure());
                et_emi_amount.setText(fieLoanDetailsBean.getEmi_amount());
                et_installment_due.setText(fieLoanDetailsBean.getInstallment_due());
                ll_outstanding_loan_container.addView(view);
            }
        }
    }

    private void setComplianceData(AddFiRequestModel fiRequestModel) {
        setCheckBox(cb_compliance_a, String.valueOf(fiRequestModel.getFie_compliance().getCompliance_a()));
        setCheckBox(cb_compliance_b, String.valueOf(fiRequestModel.getFie_compliance().getCompliance_b()));
        setCheckBox(cb_compliance_c, String.valueOf(fiRequestModel.getFie_compliance().getCompliance_c()));

    }

    private void setRiskData(AddFiRequestModel fiRequestModel) {
        setCheckBox(cb_risk_a, String.valueOf(fiRequestModel.getFie_risk_evaluation().getRisk_a()));
        setCheckBox(cb_risk_b, String.valueOf(fiRequestModel.getFie_risk_evaluation().getRisk_b()));
        setCheckBox(cb_risk_c, String.valueOf(fiRequestModel.getFie_risk_evaluation().getRisk_c()));
        setCheckBox(cb_risk_d, String.valueOf(fiRequestModel.getFie_risk_evaluation().getRisk_d()));
        setCheckBox(cb_risk_e, String.valueOf(fiRequestModel.getFie_risk_evaluation().getRisk_e()));
        setCheckBox(cb_risk_f, String.valueOf(fiRequestModel.getFie_risk_evaluation().getRisk_f()));
        setCheckBox(cb_risk_g, String.valueOf(fiRequestModel.getFie_risk_evaluation().getRisk_g()));
        setCheckBox(cb_risk_h, String.valueOf(fiRequestModel.getFie_risk_evaluation().getRisk_h()));
    }

    private void setFeedbackData(AddFiRequestModel fiRequestModel) {
        et_member_name.setText(fiRequestModel.getFie_feedback().getName1());
        et_bill_name.setText(fiRequestModel.getFie_feedback().getUtility_bill());
        et_relationship.setText(fiRequestModel.getFie_feedback().getUtility_relation());
        et_feedback.setText(fiRequestModel.getFie_feedback().getFeedback1());
        et_address.setText(fiRequestModel.getFie_feedback().getAddress1());
        et_member_name2.setText(fiRequestModel.getFie_feedback().getName2());
        et_feedback2.setText(fiRequestModel.getFie_feedback().getFeedback2());
        et_address2.setText(fiRequestModel.getFie_feedback().getAddress2());
        et_comment.setText(fiRequestModel.getFie_feedback().getComments());
        et_field_officer_name.setText(fiRequestModel.getFie_feedback().getName_officer());
        setCheckedRadioButton(rg_remark, fiRequestModel.getFie_feedback().getRemark1());
        setCheckedRadioButton(rg_remark2, fiRequestModel.getFie_feedback().getRemark2());
        setCheckedRadioButton(rg_cheque, fiRequestModel.getFie_feedback().getCheque());
        if (!TextUtils.isEmpty(fiRequestModel.getFie_feedback().getApplicant_signature())) {
            filePathApplicantSign = fiRequestModel.getFie_feedback().getApplicant_signature();
            iv_applicant_signature.setTag(filePathApplicantSign);
            Picasso.with(getActivity())
                    .load(getImageActualPath(fiRequestModel.getFie_feedback().getApplicant_signature()))
                    .placeholder(R.mipmap.loading_default)
                    .error(R.mipmap.error_default)
                    .into(iv_applicant_signature);

        } else {
            iv_applicant_signature.setImageResource(R.mipmap.default_image);
        }

        if (!TextUtils.isEmpty(fiRequestModel.getFie_feedback().getSignature())) {
            filePathOfficerSign = fiRequestModel.getFie_feedback().getSignature();
            Picasso.with(getActivity())
                    .load(getImageActualPath(fiRequestModel.getFie_feedback().getSignature()))
                    .placeholder(R.mipmap.loading_default)
                    .error(R.mipmap.error_default)
                    .into(iv_officer_signature);

        } else {
            iv_officer_signature.setImageResource(R.mipmap.default_image);
        }


        if (!TextUtils.isEmpty(fiRequestModel.getFie_feedback().getGroup_pic())) {
            filePathGroupPic = fiRequestModel.getFie_feedback().getGroup_pic();
            Picasso.with(getActivity())
                    .load(getImageActualPath(fiRequestModel.getFie_feedback().getGroup_pic()))
                    .placeholder(R.mipmap.loading_default)
                    .error(R.mipmap.error_default)
                    .into(iv_group_photo);

        } else {
            iv_group_photo.setImageResource(R.mipmap.default_image);
        }

    }


    private AddFiRequestModel getPersonalData() {
        personalBean = new AddFiRequestModel();
        if (addLeadRequestModel != null) {
            personalBean.setLead_id(addLeadRequestModel.getLead_id());
        }
        personalBean.setUser_id(user_id);
        personalBean.setDate(getCurrentDate());
        personalBean.setName_of_applicant(et_name_of_applicant.getText().toString());
        personalBean.setK_number(et_k_number.getText().toString());
        personalBean.setCustomer_type(getRadioButtonValue(rg_customer_type));
        personalBean.setName_of_father(et_father_name.getText().toString());
        personalBean.setName_of_husband(et_spouse_name.getText().toString());
        personalBean.setResidential_address(et_residential_address.getText().toString());
        personalBean.setName_of_city(et_city.getText().toString());
        personalBean.setPincode(et_pincode.getText().toString());
        personalBean.setHouse_type(getRadioButtonValue(rg_current_house_type));
        personalBean.setLiving_since(et_living_since.getText().toString());
        personalBean.setMobile(et_mobile.getText().toString());
        personalBean.setLiving_phone_2(et_mobile2.getText().toString());
        personalBean.setName_of_landlord(et_name_of_landlord.getText().toString());
        personalBean.setLandlord_mobile(et_landlord_no.getText().toString());
        personalBean.setRent_agreement_available(getRadioButtonValue(rg_rent_agreement));
        personalBean.setIs_rent_paid_timely(getRadioButtonValue(rg_rent_paid_timely));
        personalBean.setPurpose_of_loan(et_purpose_of_loan.getText().toString());
        personalBean.setLoan_amount(et_loan_amount.getText().toString());
        personalBean.setNumber_of_member(et_family_member.getText().toString());
        personalBean.setAdult(et_adult.getText().toString());
        personalBean.setChild(et_child.getText().toString());
        personalBean.setDependent(et_dependent.getText().toString());
        personalBean.setIncome_applicant(et_income_applicant.getText().toString());
        personalBean.setApplicant_income_source(et_applicant_source.getText().toString());
        personalBean.setApplicant_income_amount(et_applicant_income_amount.getText().toString());
        personalBean.setIncome_coapplicant(et_income_coapplicant.getText().toString());
        personalBean.setCoapplicant_income_source(et_coapplicant_source.getText().toString());
        personalBean.setCoapplicant_income_amount(et_coapplicant_income_amount.getText().toString());
        personalBean.setIncome_member1(et_income_member1.getText().toString());
        personalBean.setMember1_income_source(et_member1_source.getText().toString());
        personalBean.setMember1_income_amount(et_member1_income_amount.getText().toString());
        personalBean.setIncome_member2(et_income_member2.getText().toString());
        personalBean.setMember2_income_source(et_member2_source.getText().toString());
        personalBean.setMember2_income_amount(et_member2_income_amount.getText().toString());
        personalBean.setChild_school_name(et_school_name.getText().toString());
        personalBean.setChild_school_phone(et_school_contact.getText().toString());
        personalBean.setChild_school_address(et_school_address.getText().toString());
        personalBean.setNative_address(et_native_address.getText().toString());
        personalBean.setNative_village(et_village_name.getText().toString());
        personalBean.setNative_state(et_state.getText().toString());
        personalBean.setNative_district(et_district.getText().toString());
        personalBean.setNative_pincode(et_village_pincode.getText().toString());
        personalBean.setName_of_co_applicant(et_name_of_coapplicant.getText().toString());
        personalBean.setCo_applicant_relation(et_relation.getText().toString());
        personalBean.setCo_applicant_mobile(et_coapplicant_mobile.getText().toString());
        personalBean.setRent_amount(et_rent_amount.getText().toString());
        personalBean.setNative_living_since(et_native_living_since.getText().toString());
        personalBean.setNative_phone1(et_native_phone1.getText().toString());
        personalBean.setNative_phone2(et_native_phone2.getText().toString());

        return personalBean;
    }

    private AddFiRequestModel.FieAppraisalBean getAppraisalData() {
        AddFiRequestModel.FieAppraisalBean appraisalBean = new AddFiRequestModel.FieAppraisalBean();
        appraisalBean.setAppraisal_a(et_monthly_income_a.getText().toString());
        appraisalBean.setAppraisal_b(et_no_of_existing_mfi_b.getText().toString());
        appraisalBean.setAppraisal_c(et_all_loans_outstanding_c.getText().toString());
        appraisalBean.setAppraisal_d(et_monthly_loan_repayment_d.getText().toString());
        appraisalBean.setAppraisal_e(et_loan_applied_at_e.getText().toString());
        appraisalBean.setAppraisal_f(et_monthly_loan_repayment_f.getText().toString());
        appraisalBean.setAppraisal_g(et_overdues_with_g.getText().toString());
        appraisalBean.setAppraisal_h(et_monthly_household_surplus_h.getText().toString());
        return appraisalBean;
    }

    private ArrayList<AddFiRequestModel.FieLoanDetailsBean> getOutstandingLoanData() {
        ArrayList<AddFiRequestModel.FieLoanDetailsBean> outstandingLoanInfoList = new ArrayList<>();
        if (ll_outstanding_loan_container.getChildCount() > 0) {
            for (int i = 0; i < ll_outstanding_loan_container.getChildCount(); i++) {
                AddFiRequestModel.FieLoanDetailsBean outstandingLoanInfo = new AddFiRequestModel.FieLoanDetailsBean();
                View view = ll_outstanding_loan_container.getChildAt(i);
                EditText et_coapplicant_name = view.findViewById(R.id.et_coapplicant_name);
                EditText et_loan_amount = view.findViewById(R.id.et_loan_amount);
                EditText et_tenure = view.findViewById(R.id.et_tenure);
                EditText et_emi_amount = view.findViewById(R.id.et_emi_amount);
                EditText et_installment_due = view.findViewById(R.id.et_installment_due);

                String coapplicant_name = et_coapplicant_name.getText().toString();
                String loanAmount = et_loan_amount.getText().toString();
                String tenure = et_tenure.getText().toString();
                String emi = et_emi_amount.getText().toString();
                String installDue = et_installment_due.getText().toString();

                outstandingLoanInfo.setCo_name(coapplicant_name);
                outstandingLoanInfo.setEmi_amount(emi);
                outstandingLoanInfo.setLoan_amount(loanAmount);
                outstandingLoanInfo.setTenure(tenure);
                outstandingLoanInfo.setInstallment_due(installDue);

                outstandingLoanInfoList.add(outstandingLoanInfo);
            }
        }
        return outstandingLoanInfoList;
    }

    private AddFiRequestModel.FieComplianceBean getComplianceData() {
        AddFiRequestModel.FieComplianceBean fieComplianceBean = new AddFiRequestModel.FieComplianceBean();
        fieComplianceBean.setCompliance_a(getCheckBoxValue(cb_compliance_a));
        fieComplianceBean.setCompliance_b(getCheckBoxValue(cb_compliance_b));
        fieComplianceBean.setCompliance_c(getCheckBoxValue(cb_compliance_c));
        return fieComplianceBean;
    }

    private AddFiRequestModel.FieRiskEvaluationBean getRiskData() {
        AddFiRequestModel.FieRiskEvaluationBean riskEvaluationBean = new AddFiRequestModel.FieRiskEvaluationBean();
        riskEvaluationBean.setRisk_a(getCheckBoxValue(cb_risk_a));
        riskEvaluationBean.setRisk_b(getCheckBoxValue(cb_risk_b));
        riskEvaluationBean.setRisk_c(getCheckBoxValue(cb_risk_c));
        riskEvaluationBean.setRisk_d(getCheckBoxValue(cb_risk_d));
        riskEvaluationBean.setRisk_e(getCheckBoxValue(cb_risk_e));
        riskEvaluationBean.setRisk_f(getCheckBoxValue(cb_risk_f));
        riskEvaluationBean.setRisk_g(getCheckBoxValue(cb_risk_g));
        riskEvaluationBean.setRisk_h(getCheckBoxValue(cb_risk_h));
        return riskEvaluationBean;
    }

    private AddFiRequestModel.FieFeedbackBean getFeedbackData() {
        allFiImages.clear();
        AddFiRequestModel.FieFeedbackBean fieFeedbackBean = new AddFiRequestModel.FieFeedbackBean();
        fieFeedbackBean.setName1(et_member_name.getText().toString());
        fieFeedbackBean.setFeedback1(et_feedback.getText().toString());
        fieFeedbackBean.setAddress1(et_address.getText().toString());
        fieFeedbackBean.setRemark1(getRadioButtonValue(rg_remark));
        fieFeedbackBean.setCheque(getRadioButtonValue(rg_cheque));
        fieFeedbackBean.setName2(et_member_name2.getText().toString());
        fieFeedbackBean.setUtility_bill(et_bill_name.getText().toString());
        fieFeedbackBean.setUtility_relation(et_relationship.getText().toString());
        fieFeedbackBean.setFeedback2(et_feedback2.getText().toString());
        fieFeedbackBean.setAddress2(et_address2.getText().toString());
        fieFeedbackBean.setRemark2(getRadioButtonValue(rg_remark2));
        fieFeedbackBean.setComments(et_comment.getText().toString());
        fieFeedbackBean.setName_officer(et_field_officer_name.getText().toString());
        fieFeedbackBean.setApplicant_signature(filePathApplicantSign);
        fieFeedbackBean.setGroup_pic(filePathGroupPic);
        fieFeedbackBean.setSignature(filePathOfficerSign);
        if (!TextUtils.isEmpty(filePathApplicantSign) && filePathApplicantSign.contains("/")) {
            allFiImages.add(filePathApplicantSign);
        }
        if (!TextUtils.isEmpty(filePathGroupPic) && filePathGroupPic.contains("/")) {
            allFiImages.add(filePathGroupPic);
        }
        if (!TextUtils.isEmpty(filePathOfficerSign) && filePathOfficerSign.contains("/")) {
            allFiImages.add(filePathOfficerSign);
        }
        return fieFeedbackBean;
    }

    private void openPicker(final ImageView imageView) {
        ImagePickDialog picDialog = ImagePickDialog.getNewInstance(false);
        picDialog.setProfilePicDialogListner(new ImagePickDialog.ProfilePicDialogListner() {
            @Override
            public void onProfilePicSelected(FileInformation fileInformation) {
                Log.e(TAG, "onProfilePicSelected: " + fileInformation.getFilePath());
                if (imageView.getId() == iv_applicant_signature.getId()) {
                    filePathApplicantSign = fileInformation.getBitmapPathForUpload(getActivity());
                    iv_applicant_signature.setImageBitmap(fileInformation.getThumbBitmap(getActivity()));
                    iv_applicant_signature.setTag(filePathApplicantSign);
                } else if (imageView.getId() == iv_group_photo.getId()) {
                    filePathGroupPic = fileInformation.getBitmapPathForUpload(getActivity());
                    iv_group_photo.setImageBitmap(fileInformation.getThumbBitmap(getActivity()));
                } else if (imageView.getId() == iv_officer_signature.getId()) {
                    filePathOfficerSign = fileInformation.getBitmapPathForUpload(getActivity());
                    iv_officer_signature.setImageBitmap(fileInformation.getThumbBitmap(getActivity()));
                }
            }

            @Override
            public void onProfilePicRemoved() {
                if (imageView.getId() == iv_applicant_signature.getId()) {
                    iv_applicant_signature.setImageResource(R.mipmap.default_image);
                    iv_applicant_signature.setTag(null);
                } else if (imageView.getId() == iv_group_photo.getId()) {
                    iv_group_photo.setImageResource(R.mipmap.default_image);
                } else if (imageView.getId() == iv_officer_signature.getId()) {
                    iv_officer_signature.setImageResource(R.mipmap.default_image);
                }


            }
        });
        picDialog.show(getChildFm(), picDialog.getClass().getSimpleName());
    }



   /* // validation for ALL fields
    private boolean checkValidation() {
        boolean status = true;
        if (!RegexValidations.hasText(et_group_name, getActivity(), getString(R.string.GROUP_NAME_MSG))) {
            return false;
        }
        if (!RegexValidations.hasText(et_center_name, getActivity(), getString(R.string.CENTER_NAME_MSG))) {
            return false;
        }
        return status;
    }*/


    // call to addFi service
    private void addFi(AddFiRequestModel fiModel) {
        if (ConnectionDetector.isNetAvail(getActivity())) {
            displayProgressBar(false, getActivity());
            getRestClient().callback(this).addFi(fiModel);
        } else {
            displayErrorDialog(getString(R.string.error_internet_connection));
        }
    }

    // call to editFi service
    private void editFi(AddFiRequestModel fiModel) {
        if (ConnectionDetector.isNetAvail(getActivity())) {
            displayProgressBar(false, getActivity());
            getRestClient().callback(this).editFi(fiModel);
        } else {
            displayErrorDialog(getString(R.string.error_internet_connection));
        }
    }

    private void uploadImage(String filePath, boolean showProgress) {
        if (ConnectionDetector.isNetAvail(getActivity())) {
            if (showProgress) {
                displayProgressBar(false, getActivity());
            }
            MultipartBody.Part part = RetrofitUtils.createFilePart(BaseArguments.ARG_FILE,
                    filePath, RetrofitUtils.MEDIA_TYPE_IMAGE_PNG);
            getRestClient().callback(this).uploadImage(part);
        }
    }

    @Override
    public void onSuccessResponse(int apiId, Object response) {
        if (apiId == ApiIds.ID_ADD_FI) {
            BaseWebResponseModel responseModel = (BaseWebResponseModel) response;
            if (responseModel != null) {
                if (!responseModel.isError()) {
                    fiTable.delete(local_id);
                    if (allFiImages.size() > 0) {
                        uploadImage(allFiImages.get(0), false);
                    } else {
                        dismissProgressBar(getActivity());
                        displayAlertDialog(getString(R.string.message), getString(R.string.fi_form_successfully_submitted), this);
                    }
                } else {
                    dismissProgressBar(getActivity());
                    displayErrorDialog(responseModel.getMessage());
                }
            } else {
                dismissProgressBar(getActivity());
            }
        } else if (apiId == ApiIds.ID_EDIT_FI) {
            BaseWebResponseModel responseModel = (BaseWebResponseModel) response;
            if (responseModel != null) {
                if (!responseModel.isError()) {
                    fiTable.delete(local_id);
                    if (allFiImages.size() > 0) {
                        uploadImage(allFiImages.get(0), false);
                    } else {
                        dismissProgressBar(getActivity());
                        displayAlertDialog(getString(R.string.message), getString(R.string.fi_form_successfully_submitted), this);
                    }
                } else {
                    dismissProgressBar(getActivity());
                    displayErrorDialog(responseModel.getMessage());
                }
            } else {
                dismissProgressBar(getActivity());
            }
        } else if (apiId == ApiIds.ID_UPLOAD_IMAGE) {
            UploadImageResponseModel responseModel = (UploadImageResponseModel) response;
            if (responseModel != null) {
                if (!responseModel.isError()) {
                    allFiImages.remove(0);
                    if (allFiImages.size() > 0) {
                        uploadImage(allFiImages.get(0), false);
                    } else {
                        dismissProgressBar(getActivity());
                        displayAlertDialog(getString(R.string.message), getString(R.string.fi_form_successfully_submitted), this);
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

    @Override
    public void onDialogOkClick() {
        try {
            getDashBoardActivity().onBackPressed();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        long applicantAmount = checkValidInt(et_applicant_income_amount.getText().toString());
        long coapplicantAmount = checkValidInt(et_coapplicant_income_amount.getText().toString());
        long member1Amount = checkValidInt(et_member1_income_amount.getText().toString());
        long member2Amount = checkValidInt(et_member2_income_amount.getText().toString());
        long totalHoushold = applicantAmount + coapplicantAmount + member1Amount +
                member2Amount;
        et_monthly_income_a.setText(String.valueOf(totalHoushold));
    }

    private long checkValidInt(String s) {
        if (TextUtils.isEmpty(s)) {
            return 0;
        } else {
            return Long.valueOf(s);
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}
