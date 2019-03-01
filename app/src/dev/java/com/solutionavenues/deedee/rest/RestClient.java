package com.solutionavenues.deedee.rest;

import android.util.Log;

import com.solutionavenues.deedee.model.request.AddAppointmentRequestModel;
import com.solutionavenues.deedee.model.request.AddCGTRequestModel;
import com.solutionavenues.deedee.model.request.AddCenterRequestModel;
import com.solutionavenues.deedee.model.request.AddEditEnquiryRequestModel;
import com.solutionavenues.deedee.model.request.AddFiRequestModel;
import com.solutionavenues.deedee.model.request.AddGroupRequestModel;
import com.solutionavenues.deedee.model.request.AddGrtRequestModel;
import com.solutionavenues.deedee.model.request.AddLeadRequestModel;
import com.solutionavenues.deedee.model.request.AddLocalityRequestModel;
import com.solutionavenues.deedee.model.request.SubmitRecoveryDetailsRequestModel;
import com.solutionavenues.deedee.model.response.AddCenterResponseModel;
import com.solutionavenues.deedee.model.response.AddEnquiryResponseModel;
import com.solutionavenues.deedee.model.response.AppointmentListResponseModel;
import com.solutionavenues.deedee.model.response.BaseWebResponseModel;
import com.solutionavenues.deedee.model.response.BranchListResponseModel;
import com.solutionavenues.deedee.model.response.CenterListResponseModel;
import com.solutionavenues.deedee.model.response.CompanyList;
import com.solutionavenues.deedee.model.response.EnquiryListResponseModel;
import com.solutionavenues.deedee.model.response.FiListResponseModel;
import com.solutionavenues.deedee.model.response.GetEmiHistoryModel;
import com.solutionavenues.deedee.model.response.GetLeadFieldsResponseModel;
import com.solutionavenues.deedee.model.response.GroupListResponseModel;
import com.solutionavenues.deedee.model.response.GroupMemberListModel;
import com.solutionavenues.deedee.model.response.LatLngFromAddressModel;
import com.solutionavenues.deedee.model.response.LeadListResponseModel;
import com.solutionavenues.deedee.model.response.LeadResponseModel;
import com.solutionavenues.deedee.model.response.LocalityMicroEnterPriseModel;
import com.solutionavenues.deedee.model.response.LoginResponseModel;
import com.solutionavenues.deedee.model.response.MemberListResponseModel;
import com.solutionavenues.deedee.model.response.ProductListResponseModel;
import com.solutionavenues.deedee.model.response.RecoveryApplicationClosedModel;
import com.solutionavenues.deedee.model.response.RecoveryGroupModel;
import com.solutionavenues.deedee.model.response.SubmitRecoveryDetailsModel;
import com.solutionavenues.deedee.model.response.UploadImageResponseModel;
import com.solutionavenues.deedee.model.response.UserArea;

import java.util.HashMap;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ububtu on 13/7/16.
 */
public class RestClient {
    private static final String TAG = "RestClient";
    ApiHitListener apiHitListener;
    private Rest api;
    private Object object;


    public RestClient callback(ApiHitListener apiHitListener) {
        this.apiHitListener = apiHitListener;
        return this;
    }

    private Rest getApi() {
        if (api == null) {
            api = RestService.getService();
        }

        return api;

    }


    public void loginUser(String email, String password, String device_token) {
        Call<LoginResponseModel> call = getApi().loginUser(email, password, device_token);
        call.enqueue(new Callback<LoginResponseModel>() {
            @Override
            public void onResponse(Call<LoginResponseModel> call, Response<LoginResponseModel> response) {
                apiHitListener.onSuccessResponse(ApiIds.ID_LOGIN, response.body());
            }

            @Override
            public void onFailure(Call<LoginResponseModel> call, Throwable t) {
                Log.e("error on login", t.getMessage() + ",  Complete error" + t);
                apiHitListener.onFailResponse(ApiIds.ID_LOGIN, "Internet connection problem.");
            }
        });
    }

