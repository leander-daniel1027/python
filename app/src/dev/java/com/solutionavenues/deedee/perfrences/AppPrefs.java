package com.solutionavenues.deedee.perfrences;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.preferences.BasePrefs;
import com.solutionavenues.deedee.model.response.BranchListResponseModel;
import com.solutionavenues.deedee.model.response.CompanyList;
import com.solutionavenues.deedee.model.response.GetLeadFieldsResponseModel;
import com.solutionavenues.deedee.model.response.GroupListResponseModel;
import com.solutionavenues.deedee.model.response.ProductListResponseModel;
import com.solutionavenues.deedee.model.response.UserArea;

import java.lang.reflect.Type;
import java.util.ArrayList;


/**
 * Created by bitu on 24/9/17.
 */

public class AppPrefs extends BasePrefs {

    static final String Prefsname = "prefs_app";
    public static final String KEY_USER_DATA = "user_data";
    Context context;

    public static final String KEY_TERMS_CONDITION = "terms_conditions";
    public static final String KEY_PRIVACY_POLICY = "privacy_policy";
    public static final String KEY_ABOUT_US = "about_us";
    public static final String KEY_FAQ_DATA = "faq_data";
    public static final String KEY_DEVICE_TOKEN = "faq_data";
    public static final String KEY_SINGLE_COIN_PRICE = "single_coin_price";
    public static final String KEY_LOCATION_ALARM_STATUS = "alarm_status";
    public static final String KEY_CURRENT_LATITUDE = "latitude";
    public static final String KEY_CURRENT_LONGITUDE = "longitude";
    public static final String KEY_TOTAL_COIN = "total_coin";
    public static final String KEY_AUTO_START = "auto_start";
    public static final String KEY_SERVER = "server";
    public static final String KEY_COIN_MODULE = "coin_module";

    public AppPrefs(Context context) {
        this.context = context;
    }

    @Override
    public String getPrefsName() {
        return Prefsname;
    }

    @Override
    public Context getContext() {
        return context;
    }


    public void saveDeviceToken(String data) {
        setStringKeyValuePrefs(KEY_DEVICE_TOKEN, data);
    }

    public String getDeviceToken() {
        return getStringKeyValuePrefs(KEY_DEVICE_TOKEN);
    }



    public void saveCoinModuleVisibility(boolean data) {
        setBooleanKeyValuePrefs(KEY_COIN_MODULE, data);
    }

    public boolean getCoinModuleVisibility() {
        return getBooleanKeyValuePrefs(KEY_COIN_MODULE);
    }

    public void saveCoinPrice(String data) {
        setStringKeyValuePrefs(KEY_SINGLE_COIN_PRICE, data);
    }

    public String getCoinPrice() {
        return getStringKeyValuePrefs(KEY_SINGLE_COIN_PRICE);
    }

    public void saveLocationAlarmStatus(boolean data) {
        setBooleanKeyValuePrefs(KEY_LOCATION_ALARM_STATUS, data);
    }

    public boolean getLocationAlarmStatus() {
        return getBooleanKeyValuePrefs(KEY_LOCATION_ALARM_STATUS);
    }

    public void saveCurrentLatitude(String data) {
        setStringKeyValuePrefs(KEY_CURRENT_LATITUDE, data);
    }

    public String getCurrentLatitude() {
        return getStringKeyValuePrefs(KEY_CURRENT_LATITUDE);
    }

    public void saveCurrentLongitude(String data) {
        setStringKeyValuePrefs(KEY_CURRENT_LONGITUDE, data);
    }

    public String getCurrentLongitude() {
        return getStringKeyValuePrefs(KEY_CURRENT_LONGITUDE);
    }

    public void saveTotalCoin(String data) {
        setStringKeyValuePrefs(KEY_TOTAL_COIN, data);
    }

    public String getTotalCoin() {
        return getStringKeyValuePrefs(KEY_TOTAL_COIN);
    }

 public void saveAutoStart(boolean data) {
        setBooleanKeyValuePrefs(KEY_AUTO_START, data);
    }

    public boolean getAutoStart() {
        return getBooleanKeyValuePrefs(KEY_AUTO_START);
    }

public void saveCurrentServer(String data) {
        setStringKeyValuePrefs(KEY_SERVER, data);
    }

    public String getCurrentServer() {
        return getStringKeyValuePrefs(KEY_SERVER);
    }


