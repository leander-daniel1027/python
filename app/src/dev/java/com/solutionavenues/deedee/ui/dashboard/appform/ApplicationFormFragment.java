package com.solutionavenues.deedee.ui.dashboard.appform;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.support.v7.widget.AppCompatTextView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.helper.ConnectionDetector;
import com.solutionavenues.deedee.MyApplication;
import com.solutionavenues.deedee.R;
import com.solutionavenues.deedee.appBase.AppBaseFragment;
import com.solutionavenues.deedee.database.tables.LeadTable;
import com.solutionavenues.deedee.imagePicker.FileInformation;
import com.solutionavenues.deedee.imagePicker.ImagePickDialog;
import com.solutionavenues.deedee.interfaces.OnBackClickListener;
import com.solutionavenues.deedee.interfaces.OnOkClickListener;
import com.solutionavenues.deedee.model.request.AddLeadRequestModel;
import com.solutionavenues.deedee.model.response.BranchListResponseModel;
import com.solutionavenues.deedee.model.response.CompanyList;
import com.solutionavenues.deedee.model.response.CompanyName;
import com.solutionavenues.deedee.model.response.EnquiryListResponseModel;
import com.solutionavenues.deedee.model.response.GetLeadFieldsResponseModel;
import com.solutionavenues.deedee.model.response.GroupListResponseModel;
import com.solutionavenues.deedee.model.response.LeadResponseModel;
import com.solutionavenues.deedee.model.response.ProductListResponseModel;
import com.solutionavenues.deedee.model.response.UploadImageResponseModel;
import com.solutionavenues.deedee.model.response.UserArea;
import com.solutionavenues.deedee.model.response.UserAreaList;
import com.solutionavenues.deedee.rest.ApiHitListener;
import com.solutionavenues.deedee.rest.ApiIds;
import com.solutionavenues.deedee.rest.BaseArguments;
import com.solutionavenues.deedee.rest.RetrofitUtils;
import com.solutionavenues.deedee.ui.dashboard.setting.ChangePasswordFragment;
import com.solutionavenues.deedee.util.Utils;
import com.squareup.picasso.Picasso;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import okhttp3.MultipartBody;

/**
 * Created by Azher on 18/6/18.
 */
