package com.solutionavenues.deedee.database.tables;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.solutionavenues.deedee.MyApplication;
import com.solutionavenues.deedee.model.request.AddFiRequestModel;

import java.util.ArrayList;

/**
 * Created by Tan on 1/26/2016.
 */
public class FiTable {


    private String TABLE_NAME_FI = "fi";
    public String LOCAL_ID = "id";
    public String FI_ID = "fi_id";
    public String FI_DATA = "fi_data";



    public long insert(AddFiRequestModel fiRequestModel) {
        SQLiteDatabase db = MyApplication.getInstance().getDatabasehelper().openDatabase();
        ContentValues values = new ContentValues();
        values.put(FI_ID, getStringJson(fiRequestModel.getId()));
        values.put(FI_DATA, getStringJson(fiRequestModel));
        // Inserting Row
        long id = db.insert(TABLE_NAME_FI, null, values);
        MyApplication.getInstance().getDatabasehelper().closeDataBase();
        return id;

    }


    public void delete(int id) {
        SQLiteDatabase db = MyApplication.getInstance().getDatabasehelper().openDatabase();
        db.delete(TABLE_NAME_FI, LOCAL_ID + "= ?" , new String[]{String.valueOf(id)});
        MyApplication.getInstance().getDatabasehelper().closeDataBase();
    }

    public int updateFiColumn(int id, String columnName, String data) {
        SQLiteDatabase db = MyApplication.getInstance().getDatabasehelper().openDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(columnName, data);
        int rowid = db.update(TABLE_NAME_FI, contentValues, LOCAL_ID + "= ?" , new String[]{String.valueOf(id)});
        MyApplication.getInstance().getDatabasehelper().closeDataBase();
        return rowid;
    }

    public int updateFiRecord(int id, AddFiRequestModel fiRequestModel) {
        SQLiteDatabase db = MyApplication.getInstance().getDatabasehelper().openDatabase();
        ContentValues values = new ContentValues();
        values.put(FI_DATA, getStringJson(fiRequestModel));
        int rowid = db.update(TABLE_NAME_FI, values, LOCAL_ID  +"= ?" , new String[]{String.valueOf(id)});
        MyApplication.getInstance().getDatabasehelper().closeDataBase();
        return rowid;
    }

    public ArrayList<AddFiRequestModel> getAllFi() {
        SQLiteDatabase db = MyApplication.getInstance().getDatabasehelper().getReadableDatabase();
        ArrayList<AddFiRequestModel> list = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME_FI + " ORDER BY " + LOCAL_ID + " DESC", null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                AddFiRequestModel fiModel = new AddFiRequestModel();
                fiModel =((AddFiRequestModel) getDataObject(AddFiRequestModel.class, cursor.getString(cursor.getColumnIndex(FI_DATA))));
                fiModel.setLocal_id(cursor.getString(cursor.getColumnIndex(LOCAL_ID)));
                fiModel.setId(cursor.getString(cursor.getColumnIndex(FI_ID)));
                list.add(fiModel);
            }
            cursor.close();
            db.close();
        }
        return list;
    }

    public boolean isValueExist(String column_name, int value) {
        SQLiteDatabase db = MyApplication.getInstance().getDatabasehelper().getReadableDatabase();
        String Query = "Select * from " + TABLE_NAME_FI + " where " + column_name + " = " + value;
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