    public void logoutUser(String user_id) {
        Call<BaseWebResponseModel> call = getApi().logoutUser(user_id, "1");
        call.enqueue(new Callback<BaseWebResponseModel>() {
            @Override
            public void onResponse(Call<BaseWebResponseModel> call, Response<BaseWebResponseModel> response) {
                apiHitListener.onSuccessResponse(ApiIds.ID_LOGOUT, response.body());
            }

            @Override
            public void onFailure(Call<BaseWebResponseModel> call, Throwable t) {
                apiHitListener.onFailResponse(ApiIds.ID_LOGOUT, "Internet connection problem.");
            }
        });
    }

    public void forgotPassword(String email) {
        Call<BaseWebResponseModel> call = getApi().forgotPassword(email);
        call.enqueue(new Callback<BaseWebResponseModel>() {
            @Override
            public void onResponse(Call<BaseWebResponseModel> call, Response<BaseWebResponseModel> response) {
                apiHitListener.onSuccessResponse(ApiIds.ID_FORGOT_PASSWORD, response.body());
            }

            @Override
            public void onFailure(Call<BaseWebResponseModel> call, Throwable t) {
                apiHitListener.onFailResponse(ApiIds.ID_FORGOT_PASSWORD, "Internet connection problem.");
            }
        });
    }

    public void changePassword(String user_id, String old_password, String new_password) {
        Call<BaseWebResponseModel> call = getApi().changePassword(user_id, old_password, new_password);
        call.enqueue(new Callback<BaseWebResponseModel>() {
            @Override
            public void onResponse(Call<BaseWebResponseModel> call, Response<BaseWebResponseModel> response) {
                apiHitListener.onSuccessResponse(ApiIds.ID_CHANGE_PASSWORD, response.body());
            }

            @Override
            public void onFailure(Call<BaseWebResponseModel> call, Throwable t) {
                apiHitListener.onFailResponse(ApiIds.ID_CHANGE_PASSWORD, "Internet connection problem.");
            }
        });
    }

    public void addEnquiry(AddEditEnquiryRequestModel enquiryRequestModel) {
        Call<AddEnquiryResponseModel> call = getApi().addEnquiry(enquiryRequestModel);
        call.enqueue(new Callback<AddEnquiryResponseModel>() {
            @Override
            public void onResponse(Call<AddEnquiryResponseModel> call, Response<AddEnquiryResponseModel> response) {
                apiHitListener.onSuccessResponse(ApiIds.ID_ADD_ENQUIRY, response.body());
            }

            @Override
            public void onFailure(Call<AddEnquiryResponseModel> call, Throwable t) {
                apiHitListener.onFailResponse(ApiIds.ID_ADD_ENQUIRY, "Internet connection problem.");
            }
        });
    }

    public void editEnquiry(AddEditEnquiryRequestModel enquiryRequestModel) {
        Call<AddEnquiryResponseModel> call = getApi().addEnquiry(enquiryRequestModel);
        call.enqueue(new Callback<AddEnquiryResponseModel>() {
            @Override
            public void onResponse(Call<AddEnquiryResponseModel> call, Response<AddEnquiryResponseModel> response) {
                apiHitListener.onSuccessResponse(ApiIds.ID_EDIT_ENQUIRY, response.body());
            }

            @Override
            public void onFailure(Call<AddEnquiryResponseModel> call, Throwable t) {
                apiHitListener.onFailResponse(ApiIds.ID_EDIT_ENQUIRY, "Internet connection problem.");
            }
        });
    }


    public void uploadImage(MultipartBody.Part part, String type) {
        HashMap<String, String> map = new HashMap<>();
        map.put(BaseArguments.ARG_TYPE, type);
        Call<UploadImageResponseModel> call = getApi().uploadImage(part, RetrofitUtils.createMultipartRequest(map));
        call.enqueue(new Callback<UploadImageResponseModel>() {
            @Override
            public void onResponse(Call<UploadImageResponseModel> call, Response<UploadImageResponseModel> response) {
                apiHitListener.onSuccessResponse(ApiIds.ID_UPLOAD_IMAGE, response.body());
            }

            @Override
            public void onFailure(Call<UploadImageResponseModel> call, Throwable t) {
                apiHitListener.onFailResponse(ApiIds.ID_UPLOAD_IMAGE, "Internet connection problem.");
            }
        });
    }

