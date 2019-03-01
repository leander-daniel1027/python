package com.solutionavenues.deedee.database.tables;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.solutionavenues.deedee.MyApplication;
import com.solutionavenues.deedee.model.request.AddLeadRequestModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tan on 1/26/2016.
 */
public class LeadTable {


    private String TABLE_NAME_LEADS = "leads";
    public String LOCAL_ID = "id";
    public String LEAD_ID = "lead_id";
    public String SHOULD_UPDATE = "SHOULD_UPDATE";
    public String ADDRESS_INFO = "addressInfo";
    public String CHEQUE_INFO = "chequeInfo";
    public String DECLARATION_INFO = "declarationInfo";
    public String EXISTINGLOAN_INFO = "existingLoanInfo";
    public String FAMILYPROFILE_INFO = "familyProfileInfo";
    public String HOUSEHOLD_INFO = "houseHoldInfo";
    public String KYC_INFO = "kycInfo";
    public String LOANPERSONEL_INFO = "loanPersonelInfo";
    public String SAVINGS_INFO = "savingsInfo";


    public long insert(AddLeadRequestModel addLeadRequestModel) {
        SQLiteDatabase db = MyApplication.getInstance().getDatabasehelper().openDatabase();
        ContentValues values = new ContentValues();
        values.put(LEAD_ID, getStringJson(addLeadRequestModel.getLead_id()));
        values.put(LOANPERSONEL_INFO, getStringJson(addLeadRequestModel.getLoanPersonelInfo()));
        values.put(ADDRESS_INFO, getStringJson(addLeadRequestModel.getAddressInfo()));
        values.put(FAMILYPROFILE_INFO, getStringJson(addLeadRequestModel.getFamilyProfile()));
        values.put(HOUSEHOLD_INFO, getStringJson(addLeadRequestModel.getHouseHoldInfo()));
        values.put(SAVINGS_INFO, getStringJson(addLeadRequestModel.getSavingsInfo()));
        values.put(EXISTINGLOAN_INFO, getStringJson(addLeadRequestModel.getExistingLoanInfoList()));
        values.put(CHEQUE_INFO, getStringJson(addLeadRequestModel.getChequeInfo()));
        values.put(KYC_INFO, getStringJson(addLeadRequestModel.getKycInfoList()));
        values.put(DECLARATION_INFO, getStringJson(addLeadRequestModel.getDeclarationInfo()));

        // Inserting Row
        long id = db.insert(TABLE_NAME_LEADS, null, values);
        MyApplication.getInstance().getDatabasehelper().closeDataBase();
        return id;

    }


    public void delete(int id) {
        SQLiteDatabase db = MyApplication.getInstance().getDatabasehelper().openDatabase();
        db.delete(TABLE_NAME_LEADS, LOCAL_ID + "= ?" , new String[]{String.valueOf(id)});
        MyApplication.getInstance().getDatabasehelper().closeDataBase();
    }

    public int updateLeadColumn(int id, String columnName, String data) {
        SQLiteDatabase db = MyApplication.getInstance().getDatabasehelper().openDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(columnName, data);
        int rowid = db.update(TABLE_NAME_LEADS, contentValues, LOCAL_ID + "= ?" , new String[]{String.valueOf(id)});
        MyApplication.getInstance().getDatabasehelper().closeDataBase();
        return rowid;
    }

    public int updateLeadRecord(int id, AddLeadRequestModel addLeadRequestModel) {
        SQLiteDatabase db = MyApplication.getInstance().getDatabasehelper().openDatabase();
        ContentValues values = new ContentValues();
        values.put(LOANPERSONEL_INFO, getStringJson(addLeadRequestModel.getLoanPersonelInfo()));
        values.put(ADDRESS_INFO, getStringJson(addLeadRequestModel.getAddressInfo()));
        values.put(FAMILYPROFILE_INFO, getStringJson(addLeadRequestModel.getFamilyProfile()));
        values.put(HOUSEHOLD_INFO, getStringJson(addLeadRequestModel.getHouseHoldInfo()));
        values.put(SAVINGS_INFO, getStringJson(addLeadRequestModel.getSavingsInfo()));
        values.put(EXISTINGLOAN_INFO, getStringJson(addLeadRequestModel.getExistingLoanInfoList()));
        values.put(CHEQUE_INFO, getStringJson(addLeadRequestModel.getChequeInfo()));
        values.put(KYC_INFO, getStringJson(addLeadRequestModel.getKycInfoList()));
        values.put(DECLARATION_INFO, getStringJson(addLeadRequestModel.getDeclarationInfo()));

        int rowid = db.update(TABLE_NAME_LEADS, values, LOCAL_ID  +"= ?" , new String[]{String.valueOf(id)});
        MyApplication.getInstance().getDatabasehelper().closeDataBase();
        return rowid;
    }

