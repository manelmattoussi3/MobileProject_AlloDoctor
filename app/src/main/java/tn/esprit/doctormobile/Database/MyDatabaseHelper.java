package tn.esprit.doctormobile.Database;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import tn.esprit.doctormobile.Model.Booking;
import tn.esprit.doctormobile.Model.User;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    //SQLITEDB INSTANCE
    SQLiteDatabase db;
    private static MyDatabaseHelper databaseHelper;


    // drop table sql query
    private String DROP_USER_TABLE = "DROP TABLE IF EXISTS " + Utils.TABLE_USER;
    private String DROP_BOOKING_TABLE = "DROP TABLE IF EXISTS " + Utils.TABLE_BOOKING;
    /**
     * Constructor
     *
     * @param context
     */
    public MyDatabaseHelper(Context context) {
        super(context, Utils.DATABASE_NAME, null, Utils.DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Utils.CREATE_USER_TABLE);
        db.execSQL(Utils.CREATE_BOOKING_TABLE);

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Drop User Table if exist
        db.execSQL(DROP_USER_TABLE);
        db.execSQL(DROP_BOOKING_TABLE);
        // Create tables again
        onCreate(db);
    }


    /**
     * Singleton
     */
    public static MyDatabaseHelper DatabaseInstance(Context context) {
        if (databaseHelper == null)
            databaseHelper = new MyDatabaseHelper(context);
        return databaseHelper;
    }


    /**
     * This method is to create user record
     *
     * @param user
     */
    public void addUser(User user) {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Utils.COLUMN_USER_FULLNAME, user.getFullname());
        values.put(Utils.COLUMN_USER_NAME, user.getUsername());
        //   values.put(COLUMN_USER_PHONE, user.getPhone());
        values.put(Utils.COLUMN_USER_PASSWORD, user.getPassword());
        values.put(Utils.COLUMN_USER_ROLE, user.getRole());
        // Inserting Row
        db.insert(Utils.TABLE_USER, null, values);
        db.close();
    }
    /**
     * This method is to fetch all user and return the list of user records
     *
     * @return list
     */
    @SuppressLint("Range")
    public List<User> getAllUser() {
        // array of columns to fetch
        String[] columns = {
                Utils.COLUMN_USER_ID,
                Utils. COLUMN_USER_PHONE,
                Utils. COLUMN_USER_FULLNAME,
                Utils.COLUMN_USER_NAME,
                Utils.COLUMN_USER_PASSWORD,
                Utils.COLUMN_USER_ROLE,
                Utils.COLUMN_USER_IMAGE,
        };
        // sorting orders
        String sortOrder =
                Utils.COLUMN_USER_NAME + " ASC";
        List<User> userList = new ArrayList<User>();
        db = this.getReadableDatabase();
        // query the user table
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id,user_name,user_email,user_password FROM user ORDER BY user_name;
         */
        Cursor cursor = db.query(Utils.TABLE_USER, //Table to query
                columns,    //columns to return
                null,        //columns for the WHERE clause
                null,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                sortOrder); //The sort order
        // Traversing through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                User user = new User();
                user.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Utils.COLUMN_USER_ID))));
                user.setUsername(cursor.getString(cursor.getColumnIndex(Utils.COLUMN_USER_NAME)));
                user.setPhone(cursor.getString(cursor.getColumnIndex(Utils.COLUMN_USER_PHONE)));
                user.setFullname(cursor.getString(cursor.getColumnIndex(Utils.COLUMN_USER_FULLNAME)));
                user.setRole(cursor.getString(cursor.getColumnIndex(Utils.COLUMN_USER_ROLE)));
                user.setPassword(cursor.getString(cursor.getColumnIndex(Utils.COLUMN_USER_PASSWORD)));
                user.setImage(cursor.getBlob(cursor.getColumnIndex(Utils.COLUMN_USER_IMAGE)));
                // Adding user record to list
                userList.add(user);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        // return user list
        return userList;
    }
    /**
     * This method to update user record
     *
     * @param user
     */
    public void updateUser(String username , String password,User user) {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Utils.COLUMN_USER_FULLNAME, user.getFullname());
        values.put(Utils.COLUMN_USER_NAME, user.getUsername());
        values.put(Utils.COLUMN_USER_PASSWORD, user.getPassword());
        // selection criteria
        String selection = Utils.COLUMN_USER_NAME + " = ?" + " AND " + Utils.COLUMN_USER_PASSWORD + " = ?";
        // selection arguments
        String[] selectionArgs = {username,password};
        // values.put(Utils.COLUMN_USER_PHONE, user.getPhone());
        // updating row


        db.update(Utils.TABLE_USER, values, selection,selectionArgs
        );

        Log.d("UPDATE USER","new user="+user.getImage());
        db.close();
    }
    /**
     * This method is to delete user record
     *
     * @param user
     */
    public void deleteUser(User user) {
        db = this.getWritableDatabase();
        // delete user record by id
        db.delete(Utils.TABLE_USER, Utils.COLUMN_USER_ID + " = ?",
                new String[]{String.valueOf(user.getId())});
        db.close();
    }
    /**
     * This method to check user exist or not
     *
     * @param username
     * @return role
     */
    @SuppressLint("Range")
    public String getRoleByUsername(String username) {
        // array of columns to fetch
        String[] columns = {
                Utils.COLUMN_USER_ROLE
        };
        db = this.getReadableDatabase();
        // selection criteria
        String selection = Utils.COLUMN_USER_ROLE + " = ?";
        // selection argument
        String[] selectionArgs = {username};

        Cursor res =  db.rawQuery("select * from user where user_name='" + username + "'", null);
        res.moveToFirst();
        while(res.isAfterLast() == false){
            User response = new User();
            response.setRole(res.getString(res.getColumnIndex(Utils.COLUMN_USER_ROLE)));
            // rest of columns
            return response.getRole();
        }
        return null;
    }

    /**
     * This method to check user exist or not
     *
     * @param username
     * @return role
     */
    @SuppressLint("Range")
    public String getFullNameByUsername(String username) {
        // array of columns to fetch
        String[] columns = {
                Utils.COLUMN_USER_FULLNAME
        };
        db = this.getReadableDatabase();
        // selection criteria
        String selection = Utils.COLUMN_USER_FULLNAME + " = ?";
        // selection argument
        String[] selectionArgs = {username};

        Cursor res =  db.rawQuery("select * from user where user_name='" + username + "'", null);
        res.moveToFirst();
        while(res.isAfterLast() == false){
            User response = new User();
            response.setFullname(res.getString(res.getColumnIndex(Utils.COLUMN_USER_FULLNAME)));
            // rest of columns
            return response.getFullname();
        }
        return null;
    }

    /**
     * This method to check user exist or not
     *
     * @param username
     * @param password
     * @return true/false
     */
    public boolean checkUser(String username, String password) {
        // array of columns to fetch
        String[] columns = {
                Utils.COLUMN_USER_ID
        };
        db = this.getReadableDatabase();
        // selection criteria
        String selection = Utils.COLUMN_USER_NAME + " = ?" + " AND " + Utils.COLUMN_USER_PASSWORD + " = ?";
        // selection arguments
        String[] selectionArgs = {username, password};
        // query user table with conditions
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com' AND user_password = 'qwerty';
         */
        Cursor cursor = db.query(Utils.TABLE_USER, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                       //filter by row groups
                null);                      //The sort order
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();
        if (cursorCount > 0) {
            return true;
        }
        return false;
    }



    //ADD PATIENT PICTURE TO DB
    public void addPicture(View view ,String name, byte[] imageBytes){

        try {
            db = getWritableDatabase();

            ContentValues contentValues = new ContentValues();
            contentValues.put(Utils.COLUMN_USER_IMAGE, imageBytes);

            // selection criteria
            String selection = Utils.COLUMN_USER_NAME + " = ?";
            // selection arguments
            String[] selectionArgs = {name};
            db.update(Utils.TABLE_USER, contentValues, selection, selectionArgs);
        }catch (Exception ex){
            Log.d("Exception image","EXPECITON IMAGE="+ex.getMessage());
        }
    }


    //ADD DOCTOR PICTURE TO DB
    public void addDoctorPicture( String name, byte[] imageBytes){

        try {
            db = getWritableDatabase();

            ContentValues contentValues = new ContentValues();
            contentValues.put(Utils.COLUMN_USER_IMAGE, imageBytes);

            // selection criteria
            String selection = Utils.COLUMN_USER_NAME + " = ?";
            // selection arguments
            String[] selectionArgs = {name};
            db.update(Utils.TABLE_USER, contentValues, selection, selectionArgs);
        }catch (Exception ex){
            Log.d("Exception image","EXPECITON IMAGE="+ex.getMessage());
        }
    }

    //ADD BOOKING
    public void addBooking(Booking booking) {
        db = this.getWritableDatabase();





        ContentValues values = new ContentValues();
        values.put(Utils.COLUMN_BOOKING_DATE, booking.getDate());
        values.put(Utils.COLUMN_BOOKING_TIME, booking.getTime());
        values.put(Utils.COLUMN_BOOKING_STATUS, booking.getStatus());
        values.put(Utils.COLUMN_BOOKING_USERNAME, booking.getUsername());
        //   values.put(COLUMN_USER_PHONE, user.getPhone());
        values.put(Utils.COLUMN_BOOKING_USERPHONE, booking.getUserphone());
        values.put(Utils.COLUMN_BOOKING_DOCTOR, booking.getUserdoctor());
        // Inserting Row
        db. insert(Utils.TABLE_BOOKING, null, values);
        db.close();
    }



    /**
     * This method to check user exist or not
     *
     * @param username
     * @return booking
     */
    @SuppressLint("Range")
    public List<Booking> getBookingsByDoctor(String username) {
        // array of columns to fetch
        List<Booking> bookingList = new ArrayList<>();

        db = this.getReadableDatabase();


        Cursor cursor = db.rawQuery("select * from booking where booking_username_doctor='" + username + "'", null);

        if (cursor.moveToFirst()) {
            do {
                Booking booking = new Booking();
                booking.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Utils.COLUMN_BOOKING_ID))));
                booking.setDate(cursor.getString(cursor.getColumnIndex(Utils.COLUMN_BOOKING_DATE)));
                booking.setTime(cursor.getString(cursor.getColumnIndex(Utils.COLUMN_BOOKING_TIME)));
                booking.setUsername(cursor.getString(cursor.getColumnIndex(Utils.COLUMN_BOOKING_USERNAME)));
                booking.setStatus(cursor.getInt(cursor.getColumnIndex(Utils.COLUMN_BOOKING_STATUS)));
                // Adding user record to list
                bookingList.add(booking);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        // return user list
        return bookingList;

    }
    @SuppressLint("Range")
    public ArrayList<Booking> getBookingsByUsername(String username) {
        // array of columns to fetch
        ArrayList<Booking> bookingList = new ArrayList<>();

        db = this.getReadableDatabase();


        Cursor cursor = db.rawQuery("select * from booking where booking_username='" + username + "'", null);

        if (cursor.moveToFirst()) {
            do {
                Booking booking = new Booking();
                booking.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Utils.COLUMN_BOOKING_ID))));
                booking.setDate(cursor.getString(cursor.getColumnIndex(Utils.COLUMN_BOOKING_DATE)));
                booking.setTime(cursor.getString(cursor.getColumnIndex(Utils.COLUMN_BOOKING_TIME)));
                booking.setUsername(cursor.getString(cursor.getColumnIndex(Utils.COLUMN_BOOKING_USERNAME)));
                booking.setStatus(cursor.getInt(cursor.getColumnIndex(Utils.COLUMN_BOOKING_STATUS)));
                // Adding user record to list
                bookingList.add(booking);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        // return user list
        return bookingList;

    }


    @SuppressLint("Range")
    public ArrayList<Booking> getPendingBookingsByUsername(String username, String usernameDoctor) {
        // array of columns to fetch
        ArrayList<Booking> bookingList = new ArrayList<>();

        db = this.getReadableDatabase();


        Cursor cursor = db.rawQuery("select * from booking where booking_username='" + username + "' "+
                        "and booking_username_doctor ='" +  usernameDoctor +"' "+
                "and booking_status ='" + 0+"'" , null);

        if (cursor.moveToFirst()) {
            do {
                Booking booking = new Booking();
                booking.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Utils.COLUMN_BOOKING_ID))));
                booking.setDate(cursor.getString(cursor.getColumnIndex(Utils.COLUMN_BOOKING_DATE)));
                booking.setTime(cursor.getString(cursor.getColumnIndex(Utils.COLUMN_BOOKING_TIME)));
                booking.setUsername(cursor.getString(cursor.getColumnIndex(Utils.COLUMN_BOOKING_USERNAME)));
                booking.setStatus(cursor.getInt(cursor.getColumnIndex(Utils.COLUMN_BOOKING_STATUS)));
                // Adding user record to list
                bookingList.add(booking);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        // return user list
        return bookingList;

    }






    /**
     * This method to Confirm booking patient
     *
     * @param  idBooking
     * @return status =1
     */
    public void confirmBooking(int idBooking) {
        //CONVERT idbooking from int to string bch update t'accepti
        String idBookingString = String.valueOf(idBooking);
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Utils.COLUMN_BOOKING_STATUS, 1);

        // selection criteria
        String selection = Utils.COLUMN_BOOKING_ID + " = ?" ;
        // selection arguments
        String[] selectionArgs = {idBookingString};
        // values.put(Utils.COLUMN_USER_PHONE, user.getPhone());
        // updating row


       int updateRow= db.update(Utils.TABLE_BOOKING, values, selection,selectionArgs
        );

       if(updateRow ==1){
           Log.d("CONFIRM BOOKING","status updated ");

       }
       else {
           Log.d("ECHECK BOOKING","status failed ");

       }
        db.close();
    }
    /**
     * This method to Confirm booking patient
     *
     * @param  idBooking
     * @return status =-1
     */
    public void rejectBooking(int idBooking) {
        //CONVERT idbooking from int to string bch update t'accepti
        String idBookingString = String.valueOf(idBooking);
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Utils.COLUMN_BOOKING_STATUS, -1);

        // selection criteria
        String selection = Utils.COLUMN_BOOKING_ID + " = ?" ;
        // selection arguments
        String[] selectionArgs = {idBookingString};
        // values.put(Utils.COLUMN_USER_PHONE, user.getPhone());
        // updating row


        int updateRow= db.update(Utils.TABLE_BOOKING, values, selection,selectionArgs
        );

        if(updateRow ==1){
            Log.d("CONFIRM BOOKING","status updated ");

        }
        else {
            Log.d("ECHECK BOOKING","status failed ");

        }
        db.close();
    }

    @SuppressLint("Range")
    public byte[] retrieveImageFromDB(String username) throws UnsupportedEncodingException {
        SQLiteDatabase db=getReadableDatabase();

        Cursor cursor =  db.rawQuery("select user_image from user where user_name='" + username + "'", null);


        if (cursor.moveToFirst()&& cursor.getBlob(cursor.getColumnIndex(Utils.COLUMN_USER_IMAGE))!=null ){

            User user = new User();
            user.setImage(cursor.getBlob(cursor.getColumnIndex(Utils.COLUMN_USER_IMAGE)));
            byte[] imgByte = cursor.getBlob(0);
            cursor.close();

            return imgByte;
        }
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }

        return null;
    }

}