    public void uploadImage(MultipartBody.Part part) {
        Call<UploadImageResponseModel> call = getApi().uploadImage(part);
        call.enqueue(new Callback<UploadImageResponseModel>() {
            @Override
            public void onResponse(Call<UploadImageResponseModel> call, Response<UploadImageResponseModel> response) {
                apiHitListener.onSuccessResponse(ApiIds.ID_UPLOAD_IMAGE, response.body());
            }

            @Override
            public void onFailure(Call<UploadImageResponseModel> call, Throwable t) {
                apiHitListener.onFailResponse(ApiIds.ID_UPLOAD_IMAGE, "Internet connection problem.");
            }
        });
    }

    public void getEnquiryList(String user_id) {
        Call<EnquiryListResponseModel> call = getApi().getEnquiryList(user_id);
        call.enqueue(new Callback<EnquiryListResponseModel>() {
            @Override
            public void onResponse(Call<EnquiryListResponseModel> call, Response<EnquiryListResponseModel> response) {
                apiHitListener.onSuccessResponse(ApiIds.ID_ENQUIRY_LIST, response.body());
            }

            @Override
            public void onFailure(Call<EnquiryListResponseModel> call, Throwable t) {
                apiHitListener.onFailResponse(ApiIds.ID_ENQUIRY_LIST, "Internet connection problem.");
            }
        });
    }

    public void addLocality(AddLocalityRequestModel localityRequestModel) {
        Call<BaseWebResponseModel> call = getApi().addLocality(localityRequestModel);
        call.enqueue(new Callback<BaseWebResponseModel>() {
            @Override
            public void onResponse(Call<BaseWebResponseModel> call, Response<BaseWebResponseModel> response) {
                apiHitListener.onSuccessResponse(ApiIds.ID_ADD_LOCALITY, response.body());
            }

            @Override
            public void onFailure(Call<BaseWebResponseModel> call, Throwable t) {
                apiHitListener.onFailResponse(ApiIds.ID_ADD_LOCALITY, "Internet connection problem.");
            }
        });
    }


    public void getMicroEnterpriseList(String user_id) {
        Call<LocalityMicroEnterPriseModel> call = getApi().getMicroEnterpriseList(user_id);
        call.enqueue(new Callback<LocalityMicroEnterPriseModel>() {
            @Override
            public void onResponse(Call<LocalityMicroEnterPriseModel> call, Response<LocalityMicroEnterPriseModel> response) {
                apiHitListener.onSuccessResponse(ApiIds.ID_GET_MICRO_ENTERPRISES, response.body());
            }

            @Override
            public void onFailure(Call<LocalityMicroEnterPriseModel> call, Throwable t) {
                apiHitListener.onFailResponse(ApiIds.ID_GET_MICRO_ENTERPRISES, "Internet connection problem.");
            }
        });
    }


    public void addLocation(String user_id, String latitude, String longitude) {
        Call<BaseWebResponseModel> call = getApi().addLocation(user_id, latitude, longitude);
        call.enqueue(new Callback<BaseWebResponseModel>() {
            @Override
            public void onResponse(Call<BaseWebResponseModel> call, Response<BaseWebResponseModel> response) {
                apiHitListener.onSuccessResponse(ApiIds.ID_ADD_LOCATION, response.body());
            }

            @Override
            public void onFailure(Call<BaseWebResponseModel> call, Throwable t) {
                apiHitListener.onFailResponse(ApiIds.ID_ADD_LOCATION, "Internet connection problem.");
            }
        });
    }


    public void getLatLngAddress(String latlng) {

        Call<LatLngFromAddressModel> call = getApi().getLatLngAddress(latlng);
        call.enqueue(new Callback<LatLngFromAddressModel>() {
            @Override
            public void onResponse(Call<LatLngFromAddressModel> call, Response<LatLngFromAddressModel> response) {
                apiHitListener.onSuccessResponse(ApiIds.ID_GET_LATLNG_ADDRESS, response.body());
            }

            @Override
            public void onFailure(Call<LatLngFromAddressModel> call, Throwable t) {
                apiHitListener.onFailResponse(ApiIds.ID_GET_LATLNG_ADDRESS, "Internet connection problem.");
            }
        });

    }