public class ApplicationFormFragment extends AppBaseFragment implements
        DatePickerDialog.OnDateSetListener, ApiHitListener,
        TextWatcher, OnOkClickListener, OnBackClickListener {

    private static final String TAG = "ApplicationFormFragment";
    public Dialog dialog;
    public EnquiryListResponseModel.DataBean enquiryData;
    public AddLeadRequestModel leadDataForEdit;
    public Dialog loadViewDialog;
    public LeadTable leadTable;
    ArrayList<String> allLeadImages;
    AddLeadRequestModel addLeadRequestModel;
    ArrayList<String> yearList;

    ArrayList<String> relationShipArray = new ArrayList<>();


    Handler setDataHandler = new Handler();
    View currentClickedView;
    List<UserAreaList> UserAreaList = new ArrayList<>();
    List<CompanyName> COMPANY_NAME = new ArrayList<>();
    String co_aplicant_name = "", co_aplicant_relation = "", co_aplicant_age = "";
    DialogInterface.OnClickListener onClickedLivingListner = new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int item) {
            if (currentClickedView.getId() == et_living_since.getId()) {
                et_living_since.setText("" + yearList.toArray(new String[yearList.size()])[item]);
            } else if (currentClickedView.getId() == et_permanent_living_since.getId()) {
                et_permanent_living_since.setText("" + yearList.toArray(new String[yearList.size()])[item]);
            } else if (currentClickedView.getId() == et_working_since.getId()) {
                et_working_since.setText("" + yearList.toArray(new String[yearList.size()])[item]);
            }
        }
    };
    View.OnClickListener onExistingLoanDeleteListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ll_existing_loan_containor.removeViewAt(Integer.parseInt(v.getTag().toString()));
            onRefreshExistingLoan();
        }
    };
    View.OnClickListener onExistingLoanAddListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            addExistingLoanView();
            onRefreshExistingLoan();
        }
    };
    View.OnClickListener imageClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            openPicker(v);
        }
    };
    private ArrayList<BranchListResponseModel.DataBean> branchList = new ArrayList<>();
    private ArrayList<GroupListResponseModel.DataBean> groupList = new ArrayList<>();
    private ArrayList<ProductListResponseModel.DataBean> productList = new ArrayList<>();
    private ArrayList<String> groupTitleList = new ArrayList<>();
    private ArrayList<String> branchTitleList = new ArrayList<>();
    private ArrayList<String> productTitleList = new ArrayList<>();
    private ArrayList<GetLeadFieldsResponseModel.DataBean.AreaPaymentCycleBean> paymentCycleList = new ArrayList<>();
    private ArrayList<String> paymentCycleTitleList = new ArrayList<>();
    private ArrayList<String> bankNameList = new ArrayList<>();
    private ArrayList<GetLeadFieldsResponseModel.DataBean.AreaMaritalStatusBean> maritulStatusList = new ArrayList<>();
    private ArrayList<String> maritulStatusTitleList = new ArrayList<>();
    private ArrayList<GetLeadFieldsResponseModel.DataBean.AreaReligionBean> religionList = new ArrayList<>();
    private ArrayList<String> religionTitleList = new ArrayList<>();
    private ArrayList<String> purposeLoanTitleList = new ArrayList<>();
    private ArrayList<String> companyTitleList = new ArrayList<>();
    private ArrayList<String> userArealist = new ArrayList<>();
    private ArrayList<GetLeadFieldsResponseModel.DataBean.AreaDocumentsBean> kycDocList = new ArrayList<>();
    private ArrayList<String> kycDocTitleList = new ArrayList<>();
    private ArrayList<GetLeadFieldsResponseModel.DataBean.AreaTypeOfCustomerBean> customerTypeList = new ArrayList<>();
    private ArrayList<String> customerTypeTitleList = new ArrayList<>();
    private GetLeadFieldsResponseModel.DataBean leadFields;
    private TextView tv_loan_personal_info, tv_address, tv_family_profile,
            tv_monthly_savings, tv_monthly_household, tv_existing_loan,
            tv_cheque_details, tv_kyc_details, tv_declaration_details;
    private LinearLayout ll_loan_personal_info, ll_address, ll_family_profile,
            ll_monthly_savings, ll_monthly_household, ll_existing_loan,
            ll_cheque_details, ll_kyc_details, ll_declaration_details;
    private LinearLayout ll_family_income_containor, ll_existing_loan_containor;
    private ScrollView parentScrollView, childScrollView;
    private HorizontalScrollView hsv_form_header;
    private TextView[] tabArr;
    private LinearLayout[] viewArr;
    private EditText et_documentSelector;
    private String[] documentTypes;
    private LinearLayout documentItemLayout;
    private DatePickerDialog dpd;
    private String selected_date;
    private Calendar calendar;
    private long branch_id;
    DialogInterface.OnClickListener onClickedBranchListner = new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int item) {
            branch_id = branchList.get(item).getId();
            et_branch.setText("" + branchTitleList.toArray(new String[branchTitleList.size()])[item]);
        }
    };
    private long marital_id;
    DialogInterface.OnClickListener onClickedMaritalListner = new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int item) {
            marital_id = maritulStatusList.get(item).getId();
            et_maritul_status.setText("" + maritulStatusTitleList.toArray(new String[maritulStatusTitleList.size()])[item]);
        }
    };

    DialogInterface.OnClickListener onClickedBankListner = new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int item) {
//            marital_id = maritulStatusList.get(item).getId();
            et_applicant_bank_name.setText("" + bankNameList.toArray(new String[bankNameList.size()])[item]);
        }
    };
    DialogInterface.OnClickListener onClickedCoBankListner = new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int item) {
//            marital_id = bankNameList.get(item).getId();
            et_coapplicant_bank_name.setText("" + bankNameList.toArray(new String[bankNameList.size()])[item]);
        }
    };

    private long religion_id;
    DialogInterface.OnClickListener onClickedLoanPurposeListener = new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int item) {
//            religion_id = religionList.get(item).getId();
            et_purpose_of_loan.setText("" + purposeLoanTitleList.toArray(new String[purposeLoanTitleList.size()])[item]);
        }
    };
    DialogInterface.OnClickListener onClickedReligionListner = new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int item) {
            religion_id = religionList.get(item).getId();
            et_religion.setText("" + religionTitleList.toArray(new String[religionTitleList.size()])[item]);
        }
    };

    private long user_area_list_id;
    DialogInterface.OnClickListener onClickedUserArealistListner = new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int item) {
            //  user_area_list_id = us.get(item).getId();
            et_current_address_area.setText("" + userArealist.toArray(new String[userArealist.size()])[item]);
        }
    };
    private long payment_cycle_id;
    DialogInterface.OnClickListener onClickedPaymentListner = new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int item) {
            payment_cycle_id = paymentCycleList.get(item).getId();
            et_payment_cycle.setText("" + paymentCycleTitleList.toArray(new String[paymentCycleTitleList.size()])[item]);
        }
    };
    private long group_id;
    DialogInterface.OnClickListener onClickedGroupListner = new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int item) {
            group_id = groupList.get(item).getId();
            et_group.setText("" + groupTitleList.toArray(new String[groupTitleList.size()])[item]);
        }
    };
    private long customer_type_id;
    private long product_id;
    DialogInterface.OnClickListener onClickedProductListner = new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int item) {
            product_id = productList.get(item).getId();
            et_product.setText("" + productTitleList.toArray(new String[productTitleList.size()])[item]);
        }
    };
    private String customer_id, enuiry_id = "", cibil_status = "";
    private long kyc_doc_id;
    /*Personal info Fields*/
    private EditText et_type_of_customer, et_product, et_group,
            et_coapplicant_name, et_branch, et_amount, et_payment_cycle, et_purpose_of_loan,
            et_name, et_husbands_name, et_father_name, et_mother_name, et_dob,
            et_religion, et_current_address_area, et_maritul_status, et_education, et_k_number;
    DialogInterface.OnClickListener onClickedCustomerTypeListner = new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int item) {
            customer_type_id = customerTypeList.get(item).getId();
            et_type_of_customer.setText("" + customerTypeTitleList.toArray(new String[customerTypeTitleList.size()])[item]);
        }
    };
    private ImageView iv_applicant_photo1, iv_applicant_photo2;
    /*Address info Fields*/
    private EditText et_current_address, et_colony_name, et_ward_number, et_village, et_makan_number, et_city, et_pincode, et_landmark, et_landline,
            et_mobile, et_living_since, et_permanent_address, getEt_permanent_address_area, et_permanent_pincode, et_permant_city,
            et_permanent_landmark, et_permanent_landline, et_permanent_mobile,
            et_permanent_living_since, et_office_address, et_office_pincode,
            et_office_landmark, et_working_since;
    private RadioGroup rg_current_house_type, rg_permanent_house_type;
    private RadioButton rb_owned, rb_rented, rb_permanent_owned, rb_permanent_rented;
    private CheckBox cb_same_permanent_address;
    /*Household info Fields*/
    private EditText et_rent, et_food_and_fuel, et_education_household, et_medical,
            et_other_clothes_household, et_loan_details, et_total_monthly_expenditure;
    /*Savings info Fields*/
    private EditText et_total_monthly_savings, et_insurance_company,
            et_finance_company, et_chit_fund, et_other_clothes, et_bank, et_post_office;
    /*Cheque info Fields*/
    private EditText et_applicant_bank_name, et_applicant_branch,
            et_applicant_cheque_no, et_coapplicant_bank_name, et_coapplicant_branch, et_coapplicant_cheque_no;
    /*Family info Fields*/
    private TextView et_total_family_income;
    View.OnClickListener onFamilyIncomeDeleteListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ll_family_income_containor.removeViewAt(Integer.parseInt(v.getTag().toString()));
            onRefreshFamilyIncome();
        }
    };
    /*Declaration info Fields*/
    private ImageView iv_applicant_signature, iv_coapplicant_signature, iv_employee_signature;
    private boolean isDob;
    private int familyViewClickedPos;
    DialogInterface.OnClickListener onClickedSexListner = new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int item) {
            View v = ll_family_income_containor.getChildAt(familyViewClickedPos);
            EditText et_sex = v.findViewById(R.id.et_sex);
            et_sex.setText("" + getResources().getStringArray(R.array.gender_arr)[item]);
        }
    };
    View.OnClickListener onSexClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            familyViewClickedPos = (int) v.getTag();
            Utils.hideKeyboard(getActivity());
            Utils.showAlertDialog(getActivity(),
                    getString(R.string.select_gender),
                    getResources().getStringArray(R.array.gender_arr),
                    onClickedSexListner);
        }
    };
    View.OnClickListener onFamilyIncomeAddListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            addFamilyIncomeView();
            onRefreshFamilyIncome();
        }
    };
    Runnable setDataRunnable = new Runnable() {
        @Override
        public void run() {
            setAllLeadData(leadDataForEdit);
        }
    };
    private int local_id = -1;
    private String server_lead_id = "";
    private View.OnClickListener documentListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Utils.showListDialog(getActivity(), kycDocTitleList.toArray(new String[kycDocTitleList.size()]), R.string.select_document, new Utils.ListItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    final String documentName = kycDocTitleList.toArray(new String[kycDocTitleList.size()])[position];
//                    if (!isDocumentViewAlreadyShown(documentName)) {
                    kyc_doc_id = kycDocList.get(position).getId();
                    et_documentSelector.setText(documentName);
                    addNewDocumentToLayout(null, LayoutInflater.from(getActivity()), documentName, kyc_doc_id);
//                    }
                }
            });
        }
    };

    public void setEnquiryData(EnquiryListResponseModel.DataBean enquiryData) {
        this.enquiryData = enquiryData;
    }

    public AddLeadRequestModel getLeadDataForEdit() {
        return leadDataForEdit;
    }

    public void setLeadDataForEdit(AddLeadRequestModel leadDataForEdit) {
        this.leadDataForEdit = leadDataForEdit;
    }

    public void setLoadViewDialog(Dialog loadViewDialog) {
        this.loadViewDialog = loadViewDialog;
        alertDialogProgressBar = loadViewDialog;
    }

    @Override
    public int getLayoutResourceId() {
        return R.layout.fragment_application_form;
    }

    @Override
    public void initializeComponent() {
        leadTable = new LeadTable();
        yearList = Utils.getLastYearList(50);
        leadFields = new GetLeadFieldsResponseModel.DataBean();
        hsv_form_header = getView().findViewById(R.id.hsv_form_header);
        parentScrollView = getView().findViewById(R.id.parentScrollView);
        childScrollView = getView().findViewById(R.id.childScrollView);

        /*Header Tab Views*/
        tv_loan_personal_info = getView().findViewById(R.id.tv_loan_personal_info);
        tv_address = getView().findViewById(R.id.tv_address);
        tv_family_profile = getView().findViewById(R.id.tv_family_profile);
        tv_monthly_savings = getView().findViewById(R.id.tv_monthly_savings);
        tv_monthly_household = getView().findViewById(R.id.tv_monthly_household);
        tv_existing_loan = getView().findViewById(R.id.tv_existing_loan);
        tv_cheque_details = getView().findViewById(R.id.tv_cheque_details);
        tv_kyc_details = getView().findViewById(R.id.tv_kyc_details);
        tv_declaration_details = getView().findViewById(R.id.tv_declaration_details);

        /*Header Views*/
        ll_loan_personal_info = getView().findViewById(R.id.ll_loan_personal_info);
        ll_address = getView().findViewById(R.id.ll_address);
        ll_family_profile = getView().findViewById(R.id.ll_family_profile);
        ll_monthly_savings = getView().findViewById(R.id.ll_monthly_savings);
        ll_monthly_household = getView().findViewById(R.id.ll_monthly_household);
        ll_existing_loan = getView().findViewById(R.id.ll_existing_loan);
        ll_cheque_details = getView().findViewById(R.id.ll_cheque_details);
        ll_kyc_details = getView().findViewById(R.id.ll_kyc_details);
        ll_declaration_details = getView().findViewById(R.id.ll_declaration_details);

        /*click listeners to views*/
        tv_loan_personal_info.setOnClickListener(this);
        tv_address.setOnClickListener(this);
        tv_family_profile.setOnClickListener(this);
        tv_monthly_savings.setOnClickListener(this);
        tv_monthly_household.setOnClickListener(this);
        tv_existing_loan.setOnClickListener(this);
        tv_cheque_details.setOnClickListener(this);
        tv_kyc_details.setOnClickListener(this);
        tv_declaration_details.setOnClickListener(this);


        tabArr = new TextView[]{tv_loan_personal_info, tv_address,
                tv_family_profile, tv_monthly_household,
                tv_monthly_savings, tv_existing_loan, tv_cheque_details,
                tv_kyc_details, tv_declaration_details};
        viewArr = new LinearLayout[]{ll_loan_personal_info, ll_address,
                ll_family_profile, ll_monthly_household, ll_monthly_savings,
                ll_existing_loan, ll_cheque_details, ll_kyc_details, ll_declaration_details};

        initPersonalInfoViews();
        initAddressViews();
        initFamilyViews();
        initFamilyIncomeViews();
        initHouseholdViews();
        initSavingsViews();
        initExistingLoanViews();
        initChequeViews();
        initKycViews();
        initDeclarationViews();
        setTabVisibility(tv_loan_personal_info, ll_loan_personal_info);

        if (loadViewDialog != null && loadViewDialog.isShowing()) {
            loadViewDialog.dismiss();
        }
        relationShipArray.add("Self");
        relationShipArray.add("Mother");
        relationShipArray.add("Father");
        relationShipArray.add("Brother");
        relationShipArray.add("Daughter");
        relationShipArray.add("Husband");
        relationShipArray.add("Son");
        relationShipArray.add("Sister");
        relationShipArray.add("Relative");
        relationShipArray.add("Other");


        getDataOffline();
        getBranchList();
        getGroupList();
        getProductList();
        getDynamicFieldsList();
        getUserAreaList(false);
        getCompanyNameList(false);
        getBankNameList(false);
        getPurposeOfLoanList(false);
        if (enquiryData != null) {
            setEnquiryDataOnForm(enquiryData);
        }

        if (leadDataForEdit != null) {
            if (leadDataForEdit.isLocalLead() && !TextUtils.isEmpty(leadDataForEdit.getLocal_id()) &&
                    !leadDataForEdit.getLocal_id().equalsIgnoreCase("null")) {
                local_id = Integer.parseInt(leadDataForEdit.getLocal_id());
                Log.e(TAG, "local_lead: " + local_id);
            } else {
                server_lead_id = leadDataForEdit.getLead_id();
            }
            setDataHandler.post(setDataRunnable);
        }

    }

    @Override
    public void onDestroyView() {
        Log.e(TAG, "onDestroyView: ");
        super.onDestroyView();
    }

    @Override
    public void onDetach() {
        Log.e(TAG, "onDetach: ");
        super.onDetach();
    }

    private void setEnquiryDataOnForm(EnquiryListResponseModel.DataBean enquiryData) {
        et_name.setText(enquiryData.getApplicant_name());
        et_husbands_name.setText(enquiryData.getSpouse_name());
        et_father_name.setText(enquiryData.getApplicant_father_name());
        et_mother_name.setText(enquiryData.getApplicant_mother_name());
        et_dob.setText(enquiryData.getApplicant_dob());
        et_current_address.setText(enquiryData.getApplicant_address());
        et_mobile.setText(enquiryData.getApplicant_mobile1());

        enuiry_id = enquiryData.getId();
        cibil_status = enquiryData.getCebil_status();
    }

    private void initChequeViews() {
        et_applicant_bank_name = getView().findViewById(R.id.et_applicant_bank_name);
        et_applicant_branch = getView().findViewById(R.id.et_applicant_branch);
        et_applicant_cheque_no = getView().findViewById(R.id.et_applicant_cheque_no);
        et_coapplicant_bank_name = getView().findViewById(R.id.et_coapplicant_bank_name);
        et_coapplicant_branch = getView().findViewById(R.id.et_coapplicant_branch);
        et_coapplicant_cheque_no = getView().findViewById(R.id.et_coapplicant_cheque_no);
        et_coapplicant_bank_name.setOnClickListener(this);
        et_applicant_bank_name.setOnClickListener(this);
    }

    private void initHouseholdViews() {
        et_rent = getView().findViewById(R.id.et_rent);
        et_food_and_fuel = getView().findViewById(R.id.et_food_and_fuel);
        et_loan_details = getView().findViewById(R.id.et_loan_details);
        et_education_household = getView().findViewById(R.id.et_education_household);
        et_medical = getView().findViewById(R.id.et_medical);
        et_other_clothes_household = getView().findViewById(R.id.et_other_clothes_household);
        et_total_monthly_expenditure = getView().findViewById(R.id.et_total_monthly_expenditure);
        et_rent.addTextChangedListener(this);
        et_food_and_fuel.addTextChangedListener(this);
        et_loan_details.addTextChangedListener(this);
        et_education_household.addTextChangedListener(this);
        et_medical.addTextChangedListener(this);
        et_other_clothes_household.addTextChangedListener(this);

    }

    private void initSavingsViews() {
        et_bank = getView().findViewById(R.id.et_bank);
        et_post_office = getView().findViewById(R.id.et_post_office);
        et_insurance_company = getView().findViewById(R.id.et_insurance_company);
        et_finance_company = getView().findViewById(R.id.et_finance_company);
        et_chit_fund = getView().findViewById(R.id.et_chit_fund);
        et_other_clothes = getView().findViewById(R.id.et_other_clothes);
        et_total_monthly_savings = getView().findViewById(R.id.et_total_monthly_savings);
        et_bank.addTextChangedListener(this);
        et_post_office.addTextChangedListener(this);
        et_insurance_company.addTextChangedListener(this);
        et_finance_company.addTextChangedListener(this);
        et_chit_fund.addTextChangedListener(this);
        et_other_clothes.addTextChangedListener(this);
    }

    private void initPersonalInfoViews() {
        iv_applicant_photo1 = getView().findViewById(R.id.iv_applicant_photo1);
        iv_applicant_photo2 = getView().findViewById(R.id.iv_applicant_photo2);
        // et_date = getView().findViewById(R.id.et_date);
        et_type_of_customer = getView().findViewById(R.id.et_type_of_customer);
        et_coapplicant_name = getView().findViewById(R.id.et_coapplicant_name);
        et_k_number = getView().findViewById(R.id.et_k_number);
        et_product = getView().findViewById(R.id.et_product);
        et_group = getView().findViewById(R.id.et_group);
        et_branch = getView().findViewById(R.id.et_branch);
        et_amount = getView().findViewById(R.id.et_amount);
        et_payment_cycle = getView().findViewById(R.id.et_payment_cycle);
        et_purpose_of_loan = getView().findViewById(R.id.et_purpose_of_loan);
        et_name = getView().findViewById(R.id.et_name);
        et_husbands_name = getView().findViewById(R.id.et_husbands_name);
        et_father_name = getView().findViewById(R.id.et_father_name);
        et_mother_name = getView().findViewById(R.id.et_mother_name);
        et_dob = getView().findViewById(R.id.et_dob);
        et_religion = getView().findViewById(R.id.et_religion);
        et_current_address_area = getView().findViewById(R.id.et_current_address_area);
        et_maritul_status = getView().findViewById(R.id.et_maritul_status);
        et_education = getView().findViewById(R.id.et_education);
//        if (getMyApplication().getUserPrefs().getLoggedInUser().getProduct() != null && getMyApplication().getUserPrefs().getLoggedInUser().getProduct().size() > 0) {
//            String userProduct = getMyApplication().getUserPrefs().getLoggedInUser().getProduct().get(0).getProduct_name();
//            product_id = getMyApplication().getUserPrefs().getLoggedInUser().getProduct().get(0).getId();
//            et_product.setText(userProduct);
//        }
        if (getMyApplication().getUserPrefs().getLoggedInUser().getProduct() != null) {
            String userProduct = getMyApplication().getUserPrefs().getLoggedInUser().getProduct().getProduct_name();
            product_id = getMyApplication().getUserPrefs().getLoggedInUser().getProduct().getId();
            et_product.setText(userProduct);
        }

        iv_applicant_photo1.setOnClickListener(this);
        iv_applicant_photo2.setOnClickListener(this);
        et_product.setOnClickListener(this);
        et_group.setOnClickListener(this);
        et_branch.setOnClickListener(this);
        et_maritul_status.setOnClickListener(this);
        et_dob.setOnClickListener(this);
        et_religion.setOnClickListener(this);
        et_current_address_area.setOnClickListener(this);
        et_payment_cycle.setOnClickListener(this);
        et_type_of_customer.setOnClickListener(this);
        et_purpose_of_loan.setOnClickListener(this);
    }

    private void initAddressViews() {
        et_current_address = getView().findViewById(R.id.et_current_address);
        et_colony_name = getView().findViewById(R.id.et_colony_name);
        et_ward_number = getView().findViewById(R.id.et_ward_number);
        et_village = getView().findViewById(R.id.et_village_name);
        et_makan_number = getView().findViewById(R.id.et_makan_number);
        et_city = getView().findViewById(R.id.et_city);

        et_pincode = getView().findViewById(R.id.et_pincode);
        et_landmark = getView().findViewById(R.id.et_landmark);
        et_landline = getView().findViewById(R.id.et_landline);
        et_mobile = getView().findViewById(R.id.et_mobile);
        et_living_since = getView().findViewById(R.id.et_living_since);
        et_permanent_address = getView().findViewById(R.id.et_permanent_address);
        getEt_permanent_address_area = getView().findViewById(R.id.et_permanent_address_area);
        et_permant_city = getView().findViewById(R.id.et_permant_city);
        et_permanent_pincode = getView().findViewById(R.id.et_permanent_pincode);
        et_permanent_landmark = getView().findViewById(R.id.et_permanent_landmark);
        et_permanent_landline = getView().findViewById(R.id.et_permanent_landline);
        et_permanent_mobile = getView().findViewById(R.id.et_permanent_mobile);
        et_permanent_living_since = getView().findViewById(R.id.et_permanent_living_since);
        et_office_address = getView().findViewById(R.id.et_office_address);
        et_office_pincode = getView().findViewById(R.id.et_office_pincode);
        et_office_landmark = getView().findViewById(R.id.et_office_landmark);
        et_working_since = getView().findViewById(R.id.et_working_since);
        rg_current_house_type = getView().findViewById(R.id.rg_current_house_type);
        rg_permanent_house_type = getView().findViewById(R.id.rg_permanent_house_type);
        rb_owned = getView().findViewById(R.id.rb_owned);
        rb_rented = getView().findViewById(R.id.rb_rented);
        rb_permanent_owned = getView().findViewById(R.id.rb_permanent_owned);
        rb_permanent_rented = getView().findViewById(R.id.rb_permanent_rented);
        cb_same_permanent_address = getView().findViewById(R.id.cb_same_permanent_address);
//        et_living_since.setOnClickListener(this);
        et_permanent_living_since.setOnClickListener(this);
        et_working_since.setOnClickListener(this);
        cb_same_permanent_address.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    setPermanentAsCurrentAddress();
                } else {
                    removePermanentAddress();
                }
            }
        });

    }

    private void initFamilyViews() {
        et_total_family_income = getView().findViewById(R.id.et_total_family_income);
    }

    private void setPermanentAsCurrentAddress() {
        et_permanent_address.setText(et_colony_name.getText().toString());
        getEt_permanent_address_area.setText(et_current_address_area.getText().toString());
        et_permant_city.setText(et_city.getText().toString());
        et_permanent_pincode.setText(et_pincode.getText().toString());
        et_permanent_landmark.setText(et_landmark.getText().toString());
        et_permanent_landline.setText(et_landline.getText().toString());
        et_permanent_mobile.setText(et_mobile.getText().toString());
        et_permanent_living_since.setText(et_living_since.getText().toString());
    }

    private void removePermanentAddress() {
        et_permanent_address.getText().clear();
        getEt_permanent_address_area.getText().clear();
        et_permant_city.getText().clear();
        et_permanent_pincode.getText().clear();
        et_permanent_landmark.getText().clear();
        et_permanent_landline.getText().clear();
        et_permanent_mobile.getText().clear();
        et_permanent_living_since.getText().clear();
    }

    private void initFamilyIncomeViews() {
        ll_family_income_containor = getView().findViewById(R.id.ll_family_income_containor);
        addFamilyIncomeView();
    }

    private void initExistingLoanViews() {
        ll_existing_loan_containor = getView().findViewById(R.id.ll_existing_loan_containor);
        addExistingLoanView();
    }

    ImageView img_housephoto;

    private void initKycViews() {
        et_documentSelector = getView().findViewById(R.id.et_documentSelector);
        img_housephoto = getView().findViewById(R.id.img_housephoto);
        img_housephoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openPicker(img_housephoto);
            }
        });
        documentTypes = getResources().getStringArray(R.array.kyc_docuemnt_types);
        documentItemLayout = getView().findViewById(R.id.documentItems);
        et_documentSelector.setOnClickListener(documentListener);
    }

    private void initDeclarationViews() {
        iv_applicant_signature = getView().findViewById(R.id.iv_applicant_signature);
        iv_coapplicant_signature = getView().findViewById(R.id.iv_coapplicant_signature);
        iv_employee_signature = getView().findViewById(R.id.iv_employee_signature);
        iv_coapplicant_signature.setOnClickListener(this);
        iv_applicant_signature.setOnClickListener(this);
        iv_employee_signature.setOnClickListener(this);
    }

    // call to getUserAreaList service
    private void getUserAreaList(boolean progressVisibility) {
        if (ConnectionDetector.isNetAvail(getActivity())) {
            if (progressVisibility) {
                displayProgressBar(false, getActivity());
            }
            String user_id = String.valueOf(getMyApplication().getUserPrefs().getLoggedInUser().getId());
            getRestClient().callback(this).getUserAreaList(user_id);
        } else {
//            displayErrorDialog(getString(R.string.error_internet_connection));
        }
    }

    private void getPurposeOfLoanList(boolean progressVisibility) {
        if (ConnectionDetector.isNetAvail(getActivity())) {
            if (progressVisibility) {
                displayProgressBar(false, getActivity());
            }
            String user_id = String.valueOf(getMyApplication().getUserPrefs().getLoggedInUser().getId());
            getRestClient().callback(this).getPurposesList(user_id);
        } else {
//            displayErrorDialog(getString(R.string.error_internet_connection));
        }
    }

    private void getBankNameList(boolean progressVisibility) {
        if (ConnectionDetector.isNetAvail(getActivity())) {
            if (progressVisibility) {
                displayProgressBar(false, getActivity());
            }
            String user_id = String.valueOf(getMyApplication().getUserPrefs().getLoggedInUser().getId());
            getRestClient().callback(this).getBankNameList(user_id);
        } else {
//            displayErrorDialog(getString(R.string.error_internet_connection));
        }
    }

    private void getCompanyNameList(boolean progressVisibility) {
        if (ConnectionDetector.isNetAvail(getActivity())) {
            if (progressVisibility) {
                displayProgressBar(false, getActivity());
            }
            String user_id = String.valueOf(getMyApplication().getUserPrefs().getLoggedInUser().getId());
            getRestClient().callback(this).getCompanyNameList(user_id);
        } else {
//            displayErrorDialog(getString(R.string.error_internet_connection));
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_loan_personal_info:
                manageSaveAndSubmitButton(false, tv_loan_personal_info, ll_loan_personal_info);
                break;
            case R.id.et_applicant_bank_name:
                if (bankNameList != null && bankNameList.size() > 0) {
                    openBankDialog();
                } else {
                    displayToast(getString(R.string.no_option_available));
                }
                break;
            case R.id.et_purpose_of_loan:
                if (purposeLoanTitleList != null && purposeLoanTitleList.size() > 0) {
                    openPurposeOfLoanDialog();
                } else {
                    displayToast(getString(R.string.no_option_available));
                }
                break;
            case R.id.et_coapplicant_bank_name:
                if (bankNameList != null && bankNameList.size() > 0) {
                    openCoBankDialog();
                } else {
                    displayToast(getString(R.string.no_option_available));
                }
                break;
            case R.id.tv_address:
                manageSaveAndSubmitButton(false, tv_address, ll_address);
                break;
            case R.id.tv_family_profile:
                setDefaultFamilyData();
                manageSaveAndSubmitButton(false, tv_family_profile, ll_family_profile);
                break;
            case R.id.tv_existing_loan:
                manageSaveAndSubmitButton(false, tv_existing_loan, ll_existing_loan);
                break;
            case R.id.tv_monthly_savings:
                manageSaveAndSubmitButton(false, tv_monthly_savings, ll_monthly_savings);
                break;
            case R.id.tv_monthly_household:
                manageSaveAndSubmitButton(false, tv_monthly_household, ll_monthly_household);
                break;
            case R.id.tv_cheque_details:
                manageSaveAndSubmitButton(false, tv_cheque_details, ll_cheque_details);
                break;
            case R.id.tv_kyc_details:
                manageSaveAndSubmitButton(false, tv_kyc_details, ll_kyc_details);
                break;
            case R.id.tv_declaration_details:
                manageSaveAndSubmitButton(false, tv_declaration_details, ll_declaration_details);
                break;
            case R.id.et_branch:
                if (branchTitleList != null && branchTitleList.size() > 0) {
                    openBranchDialog();
                } else {
                    displayToast(getString(R.string.no_branches_available));
                }
                break;
            case R.id.et_maritul_status:
                if (maritulStatusTitleList != null && maritulStatusTitleList.size() > 0) {
                    openMaritalStatusDialog();
                } else {
                    displayToast(getString(R.string.no_option_available));
                }
                break;
            case R.id.et_religion:
                if (religionTitleList != null && religionTitleList.size() > 0) {
                    openReligionDialog();
                } else {
                    displayToast(getString(R.string.no_option_available));
                }
                break;
            case R.id.et_current_address_area:
                if (UserAreaList != null && UserAreaList.size() > 0) {
                    openUserAreaListDialog();
                } else {
                    displayToast(getString(R.string.no_option_available));
                }
                break;
            case R.id.et_payment_cycle:
                if (paymentCycleTitleList != null && paymentCycleTitleList.size() > 0) {
                    openPaymentCycleDialog();
                } else {
                    displayToast(getString(R.string.no_option_available));
                }
                break;
            case R.id.et_type_of_customer:
                if (customerTypeTitleList != null && customerTypeTitleList.size() > 0) {
                    openCustomerTypeDialog();
                } else {
                    displayToast(getString(R.string.no_option_available));
                }
                break;
            case R.id.et_group:
                if (groupTitleList != null && groupTitleList.size() > 0) {
                    openGroupDialog();
                } else {
                    displayToast(getString(R.string.no_option_available));
                }
                break;
            case R.id.et_product:
                if (productTitleList != null && productTitleList.size() > 0) {
                    openProductDialog();
                } else {
                    displayToast(getString(R.string.no_option_available));
                }
                break;
            case R.id.et_dob:
                isDob = true;
                openDatePicker(false);
                break;
            case R.id.et_living_since:
                openLivingSinceDialog(v);
                break;
            case R.id.et_permanent_living_since:
                openLivingSinceDialog(v);
                break;
            case R.id.et_city:
                openCityDialog();
                break;
            case R.id.et_working_since:
                openLivingSinceDialog(v);
                break;
            case R.id.et_date:
                isDob = false;
                openDatePicker(true);
                break;
            case R.id.iv_applicant_photo2:
                openPicker(iv_applicant_photo2);
                break;
            case R.id.iv_applicant_photo1:
                openPicker(iv_applicant_photo1);
                break;
            case R.id.iv_employee_signature:
                openPicker(iv_employee_signature);
                break;
            case R.id.iv_applicant_signature:
                openPicker(iv_applicant_signature);
                break;
            case R.id.iv_coapplicant_signature:
                openPicker(iv_coapplicant_signature);
                break;

        }

    }


    private void setDefaultFamilyData() {
        View view = ll_family_income_containor.getChildAt(0);
        EditText et_member_name = view.findViewById(R.id.et_member_name);
        EditText et_education_family = view.findViewById(R.id.et_education);
        EditText et_relationship = view.findViewById(R.id.et_relationship);
        EditText et_member_age = view.findViewById(R.id.et_member_age);
        EditText et_sex = view.findViewById(R.id.et_sex);
        et_sex.setText("" + getResources().getStringArray(R.array.gender_arr)[1]);
        et_education_family.setText(et_education.getText().toString());
        et_member_name.setText(et_name.getText().toString());
        et_relationship.setText(relationShipArray.get(0));
        if (!TextUtils.isEmpty(et_dob.getText())) {
            String dob_arr[] = et_dob.getText().toString().split("-");
            et_member_age.setText(Utils.getAge(Integer.valueOf(dob_arr[0]), Integer.valueOf(dob_arr[1]), Integer.valueOf(dob_arr[2])));
        }
    }

    private void openCityDialog() {
        Utils.hideKeyboard(getActivity());
        Utils.showAlertDialog(getActivity(),
                getString(R.string.select_customer_type),
                customerTypeTitleList.toArray(new String[customerTypeTitleList.size()]),
                onClickedCustomerTypeListner);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (ll_monthly_household.getVisibility() == View.VISIBLE) {
            long rentAmount = checkValidInt(et_rent.getText().toString());
            long food_and_fuel_Amount = checkValidInt(et_food_and_fuel.getText().toString());
            long loan_details_Amount = checkValidInt(et_loan_details.getText().toString());
            long education_household_Amount = checkValidInt(et_education_household.getText().toString());
            long medical_Amount = checkValidInt(et_medical.getText().toString());
            long other_clothes_household_Amount = checkValidInt(et_other_clothes_household.getText().toString());
            long totalHoushold = rentAmount + food_and_fuel_Amount + loan_details_Amount +
                    education_household_Amount + medical_Amount + other_clothes_household_Amount;
            et_total_monthly_expenditure.setText(String.valueOf(totalHoushold));
        } else if (ll_monthly_savings.getVisibility() == View.VISIBLE) {
            long insurance_company_Amount = checkValidInt(et_insurance_company.getText().toString());
            long finance_company_Amount = checkValidInt(et_finance_company.getText().toString());
            long chit_fund_Amount = checkValidInt(et_chit_fund.getText().toString());
            long other_clothes_Amount = checkValidInt(et_other_clothes.getText().toString());
            long bank_Amount = checkValidInt(et_bank.getText().toString());
            long post_office_Amount = checkValidInt(et_post_office.getText().toString());
            long totalHoushold = insurance_company_Amount + finance_company_Amount + other_clothes_Amount +
                    bank_Amount + post_office_Amount + chit_fund_Amount;
            et_total_monthly_savings.setText(String.valueOf(totalHoushold));
        } else if (ll_family_profile.getVisibility() == View.VISIBLE) {
            setTotalFamilyAmount();
        }

    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    private long checkValidInt(String s) {
        if (TextUtils.isEmpty(s)) {
            return 0;
        } else {
            return Long.valueOf(s);
        }
    }

    private void setTotalFamilyAmount() {
        int total_family_income = 0;
        for (int i = 0; i < ll_family_income_containor.getChildCount(); i++) {
            View view = ll_family_income_containor.getChildAt(i);
            EditText et_monthly_income = view.findViewById(R.id.et_monthly_income);
            long monthly_income = checkValidInt(et_monthly_income.getText().toString());
            total_family_income += monthly_income;
        }
        et_total_family_income.setText(String.valueOf(total_family_income));
    }

    boolean isValidate = true;

    private AddLeadRequestModel getAllLeadData() {
        AddLeadRequestModel addLeadRequestModel = new AddLeadRequestModel();
        addLeadRequestModel.setLocal_id(String.valueOf(local_id));
        addLeadRequestModel.setLead_id(String.valueOf(server_lead_id));
        addLeadRequestModel.setLoanPersonelInfo(getPersonalData());
        addLeadRequestModel.setAddressInfo(getAddressData());
        addLeadRequestModel.setFamilyProfile(getFamilyData());
        addLeadRequestModel.setHouseHoldInfo(getHouseholdData());
        addLeadRequestModel.setSavingsInfo(getSavingsData());
        addLeadRequestModel.setExistingLoanInfoList(getExistingLoanData());
        addLeadRequestModel.setChequeInfo(getChequeData());
        addLeadRequestModel.setKycInfoList(getKycData());
        addLeadRequestModel.setDeclarationInfo(getDeclarationData());
        return addLeadRequestModel;
    }

    private void setAllLeadData(AddLeadRequestModel leadResponseModel) {
        setPersonalData(leadResponseModel);
        setAddressData(leadResponseModel);
        setFamilyData(leadResponseModel);
        setHouseholdData(leadResponseModel);
        setSavingsData(leadResponseModel);
        setExistingLoanData(leadResponseModel);
        setChequeData(leadResponseModel);
        setKycData(leadResponseModel);
        setDeclarationData(leadResponseModel);
    }

    boolean personalInfoValidation() {
        isValidate = true;
        if (!isValidString(et_branch.getText().toString())) {
            isValidate = false;
            showToast(getString(R.string.select_branch));
        } else if (!isValidString(et_purpose_of_loan.getText().toString())) {
            isValidate = false;
            showToast(getString(R.string.enter_purposeofloan));
        } else if (!isValidString(et_name.getText().toString())) {
            isValidate = false;
            showToast(getString(R.string.please_enter_applicant_name));
        } else if (!isValidString(et_husbands_name.getText().toString())) {
            isValidate = false;
            showToast(getString(R.string.please_enter_applicant_spouse_name));
        } else if (!isValidString(et_father_name.getText().toString())) {
            isValidate = false;
            showToast(getString(R.string.please_enter_applicant_father_name));
        } else if (!isValidString(et_mother_name.getText().toString())) {
            isValidate = false;
            showToast(getString(R.string.please_enter_applicant_mother_name));
        } else if (!isValidString(et_dob.getText().toString())) {
            isValidate = false;
            showToast(getString(R.string.please_enter_applicant_dob));
        }  /*else if (!isValidString(et_amount.getText().toString())) {
            isValidate = false;
            showToast(getString(R.string.enter_amount));
        }*/ else if (!isValidString(et_maritul_status.getText().toString())) {
            isValidate = false;
            showToast(getString(R.string.select_marital_status));
        } else if (!isValidString(et_religion.getText().toString())) {
            isValidate = false;
            showToast(getString(R.string.select_religion));
        } else if (!isValidString(et_education.getText().toString())) {
            isValidate = false;
            showToast(getString(R.string.enter_education));
        } /*else if (iv_applicant_photo2.getTag() == null || TextUtils.isEmpty(iv_applicant_photo2.getTag().toString())) {
            isValidate = false;
            showToast(getString(R.string.add_coapplicant_image));
        }*/
        return isValidate;
    }

    boolean addressValidation() {
        isValidate = true;
        if (!isValidString(et_colony_name.getText().toString())) {
            isValidate = false;
            showToast(getString(R.string.please_enter_applicant_address));
        } else if (!isValidString(et_city.getText().toString())) {
            isValidate = false;
            showToast(getString(R.string.enter_city));
        } else if (!isValidString(et_landline.getText().toString())) {
            isValidate = false;
            showToast(getString(R.string.enter_landline));
        } else if (!isValidString(et_pincode.getText().toString())) {
            isValidate = false;
            showToast(getString(R.string.please_enter_pincode));
        } else if (!isValidString(et_mobile.getText().toString())) {
            isValidate = false;
            showToast(getString(R.string.enter_mobile));
        } else if (!isValidString(et_living_since.getText().toString())) {
            isValidate = false;
            showToast(getString(R.string.enter_living_since));
        }
        return isValidate;
    }

    boolean familyValidation() {
        if (getFamilyData() == null)
            return false;
        for (int i = 0; i < getFamilyData().familyProfileInfoList.size(); i++) {
            if (!isValidString(getFamilyData().familyProfileInfoList.get(i).getMember_name())) {
                showToast("Please enter family member name.");
                return false;
            } else if (!isValidString(getFamilyData().familyProfileInfoList.get(i).getAge())) {
                showToast("Please enter age.");
                return false;
            } else if (!isValidString(getFamilyData().familyProfileInfoList.get(i).getRelationship())) {
                showToast("Please enter relationship");
                return false;
            } else if (!isValidString(getFamilyData().familyProfileInfoList.get(i).getEducation())) {
                showToast("Please enter education.");
                return false;
            } else if (!isValidString(getFamilyData().familyProfileInfoList.get(i).getSex())) {
                showToast("Please enter sex.");
                return false;
            } else if (!isValidString(getFamilyData().familyProfileInfoList.get(i).getOccupation())) {
                showToast("Please enter occupation.");
                return false;
            } else if (getFamilyData().familyProfileInfoList.get(i).getRelationship().equalsIgnoreCase("Self") && !isValidString(getFamilyData().familyProfileInfoList.get(i).getMobile_number())) {
                showToast("Please enter mobile number.");
                return false;
            } else if (getFamilyData().familyProfileInfoList.get(i).getRelationship().equalsIgnoreCase("Self") && !isValidString(getFamilyData().familyProfileInfoList.get(i).getMonthly_income())) {
                showToast("Please enter monthly income.");
                return false;
            } else if (getFamilyData().familyProfileInfoList.get(i).getRelationship().equalsIgnoreCase("Self")) {
                if (Integer.parseInt(getFamilyData().familyProfileInfoList.get(i).getAge()) < 18 || Integer.parseInt(getFamilyData().familyProfileInfoList.get(i).getAge()) > 55) {
                    showToast("Age should be 18 to 55 years.");
                    return false;
                }
            } else if (getFamilyData().familyProfileInfoList.get(i).isCoapp()) {
                if (Integer.parseInt(getFamilyData().familyProfileInfoList.get(i).getAge()) < 18 || Integer.parseInt(getFamilyData().familyProfileInfoList.get(i).getAge()) > 50) {
                    showToast("Age should be 18 to 50 years.");
                    return false;
                }
            }
        }
        boolean co_applicant = false;
        for (int i = 0; i < getFamilyData().familyProfileInfoList.size(); i++) {
            if (getFamilyData().familyProfileInfoList.get(i).isCoapp) {
                co_applicant = true;
            }
        }
        if (co_applicant) {
            return true;
        } else {
            showToast("Please make at least one Co-Applicant.");
            return false;
        }
    }

    private void manageSaveAndSubmitButton(boolean navigateNext, TextView visibleTv, LinearLayout visibleLl) {
        int currentVisibleLayoutPosition = getVisibleLayout();
        if (local_id == -1) {
            insertDataInLeadTable();
        } else {
            updateDataInLeadTable(currentVisibleLayoutPosition);
        }
        if (navigateNext) {
            if (currentVisibleLayoutPosition != tabArr.length - 1 && currentVisibleLayoutPosition == 0) {
                if (personalInfoValidation()) {
                    if (currentVisibleLayoutPosition != tabArr.length - 1) {
                        hsv_form_header.scrollBy(120, 0);
                        if (currentVisibleLayoutPosition == 1) {
                            setDefaultFamilyData();
                        }
                        setTabVisibility(tabArr[currentVisibleLayoutPosition + 1], viewArr[currentVisibleLayoutPosition + 1]);
                    }
                }
            } else if (currentVisibleLayoutPosition != tabArr.length - 1 && currentVisibleLayoutPosition == 1) {
                if (addressValidation()) {
                    if (currentVisibleLayoutPosition != tabArr.length - 1) {
                        hsv_form_header.scrollBy(120, 0);
                        if (currentVisibleLayoutPosition == 1) {
                            setDefaultFamilyData();
                        }
                        setTabVisibility(tabArr[currentVisibleLayoutPosition + 1], viewArr[currentVisibleLayoutPosition + 1]);
                    }
                }
            } else if (currentVisibleLayoutPosition != tabArr.length - 1 && currentVisibleLayoutPosition == 2) {
                if (familyValidation()) {
                    if (currentVisibleLayoutPosition != tabArr.length - 1) {
                        hsv_form_header.scrollBy(120, 0);
                        if (currentVisibleLayoutPosition == 1) {
                            setDefaultFamilyData();
                        }
                        setTabVisibility(tabArr[currentVisibleLayoutPosition + 1], viewArr[currentVisibleLayoutPosition + 1]);
                    }
                }
            } else if (currentVisibleLayoutPosition != tabArr.length - 1 && currentVisibleLayoutPosition == 7) {
                if (img_housephoto.getTag() == null) {
                    showToast(getString(R.string.please_enter_household_photo));
                } else if (!documentNumAvial()) {
                    showToast(getString(R.string.please_enter_Document_number));
                } else {
                    if (currentVisibleLayoutPosition != tabArr.length - 1) {
                        hsv_form_header.scrollBy(120, 0);
                        if (currentVisibleLayoutPosition == 1) {
                            setDefaultFamilyData();
                        }
                        setTabVisibility(tabArr[currentVisibleLayoutPosition + 1], viewArr[currentVisibleLayoutPosition + 1]);
                    }
                }
            } else {
                if (currentVisibleLayoutPosition != tabArr.length - 1) {
                    hsv_form_header.scrollBy(120, 0);
                    if (currentVisibleLayoutPosition == 1) {
                        setDefaultFamilyData();
                    }
                    setTabVisibility(tabArr[currentVisibleLayoutPosition + 1], viewArr[currentVisibleLayoutPosition + 1]);
                }
            }
        } else {
            if (currentVisibleLayoutPosition != tabArr.length - 1 && currentVisibleLayoutPosition == 0) {
                if (personalInfoValidation()) {
                    if (currentVisibleLayoutPosition != tabArr.length - 1) {
                        hsv_form_header.scrollBy(120, 0);
                        if (currentVisibleLayoutPosition == 1) {
                            setDefaultFamilyData();
                        }
                        setTabVisibility(tabArr[currentVisibleLayoutPosition + 1], viewArr[currentVisibleLayoutPosition + 1]);
                    }
                }
            } else if (currentVisibleLayoutPosition != tabArr.length - 1 && currentVisibleLayoutPosition == 1) {
                if (addressValidation()) {
                    setTabVisibility(visibleTv, visibleLl);
                }
            } else if (currentVisibleLayoutPosition != tabArr.length - 1 && currentVisibleLayoutPosition == 2) {
                if (familyValidation()) {
                    setTabVisibility(visibleTv, visibleLl);
                }
            } else if (currentVisibleLayoutPosition != tabArr.length - 1 && currentVisibleLayoutPosition == 7) {
                if (img_housephoto.getTag() == null) {
                    showToast(getString(R.string.please_enter_household_photo));
                } else if (!documentNumAvial()) {
                    showToast(getString(R.string.please_enter_Document_number));
                } else {
                    setTabVisibility(visibleTv, visibleLl);
                }
            } else {
//                if (currentVisibleLayoutPosition != tabArr.length - 1) {
                setTabVisibility(visibleTv, visibleLl);
//                }
            }
        }
    }

    private void updateDataInLeadTable(int visibleLayoutPosition) {
        TextView visibleTab = tabArr[visibleLayoutPosition];
        String columnToUpdate = "";
        String dataToUpdate = "";
        if (visibleTab.getText().toString().equalsIgnoreCase(getString(R.string.loan_personal_info))) {
            columnToUpdate = leadTable.LOANPERSONEL_INFO;
            dataToUpdate = leadTable.getStringJson(getPersonalData());
        } else if (visibleTab.getText().toString().equalsIgnoreCase(getString(R.string.address))) {
            columnToUpdate = leadTable.ADDRESS_INFO;
            dataToUpdate = leadTable.getStringJson(getAddressData());
        } else if (visibleTab.getText().toString().equalsIgnoreCase(getString(R.string.family_profile))) {
            columnToUpdate = leadTable.FAMILYPROFILE_INFO;
            dataToUpdate = leadTable.getStringJson(getFamilyData());
        } else if (visibleTab.getText().toString().equalsIgnoreCase(getString(R.string.monthly_household))) {
            columnToUpdate = leadTable.HOUSEHOLD_INFO;
            dataToUpdate = leadTable.getStringJson(getHouseholdData());
        } else if (visibleTab.getText().toString().equalsIgnoreCase(getString(R.string.monthly_savings))) {
            columnToUpdate = leadTable.SAVINGS_INFO;
            dataToUpdate = leadTable.getStringJson(getSavingsData());
        } else if (visibleTab.getText().toString().equalsIgnoreCase(getString(R.string.existing_loan))) {
            columnToUpdate = leadTable.EXISTINGLOAN_INFO;
            dataToUpdate = leadTable.getStringJson(getExistingLoanData());
        } else if (visibleTab.getText().toString().equalsIgnoreCase(getString(R.string.cheque_details))) {
            columnToUpdate = leadTable.CHEQUE_INFO;
            dataToUpdate = leadTable.getStringJson(getChequeData());
        } else if (visibleTab.getText().toString().equalsIgnoreCase(getString(R.string.kyc_details))) {
            columnToUpdate = leadTable.KYC_INFO;
            dataToUpdate = leadTable.getStringJson(getKycData());
        } else if (visibleTab.getText().toString().equalsIgnoreCase(getString(R.string.declaration))) {
            columnToUpdate = leadTable.DECLARATION_INFO;
            dataToUpdate = leadTable.getStringJson(getDeclarationData());
        }
        int updateId = leadTable.updateLeadColumn(local_id, columnToUpdate, dataToUpdate);
        Log.e(TAG, "updateDataInLeadTable: " + local_id + " update_id: " + updateId);
    }

    private void insertDataInLeadTable() {
        local_id = (int) leadTable.insert(getAllLeadData());
        Log.e(TAG, "lead_insert_id: " + local_id);
        if (local_id != -1) {
        }
    }

    public void saveData() {
        try {
            if (getDashBoardActivity().getToolBarHelper().tv_save_continue.getText().toString().equalsIgnoreCase(getString(R.string.submit))) {

                addLeadRequestModel = getAllLeadData();
                allLeadImages = getAllImagesList(addLeadRequestModel);
//                if (isValidate) {
                if (leadDataForEdit != null && !leadDataForEdit.isLocalLead()) {
                    editAppForm();
                } else {
                    submitAppForm();
//                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        manageSaveAndSubmitButton(true, null, null);
    }

    private ArrayList<String> getAllImagesList(AddLeadRequestModel addLeadRequestModel) {
        ArrayList<String> allImagesList = new ArrayList<>();
        if (!TextUtils.isEmpty(addLeadRequestModel.getLoanPersonelInfo().getApplicant_photo1()) &&
                addLeadRequestModel.getLoanPersonelInfo().getApplicant_photo1().contains("/")) {
            allImagesList.add(addLeadRequestModel.getLoanPersonelInfo().getApplicant_photo1());
        }
        if (!TextUtils.isEmpty(addLeadRequestModel.getLoanPersonelInfo().getApplicant_photo2()) &&
                addLeadRequestModel.getLoanPersonelInfo().getApplicant_photo2().contains("/")) {
            allImagesList.add(addLeadRequestModel.getLoanPersonelInfo().getApplicant_photo2());
        }
        if (!TextUtils.isEmpty(addLeadRequestModel.getDeclarationInfo().getApplicant_signature()) &&
                addLeadRequestModel.getDeclarationInfo().getApplicant_signature().contains("/")) {
            allImagesList.add(addLeadRequestModel.getDeclarationInfo().getApplicant_signature());
        }
        if (!TextUtils.isEmpty(addLeadRequestModel.getDeclarationInfo().getCoapplicant_signature()) &&
                addLeadRequestModel.getDeclarationInfo().getCoapplicant_signature().contains("/")) {
            allImagesList.add(addLeadRequestModel.getDeclarationInfo().getCoapplicant_signature());
        }
        if (!TextUtils.isEmpty(addLeadRequestModel.getDeclarationInfo().getEmployee_signature()) &&
                addLeadRequestModel.getDeclarationInfo().getEmployee_signature().contains("/")) {
            allImagesList.add(addLeadRequestModel.getDeclarationInfo().getEmployee_signature());
        }
        if (addLeadRequestModel.getKycInfoList() != null && addLeadRequestModel.getKycInfoList().size() > 0) {
            for (int i = 0; i < addLeadRequestModel.getKycInfoList().size(); i++) {
                if (addLeadRequestModel.getKycInfoList().get(i).getDocumentImages() != null &&
                        addLeadRequestModel.getKycInfoList().get(i).getDocumentImages().size() > 0) {
                    for (int j = 0; j < addLeadRequestModel.getKycInfoList().get(i).getDocumentImages().size(); j++) {
                        if (addLeadRequestModel.getKycInfoList().get(i).getDocumentImages().get(j).contains("/")) {
                            allImagesList.add(addLeadRequestModel.getKycInfoList().get(i).getDocumentImages().get(j));
                        }
                    }
                }
            }
        }
        return allImagesList;
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

    private void openBranchDialog() {
        Utils.hideKeyboard(getActivity());
        Utils.showAlertDialog(getActivity(),
                getString(R.string.select_branch),
                branchTitleList.toArray(new String[branchTitleList.size()]),
                onClickedBranchListner);
    }

    private void openMaritalStatusDialog() {
        Utils.hideKeyboard(getActivity());
        Utils.showAlertDialog(getActivity(),
                getString(R.string.select_marital_status),
                maritulStatusTitleList.toArray(new String[maritulStatusTitleList.size()]),
                onClickedMaritalListner);
    }

    private void openBankDialog() {
        Utils.hideKeyboard(getActivity());
        Utils.showAlertDialog(getActivity(),
                getString(R.string.select_bank),
                bankNameList.toArray(new String[bankNameList.size()]),
                onClickedBankListner);
    }

    private void openPurposeOfLoanDialog() {
        Utils.hideKeyboard(getActivity());
        Utils.showAlertDialog(getActivity(),
                getString(R.string.select_purpose_of_loan),
                purposeLoanTitleList.toArray(new String[purposeLoanTitleList.size()]),
                onClickedLoanPurposeListener);
    }

    private void openCoBankDialog() {
        Utils.hideKeyboard(getActivity());
        Utils.showAlertDialog(getActivity(),
                getString(R.string.select_cobank),
                bankNameList.toArray(new String[bankNameList.size()]),
                onClickedCoBankListner);
    }

//    private void openCompanyDialog() {
//        Utils.hideKeyboard(getActivity());
//        Utils.showAlertDialog(getActivity(),
//                getString(R.string.select_company_name),
//                companyTitleList.toArray(new String[companyTitleList.size()]),
//                onClickedCompanyListner);
//    }

    private void openReligionDialog() {
        Utils.hideKeyboard(getActivity());
        Utils.showAlertDialog(getActivity(),
                getString(R.string.select_religion),
                religionTitleList.toArray(new String[religionTitleList.size()]),
                onClickedReligionListner);
    }

    private void openUserAreaListDialog() {
        Utils.hideKeyboard(getActivity());
        Utils.showAlertDialog(getActivity(),
                getString(R.string.user_area_list),
                userArealist.toArray(new String[userArealist.size()]),
                onClickedUserArealistListner);
    }

    private void openPaymentCycleDialog() {
        Utils.hideKeyboard(getActivity());
        Utils.showAlertDialog(getActivity(),
                getString(R.string.select_payment_cycle),
                paymentCycleTitleList.toArray(new String[paymentCycleTitleList.size()]),
                onClickedPaymentListner);
    }

    private void openGroupDialog() {
        Utils.hideKeyboard(getActivity());
        Utils.showAlertDialog(getActivity(),
                getString(R.string.select_group),
                groupTitleList.toArray(new String[groupTitleList.size()]),
                onClickedGroupListner);
    }

    private void openCustomerTypeDialog() {
        Utils.hideKeyboard(getActivity());
        Utils.showAlertDialog(getActivity(),
                getString(R.string.select_customer_type),
                customerTypeTitleList.toArray(new String[customerTypeTitleList.size()]),
                onClickedCustomerTypeListner);
    }

    private void openProductDialog() {
        Utils.hideKeyboard(getActivity());
        Utils.showAlertDialog(getActivity(),
                getString(R.string.select_product),
                productTitleList.toArray(new String[productTitleList.size()]),
                onClickedProductListner);
    }

    private void openLivingSinceDialog(View view) {
        currentClickedView = view;
        Utils.hideKeyboard(getActivity());
        Utils.showAlertDialog(getActivity(),
                getString(R.string.living_since),
                yearList.toArray(new String[yearList.size()]),
                onClickedLivingListner);
    }

    // call to getBranchList service
    private void getBranchList() {
        if (ConnectionDetector.isNetAvail(getActivity())) {
            displayProgressBar(false, getActivity());
            getRestClient().callback(this).getBranchList();
        } else {
//            displayErrorDialog(getString(R.string.error_internet_connection));
        }
    }

    // call to getBranchList service
    private void getGroupList() {
        if (ConnectionDetector.isNetAvail(getActivity())) {
            // displayProgressBar(false, getActivity());
            String user_id = String.valueOf(getMyApplication().getUserPrefs().getLoggedInUser().getId());

            getRestClient().callback(this).getGroupList(user_id);
        } else {
//            displayErrorDialog(getString(R.string.error_internet_connection));
        }
    }

    // call to getProductList service
    private void getProductList() {
        if (ConnectionDetector.isNetAvail(getActivity())) {
            // displayProgressBar(false, getActivity());
            getRestClient().callback(this).getProductList();
        } else {
//            displayErrorDialog(getString(R.string.error_internet_connection));
        }
    }

    // call to getBranchList service
    private void getDynamicFieldsList() {
        if (ConnectionDetector.isNetAvail(getActivity())) {
            // displayProgressBar(false, getActivity());
            getRestClient().callback(this).getLeadFields();
        } else {
//            displayErrorDialog(getString(R.string.error_internet_connection));
        }
    }

    private String setZeroInt(String value) {
        return !TextUtils.isEmpty(value) ? value : "0";
    }

    // call to submitAppForm service
    private void submitAppForm() {
        if (ConnectionDetector.isNetAvail(getActivity())) {
            displayProgressBar(false, getActivity()); //todo set not cancellable
            addLeadRequestModel.getHouseHoldInfo().setHousehold_surplus(setZeroInt(addLeadRequestModel.getHouseHoldInfo().getHousehold_surplus()));
            addLeadRequestModel.getHouseHoldInfo().setHousehold_eduction(setZeroInt(addLeadRequestModel.getHouseHoldInfo().getHousehold_eduction()));
            addLeadRequestModel.getHouseHoldInfo().setHousehold_food_n_fule(setZeroInt(addLeadRequestModel.getHouseHoldInfo().getHousehold_food_n_fule()));
            addLeadRequestModel.getHouseHoldInfo().setHousehold_loan_detail(setZeroInt(addLeadRequestModel.getHouseHoldInfo().getHousehold_loan_detail()));
            addLeadRequestModel.getHouseHoldInfo().setHousehold_medical(setZeroInt(addLeadRequestModel.getHouseHoldInfo().getHousehold_medical()));
            addLeadRequestModel.getHouseHoldInfo().setHousehold_other(setZeroInt(addLeadRequestModel.getHouseHoldInfo().getHousehold_other()));
            addLeadRequestModel.getHouseHoldInfo().setHousehold_rent(setZeroInt(addLeadRequestModel.getHouseHoldInfo().getHousehold_rent()));
            addLeadRequestModel.getHouseHoldInfo().setHousehold_rent(setZeroInt(addLeadRequestModel.getHouseHoldInfo().getHousehold_rent()));

            addLeadRequestModel.getSavingsInfo().setAverage_bank(setZeroInt(addLeadRequestModel.getSavingsInfo().getAverage_bank()));
            addLeadRequestModel.getSavingsInfo().setAverage_chit_fund(setZeroInt(addLeadRequestModel.getSavingsInfo().getAverage_chit_fund()));
            addLeadRequestModel.getSavingsInfo().setAverage_finance_company(setZeroInt(addLeadRequestModel.getSavingsInfo().getAverage_finance_company()));
            addLeadRequestModel.getSavingsInfo().setAverage_insurance(setZeroInt(addLeadRequestModel.getSavingsInfo().getAverage_insurance()));
            addLeadRequestModel.getSavingsInfo().setAverage_monthly_saving(setZeroInt(addLeadRequestModel.getSavingsInfo().getAverage_monthly_saving()));
            addLeadRequestModel.getSavingsInfo().setAverage_other(setZeroInt(addLeadRequestModel.getSavingsInfo().getAverage_other()));
            addLeadRequestModel.getSavingsInfo().setAverage_post_office(setZeroInt(addLeadRequestModel.getSavingsInfo().getAverage_post_office()));
            addLeadRequestModel.getSavingsInfo().setAverage_surplus(setZeroInt(addLeadRequestModel.getSavingsInfo().getAverage_surplus()));
            for (int i = 0; i < addLeadRequestModel.getExistingLoanInfoList().size(); i++) {
                addLeadRequestModel.getExistingLoanInfoList().get(i).setEmi_amount(setZeroInt(addLeadRequestModel.getExistingLoanInfoList().get(i).getEmi_amount()));
                addLeadRequestModel.getExistingLoanInfoList().get(i).setLoan_amount(setZeroInt(addLeadRequestModel.getExistingLoanInfoList().get(i).getLoan_amount()));
            }
            for (int i = 0; i < addLeadRequestModel.getFamilyProfile().getFamilyProfileInfoList().size(); i++) {
                addLeadRequestModel.getFamilyProfile().getFamilyProfileInfoList().get(i).setMonthly_income(setZeroInt(addLeadRequestModel.getFamilyProfile().getFamilyProfileInfoList().get(i).getMonthly_income()));
            }

            getRestClient().callback(this).submitAppForm(addLeadRequestModel);
        } else {
            getAllLeadData().shouldUpdate = true;
            if (leadTable.isValueExist(leadTable.LOCAL_ID, local_id)) {
                leadTable.updateLeadRecord(local_id, getAllLeadData());
            } else
                leadTable.insert(getAllLeadData());
            displayErrorDialog(getString(R.string.data_saved_successfully));
            getActivity().onBackPressed();
        }
    }

    // call to editAppForm service
    private void editAppForm() {
        if (ConnectionDetector.isNetAvail(getActivity())) {
            displayProgressBar(false, getActivity());
            getRestClient().callback(this).editAppForm(addLeadRequestModel);
        } else {
            getAllLeadData().shouldUpdate = true;
            if (leadTable.isValueExist(leadTable.LOCAL_ID, local_id)) {
                leadTable.updateLeadRecord(local_id, getAllLeadData());
            } else
                leadTable.insert(getAllLeadData());
            displayErrorDialog(getString(R.string.data_saved_successfully));
            getActivity().onBackPressed();
        }
    }

    private void addChangePasswordFragment() {
        ChangePasswordFragment fragment = new ChangePasswordFragment();
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

    private AddLeadRequestModel.FamilyProfile getFamilyData() {
        AddLeadRequestModel.FamilyProfile familyProfile = new AddLeadRequestModel.FamilyProfile();
        familyProfile.setTotal_family_income(et_total_family_income.getText().toString());
        ArrayList<AddLeadRequestModel.FamilyProfile.FamilyProfileInfo> familyProfileInfoList = new ArrayList<>();
        if (ll_family_income_containor.getChildCount() > 0) {
            for (int i = 0; i < ll_family_income_containor.getChildCount(); i++) {
                AddLeadRequestModel.FamilyProfile.FamilyProfileInfo familyProfileInfo = new AddLeadRequestModel.FamilyProfile.FamilyProfileInfo();
                View view = ll_family_income_containor.getChildAt(i);
                EditText et_member_name = view.findViewById(R.id.et_member_name);
                EditText et_sex = view.findViewById(R.id.et_sex);
                EditText et_member_age = view.findViewById(R.id.et_member_age);
                EditText et_relationship = view.findViewById(R.id.et_relationship);
                EditText et_education = view.findViewById(R.id.et_education);
                EditText et_occupation = view.findViewById(R.id.et_occupation);
                EditText et_member_mobile = view.findViewById(R.id.et_member_mobile);
                EditText et_monthly_income = view.findViewById(R.id.et_monthly_income);
                CheckBox isCoapp = view.findViewById(R.id.cb_checked);

                String memberName = et_member_name.getText().toString();
                String sex = et_sex.getText().toString();
                String age = et_member_age.getText().toString();
                String relation = et_relationship.getText().toString();
                String education = et_education.getText().toString();
                String occupation = et_occupation.getText().toString();
                String mobile = et_member_mobile.getText().toString();
                String income = et_monthly_income.getText().toString();
                familyProfileInfo.setCoapp(isCoapp.isChecked());
                familyProfileInfo.setMember_name(memberName);
                familyProfileInfo.setAge(age);
                familyProfileInfo.setRelationship(relation);
                familyProfileInfo.setSex(sex);
                familyProfileInfo.setEducation(education);
                familyProfileInfo.setOccupation(occupation);
                familyProfileInfo.setMobile_number(mobile);
                familyProfileInfo.setMonthly_income(income);
                familyProfileInfoList.add(familyProfileInfo);
            }
        }
        familyProfile.setFamilyProfileInfoList(familyProfileInfoList);
        return familyProfile;
    }

    private void setFamilyData(AddLeadRequestModel leadRequestModel) {

        if (leadRequestModel.getFamilyProfile().getFamilyProfileInfoList() != null &&
                leadRequestModel.getFamilyProfile().getFamilyProfileInfoList().size() > 0) {
            ll_family_income_containor.removeAllViews();
            for (int i = 0; i < leadRequestModel.getFamilyProfile().getFamilyProfileInfoList().size(); i++) {
                View view = LayoutInflater.from(getActivity()).inflate(R.layout.layout_family_income_view, null);
                EditText et_member_name = view.findViewById(R.id.et_member_name);
                EditText et_sex = view.findViewById(R.id.et_sex);
                EditText et_member_age = view.findViewById(R.id.et_member_age);
                EditText et_relationship = view.findViewById(R.id.et_relationship);
                EditText et_education = view.findViewById(R.id.et_education);
                EditText et_occupation = view.findViewById(R.id.et_occupation);
                EditText et_member_mobile = view.findViewById(R.id.et_member_mobile);
                EditText et_monthly_income = view.findViewById(R.id.et_monthly_income);
                ImageView iv_remove_view = view.findViewById(R.id.iv_remove_view);
                ImageView iv_add_view = view.findViewById(R.id.iv_add_view);
                iv_remove_view.setTag(i);
                iv_add_view.setTag(i);
                iv_remove_view.setOnClickListener(onFamilyIncomeDeleteListener);
                iv_add_view.setOnClickListener(onFamilyIncomeAddListener);
                if (i > 0) {
                    iv_remove_view.setVisibility(View.VISIBLE);
                } else {
                    iv_remove_view.setVisibility(View.GONE);
                }
                if (i == leadRequestModel.getFamilyProfile().getFamilyProfileInfoList().size() - 1) {
                    iv_add_view.setVisibility(View.VISIBLE);
                } else {
                    iv_add_view.setVisibility(View.GONE);
                }
                AddLeadRequestModel.FamilyProfile.FamilyProfileInfo familyProfileInfo = leadRequestModel.getFamilyProfile().getFamilyProfileInfoList().get(i);
                et_member_name.setText(familyProfileInfo.getMember_name());
                et_sex.setText(familyProfileInfo.getSex());
                et_member_age.setText(familyProfileInfo.getAge());
                et_relationship.setText(familyProfileInfo.getRelationship());
                et_education.setText(familyProfileInfo.getEducation());
                et_occupation.setText(familyProfileInfo.getOccupation());
                et_member_mobile.setText(familyProfileInfo.getMobile_number());
                et_monthly_income.setText(familyProfileInfo.getMonthly_income());
                ll_family_income_containor.addView(view);
            }
        }
    }

    private ArrayList<AddLeadRequestModel.ExistingLoanInfo> getExistingLoanData() {

        ArrayList<AddLeadRequestModel.ExistingLoanInfo> existingLoanInfoList = new ArrayList<>();
        if (ll_existing_loan_containor.getChildCount() > 0) {
            for (int i = 0; i < ll_existing_loan_containor.getChildCount(); i++) {
                AddLeadRequestModel.ExistingLoanInfo existingLoanInfo = new AddLeadRequestModel.ExistingLoanInfo();
                View view = ll_existing_loan_containor.getChildAt(i);
                EditText et_company_name = view.findViewById(R.id.et_company_name);
                EditText et_loan_amount = view.findViewById(R.id.et_loan_amount);
                EditText et_tenure = view.findViewById(R.id.et_tenure);
                EditText et_emi_amount = view.findViewById(R.id.et_emi_amount);
                EditText et_installment_due = view.findViewById(R.id.et_installment_due);

                String companyName = et_company_name.getText().toString();
                String loanAmount = et_loan_amount.getText().toString();
                String tenure = et_tenure.getText().toString();
                String emi = et_emi_amount.getText().toString();
                String installDue = et_installment_due.getText().toString();

                existingLoanInfo.setCompany_name(companyName);
                existingLoanInfo.setEmi_amount(emi);
                existingLoanInfo.setLoan_amount(loanAmount);
                existingLoanInfo.setTenure(tenure);
                existingLoanInfo.setInstallment_due(installDue);
                existingLoanInfoList.add(existingLoanInfo);
            }
        }
        return existingLoanInfoList;
    }

    private void setExistingLoanData(AddLeadRequestModel leadRequestModel) {
        if (leadRequestModel.getExistingLoanInfoList() != null &&
                leadRequestModel.getExistingLoanInfoList().size() > 0) {
            ll_existing_loan_containor.removeAllViews();
            for (int i = 0; i < leadRequestModel.getExistingLoanInfoList().size(); i++) {
                View view = LayoutInflater.from(getActivity()).inflate(R.layout.layout_existing_loan_view, null);
                EditText et_company_name = view.findViewById(R.id.et_company_name);
                EditText et_loan_amount = view.findViewById(R.id.et_loan_amount);
                EditText et_tenure = view.findViewById(R.id.et_tenure);
                EditText et_emi_amount = view.findViewById(R.id.et_emi_amount);
                EditText et_installment_due = view.findViewById(R.id.et_installment_due);
                ImageView iv_remove_view = view.findViewById(R.id.iv_remove_view);
                ImageView iv_add_view = view.findViewById(R.id.iv_add_view);
                iv_remove_view.setTag(i);
                iv_add_view.setTag(i);
                iv_remove_view.setOnClickListener(onExistingLoanDeleteListener);
                iv_add_view.setOnClickListener(onExistingLoanAddListener);
                if (i > 0) {
                    iv_remove_view.setVisibility(View.VISIBLE);
                } else {
                    iv_remove_view.setVisibility(View.GONE);
                }

                if (i == leadRequestModel.getExistingLoanInfoList().size() - 1) {
                    iv_add_view.setVisibility(View.VISIBLE);
                } else {
                    iv_add_view.setVisibility(View.GONE);
                }
                AddLeadRequestModel.ExistingLoanInfo existingLoanInfo = leadRequestModel.getExistingLoanInfoList().get(i);
                et_company_name.setText(existingLoanInfo.getCompany_name());
                et_loan_amount.setText(existingLoanInfo.getLoan_amount());
                et_tenure.setText(existingLoanInfo.getTenure());
                et_emi_amount.setText(existingLoanInfo.getEmi_amount());
                et_installment_due.setText(existingLoanInfo.getInstallment_due());
                ll_existing_loan_containor.addView(view);
            }
        }
    }

    private AddLeadRequestModel.MonthlyHouseHoldInfo getHouseholdData() {
        AddLeadRequestModel.MonthlyHouseHoldInfo houseHoldInfo = new AddLeadRequestModel.MonthlyHouseHoldInfo();
        houseHoldInfo.setHousehold_rent(et_rent.getText().toString());
        houseHoldInfo.setHousehold_eduction(et_education_household.getText().toString());
        houseHoldInfo.setHousehold_food_n_fule(et_food_and_fuel.getText().toString());
        houseHoldInfo.setHousehold_loan_detail(et_loan_details.getText().toString());
        houseHoldInfo.setHousehold_medical(et_medical.getText().toString());
        houseHoldInfo.setHousehold_other(et_other_clothes_household.getText().toString());
        houseHoldInfo.setHousehold_surplus(et_total_monthly_expenditure.getText().toString());
        return houseHoldInfo;
    }

    private void setHouseholdData(AddLeadRequestModel leadRequestModel) {
        et_rent.setText(leadRequestModel.getHouseHoldInfo().getHousehold_rent());
        et_education_household.setText(leadRequestModel.getHouseHoldInfo().getHousehold_eduction());
        et_food_and_fuel.setText(leadRequestModel.getHouseHoldInfo().getHousehold_food_n_fule());
        et_loan_details.setText(leadRequestModel.getHouseHoldInfo().getHousehold_loan_detail());
        et_medical.setText(leadRequestModel.getHouseHoldInfo().getHousehold_medical());
        et_other_clothes_household.setText(leadRequestModel.getHouseHoldInfo().getHousehold_other());
        et_total_monthly_expenditure.setText(leadRequestModel.getHouseHoldInfo().getHousehold_surplus());
    }

    private AddLeadRequestModel.MonthlySavingsInfo getSavingsData() {
        AddLeadRequestModel.MonthlySavingsInfo savingsInfo = new AddLeadRequestModel.MonthlySavingsInfo();
        savingsInfo.setAverage_bank(et_bank.getText().toString());
        savingsInfo.setAverage_post_office(et_post_office.getText().toString());
        savingsInfo.setAverage_chit_fund(et_chit_fund.getText().toString());
        savingsInfo.setAverage_finance_company(et_finance_company.getText().toString());
        savingsInfo.setAverage_insurance(et_insurance_company.getText().toString());
        savingsInfo.setAverage_monthly_saving(et_total_monthly_savings.getText().toString());
        savingsInfo.setAverage_other(et_other_clothes.getText().toString());
        savingsInfo.setAverage_surplus(et_total_monthly_savings.getText().toString());

        return savingsInfo;
    }

    private void setSavingsData(AddLeadRequestModel leadRequestModel) {
        et_bank.setText(leadRequestModel.getSavingsInfo().getAverage_bank());
        et_post_office.setText(leadRequestModel.getSavingsInfo().getAverage_post_office());
        et_chit_fund.setText(leadRequestModel.getSavingsInfo().getAverage_chit_fund());
        et_finance_company.setText(leadRequestModel.getSavingsInfo().getAverage_finance_company());
        et_insurance_company.setText(leadRequestModel.getSavingsInfo().getAverage_insurance());
        et_total_monthly_savings.setText(leadRequestModel.getSavingsInfo().getAverage_monthly_saving());
        et_other_clothes.setText(leadRequestModel.getSavingsInfo().getAverage_other());
        et_total_monthly_savings.setText(leadRequestModel.getSavingsInfo().getAverage_surplus());
    }

    Toast toast;

    void showToast(String message) {
        if (toast != null)
            toast.cancel();
        toast = Toast.makeText(getActivity(), message, Toast.LENGTH_LONG);
        toast.show();
    }

    public boolean isValidString(String data) {
        return data != null && !data.trim().isEmpty();
    }

    private AddLeadRequestModel.LoanPersonelInfo getPersonalData() {
        AddLeadRequestModel.LoanPersonelInfo personelInfo = new AddLeadRequestModel.LoanPersonelInfo();
        String userId = String.valueOf(getMyApplication().getUserPrefs().getLoggedInUser().getId());
        personelInfo.setUser_id(userId);
        if (leadDataForEdit != null) {
            personelInfo.setLatitude(latitude);
            personelInfo.setLongitude(longitude);
        } else {
            personelInfo.setLatitude(getMyApplication().getAppPrefs().getCurrentLatitude());
            personelInfo.setLongitude(getMyApplication().getAppPrefs().getCurrentLongitude());
        }
        personelInfo.setBranche_id(String.valueOf(branch_id));
        personelInfo.setBranche_id_value(et_branch.getText().toString());
        personelInfo.setDob(et_dob.getText().toString());
        personelInfo.setEducation(et_education.getText().toString());
        personelInfo.setFather_name(et_father_name.getText().toString());
        personelInfo.setMother_name(et_mother_name.getText().toString());
        personelInfo.setLoan_amount(et_amount.getText().toString());
        personelInfo.setLoan_purpose(et_purpose_of_loan.getText().toString());
        personelInfo.setMarital_status(String.valueOf(marital_id));
        personelInfo.setMarital_status_value(et_maritul_status.getText().toString());
        personelInfo.setPayment_cycle(String.valueOf(payment_cycle_id));
        personelInfo.setPayment_cycle_value(et_payment_cycle.getText().toString());
        personelInfo.setReligion(String.valueOf(religion_id));
        personelInfo.setReligion_value(et_religion.getText().toString());
        personelInfo.setCurrent_address_area(et_current_address_area.getText().toString());
        personelInfo.setSpouse_name(et_husbands_name.getText().toString());
        personelInfo.setName(et_name.getText().toString());
        // personelInfo.setCo_aplicant_name(et_coapplicant_name.getText().toString());
        personelInfo.setCo_aplicant_name(co_aplicant_name);
        personelInfo.setK_number(et_k_number.getText().toString());
        personelInfo.setProduct_id(String.valueOf(product_id));
        personelInfo.setProduct_id_value(et_product.getText().toString());
        personelInfo.setGroup_id(String.valueOf(group_id));
        personelInfo.setGroup_id_value(et_group.getText().toString());
        personelInfo.setCustomer_id(String.valueOf(customer_id));
        personelInfo.setType_of_customer(String.valueOf(customer_type_id));
        personelInfo.setType_of_customer_value(et_type_of_customer.getText().toString());
        if (iv_applicant_photo1.getTag() != null) {
            personelInfo.setApplicant_photo1(iv_applicant_photo1.getTag().toString());
        }
        if (iv_applicant_photo2.getTag() != null) {
            personelInfo.setApplicant_photo2(iv_applicant_photo2.getTag().toString());
        }
        personelInfo.setEnquiry_id(enuiry_id);
        personelInfo.setCibil_status(cibil_status);
        personelInfo.setCo_aplicant_age(co_aplicant_age);
        personelInfo.setCo_aplicant_relation(co_aplicant_relation);

        Log.e(TAG, "getPersonalData: Sunil = co_aplicant_relation= " + co_aplicant_relation
                + "\nco_aplicant_age = " + co_aplicant_age + "\nco_aplicant_name = " + co_aplicant_name);

        return personelInfo;
    }

    String latitude, longitude;

    /*set All lead data here*/
    private void setPersonalData(AddLeadRequestModel leadResponseModel) {
        //et_emp_name.setText(leadResponseModel.getLoanPersonelInfo().getEmp_name());
        latitude = leadResponseModel.getLoanPersonelInfo().getLatitude();
        longitude = leadResponseModel.getLoanPersonelInfo().getLongitude();
        et_dob.setText(leadResponseModel.getLoanPersonelInfo().getDob());
        et_education.setText(leadResponseModel.getLoanPersonelInfo().getEducation());
        et_father_name.setText(leadResponseModel.getLoanPersonelInfo().getFather_name());
        et_mother_name.setText(leadResponseModel.getLoanPersonelInfo().getMother_name());
        et_amount.setText(leadResponseModel.getLoanPersonelInfo().getLoan_amount());
        et_purpose_of_loan.setText(leadResponseModel.getLoanPersonelInfo().getLoan_purpose());
        et_husbands_name.setText(leadResponseModel.getLoanPersonelInfo().getSpouse_name());
        et_name.setText(leadResponseModel.getLoanPersonelInfo().getName());
        et_coapplicant_name.setText(leadResponseModel.getLoanPersonelInfo().getCo_aplicant_name());
        et_k_number.setText(leadResponseModel.getLoanPersonelInfo().getK_number());
        //et_date.setText(leadResponseModel.getLoanPersonelInfo().getDate());
        et_branch.setText(leadResponseModel.getLoanPersonelInfo().getBranche_id_value());
        et_type_of_customer.setText(leadResponseModel.getLoanPersonelInfo().getType_of_customer_value());
        et_payment_cycle.setText(leadResponseModel.getLoanPersonelInfo().getPayment_cycle_value());
        et_group.setText(leadResponseModel.getLoanPersonelInfo().getGroup_id_value());
        et_product.setText(leadResponseModel.getLoanPersonelInfo().getProduct_id_value());
        et_religion.setText(leadResponseModel.getLoanPersonelInfo().getReligion_value());
        et_current_address_area.setText((!TextUtils.isEmpty(leadResponseModel.getLoanPersonelInfo().getCurrent_address_area()) && leadResponseModel.getLoanPersonelInfo().getCurrent_address_area() != null) ? leadResponseModel.getLoanPersonelInfo().getCurrent_address_area() : "");
        et_maritul_status.setText(leadResponseModel.getLoanPersonelInfo().getMarital_status_value());
        branch_id = checkValidInt(leadResponseModel.getLoanPersonelInfo().getBranche_id());
        customer_type_id = checkValidInt(leadResponseModel.getLoanPersonelInfo().getType_of_customer());
        payment_cycle_id = checkValidInt(leadResponseModel.getLoanPersonelInfo().getPayment_cycle());
        group_id = checkValidInt(leadResponseModel.getLoanPersonelInfo().getGroup_id());
        product_id = checkValidInt(leadResponseModel.getLoanPersonelInfo().getProduct_id());
        religion_id = checkValidInt(leadResponseModel.getLoanPersonelInfo().getReligion());
        marital_id = checkValidInt(leadResponseModel.getLoanPersonelInfo().getMarital_status());
        customer_id = leadResponseModel.getLoanPersonelInfo().getCustomer_id();


        if (!TextUtils.isEmpty(leadResponseModel.getLoanPersonelInfo().getApplicant_photo1())) {
            iv_applicant_photo1.setTag(leadResponseModel.getLoanPersonelInfo().getApplicant_photo1());
               /* if (leadResponseModel.getLoanPersonelInfo().getApplicant_photo1().contains("/")) {
                    iv_applicant_photo2.setImageBitmap(getBitmapFromPath(leadResponseModel.getLoanPersonelInfo().getApplicant_photo2()));
                }*/
            Picasso.with(getActivity()).
                    load(getImageActualPath(leadResponseModel.getLoanPersonelInfo().getApplicant_photo1())).
                    error(R.mipmap.error_default).
                    placeholder(R.mipmap.loading_default).
                    into(iv_applicant_photo1);

        } else {
            iv_applicant_photo1.setTag("");
            iv_applicant_photo1.setImageResource(R.mipmap.default_image);
        }
        if (!TextUtils.isEmpty(leadResponseModel.getLoanPersonelInfo().getApplicant_photo2())) {
            iv_applicant_photo2.setTag(leadResponseModel.getLoanPersonelInfo().getApplicant_photo2());
            Picasso.with(getActivity()).
                    load(getImageActualPath(leadResponseModel.getLoanPersonelInfo().getApplicant_photo2())).
                    error(R.mipmap.error_default).
                    placeholder(R.mipmap.loading_default).
                    into(iv_applicant_photo2);
        } else {
            iv_applicant_photo2.setTag("");
            iv_applicant_photo2.setImageResource(R.mipmap.default_image);
        }
    }

    private AddLeadRequestModel.AddressInfo getAddressData() {
        AddLeadRequestModel.AddressInfo addressInfo = new AddLeadRequestModel.AddressInfo();
        //addressInfo.setCurrent_address(et_current_address.getText().toString());
        addressInfo.setAddress(et_colony_name.getText().toString());
        addressInfo.setArea(et_current_address_area.getText().toString());
        if (UserAreaList != null) {
            for (int k = 0; k < UserAreaList.size(); k++) {
                if (UserAreaList.get(k).getArea().equalsIgnoreCase(et_current_address_area.getText().toString().trim()))
                    addressInfo.setArea_id(String.valueOf(UserAreaList.get(k).getId()));
            }
        }

        addressInfo.setCity(et_city.getText().toString());
        addressInfo.setLandline_contact(et_landline.getText().toString());
        addressInfo.setPincode(et_pincode.getText().toString());
        addressInfo.setLandmark(et_landmark.getText().toString());
        addressInfo.setMobile_contact(et_mobile.getText().toString());
        addressInfo.setLiving_since(et_living_since.getText().toString());
        addressInfo.setPermanent_address_other(et_permanent_address.getText().toString());
        addressInfo.setPermanant_address_area(getEt_permanent_address_area.getText().toString());
        addressInfo.setPermanent_address(et_permanent_address.getText().toString());
        addressInfo.setPermanent_area(getEt_permanent_address_area.getText().toString());
        addressInfo.setPermanant_city(et_permant_city.getText().toString());
        for (int k = 0; k < UserAreaList.size(); k++) {
            if (UserAreaList.get(k).getArea().equalsIgnoreCase(getEt_permanent_address_area.getText().toString().trim()))
                addressInfo.setPermanent_area_id(String.valueOf(UserAreaList.get(k).getId()));
        }
        addressInfo.setPermanent_landmark(et_permanent_landmark.getText().toString());
        addressInfo.setPermanent_pincode(et_permanent_pincode.getText().toString());
        addressInfo.setPermanent_phone(et_permanent_mobile.getText().toString());
        addressInfo.setPermanent_landline(et_permanent_landline.getText().toString());
        addressInfo.setPermanent_living_since(et_permanent_living_since.getText().toString());
        addressInfo.setOffice_address(et_office_address.getText().toString());
        addressInfo.setOffice_landmark(et_office_landmark.getText().toString());
        addressInfo.setOffice_pincode(et_office_pincode.getText().toString());
        addressInfo.setWorking_since(et_working_since.getText().toString());

        String currentHouseType = getRadioButtonValue(rg_current_house_type);
        if (currentHouseType.equalsIgnoreCase(getString(R.string.owned))) {
            addressInfo.setHome_status("1");
        } else if (currentHouseType.equalsIgnoreCase(getString(R.string.rented))) {
            addressInfo.setHome_status("2");
        } else {
            addressInfo.setHome_status("");
        }

        String permanentHouseType = getRadioButtonValue(rg_permanent_house_type);
        if (permanentHouseType.equalsIgnoreCase(getString(R.string.owned))) {
            addressInfo.setPermanent_home_status("1");
        } else if (permanentHouseType.equalsIgnoreCase(getString(R.string.rented))) {
            addressInfo.setPermanent_home_status("2");
        } else {
            addressInfo.setPermanent_home_status("");
        }
       /* addressInfo.setVillage(et_village.getText().toString());
        addressInfo.setMakan_number(et_makan_number.getText().toString());*/

        return addressInfo;
    }

    private void setAddressData(AddLeadRequestModel leadResponseModel) {
        et_current_address_area.setText(leadResponseModel.getAddressInfo().getArea());
        if (UserAreaList != null) {
            for (int k = 0; k < UserAreaList.size(); k++) {
                if (UserAreaList.get(k).getArea().equalsIgnoreCase(et_current_address_area.getText().toString().trim()))
                    leadResponseModel.getAddressInfo().setArea_id(String.valueOf(UserAreaList.get(k).getId()));
            }
        }
        getEt_permanent_address_area.setText(leadResponseModel.getAddressInfo().getPermanent_area());

        for (int k = 0; k < UserAreaList.size(); k++) {
            if (UserAreaList.get(k).getArea().equalsIgnoreCase(getEt_permanent_address_area.getText().toString().trim()))
                leadResponseModel.getAddressInfo().setPermanent_area_id(String.valueOf(UserAreaList.get(k).getId()));
        }
        et_colony_name.setText(leadResponseModel.getAddressInfo().getAddress());
        et_city.setText(leadResponseModel.getAddressInfo().getCity());
        et_landline.setText(leadResponseModel.getAddressInfo().getLandline_contact());
        et_pincode.setText(leadResponseModel.getAddressInfo().getPincode());
        et_landmark.setText(leadResponseModel.getAddressInfo().getLandmark());
        et_mobile.setText(leadResponseModel.getAddressInfo().getMobile_contact());
        et_living_since.setText(leadResponseModel.getAddressInfo().getLiving_since());
        et_permanent_address.setText(leadResponseModel.getAddressInfo().getPermanent_address_other());
        et_permant_city.setText(leadResponseModel.getAddressInfo().getPermanant_city());
//        getEt_permanent_address_area.setText(!TextUtils.isEmpty(leadResponseModel.getAddressInfo().getPermanant_address_area()) ? leadResponseModel.getAddressInfo().getPermanant_address_area() : "");
        et_permanent_landmark.setText(leadResponseModel.getAddressInfo().getPermanent_landmark());
        et_permanent_pincode.setText(leadResponseModel.getAddressInfo().getPermanent_pincode());
        et_permanent_landline.setText(leadResponseModel.getAddressInfo().getPermanent_landline());
        et_permanent_mobile.setText(leadResponseModel.getAddressInfo().getPermanent_phone());
        et_permanent_living_since.setText(leadResponseModel.getAddressInfo().getLiving_since());
        et_office_address.setText(leadResponseModel.getAddressInfo().getOffice_address());
        et_office_landmark.setText(leadResponseModel.getAddressInfo().getOffice_landmark());
        et_office_pincode.setText(leadResponseModel.getAddressInfo().getOffice_pincode());
        et_working_since.setText(leadResponseModel.getAddressInfo().getWorking_since());
        setCheckedRadioButton(rg_current_house_type, leadResponseModel.getAddressInfo().getHome_status());
        setCheckedRadioButton(rg_permanent_house_type, leadResponseModel.getAddressInfo().getPermanent_home_status());
        /*if (leadResponseModel.getAddressInfo().getHome_status().equalsIgnoreCase("1")) {
            rb_owned.setChecked(true);
        } else {
            rb_rented.setChecked(true);
        }

        if (leadResponseModel.getAddressInfo().getPermanent_home_status().equalsIgnoreCase("1")) {
            rb_permanent_owned.setChecked(true);
        } else {
            rb_permanent_rented.setChecked(true);
        }*/
    }

    private ArrayList<AddLeadRequestModel.ChequeInfo> getChequeData() {
        ArrayList<AddLeadRequestModel.ChequeInfo> chequeInfoList = new ArrayList<>();
        AddLeadRequestModel.ChequeInfo chequeInfoApplicant = new AddLeadRequestModel.ChequeInfo();
        AddLeadRequestModel.ChequeInfo chequeInfoCoApplicant = new AddLeadRequestModel.ChequeInfo();
        chequeInfoApplicant.setDetaills_of("1");
        chequeInfoApplicant.setBank_name(et_applicant_bank_name.getText().toString());
        chequeInfoApplicant.setBranch_name(et_applicant_branch.getText().toString());
        chequeInfoApplicant.setCheque_number(et_applicant_cheque_no.getText().toString());
        chequeInfoList.add(chequeInfoApplicant);
        chequeInfoCoApplicant.setDetaills_of("2");
        chequeInfoCoApplicant.setBank_name(et_coapplicant_bank_name.getText().toString());
        chequeInfoCoApplicant.setBranch_name(et_coapplicant_branch.getText().toString());
        chequeInfoCoApplicant.setCheque_number(et_coapplicant_cheque_no.getText().toString());
        chequeInfoList.add(chequeInfoCoApplicant);
        return chequeInfoList;
    }

    private void setChequeData(AddLeadRequestModel leadRequestModel) {
        et_applicant_bank_name.setText(leadRequestModel.getChequeInfo().get(0).getBank_name());
        et_applicant_branch.setText(leadRequestModel.getChequeInfo().get(0).getBranch_name());
        et_applicant_cheque_no.setText(leadRequestModel.getChequeInfo().get(0).getCheque_number());
        et_coapplicant_bank_name.setText(leadRequestModel.getChequeInfo().get(1).getBank_name());
        et_coapplicant_branch.setText(leadRequestModel.getChequeInfo().get(1).getBranch_name());
        et_coapplicant_cheque_no.setText(leadRequestModel.getChequeInfo().get(1).getCheque_number());

    }

    private boolean documentNumAvial() {
        for (int i = 0; i < documentItemLayout.getChildCount(); i++) {
            View view = documentItemLayout.getChildAt(i);
            EditText documentNum = view.findViewById(R.id.documentNumber);
            return !TextUtils.isEmpty(documentNum.getText().toString().trim());
        }
        return false;
    }

    private ArrayList<AddLeadRequestModel.KycInfo> getKycData() {
        ArrayList<AddLeadRequestModel.KycInfo> documents = new ArrayList<>();

        final int childCount = documentItemLayout.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View view = documentItemLayout.getChildAt(i);
            AddLeadRequestModel.KycInfo documentItem = getDocumentFromView(view);
            if (documentItem != null) {
                documents.add(documentItem);
            }
        }
        if (img_housephoto.getTag() != null) {
            AddLeadRequestModel.KycInfo item = new AddLeadRequestModel.KycInfo();
            item.setUserType("1");
            item.setDocumentName("House Photo");
            item.setDocumentId("0");
            item.setDocumentNumber("");
            ArrayList<String> mImages = new ArrayList<>();
            mImages.add((img_housephoto.getTag()).toString());
            item.setDocumentImages(mImages);
            documents.add(item);
        }
        return documents;
    }

    private void setKycData(AddLeadRequestModel leadRequestModel) {
        documentItemLayout.removeAllViews();
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        for (AddLeadRequestModel.KycInfo item : leadRequestModel.getKycInfoList()) {
            if (item.getDocumentName() == null && item.getDocumentImages() != null) {
                img_housephoto.setTag(item.getDocumentImages().get(0));
                Picasso.with(getActivity())
                        .load(getImageActualPath(item.getDocumentImages().get(0)))
                        .placeholder(R.mipmap.loading_default)
                        .error(R.mipmap.error_default)
                        .into(img_housephoto);
            } else
                addNewDocumentToLayout(item, inflater, item.getDocumentName(), 0);

        }

    }

    private AddLeadRequestModel.DeclarationInfo getDeclarationData() {
        AddLeadRequestModel.DeclarationInfo declarationInfo = new AddLeadRequestModel.DeclarationInfo();
        if (iv_applicant_signature.getTag() != null) {
            declarationInfo.setApplicant_signature(iv_applicant_signature.getTag().toString());
        }
        if (iv_coapplicant_signature.getTag() != null) {
            declarationInfo.setCoapplicant_signature(iv_coapplicant_signature.getTag().toString());
        }
        if (iv_employee_signature.getTag() != null) {
            declarationInfo.setEmployee_signature(iv_employee_signature.getTag().toString());
        }

        return declarationInfo;
    }

    private void setDeclarationData(AddLeadRequestModel leadRequestModel) {
        if (!TextUtils.isEmpty(leadRequestModel.getDeclarationInfo().getApplicant_signature())) {
            iv_applicant_signature.setTag(leadRequestModel.getDeclarationInfo().getApplicant_signature());
            Picasso.with(getActivity())
                    .load(getImageActualPath(leadRequestModel.getDeclarationInfo().getApplicant_signature()))
                    .placeholder(R.mipmap.loading_default)
                    .error(R.mipmap.error_default)
                    .into(iv_applicant_signature);

        } else {
            iv_applicant_signature.setTag("");
            iv_applicant_signature.setImageResource(R.mipmap.default_image);
        }
        if (!TextUtils.isEmpty(leadRequestModel.getDeclarationInfo().getCoapplicant_signature())) {
            iv_coapplicant_signature.setTag(leadRequestModel.getDeclarationInfo().getCoapplicant_signature());
            Picasso.with(getActivity())
                    .load(getImageActualPath(leadRequestModel.getDeclarationInfo().getCoapplicant_signature()))
                    .placeholder(R.mipmap.loading_default)
                    .error(R.mipmap.error_default)
                    .into(iv_coapplicant_signature);

        } else {
            iv_coapplicant_signature.setTag("");
            iv_coapplicant_signature.setImageResource(R.mipmap.default_image);
        }
        if (!TextUtils.isEmpty(leadRequestModel.getDeclarationInfo().getEmployee_signature())) {
            iv_employee_signature.setTag(leadRequestModel.getDeclarationInfo().getEmployee_signature());

            Picasso.with(getActivity())
                    .load(getImageActualPath(leadRequestModel.getDeclarationInfo().getEmployee_signature()))
                    .placeholder(R.mipmap.loading_default)
                    .error(R.mipmap.error_default)
                    .into(iv_employee_signature);

        } else {
            iv_employee_signature.setTag("");
            iv_employee_signature.setImageResource(R.mipmap.default_image);
        }

    }

    public void setTabVisibility(TextView textView, LinearLayout view) {
        if (textView == null || view == null)
            return;
        textView.setTextColor(getResources().getColor(R.color.colorAccent));
        textView.setBackgroundColor(getResources().getColor(R.color.colorWhite));
        view.setVisibility(View.VISIBLE);

        for (int i = 0; i < tabArr.length; i++) {
            setTAB(i, textView, view);
        }
    }

    void setTAB(int i, TextView textView, LinearLayout view) {
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

    private void addFamilyIncomeView() {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        final View view = inflater.inflate(R.layout.layout_family_income_view, null);
        ImageView iv_add_view = view.findViewById(R.id.iv_add_view);
        ImageView iv_remove_view = view.findViewById(R.id.iv_remove_view);
        EditText et_sex = view.findViewById(R.id.et_sex);
        final EditText et_member_name = view.findViewById(R.id.et_member_name);
        final EditText et_relationship = view.findViewById(R.id.et_relationship);
        final EditText et_member_age = view.findViewById(R.id.et_member_age);
        EditText et_monthly_income = view.findViewById(R.id.et_monthly_income);
        EditText et_member_mobile = view.findViewById(R.id.et_member_mobile);
        iv_remove_view.setTag(ll_family_income_containor.getChildCount());
        et_sex.setTag(ll_family_income_containor.getChildCount());
        et_monthly_income.setTag(ll_family_income_containor.getChildCount());
        et_monthly_income.addTextChangedListener(this);

        if (et_relationship.getText().toString() != null && et_relationship.getText().toString().equalsIgnoreCase("Self")) {
            et_monthly_income.setCompoundDrawablesWithIntrinsicBounds(R.drawable.star, 0, 0, 0);
            et_member_mobile.setCompoundDrawablesWithIntrinsicBounds(R.drawable.star, 0, 0, 0);
        } else {
            et_monthly_income.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            et_member_mobile.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        }

        ll_family_income_containor.addView(view);
        iv_remove_view.setOnClickListener(onFamilyIncomeDeleteListener);
        iv_add_view.setOnClickListener(onFamilyIncomeAddListener);
        et_sex.setOnClickListener(onSexClickListener);
        final CheckBox cb_checked = view.findViewById(R.id.cb_checked);
        cb_checked.setTag(ll_family_income_containor.getChildCount());
        final int value = ll_family_income_containor.getChildCount();
        Log.e("checked_value", "" + ll_family_income_containor.getChildCount());
        final DialogInterface.OnClickListener onClickedRelationListner = new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                et_relationship.setText("" + relationShipArray.toArray(new String[relationShipArray.size()])[item]);
            }
        };
        et_relationship.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.hideKeyboard(getActivity());
                Utils.showAlertDialog(getActivity(),
                        getString(R.string.select_relation),
                        relationShipArray.toArray(new String[relationShipArray.size()]),
                        onClickedRelationListner);
            }
        });

        cb_checked.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (ll_family_income_containor.getChildCount() > 1) {
                        for (int i = 0; i < ll_family_income_containor.getChildCount(); i++) {
                            View view = ll_family_income_containor.getChildAt(i);
                            CheckBox cb_checked1 = view.findViewById(R.id.cb_checked);
                            if (value != i + 1) {
                                if (cb_checked1.isChecked()) {
                                    cb_checked.setChecked(false);
                                    Toast.makeText(getActivity(), "Remove another checked value", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                if (cb_checked1.isChecked()) {
                                    EditText et_member_name1 = view.findViewById(R.id.et_member_name);
                                    EditText et_member_age1 = view.findViewById(R.id.et_member_age);
                                    EditText et_relationship1 = view.findViewById(R.id.et_relationship);

                                    co_aplicant_name = et_member_name1.getText().toString().trim();
                                    co_aplicant_age = et_member_age1.getText().toString().trim();
                                    co_aplicant_relation = et_relationship1.getText().toString().trim();
                                }
                            }
                        }
                    } else {
                        co_aplicant_name = et_member_name.getText().toString().trim();
                        co_aplicant_age = et_member_age.getText().toString().trim();
                        co_aplicant_relation = et_relationship.getText().toString().trim();
                    }
                }
            }
        });
        setCoapplicantCheckVisibility();
    }

    private void setCoapplicantCheckVisibility() {
        View view1 = ll_family_income_containor.getChildAt(0);
        LinearLayout ll_coapplicant_check = view1.findViewById(R.id.ll_coapplicant_check);
        ll_coapplicant_check.setVisibility(View.GONE);
    }

    private void onRefreshFamilyIncome() {
        for (int i = 0; i < ll_family_income_containor.getChildCount(); i++) {
            View view = ll_family_income_containor.getChildAt(i);
            ImageView iv_remove_view = view.findViewById(R.id.iv_remove_view);
            ImageView iv_add_view = view.findViewById(R.id.iv_add_view);
            iv_remove_view.setTag(i);
            iv_remove_view.setVisibility(View.VISIBLE);
            if (i == 0) {
                iv_remove_view.setVisibility(View.GONE);
            }
            if (i == ll_family_income_containor.getChildCount() - 1) {
                iv_add_view.setVisibility(View.VISIBLE);
            } else {
                iv_add_view.setVisibility(View.GONE);
            }
        }
        setTotalFamilyAmount();
    }





    /*Upload Kyc*/

    private void addExistingLoanView() {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View view = inflater.inflate(R.layout.layout_existing_loan_view, null);
        ImageView iv_add_view = view.findViewById(R.id.iv_add_view);
        ImageView iv_remove_view = view.findViewById(R.id.iv_remove_view);
        final EditText et_company_name = view.findViewById(R.id.et_company_name);

        iv_remove_view.setTag(ll_existing_loan_containor.getChildCount());
        ll_existing_loan_containor.addView(view);
        iv_remove_view.setOnClickListener(onExistingLoanDeleteListener);
        iv_add_view.setOnClickListener(onExistingLoanAddListener);
        final DialogInterface.OnClickListener onClickedCompanyListner = new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
//            marital_id = bankNameList.get(item).getId();
                et_company_name.setText("" + companyTitleList.toArray(new String[companyTitleList.size()])[item]);
            }
        };
        et_company_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (companyTitleList != null && companyTitleList.size() > 0) {

                    Utils.hideKeyboard(getActivity());
                    Utils.showAlertDialog(getActivity(),
                            getString(R.string.select_company_name),
                            companyTitleList.toArray(new String[companyTitleList.size()]),
                            onClickedCompanyListner);
                } else {
                    displayToast(getString(R.string.no_option_available));
                }
            }
        });
    }

    private void onRefreshExistingLoan() {
        for (int i = 0; i < ll_existing_loan_containor.getChildCount(); i++) {
            View view = ll_existing_loan_containor.getChildAt(i);
            ImageView iv_remove_view = view.findViewById(R.id.iv_remove_view);
            ImageView iv_add_view = view.findViewById(R.id.iv_add_view);
            iv_remove_view.setTag(i);
            iv_remove_view.setVisibility(View.VISIBLE);
            if (i == 0) {
                iv_remove_view.setVisibility(View.GONE);
            }
            if (i == ll_existing_loan_containor.getChildCount() - 1) {
                iv_add_view.setVisibility(View.VISIBLE);
            } else {
                iv_add_view.setVisibility(View.GONE);
            }
        }
    }

    private boolean isDocumentViewAlreadyShown(String documentName) {
        final int childCount = documentItemLayout.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View view = documentItemLayout.getChildAt(i);
            String name = ((TextView) view.findViewById(R.id.documentName)).getText().toString();
            if (documentName.equals(name)) {
                return true;
            }
        }
        return false;
    }

    private void addNewDocumentToLayout(AddLeadRequestModel.KycInfo item,
                                        LayoutInflater inflater,
                                        final String documentName, long kyc_doc_id) {

        final View view = inflater.inflate(R.layout.layout_kyc_upload_item, null);
        EditText documentNum = view.findViewById(R.id.documentNumber);
        TextView documentNameTV = view.findViewById(R.id.documentName);

        final ImageView iv_add_view = view.findViewById(R.id.iv_add_view);
        ImageView iv_remove_view = view.findViewById(R.id.iv_remove_view);
        final LinearLayout businessLayout = view.findViewById(R.id.business);
        RadioGroup rg_userType = view.findViewById(R.id.rg_usertype);
        RadioButton applicant = view.findViewById(R.id.applicant);
        RadioButton co_applicant = view.findViewById(R.id.co_applicant);
        RadioButton others = view.findViewById(R.id.other);

        boolean isShow = true;
        if (documentName.equalsIgnoreCase("House Photo")) {
            co_applicant.setVisibility(View.GONE);
            others.setVisibility(View.GONE);
            documentNum.setVisibility(View.GONE);
            isShow = false;
        }

        final boolean finalIsShow = isShow;
        iv_add_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (businessLayout.getChildCount() < 3) {
                    addImageLayout(businessLayout, null, getActivity(), businessLayout.getChildCount(), finalIsShow);

                } else {
                    displayToast(getString(R.string.you_cant_add_more_than_two_images));
                }
            }
        });
        if (item != null) {
            documentNameTV.setText(item.getDocumentName());
            documentNum.setText(item.getDocumentNumber());
            if (item.getUserType() == "1")
                applicant.setChecked(true);
            else if (item.getUserType() == "2")
                co_applicant.setChecked(true);
            else
                others.setChecked(true);
            if (item.getDocumentImages() != null && item.getDocumentImages().size() > 0)
                for (String image : item.getDocumentImages()) {
                    addImageLayout(businessLayout, image, getActivity(), businessLayout.getChildCount(), isShow);
                   /* if (leadDataForEdit != null && leadDataForEdit.isLocalLead() && image.contains("/")) {

                    } else {
                        addImageLayout(businessLayout, null, getActivity());
                    }*/

                }
            view.setTag(item);
            documentNameTV.setTag(item.getDocumentId());
        } else if (!TextUtils.isEmpty(documentName)) {
            documentNameTV.setText(documentName);
            documentNameTV.setTag(kyc_doc_id);
            Log.e(TAG, "addNewDocumentToLayout: " + kyc_doc_id);
        }


        iv_remove_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.showMessageDialogWithListener(getActivity(), getString(R.string.delete_view),
                        R.string.confirm, R.string.cancel, new Utils.DialogPositiveButtonClickListener() {
                            @Override
                            public void onPositiveClick(DialogInterface dialog) {
                                documentItemLayout.removeView(view);
                            }
                        }, new Utils.DialogNegativeButtonClickListener() {
                            @Override
                            public void onNegativeClick(DialogInterface dialog) {
                                dialog.dismiss();
                            }
                        });

            }
        });
        documentItemLayout.addView(view);
    }

    public void addImageLayout(LinearLayout layout,
                               String imagePath,
                               Context context,
                               int count, boolean isShow) {
        int childCount = layout.getChildCount();
        View view = LayoutInflater.from(context).inflate(R.layout.image_layout, null);
        final ImageView imageView = view.findViewById(R.id.displayImage);
        AppCompatTextView txt_forDocument = view.findViewById(R.id.txt_forDocument);


        imageView.setOnClickListener(imageClickListener);
        if (!TextUtils.isEmpty(imagePath)) {
            imageView.setTag(imagePath);
            imageView.setVisibility(View.VISIBLE);
            Picasso.with(getActivity())
                    .load(getImageActualPath(imagePath))
                    .placeholder(R.mipmap.loading_default)
                    .error(R.mipmap.error_default)
                    .into(imageView);
        } else {
            imageView.setTag("");
            imageView.setVisibility(View.VISIBLE);
        }
        layout.addView(view, childCount - 1);
    }

    private AddLeadRequestModel.KycInfo getDocumentFromView(View view) {
        AddLeadRequestModel.KycInfo item = new AddLeadRequestModel.KycInfo();
        EditText documentNum = view.findViewById(R.id.documentNumber);
        TextView documentNameTV = view.findViewById(R.id.documentName);
        LinearLayout businessLayout = view.findViewById(R.id.business);
        RadioGroup rg_userType = view.findViewById(R.id.rg_usertype);
        RadioButton applicant = view.findViewById(R.id.applicant);
        RadioButton co_applicant = view.findViewById(R.id.co_applicant);
        RadioButton others = view.findViewById(R.id.other);

        final String name = documentNameTV.getText().toString().trim();
        final String num = documentNum.getText().toString().trim();
        ArrayList<String> mImages = new ArrayList<>();
        int frontChildCount = businessLayout.getChildCount();
//        if (rg_userType.getVisibility() == View.VISIBLE)
        if (rg_userType.getCheckedRadioButtonId() == R.id.applicant) {
            item.setUserType("1");
        } else if (rg_userType.getCheckedRadioButtonId() == R.id.co_applicant) {
            item.setUserType("2");
        } else
            item.setUserType("3");
        for (int i = 0; i < frontChildCount; i++) {
            View v = businessLayout.getChildAt(i);
            if (v instanceof RelativeLayout) {
                String imagePath = (String) v.findViewById(R.id.displayImage).getTag();
                if (!TextUtils.isEmpty(imagePath)) {
                    mImages.add(imagePath);
                }
            }
        }

        item.setDocumentImages(mImages);
        item.setDocumentName(name);
        item.setDocumentNumber(num);
        int doc_id = Integer.parseInt(documentNameTV.getTag().toString());
        item.setDocumentId(String.valueOf(doc_id));
        return item;
    }

    // open date picker dialog
    private void openDatePicker(boolean isMinimumDate) {
        calendar = Calendar.getInstance();
        @SuppressLint("WrongConstant") int year = calendar.get(Calendar.YEAR) - 18;
        dpd = DatePickerDialog.newInstance(
                ApplicationFormFragment.this,
                //  calendar.get(Calendar.YEAR), // comment by imran
                year,  // add by imran
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
        dpd.show(getActivity().getFragmentManager(), "Datepickerdialog");
        calendar.setTimeInMillis(System.currentTimeMillis() - 1000);
        if (isMinimumDate) {
            dpd.setMinDate(calendar);
        } else {
            Calendar calendar1 = Calendar.getInstance();
            calendar1.add(Calendar.YEAR, -73);
            dpd.setMinDate(calendar1);
            calendar.add(Calendar.YEAR, -18);

            dpd.setMaxDate(calendar);
        }
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
//        selected_date = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
        selected_date = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
        if (isDob) {
            et_dob.setText(selected_date);
        } else {
            //  et_date.setText(selected_date);
        }
    }

    private void openPicker(final View view) {
        ImagePickDialog picDialog = ImagePickDialog.getNewInstance(false);
        picDialog.setProfilePicDialogListner(new ImagePickDialog.ProfilePicDialogListner() {
            @Override
            public void onProfilePicSelected(FileInformation fileInformation) {
                Log.e(TAG, "onProfilePicSelected: " + fileInformation.getFilePath());
                String imagePath = fileInformation.getBitmapPathForUpload(getActivity());
                Bitmap bitmap = fileInformation.getThumbBitmap(getActivity());
                ((ImageView) view).setImageBitmap(bitmap);
                view.setTag(imagePath);
            }

            @Override
            public void onProfilePicRemoved() {

            }
        });
        picDialog.show(getChildFm(), picDialog.getClass().getSimpleName());
    }


    public void savePicture(View view, Bitmap bitmap, String imagePath) {

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
                    if (leadTable.isValueExist(leadTable.LOCAL_ID, local_id)) {
                        leadTable.updateLeadRecord(local_id, getAllLeadData());
                    }
                    getActivity().onBackPressed();

                }
            });
            tv_no.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    leadTable.delete(local_id);
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

    public void getDataOffline() {
        if (MyApplication.getInstance().getAppPrefs().getUserAreaList() != null) {
            dismissProgressBar(getActivity());
            UserArea responseModel = MyApplication.getInstance().getAppPrefs().getUserAreaList();
            UserAreaList = responseModel.getData();
            userArealist = new ArrayList<>();
            if (responseModel != null && responseModel.getData() != null) {
                for (int i = 0; i < responseModel.getData().size(); i++) {
                    userArealist.add(responseModel.getData().get(i).getArea());
                }
            }
        }
        if (MyApplication.getInstance().getAppPrefs().getCompanyNameList() != null) {
            dismissProgressBar(getActivity());
            CompanyList responseModel = MyApplication.getInstance().getAppPrefs().getCompanyNameList();
            COMPANY_NAME = responseModel.getData();
            companyTitleList = new ArrayList<>();
            if (responseModel != null && responseModel.getData() != null) {
                for (int i = 0; i < responseModel.getData().size(); i++) {
                    companyTitleList.add(responseModel.getData().get(i).getName());
                }
            }

        }
        if (MyApplication.getInstance().getAppPrefs().getBankNameList() != null) {
            dismissProgressBar(getActivity());
            CompanyList responseModel = MyApplication.getInstance().getAppPrefs().getBankNameList();
            COMPANY_NAME = responseModel.getData();
            bankNameList = new ArrayList<>();
            if (responseModel != null && responseModel.getData() != null) {
                for (int i = 0; i < responseModel.getData().size(); i++) {
                    bankNameList.add(responseModel.getData().get(i).getName());
                }
            }
        }
        if (MyApplication.getInstance().getAppPrefs().getPurposeOfLoanList() != null) {
            dismissProgressBar(getActivity());
            CompanyList responseModel = MyApplication.getInstance().getAppPrefs().getPurposeOfLoanList();
            COMPANY_NAME = responseModel.getData();
            purposeLoanTitleList = new ArrayList<>();
            if (responseModel != null && responseModel.getData() != null) {
                for (int i = 0; i < responseModel.getData().size(); i++) {
                    purposeLoanTitleList.add(responseModel.getData().get(i).getName());
                }
            }
        }
        if (MyApplication.getInstance().getAppPrefs().getBranchList() != null) {
            BranchListResponseModel responseModel = MyApplication.getInstance().getAppPrefs().getBranchList();
            MyApplication.getInstance().getAppPrefs().setBranchList(responseModel);
            if (responseModel != null) {
                if (!responseModel.isError()) {
                    branchList.clear();
                    branchTitleList.clear();
                    branchList = responseModel.getData();
                    branchTitleList = new ArrayList<>();
                    String userBranchId = getMyApplication().getUserPrefs().getLoggedInUser().getBranch_id();
                    for (int i = 0; i < responseModel.getData().size(); i++) {
                        branchTitleList.add(responseModel.getData().get(i).getName());
                        if (userBranchId.equalsIgnoreCase(String.valueOf(responseModel.getData().get(i).getId()))) {
                            et_branch.setText(branchTitleList.get(i));
                            branch_id = responseModel.getData().get(i).getId();
                        }
                    }
                } else {
                    dismissProgressBar(getActivity());
                    displayErrorDialog(responseModel.getMessage());
                }
            } else {
                dismissProgressBar(getActivity());
            }
        }
        if (MyApplication.getInstance().getAppPrefs().getGroupList() != null) {
            GroupListResponseModel responseModel = MyApplication.getInstance().getAppPrefs().getGroupList();
            MyApplication.getInstance().getAppPrefs().setGroupList(responseModel);
            if (responseModel != null) {
                if (!responseModel.isError()) {
                    groupList.clear();
                    groupTitleList.clear();
                    groupList = responseModel.getData();
                    groupTitleList = new ArrayList<>();
                    // int userGroupId = getMyApplication().getUserPrefs().getLoggedInUser().getGroup_id();
                    for (int i = 0; i < responseModel.getData().size(); i++) {
                        groupTitleList.add(responseModel.getData().get(i).getName());
                        /*if (userGroupId == responseModel.getData().get(i).getId()) {
                            et_group.setText(groupTitleList.get(i));
                            group_id = responseModel.getData().get(i).getId();
                        }*/
                    }

                } else {
                    dismissProgressBar(getActivity());
                    displayErrorDialog(responseModel.getMessage());
                }
            } else {
                dismissProgressBar(getActivity());
            }
        }
        if (MyApplication.getInstance().getAppPrefs().getProductList() != null) {
            ProductListResponseModel responseModel = MyApplication.getInstance().getAppPrefs().getProductList();
            if (responseModel != null) {
                if (!responseModel.isError()) {
                    productList.clear();
                    productTitleList.clear();
                    productList = responseModel.getData();
                    productTitleList = new ArrayList<>();
                    for (int i = 0; i < responseModel.getData().size(); i++) {
                        productTitleList.add(responseModel.getData().get(i).getProduct_name());
                    }
                } else {
                    dismissProgressBar(getActivity());
                    displayErrorDialog(responseModel.getMessage());
                }
            } else {
                dismissProgressBar(getActivity());
            }
        }
        if (MyApplication.getInstance().getAppPrefs().getDynamicFieldsList() != null) {
            GetLeadFieldsResponseModel responseModel = MyApplication.getInstance().getAppPrefs().getDynamicFieldsList();
            if (responseModel != null) {
                if (!responseModel.isError()) {
                    leadFields = responseModel.getData();
                    paymentCycleTitleList.clear();
                    paymentCycleList.clear();
                    religionTitleList.clear();
                    religionList.clear();
                    customerTypeTitleList.clear();
                    customerTypeList.clear();
                    maritulStatusTitleList.clear();
                    maritulStatusList.clear();
                    kycDocTitleList.clear();
                    kycDocList.clear();

                    paymentCycleList = leadFields.getAreaPaymentCycle();
                    paymentCycleTitleList = new ArrayList<>();
                    for (int i = 0; i < leadFields.getAreaPaymentCycle().size(); i++) {
                        paymentCycleTitleList.add(leadFields.getAreaPaymentCycle().get(i).getType());
                    }
                    religionList = leadFields.getAreaReligion();
                    religionTitleList = new ArrayList<>();
                    for (int i = 0; i < leadFields.getAreaReligion().size(); i++) {
                        religionTitleList.add(leadFields.getAreaReligion().get(i).getType());
                    }
                    customerTypeList = leadFields.getAreaTypeOfCustomer();
                    customerTypeTitleList = new ArrayList<>();
                    for (int i = 0; i < leadFields.getAreaTypeOfCustomer().size(); i++) {
                        customerTypeTitleList.add(leadFields.getAreaTypeOfCustomer().get(i).getType());
                    }
                    maritulStatusList = leadFields.getAreaMaritalStatus();
                    maritulStatusTitleList = new ArrayList<>();
                    for (int i = 0; i < leadFields.getAreaMaritalStatus().size(); i++) {
                        maritulStatusTitleList.add(leadFields.getAreaMaritalStatus().get(i).getType());
                    }
                    kycDocList = leadFields.getAreaDocuments();
                    kycDocTitleList = new ArrayList<>();
                    for (int i = 0; i < leadFields.getAreaDocuments().size(); i++) {
                        kycDocTitleList.add(leadFields.getAreaDocuments().get(i).getType());
                    }

                    et_payment_cycle.setText(paymentCycleTitleList.get(0));
                    payment_cycle_id = leadFields.getAreaPaymentCycle().get(0).getId();

                    et_religion.setText(religionTitleList.get(0));
                    religion_id = leadFields.getAreaReligion().get(0).getId();

                    et_type_of_customer.setText(customerTypeTitleList.get(0));
                    customer_type_id = leadFields.getAreaTypeOfCustomer().get(0).getId();

                    et_maritul_status.setText(maritulStatusTitleList.get(0));
                    marital_id = leadFields.getAreaMaritalStatus().get(0).getId();

                } else {
                    dismissProgressBar(getActivity());
                    displayErrorDialog(responseModel.getMessage());
                }
            } else {
                dismissProgressBar(getActivity());
            }
        }
    }


    @Override
    public void onSuccessResponse(int apiId, Object response) {
        if (apiId == ApiIds.ID_USER_AREA_LIST) {
            dismissProgressBar(getActivity());
            UserArea responseModel = (UserArea) response;
            MyApplication.getInstance().getAppPrefs().setUserAreaList(responseModel);

            UserAreaList = responseModel.getData();
            userArealist = new ArrayList<>();
            if (responseModel != null && responseModel.getData() != null) {
                for (int i = 0; i < responseModel.getData().size(); i++) {
                    userArealist.add(responseModel.getData().get(i).getArea());
                }
            }
        } else if (apiId == ApiIds.ID_COMPANY_NAME) {
            dismissProgressBar(getActivity());
            CompanyList responseModel = (CompanyList) response;
            MyApplication.getInstance().getAppPrefs().setCompanyNameList(responseModel);
            COMPANY_NAME = responseModel.getData();
            companyTitleList = new ArrayList<>();
            if (responseModel != null && responseModel.getData() != null) {
                for (int i = 0; i < responseModel.getData().size(); i++) {
                    companyTitleList.add(responseModel.getData().get(i).getName());
                }
            }

        } else if (apiId == ApiIds.ID_BANK_NAME) {
            dismissProgressBar(getActivity());
            CompanyList responseModel = (CompanyList) response;
            MyApplication.getInstance().getAppPrefs().setBankNameList(responseModel);
            COMPANY_NAME = responseModel.getData();
            bankNameList = new ArrayList<>();
            if (responseModel != null && responseModel.getData() != null) {
                for (int i = 0; i < responseModel.getData().size(); i++) {
                    bankNameList.add(responseModel.getData().get(i).getName());
                }
            }

        } else if (apiId == ApiIds.ID_PURPOSES) {
            dismissProgressBar(getActivity());
            CompanyList responseModel = (CompanyList) response;
            COMPANY_NAME = responseModel.getData();
            purposeLoanTitleList = new ArrayList<>();
            MyApplication.getInstance().getAppPrefs().setPurposeOfLoanList(responseModel);
            if (responseModel != null && responseModel.getData() != null) {
                for (int i = 0; i < responseModel.getData().size(); i++) {
                    purposeLoanTitleList.add(responseModel.getData().get(i).getName());
                }
            }
        } else if (apiId == ApiIds.ID_GET_BRANCHES) {
            dismissProgressBar(getActivity());
            BranchListResponseModel responseModel = (BranchListResponseModel) response;
            MyApplication.getInstance().getAppPrefs().setBranchList(responseModel);
            if (responseModel != null) {
                if (!responseModel.isError()) {
                    branchList.clear();
                    branchTitleList.clear();
                    branchList = responseModel.getData();
                    branchTitleList = new ArrayList<>();
                    String userBranchId = getMyApplication().getUserPrefs().getLoggedInUser().getBranch_id();
                    for (int i = 0; i < responseModel.getData().size(); i++) {
                        branchTitleList.add(responseModel.getData().get(i).getName());
                        if (userBranchId.equalsIgnoreCase(String.valueOf(responseModel.getData().get(i).getId()))) {
                            et_branch.setText(branchTitleList.get(i));
                            branch_id = responseModel.getData().get(i).getId();
                        }
                    }
                } else {
                    dismissProgressBar(getActivity());
                    displayErrorDialog(responseModel.getMessage());
                }
            } else {
                dismissProgressBar(getActivity());
            }
        } else if (apiId == ApiIds.ID_GET_GROUPS) {
            GroupListResponseModel responseModel = (GroupListResponseModel) response;
            MyApplication.getInstance().getAppPrefs().setGroupList(responseModel);
            if (responseModel != null) {
                if (!responseModel.isError()) {
                    groupList.clear();
                    groupTitleList.clear();
                    groupList = responseModel.getData();
                    groupTitleList = new ArrayList<>();
                    // int userGroupId = getMyApplication().getUserPrefs().getLoggedInUser().getGroup_id();
                    for (int i = 0; i < responseModel.getData().size(); i++) {
                        groupTitleList.add(responseModel.getData().get(i).getName());
                        /*if (userGroupId == responseModel.getData().get(i).getId()) {
                            et_group.setText(groupTitleList.get(i));
                            group_id = responseModel.getData().get(i).getId();
                        }*/
                    }

                } else {
                    dismissProgressBar(getActivity());
                    displayErrorDialog(responseModel.getMessage());
                }
            } else {
                dismissProgressBar(getActivity());
            }
        } else if (apiId == ApiIds.ID_GET_PRODUCTS) {
            ProductListResponseModel responseModel = (ProductListResponseModel) response;
            MyApplication.getInstance().getAppPrefs().setProductList(responseModel);
            if (responseModel != null) {
                if (!responseModel.isError()) {
                    productList.clear();
                    productTitleList.clear();
                    productList = responseModel.getData();
                    productTitleList = new ArrayList<>();
                    for (int i = 0; i < responseModel.getData().size(); i++) {
                        productTitleList.add(responseModel.getData().get(i).getProduct_name());
                    }
                } else {
                    dismissProgressBar(getActivity());
                    displayErrorDialog(responseModel.getMessage());
                }
            } else {
                dismissProgressBar(getActivity());
            }
        } else if (apiId == ApiIds.ID_GET_LEAD_FIELDS) {
            dismissProgressBar(getActivity());
            GetLeadFieldsResponseModel responseModel = (GetLeadFieldsResponseModel) response;
            MyApplication.getInstance().getAppPrefs().setDynamicFieldsList(responseModel);
            if (responseModel != null) {
                if (!responseModel.isError()) {
                    leadFields = responseModel.getData();
                    paymentCycleTitleList.clear();
                    paymentCycleList.clear();
                    religionTitleList.clear();
                    religionList.clear();
                    customerTypeTitleList.clear();
                    customerTypeList.clear();
                    maritulStatusTitleList.clear();
                    maritulStatusList.clear();
                    kycDocTitleList.clear();
                    kycDocList.clear();
                    paymentCycleTitleList = new ArrayList<>();
                    paymentCycleList = leadFields.getAreaPaymentCycle();
                    for (int i = 0; i < leadFields.getAreaPaymentCycle().size(); i++) {
                        paymentCycleTitleList.add(leadFields.getAreaPaymentCycle().get(i).getType());
                    }
                    religionList = leadFields.getAreaReligion();
                    religionTitleList = new ArrayList<>();
                    for (int i = 0; i < leadFields.getAreaReligion().size(); i++) {
                        religionTitleList.add(leadFields.getAreaReligion().get(i).getType());
                    }
                    customerTypeList = leadFields.getAreaTypeOfCustomer();
                    customerTypeTitleList = new ArrayList<>();
                    for (int i = 0; i < leadFields.getAreaTypeOfCustomer().size(); i++) {
                        customerTypeTitleList.add(leadFields.getAreaTypeOfCustomer().get(i).getType());
                    }
                    maritulStatusList = leadFields.getAreaMaritalStatus();
                    maritulStatusTitleList = new ArrayList<>();
                    for (int i = 0; i < leadFields.getAreaMaritalStatus().size(); i++) {
                        maritulStatusTitleList.add(leadFields.getAreaMaritalStatus().get(i).getType());
                    }
                    kycDocList = leadFields.getAreaDocuments();
                    kycDocTitleList = new ArrayList<>();
                    for (int i = 0; i < leadFields.getAreaDocuments().size(); i++) {
                        kycDocTitleList.add(leadFields.getAreaDocuments().get(i).getType());
                    }

                    et_payment_cycle.setText(paymentCycleTitleList.get(0));
                    payment_cycle_id = leadFields.getAreaPaymentCycle().get(0).getId();

                    et_religion.setText(religionTitleList.get(0));
                    religion_id = leadFields.getAreaReligion().get(0).getId();

                    et_type_of_customer.setText(customerTypeTitleList.get(0));
                    customer_type_id = leadFields.getAreaTypeOfCustomer().get(0).getId();

                    et_maritul_status.setText(maritulStatusTitleList.get(0));
                    marital_id = leadFields.getAreaMaritalStatus().get(0).getId();


                } else {
                    dismissProgressBar(getActivity());
                    displayErrorDialog(responseModel.getMessage());
                }
            } else {
                dismissProgressBar(getActivity());
            }
        } else if (apiId == ApiIds.ID_SUBMIT_APPFORM) {
            LeadResponseModel responseModel = (LeadResponseModel) response;
            if (responseModel != null) {
                if (!responseModel.isError()) {
                    leadTable.delete(local_id);
                    if (allLeadImages != null && allLeadImages.size() > 0) {
                        uploadImage(allLeadImages.get(0), false);
                    } else {
                        dismissProgressBar(getActivity());
                        displayAlertDialog(getString(R.string.message), responseModel.getMessage(), this);
                    }
                } else {
                    dismissProgressBar(getActivity());
                    displayErrorDialog(responseModel.getMessage());
                }
            }
        } else if (apiId == ApiIds.ID_EDIT_APPFORM) {
            LeadResponseModel responseModel = (LeadResponseModel) response;
            if (responseModel != null) {
                if (!responseModel.isError()) {
                    leadTable.delete(local_id);
                    if (allLeadImages != null && allLeadImages.size() > 0) {
                        uploadImage(allLeadImages.get(0), false);
                    } else {
                        dismissProgressBar(getActivity());
                        displayAlertDialog(getString(R.string.message), responseModel.getMessage(), this);
                    }
                } else {
                    dismissProgressBar(getActivity());
                    displayErrorDialog(responseModel.getMessage());
                }
            }
        } else if (apiId == ApiIds.ID_UPLOAD_IMAGE) {
            UploadImageResponseModel responseModel = (UploadImageResponseModel) response;
            if (responseModel != null) {
                if (!responseModel.isError()) {
                    if (allLeadImages.size() > 0) {
                        allLeadImages.remove(0);
                    }
                    if (allLeadImages.size() > 0) {
                        uploadImage(allLeadImages.get(0), false);
                    } else {
                        dismissProgressBar(getActivity());
                        displayAlertDialog(getString(R.string.message), getString(R.string.your_form_successfully_submitted), this);
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
    public void onBackClick() {
        // showBackDialog();
    }
}
