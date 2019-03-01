package com.solutionavenues.deedee.rest;


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
import com.solutionavenues.deedee.util.Constants;

import java.util.HashMap;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;

public interface Rest {

    @FormUrlEncoded
    @POST(BaseArguments.LOGIN)
    Call<LoginResponseModel> loginUser(@Field(BaseArguments.ARG_USERNAME) String username,
                                       @Field(BaseArguments.ARG_PASSWORD) String password,
                                       @Field(BaseArguments.ARG_DEVICE_ID) String device_token);

    @FormUrlEncoded
    @POST(BaseArguments.LOGOUT)
    Call<BaseWebResponseModel> logoutUser(@Field(BaseArguments.ARG_USER_ID) String user_id,@Field(BaseArguments.ARG_IS_LOGOUT) String isLogout);


    @FormUrlEncoded
    @POST(BaseArguments.FORGOT_PASSWORD)
    Call<BaseWebResponseModel> forgotPassword(@Field(BaseArguments.ARG_EMAIL) String email);

    @FormUrlEncoded
    @POST(BaseArguments.CHANGE_PASSWORD)
    Call<BaseWebResponseModel> changePassword(@Field(BaseArguments.ARG_USER_ID) String user_id,
                                              @Field(BaseArguments.ARG_OLD_PASSWORD) String old_password,
                                              @Field(BaseArguments.ARG_NEW_PASSWORD) String new_password);

    @POST(BaseArguments.ADD_ENQUIRY)
    Call<AddEnquiryResponseModel> addEnquiry(@Body AddEditEnquiryRequestModel enquiryRequestModel);


    @Multipart
    @POST(BaseArguments.IMAGE_UPLOAD)
    Call<UploadImageResponseModel> uploadImage(@Part MultipartBody.Part file,
                                               @PartMap HashMap<String, RequestBody> multipartRequest);

    @Multipart
    @POST(BaseArguments.IMAGE_UPLOAD)
    Call<UploadImageResponseModel> uploadImage(@Part MultipartBody.Part file);


    @FormUrlEncoded
    @POST(BaseArguments.GET_ENQUIRY_LIST)
    Call<EnquiryListResponseModel> getEnquiryList(@Field(BaseArguments.ARG_USER_ID) String user_id);

    @FormUrlEncoded
    @POST(BaseArguments.ADD_LOCATION)
    Call<BaseWebResponseModel> addLocation(
            @Field(BaseArguments.ARG_USER_ID) String user_id,
            @Field(BaseArguments.ARG_LATITUDE) String latitude,
            @Field(BaseArguments.ARG_LONGITUDE) String longitude);

    @FormUrlEncoded
    @POST(BaseArguments.GET_MICRO_ENTERPRISE)
    Call<LocalityMicroEnterPriseModel> getMicroEnterpriseList(@Field(BaseArguments.ARG_USER_ID) String user_id);


    @POST(BaseArguments.EDIT_ENQUIRY)
    Call<AddEnquiryResponseModel> editEnquiry(@Body AddEditEnquiryRequestModel enquiryRequestModel);

    @POST(BaseArguments.ADD_LOCALITY)
    Call<BaseWebResponseModel> addLocality(@Body AddLocalityRequestModel localityRequestModel);

    @GET(Constants.GOOGLE_API_GEOCODE)
    Call<LatLngFromAddressModel> getLatLngAddress(@Query(BaseArguments.ARG_LATLNG) String latlng);

    @GET(BaseArguments.GET_BRANCHES)
    Call<BranchListResponseModel> getBranchList();

    @FormUrlEncoded
    @POST(BaseArguments.GET_GROUP)
    Call<GroupListResponseModel> getGroupList(@Field(BaseArguments.ARG_USER_ID) String user_id);

    @GET(BaseArguments.GET_PRODUCT)
    Call<ProductListResponseModel> getProductList();

    @GET(BaseArguments.GET_CENTER)
    Call<CenterListResponseModel> getCenterList();

    @GET(BaseArguments.GET_LEAD_FIELDS)
    Call<GetLeadFieldsResponseModel> getLeadFields();


    @POST(BaseArguments.ADD_CENTER)
    Call<AddCenterResponseModel> addCenter(@Body AddCenterRequestModel centerRequestModel);


    @POST(BaseArguments.ADD_GROUP)
    Call<BaseWebResponseModel> addGroup(@Body AddGroupRequestModel addGroupRequestModel);

    @POST(BaseArguments.ADD_APPLICATION_FORM)
    Call<LeadResponseModel> submitAppForm(@Body AddLeadRequestModel addLeadRequestModel);

    @POST(BaseArguments.EDIT_APPLICATION_FORM)
    Call<LeadResponseModel> editAppForm(@Body AddLeadRequestModel addLeadRequestModel);