    public void getBranchList() {
        Call<BranchListResponseModel> call = getApi().getBranchList();
        call.enqueue(new Callback<BranchListResponseModel>() {
            @Override
            public void onResponse(Call<BranchListResponseModel> call, Response<BranchListResponseModel> response) {
                apiHitListener.onSuccessResponse(ApiIds.ID_GET_BRANCHES, response.body());
            }

            @Override
            public void onFailure(Call<BranchListResponseModel> call, Throwable t) {
                apiHitListener.onFailResponse(ApiIds.ID_GET_BRANCHES, "Internet connection problem.");
            }
        });
    }

    public void getGroupList(String userid) {
        Call<GroupListResponseModel> call = getApi().getGroupList(userid);
        call.enqueue(new Callback<GroupListResponseModel>() {
            @Override
            public void onResponse(Call<GroupListResponseModel> call, Response<GroupListResponseModel> response) {
                apiHitListener.onSuccessResponse(ApiIds.ID_GET_GROUPS, response.body());
            }

            @Override
            public void onFailure(Call<GroupListResponseModel> call, Throwable t) {
                apiHitListener.onFailResponse(ApiIds.ID_GET_GROUPS, "Internet connection problem.");
            }
        });
    }

    public void getMyGroups(String user_id) {
        Call<GroupListResponseModel> call = getApi().getMyGroups(user_id);
        call.enqueue(new Callback<GroupListResponseModel>() {
            @Override
            public void onResponse(Call<GroupListResponseModel> call, Response<GroupListResponseModel> response) {
                apiHitListener.onSuccessResponse(ApiIds.ID_GET_GROUPS, response.body());
            }

            @Override
            public void onFailure(Call<GroupListResponseModel> call, Throwable t) {
                apiHitListener.onFailResponse(ApiIds.ID_GET_GROUPS, "Internet connection problem.");
            }
        });
    }

    public void getGroupMembers(String group_id) {
        Call<MemberListResponseModel> call = getApi().getGroupMembers(group_id);
        call.enqueue(new Callback<MemberListResponseModel>() {
            @Override
            public void onResponse(Call<MemberListResponseModel> call, Response<MemberListResponseModel> response) {
                apiHitListener.onSuccessResponse(ApiIds.ID_GET_MEMBERS, response.body());
            }

            @Override
            public void onFailure(Call<MemberListResponseModel> call, Throwable t) {
                apiHitListener.onFailResponse(ApiIds.ID_GET_MEMBERS, "Internet connection problem.");
            }
        });
    }

    public void getProductList() {
        Call<ProductListResponseModel> call = getApi().getProductList();
        call.enqueue(new Callback<ProductListResponseModel>() {
            @Override
            public void onResponse(Call<ProductListResponseModel> call, Response<ProductListResponseModel> response) {
                apiHitListener.onSuccessResponse(ApiIds.ID_GET_PRODUCTS, response.body());
            }

            @Override
            public void onFailure(Call<ProductListResponseModel> call, Throwable t) {
                apiHitListener.onFailResponse(ApiIds.ID_GET_PRODUCTS, "Internet connection problem.");
            }
        });
    }

    public void getLeadFields() {
        Call<GetLeadFieldsResponseModel> call = getApi().getLeadFields();
        call.enqueue(new Callback<GetLeadFieldsResponseModel>() {
            @Override
            public void onResponse(Call<GetLeadFieldsResponseModel> call, Response<GetLeadFieldsResponseModel> response) {
                apiHitListener.onSuccessResponse(ApiIds.ID_GET_LEAD_FIELDS, response.body());
            }

            @Override
            public void onFailure(Call<GetLeadFieldsResponseModel> call, Throwable t) {
                apiHitListener.onFailResponse(ApiIds.ID_GET_LEAD_FIELDS, "Internet connection problem.");
            }
        });
    }