    public ArrayList<AddLeadRequestModel> getAllLeads() {
        SQLiteDatabase db = MyApplication.getInstance().getDatabasehelper().getReadableDatabase();
        ArrayList<AddLeadRequestModel> list = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME_LEADS + " ORDER BY " + LOCAL_ID + " DESC", null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                AddLeadRequestModel leadRequestModel = new AddLeadRequestModel();
                leadRequestModel.setLocal_id(cursor.getString(cursor.getColumnIndex(LOCAL_ID)));
                leadRequestModel.setLead_id(cursor.getString(cursor.getColumnIndex(LEAD_ID)));
                leadRequestModel.setLoanPersonelInfo((AddLeadRequestModel.LoanPersonelInfo) getDataObject(AddLeadRequestModel.LoanPersonelInfo.class, cursor.getString(cursor.getColumnIndex(LOANPERSONEL_INFO))));
                leadRequestModel.setAddressInfo((AddLeadRequestModel.AddressInfo) getDataObject(AddLeadRequestModel.AddressInfo.class, cursor.getString(cursor.getColumnIndex(ADDRESS_INFO))));
                leadRequestModel.setFamilyProfile((AddLeadRequestModel.FamilyProfile) getDataObject(AddLeadRequestModel.FamilyProfile.class, cursor.getString(cursor.getColumnIndex(FAMILYPROFILE_INFO))));
                leadRequestModel.setHouseHoldInfo((AddLeadRequestModel.MonthlyHouseHoldInfo) getDataObject(AddLeadRequestModel.MonthlyHouseHoldInfo.class, cursor.getString(cursor.getColumnIndex(HOUSEHOLD_INFO))));
                leadRequestModel.setSavingsInfo((AddLeadRequestModel.MonthlySavingsInfo) getDataObject(AddLeadRequestModel.MonthlySavingsInfo.class, cursor.getString(cursor.getColumnIndex(SAVINGS_INFO))));
                leadRequestModel.setExistingLoanInfoList(getDataListObject(new TypeToken<List<AddLeadRequestModel.ExistingLoanInfo>>() {
                }, cursor.getString(cursor.getColumnIndex(EXISTINGLOAN_INFO))));
                leadRequestModel.setChequeInfo(getDataListObject(new TypeToken<List<AddLeadRequestModel.ChequeInfo>>() {
                }, cursor.getString(cursor.getColumnIndex(CHEQUE_INFO))));
                leadRequestModel.setKycInfoList(getDataListObject(new TypeToken<List<AddLeadRequestModel.KycInfo>>() {
                }, cursor.getString(cursor.getColumnIndex(KYC_INFO))));
                leadRequestModel.setDeclarationInfo((AddLeadRequestModel.DeclarationInfo) getDataObject(AddLeadRequestModel.DeclarationInfo.class, cursor.getString(cursor.getColumnIndex(DECLARATION_INFO))));

                list.add(leadRequestModel);
            }
            cursor.close();
            db.close();
        }
        return list;
    }

    public boolean isValueExist(String column_name, int value) {
        SQLiteDatabase db = MyApplication.getInstance().getDatabasehelper().getReadableDatabase();
        String Query = "Select * from " + TABLE_NAME_LEADS + " where " + column_name + " = " + value;
        Cursor cursor = db.rawQuery(Query, null);
        if(cursor.getCount() <= 0){
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }

    public String getStringJson(Object data) {
        Gson gson = new Gson();
        String dataJson = gson.toJson(data);
        return dataJson;
    }

    public Object getDataObject(Class aClass, String json) {
        Gson gson = new Gson();
        Object dataJson = gson.fromJson(json, aClass);
        return dataJson;
    }


    public ArrayList getDataListObject(TypeToken typeToken, String json) {
        Gson gson = new Gson();
        ArrayList list = gson.fromJson(json, typeToken.getType());
        return list;
    }

}
