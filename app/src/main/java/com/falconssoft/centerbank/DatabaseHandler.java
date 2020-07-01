package com.falconssoft.centerbank;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.falconssoft.centerbank.Models.LoginINFO;
import com.falconssoft.centerbank.Models.NewAccount;
import com.falconssoft.centerbank.Models.Setting;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int VERSION = 6;
    private static final String BD_NAME = "cheque_editor";

    // ********************************************************************
    private final String ACCOUNT_TABLE = "ACCOUNT_TABLE";
    private final String ACCOUNT_ID = "ACCOUNT_ID";
    private final String ACCOUNT_BANK = "ACCOUNT_BANK";
    private final String ACCOUNT_BANK_NO = "ACCOUNT_BANK_NO";
    private final String ACCOUNT_STATUS = "ACCOUNT_STATUS";

    // ********************************************************************
    private final String SETTING_TABLE = "SETTING_TABLE";
    private final String SETTING_IP = "SETTING_IP";

    // ********************************************************************
    private final String LOGIN_TABLE = "LOGIN_TABLE";
    private final String USER_NAME = "USER_NAME";
    private final String PASSWORD = "PASSWORD";

    // ********************************************************************
    private final String SIGNUP_TABLE = "SIGNUP_TABLE";
    private final String SIGNUP_NATIONAL_ID = "SIGNUP_NATIONAL_ID";
    private final String SIGNUP_FIRST_NAME = "SIGNUP_FIRST_NAME";
    private final String SIGNUP_SECOND_NAME = "SIGNUP_SECOND_NAME";
    private final String SIGNUP_THIRD_NAME = "SIGNUP_THIRD_NAME";
    private final String SIGNUP_FOURTH_NAME = "SIGNUP_FOURTH_NAME";
    private final String SIGNUP_DOB = "SIGNUP_DOB";
    private final String SIGNUP_GENDER = "SIGNUP_GENDER";
    private final String SIGNUP_MOBILE = "SIGNUP_MOBILE";
    private final String SIGNUP_ADDRESS = "SIGNUP_ADDRESS";
    private final String SIGNUP_EMAIL = "SIGNUP_EMAIL";
    private final String SIGNUP_PASSWORD = "SIGNUP_PASSWORD";

    public DatabaseHandler(@Nullable Context context) {
        super(context, BD_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableSignup = "CREATE TABLE " + SIGNUP_TABLE
                + " ("
                + SIGNUP_NATIONAL_ID + " INTEGER, "
                + SIGNUP_FIRST_NAME + " TEXT, "
                + SIGNUP_SECOND_NAME + " TEXT, "
                + SIGNUP_THIRD_NAME + " TEXT, "
                + SIGNUP_FOURTH_NAME + " TEXT, "
                + SIGNUP_DOB + " TEXT, "
                + SIGNUP_GENDER + " TEXT, "
                + SIGNUP_MOBILE + " INTEGER, "
                + SIGNUP_ADDRESS + " TEXT, "
                + SIGNUP_EMAIL + " TEXT, "
                + SIGNUP_PASSWORD + " TEXT "
                + ")";
        db.execSQL(createTableSignup);

        String createTableAccounts = "CREATE TABLE " + ACCOUNT_TABLE
                + " ("
                + ACCOUNT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ACCOUNT_BANK + " TEXT, "
                + ACCOUNT_BANK_NO + " TEXT, "
                + ACCOUNT_STATUS + " TEXT "
                + ")";
        db.execSQL(createTableAccounts);

        String createTableSetting = "CREATE TABLE " + SETTING_TABLE
                + " ("
                + SETTING_IP + " TEXT "
                + ")";
        db.execSQL(createTableSetting);
        String createTableLOGIN = "CREATE TABLE " + LOGIN_TABLE
                + " ("
                + USER_NAME + " TEXT,"
                + PASSWORD + " TEXT "
                + ")";
        db.execSQL(createTableLOGIN);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        try {
        } catch (Exception e) {

            String createTableaccount = "ALTER TABLE ACCOUNT_TABLE ADD  ACCOUNT_STATUS TEXT";
            db.execSQL(createTableaccount);

            String createTableSignup = "CREATE TABLE IF NOT EXISTS " + SIGNUP_TABLE
                    + " ("
                    + SIGNUP_NATIONAL_ID + " INTEGER, "
                    + SIGNUP_FIRST_NAME + " TEXT, "
                    + SIGNUP_SECOND_NAME + " TEXT, "
                    + SIGNUP_THIRD_NAME + " TEXT, "
                    + SIGNUP_FOURTH_NAME + " TEXT, "
                    + SIGNUP_DOB + " TEXT, "
                    + SIGNUP_GENDER + " TEXT, "
                    + SIGNUP_MOBILE + " INTEGER, "
                    + SIGNUP_ADDRESS + " TEXT, "
                    + SIGNUP_EMAIL + " TEXT, "
                    + SIGNUP_PASSWORD + " TEXT "
                    + ")";
            db.execSQL(createTableSignup);

//            String createTableSetting = "CREATE TABLE " + SETTING_TABLE
//                    + " ("
//                    + SETTING_IP + " TEXT "
//                    + ")";
//            db.execSQL(createTableSetting);
//            Log.e("Setting","found");
//
//            String createTableLOGIN = "CREATE TABLE " + LOGIN_TABLE
//                    + " ("
//                    + USER_NAME + " TEXT,"
//                    + PASSWORD + " TEXT "
//                    + ")";
//            db.execSQL(createTableLOGIN);
        }

    }

    public void addNewAccount(NewAccount newAccount) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ACCOUNT_BANK_NO, newAccount.getAccountNo());
        contentValues.put(ACCOUNT_BANK, newAccount.getBank());
        contentValues.put(ACCOUNT_STATUS, newAccount.getStatus());

        database.insert(ACCOUNT_TABLE, null, contentValues);
        database.close();

    }

    // ************************************** ADD ************************************
    public void addSignupInfo(LoginINFO loginINFO) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(SIGNUP_ADDRESS, loginINFO.getAddress());
        contentValues.put(SIGNUP_DOB, loginINFO.getBirthDate());
        contentValues.put(SIGNUP_EMAIL, loginINFO.getEmail());
        contentValues.put(SIGNUP_FIRST_NAME, loginINFO.getFirstName());
        contentValues.put(SIGNUP_SECOND_NAME, loginINFO.getSecondName());
        contentValues.put(SIGNUP_THIRD_NAME, loginINFO.getThirdName());
        contentValues.put(SIGNUP_FOURTH_NAME, loginINFO.getFourthName());
        contentValues.put(SIGNUP_GENDER, loginINFO.getGender());
        contentValues.put(SIGNUP_MOBILE, loginINFO.getUsername());
        contentValues.put(SIGNUP_NATIONAL_ID, loginINFO.getNationalID());
        contentValues.put(SIGNUP_PASSWORD, loginINFO.getPassword());

        database.insert(SIGNUP_TABLE, null, contentValues);
        database.close();

    }

    public void addLoginInfo(LoginINFO loginINFO) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(USER_NAME, loginINFO.getUsername());
        contentValues.put(PASSWORD, loginINFO.getPassword());


        database.insert(LOGIN_TABLE, null, contentValues);
        database.close();

    }

    public void addSetting(Setting setting) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(SETTING_IP, setting.getIp());


        database.insert(SETTING_TABLE, null, contentValues);
        database.close();

    }

    // ************************************** GET ************************************
    public LoginINFO getLoginInfo() {
        SQLiteDatabase database = this.getWritableDatabase();
        String selectQuery = "SELECT  * FROM " + LOGIN_TABLE;
        Cursor cursor = database.rawQuery(selectQuery, null);
        LoginINFO user = new LoginINFO();
        if (cursor.moveToFirst()) {
            do {

                user.setUsername(cursor.getString(0));
                user.setPassword(cursor.getString(1));
                Log.e("user", "" + cursor.getString(0) + cursor.getString(1));

            } while (cursor.moveToNext());
        }
        return user;
    }

    public Setting getAllSetting() {
        SQLiteDatabase database = this.getWritableDatabase();
        String selectQuery = "SELECT  * FROM " + SETTING_TABLE;
        Cursor cursor = database.rawQuery(selectQuery, null);
        Setting item = new Setting();
        if (cursor.moveToFirst()) {
            do {

                item.setIp(cursor.getString(0));

            } while (cursor.moveToNext());
        }
        return item;
    }



    public boolean IfAccountFound(String AccountNo) {
        SQLiteDatabase database = this.getWritableDatabase();
        String selectQuery = "SELECT ACCOUNT_BANK_NO FROM " + ACCOUNT_TABLE +" where ACCOUNT_BANK_NO = '"+AccountNo +"'";
        Cursor cursor = database.rawQuery(selectQuery, null);
        boolean item = false;
        if (cursor.moveToFirst()) {
            do {
                item= true;

//                if(cursor.getString(0) !=null){
//                    item= true;
//                }else{
//                    item= false;
//                }

            } while (cursor.moveToNext());
        }
        return item;
    }


    public List<NewAccount> getAllAcCount() {
        ArrayList<NewAccount> newAccounts = new ArrayList<>();
        SQLiteDatabase database = this.getWritableDatabase();
        String selectQuery = "SELECT  * FROM " + ACCOUNT_TABLE;
        Cursor cursor = database.rawQuery(selectQuery, null);


        if (cursor.moveToFirst()) {
            do {
                NewAccount item = new NewAccount();

                item.setBank(cursor.getString(1));
                item.setAccountNo(cursor.getString(2));
                item.setStatus(cursor.getString(3));

                newAccounts.add(item);

            } while (cursor.moveToNext());
        }
        return newAccounts;
    }

    // ************************************** DELETE ************************************
    public void deleteLoginInfo() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + LOGIN_TABLE);
        db.close();
    }

    public void deleteAllSetting() {
//             Idb.execSQL("DELETE FROM "+tableName); //delete all rows in a table
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + SETTING_TABLE);
        db.close();
    }

    public void deleteAccount(String AccountNo) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM "+ACCOUNT_TABLE+" where ACCOUNT_BANK_NO = '"+AccountNo +"'"); //delete item code rows in a table
        db.close();

    }



    public void updateStatus(String Status) {
        SQLiteDatabase Idb = this.getWritableDatabase();

        Idb = this.getWritableDatabase();

        ContentValues args = new ContentValues();
        args.put(ACCOUNT_STATUS, Status);

        Idb.update(ACCOUNT_TABLE, args, null, null);


    }

}