    /*
    *
     public void setTaxiServices(ArrayList<TaxiTypeModel> arrayCategoryList) {
            mEditor = mSharedPreferencesTemp.edit();
            Gson gson = new Gson();
            String json = gson.toJson(arrayCategoryList);
            mEditor.putString(Tags.TAXI_DATA, json);
            mEditor.commit();
            AppDelegate.LogP("setTaxiArryList = " + arrayCategoryList);
        }

        public ArrayList<TaxiTypeModel> getTaxiServices() {
            Gson gson = new Gson();
            String json = mSharedPreferencesTemp.getString(Tags.TAXI_DATA, null);
            Type type = new TypeToken<ArrayList<TaxiTypeModel>>() {
            }.getType();
            ArrayList<TaxiTypeModel> arrayList = gson.fromJson(json, type);
            AppDelegate.LogP("getTaxiArryList => " + json);
            return arrayList;
        }
    */
    public void getCenterList() {
        Call<CenterListResponseModel> call = getApi().getCenterList();
        call.enqueue(new Callback<CenterListResponseModel>() {
            @Override
            public void onResponse(Call<CenterListResponseModel> call, Response<CenterListResponseModel> response) {
                apiHitListener.onSuccessResponse(ApiIds.ID_GET_CENTERS, response.body());
            }

            @Override
            public void onFailure(Call<CenterListResponseModel> call, Throwable t) {
                apiHitListener.onFailResponse(ApiIds.ID_GET_CENTERS, "Internet connection problem.");
            }
        });
    }


    public void addCenter(AddCenterRequestModel centerRequestModel) {
        Call<AddCenterResponseModel> call = getApi().addCenter(centerRequestModel);
        call.enqueue(new Callback<AddCenterResponseModel>() {
            @Override
            public void onResponse(Call<AddCenterResponseModel> call, Response<AddCenterResponseModel> response) {
                apiHitListener.onSuccessResponse(ApiIds.ID_ADD_CENTER, response.body());
            }

            @Override
            public void onFailure(Call<AddCenterResponseModel> call, Throwable t) {
                apiHitListener.onFailResponse(ApiIds.ID_ADD_CENTER, "Internet connection problem.");
            }
        });
    }


    public void addGroup(AddGroupRequestModel addGroupRequestModel) {
        Call<BaseWebResponseModel> call = getApi().addGroup(addGroupRequestModel);
        call.enqueue(new Callback<BaseWebResponseModel>() {
            @Override
            public void onResponse(Call<BaseWebResponseModel> call, Response<BaseWebResponseModel> response) {
                apiHitListener.onSuccessResponse(ApiIds.ID_ADD_GROUP, response.body());
            }

            @Override
            public void onFailure(Call<BaseWebResponseModel> call, Throwable t) {
                apiHitListener.onFailResponse(ApiIds.ID_ADD_GROUP, "Internet connection problem.");
            }
        });
    }

    public void submitAppForm(AddLeadRequestModel addLeadRequestModel) {
        Call<LeadResponseModel> call = getApi().submitAppForm(addLeadRequestModel);
        call.enqueue(new Callback<LeadResponseModel>() {
            @Override
            public void onResponse(Call<LeadResponseModel> call, Response<LeadResponseModel> response) {
                apiHitListener.onSuccessResponse(ApiIds.ID_SUBMIT_APPFORM, response.body());
            }

            @Override
            public void onFailure(Call<LeadResponseModel> call, Throwable t) {
                apiHitListener.onFailResponse(ApiIds.ID_SUBMIT_APPFORM, "Internet connection problem.");
            }
        });
    }

    public void editAppForm(AddLeadRequestModel addLeadRequestModel) {
        Call<LeadResponseModel> call = getApi().editAppForm(addLeadRequestModel);
        call.enqueue(new Callback<LeadResponseModel>() {
            @Override
            public void onResponse(Call<LeadResponseModel> call, Response<LeadResponseModel> response) {
                apiHitListener.onSuccessResponse(ApiIds.ID_EDIT_APPFORM, response.body());
            }

            @Override
            public void onFailure(Call<LeadResponseModel> call, Throwable t) {
                apiHitListener.onFailResponse(ApiIds.ID_EDIT_APPFORM, "Internet connection problem.");
            }
        });
    }