    @FormUrlEncoded
    @POST(BaseArguments.GET_LEADS)
    Call<LeadListResponseModel> getLeads(@Field(BaseArguments.ARG_USER_ID) String user_id);

    @FormUrlEncoded
    @POST(BaseArguments.GET_USER_AREA_LIST)
    Call<UserArea> getUserAreaList(@Field(BaseArguments.ARG_USER_ID) String user_id);

    @FormUrlEncoded
    @POST(BaseArguments.ARG_COMPANY_NAME)
    Call<CompanyList> getComapnyNameList(@Field(BaseArguments.ARG_USER_ID) String user_id);

    @FormUrlEncoded
    @POST(BaseArguments.ARG_BANK_NAME)
    Call<CompanyList> getBankNameList(@Field(BaseArguments.ARG_USER_ID) String user_id);

    @FormUrlEncoded
    @POST(BaseArguments.ARG_PURPOSE_OF_LOAN)
    Call<CompanyList> getPurposeOfLoanList(@Field(BaseArguments.ARG_USER_ID) String user_id);



    @POST(BaseArguments.ADD_APPOINTMENT)
    Call<BaseWebResponseModel> addAppointment(@Body AddAppointmentRequestModel appointmentRequestModel);

    @FormUrlEncoded
    @POST(BaseArguments.GET_APPOINTMENT)
    Call<AppointmentListResponseModel> getAppointment(
            @Field(BaseArguments.ARG_USER_ID) String user_id,
            @Field(BaseArguments.ARG_LEAD_ID) String lead_id);

    @FormUrlEncoded
    @POST(BaseArguments.DELETE_APPOINTMENT)
    Call<BaseWebResponseModel> deleteAppointment(
            @Field(BaseArguments.ARG_ID) String id,
            @Field(BaseArguments.ARG_DELETED_BY) String user_id);


    @POST(BaseArguments.ADD_FI)
    Call<BaseWebResponseModel> addFi(@Body AddFiRequestModel fiModel);

    @POST(BaseArguments.EDIT_FI)
    Call<BaseWebResponseModel> editFi(@Body AddFiRequestModel fiModel);

    @FormUrlEncoded
    @POST(BaseArguments.GET_FI)
    Call<FiListResponseModel> getFi(@Field(BaseArguments.ARG_USER_ID) String user_id);

    @POST(BaseArguments.ADD_CGT)
    Call<BaseWebResponseModel> addCGT(@Body AddCGTRequestModel cgtRequestModel);

    @FormUrlEncoded
    @POST(BaseArguments.GET_GROUP_LIST)
    Call<GroupListResponseModel> getMyGroups(@Field(BaseArguments.ARG_USER_ID) String user_id);

    @FormUrlEncoded
    @POST(BaseArguments.GET_MEMBERS)
    Call<MemberListResponseModel> getGroupMembers(@Field(BaseArguments.ARG_GROUP_ID) String group_id);

    @POST(BaseArguments.ADD_GRT)
    Call<BaseWebResponseModel> addGRT(@Body AddGrtRequestModel grtRequestModel);

    @FormUrlEncoded
    @POST(BaseArguments.GET_LEADS)
    Call<LeadListResponseModel> getProfile(@Field(BaseArguments.ARG_USER_ID) String user_id);


    @FormUrlEncoded
    @POST(BaseArguments.GET_RECOVERY_GROUPS)
    Call<RecoveryGroupModel> getRecoveryGroups(
            @Field(BaseArguments.ARG_USER_ID) String user_id,
            @Field(BaseArguments.ARG_RECOVERY_DATE) String date
    );

    @FormUrlEncoded
    @POST(BaseArguments.GET_RECOVERY_MEMBERS)
    Call<GroupMemberListModel> getRecoveryMembers(
            @Field(BaseArguments.ARG_GROUP_ID) String group_id,
            @Field(BaseArguments.ARG_RECOVERY_DATE) String date);


    @POST(BaseArguments.GET_SUBMIT_RECOVERY)
    Call<SubmitRecoveryDetailsModel> submitRecoveryCollection(
            @Body SubmitRecoveryDetailsRequestModel requestModel);


    @FormUrlEncoded
    @POST(BaseArguments.GET_EMI_HISTORY)
    Call<GetEmiHistoryModel> getEmiHistory(
            @Field(BaseArguments.ARG_GROUP_ID) String group_id,
            @Field(BaseArguments.ARG_RECOVERY_DATE) String date,
            @Field(BaseArguments.ARG_APPLICATION_AREA_ID) String application_area_id
    );

    @FormUrlEncoded
    @POST(BaseArguments.APPLICATION_CLOSED)
    Call<RecoveryApplicationClosedModel> applicationClosed(
            @Field(BaseArguments.ARG_APPLICATION_AREA_ID) String application_area_id);



}
