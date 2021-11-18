package tn.esprit.doctormobile.Database;

import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;


//CONFIGURATIUON BASE DE DONNEES
public   class Utils {
     // Database Version
     public static final int DATABASE_VERSION = 1;
    // Database Name
     public static final String DATABASE_NAME = "AlloDoctor.db";
    //TABLES NAMES
    public static final String TABLE_USER = "user";
    public static final String TABLE_BOOKING = "booking";
    // User Table Columns names
    public static final String COLUMN_USER_ID = "user_id";
    public static final String COLUMN_USER_FULLNAME = "full_name";
    public static final String COLUMN_USER_NAME = "user_name";
    public static final String COLUMN_USER_PHONE = "user_phone";
    public static final String COLUMN_USER_PASSWORD = "user_password";
    public static final String COLUMN_USER_ROLE = "user_role";
    public static final String COLUMN_USER_IMAGE = "user_image";

    public static final String COLUMN_BOOKING_ID = "booking_id";
    public static final String COLUMN_BOOKING_TIME = "booking_time";
    public static final String COLUMN_BOOKING_DATE = "booking_date";
    public static final String COLUMN_BOOKING_USERNAME = "booking_username";
    public static final String COLUMN_BOOKING_USERPHONE = "booking_phone";
    public static final String COLUMN_BOOKING_DOCTOR = "booking_username_doctor";
    public static final String COLUMN_BOOKING_STATUS = "booking_status";
    //TABLE USER

    // create table User sql query
    public static String CREATE_USER_TABLE = "CREATE TABLE " + Utils.TABLE_USER + "("
            + Utils.COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + Utils.COLUMN_USER_NAME + " TEXT,"
            +  Utils.COLUMN_USER_ROLE + " TEXT,"
            +  Utils.COLUMN_USER_FULLNAME + " TEXT,"
            +  Utils.COLUMN_USER_IMAGE + " BLOB,"
            + Utils.COLUMN_USER_PHONE + " TEXT," + Utils.COLUMN_USER_PASSWORD + " TEXT" + ")";

    // create table Booking sql query
    public static String CREATE_BOOKING_TABLE = "CREATE TABLE " + Utils.TABLE_BOOKING + "("
            +  Utils.COLUMN_BOOKING_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            +  Utils.COLUMN_BOOKING_DATE + " TEXT,"
            +  Utils.COLUMN_BOOKING_TIME + " TEXT,"
            +  Utils.COLUMN_BOOKING_USERNAME + " TEXT,"
            +  Utils.COLUMN_BOOKING_USERPHONE + " TEXT,"
            +  Utils.COLUMN_BOOKING_DOCTOR + " TEXT,"
            +  Utils.COLUMN_BOOKING_STATUS + " INTEGER,"
            +  Utils.COLUMN_USER_PHONE + " TEXT" + ")";
    //







    //CONVERT BITMAP IMAGE TO BYTE[]
    public static byte[] getImageBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }
    //CONVERT BYTE[] IMAGE TO BITMAP
    public static Bitmap getImage(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }
    //CONVERT INPUT LIST PICTRUES/FILES TO ARRAY

    public static byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }

}