    public void getLeads(String user_id) {
        Call<LeadListResponseModel> call = getApi().getLeads(user_id);
        call.enqueue(new Callback<LeadListResponseModel>() {
            @Override
            public void onResponse(Call<LeadListResponseModel> call, Response<LeadListResponseModel> response) {
                apiHitListener.onSuccessResponse(ApiIds.ID_GET_LEADS, response.body());
            }

            @Override
            public void onFailure(Call<LeadListResponseModel> call, Throwable t) {
                apiHitListener.onFailResponse(ApiIds.ID_GET_LEADS, "Internet connection problem.");
            }
        });
    }

    public void getUserAreaList(String user_id) {
        Call<UserArea> call = getApi().getUserAreaList(user_id);
        call.enqueue(new Callback<UserArea>() {
            @Override
            public void onResponse(Call<UserArea> call, Response<UserArea> response) {
                apiHitListener.onSuccessResponse(ApiIds.ID_USER_AREA_LIST, response.body());
            }

            @Override
            public void onFailure(Call<UserArea> call, Throwable t) {
                apiHitListener.onFailResponse(ApiIds.ID_USER_AREA_LIST, "Internet connection problem.");
            }
        });
    }

    public void getCompanyNameList(String user_id) {
        Call<CompanyList> call = getApi().getComapnyNameList(user_id);
        call.enqueue(new Callback<CompanyList>() {
            @Override
            public void onResponse(Call<CompanyList> call, Response<CompanyList> response) {
                apiHitListener.onSuccessResponse(ApiIds.ID_COMPANY_NAME, response.body());
            }

            @Override
            public void onFailure(Call<CompanyList> call, Throwable t) {
                apiHitListener.onFailResponse(ApiIds.ID_COMPANY_NAME, "Internet connection problem.");
            }
        });
    }

    public void getPurposesList(String user_id) {
        Call<CompanyList> call = getApi().getPurposeOfLoanList(user_id);
        call.enqueue(new Callback<CompanyList>() {
            @Override
            public void onResponse(Call<CompanyList> call, Response<CompanyList> response) {
                apiHitListener.onSuccessResponse(ApiIds.ID_PURPOSES, response.body());
            }

            @Override
            public void onFailure(Call<CompanyList> call, Throwable t) {
                apiHitListener.onFailResponse(ApiIds.ID_PURPOSES, "Internet connection problem.");
            }
        });
    }

    public void getBankNameList(String user_id) {
        Call<CompanyList> call = getApi().getBankNameList(user_id);
        call.enqueue(new Callback<CompanyList>() {
            @Override
            public void onResponse(Call<CompanyList> call, Response<CompanyList> response) {
                apiHitListener.onSuccessResponse(ApiIds.ID_BANK_NAME, response.body());
            }

            @Override
            public void onFailure(Call<CompanyList> call, Throwable t) {
                apiHitListener.onFailResponse(ApiIds.ID_BANK_NAME, "Internet connection problem.");
            }
        });
    }


    public void addAppointment(AddAppointmentRequestModel appointmentRequestModel) {
        Call<BaseWebResponseModel> call = getApi().addAppointment(appointmentRequestModel);
        call.enqueue(new Callback<BaseWebResponseModel>() {
            @Override
            public void onResponse(Call<BaseWebResponseModel> call, Response<BaseWebResponseModel> response) {
                apiHitListener.onSuccessResponse(ApiIds.ID_ADD_APPOINTMENT, response.body());
            }

            @Override
            public void onFailure(Call<BaseWebResponseModel> call, Throwable t) {
                apiHitListener.onFailResponse(ApiIds.ID_ADD_APPOINTMENT, "Internet connection problem.");
            }
        });
    }

    public void getAppointment(String user_id, String lead_id) {
        Call<AppointmentListResponseModel> call = getApi().getAppointment(user_id, lead_id);
        call.enqueue(new Callback<AppointmentListResponseModel>() {
            @Override
            public void onResponse(Call<AppointmentListResponseModel> call, Response<AppointmentListResponseModel> response) {
                apiHitListener.onSuccessResponse(ApiIds.ID_GET_APPOINTMENT, response.body());
            }

            @Override
            public void onFailure(Call<AppointmentListResponseModel> call, Throwable t) {
                apiHitListener.onFailResponse(ApiIds.ID_GET_APPOINTMENT, "Internet connection problem.");
            }
        });
    }

