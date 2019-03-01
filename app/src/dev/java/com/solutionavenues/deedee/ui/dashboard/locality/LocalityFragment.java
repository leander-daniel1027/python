package com.solutionavenues.deedee.ui.dashboard.locality;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.helper.ConnectionDetector;
import com.solutionavenues.deedee.R;
import com.solutionavenues.deedee.appBase.AppBaseFragment;
import com.solutionavenues.deedee.imagePicker.FileInformation;
import com.solutionavenues.deedee.imagePicker.ImagePickDialog;
import com.solutionavenues.deedee.interfaces.OnOkClickListener;
import com.solutionavenues.deedee.model.request.AddLocalityRequestModel;
import com.solutionavenues.deedee.model.response.BaseWebResponseModel;
import com.solutionavenues.deedee.model.response.LocalityMicroEnterPriseModel;
import com.solutionavenues.deedee.model.response.UploadImageResponseModel;
import com.solutionavenues.deedee.rest.ApiHitListener;
import com.solutionavenues.deedee.rest.ApiIds;
import com.solutionavenues.deedee.rest.BaseArguments;
import com.solutionavenues.deedee.rest.RetrofitUtils;
import com.solutionavenues.deedee.util.Utils;

import java.util.ArrayList;

import okhttp3.MultipartBody;

/**
 * Created by Azher on 18/6/18.
 */
