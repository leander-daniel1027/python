package com.solutionavenues.deedee.database;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DatabaseHelper extends SQLiteOpenHelper {
    private SQLiteDatabase myDataBase;
    private final Context myContext;
    private static final String DATABASE_NAME = "mfi.sqlite";
    public static String DATABASE_PATH = "";
    public static final int DATABASE_VERSION = 1;



    //Constructor
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.myContext = context;
        DATABASE_PATH = myContext.getDatabasePath(DATABASE_NAME).toString();
    }

   /* public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        throw new SQLiteException("Can't downgrade database from version " +
                oldVersion + " to " + newVersion);
    }*/

    //Create a empty database on the system
    public void createDatabase() {
        boolean dbExist = checkDataBase();
        if (dbExist) {
            Log.v("DB Exists", "db exists");
        }
        boolean dbExist1 = checkDataBase();
        if (!dbExist1) {
            this.getReadableDatabase();
            try {
                this.close();
                copyDataBase();
            } catch (IOException e) {
                throw new Error("Error copying database");
            }
        }
    }

    //Check database already exist or not
    private boolean checkDataBase() {
        boolean checkDB = false;
        try {
            String myPath = DATABASE_PATH;
            File dbfile = new File(myPath);
            checkDB = dbfile.exists();
        } catch (SQLiteException e) {
        }
        return checkDB;
    }

    //Copies your database from your local assets-folder to the just created empty database in the system folder

    private void copyDataBase() throws IOException {
        String outFileName = DATABASE_PATH;
        OutputStream myOutput = new FileOutputStream(outFileName);
        InputStream myInput = myContext.getAssets().open("databases/" + DATABASE_NAME);
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }
        myInput.close();
        myOutput.flush();
        myOutput.close();
    }

    //delete database
    public void db_delete() {
        File file = new File(DATABASE_PATH);
        if (file.exists()) {
            file.delete();
            System.out.println("delete database file.");
        }
    }

    //Open database
    public SQLiteDatabase openDatabase() throws SQLException {
        String myPath = DATABASE_PATH;
        myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
        return myDataBase;
    }

    public synchronized void closeDataBase() throws SQLException {
        if (myDataBase != null)
            myDataBase.close();
        super.close();
    }

    public void onCreate(SQLiteDatabase db) {
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion) {
            Log.v("Database Upgrade", "Database version higher than old.");
            db_delete();
        }
    }


}
