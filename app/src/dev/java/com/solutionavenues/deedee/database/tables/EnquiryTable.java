package com.solutionavenues.deedee.database.tables;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.solutionavenues.deedee.MyApplication;
import com.solutionavenues.deedee.model.request.AddEditEnquiryRequestModel;

import java.util.ArrayList;

/**
 * Created by Tan on 1/26/2016.
 */
public class EnquiryTable {


    private String TABLE_NAME_ENQUIRY = "enquiries";
    private String ENQUIRY_ID = "id";
    private String APPLICANT_NAME = "applicant_name";
    private String APPLICANT_FATHER_NAME = "applicant_father_name";
    private String APPLICANT_MOTHER_NAME = "applicant_mother_name";
    private String APPLICANT_SPOUSE_NAME = "spouse_name";
    private String APPLICANT_DOB = "applicant_dob";
    private String APPLICANT_AADHAR_NO = "applicant_aadhar_no";
    private String APPLICANT_ID_NO = "applicant_id_number";
    private String APPLICANT_MOBILE1 = "applicant_mobile1";
    private String APPLICANT_MOBILE2 = "applicant_mobile2";
    private String APPLICANT_ADDRESS = "applicant_address";
    private String APPLICANT_ADDED_FROM = "added_form";
    private String APPLICANT_AADHAR_IMAGE = "applicant_aadhar_image";
    private String APPLICANT_ID_IMAGE = "applicant_id_image";
    private String SERVER_ID = "server_id";


    public long insert(AddEditEnquiryRequestModel enquiryModel) {
        SQLiteDatabase db = MyApplication.getInstance().getDatabasehelper().openDatabase();
        ContentValues values = new ContentValues();
        values.put(APPLICANT_NAME, enquiryModel.getApplicant_name());
        values.put(APPLICANT_FATHER_NAME, enquiryModel.getApplicant_father_name());
        values.put(APPLICANT_MOTHER_NAME, enquiryModel.getApplicant_mother_name());
        values.put(APPLICANT_SPOUSE_NAME, enquiryModel.getSpouse_name());
        values.put(APPLICANT_DOB, enquiryModel.getApplicant_dob());
        values.put(APPLICANT_AADHAR_NO, enquiryModel.getApplicant_aadhar_no());
        values.put(APPLICANT_ID_NO, enquiryModel.getApplicant_id_number());
        values.put(APPLICANT_MOBILE1, enquiryModel.getApplicant_mobile1());
        values.put(APPLICANT_MOBILE2, enquiryModel.getApplicant_mobile2());
        values.put(APPLICANT_ADDRESS, enquiryModel.getApplicant_address());
        values.put(APPLICANT_ADDED_FROM, enquiryModel.getAdded_form());
        values.put(APPLICANT_AADHAR_IMAGE, enquiryModel.getApplicant_aadhar_image());
        values.put(APPLICANT_ID_IMAGE, enquiryModel.getApplicant_id_image());
        values.put(SERVER_ID, enquiryModel.getServer_id());
        // Inserting Row
        long id = db.insert(TABLE_NAME_ENQUIRY, null, values);
        MyApplication.getInstance().getDatabasehelper().closeDataBase();
        return id;

    }


    public void delete(int id) {
        SQLiteDatabase db = MyApplication.getInstance().getDatabasehelper().openDatabase();
        db.delete(TABLE_NAME_ENQUIRY, ENQUIRY_ID = "" + id, null);
        MyApplication.getInstance().getDatabasehelper().closeDataBase();
    }

    public void updateServerIdOfEnquiry(int id, String serverId) {
        SQLiteDatabase db = MyApplication.getInstance().getDatabasehelper().openDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(SERVER_ID, serverId);
        db.update(TABLE_NAME_ENQUIRY, contentValues, ENQUIRY_ID = "" + id, null);
        MyApplication.getInstance().getDatabasehelper().closeDataBase();
    }


    public ArrayList<AddEditEnquiryRequestModel> getAllEnquiries() {
        SQLiteDatabase db = MyApplication.getInstance().getDatabasehelper().getReadableDatabase();
        ArrayList<AddEditEnquiryRequestModel> list = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME_ENQUIRY, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                AddEditEnquiryRequestModel enquiryModel = new AddEditEnquiryRequestModel();
                enquiryModel.setId(cursor.getString(cursor.getColumnIndex(ENQUIRY_ID)));
                enquiryModel.setApplicant_name(cursor.getString(cursor.getColumnIndex(APPLICANT_NAME)));
                enquiryModel.setApplicant_father_name(cursor.getString(cursor.getColumnIndex(APPLICANT_FATHER_NAME)));
                enquiryModel.setApplicant_mother_name(cursor.getString(cursor.getColumnIndex(APPLICANT_MOTHER_NAME)));
                enquiryModel.setApplicant_mobile1(cursor.getString(cursor.getColumnIndex(APPLICANT_MOBILE1)));
                enquiryModel.setApplicant_mobile2(cursor.getString(cursor.getColumnIndex(APPLICANT_MOBILE2)));
                enquiryModel.setApplicant_dob(cursor.getString(cursor.getColumnIndex(APPLICANT_DOB)));
                enquiryModel.setApplicant_aadhar_no(cursor.getString(cursor.getColumnIndex(APPLICANT_AADHAR_NO)));
                enquiryModel.setApplicant_aadhar_image(cursor.getString(cursor.getColumnIndex(APPLICANT_AADHAR_IMAGE)));
                enquiryModel.setApplicant_id_number(cursor.getString(cursor.getColumnIndex(APPLICANT_ID_NO)));
                enquiryModel.setApplicant_id_image(cursor.getString(cursor.getColumnIndex(APPLICANT_ID_IMAGE)));
                enquiryModel.setSpouse_name(cursor.getString(cursor.getColumnIndex(APPLICANT_SPOUSE_NAME)));
                enquiryModel.setApplicant_address(cursor.getString(cursor.getColumnIndex(APPLICANT_ADDRESS)));
                enquiryModel.setAdded_form(cursor.getString(cursor.getColumnIndex(APPLICANT_ADDED_FROM)));
                enquiryModel.setServer_id(cursor.getString(cursor.getColumnIndex(SERVER_ID)));
                list.add(enquiryModel);
            }
            cursor.close();
            db.close();
        }
        return list;
    }

}