public class LocalityFragment extends AppBaseFragment implements
        ApiHitListener,OnOkClickListener {
    private String TAG = this.getClass().getSimpleName();
    private TextView tv_save;
    private TextView tv_village_info, tv_village_detail, tv_mfi, tv_activities;

    private LinearLayout ll_village_detail, ll_mfi_operating,
            ll_village_info, ll_activities, ll_mfi_view_containor,ll_locality_images_containor,
            ll_activities_m_e_view_containor, ll_activities_o_m_e_view_containor;

    private EditText et_village_name, et_block_area, et_tehsil_name,
            et_post_office, et_police_station, et_district, et_state;

    private EditText et_household_kachcha, et_household_pakka, et_general,
            et_obc, et_sc, et_st, et_distance_branch, et_distance_mainroad,
            et_ope_village_name, et_ope_village_distance, et_hospital_private,
            et_hospital_govt, et_distance_hospital, et_school_private, et_school_govt,
            et_distance_school, et_service_bank, et_credit_history, et_political_influence,
            et_major_livelihood, et_main_crops,et_law_order_position,et_remark_of_person;

    private RadioGroup rg_road_connectivity;
    private ArrayList<LocalityMicroEnterPriseModel.DataBean.MicroEnterpriseBean> microEntpList = new ArrayList<>();
    private ArrayList<String> microEntpTitleList = new ArrayList<>();
    private ArrayList<LocalityMicroEnterPriseModel.DataBean.OtherMicroEnterpriseBean> otherMicroEntpList = new ArrayList<>();
    private ArrayList<String> otherMicroEntpTitleList = new ArrayList<>();
    private TextView[] tabArr;
    private LinearLayout[] viewArr;
    int microEntpClickPos = 0;
    int otherOicroEntpClickPos = 0;
    int otherMicroId;
    int microId;
    private ArrayList<String> allLocalityImages = new ArrayList<>();

    @Override
    public int getLayoutResourceId() {
        return R.layout.fragment_locality_form;
    }

    @Override
    public void initializeComponent() {

        ll_village_info = getView().findViewById(R.id.ll_village_info);
        ll_village_detail = getView().findViewById(R.id.ll_village_detail);
        ll_mfi_operating = getView().findViewById(R.id.ll_mfi_operating);
        ll_activities = getView().findViewById(R.id.ll_activities);
        ll_mfi_view_containor = getView().findViewById(R.id.ll_mfi_view_containor);
        ll_locality_images_containor = getView().findViewById(R.id.ll_locality_images_containor);
        ll_activities_m_e_view_containor = getView().findViewById(R.id.ll_activities_m_e_view_containor);
        ll_activities_o_m_e_view_containor = getView().findViewById(R.id.ll_activities_o_m_e_view_containor);
        tv_mfi = getView().findViewById(R.id.tv_mfi);
        tv_activities = getView().findViewById(R.id.tv_activities);
        tv_save = getView().findViewById(R.id.tv_save);
        tv_village_info = getView().findViewById(R.id.tv_village_info);
        tv_village_detail = getView().findViewById(R.id.tv_village_detail);


        /*set click listeners to views*/
        tv_village_detail.setOnClickListener(this);
        tv_village_info.setOnClickListener(this);
        tv_mfi.setOnClickListener(this);
        tv_activities.setOnClickListener(this);
        tv_save.setOnClickListener(this);

        tabArr = new TextView[]{tv_village_info, tv_village_detail, tv_mfi, tv_activities};
        viewArr = new LinearLayout[]{ll_village_info, ll_village_detail, ll_mfi_operating, ll_activities};


        tv_village_info.performClick();
        addMfiView();
        addActivityMicroEnterpriseView();
        addActivityOtherMicroEnterpriseView();
        addLocalityImageView();


        /*initialize village info views*/
        et_village_name = getView().findViewById(R.id.et_village_name);
        et_block_area = getView().findViewById(R.id.et_block_area);
        et_tehsil_name = getView().findViewById(R.id.et_tehsil_name);
        et_post_office = getView().findViewById(R.id.et_post_office);
        et_police_station = getView().findViewById(R.id.et_police_station);
        et_district = getView().findViewById(R.id.et_district);
        et_state = getView().findViewById(R.id.et_state);

        /*initialize village Details views*/
        et_household_kachcha = getView().findViewById(R.id.et_household_kachcha);
        et_household_pakka = getView().findViewById(R.id.et_household_pakka);
        et_general = getView().findViewById(R.id.et_general);
        et_obc = getView().findViewById(R.id.et_obc);
        et_sc = getView().findViewById(R.id.et_sc);
        et_st = getView().findViewById(R.id.et_st);
        et_distance_branch = getView().findViewById(R.id.et_distance_branch);
        et_distance_mainroad = getView().findViewById(R.id.et_distance_mainroad);
        et_ope_village_name = getView().findViewById(R.id.et_ope_village_name);
        et_ope_village_distance = getView().findViewById(R.id.et_ope_village_distance);
        rg_road_connectivity = getView().findViewById(R.id.rg_road_connectivity);
        et_hospital_private = getView().findViewById(R.id.et_hospital_private);
        et_hospital_govt = getView().findViewById(R.id.et_hospital_govt);
        et_distance_hospital = getView().findViewById(R.id.et_distance_hospital);
        et_school_private = getView().findViewById(R.id.et_school_private);
        et_school_govt = getView().findViewById(R.id.et_school_govt);
        et_distance_school = getView().findViewById(R.id.et_distance_school);
        et_service_bank = getView().findViewById(R.id.et_service_bank);
        et_credit_history = getView().findViewById(R.id.et_credit_history);
        et_political_influence = getView().findViewById(R.id.et_political_influence);
        et_major_livelihood = getView().findViewById(R.id.et_major_livelihood);
        et_main_crops = getView().findViewById(R.id.et_main_crops);
        et_remark_of_person = getView().findViewById(R.id.et_remark_of_person);
        et_law_order_position = getView().findViewById(R.id.et_law_order_position);
        getMicroEnterpriseList();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_save:
                AddLocalityRequestModel localityRequestModel = getAllLocalityData();
                /*Gson gson = new Gson();
                String localityJson = gson.toJson(localityRequestModel);
                Log.e(TAG, "localityJson: " + localityJson);*/
                addLocality(localityRequestModel);

                break;
            case R.id.tv_village_info:
                setTabVisibility(tv_village_info, ll_village_info);
                break;
            case R.id.tv_village_detail:
                setTabVisibility(tv_village_detail, ll_village_detail);
                break;
            case R.id.tv_mfi:
                setTabVisibility(tv_mfi, ll_mfi_operating);
                break;
            case R.id.tv_activities:
                setTabVisibility(tv_activities, ll_activities);
                break;

        }

    }


    private AddLocalityRequestModel getAllLocalityData() {
        AddLocalityRequestModel localityRequestModel = new AddLocalityRequestModel();
        String user_id = String.valueOf(getMyApplication().getUserPrefs().getLoggedInUser().getId());
        localityRequestModel.setUser_id(user_id);

        /*Village info Data*/
        localityRequestModel.setVillage_name(et_village_name.getText().toString());
        localityRequestModel.setTehsil(et_tehsil_name.getText().toString());
        localityRequestModel.setPost_office(et_post_office.getText().toString());
        localityRequestModel.setBlock_area(et_block_area.getText().toString());
        localityRequestModel.setPolice_station(et_police_station.getText().toString());
        localityRequestModel.setDistrict(et_district.getText().toString());
        localityRequestModel.setState(et_state.getText().toString());

        /*Village info Details Data*/
        AddLocalityRequestModel.BrancheDetailBean detailBean = new AddLocalityRequestModel.BrancheDetailBean();
        detailBean.setKaccha_ghar(et_household_kachcha.getText().toString());
        detailBean.setPakka_ghar(et_household_pakka.getText().toString());
        detailBean.setComu_gen(et_general.getText().toString());
        detailBean.setComu_obc(et_obc.getText().toString());
        detailBean.setComu_sc(et_sc.getText().toString());
        detailBean.setComu_st(et_st.getText().toString());
        detailBean.setDistance_frm_branch(et_distance_branch.getText().toString());
        detailBean.setDistance_frm_main_road(et_distance_mainroad.getText().toString());
        detailBean.setDistance_frm_nov(et_ope_village_distance.getText().toString());
        detailBean.setBranch_name_nov(et_ope_village_name.getText().toString());
        detailBean.setPrivate_hospital_count(et_hospital_private.getText().toString());
        detailBean.setGovt_hospital_count(et_hospital_govt.getText().toString());
        detailBean.setHospital_distance(et_distance_hospital.getText().toString());
        detailBean.setSchool_distance(et_distance_school.getText().toString());
        detailBean.setGovt_school_count(et_school_govt.getText().toString());
        detailBean.setPrivate_school_count(et_school_private.getText().toString());
        detailBean.setVillage_credit_history(et_credit_history.getText().toString());
        detailBean.setPolitical_parties_count(et_political_influence.getText().toString());
        detailBean.setLivelihood_activity(et_major_livelihood.getText().toString());
        detailBean.setMain_crops(et_main_crops.getText().toString());
        detailBean.setRoad_connectivity(getRadioButtonValue(rg_road_connectivity));
        detailBean.setDate(getCurrentDate());
        detailBean.setRemark_survey_person(et_remark_of_person.getText().toString());
        detailBean.setLaw_order_postion(et_law_order_position.getText().toString());
        localityRequestModel.setBranche_detail(detailBean);

        /*Mfi Operating Data*/
        localityRequestModel.setMfi_operation_area(getMfiData());

        /*Activity Micro Enterprise Data*/
        localityRequestModel.setMicro_enterprise(getActivityMicroEntpData());

        /*Activity Other Micro Enterprise Data*/
        localityRequestModel.setOther_micro_enterprise(getActivityOtherMicroEntpData());

        return localityRequestModel;
    }


    private void setTabVisibility(TextView textView, LinearLayout view) {
        textView.setTextColor(getResources().getColor(R.color.colorAccent));
        textView.setBackgroundColor(getResources().getColor(R.color.colorWhite));
        view.setVisibility(View.VISIBLE);
        for (int i = 0; i < tabArr.length; i++) {
            if (textView.getId() != tabArr[i].getId()) {
                tabArr[i].setTextColor(getResources().getColor(R.color.colorWhite));
                tabArr[i].setBackgroundColor(getResources().getColor(R.color.semi_black));
            }
            if (view.getId() != viewArr[i].getId()) {
                viewArr[i].setVisibility(View.GONE);
            }
        }


    }


    private void addMfiView() {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View view = inflater.inflate(R.layout.layout_mfi_view, null);
        ImageView iv_add_view = view.findViewById(R.id.iv_add_view);
        ImageView iv_remove_view = view.findViewById(R.id.iv_remove_view);
        iv_remove_view.setTag(ll_mfi_view_containor.getChildCount());
        ll_mfi_view_containor.addView(view);
        iv_remove_view.setOnClickListener(onMfiDeleteListener);
        iv_add_view.setOnClickListener(onMfiAddListener);

    }

    View.OnClickListener onMfiAddListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            addMfiView();
            onRefreshMfi();
        }
    };

    View.OnClickListener onMfiDeleteListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ll_mfi_view_containor.removeViewAt(Integer.parseInt(v.getTag().toString()));
            onRefreshMfi();
        }
    };

    private void onRefreshMfi() {
        for (int i = 0; i < ll_mfi_view_containor.getChildCount(); i++) {
            View view = ll_mfi_view_containor.getChildAt(i);
            ImageView iv_remove_view = view.findViewById(R.id.iv_remove_view);
            ImageView iv_add_view = view.findViewById(R.id.iv_add_view);
            iv_remove_view.setTag(i);
            iv_remove_view.setVisibility(View.VISIBLE);
            if (i == 0) {
                iv_remove_view.setVisibility(View.GONE);
            }
            if (i == ll_mfi_view_containor.getChildCount() - 1) {
                iv_add_view.setVisibility(View.VISIBLE);
            } else {
                iv_add_view.setVisibility(View.GONE);
            }
        }
    }


    private void addActivityMicroEnterpriseView() {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View view = inflater.inflate(R.layout.layout_activities_m_e_view, null);
        ImageView iv_add_m_e_view = view.findViewById(R.id.iv_add_m_e_view);
        ImageView iv_remove_m_e_view = view.findViewById(R.id.iv_remove_m_e_view);
        EditText et_micro_enterprise = view.findViewById(R.id.et_micro_enterprise);
        iv_remove_m_e_view.setTag(ll_activities_m_e_view_containor.getChildCount());
        et_micro_enterprise.setTag(ll_activities_m_e_view_containor.getChildCount());
        ll_activities_m_e_view_containor.addView(view);
        iv_remove_m_e_view.setOnClickListener(onActivityMicroEntpDeleteListener);
        iv_add_m_e_view.setOnClickListener(onActivityMicroEntpAddListener);
        et_micro_enterprise.setOnClickListener(onMicroEntpClickListner);

    }

    View.OnClickListener onMicroEntpClickListner = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            microEntpClickPos = (int) v.getTag();
            Utils.hideKeyboard(getActivity());
            Utils.showAlertDialog(getActivity(),
                    getString(R.string.select_micro_entp),
                    microEntpTitleList.toArray(new String[microEntpTitleList.size()]),
                    onClickedMicroEntpListner);
        }
    };

    DialogInterface.OnClickListener onClickedMicroEntpListner = new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int item) {
            View v = ll_activities_m_e_view_containor.getChildAt(microEntpClickPos);
            EditText et_micro_enterprise = v.findViewById(R.id.et_micro_enterprise);
            EditText et_micro_enterprise_no = v.findViewById(R.id.et_micro_enterprise_no);
            microId=microEntpList.get(item).getId();
            et_micro_enterprise_no.setTag(microId);
            et_micro_enterprise.setText("" + microEntpTitleList.toArray(new String[microEntpTitleList.size()])[item]);
        }
    };

    View.OnClickListener onActivityMicroEntpAddListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            addActivityMicroEnterpriseView();
            onRefreshMicroEp();
        }
    };

    View.OnClickListener onActivityMicroEntpDeleteListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ll_activities_m_e_view_containor.removeViewAt(Integer.parseInt(v.getTag().toString()));
            onRefreshMicroEp();
        }
    };


    private void onRefreshMicroEp() {
        for (int i = 0; i < ll_activities_m_e_view_containor.getChildCount(); i++) {
            View view = ll_activities_m_e_view_containor.getChildAt(i);
            ImageView iv_add_m_e_view = view.findViewById(R.id.iv_add_m_e_view);
            ImageView iv_remove_m_e_view = view.findViewById(R.id.iv_remove_m_e_view);
            EditText et_micro_enterprise = view.findViewById(R.id.et_micro_enterprise);
            et_micro_enterprise.setTag(i);
            iv_remove_m_e_view.setTag(i);
            iv_remove_m_e_view.setVisibility(View.VISIBLE);
            if (i == 0) {
                iv_remove_m_e_view.setVisibility(View.GONE);
            }
            if (i == ll_activities_m_e_view_containor.getChildCount() - 1) {
                iv_add_m_e_view.setVisibility(View.VISIBLE);
            } else {
                iv_add_m_e_view.setVisibility(View.GONE);
            }
        }
    }


    private void addActivityOtherMicroEnterpriseView() {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View view = inflater.inflate(R.layout.layout_activities_o_m_e_view, null);
        ImageView iv_add_o_m_e_view = view.findViewById(R.id.iv_add_o_m_e_view);
        ImageView iv_remove_o_m_e_view = view.findViewById(R.id.iv_remove_o_m_e_view);
        EditText et_other_micro_enterprise = view.findViewById(R.id.et_other_micro_enterprise);
        iv_remove_o_m_e_view.setTag(ll_activities_o_m_e_view_containor.getChildCount());
        et_other_micro_enterprise.setTag(ll_activities_o_m_e_view_containor.getChildCount());
        ll_activities_o_m_e_view_containor.addView(view);
        iv_remove_o_m_e_view.setOnClickListener(onActivityOtherMicroEntpDeleteListener);
        iv_add_o_m_e_view.setOnClickListener(onActivityOtherMicroEntpAddListener);
        et_other_micro_enterprise.setOnClickListener(onOtherMicroEntpClickListner);


    }


    View.OnClickListener onOtherMicroEntpClickListner = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            otherOicroEntpClickPos= (int) v.getTag();
            Utils.hideKeyboard(getActivity());
            Utils.showAlertDialog(getActivity(),
                    getString(R.string.select_other_micro_entp),
                    otherMicroEntpTitleList.toArray(new String[otherMicroEntpTitleList.size()]),
                    onClickedOtherMicroEntpListner);
        }
    };

    DialogInterface.OnClickListener onClickedOtherMicroEntpListner = new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int item) {
            View v = ll_activities_o_m_e_view_containor.getChildAt(otherOicroEntpClickPos);
            EditText et_other_micro_enterprise = v.findViewById(R.id.et_other_micro_enterprise);
            EditText et_other_micro_enterprise_no = v.findViewById(R.id.et_other_micro_enterprise_no);
            otherMicroId=otherMicroEntpList.get(item).getId();
            et_other_micro_enterprise_no.setTag(otherMicroId);
            et_other_micro_enterprise.setText("" + otherMicroEntpTitleList.toArray(new String[otherMicroEntpTitleList.size()])[item]);
        }
    };


    View.OnClickListener onActivityOtherMicroEntpAddListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            addActivityOtherMicroEnterpriseView();
            onRefreshOtherMicroEp();
        }
    };

    View.OnClickListener onActivityOtherMicroEntpDeleteListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ll_activities_o_m_e_view_containor.removeViewAt(Integer.parseInt(v.getTag().toString()));
            onRefreshOtherMicroEp();
        }
    };


    private void onRefreshOtherMicroEp() {
        for (int i = 0; i < ll_activities_o_m_e_view_containor.getChildCount(); i++) {
            View view = ll_activities_o_m_e_view_containor.getChildAt(i);
            ImageView iv_add_o_m_e_view = view.findViewById(R.id.iv_add_o_m_e_view);
            ImageView iv_remove_o_m_e_view = view.findViewById(R.id.iv_remove_o_m_e_view);
            EditText et_other_micro_enterprise = view.findViewById(R.id.et_other_micro_enterprise);
            et_other_micro_enterprise.setTag(i);
            iv_remove_o_m_e_view.setTag(i);
            iv_remove_o_m_e_view.setVisibility(View.VISIBLE);
            if (i == 0) {
                iv_remove_o_m_e_view.setVisibility(View.GONE);
            }
            if (i == ll_activities_o_m_e_view_containor.getChildCount() - 1) {
                iv_add_o_m_e_view.setVisibility(View.VISIBLE);
            } else {
                iv_add_o_m_e_view.setVisibility(View.GONE);
            }
        }
    }

    private void addLocalityImageView() {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View view = inflater.inflate(R.layout.layout_locality_image_view, null);
        ImageView iv_add_view = view.findViewById(R.id.iv_add_view);
        ImageView iv_remove_view = view.findViewById(R.id.iv_remove_view);
        ImageView iv_locality_image = view.findViewById(R.id.iv_locality_image);
        iv_remove_view.setTag(ll_locality_images_containor.getChildCount());
        iv_locality_image.setTag(ll_locality_images_containor.getChildCount());
        ll_locality_images_containor.addView(view);
        iv_remove_view.setOnClickListener(onImageDeleteListener);
        iv_add_view.setOnClickListener(onImageAddListener);
        iv_locality_image.setOnClickListener(imageClickListener);
    }

    View.OnClickListener onImageAddListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            addLocalityImageView();
            onRefreshLocalityImage();
        }
    };

    View.OnClickListener onImageDeleteListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ll_locality_images_containor.removeViewAt(Integer.parseInt(v.getTag().toString()));
            onRefreshLocalityImage();
        }
    };

    private void onRefreshLocalityImage() {
        for (int i = 0; i < ll_locality_images_containor.getChildCount(); i++) {
            View view = ll_locality_images_containor.getChildAt(i);
            ImageView iv_remove_view = view.findViewById(R.id.iv_remove_view);
            ImageView iv_add_view = view.findViewById(R.id.iv_add_view);
            iv_remove_view.setTag(i);
            iv_remove_view.setVisibility(View.VISIBLE);
            if (i == 0) {
                iv_remove_view.setVisibility(View.GONE);
            }
            if (i == ll_locality_images_containor.getChildCount() - 1) {
                iv_add_view.setVisibility(View.VISIBLE);
            } else {
                iv_add_view.setVisibility(View.GONE);
            }
        }
    }
    View.OnClickListener imageClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            openPicker(v);
        }
    };

    private void openPicker(final View view) {
        ImagePickDialog picDialog = ImagePickDialog.getNewInstance(false);
        picDialog.setProfilePicDialogListner(new ImagePickDialog.ProfilePicDialogListner() {
            @Override
            public void onProfilePicSelected(FileInformation fileInformation) {
                Log.e(TAG, "onPicSelected: " + fileInformation.getFilePath());
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


    private ArrayList<AddLocalityRequestModel.MfiOperationAreaBean> getMfiData() {

        ArrayList<AddLocalityRequestModel.MfiOperationAreaBean> mfiOperationList = new ArrayList<>();
        if (ll_mfi_view_containor.getChildCount() > 0) {
            for (int i = 0; i < ll_mfi_view_containor.getChildCount(); i++) {
                AddLocalityRequestModel.MfiOperationAreaBean areaBean = new AddLocalityRequestModel.MfiOperationAreaBean();
                View view = ll_mfi_view_containor.getChildAt(i);
                EditText et_mfi_name = view.findViewById(R.id.et_mfi_name);
                EditText et_years_operation = view.findViewById(R.id.et_years_operation);
                EditText et_total_customers = view.findViewById(R.id.et_total_customers);
                EditText et_repayment = view.findViewById(R.id.et_repayment);
                String mfiName = et_mfi_name.getText().toString();
                String mfiYears = et_years_operation.getText().toString();
                String totalCustomers = et_total_customers.getText().toString();
                String repayment = et_repayment.getText().toString();
                areaBean.setMfi_name(mfiName);
                areaBean.setYear_of_operations(mfiYears);
                areaBean.setTotal_customers(totalCustomers);
                areaBean.setRepayment(repayment);
                mfiOperationList.add(areaBean);
            }
        }
        return mfiOperationList;
    }

    private ArrayList<AddLocalityRequestModel.MicroEnterpriseBean> getActivityMicroEntpData() {
        ArrayList<AddLocalityRequestModel.MicroEnterpriseBean> activityMEList = new ArrayList<>();
        if (ll_activities_m_e_view_containor.getChildCount() > 0) {
            for (int i = 0; i < ll_activities_m_e_view_containor.getChildCount(); i++) {
                AddLocalityRequestModel.MicroEnterpriseBean microEnterpriseBean = new AddLocalityRequestModel.MicroEnterpriseBean();
                View view = ll_activities_m_e_view_containor.getChildAt(i);
                EditText et_micro_enterprise = view.findViewById(R.id.et_micro_enterprise);
                EditText et_micro_enterprise_no = view.findViewById(R.id.et_micro_enterprise_no);
              //  String microEnterPrise = et_micro_enterprise.getText().toString();
                String microEnterPriseNo = et_micro_enterprise_no.getText().toString();
                microEnterpriseBean.setShop_type(String.valueOf(et_micro_enterprise_no.getTag()));
                microEnterpriseBean.setNum_of_shop(microEnterPriseNo);
                activityMEList.add(microEnterpriseBean);
            }
        }
        return activityMEList;
    }

    private ArrayList<AddLocalityRequestModel.OtherMicroEnterpriseBean> getActivityOtherMicroEntpData() {

        ArrayList<AddLocalityRequestModel.OtherMicroEnterpriseBean> activityOMEList = new ArrayList<>();
        if (ll_activities_o_m_e_view_containor.getChildCount() > 0) {
            for (int i = 0; i < ll_activities_o_m_e_view_containor.getChildCount(); i++) {
                AddLocalityRequestModel.OtherMicroEnterpriseBean otherMicroEnterpriseBean = new AddLocalityRequestModel.OtherMicroEnterpriseBean();
                View view = ll_activities_o_m_e_view_containor.getChildAt(i);
                EditText et_micro_other_enterprise = view.findViewById(R.id.et_other_micro_enterprise);
                EditText et_micro_other_enterprise_no = view.findViewById(R.id.et_other_micro_enterprise_no);
              //  String microEnterPrise = et_micro_other_enterprise.getText().toString();
                String microEnterPriseNo = et_micro_other_enterprise_no.getText().toString();
                otherMicroEnterpriseBean.setShop_type(String.valueOf(et_micro_other_enterprise_no.getTag()));
                otherMicroEnterpriseBean.setNum_of_shop(microEnterPriseNo);
                activityOMEList.add(otherMicroEnterpriseBean);
            }
        }
        return activityOMEList;
    }


    /* // validation for ALL fields
     private boolean checkValidation() {
         boolean status = true;
         if (!RegexValidations.hasText(et_old_password, getActivity(), getString(R.string.OLD_PASSWORD_MSG))) {
             return false;
         }
         if (!RegexValidations.isValidPassword(et_new_password, false, getActivity())) {
             return false;
         }
         if (!RegexValidations.isValidPassword(et_confirm_password, true, getActivity())) {
             return false;
         }
         if (!et_new_password.getText().toString().equals(et_confirm_password.getText().toString())) {
             displayToast(getString(R.string.password_not_matched));
             return false;
         }
         return status;
     }
*/

    // call to getMicroEnterpriseList service
    private void getMicroEnterpriseList() {
        if (ConnectionDetector.isNetAvail(getActivity())) {
            displayProgressBar(false, getActivity());
            String user_id = String.valueOf(getMyApplication().getUserPrefs().getLoggedInUser().getId());
            getRestClient().callback(this).getMicroEnterpriseList(user_id);
        } else {
            displayErrorDialog(getString(R.string.error_internet_connection));
        }
    }


    // call to addLocality service
    private void addLocality(AddLocalityRequestModel localityRequestModel) {
        if (ConnectionDetector.isNetAvail(getActivity())) {
            displayProgressBar(false, getActivity());
            getRestClient().callback(this).addLocality(localityRequestModel);
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
        dismissProgressBar(getActivity());
        if (apiId == ApiIds.ID_ADD_LOCALITY) {
            BaseWebResponseModel responseModel = (BaseWebResponseModel) response;
            if (responseModel != null) {
                if (!responseModel.isError()) {
                    if (allLocalityImages.size() > 0) {
                        uploadImage(allLocalityImages.get(0), false);
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
        }else if (apiId == ApiIds.ID_UPLOAD_IMAGE) {
            UploadImageResponseModel responseModel = (UploadImageResponseModel) response;
            if (responseModel != null) {
                if (!responseModel.isError()) {
                    allLocalityImages.remove(0);
                    if (allLocalityImages.size() > 0) {
                        uploadImage(allLocalityImages.get(0), false);
                    } else {
                        dismissProgressBar(getActivity());
                        displayAlertDialog(getString(R.string.message), getString(R.string.locality_form_successfully_submitted), this);
                    }
                } else {
                    dismissProgressBar(getActivity());
                }
            } else {
                dismissProgressBar(getActivity());
            }
        } else if (apiId == ApiIds.ID_GET_MICRO_ENTERPRISES) {
            LocalityMicroEnterPriseModel responseModel = (LocalityMicroEnterPriseModel) response;
            if (responseModel != null) {
                if (!responseModel.isError()) {
                    microEntpTitleList.clear();
                    otherMicroEntpTitleList.clear();
                    microEntpList.clear();
                    otherMicroEntpList.clear();
                    for (int i = 0; i < responseModel.getData().getMicro_enterprise().size(); i++) {
                        microEntpTitleList.add(responseModel.getData().getMicro_enterprise().get(i).getType());
                        microEntpList.add(responseModel.getData().getMicro_enterprise().get(i));
                    }
                    for (int i = 0; i < responseModel.getData().getOther_micro_enterprise().size(); i++) {
                        otherMicroEntpTitleList.add(responseModel.getData().getOther_micro_enterprise().get(i).getType());
                        otherMicroEntpList.add(responseModel.getData().getOther_micro_enterprise().get(i));
                    }
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

    @Override
    public void onDialogOkClick() {
        try {
            getDashBoardActivity().onBackPressed();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
