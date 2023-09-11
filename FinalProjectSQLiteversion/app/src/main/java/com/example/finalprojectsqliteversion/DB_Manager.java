package com.example.finalprojectsqliteversion;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DB_Manager extends SQLiteOpenHelper {
    private final static String DB_NAME = "DB_1";
    private final static int DB_VER = 1;


    public final static String TBL_COMPANIES = "companies";
    public final static String COMPANY_ID = "company_id";
    public final static String NAME = "name";
    public final static String EMAIL = "email";
    public final static String PASSWORD = "password";
    private final static String CREATE_TABLE_COMPANIES =
            "CREATE TABLE IF NOT EXISTS " + TBL_COMPANIES +
                    " (" + COMPANY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    NAME + " text, " +
                    EMAIL + " text, " +
                    PASSWORD + " text)";

    public final static String TBL_CATEGORIES = "categories";
    public final static String CATEGORY_ID = "category_id";
    public final static String CATEGORY_NAME= "category_name";
    private final static String CREATE_TABLE_CATEGORIES =
            "CREATE TABLE IF NOT EXISTS " + TBL_CATEGORIES +
                    " (" + CATEGORY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    NAME + " text)";


    public final static String TBL_CUSTOMERS = "Customers";
    public final static String CUSTOMER_ID = "id";
    public final static String F_NAME = "fName";
    public final static String L_NAME = "lName";
    private final static String CREATE_TABLE_CUSTOMERS =
            "CREATE TABLE IF NOT EXISTS " + TBL_CUSTOMERS +
                    " (" + CUSTOMER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    F_NAME + " text, " +
                    L_NAME + " text, " +
                    EMAIL + " text, " +
                    PASSWORD + " text)";

    public final static String TBL_COUPONS = "Coupons";
    public final static String COUPONS_ID = "id";
    public final static String KEY_COMPANY_ID_FK = "company_id";
    public final static String KEY_CATEGORY_ID_FK = "category_id";
    public final static String COUPONS_TITLE = "title";
    public final static String COUPONS_DESCRIPTION = "description";
    public final static String COUPONS_START_DATE = "start_date";
    public final static String COUPONS_END_DATE = "end_date";
    public final static String COUPONS_AMOUNT = "amount";
    public final static String COUPONS_PRICE = "price";
    public final static String COUPONS_IMAGE = "image";
    private final static String CREATE_TABLE_COUPONS = "CREATE TABLE IF NOT EXISTS " + TBL_COUPONS + "(" +
            COUPONS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            KEY_COMPANY_ID_FK + " INTEGER," +
            KEY_CATEGORY_ID_FK + " INTEGER," +
            COUPONS_TITLE + " TEXT," +
            COUPONS_DESCRIPTION + " TEXT," +
            COUPONS_START_DATE + " DATE," +
            COUPONS_END_DATE + " DATE," +
            COUPONS_AMOUNT + " INTEGER," +
            COUPONS_PRICE + " REAL," +
            COUPONS_IMAGE + " TEXT," +
            "FOREIGN KEY(" + KEY_COMPANY_ID_FK + ") REFERENCES " + TBL_COMPANIES + "(" + COMPANY_ID + ")," +
            "FOREIGN KEY(" + KEY_CATEGORY_ID_FK + ") REFERENCES " + TBL_CATEGORIES + "(" + CATEGORY_ID + "))";


    private final static String TBL_CUSTOMERS_VS_COUPONS = "Customers_vs_Coupons";
    private final static String COUPON_ID = "CouponID";


    private final static String CREATE_TABLE_CUSTOMERS_VS_COUPONS =
            "CREATE TABLE IF NOT EXISTS " + TBL_CUSTOMERS_VS_COUPONS +
                    " (" + CUSTOMER_ID + " INTEGER, " +
                    COUPON_ID + " INTEGER, " +
                    "PRIMARY KEY (" + CUSTOMER_ID + ", " + COUPON_ID + "), " +
                    "FOREIGN KEY (" + CUSTOMER_ID + ") REFERENCES " + TBL_CUSTOMERS + "(" + CUSTOMER_ID + "), " +
                    "FOREIGN KEY (" + COUPON_ID + ") REFERENCES " + TBL_COUPONS + "(" + COUPON_ID + "))";

    //...Singleton.............................
    private static DB_Manager instance = null;

    private DB_Manager(Context context) {
        super(context, DB_NAME, null, DB_VER);
        try {
        } catch (Exception e) {
            throw e;
        }
    }

    public static DB_Manager getInstance(Context context) {
        if (instance == null) instance = new DB_Manager(context);
        return instance;
    }
    //...Singleton.............................

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_CUSTOMERS_VS_COUPONS);
        sqLiteDatabase.execSQL(CREATE_TABLE_COUPONS);
        sqLiteDatabase.execSQL(CREATE_TABLE_CUSTOMERS);
        sqLiteDatabase.execSQL(CREATE_TABLE_CATEGORIES);
        sqLiteDatabase.execSQL(CREATE_TABLE_COMPANIES);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TBL_CATEGORIES);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TBL_COMPANIES);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TBL_CUSTOMERS);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TBL_CUSTOMERS_VS_COUPONS);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TBL_COUPONS);
        onCreate(sqLiteDatabase);

    }

    public Cursor getCursor(String tableName, String[] fields, String where) {
        String strQry = "SELECT ";
        for (int i = 0; i < fields.length; i++) {
            strQry += fields[i] + " ";
            if (i < fields.length - 1)
                strQry += ",";
        }
        strQry += " FROM " + tableName;
        if (where != null && !where.isEmpty())
            strQry += " " + where;

        try {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cr = db.rawQuery(strQry, null);
            return cr;
        } catch (Exception e) {
            throw e;
        }
    }
}
//    }
//    public Cursor insertCursor(String tableName, ContentValues values) {
//        try {
//            SQLiteDatabase db = this.getWritableDatabase();
//            long rowId = db.insert(tableName, null, values);
//
//
//        } catch (Exception e) {
//            throw e;
//        }
//    }
//    public Cursor deleteCursor(String tableName, String whereClause, String[] whereArgs) {
//        try {
//            SQLiteDatabase db = this.getWritableDatabase();
//            int rowsDeleted = db.delete(tableName, whereClause, whereArgs);
//
//            // Check if any rows were deleted
//            if (rowsDeleted > 0) {
//                // If rows were deleted, return a cursor indicating the affected rows
//                return db.query(tableName, null, whereClause, whereArgs, null, null, null);
//            } else {
//                // If no rows were deleted, return null or handle the error as needed
//                return null;
//            }
//        } catch (Exception e) {
//            throw e;
//        }
//    }
//
//}