    public GroupListResponseModel getGroupList() {
        Gson gson = new Gson();
        SharedPreferences prefs = getContext().getSharedPreferences(getPrefsName(),
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        String json = prefs.getString("GROUP_LIST", "");
        GroupListResponseModel obj = gson.fromJson(json, GroupListResponseModel.class);
        return obj;
    }
    public void setGroupList(GroupListResponseModel result) {
        SharedPreferences prefs = getContext().getSharedPreferences(getPrefsName(),
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(result);
        editor.putString("GROUP_LIST", json);
        editor.commit();
    }

    public ProductListResponseModel getProductList() {
        Gson gson = new Gson();
        SharedPreferences prefs = getContext().getSharedPreferences(getPrefsName(),
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        String json = prefs.getString("PRODUCT_LIST", "");
        ProductListResponseModel obj = gson.fromJson(json, ProductListResponseModel.class);
        return obj;
    }
    public void setProductList(ProductListResponseModel result) {
        SharedPreferences prefs = getContext().getSharedPreferences(getPrefsName(),
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(result);
        editor.putString("PRODUCT_LIST", json);
        editor.commit();
    }

    public GetLeadFieldsResponseModel getDynamicFieldsList() {
        Gson gson = new Gson();
        SharedPreferences prefs = getContext().getSharedPreferences(getPrefsName(),
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        String json = prefs.getString("LEAD_FIELD_LIST", "");
        GetLeadFieldsResponseModel obj = gson.fromJson(json, GetLeadFieldsResponseModel.class);
        return obj;
    }
    public void setDynamicFieldsList(GetLeadFieldsResponseModel result) {
        SharedPreferences prefs = getContext().getSharedPreferences(getPrefsName(),
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(result);
        editor.putString("LEAD_FIELD_LIST", json);
        editor.commit();
    }

    public UserArea getUserAreaList() {
        Gson gson = new Gson();
        SharedPreferences prefs = getContext().getSharedPreferences(getPrefsName(),
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        String json = prefs.getString("USER_LIST", "");
        UserArea obj = gson.fromJson(json, UserArea.class);
        return obj;
    }
    public void setUserAreaList(UserArea result) {
        SharedPreferences prefs = getContext().getSharedPreferences(getPrefsName(),
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(result);
        editor.putString("USER_LIST", json);
        editor.commit();
    }

    public CompanyList getCompanyNameList() {
        Gson gson = new Gson();
        SharedPreferences prefs = getContext().getSharedPreferences(getPrefsName(),
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        String json = prefs.getString("COMPANY_LIST", "");
        CompanyList obj = gson.fromJson(json, CompanyList.class);
        return obj;
    }
    public void setCompanyNameList(CompanyList result) {
        SharedPreferences prefs = getContext().getSharedPreferences(getPrefsName(),
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(result);
        editor.putString("COMPANY_LIST", json);
        editor.commit();
    }

    public CompanyList getBankNameList() {
        Gson gson = new Gson();
        SharedPreferences prefs = getContext().getSharedPreferences(getPrefsName(),
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        String json = prefs.getString("BANK_LIST", "");
        CompanyList obj = gson.fromJson(json, CompanyList.class);
        return obj;
    }
    public void setBankNameList(CompanyList result) {
        SharedPreferences prefs = getContext().getSharedPreferences(getPrefsName(),
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(result);
        editor.putString("BANK_LIST", json);
        editor.commit();
    }

    public CompanyList getPurposeOfLoanList() {
        Gson gson = new Gson();
        SharedPreferences prefs = getContext().getSharedPreferences(getPrefsName(),
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        String json = prefs.getString("PURPOSE_LIST", "");
        CompanyList obj = gson.fromJson(json, CompanyList.class);
        return obj;
    }
    public void setPurposeOfLoanList(CompanyList result) {
        SharedPreferences prefs = getContext().getSharedPreferences(getPrefsName(),
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(result);
        editor.putString("PURPOSE_LIST", json);
        editor.commit();
    }
    public BranchListResponseModel getBranchList() {
        Gson gson = new Gson();
        SharedPreferences prefs = getContext().getSharedPreferences(getPrefsName(),
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        String json = prefs.getString("BRANCH_LIST", "");
        BranchListResponseModel obj = gson.fromJson(json, BranchListResponseModel.class);
        return obj;
    }
    public void setBranchList(BranchListResponseModel result) {
        SharedPreferences prefs = getContext().getSharedPreferences(getPrefsName(),
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(result);
        editor.putString("BRANCH_LIST", json);
        editor.commit();
    }

}