    public void deleteAppointment(String id, String user_id) {
        Call<BaseWebResponseModel> call = getApi().deleteAppointment(id, user_id);
        call.enqueue(new Callback<BaseWebResponseModel>() {
            @Override
            public void onResponse(Call<BaseWebResponseModel> call, Response<BaseWebResponseModel> response) {
                apiHitListener.onSuccessResponse(ApiIds.ID_DELETE_APPOINTMENT, response.body());
            }

            @Override
            public void onFailure(Call<BaseWebResponseModel> call, Throwable t) {
                apiHitListener.onFailResponse(ApiIds.ID_DELETE_APPOINTMENT, "Internet connection problem.");
            }
        });
    }

    public void addFi(AddFiRequestModel fiModel) {
        Call<BaseWebResponseModel> call = getApi().addFi(fiModel);
        call.enqueue(new Callback<BaseWebResponseModel>() {
            @Override
            public void onResponse(Call<BaseWebResponseModel> call, Response<BaseWebResponseModel> response) {
                apiHitListener.onSuccessResponse(ApiIds.ID_ADD_FI, response.body());
            }

            @Override
            public void onFailure(Call<BaseWebResponseModel> call, Throwable t) {
                apiHitListener.onFailResponse(ApiIds.ID_ADD_FI, "Internet connection problem.");
            }
        });
    }

    public void editFi(AddFiRequestModel fiModel) {
        Call<BaseWebResponseModel> call = getApi().editFi(fiModel);
        call.enqueue(new Callback<BaseWebResponseModel>() {
            @Override
            public void onResponse(Call<BaseWebResponseModel> call, Response<BaseWebResponseModel> response) {
                apiHitListener.onSuccessResponse(ApiIds.ID_EDIT_FI, response.body());
            }

            @Override
            public void onFailure(Call<BaseWebResponseModel> call, Throwable t) {
                apiHitListener.onFailResponse(ApiIds.ID_EDIT_FI, "Internet connection problem.");
            }
        });
    }

    public void getFi(String user_id) {
        Call<FiListResponseModel> call = getApi().getFi(user_id);
        call.enqueue(new Callback<FiListResponseModel>() {
            @Override
            public void onResponse(Call<FiListResponseModel> call, Response<FiListResponseModel> response) {
                apiHitListener.onSuccessResponse(ApiIds.ID_GET_FI, response.body());
            }

            @Override
            public void onFailure(Call<FiListResponseModel> call, Throwable t) {
                apiHitListener.onFailResponse(ApiIds.ID_GET_FI, "Internet connection problem.");
            }
        });
    }


    public void addCGT(AddCGTRequestModel cgtRequestModel) {
        Call<BaseWebResponseModel> call = getApi().addCGT(cgtRequestModel);
        call.enqueue(new Callback<BaseWebResponseModel>() {
            @Override
            public void onResponse(Call<BaseWebResponseModel> call, Response<BaseWebResponseModel> response) {
                apiHitListener.onSuccessResponse(ApiIds.ID_ADD_CGT, response.body());
            }

            @Override
            public void onFailure(Call<BaseWebResponseModel> call, Throwable t) {
                apiHitListener.onFailResponse(ApiIds.ID_ADD_CGT, "Internet connection problem.");
            }
        });
    }

    public void addGRT(AddGrtRequestModel grtRequestModel) {
        Call<BaseWebResponseModel> call = getApi().addGRT(grtRequestModel);
        call.enqueue(new Callback<BaseWebResponseModel>() {
            @Override
            public void onResponse(Call<BaseWebResponseModel> call, Response<BaseWebResponseModel> response) {
                apiHitListener.onSuccessResponse(ApiIds.ID_ADD_GRT, response.body());
            }

            @Override
            public void onFailure(Call<BaseWebResponseModel> call, Throwable t) {
                apiHitListener.onFailResponse(ApiIds.ID_ADD_GRT, "Internet connection problem.");
            }
        });
    }

    public void getProfile(String user_id) {
        Call<LeadListResponseModel> call = getApi().getLeads(user_id);
        call.enqueue(new Callback<LeadListResponseModel>() {
            @Override
            public void onResponse(Call<LeadListResponseModel> call, Response<LeadListResponseModel> response) {
                apiHitListener.onSuccessResponse(ApiIds.ID_GET_PROFILE, response.body());
            }

            @Override
            public void onFailure(Call<LeadListResponseModel> call, Throwable t) {
                apiHitListener.onFailResponse(ApiIds.ID_GET_PROFILE, "Internet connection problem.");
            }
        });
    }

    public void getRecoveryGroups(String user_id, String date) {
        Call<RecoveryGroupModel> call = getApi().getRecoveryGroups(user_id, date);
        call.enqueue(new Callback<RecoveryGroupModel>() {
            @Override
            public void onResponse(Call<RecoveryGroupModel> call, Response<RecoveryGroupModel> response) {
                apiHitListener.onSuccessResponse(ApiIds.ID_GET_RECOVERY_GROUPS, response.body());
            }

            @Override
            public void onFailure(Call<RecoveryGroupModel> call, Throwable t) {
                apiHitListener.onFailResponse(ApiIds.ID_GET_RECOVERY_GROUPS, "Internet connection problem.");
            }
        });
    }

    public void getRecoveryMembers(String group_id, String date) {
        Call<GroupMemberListModel> call = getApi().getRecoveryMembers(group_id, date);
        call.enqueue(new Callback<GroupMemberListModel>() {
            @Override
            public void onResponse(Call<GroupMemberListModel> call, Response<GroupMemberListModel> response) {
                apiHitListener.onSuccessResponse(ApiIds.ID_GET_RECOVERY_MEMBERS, response.body());
            }

            @Override
            public void onFailure(Call<GroupMemberListModel> call, Throwable t) {
                apiHitListener.onFailResponse(ApiIds.ID_GET_RECOVERY_MEMBERS, "Internet connection problem.");
            }
        });
    }

    public void submitRecoveryCollection(SubmitRecoveryDetailsRequestModel detailsRequestModel) {
        Call<SubmitRecoveryDetailsModel> call = getApi().submitRecoveryCollection(detailsRequestModel);
        call.enqueue(new Callback<SubmitRecoveryDetailsModel>() {
            @Override
            public void onResponse(Call<SubmitRecoveryDetailsModel> call, Response<SubmitRecoveryDetailsModel> response) {
                apiHitListener.onSuccessResponse(ApiIds.ID_SUBMIT_RECOVERY_DETAILS, response.body());
            }

            @Override
            public void onFailure(Call<SubmitRecoveryDetailsModel> call, Throwable t) {
                apiHitListener.onFailResponse(ApiIds.ID_SUBMIT_RECOVERY_DETAILS, "Internet connection problem.");
            }
        });
    }


    public void getEmiHistory(String group_id, String date, String application_area_id) {
        Call<GetEmiHistoryModel> call = getApi().getEmiHistory(group_id, date, application_area_id);
        call.enqueue(new Callback<GetEmiHistoryModel>() {
            @Override
            public void onResponse(Call<GetEmiHistoryModel> call, Response<GetEmiHistoryModel> response) {
                apiHitListener.onSuccessResponse(ApiIds.ID_EMI_HISTORY, response.body());
            }

            @Override
            public void onFailure(Call<GetEmiHistoryModel> call, Throwable t) {
                apiHitListener.onFailResponse(ApiIds.ID_EMI_HISTORY, "Internet connection problem.");
            }
        });
    }

    public void applicationClosed(String application_area_id) {
        Call<RecoveryApplicationClosedModel> call = getApi().applicationClosed(application_area_id);
        call.enqueue(new Callback<RecoveryApplicationClosedModel>() {
            @Override
            public void onResponse(Call<RecoveryApplicationClosedModel> call, Response<RecoveryApplicationClosedModel> response) {
                apiHitListener.onSuccessResponse(ApiIds.ID_APPLICATION_CLOSED, response.body());
            }

            @Override
            public void onFailure(Call<RecoveryApplicationClosedModel> call, Throwable t) {
                apiHitListener.onFailResponse(ApiIds.ID_APPLICATION_CLOSED, "Internet connection problem.");
            }
        });
    }


}